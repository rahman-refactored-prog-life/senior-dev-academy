package com.learningportal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Database Configuration Validator Service
 * 
 * Validates PostgreSQL configuration settings and provides optimization recommendations.
 */
@Service
public class DatabaseConfigurationValidator {
    
    private static final Logger log = LoggerFactory.getLogger(DatabaseConfigurationValidator.class);
    
    @Value("${spring.datasource.url:}")
    private String datasourceUrl;
    
    @Value("${spring.datasource.username:}")
    private String datasourceUsername;
    
    @Value("${spring.datasource.hikari.maximum-pool-size:10}")
    private int maxPoolSize;
    
    @Value("${spring.datasource.hikari.minimum-idle:2}")
    private int minIdle;
    
    @Value("${spring.datasource.hikari.connection-timeout:20000}")
    private long connectionTimeout;
    
    @Value("${spring.datasource.hikari.idle-timeout:300000}")
    private long idleTimeout;
    
    @Value("${spring.datasource.hikari.leak-detection-threshold:60000}")
    private long leakDetectionThreshold;
    
    private final DataSource dataSource;
    
    public DatabaseConfigurationValidator(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    /**
     * Validate current database configuration
     */
    public DatabaseConfigurationResult validateConfiguration() {
        log.info("🔍 Validating PostgreSQL configuration...");
        
        DatabaseConfigurationResult result = new DatabaseConfigurationResult();
        result.setConfigurationValid(true);
        
        List<String> issues = new ArrayList<>();
        List<String> recommendations = new ArrayList<>();
        List<String> optimizations = new ArrayList<>();
        
        // Validate datasource URL
        validateDatasourceUrl(issues, recommendations);
        
        // Validate connection pool settings
        validateConnectionPoolSettings(issues, recommendations, optimizations);
        
        // Validate timeout settings
        validateTimeoutSettings(issues, recommendations);
        
        // Set results
        result.setIssues(issues);
        result.setRecommendations(recommendations);
        result.setOptimizations(optimizations);
        result.setConfigurationValid(issues.isEmpty());
        
        // Create configuration summary
        Map<String, Object> configSummary = createConfigurationSummary();
        result.setConfigurationSummary(configSummary);
        
        if (result.isConfigurationValid()) {
            log.info("✅ PostgreSQL configuration validation passed");
        } else {
            log.warn("⚠️ PostgreSQL configuration has {} issues", issues.size());
        }
        
        return result;
    }
    
    private void validateDatasourceUrl(List<String> issues, List<String> recommendations) {
        if (datasourceUrl == null || datasourceUrl.trim().isEmpty()) {
            issues.add("Datasource URL is not configured");
            recommendations.add("Configure spring.datasource.url in application.yml");
            return;
        }
        
        if (!datasourceUrl.startsWith("jdbc:postgresql://")) {
            issues.add("Datasource URL is not PostgreSQL");
            recommendations.add("Use PostgreSQL JDBC URL format: jdbc:postgresql://host:port/database");
        }
        
        if (datasourceUrl.contains("localhost") || datasourceUrl.contains("127.0.0.1")) {
            recommendations.add("Consider using environment-specific database hosts for production");
        }
        
        if (!datasourceUrl.contains("?")) {
            recommendations.add("Consider adding PostgreSQL connection parameters for optimization");
        }
    }
    
    private void validateConnectionPoolSettings(List<String> issues, List<String> recommendations, List<String> optimizations) {
        // Validate maximum pool size
        if (maxPoolSize < 5) {
            issues.add("Maximum pool size is too small: " + maxPoolSize);
            recommendations.add("Increase maximum-pool-size to at least 10 for better concurrency");
        } else if (maxPoolSize > 50) {
            recommendations.add("Maximum pool size is quite large: " + maxPoolSize + ". Monitor for resource usage");
        }
        
        // Validate minimum idle
        if (minIdle < 1) {
            issues.add("Minimum idle connections is too small: " + minIdle);
            recommendations.add("Set minimum-idle to at least 2 to maintain ready connections");
        } else if (minIdle > maxPoolSize / 2) {
            recommendations.add("Minimum idle is high relative to max pool size. Consider reducing for resource efficiency");
        }
        
        // Validate pool size ratio
        if (minIdle > maxPoolSize) {
            issues.add("Minimum idle (" + minIdle + ") cannot be greater than maximum pool size (" + maxPoolSize + ")");
            recommendations.add("Ensure minimum-idle <= maximum-pool-size");
        }
        
        // Optimization suggestions
        if (maxPoolSize == 10 && minIdle == 2) {
            optimizations.add("Current pool settings are well-balanced for development");
        }
        
        optimizations.add("For production, consider: max-pool-size=20, minimum-idle=5");
        optimizations.add("Monitor connection usage and adjust pool size based on actual load");
    }
    
    private void validateTimeoutSettings(List<String> issues, List<String> recommendations) {
        // Validate connection timeout
        if (connectionTimeout < 10000) { // Less than 10 seconds
            recommendations.add("Connection timeout is quite short: " + connectionTimeout + "ms. Consider increasing for stability");
        } else if (connectionTimeout > 60000) { // More than 60 seconds
            recommendations.add("Connection timeout is quite long: " + connectionTimeout + "ms. Consider reducing for faster failure detection");
        }
        
        // Validate idle timeout
        if (idleTimeout < 60000) { // Less than 1 minute
            recommendations.add("Idle timeout is very short: " + idleTimeout + "ms. Connections may be closed too quickly");
        } else if (idleTimeout > 1800000) { // More than 30 minutes
            recommendations.add("Idle timeout is very long: " + idleTimeout + "ms. Consider reducing to free up resources");
        }
        
        // Validate leak detection
        if (leakDetectionThreshold == 0) {
            recommendations.add("Leak detection is disabled. Enable it for development: leak-detection-threshold=60000");
        } else if (leakDetectionThreshold < 30000) {
            recommendations.add("Leak detection threshold is quite short: " + leakDetectionThreshold + "ms");
        }
    }
    
    private Map<String, Object> createConfigurationSummary() {
        Map<String, Object> summary = new HashMap<>();
        
        summary.put("datasourceUrl", maskSensitiveInfo(datasourceUrl));
        summary.put("datasourceUsername", datasourceUsername);
        summary.put("maxPoolSize", maxPoolSize);
        summary.put("minIdle", minIdle);
        summary.put("connectionTimeoutMs", connectionTimeout);
        summary.put("idleTimeoutMs", idleTimeout);
        summary.put("leakDetectionThresholdMs", leakDetectionThreshold);
        
        // Add calculated values
        summary.put("connectionTimeoutSeconds", connectionTimeout / 1000);
        summary.put("idleTimeoutMinutes", idleTimeout / 60000);
        summary.put("poolSizeRatio", minIdle + "/" + maxPoolSize);
        
        return summary;
    }
    
    public Map<String, Object> getConfigurationRecommendations() {
        Map<String, Object> recommendations = new HashMap<>();
        
        recommendations.put("title", "PostgreSQL Configuration Recommendations");
        recommendations.put("description", "Optimal settings for different environments");
        
        Map<String, Object> development = new HashMap<>();
        development.put("maximum-pool-size", 10);
        development.put("minimum-idle", 2);
        development.put("connection-timeout", 20000);
        development.put("idle-timeout", 300000);
        development.put("leak-detection-threshold", 60000);
        development.put("show-sql", true);
        development.put("format-sql", true);
        
        Map<String, Object> production = new HashMap<>();
        production.put("maximum-pool-size", 20);
        production.put("minimum-idle", 5);
        production.put("connection-timeout", 30000);
        production.put("idle-timeout", 600000);
        production.put("leak-detection-threshold", 0); // Disabled in production
        production.put("show-sql", false);
        production.put("format-sql", false);
        
        Map<String, Object> testing = new HashMap<>();
        testing.put("maximum-pool-size", 5);
        testing.put("minimum-idle", 1);
        testing.put("connection-timeout", 10000);
        testing.put("idle-timeout", 120000);
        testing.put("leak-detection-threshold", 30000);
        testing.put("show-sql", false);
        testing.put("format-sql", false);
        
        recommendations.put("development", development);
        recommendations.put("production", production);
        recommendations.put("testing", testing);
        
        return recommendations;
    }
    
    // Helper method to mask sensitive information
    private String maskSensitiveInfo(String url) {
        if (url == null) return null;
        return url.replaceAll("password=[^&;]*", "password=***");
    }
}