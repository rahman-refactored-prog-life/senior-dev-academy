package com.learningportal.continuity;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Parsed content from a documentation file for consistency validation.
 */
public class DocumentationContent {
    
    private String fileName;
    private String sessionId;
    private LocalDateTime timestamp;
    private Double progressPercentage;
    private String currentPhase;
    private String compilationStatus;
    private String databaseStatus;
    private Map<String, String> extractedData;
    private LocalDateTime lastModified;
    
    public DocumentationContent() {}
    
    public DocumentationContent(String fileName) {
        this.fileName = fileName;
        this.lastModified = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public Double getProgressPercentage() {
        return progressPercentage;
    }
    
    public void setProgressPercentage(Double progressPercentage) {
        this.progressPercentage = progressPercentage;
    }
    
    public String getCurrentPhase() {
        return currentPhase;
    }
    
    public void setCurrentPhase(String currentPhase) {
        this.currentPhase = currentPhase;
    }
    
    public String getCompilationStatus() {
        return compilationStatus;
    }
    
    public void setCompilationStatus(String compilationStatus) {
        this.compilationStatus = compilationStatus;
    }
    
    public String getDatabaseStatus() {
        return databaseStatus;
    }
    
    public void setDatabaseStatus(String databaseStatus) {
        this.databaseStatus = databaseStatus;
    }
    
    public Map<String, String> getExtractedData() {
        return extractedData;
    }
    
    public void setExtractedData(Map<String, String> extractedData) {
        this.extractedData = extractedData;
    }
    
    public LocalDateTime getLastModified() {
        return lastModified;
    }
    
    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }
    
    // Additional methods for compatibility
    private String content;
    
    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
        this.lastModified = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "DocumentationContent{" +
                "fileName='" + fileName + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", currentPhase='" + currentPhase + '\'' +
                ", progressPercentage=" + progressPercentage +
                ", lastModified=" + lastModified +
                '}';
    }
}