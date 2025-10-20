package com.learningportal.controller;

import com.learningportal.service.DatabaseConnectionResult;
import com.learningportal.service.DatabaseHealthStatus;
import com.learningportal.service.PostgreSQLConnectionValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Database Health Controller
 * 
 * Provides endpoints for monitoring PostgreSQL database health, connection status,
 * and performance metrics.
 */
@RestController
@RequestMapping("/database")
@Tag(name = "Database Health", description = "PostgreSQL database health monitoring and validation")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DatabaseHealthController {
    
    private final PostgreSQLConnectionValidator connectionValidator;
    
    public DatabaseHealthController(PostgreSQLConnectionValidator connectionValidator) {
        this.connectionValidator = connectionValidator;
    }
    
    @Operation(
        summary = "Get database health status",
        description = "Returns current PostgreSQL database health and connection status"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Database health status retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Database health check failed")
    })
    @GetMapping("/health")
    public ResponseEntity<DatabaseHealthStatus> getDatabaseHealth() {
        try {
            DatabaseHealthStatus health = connectionValidator.getDatabaseHealth();
            
            if (health.isHealthy()) {
                return ResponseEntity.ok(health);
            } else {
                return ResponseEntity.status(503).body(health); // Service Unavailable
            }
            
        } catch (Exception e) {
            DatabaseHealthStatus errorHealth = new DatabaseHealthStatus("Unhealthy", false);
            errorHealth.setErrorMessage("Health check failed: " + e.getMessage());
            return ResponseEntity.status(500).body(errorHealth);
        }
    }
    
    @Operation(
        summary = "Validate database connection",
        description = "Performs comprehensive database connection validation with detailed results"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Connection validation completed"),
        @ApiResponse(responseCode = "500", description = "Connection validation failed")
    })
    @GetMapping("/validate-connection")
    public ResponseEntity<DatabaseConnectionResult> validateConnection() {
        try {
            DatabaseConnectionResult result = connectionValidator.validateConnection();
            
            if (result.isConnectionSuccessful()) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(503).body(result); // Service Unavailable
            }
            
        } catch (Exception e) {
            DatabaseConnectionResult errorResult = new DatabaseConnectionResult();
            errorResult.setConnectionSuccessful(false);
            errorResult.setErrorMessage("Connection validation failed: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResult);
        }
    }
    
    @Operation(
        summary = "Test database connectivity with retry",
        description = "Tests database connectivity with configurable retry mechanism"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Connectivity test completed"),
        @ApiResponse(responseCode = "503", description = "Database connectivity failed")
    })
    @PostMapping("/test-connectivity")
    public ResponseEntity<Map<String, Object>> testConnectivity(
            @RequestParam(defaultValue = "3") int maxRetries,
            @RequestParam(defaultValue = "1000") long retryDelayMs) {
        
        Map<String, Object> result = new HashMap<>();
        result.put("timestamp", java.time.LocalDateTime.now());
        result.put("maxRetries", maxRetries);
        result.put("retryDelayMs", retryDelayMs);
        
        long startTime = System.currentTimeMillis();
        boolean success = connectionValidator.testConnectivityWithRetry(maxRetries, retryDelayMs);
        long totalTime = System.currentTimeMillis() - startTime;
        
        result.put("success", success);
        result.put("totalTimeMs", totalTime);
        result.put("message", success ? 
                   "Database connectivity successful" : 
                   "Database connectivity failed after " + maxRetries + " attempts");
        
        if (success) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(503).body(result); // Service Unavailable
        }
    }
    
    @Operation(
        summary = "Get database configuration info",
        description = "Returns current database configuration and connection pool settings"
    )
    @GetMapping("/config")
    public ResponseEntity<Map<String, Object>> getDatabaseConfig() {
        try {
            DatabaseConnectionResult connectionResult = connectionValidator.validateConnection();
            
            Map<String, Object> config = new HashMap<>();
            config.put("timestamp", java.time.LocalDateTime.now());
            config.put("databaseProduct", connectionResult.getDatabaseProductName());
            config.put("databaseVersion", connectionResult.getDatabaseProductVersion());
            config.put("driverName", connectionResult.getDriverName());
            config.put("driverVersion", connectionResult.getDriverVersion());
            config.put("jdbcUrl", maskSensitiveInfo(connectionResult.getJdbcUrl()));
            config.put("userName", connectionResult.getUserName());
            config.put("supportedFeatures", connectionResult.getSupportedFeatures());
            config.put("connectionPoolInfo", connectionResult.getConnectionPoolInfo());
            config.put("performanceMetrics", connectionResult.getPerformanceMetrics());
            
            return ResponseEntity.ok(config);
            
        } catch (Exception e) {
            Map<String, Object> errorConfig = new HashMap<>();
            errorConfig.put("error", "Failed to retrieve database configuration");
            errorConfig.put("message", e.getMessage());
            errorConfig.put("timestamp", java.time.LocalDateTime.now());
            
            return ResponseEntity.status(500).body(errorConfig);
        }
    }
    
    @Operation(
        summary = "Get database performance metrics",
        description = "Returns database performance metrics and benchmarks"
    )
    @GetMapping("/performance")
    public ResponseEntity<Map<String, Object>> getPerformanceMetrics() {
        try {
            DatabaseConnectionResult result = connectionValidator.validateConnection();
            
            Map<String, Object> performance = new HashMap<>();
            performance.put("timestamp", java.time.LocalDateTime.now());
            performance.put("connectionTimeMs", result.getConnectionTimeMs());
            performance.put("performanceGrade", result.getPerformanceGrade());
            performance.put("hasPerformanceIssues", result.hasPerformanceIssues());
            performance.put("metrics", result.getPerformanceMetrics());
            
            if (result.getPerformanceTestError() != null) {
                performance.put("error", result.getPerformanceTestError());
            }
            
            // Add recommendations based on performance
            if (result.hasPerformanceIssues()) {
                performance.put("recommendations", java.util.Arrays.asList(
                    "Check network latency to database server",
                    "Optimize connection pool settings",
                    "Consider database server hardware upgrade",
                    "Review database configuration parameters"
                ));
            }
            
            return ResponseEntity.ok(performance);
            
        } catch (Exception e) {
            Map<String, Object> errorPerformance = new HashMap<>();
            errorPerformance.put("error", "Failed to retrieve performance metrics");
            errorPerformance.put("message", e.getMessage());
            errorPerformance.put("timestamp", java.time.LocalDateTime.now());
            
            return ResponseEntity.status(500).body(errorPerformance);
        }
    }
    
    // Helper method to mask sensitive information in JDBC URL
    private String maskSensitiveInfo(String jdbcUrl) {
        if (jdbcUrl == null) return null;
        
        // Mask password if present in URL
        return jdbcUrl.replaceAll("password=[^&;]*", "password=***");
    }
}