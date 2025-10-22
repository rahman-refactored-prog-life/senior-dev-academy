package com.learningportal.continuity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Maven status and dependency resolution information.
 */
public class MavenStatus {
    
    private boolean dependencyResolutionSuccessful;
    private String status;
    private String output;
    private String errorOutput;
    private int exitCode;
    private List<String> issues;
    private LocalDateTime captureTime;
    
    public MavenStatus() {}
    
    // Getters and Setters
    public boolean isDependencyResolutionSuccessful() {
        return dependencyResolutionSuccessful;
    }
    
    public void setDependencyResolutionSuccessful(boolean dependencyResolutionSuccessful) {
        this.dependencyResolutionSuccessful = dependencyResolutionSuccessful;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getOutput() {
        return output;
    }
    
    public void setOutput(String output) {
        this.output = output;
    }
    
    public String getErrorOutput() {
        return errorOutput;
    }
    
    public void setErrorOutput(String errorOutput) {
        this.errorOutput = errorOutput;
    }
    
    public int getExitCode() {
        return exitCode;
    }
    
    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }
    
    public List<String> getIssues() {
        return issues;
    }
    
    public void setIssues(List<String> issues) {
        this.issues = issues;
    }
    
    public LocalDateTime getCaptureTime() {
        return captureTime;
    }
    
    public void setCaptureTime(LocalDateTime captureTime) {
        this.captureTime = captureTime;
    }
    
    @Override
    public String toString() {
        return "MavenStatus{" +
                "dependencyResolutionSuccessful=" + dependencyResolutionSuccessful +
                ", status='" + status + '\'' +
                ", exitCode=" + exitCode +
                ", issues=" + (issues != null ? issues.size() : 0) +
                '}';
    }
}