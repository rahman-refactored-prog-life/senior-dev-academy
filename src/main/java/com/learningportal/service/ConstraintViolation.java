package com.learningportal.service;

/**
 * Constraint Violation Data Class
 * 
 * Represents a database constraint violation with details and resolution information.
 */
public class ConstraintViolation {
    
    private String constraintName;
    private String tableName;
    private String columnName;
    private ConstraintType constraintType;
    private String violationDescription;
    private Object violatingValue;
    private String expectedValue;
    private String suggestedFix;
    private boolean autoFixable;
    
    public enum ConstraintType {
        PRIMARY_KEY("Primary Key"),
        FOREIGN_KEY("Foreign Key"),
        UNIQUE("Unique"),
        NOT_NULL("Not Null"),
        CHECK("Check"),
        DEFAULT("Default");
        
        private final String displayName;
        
        ConstraintType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Constructors
    public ConstraintViolation() {}
    
    public ConstraintViolation(String constraintName, String tableName, 
                             ConstraintType constraintType, String violationDescription) {
        this.constraintName = constraintName;
        this.tableName = tableName;
        this.constraintType = constraintType;
        this.violationDescription = violationDescription;
    }
    
    // Helper methods
    public String getLocationString() {
        return columnName != null ? 
               String.format("%s.%s", tableName, columnName) : tableName;
    }
    
    public String getFormattedViolation() {
        return String.format("[%s Violation] %s at %s: %s",
                           constraintType.getDisplayName(),
                           constraintName != null ? constraintName : "Unnamed",
                           getLocationString(),
                           violationDescription);
    }
    
    public boolean hasViolatingValue() {
        return violatingValue != null;
    }
    
    public boolean hasExpectedValue() {
        return expectedValue != null && !expectedValue.trim().isEmpty();
    }
    
    public boolean canAutoFix() {
        return autoFixable;
    }
    
    public String getValueComparison() {
        if (hasViolatingValue() && hasExpectedValue()) {
            return String.format("Found: %s, Expected: %s", violatingValue, expectedValue);
        } else if (hasViolatingValue()) {
            return String.format("Violating value: %s", violatingValue);
        } else if (hasExpectedValue()) {
            return String.format("Expected: %s", expectedValue);
        }
        return "No value information available";
    }
    
    // Standard getters and setters
    public String getConstraintName() { return constraintName; }
    public void setConstraintName(String constraintName) { this.constraintName = constraintName; }
    
    public String getTableName() { return tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }
    
    public String getColumnName() { return columnName; }
    public void setColumnName(String columnName) { this.columnName = columnName; }
    
    public ConstraintType getConstraintType() { return constraintType; }
    public void setConstraintType(ConstraintType constraintType) { this.constraintType = constraintType; }
    
    public String getViolationDescription() { return violationDescription; }
    public void setViolationDescription(String violationDescription) { this.violationDescription = violationDescription; }
    
    public Object getViolatingValue() { return violatingValue; }
    public void setViolatingValue(Object violatingValue) { this.violatingValue = violatingValue; }
    
    public String getExpectedValue() { return expectedValue; }
    public void setExpectedValue(String expectedValue) { this.expectedValue = expectedValue; }
    
    public String getSuggestedFix() { return suggestedFix; }
    public void setSuggestedFix(String suggestedFix) { this.suggestedFix = suggestedFix; }
    
    public boolean isAutoFixable() { return autoFixable; }
    public void setAutoFixable(boolean autoFixable) { this.autoFixable = autoFixable; }
    
    @Override
    public String toString() {
        return "ConstraintViolation{" +
                "constraintName='" + constraintName + '\'' +
                ", tableName='" + tableName + '\'' +
                ", constraintType=" + constraintType +
                ", violationDescription='" + violationDescription + '\'' +
                '}';
    }
}