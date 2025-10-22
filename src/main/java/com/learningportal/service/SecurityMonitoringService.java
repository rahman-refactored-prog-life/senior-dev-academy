package com.learningportal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Service for monitoring API security events and suspicious activities
 */
@Service
public class SecurityMonitoringService {

    private static final Logger securityLogger = LoggerFactory.getLogger("SECURITY");
    
    // Track failed attempts per IP
    private final Map<String, AtomicInteger> failedAttempts = new ConcurrentHashMap<>();
    private final Map<String, LocalDateTime> lastFailedAttempt = new ConcurrentHashMap<>();
    
    // Suspicious patterns
    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final int SUSPICIOUS_REQUEST_THRESHOLD = 100;

    public void logSecurityEvent(String eventType, String clientIp, String userAgent, 
                                String endpoint, String details) {
        securityLogger.info("Security Event: {} | IP: {} | Endpoint: {} | UserAgent: {} | Details: {}", 
                           eventType, clientIp, endpoint, userAgent, details);
    }

    public void logFailedAuthentication(String clientIp, String username, String reason) {
        // Increment failed attempts
        failedAttempts.computeIfAbsent(clientIp, k -> new AtomicInteger(0)).incrementAndGet();
        lastFailedAttempt.put(clientIp, LocalDateTime.now());
        
        securityLogger.warn("Failed Authentication: IP: {} | Username: {} | Reason: {}", 
                           clientIp, username, reason);
        
        // Check for suspicious activity
        if (failedAttempts.get(clientIp).get() >= MAX_FAILED_ATTEMPTS) {
            logSuspiciousActivity("BRUTE_FORCE_ATTEMPT", clientIp, 
                                "Multiple failed authentication attempts");
        }
    }

    public void logSuspiciousActivity(String activityType, String clientIp, String details) {
        securityLogger.error("SUSPICIOUS ACTIVITY: {} | IP: {} | Details: {}", 
                            activityType, clientIp, details);
        
        // Here you could integrate with alerting systems
        // sendAlert(activityType, clientIp, details);
    }

    public void logApiUsage(String clientIp, String endpoint, String method, 
                           int responseStatus, long responseTime) {
        // Log API usage for analytics
        securityLogger.debug("API Usage: IP: {} | {} {} | Status: {} | Time: {}ms", 
                            clientIp, method, endpoint, responseStatus, responseTime);
    }

    public boolean isSuspiciousIp(String clientIp) {
        AtomicInteger attempts = failedAttempts.get(clientIp);
        return attempts != null && attempts.get() >= MAX_FAILED_ATTEMPTS;
    }

    public void resetFailedAttempts(String clientIp) {
        failedAttempts.remove(clientIp);
        lastFailedAttempt.remove(clientIp);
    }

    public Map<String, Object> getSecurityMetrics() {
        return Map.of(
            "totalSuspiciousIps", failedAttempts.size(),
            "activeThreats", failedAttempts.entrySet().stream()
                .mapToInt(entry -> entry.getValue().get())
                .sum(),
            "lastUpdated", LocalDateTime.now()
        );
    }
}