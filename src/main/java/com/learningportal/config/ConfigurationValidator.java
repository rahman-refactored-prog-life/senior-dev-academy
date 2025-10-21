package com.learningportal.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration Validator
 * 
 * Validates all required configuration properties at application startup
 * and provides detailed error messages for missing or invalid configurations.
 * 
 * This ensures that the application fails fast with clear error messages
 * rather than failing at runtime with cryptic errors.
 */
@Component
public class ConfigurationValidator {
    
    private static final Logger log = LoggerFactory.getLogger(ConfigurationValidator.class);
    
    private final Environment environment;
    
    @Value("${spring.datasource.url}")
    private String datasourceUrl;
    
    @Value("${spring.datasource.username}")
    private String datasourceUsername;
    
    @Value("${spring.datasource.password:#{null}}")
    private String datasourcePassword;
    
    @Value("${server.port}")
    private Integer serverPort;
    
    @Value("${spring.profiles.active:default}")
    private String activeProfile;
    
    public ConfigurationValidator(Environment environment) {
        this.environment = environment;
    }
    
    /**
     * Validates configuration after application is ready
     */
    @EventListener(ApplicationReadyEvent.class)
    public void validateConfiguration() {
        log.info("=".repeat(80));
        log.info("CONFIGURATION VALIDATION STARTED");
        log.info("=".repeat(80));
        
        List<String> errors = new ArrayList<>();
        List<String> warnings = new ArrayList<>();
        
        // Validate active profile
        validateActiveProfile(warnings);
        
        // Validate database configuration
        validateDatabaseConfiguration(errors, warnings);
        
        // Validate server configuration
        validateServerConfiguration(errors, warnings);
        
        // Validate security configuration
        validateSecurityConfiguration(errors, warnings);
        
        // Validate cache configuration
        validateCacheConfiguration(warnings);
        
        // Validate logging configuration
        validateLoggingConfiguration(warnings);
        
        // Report results
        reportValidationResults(errors, warnings);
        
        log.info("=".repeat(80));
        log.info("CONFIGURATION VALIDATION COMPLETED");
        log.info("=".repeat(80));
    }
    
    private void validateActiveProfile(List<String> warnings) {
        log.info("Active Profile: {}", activeProfile);
        
        if ("default".equals(activeProfile)) {
            warnings.add("No active profile set. Using default configuration. " +
                       "Set SPRING_PROFILES_ACTIVE environment variable to 'dev', 'test', or 'production'.");
        }
        
        if ("production".equals(activeProfile)) {
            log.warn("PRODUCTION PROFILE ACTIVE - Ensure all required environment variables are set!");
        }
    }
    
    private void validateDatabaseConfiguration(List<String> errors, List<String> warnings) {
        log.info("Database Configuration:");
        log.info("  URL: {}", datasourceUrl);
        log.info("  Username: {}", datasourceUsername);
        log.info("  Password: {}", datasourcePassword != null ? "***SET***" : "NOT SET");
        
        if (datasourceUrl == null || datasourceUrl.trim().isEmpty()) {
            errors.add("Database URL is not configured. Set DB_URL environment variable.");
        }
        
        if (datasourceUsername == null || datasourceUsername.trim().isEmpty()) {
            errors.add("Database username is not configured. Set DB_USERNAME environment variable.");
        }
        
        if (datasourcePassword == null || datasourcePassword.trim().isEmpty()) {
            if ("production".equals(activeProfile)) {
                errors.add("Database password is REQUIRED in production. Set DB_PASSWORD environment variable.");
            } else {
                warnings.add("Database password is not set. This is acceptable for development but REQUIRED for production.");
            }
        }
        
        // Validate connection pool settings
        Integer maxPoolSize = environment.getProperty("spring.datasource.hikari.maximum-pool-size", Integer.class);
        if (maxPoolSize != null) {
            log.info("  Connection Pool Max Size: {}", maxPoolSize);
            if (maxPoolSize < 5) {
                warnings.add("Connection pool size is very small (" + maxPoolSize + "). Consider increasing for better performance.");
            }
        }
    }
    
    private void validateServerConfiguration(List<String> errors, List<String> warnings) {
        log.info("Server Configuration:");
        log.info("  Port: {}", serverPort);
        
        if (serverPort == null) {
            errors.add("Server port is not configured. Set SERVER_PORT environment variable.");
        } else if (serverPort < 1024 && !"root".equals(System.getProperty("user.name"))) {
            warnings.add("Server port " + serverPort + " requires root privileges on Unix systems.");
        } else if (serverPort > 65535) {
            errors.add("Server port " + serverPort + " is invalid. Must be between 1 and 65535.");
        }
        
        String contextPath = environment.getProperty("server.servlet.context-path");
        log.info("  Context Path: {}", contextPath);
    }
    
    private void validateSecurityConfiguration(List<String> errors, List<String> warnings) {
        log.info("Security Configuration:");
        
        String securityUserName = environment.getProperty("spring.security.user.name");
        String securityUserPassword = environment.getProperty("spring.security.user.password");
        
        log.info("  User: {}", securityUserName);
        log.info("  Password: {}", securityUserPassword != null ? "***SET***" : "NOT SET");
        
        if ("production".equals(activeProfile)) {
            if (securityUserName == null || securityUserName.trim().isEmpty()) {
                errors.add("Security username is REQUIRED in production. Set SECURITY_USER_NAME environment variable.");
            }
            
            if (securityUserPassword == null || securityUserPassword.trim().isEmpty()) {
                errors.add("Security password is REQUIRED in production. Set SECURITY_USER_PASSWORD environment variable.");
            } else if (securityUserPassword.length() < 12) {
                warnings.add("Security password is weak. Use at least 12 characters in production.");
            }
            
            if ("admin".equals(securityUserName) || "admin123".equals(securityUserPassword)) {
                errors.add("Default security credentials detected in production! Change SECURITY_USER_NAME and SECURITY_USER_PASSWORD.");
            }
        }
        
        // Validate JWT configuration if present
        String jwtSecret = environment.getProperty("app.security.jwt.secret");
        if (jwtSecret != null) {
            log.info("  JWT Secret: ***SET***");
            if ("production".equals(activeProfile) && jwtSecret.length() < 32) {
                warnings.add("JWT secret is too short. Use at least 32 characters for production.");
            }
        }
    }
    
    private void validateCacheConfiguration(List<String> warnings) {
        log.info("Cache Configuration:");
        
        String cacheType = environment.getProperty("spring.cache.type");
        log.info("  Type: {}", cacheType);
        
        if ("production".equals(activeProfile) && "caffeine".equals(cacheType)) {
            warnings.add("Using in-memory cache (Caffeine) in production. Consider using Redis for distributed caching.");
        }
        
        if ("redis".equals(cacheType)) {
            String redisHost = environment.getProperty("spring.redis.host");
            Integer redisPort = environment.getProperty("spring.redis.port", Integer.class);
            log.info("  Redis Host: {}", redisHost);
            log.info("  Redis Port: {}", redisPort);
        }
    }
    
    private void validateLoggingConfiguration(List<String> warnings) {
        log.info("Logging Configuration:");
        
        String rootLevel = environment.getProperty("logging.level.root");
        String appLevel = environment.getProperty("logging.level.com.learningportal");
        
        log.info("  Root Level: {}", rootLevel);
        log.info("  App Level: {}", appLevel);
        
        if ("production".equals(activeProfile)) {
            if ("DEBUG".equals(rootLevel) || "TRACE".equals(rootLevel)) {
                warnings.add("Root logging level is " + rootLevel + " in production. Consider using INFO or WARN.");
            }
            if ("DEBUG".equals(appLevel) || "TRACE".equals(appLevel)) {
                warnings.add("Application logging level is " + appLevel + " in production. Consider using INFO.");
            }
        }
        
        String logFile = environment.getProperty("logging.file.name");
        if (logFile != null) {
            log.info("  Log File: {}", logFile);
        }
    }
    
    private void reportValidationResults(List<String> errors, List<String> warnings) {
        if (!errors.isEmpty()) {
            log.error("=".repeat(80));
            log.error("CONFIGURATION ERRORS DETECTED ({} errors):", errors.size());
            log.error("=".repeat(80));
            for (int i = 0; i < errors.size(); i++) {
                log.error("{}. {}", i + 1, errors.get(i));
            }
            log.error("=".repeat(80));
            throw new IllegalStateException("Configuration validation failed with " + errors.size() + " error(s). " +
                                          "Please fix the configuration and restart the application.");
        }
        
        if (!warnings.isEmpty()) {
            log.warn("=".repeat(80));
            log.warn("CONFIGURATION WARNINGS ({} warnings):", warnings.size());
            log.warn("=".repeat(80));
            for (int i = 0; i < warnings.size(); i++) {
                log.warn("{}. {}", i + 1, warnings.get(i));
            }
            log.warn("=".repeat(80));
        }
        
        if (errors.isEmpty() && warnings.isEmpty()) {
            log.info("✓ All configuration validation checks passed!");
        } else if (errors.isEmpty()) {
            log.info("✓ Configuration validation passed with {} warning(s)", warnings.size());
        }
    }
}
