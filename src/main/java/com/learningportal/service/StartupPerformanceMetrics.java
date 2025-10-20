package com.learningportal.service;

import java.time.LocalDateTime;

/**
 * Startup Performance Metrics Data Class
 * 
 * Contains performance metrics for application startup.
 */
public class StartupPerformanceMetrics {
    
    private long startupTimeMs;
    private String performanceGrade;
    private LocalDateTime measurementTimestamp;
    private long totalMemoryMB;
    private long freeMemoryMB;
    private long usedMemoryMB;
    private double memoryUsagePercentage;
    private int activeThreadCount;
    private long gcTimeMs;
    private String jvmVersion;
    
    // Constructors
    public StartupPerformanceMetrics() {
        this.measurementTimestamp = LocalDateTime.now();
    }
    
    // Helper methods
    public double getMemoryUsagePercentage() {
        if (totalMemoryMB > 0) {
            return (usedMemoryMB * 100.0) / totalMemoryMB;
        }
        return 0.0;
    }
    
    public String getPerformanceIcon() {
        return switch (performanceGrade) {
            case "Excellent" -> "🚀";
            case "Good" -> "✅";
            case "Fair" -> "⚠️";
            case "Poor" -> "❌";
            default -> "❓";
        };
    }
    
    public String getFormattedMetrics() {
        return String.format("%s %s startup in %dms - Memory: %dMB/%.1f%% used", 
                           getPerformanceIcon(), performanceGrade, startupTimeMs, 
                           usedMemoryMB, getMemoryUsagePercentage());
    }
    
    public boolean isPerformanceAcceptable() {
        return "Excellent".equals(performanceGrade) || "Good".equals(performanceGrade);
    }
    
    public String getPerformanceRecommendation() {
        return switch (performanceGrade) {
            case "Excellent" -> "Startup performance is excellent - no optimization needed";
            case "Good" -> "Startup performance is good - monitor for degradation";
            case "Fair" -> "Startup performance is fair - consider optimization";
            case "Poor" -> "Startup performance is poor - optimization required";
            default -> "Performance grade unknown - unable to provide recommendation";
        };
    }
    
    // Standard getters and setters
    public long getStartupTimeMs() { return startupTimeMs; }
    public void setStartupTimeMs(long startupTimeMs) { this.startupTimeMs = startupTimeMs; }
    
    public String getPerformanceGrade() { return performanceGrade; }
    public void setPerformanceGrade(String performanceGrade) { 
        this.performanceGrade = performanceGrade; 
    }
    
    public LocalDateTime getMeasurementTimestamp() { return measurementTimestamp; }
    public void setMeasurementTimestamp(LocalDateTime measurementTimestamp) { 
        this.measurementTimestamp = measurementTimestamp; 
    }
    
    public long getTotalMemoryMB() { return totalMemoryMB; }
    public void setTotalMemoryMB(long totalMemoryMB) { this.totalMemoryMB = totalMemoryMB; }
    
    public long getFreeMemoryMB() { return freeMemoryMB; }
    public void setFreeMemoryMB(long freeMemoryMB) { this.freeMemoryMB = freeMemoryMB; }
    
    public long getUsedMemoryMB() { return usedMemoryMB; }
    public void setUsedMemoryMB(long usedMemoryMB) { this.usedMemoryMB = usedMemoryMB; }
    
    public void setMemoryUsagePercentage(double memoryUsagePercentage) { 
        this.memoryUsagePercentage = memoryUsagePercentage; 
    }
    
    public int getActiveThreadCount() { return activeThreadCount; }
    public void setActiveThreadCount(int activeThreadCount) { 
        this.activeThreadCount = activeThreadCount; 
    }
    
    public long getGcTimeMs() { return gcTimeMs; }
    public void setGcTimeMs(long gcTimeMs) { this.gcTimeMs = gcTimeMs; }
    
    public String getJvmVersion() { return jvmVersion; }
    public void setJvmVersion(String jvmVersion) { this.jvmVersion = jvmVersion; }
    
    @Override
    public String toString() {
        return "StartupPerformanceMetrics{" +
                "startupTimeMs=" + startupTimeMs +
                ", performanceGrade='" + performanceGrade + '\'' +
                ", usedMemoryMB=" + usedMemoryMB +
                ", memoryUsagePercentage=" + getMemoryUsagePercentage() +
                '}';
    }
}