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
import java.util.HashSet;
import java.util.Set;

/**
 * Interview Question Entity - Stores technical interview questions
 * 
 * This entity demonstrates several advanced JPA/Hibernate concepts:
 * - Complex enum handling with multiple attributes
 * - @ElementCollection for storing collections of basic types
 * - @CollectionTable for custom collection mapping
 * - JSON storage for complex data structures
 * - Multiple indexing strategies for performance
 * - Advanced validation constraints
 */
@Entity
@Table(name = "interview_questions", 
       indexes = {
           @Index(name = "idx_question_difficulty", columnList = "difficulty"),
           @Index(name = "idx_question_category", columnList = "category"),
           @Index(name = "idx_question_company", columnList = "companies"),
           @Index(name = "idx_question_topic", columnList = "topic_id")
       })
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = {"topic"})
public class InterviewQuestion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Question text is required")
    @Size(max = 2000, message = "Question must not exceed 2000 characters")
    @Column(name = "question_text", nullable = false, length = 2000)
    private String questionText;
    
    /**
     * Detailed answer/explanation stored as HTML/Markdown
     */
    @Lob
    @Column(name = "answer", columnDefinition = "TEXT")
    private String answer;
    
    /**
     * Code solution if applicable (stored as JSON for multiple language support)
     * Example: {"java": "public class...", "python": "def solution():..."}
     */
    @Lob
    @Column(name = "code_solution", columnDefinition = "TEXT")
    private String codeSolution;
    
    /**
     * Hints to help with the question (stored as JSON array)
     * Example: ["Think about time complexity", "Consider edge cases"]
     */
    @Lob
    @Column(name = "hints", columnDefinition = "TEXT")
    private String hints;
    
    @NotNull(message = "Difficulty level is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", nullable = false)
    private Difficulty difficulty;
    
    @NotNull(message = "Question category is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private QuestionCategory category;
    
    /**
     * Companies that have asked this question
     * Using @ElementCollection to store a collection of enums
     * 
     * Key Learning Points:
     * - @ElementCollection: For collections of basic types or embeddables
     * - @CollectionTable: Specifies the table for storing collection elements
     * - @Enumerated: How to store enums in collection tables
     */
    @ElementCollection(targetClass = Company.class)
    @CollectionTable(name = "question_companies", 
                    joinColumns = @JoinColumn(name = "question_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "companies")
    private Set<Company> companies = new HashSet<>();
    
    /**
     * Tags for better categorization and search
     * Example: ["arrays", "sorting", "two-pointers"]
     */
    @ElementCollection
    @CollectionTable(name = "question_tags", 
                    joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>();
    
    /**
     * Time complexity of the optimal solution
     */
    @Column(name = "time_complexity", length = 50)
    private String timeComplexity;
    
    /**
     * Space complexity of the optimal solution
     */
    @Column(name = "space_complexity", length = 50)
    private String spaceComplexity;
    
    /**
     * Frequency of this question being asked (1-10 scale)
     */
    @Column(name = "frequency_score")
    private Integer frequencyScore;
    
    /**
     * Follow-up questions or variations
     */
    @Lob
    @Column(name = "follow_up_questions", columnDefinition = "TEXT")
    private String followUpQuestions;
    
    @Column(name = "active", nullable = false)
    private Boolean active = true;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * Many-to-One relationship with Topic
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;
    
    /**
     * Difficulty levels for interview questions
     */
    public enum Difficulty {
        EASY("Easy", 1),
        MEDIUM("Medium", 2),
        HARD("Hard", 3),
        EXPERT("Expert", 4);
        
        private final String displayName;
        private final int level;
        
        Difficulty(String displayName, int level) {
            this.displayName = displayName;
            this.level = level;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        public int getLevel() {
            return level;
        }
    }
    
    /**
     * Question categories based on computer science topics
     */
    public enum QuestionCategory {
        ARRAYS_AND_STRINGS("Arrays & Strings"),
        LINKED_LISTS("Linked Lists"),
        STACKS_AND_QUEUES("Stacks & Queues"),
        TREES_AND_GRAPHS("Trees & Graphs"),
        DYNAMIC_PROGRAMMING("Dynamic Programming"),
        SORTING_AND_SEARCHING("Sorting & Searching"),
        HASH_TABLES("Hash Tables"),
        RECURSION("Recursion"),
        GREEDY_ALGORITHMS("Greedy Algorithms"),
        BACKTRACKING("Backtracking"),
        BIT_MANIPULATION("Bit Manipulation"),
        MATH_AND_LOGIC("Math & Logic"),
        SYSTEM_DESIGN("System Design"),
        OBJECT_ORIENTED_DESIGN("Object Oriented Design"),
        JAVA_SPECIFIC("Java Specific"),
        SPRING_FRAMEWORK("Spring Framework"),
        DATABASE_DESIGN("Database Design"),
        CONCURRENCY("Concurrency"),
        PERFORMANCE_OPTIMIZATION("Performance Optimization"),
        BEHAVIORAL("Behavioral Questions");
        
        private final String displayName;
        
        QuestionCategory(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    /**
     * Companies that commonly ask these questions
     */
    public enum Company {
        AMAZON("Amazon"),
        GOOGLE("Google"),
        MICROSOFT("Microsoft"),
        META("Meta"),
        APPLE("Apple"),
        NETFLIX("Netflix"),
        UBER("Uber"),
        AIRBNB("Airbnb"),
        LINKEDIN("LinkedIn"),
        TWITTER("Twitter"),
        SALESFORCE("Salesforce"),
        ORACLE("Oracle"),
        IBM("IBM"),
        ADOBE("Adobe"),
        SPOTIFY("Spotify"),
        DROPBOX("Dropbox"),
        PALANTIR("Palantir"),
        GOLDMAN_SACHS("Goldman Sachs"),
        JP_MORGAN("JP Morgan"),
        BLOOMBERG("Bloomberg"),
        CITADEL("Citadel"),
        TWO_SIGMA("Two Sigma"),
        JANE_STREET("Jane Street"),
        STRIPE("Stripe"),
        SQUARE("Square"),
        COINBASE("Coinbase"),
        ROBINHOOD("Robinhood");
        
        private final String displayName;
        
        Company(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    /**
     * Add a company to the list of companies that ask this question
     */
    public void addCompany(Company company) {
        if (companies == null) {
            companies = new HashSet<>();
        }
        companies.add(company);
    }
    
    /**
     * Add a tag to this question
     */
    public void addTag(String tag) {
        if (tags == null) {
            tags = new HashSet<>();
        }
        tags.add(tag.toLowerCase().trim());
    }
    
    /**
     * Check if this question has a code solution
     */
    public boolean hasCodeSolution() {
        return codeSolution != null && !codeSolution.trim().isEmpty();
    }
    
    /**
     * Check if this question has hints
     */
    public boolean hasHints() {
        return hints != null && !hints.trim().isEmpty();
    }
    
    /**
     * Get difficulty color for UI display
     */
    public String getDifficultyColor() {
        return switch (difficulty) {
            case EASY -> "#28a745";      // Green
            case MEDIUM -> "#ffc107";    // Yellow
            case HARD -> "#dc3545";      // Red
            case EXPERT -> "#6f42c1";    // Purple
        };
    }
    
    /**
     * Get a formatted string of companies that ask this question
     */
    public String getCompaniesFormatted() {
        if (companies == null || companies.isEmpty()) {
            return "Various Companies";
        }
        
        return companies.stream()
                .map(Company::getDisplayName)
                .sorted()
                .reduce((a, b) -> a + ", " + b)
                .orElse("Various Companies");
    }
}