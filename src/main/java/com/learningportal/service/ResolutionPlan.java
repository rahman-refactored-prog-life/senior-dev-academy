package com.learningportal.service;

import java.util.List;

/**
 * Resolution Plan Data Class
 * 
 * Contains a step-by-step plan for resolving compilation issues.
 */
public class ResolutionPlan {
    
    private List<ResolutionStep> steps;
    private long estimatedTimeMinutes;
    private List<String> prerequisites;
    private List<String> risks;
    private String rollbackPlan;
    private PlanType planType;
    
    public enum PlanType {
        AUTOMATIC("Automatic Fix"),
        MANUAL("Manual Fix Required"),
        HYBRID("Automatic + Manual Steps");
        
        private final String displayName;
        
        PlanType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Constructors
    public ResolutionPlan() {}
    
    public ResolutionPlan(List<ResolutionStep> steps, long estimatedTimeMinutes, PlanType planType) {
        this.steps = steps;
        this.estimatedTimeMinutes = estimatedTimeMinutes;
        this.planType = planType;
    }
    
    // Helper methods
    public boolean isFullyAutomated() {
        return planType == PlanType.AUTOMATIC && 
               steps != null && 
               steps.stream().allMatch(ResolutionStep::isAutomated);
    }
    
    public int getStepCount() {
        return steps != null ? steps.size() : 0;
    }
    
    public int getAutomatedStepCount() {
        return steps != null ? 
               (int) steps.stream().filter(ResolutionStep::isAutomated).count() : 0;
    }
    
    public int getManualStepCount() {
        return getStepCount() - getAutomatedStepCount();
    }
    
    public String getSummary() {
        return String.format("%s plan with %d steps (%d automated, %d manual) - Est. %d minutes",
                           planType.getDisplayName(),
                           getStepCount(),
                           getAutomatedStepCount(),
                           getManualStepCount(),
                           estimatedTimeMinutes);
    }
    
    // Standard getters and setters
    public List<ResolutionStep> getSteps() { return steps; }
    public void setSteps(List<ResolutionStep> steps) { this.steps = steps; }
    
    public long getEstimatedTimeMinutes() { return estimatedTimeMinutes; }
    public void setEstimatedTimeMinutes(long estimatedTimeMinutes) { this.estimatedTimeMinutes = estimatedTimeMinutes; }
    
    public List<String> getPrerequisites() { return prerequisites; }
    public void setPrerequisites(List<String> prerequisites) { this.prerequisites = prerequisites; }
    
    public List<String> getRisks() { return risks; }
    public void setRisks(List<String> risks) { this.risks = risks; }
    
    public String getRollbackPlan() { return rollbackPlan; }
    public void setRollbackPlan(String rollbackPlan) { this.rollbackPlan = rollbackPlan; }
    
    public PlanType getPlanType() { return planType; }
    public void setPlanType(PlanType planType) { this.planType = planType; }
    
    @Override
    public String toString() {
        return "ResolutionPlan{" +
                "stepCount=" + getStepCount() +
                ", estimatedTimeMinutes=" + estimatedTimeMinutes +
                ", planType=" + planType +
                '}';
    }
}