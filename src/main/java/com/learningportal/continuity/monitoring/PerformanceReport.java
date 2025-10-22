package com.learningportal.continuity.monitoring;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Performance report data class
 */
public class PerformanceReport {
    private LocalDateTime timestamp;
    private Map<String, Double> performanceMetrics;
    private Map<String, Long> counters;
    private Map<String, Double> successRates;
    
    // Getters and setters
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public Map<String, Double> getPerformanceMetrics() { return performanceMetrics; }
    public void setPerformanceMetrics(Map<String, Double> performanceMetrics) { this.performanceMetrics = performanceMetrics; }
    
    public Map<String, Long> getCounters() { return counters; }
    public void setCounters(Map<String, Long> counters) { this.counters = counters; }
    
    public Map<String, Double> getSuccessRates() { return successRates; }
    public void setSuccessRates(Map<String, Double> successRates) { this.successRates = successRates; }
}