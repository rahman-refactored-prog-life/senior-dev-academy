package com.learningportal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PostgreSQL Connection Validator Service
 * 
 * Provides comprehensive validation of PostgreSQL database connections,
 * configuration, and performance monitoring.
 */
@Service
public class PostgreSQLConnectionValidator {
    
    private static final Logger log = LoggerFactory.getLogger(PostgreSQLConnectionValidator.class);
    
    private final DataSource dataSource;
    
    public PostgreSQLConnectionValidator(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    /**
     * Validate PostgreSQL database connection and configuration
     */
    public DatabaseConnectionResult validateConnection() {
        log.info("🔍 Starting PostgreSQL connection validation...");
        
        long startTime = System.currentTimeMillis();
        DatabaseConnectionResult result = new DatabaseConnectionResult();
        result.setValidationTimestamp(LocalDateTime.now());
        
        try (Connection connection = dataSource.getConnection()) {
            // Basic connection test
            result.setConnectionSuccessful(true);
            result.setConnectionTimeMs(System.currentTimeMillis() - startTime);
            
            // Get database metadata
            DatabaseMetaData metaData = connection.getMetaData();
            result.setDatabaseProductName(metaData.getDatabaseProductName());
            result.setDatabaseProductVersion(metaData.getDatabaseProductVersion());
            result.setDriverName(metaData.getDriverName());
            result.setDriverVersion(metaData.getDriverVersion());
            result.setJdbcUrl(metaData.getURL());
            result.setUserName(metaData.getUserName());
            
            // Validate PostgreSQL specific features
            validatePostgreSQLFeatures(connection, result);
            
            // Check connection pool status
            validateConnectionPool(result);
            
            // Performance tests
            performPerformanceTests(connection, result);
            
            log.info("✅ PostgreSQL connection validation successful in {}ms", 
                    result.getConnectionTimeMs());
            
        } catch (SQLException e) {
            log.error("❌ PostgreSQL connection validation failed", e);
            result.setConnectionSuccessful(false);
            result.setConnectionTimeMs(System.currentTimeMillis() - startTime);
            result.setErrorMessage(e.getMessage());
            result.setSqlState(e.getSQLState());
            result.setErrorCode(e.getErrorCode());
        }
        
        return result;
    }
    
    /**
     * Validate PostgreSQL-specific features and capabilities
     */
    private void validatePostgreSQLFeatures(Connection connection, DatabaseConnectionResult result) 
            throws SQLException {
        
        List<String> supportedFeatures = new ArrayList<>();
        List<String> missingFeatures = new ArrayList<>();
        
        DatabaseMetaData metaData = connection.getMetaData();
        
        // Check for essential PostgreSQL features
        if (metaData.supportsTransactions()) {
            supportedFeatures.add("Transactions");
        } else {
            missingFeatures.add("Transactions");
        }
        
        if (metaData.supportsStoredProcedures()) {
            supportedFeatures.add("Stored Procedures");
        } else {
            missingFeatures.add("Stored Procedures");
        }
        
        if (metaData.supportsMultipleTransactions()) {
            supportedFeatures.add("Multiple Transactions");
        } else {
            missingFeatures.add("Multiple Transactions");
        }
        
        if (metaData.supportsOuterJoins()) {
            supportedFeatures.add("Outer Joins");
        } else {
            missingFeatures.add("Outer Joins");
        }
        
        if (metaData.supportsSubqueriesInExists()) {
            supportedFeatures.add("Subqueries in EXISTS");
        } else {
            missingFeatures.add("Subqueries in EXISTS");
        }
        
        // Check PostgreSQL version compatibility
        String version = metaData.getDatabaseProductVersion();
        if (version.startsWith("PostgreSQL 13") || version.startsWith("PostgreSQL 14") || 
            version.startsWith("PostgreSQL 15") || version.startsWith("PostgreSQL 16")) {
            supportedFeatures.add("Modern PostgreSQL Version");
        } else {
            missingFeatures.add("Modern PostgreSQL Version (13+)");
        }
        
        result.setSupportedFeatures(supportedFeatures);
        result.setMissingFeatures(missingFeatures);
        
        log.debug("PostgreSQL features - Supported: {}, Missing: {}", 
                 supportedFeatures.size(), missingFeatures.size());
    }
    
    /**
     * Validate connection pool configuration and status
     */
    private void validateConnectionPool(DatabaseConnectionResult result) {
        try {
            // Get connection pool information from HikariCP
            if (dataSource instanceof com.zaxxer.hikari.HikariDataSource) {
                com.zaxxer.hikari.HikariDataSource hikariDS = (com.zaxxer.hikari.HikariDataSource) dataSource;
                
                Map<String, Object> poolInfo = new HashMap<>();
                poolInfo.put("poolName", hikariDS.getPoolName());
                poolInfo.put("maximumPoolSize", hikariDS.getMaximumPoolSize());
                poolInfo.put("minimumIdle", hikariDS.getMinimumIdle());
                poolInfo.put("connectionTimeout", hikariDS.getConnectionTimeout());
                poolInfo.put("idleTimeout", hikariDS.getIdleTimeout());
                poolInfo.put("maxLifetime", hikariDS.getMaxLifetime());
                poolInfo.put("leakDetectionThreshold", hikariDS.getLeakDetectionThreshold());
                
                // Get runtime pool metrics
                com.zaxxer.hikari.HikariPoolMXBean poolBean = hikariDS.getHikariPoolMXBean();
                if (poolBean != null) {
                    poolInfo.put("activeConnections", poolBean.getActiveConnections());
                    poolInfo.put("idleConnections", poolBean.getIdleConnections());
                    poolInfo.put("totalConnections", poolBean.getTotalConnections());
                    poolInfo.put("threadsAwaitingConnection", poolBean.getThreadsAwaitingConnection());
                }
                
                result.setConnectionPoolInfo(poolInfo);
                result.setConnectionPoolHealthy(true);
                
                log.debug("Connection pool status: {} active, {} idle, {} total", 
                         poolBean != null ? poolBean.getActiveConnections() : "unknown",
                         poolBean != null ? poolBean.getIdleConnections() : "unknown",
                         poolBean != null ? poolBean.getTotalConnections() : "unknown");
            }
            
        } catch (Exception e) {
            log.warn("Could not retrieve connection pool information: {}", e.getMessage());
            result.setConnectionPoolHealthy(false);
            result.setConnectionPoolError(e.getMessage());
        }
    }
    
    /**
     * Perform basic performance tests on the database connection
     */
    private void performPerformanceTests(Connection connection, DatabaseConnectionResult result) {
        try {
            // Test simple query performance
            long queryStart = System.currentTimeMillis();
            try (var stmt = connection.createStatement();
                 var rs = stmt.executeQuery("SELECT 1")) {
                rs.next();
            }
            long simpleQueryTime = System.currentTimeMillis() - queryStart;
            
            // Test transaction performance
            long transactionStart = System.currentTimeMillis();
            connection.setAutoCommit(false);
            try (var stmt = connection.createStatement()) {
                stmt.execute("SELECT 1");
                connection.commit();
            } finally {
                connection.setAutoCommit(true);
            }
            long transactionTime = System.currentTimeMillis() - transactionStart;
            
            Map<String, Object> performanceMetrics = new HashMap<>();
            performanceMetrics.put("simpleQueryTimeMs", simpleQueryTime);
            performanceMetrics.put("transactionTimeMs", transactionTime);
            performanceMetrics.put("performanceGrade", calculatePerformanceGrade(simpleQueryTime, transactionTime));
            
            result.setPerformanceMetrics(performanceMetrics);
            
            log.debug("Performance metrics - Simple query: {}ms, Transaction: {}ms", 
                     simpleQueryTime, transactionTime);
            
        } catch (SQLException e) {
            log.warn("Performance tests failed: {}", e.getMessage());
            result.setPerformanceTestError(e.getMessage());
        }
    }
    
    /**
     * Calculate performance grade based on query and transaction times
     */
    private String calculatePerformanceGrade(long simpleQueryTime, long transactionTime) {
        if (simpleQueryTime <= 5 && transactionTime <= 10) {
            return "Excellent";
        } else if (simpleQueryTime <= 10 && transactionTime <= 20) {
            return "Good";
        } else if (simpleQueryTime <= 20 && transactionTime <= 50) {
            return "Fair";
        } else {
            return "Poor";
        }
    }
    
    /**
     * Get database health status
     */
    public DatabaseHealthStatus getDatabaseHealth() {
        DatabaseConnectionResult connectionResult = validateConnection();
        
        DatabaseHealthStatus health = new DatabaseHealthStatus();
        health.setTimestamp(LocalDateTime.now());
        health.setConnectionHealthy(connectionResult.isConnectionSuccessful());
        health.setConnectionTimeMs(connectionResult.getConnectionTimeMs());
        
        if (connectionResult.isConnectionSuccessful()) {
            health.setOverallHealth("Healthy");
            health.setDatabaseVersion(connectionResult.getDatabaseProductVersion());
            health.setConnectionPoolHealthy(connectionResult.isConnectionPoolHealthy());
            
            // Determine overall health based on various factors
            if (connectionResult.getConnectionTimeMs() > 1000) {
                health.setOverallHealth("Slow Connection");
            } else if (!connectionResult.getMissingFeatures().isEmpty()) {
                health.setOverallHealth("Feature Issues");
            } else if (!connectionResult.isConnectionPoolHealthy()) {
                health.setOverallHealth("Pool Issues");
            }
        } else {
            health.setOverallHealth("Unhealthy");
            health.setErrorMessage(connectionResult.getErrorMessage());
        }
        
        return health;
    }
    
    /**
     * Test database connectivity with retry mechanism
     */
    public boolean testConnectivityWithRetry(int maxRetries, long retryDelayMs) {
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            log.info("Database connectivity test attempt {}/{}", attempt, maxRetries);
            
            DatabaseConnectionResult result = validateConnection();
            if (result.isConnectionSuccessful()) {
                log.info("✅ Database connectivity successful on attempt {}", attempt);
                return true;
            }
            
            if (attempt < maxRetries) {
                log.warn("❌ Database connectivity failed on attempt {}, retrying in {}ms", 
                        attempt, retryDelayMs);
                try {
                    Thread.sleep(retryDelayMs);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.error("Connectivity test interrupted", e);
                    return false;
                }
            }
        }
        
        log.error("❌ Database connectivity failed after {} attempts", maxRetries);
        return false;
    }
}