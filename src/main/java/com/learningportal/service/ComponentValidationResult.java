package com.learningportal.service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Component Validation Result Data Class
 * 
 * Contains results of individual component validation.
 */
public class ComponentValidationResult {
    
    private String componentName;
    private boolean validationSuccessful;
    private LocalDateTime validationTimestamp;
    private ComponentStatus componentStatus;
    private List<String> validationIssues;
    private List<String> recommendations;
    private long validationTimeMs;
    private String overallStatus;
    
    // Constructors
    public ComponentValidationResult() {
        this.validationTimestamp = LocalDateTime.now();
    }
    
    public ComponentValidationResult(String componentName, boolean validationSuccessful) {
        this();
        this.componentName = componentName;
        this.validationSuccessful = validationSuccessful;
    }
    
    // Helper methods
    public boolean hasIssues() {
        return validationIssues != null && !validationIssues.isEmpty();
    }
    
    public boolean hasRecommendations() {
        return recommendations != null && !recommendations.isEmpty();
    }
    
    public int getIssueCount() {
        return validationIssues != null ? validationIssues.size() : 0;
    }
    
    public String getValidationSummary() {
        if (validationSuccessful) {
            return String.format("✅ %s validation passed (%dms)", componentName, validationTimeMs);
        } else {
            return String.format("❌ %s validation failed with %d issues (%dms)", 
                               componentName, getIssueCount(), validationTimeMs);
        }
    }
    
    // Standard getters and setters
    public String getComponentName() { return componentName; }
    public void setComponentName(String componentName) { this.componentName = componentName; }
    
    public boolean isValidationSuccessful() { return validationSuccessful; }
    public void setValidationSuccessful(boolean validationSuccessful) { 
        this.validationSuccessful = validationSuccessful; 
    }
    
    public LocalDateTime getValidationTimestamp() { return validationTimestamp; }
    public void setValidationTimestamp(LocalDateTime validationTimestamp) { 
        this.validationTimestamp = validationTimestamp; 
    }
    
    public ComponentStatus getComponentStatus() { return componentStatus; }
    public void setComponentStatus(ComponentStatus componentStatus) { 
        this.componentStatus = componentStatus; 
    }
    
    public List<String> getValidationIssues() { return validationIssues; }
    public void setValidationIssues(List<String> validationIssues) { 
        this.validationIssues = validationIssues; 
    }
    
    public List<String> getRecommendations() { return recommendations; }
    public void setRecommendations(List<String> recommendations) { 
        this.recommendations = recommendations; 
    }
    
    public long getValidationTimeMs() { return validationTimeMs; }
    public void setValidationTimeMs(long validationTimeMs) { 
        this.validationTimeMs = validationTimeMs; 
    }
    
    public String getOverallStatus() { return overallStatus; }
    public void setOverallStatus(String overallStatus) { this.overallStatus = overallStatus; }
    
    @Override
    public String toString() {
        return "ComponentValidationResult{" +
                "componentName='" + componentName + '\'' +
                ", validationSuccessful=" + validationSuccessful +
                ", issueCount=" + getIssueCount() +
                '}';
    }
}