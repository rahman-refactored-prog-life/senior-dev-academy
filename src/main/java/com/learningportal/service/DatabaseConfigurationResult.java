package com.learningportal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class DatabaseConfigurationResult {
    private LocalDateTime validationTimestamp;
    private boolean configurationValid;
    private List<String> issues;
    private List<String> recommendations;
    private List<String> optimizations;
    private Map<String, Object> configurationSummary;
    
    public DatabaseConfigurationResult() {
        this.validationTimestamp = LocalDateTime.now();
    }
    
    public boolean hasIssues() { return issues != null && !issues.isEmpty(); }
    public boolean hasRecommendations() { return recommendations != null && !recommendations.isEmpty(); }
    public boolean hasOptimizations() { return optimizations != null && !optimizations.isEmpty(); }
    public int getIssueCount() { return issues != null ? issues.size() : 0; }
    public int getRecommendationCount() { return recommendations != null ? recommendations.size() : 0; }
    
    public String getValidationSummary() {
        if (configurationValid) {
            return String.format("✅ Configuration valid with %d recommendations", getRecommendationCount());
        } else {
            return String.format("❌ Configuration has %d issues and %d recommendations", 
                               getIssueCount(), getRecommendationCount());
        }
    }
    
    // Getters and setters
    public LocalDateTime getValidationTimestamp() { return validationTimestamp; }
    public void setValidationTimestamp(LocalDateTime validationTimestamp) { this.validationTimestamp = validationTimestamp; }
    public boolean isConfigurationValid() { return configurationValid; }
    public void setConfigurationValid(boolean configurationValid) { this.configurationValid = configurationValid; }
    public List<String> getIssues() { return issues; }
    public void setIssues(List<String> issues) { this.issues = issues; }
    public List<String> getRecommendations() { return recommendations; }
    public void setRecommendations(List<String> recommendations) { this.recommendations = recommendations; }
    public List<String> getOptimizations() { return optimizations; }
    public void setOptimizations(List<String> optimizations) { this.optimizations = optimizations; }
    public Map<String, Object> getConfigurationSummary() { return configurationSummary; }
    public void setConfigurationSummary(Map<String, Object> configurationSummary) { this.configurationSummary = configurationSummary; }
}