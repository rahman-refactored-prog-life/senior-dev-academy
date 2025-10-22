package com.learningportal.continuity;

import java.time.LocalDateTime;

/**
 * Represents the resolution of a conflict between documentation files.
 */
public class ConflictResolution {
    
    private String conflictId;
    private String conflictType;
    private String description;
    private boolean resolved;
    private String resolutionMethod;
    private String authoritativeSource;
    private LocalDateTime resolutionTime;
    private String resolutionDetails;
    
    public ConflictResolution() {}
    
    public ConflictResolution(String conflictType, String description) {
        this.conflictId = generateConflictId();
        this.conflictType = conflictType;
        this.description = description;
        this.resolutionTime = LocalDateTime.now();
    }
    
    private String generateConflictId() {
        return "conflict_" + System.currentTimeMillis();
    }
    
    // Getters and Setters
    public String getConflictId() {
        return conflictId;
    }
    
    public void setConflictId(String conflictId) {
        this.conflictId = conflictId;
    }
    
    public String getConflictType() {
        return conflictType;
    }
    
    public void setConflictType(String conflictType) {
        this.conflictType = conflictType;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean isResolved() {
        return resolved;
    }
    
    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }
    
    public String getResolutionMethod() {
        return resolutionMethod;
    }
    
    public void setResolutionMethod(String resolutionMethod) {
        this.resolutionMethod = resolutionMethod;
    }
    
    public String getAuthoritativeSource() {
        return authoritativeSource;
    }
    
    public void setAuthoritativeSource(String authoritativeSource) {
        this.authoritativeSource = authoritativeSource;
    }
    
    public LocalDateTime getResolutionTime() {
        return resolutionTime;
    }
    
    public void setResolutionTime(LocalDateTime resolutionTime) {
        this.resolutionTime = resolutionTime;
    }
    
    public String getResolutionDetails() {
        return resolutionDetails;
    }
    
    public void setResolutionDetails(String resolutionDetails) {
        this.resolutionDetails = resolutionDetails;
    }
    
    // Additional methods for compatibility
    public java.util.List<String> getAffectedFiles() {
        // Return empty list for now, can be enhanced later
        return new java.util.ArrayList<>();
    }
    
    public void setAffectedFiles(java.util.List<String> affectedFiles) {
        // Store affected files information in resolution details
        if (affectedFiles != null && !affectedFiles.isEmpty()) {
            this.resolutionDetails = "Affected files: " + String.join(", ", affectedFiles);
        }
    }
    
    public String getResolutionStrategy() {
        return this.resolutionMethod;
    }
    
    public void setResolutionStrategy(String resolutionStrategy) {
        this.resolutionMethod = resolutionStrategy;
    }
    
    @Override
    public String toString() {
        return "ConflictResolution{" +
                "conflictId='" + conflictId + '\'' +
                ", conflictType='" + conflictType + '\'' +
                ", resolved=" + resolved +
                ", resolutionMethod='" + resolutionMethod + '\'' +
                ", resolutionTime=" + resolutionTime +
                '}';
    }
}

class DocumentationConflict {
    private String conflictId;
    private String conflictType;
    private String description;
    private String[] affectedFiles;
    private String conflictingData;
    private IssueSeverity severity;
    
    public DocumentationConflict() {}
    
    // Getters and Setters
    public String getConflictId() {
        return conflictId;
    }
    
    public void setConflictId(String conflictId) {
        this.conflictId = conflictId;
    }
    
    public String getConflictType() {
        return conflictType;
    }
    
    public void setConflictType(String conflictType) {
        this.conflictType = conflictType;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String[] getAffectedFiles() {
        return affectedFiles;
    }
    
    public void setAffectedFiles(String[] affectedFiles) {
        this.affectedFiles = affectedFiles;
    }
    
    public String getConflictingData() {
        return conflictingData;
    }
    
    public void setConflictingData(String conflictingData) {
        this.conflictingData = conflictingData;
    }
    
    public IssueSeverity getSeverity() {
        return severity;
    }
    
    public void setSeverity(IssueSeverity severity) {
        this.severity = severity;
    }
}