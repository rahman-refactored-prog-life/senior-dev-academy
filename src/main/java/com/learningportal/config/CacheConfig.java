package com.learningportal.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Advanced Cache Configuration
 * 
 * Configures multiple Caffeine caches with different TTL and size settings
 * optimized for different types of learning content and user data
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Configure multiple caches with different strategies
     */
    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        
        cacheManager.setCaches(Arrays.asList(
            // Learning modules cache - long TTL, high capacity
            buildCache("learningModules", 2000, 30, 15),
            
            // Learning statistics cache - medium TTL, medium capacity  
            buildCache("learningStats", 500, 10, 5),
            
            // User progress cache - short TTL, high capacity
            buildCache("userProgress", 1000, 5, 2),
            
            // Interview questions cache - long TTL, very high capacity
            buildCache("interviewQuestions", 5000, 60, 30),
            
            // Topic content cache - medium TTL, high capacity
            buildCache("topicContent", 3000, 20, 10),
            
            // User notes cache - short TTL, medium capacity
            buildCache("userNotes", 800, 8, 4),
            
            // Search results cache - very short TTL, medium capacity
            buildCache("searchResults", 200, 2, 1),
            
            // Database query results cache - short TTL, high capacity
            buildCache("queryResults", 1500, 5, 2)
        ));
        
        return cacheManager;
    }

    /**
     * Build a Caffeine cache with specific configuration
     */
    private CaffeineCache buildCache(String name, int maxSize, int expireAfterWriteMinutes, int expireAfterAccessMinutes) {
        Cache<Object, Object> cache = Caffeine.newBuilder()
            .maximumSize(maxSize)
            .expireAfterWrite(expireAfterWriteMinutes, TimeUnit.MINUTES)
            .expireAfterAccess(expireAfterAccessMinutes, TimeUnit.MINUTES)
            .recordStats()  // Enable statistics for monitoring
            .build();
        
        return new CaffeineCache(name, cache);
    }

    /**
     * High-frequency cache for very frequently accessed data
     */
    @Bean("highFrequencyCache")
    public Cache<String, Object> highFrequencyCache() {
        return Caffeine.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .expireAfterAccess(30, TimeUnit.SECONDS)
            .recordStats()
            .build();
    }

    /**
     * Long-term cache for static content
     */
    @Bean("longTermCache")
    public Cache<String, Object> longTermCache() {
        return Caffeine.newBuilder()
            .maximumSize(10000)
            .expireAfterWrite(2, TimeUnit.HOURS)
            .expireAfterAccess(1, TimeUnit.HOURS)
            .recordStats()
            .build();
    }
}