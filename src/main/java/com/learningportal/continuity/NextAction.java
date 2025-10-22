package com.learningportal.continuity;

/**
 * Represents a next action for session continuation with priority and context.
 */
public class NextAction {
    
    private String actionId;
    private String description;
    private ActionPriority priority;
    private String specName;
    private String taskId;
    private String fileName;
    private Integer lineNumber;
    private String context;
    private int estimatedMinutes;
    private String[] prerequisites;
    
    public NextAction() {}
    
    public NextAction(String description, ActionPriority priority) {
        this.actionId = generateActionId();
        this.description = description;
        this.priority = priority;
    }
    
    private String generateActionId() {
        return "action_" + System.currentTimeMillis();
    }
    
    // Getters and Setters
    public String getActionId() {
        return actionId;
    }
    
    public void setActionId(String actionId) {
        this.actionId = actionId;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public ActionPriority getPriority() {
        return priority;
    }
    
    public void setPriority(ActionPriority priority) {
        this.priority = priority;
    }
    
    public String getSpecName() {
        return specName;
    }
    
    public void setSpecName(String specName) {
        this.specName = specName;
    }
    
    public String getTaskId() {
        return taskId;
    }
    
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public Integer getLineNumber() {
        return lineNumber;
    }
    
    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }
    
    public String getContext() {
        return context;
    }
    
    public void setContext(String context) {
        this.context = context;
    }
    
    public int getEstimatedMinutes() {
        return estimatedMinutes;
    }
    
    public void setEstimatedMinutes(int estimatedMinutes) {
        this.estimatedMinutes = estimatedMinutes;
    }
    
    public String[] getPrerequisites() {
        return prerequisites;
    }
    
    public void setPrerequisites(String[] prerequisites) {
        this.prerequisites = prerequisites;
    }
    
    @Override
    public String toString() {
        return "NextAction{" +
                "actionId='" + actionId + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", specName='" + specName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", lineNumber=" + lineNumber +
                '}';
    }
}

enum ActionPriority {
    CRITICAL,
    HIGH,
    MEDIUM,
    LOW
}