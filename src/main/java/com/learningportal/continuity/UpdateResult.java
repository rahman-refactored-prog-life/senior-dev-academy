package com.learningportal.continuity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Result of updating a single documentation file.
 */
public class UpdateResult {
    
    private String fileName;
    private boolean success;
    private LocalDateTime updateTime;
    private int linesModified;
    private List<String> contentSections;
    private String errorMessage;
    
    public UpdateResult() {}
    
    public UpdateResult(String fileName, boolean success) {
        this.fileName = fileName;
        this.success = success;
        this.updateTime = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    
    public int getLinesModified() {
        return linesModified;
    }
    
    public void setLinesModified(int linesModified) {
        this.linesModified = linesModified;
    }
    
    public List<String> getContentSections() {
        return contentSections;
    }
    
    public void setContentSections(List<String> contentSections) {
        this.contentSections = contentSections;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    // Additional methods for compatibility
    public List<String> getErrors() {
        return errorMessage != null ? List.of(errorMessage) : List.of();
    }
    
    public void setErrors(List<String> errors) {
        if (errors != null && !errors.isEmpty()) {
            this.errorMessage = String.join("; ", errors);
        }
    }
    
    @Override
    public String toString() {
        return "UpdateResult{" +
                "fileName='" + fileName + '\'' +
                ", success=" + success +
                ", linesModified=" + linesModified +
                ", updateTime=" + updateTime +
                '}';
    }
}