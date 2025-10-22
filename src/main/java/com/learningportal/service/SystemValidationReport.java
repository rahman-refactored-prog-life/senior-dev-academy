package com.learningportal.service;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Comprehensive System Validation Report
 * 
 * Contains all validation results from end-to-end system testing
 */
public class SystemValidationReport {
    
    private LocalDateTime validationStartTime;
    private LocalDateTime validationEndTime;
    private Duration validationDuration;
    private ValidationStatus overallStatus;
    private List<String> errors = new ArrayList<>();
    private List<String> warnings = new ArrayList<>();
    
    // Validation result sections
    private CompilationValidationResults compilationResults;
    private DatabaseValidationResults databaseResults;
    private HealthValidationResults healthResults;
    private ApiValidationResults apiResults;
    private PerformanceValidationResults performanceResults;
    private TechnicalDebtValidationResults technicalDebtResults;
    
    // Summary metrics
    private int totalValidations;
    private int successfulValidations;
    private double successRate;
    
    public void calculateOverallStatus() {
        List<Boolean> results = List.of(
            compilationResults != null && compilationResults.isOverallSuccess(),
            databaseResults != null && databaseResults.isOverallSuccess(),
            healthResults != null && healthResults.isOverallSuccess(),
            apiResults != null && apiResults.isOverallSuccess(),
            performanceResults != null && performanceResults.isOverallSuccess(),
            technicalDebtResults != null && technicalDebtResults.isOverallSuccess()
        );
        
        totalValidations = results.size();
        successfulValidations = (int) results.stream().mapToLong(success -> success ? 1 : 0).sum();
        successRate = (double) successfulValidations / totalValidations;
        
        if (successRate == 1.0) {
            overallStatus = ValidationStatus.PASSED;
        } else if (successRate >= 0.8) {
            overallStatus = ValidationStatus.PASSED_WITH_WARNINGS;
        } else {
            overallStatus = ValidationStatus.FAILED;
        }
    }
    
    public void calculateDuration() {
        if (validationStartTime != null && validationEndTime != null) {
            validationDuration = Duration.between(validationStartTime, validationEndTime);
        }
    }
    
    public void addError(String error) {
        errors.add(error);
    }
    
    public void addWarning(String warning) {
        warnings.add(warning);
    }
    
    // Getters and setters
    public LocalDateTime getValidationStartTime() { return validationStartTime; }
    public void setValidationStartTime(LocalDateTime validationStartTime) { this.validationStartTime = validationStartTime; }
    
    public LocalDateTime getValidationEndTime() { return validationEndTime; }
    public void setValidationEndTime(LocalDateTime validationEndTime) { this.validationEndTime = validationEndTime; }
    
    public Duration getValidationDuration() { return validationDuration; }
    public void setValidationDuration(Duration validationDuration) { this.validationDuration = validationDuration; }
    
    public ValidationStatus getOverallStatus() { return overallStatus; }
    public void setOverallStatus(ValidationStatus overallStatus) { this.overallStatus = overallStatus; }
    
    public List<String> getErrors() { return errors; }
    public void setErrors(List<String> errors) { this.errors = errors; }
    
    public List<String> getWarnings() { return warnings; }
    public void setWarnings(List<String> warnings) { this.warnings = warnings; }
    
    public CompilationValidationResults getCompilationResults() { return compilationResults; }
    public void setCompilationResults(CompilationValidationResults compilationResults) { this.compilationResults = compilationResults; }
    
    public DatabaseValidationResults getDatabaseResults() { return databaseResults; }
    public void setDatabaseResults(DatabaseValidationResults databaseResults) { this.databaseResults = databaseResults; }
    
    public HealthValidationResults getHealthResults() { return healthResults; }
    public void setHealthResults(HealthValidationResults healthResults) { this.healthResults = healthResults; }
    
    public ApiValidationResults getApiResults() { return apiResults; }
    public void setApiResults(ApiValidationResults apiResults) { this.apiResults = apiResults; }
    
    public PerformanceValidationResults getPerformanceResults() { return performanceResults; }
    public void setPerformanceResults(PerformanceValidationResults performanceResults) { this.performanceResults = performanceResults; }
    
    public TechnicalDebtValidationResults getTechnicalDebtResults() { return technicalDebtResults; }
    public void setTechnicalDebtResults(TechnicalDebtValidationResults technicalDebtResults) { this.technicalDebtResults = technicalDebtResults; }
    
    public int getTotalValidations() { return totalValidations; }
    public void setTotalValidations(int totalValidations) { this.totalValidations = totalValidations; }
    
    public int getSuccessfulValidations() { return successfulValidations; }
    public void setSuccessfulValidations(int successfulValidations) { this.successfulValidations = successfulValidations; }
    
    public double getSuccessRate() { return successRate; }
    public void setSuccessRate(double successRate) { this.successRate = successRate; }
}

enum ValidationStatus {
    PASSED,
    PASSED_WITH_WARNINGS,
    FAILED
}