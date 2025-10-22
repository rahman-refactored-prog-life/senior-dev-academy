package com.learningportal.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Performance Monitoring Service
 * 
 * Provides comprehensive performance monitoring including:
 * - API response time tracking
 * - Database query performance monitoring
 * - Memory usage and garbage collection metrics
 * - Performance regression detection
 */
@Service
public class PerformanceMonitoringService {

    private static final Logger log = LoggerFactory.getLogger(PerformanceMonitoringService.class);

    private final MeterRegistry meterRegistry;
    private final Timer apiResponseTimer;
    private final Timer databaseQueryTimer;
    private final Counter apiRequestCounter;
    private final Counter databaseQueryCounter;
    
    // Performance baseline storage
    private final Map<String, Double> performanceBaselines = new ConcurrentHashMap<>();
    private final Map<String, Double> currentMetrics = new ConcurrentHashMap<>();

    public PerformanceMonitoringService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.apiResponseTimer = Timer.builder("api.response.time")
            .description("API response time")
            .register(meterRegistry);
        this.databaseQueryTimer = Timer.builder("database.query.time")
            .description("Database query execution time")
            .register(meterRegistry);
        this.apiRequestCounter = Counter.builder("api.requests.total")
            .description("Total API requests")
            .register(meterRegistry);
        this.databaseQueryCounter = Counter.builder("database.queries.total")
            .description("Total database queries")
            .register(meterRegistry);
        
        initializeMemoryMetrics();
        initializeGarbageCollectionMetrics();
    }

    /**
     * Record API response time
     */
    public void recordApiResponseTime(String endpoint, long responseTimeMs) {
        apiResponseTimer.record(responseTimeMs, java.util.concurrent.TimeUnit.MILLISECONDS);
        apiRequestCounter.increment();
        
        // Store current metric for regression detection
        currentMetrics.put("api.response." + endpoint, (double) responseTimeMs);
        
        log.debug("API response time recorded: {} - {}ms", endpoint, responseTimeMs);
        
        // Check for performance regression
        checkPerformanceRegression("api.response." + endpoint, responseTimeMs);
    }

    /**
     * Record database query execution time
     */
    public void recordDatabaseQueryTime(String queryType, long executionTimeMs) {
        databaseQueryTimer.record(executionTimeMs, java.util.concurrent.TimeUnit.MILLISECONDS);
        databaseQueryCounter.increment();
        
        // Store current metric for regression detection
        currentMetrics.put("db.query." + queryType, (double) executionTimeMs);
        
        log.debug("Database query time recorded: {} - {}ms", queryType, executionTimeMs);
        
        // Check for performance regression
        checkPerformanceRegression("db.query." + queryType, executionTimeMs);
    }

    /**
     * Get current performance metrics
     */
    public Map<String, Object> getCurrentPerformanceMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        
        // API metrics
        metrics.put("api.requests.total", apiRequestCounter.count());
        metrics.put("api.response.time.mean", apiResponseTimer.mean(java.util.concurrent.TimeUnit.MILLISECONDS));
        metrics.put("api.response.time.max", apiResponseTimer.max(java.util.concurrent.TimeUnit.MILLISECONDS));
        
        // Database metrics
        metrics.put("database.queries.total", databaseQueryCounter.count());
        metrics.put("database.query.time.mean", databaseQueryTimer.mean(java.util.concurrent.TimeUnit.MILLISECONDS));
        metrics.put("database.query.time.max", databaseQueryTimer.max(java.util.concurrent.TimeUnit.MILLISECONDS));
        
        // Memory metrics
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        metrics.put("memory.heap.used", memoryBean.getHeapMemoryUsage().getUsed());
        metrics.put("memory.heap.max", memoryBean.getHeapMemoryUsage().getMax());
        metrics.put("memory.heap.usage.percent", 
            (double) memoryBean.getHeapMemoryUsage().getUsed() / memoryBean.getHeapMemoryUsage().getMax() * 100);
        
        // GC metrics
        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
        long totalGcTime = gcBeans.stream().mapToLong(GarbageCollectorMXBean::getCollectionTime).sum();
        long totalGcCount = gcBeans.stream().mapToLong(GarbageCollectorMXBean::getCollectionCount).sum();
        
        metrics.put("gc.total.time", totalGcTime);
        metrics.put("gc.total.count", totalGcCount);
        
        metrics.put("timestamp", LocalDateTime.now());
        
        return metrics;
    }

    /**
     * Set performance baseline for regression detection
     */
    public void setPerformanceBaseline(String metricName, double baselineValue) {
        performanceBaselines.put(metricName, baselineValue);
        log.info("Performance baseline set: {} = {}", metricName, baselineValue);
    }

    /**
     * Check for performance regression
     */
    private void checkPerformanceRegression(String metricName, double currentValue) {
        Double baseline = performanceBaselines.get(metricName);
        if (baseline != null) {
            double regressionThreshold = baseline * 1.5; // 50% increase threshold
            
            if (currentValue > regressionThreshold) {
                log.warn("Performance regression detected for {}: current={}, baseline={}, threshold={}", 
                    metricName, currentValue, baseline, regressionThreshold);
                
                // Increment regression counter
                Counter.builder("performance.regressions")
                    .tag("metric", metricName)
                    .description("Performance regression occurrences")
                    .register(meterRegistry)
                    .increment();
            }
        }
    }

    /**
     * Initialize memory usage metrics
     */
    private void initializeMemoryMetrics() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        
        Gauge.builder("memory.heap.used", memoryBean, bean -> bean.getHeapMemoryUsage().getUsed())
            .description("Used heap memory in bytes")
            .register(meterRegistry);
        
        Gauge.builder("memory.heap.max", memoryBean, bean -> bean.getHeapMemoryUsage().getMax())
            .description("Maximum heap memory in bytes")
            .register(meterRegistry);
        
        Gauge.builder("memory.heap.usage.percent", memoryBean, bean -> 
                (double) bean.getHeapMemoryUsage().getUsed() / bean.getHeapMemoryUsage().getMax() * 100)
            .description("Heap memory usage percentage")
            .register(meterRegistry);
        
        Gauge.builder("memory.non.heap.used", memoryBean, bean -> bean.getNonHeapMemoryUsage().getUsed())
            .description("Used non-heap memory in bytes")
            .register(meterRegistry);
    }

    /**
     * Initialize garbage collection metrics
     */
    private void initializeGarbageCollectionMetrics() {
        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
        
        for (GarbageCollectorMXBean gcBean : gcBeans) {
            Gauge.builder("gc.collection.time", gcBean, GarbageCollectorMXBean::getCollectionTime)
                .tag("gc", gcBean.getName())
                .description("Garbage collection time")
                .register(meterRegistry);
            
            Gauge.builder("gc.collection.count", gcBean, GarbageCollectorMXBean::getCollectionCount)
                .tag("gc", gcBean.getName())
                .description("Garbage collection count")
                .register(meterRegistry);
        }
    }

    /**
     * Generate performance report
     */
    public PerformanceReport generatePerformanceReport() {
        Map<String, Object> metrics = getCurrentPerformanceMetrics();
        
        PerformanceReport report = new PerformanceReport();
        report.setTimestamp(LocalDateTime.now());
        report.setMetrics(metrics);
        report.setBaselines(new HashMap<>(performanceBaselines));
        report.setCurrentValues(new HashMap<>(currentMetrics));
        
        // Calculate performance scores
        report.setApiPerformanceScore(calculateApiPerformanceScore());
        report.setDatabasePerformanceScore(calculateDatabasePerformanceScore());
        report.setMemoryPerformanceScore(calculateMemoryPerformanceScore());
        
        return report;
    }

    private double calculateApiPerformanceScore() {
        double meanResponseTime = apiResponseTimer.mean(java.util.concurrent.TimeUnit.MILLISECONDS);
        // Score based on response time: 100 for <100ms, decreasing linearly
        return Math.max(0, 100 - (meanResponseTime / 10));
    }

    private double calculateDatabasePerformanceScore() {
        double meanQueryTime = databaseQueryTimer.mean(java.util.concurrent.TimeUnit.MILLISECONDS);
        // Score based on query time: 100 for <50ms, decreasing linearly
        return Math.max(0, 100 - (meanQueryTime / 5));
    }

    private double calculateMemoryPerformanceScore() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        double heapUsagePercent = (double) memoryBean.getHeapMemoryUsage().getUsed() / 
                                 memoryBean.getHeapMemoryUsage().getMax() * 100;
        // Score based on memory usage: 100 for <50%, decreasing linearly
        return Math.max(0, 100 - (heapUsagePercent * 2));
    }



    /**
     * Performance Report class
     */
    public static class PerformanceReport {
        private LocalDateTime timestamp;
        private Map<String, Object> metrics;
        private Map<String, Double> baselines;
        private Map<String, Double> currentValues;
        private double apiPerformanceScore;
        private double databasePerformanceScore;
        private double memoryPerformanceScore;

        // Getters and setters
        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
        
        public Map<String, Object> getMetrics() { return metrics; }
        public void setMetrics(Map<String, Object> metrics) { this.metrics = metrics; }
        
        public Map<String, Double> getBaselines() { return baselines; }
        public void setBaselines(Map<String, Double> baselines) { this.baselines = baselines; }
        
        public Map<String, Double> getCurrentValues() { return currentValues; }
        public void setCurrentValues(Map<String, Double> currentValues) { this.currentValues = currentValues; }
        
        public double getApiPerformanceScore() { return apiPerformanceScore; }
        public void setApiPerformanceScore(double apiPerformanceScore) { this.apiPerformanceScore = apiPerformanceScore; }
        
        public double getDatabasePerformanceScore() { return databasePerformanceScore; }
        public void setDatabasePerformanceScore(double databasePerformanceScore) { this.databasePerformanceScore = databasePerformanceScore; }
        
        public double getMemoryPerformanceScore() { return memoryPerformanceScore; }
        public void setMemoryPerformanceScore(double memoryPerformanceScore) { this.memoryPerformanceScore = memoryPerformanceScore; }
        
        public double getOverallPerformanceScore() {
            return (apiPerformanceScore + databasePerformanceScore + memoryPerformanceScore) / 3;
        }
    }
}