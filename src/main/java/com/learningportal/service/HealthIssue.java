package com.learningportal.service;

import java.time.LocalDateTime;

/**
 * Health Issue Data Class
 * 
 * Represents a specific health issue detected during monitoring.
 */
public class HealthIssue {
    
    private String component;
    private IssueType issueType;
    private IssueSeverity severity;
    private String description;
    private LocalDateTime detectedAt;
    private String suggestedFix;
    private boolean autoFixable;
    
    public enum IssueType {
        PERFORMANCE_DEGRADATION("Performance Degradation"),
        MEMORY_LEAK("Memory Leak"),
        CONNECTION_FAILURE("Connection Failure"),
        TIMEOUT("Timeout"),
        ERROR_RATE_HIGH("High Error Rate"),
        RESOURCE_EXHAUSTION("Resource Exhaustion");
        
        private final String displayName;
        IssueType(String displayName) { this.displayName = displayName; }
        public String getDisplayName() { return displayName; }
    }
    
    public enum IssueSeverity {
        CRITICAL("Critical"), HIGH("High"), MEDIUM("Medium"), LOW("Low");
        private final String displayName;
        IssueSeverity(String displayName) { this.displayName = displayName; }
        public String getDisplayName() { return displayName; }
    }
    
    public HealthIssue() { this.detectedAt = LocalDateTime.now(); }
    
    public String getFormattedIssue() {
        return String.format("[%s] %s in %s: %s", 
                           severity.getDisplayName(), issueType.getDisplayName(), component, description);
    }
    
    // Getters and setters
    public String getComponent() { return component; }
    public void setComponent(String component) { this.component = component; }
    public IssueType getIssueType() { return issueType; }
    public void setIssueType(IssueType issueType) { this.issueType = issueType; }
    public IssueSeverity getSeverity() { return severity; }
    public void setSeverity(IssueSeverity severity) { this.severity = severity; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getDetectedAt() { return detectedAt; }
    public void setDetectedAt(LocalDateTime detectedAt) { this.detectedAt = detectedAt; }
    public String getSuggestedFix() { return suggestedFix; }
    public void setSuggestedFix(String suggestedFix) { this.suggestedFix = suggestedFix; }
    public boolean isAutoFixable() { return autoFixable; }
    public void setAutoFixable(boolean autoFixable) { this.autoFixable = autoFixable; }
}