package com.learningportal.continuity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Comprehensive results from executing all quality gates.
 */
public class QualityGateResults {
    
    private String sessionId;
    private LocalDateTime executionTime;
    private List<GateResult> gateResults;
    private boolean overallPassed;
    private int passedGates;
    private int totalGates;
    private List<String> recommendations;
    private String summary;
    
    public QualityGateResults() {}
    
    // Getters and Setters
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public LocalDateTime getExecutionTime() {
        return executionTime;
    }
    
    public void setExecutionTime(LocalDateTime executionTime) {
        this.executionTime = executionTime;
    }
    
    public List<GateResult> getGateResults() {
        return gateResults;
    }
    
    public void setGateResults(List<GateResult> gateResults) {
        this.gateResults = gateResults;
    }
    
    public boolean isOverallPassed() {
        return overallPassed;
    }
    
    public void setOverallPassed(boolean overallPassed) {
        this.overallPassed = overallPassed;
    }
    
    public int getPassedGates() {
        return passedGates;
    }
    
    public void setPassedGates(int passedGates) {
        this.passedGates = passedGates;
    }
    
    public int getTotalGates() {
        return totalGates;
    }
    
    public void setTotalGates(int totalGates) {
        this.totalGates = totalGates;
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
    public boolean isAllPassed() {
        return this.overallPassed;
    }
    
    public void setAllPassed(boolean allPassed) {
        this.overallPassed = allPassed;
    }
    
    /**
     * Gets the gate results (alias for getGateResults for compatibility).
     * 
     * @return List of individual gate results
     */
    public List<GateResult> getResults() {
        return this.gateResults;
    }
    
    @Override
    public String toString() {
        return "QualityGateResults{" +
                "sessionId='" + sessionId + '\'' +
                ", overallPassed=" + overallPassed +
                ", passedGates=" + passedGates +
                ", totalGates=" + totalGates +
                ", executionTime=" + executionTime +
                '}';
    }
}