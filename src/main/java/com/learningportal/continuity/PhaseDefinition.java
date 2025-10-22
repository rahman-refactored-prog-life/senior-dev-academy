package com.learningportal.continuity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Definition of a development phase with deliverables and success criteria.
 */
public class PhaseDefinition {
    
    private String phaseId;
    private String name;
    private String description;
    private List<String> deliverables;
    private int estimatedMinutes;
    private List<String> successCriteria;
    private LocalDateTime createdTime;
    private List<String> dependencies;
    
    public PhaseDefinition() {}
    
    public PhaseDefinition(String phaseId, String name) {
        this.phaseId = phaseId;
        this.name = name;
        this.createdTime = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getPhaseId() {
        return phaseId;
    }
    
    public void setPhaseId(String phaseId) {
        this.phaseId = phaseId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public List<String> getDeliverables() {
        return deliverables;
    }
    
    public void setDeliverables(List<String> deliverables) {
        this.deliverables = deliverables;
    }
    
    public int getEstimatedMinutes() {
        return estimatedMinutes;
    }
    
    public void setEstimatedMinutes(int estimatedMinutes) {
        this.estimatedMinutes = estimatedMinutes;
    }
    
    public List<String> getSuccessCriteria() {
        return successCriteria;
    }
    
    public void setSuccessCriteria(List<String> successCriteria) {
        this.successCriteria = successCriteria;
    }
    
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
    
    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
    
    public List<String> getDependencies() {
        return dependencies;
    }
    
    public void setDependencies(List<String> dependencies) {
        this.dependencies = dependencies;
    }
    
    // Additional method for compatibility
    public String getPhaseName() {
        return this.name;
    }
    
    @Override
    public String toString() {
        return "PhaseDefinition{" +
                "phaseId='" + phaseId + '\'' +
                ", name='" + name + '\'' +
                ", deliverables=" + (deliverables != null ? deliverables.size() : 0) +
                ", estimatedMinutes=" + estimatedMinutes +
                ", successCriteria=" + (successCriteria != null ? successCriteria.size() : 0) +
                '}';
    }
}