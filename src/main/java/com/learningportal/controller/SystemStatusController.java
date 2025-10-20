package com.learningportal.controller;

import com.learningportal.service.DataInitializationMonitor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * System Status Controller
 * 
 * Provides endpoints for monitoring system status, data initialization progress,
 * and overall application health.
 */
@RestController
@RequestMapping("/system")
@Tag(name = "System Status", description = "System monitoring and status endpoints")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SystemStatusController {
    
    private final DataInitializationMonitor initializationMonitor;
    
    public SystemStatusController(DataInitializationMonitor initializationMonitor) {
        this.initializationMonitor = initializationMonitor;
    }
    
    @Operation(
        summary = "Get system health status",
        description = "Returns overall system health including data initialization status"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "System status retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "System health check failed")
    })
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> getSystemHealth() {
        Map<String, Object> health = new HashMap<>();
        
        try {
            // Basic system info
            health.put("status", "UP");
            health.put("timestamp", java.time.LocalDateTime.now());
            health.put("javaVersion", System.getProperty("java.version"));
            health.put("springProfile", System.getProperty("spring.profiles.active", "default"));
            
            // Data initialization status
            DataInitializationMonitor.InitializationStatus initStatus = 
                initializationMonitor.getInitializationStatus();
            
            Map<String, Object> dataInit = new HashMap<>();
            dataInit.put("complete", initStatus.isInitializationComplete());
            dataInit.put("progress", initializationMonitor.getInitializationProgress());
            dataInit.put("currentPhase", initializationMonitor.getCurrentPhase());
            dataInit.put("healthy", initializationMonitor.isInitializationHealthy());
            dataInit.put("modulesCreated", initStatus.getCreatedModulesCount());
            dataInit.put("topicsCreated", initStatus.getCreatedTopicsCount());
            dataInit.put("questionsCreated", initStatus.getCreatedQuestionsCount());
            
            health.put("dataInitialization", dataInit);
            
            // Memory info
            Runtime runtime = Runtime.getRuntime();
            Map<String, Object> memory = new HashMap<>();
            memory.put("totalMemoryMB", runtime.totalMemory() / (1024 * 1024));
            memory.put("freeMemoryMB", runtime.freeMemory() / (1024 * 1024));
            memory.put("usedMemoryMB", (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024));
            memory.put("maxMemoryMB", runtime.maxMemory() / (1024 * 1024));
            
            health.put("memory", memory);
            
            return ResponseEntity.ok(health);
            
        } catch (Exception e) {
            health.put("status", "DOWN");
            health.put("error", e.getMessage());
            return ResponseEntity.status(500).body(health);
        }
    }
    
    @Operation(
        summary = "Get data initialization status",
        description = "Returns detailed status of the data initialization process"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Initialization status retrieved successfully")
    })
    @GetMapping("/initialization")
    public ResponseEntity<Map<String, Object>> getInitializationStatus() {
        DataInitializationMonitor.InitializationStatus status = 
            initializationMonitor.getInitializationStatus();
        
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", status.getTimestamp());
        response.put("phase1Complete", status.isPhase1Complete());
        response.put("phase2Complete", status.isPhase2Complete());
        response.put("initializationComplete", status.isInitializationComplete());
        response.put("progress", initializationMonitor.getInitializationProgress());
        response.put("currentPhase", initializationMonitor.getCurrentPhase());
        response.put("healthy", initializationMonitor.isInitializationHealthy());
        
        Map<String, Object> counts = new HashMap<>();
        counts.put("modules", status.getCreatedModulesCount());
        counts.put("topics", status.getCreatedTopicsCount());
        counts.put("questions", status.getCreatedQuestionsCount());
        response.put("createdCounts", counts);
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(
        summary = "Get system information",
        description = "Returns basic system information and configuration"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "System information retrieved successfully")
    })
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getSystemInfo() {
        Map<String, Object> info = new HashMap<>();
        
        info.put("applicationName", "Comprehensive Developer Portal");
        info.put("version", "1.0.0");
        info.put("description", "The most comprehensive learning portal for Java, Spring, React, and System Design");
        info.put("javaVersion", System.getProperty("java.version"));
        info.put("springProfile", System.getProperty("spring.profiles.active", "default"));
        info.put("osName", System.getProperty("os.name"));
        info.put("osVersion", System.getProperty("os.version"));
        info.put("timestamp", java.time.LocalDateTime.now());
        
        // Features
        info.put("features", java.util.Arrays.asList(
            "Java Fundamentals to Advanced",
            "Node.js Complete Mastery",
            "React Development",
            "Data Structures & Algorithms",
            "System Design",
            "Interview Preparation",
            "Interactive Code Examples",
            "Progress Tracking"
        ));
        
        return ResponseEntity.ok(info);
    }
    
    @Operation(
        summary = "Trigger initialization status logging",
        description = "Logs current initialization status to console (for debugging)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status logged successfully")
    })
    @PostMapping("/log-status")
    public ResponseEntity<Map<String, String>> logInitializationStatus() {
        initializationMonitor.logCurrentStatus();
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Initialization status logged to console");
        response.put("timestamp", java.time.LocalDateTime.now().toString());
        
        return ResponseEntity.ok(response);
    }
}