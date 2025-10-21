package com.learningportal.controller;

import com.learningportal.service.DatabasePerformanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Database Performance Controller
 * 
 * Provides endpoints for database performance monitoring and optimization
 */
@RestController
@RequestMapping("/api/database/performance")
@Tag(name = "Database Performance", description = "Database performance monitoring and optimization endpoints")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DatabasePerformanceController {

    private final DatabasePerformanceService databasePerformanceService;

    public DatabasePerformanceController(DatabasePerformanceService databasePerformanceService) {
        this.databasePerformanceService = databasePerformanceService;
    }

    @Operation(
        summary = "Get database performance metrics",
        description = "Retrieve comprehensive database performance metrics including connection pool, query statistics, and optimization recommendations"
    )
    @GetMapping("/metrics")
    public ResponseEntity<DatabasePerformanceService.DatabasePerformanceMetrics> getDatabasePerformanceMetrics() {
        DatabasePerformanceService.DatabasePerformanceMetrics metrics = 
            databasePerformanceService.getDatabasePerformanceMetrics();
        return ResponseEntity.ok(metrics);
    }
}