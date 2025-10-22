package com.learningportal.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Service for handling rate limiting logic
 */
@Service
public class RateLimitingService {

    private final RedisTemplate<String, String> redisTemplate;

    public RateLimitingService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isAllowed(String key, int maxRequests, Duration window) {
        String redisKey = "rate_limit:" + key;
        
        // Get current count
        String currentCountStr = redisTemplate.opsForValue().get(redisKey);
        int currentCount = currentCountStr != null ? Integer.parseInt(currentCountStr) : 0;
        
        if (currentCount >= maxRequests) {
            return false;
        }
        
        // Increment counter
        if (currentCount == 0) {
            // First request in window - set with expiration
            redisTemplate.opsForValue().set(redisKey, "1", window.getSeconds(), TimeUnit.SECONDS);
        } else {
            // Increment existing counter
            redisTemplate.opsForValue().increment(redisKey);
        }
        
        return true;
    }

    public int getRemainingRequests(String key, int maxRequests) {
        String redisKey = "rate_limit:" + key;
        String currentCountStr = redisTemplate.opsForValue().get(redisKey);
        int currentCount = currentCountStr != null ? Integer.parseInt(currentCountStr) : 0;
        return Math.max(0, maxRequests - currentCount);
    }

    public long getResetTime(String key) {
        String redisKey = "rate_limit:" + key;
        Long ttl = redisTemplate.getExpire(redisKey, TimeUnit.SECONDS);
        return ttl != null ? System.currentTimeMillis() + (ttl * 1000) : 0;
    }
}