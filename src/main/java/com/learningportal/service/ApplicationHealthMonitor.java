package com.learningportal.service;

import java.util.List;
import java.util.Map;

/**
 * Application Health Monitor Interface
 * 
 * Provides scheduled health checks, comprehensive health status reporting,
 * and alerting mechanisms for health issues and performance degradation.
 */
public interface ApplicationHealthMonitor {
    
    /**
     * Performs comprehensive health check of all application components
     * @return HealthCheckResult with detailed health information
     */
    HealthCheckResult performHealthCheck();
    
    /**
     * Gets current health status of all monitored components
     * @return Map of component names to their health status
     */
    Map<String, ComponentHealthStatus> getComponentHealthStatuses();
    
    /**
     * Starts scheduled health monitoring
     * @param intervalSeconds Interval between health checks in seconds
     */
    void startScheduledHealthChecks(int intervalSeconds);
    
    /**
     * Stops scheduled health monitoring
     */
    void stopScheduledHealthChecks();
    
    /**
     * Gets health monitoring configuration
     * @return HealthMonitoringConfig with current settings
     */
    HealthMonitoringConfig getMonitoringConfiguration();
    
    /**
     * Updates health monitoring configuration
     * @param config New health monitoring configuration
     */
    void updateMonitoringConfiguration(HealthMonitoringConfig config);
    
    /**
     * Generates health alerts based on current component status
     * @return List of alert messages
     */
    List<String> generateHealthAlerts();
    
    /**
     * Attempts to perform self-healing for detected issues
     * @param issues List of detected health issues
     * @return SelfHealingResult with actions taken
     */
    SelfHealingResult attemptSelfHealing(List<HealthIssue> issues);
    
    /**
     * Generates comprehensive health report
     * @return SystemHealthReport with detailed health information
     */
    SystemHealthReport generateHealthReport();
    
    /**
     * Gets startup performance metrics
     * @return StartupPerformanceMetrics with startup timing information
     */
    StartupPerformanceMetrics getStartupMetrics();
}