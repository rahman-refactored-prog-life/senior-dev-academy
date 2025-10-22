package com.learningportal.continuity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Result of code validation including compilation and execution checks.
 */
public class CodeValidationResult {
    
    private boolean compilationSuccessful;
    private boolean allTestsPassing;
    private boolean applicationStartable;
    private boolean overallValid;
    private List<CompilationError> compilationErrors;
    private LocalDateTime validationTime;
    private String summary;
    
    public CodeValidationResult() {}
    
    // Getters and Setters
    public boolean isCompilationSuccessful() {
        return compilationSuccessful;
    }
    
    public void setCompilationSuccessful(boolean compilationSuccessful) {
        this.compilationSuccessful = compilationSuccessful;
    }
    
    public boolean isAllTestsPassing() {
        return allTestsPassing;
    }
    
    public void setAllTestsPassing(boolean allTestsPassing) {
        this.allTestsPassing = allTestsPassing;
    }
    
    public boolean isApplicationStartable() {
        return applicationStartable;
    }
    
    public void setApplicationStartable(boolean applicationStartable) {
        this.applicationStartable = applicationStartable;
    }
    
    public boolean isOverallValid() {
        return overallValid;
    }
    
    public void setOverallValid(boolean overallValid) {
        this.overallValid = overallValid;
    }
    
    public List<CompilationError> getCompilationErrors() {
        return compilationErrors;
    }
    
    public void setCompilationErrors(List<CompilationError> compilationErrors) {
        this.compilationErrors = compilationErrors;
    }
    
    public LocalDateTime getValidationTime() {
        return validationTime;
    }
    
    public void setValidationTime(LocalDateTime validationTime) {
        this.validationTime = validationTime;
    }
    
    public String getSummary() {
        return summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    @Override
    public String toString() {
        return "CodeValidationResult{" +
                "compilationSuccessful=" + compilationSuccessful +
                ", allTestsPassing=" + allTestsPassing +
                ", applicationStartable=" + applicationStartable +
                ", overallValid=" + overallValid +
                ", validationTime=" + validationTime +
                '}';
    }
}