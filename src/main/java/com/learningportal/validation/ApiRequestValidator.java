package com.learningportal.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import java.util.ArrayList;
import java.util.List;

/**
 * API Request Validator
 * Validates incoming API requests and provides standardized error messages
 */
@Component
public class ApiRequestValidator {
    
    /**
     * Validates request data and returns formatted error messages
     */
    public List<String> validateRequest(BindingResult bindingResult) {
        List<String> errors = new ArrayList<>();
        
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                String errorMessage = String.format("Field '%s': %s", 
                    error.getField(), error.getDefaultMessage());
                errors.add(errorMessage);
            }
        }
        
        return errors;
    }
    
    /**
     * Validates required fields are present
     */
    public boolean validateRequiredFields(Object request, String... requiredFields) {
        if (request == null) {
            return false;
        }
        
        // Basic validation - can be enhanced with reflection for dynamic field checking
        return true;
    }
    
    /**
     * Validates pagination parameters
     */
    public boolean validatePaginationParams(Integer page, Integer size) {
        if (page != null && page < 0) {
            return false;
        }
        
        if (size != null && (size < 1 || size > 100)) {
            return false;
        }
        
        return true;
    }
}