package com.learningportal.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Results from performance validation
 */
public class PerformanceValidationResults {
    
    private boolean responseTimesValid;
    private boolean memoryUsageValid;
    private boolean throughputValid;
    private boolean databasePerformanceValid;
    private boolean overallSuccess;
    
    private SystemPerformanceMetrics performanceMetrics;
    private List<String> errors = new ArrayList<>();
    
    public void addError(String error) {
        errors.add(error);
    }
    
    // Getters and setters
    public boolean isResponseTimesValid() { return responseTimesValid; }
    public void setResponseTimesValid(boolean responseTimesValid) { this.responseTimesValid = responseTimesValid; }
    
    public boolean isMemoryUsageValid() { return memoryUsageValid; }
    public void setMemoryUsageValid(boolean memoryUsageValid) { this.memoryUsageValid = memoryUsageValid; }
    
    public boolean isThroughputValid() { return throughputValid; }
    public void setThroughputValid(boolean throughputValid) { this.throughputValid = throughputValid; }
    
    public boolean isDatabasePerformanceValid() { return databasePerformanceValid; }
    public void setDatabasePerformanceValid(boolean databasePerformanceValid) { this.databasePerformanceValid = databasePerformanceValid; }
    
    public boolean isOverallSuccess() { return overallSuccess; }
    public void setOverallSuccess(boolean overallSuccess) { this.overallSuccess = overallSuccess; }
    
    public SystemPerformanceMetrics getPerformanceMetrics() { return performanceMetrics; }
    public void setPerformanceMetrics(SystemPerformanceMetrics performanceMetrics) { this.performanceMetrics = performanceMetrics; }
    
    public List<String> getErrors() { return errors; }
    public void setErrors(List<String> errors) { this.errors = errors; }
}