package com.learningportal.continuity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Interface for Context Recovery operations
 */
public interface ContextRecoveryEngineInterface {
    
    /**
     * Reconstruct context from available sources
     */
    ReconstructedContext reconstructContext(String sessionId);
    
    /**
     * Analyze available sources for context reconstruction
     */
    List<SourceAnalysis> analyzeAvailableSources(String sessionId);
    
    /**
     * Validate reconstructed context
     */
    ValidationResult validateReconstruction(List<SourceAnalysis> sources);
    
    /**
     * Generate recovery report
     */
    RecoveryReport generateRecoveryReport(String sessionId);
    
    /**
     * Asynchronous source analysis
     */
    CompletableFuture<List<SourceAnalysis>> analyzeSourceAsync(String sourceType, String sessionId, int priority);
    
    /**
     * Get cached context
     */
    ReconstructedContext getCachedContext(String sessionId);
}