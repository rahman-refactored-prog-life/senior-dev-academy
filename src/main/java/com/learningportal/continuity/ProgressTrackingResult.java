package com.learningportal.continuity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Result of progress tracking with detailed metrics and insights.
 */
public class ProgressTrackingResult {
    
    private LocalDateTime trackingTime;
    private PhaseDefinition currentPhase;
    private double phaseCompletionPercentage;
    private double overallProgressPercentage;
    private List<DeliverableStatus> deliverableStatuses;
    private int timeSpent;
    private int estimatedTimeRemaining;
    private List<String> progressInsights;
    
    public ProgressTrackingResult() {}
    
    // Getters and Setters
    public LocalDateTime getTrackingTime() {
        return trackingTime;
    }
    
    public void setTrackingTime(LocalDateTime trackingTime) {
        this.trackingTime = trackingTime;
    }
    
    public PhaseDefinition getCurrentPhase() {
        return currentPhase;
    }
    
    public void setCurrentPhase(PhaseDefinition currentPhase) {
        this.currentPhase = currentPhase;
    }
    
    public void setCurrentPhase(String currentPhaseName) {
        // Create a simple PhaseDefinition from the name
        PhaseDefinition phase = new PhaseDefinition();
        phase.setName(currentPhaseName);
        phase.setPhaseId(currentPhaseName);
        this.currentPhase = phase;
    }
    
    public double getPhaseCompletionPercentage() {
        return phaseCompletionPercentage;
    }
    
    public void setPhaseCompletionPercentage(double phaseCompletionPercentage) {
        this.phaseCompletionPercentage = phaseCompletionPercentage;
    }
    
    public double getOverallProgressPercentage() {
        return overallProgressPercentage;
    }
    
    public void setOverallProgressPercentage(double overallProgressPercentage) {
        this.overallProgressPercentage = overallProgressPercentage;
    }
    
    public List<DeliverableStatus> getDeliverableStatuses() {
        return deliverableStatuses;
    }
    
    public void setDeliverableStatuses(List<DeliverableStatus> deliverableStatuses) {
        this.deliverableStatuses = deliverableStatuses;
    }
    
    public int getTimeSpent() {
        return timeSpent;
    }
    
    public void setTimeSpent(int timeSpent) {
        this.timeSpent = timeSpent;
    }
    
    public int getEstimatedTimeRemaining() {
        return estimatedTimeRemaining;
    }
    
    public void setEstimatedTimeRemaining(int estimatedTimeRemaining) {
        this.estimatedTimeRemaining = estimatedTimeRemaining;
    }
    
    public List<String> getProgressInsights() {
        return progressInsights;
    }
    
    public void setProgressInsights(List<String> progressInsights) {
        this.progressInsights = progressInsights;
    }
    
    // Additional methods for compatibility
    public void setPhaseProgress(double phaseProgress) {
        this.phaseCompletionPercentage = phaseProgress;
    }
    
    public double getPhaseProgress() {
        return this.phaseCompletionPercentage;
    }
    
    public void setOverallProgress(double overallProgress) {
        this.overallProgressPercentage = overallProgress;
    }
    
    public double getOverallProgress() {
        return this.overallProgressPercentage;
    }
    
    public void setCompletedDeliverables(int completedDeliverables) {
        // Implementation based on deliverable statuses
    }
    
    public void setTotalDeliverables(int totalDeliverables) {
        // Implementation based on deliverable statuses
    }
    
    public void setNextActions(List<String> nextActions) {
        this.progressInsights = nextActions;
    }
    
    public void setPhaseComplete(boolean phaseComplete) {
        if (phaseComplete) {
            this.phaseCompletionPercentage = 100.0;
        }
    }
    
    public boolean isPhaseComplete() {
        return this.phaseCompletionPercentage >= 100.0;
    }
    
    private boolean readyForNextPhase;
    
    public boolean isReadyForNextPhase() {
        return this.readyForNextPhase;
    }
    
    public void setReadyForNextPhase(boolean readyForNextPhase) {
        this.readyForNextPhase = readyForNextPhase;
    }
    
    @Override
    public String toString() {
        return "ProgressTrackingResult{" +
                "trackingTime=" + trackingTime +
                ", currentPhase=" + (currentPhase != null ? currentPhase.getPhaseName() : "None") +
                ", phaseCompletionPercentage=" + String.format("%.1f", phaseCompletionPercentage) + "%" +
                ", overallProgressPercentage=" + String.format("%.1f", overallProgressPercentage) + "%" +
                ", timeSpent=" + timeSpent + " min" +
                ", estimatedTimeRemaining=" + estimatedTimeRemaining + " min" +
                '}';
    }
}

class DeliverableStatus {
    private String deliverableName;
    private boolean completed;
    private double completionPercentage;
    private String status;
    
    public DeliverableStatus() {}
    
    // Getters and Setters
    public String getDeliverableName() {
        return deliverableName;
    }
    
    public void setDeliverableName(String deliverableName) {
        this.deliverableName = deliverableName;
    }
    
    public boolean isCompleted() {
        return completed;
    }
    
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    public double getCompletionPercentage() {
        return completionPercentage;
    }
    
    public void setCompletionPercentage(double completionPercentage) {
        this.completionPercentage = completionPercentage;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}