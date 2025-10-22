package com.learningportal.continuity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Result of executing a single quality gate.
 */
public class GateResult {
    
    private String gateName;
    private boolean passed;
    private double score;
    private LocalDateTime executionTime;
    private List<String> issues;
    private List<String> recommendations;
    private String details;
    
    public GateResult() {}
    
    public GateResult(String gateName, boolean passed, double score) {
        this.gateName = gateName;
        this.passed = passed;
        this.score = score;
        this.executionTime = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getGateName() {
        return gateName;
    }
    
    public void setGateName(String gateName) {
        this.gateName = gateName;
    }
    
    public boolean isPassed() {
        return passed;
    }
    
    public void setPassed(boolean passed) {
        this.passed = passed;
    }
    
    public double getScore() {
        return score;
    }
    
    public void setScore(double score) {
        this.score = score;
    }
    
    public LocalDateTime getExecutionTime() {
        return executionTime;
    }
    
    public void setExecutionTime(LocalDateTime executionTime) {
        this.executionTime = executionTime;
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
    
    public String getDetails() {
        return details;
    }
    
    public void setDetails(String details) {
        this.details = details;
    }
    
    @Override
    public String toString() {
        return "GateResult{" +
                "gateName='" + gateName + '\'' +
                ", passed=" + passed +
                ", score=" + String.format("%.1f", score) +
                ", issues=" + (issues != null ? issues.size() : 0) +
                ", executionTime=" + executionTime +
                '}';
    }
}