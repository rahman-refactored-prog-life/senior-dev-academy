package com.learningportal.continuity.monitoring;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Monitoring dashboard data class
 */
public class MonitoringDashboardData {
    private SystemStatus systemStatus;
    private LocalDateTime lastHealthCheck;
    private SystemHealthReport healthReport;
    private PerformanceReport performanceReport;
    private List<MonitoringEvent> recentEvents;
    private Map<String, SystemHealthMetric> healthMetrics;
    private AlertStatistics alertStatistics;
    
    // Getters and setters
    public SystemStatus getSystemStatus() { return systemStatus; }
    public void setSystemStatus(SystemStatus systemStatus) { this.systemStatus = systemStatus; }
    
    public LocalDateTime getLastHealthCheck() { return lastHealthCheck; }
    public void setLastHealthCheck(LocalDateTime lastHealthCheck) { this.lastHealthCheck = lastHealthCheck; }
    
    public SystemHealthReport getHealthReport() { return healthReport; }
    public void setHealthReport(SystemHealthReport healthReport) { this.healthReport = healthReport; }
    
    public PerformanceReport getPerformanceReport() { return performanceReport; }
    public void setPerformanceReport(PerformanceReport performanceReport) { this.performanceReport = performanceReport; }
    
    public List<MonitoringEvent> getRecentEvents() { return recentEvents; }
    public void setRecentEvents(List<MonitoringEvent> recentEvents) { this.recentEvents = recentEvents; }
    
    public Map<String, SystemHealthMetric> getHealthMetrics() { return healthMetrics; }
    public void setHealthMetrics(Map<String, SystemHealthMetric> healthMetrics) { this.healthMetrics = healthMetrics; }
    
    public AlertStatistics getAlertStatistics() { return alertStatistics; }
    public void setAlertStatistics(AlertStatistics alertStatistics) { this.alertStatistics = alertStatistics; }
}

/**
 * System health metric data class
 */
class SystemHealthMetric {
    private String name;
    private SystemHealthStatus status;
    private String message;
    private LocalDateTime lastUpdate;
    
    public SystemHealthMetric(String name, SystemHealthStatus status) {
        this.name = name;
        this.status = status;
        this.lastUpdate = LocalDateTime.now();
    }
    
    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public SystemHealthStatus getStatus() { return status; }
    public void setStatus(SystemHealthStatus status) { 
        this.status = status; 
        this.lastUpdate = LocalDateTime.now();
    }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public LocalDateTime getLastUpdate() { return lastUpdate; }
    public void setLastUpdate(LocalDateTime lastUpdate) { this.lastUpdate = lastUpdate; }
}