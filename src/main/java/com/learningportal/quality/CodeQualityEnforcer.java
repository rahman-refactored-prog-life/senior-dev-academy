package com.learningportal.quality;

import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Code Quality Enforcement Engine
 * Implements automated checking for naming conventions, method documentation, and exception handling
 */
@Component
public class CodeQualityEnforcer {
    
    /**
     * Validates code quality across multiple dimensions
     */
    public CodeQualityReport validateCodeQuality(Class<?> clazz) {
        CodeQualityReport report = new CodeQualityReport(clazz.getName());
        
        // Check naming conventions
        validateNamingConventions(clazz, report);
        
        // Check method documentation
        validateMethodDocumentation(clazz, report);
        
        // Check exception handling
        validateExceptionHandling(clazz, report);
        
        return report;
    }
    
    private void validateNamingConventions(Class<?> clazz, CodeQualityReport report) {
        // Class name should be PascalCase
        if (!isPascalCase(clazz.getSimpleName())) {
            report.addViolation("Class name should follow PascalCase convention: " + clazz.getSimpleName());
        }
        
        // Method names should be camelCase
        for (Method method : clazz.getDeclaredMethods()) {
            if (!isCamelCase(method.getName())) {
                report.addViolation("Method name should follow camelCase convention: " + method.getName());
            }
        }
    }
    
    private void validateMethodDocumentation(Class<?> clazz, CodeQualityReport report) {
        for (Method method : clazz.getDeclaredMethods()) {
            // Public methods should have JavaDoc
            if (java.lang.reflect.Modifier.isPublic(method.getModifiers())) {
                // This is a simplified check - in real implementation, we'd parse JavaDoc
                if (!hasJavaDoc(method)) {
                    report.addWarning("Public method missing JavaDoc documentation: " + method.getName());
                }
            }
        }
    }
    
    private void validateExceptionHandling(Class<?> clazz, CodeQualityReport report) {
        // Check if controller classes have proper exception handling
        if (clazz.getName().contains("Controller")) {
            boolean hasExceptionHandler = Arrays.stream(clazz.getDeclaredMethods())
                .anyMatch(method -> method.isAnnotationPresent(org.springframework.web.bind.annotation.ExceptionHandler.class));
            
            if (!hasExceptionHandler) {
                report.addRecommendation("Controller should have exception handling methods or rely on global exception handler");
            }
        }
    }
    
    private boolean isPascalCase(String name) {
        return name.matches("^[A-Z][a-zA-Z0-9]*$");
    }
    
    private boolean isCamelCase(String name) {
        return name.matches("^[a-z][a-zA-Z0-9]*$");
    }
    
    private boolean hasJavaDoc(Method method) {
        // Simplified check - in real implementation, we'd use AST parsing
        // For now, assume methods with certain annotations have documentation
        return method.getAnnotations().length > 0;
    }
    
    /**
     * Code Quality Report container
     */
    public static class CodeQualityReport {
        private final String className;
        private final List<String> violations = new ArrayList<>();
        private final List<String> warnings = new ArrayList<>();
        private final List<String> recommendations = new ArrayList<>();
        
        public CodeQualityReport(String className) {
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
        
        public boolean hasViolations() {
            return !violations.isEmpty();
        }
        
        public double getQualityScore() {
            int totalIssues = violations.size() + warnings.size();
            return totalIssues == 0 ? 100.0 : Math.max(0, 100 - (totalIssues * 10));
        }
        
        // Getters
        public String getClassName() { return className; }
        public List<String> getViolations() { return new ArrayList<>(violations); }
        public List<String> getWarnings() { return new ArrayList<>(warnings); }
        public List<String> getRecommendations() { return new ArrayList<>(recommendations); }
    }
}