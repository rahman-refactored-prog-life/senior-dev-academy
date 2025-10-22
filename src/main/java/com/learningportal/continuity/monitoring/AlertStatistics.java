package com.learningportal.continuity.monitoring;

import java.util.Map;

/**
 * Alert statistics data class
 */
public class AlertStatistics {
    private Map<AlertLevel, Long> alertCounts;
    private long totalAlerts;
    private Map<AlertLevel, Long> recentAlertCounts;
    private long recentTotalAlerts;
    
    // Getters and setters
    public Map<AlertLevel, Long> getAlertCounts() { return alertCounts; }
    public void setAlertCounts(Map<AlertLevel, Long> alertCounts) { this.alertCounts = alertCounts; }
    
    public long getTotalAlerts() { return totalAlerts; }
    public void setTotalAlerts(long totalAlerts) { this.totalAlerts = totalAlerts; }
    
    public Map<AlertLevel, Long> getRecentAlertCounts() { return recentAlertCounts; }
    public void setRecentAlertCounts(Map<AlertLevel, Long> recentAlertCounts) { this.recentAlertCounts = recentAlertCounts; }
    
    public long getRecentTotalAlerts() { return recentTotalAlerts; }
    public void setRecentTotalAlerts(long recentTotalAlerts) { this.recentTotalAlerts = recentTotalAlerts; }
}