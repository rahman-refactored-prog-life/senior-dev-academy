package com.learningportal.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * Learning Methodology Configuration Entity
 * 
 * Represents the configuration for cognitive science-based learning methodologies
 * with Amazon competency alignment and multi-modal support.
 */
@Entity
@Table(name = "learning_methodology_config")
@Schema(description = "Configuration for cognitive science-based learning methodologies")
public class LearningMethodologyConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the methodology configuration", example = "1")
    private Long id;

    @NotBlank(message = "Methodology name is required")
    @Column(name = "methodology_name", nullable = false)
    @Schema(description = "Name of the learning methodology", example = "Zero-Experience to Amazon Senior SDE")
    private String methodologyName;

    @Column(name = "cognitive_science_principles", columnDefinition = "TEXT")
    @Schema(description = "JSON array of cognitive science principles applied")
    private String cognitiveSciencePrinciples;

    @Column(name = "amazon_competency_alignment", columnDefinition = "TEXT")
    @Schema(description = "JSON mapping to Amazon L3-L6 competency levels")
    private String amazonCompetencyAlignment;

    @Column(name = "multi_modal_support")
    @Schema(description = "Whether multi-modal learning is supported")
    private Boolean multiModalSupport = true;

    @Column(name = "adaptive_intelligence")
    @Schema(description = "Whether adaptive intelligence is enabled")
    private Boolean adaptiveIntelligence = true;

    @Column(name = "effectiveness_metrics", columnDefinition = "TEXT")
    @Schema(description = "JSON metrics tracking methodology effectiveness")
    private String effectivenessMetrics;

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
    public LearningMethodologyConfig() {}

    public LearningMethodologyConfig(String methodologyName, String cognitiveSciencePrinciples, 
                                   String amazonCompetencyAlignment) {
        this.methodologyName = methodologyName;
        this.cognitiveSciencePrinciples = cognitiveSciencePrinciples;
        this.amazonCompetencyAlignment = amazonCompetencyAlignment;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMethodologyName() { return methodologyName; }
    public void setMethodologyName(String methodologyName) { this.methodologyName = methodologyName; }

    public String getCognitiveSciencePrinciples() { return cognitiveSciencePrinciples; }
    public void setCognitiveSciencePrinciples(String cognitiveSciencePrinciples) { 
        this.cognitiveSciencePrinciples = cognitiveSciencePrinciples; 
    }

    public String getAmazonCompetencyAlignment() { return amazonCompetencyAlignment; }
    public void setAmazonCompetencyAlignment(String amazonCompetencyAlignment) { 
        this.amazonCompetencyAlignment = amazonCompetencyAlignment; 
    }

    public Boolean getMultiModalSupport() { return multiModalSupport; }
    public void setMultiModalSupport(Boolean multiModalSupport) { this.multiModalSupport = multiModalSupport; }

    public Boolean getAdaptiveIntelligence() { return adaptiveIntelligence; }
    public void setAdaptiveIntelligence(Boolean adaptiveIntelligence) { this.adaptiveIntelligence = adaptiveIntelligence; }

    public String getEffectivenessMetrics() { return effectivenessMetrics; }
    public void setEffectivenessMetrics(String effectivenessMetrics) { this.effectivenessMetrics = effectivenessMetrics; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "LearningMethodologyConfig{" +
                "id=" + id +
                ", methodologyName='" + methodologyName + '\'' +
                ", multiModalSupport=" + multiModalSupport +
                ", adaptiveIntelligence=" + adaptiveIntelligence +
                '}';
    }
}