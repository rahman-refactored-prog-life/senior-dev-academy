package com.learningportal.continuity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents the result of session state validation with detailed feedback.
 */
public class ValidationResult {
    
    private boolean valid;
    private double score;
    private LocalDateTime validationTime;
    private List<String> issues;
    private List<String> recommendations;
    private String summary;
    
    public ValidationResult() {}
    
    public ValidationResult(boolean valid, double score) {
        this.valid = valid;
        this.score = score;
        this.validationTime = LocalDateTime.now();
    }
    
    // Getters and Setters
    public boolean isValid() {
        return valid;
    }
    
    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
    public double getScore() {
        return score;
    }
    
    public void setScore(double score) {
        this.score = score;
    }
    
    public LocalDateTime getValidationTime() {
        return validationTime;
    }
    
    public void setValidationTime(LocalDateTime validationTime) {
        this.validationTime = validationTime;
    }
    
    public List<String> getIssues() {
        return issues;
    }
    
    public void setIssues(List<String> issues) {
        this.issues = issues;
    }
    
    public List<String> getRecommendations() {
        return recommendations;
    }
    
    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }
    
    public String getSummary() {
        return summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    // Additional methods for compatibility
    public List<String> getErrors() {
        return this.issues;
    }
    
    public void setErrors(List<String> errors) {
        this.issues = errors;
    }
    
    /**
     * Gets a formatted error message combining all issues.
     * 
     * @return Combined error message or null if no issues
     */
    public String getErrorMessage() {
        if (issues == null || issues.isEmpty()) {
            return null;
        }
        
        if (issues.size() == 1) {
            return issues.get(0);
        }
        
        return String.join("; ", issues);
    }
    
    @Override
    public String toString() {
        return "ValidationResult{" +
                "valid=" + valid +
                ", score=" + score +
                ", issues=" + (issues != null ? issues.size() : 0) +
                ", validationTime=" + validationTime +
                '}';
    }
}