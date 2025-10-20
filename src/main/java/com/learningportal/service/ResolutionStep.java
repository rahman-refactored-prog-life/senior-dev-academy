package com.learningportal.service;

import java.util.List;

/**
 * Resolution Step Data Class
 * 
 * Represents a single step in a compilation issue resolution plan.
 */
public class ResolutionStep {
    
    private int order;
    private String description;
    private String command;
    private String expectedResult;
    private List<String> validationSteps;
    private boolean automated;
    private StepType stepType;
    private String filePath;
    private String backupRequired;
    
    public enum StepType {
        DEPENDENCY_UPDATE("Update Dependency"),
        FILE_MODIFICATION("Modify File"),
        COMMAND_EXECUTION("Execute Command"),
        CONFIGURATION_CHANGE("Change Configuration"),
        VALIDATION("Validation Step");
        
        private final String displayName;
        
        StepType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Constructors
    public ResolutionStep() {}
    
    public ResolutionStep(int order, String description, boolean automated, StepType stepType) {
        this.order = order;
        this.description = description;
        this.automated = automated;
        this.stepType = stepType;
    }
    
    // Helper methods
    public boolean hasCommand() {
        return command != null && !command.trim().isEmpty();
    }
    
    public boolean hasValidation() {
        return validationSteps != null && !validationSteps.isEmpty();
    }
    
    public boolean requiresBackup() {
        return backupRequired != null && !backupRequired.trim().isEmpty();
    }
    
    public String getFormattedStep() {
        String prefix = automated ? "🤖 AUTO" : "👤 MANUAL";
        return String.format("%s Step %d: %s", prefix, order, description);
    }
    
    // Standard getters and setters
    public int getOrder() { return order; }
    public void setOrder(int order) { this.order = order; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getCommand() { return command; }
    public void setCommand(String command) { this.command = command; }
    
    public String getExpectedResult() { return expectedResult; }
    public void setExpectedResult(String expectedResult) { this.expectedResult = expectedResult; }
    
    public List<String> getValidationSteps() { return validationSteps; }
    public void setValidationSteps(List<String> validationSteps) { this.validationSteps = validationSteps; }
    
    public boolean isAutomated() { return automated; }
    public void setAutomated(boolean automated) { this.automated = automated; }
    
    public StepType getStepType() { return stepType; }
    public void setStepType(StepType stepType) { this.stepType = stepType; }
    
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    
    public String getBackupRequired() { return backupRequired; }
    public void setBackupRequired(String backupRequired) { this.backupRequired = backupRequired; }
    
    @Override
    public String toString() {
        return "ResolutionStep{" +
                "order=" + order +
                ", description='" + description + '\'' +
                ", automated=" + automated +
                ", stepType=" + stepType +
                '}';
    }
}