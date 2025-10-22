package com.learningportal.continuity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents an exact continuation point for seamless session resumption.
 */
public class ContinuationPoint {
    
    private LocalDateTime timestamp;
    private String currentSpec;
    private String currentTask;
    private String currentFile;
    private Integer currentLine;
    private List<NextAction> nextActions;
    private String context;
    private String instructions;
    private List<String> prerequisites;
    
    public ContinuationPoint() {}
    
    public ContinuationPoint(String currentSpec, String currentTask) {
        this.timestamp = LocalDateTime.now();
        this.currentSpec = currentSpec;
        this.currentTask = currentTask;
    }
    
    // Getters and Setters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getCurrentSpec() {
        return currentSpec;
    }
    
    public void setCurrentSpec(String currentSpec) {
        this.currentSpec = currentSpec;
    }
    
    public String getCurrentTask() {
        return currentTask;
    }
    
    public void setCurrentTask(String currentTask) {
        this.currentTask = currentTask;
    }
    
    public String getCurrentFile() {
        return currentFile;
    }
    
    public void setCurrentFile(String currentFile) {
        this.currentFile = currentFile;
    }
    
    public Integer getCurrentLine() {
        return currentLine;
    }
    
    public void setCurrentLine(Integer currentLine) {
        this.currentLine = currentLine;
    }
    
    public List<NextAction> getNextActions() {
        return nextActions;
    }
    
    public void setNextActions(List<NextAction> nextActions) {
        this.nextActions = nextActions;
    }
    
    public String getContext() {
        return context;
    }
    
    public void setContext(String context) {
        this.context = context;
    }
    
    public String getInstructions() {
        return instructions;
    }
    
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    
    public List<String> getPrerequisites() {
        return prerequisites;
    }
    
    public void setPrerequisites(List<String> prerequisites) {
        this.prerequisites = prerequisites;
    }
    
    @Override
    public String toString() {
        return "ContinuationPoint{" +
                "timestamp=" + timestamp +
                ", currentSpec='" + currentSpec + '\'' +
                ", currentTask='" + currentTask + '\'' +
                ", currentFile='" + currentFile + '\'' +
                ", currentLine=" + currentLine +
                ", nextActions=" + (nextActions != null ? nextActions.size() : 0) +
                '}';
    }
}