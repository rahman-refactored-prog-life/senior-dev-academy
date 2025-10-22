package com.learningportal.controller;

import com.learningportal.service.SystemValidationService;
import com.learningportal.service.SystemValidationReport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * System Validation Controller
 * 
 * Provides endpoints for comprehensive system validation and technical debt verification
 */
@RestController
@RequestMapping("/validation")
@Tag(name = "System Validation", description = "Comprehensive system validation and technical debt verification")
public class SystemValidationController {

    @Autowired
    private SystemValidationService validationService;

    @Operation(
        summary = "Execute Complete System Validation",
        description = """
            Executes comprehensive end-to-end system validation including:
            - Compilation and build validation
            - Database connectivity and schema validation
            - Application health and startup validation
            - API endpoint accessibility validation
            - Performance benchmarking
            - Technical debt resolution verification
            
            Returns detailed validation report with metrics and recommendations.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Validation completed successfully"),
        @ApiResponse(responseCode = "500", description = "Validation failed due to system error")
    })
    @GetMapping("/complete")
    public ResponseEntity<SystemValidationReport> executeCompleteValidation() {
        try {
            SystemValidationReport report = validationService.executeCompleteValidation();
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            SystemValidationReport errorReport = new SystemValidationReport();
            errorReport.addError("System validation failed: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorReport);
        }
    }

    @Operation(
        summary = "Get Validation Summary",
        description = "Returns a summary of the last validation results with key metrics"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Summary retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "No validation results available")
    })
    @GetMapping("/summary")
    public ResponseEntity<ValidationSummary> getValidationSummary() {
        try {
            SystemValidationReport report = validationService.executeCompleteValidation();
            ValidationSummary summary = createSummary(report);
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Validate Specific Component",
        description = "Validates a specific system component (compilation, database, health, api, performance, technical-debt)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Component validation completed"),
        @ApiResponse(responseCode = "400", description = "Invalid component specified"),
        @ApiResponse(responseCode = "500", description = "Component validation failed")
    })
    @GetMapping("/component/{component}")
    public ResponseEntity<Object> validateComponent(@PathVariable String component) {
        try {
            SystemValidationReport fullReport = validationService.executeCompleteValidation();
            
            return switch (component.toLowerCase()) {
                case "compilation" -> ResponseEntity.ok(fullReport.getCompilationResults());
                case "database" -> ResponseEntity.ok(fullReport.getDatabaseResults());
                case "health" -> ResponseEntity.ok(fullReport.getHealthResults());
                case "api" -> ResponseEntity.ok(fullReport.getApiResults());
                case "performance" -> ResponseEntity.ok(fullReport.getPerformanceResults());
                case "technical-debt" -> ResponseEntity.ok(fullReport.getTechnicalDebtResults());
                default -> ResponseEntity.badRequest().body("Invalid component: " + component);
            };
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Component validation failed: " + e.getMessage());
        }
    }

    private ValidationSummary createSummary(SystemValidationReport report) {
        ValidationSummary summary = new ValidationSummary();
        summary.setOverallStatus(String.valueOf(report.getOverallStatus()));
        summary.setSuccessRate(report.getSuccessRate());
        summary.setTotalValidations(report.getTotalValidations());
        summary.setSuccessfulValidations(report.getSuccessfulValidations());
        summary.setValidationDuration(report.getValidationDuration());
        summary.setErrorCount(report.getErrors().size());
        summary.setWarningCount(report.getWarnings().size());
        
        // Component status summary
        summary.setCompilationStatus(getComponentStatus(report.getCompilationResults()));
        summary.setDatabaseStatus(getComponentStatus(report.getDatabaseResults()));
        summary.setHealthStatus(getComponentStatus(report.getHealthResults()));
        summary.setApiStatus(getComponentStatus(report.getApiResults()));
        summary.setPerformanceStatus(getComponentStatus(report.getPerformanceResults()));
        summary.setTechnicalDebtStatus(getComponentStatus(report.getTechnicalDebtResults()));
        
        return summary;
    }
    
    private String getComponentStatus(Object componentResult) {
        if (componentResult == null) return "NOT_EXECUTED";
        
        try {
            // Use reflection to check isOverallSuccess method
            boolean success = (boolean) componentResult.getClass()
                .getMethod("isOverallSuccess")
                .invoke(componentResult);
            return success ? "PASSED" : "FAILED";
        } catch (Exception e) {
            return "UNKNOWN";
        }
    }
}

/**
 * Validation Summary DTO
 */
class ValidationSummary {
    private String overallStatus;
    private double successRate;
    private int totalValidations;
    private int successfulValidations;
    private java.time.Duration validationDuration;
    private int errorCount;
    private int warningCount;
    
    // Component statuses
    private String compilationStatus;
    private String databaseStatus;
    private String healthStatus;
    private String apiStatus;
    private String performanceStatus;
    private String technicalDebtStatus;
    
    // Getters and setters
    public String getOverallStatus() { return overallStatus; }
    public void setOverallStatus(String overallStatus) { this.overallStatus = overallStatus; }
    
    public double getSuccessRate() { return successRate; }
    public void setSuccessRate(double successRate) { this.successRate = successRate; }
    
    public int getTotalValidations() { return totalValidations; }
    public void setTotalValidations(int totalValidations) { this.totalValidations = totalValidations; }
    
    public int getSuccessfulValidations() { return successfulValidations; }
    public void setSuccessfulValidations(int successfulValidations) { this.successfulValidations = successfulValidations; }
    
    public java.time.Duration getValidationDuration() { return validationDuration; }
    public void setValidationDuration(java.time.Duration validationDuration) { this.validationDuration = validationDuration; }
    
    public int getErrorCount() { return errorCount; }
    public void setErrorCount(int errorCount) { this.errorCount = errorCount; }
    
    public int getWarningCount() { return warningCount; }
    public void setWarningCount(int warningCount) { this.warningCount = warningCount; }
    
    public String getCompilationStatus() { return compilationStatus; }
    public void setCompilationStatus(String compilationStatus) { this.compilationStatus = compilationStatus; }
    
    public String getDatabaseStatus() { return databaseStatus; }
    public void setDatabaseStatus(String databaseStatus) { this.databaseStatus = databaseStatus; }
    
    public String getHealthStatus() { return healthStatus; }
    public void setHealthStatus(String healthStatus) { this.healthStatus = healthStatus; }
    
    public String getApiStatus() { return apiStatus; }
    public void setApiStatus(String apiStatus) { this.apiStatus = apiStatus; }
    
    public String getPerformanceStatus() { return performanceStatus; }
    public void setPerformanceStatus(String performanceStatus) { this.performanceStatus = performanceStatus; }
    
    public String getTechnicalDebtStatus() { return technicalDebtStatus; }
    public void setTechnicalDebtStatus(String technicalDebtStatus) { this.technicalDebtStatus = technicalDebtStatus; }
}