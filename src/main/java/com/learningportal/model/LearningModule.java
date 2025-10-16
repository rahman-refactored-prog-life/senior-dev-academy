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
 * Learning Module Entity - Represents different learning sections
 * 
 * Examples: Java Fundamentals, Spring Framework, React Development, etc.
 * 
 * Key Learning Points:
 * - @Enumerated: How to store enums in database
 * - @Lob: Large Object annotation for storing long text
 * - @OneToMany with @JoinColumn: Parent-child relationship
 * - @OrderBy: Automatic ordering of child entities
 */
@Entity
@Table(name = "learning_modules")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LearningModule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Module name is required")
    @Size(max = 100, message = "Module name must not exceed 100 characters")
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Column(name = "description", nullable = false, length = 500)
    private String description;
    
    @Lob
    @Column(name = "detailed_content", columnDefinition = "TEXT")
    private String detailedContent;
    
    @NotNull(message = "Category is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;
    
    @NotNull(message = "Difficulty level is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty_level", nullable = false)
    private DifficultyLevel difficultyLevel;
    
    @Column(name = "estimated_hours")
    private Integer estimatedHours;
    
    @Column(name = "sort_order")
    private Integer sortOrder = 0;
    
    @Column(name = "active", nullable = false)
    private Boolean active = true;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // One-to-Many relationship with Topics
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    @OrderBy("sortOrder ASC")
    private List<Topic> topics = new ArrayList<>();
    
    /**
     * Learning Module Categories
     */
    public enum Category {
        JAVA_FUNDAMENTALS("Java Fundamentals"),
        SPRING_FRAMEWORK("Spring Framework"),
        REACT_DEVELOPMENT("React Development"),
        DATA_STRUCTURES("Data Structures"),
        ALGORITHMS("Algorithms"),
        SYSTEM_DESIGN("System Design"),
        INTERVIEW_PREP("Interview Preparation"),
        BEHAVIORAL_QUESTIONS("Behavioral Questions"),
        DESIGN_PATTERNS("Design Patterns"),
        DATABASE_DESIGN("Database Design"),
        MICROSERVICES("Microservices"),
        PERFORMANCE_OPTIMIZATION("Performance Optimization");
        
        private final String displayName;
        
        Category(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    /**
     * Difficulty Levels
     */
    public enum DifficultyLevel {
        BEGINNER("Beginner"),
        INTERMEDIATE("Intermediate"),
        ADVANCED("Advanced"),
        EXPERT("Expert");
        
        private final String displayName;
        
        DifficultyLevel(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    /**
     * Add a topic to this module
     */
    public void addTopic(Topic topic) {
        topics.add(topic);
        if (topic.getSortOrder() == null) {
            topic.setSortOrder(topics.size());
        }
    }
    
    /**
     * Get total number of topics in this module
     */
    public int getTopicCount() {
        return topics != null ? topics.size() : 0;
    }
}