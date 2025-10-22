package com.learningportal.continuity;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Result of storing information across all redundancy layers.
 */
public class RedundancyStorageResult {
    
    private LocalDateTime storageTime;
    private String sessionId;
    private Map<String, Boolean> layerResults;
    private int successfulLayers;
    private int totalLayers;
    private boolean overallSuccess;
    private String summary;
    
    public RedundancyStorageResult() {}
    
    // Getters and Setters
    public LocalDateTime getStorageTime() {
        return storageTime;
    }
    
    public void setStorageTime(LocalDateTime storageTime) {
        this.storageTime = storageTime;
    }
    
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public Map<String, Boolean> getLayerResults() {
        return layerResults;
    }
    
    public void setLayerResults(Map<String, Boolean> layerResults) {
        this.layerResults = layerResults;
    }
    
    public int getSuccessfulLayers() {
        return successfulLayers;
    }
    
    public void setSuccessfulLayers(int successfulLayers) {
        this.successfulLayers = successfulLayers;
    }
    
    public int getTotalLayers() {
        return totalLayers;
    }
    
    public void setTotalLayers(int totalLayers) {
        this.totalLayers = totalLayers;
    }
    
    public boolean isOverallSuccess() {
        return overallSuccess;
    }
    
    public void setOverallSuccess(boolean overallSuccess) {
        this.overallSuccess = overallSuccess;
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
    
    public java.util.List<String> getStorageLocations() {
        if (layerResults != null) {
            return new java.util.ArrayList<>(layerResults.keySet());
        }
        return new java.util.ArrayList<>();
    }
    
    public java.util.List<String> getErrors() {
        java.util.List<String> errors = new java.util.ArrayList<>();
        if (layerResults != null) {
            layerResults.entrySet().stream()
                .filter(entry -> !entry.getValue())
                .forEach(entry -> errors.add("Failed to store in: " + entry.getKey()));
        }
        return errors;
    }
    
    @Override
    public String toString() {
        return "RedundancyStorageResult{" +
                "sessionId='" + sessionId + '\'' +
                ", successfulLayers=" + successfulLayers +
                ", totalLayers=" + totalLayers +
                ", overallSuccess=" + overallSuccess +
                ", storageTime=" + storageTime +
                '}';
    }
}