package com.learningportal.service;

import java.time.LocalDateTime;

/**
 * System Performance Metrics Data Class
 * 
 * Contains comprehensive system performance metrics.
 */
public class SystemPerformanceMetrics {
    
    private LocalDateTime measurementTimestamp;
    private double cpuUsagePercentage;
    private long totalMemoryMB;
    private long freeMemoryMB;
    private long usedMemoryMB;
    private double memoryUsagePercentage;
    private int activeThreadCount;
    private long gcTimeMs;
    private long gcCount;
    private double systemLoadAverage;
    private long diskSpaceUsedMB;
    private long diskSpaceFreeMB;
    private String performanceGrade;
    
    // Constructors
    public SystemPerformanceMetrics() {
        this.measurementTimestamp = LocalDateTime.now();
    }
    
    // Helper methods
    public double getMemoryUsagePercentage() {
        if (totalMemoryMB > 0) {
            return (usedMemoryMB * 100.0) / totalMemoryMB;
        }
        return 0.0;
    }
    
    public double getDiskUsagePercentage() {
        long totalDisk = diskSpaceUsedMB + diskSpaceFreeMB;
        if (totalDisk > 0) {
            return (diskSpaceUsedMB * 100.0) / totalDisk;
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
        return String.format("%s %s - CPU: %.1f%%, Memory: %.1f%%, Threads: %d", 
                           getPerformanceIcon(), performanceGrade, 
                           cpuUsagePercentage, getMemoryUsagePercentage(), activeThreadCount);
    }
    
    public boolean isPerformanceHealthy() {
        return cpuUsagePercentage < 80.0 && 
               getMemoryUsagePercentage() < 85.0 && 
               systemLoadAverage < 2.0;
    }
    
    // Standard getters and setters
    public LocalDateTime getMeasurementTimestamp() { return measurementTimestamp; }
    public void setMeasurementTimestamp(LocalDateTime measurementTimestamp) { 
        this.measurementTimestamp = measurementTimestamp; 
    }
    
    public double getCpuUsagePercentage() { return cpuUsagePercentage; }
    public void setCpuUsagePercentage(double cpuUsagePercentage) { 
        this.cpuUsagePercentage = cpuUsagePercentage; 
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
    
    public long getGcCount() { return gcCount; }
    public void setGcCount(long gcCount) { this.gcCount = gcCount; }
    
    public double getSystemLoadAverage() { return systemLoadAverage; }
    public void setSystemLoadAverage(double systemLoadAverage) { 
        this.systemLoadAverage = systemLoadAverage; 
    }
    
    public long getDiskSpaceUsedMB() { return diskSpaceUsedMB; }
    public void setDiskSpaceUsedMB(long diskSpaceUsedMB) { 
        this.diskSpaceUsedMB = diskSpaceUsedMB; 
    }
    
    public long getDiskSpaceFreeMB() { return diskSpaceFreeMB; }
    public void setDiskSpaceFreeMB(long diskSpaceFreeMB) { 
        this.diskSpaceFreeMB = diskSpaceFreeMB; 
    }
    
    public String getPerformanceGrade() { return performanceGrade; }
    public void setPerformanceGrade(String performanceGrade) { 
        this.performanceGrade = performanceGrade; 
    }
    
    @Override
    public String toString() {
        return "SystemPerformanceMetrics{" +
                "cpuUsagePercentage=" + cpuUsagePercentage +
                ", memoryUsagePercentage=" + getMemoryUsagePercentage() +
                ", activeThreadCount=" + activeThreadCount +
                ", performanceGrade='" + performanceGrade + '\'' +
                '}';
    }
}