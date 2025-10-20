package com.learningportal.service;

import java.time.LocalDateTime;

/**
 * Component Health Status Data Class
 * 
 * Represents detailed health status of individual application components.
 */
public class ComponentHealthStatus {
    
    private String componentName;
    private HealthLevel healthLevel;
    private boolean operational;
    private String status;
    private String description;
    private LocalDateTime lastCheckTime;
    private long responseTimeMs;
    private String errorMessage;
    private double healthScore;
    
    public enum HealthLevel {
        EXCELLENT("Excellent"),
        GOOD("Good"),
        FAIR("Fair"),
        POOR("Poor"),
        CRITICAL("Critical"),
        UNKNOWN("Unknown");
        
        private final String displayName;
        HealthLevel(String displayName) { this.displayName = displayName; }
        public String getDisplayName() { return displayName; }
    }
    
    public ComponentHealthStatus() {
        this.lastCheckTime = LocalDateTime.now();
    }
    
    public ComponentHealthStatus(String componentName, HealthLevel healthLevel, boolean operational) {
        this();
        this.componentName = componentName;
        this.healthLevel = healthLevel;
        this.operational = operational;
    }
    
    public boolean isHealthy() { 
        return operational && (healthLevel == HealthLevel.EXCELLENT || healthLevel == HealthLevel.GOOD); 
    }
    
    public String getHealthIcon() {
        return switch (healthLevel) {
            case EXCELLENT -> "🟢";
            case GOOD -> "✅";
            case FAIR -> "🟡";
            case POOR -> "🟠";
            case CRITICAL -> "🔴";
            case UNKNOWN -> "❓";
        };
    }
    
    public String getFormattedStatus() {
        return String.format("%s %s: %s (%.1f%%, %dms)", 
                           getHealthIcon(), componentName, healthLevel.getDisplayName(), 
                           healthScore, responseTimeMs);
    }
    
    // Getters and setters
    public String getComponentName() { return componentName; }
    public void setComponentName(String componentName) { this.componentName = componentName; }
    public HealthLevel getHealthLevel() { return healthLevel; }
    public void setHealthLevel(HealthLevel healthLevel) { this.healthLevel = healthLevel; }
    public boolean isOperational() { return operational; }
    public void setOperational(boolean operational) { this.operational = operational; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getLastCheckTime() { return lastCheckTime; }
    public void setLastCheckTime(LocalDateTime lastCheckTime) { this.lastCheckTime = lastCheckTime; }
    public long getResponseTimeMs() { return responseTimeMs; }
    public void setResponseTimeMs(long responseTimeMs) { this.responseTimeMs = responseTimeMs; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    public double getHealthScore() { return healthScore; }
    public void setHealthScore(double healthScore) { this.healthScore = healthScore; }
}