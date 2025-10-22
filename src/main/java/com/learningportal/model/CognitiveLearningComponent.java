package com.learningportal.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * Cognitive Learning Component Entity
 * 
 * Represents individual cognitive science components like Feynman Technique,
 * Dual Coding Theory, Bloom's Taxonomy, and Spaced Repetition.
 */
@Entity
@Table(name = "cognitive_learning_components")
@Schema(description = "Individual cognitive science learning components")
public class CognitiveLearningComponent {

    @Schema(description = "Types of cognitive learning components")
    public enum ComponentType {
        FEYNMAN("Feynman Technique"),
        DUAL_CODING("Dual Coding Theory"),
        BLOOMS("Bloom's Taxonomy"),
        SPACED_REPETITION("Spaced Repetition"),
        COGNITIVE_LOAD("Cognitive Load Theory");

        private final String displayName;

        ComponentType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the cognitive component", example = "1")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "component_type", nullable = false)
    @Schema(description = "Type of cognitive learning component")
    private ComponentType componentType;

    @NotBlank(message = "Implementation details are required")
    @Column(name = "implementation_details", columnDefinition = "TEXT", nullable = false)
    @Schema(description = "JSON implementation specifics for the component")
    private String implementationDetails;

    @Column(name = "amazon_integration", columnDefinition = "TEXT")
    @Schema(description = "JSON Amazon-specific integration details")
    private String amazonIntegration;

    @Column(name = "effectiveness_metrics", columnDefinition = "TEXT")
    @Schema(description = "JSON effectiveness tracking metrics")
    private String effectivenessMetrics;

    @Column(name = "validation_criteria", columnDefinition = "TEXT")
    @Schema(description = "JSON validation rules for the component")
    private String validationCriteria;

    @Column(name = "order_index")
    @Schema(description = "Order of execution in the learning sequence", example = "1")
    private Integer orderIndex = 0;

    @Column(name = "is_active")
    @Schema(description = "Whether the component is currently active")
    private Boolean isActive = true;

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
    public CognitiveLearningComponent() {}

    public CognitiveLearningComponent(ComponentType componentType, String implementationDetails, 
                                    String amazonIntegration, Integer orderIndex) {
        this.componentType = componentType;
        this.implementationDetails = implementationDetails;
        this.amazonIntegration = amazonIntegration;
        this.orderIndex = orderIndex;
    }

    // Helper methods
    @Schema(description = "Get the display name of the component type")
    public String getComponentTypeDisplayName() {
        return componentType != null ? componentType.getDisplayName() : null;
    }

    @Schema(description = "Check if the component is Amazon-integrated")
    public boolean hasAmazonIntegration() {
        return amazonIntegration != null && !amazonIntegration.trim().isEmpty();
    }

    @Schema(description = "Check if effectiveness metrics are available")
    public boolean hasEffectivenessMetrics() {
        return effectivenessMetrics != null && !effectivenessMetrics.trim().isEmpty();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ComponentType getComponentType() { return componentType; }
    public void setComponentType(ComponentType componentType) { this.componentType = componentType; }

    public String getImplementationDetails() { return implementationDetails; }
    public void setImplementationDetails(String implementationDetails) { 
        this.implementationDetails = implementationDetails; 
    }

    public String getAmazonIntegration() { return amazonIntegration; }
    public void setAmazonIntegration(String amazonIntegration) { this.amazonIntegration = amazonIntegration; }

    public String getEffectivenessMetrics() { return effectivenessMetrics; }
    public void setEffectivenessMetrics(String effectivenessMetrics) { 
        this.effectivenessMetrics = effectivenessMetrics; 
    }

    public String getValidationCriteria() { return validationCriteria; }
    public void setValidationCriteria(String validationCriteria) { this.validationCriteria = validationCriteria; }

    public Integer getOrderIndex() { return orderIndex; }
    public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "CognitiveLearningComponent{" +
                "id=" + id +
                ", componentType=" + componentType +
                ", orderIndex=" + orderIndex +
                ", isActive=" + isActive +
                '}';
    }
}