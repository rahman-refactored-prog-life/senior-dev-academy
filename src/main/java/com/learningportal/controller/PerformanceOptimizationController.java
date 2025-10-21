package com.learningportal.controller;

import com.learningportal.service.PerformanceOptimizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Performance Optimization Controller
 * 
 * Provides endpoints for performance optimization and monitoring
 */
@RestController
@RequestMapping("/api/performance/optimization")
@Tag(name = "Performance Optimization", description = "Performance optimization and monitoring endpoints")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PerformanceOptimizationController {

    private final PerformanceOptimizationService performanceOptimizationService;

    public PerformanceOptimizationController(PerformanceOptimizationService performanceOptimizationService) {
        this.performanceOptimizationService = performanceOptimizationService;
    }

    @Operation(
        summary = "Get database connection pool status",
        description = "Retrieve current database connection pool status and metrics"
    )
    @GetMapping("/database/pool-status")
    public ResponseEntity<Map<String, Object>> getDatabasePoolStatus() {
        Map<String, Object> status = performanceOptimizationService.getDatabaseConnectionPoolStatus();
        return ResponseEntity.ok(status);
    }

    @Operation(
        summary = "Get cache performance statistics",
        description = "Retrieve cache hit rates, miss rates, and other performance statistics"
    )
    @GetMapping("/cache/stats")
    public ResponseEntity<Map<String, Object>> getCacheStats() {
        Map<String, Object> stats = performanceOptimizationService.getCachePerformanceStats();
        return ResponseEntity.ok(stats);
    }

    @Operation(
        summary = "Get performance recommendations",
        description = "Get automated performance optimization recommendations"
    )
    @GetMapping("/recommendations")
    public ResponseEntity<Map<String, Object>> getPerformanceRecommendations() {
        Map<String, Object> recommendations = performanceOptimizationService.getPerformanceRecommendations();
        return ResponseEntity.ok(recommendations);
    }

    @Operation(
        summary = "Clear all caches",
        description = "Clear all application caches for performance testing"
    )
    @PostMapping("/cache/clear")
    public ResponseEntity<String> clearAllCaches() {
        performanceOptimizationService.clearAllCaches();
        return ResponseEntity.ok("All caches cleared successfully");
    }

    @Operation(
        summary = "Warm up caches",
        description = "Pre-load frequently accessed data into caches"
    )
    @PostMapping("/cache/warmup")
    public ResponseEntity<String> warmUpCaches() {
        performanceOptimizationService.warmUpCaches();
        return ResponseEntity.ok("Cache warm-up completed successfully");
    }

    @Operation(
        summary = "Get comprehensive performance status",
        description = "Get complete performance status including database, cache, and recommendations"
    )
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getComprehensiveStatus() {
        Map<String, Object> status = performanceOptimizationService.getComprehensivePerformanceStatus();
        return ResponseEntity.ok(status);
    }
}