package com.learningportal.continuity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Result of phase completion validation with detailed assessment.
 */
public class PhaseValidationResult {
    
    private LocalDateTime validationTime;
    private PhaseDefinition phase;
    private boolean completed;
    private double completionScore;
    private List<String> issues;
    private List<String> recommendations;
    private String summary;
    
    public PhaseValidationResult() {}
    
    // Getters and Setters
    public LocalDateTime getValidationTime() {
        return validationTime;
    }
    
    public void setValidationTime(LocalDateTime validationTime) {
        this.validationTime = validationTime;
    }
    
    public PhaseDefinition getPhase() {
        return phase;
    }
    
    public void setPhase(PhaseDefinition phase) {
        this.phase = phase;
    }
    
    public boolean isCompleted() {
        return completed;
    }
    
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    public double getCompletionScore() {
        return completionScore;
    }
    
    public void setCompletionScore(double completionScore) {
        this.completionScore = completionScore;
    }
    
    public List<String> getIssues() {
        return issues;
    }
    
    public void setIssues(List<String> issues) {
        this.issues = issues;
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
    public void setPhaseId(String phaseId) {
        if (this.phase == null) {
            this.phase = new PhaseDefinition();
        }
        this.phase.setPhaseId(phaseId);
    }
    
    public void setPhaseName(String phaseName) {
        if (this.phase == null) {
            this.phase = new PhaseDefinition();
        }
        this.phase.setName(phaseName);
    }
    
    public void setValidationScore(double validationScore) {
        this.completionScore = validationScore;
    }
    
    public void setPhaseComplete(boolean phaseComplete) {
        this.completed = phaseComplete;
    }
    
    public boolean isPhaseComplete() {
        return this.completed;
    }
    
    public void setValidationIssues(List<String> validationIssues) {
        this.issues = validationIssues;
    }
    
    public void setNextPhaseRecommendation(NextPhaseRecommendation nextPhaseRecommendation) {
        // Store as recommendation in summary
        if (nextPhaseRecommendation != null) {
            this.summary = "Next phase: " + nextPhaseRecommendation.toString();
        }
    }
    
    @Override
    public String toString() {
        return "PhaseValidationResult{" +
                "validationTime=" + validationTime +
                ", phase=" + (phase != null ? phase.getPhaseName() : "None") +
                ", completed=" + completed +
                ", completionScore=" + String.format("%.1f", completionScore) + "%" +
                ", issues=" + (issues != null ? issues.size() : 0) +
                '}';
    }
}