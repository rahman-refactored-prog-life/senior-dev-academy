package com.learningportal.continuity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Result of cross-validation between all redundancy layers.
 */
public class CrossValidationResult {
    
    private LocalDateTime validationTime;
    private List<ConsistencyIssue> consistencyIssues;
    private double consistencyScore;
    private boolean overallConsistent;
    private String summary;
    
    public CrossValidationResult() {}
    
    // Getters and Setters
    public LocalDateTime getValidationTime() {
        return validationTime;
    }
    
    public void setValidationTime(LocalDateTime validationTime) {
        this.validationTime = validationTime;
    }
    
    public List<ConsistencyIssue> getConsistencyIssues() {
        return consistencyIssues;
    }
    
    public void setConsistencyIssues(List<ConsistencyIssue> consistencyIssues) {
        this.consistencyIssues = consistencyIssues;
    }
    
    public double getConsistencyScore() {
        return consistencyScore;
    }
    
    public void setConsistencyScore(double consistencyScore) {
        this.consistencyScore = consistencyScore;
    }
    
    public boolean isOverallConsistent() {
        return overallConsistent;
    }
    
    public void setOverallConsistent(boolean overallConsistent) {
        this.overallConsistent = overallConsistent;
    }
    
    public String getSummary() {
        return summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    // Additional methods for compatibility
    public boolean isConsistent() {
        return this.overallConsistent;
    }
    
    public void setConsistent(boolean consistent) {
        this.overallConsistent = consistent;
    }
    
    @Override
    public String toString() {
        return "CrossValidationResult{" +
                "validationTime=" + validationTime +
                ", consistencyScore=" + consistencyScore +
                ", overallConsistent=" + overallConsistent +
                ", issues=" + (consistencyIssues != null ? consistencyIssues.size() : 0) +
                '}';
    }
}

class ConsistencyIssue {
    private String issueType;
    private String description;
    private String affectedLayers;
    private IssueSeverity severity;
    
    public ConsistencyIssue() {}
    
    public ConsistencyIssue(String issueType, String description) {
        this.issueType = issueType;
        this.description = description;
    }
    
    // Getters and Setters
    public String getIssueType() {
        return issueType;
    }
    
    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getAffectedLayers() {
        return affectedLayers;
    }
    
    public void setAffectedLayers(String affectedLayers) {
        this.affectedLayers = affectedLayers;
    }
    
    public IssueSeverity getSeverity() {
        return severity;
    }
    
    public void setSeverity(IssueSeverity severity) {
        this.severity = severity;
    }
}

class LayerInformation {
    private String layerName;
    private String sessionId;
    private LocalDateTime timestamp;
    private double progressPercentage;
    private String phase;
    private String status;
    
    public LayerInformation() {}
    
    // Getters and Setters
    public String getLayerName() {
        return layerName;
    }
    
    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }
    
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public double getProgressPercentage() {
        return progressPercentage;
    }
    
    public void setProgressPercentage(double progressPercentage) {
        this.progressPercentage = progressPercentage;
    }
    
    public String getPhase() {
        return phase;
    }
    
    public void setPhase(String phase) {
        this.phase = phase;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}

class ConsistencyAnalysis {
    private LocalDateTime analysisTime;
    private Map<String, Double> informationDistribution;
    private double redundancyEffectiveness;
    private double recoveryCapability;
    private List<String> recommendations;
    
    public ConsistencyAnalysis() {}
    
    // Getters and Setters
    public LocalDateTime getAnalysisTime() {
        return analysisTime;
    }
    
    public void setAnalysisTime(LocalDateTime analysisTime) {
        this.analysisTime = analysisTime;
    }
    
    public Map<String, Double> getInformationDistribution() {
        return informationDistribution;
    }
    
    public void setInformationDistribution(Map<String, Double> informationDistribution) {
        this.informationDistribution = informationDistribution;
    }
    
    public double getRedundancyEffectiveness() {
        return redundancyEffectiveness;
    }
    
    public void setRedundancyEffectiveness(double redundancyEffectiveness) {
        this.redundancyEffectiveness = redundancyEffectiveness;
    }
    
    public double getRecoveryCapability() {
        return recoveryCapability;
    }
    
    public void setRecoveryCapability(double recoveryCapability) {
        this.recoveryCapability = recoveryCapability;
    }
    
    public List<String> getRecommendations() {
        return recommendations;
    }
    
    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }
}