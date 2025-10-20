package com.learningportal.service;

import java.time.LocalDateTime;

public class ComponentStatus {
    private String componentName;
    private ComponentHealth health;
    private boolean operational;
    private String status;
    private String description;
    private LocalDateTime lastCheckTime;
    private long responseTimeMs;
    private String version;
    private String errorMessage;
    
    public enum ComponentHealth {
        HEALTHY("Healthy"), DEGRADED("Degraded"), UNHEALTHY("Unhealthy"), UNKNOWN("Unknown");
        private final String displayName;
        ComponentHealth(String displayName) { this.displayName = displayName; }
        public String getDisplayName() { return displayName; }
    }
    
    public ComponentStatus() {
        this.lastCheckTime = LocalDateTime.now();
    }
    
    public ComponentStatus(String componentName, ComponentHealth health, boolean operational) {
        this();
        this.componentName = componentName;
        this.health = health;
        this.operational = operational;
    }
    
    public boolean isHealthy() { return health == ComponentHealth.HEALTHY && operational; }
    
    public String getHealthIcon() {
        return switch (health) {
            case HEALTHY -> "✅";
            case DEGRADED -> "⚠️";
            case UNHEALTHY -> "❌";
            case UNKNOWN -> "❓";
        };
    }
    
    public String getFormattedStatus() {
        return String.format("%s %s: %s (%dms)", 
                           getHealthIcon(), componentName, health.getDisplayName(), responseTimeMs);
    }
    
    // Getters and setters
    public String getComponentName() { return componentName; }
    public void setComponentName(String componentName) { this.componentName = componentName; }
    public ComponentHealth getHealth() { return health; }
    public void setHealth(ComponentHealth health) { this.health = health; }
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
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}