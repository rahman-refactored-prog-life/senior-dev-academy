package com.learningportal.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Cache Monitoring Service
 * 
 * Provides cache performance monitoring, statistics, and optimization recommendations
 */
@Service
public class CacheMonitoringService {

    private final CacheManager cacheManager;

    public CacheMonitoringService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /**
     * Get comprehensive cache statistics
     */
    public Map<String, CacheStatistics> getAllCacheStatistics() {
        Map<String, CacheStatistics> statistics = new HashMap<>();
        
        cacheManager.getCacheNames().forEach(cacheName -> {
            org.springframework.cache.Cache springCache = cacheManager.getCache(cacheName);
            if (springCache instanceof CaffeineCache) {
                CaffeineCache caffeineCache = (CaffeineCache) springCache;
                Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();
                CacheStats stats = nativeCache.stats();
                
                CacheStatistics cacheStats = new CacheStatistics();
                cacheStats.setCacheName(cacheName);
                cacheStats.setHitCount(stats.hitCount());
                cacheStats.setMissCount(stats.missCount());
                cacheStats.setHitRate(stats.hitRate());
                cacheStats.setMissRate(stats.missRate());
                cacheStats.setEvictionCount(stats.evictionCount());
                cacheStats.setLoadCount(stats.loadCount());
                cacheStats.setAverageLoadTime(stats.averageLoadPenalty());
                cacheStats.setEstimatedSize(nativeCache.estimatedSize());
                
                // Calculate efficiency score
                cacheStats.setEfficiencyScore(calculateEfficiencyScore(stats));
                
                statistics.put(cacheName, cacheStats);
            }
        });
        
        return statistics;
    }

    /**
     * Get cache statistics for a specific cache
     */
    public CacheStatistics getCacheStatistics(String cacheName) {
        org.springframework.cache.Cache springCache = cacheManager.getCache(cacheName);
        if (springCache instanceof CaffeineCache) {
            CaffeineCache caffeineCache = (CaffeineCache) springCache;
            Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();
            CacheStats stats = nativeCache.stats();
            
            CacheStatistics cacheStats = new CacheStatistics();
            cacheStats.setCacheName(cacheName);
            cacheStats.setHitCount(stats.hitCount());
            cacheStats.setMissCount(stats.missCount());
            cacheStats.setHitRate(stats.hitRate());
            cacheStats.setMissRate(stats.missRate());
            cacheStats.setEvictionCount(stats.evictionCount());
            cacheStats.setLoadCount(stats.loadCount());
            cacheStats.setAverageLoadTime(stats.averageLoadPenalty());
            cacheStats.setEstimatedSize(nativeCache.estimatedSize());
            cacheStats.setEfficiencyScore(calculateEfficiencyScore(stats));
            
            return cacheStats;
        }
        
        return null;
    }

    /**
     * Clear all caches
     */
    public void clearAllCaches() {
        cacheManager.getCacheNames().forEach(cacheName -> {
            org.springframework.cache.Cache cache = cacheManager.getCache(cacheName);
            if (cache != null) {
                cache.clear();
            }
        });
    }

    /**
     * Clear specific cache
     */
    public void clearCache(String cacheName) {
        org.springframework.cache.Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        }
    }

    /**
     * Get cache optimization recommendations
     */
    public Map<String, String> getCacheOptimizationRecommendations() {
        Map<String, String> recommendations = new HashMap<>();
        Map<String, CacheStatistics> statistics = getAllCacheStatistics();
        
        statistics.forEach((cacheName, stats) -> {
            StringBuilder recommendation = new StringBuilder();
            
            // Low hit rate recommendations
            if (stats.getHitRate() < 0.5) {
                recommendation.append("Low hit rate (").append(String.format("%.2f", stats.getHitRate() * 100))
                    .append("%). Consider increasing TTL or cache size. ");
            }
            
            // High eviction rate recommendations
            if (stats.getEvictionCount() > stats.getHitCount() * 0.1) {
                recommendation.append("High eviction rate. Consider increasing cache size. ");
            }
            
            // Performance recommendations
            if (stats.getAverageLoadTime() > 1000000) { // 1ms in nanoseconds
                recommendation.append("Slow cache loading. Consider optimizing data retrieval. ");
            }
            
            // Efficiency recommendations
            if (stats.getEfficiencyScore() < 70) {
                recommendation.append("Low efficiency score. Review cache strategy. ");
            }
            
            if (recommendation.length() == 0) {
                recommendation.append("Cache performing well. No immediate optimizations needed.");
            }
            
            recommendations.put(cacheName, recommendation.toString());
        });
        
        return recommendations;
    }

    /**
     * Calculate cache efficiency score (0-100)
     */
    private double calculateEfficiencyScore(CacheStats stats) {
        double hitRateScore = stats.hitRate() * 50; // 50 points for hit rate
        double loadTimeScore = Math.max(0, 30 - (stats.averageLoadPenalty() / 1000000)); // 30 points for load time
        double evictionScore = Math.max(0, 20 - (stats.evictionCount() * 20.0 / Math.max(1, stats.requestCount()))); // 20 points for low evictions
        
        return Math.min(100, hitRateScore + loadTimeScore + evictionScore);
    }

    /**
     * Cache Statistics class
     */
    public static class CacheStatistics {
        private String cacheName;
        private long hitCount;
        private long missCount;
        private double hitRate;
        private double missRate;
        private long evictionCount;
        private long loadCount;
        private double averageLoadTime;
        private long estimatedSize;
        private double efficiencyScore;

        // Getters and setters
        public String getCacheName() { return cacheName; }
        public void setCacheName(String cacheName) { this.cacheName = cacheName; }
        
        public long getHitCount() { return hitCount; }
        public void setHitCount(long hitCount) { this.hitCount = hitCount; }
        
        public long getMissCount() { return missCount; }
        public void setMissCount(long missCount) { this.missCount = missCount; }
        
        public double getHitRate() { return hitRate; }
        public void setHitRate(double hitRate) { this.hitRate = hitRate; }
        
        public double getMissRate() { return missRate; }
        public void setMissRate(double missRate) { this.missRate = missRate; }
        
        public long getEvictionCount() { return evictionCount; }
        public void setEvictionCount(long evictionCount) { this.evictionCount = evictionCount; }
        
        public long getLoadCount() { return loadCount; }
        public void setLoadCount(long loadCount) { this.loadCount = loadCount; }
        
        public double getAverageLoadTime() { return averageLoadTime; }
        public void setAverageLoadTime(double averageLoadTime) { this.averageLoadTime = averageLoadTime; }
        
        public long getEstimatedSize() { return estimatedSize; }
        public void setEstimatedSize(long estimatedSize) { this.estimatedSize = estimatedSize; }
        
        public double getEfficiencyScore() { return efficiencyScore; }
        public void setEfficiencyScore(double efficiencyScore) { this.efficiencyScore = efficiencyScore; }
        
        public long getTotalRequests() { return hitCount + missCount; }
    }
}