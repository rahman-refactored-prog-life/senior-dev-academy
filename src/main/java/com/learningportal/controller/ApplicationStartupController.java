package com.learningportal.controller;

import com.learningportal.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Application Startup Controller
 * 
 * REST endpoints for application startup validation and health monitoring.
 */
@RestController
@RequestMapping("/api/startup")
@Tag(name = "Application Startup", description = "Application startup validation and health monitoring")
public class ApplicationStartupController {
    
    private static final Logger log = LoggerFactory.getLogger(ApplicationStartupController.class);
    
    private final ApplicationStartupValidator startupValidator;
    
    public ApplicationStartupController(ApplicationStartupValidator startupValidator) {
        this.startupValidator = startupValidator;
    }
    
    @Operation(
        summary = "Validate application startup",
        description = "Performs comprehensive validation of application startup process including dependencies, configuration, and readiness checks"
    )
    @GetMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateStartup() {
        try {
            log.info("🚀 Starting application startup validation...");
            
            StartupValidationResult result = startupValidator.validateStartup();
            
            Map<String, Object> response = new HashMap<>();
            response.put("timestamp", LocalDateTime.now());
            response.put("startupSuccessful", result.isStartupSuccessful());
            response.put("startupTimeMs", result.getStartupTimeMs());
            response.put("startupGrade", result.getStartupGrade());
            response.put("overallStatus", result.getOverallStatus());
            response.put("validationSummary", result.getValidationSummary());
            response.put("applicationReady", result.isApplicationReady());
            
            // Issue counts
            response.put("issueCount", result.getIssueCount());
            response.put("criticalErrorCount", result.getCriticalErrorCount());
            response.put("warningCount", result.getWarningCount());
            
            // Detailed information
            if (result.hasIssues()) {
                response.put("issues", result.getIssues());
            }
            if (result.hasCriticalErrors()) {
                response.put("criticalErrors", result.getCriticalErrors());
            }
            if (result.hasWarnings()) {
                response.put("warnings", result.getWarnings());
            }
            if (result.getRecommendations() != null && !result.getRecommendations().isEmpty()) {
                response.put("recommendations", result.getRecommendations());
            }
            
            // Component statuses
            if (result.getComponentStatuses() != null) {
                response.put("componentStatuses", result.getComponentStatuses());
            }
            
            // Readiness status
            if (result.getReadinessStatus() != null) {
                response.put("readinessStatus", result.getReadinessStatus());
            }
            
            // Performance metrics
            if (result.getPerformanceMetrics() != null) {
                response.put("performanceMetrics", result.getPerformanceMetrics());
            }
            
            log.info("✅ Application startup validation completed: {}", result.getValidationSummary());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("❌ Application startup validation failed", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Startup validation failed");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());
            errorResponse.put("startupSuccessful", false);
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @Operation(
        summary = "Check application readiness",
        description = "Checks if the application is ready to serve requests with detailed component readiness information"
    )
    @GetMapping("/readiness")
    public ResponseEntity<Map<String, Object>> checkReadiness() {
        try {
            log.info("🔍 Checking application readiness...");
            
            ApplicationReadinessStatus readinessStatus = startupValidator.checkApplicationReadiness();
            
            Map<String, Object> response = new HashMap<>();
            response.put("timestamp", LocalDateTime.now());
            response.put("ready", readinessStatus.isReady());
            response.put("readinessLevel", readinessStatus.getReadinessLevel());
            response.put("overallStatus", readinessStatus.getOverallStatus());
            response.put("formattedStatus", readinessStatus.getFormattedStatus());
            response.put("readinessPercentage", readinessStatus.getReadinessPercentage());
            response.put("readinessCheckTimeMs", readinessStatus.getReadinessCheckTimeMs());
            
            // Check details
            response.put("passedCheckCount", readinessStatus.getPassedCheckCount());
            response.put("failedCheckCount", readinessStatus.getFailedCheckCount());
            
            if (readinessStatus.getReadinessChecks() != null) {
                response.put("readinessChecks", readinessStatus.getReadinessChecks());
            }
            if (readinessStatus.hasFailedChecks()) {
                response.put("failedChecks", readinessStatus.getFailedChecks());
            }
            if (readinessStatus.getComponentReadiness() != null) {
                response.put("componentReadiness", readinessStatus.getComponentReadiness());
            }
            
            log.info("Application readiness check completed: {}", readinessStatus.getFormattedStatus());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("❌ Application readiness check failed", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Readiness check failed");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());
            errorResponse.put("ready", false);
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @Operation(
        summary = "Validate dependencies",
        description = "Validates all application dependencies including database, Java version, memory, and environment variables"
    )
    @GetMapping("/dependencies")
    public ResponseEntity<Map<String, Object>> validateDependencies() {
        try {
            log.info("🔍 Validating application dependencies...");
            
            List<DependencyIssue> dependencyIssues = startupValidator.validateDependencies();
            
            Map<String, Object> response = new HashMap<>();
            response.put("timestamp", LocalDateTime.now());
            response.put("dependencyCount", dependencyIssues.size());
            response.put("hasIssues", !dependencyIssues.isEmpty());
            
            // Categorize issues
            long criticalIssues = dependencyIssues.stream().filter(DependencyIssue::isRequired).count();
            long warningIssues = dependencyIssues.stream().filter(issue -> !issue.isRequired()).count();
            
            response.put("criticalIssueCount", criticalIssues);
            response.put("warningIssueCount", warningIssues);
            
            if (!dependencyIssues.isEmpty()) {
                response.put("dependencyIssues", dependencyIssues);
            }
            
            // Overall status
            if (criticalIssues == 0) {
                response.put("overallStatus", "Healthy");
                response.put("statusMessage", String.format("✅ All critical dependencies validated - %d warnings", warningIssues));
            } else {
                response.put("overallStatus", "Issues Found");
                response.put("statusMessage", String.format("❌ %d critical dependency issues found", criticalIssues));
            }
            
            log.info("Dependency validation completed: {} critical, {} warnings", criticalIssues, warningIssues);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("❌ Dependency validation failed", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Dependency validation failed");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @Operation(
        summary = "Get startup performance metrics",
        description = "Returns detailed startup performance metrics including timing, memory usage, and performance grading"
    )
    @GetMapping("/performance")
    public ResponseEntity<Map<String, Object>> getStartupPerformance() {
        try {
            log.info("📊 Getting startup performance metrics...");
            
            StartupPerformanceMetrics performanceMetrics = startupValidator.validateStartupPerformance();
            
            Map<String, Object> response = new HashMap<>();
            response.put("timestamp", LocalDateTime.now());
            response.put("startupTimeMs", performanceMetrics.getStartupTimeMs());
            response.put("performanceGrade", performanceMetrics.getPerformanceGrade());
            response.put("performanceIcon", performanceMetrics.getPerformanceIcon());
            response.put("formattedMetrics", performanceMetrics.getFormattedMetrics());
            response.put("performanceAcceptable", performanceMetrics.isPerformanceAcceptable());
            response.put("recommendation", performanceMetrics.getPerformanceRecommendation());
            
            // Memory metrics
            response.put("totalMemoryMB", performanceMetrics.getTotalMemoryMB());
            response.put("freeMemoryMB", performanceMetrics.getFreeMemoryMB());
            response.put("usedMemoryMB", performanceMetrics.getUsedMemoryMB());
            response.put("memoryUsagePercentage", performanceMetrics.getMemoryUsagePercentage());
            
            // Additional metrics if available
            if (performanceMetrics.getJvmVersion() != null) {
                response.put("jvmVersion", performanceMetrics.getJvmVersion());
            }
            if (performanceMetrics.getActiveThreadCount() > 0) {
                response.put("activeThreadCount", performanceMetrics.getActiveThreadCount());
            }
            if (performanceMetrics.getGcTimeMs() > 0) {
                response.put("gcTimeMs", performanceMetrics.getGcTimeMs());
            }
            
            log.info("Startup performance metrics: {}", performanceMetrics.getFormattedMetrics());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("❌ Failed to get startup performance metrics", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Performance metrics retrieval failed");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @Operation(
        summary = "Validate specific component",
        description = "Validates a specific application component by name"
    )
    @GetMapping("/component/{componentName}")
    public ResponseEntity<Map<String, Object>> validateComponent(@PathVariable String componentName) {
        try {
            log.info("🔍 Validating component: {}", componentName);
            
            ComponentValidationResult result = startupValidator.validateComponent(componentName);
            
            Map<String, Object> response = new HashMap<>();
            response.put("timestamp", LocalDateTime.now());
            response.put("componentName", result.getComponentName());
            response.put("validationSuccessful", result.isValidationSuccessful());
            response.put("validationSummary", result.getValidationSummary());
            response.put("validationTimeMs", result.getValidationTimeMs());
            
            if (result.hasIssues()) {
                response.put("validationIssues", result.getValidationIssues());
            }
            if (result.hasRecommendations()) {
                response.put("recommendations", result.getRecommendations());
            }
            if (result.getComponentStatus() != null) {
                response.put("componentStatus", result.getComponentStatus());
            }
            
            log.info("Component validation completed for {}: {}", componentName, result.getValidationSummary());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("❌ Component validation failed for {}", componentName, e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Component validation failed");
            errorResponse.put("componentName", componentName);
            errorResponse.put("message", e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @Operation(
        summary = "Get comprehensive system health report",
        description = "Returns a comprehensive system health report including all components and performance metrics"
    )
    @GetMapping("/health-report")
    public ResponseEntity<Map<String, Object>> getSystemHealthReport() {
        try {
            log.info("📋 Generating comprehensive system health report...");
            
            SystemHealthReport healthReport = startupValidator.performHealthCheck();
            
            Map<String, Object> response = new HashMap<>();
            response.put("timestamp", LocalDateTime.now());
            response.put("healthy", healthReport.isHealthy());
            response.put("healthSummary", healthReport.getHealthSummary());
            response.put("healthPercentage", healthReport.getHealthPercentage());
            response.put("reportGenerationTimeMs", healthReport.getReportGenerationTimeMs());
            
            // Component health
            response.put("healthyComponentCount", healthReport.getHealthyComponentCount());
            response.put("totalComponentCount", healthReport.getTotalComponentCount());
            
            if (healthReport.getComponentHealth() != null) {
                response.put("componentHealth", healthReport.getComponentHealth());
            }
            if (healthReport.hasHealthIssues()) {
                response.put("healthIssues", healthReport.getHealthIssues());
            }
            if (healthReport.hasRecommendations()) {
                response.put("recommendations", healthReport.getRecommendations());
            }
            if (healthReport.getPerformanceMetrics() != null) {
                response.put("performanceMetrics", healthReport.getPerformanceMetrics());
            }
            
            log.info("System health report generated: {}", healthReport.getHealthSummary());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("❌ Failed to generate system health report", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Health report generation failed");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}