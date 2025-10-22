package com.learningportal.continuity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Interface for Documentation Synchronization operations
 */
public interface DocumentationSynchronizerInterface {
    
    /**
     * Update all documentation files
     */
    UpdateSummary updateAllFiles(SessionState sessionState);
    
    /**
     * Validate consistency across documentation files
     */
    ConsistencyReport validateConsistency();
    
    /**
     * Resolve conflicts between documentation files
     */
    List<ConflictResolution> resolveConflicts();
    
    /**
     * Asynchronous file update
     */
    CompletableFuture<UpdateResult> updateFileAsync(String fileName, SessionState sessionState, int priority);
}