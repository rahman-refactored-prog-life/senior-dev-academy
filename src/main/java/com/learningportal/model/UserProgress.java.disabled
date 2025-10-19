package com.learningportal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * User Progress Entity - Tracks learning progress for users
 * 
 * This entity demonstrates:
 * - Composite relationships (User + LearningModule/Topic)
 * - Progress tracking with percentages and timestamps
 * - Enum usage for status tracking
 * - Unique constraints on composite keys
 * - Performance considerations with indexes
 */
@Entity
@Table(name = "user_progress", 
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"user_id", "module_id"}),
           @UniqueConstraint(columnNames = {"user_id", "topic_id"})
       },
       indexes = {
           @Index(name = "idx_user_progress_user", columnList = "user_id"),
           @Index(name = "idx_user_progress_module", columnList = "module_id"),
           @Index(name = "idx_user_progress_status", columnList = "status"),
           @Index(name = "idx_user_progress_updated", columnList = "updated_at")
       })
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = {"user", "module", "topic"})
public class UserProgress {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Progress percentage (0-100)
     * Using validation to ensure valid range
     */
    @NotNull(message = "Progress percentage is required")
    @Min(value = 0, message = "Progress cannot be negative")
    @Max(value = 100, message = "Progress cannot exceed 100%")
    @Column(name = "progress_percentage", nullable = false)
    private Integer progressPercentage = 0;
    
    /**
     * Current status of the learning item
     */
    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProgressStatus status = ProgressStatus.NOT_STARTED;
    
    /**
     * Time spent on this item in minutes
     */
    @Column(name = "time_spent_minutes")
    private Integer timeSpentMinutes = 0;
    
    /**
     * When the user started this item
     */
    @Column(name = "started_at")
    private LocalDateTime startedAt;
    
    /**
     * When the user completed this item
     */
    @Column(name = "completed_at")
    private LocalDateTime completedAt;
    
    /**
     * Last accessed timestamp for tracking engagement
     */
    @Column(name = "last_accessed_at")
    private LocalDateTime lastAccessedAt;
    
    /**
     * Number of times the user has accessed this item
     */
    @Column(name = "access_count")
    private Integer accessCount = 0;
    
    /**
     * User's self-assessment score (1-5 stars)
     */
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot exceed 5")
    @Column(name = "user_rating")
    private Integer userRating;
    
    /**
     * User's notes about their progress or understanding
     */
    @Lob
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * Many-to-One relationship with User
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    /**
     * Many-to-One relationship with LearningModule (optional)
     * Either module_id or topic_id should be set, not both
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private LearningModule module;
    
    /**
     * Many-to-One relationship with Topic (optional)
     * Either module_id or topic_id should be set, not both
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;
    
    /**
     * Progress Status Enum
     */
    public enum ProgressStatus {
        NOT_STARTED("Not Started", "#6c757d"),
        IN_PROGRESS("In Progress", "#007bff"),
        COMPLETED("Completed", "#28a745"),
        PAUSED("Paused", "#ffc107"),
        REVIEW_NEEDED("Review Needed", "#fd7e14"),
        MASTERED("Mastered", "#20c997");
        
        private final String displayName;
        private final String colorCode;
        
        ProgressStatus(String displayName, String colorCode) {
            this.displayName = displayName;
            this.colorCode = colorCode;
        }
        
        public String getDisplayName() { return displayName; }
        public String getColorCode() { return colorCode; }
    }
    
    /**
     * Business logic methods
     */
    
    /**
     * Mark as started and update timestamps
     */
    public void markAsStarted() {
        if (this.status == ProgressStatus.NOT_STARTED) {
            this.status = ProgressStatus.IN_PROGRESS;
            this.startedAt = LocalDateTime.now();
            this.progressPercentage = Math.max(this.progressPercentage, 1);
        }
        updateLastAccessed();
    }
    
    /**
     * Mark as completed
     */
    public void markAsCompleted() {
        this.status = ProgressStatus.COMPLETED;
        this.progressPercentage = 100;
        this.completedAt = LocalDateTime.now();
        updateLastAccessed();
    }
    
    /**
     * Update progress percentage and adjust status accordingly
     */
    public void updateProgress(int newProgress) {
        this.progressPercentage = Math.max(0, Math.min(100, newProgress));
        
        if (this.progressPercentage == 0) {
            this.status = ProgressStatus.NOT_STARTED;
        } else if (this.progressPercentage == 100) {
            markAsCompleted();
        } else {
            if (this.status == ProgressStatus.NOT_STARTED) {
                markAsStarted();
            } else if (this.status == ProgressStatus.COMPLETED) {
                this.status = ProgressStatus.IN_PROGRESS;
                this.completedAt = null;
            }
        }
        updateLastAccessed();
    }
    
    /**
     * Add time spent and update last accessed
     */
    public void addTimeSpent(int minutes) {
        this.timeSpentMinutes = (this.timeSpentMinutes != null ? this.timeSpentMinutes : 0) + minutes;
        updateLastAccessed();
    }
    
    /**
     * Update last accessed timestamp and increment access count
     */
    public void updateLastAccessed() {
        this.lastAccessedAt = LocalDateTime.now();
        this.accessCount = (this.accessCount != null ? this.accessCount : 0) + 1;
    }
    
    /**
     * Check if this progress is for a module or topic
     */
    public boolean isModuleProgress() {
        return module != null && topic == null;
    }
    
    public boolean isTopicProgress() {
        return topic != null && module == null;
    }
    
    /**
     * Get the name of what this progress is tracking
     */
    public String getProgressItemName() {
        if (isModuleProgress()) {
            return module.getName();
        } else if (isTopicProgress()) {
            return topic.getTitle();
        }
        return "Unknown";
    }
    
    /**
     * Get formatted time spent
     */
    public String getTimeSpentFormatted() {
        if (timeSpentMinutes == null || timeSpentMinutes == 0) {
            return "0 minutes";
        }
        
        if (timeSpentMinutes < 60) {
            return timeSpentMinutes + " minutes";
        } else {
            int hours = timeSpentMinutes / 60;
            int minutes = timeSpentMinutes % 60;
            if (minutes == 0) {
                return hours + " hour" + (hours > 1 ? "s" : "");
            } else {
                return hours + "h " + minutes + "m";
            }
        }
    }
    
    /**
     * Calculate learning velocity (progress per hour)
     */
    public double getLearningVelocity() {
        if (timeSpentMinutes == null || timeSpentMinutes == 0) {
            return 0.0;
        }
        double hoursSpent = timeSpentMinutes / 60.0;
        return progressPercentage / hoursSpent;
    }
    
    /**
     * Check if user is actively learning (accessed within last 7 days)
     */
    public boolean isActivelyLearning() {
        if (lastAccessedAt == null) {
            return false;
        }
        return lastAccessedAt.isAfter(LocalDateTime.now().minusDays(7));
    }
}