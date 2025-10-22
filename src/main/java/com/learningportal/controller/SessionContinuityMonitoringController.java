package com.learningportal.controller;

import com.learningportal.continuity.monitoring.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for Session Continuity Monitoring and Alerting
 * Provides endpoints for monitoring dashboard, health checks, and alert management
 */
@RestController
@RequestMapping("/api/continuity/monitoring")
@CrossOrigin(origins = "*")
public class SessionContinuityMonitoringController {
    
    private static final Logger logger = LoggerFactory.getLogger(SessionContinuityMonitoringController.class);
    
    @Autowired
    private SessionContinuityMonitoringService monitoringService;
    
    @Autowired
    private AlertingService alertingService;
    
    /**
     * Get comprehensive monitoring dashboard data
     */
    @GetMapping("/dashboard")
    public ResponseEntity<MonitoringDashboardData> getDashboardData() {
        try {
            logger.debug("Fetching monitoring dashboard data");
            
            MonitoringDashboardData dashboardData = monitoringService.getDashboardData();
            
            // Add alert statistics
            AlertStatistics alertStats = alertingService.getAlertStatistics();
            dashboardData.setAlertStatistics(alertStats);
            
            return ResponseEntity.ok(dashboardData);
            
        } catch (Exception e) {
            logger.error("Error fetching dashboard data", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get current system status
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getSystemStatus() {
        try {
            logger.debug("Fetching system status");
            
            SystemStatus status = monitoringService.getCurrentStatus();
            
            Map<String, Object> response = Map.of(
                "status", status.toString(),
                "timestamp", java.time.LocalDateTime.now(),
                "healthy", status == SystemStatus.HEALTHY
            );
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error fetching system status", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get detailed health report
     */
    @GetMapping("/health/detailed")
    public ResponseEntity<SystemHealthReport> getDetailedHealthReport() {
        try {
            logger.debug("Generating detailed health report");
            
            SystemHealthReport healthReport = monitoringService.generateHealthReport();
            
            return ResponseEntity.ok(healthReport);
            
        } catch (Exception e) {
            logger.error("Error generating health report", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get performance metrics
     */
    @GetMapping("/performance")
    public ResponseEntity<PerformanceReport> getPerformanceMetrics() {
        try {
            logger.debug("Fetching performance metrics");
            
            PerformanceReport performanceReport = monitoringService.generatePerformanceReport();
            
            return ResponseEntity.ok(performanceReport);
            
        } catch (Exception e) {
            logger.error("Error fetching performance metrics", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Trigger manual health check
     */
    @PostMapping("/health/check")
    public ResponseEntity<SystemHealthReport> triggerHealthCheck() {
        try {
            logger.info("Manual health check triggered");
            
            monitoringService.performHealthCheck();
            SystemHealthReport healthReport = monitoringService.generateHealthReport();
            
            return ResponseEntity.ok(healthReport);
            
        } catch (Exception e) {
            logger.error("Error during manual health check", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get recent monitoring events
     */
    @GetMapping("/events")
    public ResponseEntity<List<MonitoringEvent>> getRecentEvents(
            @RequestParam(defaultValue = "50") int limit) {
        try {
            logger.debug("Fetching recent monitoring events, limit: {}", limit);
            
            MonitoringDashboardData dashboardData = monitoringService.getDashboardData();
            List<MonitoringEvent> events = dashboardData.getRecentEvents();
            
            // Limit the results
            if (events.size() > limit) {
                events = events.subList(Math.max(0, events.size() - limit), events.size());
            }
            
            return ResponseEntity.ok(events);
            
        } catch (Exception e) {
            logger.error("Error fetching monitoring events", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get alert statistics
     */
    @GetMapping("/alerts/statistics")
    public ResponseEntity<AlertStatistics> getAlertStatistics() {
        try {
            logger.debug("Fetching alert statistics");
            
            AlertStatistics statistics = alertingService.getAlertStatistics();
            
            return ResponseEntity.ok(statistics);
            
        } catch (Exception e) {
            logger.error("Error fetching alert statistics", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get recent alerts
     */
    @GetMapping("/alerts/recent")
    public ResponseEntity<List<Alert>> getRecentAlerts(
            @RequestParam(defaultValue = "20") int limit) {
        try {
            logger.debug("Fetching recent alerts, limit: {}", limit);
            
            List<Alert> alerts = alertingService.getRecentAlerts(limit);
            
            return ResponseEntity.ok(alerts);
            
        } catch (Exception e) {
            logger.error("Error fetching recent alerts", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Send test alert
     */
    @PostMapping("/alerts/test")
    public ResponseEntity<Map<String, String>> sendTestAlert() {
        try {
            logger.info("Test alert requested");
            
            alertingService.sendTestAlert();
            
            Map<String, String> response = Map.of(
                "message", "Test alert sent successfully",
                "timestamp", java.time.LocalDateTime.now().toString()
            );
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error sending test alert", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get alerting configuration
     */
    @GetMapping("/alerts/config")
    public ResponseEntity<AlertingConfiguration> getAlertingConfiguration() {
        try {
            logger.debug("Fetching alerting configuration");
            
            AlertingConfiguration config = alertingService.getConfiguration();
            
            return ResponseEntity.ok(config);
            
        } catch (Exception e) {
            logger.error("Error fetching alerting configuration", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Record session capture metrics (for integration)
     */
    @PostMapping("/metrics/session-capture")
    public ResponseEntity<Map<String, String>> recordSessionCaptureMetrics(
            @RequestBody Map<String, Object> metrics) {
        try {
            long duration = ((Number) metrics.get("duration")).longValue();
            boolean successful = (Boolean) metrics.get("successful");
            
            monitoringService.recordSessionCapture(duration, successful);
            
            Map<String, String> response = Map.of(
                "message", "Session capture metrics recorded",
                "duration", String.valueOf(duration),
                "successful", String.valueOf(successful)
            );
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error recording session capture metrics", e);
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Record documentation sync metrics (for integration)
     */
    @PostMapping("/metrics/documentation-sync")
    public ResponseEntity<Map<String, String>> recordDocumentationSyncMetrics(
            @RequestBody Map<String, Object> metrics) {
        try {
            long duration = ((Number) metrics.get("duration")).longValue();
            boolean successful = (Boolean) metrics.get("successful");
            
            monitoringService.recordDocumentationSync(duration, successful);
            
            Map<String, String> response = Map.of(
                "message", "Documentation sync metrics recorded",
                "duration", String.valueOf(duration),
                "successful", String.valueOf(successful)
            );
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error recording documentation sync metrics", e);
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Record quality gate execution metrics (for integration)
     */
    @PostMapping("/metrics/quality-gate")
    public ResponseEntity<Map<String, String>> recordQualityGateMetrics(
            @RequestBody Map<String, Object> metrics) {
        try {
            long duration = ((Number) metrics.get("duration")).longValue();
            boolean passed = (Boolean) metrics.get("passed");
            
            monitoringService.recordQualityGateExecution(duration, passed);
            
            Map<String, String> response = Map.of(
                "message", "Quality gate metrics recorded",
                "duration", String.valueOf(duration),
                "passed", String.valueOf(passed)
            );
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error recording quality gate metrics", e);
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Record context recovery metrics (for integration)
     */
    @PostMapping("/metrics/context-recovery")
    public ResponseEntity<Map<String, String>> recordContextRecoveryMetrics(
            @RequestBody Map<String, Object> metrics) {
        try {
            long duration = ((Number) metrics.get("duration")).longValue();
            boolean successful = (Boolean) metrics.get("successful");
            
            monitoringService.recordContextRecovery(duration, successful);
            
            Map<String, String> response = Map.of(
                "message", "Context recovery metrics recorded",
                "duration", String.valueOf(duration),
                "successful", String.valueOf(successful)
            );
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error recording context recovery metrics", e);
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Get monitoring system information
     */
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getMonitoringInfo() {
        try {
            Map<String, Object> info = Map.of(
                "service", "Session Continuity Monitoring",
                "version", "1.0.0",
                "features", List.of(
                    "Real-time health monitoring",
                    "Performance metrics collection",
                    "Multi-channel alerting",
                    "Dashboard visualization",
                    "Historical data tracking"
                ),
                "alerting_channels", List.of(
                    "Slack",
                    "Email", 
                    "Webhook",
                    "PagerDuty",
                    "OpsGenie"
                ),
                "monitoring_components", List.of(
                    "Session Capture",
                    "Documentation Sync",
                    "Quality Gates",
                    "Context Recovery"
                )
            );
            
            return ResponseEntity.ok(info);
            
        } catch (Exception e) {
            logger.error("Error fetching monitoring info", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Health check endpoint for the monitoring system itself
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> getMonitoringHealth() {
        try {
            boolean alertingEnabled = alertingService.isAlertingEnabled();
            SystemStatus currentStatus = monitoringService.getCurrentStatus();
            
            Map<String, Object> health = Map.of(
                "status", "UP",
                "monitoring_service", "HEALTHY",
                "alerting_service", alertingEnabled ? "ENABLED" : "DISABLED",
                "system_status", currentStatus.toString(),
                "timestamp", java.time.LocalDateTime.now()
            );
            
            return ResponseEntity.ok(health);
            
        } catch (Exception e) {
            logger.error("Error checking monitoring health", e);
            
            Map<String, Object> health = Map.of(
                "status", "DOWN",
                "error", e.getMessage(),
                "timestamp", java.time.LocalDateTime.now()
            );
            
            return ResponseEntity.status(503).body(health);
        }
    }
}