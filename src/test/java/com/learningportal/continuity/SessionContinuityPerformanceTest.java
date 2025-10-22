package com.learningportal.continuity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.StopWatch;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Performance Benchmark Tests for Session Continuity System
 * Tests performance requirements and optimization targets
 */
@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SessionContinuityPerformanceTest {

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

    private SessionState performanceTestState;
    private List<Long> sessionCaptureTimes;
    private List<Long> documentationUpdateTimes;
    private List<Long> contextRecoveryTimes;
    private List<Long> qualityGateTimes;

    @BeforeEach
    void setUp() {
        sessionCaptureTimes = new ArrayList<>();
        documentationUpdateTimes = new ArrayList<>();
        contextRecoveryTimes = new ArrayList<>();
        qualityGateTimes = new ArrayList<>();
        
        // Create comprehensive test session state for performance testing
        performanceTestState = createLargeSessionState();
    }

    @Test
    void testSessionCapturePerformanceTarget() {
        // Target: Session capture time < 5 seconds
        StopWatch stopWatch = new StopWatch();
        
        // Warm up JVM
        for (int i = 0; i < 3; i++) {
            sessionStateManager.captureSessionState(performanceTestState);
        }
        
        // Measure performance over multiple iterations
        int iterations = 10;
        for (int i = 0; i < iterations; i++) {
            stopWatch.start("capture-" + i);
            ValidationResult result = sessionStateManager.captureSessionState(performanceTestState);
            stopWatch.stop();
            
            assertTrue(result.isValid(), "Session capture should succeed in iteration " + i);
            long timeMs = stopWatch.getLastTaskTimeMillis();
            sessionCaptureTimes.add(timeMs);
            
            assertTrue(timeMs < 5000, 
                "Session capture should take < 5 seconds, iteration " + i + " took: " + timeMs + "ms");
        }
        
        // Calculate statistics
        double avgTime = sessionCaptureTimes.stream().mapToLong(Long::longValue).average().orElse(0.0);
        long maxTime = sessionCaptureTimes.stream().mapToLong(Long::longValue).max().orElse(0L);
        long minTime = sessionCaptureTimes.stream().mapToLong(Long::longValue).min().orElse(0L);
        
        System.out.println("Session Capture Performance:");
        System.out.println("  Average: " + avgTime + "ms");
        System.out.println("  Min: " + minTime + "ms");
        System.out.println("  Max: " + maxTime + "ms");
        
        assertTrue(avgTime < 3000, "Average session capture time should be < 3 seconds");
        assertTrue(maxTime < 5000, "Maximum session capture time should be < 5 seconds");
    }

    @Test
    void testDocumentationUpdatePerformanceTarget() {
        // Target: Documentation update time < 10 seconds
        StopWatch stopWatch = new StopWatch();
        
        // Warm up
        for (int i = 0; i < 3; i++) {
            documentationSynchronizer.updateAllFiles(performanceTestState);
        }
        
        int iterations = 10;
        for (int i = 0; i < iterations; i++) {
            stopWatch.start("documentation-" + i);
            UpdateSummary result = documentationSynchronizer.updateAllFiles(performanceTestState);
            stopWatch.stop();
            
            assertTrue(result.isSuccess(), "Documentation update should succeed in iteration " + i);
            long timeMs = stopWatch.getLastTaskTimeMillis();
            documentationUpdateTimes.add(timeMs);
            
            assertTrue(timeMs < 10000, 
                "Documentation update should take < 10 seconds, iteration " + i + " took: " + timeMs + "ms");
        }
        
        double avgTime = documentationUpdateTimes.stream().mapToLong(Long::longValue).average().orElse(0.0);
        long maxTime = documentationUpdateTimes.stream().mapToLong(Long::longValue).max().orElse(0L);
        
        System.out.println("Documentation Update Performance:");
        System.out.println("  Average: " + avgTime + "ms");
        System.out.println("  Max: " + maxTime + "ms");
        
        assertTrue(avgTime < 7000, "Average documentation update time should be < 7 seconds");
        assertTrue(maxTime < 10000, "Maximum documentation update time should be < 10 seconds");
    }

    @Test
    void testContextRecoveryPerformanceTarget() {
        // Target: Context recovery time < 30 seconds
        StopWatch stopWatch = new StopWatch();
        
        // First store the session state
        sessionStateManager.captureSessionState(performanceTestState);
        redundancyManager.storeWithRedundancy(performanceTestState);
        documentationSynchronizer.updateAllFiles(performanceTestState);
        
        String sessionId = performanceTestState.getSessionId();
        
        // Warm up
        for (int i = 0; i < 3; i++) {
            contextRecoveryEngine.reconstructContext(sessionId);
        }
        
        int iterations = 10;
        for (int i = 0; i < iterations; i++) {
            stopWatch.start("recovery-" + i);
            ReconstructedContext result = contextRecoveryEngine.reconstructContext(sessionId);
            stopWatch.stop();
            
            assertNotNull(result, "Context recovery should succeed in iteration " + i);
            assertTrue(result.getConfidence() > 0.9, "Recovery confidence should be > 90%");
            
            long timeMs = stopWatch.getLastTaskTimeMillis();
            contextRecoveryTimes.add(timeMs);
            
            assertTrue(timeMs < 30000, 
                "Context recovery should take < 30 seconds, iteration " + i + " took: " + timeMs + "ms");
        }
        
        double avgTime = contextRecoveryTimes.stream().mapToLong(Long::longValue).average().orElse(0.0);
        long maxTime = contextRecoveryTimes.stream().mapToLong(Long::longValue).max().orElse(0L);
        
        System.out.println("Context Recovery Performance:");
        System.out.println("  Average: " + avgTime + "ms");
        System.out.println("  Max: " + maxTime + "ms");
        
        assertTrue(avgTime < 20000, "Average context recovery time should be < 20 seconds");
        assertTrue(maxTime < 30000, "Maximum context recovery time should be < 30 seconds");
    }

    @Test
    void testQualityGatePerformanceTarget() {
        // Target: Quality gate execution time < 15 seconds
        StopWatch stopWatch = new StopWatch();
        
        // Warm up
        for (int i = 0; i < 3; i++) {
            qualityGateManager.runAllGates(performanceTestState);
        }
        
        int iterations = 10;
        for (int i = 0; i < iterations; i++) {
            stopWatch.start("quality-gates-" + i);
            QualityGateResults result = qualityGateManager.runAllGates(performanceTestState);
            stopWatch.stop();
            
            assertTrue(result.isAllPassed(), "Quality gates should pass in iteration " + i);
            
            long timeMs = stopWatch.getLastTaskTimeMillis();
            qualityGateTimes.add(timeMs);
            
            assertTrue(timeMs < 15000, 
                "Quality gates should execute < 15 seconds, iteration " + i + " took: " + timeMs + "ms");
        }
        
        double avgTime = qualityGateTimes.stream().mapToLong(Long::longValue).average().orElse(0.0);
        long maxTime = qualityGateTimes.stream().mapToLong(Long::longValue).max().orElse(0L);
        
        System.out.println("Quality Gates Performance:");
        System.out.println("  Average: " + avgTime + "ms");
        System.out.println("  Max: " + maxTime + "ms");
        
        assertTrue(avgTime < 10000, "Average quality gate time should be < 10 seconds");
        assertTrue(maxTime < 15000, "Maximum quality gate time should be < 15 seconds");
    }

    @Test
    void testConcurrentSessionPerformance() {
        // Test performance under concurrent load
        int concurrentSessions = 5;
        ExecutorService executor = Executors.newFixedThreadPool(concurrentSessions);
        List<CompletableFuture<Long>> futures = new ArrayList<>();
        
        for (int i = 0; i < concurrentSessions; i++) {
            final int sessionIndex = i;
            CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> {
                SessionState concurrentState = createLargeSessionState();
                concurrentState.setSessionId("concurrent-perf-" + sessionIndex);
                
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                
                // Full session cycle
                ValidationResult captureResult = sessionStateManager.captureSessionState(concurrentState);
                RedundancyStorageResult storageResult = redundancyManager.storeWithRedundancy(concurrentState);
                UpdateSummary updateResult = documentationSynchronizer.updateAllFiles(concurrentState);
                QualityGateResults gateResults = qualityGateManager.runAllGates(concurrentState);
                ReconstructedContext recoveryResult = contextRecoveryEngine.reconstructContext(concurrentState.getSessionId());
                
                stopWatch.stop();
                
                // Verify all operations succeeded
                assertTrue(captureResult.isValid(), "Concurrent capture should succeed");
                assertTrue(storageResult.isSuccess(), "Concurrent storage should succeed");
                assertTrue(updateResult.isSuccess(), "Concurrent update should succeed");
                assertTrue(gateResults.isAllPassed(), "Concurrent gates should pass");
                assertNotNull(recoveryResult, "Concurrent recovery should succeed");
                
                return stopWatch.getTotalTimeMillis();
            }, executor);
            
            futures.add(future);
        }
        
        // Wait for all to complete and collect results
        List<Long> concurrentTimes = new ArrayList<>();
        for (CompletableFuture<Long> future : futures) {
            try {
                Long time = future.get(60, TimeUnit.SECONDS); // 60 second timeout
                concurrentTimes.add(time);
            } catch (Exception e) {
                fail("Concurrent session failed: " + e.getMessage());
            }
        }
        
        executor.shutdown();
        
        // Verify performance under load
        double avgConcurrentTime = concurrentTimes.stream().mapToLong(Long::longValue).average().orElse(0.0);
        long maxConcurrentTime = concurrentTimes.stream().mapToLong(Long::longValue).max().orElse(0L);
        
        System.out.println("Concurrent Session Performance:");
        System.out.println("  Sessions: " + concurrentSessions);
        System.out.println("  Average: " + avgConcurrentTime + "ms");
        System.out.println("  Max: " + maxConcurrentTime + "ms");
        
        // Performance should not degrade significantly under concurrent load
        assertTrue(avgConcurrentTime < 60000, "Average concurrent session time should be < 60 seconds");
        assertTrue(maxConcurrentTime < 90000, "Maximum concurrent session time should be < 90 seconds");
    }

    @RepeatedTest(5)
    void testMemoryUsageOptimization() {
        // Test memory usage during session operations
        Runtime runtime = Runtime.getRuntime();
        
        // Force garbage collection and measure baseline
        System.gc();
        Thread.yield();
        long baselineMemory = runtime.totalMemory() - runtime.freeMemory();
        
        // Perform session operations
        SessionState largeState = createLargeSessionState();
        ValidationResult captureResult = sessionStateManager.captureSessionState(largeState);
        RedundancyStorageResult storageResult = redundancyManager.storeWithRedundancy(largeState);
        UpdateSummary updateResult = documentationSynchronizer.updateAllFiles(largeState);
        
        // Measure memory after operations
        long peakMemory = runtime.totalMemory() - runtime.freeMemory();
        long memoryIncrease = peakMemory - baselineMemory;
        
        // Clean up and measure final memory
        largeState = null;
        captureResult = null;
        storageResult = null;
        updateResult = null;
        
        System.gc();
        Thread.yield();
        long finalMemory = runtime.totalMemory() - runtime.freeMemory();
        long memoryLeak = finalMemory - baselineMemory;
        
        System.out.println("Memory Usage Analysis:");
        System.out.println("  Baseline: " + (baselineMemory / 1024 / 1024) + " MB");
        System.out.println("  Peak: " + (peakMemory / 1024 / 1024) + " MB");
        System.out.println("  Increase: " + (memoryIncrease / 1024 / 1024) + " MB");
        System.out.println("  Final: " + (finalMemory / 1024 / 1024) + " MB");
        System.out.println("  Potential Leak: " + (memoryLeak / 1024 / 1024) + " MB");
        
        // Memory increase should be reasonable (< 100MB for session operations)
        assertTrue(memoryIncrease < 100 * 1024 * 1024, "Memory increase should be < 100MB");
        
        // Memory leak should be minimal (< 10MB)
        assertTrue(memoryLeak < 10 * 1024 * 1024, "Memory leak should be < 10MB");
    }

    @Test
    void testThroughputOptimization() {
        // Test system throughput - sessions per minute
        int targetSessionsPerMinute = 10;
        int testDurationSeconds = 30; // Test for 30 seconds
        
        StopWatch overallTimer = new StopWatch();
        overallTimer.start();
        
        int completedSessions = 0;
        long startTime = System.currentTimeMillis();
        long endTime = startTime + (testDurationSeconds * 1000);
        
        while (System.currentTimeMillis() < endTime) {
            SessionState throughputState = createLargeSessionState();
            throughputState.setSessionId("throughput-test-" + completedSessions);
            
            try {
                // Minimal session cycle for throughput testing
                ValidationResult result = sessionStateManager.captureSessionState(throughputState);
                if (result.isValid()) {
                    redundancyManager.storeWithRedundancy(throughputState);
                    completedSessions++;
                }
            } catch (Exception e) {
                // Continue testing even if individual sessions fail
                System.err.println("Session failed during throughput test: " + e.getMessage());
            }
        }
        
        overallTimer.stop();
        
        double actualDurationMinutes = overallTimer.getTotalTimeMillis() / 60000.0;
        double sessionsPerMinute = completedSessions / actualDurationMinutes;
        
        System.out.println("Throughput Analysis:");
        System.out.println("  Test Duration: " + actualDurationMinutes + " minutes");
        System.out.println("  Completed Sessions: " + completedSessions);
        System.out.println("  Sessions per Minute: " + sessionsPerMinute);
        
        assertTrue(sessionsPerMinute >= targetSessionsPerMinute, 
            "Should achieve at least " + targetSessionsPerMinute + " sessions per minute, achieved: " + sessionsPerMinute);
    }

    private SessionState createLargeSessionState() {
        SessionState state = new SessionState();
        state.setSessionId("perf-test-" + System.currentTimeMillis());
        state.setTimestamp(LocalDateTime.now().toString());
        state.setDuration(7200); // 2 hours
        state.setLastCompletedPhase("Phase 3.5");
        state.setProgressPercentage(67.5);
        
        // Create many tasks to simulate large session
        List<Task> completedTasks = new ArrayList<>();
        List<Task> inProgressTasks = new ArrayList<>();
        
        for (int i = 0; i < 50; i++) {
            Task task = new Task();
            task.setId("task-" + i);
            task.setDescription("Performance test task " + i);
            task.setStatus("COMPLETED");
            task.setPhase("Phase " + (i % 5 + 1) + "." + (i % 3 + 1));
            completedTasks.add(task);
        }
        
        for (int i = 0; i < 10; i++) {
            Task task = new Task();
            task.setId("in-progress-task-" + i);
            task.setDescription("In progress performance test task " + i);
            task.setStatus("IN_PROGRESS");
            task.setPhase("Phase 4." + (i % 3 + 1));
            inProgressTasks.add(task);
        }
        
        state.setCompletedTasks(completedTasks);
        state.setInProgressTasks(inProgressTasks);
        
        // Add technical environment
        state.setJavaVersion("17.0.2");
        state.setMavenStatus("SUCCESS");
        
        CompilationStatus compilation = new CompilationStatus();
        compilation.setSuccess(true);
        compilation.setErrorCount(0);
        compilation.setWarningCount(5);
        state.setCompilationStatus(compilation);
        
        DatabaseStatus database = new DatabaseStatus();
        database.setConnected(true);
        database.setSchemaValid(true);
        database.setUrl("jdbc:postgresql://localhost:5432/learningportal");
        state.setDatabaseStatus(database);
        
        // Add many file modifications
        List<FileModification> fileModifications = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            FileModification mod = new FileModification();
            mod.setFilePath("src/main/java/com/learningportal/service/TestService" + i + ".java");
            mod.setModificationType(i % 2 == 0 ? "MODIFIED" : "CREATED");
            mod.setLinesChanged(50 + (i * 10));
            mod.setTimestamp(LocalDateTime.now().toString());
            fileModifications.add(mod);
        }
        state.setFilesModified(fileModifications);
        
        // Add many next actions
        List<NextAction> nextActions = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            NextAction action = new NextAction();
            action.setDescription("Performance test next action " + i);
            action.setPriority(i % 3 == 0 ? "HIGH" : i % 3 == 1 ? "MEDIUM" : "LOW");
            action.setEstimatedTime(60 + (i * 30));
            action.setDependencies(Arrays.asList("task-" + (i - 1), "task-" + (i - 2)));
            nextActions.add(action);
        }
        state.setNextActions(nextActions);
        
        // Add known issues
        List<Issue> knownIssues = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Issue issue = new Issue();
            issue.setId("issue-" + i);
            issue.setDescription("Performance test issue " + i);
            issue.setSeverity(i % 3 == 0 ? "HIGH" : i % 3 == 1 ? "MEDIUM" : "LOW");
            issue.setStatus("OPEN");
            issue.setResolutionSteps(Arrays.asList("Step 1", "Step 2", "Step 3"));
            knownIssues.add(issue);
        }
        state.setKnownIssues(knownIssues);
        
        return state;
    }
}