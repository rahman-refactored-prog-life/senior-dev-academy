package com.learningportal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * System Health Report Data Class
 * 
 * Contains comprehensive system health information.
 */
public class SystemHealthReport {
    
    private boolean healthy;
    private LocalDateTime reportTimestamp;
    private Map<String, ComponentStatus> componentHealth;
    private List<String> healthIssues;
    private List<String> recommendations;
    private SystemPerformanceMetrics performanceMetrics;
    private String overallHealthStatus;
    private long reportGenerationTimeMs;
    
    // Constructors
    public SystemHealthReport() {
        this.reportTimestamp = LocalDateTime.now();
    }
    
    // Helper methods
    public boolean hasHealthIssues() {
        return healthIssues != null && !healthIssues.isEmpty();
    }
    
    public boolean hasRecommendations() {
        return recommendations != null && !recommendations.isEmpty();
    }
    
    public int getHealthIssueCount() {
        return healthIssues != null ? healthIssues.size() : 0;
    }
    
    public int getHealthyComponentCount() {
        if (componentHealth == null) return 0;
        return (int) componentHealth.values().stream()
                .filter(ComponentStatus::isHealthy)
                .count();
    }
    
    public int getTotalComponentCount() {
        return componentHealth != null ? componentHealth.size() : 0;
    }
    
    public double getHealthPercentage() {
        int total = getTotalComponentCount();
        if (total == 0) return 100.0;
        return (getHealthyComponentCount() * 100.0) / total;
    }
    
    public String getHealthSummary() {
        if (healthy) {
            return String.format("✅ System healthy - %d/%d components operational (%.1f%%)", 
                               getHealthyComponentCount(), getTotalComponentCount(), getHealthPercentage());
        } else {
            return String.format("❌ System unhealthy - %d issues, %d/%d components operational (%.1f%%)", 
                               getHealthIssueCount(), getHealthyComponentCount(), 
                               getTotalComponentCount(), getHealthPercentage());
        }
    }
    
    // Standard getters and setters
    public boolean isHealthy() { return healthy; }
    public void setHealthy(boolean healthy) { this.healthy = healthy; }
    
    public LocalDateTime getReportTimestamp() { return reportTimestamp; }
    public void setReportTimestamp(LocalDateTime reportTimestamp) { 
        this.reportTimestamp = reportTimestamp; 
    }
    
    public Map<String, ComponentStatus> getComponentHealth() { return componentHealth; }
    public void setComponentHealth(Map<String, ComponentStatus> componentHealth) { 
        this.componentHealth = componentHealth; 
    }
    
    public List<String> getHealthIssues() { return healthIssues; }
    public void setHealthIssues(List<String> healthIssues) { this.healthIssues = healthIssues; }
    
    public List<String> getRecommendations() { return recommendations; }
    public void setRecommendations(List<String> recommendations) { 
        this.recommendations = recommendations; 
    }
    
    public SystemPerformanceMetrics getPerformanceMetrics() { return performanceMetrics; }
    public void setPerformanceMetrics(SystemPerformanceMetrics performanceMetrics) { 
        this.performanceMetrics = performanceMetrics; 
    }
    
    public String getOverallHealthStatus() { return overallHealthStatus; }
    public void setOverallHealthStatus(String overallHealthStatus) { 
        this.overallHealthStatus = overallHealthStatus; 
    }
    
    public long getReportGenerationTimeMs() { return reportGenerationTimeMs; }
    public void setReportGenerationTimeMs(long reportGenerationTimeMs) { 
        this.reportGenerationTimeMs = reportGenerationTimeMs; 
    }
    
    @Override
    public String toString() {
        return "SystemHealthReport{" +
                "healthy=" + healthy +
                ", healthyComponents=" + getHealthyComponentCount() +
                ", totalComponents=" + getTotalComponentCount() +
                ", healthPercentage=" + getHealthPercentage() +
                '}';
    }
}