package com.learningportal.continuity;

import java.time.LocalDateTime;

/**
 * Represents a file modification with detailed change tracking.
 */
public class FileModification {
    
    private String fileName;
    private String filePath;
    private ModificationType modificationType;
    private LocalDateTime modificationTime;
    private int linesAdded;
    private int linesRemoved;
    private int linesModified;
    private String changeDescription;
    private String author;
    
    public FileModification() {}
    
    public FileModification(String fileName, String filePath, ModificationType modificationType) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.modificationType = modificationType;
        this.modificationTime = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public ModificationType getModificationType() {
        return modificationType;
    }
    
    public void setModificationType(ModificationType modificationType) {
        this.modificationType = modificationType;
    }
    
    public LocalDateTime getModificationTime() {
        return modificationTime;
    }
    
    public void setModificationTime(LocalDateTime modificationTime) {
        this.modificationTime = modificationTime;
    }
    
    public int getLinesAdded() {
        return linesAdded;
    }
    
    public void setLinesAdded(int linesAdded) {
        this.linesAdded = linesAdded;
    }
    
    public int getLinesRemoved() {
        return linesRemoved;
    }
    
    public void setLinesRemoved(int linesRemoved) {
        this.linesRemoved = linesRemoved;
    }
    
    public int getLinesModified() {
        return linesModified;
    }
    
    public void setLinesModified(int linesModified) {
        this.linesModified = linesModified;
    }
    
    public String getChangeDescription() {
        return changeDescription;
    }
    
    public void setChangeDescription(String changeDescription) {
        this.changeDescription = changeDescription;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    // Additional methods for compatibility
    public int getLinesChanged() {
        return this.linesAdded + this.linesRemoved + this.linesModified;
    }
    
    public void setLinesChanged(int linesChanged) {
        this.linesModified = linesChanged;
    }
    
    public String getTimestamp() {
        return this.modificationTime != null ? this.modificationTime.toString() : null;
    }
    
    public void setTimestamp(String timestamp) {
        if (timestamp != null && !timestamp.isEmpty()) {
            try {
                this.modificationTime = LocalDateTime.parse(timestamp);
            } catch (Exception e) {
                this.modificationTime = LocalDateTime.now();
            }
        }
    }
    
    public void setModificationType(String modificationType) {
        try {
            this.modificationType = ModificationType.valueOf(modificationType.toUpperCase());
        } catch (IllegalArgumentException e) {
            this.modificationType = ModificationType.MODIFIED;
        }
    }
    
    @Override
    public String toString() {
        return "FileModification{" +
                "fileName='" + fileName + '\'' +
                ", modificationType=" + modificationType +
                ", linesAdded=" + linesAdded +
                ", linesRemoved=" + linesRemoved +
                ", modificationTime=" + modificationTime +
                '}';
    }
}

enum ModificationType {
    CREATED,
    MODIFIED,
    DELETED,
    RENAMED,
    MOVED
}