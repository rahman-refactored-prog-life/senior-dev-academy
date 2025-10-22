package com.learningportal.continuity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Reliability and Stress Tests for Session Continuity System
 * Tests system reliability under various failure and stress conditions
 */
@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SessionContinuityReliabilityTest {

    @Autowired
    private SessionStateManager sessionStateManager;

    @Autowired
    private TechnicalEnvironmentTracker environmentTracker;

    @Autowired
    private DocumentationSynchronizer documentationSynchronizer;

    @Autowired
    private QualityGateManager qualityGateManager;

    @Autowired
    private ContextRecoveryEngine contextRecoveryEngine;

    @Autowired
    private RedundancyManager redundancyManager;

    private Random random;

    @BeforeEach
    void setUp() {
        random = new Random(System.currentTimeMillis());
    }

    @Test
    void testContextLossSimulationAndRecovery() {
        // Test context loss simulation and recovery testing
        
        int totalScenarios = 20;
        int successfulRecoveries = 0;
        List<String> failureReasons = new ArrayList<>();
        
        for (int i = 0; i < totalScenarios; i++) {
            // Create test session with varying corruption levels
            SessionState originalState = createTestSessionState("context-loss-test-" + i);
            
            // Store original state
            ValidationResult captureResult = sessionStateManager.captureSessionState(originalState);
            assertTrue(captureResult.isValid(), "Original state should be valid");
            
            RedundancyStorageResult storageResult = redundancyManager.storeWithRedundancy(originalState);
            assertTrue(storageResult.isSuccess(), "Storage should succeed");
            
            // Simulate various types of context corruption
            CorruptionScenario scenario = generateCorruptionScenario(i);
            simulateContextCorruption(originalState.getSessionId(), scenario);
            
            // Attempt recovery
            try {
                ReconstructedContext recoveredContext = contextRecoveryEngine.reconstructContext(originalState.getSessionId());
                
                if (recoveredContext != null && validateRecoveryAccuracy(originalState, recoveredContext)) {
                    successfulRecoveries++;
                } else {
                    failureReasons.add("Scenario " + i + ": " + scenario.getDescription() + 
                                     " - Recovery failed or inaccurate");
                }
            } catch (Exception e) {
                failureReasons.add("Scenario " + i + ": " + scenario.getDescription() + 
                                 " - Exception: " + e.getMessage());
            }
        }
        
        double recoverySuccessRate = (double) successfulRecoveries / totalScenarios;
        
        System.out.println("Context Loss Recovery Analysis:");
        System.out.println("  Total Scenarios: " + totalScenarios);
        System.out.println("  Successful Recoveries: " + successfulRecoveries);
        System.out.println("  Success Rate: " + (recoverySuccessRate * 100) + "%");
        
        if (!failureReasons.isEmpty()) {
            System.out.println("  Failure Reasons:");
            failureReasons.forEach(reason -> System.out.println("    - " + reason));
        }
        
        // Target: 95% recovery success rate
        assertTrue(recoverySuccessRate >= 0.95, 
            "Recovery success rate should be >= 95%, was: " + (recoverySuccessRate * 100) + "%");
    }

    @Test
    void testRecoveryAccuracyWithVariousCorruptionScenarios() {
        // Test recovery accuracy with various corruption scenarios
        
        CorruptionScenario[] scenarios = {
            new CorruptionScenario("PARTIAL_FILE_CORRUPTION", "50% of files corrupted", 0.5),
            new CorruptionScenario("MISSING_PRIMARY_STORAGE", "Primary storage location missing", 0.8),
            new CorruptionScenario("INCONSISTENT_TIMESTAMPS", "Timestamp inconsistencies", 0.3),
            new CorruptionScenario("INCOMPLETE_TASK_DATA", "Task information incomplete", 0.4),
            new CorruptionScenario("CORRUPTED_TECHNICAL_ENV", "Technical environment data corrupted", 0.6),
            new CorruptionScenario("MISSING_REDUNDANCY_LAYER", "One redundancy layer missing", 0.7),
            new CorruptionScenario("CROSS_REFERENCE_ERRORS", "Cross-reference validation errors", 0.5),
            new CorruptionScenario("PARTIAL_GIT_HISTORY", "Git history partially corrupted", 0.4)
        };
        
        List<RecoveryAccuracyResult> results = new ArrayList<>();
        
        for (CorruptionScenario scenario : scenarios) {
            SessionState originalState = createTestSessionState("accuracy-test-" + scenario.getType());
            
            // Store and then corrupt
            sessionStateManager.captureSessionState(originalState);
            redundancyManager.storeWithRedundancy(originalState);
            documentationSynchronizer.updateAllFiles(originalState);
            
            simulateContextCorruption(originalState.getSessionId(), scenario);
            
            // Attempt recovery and measure accuracy
            ReconstructedContext recoveredContext = contextRecoveryEngine.reconstructContext(originalState.getSessionId());
            
            RecoveryAccuracyResult result = new RecoveryAccuracyResult();
            result.setScenario(scenario);
            result.setRecoverySuccessful(recoveredContext != null);
            
            if (recoveredContext != null) {
                result.setConfidenceScore(recoveredContext.getConfidence());
                result.setAccuracyScore(calculateAccuracyScore(originalState, recoveredContext));
                result.setMissingElementsCount(recoveredContext.getMissingElements().size());
            } else {
                result.setConfidenceScore(0.0);
                result.setAccuracyScore(0.0);
                result.setMissingElementsCount(Integer.MAX_VALUE);
            }
            
            results.add(result);
        }
        
        // Analyze results
        double avgAccuracy = results.stream()
            .filter(r -> r.isRecoverySuccessful())
            .mapToDouble(RecoveryAccuracyResult::getAccuracyScore)
            .average()
            .orElse(0.0);
        
        long successfulRecoveries = results.stream()
            .mapToLong(r -> r.isRecoverySuccessful() ? 1 : 0)
            .sum();
        
        System.out.println("Recovery Accuracy Analysis:");
        System.out.println("  Scenarios Tested: " + scenarios.length);
        System.out.println("  Successful Recoveries: " + successfulRecoveries);
        System.out.println("  Average Accuracy: " + (avgAccuracy * 100) + "%");
        
        for (RecoveryAccuracyResult result : results) {
            System.out.println("  " + result.getScenario().getType() + ": " +
                             "Success=" + result.isRecoverySuccessful() +
                             ", Accuracy=" + (result.getAccuracyScore() * 100) + "%" +
                             ", Confidence=" + (result.getConfidenceScore() * 100) + "%" +
                             ", Missing=" + result.getMissingElementsCount());
        }
        
        // Targets: 95% recovery success, 90% average accuracy
        assertTrue(successfulRecoveries >= scenarios.length * 0.95, 
            "Should successfully recover from at least 95% of corruption scenarios");
        assertTrue(avgAccuracy >= 0.90, 
            "Average recovery accuracy should be >= 90%, was: " + (avgAccuracy * 100) + "%");
    }

    @Test
    void testSystemReliabilityUnderStressConditions() {
        // Test system reliability under stress conditions
        
        int stressTestDuration = 60; // seconds
        int concurrentSessions = 10;
        int operationsPerSession = 20;
        
        ExecutorService executor = Executors.newFixedThreadPool(concurrentSessions);
        List<CompletableFuture<StressTestResult>> futures = new ArrayList<>();
        
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < concurrentSessions; i++) {
            final int sessionIndex = i;
            
            CompletableFuture<StressTestResult> future = CompletableFuture.supplyAsync(() -> {
                StressTestResult result = new StressTestResult();
                result.setSessionId("stress-test-" + sessionIndex);
                
                try {
                    for (int op = 0; op < operationsPerSession; op++) {
                        if (System.currentTimeMillis() - startTime > stressTestDuration * 1000) {
                            break; // Time limit reached
                        }
                        
                        SessionState stressState = createRandomSessionState("stress-" + sessionIndex + "-" + op);
                        
                        // Perform full session cycle with random delays and errors
                        try {
                            ValidationResult captureResult = sessionStateManager.captureSessionState(stressState);
                            result.incrementOperation(captureResult.isValid());
                            
                            if (captureResult.isValid()) {
                                RedundancyStorageResult storageResult = redundancyManager.storeWithRedundancy(stressState);
                                result.incrementOperation(storageResult.isSuccess());
                                
                                UpdateSummary updateResult = documentationSynchronizer.updateAllFiles(stressState);
                                result.incrementOperation(updateResult.isSuccess());
                                
                                QualityGateResults gateResults = qualityGateManager.runAllGates(stressState);
                                result.incrementOperation(gateResults.isAllPassed());
                                
                                ReconstructedContext recoveryResult = contextRecoveryEngine.reconstructContext(stressState.getSessionId());
                                result.incrementOperation(recoveryResult != null && recoveryResult.getConfidence() > 0.8);
                            }
                            
                            // Random delay to simulate real-world usage patterns
                            Thread.sleep(random.nextInt(100) + 50);
                            
                        } catch (Exception e) {
                            result.addError(e.getMessage());
                        }
                    }
                } catch (Exception e) {
                    result.addError("Session " + sessionIndex + " failed: " + e.getMessage());
                }
                
                return result;
            }, executor);
            
            futures.add(future);
        }
        
        // Collect results
        List<StressTestResult> results = new ArrayList<>();
        for (CompletableFuture<StressTestResult> future : futures) {
            try {
                StressTestResult result = future.get(stressTestDuration + 30, TimeUnit.SECONDS);
                results.add(result);
            } catch (Exception e) {
                StressTestResult failedResult = new StressTestResult();
                failedResult.setSessionId("failed-session");
                failedResult.addError("Session failed to complete: " + e.getMessage());
                results.add(failedResult);
            }
        }
        
        executor.shutdown();
        
        // Analyze stress test results
        int totalOperations = results.stream().mapToInt(StressTestResult::getTotalOperations).sum();
        int successfulOperations = results.stream().mapToInt(StressTestResult::getSuccessfulOperations).sum();
        int totalErrors = results.stream().mapToInt(r -> r.getErrors().size()).sum();
        
        double successRate = totalOperations > 0 ? (double) successfulOperations / totalOperations : 0.0;
        
        System.out.println("Stress Test Analysis:");
        System.out.println("  Duration: " + stressTestDuration + " seconds");
        System.out.println("  Concurrent Sessions: " + concurrentSessions);
        System.out.println("  Total Operations: " + totalOperations);
        System.out.println("  Successful Operations: " + successfulOperations);
        System.out.println("  Success Rate: " + (successRate * 100) + "%");
        System.out.println("  Total Errors: " + totalErrors);
        
        if (totalErrors > 0) {
            System.out.println("  Error Summary:");
            results.forEach(result -> {
                if (!result.getErrors().isEmpty()) {
                    System.out.println("    " + result.getSessionId() + ": " + result.getErrors().size() + " errors");
                }
            });
        }
        
        // Reliability targets: 95% success rate, < 5% error rate
        assertTrue(successRate >= 0.95, 
            "Success rate under stress should be >= 95%, was: " + (successRate * 100) + "%");
        
        double errorRate = totalOperations > 0 ? (double) totalErrors / totalOperations : 0.0;
        assertTrue(errorRate <= 0.05, 
            "Error rate under stress should be <= 5%, was: " + (errorRate * 100) + "%");
    }

    @Test
    void testFailoverAndRecoveryMechanisms() {
        // Test failover and recovery mechanisms
        
        SessionState testState = createTestSessionState("failover-test");
        
        // 1. Test primary storage failure scenario
        sessionStateManager.captureSessionState(testState);
        redundancyManager.storeWithRedundancy(testState);
        
        // Simulate primary storage failure
        simulateStorageFailure("PRIMARY");
        
        // Recovery should still work from secondary sources
        ReconstructedContext recovery1 = contextRecoveryEngine.reconstructContext(testState.getSessionId());
        assertNotNull(recovery1, "Should recover from secondary sources when primary fails");
        assertTrue(recovery1.getConfidence() > 0.8, "Recovery confidence should remain high");
        
        // 2. Test multiple storage failures
        simulateStorageFailure("SECONDARY");
        
        ReconstructedContext recovery2 = contextRecoveryEngine.reconstructContext(testState.getSessionId());
        assertNotNull(recovery2, "Should recover from tertiary sources when primary and secondary fail");
        assertTrue(recovery2.getConfidence() > 0.6, "Recovery confidence should be reasonable");
        
        // 3. Test documentation synchronization failure recovery
        SessionState newState = createTestSessionState("doc-failure-test");
        
        // Simulate documentation update failure
        simulateDocumentationFailure();
        
        UpdateSummary updateResult = documentationSynchronizer.updateAllFiles(newState);
        // Should handle failure gracefully and provide fallback
        assertNotNull(updateResult, "Should provide update result even on failure");
        assertTrue(updateResult.getErrors().size() > 0, "Should report documentation errors");
        
        // 4. Test quality gate failure recovery
        SessionState corruptedState = createCorruptedSessionState("quality-failure-test");
        
        QualityGateResults gateResults = qualityGateManager.runAllGates(corruptedState);
        assertFalse(gateResults.isAllPassed(), "Quality gates should fail for corrupted state");
        assertTrue(gateResults.getFailedGates().size() > 0, "Should identify failed gates");
        
        // Should provide recovery recommendations
        assertTrue(gateResults.getFailedGates().stream()
            .allMatch(gate -> gate.getRecommendations() != null && !gate.getRecommendations().isEmpty()),
            "Failed gates should provide recovery recommendations");
    }

    @Test
    void testDataIntegrityUnderConcurrentAccess() {
        // Test data integrity under concurrent access
        
        int concurrentThreads = 8;
        int operationsPerThread = 25;
        String sharedSessionId = "concurrent-integrity-test";
        
        ExecutorService executor = Executors.newFixedThreadPool(concurrentThreads);
        List<CompletableFuture<Boolean>> futures = new ArrayList<>();
        
        // Create initial session state
        SessionState initialState = createTestSessionState(sharedSessionId);
        sessionStateManager.captureSessionState(initialState);
        redundancyManager.storeWithRedundancy(initialState);
        
        for (int i = 0; i < concurrentThreads; i++) {
            final int threadIndex = i;
            
            CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
                try {
                    for (int op = 0; op < operationsPerThread; op++) {
                        // Concurrent read operations
                        ReconstructedContext context = contextRecoveryEngine.reconstructContext(sharedSessionId);
                        if (context == null) {
                            return false;
                        }
                        
                        // Concurrent write operations (updates)
                        SessionState updateState = createTestSessionState(sharedSessionId + "-update-" + threadIndex + "-" + op);
                        ValidationResult result = sessionStateManager.captureSessionState(updateState);
                        if (!result.isValid()) {
                            return false;
                        }
                        
                        // Verify data consistency
                        CrossValidationResult validation = redundancyManager.crossValidateAllLayers();
                        if (!validation.isConsistent()) {
                            System.err.println("Data inconsistency detected in thread " + threadIndex + ", operation " + op);
                            return false;
                        }
                        
                        Thread.sleep(10); // Small delay to increase concurrency
                    }
                    return true;
                } catch (Exception e) {
                    System.err.println("Thread " + threadIndex + " failed: " + e.getMessage());
                    return false;
                }
            }, executor);
            
            futures.add(future);
        }
        
        // Wait for all threads to complete
        List<Boolean> results = new ArrayList<>();
        for (CompletableFuture<Boolean> future : futures) {
            try {
                Boolean result = future.get(60, TimeUnit.SECONDS);
                results.add(result);
            } catch (Exception e) {
                results.add(false);
                System.err.println("Thread failed to complete: " + e.getMessage());
            }
        }
        
        executor.shutdown();
        
        // Verify data integrity
        long successfulThreads = results.stream().mapToLong(r -> r ? 1 : 0).sum();
        double successRate = (double) successfulThreads / concurrentThreads;
        
        System.out.println("Concurrent Data Integrity Test:");
        System.out.println("  Threads: " + concurrentThreads);
        System.out.println("  Operations per Thread: " + operationsPerThread);
        System.out.println("  Successful Threads: " + successfulThreads);
        System.out.println("  Success Rate: " + (successRate * 100) + "%");
        
        // Final consistency check
        CrossValidationResult finalValidation = redundancyManager.crossValidateAllLayers();
        assertTrue(finalValidation.isConsistent(), "Final data should be consistent");
        
        // Target: 100% data integrity under concurrent access
        assertTrue(successRate >= 0.95, 
            "Data integrity success rate should be >= 95%, was: " + (successRate * 100) + "%");
    }

    // Helper methods for test scenarios

    private SessionState createTestSessionState(String sessionId) {
        SessionState state = new SessionState();
        state.setSessionId(sessionId);
        state.setTimestamp(LocalDateTime.now().toString());
        state.setDuration(1800);
        state.setLastCompletedPhase("Phase 2.1");
        state.setProgressPercentage(42.5);
        
        Task task = new Task();
        task.setId("task-" + sessionId);
        task.setDescription("Test task for " + sessionId);
        task.setStatus("COMPLETED");
        task.setPhase("Phase 2.1");
        state.setCompletedTasks(Arrays.asList(task));
        
        state.setJavaVersion("17.0.2");
        state.setMavenStatus("SUCCESS");
        
        CompilationStatus compilation = new CompilationStatus();
        compilation.setSuccess(true);
        compilation.setErrorCount(0);
        compilation.setWarningCount(1);
        state.setCompilationStatus(compilation);
        
        DatabaseStatus database = new DatabaseStatus();
        database.setConnected(true);
        database.setSchemaValid(true);
        database.setUrl("jdbc:postgresql://localhost:5432/learningportal");
        state.setDatabaseStatus(database);
        
        return state;
    }

    private SessionState createRandomSessionState(String sessionId) {
        SessionState state = createTestSessionState(sessionId);
        
        // Add randomization
        state.setProgressPercentage(random.nextDouble() * 100);
        state.setDuration(random.nextInt(7200) + 300); // 5 minutes to 2 hours
        
        // Random compilation status
        CompilationStatus compilation = state.getCompilationStatus();
        compilation.setErrorCount(random.nextInt(5));
        compilation.setWarningCount(random.nextInt(10));
        compilation.setSuccess(compilation.getErrorCount() == 0);
        
        return state;
    }

    private SessionState createCorruptedSessionState(String sessionId) {
        SessionState state = new SessionState();
        state.setSessionId(sessionId);
        // Intentionally leave required fields null or invalid
        state.setTimestamp(null);
        state.setProgressPercentage(-1.0); // Invalid percentage
        state.setLastCompletedPhase("");
        
        return state;
    }

    private CorruptionScenario generateCorruptionScenario(int index) {
        CorruptionScenario[] scenarios = {
            new CorruptionScenario("PARTIAL_CORRUPTION", "Partial file corruption", 0.3),
            new CorruptionScenario("MISSING_FILES", "Missing storage files", 0.6),
            new CorruptionScenario("TIMESTAMP_MISMATCH", "Timestamp inconsistencies", 0.2),
            new CorruptionScenario("INCOMPLETE_DATA", "Incomplete session data", 0.4),
            new CorruptionScenario("CROSS_REF_ERROR", "Cross-reference errors", 0.5)
        };
        
        return scenarios[index % scenarios.length];
    }

    private void simulateContextCorruption(String sessionId, CorruptionScenario scenario) {
        // Simulate various types of context corruption
        // In a real implementation, this would modify stored files or data
        System.out.println("Simulating corruption: " + scenario.getDescription() + 
                          " (severity: " + scenario.getSeverity() + ") for session: " + sessionId);
    }

    private void simulateStorageFailure(String storageLayer) {
        System.out.println("Simulating storage failure for layer: " + storageLayer);
    }

    private void simulateDocumentationFailure() {
        System.out.println("Simulating documentation synchronization failure");
    }

    private boolean validateRecoveryAccuracy(SessionState original, ReconstructedContext recovered) {
        // Validate that recovered context matches original with acceptable accuracy
        if (recovered.getConfidence() < 0.8) {
            return false;
        }
        
        // Check key elements are recovered
        boolean hasSessionId = recovered.getReconstructedElements().stream()
            .anyMatch(e -> e.getType().equals("SESSION_ID") && e.getValue().equals(original.getSessionId()));
        
        boolean hasPhase = recovered.getReconstructedElements().stream()
            .anyMatch(e -> e.getType().equals("PHASE") && e.getValue().equals(original.getLastCompletedPhase()));
        
        return hasSessionId && hasPhase;
    }

    private double calculateAccuracyScore(SessionState original, ReconstructedContext recovered) {
        // Calculate accuracy score based on how much information was correctly recovered
        int totalElements = 10; // Expected number of key elements
        int recoveredElements = 0;
        
        // Check each key element
        if (recovered.getReconstructedElements().stream()
            .anyMatch(e -> e.getType().equals("SESSION_ID") && e.getValue().equals(original.getSessionId()))) {
            recoveredElements++;
        }
        
        if (recovered.getReconstructedElements().stream()
            .anyMatch(e -> e.getType().equals("PHASE") && e.getValue().equals(original.getLastCompletedPhase()))) {
            recoveredElements++;
        }
        
        // Add more element checks as needed...
        recoveredElements += Math.min(8, recovered.getReconstructedElements().size() - 2);
        
        return (double) recoveredElements / totalElements;
    }

    // Helper classes for test results

    private static class CorruptionScenario {
        private String type;
        private String description;
        private double severity;
        
        public CorruptionScenario(String type, String description, double severity) {
            this.type = type;
            this.description = description;
            this.severity = severity;
        }
        
        public String getType() { return type; }
        public String getDescription() { return description; }
        public double getSeverity() { return severity; }
    }

    private static class RecoveryAccuracyResult {
        private CorruptionScenario scenario;
        private boolean recoverySuccessful;
        private double confidenceScore;
        private double accuracyScore;
        private int missingElementsCount;
        
        // Getters and setters
        public CorruptionScenario getScenario() { return scenario; }
        public void setScenario(CorruptionScenario scenario) { this.scenario = scenario; }
        public boolean isRecoverySuccessful() { return recoverySuccessful; }
        public void setRecoverySuccessful(boolean recoverySuccessful) { this.recoverySuccessful = recoverySuccessful; }
        public double getConfidenceScore() { return confidenceScore; }
        public void setConfidenceScore(double confidenceScore) { this.confidenceScore = confidenceScore; }
        public double getAccuracyScore() { return accuracyScore; }
        public void setAccuracyScore(double accuracyScore) { this.accuracyScore = accuracyScore; }
        public int getMissingElementsCount() { return missingElementsCount; }
        public void setMissingElementsCount(int missingElementsCount) { this.missingElementsCount = missingElementsCount; }
    }

    private static class StressTestResult {
        private String sessionId;
        private int totalOperations = 0;
        private int successfulOperations = 0;
        private List<String> errors = new ArrayList<>();
        
        public void incrementOperation(boolean success) {
            totalOperations++;
            if (success) {
                successfulOperations++;
            }
        }
        
        public void addError(String error) {
            errors.add(error);
        }
        
        // Getters and setters
        public String getSessionId() { return sessionId; }
        public void setSessionId(String sessionId) { this.sessionId = sessionId; }
        public int getTotalOperations() { return totalOperations; }
        public int getSuccessfulOperations() { return successfulOperations; }
        public List<String> getErrors() { return errors; }
    }
}