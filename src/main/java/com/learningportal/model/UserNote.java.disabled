package com.learningportal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * User Note Entity - Stores user's personal notes and annotations
 * 
 * This entity demonstrates:
 * - Rich text content storage
 * - Flexible categorization system
 * - Search optimization with indexes
 * - User-generated content management
 * - Tagging system for organization
 */
@Entity
@Table(name = "user_notes",
       indexes = {
           @Index(name = "idx_user_notes_user", columnList = "user_id"),
           @Index(name = "idx_user_notes_category", columnList = "category"),
           @Index(name = "idx_user_notes_created", columnList = "created_at"),
           @Index(name = "idx_user_notes_updated", columnList = "updated_at"),
           @Index(name = "idx_user_notes_pinned", columnList = "pinned")
       })
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = {"user", "module", "topic", "question"})
public class UserNote {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Note title is required")
    @Size(max = 200, message = "Note title must not exceed 200 characters")
    @Column(name = "title", nullable = false, length = 200)
    private String title;
    
    /**
     * Main content of the note
     * Supports rich text (HTML/Markdown)
     */
    @NotBlank(message = "Note content is required")
    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
    
    /**
     * Code snippets associated with this note
     * Stored as JSON: [{"language": "java", "code": "...", "description": "..."}]
     */
    @Lob
    @Column(name = "code_snippets", columnDefinition = "TEXT")
    private String codeSnippets;
    
    /**
     * Tags for categorization and search
     * Stored as JSON array: ["important", "review", "algorithm", "interview"]
     */
    @Lob
    @Column(name = "tags", columnDefinition = "TEXT")
    private String tags;
    
    @NotNull(message = "Note category is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private NoteCategory category;
    
    /**
     * Priority level for the note
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority = Priority.MEDIUM;
    
    /**
     * Whether this note is pinned (appears at top)
     */
    @Column(name = "pinned", nullable = false)
    private Boolean pinned = false;
    
    /**
     * Whether this note is marked as favorite
     */
    @Column(name = "favorite", nullable = false)
    private Boolean favorite = false;
    
    /**
     * Color code for visual organization
     */
    @Size(max = 7, message = "Color code must be valid hex color")
    @Column(name = "color", length = 7)
    private String color;
    
    /**
     * Reminder date for follow-up
     */
    @Column(name = "reminder_date")
    private LocalDateTime reminderDate;
    
    /**
     * Whether the reminder has been sent/acknowledged
     */
    @Column(name = "reminder_sent", nullable = false)
    private Boolean reminderSent = false;
    
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
     * Optional relationship with LearningModule
     * Notes can be associated with specific modules
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private LearningModule module;
    
    /**
     * Optional relationship with Topic
     * Notes can be associated with specific topics
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;
    
    /**
     * Optional relationship with InterviewQuestion
     * Notes can be associated with specific questions
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private InterviewQuestion question;
    
    /**
     * Note Categories
     */
    public enum NoteCategory {
        GENERAL("General Notes", "#6c757d"),
        CONCEPT("Concept Explanation", "#007bff"),
        CODE_SNIPPET("Code Snippet", "#28a745"),
        INTERVIEW_TIP("Interview Tip", "#dc3545"),
        BEST_PRACTICE("Best Practice", "#20c997"),
        TROUBLESHOOTING("Troubleshooting", "#fd7e14"),
        RESOURCE("External Resource", "#6f42c1"),
        QUESTION("Question/Doubt", "#e83e8c"),
        SUMMARY("Summary", "#17a2b8"),
        TODO("To-Do Item", "#ffc107"),
        INSIGHT("Personal Insight", "#343a40"),
        REVIEW("Review Notes", "#495057");
        
        private final String displayName;
        private final String defaultColor;
        
        NoteCategory(String displayName, String defaultColor) {
            this.displayName = displayName;
            this.defaultColor = defaultColor;
        }
        
        public String getDisplayName() { return displayName; }
        public String getDefaultColor() { return defaultColor; }
    }
    
    /**
     * Priority Levels
     */
    public enum Priority {
        LOW("Low", 1, "#28a745"),
        MEDIUM("Medium", 2, "#ffc107"),
        HIGH("High", 3, "#fd7e14"),
        URGENT("Urgent", 4, "#dc3545");
        
        private final String displayName;
        private final int level;
        private final String colorCode;
        
        Priority(String displayName, int level, String colorCode) {
            this.displayName = displayName;
            this.level = level;
            this.colorCode = colorCode;
        }
        
        public String getDisplayName() { return displayName; }
        public int getLevel() { return level; }
        public String getColorCode() { return colorCode; }
    }
    
    /**
     * Business logic methods
     */
    
    /**
     * Toggle pinned status
     */
    public void togglePinned() {
        this.pinned = !this.pinned;
    }
    
    /**
     * Toggle favorite status
     */
    public void toggleFavorite() {
        this.favorite = !this.favorite;
    }
    
    /**
     * Set reminder for this note
     */
    public void setReminder(LocalDateTime reminderDate) {
        this.reminderDate = reminderDate;
        this.reminderSent = false;
    }
    
    /**
     * Mark reminder as sent
     */
    public void markReminderSent() {
        this.reminderSent = true;
    }
    
    /**
     * Check if reminder is due
     */
    public boolean isReminderDue() {
        return reminderDate != null && 
               !reminderSent && 
               reminderDate.isBefore(LocalDateTime.now());
    }
    
    /**
     * Get the context this note is associated with
     */
    public String getContext() {
        if (question != null) {
            return "Question: " + question.getTitle();
        } else if (topic != null) {
            return "Topic: " + topic.getTitle();
        } else if (module != null) {
            return "Module: " + module.getName();
        }
        return "General";
    }
    
    /**
     * Check if note has code snippets
     */
    public boolean hasCodeSnippets() {
        return codeSnippets != null && !codeSnippets.trim().isEmpty();
    }
    
    /**
     * Check if note has tags
     */
    public boolean hasTags() {
        return tags != null && !tags.trim().isEmpty();
    }
    
    /**
     * Get effective color (custom color or category default)
     */
    public String getEffectiveColor() {
        return color != null ? color : category.getDefaultColor();
    }
    
    /**
     * Get word count for the note content
     */
    public int getWordCount() {
        if (content == null || content.trim().isEmpty()) {
            return 0;
        }
        // Simple word count (split by whitespace)
        return content.trim().split("\\s+").length;
    }
    
    /**
     * Get estimated reading time in minutes
     */
    public int getEstimatedReadingTime() {
        int wordCount = getWordCount();
        // Average reading speed: 200 words per minute
        return Math.max(1, (int) Math.ceil(wordCount / 200.0));
    }
    
    /**
     * Check if note was recently updated (within last 24 hours)
     */
    public boolean isRecentlyUpdated() {
        return updatedAt != null && 
               updatedAt.isAfter(LocalDateTime.now().minusDays(1));
    }
    
    /**
     * Get a preview of the note content (first 100 characters)
     */
    public String getContentPreview() {
        if (content == null || content.trim().isEmpty()) {
            return "";
        }
        
        String plainText = content.replaceAll("<[^>]*>", ""); // Remove HTML tags
        if (plainText.length() <= 100) {
            return plainText;
        }
        
        return plainText.substring(0, 97) + "...";
    }
}