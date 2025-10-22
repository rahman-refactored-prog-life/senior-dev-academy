package com.learningportal.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Visual Learning Component Entity
 * 
 * Manages interactive visualizations, algorithm animations, infographics,
 * mind maps, and Amazon architecture diagrams for optimal visual learning.
 */
@Entity
@Table(name = "visual_learning_components")
@Schema(description = "Visual learning components for enhanced comprehension")
public class VisualLearningComponent {

    @Schema(description = "Types of visual learning components")
    public enum VisualComponentType {
        ALGORITHM_ANIMATION("Algorithm Animation", "Interactive step-by-step algorithm visualization"),
        INFOGRAPHIC("Infographic", "Complex concept summary with visual elements"),
        MIND_MAP("Mind Map", "Knowledge hierarchy and relationship visualization"),
        ARCHITECTURE_DIAGRAM("Architecture Diagram", "System architecture and component relationships"),
        FLOWCHART("Flowchart", "Process flow and decision tree visualization"),
        DATA_VISUALIZATION("Data Visualization", "Charts, graphs, and data representation"),
        INTERACTIVE_DIAGRAM("Interactive Diagram", "Clickable and explorable visual elements"),
        CONCEPT_MAP("Concept Map", "Conceptual relationships and connections"),
        TIMELINE("Timeline", "Chronological process or historical visualization"),
        COMPARISON_CHART("Comparison Chart", "Side-by-side feature or concept comparison");

        private final String displayName;
        private final String description;

        VisualComponentType(String displayName, String description) {
            this.displayName = displayName;
            this.description = description;
        }

        public String getDisplayName() { return displayName; }
        public String getDescription() { return description; }
    }

    @Schema(description = "Complexity levels for visual components")
    public enum ComplexityLevel {
        BEGINNER("Beginner", "Simple, clear visuals for zero-experience learners"),
        INTERMEDIATE("Intermediate", "Moderate complexity with additional details"),
        ADVANCED("Advanced", "Complex visuals with comprehensive information"),
        EXPERT("Expert", "Highly detailed visuals for expert-level understanding");

        private final String level;
        private final String description;

        ComplexityLevel(String level, String description) {
            this.level = level;
            this.description = description;
        }

        public String getLevel() { return level; }
        public String getDescription() { return description; }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the visual component", example = "1")
    private Long id;

    @NotNull(message = "Content ID is required")
    @Column(name = "content_id", nullable = false)
    @Schema(description = "Reference to the learning content", example = "123")
    private Long contentId;

    @NotNull(message = "Content type is required")
    @Column(name = "content_type", nullable = false, length = 100)
    @Schema(description = "Type of content (module, topic, question)", example = "topic")
    private String contentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "component_type", nullable = false)
    @Schema(description = "Type of visual component")
    private VisualComponentType componentType;

    @NotNull(message = "Title is required")
    @Column(name = "title", nullable = false, length = 300)
    @Schema(description = "Title of the visual component", example = "Binary Search Algorithm Animation")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    @Schema(description = "Detailed description of the visual component")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "complexity_level")
    @Schema(description = "Complexity level of the visual component")
    private ComplexityLevel complexityLevel = ComplexityLevel.BEGINNER;

    @Column(name = "visual_data", columnDefinition = "TEXT")
    @Schema(description = "JSON data for visual rendering (SVG, canvas data, etc.)")
    private String visualData;

    @Column(name = "interactive_elements", columnDefinition = "TEXT")
    @Schema(description = "JSON configuration for interactive elements")
    private String interactiveElements;

    @Column(name = "animation_config", columnDefinition = "TEXT")
    @Schema(description = "JSON configuration for animations and transitions")
    private String animationConfig;

    @Column(name = "amazon_context", columnDefinition = "TEXT")
    @Schema(description = "JSON Amazon-specific context and examples")
    private String amazonContext;

    @Min(0) @Max(100)
    @Column(name = "effectiveness_score")
    @Schema(description = "Learning effectiveness score (0-100)", example = "85")
    private Integer effectivenessScore = 0;

    @Min(1) @Max(10)
    @Column(name = "cognitive_load_level")
    @Schema(description = "Cognitive load level (1-10)", example = "5")
    private Integer cognitiveLoadLevel = 5;

    @Column(name = "accessibility_features", columnDefinition = "TEXT")
    @Schema(description = "JSON accessibility features and alternatives")
    private String accessibilityFeatures;

    @Column(name = "mobile_optimized")
    @Schema(description = "Whether the component is optimized for mobile devices")
    private Boolean mobileOptimized = false;

    @Column(name = "view_count")
    @Schema(description = "Number of times this component has been viewed")
    private Long viewCount = 0L;

    @Column(name = "average_view_duration")
    @Schema(description = "Average viewing duration in seconds")
    private Integer averageViewDuration = 0;

    @Column(name = "is_active")
    @Schema(description = "Whether the component is active and available")
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

    /**
     * Check if component is highly effective
     */
    public boolean isHighlyEffective() {
        return effectivenessScore >= 80;
    }

    /**
     * Check if component has low cognitive load
     */
    public boolean hasLowCognitiveLoad() {
        return cognitiveLoadLevel <= 4;
    }

    /**
     * Check if component is suitable for beginners
     */
    public boolean isSuitableForBeginners() {
        return complexityLevel == ComplexityLevel.BEGINNER && hasLowCognitiveLoad();
    }

    /**
     * Get engagement level based on view metrics
     */
    public String getEngagementLevel() {
        if (viewCount == 0) return "No Data";
        
        double avgDuration = averageViewDuration.doubleValue();
        if (avgDuration >= 300) return "High Engagement"; // 5+ minutes
        if (avgDuration >= 120) return "Good Engagement"; // 2+ minutes
        if (avgDuration >= 60) return "Moderate Engagement"; // 1+ minute
        return "Low Engagement";
    }

    /**
     * Get visual learning effectiveness rating
     */
    public String getEffectivenessRating() {
        if (effectivenessScore >= 90) return "Excellent";
        if (effectivenessScore >= 80) return "Very Good";
        if (effectivenessScore >= 70) return "Good";
        if (effectivenessScore >= 60) return "Fair";
        return "Needs Improvement";
    }

    /**
     * Check if component has Amazon context
     */
    public boolean hasAmazonContext() {
        return amazonContext != null && !amazonContext.trim().isEmpty();
    }

    /**
     * Check if component is interactive
     */
    public boolean isInteractive() {
        return interactiveElements != null && !interactiveElements.trim().isEmpty();
    }

    /**
     * Check if component is animated
     */
    public boolean isAnimated() {
        return animationConfig != null && !animationConfig.trim().isEmpty();
    }

    /**
     * Check if component is accessible
     */
    public boolean isAccessible() {
        return accessibilityFeatures != null && !accessibilityFeatures.trim().isEmpty();
    }

    /**
     * Get cognitive load description
     */
    public String getCognitiveLoadDescription() {
        return switch (cognitiveLoadLevel) {
            case 1, 2 -> "Very Low - Minimal mental effort required";
            case 3, 4 -> "Low - Easy to process and understand";
            case 5, 6 -> "Moderate - Requires focused attention";
            case 7, 8 -> "High - Demanding cognitive processing";
            case 9, 10 -> "Very High - Maximum mental effort required";
            default -> "Unknown";
        };
    }

    /**
     * Generate learning recommendations based on component characteristics
     */
    public String generateLearningRecommendations() {
        StringBuilder recommendations = new StringBuilder();
        
        if (hasLowCognitiveLoad()) {
            recommendations.append("Suitable for extended learning sessions. ");
        } else {
            recommendations.append("Take breaks during study to prevent cognitive overload. ");
        }
        
        if (isInteractive()) {
            recommendations.append("Engage with interactive elements for better retention. ");
        }
        
        if (isAnimated()) {
            recommendations.append("Watch animations multiple times to understand the process. ");
        }
        
        if (hasAmazonContext()) {
            recommendations.append("Pay attention to Amazon-specific examples for interview preparation. ");
        }
        
        if (isSuitableForBeginners()) {
            recommendations.append("Perfect starting point for zero-experience learners. ");
        }
        
        return recommendations.length() > 0 ? recommendations.toString() : "Standard visual learning component.";
    }

    /**
     * Update view statistics
     */
    public void updateViewStatistics(int viewDurationSeconds) {
        this.viewCount++;
        
        // Calculate new average duration
        if (this.averageViewDuration == 0) {
            this.averageViewDuration = viewDurationSeconds;
        } else {
            // Weighted average with more weight on recent views
            this.averageViewDuration = (int) Math.round(
                (this.averageViewDuration * 0.8) + (viewDurationSeconds * 0.2)
            );
        }
        
        this.updatedAt = LocalDateTime.now();
    }

    // Constructors
    public VisualLearningComponent() {}

    public VisualLearningComponent(Long contentId, String contentType, VisualComponentType componentType, String title) {
        this.contentId = contentId;
        this.contentType = contentType;
        this.componentType = componentType;
        this.title = title;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getContentId() { return contentId; }
    public void setContentId(Long contentId) { this.contentId = contentId; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    public VisualComponentType getComponentType() { return componentType; }
    public void setComponentType(VisualComponentType componentType) { this.componentType = componentType; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public ComplexityLevel getComplexityLevel() { return complexityLevel; }
    public void setComplexityLevel(ComplexityLevel complexityLevel) { this.complexityLevel = complexityLevel; }

    public String getVisualData() { return visualData; }
    public void setVisualData(String visualData) { this.visualData = visualData; }

    public String getInteractiveElements() { return interactiveElements; }
    public void setInteractiveElements(String interactiveElements) { this.interactiveElements = interactiveElements; }

    public String getAnimationConfig() { return animationConfig; }
    public void setAnimationConfig(String animationConfig) { this.animationConfig = animationConfig; }

    public String getAmazonContext() { return amazonContext; }
    public void setAmazonContext(String amazonContext) { this.amazonContext = amazonContext; }

    public Integer getEffectivenessScore() { return effectivenessScore; }
    public void setEffectivenessScore(Integer effectivenessScore) { this.effectivenessScore = effectivenessScore; }

    public Integer getCognitiveLoadLevel() { return cognitiveLoadLevel; }
    public void setCognitiveLoadLevel(Integer cognitiveLoadLevel) { this.cognitiveLoadLevel = cognitiveLoadLevel; }

    public String getAccessibilityFeatures() { return accessibilityFeatures; }
    public void setAccessibilityFeatures(String accessibilityFeatures) { this.accessibilityFeatures = accessibilityFeatures; }

    public Boolean getMobileOptimized() { return mobileOptimized; }
    public void setMobileOptimized(Boolean mobileOptimized) { this.mobileOptimized = mobileOptimized; }

    public Long getViewCount() { return viewCount; }
    public void setViewCount(Long viewCount) { this.viewCount = viewCount; }

    public Integer getAverageViewDuration() { return averageViewDuration; }
    public void setAverageViewDuration(Integer averageViewDuration) { this.averageViewDuration = averageViewDuration; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "VisualLearningComponent{" +
                "id=" + id +
                ", contentId=" + contentId +
                ", componentType=" + componentType +
                ", title='" + title + '\'' +
                ", complexityLevel=" + complexityLevel +
                ", effectivenessScore=" + effectivenessScore +
                ", cognitiveLoadLevel=" + cognitiveLoadLevel +
                ", viewCount=" + viewCount +
                ", isActive=" + isActive +
                '}';
    }
}