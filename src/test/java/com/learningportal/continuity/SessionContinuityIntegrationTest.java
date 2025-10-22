package com.learningportal.continuity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * End-to-End Integration Tests for Session Continuity System
 * Tests complete session capture and recovery cycle with all components
 */
@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SessionContinuityIntegrationTest {

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
    private PhaseManager phaseManager;

    @Autowired
    private RedundancyManager redundancyManager;

    @Autowired
    private ObjectMapper objectMapper;

    private SessionState testSessionState;
    private String testSessionId;

    @BeforeEach
    void setUp() {
        testSessionId = "test-session-" + System.currentTimeMillis();
        
        // Create test session state
        testSessionState = new SessionState();
        testSessionState.setSessionId(testSessionId);
        testSessionState.setTimestamp(LocalDateTime.now().toString());
        testSessionState.setDuration(3600); // 1 hour
        testSessionState.setLastCompletedPhase("Phase 2.1");
        testSessionState.setProgressPercentage(45.5);
        
        // Add test tasks
        Task completedTask = new Task();
        completedTask.setId("task-1");
        completedTask.setDescription("Implement core functionality");
        completedTask.setStatus("COMPLETED");
        completedTask.setPhase("Phase 2.1");
        
        Task inProgressTask = new Task();
        inProgressTask.setId("task-2");
        inProgressTask.setDescription("Add validation layer");
        inProgressTask.setStatus("IN_PROGRESS");
        inProgressTask.setPhase("Phase 2.2");
        
        testSessionState.setCompletedTasks(Arrays.asList(completedTask));
        testSessionState.setInProgressTasks(Arrays.asList(inProgressTask));
        
        // Add technical environment info
        testSessionState.setJavaVersion("17.0.2");
        testSessionState.setMavenStatus("SUCCESS");
        
        CompilationStatus compilationStatus = new CompilationStatus();
        compilationStatus.setSuccess(true);
        compilationStatus.setErrorCount(0);
        compilationStatus.setWarningCount(2);
        testSessionState.setCompilationStatus(compilationStatus);
        
        DatabaseStatus databaseStatus = new DatabaseStatus();
        databaseStatus.setConnected(true);
        databaseStatus.setSchemaValid(true);
        databaseStatus.setUrl("jdbc:postgresql://localhost:5432/learningportal");
        testSessionState.setDatabaseStatus(databaseStatus);
        
        // Add file modifications
        FileModification fileModification = new FileModification();
        fileModification.setFilePath("src/main/java/com/learningportal/service/TestService.java");
        fileModification.setModificationType("MODIFIED");
        fileModification.setLinesChanged(25);
        fileModification.setTimestamp(LocalDateTime.now().toString());
        testSessionState.setFilesModified(Arrays.asList(fileModification));
        
        // Add next actions
        NextAction nextAction = new NextAction();
        nextAction.setDescription("Complete validation layer implementation");
        nextAction.setPriority("HIGH");
        nextAction.setEstimatedTime(120); // 2 hours
        nextAction.setDependencies(Arrays.asList("task-1"));
        testSessionState.setNextActions(Arrays.asList(nextAction));
    }

    @Test
    @Order(1)
    void testCompleteSessionCaptureAndRecoveryCycle() {
        // Test complete end-to-end session continuity cycle
        
        // 1. Capture session state
        ValidationResult captureResult = sessionStateManager.captureSessionState(testSessionState);
        assertTrue(captureResult.isValid(), "Session capture should succeed");
        assertEquals(0, captureResult.getErrors().size(), "Should have no capture errors");
        
        // 2. Store in redundant locations
        RedundancyStorageResult storageResult = redundancyManager.storeWithRedundancy(testSessionState);
        assertTrue(storageResult.isSuccess(), "Redundant storage should succeed");
        assertEquals(4, storageResult.getStorageLocations().size(), "Should store in 4 redundant locations");
        
        // 3. Validate technical environment
        JavaEnvironmentInfo envInfo = environmentTracker.captureEnvironmentState();
        assertNotNull(envInfo, "Environment info should be captured");
        assertTrue(envInfo.getJavaVersion().startsWith("17"), "Should detect Java 17");
        
        // 4. Run quality gates
        QualityGateResults gateResults = qualityGateManager.runAllGates(testSessionState);
        assertTrue(gateResults.isAllPassed(), "All quality gates should pass");
        assertEquals(4, gateResults.getResults().size(), "Should run 4 quality gates");
        
        // 5. Update documentation
        UpdateSummary updateSummary = documentationSynchronizer.updateAllFiles(testSessionState);
        assertTrue(updateSummary.isSuccess(), "Documentation update should succeed");
        assertTrue(updateSummary.getFilesUpdated() >= 9, "Should update at least 9 files");
        
        // 6. Simulate session end and recovery
        String sessionId = testSessionState.getSessionId();
        
        // 7. Recover context from multiple sources
        ReconstructedContext recoveredContext = contextRecoveryEngine.reconstructContext(sessionId);
        assertNotNull(recoveredContext, "Context recovery should succeed");
        assertTrue(recoveredContext.getConfidence() > 0.95, "Recovery confidence should be > 95%");
        assertEquals(0, recoveredContext.getMissingElements().size(), "Should have no missing elements");
        
        // 8. Validate recovered context matches original
        assertEquals(testSessionState.getLastCompletedPhase(), 
                    recoveredContext.getReconstructedElements().stream()
                        .filter(e -> e.getType().equals("PHASE"))
                        .findFirst()
                        .map(ContextElement::getValue)
                        .orElse(""), 
                    "Recovered phase should match original");
        
        // 9. Validate cross-component data flow
        CrossValidationResult crossValidation = redundancyManager.crossValidateAllLayers();
        assertTrue(crossValidation.isConsistent(), "Cross-validation should pass");
        assertEquals(100.0, crossValidation.getConsistencyScore(), 0.01, "Consistency score should be 100%");
    }

    @Test
    @Order(2)
    void testQualityGatesIntegration() {
        // Test automated testing of all quality gates and validation systems
        
        // 1. Test compilation gate
        GateResult compilationGate = qualityGateManager.runCompilationGate();
        assertTrue(compilationGate.isPassed(), "Compilation gate should pass");
        assertTrue(compilationGate.getScore() >= 90.0, "Compilation score should be >= 90%");
        
        // 2. Test documentation gate
        GateResult documentationGate = qualityGateManager.runDocumentationGate();
        assertTrue(documentationGate.isPassed(), "Documentation gate should pass");
        assertEquals(0, documentationGate.getIssues().size(), "Should have no documentation issues");
        
        // 3. Test progress tracking gate
        GateResult progressGate = qualityGateManager.runProgressGate();
        assertTrue(progressGate.isPassed(), "Progress gate should pass");
        assertNotNull(progressGate.getRecommendations(), "Should provide recommendations");
        
        // 4. Test quality assurance gate
        GateResult qualityGate = qualityGateManager.runQualityGate();
        assertTrue(qualityGate.isPassed(), "Quality gate should pass");
        assertTrue(qualityGate.getScore() >= 85.0, "Quality score should be >= 85%");
        
        // 5. Test emergency bypass procedures
        BypassResult bypassResult = qualityGateManager.requestEmergencyBypass(
            "COMPILATION", "Critical production issue requires immediate deployment");
        assertNotNull(bypassResult, "Bypass result should be provided");
        assertTrue(bypassResult.getJustification().length() > 10, "Should require detailed justification");
        assertNotNull(bypassResult.getRemediationPlan(), "Should include remediation plan");
    }

    @Test
    @Order(3)
    void testErrorHandlingAndRecovery() {
        // Test cross-component data flow and error handling
        
        // 1. Test partial context corruption scenario
        SessionState corruptedState = new SessionState();
        corruptedState.setSessionId("corrupted-session");
        corruptedState.setTimestamp(null); // Simulate corruption
        
        ValidationResult corruptedValidation = sessionStateManager.captureSessionState(corruptedState);
        assertFalse(corruptedValidation.isValid(), "Corrupted state should fail validation");
        assertTrue(corruptedValidation.getErrors().size() > 0, "Should report validation errors");
        
        // 2. Test recovery from partial information
        ReconstructedContext partialRecovery = contextRecoveryEngine.reconstructContext("corrupted-session");
        assertNotNull(partialRecovery, "Should attempt recovery even with partial data");
        assertTrue(partialRecovery.getConfidence() < 0.5, "Confidence should be low for corrupted data");
        assertTrue(partialRecovery.getMissingElements().size() > 0, "Should identify missing elements");
        
        // 3. Test redundancy system error handling
        RedundancyStorageResult failureResult = redundancyManager.storeWithRedundancy(corruptedState);
        assertFalse(failureResult.isSuccess(), "Should fail to store corrupted state");
        assertTrue(failureResult.getErrors().size() > 0, "Should report storage errors");
        
        // 4. Test quality gate failure handling
        QualityGateResults failedGates = qualityGateManager.runAllGates(corruptedState);
        assertFalse(failedGates.isAllPassed(), "Quality gates should fail for corrupted state");
        assertTrue(failedGates.getFailedGates().size() > 0, "Should identify failed gates");
        
        // 5. Test documentation synchronization error handling
        UpdateSummary failedUpdate = documentationSynchronizer.updateAllFiles(corruptedState);
        assertFalse(failedUpdate.isSuccess(), "Documentation update should fail for corrupted state");
        assertTrue(failedUpdate.getErrors().size() > 0, "Should report update errors");
    }

    @Test
    @Order(4)
    void testPhaseManagementIntegration() {
        // Test phase management system integration
        
        // 1. Test phase validation
        PhaseValidationResult phaseValidation = phaseManager.validatePhaseCompletion("Phase 2.1");
        assertTrue(phaseValidation.isValid(), "Phase validation should succeed");
        assertEquals(100.0, phaseValidation.getCompletionPercentage(), 0.01, "Phase should be 100% complete");
        
        // 2. Test next phase recommendation
        NextPhaseRecommendation nextPhase = phaseManager.recommendNextPhase(testSessionState);
        assertNotNull(nextPhase, "Should provide next phase recommendation");
        assertEquals("Phase 2.2", nextPhase.getPhaseId(), "Should recommend Phase 2.2");
        assertTrue(nextPhase.getEstimatedTime() > 0, "Should provide time estimate");
        
        // 3. Test progress tracking
        ProgressTrackingResult progressTracking = phaseManager.trackProgress(testSessionState);
        assertNotNull(progressTracking, "Should provide progress tracking");
        assertEquals(45.5, progressTracking.getOverallProgress(), 0.01, "Should match session progress");
        assertTrue(progressTracking.getCompletedPhases().contains("Phase 2.1"), "Should include completed phases");
    }

    @Test
    @Order(5)
    void testPerformanceRequirements() {
        // Test that performance requirements are met
        
        long startTime, endTime, duration;
        
        // 1. Test session capture time < 5 seconds
        startTime = System.currentTimeMillis();
        ValidationResult captureResult = sessionStateManager.captureSessionState(testSessionState);
        endTime = System.currentTimeMillis();
        duration = endTime - startTime;
        
        assertTrue(captureResult.isValid(), "Session capture should succeed");
        assertTrue(duration < 5000, "Session capture should take < 5 seconds, took: " + duration + "ms");
        
        // 2. Test documentation update time < 10 seconds
        startTime = System.currentTimeMillis();
        UpdateSummary updateResult = documentationSynchronizer.updateAllFiles(testSessionState);
        endTime = System.currentTimeMillis();
        duration = endTime - startTime;
        
        assertTrue(updateResult.isSuccess(), "Documentation update should succeed");
        assertTrue(duration < 10000, "Documentation update should take < 10 seconds, took: " + duration + "ms");
        
        // 3. Test context recovery time < 30 seconds
        startTime = System.currentTimeMillis();
        ReconstructedContext recoveryResult = contextRecoveryEngine.reconstructContext(testSessionId);
        endTime = System.currentTimeMillis();
        duration = endTime - startTime;
        
        assertNotNull(recoveryResult, "Context recovery should succeed");
        assertTrue(duration < 30000, "Context recovery should take < 30 seconds, took: " + duration + "ms");
        
        // 4. Test quality gate execution time < 15 seconds
        startTime = System.currentTimeMillis();
        QualityGateResults gateResults = qualityGateManager.runAllGates(testSessionState);
        endTime = System.currentTimeMillis();
        duration = endTime - startTime;
        
        assertTrue(gateResults.isAllPassed(), "Quality gates should pass");
        assertTrue(duration < 15000, "Quality gates should execute < 15 seconds, took: " + duration + "ms");
    }

    @Test
    @Order(6)
    void testReliabilityRequirements() {
        // Test reliability requirements and stress conditions
        
        // 1. Test context preservation accuracy > 95%
        int totalTests = 20;
        int successfulRecoveries = 0;
        
        for (int i = 0; i < totalTests; i++) {
            SessionState testState = createTestSessionState("reliability-test-" + i);
            
            // Capture and store
            ValidationResult captureResult = sessionStateManager.captureSessionState(testState);
            if (captureResult.isValid()) {
                redundancyManager.storeWithRedundancy(testState);
                
                // Attempt recovery
                ReconstructedContext recovery = contextRecoveryEngine.reconstructContext(testState.getSessionId());
                if (recovery != null && recovery.getConfidence() > 0.95) {
                    successfulRecoveries++;
                }
            }
        }
        
        double successRate = (double) successfulRecoveries / totalTests;
        assertTrue(successRate >= 0.95, "Context preservation success rate should be >= 95%, was: " + successRate);
        
        // 2. Test zero context loss rate
        int contextLossEvents = 0;
        
        for (int i = 0; i < 10; i++) {
            SessionState testState = createTestSessionState("context-loss-test-" + i);
            
            // Full session cycle
            sessionStateManager.captureSessionState(testState);
            redundancyManager.storeWithRedundancy(testState);
            documentationSynchronizer.updateAllFiles(testState);
            
            // Verify no information loss
            ReconstructedContext recovery = contextRecoveryEngine.reconstructContext(testState.getSessionId());
            if (recovery == null || recovery.getMissingElements().size() > 0) {
                contextLossEvents++;
            }
        }
        
        assertEquals(0, contextLossEvents, "Should have zero context loss events");
        
        // 3. Test system reliability under concurrent load
        int concurrentSessions = 5;
        Thread[] threads = new Thread[concurrentSessions];
        boolean[] results = new boolean[concurrentSessions];
        
        for (int i = 0; i < concurrentSessions; i++) {
            final int sessionIndex = i;
            threads[i] = new Thread(() -> {
                try {
                    SessionState concurrentState = createTestSessionState("concurrent-test-" + sessionIndex);
                    
                    ValidationResult result = sessionStateManager.captureSessionState(concurrentState);
                    redundancyManager.storeWithRedundancy(concurrentState);
                    ReconstructedContext recovery = contextRecoveryEngine.reconstructContext(concurrentState.getSessionId());
                    
                    results[sessionIndex] = result.isValid() && recovery != null && recovery.getConfidence() > 0.9;
                } catch (Exception e) {
                    results[sessionIndex] = false;
                }
            });
        }
        
        // Start all threads
        for (Thread thread : threads) {
            thread.start();
        }
        
        // Wait for completion
        for (Thread thread : threads) {
            try {
                thread.join(10000); // 10 second timeout
            } catch (InterruptedException e) {
                fail("Thread interrupted during concurrent test");
            }
        }
        
        // Verify all sessions succeeded
        for (int i = 0; i < concurrentSessions; i++) {
            assertTrue(results[i], "Concurrent session " + i + " should succeed");
        }
    }

    private SessionState createTestSessionState(String sessionId) {
        SessionState state = new SessionState();
        state.setSessionId(sessionId);
        state.setTimestamp(LocalDateTime.now().toString());
        state.setDuration(1800); // 30 minutes
        state.setLastCompletedPhase("Phase 1.1");
        state.setProgressPercentage(25.0);
        
        Task task = new Task();
        task.setId("task-" + sessionId);
        task.setDescription("Test task for " + sessionId);
        task.setStatus("COMPLETED");
        task.setPhase("Phase 1.1");
        state.setCompletedTasks(Arrays.asList(task));
        
        state.setJavaVersion("17.0.2");
        state.setMavenStatus("SUCCESS");
        
        CompilationStatus compilation = new CompilationStatus();
        compilation.setSuccess(true);
        compilation.setErrorCount(0);
        compilation.setWarningCount(0);
        state.setCompilationStatus(compilation);
        
        DatabaseStatus database = new DatabaseStatus();
        database.setConnected(true);
        database.setSchemaValid(true);
        database.setUrl("jdbc:postgresql://localhost:5432/learningportal");
        state.setDatabaseStatus(database);
        
        return state;
    }
}