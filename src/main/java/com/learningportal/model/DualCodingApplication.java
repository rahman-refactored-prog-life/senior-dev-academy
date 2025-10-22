package com.learningportal.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Dual Coding Application Entity
 * 
 * Represents the application of Dual Coding Theory for enhanced learning through
 * visual and verbal information processing, spatial relationships, and temporal sequences
 * with Amazon architecture examples integration.
 */
@Entity
@Table(name = "dual_coding_applications")
@Schema(description = "Dual Coding Theory application for enhanced learning")
public class DualCodingApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the dual coding application", example = "1")
    private Long id;

    @NotNull(message = "Content ID is required")
    @Column(name = "content_id", nullable = false)
    @Schema(description = "Reference to the learning content", example = "123")
    private Long contentId;

    @NotNull(message = "Content type is required")
    @Column(name = "content_type", nullable = false)
    @Schema(description = "Type of content (module, topic, question)", example = "topic")
    private String contentType;

    @Column(name = "visual_information", columnDefinition = "TEXT")
    @Schema(description = "JSON visual processing elements (diagrams, charts, animations)")
    private String visualInformation;

    @Column(name = "verbal_information", columnDefinition = "TEXT")
    @Schema(description = "JSON verbal processing elements (explanations, descriptions)")
    private String verbalInformation;

    @Column(name = "spatial_relationships", columnDefinition = "TEXT")
    @Schema(description = "JSON spatial visualization for system architectures")
    private String spatialRelationships;

    @Column(name = "temporal_sequences", columnDefinition = "TEXT")
    @Schema(description = "JSON process flow sequences and time-based learning")
    private String temporalSequences;

    @Min(0) @Max(100)
    @Column(name = "integration_effectiveness")
    @Schema(description = "Effectiveness of visual-verbal integration (0-100)", example = "85")
    private Integer integrationEffectiveness = 0;

    @Column(name = "amazon_architecture_examples", columnDefinition = "TEXT")
    @Schema(description = "JSON Amazon system examples and architecture patterns")
    private String amazonArchitectureExamples;

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
    public DualCodingApplication() {}

    public DualCodingApplication(Long contentId, String contentType) {
        this.contentId = contentId;
        this.contentType = contentType;
    }

    // Helper methods
    @Schema(description = "Check if visual information is available")
    public boolean hasVisualInformation() {
        return visualInformation != null && !visualInformation.trim().isEmpty();
    }

    @Schema(description = "Check if verbal information is available")
    public boolean hasVerbalInformation() {
        return verbalInformation != null && !verbalInformation.trim().isEmpty();
    }

    @Schema(description = "Check if spatial relationships are defined")
    public boolean hasSpatialRelationships() {
        return spatialRelationships != null && !spatialRelationships.trim().isEmpty();
    }

    @Schema(description = "Check if temporal sequences are available")
    public boolean hasTemporalSequences() {
        return temporalSequences != null && !temporalSequences.trim().isEmpty();
    }

    @Schema(description = "Check if Amazon architecture examples are integrated")
    public boolean hasAmazonArchitectureExamples() {
        return amazonArchitectureExamples != null && !amazonArchitectureExamples.trim().isEmpty();
    }

    @Schema(description = "Check if dual coding is properly implemented (both visual and verbal)")
    public boolean isDualCodingImplemented() {
        return hasVisualInformation() && hasVerbalInformation();
    }

    @Schema(description = "Get the number of coding channels implemented")
    public int getCodingChannelCount() {
        int count = 0;
        if (hasVisualInformation()) count++;
        if (hasVerbalInformation()) count++;
        if (hasSpatialRelationships()) count++;
        if (hasTemporalSequences()) count++;
        return count;
    }

    @Schema(description = "Get dual coding completeness level")
    public String getCompletenessLevel() {
        int channelCount = getCodingChannelCount();
        if (channelCount >= 4) return "Complete";
        if (channelCount >= 3) return "Advanced";
        if (channelCount >= 2) return "Basic";
        return "Minimal";
    }

    @Schema(description = "Get integration effectiveness category")
    public String getEffectivenessCategory() {
        if (integrationEffectiveness >= 90) return "Excellent";
        if (integrationEffectiveness >= 80) return "Very Good";
        if (integrationEffectiveness >= 70) return "Good";
        if (integrationEffectiveness >= 60) return "Fair";
        return "Needs Improvement";
    }

    @Schema(description = "Check if ready for Amazon-scale learning")
    public boolean isAmazonScaleReady() {
        return isDualCodingImplemented() && hasAmazonArchitectureExamples() && integrationEffectiveness >= 70;
    }

    @Schema(description = "Get learning modality strength")
    public String getLearningModalityStrength() {
        if (hasVisualInformation() && hasVerbalInformation()) {
            return "Balanced Visual-Verbal";
        } else if (hasVisualInformation()) {
            return "Visual Dominant";
        } else if (hasVerbalInformation()) {
            return "Verbal Dominant";
        }
        return "Undefined";
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getContentId() { return contentId; }
    public void setContentId(Long contentId) { this.contentId = contentId; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    public String getVisualInformation() { return visualInformation; }
    public void setVisualInformation(String visualInformation) { this.visualInformation = visualInformation; }

    public String getVerbalInformation() { return verbalInformation; }
    public void setVerbalInformation(String verbalInformation) { this.verbalInformation = verbalInformation; }

    public String getSpatialRelationships() { return spatialRelationships; }
    public void setSpatialRelationships(String spatialRelationships) { this.spatialRelationships = spatialRelationships; }

    public String getTemporalSequences() { return temporalSequences; }
    public void setTemporalSequences(String temporalSequences) { this.temporalSequences = temporalSequences; }

    public Integer getIntegrationEffectiveness() { return integrationEffectiveness; }
    public void setIntegrationEffectiveness(Integer integrationEffectiveness) { 
        this.integrationEffectiveness = integrationEffectiveness; 
    }

    public String getAmazonArchitectureExamples() { return amazonArchitectureExamples; }
    public void setAmazonArchitectureExamples(String amazonArchitectureExamples) { 
        this.amazonArchitectureExamples = amazonArchitectureExamples; 
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "DualCodingApplication{" +
                "id=" + id +
                ", contentId=" + contentId +
                ", contentType='" + contentType + '\'' +
                ", integrationEffectiveness=" + integrationEffectiveness +
                ", completenessLevel='" + getCompletenessLevel() + '\'' +
                ", codingChannels=" + getCodingChannelCount() +
                ", amazonScaleReady=" + isAmazonScaleReady() +
                '}';
    }
}