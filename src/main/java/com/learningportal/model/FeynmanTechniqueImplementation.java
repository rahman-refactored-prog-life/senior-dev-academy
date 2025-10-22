package com.learningportal.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Feynman Technique Implementation Entity
 * 
 * Represents the implementation of the Feynman Technique for learning complex concepts
 * through simple explanations, knowledge gap identification, and iterative simplification
 * with Amazon-scale context integration.
 */
@Entity
@Table(name = "feynman_technique_implementations")
@Schema(description = "Feynman Technique implementation for concept mastery")
public class FeynmanTechniqueImplementation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the Feynman implementation", example = "1")
    private Long id;

    @NotNull(message = "Content ID is required")
    @Column(name = "content_id", nullable = false)
    @Schema(description = "Reference to the learning content", example = "123")
    private Long contentId;

    @NotBlank(message = "Content type is required")
    @Column(name = "content_type", nullable = false)
    @Schema(description = "Type of content (module, topic, question)", example = "topic")
    private String contentType;

    @NotBlank(message = "Simple explanation is required")
    @Column(name = "simple_explanation", columnDefinition = "TEXT", nullable = false)
    @Schema(description = "Child-friendly explanation of the complex concept")
    private String simpleExplanation;

    @Column(name = "knowledge_gaps", columnDefinition = "TEXT")
    @Schema(description = "JSON array of identified knowledge gaps")
    private String knowledgeGaps;

    @Column(name = "source_reinforcement", columnDefinition = "TEXT")
    @Schema(description = "JSON reinforcement materials and references")
    private String sourceReinforcement;

    @Column(name = "simplification_analogies", columnDefinition = "TEXT")
    @Schema(description = "JSON array of analogies for difficult concepts")
    private String simplificationAnalogies;

    @Column(name = "amazon_scale_context", columnDefinition = "TEXT")
    @Schema(description = "JSON Amazon-specific context and examples")
    private String amazonScaleContext;

    @Column(name = "understanding_validation", columnDefinition = "TEXT")
    @Schema(description = "JSON validation criteria for understanding")
    private String understandingValidation;

    @Column(name = "iteration_count")
    @Schema(description = "Number of simplification iterations", example = "3")
    private Integer iterationCount = 1;

    @Column(name = "mastery_achieved")
    @Schema(description = "Whether complete understanding has been achieved")
    private Boolean masteryAchieved = false;

    @Column(name = "created_at")
    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Schema(description = "Last update timestamp")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Constructors
    public FeynmanTechniqueImplementation() {}

    public FeynmanTechniqueImplementation(Long contentId, String contentType, String simpleExplanation) {
        this.contentId = contentId;
        this.contentType = contentType;
        this.simpleExplanation = simpleExplanation;
    }

    // Helper methods
    @Schema(description = "Check if knowledge gaps have been identified")
    public boolean hasKnowledgeGaps() {
        return knowledgeGaps != null && !knowledgeGaps.trim().isEmpty();
    }

    @Schema(description = "Check if source reinforcement is available")
    public boolean hasSourceReinforcement() {
        return sourceReinforcement != null && !sourceReinforcement.trim().isEmpty();
    }

    @Schema(description = "Check if simplification analogies are available")
    public boolean hasSimplificationAnalogies() {
        return simplificationAnalogies != null && !simplificationAnalogies.trim().isEmpty();
    }

    @Schema(description = "Check if Amazon scale context is integrated")
    public boolean hasAmazonScaleContext() {
        return amazonScaleContext != null && !amazonScaleContext.trim().isEmpty();
    }

    @Schema(description = "Check if understanding validation criteria exist")
    public boolean hasUnderstandingValidation() {
        return understandingValidation != null && !understandingValidation.trim().isEmpty();
    }

    @Schema(description = "Get mastery level based on iterations and validation")
    public String getMasteryLevel() {
        if (masteryAchieved) return "Mastered";
        if (iterationCount >= 3) return "Advanced";
        if (iterationCount >= 2) return "Intermediate";
        return "Beginner";
    }

    @Schema(description = "Check if ready for next iteration")
    public boolean isReadyForNextIteration() {
        return !masteryAchieved && hasKnowledgeGaps();
    }

    @Schema(description = "Get explanation complexity level")
    public String getExplanationComplexity() {
        if (simpleExplanation.length() < 200) return "Very Simple";
        if (simpleExplanation.length() < 500) return "Simple";
        if (simpleExplanation.length() < 1000) return "Moderate";
        return "Complex";
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getContentId() { return contentId; }
    public void setContentId(Long contentId) { this.contentId = contentId; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    public String getSimpleExplanation() { return simpleExplanation; }
    public void setSimpleExplanation(String simpleExplanation) { this.simpleExplanation = simpleExplanation; }

    public String getKnowledgeGaps() { return knowledgeGaps; }
    public void setKnowledgeGaps(String knowledgeGaps) { this.knowledgeGaps = knowledgeGaps; }

    public String getSourceReinforcement() { return sourceReinforcement; }
    public void setSourceReinforcement(String sourceReinforcement) { this.sourceReinforcement = sourceReinforcement; }

    public String getSimplificationAnalogies() { return simplificationAnalogies; }
    public void setSimplificationAnalogies(String simplificationAnalogies) { 
        this.simplificationAnalogies = simplificationAnalogies; 
    }

    public String getAmazonScaleContext() { return amazonScaleContext; }
    public void setAmazonScaleContext(String amazonScaleContext) { this.amazonScaleContext = amazonScaleContext; }

    public String getUnderstandingValidation() { return understandingValidation; }
    public void setUnderstandingValidation(String understandingValidation) { 
        this.understandingValidation = understandingValidation; 
    }

    public Integer getIterationCount() { return iterationCount; }
    public void setIterationCount(Integer iterationCount) { this.iterationCount = iterationCount; }

    public Boolean getMasteryAchieved() { return masteryAchieved; }
    public void setMasteryAchieved(Boolean masteryAchieved) { this.masteryAchieved = masteryAchieved; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "FeynmanTechniqueImplementation{" +
                "id=" + id +
                ", contentId=" + contentId +
                ", contentType='" + contentType + '\'' +
                ", iterationCount=" + iterationCount +
                ", masteryAchieved=" + masteryAchieved +
                ", masteryLevel='" + getMasteryLevel() + '\'' +
                '}';
    }
}