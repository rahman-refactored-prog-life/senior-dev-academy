package com.learningportal.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Results from technical debt resolution validation
 */
public class TechnicalDebtValidationResults {
    
    private boolean compilationIssuesResolved;
    private boolean databaseIssuesResolved;
    private boolean stabilityIssuesResolved;
    private boolean codeQualityStandardsMet;
    private boolean documentationComplete;
    private boolean testingFrameworkComplete;
    private boolean overallSuccess;
    
    private List<String> remainingIssues = new ArrayList<>();
    private List<String> errors = new ArrayList<>();
    
    public void addError(String error) {
        errors.add(error);
    }
    
    public void addRemainingIssue(String issue) {
        remainingIssues.add(issue);
    }
    
    // Getters and setters
    public boolean isCompilationIssuesResolved() { return compilationIssuesResolved; }
    public void setCompilationIssuesResolved(boolean compilationIssuesResolved) { this.compilationIssuesResolved = compilationIssuesResolved; }
    
    public boolean isDatabaseIssuesResolved() { return databaseIssuesResolved; }
    public void setDatabaseIssuesResolved(boolean databaseIssuesResolved) { this.databaseIssuesResolved = databaseIssuesResolved; }
    
    public boolean isStabilityIssuesResolved() { return stabilityIssuesResolved; }
    public void setStabilityIssuesResolved(boolean stabilityIssuesResolved) { this.stabilityIssuesResolved = stabilityIssuesResolved; }
    
    public boolean isCodeQualityStandardsMet() { return codeQualityStandardsMet; }
    public void setCodeQualityStandardsMet(boolean codeQualityStandardsMet) { this.codeQualityStandardsMet = codeQualityStandardsMet; }
    
    public boolean isDocumentationComplete() { return documentationComplete; }
    public void setDocumentationComplete(boolean documentationComplete) { this.documentationComplete = documentationComplete; }
    
    public boolean isTestingFrameworkComplete() { return testingFrameworkComplete; }
    public void setTestingFrameworkComplete(boolean testingFrameworkComplete) { this.testingFrameworkComplete = testingFrameworkComplete; }
    
    public boolean isOverallSuccess() { return overallSuccess; }
    public void setOverallSuccess(boolean overallSuccess) { this.overallSuccess = overallSuccess; }
    
    public List<String> getRemainingIssues() { return remainingIssues; }
    public void setRemainingIssues(List<String> remainingIssues) { this.remainingIssues = remainingIssues; }
    
    public List<String> getErrors() { return errors; }
    public void setErrors(List<String> errors) { this.errors = errors; }
}