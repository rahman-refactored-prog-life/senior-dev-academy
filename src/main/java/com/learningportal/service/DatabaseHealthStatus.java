package com.learningportal.service;

import java.time.LocalDateTime;

/**
 * Database Health Status Data Class
 * 
 * Represents the current health status of the database connection and configuration.
 */
public class DatabaseHealthStatus {
    
    private LocalDateTime timestamp;
    private String overallHealth;
    private boolean connectionHealthy;
    private long connectionTimeMs;
    private String databaseVersion;
    private boolean connectionPoolHealthy;
    private String errorMessage;
    private String recommendations;
    
    // Constructors
    public DatabaseHealthStatus() {
        this.timestamp = LocalDateTime.now();
    }
    
    public DatabaseHealthStatus(String overallHealth, boolean connectionHealthy) {
        this();
        this.overallHealth = overallHealth;
        this.connectionHealthy = connectionHealthy;
    }
    
    // Helper methods
    public boolean isHealthy() {
        return "Healthy".equals(overallHealth) && connectionHealthy && connectionPoolHealthy;
    }
    
    public String getHealthIcon() {
        return switch (overallHealth) {
            case "Healthy" -> "✅";
            case "Slow Connection", "Feature Issues", "Pool Issues" -> "⚠️";
            case "Unhealthy" -> "❌";
            default -> "❓";
        };
    }
    
    public String getFormattedStatus() {
        return String.format("%s %s - Connection: %dms", 
                           getHealthIcon(), 
                           overallHealth, 
                           connectionTimeMs);
    }
    
    public HealthLevel getHealthLevel() {
        return switch (overallHealth) {
            case "Healthy" -> HealthLevel.HEALTHY;
            case "Slow Connection", "Feature Issues", "Pool Issues" -> HealthLevel.WARNING;
            case "Unhealthy" -> HealthLevel.CRITICAL;
            default -> HealthLevel.UNKNOWN;
        };
    }
    
    public enum HealthLevel {
        HEALTHY("Healthy"),
        WARNING("Warning"), 
        CRITICAL("Critical"),
        UNKNOWN("Unknown");
        
        private final String displayName;
        
        HealthLevel(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Standard getters and setters
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public String getOverallHealth() { return overallHealth; }
    public void setOverallHealth(String overallHealth) { this.overallHealth = overallHealth; }
    
    public boolean isConnectionHealthy() { return connectionHealthy; }
    public void setConnectionHealthy(boolean connectionHealthy) { this.connectionHealthy = connectionHealthy; }
    
    public long getConnectionTimeMs() { return connectionTimeMs; }
    public void setConnectionTimeMs(long connectionTimeMs) { this.connectionTimeMs = connectionTimeMs; }
    
    public String getDatabaseVersion() { return databaseVersion; }
    public void setDatabaseVersion(String databaseVersion) { this.databaseVersion = databaseVersion; }
    
    public boolean isConnectionPoolHealthy() { return connectionPoolHealthy; }
    public void setConnectionPoolHealthy(boolean connectionPoolHealthy) { 
        this.connectionPoolHealthy = connectionPoolHealthy; 
    }
    
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    
    public String getRecommendations() { return recommendations; }
    public void setRecommendations(String recommendations) { this.recommendations = recommendations; }
    
    @Override
    public String toString() {
        return "DatabaseHealthStatus{" +
                "overallHealth='" + overallHealth + '\'' +
                ", connectionHealthy=" + connectionHealthy +
                ", connectionTimeMs=" + connectionTimeMs +
                ", connectionPoolHealthy=" + connectionPoolHealthy +
                '}';
    }
}