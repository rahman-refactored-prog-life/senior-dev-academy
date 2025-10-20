package com.learningportal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Health Check Result Data Class
 * 
 * Contains comprehensive results of application health monitoring.
 */
public class HealthCheckResult {
    
    private boolean healthy;
    private LocalDateTime checkTimestamp;
    private Map<String, ComponentHealthStatus> componentStatuses;
    private List<String> healthIssues;
    private List<String> warnings;
    private List<String> recommendations;
    private SystemPerformanceMetrics performanceMetrics;
    private String overallHealthStatus;
    private long healthCheckTimeMs;
    
    public HealthCheckResult() {
        this.checkTimestamp = LocalDateTime.now();
    }
    
    public boolean hasHealthIssues() { return healthIssues != null && !healthIssues.isEmpty(); }
    public boolean hasWarnings() { return warnings != null && !warnings.isEmpty(); }
    public int getHealthIssueCount() { return healthIssues != null ? healthIssues.size() : 0; }
    public int getWarningCount() { return warnings != null ? warnings.size() : 0; }
    
    public String getHealthSummary() {
        if (healthy) {
            return String.format("✅ System healthy - %d warnings", getWarningCount());
        } else {
            return String.format("❌ System unhealthy - %d issues, %d warnings", 
                               getHealthIssueCount(), getWarningCount());
        }
    }
    
    // Getters and setters
    public boolean isHealthy() { return healthy; }
    public void setHealthy(boolean healthy) { this.healthy = healthy; }
    public LocalDateTime getCheckTimestamp() { return checkTimestamp; }
    public void setCheckTimestamp(LocalDateTime checkTimestamp) { this.checkTimestamp = checkTimestamp; }
    public Map<String, ComponentHealthStatus> getComponentStatuses() { return componentStatuses; }
    public void setComponentStatuses(Map<String, ComponentHealthStatus> componentStatuses) { this.componentStatuses = componentStatuses; }
    public List<String> getHealthIssues() { return healthIssues; }
    public void setHealthIssues(List<String> healthIssues) { this.healthIssues = healthIssues; }
    public List<String> getWarnings() { return warnings; }
    public void setWarnings(List<String> warnings) { this.warnings = warnings; }
    public List<String> getRecommendations() { return recommendations; }
    public void setRecommendations(List<String> recommendations) { this.recommendations = recommendations; }
    public SystemPerformanceMetrics getPerformanceMetrics() { return performanceMetrics; }
    public void setPerformanceMetrics(SystemPerformanceMetrics performanceMetrics) { this.performanceMetrics = performanceMetrics; }
    public String getOverallHealthStatus() { return overallHealthStatus; }
    public void setOverallHealthStatus(String overallHealthStatus) { this.overallHealthStatus = overallHealthStatus; }
    public long getHealthCheckTimeMs() { return healthCheckTimeMs; }
    public void setHealthCheckTimeMs(long healthCheckTimeMs) { this.healthCheckTimeMs = healthCheckTimeMs; }
}