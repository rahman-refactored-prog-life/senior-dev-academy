package com.learningportal.controller;

import com.learningportal.service.CacheMonitoringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Cache Management Controller
 * 
 * Provides endpoints for cache monitoring, statistics, and management
 */
@RestController
@RequestMapping("/api/cache")
@Tag(name = "Cache Management", description = "Cache monitoring and management endpoints")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CacheController {

    private final CacheMonitoringService cacheMonitoringService;

    public CacheController(CacheMonitoringService cacheMonitoringService) {
        this.cacheMonitoringService = cacheMonitoringService;
    }

    @Operation(
        summary = "Get all cache statistics",
        description = "Retrieve comprehensive statistics for all configured caches"
    )
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, CacheMonitoringService.CacheStatistics>> getAllCacheStatistics() {
        Map<String, CacheMonitoringService.CacheStatistics> statistics = 
            cacheMonitoringService.getAllCacheStatistics();
        return ResponseEntity.ok(statistics);
    }

    @Operation(
        summary = "Get cache statistics for specific cache",
        description = "Retrieve detailed statistics for a specific cache by name"
    )
    @GetMapping("/statistics/{cacheName}")
    public ResponseEntity<CacheMonitoringService.CacheStatistics> getCacheStatistics(
            @PathVariable String cacheName) {
        CacheMonitoringService.CacheStatistics statistics = 
            cacheMonitoringService.getCacheStatistics(cacheName);
        
        if (statistics != null) {
            return ResponseEntity.ok(statistics);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Get cache optimization recommendations",
        description = "Get recommendations for optimizing cache performance"
    )
    @GetMapping("/recommendations")
    public ResponseEntity<Map<String, String>> getCacheOptimizationRecommendations() {
        Map<String, String> recommendations = cacheMonitoringService.getCacheOptimizationRecommendations();
        return ResponseEntity.ok(recommendations);
    }

    @Operation(
        summary = "Clear all caches",
        description = "Clear all cached data from all caches"
    )
    @PostMapping("/clear-all")
    public ResponseEntity<String> clearAllCaches() {
        cacheMonitoringService.clearAllCaches();
        return ResponseEntity.ok("All caches cleared successfully");
    }

    @Operation(
        summary = "Clear specific cache",
        description = "Clear cached data from a specific cache by name"
    )
    @PostMapping("/clear/{cacheName}")
    public ResponseEntity<String> clearCache(@PathVariable String cacheName) {
        cacheMonitoringService.clearCache(cacheName);
        return ResponseEntity.ok("Cache '" + cacheName + "' cleared successfully");
    }
}