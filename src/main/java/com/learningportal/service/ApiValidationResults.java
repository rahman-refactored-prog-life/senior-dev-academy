package com.learningportal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Results from API endpoint validation
 */
public class ApiValidationResults {
    
    private boolean overallSuccess;
    private double successRate;
    private Map<String, EndpointValidationResult> endpointResults;
    private List<String> errors = new ArrayList<>();
    
    public void addError(String error) {
        errors.add(error);
    }
    
    // Getters and setters
    public boolean isOverallSuccess() { return overallSuccess; }
    public void setOverallSuccess(boolean overallSuccess) { this.overallSuccess = overallSuccess; }
    
    public double getSuccessRate() { return successRate; }
    public void setSuccessRate(double successRate) { this.successRate = successRate; }
    
    public Map<String, EndpointValidationResult> getEndpointResults() { return endpointResults; }
    public void setEndpointResults(Map<String, EndpointValidationResult> endpointResults) { this.endpointResults = endpointResults; }
    
    public List<String> getErrors() { return errors; }
    public void setErrors(List<String> errors) { this.errors = errors; }
}

/**
 * Individual endpoint validation result
 */
class EndpointValidationResult {
    private String url;
    private boolean success;
    private long responseTime;
    private int statusCode;
    private String error;
    
    // Getters and setters
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    
    public long getResponseTime() { return responseTime; }
    public void setResponseTime(long responseTime) { this.responseTime = responseTime; }
    
    public int getStatusCode() { return statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }
    
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}