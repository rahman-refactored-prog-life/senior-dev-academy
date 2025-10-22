package com.learningportal.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Success Metrics Report
 * 
 * Contains validation results for all success metrics and performance benchmarks
 */
public class SuccessMetricsReport {
    
    private LocalDateTime validationTimestamp;
    private boolean overallSuccess;
    private double successRate;
    
    private CompilationMetrics compilationMetrics;
    private StartupMetrics startupMetrics;
    private RuntimePerformanceMetrics runtimeMetrics;
    private ReliabilityMetrics reliabilityMetrics;
    
    private List<String> errors = new ArrayList<>();
    private List<String> warnings = new ArrayList<>();
    
    public void calculateSuccessRate() {
        int totalMetrics = 0;
        int successfulMetrics = 0;
        
        if (compilationMetrics != null) {
            totalMetrics++;
            if (compilationMetrics.isAllMetricsMet()) successfulMetrics++;
        }
        
        if (startupMetrics != null) {
            totalMetrics++;
            if (startupMetrics.isAllMetricsMet()) successfulMetrics++;
        }
        
        if (runtimeMetrics != null) {
            totalMetrics++;
            if (runtimeMetrics.isAllMetricsMet()) successfulMetrics++;
        }
        
        if (reliabilityMetrics != null) {
            totalMetrics++;
            if (reliabilityMetrics.isAllMetricsMet()) successfulMetrics++;
        }
        
        this.successRate = totalMetrics > 0 ? (double) successfulMetrics / totalMetrics : 0.0;
    }
    
    public void addError(String error) {
        errors.add(error);
    }
    
    public void addWarning(String warning) {
        warnings.add(warning);
    }
    
    // Helper methods for status checking
    public String getCompilationStatus() {
        return compilationMetrics != null && compilationMetrics.isAllMetricsMet() ? "PASSED" : "FAILED";
    }
    
    public String getStartupStatus() {
        return startupMetrics != null && startupMetrics.isAllMetricsMet() ? "PASSED" : "FAILED";
    }
    
    public String getRuntimeStatus() {
        return runtimeMetrics != null && runtimeMetrics.isAllMetricsMet() ? "PASSED" : "FAILED";
    }
    
    public String getReliabilityStatus() {
        return reliabilityMetrics != null && reliabilityMetrics.isAllMetricsMet() ? "PASSED" : "FAILED";
    }
    
    // Getters and setters
    public LocalDateTime getValidationTimestamp() { return validationTimestamp; }
    public void setValidationTimestamp(LocalDateTime validationTimestamp) { this.validationTimestamp = validationTimestamp; }
    
    public boolean isOverallSuccess() { return overallSuccess; }
    public void setOverallSuccess(boolean overallSuccess) { this.overallSuccess = overallSuccess; }
    
    public double getSuccessRate() { return successRate; }
    public void setSuccessRate(double successRate) { this.successRate = successRate; }
    
    public CompilationMetrics getCompilationMetrics() { return compilationMetrics; }
    public void setCompilationMetrics(CompilationMetrics compilationMetrics) { this.compilationMetrics = compilationMetrics; }
    
    public StartupMetrics getStartupMetrics() { return startupMetrics; }
    public void setStartupMetrics(StartupMetrics startupMetrics) { this.startupMetrics = startupMetrics; }
    
    public RuntimePerformanceMetrics getRuntimeMetrics() { return runtimeMetrics; }
    public void setRuntimeMetrics(RuntimePerformanceMetrics runtimeMetrics) { this.runtimeMetrics = runtimeMetrics; }
    
    public ReliabilityMetrics getReliabilityMetrics() { return reliabilityMetrics; }
    public void setReliabilityMetrics(ReliabilityMetrics reliabilityMetrics) { this.reliabilityMetrics = reliabilityMetrics; }
    
    public List<String> getErrors() { return errors; }
    public void setErrors(List<String> errors) { this.errors = errors; }
    
    public List<String> getWarnings() { return warnings; }
    public void setWarnings(List<String> warnings) { this.warnings = warnings; }
}

/**
 * Base class for metrics validation
 */
abstract class BaseMetrics {
    protected List<ValidationResult> validationResults = new ArrayList<>();
    protected List<String> errors = new ArrayList<>();
    protected boolean allMetricsMet;
    
    public void addValidationResult(String metricName, boolean passed, String description) {
        validationResults.add(new ValidationResult(metricName, passed, description));
    }
    
    public void addError(String error) {
        errors.add(error);
    }
    
    public abstract void calculateOverallSuccess();
    
    // Getters
    public List<ValidationResult> getValidationResults() { return validationResults; }
    public List<String> getErrors() { return errors; }
    public boolean isAllMetricsMet() { return allMetricsMet; }
}

/**
 * Compilation metrics validation results
 */
class CompilationMetrics extends BaseMetrics {
    private boolean zeroCompilationErrors;
    private boolean zeroCompilationWarnings;
    private boolean successfulMavenBuild;
    private boolean dependenciesResolved;
    
    @Override
    public void calculateOverallSuccess() {
        this.allMetricsMet = zeroCompilationErrors && zeroCompilationWarnings && 
                           successfulMavenBuild && dependenciesResolved;
    }
    
    // Getters and setters
    public boolean isZeroCompilationErrors() { return zeroCompilationErrors; }
    public void setZeroCompilationErrors(boolean zeroCompilationErrors) { this.zeroCompilationErrors = zeroCompilationErrors; }
    
    public boolean isZeroCompilationWarnings() { return zeroCompilationWarnings; }
    public void setZeroCompilationWarnings(boolean zeroCompilationWarnings) { this.zeroCompilationWarnings = zeroCompilationWarnings; }
    
    public boolean isSuccessfulMavenBuild() { return successfulMavenBuild; }
    public void setSuccessfulMavenBuild(boolean successfulMavenBuild) { this.successfulMavenBuild = successfulMavenBuild; }
    
    public boolean isDependenciesResolved() { return dependenciesResolved; }
    public void setDependenciesResolved(boolean dependenciesResolved) { this.dependenciesResolved = dependenciesResolved; }
}

/**
 * Startup metrics validation results
 */
class StartupMetrics extends BaseMetrics {
    private long startupTime;
    private boolean startupWithinTarget;
    private long databaseConnectionTime;
    private boolean databaseConnectionFast;
    private boolean endpointsAccessible;
    
    @Override
    public void calculateOverallSuccess() {
        this.allMetricsMet = startupWithinTarget && databaseConnectionFast && endpointsAccessible;
    }
    
    // Getters and setters
    public long getStartupTime() { return startupTime; }
    public void setStartupTime(long startupTime) { this.startupTime = startupTime; }
    
    public boolean isStartupWithinTarget() { return startupWithinTarget; }
    public void setStartupWithinTarget(boolean startupWithinTarget) { this.startupWithinTarget = startupWithinTarget; }
    
    public long getDatabaseConnectionTime() { return databaseConnectionTime; }
    public void setDatabaseConnectionTime(long databaseConnectionTime) { this.databaseConnectionTime = databaseConnectionTime; }
    
    public boolean isDatabaseConnectionFast() { return databaseConnectionFast; }
    public void setDatabaseConnectionFast(boolean databaseConnectionFast) { this.databaseConnectionFast = databaseConnectionFast; }
    
    public boolean isEndpointsAccessible() { return endpointsAccessible; }
    public void setEndpointsAccessible(boolean endpointsAccessible) { this.endpointsAccessible = endpointsAccessible; }
}

/**
 * Runtime performance metrics validation results
 */
class RuntimePerformanceMetrics extends BaseMetrics {
    private double averageResponseTime;
    private boolean responseTimesWithinTarget;
    private double memoryUsage;
    private boolean memoryUsageWithinTarget;
    private double averageDatabaseQueryTime;
    private boolean databasePerformanceWithinTarget;
    
    @Override
    public void calculateOverallSuccess() {
        this.allMetricsMet = responseTimesWithinTarget && memoryUsageWithinTarget && 
                           databasePerformanceWithinTarget;
    }
    
    // Getters and setters
    public double getAverageResponseTime() { return averageResponseTime; }
    public void setAverageResponseTime(double averageResponseTime) { this.averageResponseTime = averageResponseTime; }
    
    public boolean isResponseTimesWithinTarget() { return responseTimesWithinTarget; }
    public void setResponseTimesWithinTarget(boolean responseTimesWithinTarget) { this.responseTimesWithinTarget = responseTimesWithinTarget; }
    
    public double getMemoryUsage() { return memoryUsage; }
    public void setMemoryUsage(double memoryUsage) { this.memoryUsage = memoryUsage; }
    
    public boolean isMemoryUsageWithinTarget() { return memoryUsageWithinTarget; }
    public void setMemoryUsageWithinTarget(boolean memoryUsageWithinTarget) { this.memoryUsageWithinTarget = memoryUsageWithinTarget; }
    
    public double getAverageDatabaseQueryTime() { return averageDatabaseQueryTime; }
    public void setAverageDatabaseQueryTime(double averageDatabaseQueryTime) { this.averageDatabaseQueryTime = averageDatabaseQueryTime; }
    
    public boolean isDatabasePerformanceWithinTarget() { return databasePerformanceWithinTarget; }
    public void setDatabasePerformanceWithinTarget(boolean databasePerformanceWithinTarget) { this.databasePerformanceWithinTarget = databasePerformanceWithinTarget; }
}

/**
 * Reliability metrics validation results
 */
class ReliabilityMetrics extends BaseMetrics {
    private boolean applicationStable;
    private double errorRate;
    private boolean errorRateWithinTarget;
    private boolean dataIntegrityMaintained;
    
    @Override
    public void calculateOverallSuccess() {
        this.allMetricsMet = applicationStable && errorRateWithinTarget && dataIntegrityMaintained;
    }
    
    // Getters and setters
    public boolean isApplicationStable() { return applicationStable; }
    public void setApplicationStable(boolean applicationStable) { this.applicationStable = applicationStable; }
    
    public double getErrorRate() { return errorRate; }
    public void setErrorRate(double errorRate) { this.errorRate = errorRate; }
    
    public boolean isErrorRateWithinTarget() { return errorRateWithinTarget; }
    public void setErrorRateWithinTarget(boolean errorRateWithinTarget) { this.errorRateWithinTarget = errorRateWithinTarget; }
    
    public boolean isDataIntegrityMaintained() { return dataIntegrityMaintained; }
    public void setDataIntegrityMaintained(boolean dataIntegrityMaintained) { this.dataIntegrityMaintained = dataIntegrityMaintained; }
}

/**
 * Individual validation result
 */
class ValidationResult {
    private String metricName;
    private boolean passed;
    private String description;
    
    public ValidationResult(String metricName, boolean passed, String description) {
        this.metricName = metricName;
        this.passed = passed;
        this.description = description;
    }
    
    // Getters
    public String getMetricName() { return metricName; }
    public boolean isPassed() { return passed; }
    public String getDescription() { return description; }
}