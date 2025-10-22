package com.learningportal.continuity.monitoring;

import java.time.LocalDateTime;

/**
 * System health report data class
 */
public class SystemHealthReport {
    private LocalDateTime timestamp;
    private SystemHealthStatus overallHealth;
    private SessionCaptureHealth sessionCaptureHealth;
    private DocumentationSyncHealth documentationSyncHealth;
    private QualityGatesHealth qualityGatesHealth;
    private ContextRecoveryHealth contextRecoveryHealth;
    
    // Getters and setters
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public SystemHealthStatus getOverallHealth() { return overallHealth; }
    public void setOverallHealth(SystemHealthStatus overallHealth) { this.overallHealth = overallHealth; }
    
    public SessionCaptureHealth getSessionCaptureHealth() { return sessionCaptureHealth; }
    public void setSessionCaptureHealth(SessionCaptureHealth sessionCaptureHealth) { this.sessionCaptureHealth = sessionCaptureHealth; }
    
    public DocumentationSyncHealth getDocumentationSyncHealth() { return documentationSyncHealth; }
    public void setDocumentationSyncHealth(DocumentationSyncHealth documentationSyncHealth) { this.documentationSyncHealth = documentationSyncHealth; }
    
    public QualityGatesHealth getQualityGatesHealth() { return qualityGatesHealth; }
    public void setQualityGatesHealth(QualityGatesHealth qualityGatesHealth) { this.qualityGatesHealth = qualityGatesHealth; }
    
    public ContextRecoveryHealth getContextRecoveryHealth() { return contextRecoveryHealth; }
    public void setContextRecoveryHealth(ContextRecoveryHealth contextRecoveryHealth) { this.contextRecoveryHealth = contextRecoveryHealth; }
}

/**
 * Session capture health data class
 */
class SessionCaptureHealth {
    private SystemHealthStatus status;
    private String message;
    private double averageTime;
    private double successRate;
    
    // Getters and setters
    public SystemHealthStatus getStatus() { return status; }
    public void setStatus(SystemHealthStatus status) { this.status = status; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public double getAverageTime() { return averageTime; }
    public void setAverageTime(double averageTime) { this.averageTime = averageTime; }
    
    public double getSuccessRate() { return successRate; }
    public void setSuccessRate(double successRate) { this.successRate = successRate; }
}

/**
 * Documentation sync health data class
 */
class DocumentationSyncHealth {
    private SystemHealthStatus status;
    private String message;
    private double consistencyScore;
    private int filesInSync;
    
    // Getters and setters
    public SystemHealthStatus getStatus() { return status; }
    public void setStatus(SystemHealthStatus status) { this.status = status; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public double getConsistencyScore() { return consistencyScore; }
    public void setConsistencyScore(double consistencyScore) { this.consistencyScore = consistencyScore; }
    
    public int getFilesInSync() { return filesInSync; }
    public void setFilesInSync(int filesInSync) { this.filesInSync = filesInSync; }
}

/**
 * Quality gates health data class
 */
class QualityGatesHealth {
    private SystemHealthStatus status;
    private String message;
    private double passRate;
    private int totalGates;
    
    // Getters and setters
    public SystemHealthStatus getStatus() { return status; }
    public void setStatus(SystemHealthStatus status) { this.status = status; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public double getPassRate() { return passRate; }
    public void setPassRate(double passRate) { this.passRate = passRate; }
    
    public int getTotalGates() { return totalGates; }
    public void setTotalGates(int totalGates) { this.totalGates = totalGates; }
}

/**
 * Context recovery health data class
 */
class ContextRecoveryHealth {
    private SystemHealthStatus status;
    private String message;
    private double averageConfidence;
    private int availableSources;
    
    // Getters and setters
    public SystemHealthStatus getStatus() { return status; }
    public void setStatus(SystemHealthStatus status) { this.status = status; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public double getAverageConfidence() { return averageConfidence; }
    public void setAverageConfidence(double averageConfidence) { this.averageConfidence = averageConfidence; }
    
    public int getAvailableSources() { return availableSources; }
    public void setAvailableSources(int availableSources) { this.availableSources = availableSources; }
}