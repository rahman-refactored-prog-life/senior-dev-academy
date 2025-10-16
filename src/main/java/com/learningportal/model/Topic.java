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
import java.util.ArrayList;
import java.util.List;

/**
 * Topic Entity - Represents individual topics within a learning module
 * 
 * Examples: "Variables and Data Types", "Spring Boot Auto-Configuration", etc.
 * 
 * Key Learning Points Demonstrated:
 * - @ManyToOne relationship (Many topics belong to one module)
 * - @OneToMany with mappedBy (One topic has many questions)
 * - JSON handling with @Lob for storing structured data
 * - Enum usage for categorization
 * - Cascade operations and fetch strategies
 * - Bidirectional relationships
 */
@Entity
@Table(name = "topics")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = {"module", "questions"})
public class Topic {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Topic title is required")
    @Size(max = 200, message = "Topic title must not exceed 200 characters")
    @Column(name = "title", nullable = false, length = 200)
    private String title;
    
    @NotBlank(message = "Topic description is required")
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    @Column(name = "description", nullable = false, length = 1000)
    private String description;
    
    /**
     * Main content of the topic stored as HTML/Markdown
     * Using @Lob for large text content
     */
    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
    
    /**
     * Code examples stored as JSON string
     * Example: [{"language": "java", "code": "public class...", "explanation": "..."}]
     */
    @Lob
    @Column(name = "code_examples", columnDefinition = "TEXT")
    private String codeExamples;
    
    /**
     * Key concepts covered in this topic (stored as JSON array)
     * Example: ["Variables", "Data Types", "Type Casting"]
     */
    @Lob
    @Column(name = "key_concepts", columnDefinition = "TEXT")
    private String keyConcepts;
    
    @NotNull(message = "Topic type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "topic_type", nullable = false)
    private TopicType topicType;
    
    @Column(name = "estimated_minutes")
    private Integer estimatedMinutes;
    
    @Column(name = "sort_order")
    private Integer sortOrder = 0;
    
    @Column(name = "active", nullable = false)
    private Boolean active = true;
    
    /**
     * Difficulty level for this specific topic
     * Can be different from the module's overall difficulty
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty_level")
    private LearningModule.DifficultyLevel difficultyLevel;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * Many-to-One relationship with LearningModule
     * 
     * Key Learning Points:
     * - @ManyToOne: Many topics belong to one module
     * - @JoinColumn: Specifies the foreign key column
     * - FetchType.LAZY: Module is loaded only when accessed (performance optimization)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private LearningModule module;
    
    /**
     * One-to-Many relationship with InterviewQuestion
     * 
     * Key Learning Points:
     * - mappedBy: Indicates this is the inverse side of the relationship
     * - CascadeType.ALL: All operations cascade to questions
     * - FetchType.LAZY: Questions loaded only when needed
     * - orphanRemoval: Removes questions when they're removed from this collection
     */
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<InterviewQuestion> questions = new ArrayList<>();
    
    /**
     * Topic Types - Different kinds of learning content
     */
    public enum TopicType {
        THEORY("Theory"),
        PRACTICAL("Practical"),
        CODE_EXAMPLE("Code Example"),
        EXERCISE("Exercise"),
        QUIZ("Quiz"),
        PROJECT("Project"),
        INTERVIEW_PREP("Interview Preparation"),
        BEST_PRACTICES("Best Practices"),
        TROUBLESHOOTING("Troubleshooting"),
        PERFORMANCE("Performance Optimization");
        
        private final String displayName;
        
        TopicType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    /**
     * Convenience method to add a question to this topic
     * Demonstrates proper bidirectional relationship management
     */
    public void addQuestion(InterviewQuestion question) {
        questions.add(question);
        question.setTopic(this);
    }
    
    /**
     * Convenience method to remove a question from this topic
     */
    public void removeQuestion(InterviewQuestion question) {
        questions.remove(question);
        question.setTopic(null);
    }
    
    /**
     * Get the number of questions for this topic
     */
    public int getQuestionCount() {
        return questions != null ? questions.size() : 0;
    }
    
    /**
     * Check if this topic has any code examples
     */
    public boolean hasCodeExamples() {
        return codeExamples != null && !codeExamples.trim().isEmpty();
    }
    
    /**
     * Get estimated reading time in a human-readable format
     */
    public String getEstimatedTimeFormatted() {
        if (estimatedMinutes == null) {
            return "Unknown";
        }
        
        if (estimatedMinutes < 60) {
            return estimatedMinutes + " minutes";
        } else {
            int hours = estimatedMinutes / 60;
            int minutes = estimatedMinutes % 60;
            if (minutes == 0) {
                return hours + " hour" + (hours > 1 ? "s" : "");
            } else {
                return hours + "h " + minutes + "m";
            }
        }
    }
}