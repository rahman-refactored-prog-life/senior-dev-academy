package com.learningportal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Schema Validation Result Data Class
 * 
 * Contains comprehensive results of database schema validation.
 */
public class SchemaValidationResult {
    
    private boolean schemaValid;
    private List<SchemaIssue> issues;
    private Map<String, TableStatus> tableStatuses;
    private List<ConstraintViolation> constraintViolations;
    private LocalDateTime validationTimestamp;
    private long validationTimeMs;
    private int totalTables;
    private int validTables;
    private int totalConstraints;
    private int validConstraints;
    
    // Constructors
    public SchemaValidationResult() {
        this.validationTimestamp = LocalDateTime.now();
    }
    
    public SchemaValidationResult(boolean schemaValid, List<SchemaIssue> issues) {
        this();
        this.schemaValid = schemaValid;
        this.issues = issues;
    }
    
    // Static factory methods
    public static SchemaValidationResult success(long validationTimeMs) {
        SchemaValidationResult result = new SchemaValidationResult(true, List.of());
        result.setValidationTimeMs(validationTimeMs);
        return result;
    }
    
    public static SchemaValidationResult failure(List<SchemaIssue> issues, long validationTimeMs) {
        SchemaValidationResult result = new SchemaValidationResult(false, issues);
        result.setValidationTimeMs(validationTimeMs);
        return result;
    }
    
    // Helper methods
    public boolean hasIssues() {
        return issues != null && !issues.isEmpty();
    }
    
    public boolean hasConstraintViolations() {
        return constraintViolations != null && !constraintViolations.isEmpty();
    }
    
    public int getIssueCount() {
        return issues != null ? issues.size() : 0;
    }
    
    public int getConstraintViolationCount() {
        return constraintViolations != null ? constraintViolations.size() : 0;
    }
    
    public double getTableValidityPercentage() {
        return totalTables > 0 ? (validTables * 100.0) / totalTables : 100.0;
    }
    
    public double getConstraintValidityPercentage() {
        return totalConstraints > 0 ? (validConstraints * 100.0) / totalConstraints : 100.0;
    }
    
    public String getValidationSummary() {
        if (schemaValid) {
            return String.format("✅ Schema validation passed - %d tables, %d constraints validated in %dms",
                               totalTables, totalConstraints, validationTimeMs);
        } else {
            return String.format("❌ Schema validation failed - %d issues, %d constraint violations found in %dms",
                               getIssueCount(), getConstraintViolationCount(), validationTimeMs);
        }
    }
    
    // Standard getters and setters
    public boolean isSchemaValid() { return schemaValid; }
    public void setSchemaValid(boolean schemaValid) { this.schemaValid = schemaValid; }
    
    public List<SchemaIssue> getIssues() { return issues; }
    public void setIssues(List<SchemaIssue> issues) { this.issues = issues; }
    
    public Map<String, TableStatus> getTableStatuses() { return tableStatuses; }
    public void setTableStatuses(Map<String, TableStatus> tableStatuses) { this.tableStatuses = tableStatuses; }
    
    public List<ConstraintViolation> getConstraintViolations() { return constraintViolations; }
    public void setConstraintViolations(List<ConstraintViolation> constraintViolations) { 
        this.constraintViolations = constraintViolations; 
    }
    
    public LocalDateTime getValidationTimestamp() { return validationTimestamp; }
    public void setValidationTimestamp(LocalDateTime validationTimestamp) { this.validationTimestamp = validationTimestamp; }
    
    public long getValidationTimeMs() { return validationTimeMs; }
    public void setValidationTimeMs(long validationTimeMs) { this.validationTimeMs = validationTimeMs; }
    
    public int getTotalTables() { return totalTables; }
    public void setTotalTables(int totalTables) { this.totalTables = totalTables; }
    
    public int getValidTables() { return validTables; }
    public void setValidTables(int validTables) { this.validTables = validTables; }
    
    public int getTotalConstraints() { return totalConstraints; }
    public void setTotalConstraints(int totalConstraints) { this.totalConstraints = totalConstraints; }
    
    public int getValidConstraints() { return validConstraints; }
    public void setValidConstraints(int validConstraints) { this.validConstraints = validConstraints; }
    
    @Override
    public String toString() {
        return "SchemaValidationResult{" +
                "schemaValid=" + schemaValid +
                ", issueCount=" + getIssueCount() +
                ", constraintViolationCount=" + getConstraintViolationCount() +
                ", validationTimeMs=" + validationTimeMs +
                '}';
    }
}