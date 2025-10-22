package com.learningportal.continuity;

import java.time.LocalDateTime;

/**
 * Represents the database connection and schema status.
 */
public class DatabaseStatus {
    
    private boolean connected;
    private String databaseType;
    private String connectionUrl;
    private String schemaVersion;
    private LocalDateTime lastConnectionTime;
    private int tableCount;
    private long recordCount;
    private String healthStatus;
    
    public DatabaseStatus() {}
    
    public DatabaseStatus(boolean connected, String databaseType) {
        this.connected = connected;
        this.databaseType = databaseType;
        this.lastConnectionTime = LocalDateTime.now();
    }
    
    // Getters and Setters
    public boolean isConnected() {
        return connected;
    }
    
    public void setConnected(boolean connected) {
        this.connected = connected;
    }
    
    public String getDatabaseType() {
        return databaseType;
    }
    
    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }
    
    public String getConnectionUrl() {
        return connectionUrl;
    }
    
    public void setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }
    
    public String getSchemaVersion() {
        return schemaVersion;
    }
    
    public void setSchemaVersion(String schemaVersion) {
        this.schemaVersion = schemaVersion;
    }
    
    public LocalDateTime getLastConnectionTime() {
        return lastConnectionTime;
    }
    
    public void setLastConnectionTime(LocalDateTime lastConnectionTime) {
        this.lastConnectionTime = lastConnectionTime;
    }
    
    public int getTableCount() {
        return tableCount;
    }
    
    public void setTableCount(int tableCount) {
        this.tableCount = tableCount;
    }
    
    public long getRecordCount() {
        return recordCount;
    }
    
    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }
    
    public String getHealthStatus() {
        return healthStatus;
    }
    
    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }
    
    // Additional methods for compatibility
    public boolean isSchemaValid() {
        return this.connected && this.schemaVersion != null && !this.schemaVersion.isEmpty();
    }
    
    public void setSchemaValid(boolean schemaValid) {
        if (schemaValid) {
            this.schemaVersion = "valid";
        } else {
            this.schemaVersion = null;
        }
    }
    
    public String getUrl() {
        return this.connectionUrl;
    }
    
    public void setUrl(String url) {
        this.connectionUrl = url;
    }
    
    @Override
    public String toString() {
        return "DatabaseStatus{" +
                "connected=" + connected +
                ", databaseType='" + databaseType + '\'' +
                ", schemaVersion='" + schemaVersion + '\'' +
                ", tableCount=" + tableCount +
                ", healthStatus='" + healthStatus + '\'' +
                '}';
    }
}