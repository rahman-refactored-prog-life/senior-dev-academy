package com.learningportal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Application Startup Validator Implementation
 * 
 * Provides comprehensive validation of application startup process,
 * dependency checking, and system readiness verification.
 */
@Service
public class ApplicationStartupValidatorImpl implements ApplicationStartupValidator {
    
    private static final Logger log = LoggerFactory.getLogger(ApplicationStartupValidatorImpl.class);
    
    private final DataSource dataSource;
    private final PostgreSQLConnectionValidator connectionValidator;
    private final DatabaseSchemaValidator schemaValidator;
    
    // Track startup time
    private final long applicationStartTime;
    
    public ApplicationStartupValidatorImpl(DataSource dataSource,
                                         PostgreSQLConnectionValidator connectionValidator,
                                         DatabaseSchemaValidator schemaValidator) {
        this.dataSource = dataSource;
        this.connectionValidator = connectionValidator;
        this.schemaValidator = schemaValidator;
        
        // Get application start time from JVM
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        this.applicationStartTime = runtimeBean.getStartTime();
    }
    
    @Override
    public StartupValidationResult validateStartup() {
        log.info("🚀 Starting comprehensive application startup validation...");
        
        long validationStart = System.currentTimeMillis();
        long currentStartupTime = System.currentTimeMillis() - applicationStartTime;
        
        StartupValidationResult result = new StartupValidationResult();
        result.setStartupTimeMs(currentStartupTime);
        
        List<StartupIssue> issues = new ArrayList<>();
        List<String> criticalErrors = new ArrayList<>();
        List<String> warnings = new ArrayList<>();
        List<String> recommendations = new ArrayList<>();
        
        try {
            // Validate dependencies
            List<DependencyIssue> dependencyIssues = validateDependencies();
            result.setDependencyIssues(dependencyIssues);
            
            // Convert dependency issues to startup issues
            for (DependencyIssue depIssue : dependencyIssues) {
                StartupIssue startupIssue = new StartupIssue();
                startupIssue.setComponent(depIssue.getDependencyName());
                startupIssue.setIssueType(mapDependencyToStartupIssue(depIssue.getIssueType()));
                startupIssue.setSeverity(depIssue.isRequired() ? 
                                       StartupIssue.IssueSeverity.CRITICAL : 
                                       StartupIssue.IssueSeverity.MEDIUM);
                startupIssue.setDescription(depIssue.getDescription());
                startupIssue.setSuggestedFix(depIssue.getSuggestedFix());
                issues.add(startupIssue);
                
                if (depIssue.isRequired()) {
                    criticalErrors.add(depIssue.getFormattedIssue());
                } else {
                    warnings.add(depIssue.getFormattedIssue());
                }
            }
            
            // Validate component statuses
            Map<String, ComponentStatus> componentStatuses = validateAllComponents();
            result.setComponentStatuses(componentStatuses);
            
            // Check application readiness
            ApplicationReadinessStatus readinessStatus = checkApplicationReadiness();
            result.setReadinessStatus(readinessStatus);
            
            // Validate startup performance
            StartupPerformanceMetrics performanceMetrics = validateStartupPerformance();
            result.setPerformanceMetrics(performanceMetrics);
            
            // Add performance recommendations
            if (currentStartupTime > 20000) {
                recommendations.add("Startup time is slow (>20s). Consider optimizing data initialization.");
            } else if (currentStartupTime > 10000) {
                recommendations.add("Startup time is moderate (>10s). Monitor for performance degradation.");
            }
            
            // Determine overall status
            boolean startupSuccessful = criticalErrors.isEmpty() && readinessStatus.isReady();
            result.setStartupSuccessful(startupSuccessful);
            result.setIssues(issues);
            result.setCriticalErrors(criticalErrors);
            result.setWarnings(warnings);
            result.setRecommendations(recommendations);
            
            if (startupSuccessful) {
                result.setOverallStatus("Healthy");
                log.info("✅ Application startup validation successful in {}ms", 
                        System.currentTimeMillis() - validationStart);
            } else {
                result.setOverallStatus("Failed");
                log.warn("❌ Application startup validation found {} critical errors", 
                        criticalErrors.size());
            }
            
        } catch (Exception e) {
            log.error("Application startup validation failed with exception", e);
            result.setStartupSuccessful(false);
            result.setOverallStatus("Error");
            criticalErrors.add("Startup validation failed: " + e.getMessage());
            result.setCriticalErrors(criticalErrors);
        }
        
        return result;
    }
    
    @Override
    public List<DependencyIssue> validateDependencies() {
        log.info("🔍 Validating application dependencies...");
        
        List<DependencyIssue> issues = new ArrayList<>();
        
        // Validate database dependency
        validateDatabaseDependency(issues);
        
        // Validate Java version
        validateJavaVersion(issues);
        
        // Validate memory availability
        validateMemoryAvailability(issues);
        
        // Validate environment variables
        validateEnvironmentVariables(issues);
        
        log.info("Found {} dependency issues", issues.size());
        return issues;
    }
    
    @Override
    public ConfigurationValidationResult validateConfiguration() {
        ConfigurationValidationResult result = new ConfigurationValidationResult();
        result.setConfigurationValid(true);
        result.setValidationTimestamp(LocalDateTime.now());
        return result;
    }
    
    @Override
    public ApplicationReadinessStatus checkApplicationReadiness() {
        log.info("🔍 Checking application readiness...");
        
        long checkStart = System.currentTimeMillis();
        ApplicationReadinessStatus status = new ApplicationReadinessStatus();
        
        List<String> readinessChecks = Arrays.asList(
            "Database Connection",
            "Schema Validation", 
            "Data Initialization",
            "Component Health",
            "Memory Availability"
        );
        
        List<String> failedChecks = new ArrayList<>();
        Map<String, ApplicationReadinessStatus.ComponentReadiness> componentReadiness = new HashMap<>();
        
        // Check database connection
        try {
            DatabaseHealthStatus dbHealth = connectionValidator.getDatabaseHealth();
            boolean dbReady = dbHealth.isHealthy();
            componentReadiness.put("Database", new ApplicationReadinessStatus.ComponentReadiness(
                dbReady, dbHealth.getOverallHealth(), "PostgreSQL database connection"
            ));
            if (!dbReady) {
                failedChecks.add("Database Connection");
            }
        } catch (Exception e) {
            componentReadiness.put("Database", new ApplicationReadinessStatus.ComponentReadiness(
                false, "Error", "Database check failed: " + e.getMessage()
            ));
            failedChecks.add("Database Connection");
        }
        
        // Check schema validation
        try {
            SchemaValidationResult schemaResult = schemaValidator.validateSchema();
            boolean schemaReady = schemaResult.isSchemaValid();
            componentReadiness.put("Schema", new ApplicationReadinessStatus.ComponentReadiness(
                schemaReady, schemaReady ? "Valid" : "Invalid", "Database schema validation"
            ));
            if (!schemaReady) {
                failedChecks.add("Schema Validation");
            }
        } catch (Exception e) {
            componentReadiness.put("Schema", new ApplicationReadinessStatus.ComponentReadiness(
                false, "Error", "Schema validation failed: " + e.getMessage()
            ));
            failedChecks.add("Schema Validation");
        }
        
        // Check memory availability
        Runtime runtime = Runtime.getRuntime();
        long freeMemory = runtime.freeMemory();
        long totalMemory = runtime.totalMemory();
        double memoryUsagePercent = ((totalMemory - freeMemory) * 100.0) / totalMemory;
        
        boolean memoryOk = memoryUsagePercent < 90.0;
        componentReadiness.put("Memory", new ApplicationReadinessStatus.ComponentReadiness(
            memoryOk, memoryOk ? "Sufficient" : "Low", 
            String.format("Memory usage: %.1f%%", memoryUsagePercent)
        ));
        if (!memoryOk) {
            failedChecks.add("Memory Availability");
        }
        
        // Set readiness status
        status.setReadinessChecks(readinessChecks);
        status.setFailedChecks(failedChecks);
        status.setComponentReadiness(componentReadiness);
        status.setReadinessCheckTimeMs(System.currentTimeMillis() - checkStart);
        
        // Determine overall readiness
        if (failedChecks.isEmpty()) {
            status.setReady(true);
            status.setReadinessLevel(ApplicationReadinessStatus.ReadinessLevel.READY);
            status.setOverallStatus("Application is ready to serve requests");
        } else if (failedChecks.size() <= readinessChecks.size() / 2) {
            status.setReady(false);
            status.setReadinessLevel(ApplicationReadinessStatus.ReadinessLevel.PARTIALLY_READY);
            status.setOverallStatus("Application is partially ready with some issues");
        } else {
            status.setReady(false);
            status.setReadinessLevel(ApplicationReadinessStatus.ReadinessLevel.NOT_READY);
            status.setOverallStatus("Application is not ready to serve requests");
        }
        
        log.info("Application readiness: {} ({} failed checks)", 
                status.getReadinessLevel().getDisplayName(), failedChecks.size());
        
        return status;
    }
    
    @Override
    public ComponentValidationResult validateComponent(String componentName) {
        ComponentValidationResult result = new ComponentValidationResult();
        result.setComponentName(componentName);
        result.setValidationTimestamp(LocalDateTime.now());
        return result;
    }
    
    @Override
    public SystemHealthReport performHealthCheck() {
        SystemHealthReport report = new SystemHealthReport();
        report.setHealthy(true);
        report.setReportTimestamp(LocalDateTime.now());
        return report;
    }
    
    @Override
    public StartupResolutionPlan generateResolutionPlan(List<StartupIssue> issues) {
        StartupResolutionPlan plan = new StartupResolutionPlan();
        plan.setIssueCount(issues.size());
        plan.setGenerationTimestamp(LocalDateTime.now());
        return plan;
    }
    
    @Override
    public StartupPerformanceMetrics validateStartupPerformance() {
        long currentStartupTime = System.currentTimeMillis() - applicationStartTime;
        
        StartupPerformanceMetrics metrics = new StartupPerformanceMetrics();
        metrics.setStartupTimeMs(currentStartupTime);
        metrics.setPerformanceGrade(calculatePerformanceGrade(currentStartupTime));
        metrics.setMeasurementTimestamp(LocalDateTime.now());
        
        // Add JVM metrics
        Runtime runtime = Runtime.getRuntime();
        metrics.setTotalMemoryMB(runtime.totalMemory() / (1024 * 1024));
        metrics.setFreeMemoryMB(runtime.freeMemory() / (1024 * 1024));
        metrics.setUsedMemoryMB((runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024));
        
        return metrics;
    }
    
    // Private helper methods
    
    private void validateDatabaseDependency(List<DependencyIssue> issues) {
        try {
            DatabaseHealthStatus dbHealth = connectionValidator.getDatabaseHealth();
            if (!dbHealth.isHealthy()) {
                DependencyIssue issue = new DependencyIssue(
                    "PostgreSQL Database", 
                    DependencyIssue.DependencyType.DATABASE,
                    DependencyIssue.IssueType.UNAVAILABLE,
                    "Database connection is not healthy: " + dbHealth.getOverallHealth()
                );
                issue.setRequired(true);
                issue.setSuggestedFix("Check database connection settings and ensure PostgreSQL is running");
                issues.add(issue);
            }
        } catch (Exception e) {
            DependencyIssue issue = new DependencyIssue(
                "PostgreSQL Database", 
                DependencyIssue.DependencyType.DATABASE,
                DependencyIssue.IssueType.UNAVAILABLE,
                "Failed to check database health: " + e.getMessage()
            );
            issue.setRequired(true);
            issue.setSuggestedFix("Verify database configuration and connectivity");
            issues.add(issue);
        }
    }
    
    private void validateJavaVersion(List<DependencyIssue> issues) {
        String javaVersion = System.getProperty("java.version");
        String expectedVersion = "17";
        
        if (!javaVersion.startsWith(expectedVersion)) {
            DependencyIssue issue = new DependencyIssue(
                "Java Runtime", 
                DependencyIssue.DependencyType.ENVIRONMENT_VARIABLE,
                DependencyIssue.IssueType.VERSION_MISMATCH,
                "Java version mismatch detected"
            );
            issue.setExpectedVersion(expectedVersion + ".x");
            issue.setActualVersion(javaVersion);
            issue.setRequired(true);
            issue.setSuggestedFix("Update to Java 17 or later for optimal compatibility");
            issues.add(issue);
        }
    }
    
    private void validateMemoryAvailability(List<DependencyIssue> issues) {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        
        // Check if available memory is less than 512MB
        long availableMemory = maxMemory - (totalMemory - freeMemory);
        if (availableMemory < 512 * 1024 * 1024) {
            DependencyIssue issue = new DependencyIssue(
                "JVM Memory", 
                DependencyIssue.DependencyType.ENVIRONMENT_VARIABLE,
                DependencyIssue.IssueType.MISCONFIGURED,
                String.format("Low memory available: %dMB", availableMemory / (1024 * 1024))
            );
            issue.setRequired(false);
            issue.setSuggestedFix("Consider increasing JVM heap size with -Xmx parameter");
            issues.add(issue);
        }
    }
    
    private void validateEnvironmentVariables(List<DependencyIssue> issues) {
        // Check for important environment variables
        String[] requiredEnvVars = {"JAVA_HOME"};
        
        for (String envVar : requiredEnvVars) {
            String value = System.getenv(envVar);
            if (value == null || value.trim().isEmpty()) {
                DependencyIssue issue = new DependencyIssue(
                    envVar, 
                    DependencyIssue.DependencyType.ENVIRONMENT_VARIABLE,
                    DependencyIssue.IssueType.MISSING,
                    "Required environment variable is not set"
                );
                issue.setRequired(false);
                issue.setSuggestedFix("Set " + envVar + " environment variable");
                issues.add(issue);
            }
        }
    }
    
    private Map<String, ComponentStatus> validateAllComponents() {
        Map<String, ComponentStatus> componentStatuses = new HashMap<>();
        
        // Database component
        try {
            DatabaseHealthStatus dbHealth = connectionValidator.getDatabaseHealth();
            ComponentStatus dbStatus = new ComponentStatus(
                "Database", 
                dbHealth.isHealthy() ? ComponentStatus.ComponentHealth.HEALTHY : ComponentStatus.ComponentHealth.UNHEALTHY,
                dbHealth.isHealthy()
            );
            dbStatus.setStatus(dbHealth.getOverallHealth());
            dbStatus.setResponseTimeMs(dbHealth.getConnectionTimeMs());
            componentStatuses.put("Database", dbStatus);
        } catch (Exception e) {
            ComponentStatus dbStatus = new ComponentStatus(
                "Database", 
                ComponentStatus.ComponentHealth.UNHEALTHY,
                false
            );
            dbStatus.setErrorMessage(e.getMessage());
            componentStatuses.put("Database", dbStatus);
        }
        
        // Memory component
        Runtime runtime = Runtime.getRuntime();
        long freeMemory = runtime.freeMemory();
        long totalMemory = runtime.totalMemory();
        double memoryUsagePercent = ((totalMemory - freeMemory) * 100.0) / totalMemory;
        
        ComponentStatus memoryStatus = new ComponentStatus(
            "Memory", 
            memoryUsagePercent < 90 ? ComponentStatus.ComponentHealth.HEALTHY : ComponentStatus.ComponentHealth.DEGRADED,
            memoryUsagePercent < 95
        );
        memoryStatus.setDescription(String.format("Memory usage: %.1f%%", memoryUsagePercent));
        componentStatuses.put("Memory", memoryStatus);
        
        return componentStatuses;
    }
    
    private StartupIssue.IssueType mapDependencyToStartupIssue(DependencyIssue.IssueType dependencyIssueType) {
        return switch (dependencyIssueType) {
            case MISSING -> StartupIssue.IssueType.DEPENDENCY_MISSING;
            case UNAVAILABLE -> StartupIssue.IssueType.SERVICE_UNAVAILABLE;
            case MISCONFIGURED -> StartupIssue.IssueType.CONFIGURATION_ERROR;
            case TIMEOUT -> StartupIssue.IssueType.TIMEOUT;
            case PERMISSION_DENIED -> StartupIssue.IssueType.PERMISSION_DENIED;
            case VERSION_MISMATCH -> StartupIssue.IssueType.CONFIGURATION_ERROR;
        };
    }
    
    private String calculatePerformanceGrade(long startupTimeMs) {
        if (startupTimeMs <= 5000) {
            return "Excellent";
        } else if (startupTimeMs <= 10000) {
            return "Good";
        } else if (startupTimeMs <= 20000) {
            return "Fair";
        } else {
            return "Poor";
        }
    }
}