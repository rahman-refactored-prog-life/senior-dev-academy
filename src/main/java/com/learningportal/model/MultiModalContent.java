package com.learningportal.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Multi-Modal Content Entity
 * 
 * Represents multi-modal learning content with visual, auditory, kinesthetic,
 * and reading/writing components, integrated with Amazon context.
 */
@Entity
@Table(name = "multi_modal_content")
@Schema(description = "Multi-modal learning content with Amazon integration")
public class MultiModalContent {

    @Schema(description = "Types of content that can have multi-modal delivery")
    public enum ContentType {
        MODULE("Learning Module"),
        TOPIC("Learning Topic"),
        QUESTION("Interview Question"),
        EXERCISE("Practice Exercise"),
        ASSESSMENT("Assessment");

        private final String displayName;

        ContentType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the multi-modal content", example = "1")
    private Long id;

    @NotNull(message = "Content ID is required")
    @Column(name = "content_id", nullable = false)
    @Schema(description = "Reference to the learning content", example = "123")
    private Long contentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    @Schema(description = "Type of content being enhanced with multi-modal delivery")
    private ContentType contentType;

    @Column(name = "visual_component", columnDefinition = "TEXT")
    @Schema(description = "JSON with diagrams, animations, infographics")
    private String visualComponent;

    @Column(name = "auditory_component", columnDefinition = "TEXT")
    @Schema(description = "JSON with audio explanations, pronunciations")
    private String auditoryComponent;

    @Column(name = "kinesthetic_component", columnDefinition = "TEXT")
    @Schema(description = "JSON with hands-on exercises, drag-drop activities")
    private String kinestheticComponent;

    @Column(name = "reading_writing_component", columnDefinition = "TEXT")
    @Schema(description = "JSON with documentation, notes, essay prompts")
    private String readingWritingComponent;

    @Column(name = "amazon_context_integration", columnDefinition = "TEXT")
    @Schema(description = "JSON Amazon-specific examples and context")
    private String amazonContextIntegration;

    @Min(0) @Max(100)
    @Column(name = "effectiveness_score")
    @Schema(description = "Effectiveness rating from 0-100", example = "85")
    private Integer effectivenessScore = 0;

    @Min(1) @Max(10)
    @Column(name = "cognitive_load_level")
    @Schema(description = "Cognitive complexity level from 1-10", example = "5")
    private Integer cognitiveLoadLevel = 5;

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
    public MultiModalContent() {}

    public MultiModalContent(Long contentId, ContentType contentType) {
        this.contentId = contentId;
        this.contentType = contentType;
    }

    // Helper methods
    @Schema(description = "Check if visual component is available")
    public boolean hasVisualComponent() {
        return visualComponent != null && !visualComponent.trim().isEmpty();
    }

    @Schema(description = "Check if auditory component is available")
    public boolean hasAuditoryComponent() {
        return auditoryComponent != null && !auditoryComponent.trim().isEmpty();
    }

    @Schema(description = "Check if kinesthetic component is available")
    public boolean hasKinestheticComponent() {
        return kinestheticComponent != null && !kinestheticComponent.trim().isEmpty();
    }

    @Schema(description = "Check if reading/writing component is available")
    public boolean hasReadingWritingComponent() {
        return readingWritingComponent != null && !readingWritingComponent.trim().isEmpty();
    }

    @Schema(description = "Check if Amazon context integration is available")
    public boolean hasAmazonContextIntegration() {
        return amazonContextIntegration != null && !amazonContextIntegration.trim().isEmpty();
    }

    @Schema(description = "Get the number of available learning modalities")
    public int getAvailableModalityCount() {
        int count = 0;
        if (hasVisualComponent()) count++;
        if (hasAuditoryComponent()) count++;
        if (hasKinestheticComponent()) count++;
        if (hasReadingWritingComponent()) count++;
        return count;
    }

    @Schema(description = "Check if content is multi-modal (has 2+ modalities)")
    public boolean isMultiModal() {
        return getAvailableModalityCount() >= 2;
    }

    @Schema(description = "Get cognitive load category")
    public String getCognitiveLoadCategory() {
        if (cognitiveLoadLevel <= 3) return "Low";
        if (cognitiveLoadLevel <= 7) return "Medium";
        return "High";
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getContentId() { return contentId; }
    public void setContentId(Long contentId) { this.contentId = contentId; }

    public ContentType getContentType() { return contentType; }
    public void setContentType(ContentType contentType) { this.contentType = contentType; }

    public String getVisualComponent() { return visualComponent; }
    public void setVisualComponent(String visualComponent) { this.visualComponent = visualComponent; }

    public String getAuditoryComponent() { return auditoryComponent; }
    public void setAuditoryComponent(String auditoryComponent) { this.auditoryComponent = auditoryComponent; }

    public String getKinestheticComponent() { return kinestheticComponent; }
    public void setKinestheticComponent(String kinestheticComponent) { this.kinestheticComponent = kinestheticComponent; }

    public String getReadingWritingComponent() { return readingWritingComponent; }
    public void setReadingWritingComponent(String readingWritingComponent) { 
        this.readingWritingComponent = readingWritingComponent; 
    }

    public String getAmazonContextIntegration() { return amazonContextIntegration; }
    public void setAmazonContextIntegration(String amazonContextIntegration) { 
        this.amazonContextIntegration = amazonContextIntegration; 
    }

    public Integer getEffectivenessScore() { return effectivenessScore; }
    public void setEffectivenessScore(Integer effectivenessScore) { this.effectivenessScore = effectivenessScore; }

    public Integer getCognitiveLoadLevel() { return cognitiveLoadLevel; }
    public void setCognitiveLoadLevel(Integer cognitiveLoadLevel) { this.cognitiveLoadLevel = cognitiveLoadLevel; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "MultiModalContent{" +
                "id=" + id +
                ", contentId=" + contentId +
                ", contentType=" + contentType +
                ", effectivenessScore=" + effectivenessScore +
                ", cognitiveLoadLevel=" + cognitiveLoadLevel +
                ", modalityCount=" + getAvailableModalityCount() +
                '}';
    }
}