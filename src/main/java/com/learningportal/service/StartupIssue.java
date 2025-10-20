package com.learningportal.service;

public class StartupIssue {
    private String component;
    private IssueType issueType;
    private IssueSeverity severity;
    private String description;
    private String errorMessage;
    private String suggestedFix;
    private boolean autoFixable;
    private long detectionTimeMs;
    
    public enum IssueType {
        DEPENDENCY_MISSING("Missing Dependency"),
        CONFIGURATION_ERROR("Configuration Error"),
        DATABASE_CONNECTION("Database Connection Issue"),
        PORT_CONFLICT("Port Conflict"),
        MEMORY_ISSUE("Memory Issue"),
        PERMISSION_DENIED("Permission Denied"),
        SERVICE_UNAVAILABLE("Service Unavailable"),
        TIMEOUT("Timeout"),
        INITIALIZATION_FAILURE("Initialization Failure");
        
        private final String displayName;
        IssueType(String displayName) { this.displayName = displayName; }
        public String getDisplayName() { return displayName; }
    }
    
    public enum IssueSeverity {
        CRITICAL("Critical"), HIGH("High"), MEDIUM("Medium"), LOW("Low"), INFO("Info");
        private final String displayName;
        IssueSeverity(String displayName) { this.displayName = displayName; }
        public String getDisplayName() { return displayName; }
    }
    
    public StartupIssue() {}
    public StartupIssue(String component, IssueType issueType, IssueSeverity severity, String description) {
        this.component = component;
        this.issueType = issueType;
        this.severity = severity;
        this.description = description;
    }
    
    public String getFormattedIssue() {
        return String.format("[%s] %s in %s: %s", 
                           severity.getDisplayName(), issueType.getDisplayName(), component, description);
    }
    
    public boolean isCritical() { return severity == IssueSeverity.CRITICAL; }
    public boolean canAutoFix() { return autoFixable; }
    
    public String getSeverityIcon() {
        return switch (severity) {
            case CRITICAL -> "🔴";
            case HIGH -> "🟠";
            case MEDIUM -> "🟡";
            case LOW -> "🔵";
            case INFO -> "ℹ️";
        };
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
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    public String getSuggestedFix() { return suggestedFix; }
    public void setSuggestedFix(String suggestedFix) { this.suggestedFix = suggestedFix; }
    public boolean isAutoFixable() { return autoFixable; }
    public void setAutoFixable(boolean autoFixable) { this.autoFixable = autoFixable; }
    public long getDetectionTimeMs() { return detectionTimeMs; }
    public void setDetectionTimeMs(long detectionTimeMs) { this.detectionTimeMs = detectionTimeMs; }
}