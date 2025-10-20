package com.learningportal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Configuration Validation Result Data Class
 * 
 * Contains results of application configuration validation.
 */
public class ConfigurationValidationResult {
    
    private boolean configurationValid;
    private LocalDateTime validationTimestamp;
    private List<String> configurationIssues;
    private List<String> recommendations;
    private Map<String, Object> configurationSummary;
    private String overallStatus;
    
    // Constructors
    public ConfigurationValidationResult() {
        this.validationTimestamp = LocalDateTime.now();
    }
    
    // Helper methods
    public boolean hasIssues() {
        return configurationIssues != null && !configurationIssues.isEmpty();
    }
    
    public boolean hasRecommendations() {
        return recommendations != null && !recommendations.isEmpty();
    }
    
    public int getIssueCount() {
        return configurationIssues != null ? configurationIssues.size() : 0;
    }
    
    public String getValidationSummary() {
        if (configurationValid) {
            return String.format("✅ Configuration valid with %d recommendations", 
                               recommendations != null ? recommendations.size() : 0);
        } else {
            return String.format("❌ Configuration has %d issues", getIssueCount());
        }
    }
    
    // Standard getters and setters
    public boolean isConfigurationValid() { return configurationValid; }
    public void setConfigurationValid(boolean configurationValid) { 
        this.configurationValid = configurationValid; 
    }
    
    public LocalDateTime getValidationTimestamp() { return validationTimestamp; }
    public void setValidationTimestamp(LocalDateTime validationTimestamp) { 
        this.validationTimestamp = validationTimestamp; 
    }
    
    public List<String> getConfigurationIssues() { return configurationIssues; }
    public void setConfigurationIssues(List<String> configurationIssues) { 
        this.configurationIssues = configurationIssues; 
    }
    
    public List<String> getRecommendations() { return recommendations; }
    public void setRecommendations(List<String> recommendations) { 
        this.recommendations = recommendations; 
    }
    
    public Map<String, Object> getConfigurationSummary() { return configurationSummary; }
    public void setConfigurationSummary(Map<String, Object> configurationSummary) { 
        this.configurationSummary = configurationSummary; 
    }
    
    public String getOverallStatus() { return overallStatus; }
    public void setOverallStatus(String overallStatus) { this.overallStatus = overallStatus; }
    
    @Override
    public String toString() {
        return "ConfigurationValidationResult{" +
                "configurationValid=" + configurationValid +
                ", issueCount=" + getIssueCount() +
                ", overallStatus='" + overallStatus + '\'' +
                '}';
    }
}