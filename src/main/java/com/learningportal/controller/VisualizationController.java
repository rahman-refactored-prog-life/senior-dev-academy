package com.learningportal.controller;

import com.learningportal.service.VisualizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * REST Controller for Interactive Learning Visualizations
 * 
 * Provides endpoints for algorithm visualizations, system architecture diagrams,
 * and interactive learning content generation.
 */
@RestController
@RequestMapping("/api/visualizations")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VisualizationController {
    
    private final VisualizationService visualizationService;
    
    /**
     * Generate algorithm visualization
     */
    @PostMapping("/algorithm/{algorithmType}")
    public ResponseEntity<Map<String, Object>> generateAlgorithmVisualization(
            @PathVariable String algorithmType,
            @RequestBody Object[] inputData) {
        
        Map<String, Object> visualization = visualizationService.generateAlgorithmVisualization(algorithmType, inputData);
        return ResponseEntity.ok(visualization);
    }
    
    /**
     * Generate system architecture visualization
     */
    @GetMapping("/architecture/{systemType}")
    public ResponseEntity<Map<String, Object>> generateSystemArchitecture(@PathVariable String systemType) {
        Map<String, Object> architecture = visualizationService.generateSystemArchitectureVisualization(systemType);
        return ResponseEntity.ok(architecture);
    }
    
    /**
     * Create collaborative coding session
     */
    @PostMapping("/collaborative-session")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> createCollaborativeSession(
            @RequestParam String sessionId,
            @RequestBody List<String> participants) {
        
        return visualizationService.generateCollaborativeCodingSession(sessionId, participants)
                .thenApply(ResponseEntity::ok);
    }
    
    /**
     * Get available algorithm visualizations
     */
    @GetMapping("/algorithms")
    public ResponseEntity<Map<String, Object>> getAvailableAlgorithms() {
        Map<String, Object> algorithms = Map.of(
            "sorting", List.of("bubble_sort", "quick_sort", "merge_sort", "heap_sort", "insertion_sort"),
            "searching", List.of("binary_search", "linear_search", "depth_first_search", "breadth_first_search"),
            "graph", List.of("dijkstra", "bellman_ford", "floyd_warshall", "kruskal", "prim"),
            "dynamic_programming", List.of("fibonacci", "knapsack", "longest_common_subsequence", "edit_distance"),
            "tree", List.of("binary_tree_traversal", "avl_tree", "red_black_tree", "b_tree"),
            "description", "Interactive visualizations available for comprehensive algorithm learning"
        );
        
        return ResponseEntity.ok(algorithms);
    }
    
    /**
     * Get available system architecture patterns
     */
    @GetMapping("/architectures")
    public ResponseEntity<Map<String, Object>> getAvailableArchitectures() {
        Map<String, Object> architectures = Map.of(
            "patterns", List.of("microservices", "monolith", "serverless", "event_driven", "layered", "hexagonal"),
            "cloud", List.of("aws_three_tier", "azure_microservices", "gcp_serverless", "multi_cloud"),
            "distributed", List.of("cqrs", "event_sourcing", "saga_pattern", "circuit_breaker"),
            "description", "Interactive system architecture visualizations for senior developer preparation"
        );
        
        return ResponseEntity.ok(architectures);
    }
}