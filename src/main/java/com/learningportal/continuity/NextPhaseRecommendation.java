package com.learningportal.continuity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Recommendation for the next phase with preparation guidance.
 */
public class NextPhaseRecommendation {
    
    private LocalDateTime recommendationTime;
    private PhaseDefinition currentPhase;
    private PhaseDefinition recommendedNextPhase;
    private boolean ready;
    private double readinessScore;
    private List<String> preparationSteps;
    private String guidance;
    private List<String> blockers;
    
    public NextPhaseRecommendation() {}
    
    // Getters and Setters
    public LocalDateTime getRecommendationTime() {
        return recommendationTime;
    }
    
    public void setRecommendationTime(LocalDateTime recommendationTime) {
        this.recommendationTime = recommendationTime;
    }
    
    public PhaseDefinition getCurrentPhase() {
        return currentPhase;
    }
    
    public void setCurrentPhase(PhaseDefinition currentPhase) {
        this.currentPhase = currentPhase;
    }
    
    public PhaseDefinition getRecommendedNextPhase() {
        return recommendedNextPhase;
    }
    
    public void setRecommendedNextPhase(PhaseDefinition recommendedNextPhase) {
        this.recommendedNextPhase = recommendedNextPhase;
    }
    
    public boolean isReady() {
        return ready;
    }
    
    public void setReady(boolean ready) {
        this.ready = ready;
    }
    
    public double getReadinessScore() {
        return readinessScore;
    }
    
    public void setReadinessScore(double readinessScore) {
        this.readinessScore = readinessScore;
    }
    
    public List<String> getPreparationSteps() {
        return preparationSteps;
    }
    
    public void setPreparationSteps(List<String> preparationSteps) {
        this.preparationSteps = preparationSteps;
    }
    
    public String getGuidance() {
        return guidance;
    }
    
    public void setGuidance(String guidance) {
        this.guidance = guidance;
    }
    
    public List<String> getBlockers() {
        return blockers;
    }
    
    public void setBlockers(List<String> blockers) {
        this.blockers = blockers;
    }
    
    // Additional methods for compatibility
    public void setCurrentPhaseId(String currentPhaseId) {
        if (this.currentPhase == null) {
            this.currentPhase = new PhaseDefinition();
        }
        this.currentPhase.setPhaseId(currentPhaseId);
    }
    
    public void setNextPhaseId(String nextPhaseId) {
        if (this.recommendedNextPhase == null) {
            this.recommendedNextPhase = new PhaseDefinition();
        }
        this.recommendedNextPhase.setPhaseId(nextPhaseId);
    }
    
    public void setNextPhaseName(String nextPhaseName) {
        if (this.recommendedNextPhase == null) {
            this.recommendedNextPhase = new PhaseDefinition();
        }
        this.recommendedNextPhase.setName(nextPhaseName);
    }
    
    public void setEstimatedDuration(int estimatedDuration) {
        if (this.recommendedNextPhase == null) {
            this.recommendedNextPhase = new PhaseDefinition();
        }
        this.recommendedNextPhase.setEstimatedMinutes(estimatedDuration);
    }
    
    public void setReadyToProceed(boolean readyToProceed) {
        this.ready = readyToProceed;
    }
    
    public String getNextPhaseName() {
        return this.recommendedNextPhase != null ? this.recommendedNextPhase.getPhaseName() : null;
    }
    
    public String getPhaseId() {
        return this.recommendedNextPhase != null ? this.recommendedNextPhase.getPhaseId() : null;
    }
    
    public int getEstimatedTime() {
        return this.recommendedNextPhase != null ? this.recommendedNextPhase.getEstimatedMinutes() : 0;
    }
    
    @Override
    public String toString() {
        return "NextPhaseRecommendation{" +
                "recommendationTime=" + recommendationTime +
                ", currentPhase=" + (currentPhase != null ? currentPhase.getPhaseName() : "None") +
                ", recommendedNextPhase=" + (recommendedNextPhase != null ? recommendedNextPhase.getPhaseName() : "None") +
                ", ready=" + ready +
                ", readinessScore=" + String.format("%.1f", readinessScore) + "%" +
                '}';
    }
}