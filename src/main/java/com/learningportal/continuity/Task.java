package com.learningportal.continuity;

import java.time.LocalDateTime;

/**
 * Represents a development task with completion tracking and metadata.
 */
public class Task {
    
    private String taskId;
    private String description;
    private String specName;
    private TaskStatus status;
    private LocalDateTime startTime;
    private LocalDateTime completionTime;
    private int estimatedMinutes;
    private int actualMinutes;
    private String requirements;
    
    public Task() {}
    
    public Task(String taskId, String description, String specName) {
        this.taskId = taskId;
        this.description = description;
        this.specName = specName;
        this.status = TaskStatus.NOT_STARTED;
        this.startTime = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getTaskId() {
        return taskId;
    }
    
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getSpecName() {
        return specName;
    }
    
    public void setSpecName(String specName) {
        this.specName = specName;
    }
    
    public TaskStatus getStatus() {
        return status;
    }
    
    public void setStatus(TaskStatus status) {
        this.status = status;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalDateTime getCompletionTime() {
        return completionTime;
    }
    
    public void setCompletionTime(LocalDateTime completionTime) {
        this.completionTime = completionTime;
    }
    
    public int getEstimatedMinutes() {
        return estimatedMinutes;
    }
    
    public void setEstimatedMinutes(int estimatedMinutes) {
        this.estimatedMinutes = estimatedMinutes;
    }
    
    public int getActualMinutes() {
        return actualMinutes;
    }
    
    public void setActualMinutes(int actualMinutes) {
        this.actualMinutes = actualMinutes;
    }
    
    public String getRequirements() {
        return requirements;
    }
    
    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }
    
    // Additional methods for compatibility
    public String getId() {
        return this.taskId;
    }
    
    public void setId(String id) {
        this.taskId = id;
    }
    
    public String getPhase() {
        return this.specName; // Use specName as phase for now
    }
    
    public void setPhase(String phase) {
        this.specName = phase;
    }
    
    public void setStatusFromString(String status) {
        try {
            this.status = TaskStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            this.status = TaskStatus.NOT_STARTED;
        }
    }
    
    public String getStatusAsString() {
        return this.status != null ? this.status.toString() : "NOT_STARTED";
    }
    
    @Override
    public String toString() {
        return "Task{" +
                "taskId='" + taskId + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", specName='" + specName + '\'' +
                '}';
    }
}

enum TaskStatus {
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETED,
    BLOCKED,
    CANCELLED
}