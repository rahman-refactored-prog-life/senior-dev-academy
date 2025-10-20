package com.learningportal.service;

public class DependencyIssue {
    private String dependencyName;
    private DependencyType dependencyType;
    private IssueType issueType;
    private String description;
    private String expectedVersion;
    private String actualVersion;
    private String suggestedFix;
    private boolean required;
    
    public enum DependencyType {
        DATABASE("Database"), EXTERNAL_SERVICE("External Service"), LIBRARY("Library"),
        CONFIGURATION("Configuration"), ENVIRONMENT_VARIABLE("Environment Variable"),
        FILE_SYSTEM("File System"), NETWORK("Network");
        
        private final String displayName;
        DependencyType(String displayName) { this.displayName = displayName; }
        public String getDisplayName() { return displayName; }
    }
    
    public enum IssueType {
        MISSING("Missing"), VERSION_MISMATCH("Version Mismatch"), UNAVAILABLE("Unavailable"),
        MISCONFIGURED("Misconfigured"), PERMISSION_DENIED("Permission Denied"), TIMEOUT("Timeout");
        
        private final String displayName;
        IssueType(String displayName) { this.displayName = displayName; }
        public String getDisplayName() { return displayName; }
    }
    
    public DependencyIssue() {}
    public DependencyIssue(String dependencyName, DependencyType dependencyType, IssueType issueType, String description) {
        this.dependencyName = dependencyName;
        this.dependencyType = dependencyType;
        this.issueType = issueType;
        this.description = description;
    }
    
    public String getFormattedIssue() {
        return String.format("%s %s: %s - %s", 
                           dependencyType.getDisplayName(), dependencyName, issueType.getDisplayName(), description);
    }
    
    public boolean hasVersionInfo() { return expectedVersion != null && actualVersion != null; }
    
    public String getVersionComparison() {
        if (hasVersionInfo()) {
            return String.format("Expected: %s, Actual: %s", expectedVersion, actualVersion);
        }
        return "No version information available";
    }
    
    public String getSeverityIcon() { return required ? "🔴" : "🟡"; }
    
    // Getters and setters
    public String getDependencyName() { return dependencyName; }
    public void setDependencyName(String dependencyName) { this.dependencyName = dependencyName; }
    public DependencyType getDependencyType() { return dependencyType; }
    public void setDependencyType(DependencyType dependencyType) { this.dependencyType = dependencyType; }
    public IssueType getIssueType() { return issueType; }
    public void setIssueType(IssueType issueType) { this.issueType = issueType; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getExpectedVersion() { return expectedVersion; }
    public void setExpectedVersion(String expectedVersion) { this.expectedVersion = expectedVersion; }
    public String getActualVersion() { return actualVersion; }
    public void setActualVersion(String actualVersion) { this.actualVersion = actualVersion; }
    public String getSuggestedFix() { return suggestedFix; }
    public void setSuggestedFix(String suggestedFix) { this.suggestedFix = suggestedFix; }
    public boolean isRequired() { return required; }
    public void setRequired(boolean required) { this.required = required; }
}