package com.learningportal.continuity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Reconstructed context with confidence metrics and missing elements.
 */
public class ReconstructedContext {
    
    private double confidence;
    private List<ContextElement> reconstructedElements;
    private List<String> missingElements;
    private List<String> recommendedActions;
    private LocalDateTime reconstructionTime;
    private String summary;
    
    public ReconstructedContext() {}
    
    // Getters and Setters
    public double getConfidence() {
        return confidence;
    }
    
    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }
    
    public List<ContextElement> getReconstructedElements() {
        return reconstructedElements;
    }
    
    public void setReconstructedElements(List<ContextElement> reconstructedElements) {
        this.reconstructedElements = reconstructedElements;
    }
    
    public List<String> getMissingElements() {
        return missingElements;
    }
    
    public void setMissingElements(List<String> missingElements) {
        this.missingElements = missingElements;
    }
    
    public List<String> getRecommendedActions() {
        return recommendedActions;
    }
    
    public void setRecommendedActions(List<String> recommendedActions) {
        this.recommendedActions = recommendedActions;
    }
    
    public LocalDateTime getReconstructionTime() {
        return reconstructionTime;
    }
    
    public void setReconstructionTime(LocalDateTime reconstructionTime) {
        this.reconstructionTime = reconstructionTime;
    }
    
    public String getSummary() {
        return summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    // Additional methods for compatibility
    public String getSessionId() {
        return this.summary != null && this.summary.contains("Session ID:") ? 
            this.summary.substring(this.summary.indexOf("Session ID:") + 11).split(" ")[0] : null;
    }
    
    public void setSessionId(String sessionId) {
        this.summary = "Session ID: " + sessionId + (this.summary != null ? " - " + this.summary : "");
    }
    
    public LocalDateTime getReconstructionTimestamp() {
        return this.reconstructionTime;
    }
    
    public void setReconstructionTimestamp(LocalDateTime reconstructionTimestamp) {
        this.reconstructionTime = reconstructionTimestamp;
    }
    
    @Override
    public String toString() {
        return "ReconstructedContext{" +
                "confidence=" + String.format("%.1f", confidence) + "%" +
                ", reconstructedElements=" + (reconstructedElements != null ? reconstructedElements.size() : 0) +
                ", missingElements=" + (missingElements != null ? missingElements.size() : 0) +
                ", reconstructionTime=" + reconstructionTime +
                '}';
    }
}