package com.learningportal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Results from application health validation
 */
public class HealthValidationResults {
    
    private boolean overallHealthy;
    private boolean startupWithinTarget;
    private boolean allComponentsHealthy;
    private boolean overallSuccess;
    
    private long startupTime;
    private SystemHealthReport healthReport;
    private Map<String, ComponentHealthStatus> componentHealthStatuses;
    private List<String> errors = new ArrayList<>();
    
    public void addError(String error) {
        errors.add(error);
    }
    
    // Getters and setters
    public boolean isOverallHealthy() { return overallHealthy; }
    public void setOverallHealthy(boolean overallHealthy) { this.overallHealthy = overallHealthy; }
    
    public boolean isStartupWithinTarget() { return startupWithinTarget; }
    public void setStartupWithinTarget(boolean startupWithinTarget) { this.startupWithinTarget = startupWithinTarget; }
    
    public boolean isAllComponentsHealthy() { return allComponentsHealthy; }
    public void setAllComponentsHealthy(boolean allComponentsHealthy) { this.allComponentsHealthy = allComponentsHealthy; }
    
    public boolean isOverallSuccess() { return overallSuccess; }
    public void setOverallSuccess(boolean overallSuccess) { this.overallSuccess = overallSuccess; }
    
    public long getStartupTime() { return startupTime; }
    public void setStartupTime(long startupTime) { this.startupTime = startupTime; }
    
    public SystemHealthReport getHealthReport() { return healthReport; }
    public void setHealthReport(SystemHealthReport healthReport) { this.healthReport = healthReport; }
    
    public Map<String, ComponentHealthStatus> getComponentHealthStatuses() { return componentHealthStatuses; }
    public void setComponentHealthStatuses(Map<String, ComponentHealthStatus> componentHealthStatuses) { this.componentHealthStatuses = componentHealthStatuses; }
    
    public List<String> getErrors() { return errors; }
    public void setErrors(List<String> errors) { this.errors = errors; }
}