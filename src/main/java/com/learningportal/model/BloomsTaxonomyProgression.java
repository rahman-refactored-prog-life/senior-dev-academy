package com.learningportal.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Bloom's Taxonomy Progression Entity
 * 
 * Tracks user progression through Bloom's Taxonomy levels:
 * Remember → Understand → Apply → Analyze → Evaluate → Create
 * with Amazon L3-L6 competency alignment and evidence-based progression.
 */
@Entity
@Table(name = "blooms_taxonomy_progression")
@Schema(description = "Bloom's Taxonomy progression tracking with Amazon competency alignment")
public class BloomsTaxonomyProgression {

    @Schema(description = "Bloom's Taxonomy learning levels")
    public enum BloomsLevel {
        REMEMBER("Remember", "Recall facts and basic concepts", "L3"),
        UNDERSTAND("Understand", "Explain ideas or concepts", "L3-L4"),
        APPLY("Apply", "Use information in new situations", "L4"),
        ANALYZE("Analyze", "Draw connections among ideas", "L4-L5"),
        EVALUATE("Evaluate", "Justify a stand or decision", "L5"),
        CREATE("Create", "Produce new or original work", "L5-L6");

        private final String levelName;
        private final String description;
        private final String amazonLevel;

        BloomsLevel(String levelName, String description, String amazonLevel) {
            this.levelName = levelName;
            this.description = description;
            this.amazonLevel = amazonLevel;
        }

        public String getLevelName() { return levelName; }
        public String getDescription() { return description; }
        public String getAmazonLevel() { return amazonLevel; }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the progression record", example = "1")
    private Long id;

    @NotNull(message = "User ID is required")
    @Column(name = "user_id", nullable = false)
    @Schema(description = "Reference to the user", example = "123")
    private Long userId;

    @NotNull(message = "Content ID is required")
    @Column(name = "content_id", nullable = false)
    @Schema(description = "Reference to the learning content", example = "456")
    private Long contentId;

    @Min(0) @Max(100)
    @Column(name = "remember_level_score")
    @Schema(description = "Score for Remember level (0-100)", example = "85")
    private Integer rememberLevelScore = 0;

    @Min(0) @Max(100)
    @Column(name = "understand_level_score")
    @Schema(description = "Score for Understand level (0-100)", example = "75")
    private Integer understandLevelScore = 0;

    @Min(0) @Max(100)
    @Column(name = "apply_level_score")
    @Schema(description = "Score for Apply level (0-100)", example = "65")
    private Integer applyLevelScore = 0;

    @Min(0) @Max(100)
    @Column(name = "analyze_level_score")
    @Schema(description = "Score for Analyze level (0-100)", example = "55")
    private Integer analyzeLevelScore = 0;

    @Min(0) @Max(100)
    @Column(name = "evaluate_level_score")
    @Schema(description = "Score for Evaluate level (0-100)", example = "45")
    private Integer evaluateLevelScore = 0;

    @Min(0) @Max(100)
    @Column(name = "create_level_score")
    @Schema(description = "Score for Create level (0-100)", example = "35")
    private Integer createLevelScore = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_level")
    @Schema(description = "Current Bloom's Taxonomy level")
    private BloomsLevel currentLevel = BloomsLevel.REMEMBER;

    @Column(name = "amazon_competency_alignment")
    @Schema(description = "Amazon competency level alignment (L3, L4, L5, L6)", example = "L4")
    private String amazonCompetencyAlignment;

    @Column(name = "progression_evidence", columnDefinition = "TEXT")
    @Schema(description = "JSON evidence of progression through levels")
    private String progressionEvidence;

    @Column(name = "next_level_requirements", columnDefinition = "TEXT")
    @Schema(description = "JSON requirements for advancing to next level")
    private String nextLevelRequirements;

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
        updateAmazonAlignment();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        updateAmazonAlignment();
    }

    // Helper methods
    @Schema(description = "Get overall progression score (average of all levels)")
    public Double getOverallProgressionScore() {
        return (rememberLevelScore + understandLevelScore + applyLevelScore + 
                analyzeLevelScore + evaluateLevelScore + createLevelScore) / 6.0;
    }

    @Schema(description = "Get current level score")
    public Integer getCurrentLevelScore() {
        switch (currentLevel) {
            case REMEMBER: return rememberLevelScore;
            case UNDERSTAND: return understandLevelScore;
            case APPLY: return applyLevelScore;
            case ANALYZE: return analyzeLevelScore;
            case EVALUATE: return evaluateLevelScore;
            case CREATE: return createLevelScore;
            default: return 0;
        }
    }

    @Schema(description = "Check if ready to advance to next level")
    public boolean isReadyForNextLevel() {
        return getCurrentLevelScore() >= 80; // 80% mastery required for advancement
    }

    @Schema(description = "Get next Bloom's level")
    public BloomsLevel getNextLevel() {
        BloomsLevel[] levels = BloomsLevel.values();
        int currentIndex = currentLevel.ordinal();
        return currentIndex < levels.length - 1 ? levels[currentIndex + 1] : currentLevel;
    }

    @Schema(description = "Check if progression evidence exists")
    public boolean hasProgressionEvidence() {
        return progressionEvidence != null && !progressionEvidence.trim().isEmpty();
    }

    @Schema(description = "Check if next level requirements are defined")
    public boolean hasNextLevelRequirements() {
        return nextLevelRequirements != null && !nextLevelRequirements.trim().isEmpty();
    }

    @Schema(description = "Get progression completion percentage")
    public Double getProgressionCompletionPercentage() {
        double totalPossible = 600.0; // 6 levels × 100 points each
        double currentTotal = rememberLevelScore + understandLevelScore + applyLevelScore + 
                             analyzeLevelScore + evaluateLevelScore + createLevelScore;
        return (currentTotal / totalPossible) * 100.0;
    }

    @Schema(description = "Get Amazon competency readiness level")
    public String getAmazonCompetencyReadiness() {
        double overallScore = getOverallProgressionScore();
        if (overallScore >= 90 && currentLevel.ordinal() >= BloomsLevel.CREATE.ordinal()) return "L6 Ready";
        if (overallScore >= 80 && currentLevel.ordinal() >= BloomsLevel.EVALUATE.ordinal()) return "L5 Ready";
        if (overallScore >= 70 && currentLevel.ordinal() >= BloomsLevel.ANALYZE.ordinal()) return "L4 Ready";
        if (overallScore >= 60 && currentLevel.ordinal() >= BloomsLevel.APPLY.ordinal()) return "L4 Developing";
        if (overallScore >= 50 && currentLevel.ordinal() >= BloomsLevel.UNDERSTAND.ordinal()) return "L3 Ready";
        return "L3 Developing";
    }

    /**
     * Update Amazon competency alignment based on current progression
     */
    private void updateAmazonAlignment() {
        this.amazonCompetencyAlignment = getAmazonCompetencyReadiness();
    }

    @Schema(description = "Check if at Amazon Senior SDE level (L5+)")
    public boolean isAmazonSeniorSDELevel() {
        return getOverallProgressionScore() >= 80 && currentLevel.ordinal() >= BloomsLevel.EVALUATE.ordinal();
    }

    @Schema(description = "Get learning velocity (progression rate)")
    public String getLearningVelocity() {
        if (createdAt == null || updatedAt == null) return "Unknown";
        
        long daysSinceCreation = java.time.Duration.between(createdAt, updatedAt).toDays();
        if (daysSinceCreation == 0) return "Just Started";
        
        double progressionRate = getProgressionCompletionPercentage() / daysSinceCreation;
        if (progressionRate >= 10) return "Very Fast";
        if (progressionRate >= 5) return "Fast";
        if (progressionRate >= 2) return "Moderate";
        if (progressionRate >= 1) return "Slow";
        return "Very Slow";
    }

    // Constructors
    public BloomsTaxonomyProgression() {}

    public BloomsTaxonomyProgression(Long userId, Long contentId) {
        this.userId = userId;
        this.contentId = contentId;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getContentId() { return contentId; }
    public void setContentId(Long contentId) { this.contentId = contentId; }

    public Integer getRememberLevelScore() { return rememberLevelScore; }
    public void setRememberLevelScore(Integer rememberLevelScore) { this.rememberLevelScore = rememberLevelScore; }

    public Integer getUnderstandLevelScore() { return understandLevelScore; }
    public void setUnderstandLevelScore(Integer understandLevelScore) { this.understandLevelScore = understandLevelScore; }

    public Integer getApplyLevelScore() { return applyLevelScore; }
    public void setApplyLevelScore(Integer applyLevelScore) { this.applyLevelScore = applyLevelScore; }

    public Integer getAnalyzeLevelScore() { return analyzeLevelScore; }
    public void setAnalyzeLevelScore(Integer analyzeLevelScore) { this.analyzeLevelScore = analyzeLevelScore; }

    public Integer getEvaluateLevelScore() { return evaluateLevelScore; }
    public void setEvaluateLevelScore(Integer evaluateLevelScore) { this.evaluateLevelScore = evaluateLevelScore; }

    public Integer getCreateLevelScore() { return createLevelScore; }
    public void setCreateLevelScore(Integer createLevelScore) { this.createLevelScore = createLevelScore; }

    public BloomsLevel getCurrentLevel() { return currentLevel; }
    public void setCurrentLevel(BloomsLevel currentLevel) { this.currentLevel = currentLevel; }

    public String getAmazonCompetencyAlignment() { return amazonCompetencyAlignment; }
    public void setAmazonCompetencyAlignment(String amazonCompetencyAlignment) { 
        this.amazonCompetencyAlignment = amazonCompetencyAlignment; 
    }

    public String getProgressionEvidence() { return progressionEvidence; }
    public void setProgressionEvidence(String progressionEvidence) { this.progressionEvidence = progressionEvidence; }

    public String getNextLevelRequirements() { return nextLevelRequirements; }
    public void setNextLevelRequirements(String nextLevelRequirements) { 
        this.nextLevelRequirements = nextLevelRequirements; 
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "BloomsTaxonomyProgression{" +
                "id=" + id +
                ", userId=" + userId +
                ", contentId=" + contentId +
                ", currentLevel=" + currentLevel +
                ", overallScore=" + getOverallProgressionScore() +
                ", amazonReadiness='" + getAmazonCompetencyReadiness() + '\'' +
                ", learningVelocity='" + getLearningVelocity() + '\'' +
                '}';
    }
}