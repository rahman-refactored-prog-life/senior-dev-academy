package com.learningportal.continuity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Comprehensive recovery report with findings and recommendations.
 */
public class RecoveryReport {
    
    private LocalDateTime reportTime;
    private boolean overallSuccess;
    private double confidenceScore;
    private double validationScore;
    private int elementsRecovered;
    private int elementsMissing;
    private int sourcesAnalyzed;
    private String summary;
    private List<String> recommendations;
    private List<String> recoveryActions;
    
    public RecoveryReport() {}
    
    // Getters and Setters
    public LocalDateTime getReportTime() {
        return reportTime;
    }
    
    public void setReportTime(LocalDateTime reportTime) {
        this.reportTime = reportTime;
    }
    
    public boolean isOverallSuccess() {
        return overallSuccess;
    }
    
    public void setOverallSuccess(boolean overallSuccess) {
        this.overallSuccess = overallSuccess;
    }
    
    public double getConfidenceScore() {
        return confidenceScore;
    }
    
    public void setConfidenceScore(double confidenceScore) {
        this.confidenceScore = confidenceScore;
    }
    
    public double getValidationScore() {
        return validationScore;
    }
    
    public void setValidationScore(double validationScore) {
        this.validationScore = validationScore;
    }
    
    public int getElementsRecovered() {
        return elementsRecovered;
    }
    
    public void setElementsRecovered(int elementsRecovered) {
        this.elementsRecovered = elementsRecovered;
    }
    
    public int getElementsMissing() {
        return elementsMissing;
    }
    
    public void setElementsMissing(int elementsMissing) {
        this.elementsMissing = elementsMissing;
    }
    
    public int getSourcesAnalyzed() {
        return sourcesAnalyzed;
    }
    
    public void setSourcesAnalyzed(int sourcesAnalyzed) {
        this.sourcesAnalyzed = sourcesAnalyzed;
    }
    
    public String getSummary() {
        return summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    public List<String> getRecommendations() {
        return recommendations;
    }
    
    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }
    
    public List<String> getRecoveryActions() {
        return recoveryActions;
    }
    
    public void setRecoveryActions(List<String> recoveryActions) {
        this.recoveryActions = recoveryActions;
    }
    
    // Additional methods for compatibility
    public String getSessionId() {
        return "recovery_session_" + (reportTime != null ? reportTime.toString() : "unknown");
    }
    
    public void setSessionId(String sessionId) {
        // Store session ID in summary for now
        this.summary = "Session ID: " + sessionId + (this.summary != null ? " - " + this.summary : "");
    }
    
    public LocalDateTime getRecoveryTimestamp() {
        return this.reportTime;
    }
    
    public void setRecoveryTimestamp(LocalDateTime recoveryTimestamp) {
        this.reportTime = recoveryTimestamp;
    }
    
    public boolean isRecoverySuccessful() {
        return this.overallSuccess;
    }
    
    public void setRecoverySuccessful(boolean recoverySuccessful) {
        this.overallSuccess = recoverySuccessful;
    }
    
    public int getRecoveredElements() {
        return this.elementsRecovered;
    }
    
    public void setRecoveredElements(int recoveredElements) {
        this.elementsRecovered = recoveredElements;
    }
    
    public int getMissingElements() {
        return this.elementsMissing;
    }
    
    public void setMissingElements(int missingElements) {
        this.elementsMissing = missingElements;
    }
    
    public int getReliableSources() {
        return this.sourcesAnalyzed;
    }
    
    public void setReliableSources(int reliableSources) {
        this.sourcesAnalyzed = reliableSources;
    }
    
    @Override
    public String toString() {
        return "RecoveryReport{" +
                "reportTime=" + reportTime +
                ", overallSuccess=" + overallSuccess +
                ", confidenceScore=" + String.format("%.1f", confidenceScore) + "%" +
                ", validationScore=" + String.format("%.1f", validationScore) + "%" +
                ", elementsRecovered=" + elementsRecovered +
                ", elementsMissing=" + elementsMissing +
                ", sourcesAnalyzed=" + sourcesAnalyzed +
                '}';
    }
}