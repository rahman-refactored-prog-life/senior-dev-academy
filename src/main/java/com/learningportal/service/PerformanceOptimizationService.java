package com.learningportal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Performance Optimization Service
 * 
 * Provides performance monitoring and optimization mechanisms including:
 * - Database connection pool monitoring
 * - Cache performance tracking
 * - Query optimization recommendations
 */
@Service
public class PerformanceOptimizationService {

    private static final Logger log = LoggerFactory.getLogger(PerformanceOptimizationService.class);

    private final DataSource dataSource;
    private final CacheManager cacheManager;

    public PerformanceOptimizationService(DataSource dataSource, CacheManager cacheManager) {
        this.dataSource = dataSource;
        this.cacheManager = cacheManager;
    }

    /**
     * Get database connection pool status
     */
    public Map<String, Object> getDatabaseConnectionPoolStatus() {
        Map<String, Object> status = new HashMap<>();
        
        try {
            // Test connection
            try (Connection connection = dataSource.getConnection()) {
                status.put("connectionAvailable", true);
                status.put("connectionValid", connection.isValid(5));
                status.put("autoCommit", connection.getAutoCommit());
                status.put("readOnly", connection.isReadOnly());
                status.put("transactionIsolation", connection.getTransactionIsolation());
            }
            
            // HikariCP specific metrics (if available)
            if (dataSource instanceof com.zaxxer.hikari.HikariDataSource) {
                com.zaxxer.hikari.HikariDataSource hikariDS = (com.zaxxer.hikari.HikariDataSource) dataSource;
                com.zaxxer.hikari.HikariPoolMXBean poolBean = hikariDS.getHikariPoolMXBean();
                
                if (poolBean != null) {
                    status.put("activeConnections", poolBean.getActiveConnections());
                    status.put("idleConnections", poolBean.getIdleConnections());
                    status.put("totalConnections", poolBean.getTotalConnections());
                    status.put("threadsAwaitingConnection", poolBean.getThreadsAwaitingConnection());
                    status.put("maximumPoolSize", hikariDS.getMaximumPoolSize());
                    status.put("minimumIdle", hikariDS.getMinimumIdle());
                }
            }
            
        } catch (SQLException e) {
            log.error("Error getting database connection pool status", e);
            status.put("connectionAvailable", false);
            status.put("error", e.getMessage());
        }
        
        return status;
    }

    /**
     * Get cache performance statistics
     */
    public Map<String, Object> getCachePerformanceStats() {
        Map<String, Object> stats = new HashMap<>();
        
        // Get cache names and their statistics
        for (String cacheName : cacheManager.getCacheNames()) {
            org.springframework.cache.Cache cache = cacheManager.getCache(cacheName);
            if (cache != null) {
                Map<String, Object> cacheStats = new HashMap<>();
                
                // Try to get Caffeine cache statistics
                if (cache.getNativeCache() instanceof com.github.benmanes.caffeine.cache.Cache) {
                    com.github.benmanes.caffeine.cache.Cache<?, ?> caffeineCache = 
                        (com.github.benmanes.caffeine.cache.Cache<?, ?>) cache.getNativeCache();
                    
                    com.github.benmanes.caffeine.cache.stats.CacheStats cacheStatsObj = caffeineCache.stats();
                    
                    cacheStats.put("hitCount", cacheStatsObj.hitCount());
                    cacheStats.put("missCount", cacheStatsObj.missCount());
                    cacheStats.put("hitRate", cacheStatsObj.hitRate());
                    cacheStats.put("missRate", cacheStatsObj.missRate());
                    cacheStats.put("loadCount", cacheStatsObj.loadCount());
                    cacheStats.put("evictionCount", cacheStatsObj.evictionCount());
                    cacheStats.put("averageLoadTime", cacheStatsObj.averageLoadPenalty());
                    cacheStats.put("estimatedSize", caffeineCache.estimatedSize());
                }
                
                stats.put(cacheName, cacheStats);
            }
        }
        
        return stats;
    }

    /**
     * Get performance optimization recommendations
     */
    public Map<String, Object> getPerformanceRecommendations() {
        Map<String, Object> recommendations = new HashMap<>();
        
        // Database recommendations
        Map<String, Object> dbStatus = getDatabaseConnectionPoolStatus();
        if (dbStatus.containsKey("activeConnections") && dbStatus.containsKey("maximumPoolSize")) {
            int active = (Integer) dbStatus.get("activeConnections");
            int max = (Integer) dbStatus.get("maximumPoolSize");
            
            if (active > max * 0.8) {
                recommendations.put("database", "Consider increasing maximum pool size. Current usage: " + active + "/" + max);
            }
        }
        
        // Cache recommendations
        Map<String, Object> cacheStats = getCachePerformanceStats();
        for (Map.Entry<String, Object> entry : cacheStats.entrySet()) {
            if (entry.getValue() instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> stats = (Map<String, Object>) entry.getValue();
                
                if (stats.containsKey("hitRate")) {
                    double hitRate = (Double) stats.get("hitRate");
                    if (hitRate < 0.7) {
                        recommendations.put("cache_" + entry.getKey(), 
                            "Low cache hit rate (" + String.format("%.2f", hitRate * 100) + "%). Consider increasing cache size or TTL.");
                    }
                }
            }
        }
        
        return recommendations;
    }

    /**
     * Clear all caches (for performance testing)
     */
    public void clearAllCaches() {
        log.info("Clearing all caches for performance testing");
        
        for (String cacheName : cacheManager.getCacheNames()) {
            org.springframework.cache.Cache cache = cacheManager.getCache(cacheName);
            if (cache != null) {
                cache.clear();
                log.debug("Cleared cache: {}", cacheName);
            }
        }
    }

    /**
     * Warm up caches with frequently accessed data
     */
    public void warmUpCaches() {
        log.info("Warming up caches with frequently accessed data");
        
        // This would typically pre-load frequently accessed data
        // Implementation depends on specific business logic
        
        log.info("Cache warm-up completed");
    }

    /**
     * Get comprehensive performance status
     */
    public Map<String, Object> getComprehensivePerformanceStatus() {
        Map<String, Object> status = new HashMap<>();
        
        status.put("database", getDatabaseConnectionPoolStatus());
        status.put("cache", getCachePerformanceStats());
        status.put("recommendations", getPerformanceRecommendations());
        status.put("timestamp", java.time.LocalDateTime.now());
        
        return status;
    }
}