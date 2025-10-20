package com.learningportal.service;

/**
 * Table Status Data Class
 * 
 * Represents the status of a database table during schema validation.
 */
public class TableStatus {
    
    private String tableName;
    private boolean exists;
    private int recordCount;
    private TableHealth health;
    private String description;
    
    public enum TableHealth {
        HEALTHY("Healthy"),
        WARNING("Warning"),
        ERROR("Error"),
        MISSING("Missing");
        
        private final String displayName;
        
        TableHealth(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Constructors
    public TableStatus() {}
    
    public TableStatus(String tableName, boolean exists, int recordCount, TableHealth health) {
        this.tableName = tableName;
        this.exists = exists;
        this.recordCount = recordCount;
        this.health = health;
    }
    
    // Helper methods
    public boolean isHealthy() {
        return health == TableHealth.HEALTHY;
    }
    
    public boolean hasRecords() {
        return recordCount > 0;
    }
    
    public String getStatusSummary() {
        if (!exists) {
            return String.format("❌ Table '%s' does not exist", tableName);
        }
        
        String healthIcon = switch (health) {
            case HEALTHY -> "✅";
            case WARNING -> "⚠️";
            case ERROR -> "❌";
            case MISSING -> "❓";
        };
        
        return String.format("%s Table '%s': %s (%d records)", 
                           healthIcon, tableName, health.getDisplayName(), recordCount);
    }
    
    // Standard getters and setters
    public String getTableName() { return tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }
    
    public boolean isExists() { return exists; }
    public void setExists(boolean exists) { this.exists = exists; }
    
    public int getRecordCount() { return recordCount; }
    public void setRecordCount(int recordCount) { this.recordCount = recordCount; }
    
    public TableHealth getHealth() { return health; }
    public void setHealth(TableHealth health) { this.health = health; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    @Override
    public String toString() {
        return "TableStatus{" +
                "tableName='" + tableName + '\'' +
                ", exists=" + exists +
                ", recordCount=" + recordCount +
                ", health=" + health +
                '}';
    }
}