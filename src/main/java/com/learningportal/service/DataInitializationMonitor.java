package com.learningportal.service;

import com.learningportal.config.SafeDataInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Data Initialization Monitor Service
 * 
 * Provides monitoring and status reporting for the safe data initialization process.
 */
@Service
public class DataInitializationMonitor {
    
    private static final Logger log = LoggerFactory.getLogger(DataInitializationMonitor.class);
    
    private final SafeDataInitializer dataInitializer;
    
    public DataInitializationMonitor(SafeDataInitializer dataInitializer) {
        this.dataInitializer = dataInitializer;
    }
    
    /**
     * Get current initialization status
     */
    public InitializationStatus getInitializationStatus() {
        InitializationStatus status = new InitializationStatus();
        status.setTimestamp(LocalDateTime.now());
        status.setPhase1Complete(dataInitializer.isPhase1Complete());
        status.setPhase2Complete(dataInitializer.isPhase2Complete());
        status.setInitializationComplete(dataInitializer.isInitializationComplete());
        status.setCreatedModulesCount(dataInitializer.getCreatedModulesCount());
        status.setCreatedTopicsCount(dataInitializer.getCreatedTopicsCount());
        status.setCreatedQuestionsCount(dataInitializer.getCreatedQuestionsCount());
        
        return status;
    }
    
    /**
     * Get initialization progress as percentage
     */
    public double getInitializationProgress() {
        if (dataInitializer.isInitializationComplete()) {
            return 100.0;
        } else if (dataInitializer.isPhase2Complete()) {
            return 90.0;
        } else if (dataInitializer.isPhase1Complete()) {
            return 50.0;
        } else {
            return 0.0;
        }
    }
    
    /**
     * Get current initialization phase
     */
    public String getCurrentPhase() {
        if (dataInitializer.isInitializationComplete()) {
            return "Complete";
        } else if (dataInitializer.isPhase2Complete()) {
            return "Phase 2 Complete";
        } else if (dataInitializer.isPhase1Complete()) {
            return "Phase 1 Complete - Phase 2 In Progress";
        } else {
            return "Phase 1 In Progress";
        }
    }
    
    /**
     * Log current status
     */
    public void logCurrentStatus() {
        InitializationStatus status = getInitializationStatus();
        log.info("📊 Data Initialization Status:");
        log.info("   Phase: {}", getCurrentPhase());
        log.info("   Progress: {:.1f}%", getInitializationProgress());
        log.info("   Modules: {}", status.getCreatedModulesCount());
        log.info("   Topics: {}", status.getCreatedTopicsCount());
        log.info("   Questions: {}", status.getCreatedQuestionsCount());
    }
    
    /**
     * Check if initialization is healthy
     */
    public boolean isInitializationHealthy() {
        // Basic health check - if phase 1 is complete, we should have modules
        if (dataInitializer.isPhase1Complete() && dataInitializer.getCreatedModulesCount() == 0) {
            return false;
        }
        
        // If phase 2 is complete, we should have topics and questions
        if (dataInitializer.isPhase2Complete() && 
            (dataInitializer.getCreatedTopicsCount() == 0 || dataInitializer.getCreatedQuestionsCount() == 0)) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Initialization Status Data Class
     */
    public static class InitializationStatus {
        private LocalDateTime timestamp;
        private boolean phase1Complete;
        private boolean phase2Complete;
        private boolean initializationComplete;
        private int createdModulesCount;
        private int createdTopicsCount;
        private int createdQuestionsCount;
        
        // Standard getters and setters
        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
        
        public boolean isPhase1Complete() { return phase1Complete; }
        public void setPhase1Complete(boolean phase1Complete) { this.phase1Complete = phase1Complete; }
        
        public boolean isPhase2Complete() { return phase2Complete; }
        public void setPhase2Complete(boolean phase2Complete) { this.phase2Complete = phase2Complete; }
        
        public boolean isInitializationComplete() { return initializationComplete; }
        public void setInitializationComplete(boolean initializationComplete) { 
            this.initializationComplete = initializationComplete; 
        }
        
        public int getCreatedModulesCount() { return createdModulesCount; }
        public void setCreatedModulesCount(int createdModulesCount) { this.createdModulesCount = createdModulesCount; }
        
        public int getCreatedTopicsCount() { return createdTopicsCount; }
        public void setCreatedTopicsCount(int createdTopicsCount) { this.createdTopicsCount = createdTopicsCount; }
        
        public int getCreatedQuestionsCount() { return createdQuestionsCount; }
        public void setCreatedQuestionsCount(int createdQuestionsCount) { 
            this.createdQuestionsCount = createdQuestionsCount; 
        }
        
        @Override
        public String toString() {
            return "InitializationStatus{" +
                    "timestamp=" + timestamp +
                    ", phase1Complete=" + phase1Complete +
                    ", phase2Complete=" + phase2Complete +
                    ", initializationComplete=" + initializationComplete +
                    ", createdModulesCount=" + createdModulesCount +
                    ", createdTopicsCount=" + createdTopicsCount +
                    ", createdQuestionsCount=" + createdQuestionsCount +
                    '}';
        }
    }
}