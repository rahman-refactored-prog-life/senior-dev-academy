package com.learningportal.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Advanced Visualization Service for Interactive Learning
 * 
 * Provides real-time algorithm visualizations, interactive simulations,
 * and dynamic content generation for enhanced learning experiences.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class VisualizationService {
    
    /**
     * Generate interactive algorithm visualization data
     */
    public Map<String, Object> generateAlgorithmVisualization(String algorithmType, Object[] inputData) {
        log.info("Generating visualization for algorithm: {}", algorithmType);
        
        Map<String, Object> visualization = new HashMap<>();
        
        switch (algorithmType.toUpperCase()) {
            case "BUBBLE_SORT":
                return generateBubbleSortVisualization(inputData);
            case "QUICK_SORT":
                return generateGenericVisualization("Quick Sort", inputData);
            case "MERGE_SORT":
                return generateGenericVisualization("Merge Sort", inputData);
            case "BINARY_SEARCH":
                return generateBinarySearchVisualization(inputData);
            case "DFS":
                return generateGenericVisualization("Depth-First Search", inputData);
            case "BFS":
                return generateGenericVisualization("Breadth-First Search", inputData);
            case "DIJKSTRA":
                return generateGenericVisualization("Dijkstra's Algorithm", inputData);
            case "DYNAMIC_PROGRAMMING":
                return generateDPVisualization(inputData);
            default:
                return generateGenericVisualization(algorithmType, inputData);
        }
    }
    
    /**
     * Generate Bubble Sort step-by-step visualization
     */
    private Map<String, Object> generateBubbleSortVisualization(Object[] inputData) {
        List<Map<String, Object>> steps = new ArrayList<>();
        int[] arr = Arrays.stream(inputData).mapToInt(x -> (Integer) x).toArray();
        int[] original = arr.clone();
        
        int n = arr.length;
        int comparisons = 0;
        int swaps = 0;
        
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                comparisons++;
                
                // Create step for comparison
                Map<String, Object> step = new HashMap<>();
                step.put("type", "comparison");
                step.put("array", arr.clone());
                step.put("comparing", Arrays.asList(j, j + 1));
                step.put("comparisons", comparisons);
                step.put("swaps", swaps);
                step.put("description", String.format("Comparing elements at positions %d and %d: %d vs %d", 
                    j, j + 1, arr[j], arr[j + 1]));
                steps.add(step);
                
                if (arr[j] > arr[j + 1]) {
                    // Swap elements
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swaps++;
                    
                    // Create step for swap
                    Map<String, Object> swapStep = new HashMap<>();
                    swapStep.put("type", "swap");
                    swapStep.put("array", arr.clone());
                    swapStep.put("swapped", Arrays.asList(j, j + 1));
                    swapStep.put("comparisons", comparisons);
                    swapStep.put("swaps", swaps);
                    swapStep.put("description", String.format("Swapped elements: %d and %d", arr[j + 1], arr[j]));
                    steps.add(swapStep);
                }
            }
            
            // Mark element as sorted
            Map<String, Object> sortedStep = new HashMap<>();
            sortedStep.put("type", "sorted");
            sortedStep.put("array", arr.clone());
            sortedStep.put("sortedPosition", n - i - 1);
            sortedStep.put("description", String.format("Element %d is now in its final position", arr[n - i - 1]));
            steps.add(sortedStep);
        }
        
        return Map.of(
            "algorithm", "Bubble Sort",
            "originalArray", original,
            "sortedArray", arr,
            "steps", steps,
            "totalComparisons", comparisons,
            "totalSwaps", swaps,
            "timeComplexity", "O(n²)",
            "spaceComplexity", "O(1)",
            "description", "Bubble Sort repeatedly steps through the list, compares adjacent elements and swaps them if they are in the wrong order."
        );
    }
    
    /**
     * Generate Binary Search visualization
     */
    private Map<String, Object> generateBinarySearchVisualization(Object[] inputData) {
        int[] arr = Arrays.stream(Arrays.copyOf(inputData, inputData.length - 1))
                          .mapToInt(x -> (Integer) x).toArray();
        int target = (Integer) inputData[inputData.length - 1];
        
        List<Map<String, Object>> steps = new ArrayList<>();
        int left = 0;
        int right = arr.length - 1;
        int iterations = 0;
        
        while (left <= right) {
            iterations++;
            int mid = left + (right - left) / 2;
            
            Map<String, Object> step = new HashMap<>();
            step.put("type", "search");
            step.put("array", arr);
            step.put("left", left);
            step.put("right", right);
            step.put("mid", mid);
            step.put("target", target);
            step.put("midValue", arr[mid]);
            step.put("iteration", iterations);
            
            if (arr[mid] == target) {
                step.put("found", true);
                step.put("description", String.format("Found target %d at position %d", target, mid));
                steps.add(step);
                break;
            } else if (arr[mid] < target) {
                step.put("description", String.format("Target %d > %d, search right half", target, arr[mid]));
                left = mid + 1;
            } else {
                step.put("description", String.format("Target %d < %d, search left half", target, arr[mid]));
                right = mid - 1;
            }
            
            steps.add(step);
        }
        
        if (left > right) {
            Map<String, Object> notFoundStep = new HashMap<>();
            notFoundStep.put("type", "not_found");
            notFoundStep.put("description", String.format("Target %d not found in array", target));
            steps.add(notFoundStep);
        }
        
        return Map.of(
            "algorithm", "Binary Search",
            "array", arr,
            "target", target,
            "steps", steps,
            "totalIterations", iterations,
            "timeComplexity", "O(log n)",
            "spaceComplexity", "O(1)",
            "description", "Binary Search finds a target value in a sorted array by repeatedly dividing the search interval in half."
        );
    }
    
    /**
     * Generate Dynamic Programming visualization (Fibonacci example)
     */
    private Map<String, Object> generateDPVisualization(Object[] inputData) {
        int n = (Integer) inputData[0];
        List<Map<String, Object>> steps = new ArrayList<>();
        Map<Integer, Long> memo = new HashMap<>();
        
        // Generate DP table visualization
        long result = fibonacciWithVisualization(n, memo, steps);
        
        return Map.of(
            "algorithm", "Dynamic Programming - Fibonacci",
            "input", n,
            "result", result,
            "steps", steps,
            "memoTable", memo,
            "timeComplexity", "O(n)",
            "spaceComplexity", "O(n)",
            "description", "Dynamic Programming solves complex problems by breaking them down into simpler subproblems and storing results to avoid redundant calculations."
        );
    }
    
    private long fibonacciWithVisualization(int n, Map<Integer, Long> memo, List<Map<String, Object>> steps) {
        if (n <= 1) {
            Map<String, Object> step = new HashMap<>();
            step.put("type", "base_case");
            step.put("n", n);
            step.put("result", (long) n);
            step.put("description", String.format("Base case: F(%d) = %d", n, n));
            steps.add(step);
            return n;
        }
        
        if (memo.containsKey(n)) {
            Map<String, Object> step = new HashMap<>();
            step.put("type", "memo_hit");
            step.put("n", n);
            step.put("result", memo.get(n));
            step.put("description", String.format("Memoized result: F(%d) = %d", n, memo.get(n)));
            steps.add(step);
            return memo.get(n);
        }
        
        Map<String, Object> computeStep = new HashMap<>();
        computeStep.put("type", "compute");
        computeStep.put("n", n);
        computeStep.put("description", String.format("Computing F(%d) = F(%d) + F(%d)", n, n-1, n-2));
        steps.add(computeStep);
        
        long result = fibonacciWithVisualization(n - 1, memo, steps) + 
                     fibonacciWithVisualization(n - 2, memo, steps);
        
        memo.put(n, result);
        
        Map<String, Object> memoStep = new HashMap<>();
        memoStep.put("type", "memoize");
        memoStep.put("n", n);
        memoStep.put("result", result);
        memoStep.put("description", String.format("Memoizing: F(%d) = %d", n, result));
        steps.add(memoStep);
        
        return result;
    }
    
    /**
     * Generate system architecture visualization
     */
    public Map<String, Object> generateSystemArchitectureVisualization(String systemType) {
        Map<String, Object> architecture = new HashMap<>();
        
        switch (systemType.toUpperCase()) {
            case "MICROSERVICES":
                return generateMicroservicesArchitecture();
            case "MONOLITH":
                return generateMonolithArchitecture();
            case "SERVERLESS":
                return generateServerlessArchitecture();
            case "EVENT_DRIVEN":
                return generateEventDrivenArchitecture();
            default:
                return generateGenericArchitecture(systemType);
        }
    }
    
    private Map<String, Object> generateMicroservicesArchitecture() {
        return Map.of(
            "type", "Microservices Architecture",
            "components", Arrays.asList(
                Map.of("name", "API Gateway", "type", "gateway", "connections", Arrays.asList("User Service", "Order Service", "Payment Service")),
                Map.of("name", "User Service", "type", "service", "database", "User DB"),
                Map.of("name", "Order Service", "type", "service", "database", "Order DB"),
                Map.of("name", "Payment Service", "type", "service", "database", "Payment DB"),
                Map.of("name", "Message Queue", "type", "messaging", "connections", Arrays.asList("Order Service", "Payment Service"))
            ),
            "benefits", Arrays.asList("Scalability", "Technology Diversity", "Fault Isolation", "Team Independence"),
            "challenges", Arrays.asList("Complexity", "Network Latency", "Data Consistency", "Monitoring"),
            "description", "Microservices architecture decomposes applications into small, independent services that communicate over well-defined APIs."
        );
    }
    
    /**
     * Generate real-time collaborative coding session data
     */
    public CompletableFuture<Map<String, Object>> generateCollaborativeCodingSession(String sessionId, List<String> participants) {
        return CompletableFuture.supplyAsync(() -> {
            Map<String, Object> session = new HashMap<>();
            session.put("sessionId", sessionId);
            session.put("participants", participants);
            session.put("sharedCode", "// Collaborative coding session started\n// Participants: " + String.join(", ", participants));
            session.put("cursor", Map.of("line", 3, "column", 0));
            session.put("activeUser", participants.get(0));
            session.put("timestamp", System.currentTimeMillis());
            
            return session;
        });
    }
    
    /**
     * Generate generic visualization for unknown algorithm types
     */
    private Map<String, Object> generateGenericVisualization(String algorithmType, Object[] inputData) {
        return Map.of(
            "algorithm", algorithmType,
            "input", inputData,
            "message", "Visualization not yet implemented for " + algorithmType,
            "suggestion", "This algorithm visualization is coming soon! Check back for updates."
        );
    }
    
    private Map<String, Object> generateMonolithArchitecture() {
        return Map.of(
            "type", "Monolithic Architecture",
            "components", Arrays.asList(
                Map.of("name", "Web Layer", "type", "presentation", "connections", Arrays.asList("Business Layer")),
                Map.of("name", "Business Layer", "type", "business", "connections", Arrays.asList("Data Layer")),
                Map.of("name", "Data Layer", "type", "data", "database", "Single Database")
            ),
            "benefits", Arrays.asList("Simple Deployment", "Easy Testing", "Straightforward Development", "Performance"),
            "challenges", Arrays.asList("Scalability Limits", "Technology Lock-in", "Large Codebase", "Single Point of Failure"),
            "description", "Monolithic architecture deploys the entire application as a single unit with all components tightly coupled."
        );
    }
    
    private Map<String, Object> generateServerlessArchitecture() {
        return Map.of(
            "type", "Serverless Architecture",
            "components", Arrays.asList(
                Map.of("name", "API Gateway", "type", "gateway", "connections", Arrays.asList("Lambda Functions")),
                Map.of("name", "Lambda Functions", "type", "compute", "connections", Arrays.asList("DynamoDB", "S3")),
                Map.of("name", "DynamoDB", "type", "database", "serverless", true),
                Map.of("name", "S3", "type", "storage", "serverless", true),
                Map.of("name", "CloudWatch", "type", "monitoring", "connections", Arrays.asList("Lambda Functions"))
            ),
            "benefits", Arrays.asList("Auto-scaling", "Pay-per-use", "No Server Management", "High Availability"),
            "challenges", Arrays.asList("Cold Starts", "Vendor Lock-in", "Debugging Complexity", "Timeout Limits"),
            "description", "Serverless architecture runs code in stateless compute containers managed by cloud providers."
        );
    }
    
    private Map<String, Object> generateEventDrivenArchitecture() {
        return Map.of(
            "type", "Event-Driven Architecture",
            "components", Arrays.asList(
                Map.of("name", "Event Producer", "type", "producer", "connections", Arrays.asList("Event Bus")),
                Map.of("name", "Event Bus", "type", "messaging", "connections", Arrays.asList("Event Consumer 1", "Event Consumer 2")),
                Map.of("name", "Event Consumer 1", "type", "consumer", "database", "Consumer DB 1"),
                Map.of("name", "Event Consumer 2", "type", "consumer", "database", "Consumer DB 2"),
                Map.of("name", "Event Store", "type", "storage", "connections", Arrays.asList("Event Bus"))
            ),
            "benefits", Arrays.asList("Loose Coupling", "Scalability", "Flexibility", "Real-time Processing"),
            "challenges", Arrays.asList("Event Ordering", "Duplicate Events", "Debugging", "Eventual Consistency"),
            "description", "Event-driven architecture uses events to trigger and communicate between decoupled services."
        );
    }
    
    private Map<String, Object> generateGenericArchitecture(String systemType) {
        return Map.of(
            "type", systemType,
            "message", "Architecture visualization not yet implemented for " + systemType,
            "suggestion", "This architecture pattern visualization is coming soon!"
        );
    }
}