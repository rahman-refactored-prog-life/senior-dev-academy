package com.learningportal.continuity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Report of consistency validation across all documentation files.
 */
public class ConsistencyReport {
    
    private LocalDateTime validationTime;
    private List<ConsistencyIssue> consistencyIssues;
    private double consistencyScore;
    private boolean overallConsistent;
    private int filesValidated;
    private String summary;
    
    public ConsistencyReport() {}
    
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
    
    public int getFilesValidated() {
        return filesValidated;
    }
    
    public void setFilesValidated(int filesValidated) {
        this.filesValidated = filesValidated;
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
    
    public int getValidationCount() {
        return this.filesValidated;
    }
    
    public void setValidationCount(int validationCount) {
        this.filesValidated = validationCount;
    }
    
    public int getPassedValidations() {
        return this.overallConsistent ? this.filesValidated : 0;
    }
    
    public void setPassedValidations(int passedValidations) {
        // Calculate consistency based on passed validations
        if (this.filesValidated > 0) {
            this.overallConsistent = (passedValidations == this.filesValidated);
            this.consistencyScore = (double) passedValidations / this.filesValidated * 100.0;
        }
    }
    
    @Override
    public String toString() {
        return "ConsistencyReport{" +
                "validationTime=" + validationTime +
                ", consistencyScore=" + consistencyScore +
                ", overallConsistent=" + overallConsistent +
                ", filesValidated=" + filesValidated +
                ", issues=" + (consistencyIssues != null ? consistencyIssues.size() : 0) +
                '}';
    }
}