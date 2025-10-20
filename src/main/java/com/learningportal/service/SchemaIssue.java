package com.learningportal.service;

/**
 * Schema Issue Data Class
 * 
 * Represents a specific database schema issue with details and resolution information.
 */
public class SchemaIssue {
    
    private String tableName;
    private String columnName;
    private IssueType issueType;
    private IssueSeverity severity;
    private String description;
    private String suggestedFix;
    private boolean autoFixable;
    
    public enum IssueType {
        MISSING_TABLE("Missing Table"),
        MISSING_COLUMN("Missing Column"),
        INCORRECT_DATA_TYPE("Incorrect Data Type"),
        MISSING_CONSTRAINT("Missing Constraint"),
        INVALID_CONSTRAINT("Invalid Constraint"),
        MISSING_INDEX("Missing Index"),
        ORPHANED_RECORD("Orphaned Record"),
        FOREIGN_KEY_VIOLATION("Foreign Key Violation");
        
        private final String displayName;
        
        IssueType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public enum IssueSeverity {
        CRITICAL("Critical"),
        HIGH("High"),
        MEDIUM("Medium"),
        LOW("Low"),
        INFO("Info");
        
        private final String displayName;
        
        IssueSeverity(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Constructors
    public SchemaIssue() {}
    
    public SchemaIssue(String tableName, IssueType issueType, IssueSeverity severity, String description) {
        this.tableName = tableName;
        this.issueType = issueType;
        this.severity = severity;
        this.description = description;
    }
    
    // Helper methods
    public String getFormattedIssue() {
        String location = columnName != null ? 
                         String.format("%s.%s", tableName, columnName) : tableName;
        return String.format("[%s] %s at %s: %s", 
                           severity.getDisplayName(),
                           issueType.getDisplayName(),
                           location,
                           description);
    }
    
    public boolean isCritical() {
        return severity == IssueSeverity.CRITICAL;
    }
    
    public boolean canAutoFix() {
        return autoFixable;
    }
    
    // Standard getters and setters
    public String getTableName() { return tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }
    
    public String getColumnName() { return columnName; }
    public void setColumnName(String columnName) { this.columnName = columnName; }
    
    public IssueType getIssueType() { return issueType; }
    public void setIssueType(IssueType issueType) { this.issueType = issueType; }
    
    public IssueSeverity getSeverity() { return severity; }
    public void setSeverity(IssueSeverity severity) { this.severity = severity; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getSuggestedFix() { return suggestedFix; }
    public void setSuggestedFix(String suggestedFix) { this.suggestedFix = suggestedFix; }
    
    public boolean isAutoFixable() { return autoFixable; }
    public void setAutoFixable(boolean autoFixable) { this.autoFixable = autoFixable; }
    
    @Override
    public String toString() {
        return "SchemaIssue{" +
                "tableName='" + tableName + '\'' +
                ", columnName='" + columnName + '\'' +
                ", issueType=" + issueType +
                ", severity=" + severity +
                ", description='" + description + '\'' +
                '}';
    }
}