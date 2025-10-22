package com.learningportal.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Results from compilation and build validation
 */
public class CompilationValidationResults {
    
    private boolean javaCompilationSuccess;
    private boolean mavenBuildSuccess;
    private boolean dependencyResolutionSuccess;
    private boolean overallSuccess;
    
    private List<CompilationError> compilationErrors = new ArrayList<>();
    private List<CompilationWarning> compilationWarnings = new ArrayList<>();
    private List<String> errors = new ArrayList<>();
    
    public void addError(String error) {
        errors.add(error);
    }
    
    // Getters and setters
    public boolean isJavaCompilationSuccess() { return javaCompilationSuccess; }
    public void setJavaCompilationSuccess(boolean javaCompilationSuccess) { this.javaCompilationSuccess = javaCompilationSuccess; }
    
    public boolean isMavenBuildSuccess() { return mavenBuildSuccess; }
    public void setMavenBuildSuccess(boolean mavenBuildSuccess) { this.mavenBuildSuccess = mavenBuildSuccess; }
    
    public boolean isDependencyResolutionSuccess() { return dependencyResolutionSuccess; }
    public void setDependencyResolutionSuccess(boolean dependencyResolutionSuccess) { this.dependencyResolutionSuccess = dependencyResolutionSuccess; }
    
    public boolean isOverallSuccess() { return overallSuccess; }
    public void setOverallSuccess(boolean overallSuccess) { this.overallSuccess = overallSuccess; }
    
    public List<CompilationError> getCompilationErrors() { return compilationErrors; }
    public void setCompilationErrors(List<CompilationError> compilationErrors) { this.compilationErrors = compilationErrors; }
    
    public List<CompilationWarning> getCompilationWarnings() { return compilationWarnings; }
    public void setCompilationWarnings(List<CompilationWarning> compilationWarnings) { this.compilationWarnings = compilationWarnings; }
    
    public List<String> getErrors() { return errors; }
    public void setErrors(List<String> errors) { this.errors = errors; }
}