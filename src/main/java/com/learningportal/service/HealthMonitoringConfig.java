package com.learningportal.service;

import java.util.List;

/**
 * Health Monitoring Configuration Data Class
 * 
 * Configuration settings for application health monitoring.
 */
public class HealthMonitoringConfig {
    
    private int healthCheckIntervalSeconds;
    private int alertThresholdMinutes;
    private boolean enableSelfHealing;
    private boolean enableAlerting;
    private List<String> monitoredComponents;
    private List<String> criticalComponents;
    private double healthScoreThreshold;
    private int maxRetryAttempts;
    private long timeoutMs;
    
    public HealthMonitoringConfig() {
        // Default values
        this.healthCheckIntervalSeconds = 60;
        this.alertThresholdMinutes = 5;
        this.enableSelfHealing = true;
        this.enableAlerting = true;
        this.healthScoreThreshold = 80.0;
        this.maxRetryAttempts = 3;
        this.timeoutMs = 30000;
    }
    
    public boolean isComponentCritical(String componentName) {
        return criticalComponents != null && criticalComponents.contains(componentName);
    }
    
    public boolean isComponentMonitored(String componentName) {
        return monitoredComponents == null || monitoredComponents.contains(componentName);
    }
    
    public boolean shouldTriggerAlert(double healthScore) {
        return healthScore < healthScoreThreshold;
    }
    
    // Getters and setters
    public int getHealthCheckIntervalSeconds() { return healthCheckIntervalSeconds; }
    public void setHealthCheckIntervalSeconds(int healthCheckIntervalSeconds) { 
        this.healthCheckIntervalSeconds = healthCheckIntervalSeconds; 
    }
    public int getAlertThresholdMinutes() { return alertThresholdMinutes; }
    public void setAlertThresholdMinutes(int alertThresholdMinutes) { 
        this.alertThresholdMinutes = alertThresholdMinutes; 
    }
    public boolean isEnableSelfHealing() { return enableSelfHealing; }
    public void setEnableSelfHealing(boolean enableSelfHealing) { this.enableSelfHealing = enableSelfHealing; }
    public boolean isEnableAlerting() { return enableAlerting; }
    public void setEnableAlerting(boolean enableAlerting) { this.enableAlerting = enableAlerting; }
    public List<String> getMonitoredComponents() { return monitoredComponents; }
    public void setMonitoredComponents(List<String> monitoredComponents) { 
        this.monitoredComponents = monitoredComponents; 
    }
    public List<String> getCriticalComponents() { return criticalComponents; }
    public void setCriticalComponents(List<String> criticalComponents) { 
        this.criticalComponents = criticalComponents; 
    }
    public double getHealthScoreThreshold() { return healthScoreThreshold; }
    public void setHealthScoreThreshold(double healthScoreThreshold) { 
        this.healthScoreThreshold = healthScoreThreshold; 
    }
    public int getMaxRetryAttempts() { return maxRetryAttempts; }
    public void setMaxRetryAttempts(int maxRetryAttempts) { this.maxRetryAttempts = maxRetryAttempts; }
    public long getTimeoutMs() { return timeoutMs; }
    public void setTimeoutMs(long timeoutMs) { this.timeoutMs = timeoutMs; }
}