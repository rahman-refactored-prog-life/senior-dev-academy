package com.learningportal.controller;

import com.learningportal.service.PerformanceMonitoringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Performance Monitoring Controller
 * 
 * Provides endpoints for accessing performance metrics and reports
 */
@RestController
@RequestMapping("/api/performance")
@Tag(name = "Performance Monitoring", description = "Performance metrics and monitoring endpoints")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PerformanceController {

    private final PerformanceMonitoringService performanceMonitoringService;

    public PerformanceController(PerformanceMonitoringService performanceMonitoringService) {
        this.performanceMonitoringService = performanceMonitoringService;
    }

    @Operation(
        summary = "Get current performance metrics",
        description = "Retrieve current performance metrics including API response times, database query times, and memory usage"
    )
    @GetMapping("/metrics")
    public ResponseEntity<Map<String, Object>> getCurrentMetrics() {
        Map<String, Object> metrics = performanceMonitoringService.getCurrentPerformanceMetrics();
        return ResponseEntity.ok(metrics);
    }

    @Operation(
        summary = "Get performance report",
        description = "Generate comprehensive performance report with scores and analysis"
    )
    @GetMapping("/report")
    public ResponseEntity<PerformanceMonitoringService.PerformanceReport> getPerformanceReport() {
        PerformanceMonitoringService.PerformanceReport report = performanceMonitoringService.generatePerformanceReport();
        return ResponseEntity.ok(report);
    }

    @Operation(
        summary = "Set performance baseline",
        description = "Set performance baseline for regression detection"
    )
    @PostMapping("/baseline")
    public ResponseEntity<String> setPerformanceBaseline(
            @RequestParam String metricName,
            @RequestParam double baselineValue) {
        performanceMonitoringService.setPerformanceBaseline(metricName, baselineValue);
        return ResponseEntity.ok("Performance baseline set for " + metricName + ": " + baselineValue);
    }
}