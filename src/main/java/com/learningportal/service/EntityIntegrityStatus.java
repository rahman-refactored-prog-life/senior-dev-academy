package com.learningportal.service;

/**
 * Entity Integrity Status Data Class
 * 
 * Represents the integrity status of a specific entity.
 */
public class EntityIntegrityStatus {
    
    private String entityName;
    private int totalRecords;
    private int validRecords;
    private int invalidRecords;
    private IntegrityStatus status;
    private String statusDescription;
    
    public enum IntegrityStatus {
        VALID("Valid"),
        WARNING("Warning"),
        INVALID("Invalid"),
        UNKNOWN("Unknown");
        
        private final String displayName;
        
        IntegrityStatus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Constructors
    public EntityIntegrityStatus() {}
    
    public EntityIntegrityStatus(String entityName, int totalRecords, int validRecords) {
        this.entityName = entityName;
        this.totalRecords = totalRecords;
        this.validRecords = validRecords;
        this.invalidRecords = totalRecords - validRecords;
        this.status = determineStatus();
    }
    
    // Helper methods
    public double getValidityPercentage() {
        return totalRecords > 0 ? (validRecords * 100.0) / totalRecords : 100.0;
    }
    
    public boolean isFullyValid() {
        return status == IntegrityStatus.VALID && invalidRecords == 0;
    }
    
    public boolean hasIssues() {
        return invalidRecords > 0 || status == IntegrityStatus.INVALID;
    }
    
    public String getStatusSummary() {
        String statusIcon = switch (status) {
            case VALID -> "✅";
            case WARNING -> "⚠️";
            case INVALID -> "❌";
            case UNKNOWN -> "❓";
        };
        
        return String.format("%s %s: %.1f%% valid (%d/%d records)",
                           statusIcon, entityName, getValidityPercentage(), 
                           validRecords, totalRecords);
    }
    
    private IntegrityStatus determineStatus() {
        if (totalRecords == 0) {
            return IntegrityStatus.UNKNOWN;
        } else if (invalidRecords == 0) {
            return IntegrityStatus.VALID;
        } else if (getValidityPercentage() >= 90.0) {
            return IntegrityStatus.WARNING;
        } else {
            return IntegrityStatus.INVALID;
        }
    }
    
    // Standard getters and setters
    public String getEntityName() { return entityName; }
    public void setEntityName(String entityName) { this.entityName = entityName; }
    
    public int getTotalRecords() { return totalRecords; }
    public void setTotalRecords(int totalRecords) { 
        this.totalRecords = totalRecords;
        this.status = determineStatus();
    }
    
    public int getValidRecords() { return validRecords; }
    public void setValidRecords(int validRecords) { 
        this.validRecords = validRecords;
        this.invalidRecords = totalRecords - validRecords;
        this.status = determineStatus();
    }
    
    public int getInvalidRecords() { return invalidRecords; }
    public void setInvalidRecords(int invalidRecords) { this.invalidRecords = invalidRecords; }
    
    public IntegrityStatus getStatus() { return status; }
    public void setStatus(IntegrityStatus status) { this.status = status; }
    
    public String getStatusDescription() { return statusDescription; }
    public void setStatusDescription(String statusDescription) { this.statusDescription = statusDescription; }
    
    @Override
    public String toString() {
        return "EntityIntegrityStatus{" +
                "entityName='" + entityName + '\'' +
                ", totalRecords=" + totalRecords +
                ", validRecords=" + validRecords +
                ", status=" + status +
                '}';
    }
}