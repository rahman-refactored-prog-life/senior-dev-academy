package com.learningportal.controller;

import com.learningportal.service.TechnicalDebtPreventionService;
import com.learningportal.service.TechnicalDebtPreventionReport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Technical Debt Prevention Controller
 * 
 * Provides endpoints for technical debt detection, prevention, and monitoring
 */
@RestController
@RequestMapping("/debt-prevention")
@Tag(name = "Technical Debt Prevention", description = "Technical debt detection and prevention framework")
public class TechnicalDebtPreventionController {

    @Autowired
    private TechnicalDebtPreventionService preventionService;

    @Operation(
        summary = "Initialize Prevention Framework",
        description = """
            Initializes the technical debt prevention framework with:
            - Automated detection rules
            - Quality gates configuration
            - Continuous monitoring setup
            - Alert mechanisms
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Framework initialized successfully"),
        @ApiResponse(responseCode = "500", description = "Framework initialization failed")
    })
    @PostMapping("/initialize")
    public ResponseEntity<String> initializeFramework() {
        try {
            preventionService.initializePreventionFramework();
            return ResponseEntity.ok("Technical Debt Prevention Framework initialized successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Framework initialization failed: " + e.getMessage());
        }
    }

    @Operation(
        summary = "Execute Debt Detection",
        description = """
            Executes comprehensive technical debt detection including:
            - Compilation quality checks
            - Database health validation
            - Memory usage monitoring
            - Performance metrics analysis
            - Error rate tracking
            
            Returns detailed report with violations and recommendations.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Debt detection completed successfully"),
        @ApiResponse(responseCode = "500", description = "Debt detection failed")
    })
    @GetMapping("/detect")
    public ResponseEntity<TechnicalDebtPreventionReport> executeDebtDetection() {
        try {
            TechnicalDebtPreventionReport report = preventionService.executeDebtDetection();
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            TechnicalDebtPreventionReport errorReport = new TechnicalDebtPreventionReport();
            errorReport.addError("Debt detection failed: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorReport);
        }
    }

    @Operation(
        summary = "Execute Quality Gates",
        description = """
            Executes quality gates to prevent technical debt introduction:
            - Compilation quality gate
            - Code complexity gate
            - Test coverage gate
            - Performance standards gate
            - Security standards gate
            
            Returns pass/fail status for each gate.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Quality gates executed successfully"),
        @ApiResponse(responseCode = "500", description = "Quality gate execution failed")
    })
    @GetMapping("/quality-gates")
    public ResponseEntity<Object> executeQualityGates() {
        try {
            Object result = preventionService.executeQualityGates();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Quality gate execution failed: " + e.getMessage());
        }
    }

    @Operation(
        summary = "Get Current Metrics",
        description = "Returns current technical debt metrics and monitoring data"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Metrics retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "No metrics available")
    })
    @GetMapping("/metrics")
    public ResponseEntity<Object> getCurrentMetrics() {
        try {
            Object metrics = preventionService.getCurrentMetrics();
            return ResponseEntity.ok(metrics);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Get Active Alerts",
        description = "Returns list of active technical debt alerts requiring attention"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Alerts retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "No alerts available")
    })
    @GetMapping("/alerts")
    public ResponseEntity<Object> getActiveAlerts() {
        try {
            Object alerts = preventionService.getActiveAlerts();
            return ResponseEntity.ok(alerts);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Clear Alert",
        description = "Clears/acknowledges a specific technical debt alert"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Alert cleared successfully"),
        @ApiResponse(responseCode = "404", description = "Alert not found"),
        @ApiResponse(responseCode = "500", description = "Alert clearing failed")
    })
    @DeleteMapping("/alerts/{alertId}")
    public ResponseEntity<String> clearAlert(@PathVariable String alertId) {
        try {
            preventionService.clearAlert(alertId);
            return ResponseEntity.ok("Alert cleared successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Alert clearing failed: " + e.getMessage());
        }
    }

    @Operation(
        summary = "Get Prevention Status",
        description = "Returns overall status of the technical debt prevention framework"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status retrieved successfully")
    })
    @GetMapping("/status")
    public ResponseEntity<PreventionStatus> getPreventionStatus() {
        try {
            TechnicalDebtPreventionReport report = preventionService.executeDebtDetection();
            Object qualityGates = preventionService.executeQualityGates();
            
            PreventionStatus status = new PreventionStatus();
            status.setFrameworkActive(true);
            status.setTechnicalDebtScore(report.getTechnicalDebtScore());
            status.setViolationCount(report.getViolationCount());
            status.setLastCheckTimestamp(report.getDetectionTimestamp());
            
            // Determine overall health
            if (report.getTechnicalDebtScore() < 20.0) {
                status.setOverallHealth("EXCELLENT");
            } else if (report.getTechnicalDebtScore() < 40.0) {
                status.setOverallHealth("GOOD");
            } else if (report.getTechnicalDebtScore() < 60.0) {
                status.setOverallHealth("FAIR");
            } else {
                status.setOverallHealth("POOR");
            }
            
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            PreventionStatus errorStatus = new PreventionStatus();
            errorStatus.setFrameworkActive(false);
            errorStatus.setOverallHealth("ERROR");
            return ResponseEntity.ok(errorStatus);
        }
    }
}

/**
 * Prevention Status DTO
 */
class PreventionStatus {
    private boolean frameworkActive;
    private double technicalDebtScore;
    private int violationCount;
    private java.time.LocalDateTime lastCheckTimestamp;
    private String overallHealth;
    
    // Getters and setters
    public boolean isFrameworkActive() { return frameworkActive; }
    public void setFrameworkActive(boolean frameworkActive) { this.frameworkActive = frameworkActive; }
    
    public double getTechnicalDebtScore() { return technicalDebtScore; }
    public void setTechnicalDebtScore(double technicalDebtScore) { this.technicalDebtScore = technicalDebtScore; }
    
    public int getViolationCount() { return violationCount; }
    public void setViolationCount(int violationCount) { this.violationCount = violationCount; }
    
    public java.time.LocalDateTime getLastCheckTimestamp() { return lastCheckTimestamp; }
    public void setLastCheckTimestamp(java.time.LocalDateTime lastCheckTimestamp) { this.lastCheckTimestamp = lastCheckTimestamp; }
    
    public String getOverallHealth() { return overallHealth; }
    public void setOverallHealth(String overallHealth) { this.overallHealth = overallHealth; }
}