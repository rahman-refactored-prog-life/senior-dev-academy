package com.learningportal.continuity.monitoring;

import com.learningportal.continuity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Comprehensive monitoring service for the Session Continuity System.
 * Provides real-time monitoring, alerting, and performance metrics.
 */
@Service
public class SessionContinuityMonitoringService implements HealthIndicator {
    
    private static final Logger logger = LoggerFactory.getLogger(SessionContinuityMonitoringService.class);
    
    @Autowired
    private SessionStateManager sessionStateManager;
    
    @Autowired
    private DocumentationSynchronizer documentationSynchronizer;
    
    @Autowired
    private QualityGateManager qualityGateManager;
    
    @Autowired
    private ContextRecoveryEngine contextRecoveryEngine;
    
    @Autowired
    private AlertingService alertingService;
    
    // Performance metrics
    private final Map<String, PerformanceMetric> performanceMetrics = new ConcurrentHashMap<>();
    private final Map<String, AtomicLong> counters = new ConcurrentHashMap<>();
    private final Map<String, SystemHealthMetric> healthMetrics = new ConcurrentHashMap<>();
    
    // Monitoring configuration
    private final MonitoringConfiguration config = new MonitoringConfiguration();
    
    // System status
    private SystemStatus currentStatus = SystemStatus.HEALTHY;
    private LocalDateTime lastHealthCheck = LocalDateTime.now();
    private final List<MonitoringEvent> recentEvents = new ArrayList<>();
    
    /**
     * Initialize monitoring service
     */
    public void initialize() {
        logger.info("Initializing Session Continuity Monitoring Service");
        
        // Initialize performance metrics
        initializePerformanceMetrics();
        
        // Initialize counters
        initializeCounters();
        
        // Initialize health metrics
        initializeHealthMetrics();
        
        // Start monitoring
        startMonitoring();
        
        logger.info("Session Continuity Monitoring Service initialized successfully");
    }
    
    /**
     * Initialize performance metrics
     */
    private void initializePerformanceMetrics() {
        performanceMetrics.put("session_capture_time", new PerformanceMetric("Session Capture Time", "ms", 5000));
        performanceMetrics.put("documentation_sync_time", new PerformanceMetric("Documentation Sync Time", "ms", 10000));
        performanceMetrics.put("context_recovery_time", new PerformanceMetric("Context Recovery Time", "ms", 30000));
        performanceMetrics.put("quality_gate_execution_time", new PerformanceMetric("Quality Gate Execution Time", "ms", 15000));
        performanceMetrics.put("validation_time", new PerformanceMetric("Validation Time", "ms", 10000));
    }
    
    /**
     * Initialize counters
     */
    private void initializeCounters() {
        counters.put("session_captures_total", new AtomicLong(0));
        counters.put("session_captures_successful", new AtomicLong(0));
        counters.put("session_captures_failed", new AtomicLong(0));
        counters.put("documentation_syncs_total", new AtomicLong(0));
        counters.put("documentation_syncs_successful", new AtomicLong(0));
        counters.put("documentation_syncs_failed", new AtomicLong(0));
        counters.put("quality_gate_executions_total", new AtomicLong(0));
        counters.put("quality_gate_executions_passed", new AtomicLong(0));
        counters.put("quality_gate_executions_failed", new AtomicLong(0));
        counters.put("context_recoveries_total", new AtomicLong(0));
        counters.put("context_recoveries_successful", new AtomicLong(0));
        counters.put("context_recoveries_failed", new AtomicLong(0));
        counters.put("alerts_triggered", new AtomicLong(0));
    }
    
    /**
     * Initialize health metrics
     */
    private void initializeHealthMetrics() {
        healthMetrics.put("system_health", new SystemHealthMetric("System Health", SystemHealthStatus.HEALTHY));
        healthMetrics.put("session_capture_health", new SystemHealthMetric("Session Capture Health", SystemHealthStatus.HEALTHY));
        healthMetrics.put("documentation_sync_health", new SystemHealthMetric("Documentation Sync Health", SystemHealthStatus.HEALTHY));
        healthMetrics.put("quality_gates_health", new SystemHealthMetric("Quality Gates Health", SystemHealthStatus.HEALTHY));
        healthMetrics.put("context_recovery_health", new SystemHealthMetric("Context Recovery Health", SystemHealthStatus.HEALTHY));
    }
    
    /**
     * Start monitoring processes
     */
    private void startMonitoring() {
        logger.info("Starting monitoring processes");
        
        // Schedule health checks
        scheduleHealthChecks();
        
        // Schedule performance monitoring
        schedulePerformanceMonitoring();
        
        // Schedule alerting checks
        scheduleAlertingChecks();
    }
    
    /**
     * Schedules health checks to run periodically.
     */
    private void scheduleHealthChecks() {
        logger.info("Scheduling health checks to run every 30 seconds");
        // Health checks are scheduled via @Scheduled annotation on performHealthCheck method
    }
    
    /**
     * Schedules performance monitoring to run periodically.
     */
    private void schedulePerformanceMonitoring() {
        logger.info("Scheduling performance monitoring to run every 60 seconds");
        // Performance monitoring is scheduled via @Scheduled annotation on performPerformanceCheck method
    }
    
    /**
     * Schedules alerting checks to run periodically.
     */
    private void scheduleAlertingChecks() {
        logger.info("Scheduling alerting checks to run every 120 seconds");
        // Alerting checks are scheduled via @Scheduled annotation on performAlertingCheck method
    }
    
    /**
     * Scheduled performance check (every 60 seconds)
     */
    @Scheduled(fixedRate = 60000)
    public void performPerformanceCheck() {
        try {
            logger.debug("Performing scheduled performance check");
            
            PerformanceReport report = generatePerformanceReport();
            updatePerformanceMetrics(report);
            checkPerformanceThresholds(report);
            
        } catch (Exception e) {
            logger.error("Error during performance check", e);
            recordEvent(MonitoringEventType.ERROR, "Performance check failed: " + e.getMessage());
        }
    }
    
    /**
     * Scheduled alerting check (every 120 seconds)
     */
    @Scheduled(fixedRate = 120000)
    public void performAlertingCheck() {
        try {
            logger.debug("Performing scheduled alerting check");
            
            checkSystemDegradation();
            checkPerformanceIssues();
            checkFailurePatterns();
            
        } catch (Exception e) {
            logger.error("Error during alerting check", e);
            recordEvent(MonitoringEventType.ERROR, "Alerting check failed: " + e.getMessage());
        }
    }
    
    /**
     * Scheduled health check (every 30 seconds)
     */
    @Scheduled(fixedRate = 30000)
    public void performHealthCheck() {
        try {
            logger.debug("Performing scheduled health check");
            
            SystemHealthReport healthReport = generateHealthReport();
            updateSystemStatus(healthReport);
            
            // Check for alerts
            checkForAlerts(healthReport);
            
            lastHealthCheck = LocalDateTime.now();
            
        } catch (Exception e) {
            logger.error("Error during health check", e);
            recordEvent(MonitoringEventType.ERROR, "Health check failed: " + e.getMessage());
        }
    }
    
    /**
     * Scheduled performance monitoring (every 60 seconds)
     */
    @Scheduled(fixedRate = 60000)
    public void performPerformanceMonitoring() {
        try {
            logger.debug("Performing performance monitoring");
            
            PerformanceReport performanceReport = generatePerformanceReport();
            updatePerformanceMetrics(performanceReport);
            
            // Check performance thresholds
            checkPerformanceThresholds(performanceReport);
            
        } catch (Exception e) {
            logger.error("Error during performance monitoring", e);
            recordEvent(MonitoringEventType.ERROR, "Performance monitoring failed: " + e.getMessage());
        }
    }
    
    /**
     * Scheduled alerting checks (every 2 minutes)
     */
    @Scheduled(fixedRate = 120000)
    public void performAlertingChecks() {
        try {
            logger.debug("Performing alerting checks");
            
            // Check for system degradation
            checkSystemDegradation();
            
            // Check for performance issues
            checkPerformanceIssues();
            
            // Check for failure patterns
            checkFailurePatterns();
            
        } catch (Exception e) {
            logger.error("Error during alerting checks", e);
            recordEvent(MonitoringEventType.ERROR, "Alerting checks failed: " + e.getMessage());
        }
    }
    
    /**
     * Generate comprehensive health report
     */
    public SystemHealthReport generateHealthReport() {
        SystemHealthReport report = new SystemHealthReport();
        report.setTimestamp(LocalDateTime.now());
        
        // Check session capture health
        SessionCaptureHealth captureHealth = checkSessionCaptureHealth();
        report.setSessionCaptureHealth(captureHealth);
        
        // Check documentation sync health
        DocumentationSyncHealth syncHealth = checkDocumentationSyncHealth();
        report.setDocumentationSyncHealth(syncHealth);
        
        // Check quality gates health
        QualityGatesHealth gatesHealth = checkQualityGatesHealth();
        report.setQualityGatesHealth(gatesHealth);
        
        // Check context recovery health
        ContextRecoveryHealth recoveryHealth = checkContextRecoveryHealth();
        report.setContextRecoveryHealth(recoveryHealth);
        
        // Determine overall health
        SystemHealthStatus overallHealth = determineOverallHealth(captureHealth, syncHealth, gatesHealth, recoveryHealth);
        report.setOverallHealth(overallHealth);
        
        return report;
    }
    
    /**
     * Check session capture health
     */
    private SessionCaptureHealth checkSessionCaptureHealth() {
        SessionCaptureHealth health = new SessionCaptureHealth();
        
        try {
            // Test session capture functionality
            ValidationResult validation = sessionStateManager.validateSessionCapture();
            
            if (validation.isValid()) {
                health.setStatus(SystemHealthStatus.HEALTHY);
                health.setMessage("Session capture is functioning normally");
            } else {
                health.setStatus(SystemHealthStatus.DEGRADED);
                health.setMessage("Session capture validation failed: " + validation.getErrorMessage());
            }
            
            // Check performance metrics
            PerformanceMetric captureMetric = performanceMetrics.get("session_capture_time");
            if (captureMetric.getCurrentValue() > captureMetric.getThreshold()) {
                health.setStatus(SystemHealthStatus.DEGRADED);
                health.setMessage("Session capture performance degraded");
            }
            
        } catch (Exception e) {
            health.setStatus(SystemHealthStatus.UNHEALTHY);
            health.setMessage("Session capture health check failed: " + e.getMessage());
        }
        
        return health;
    }
    
    /**
     * Check documentation sync health
     */
    private DocumentationSyncHealth checkDocumentationSyncHealth() {
        DocumentationSyncHealth health = new DocumentationSyncHealth();
        
        try {
            // Test documentation sync functionality
            ConsistencyReport consistency = documentationSynchronizer.validateConsistency();
            
            if (consistency.getConsistencyScore() >= 0.95) {
                health.setStatus(SystemHealthStatus.HEALTHY);
                health.setMessage("Documentation sync is functioning normally");
            } else if (consistency.getConsistencyScore() >= 0.8) {
                health.setStatus(SystemHealthStatus.DEGRADED);
                health.setMessage("Documentation sync consistency degraded: " + consistency.getConsistencyScore());
            } else {
                health.setStatus(SystemHealthStatus.UNHEALTHY);
                health.setMessage("Documentation sync consistency critical: " + consistency.getConsistencyScore());
            }
            
        } catch (Exception e) {
            health.setStatus(SystemHealthStatus.UNHEALTHY);
            health.setMessage("Documentation sync health check failed: " + e.getMessage());
        }
        
        return health;
    }
    
    /**
     * Check quality gates health
     */
    private QualityGatesHealth checkQualityGatesHealth() {
        QualityGatesHealth health = new QualityGatesHealth();
        
        try {
            // Test quality gates functionality
            SessionState testSession = sessionStateManager.captureSessionState();
            QualityGateResults results = qualityGateManager.runAllGates(testSession);
            
            long passedGates = results.getResults().stream()
                .mapToLong(result -> result.isPassed() ? 1 : 0)
                .sum();
            
            double passRate = (double) passedGates / results.getResults().size();
            
            if (passRate >= 0.9) {
                health.setStatus(SystemHealthStatus.HEALTHY);
                health.setMessage("Quality gates are functioning normally");
            } else if (passRate >= 0.7) {
                health.setStatus(SystemHealthStatus.DEGRADED);
                health.setMessage("Quality gates performance degraded: " + passRate);
            } else {
                health.setStatus(SystemHealthStatus.UNHEALTHY);
                health.setMessage("Quality gates performance critical: " + passRate);
            }
            
        } catch (Exception e) {
            health.setStatus(SystemHealthStatus.UNHEALTHY);
            health.setMessage("Quality gates health check failed: " + e.getMessage());
        }
        
        return health;
    }
    
    /**
     * Check context recovery health
     */
    private ContextRecoveryHealth checkContextRecoveryHealth() {
        ContextRecoveryHealth health = new ContextRecoveryHealth();
        
        try {
            // Test context recovery functionality
            List<SourceAnalysis> sources = contextRecoveryEngine.analyzeAvailableSources();
            
            if (!sources.isEmpty() && sources.stream().anyMatch(s -> s.getReliabilityScore() >= 0.8)) {
                health.setStatus(SystemHealthStatus.HEALTHY);
                health.setMessage("Context recovery is functioning normally");
            } else if (!sources.isEmpty()) {
                health.setStatus(SystemHealthStatus.DEGRADED);
                health.setMessage("Context recovery sources have low reliability");
            } else {
                health.setStatus(SystemHealthStatus.UNHEALTHY);
                health.setMessage("No reliable context recovery sources available");
            }
            
        } catch (Exception e) {
            health.setStatus(SystemHealthStatus.UNHEALTHY);
            health.setMessage("Context recovery health check failed: " + e.getMessage());
        }
        
        return health;
    }
    
    /**
     * Determine overall system health
     */
    private SystemHealthStatus determineOverallHealth(SessionCaptureHealth captureHealth,
                                                     DocumentationSyncHealth syncHealth,
                                                     QualityGatesHealth gatesHealth,
                                                     ContextRecoveryHealth recoveryHealth) {
        
        List<SystemHealthStatus> statuses = Arrays.asList(
            captureHealth.getStatus(),
            syncHealth.getStatus(),
            gatesHealth.getStatus(),
            recoveryHealth.getStatus()
        );
        
        // If any component is unhealthy, system is unhealthy
        if (statuses.contains(SystemHealthStatus.UNHEALTHY)) {
            return SystemHealthStatus.UNHEALTHY;
        }
        
        // If any component is degraded, system is degraded
        if (statuses.contains(SystemHealthStatus.DEGRADED)) {
            return SystemHealthStatus.DEGRADED;
        }
        
        // All components are healthy
        return SystemHealthStatus.HEALTHY;
    }
    
    /**
     * Generate performance report
     */
    public PerformanceReport generatePerformanceReport() {
        PerformanceReport report = new PerformanceReport();
        report.setTimestamp(LocalDateTime.now());
        
        // Collect current performance metrics
        Map<String, Double> currentMetrics = new HashMap<>();
        for (Map.Entry<String, PerformanceMetric> entry : performanceMetrics.entrySet()) {
            currentMetrics.put(entry.getKey(), entry.getValue().getCurrentValue());
        }
        report.setPerformanceMetrics(currentMetrics);
        
        // Collect counters
        Map<String, Long> currentCounters = new HashMap<>();
        for (Map.Entry<String, AtomicLong> entry : counters.entrySet()) {
            currentCounters.put(entry.getKey(), entry.getValue().get());
        }
        report.setCounters(currentCounters);
        
        // Calculate success rates
        Map<String, Double> successRates = calculateSuccessRates();
        report.setSuccessRates(successRates);
        
        return report;
    }
    
    /**
     * Calculate success rates for various operations
     */
    private Map<String, Double> calculateSuccessRates() {
        Map<String, Double> successRates = new HashMap<>();
        
        // Session capture success rate
        long totalCaptures = counters.get("session_captures_total").get();
        long successfulCaptures = counters.get("session_captures_successful").get();
        if (totalCaptures > 0) {
            successRates.put("session_capture_success_rate", (double) successfulCaptures / totalCaptures);
        }
        
        // Documentation sync success rate
        long totalSyncs = counters.get("documentation_syncs_total").get();
        long successfulSyncs = counters.get("documentation_syncs_successful").get();
        if (totalSyncs > 0) {
            successRates.put("documentation_sync_success_rate", (double) successfulSyncs / totalSyncs);
        }
        
        // Quality gate success rate
        long totalGateExecutions = counters.get("quality_gate_executions_total").get();
        long passedGateExecutions = counters.get("quality_gate_executions_passed").get();
        if (totalGateExecutions > 0) {
            successRates.put("quality_gate_success_rate", (double) passedGateExecutions / totalGateExecutions);
        }
        
        // Context recovery success rate
        long totalRecoveries = counters.get("context_recoveries_total").get();
        long successfulRecoveries = counters.get("context_recoveries_successful").get();
        if (totalRecoveries > 0) {
            successRates.put("context_recovery_success_rate", (double) successfulRecoveries / totalRecoveries);
        }
        
        return successRates;
    }
    
    /**
     * Update system status based on health report
     */
    private void updateSystemStatus(SystemHealthReport healthReport) {
        SystemStatus previousStatus = currentStatus;
        
        switch (healthReport.getOverallHealth()) {
            case HEALTHY:
                currentStatus = SystemStatus.HEALTHY;
                break;
            case DEGRADED:
                currentStatus = SystemStatus.DEGRADED;
                break;
            case UNHEALTHY:
                currentStatus = SystemStatus.UNHEALTHY;
                break;
        }
        
        // Record status change event
        if (currentStatus != previousStatus) {
            recordEvent(MonitoringEventType.STATUS_CHANGE, 
                "System status changed from " + previousStatus + " to " + currentStatus);
        }
    }
    
    /**
     * Update performance metrics
     */
    private void updatePerformanceMetrics(PerformanceReport performanceReport) {
        for (Map.Entry<String, Double> entry : performanceReport.getPerformanceMetrics().entrySet()) {
            PerformanceMetric metric = performanceMetrics.get(entry.getKey());
            if (metric != null) {
                metric.updateValue(entry.getValue());
            }
        }
    }
    
    /**
     * Check for alerts based on health report
     */
    private void checkForAlerts(SystemHealthReport healthReport) {
        // System health alerts
        if (healthReport.getOverallHealth() == SystemHealthStatus.UNHEALTHY) {
            triggerAlert(AlertLevel.CRITICAL, "System Health Critical", 
                "Session continuity system is in unhealthy state");
        } else if (healthReport.getOverallHealth() == SystemHealthStatus.DEGRADED) {
            triggerAlert(AlertLevel.WARNING, "System Health Degraded", 
                "Session continuity system performance is degraded");
        }
        
        // Component-specific alerts
        checkComponentAlerts(healthReport);
    }
    
    /**
     * Check component-specific alerts
     */
    private void checkComponentAlerts(SystemHealthReport healthReport) {
        // Session capture alerts
        if (healthReport.getSessionCaptureHealth().getStatus() == SystemHealthStatus.UNHEALTHY) {
            triggerAlert(AlertLevel.CRITICAL, "Session Capture Failed", 
                healthReport.getSessionCaptureHealth().getMessage());
        }
        
        // Documentation sync alerts
        if (healthReport.getDocumentationSyncHealth().getStatus() == SystemHealthStatus.UNHEALTHY) {
            triggerAlert(AlertLevel.CRITICAL, "Documentation Sync Failed", 
                healthReport.getDocumentationSyncHealth().getMessage());
        }
        
        // Quality gates alerts
        if (healthReport.getQualityGatesHealth().getStatus() == SystemHealthStatus.UNHEALTHY) {
            triggerAlert(AlertLevel.CRITICAL, "Quality Gates Failed", 
                healthReport.getQualityGatesHealth().getMessage());
        }
        
        // Context recovery alerts
        if (healthReport.getContextRecoveryHealth().getStatus() == SystemHealthStatus.UNHEALTHY) {
            triggerAlert(AlertLevel.CRITICAL, "Context Recovery Failed", 
                healthReport.getContextRecoveryHealth().getMessage());
        }
    }
    
    /**
     * Check performance thresholds
     */
    private void checkPerformanceThresholds(PerformanceReport performanceReport) {
        for (Map.Entry<String, Double> entry : performanceReport.getPerformanceMetrics().entrySet()) {
            PerformanceMetric metric = performanceMetrics.get(entry.getKey());
            if (metric != null && entry.getValue() > metric.getThreshold()) {
                triggerAlert(AlertLevel.WARNING, "Performance Threshold Exceeded", 
                    metric.getName() + " exceeded threshold: " + entry.getValue() + " > " + metric.getThreshold());
            }
        }
    }
    
    /**
     * Check for system degradation patterns
     */
    private void checkSystemDegradation() {
        // Check if system has been degraded for extended period
        if (currentStatus == SystemStatus.DEGRADED) {
            Duration degradedDuration = Duration.between(getLastHealthyTime(), LocalDateTime.now());
            if (degradedDuration.toMinutes() > config.getDegradationAlertThresholdMinutes()) {
                triggerAlert(AlertLevel.WARNING, "Extended System Degradation", 
                    "System has been degraded for " + degradedDuration.toMinutes() + " minutes");
            }
        }
    }
    
    /**
     * Check for performance issues
     */
    private void checkPerformanceIssues() {
        // Check success rates
        Map<String, Double> successRates = calculateSuccessRates();
        
        for (Map.Entry<String, Double> entry : successRates.entrySet()) {
            if (entry.getValue() < config.getMinimumSuccessRate()) {
                triggerAlert(AlertLevel.WARNING, "Low Success Rate", 
                    entry.getKey() + " success rate is low: " + entry.getValue());
            }
        }
    }
    
    /**
     * Check for failure patterns
     */
    private void checkFailurePatterns() {
        // Check for repeated failures
        long recentFailures = recentEvents.stream()
            .filter(event -> event.getType() == MonitoringEventType.ERROR)
            .filter(event -> event.getTimestamp().isAfter(LocalDateTime.now().minusMinutes(10)))
            .count();
        
        if (recentFailures >= config.getFailurePatternThreshold()) {
            triggerAlert(AlertLevel.CRITICAL, "Failure Pattern Detected", 
                "Multiple failures detected in the last 10 minutes: " + recentFailures);
        }
    }
    
    /**
     * Trigger an alert
     */
    private void triggerAlert(AlertLevel level, String title, String message) {
        Alert alert = new Alert();
        alert.setLevel(level);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setTimestamp(LocalDateTime.now());
        alert.setComponent("session-continuity");
        
        // Send alert through alerting service
        alertingService.sendAlert(alert);
        
        // Increment alert counter
        counters.get("alerts_triggered").incrementAndGet();
        
        // Record event
        recordEvent(MonitoringEventType.ALERT, title + ": " + message);
        
        logger.warn("Alert triggered: {} - {}", title, message);
    }
    
    /**
     * Record monitoring event
     */
    private void recordEvent(MonitoringEventType type, String message) {
        MonitoringEvent event = new MonitoringEvent();
        event.setType(type);
        event.setMessage(message);
        event.setTimestamp(LocalDateTime.now());
        
        // Add to recent events (keep only last 100)
        synchronized (recentEvents) {
            recentEvents.add(event);
            if (recentEvents.size() > 100) {
                recentEvents.remove(0);
            }
        }
        
        logger.info("Monitoring event: {} - {}", type, message);
    }
    
    /**
     * Get last healthy time
     */
    private LocalDateTime getLastHealthyTime() {
        // Find the last time system was healthy
        for (int i = recentEvents.size() - 1; i >= 0; i--) {
            MonitoringEvent event = recentEvents.get(i);
            if (event.getType() == MonitoringEventType.STATUS_CHANGE && 
                event.getMessage().contains("HEALTHY")) {
                return event.getTimestamp();
            }
        }
        return LocalDateTime.now().minusHours(1); // Default to 1 hour ago
    }
    
    /**
     * Record session capture metrics
     */
    public void recordSessionCapture(long duration, boolean successful) {
        counters.get("session_captures_total").incrementAndGet();
        if (successful) {
            counters.get("session_captures_successful").incrementAndGet();
        } else {
            counters.get("session_captures_failed").incrementAndGet();
        }
        
        PerformanceMetric metric = performanceMetrics.get("session_capture_time");
        metric.updateValue(duration);
    }
    
    /**
     * Record documentation sync metrics
     */
    public void recordDocumentationSync(long duration, boolean successful) {
        counters.get("documentation_syncs_total").incrementAndGet();
        if (successful) {
            counters.get("documentation_syncs_successful").incrementAndGet();
        } else {
            counters.get("documentation_syncs_failed").incrementAndGet();
        }
        
        PerformanceMetric metric = performanceMetrics.get("documentation_sync_time");
        metric.updateValue(duration);
    }
    
    /**
     * Record quality gate execution metrics
     */
    public void recordQualityGateExecution(long duration, boolean passed) {
        counters.get("quality_gate_executions_total").incrementAndGet();
        if (passed) {
            counters.get("quality_gate_executions_passed").incrementAndGet();
        } else {
            counters.get("quality_gate_executions_failed").incrementAndGet();
        }
        
        PerformanceMetric metric = performanceMetrics.get("quality_gate_execution_time");
        metric.updateValue(duration);
    }
    
    /**
     * Record context recovery metrics
     */
    public void recordContextRecovery(long duration, boolean successful) {
        counters.get("context_recoveries_total").incrementAndGet();
        if (successful) {
            counters.get("context_recoveries_successful").incrementAndGet();
        } else {
            counters.get("context_recoveries_failed").incrementAndGet();
        }
        
        PerformanceMetric metric = performanceMetrics.get("context_recovery_time");
        metric.updateValue(duration);
    }
    
    /**
     * Get current system status
     */
    public SystemStatus getCurrentStatus() {
        return currentStatus;
    }
    
    /**
     * Get monitoring dashboard data
     */
    public MonitoringDashboardData getDashboardData() {
        MonitoringDashboardData data = new MonitoringDashboardData();
        data.setSystemStatus(currentStatus);
        data.setLastHealthCheck(lastHealthCheck);
        data.setHealthReport(generateHealthReport());
        data.setPerformanceReport(generatePerformanceReport());
        data.setRecentEvents(new ArrayList<>(recentEvents));
        data.setHealthMetrics(new HashMap<>(healthMetrics));
        
        return data;
    }
    
    /**
     * Spring Boot Actuator Health Indicator implementation
     */
    @Override
    public Health health() {
        SystemHealthReport healthReport = generateHealthReport();
        
        Health.Builder builder;
        switch (healthReport.getOverallHealth()) {
            case HEALTHY:
                builder = Health.up();
                break;
            case DEGRADED:
                builder = Health.status("DEGRADED");
                break;
            case UNHEALTHY:
            default:
                builder = Health.down();
                break;
        }
        
        return builder
            .withDetail("sessionCapture", healthReport.getSessionCaptureHealth().getStatus())
            .withDetail("documentationSync", healthReport.getDocumentationSyncHealth().getStatus())
            .withDetail("qualityGates", healthReport.getQualityGatesHealth().getStatus())
            .withDetail("contextRecovery", healthReport.getContextRecoveryHealth().getStatus())
            .withDetail("lastHealthCheck", lastHealthCheck)
            .build();
    }
}