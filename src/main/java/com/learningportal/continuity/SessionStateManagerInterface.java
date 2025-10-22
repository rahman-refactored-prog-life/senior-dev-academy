package com.learningportal.continuity;

import java.util.concurrent.CompletableFuture;

/**
 * Interface for Session State Management operations
 */
public interface SessionStateManagerInterface {
    
    /**
     * Capture the current session state
     */
    ValidationResult captureSessionState(SessionState sessionState);
    
    /**
     * Get cached session state
     */
    SessionState getSessionState(String sessionId);
    
    /**
     * Evict session state from cache
     */
    void evictSessionState(String sessionId);
    
    /**
     * Asynchronous session state capture
     */
    CompletableFuture<ValidationResult> captureSessionStateAsync(SessionState sessionState);
}