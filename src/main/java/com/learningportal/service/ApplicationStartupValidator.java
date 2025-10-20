package com.learningportal.service;

import java.util.List;

/**
 * Application Startup Validator Interface
 * 
 * Provides comprehensive validation of application startup process,
 * dependency checking, and system readiness verification.
 */
public interface ApplicationStartupValidator {
    
    /**
     * Validates the complete application startup process
     * @return StartupValidationResult with detailed status and issues
     */
    StartupValidationResult validateStartup();
    
    /**
     * Validates all critical dependencies are available and healthy
     * @return List of dependency issues found
     */
    List<DependencyIssue> validateDependencies();
    
    /**
     * Validates application configuration and environment setup
     * @return ConfigurationValidationResult with detailed analysis
     */
    ConfigurationValidationResult validateConfiguration();
    
    /**
     * Checks if the application is ready to serve requests
     * @return ApplicationReadinessStatus with detailed readiness information
     */
    ApplicationReadinessStatus checkApplicationReadiness();
    
    /**
     * Validates specific application component
     * @param componentName Name of the component to validate
     * @return ComponentValidationResult with component-specific status
     */
    ComponentValidationResult validateComponent(String componentName);
    
    /**
     * Performs comprehensive health check of all application systems
     * @return SystemHealthReport with detailed health information
     */
    SystemHealthReport performHealthCheck();
    
    /**
     * Generates startup issue resolution guidance
     * @param issues List of startup issues to resolve
     * @return StartupResolutionPlan with step-by-step guidance
     */
    StartupResolutionPlan generateResolutionPlan(List<StartupIssue> issues);
    
    /**
     * Validates application startup time and performance
     * @return StartupPerformanceMetrics with timing and performance data
     */
    StartupPerformanceMetrics validateStartupPerformance();
}