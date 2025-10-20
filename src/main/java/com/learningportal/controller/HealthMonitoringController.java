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
 * Health Monitoring Controller
 * 
 * REST endpoints for comprehensive health monitoring, alerting, and self-healing.
 */
@RestController
@RequestMapping("/api/health")
@Tag(name = "Health Monitoring", description = "Comprehensive health monitoring and alerting system")
public class HealthMonitoringController {
    
    private static final Logger log = LoggerFactory.getLogger(HealthMonitoringController.class);
    
    private final ApplicationHealthMonitor healthMonitor;
    
    public HealthMonitoringController(ApplicationHealthMonitor healthMonitor) {
        this.healthMonitor = healthMonitor;
    }
    
    @Operation(
        summary = "Perform comprehensive health check",
        description = "Executes a complete health check of all application components and returns detailed status"
    )
    @GetMapping("/check")
    public ResponseEntity<Map<String, Object>> performHealthCheck() {
        try {
            log.info("🔍 Health check requested via API");
            
            HealthCheckResult result = healthMonitor.performHealthCheck();
            
            Map<String, Object> response = new HashMap<>();
            response.put("timestamp", LocalDateTime.now());
            response.put("healthy", result.isHealthy());
            response.put("overallHealthStatus", result.getOverallHealthStatus());
            response.put("healthSummary", result.getHealthSummary());
            response.put("healthCheckTimeMs", result.getHealthCheckTimeMs());
            
            // Component statuses
            if (result.getComponentStatuses() != null) {
                response.put("componentStatuses", result.getComponentStatuses());
            }
            
            // Issues and recommendations
            if (result.hasHealthIssues()) {
                response.put("healthIssues", result.getHealthIssues());
                response.put("healthIssueCount", result.getHealthIssueCount());
            }
            if (result.hasWarnings()) {
                response.put("warnings", result.getWarnings());
                response.put("warningCount", result.getWarningCount());
            }
            if (result.getRecommendations() != null && !result.getRecommendations().isEmpty()) {
                response.put("recommendations", result.getRecommendations());
            }
            
            // Performance metrics
            if (result.getPerformanceMetrics() != null) {
                response.put("performanceMetrics", result.getPerformanceMetrics());
            }
            
            log.info("Health check completed: {}", result.getHealthSummary());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("❌ Health check failed", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Health check failed");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());
            errorResponse.put("healthy", false);
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @Operation(
        summary = "Get component health statuses",
        description = "Returns current health status of all monitored components"
    )
    @GetMapping("/components")
    public ResponseEntity<Map<String, Object>> getComponentHealthStatuses() {
        try {
            Map<String, ComponentHealthStatus> componentStatuses = healthMonitor.getComponentHealthStatuses();
            
            Map<String, Object> response = new HashMap<>();
            response.put("timestamp", LocalDateTime.now());
            response.put("componentCount", componentStatuses.size());
            response.put("componentStatuses", componentStatuses);
            
            // Calculate summary statistics
            long healthyCount = componentStatuses.values().stream()
                    .mapToLong(status -> status.isHealthy() ? 1 : 0)
                    .sum();
            
            response.put("healthyComponentCount", healthyCount);
            response.put("unhealthyComponentCount", componentStatuses.size() - healthyCount);
            response.put("healthPercentage", componentStatuses.isEmpty() ? 100.0 : 
                        (healthyCount * 100.0) / componentStatuses.size());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("❌ Failed to get component health statuses", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to get component health statuses");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @Operation(
        summary = "Start scheduled health monitoring",
        description = "Starts automated health checks at specified intervals"
    )
    @PostMapping("/monitoring/start")
    public ResponseEntity<Map<String, Object>> startHealthMonitoring(
            @RequestParam(defaultValue = "60") int intervalSeconds) {
        try {
            log.info("🔄 Starting health monitoring with {}s intervals", intervalSeconds);
            
            healthMonitor.startScheduledHealthChecks(intervalSeconds);
            
            Map<String, Object> response = new HashMap<>();
            response.put("timestamp", LocalDateTime.now());
            response.put("message", "Health monitoring started successfully");
            response.put("intervalSeconds", intervalSeconds);
            response.put("monitoringActive", true);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("❌ Failed to start health monitoring", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to start health monitoring");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @Operation(
        summary = "Stop scheduled health monitoring",
        description = "Stops automated health checks"
    )
    @PostMapping("/monitoring/stop")
    public ResponseEntity<Map<String, Object>> stopHealthMonitoring() {
        try {
            log.info("⏹️ Stopping health monitoring");
            
            healthMonitor.stopScheduledHealthChecks();
            
            Map<String, Object> response = new HashMap<>();
            response.put("timestamp", LocalDateTime.now());
            response.put("message", "Health monitoring stopped successfully");
            response.put("monitoringActive", false);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("❌ Failed to stop health monitoring", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to stop health monitoring");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @Operation(
        summary = "Get health alerts",
        description = "Returns current health alerts based on configured thresholds"
    )
    @GetMapping("/alerts")
    public ResponseEntity<Map<String, Object>> getHealthAlerts() {
        try {
            List<String> alerts = healthMonitor.generateHealthAlerts();
            
            Map<String, Object> response = new HashMap<>();
            response.put("timestamp", LocalDateTime.now());
            response.put("alertCount", alerts.size());
            response.put("hasAlerts", !alerts.isEmpty());
            
            if (!alerts.isEmpty()) {
                response.put("alerts", alerts);
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("❌ Failed to get health alerts", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to get health alerts");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @Operation(
        summary = "Get monitoring configuration",
        description = "Returns current health monitoring configuration settings"
    )
    @GetMapping("/config")
    public ResponseEntity<Map<String, Object>> getMonitoringConfiguration() {
        try {
            HealthMonitoringConfig config = healthMonitor.getMonitoringConfiguration();
            
            Map<String, Object> response = new HashMap<>();
            response.put("timestamp", LocalDateTime.now());
            response.put("configuration", config);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("❌ Failed to get monitoring configuration", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to get monitoring configuration");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @Operation(
        summary = "Update monitoring configuration",
        description = "Updates health monitoring configuration settings"
    )
    @PutMapping("/config")
    public ResponseEntity<Map<String, Object>> updateMonitoringConfiguration(
            @RequestBody HealthMonitoringConfig newConfig) {
        try {
            log.info("⚙️ Updating health monitoring configuration");
            
            healthMonitor.updateMonitoringConfiguration(newConfig);
            
            Map<String, Object> response = new HashMap<>();
            response.put("timestamp", LocalDateTime.now());
            response.put("message", "Monitoring configuration updated successfully");
            response.put("configuration", newConfig);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("❌ Failed to update monitoring configuration", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to update monitoring configuration");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}