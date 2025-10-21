package com.learningportal.quality;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Documentation Validator
 * Validates that all REST endpoints have proper documentation
 */
@Component
public class DocumentationValidator {
    
    /**
     * Validates documentation completeness for a controller class
     * 
     * @param controllerClass The controller class to validate
     * @return Documentation validation report
     */
    public DocumentationReport validateDocumentation(Class<?> controllerClass) {
        DocumentationReport report = new DocumentationReport(controllerClass.getName());
        
        // Check if controller has @Tag annotation
        if (!controllerClass.isAnnotationPresent(Tag.class)) {
            report.addViolation("Controller missing @Tag annotation for API documentation");
        }
        
        // Check each endpoint method
        for (Method method : controllerClass.getDeclaredMethods()) {
            if (isRestEndpoint(method)) {
                validateEndpointDocumentation(method, report);
            }
        }
        
        return report;
    }
    
    private boolean isRestEndpoint(Method method) {
        return method.isAnnotationPresent(GetMapping.class) ||
               method.isAnnotationPresent(PostMapping.class) ||
               method.isAnnotationPresent(PutMapping.class) ||
               method.isAnnotationPresent(DeleteMapping.class) ||
               method.isAnnotationPresent(PatchMapping.class) ||
               method.isAnnotationPresent(RequestMapping.class);
    }
    
    private void validateEndpointDocumentation(Method method, DocumentationReport report) {
        String methodName = method.getName();
        
        // Check for @Operation annotation
        if (!method.isAnnotationPresent(Operation.class)) {
            report.addViolation("Method '" + methodName + "' missing @Operation annotation");
        } else {
            Operation operation = method.getAnnotation(Operation.class);
            if (operation.summary().isEmpty()) {
                report.addWarning("Method '" + methodName + "' has empty summary in @Operation");
            }
            if (operation.description().isEmpty()) {
                report.addWarning("Method '" + methodName + "' has empty description in @Operation");
            }
        }
        
        // Check for JavaDoc (simplified check)
        if (!hasJavaDocComment(method)) {
            report.addRecommendation("Method '" + methodName + "' should have JavaDoc comments");
        }
    }
    
    private boolean hasJavaDocComment(Method method) {
        // Simplified check - in real implementation, we'd parse source code
        // For now, assume methods with Operation annotation have documentation
        return method.isAnnotationPresent(Operation.class);
    }
    
    /**
     * Documentation validation report
     */
    public static class DocumentationReport {
        private final String className;
        private final List<String> violations = new ArrayList<>();
        private final List<String> warnings = new ArrayList<>();
        private final List<String> recommendations = new ArrayList<>();
        
        public DocumentationReport(String className) {
            this.className = className;
        }
        
        public void addViolation(String violation) {
            violations.add(violation);
        }
        
        public void addWarning(String warning) {
            warnings.add(warning);
        }
        
        public void addRecommendation(String recommendation) {
            recommendations.add(recommendation);
        }
        
        public boolean isCompliant() {
            return violations.isEmpty();
        }
        
        public double getDocumentationScore() {
            int totalIssues = violations.size() + (warnings.size() / 2);
            return totalIssues == 0 ? 100.0 : Math.max(0, 100 - (totalIssues * 15));
        }
        
        // Getters
        public String getClassName() { return className; }
        public List<String> getViolations() { return new ArrayList<>(violations); }
        public List<String> getWarnings() { return new ArrayList<>(warnings); }
        public List<String> getRecommendations() { return new ArrayList<>(recommendations); }
    }
}