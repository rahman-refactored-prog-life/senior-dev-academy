package com.learningportal.validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Method;
import java.util.*;

/**
 * REST API Standards Validator
 * Validates REST endpoints follow RESTful design principles and proper HTTP status codes
 */
@Component
public class RestApiValidator {
    
    private final Map<String, HttpStatus> standardStatusCodes = Map.of(
        "GET", HttpStatus.OK,
        "POST", HttpStatus.CREATED,
        "PUT", HttpStatus.OK,
        "DELETE", HttpStatus.NO_CONTENT,
        "PATCH", HttpStatus.OK
    );
    
    /**
     * Validates REST endpoint follows RESTful principles
     */
    public ValidationResult validateEndpoint(Method method, String path) {
        ValidationResult result = new ValidationResult();
        
        // Check HTTP method annotation
        validateHttpMethod(method, result);
        
        // Check path structure
        validatePathStructure(path, result);
        
        // Check return type
        validateReturnType(method, result);
        
        return result;
    }
    
    private void validateHttpMethod(Method method, ValidationResult result) {
        boolean hasHttpMethod = method.isAnnotationPresent(GetMapping.class) ||
                               method.isAnnotationPresent(PostMapping.class) ||
                               method.isAnnotationPresent(PutMapping.class) ||
                               method.isAnnotationPresent(DeleteMapping.class) ||
                               method.isAnnotationPresent(PatchMapping.class);
        
        if (!hasHttpMethod) {
            result.addError("Method must have HTTP method annotation (@GetMapping, @PostMapping, etc.)");
        }
    }
    
    private void validatePathStructure(String path, ValidationResult result) {
        if (path == null || path.isEmpty()) {
            result.addError("Path cannot be empty");
            return;
        }
        
        // Check for proper resource naming (plural nouns)
        if (!path.matches("^/[a-z]+s(/.*)?$") && !path.equals("/")) {
            result.addWarning("Consider using plural nouns for resource paths (e.g., /users, /modules)");
        }
        
        // Check for proper parameter naming
        if (path.contains("{") && !path.matches(".*\\{[a-zA-Z][a-zA-Z0-9]*\\}.*")) {
            result.addError("Path parameters should use camelCase naming");
        }
    }
    
    private void validateReturnType(Method method, ValidationResult result) {
        Class<?> returnType = method.getReturnType();
        
        if (!ResponseEntity.class.isAssignableFrom(returnType)) {
            result.addWarning("Consider using ResponseEntity for better HTTP status control");
        }
    }
    
    /**
     * Validation result container
     */
    public static class ValidationResult {
        private final List<String> errors = new ArrayList<>();
        private final List<String> warnings = new ArrayList<>();
        
        public void addError(String error) {
            errors.add(error);
        }
        
        public void addWarning(String warning) {
            warnings.add(warning);
        }
        
        public boolean hasErrors() {
            return !errors.isEmpty();
        }
        
        public List<String> getErrors() {
            return new ArrayList<>(errors);
        }
        
        public List<String> getWarnings() {
            return new ArrayList<>(warnings);
        }
        
        public boolean isValid() {
            return errors.isEmpty();
        }
    }
}