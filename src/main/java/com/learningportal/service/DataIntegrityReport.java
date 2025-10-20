package com.learningportal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Data Integrity Report Data Class
 * 
 * Comprehensive report on data integrity across all entities and relationships.
 */
public class DataIntegrityReport {
    
    private boolean dataIntegrityValid;
    private List<EntityRelationshipIssue> relationshipIssues;
    private List<OrphanedRecordIssue> orphanedRecords;
    private List<ConstraintViolation> constraintViolations;
    private Map<String, EntityIntegrityStatus> entityStatuses;
    private LocalDateTime reportTimestamp;
    private long analysisTimeMs;
    private int totalRecords;
    private int validRecords;
    private int totalRelationships;
    private int validRelationships;
    
    // Constructors
    public DataIntegrityReport() {
        this.reportTimestamp = LocalDateTime.now();
    }
    
    // Helper methods
    public boolean hasIssues() {
        return hasRelationshipIssues() || hasOrphanedRecords() || hasConstraintViolations();
    }
    
    public boolean hasRelationshipIssues() {
        return relationshipIssues != null && !relationshipIssues.isEmpty();
    }
    
    public boolean hasOrphanedRecords() {
        return orphanedRecords != null && !orphanedRecords.isEmpty();
    }
    
    public boolean hasConstraintViolations() {
        return constraintViolations != null && !constraintViolations.isEmpty();
    }
    
    public int getTotalIssueCount() {
        return getRelationshipIssueCount() + getOrphanedRecordCount() + getConstraintViolationCount();
    }
    
    public int getRelationshipIssueCount() {
        return relationshipIssues != null ? relationshipIssues.size() : 0;
    }
    
    public int getOrphanedRecordCount() {
        return orphanedRecords != null ? orphanedRecords.size() : 0;
    }
    
    public int getConstraintViolationCount() {
        return constraintViolations != null ? constraintViolations.size() : 0;
    }
    
    public double getDataIntegrityPercentage() {
        return totalRecords > 0 ? (validRecords * 100.0) / totalRecords : 100.0;
    }
    
    public double getRelationshipIntegrityPercentage() {
        return totalRelationships > 0 ? (validRelationships * 100.0) / totalRelationships : 100.0;
    }
    
    public String getIntegritySummary() {
        if (dataIntegrityValid) {
            return String.format("✅ Data integrity validated - %d records, %d relationships checked in %dms",
                               totalRecords, totalRelationships, analysisTimeMs);
        } else {
            return String.format("❌ Data integrity issues found - %d total issues (%d relationship, %d orphaned, %d constraint violations) in %dms",
                               getTotalIssueCount(), getRelationshipIssueCount(), 
                               getOrphanedRecordCount(), getConstraintViolationCount(), analysisTimeMs);
        }
    }
    
    public String getDetailedSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append(getIntegritySummary()).append("\n");
        
        if (hasIssues()) {
            summary.append("Issues breakdown:\n");
            if (hasRelationshipIssues()) {
                summary.append(String.format("  - Relationship issues: %d\n", getRelationshipIssueCount()));
            }
            if (hasOrphanedRecords()) {
                summary.append(String.format("  - Orphaned records: %d\n", getOrphanedRecordCount()));
            }
            if (hasConstraintViolations()) {
                summary.append(String.format("  - Constraint violations: %d\n", getConstraintViolationCount()));
            }
        }
        
        summary.append(String.format("Data integrity: %.1f%% (%d/%d records)\n", 
                                    getDataIntegrityPercentage(), validRecords, totalRecords));
        summary.append(String.format("Relationship integrity: %.1f%% (%d/%d relationships)", 
                                    getRelationshipIntegrityPercentage(), validRelationships, totalRelationships));
        
        return summary.toString();
    }
    
    // Standard getters and setters
    public boolean isDataIntegrityValid() { return dataIntegrityValid; }
    public void setDataIntegrityValid(boolean dataIntegrityValid) { this.dataIntegrityValid = dataIntegrityValid; }
    
    public List<EntityRelationshipIssue> getRelationshipIssues() { return relationshipIssues; }
    public void setRelationshipIssues(List<EntityRelationshipIssue> relationshipIssues) { 
        this.relationshipIssues = relationshipIssues; 
    }
    
    public List<OrphanedRecordIssue> getOrphanedRecords() { return orphanedRecords; }
    public void setOrphanedRecords(List<OrphanedRecordIssue> orphanedRecords) { this.orphanedRecords = orphanedRecords; }
    
    public List<ConstraintViolation> getConstraintViolations() { return constraintViolations; }
    public void setConstraintViolations(List<ConstraintViolation> constraintViolations) { 
        this.constraintViolations = constraintViolations; 
    }
    
    public Map<String, EntityIntegrityStatus> getEntityStatuses() { return entityStatuses; }
    public void setEntityStatuses(Map<String, EntityIntegrityStatus> entityStatuses) { 
        this.entityStatuses = entityStatuses; 
    }
    
    public LocalDateTime getReportTimestamp() { return reportTimestamp; }
    public void setReportTimestamp(LocalDateTime reportTimestamp) { this.reportTimestamp = reportTimestamp; }
    
    public long getAnalysisTimeMs() { return analysisTimeMs; }
    public void setAnalysisTimeMs(long analysisTimeMs) { this.analysisTimeMs = analysisTimeMs; }
    
    public int getTotalRecords() { return totalRecords; }
    public void setTotalRecords(int totalRecords) { this.totalRecords = totalRecords; }
    
    public int getValidRecords() { return validRecords; }
    public void setValidRecords(int validRecords) { this.validRecords = validRecords; }
    
    public int getTotalRelationships() { return totalRelationships; }
    public void setTotalRelationships(int totalRelationships) { this.totalRelationships = totalRelationships; }
    
    public int getValidRelationships() { return validRelationships; }
    public void setValidRelationships(int validRelationships) { this.validRelationships = validRelationships; }
    
    @Override
    public String toString() {
        return "DataIntegrityReport{" +
                "dataIntegrityValid=" + dataIntegrityValid +
                ", totalIssues=" + getTotalIssueCount() +
                ", analysisTimeMs=" + analysisTimeMs +
                '}';
    }
}