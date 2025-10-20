package com.learningportal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class StartupValidationResult {
    private boolean startupSuccessful;
    private LocalDateTime validationTimestamp;
    private long startupTimeMs;
    private List<StartupIssue> issues;
    private List<DependencyIssue> dependencyIssues;
    private Map<String, ComponentStatus> componentStatuses;
    private ApplicationReadinessStatus readinessStatus;
    private StartupPerformanceMetrics performanceMetrics;
    private String overallStatus;
    private List<String> criticalErrors;
    private List<String> warnings;
    private List<String> recommendations;
    
    public StartupValidationResult() {
        this.validationTimestamp = LocalDateTime.now();
    }
    
    public boolean hasIssues() { return issues != null && !issues.isEmpty(); }
    public boolean hasCriticalErrors() { return criticalErrors != null && !criticalErrors.isEmpty(); }
    public boolean hasWarnings() { return warnings != null && !warnings.isEmpty(); }
    public int getIssueCount() { return issues != null ? issues.size() : 0; }
    public int getCriticalErrorCount() { return criticalErrors != null ? criticalErrors.size() : 0; }
    public int getWarningCount() { return warnings != null ? warnings.size() : 0; }
    
    public String getStartupGrade() {
        if (!startupSuccessful) return "Failed";
        else if (startupTimeMs <= 5000) return "Excellent";
        else if (startupTimeMs <= 10000) return "Good";
        else if (startupTimeMs <= 20000) return "Fair";
        else return "Slow";
    }
    
    public String getValidationSummary() {
        if (startupSuccessful) {
            return String.format("✅ Startup validation passed in %dms (%s) - %d warnings", 
                               startupTimeMs, getStartupGrade(), getWarningCount());
        } else {
            return String.format("❌ Startup validation failed in %dms - %d errors, %d warnings", 
                               startupTimeMs, getCriticalErrorCount(), getWarningCount());
        }
    }
    
    public boolean isApplicationReady() {
        return startupSuccessful && !hasCriticalErrors() && 
               (readinessStatus != null && readinessStatus.isReady());
    }
    
    // Getters and setters
    public boolean isStartupSuccessful() { return startupSuccessful; }
    public void setStartupSuccessful(boolean startupSuccessful) { this.startupSuccessful = startupSuccessful; }
    public LocalDateTime getValidationTimestamp() { return validationTimestamp; }
    public void setValidationTimestamp(LocalDateTime validationTimestamp) { this.validationTimestamp = validationTimestamp; }
    public long getStartupTimeMs() { return startupTimeMs; }
    public void setStartupTimeMs(long startupTimeMs) { this.startupTimeMs = startupTimeMs; }
    public List<StartupIssue> getIssues() { return issues; }
    public void setIssues(List<StartupIssue> issues) { this.issues = issues; }
    public List<DependencyIssue> getDependencyIssues() { return dependencyIssues; }
    public void setDependencyIssues(List<DependencyIssue> dependencyIssues) { this.dependencyIssues = dependencyIssues; }
    public Map<String, ComponentStatus> getComponentStatuses() { return componentStatuses; }
    public void setComponentStatuses(Map<String, ComponentStatus> componentStatuses) { this.componentStatuses = componentStatuses; }
    public ApplicationReadinessStatus getReadinessStatus() { return readinessStatus; }
    public void setReadinessStatus(ApplicationReadinessStatus readinessStatus) { this.readinessStatus = readinessStatus; }
    public StartupPerformanceMetrics getPerformanceMetrics() { return performanceMetrics; }
    public void setPerformanceMetrics(StartupPerformanceMetrics performanceMetrics) { this.performanceMetrics = performanceMetrics; }
    public String getOverallStatus() { return overallStatus; }
    public void setOverallStatus(String overallStatus) { this.overallStatus = overallStatus; }
    public List<String> getCriticalErrors() { return criticalErrors; }
    public void setCriticalErrors(List<String> criticalErrors) { this.criticalErrors = criticalErrors; }
    public List<String> getWarnings() { return warnings; }
    public void setWarnings(List<String> warnings) { this.warnings = warnings; }
    public List<String> getRecommendations() { return recommendations; }
    public void setRecommendations(List<String> recommendations) { this.recommendations = recommendations; }
}