package com.learningportal.continuity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Comprehensive summary of documentation update operations.
 */
public class UpdateSummary {
    
    private LocalDateTime updateTime;
    private int totalFiles;
    private int successfulUpdates;
    private int failedUpdates;
    private int totalLinesModified;
    private double successRate;
    private boolean overallSuccess;
    private List<String> recommendations;
    private String summary;
    
    public UpdateSummary() {}
    
    // Getters and Setters
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    
    public int getTotalFiles() {
        return totalFiles;
    }
    
    public void setTotalFiles(int totalFiles) {
        this.totalFiles = totalFiles;
    }
    
    public int getSuccessfulUpdates() {
        return successfulUpdates;
    }
    
    public void setSuccessfulUpdates(int successfulUpdates) {
        this.successfulUpdates = successfulUpdates;
    }
    
    public int getFailedUpdates() {
        return failedUpdates;
    }
    
    public void setFailedUpdates(int failedUpdates) {
        this.failedUpdates = failedUpdates;
    }
    
    public int getTotalLinesModified() {
        return totalLinesModified;
    }
    
    public void setTotalLinesModified(int totalLinesModified) {
        this.totalLinesModified = totalLinesModified;
    }
    
    public double getSuccessRate() {
        return successRate;
    }
    
    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }
    
    public boolean isOverallSuccess() {
        return overallSuccess;
    }
    
    public void setOverallSuccess(boolean overallSuccess) {
        this.overallSuccess = overallSuccess;
    }
    
    public List<String> getRecommendations() {
        return recommendations;
    }
    
    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }
    
    public String getSummary() {
        return summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    // Additional methods for compatibility
    public boolean isSuccess() {
        return this.overallSuccess;
    }
    
    public void setSuccess(boolean success) {
        this.overallSuccess = success;
    }
    
    public int getFilesUpdated() {
        return this.successfulUpdates;
    }
    
    public void setFilesUpdated(int filesUpdated) {
        this.successfulUpdates = filesUpdated;
    }
    
    public List<String> getErrors() {
        return this.recommendations != null ? this.recommendations : List.of();
    }
    
    public void setErrors(List<String> errors) {
        this.recommendations = errors;
    }
    
    public String getSessionId() {
        return "session-id"; // Placeholder
    }
    
    public void setSessionId(String sessionId) {
        // Store in summary for now
        if (this.summary == null) {
            this.summary = "Session: " + sessionId;
        }
    }
    
    public String getUpdateTimestamp() {
        return this.updateTime != null ? this.updateTime.toString() : null;
    }
    
    public void setUpdateTimestamp(String updateTimestamp) {
        // Parse and set updateTime if needed
    }
    
    @Override
    public String toString() {
        return "UpdateSummary{" +
                "updateTime=" + updateTime +
                ", totalFiles=" + totalFiles +
                ", successfulUpdates=" + successfulUpdates +
                ", failedUpdates=" + failedUpdates +
                ", successRate=" + String.format("%.1f", successRate) + "%" +
                ", overallSuccess=" + overallSuccess +
                '}';
    }
}