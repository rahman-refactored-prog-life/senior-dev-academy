package com.learningportal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ApplicationReadinessStatus {
    private boolean ready;
    private LocalDateTime checkTimestamp;
    private ReadinessLevel readinessLevel;
    private Map<String, ComponentReadiness> componentReadiness;
    private List<String> readinessChecks;
    private List<String> failedChecks;
    private String overallStatus;
    private long readinessCheckTimeMs;
    
    public enum ReadinessLevel {
        READY("Ready"), PARTIALLY_READY("Partially Ready"), NOT_READY("Not Ready"), UNKNOWN("Unknown");
        private final String displayName;
        ReadinessLevel(String displayName) { this.displayName = displayName; }
        public String getDisplayName() { return displayName; }
    }
    
    public static class ComponentReadiness {
        private boolean ready;
        private String status;
        private String description;
        private long checkTimeMs;
        
        public ComponentReadiness(boolean ready, String status, String description) {
            this.ready = ready;
            this.status = status;
            this.description = description;
        }
        
        public boolean isReady() { return ready; }
        public void setReady(boolean ready) { this.ready = ready; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public long getCheckTimeMs() { return checkTimeMs; }
        public void setCheckTimeMs(long checkTimeMs) { this.checkTimeMs = checkTimeMs; }
    }
    
    public ApplicationReadinessStatus() {
        this.checkTimestamp = LocalDateTime.now();
    }
    
    public boolean isReady() { return ready && readinessLevel == ReadinessLevel.READY; }
    public boolean hasFailedChecks() { return failedChecks != null && !failedChecks.isEmpty(); }
    public int getFailedCheckCount() { return failedChecks != null ? failedChecks.size() : 0; }
    public int getPassedCheckCount() {
        int totalChecks = readinessChecks != null ? readinessChecks.size() : 0;
        return totalChecks - getFailedCheckCount();
    }
    
    public double getReadinessPercentage() {
        int totalChecks = readinessChecks != null ? readinessChecks.size() : 0;
        if (totalChecks == 0) return 100.0;
        return (getPassedCheckCount() * 100.0) / totalChecks;
    }
    
    public String getReadinessIcon() {
        return switch (readinessLevel) {
            case READY -> "✅";
            case PARTIALLY_READY -> "⚠️";
            case NOT_READY -> "❌";
            case UNKNOWN -> "❓";
        };
    }
    
    public String getFormattedStatus() {
        return String.format("%s %s (%.1f%% ready) - %d/%d checks passed",
                           getReadinessIcon(), readinessLevel.getDisplayName(), getReadinessPercentage(),
                           getPassedCheckCount(), readinessChecks != null ? readinessChecks.size() : 0);
    }
    
    // Getters and setters
    public boolean isReadyFlag() { return ready; }
    public void setReady(boolean ready) { this.ready = ready; }
    public LocalDateTime getCheckTimestamp() { return checkTimestamp; }
    public void setCheckTimestamp(LocalDateTime checkTimestamp) { this.checkTimestamp = checkTimestamp; }
    public ReadinessLevel getReadinessLevel() { return readinessLevel; }
    public void setReadinessLevel(ReadinessLevel readinessLevel) { this.readinessLevel = readinessLevel; }
    public Map<String, ComponentReadiness> getComponentReadiness() { return componentReadiness; }
    public void setComponentReadiness(Map<String, ComponentReadiness> componentReadiness) { this.componentReadiness = componentReadiness; }
    public List<String> getReadinessChecks() { return readinessChecks; }
    public void setReadinessChecks(List<String> readinessChecks) { this.readinessChecks = readinessChecks; }
    public List<String> getFailedChecks() { return failedChecks; }
    public void setFailedChecks(List<String> failedChecks) { this.failedChecks = failedChecks; }
    public String getOverallStatus() { return overallStatus; }
    public void setOverallStatus(String overallStatus) { this.overallStatus = overallStatus; }
    public long getReadinessCheckTimeMs() { return readinessCheckTimeMs; }
    public void setReadinessCheckTimeMs(long readinessCheckTimeMs) { this.readinessCheckTimeMs = readinessCheckTimeMs; }
}