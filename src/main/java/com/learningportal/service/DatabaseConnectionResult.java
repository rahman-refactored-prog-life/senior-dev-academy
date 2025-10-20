package com.learningportal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Database Connection Result Data Class
 * 
 * Contains comprehensive results of database connection validation.
 */
public class DatabaseConnectionResult {
    
    private LocalDateTime validationTimestamp;
    private boolean connectionSuccessful;
    private long connectionTimeMs;
    private String databaseProductName;
    private String databaseProductVersion;
    private String driverName;
    private String driverVersion;
    private String jdbcUrl;
    private String userName;
    private List<String> supportedFeatures;
    private List<String> missingFeatures;
    private Map<String, Object> connectionPoolInfo;
    private boolean connectionPoolHealthy;
    private String connectionPoolError;
    private Map<String, Object> performanceMetrics;
    private String performanceTestError;
    private String errorMessage;
    private String sqlState;
    private int errorCode;
    
    // Constructors
    public DatabaseConnectionResult() {
        this.validationTimestamp = LocalDateTime.now();
    }
    
    // Helper methods
    public boolean isHealthy() {
        return connectionSuccessful && connectionPoolHealthy && 
               (missingFeatures == null || missingFeatures.isEmpty());
    }
    
    public String getHealthSummary() {
        if (!connectionSuccessful) {
            return "❌ Connection Failed: " + errorMessage;
        }
        
        if (!connectionPoolHealthy) {
            return "⚠️ Connection Pool Issues: " + connectionPoolError;
        }
        
        if (missingFeatures != null && !missingFeatures.isEmpty()) {
            return "⚠️ Missing Features: " + String.join(", ", missingFeatures);
        }
        
        return "✅ Database connection healthy";
    }
    
    public String getPerformanceGrade() {
        if (performanceMetrics != null && performanceMetrics.containsKey("performanceGrade")) {
            return (String) performanceMetrics.get("performanceGrade");
        }
        return "Unknown";
    }
    
    public boolean hasPerformanceIssues() {
        return connectionTimeMs > 1000 || "Poor".equals(getPerformanceGrade());
    }
    
    // Standard getters and setters
    public LocalDateTime getValidationTimestamp() { return validationTimestamp; }
    public void setValidationTimestamp(LocalDateTime validationTimestamp) { 
        this.validationTimestamp = validationTimestamp; 
    }
    
    public boolean isConnectionSuccessful() { return connectionSuccessful; }
    public void setConnectionSuccessful(boolean connectionSuccessful) { 
        this.connectionSuccessful = connectionSuccessful; 
    }
    
    public long getConnectionTimeMs() { return connectionTimeMs; }
    public void setConnectionTimeMs(long connectionTimeMs) { this.connectionTimeMs = connectionTimeMs; }
    
    public String getDatabaseProductName() { return databaseProductName; }
    public void setDatabaseProductName(String databaseProductName) { 
        this.databaseProductName = databaseProductName; 
    }
    
    public String getDatabaseProductVersion() { return databaseProductVersion; }
    public void setDatabaseProductVersion(String databaseProductVersion) { 
        this.databaseProductVersion = databaseProductVersion; 
    }
    
    public String getDriverName() { return driverName; }
    public void setDriverName(String driverName) { this.driverName = driverName; }
    
    public String getDriverVersion() { return driverVersion; }
    public void setDriverVersion(String driverVersion) { this.driverVersion = driverVersion; }
    
    public String getJdbcUrl() { return jdbcUrl; }
    public void setJdbcUrl(String jdbcUrl) { this.jdbcUrl = jdbcUrl; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public List<String> getSupportedFeatures() { return supportedFeatures; }
    public void setSupportedFeatures(List<String> supportedFeatures) { 
        this.supportedFeatures = supportedFeatures; 
    }
    
    public List<String> getMissingFeatures() { return missingFeatures; }
    public void setMissingFeatures(List<String> missingFeatures) { this.missingFeatures = missingFeatures; }
    
    public Map<String, Object> getConnectionPoolInfo() { return connectionPoolInfo; }
    public void setConnectionPoolInfo(Map<String, Object> connectionPoolInfo) { 
        this.connectionPoolInfo = connectionPoolInfo; 
    }
    
    public boolean isConnectionPoolHealthy() { return connectionPoolHealthy; }
    public void setConnectionPoolHealthy(boolean connectionPoolHealthy) { 
        this.connectionPoolHealthy = connectionPoolHealthy; 
    }
    
    public String getConnectionPoolError() { return connectionPoolError; }
    public void setConnectionPoolError(String connectionPoolError) { 
        this.connectionPoolError = connectionPoolError; 
    }
    
    public Map<String, Object> getPerformanceMetrics() { return performanceMetrics; }
    public void setPerformanceMetrics(Map<String, Object> performanceMetrics) { 
        this.performanceMetrics = performanceMetrics; 
    }
    
    public String getPerformanceTestError() { return performanceTestError; }
    public void setPerformanceTestError(String performanceTestError) { 
        this.performanceTestError = performanceTestError; 
    }
    
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    
    public String getSqlState() { return sqlState; }
    public void setSqlState(String sqlState) { this.sqlState = sqlState; }
    
    public int getErrorCode() { return errorCode; }
    public void setErrorCode(int errorCode) { this.errorCode = errorCode; }
    
    @Override
    public String toString() {
        return "DatabaseConnectionResult{" +
                "connectionSuccessful=" + connectionSuccessful +
                ", connectionTimeMs=" + connectionTimeMs +
                ", databaseProductName='" + databaseProductName + '\'' +
                ", databaseProductVersion='" + databaseProductVersion + '\'' +
                ", connectionPoolHealthy=" + connectionPoolHealthy +
                '}';
    }
}