package com.learningportal.controller;

import com.learningportal.service.SuccessMetricsValidationService;
import com.learningportal.service.SuccessMetricsReport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Success Metrics Controller
 * 
 * Provides endpoints for validating success metrics and performance benchmarks
 */
@RestController
@RequestMapping("/metrics")
@Tag(name = "Success Metrics", description = "Success metrics validation and performance benchmarking")
public class SuccessMetricsController {

    @Autowired
    private SuccessMetricsValidationService validationService;

    @Operation(
        summary = "Validate All Success Metrics",
        description = """
            Validates all success metrics and performance benchmarks including:
            - Zero compilation errors and warnings
            - Application startup time under 30 seconds
            - API response times under 200ms
            - Memory usage under 80%
            - Database query performance under 50ms
            - Error rate under 0.1%
            - Data integrity maintenance
            
            Returns comprehensive report with baseline measurements and improvement tracking.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Metrics validation completed successfully"),
        @ApiResponse(responseCode = "500", description = "Metrics validation failed due to system error")
    })
    @GetMapping("/validate")
    public ResponseEntity<SuccessMetricsReport> validateAllMetrics() {
        try {
            SuccessMetricsReport report = validationService.validateAllSuccessMetrics();
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            SuccessMetricsReport errorReport = new SuccessMetricsReport();
            errorReport.addError("Metrics validation failed: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorReport);
        }
    }

    @Operation(
        summary = "Get Success Metrics Summary",
        description = "Returns a summary of success metrics with pass/fail status for each category"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Summary retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "No metrics data available")
    })
    @GetMapping("/summary")
    public ResponseEntity<MetricsSummary> getMetricsSummary() {
        try {
            SuccessMetricsReport report = validationService.validateAllSuccessMetrics();
            MetricsSummary summary = createSummary(report);
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Validate Specific Metric Category",
        description = "Validates a specific category of metrics (compilation, startup, runtime, reliability)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category validation completed"),
        @ApiResponse(responseCode = "400", description = "Invalid category specified"),
        @ApiResponse(responseCode = "500", description = "Category validation failed")
    })
    @GetMapping("/category/{category}")
    public ResponseEntity<Object> validateMetricCategory(@PathVariable String category) {
        try {
            SuccessMetricsReport fullReport = validationService.validateAllSuccessMetrics();
            
            return switch (category.toLowerCase()) {
                case "compilation" -> ResponseEntity.ok(fullReport.getCompilationMetrics());
                case "startup" -> ResponseEntity.ok(fullReport.getStartupMetrics());
                case "runtime" -> ResponseEntity.ok(fullReport.getRuntimeMetrics());
                case "reliability" -> ResponseEntity.ok(fullReport.getReliabilityMetrics());
                default -> ResponseEntity.badRequest().body("Invalid category: " + category);
            };
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Category validation failed: " + e.getMessage());
        }
    }

    private MetricsSummary createSummary(SuccessMetricsReport report) {
        MetricsSummary summary = new MetricsSummary();
        summary.setOverallSuccess(report.isOverallSuccess());
        summary.setSuccessRate(report.getSuccessRate());
        summary.setValidationTimestamp(report.getValidationTimestamp());
        
        // Category status summary
        summary.setCompilationStatus(report.getCompilationStatus());
        summary.setStartupStatus(report.getStartupStatus());
        summary.setRuntimeStatus(report.getRuntimeStatus());
        summary.setReliabilityStatus(report.getReliabilityStatus());
        
        summary.setErrorCount(report.getErrors().size());
        summary.setWarningCount(report.getWarnings().size());
        
        return summary;
    }
}

/**
 * Metrics Summary DTO
 */
class MetricsSummary {
    private boolean overallSuccess;
    private double successRate;
    private java.time.LocalDateTime validationTimestamp;
    private int errorCount;
    private int warningCount;
    
    // Category statuses
    private String compilationStatus;
    private String startupStatus;
    private String runtimeStatus;
    private String reliabilityStatus;
    
    // Getters and setters
    public boolean isOverallSuccess() { return overallSuccess; }
    public void setOverallSuccess(boolean overallSuccess) { this.overallSuccess = overallSuccess; }
    
    public double getSuccessRate() { return successRate; }
    public void setSuccessRate(double successRate) { this.successRate = successRate; }
    
    public java.time.LocalDateTime getValidationTimestamp() { return validationTimestamp; }
    public void setValidationTimestamp(java.time.LocalDateTime validationTimestamp) { this.validationTimestamp = validationTimestamp; }
    
    public int getErrorCount() { return errorCount; }
    public void setErrorCount(int errorCount) { this.errorCount = errorCount; }
    
    public int getWarningCount() { return warningCount; }
    public void setWarningCount(int warningCount) { this.warningCount = warningCount; }
    
    public String getCompilationStatus() { return compilationStatus; }
    public void setCompilationStatus(String compilationStatus) { this.compilationStatus = compilationStatus; }
    
    public String getStartupStatus() { return startupStatus; }
    public void setStartupStatus(String startupStatus) { this.startupStatus = startupStatus; }
    
    public String getRuntimeStatus() { return runtimeStatus; }
    public void setRuntimeStatus(String runtimeStatus) { this.runtimeStatus = runtimeStatus; }
    
    public String getReliabilityStatus() { return reliabilityStatus; }
    public void setReliabilityStatus(String reliabilityStatus) { this.reliabilityStatus = reliabilityStatus; }
}