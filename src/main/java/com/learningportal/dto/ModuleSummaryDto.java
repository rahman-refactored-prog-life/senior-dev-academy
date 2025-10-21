package com.learningportal.dto;

import com.learningportal.model.LearningModule;

/**
 * Lightweight DTO for module summaries to improve query performance
 */
public class ModuleSummaryDto {
    private Long id;
    private String name;
    private LearningModule.Category category;
    private LearningModule.DifficultyLevel difficultyLevel;
    private Integer estimatedHours;

    public ModuleSummaryDto() {}

    public ModuleSummaryDto(Long id, String name, LearningModule.Category category, 
                           LearningModule.DifficultyLevel difficultyLevel, Integer estimatedHours) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.difficultyLevel = difficultyLevel;
        this.estimatedHours = estimatedHours;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LearningModule.Category getCategory() { return category; }
    public void setCategory(LearningModule.Category category) { this.category = category; }

    public LearningModule.DifficultyLevel getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(LearningModule.DifficultyLevel difficultyLevel) { this.difficultyLevel = difficultyLevel; }

    public Integer getEstimatedHours() { return estimatedHours; }
    public void setEstimatedHours(Integer estimatedHours) { this.estimatedHours = estimatedHours; }
}