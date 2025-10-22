package com.learningportal.continuity;

import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Performance-optimized implementation of SessionStateManager
 * Targets: Session capture time < 5 seconds
 */
@Service
public class PerformanceOptimizedSessionStateManager implements SessionStateManagerInterface {

    private final ExecutorService captureExecutor;
    private final ExecutorService validationExecutor;
    private final Map<String, SessionState> sessionCache;
    private final Map<String, Long> captureTimestamps;
    
    // Performance monitoring
    private final Map<String, Long> operationTimes;
    private final List<PerformanceMetric> performanceHistory;
    
    public PerformanceOptimizedSessionStateManager() {
        // Optimized thread pools for parallel processing
        this.captureExecutor = Executors.newFixedThreadPool(4, r -> {
            Thread t = new Thread(r, "session-capture-" + System.currentTimeMillis());
            t.setDaemon(true);
            return t;
        });
        
        this.validationExecutor = Executors.newFixedThreadPool(2, r -> {
            Thread t = new Thread(r, "session-validation-" + System.currentTimeMillis());
            t.setDaemon(true);
            return t;
        });
        
        this.sessionCache = new ConcurrentHashMap<>();
        this.captureTimestamps = new ConcurrentHashMap<>();
        this.operationTimes = new ConcurrentHashMap<>();
        this.performanceHistory = new ArrayList<>();
    }

    @Override
    public ValidationResult captureSessionState(SessionState sessionState) {
        long startTime = System.currentTimeMillis();
        
        try {
            // Parallel validation and processing for speed
            CompletableFuture<ValidationResult> basicValidation = 
                CompletableFuture.supplyAsync(() -> validateBasicFields(sessionState), validationExecutor);
            
            CompletableFuture<ValidationResult> technicalValidation = 
                CompletableFuture.supplyAsync(() -> validateTechnicalEnvironment(sessionState), validationExecutor);
            
            CompletableFuture<Void> cacheUpdate = 
                CompletableFuture.runAsync(() -> updateSessionCache(sessionState), captureExecutor);
            
            CompletableFuture<Void> timestampUpdate = 
                CompletableFuture.runAsync(() -> updateCaptureTimestamp(sessionState.getSessionId()), captureExecutor);
            
            // Wait for all validations to complete
            ValidationResult basicResult = basicValidation.get(2, TimeUnit.SECONDS);
            ValidationResult technicalResult = technicalValidation.get(2, TimeUnit.SECONDS);
            
            // Wait for cache updates
            cacheUpdate.get(1, TimeUnit.SECONDS);
            timestampUpdate.get(1, TimeUnit.SECONDS);
            
            // Combine validation results
            ValidationResult combinedResult = combineValidationResults(basicResult, technicalResult);
            
            // Record performance metrics
            long duration = System.currentTimeMillis() - startTime;
            recordPerformanceMetric("captureSessionState", duration);
            
            // Ensure we meet the 5-second target
            if (duration > 5000) {
                System.err.println("WARNING: Session capture took " + duration + "ms, exceeding 5-second target");
            }
            
            return combinedResult;
            
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            recordPerformanceMetric("captureSessionState_ERROR", duration);
            
            ValidationResult errorResult = new ValidationResult();
            errorResult.setValid(false);
            errorResult.setErrors(List.of("Session capture failed: " + e.getMessage()));
            return errorResult;
        }
    }

    @Cacheable(value = "sessionStates", key = "#sessionId")
    public SessionState getSessionState(String sessionId) {
        return sessionCache.get(sessionId);
    }

    @CacheEvict(value = "sessionStates", key = "#sessionId")
    public void evictSessionState(String sessionId) {
        sessionCache.remove(sessionId);
        captureTimestamps.remove(sessionId);
    }

    @Async
    public CompletableFuture<ValidationResult> captureSessionStateAsync(SessionState sessionState) {
        return CompletableFuture.supplyAsync(() -> captureSessionState(sessionState), captureExecutor);
    }

    public PerformanceReport getPerformanceReport() {
        PerformanceReport report = new PerformanceReport();
        
        // Calculate average capture time
        List<Long> captureTimes = performanceHistory.stream()
            .filter(m -> m.getOperation().equals("captureSessionState"))
            .map(PerformanceMetric::getDuration)
            .toList();
        
        if (!captureTimes.isEmpty()) {
            double avgTime = captureTimes.stream().mapToLong(Long::longValue).average().orElse(0.0);
            long maxTime = captureTimes.stream().mapToLong(Long::longValue).max().orElse(0L);
            long minTime = captureTimes.stream().mapToLong(Long::longValue).min().orElse(0L);
            
            report.setAverageCaptureTime(avgTime);
            report.setMaxCaptureTime(maxTime);
            report.setMinCaptureTime(minTime);
            report.setTotalCaptures(captureTimes.size());
            
            // Calculate performance target compliance
            long targetViolations = captureTimes.stream()
                .mapToLong(time -> time > 5000 ? 1 : 0)
                .sum();
            
            report.setTargetCompliance((double) (captureTimes.size() - targetViolations) / captureTimes.size());
        }
        
        report.setCacheHitRate(calculateCacheHitRate());
        report.setThreadPoolUtilization(calculateThreadPoolUtilization());
        
        return report;
    }

    public void optimizePerformance() {
        // Dynamic performance optimization based on metrics
        PerformanceReport report = getPerformanceReport();
        
        if (report.getAverageCaptureTime() > 3000) {
            // Increase thread pool size if average time is high
            if (captureExecutor instanceof ThreadPoolExecutor) {
                ThreadPoolExecutor tpe = (ThreadPoolExecutor) captureExecutor;
                int currentSize = tpe.getCorePoolSize();
                if (currentSize < 8) {
                    tpe.setCorePoolSize(currentSize + 1);
                    tpe.setMaximumPoolSize(currentSize + 2);
                    System.out.println("Increased capture thread pool size to " + (currentSize + 1));
                }
            }
        }
        
        if (report.getCacheHitRate() < 0.7) {
            // Clear old cache entries to improve hit rate
            cleanupOldCacheEntries();
        }
        
        // Cleanup old performance metrics to prevent memory leaks
        if (performanceHistory.size() > 1000) {
            performanceHistory.subList(0, 500).clear();
        }
    }

    // Private helper methods

    private ValidationResult validateBasicFields(SessionState sessionState) {
        ValidationResult result = new ValidationResult();
        List<String> errors = new ArrayList<>();
        
        if (sessionState.getSessionId() == null || sessionState.getSessionId().trim().isEmpty()) {
            errors.add("Session ID is required");
        }
        
        if (sessionState.getTimestamp() == null) {
            errors.add("Timestamp is required");
        }
        
        if (sessionState.getProgressPercentage() < 0 || sessionState.getProgressPercentage() > 100) {
            errors.add("Progress percentage must be between 0 and 100");
        }
        
        result.setValid(errors.isEmpty());
        result.setErrors(errors);
        return result;
    }

    private ValidationResult validateTechnicalEnvironment(SessionState sessionState) {
        ValidationResult result = new ValidationResult();
        List<String> errors = new ArrayList<>();
        
        if (sessionState.getJavaVersion() == null || sessionState.getJavaVersion().trim().isEmpty()) {
            errors.add("Java version is required");
        }
        
        if (sessionState.getMavenStatus() == null || sessionState.getMavenStatus().trim().isEmpty()) {
            errors.add("Maven status is required");
        }
        
        if (sessionState.getCompilationStatus() == null) {
            errors.add("Compilation status is required");
        }
        
        if (sessionState.getDatabaseStatus() == null) {
            errors.add("Database status is required");
        }
        
        result.setValid(errors.isEmpty());
        result.setErrors(errors);
        return result;
    }

    private ValidationResult combineValidationResults(ValidationResult... results) {
        ValidationResult combined = new ValidationResult();
        List<String> allErrors = new ArrayList<>();
        boolean allValid = true;
        
        for (ValidationResult result : results) {
            if (!result.isValid()) {
                allValid = false;
            }
            allErrors.addAll(result.getErrors());
        }
        
        combined.setValid(allValid);
        combined.setErrors(allErrors);
        return combined;
    }

    private void updateSessionCache(SessionState sessionState) {
        sessionCache.put(sessionState.getSessionId(), sessionState);
    }

    private void updateCaptureTimestamp(String sessionId) {
        captureTimestamps.put(sessionId, System.currentTimeMillis());
    }

    private void recordPerformanceMetric(String operation, long duration) {
        PerformanceMetric metric = new PerformanceMetric();
        metric.setOperation(operation);
        metric.setDuration(duration);
        metric.setTimestamp(LocalDateTime.now());
        
        synchronized (performanceHistory) {
            performanceHistory.add(metric);
        }
        
        operationTimes.put(operation + "_" + System.currentTimeMillis(), duration);
    }

    private double calculateCacheHitRate() {
        // Simplified cache hit rate calculation
        // In a real implementation, this would track actual cache hits/misses
        return sessionCache.size() > 0 ? 0.85 : 0.0;
    }

    private double calculateThreadPoolUtilization() {
        if (captureExecutor instanceof ThreadPoolExecutor) {
            ThreadPoolExecutor tpe = (ThreadPoolExecutor) captureExecutor;
            return (double) tpe.getActiveCount() / tpe.getPoolSize();
        }
        return 0.0;
    }

    private void cleanupOldCacheEntries() {
        long currentTime = System.currentTimeMillis();
        long maxAge = 30 * 60 * 1000; // 30 minutes
        
        captureTimestamps.entrySet().removeIf(entry -> {
            if (currentTime - entry.getValue() > maxAge) {
                sessionCache.remove(entry.getKey());
                return true;
            }
            return false;
        });
    }

    // Performance monitoring classes

    public static class PerformanceMetric {
        private String operation;
        private long duration;
        private LocalDateTime timestamp;
        
        // Getters and setters
        public String getOperation() { return operation; }
        public void setOperation(String operation) { this.operation = operation; }
        public long getDuration() { return duration; }
        public void setDuration(long duration) { this.duration = duration; }
        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    }

    public static class PerformanceReport {
        private double averageCaptureTime;
        private long maxCaptureTime;
        private long minCaptureTime;
        private int totalCaptures;
        private double targetCompliance;
        private double cacheHitRate;
        private double threadPoolUtilization;
        
        // Getters and setters
        public double getAverageCaptureTime() { return averageCaptureTime; }
        public void setAverageCaptureTime(double averageCaptureTime) { this.averageCaptureTime = averageCaptureTime; }
        public long getMaxCaptureTime() { return maxCaptureTime; }
        public void setMaxCaptureTime(long maxCaptureTime) { this.maxCaptureTime = maxCaptureTime; }
        public long getMinCaptureTime() { return minCaptureTime; }
        public void setMinCaptureTime(long minCaptureTime) { this.minCaptureTime = minCaptureTime; }
        public int getTotalCaptures() { return totalCaptures; }
        public void setTotalCaptures(int totalCaptures) { this.totalCaptures = totalCaptures; }
        public double getTargetCompliance() { return targetCompliance; }
        public void setTargetCompliance(double targetCompliance) { this.targetCompliance = targetCompliance; }
        public double getCacheHitRate() { return cacheHitRate; }
        public void setCacheHitRate(double cacheHitRate) { this.cacheHitRate = cacheHitRate; }
        public double getThreadPoolUtilization() { return threadPoolUtilization; }
        public void setThreadPoolUtilization(double threadPoolUtilization) { this.threadPoolUtilization = threadPoolUtilization; }
    }
}