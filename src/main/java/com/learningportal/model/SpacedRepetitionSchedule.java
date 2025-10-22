package com.learningportal.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Spaced Repetition Schedule Entity
 * 
 * Implements scientifically-optimized review schedules for long-term retention
 * with Amazon interview preparation patterns and adaptive scheduling.
 */
@Entity
@Table(name = "spaced_repetition_schedule")
@Schema(description = "Spaced repetition scheduling for optimal learning retention")
public class SpacedRepetitionSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the schedule", example = "1")
    private Long id;

    @NotNull(message = "User ID is required")
    @Column(name = "user_id", nullable = false)
    @Schema(description = "Reference to the user", example = "123")
    private Long userId;

    @NotNull(message = "Content ID is required")
    @Column(name = "content_id", nullable = false)
    @Schema(description = "Reference to the learning content", example = "456")
    private Long contentId;

    @NotNull(message = "Content type is required")
    @Column(name = "content_type", nullable = false, length = 100)
    @Schema(description = "Type of content (module, topic, question)", example = "topic")
    private String contentType;

    @Min(1)
    @Column(name = "repetition_interval")
    @Schema(description = "Days until next review", example = "3")
    private Integer repetitionInterval = 1;

    @Column(name = "ease_factor", precision = 3, scale = 2)
    @Schema(description = "Spaced repetition ease factor (1.3-2.5)", example = "2.5")
    private BigDecimal easeFactor = BigDecimal.valueOf(2.5);

    @Min(0)
    @Column(name = "repetition_count")
    @Schema(description = "Number of times reviewed", example = "5")
    private Integer repetitionCount = 0;

    @Column(name = "last_reviewed")
    @Schema(description = "Last review timestamp")
    private LocalDateTime lastReviewed;

    @Column(name = "next_review_date")
    @Schema(description = "Next scheduled review date")
    private LocalDateTime nextReviewDate;

    @Min(0) @Max(100)
    @Column(name = "retention_score")
    @Schema(description = "Retention measurement score (0-100)", example = "85")
    private Integer retentionScore = 0;

    @Column(name = "amazon_interview_priority")
    @Schema(description = "High priority for Amazon interview preparation")
    private Boolean amazonInterviewPriority = false;

    @Column(name = "difficulty_adjustment", precision = 3, scale = 2)
    @Schema(description = "Difficulty adjustment factor", example = "1.0")
    private BigDecimal difficultyAdjustment = BigDecimal.valueOf(1.0);

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
        if (nextReviewDate == null) {
            nextReviewDate = LocalDateTime.now().plusDays(repetitionInterval);
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Calculate next review interval based on performance
     */
    public void calculateNextInterval(int performanceRating) {
        // SM-2 Algorithm implementation
        if (performanceRating >= 3) {
            // Successful recall
            if (repetitionCount == 0) {
                repetitionInterval = 1;
            } else if (repetitionCount == 1) {
                repetitionInterval = 6;
            } else {
                repetitionInterval = (int) Math.round(repetitionInterval * easeFactor.doubleValue());
            }
            repetitionCount++;
        } else {
            // Failed recall - reset interval
            repetitionCount = 0;
            repetitionInterval = 1;
        }

        // Update ease factor based on performance
        BigDecimal newEaseFactor = easeFactor.add(
            BigDecimal.valueOf(0.1 - (5 - performanceRating) * (0.08 + (5 - performanceRating) * 0.02))
        );
        
        // Ensure ease factor stays within bounds
        if (newEaseFactor.compareTo(BigDecimal.valueOf(1.3)) < 0) {
            easeFactor = BigDecimal.valueOf(1.3);
        } else {
            easeFactor = newEaseFactor;
        }

        // Apply difficulty adjustment
        double adjustedInterval = repetitionInterval * difficultyAdjustment.doubleValue();
        repetitionInterval = Math.max(1, (int) Math.round(adjustedInterval));

        // Amazon interview priority adjustment
        if (amazonInterviewPriority) {
            repetitionInterval = Math.max(1, repetitionInterval / 2); // More frequent reviews
        }

        // Set next review date
        nextReviewDate = LocalDateTime.now().plusDays(repetitionInterval);
        lastReviewed = LocalDateTime.now();
    }

    /**
     * Check if review is due
     */
    public boolean isReviewDue() {
        return nextReviewDate != null && LocalDateTime.now().isAfter(nextReviewDate);
    }

    /**
     * Check if overdue for review
     */
    public boolean isOverdue() {
        if (nextReviewDate == null) return false;
        return LocalDateTime.now().isAfter(nextReviewDate.plusDays(1));
    }

    /**
     * Get days until next review
     */
    public long getDaysUntilNextReview() {
        if (nextReviewDate == null) return 0;
        return java.time.Duration.between(LocalDateTime.now(), nextReviewDate).toDays();
    }

    /**
     * Get retention strength indicator
     */
    public String getRetentionStrength() {
        if (retentionScore >= 90) return "Excellent";
        if (retentionScore >= 80) return "Good";
        if (retentionScore >= 70) return "Fair";
        if (retentionScore >= 60) return "Weak";
        return "Very Weak";
    }

    /**
     * Get learning efficiency score
     */
    public double getLearningEfficiency() {
        if (repetitionCount == 0) return 0.0;
        return (double) retentionScore / repetitionCount;
    }

    /**
     * Check if ready for Amazon interview
     */
    public boolean isAmazonInterviewReady() {
        return amazonInterviewPriority && 
               retentionScore >= 85 && 
               repetitionCount >= 3 &&
               easeFactor.compareTo(BigDecimal.valueOf(2.0)) >= 0;
    }

    /**
     * Get priority level for scheduling
     */
    public int getPriorityLevel() {
        int priority = 1; // Base priority
        
        if (amazonInterviewPriority) priority += 3;
        if (isOverdue()) priority += 2;
        if (isReviewDue()) priority += 1;
        if (retentionScore < 70) priority += 1;
        
        return Math.min(5, priority); // Max priority of 5
    }

    /**
     * Generate review recommendation
     */
    public String getReviewRecommendation() {
        if (isOverdue()) {
            return "Urgent: Review overdue by " + 
                   java.time.Duration.between(nextReviewDate, LocalDateTime.now()).toDays() + " days";
        }
        if (isReviewDue()) {
            return "Due for review now";
        }
        if (getDaysUntilNextReview() <= 1) {
            return "Review due tomorrow";
        }
        if (retentionScore < 70) {
            return "Consider additional practice - retention below optimal";
        }
        return "On track - next review in " + getDaysUntilNextReview() + " days";
    }

    // Constructors
    public SpacedRepetitionSchedule() {}

    public SpacedRepetitionSchedule(Long userId, Long contentId, String contentType) {
        this.userId = userId;
        this.contentId = contentId;
        this.contentType = contentType;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getContentId() { return contentId; }
    public void setContentId(Long contentId) { this.contentId = contentId; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    public Integer getRepetitionInterval() { return repetitionInterval; }
    public void setRepetitionInterval(Integer repetitionInterval) { this.repetitionInterval = repetitionInterval; }

    public BigDecimal getEaseFactor() { return easeFactor; }
    public void setEaseFactor(BigDecimal easeFactor) { this.easeFactor = easeFactor; }

    public Integer getRepetitionCount() { return repetitionCount; }
    public void setRepetitionCount(Integer repetitionCount) { this.repetitionCount = repetitionCount; }

    public LocalDateTime getLastReviewed() { return lastReviewed; }
    public void setLastReviewed(LocalDateTime lastReviewed) { this.lastReviewed = lastReviewed; }

    public LocalDateTime getNextReviewDate() { return nextReviewDate; }
    public void setNextReviewDate(LocalDateTime nextReviewDate) { this.nextReviewDate = nextReviewDate; }

    public Integer getRetentionScore() { return retentionScore; }
    public void setRetentionScore(Integer retentionScore) { this.retentionScore = retentionScore; }

    public Boolean getAmazonInterviewPriority() { return amazonInterviewPriority; }
    public void setAmazonInterviewPriority(Boolean amazonInterviewPriority) { 
        this.amazonInterviewPriority = amazonInterviewPriority; 
    }

    public BigDecimal getDifficultyAdjustment() { return difficultyAdjustment; }
    public void setDifficultyAdjustment(BigDecimal difficultyAdjustment) { 
        this.difficultyAdjustment = difficultyAdjustment; 
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "SpacedRepetitionSchedule{" +
                "id=" + id +
                ", userId=" + userId +
                ", contentId=" + contentId +
                ", contentType='" + contentType + '\'' +
                ", repetitionInterval=" + repetitionInterval +
                ", repetitionCount=" + repetitionCount +
                ", retentionScore=" + retentionScore +
                ", nextReviewDate=" + nextReviewDate +
                ", amazonInterviewPriority=" + amazonInterviewPriority +
                '}';
    }
}