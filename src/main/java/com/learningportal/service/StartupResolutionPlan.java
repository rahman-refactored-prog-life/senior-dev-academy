package com.learningportal.service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Startup Resolution Plan Data Class
 * 
 * Contains step-by-step resolution plan for startup issues.
 */
public class StartupResolutionPlan {
    
    private int issueCount;
    private LocalDateTime generationTimestamp;
    private List<ResolutionStep> resolutionSteps;
    private List<String> prerequisites;
    private List<String> recommendations;
    private String planSummary;
    private long estimatedResolutionTimeMinutes;
    
    // Constructors
    public StartupResolutionPlan() {
        this.generationTimestamp = LocalDateTime.now();
    }
    
    // Helper methods
    public boolean hasResolutionSteps() {
        return resolutionSteps != null && !resolutionSteps.isEmpty();
    }
    
    public boolean hasPrerequisites() {
        return prerequisites != null && !prerequisites.isEmpty();
    }
    
    public int getResolutionStepCount() {
        return resolutionSteps != null ? resolutionSteps.size() : 0;
    }
    
    public String getFormattedPlan() {
        if (issueCount == 0) {
            return "✅ No issues found - no resolution plan needed";
        } else {
            return String.format("🔧 Resolution plan for %d issues - %d steps, estimated %d minutes", 
                               issueCount, getResolutionStepCount(), estimatedResolutionTimeMinutes);
        }
    }
    
    // Standard getters and setters
    public int getIssueCount() { return issueCount; }
    public void setIssueCount(int issueCount) { this.issueCount = issueCount; }
    
    public LocalDateTime getGenerationTimestamp() { return generationTimestamp; }
    public void setGenerationTimestamp(LocalDateTime generationTimestamp) { 
        this.generationTimestamp = generationTimestamp; 
    }
    
    public List<ResolutionStep> getResolutionSteps() { return resolutionSteps; }
    public void setResolutionSteps(List<ResolutionStep> resolutionSteps) { 
        this.resolutionSteps = resolutionSteps; 
    }
    
    public List<String> getPrerequisites() { return prerequisites; }
    public void setPrerequisites(List<String> prerequisites) { this.prerequisites = prerequisites; }
    
    public List<String> getRecommendations() { return recommendations; }
    public void setRecommendations(List<String> recommendations) { 
        this.recommendations = recommendations; 
    }
    
    public String getPlanSummary() { return planSummary; }
    public void setPlanSummary(String planSummary) { this.planSummary = planSummary; }
    
    public long getEstimatedResolutionTimeMinutes() { return estimatedResolutionTimeMinutes; }
    public void setEstimatedResolutionTimeMinutes(long estimatedResolutionTimeMinutes) { 
        this.estimatedResolutionTimeMinutes = estimatedResolutionTimeMinutes; 
    }
    
    @Override
    public String toString() {
        return "StartupResolutionPlan{" +
                "issueCount=" + issueCount +
                ", resolutionStepCount=" + getResolutionStepCount() +
                ", estimatedTimeMinutes=" + estimatedResolutionTimeMinutes +
                '}';
    }
}