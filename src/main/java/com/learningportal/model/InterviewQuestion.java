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
 * Interview Question Entity - Stores technical interview questions
 * 
 * This entity demonstrates advanced JPA concepts and real-world interview preparation data modeling.
 * 
 * Key Learning Points:
 * - Complex enum usage with multiple attributes
 * - JSON storage for flexible data structures
 * - Many-to-One relationships with proper foreign key management
 * - Advanced validation constraints
 * - Business logic methods within entities
 * - Proper handling of collections and arrays in JPA
 */
@Entity
@Table(name = "interview_questions", indexes = {
    @Index(name = "idx_question_difficulty", columnList = "difficulty"),
    @Index(name = "idx_question_company", columnList = "company"),
    @Index(name = "idx_question_category", columnList = "category"),
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
    
    @NotBlank(message = "Question title is required")
    @Size(max = 300, message = "Question title must not exceed 300 characters")
    @Column(name = "title", nullable = false, length = 300)
    private String title;
    
    @NotBlank(message = "Question description is required")
    @Lob
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;
    
    /**
     * Sample solution or approach to the question
     * Stored as formatted text (HTML/Markdown)
     */
    @Lob
    @Column(name = "solution", columnDefinition = "TEXT")
    private String solution;
    
    /**
     * Code examples for the solution
     * Stored as JSON: [{"language": "java", "code": "...", "explanation": "..."}]
     */
    @Lob
    @Column(name = "code_examples", columnDefinition = "TEXT")
    private String codeExamples;
    
    /**
     * Follow-up questions related to this main question
     * Stored as JSON array: ["What if the input size is very large?", "How would you optimize this?"]
     */
    @Lob
    @Column(name = "follow_up_questions", columnDefinition = "TEXT")
    private String followUpQuestions;
    
    /**
     * Hints to help solve the question
     * Stored as JSON array: ["Think about using a HashMap", "Consider the two-pointer technique"]
     */
    @Lob
    @Column(name = "hints", columnDefinition = "TEXT")
    private String hints;
    
    /**
     * Tags for categorization and search
     * Stored as JSON array: ["array", "two-pointers", "hash-table"]
     */
    @Lob
    @Column(name = "tags", columnDefinition = "TEXT")
    private String tags;
    
    @NotNull(message = "Difficulty level is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", nullable = false)
    private Difficulty difficulty;
    
    @NotNull(message = "Question category is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private QuestionCategory category;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "company")
    private Company company;
    
    /**
     * Frequency of this question being asked (1-10 scale)
     * 10 = Very frequently asked, 1 = Rarely asked
     */
    @Column(name = "frequency_score")
    private Integer frequencyScore;
    
    /**
     * Estimated time to solve in minutes
     */
    @Column(name = "estimated_time_minutes")
    private Integer estimatedTimeMinutes;
    
    /**
     * Time complexity of the optimal solution
     * Example: "O(n)", "O(log n)", "O(n^2)"
     */
    @Size(max = 50)
    @Column(name = "time_complexity", length = 50)
    private String timeComplexity;
    
    /**
     * Space complexity of the optimal solution
     * Example: "O(1)", "O(n)", "O(log n)"
     */
    @Size(max = 50)
    @Column(name = "space_complexity", length = 50)
    private String spaceComplexity;
    
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
     * Each question belongs to a specific topic
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;
    
    /**
     * Difficulty levels for interview questions
     */
    public enum Difficulty {
        EASY("Easy", 1, "#28a745"),
        MEDIUM("Medium", 2, "#ffc107"),
        HARD("Hard", 3, "#dc3545");
        
        private final String displayName;
        private final int level;
        private final String colorCode;
        
        Difficulty(String displayName, int level, String colorCode) {
            this.displayName = displayName;
            this.level = level;
            this.colorCode = colorCode;
        }
        
        public String getDisplayName() { return displayName; }
        public int getLevel() { return level; }
        public String getColorCode() { return colorCode; }
    }
    
    /**
     * Question categories for organization
     */
    public enum QuestionCategory {
        // Data Structures
        ARRAY("Array"),
        LINKED_LIST("Linked List"),
        STACK("Stack"),
        QUEUE("Queue"),
        TREE("Tree"),
        GRAPH("Graph"),
        HASH_TABLE("Hash Table"),
        HEAP("Heap"),
        TRIE("Trie"),
        
        // Algorithms
        SORTING("Sorting"),
        SEARCHING("Search"),
        DYNAMIC_PROGRAMMING("Dynamic Programming"),
        GREEDY("Greedy"),
        BACKTRACKING("Backtracking"),
        DIVIDE_CONQUER("Divide and Conquer"),
        TWO_POINTERS("Two Pointers"),
        SLIDING_WINDOW("Sliding Window"),
        
        // System Design
        SYSTEM_DESIGN("System Design"),
        SCALABILITY("Scalability"),
        CACHING("Caching"),
        LOAD_BALANCING("Load Balancing"),
        MICROSERVICES("Microservices"),
        
        // Database Categories
        DATABASE("Database"),
        SQL_FUNDAMENTALS("SQL Fundamentals"),
        ADVANCED_SQL("Advanced SQL"),
        DATABASE_DESIGN("Database Design"),
        DATABASE_NORMALIZATION("Database Normalization"),
        DATABASE_INDEXING("Database Indexing"),
        QUERY_OPTIMIZATION("Query Optimization"),
        TRANSACTIONS_ACID("Transactions & ACID"),
        STORED_PROCEDURES("Stored Procedures & Functions"),
        DATABASE_TRIGGERS("Database Triggers"),
        
        // NoSQL Categories
        NOSQL_CONCEPTS("NoSQL Concepts"),
        MONGODB("MongoDB"),
        REDIS("Redis"),
        CASSANDRA("Cassandra"),
        ELASTICSEARCH("Elasticsearch"),
        DYNAMODB("DynamoDB"),
        
        // Database Administration
        DATABASE_PERFORMANCE("Database Performance"),
        DATABASE_SECURITY("Database Security"),
        BACKUP_RECOVERY("Backup & Recovery"),
        DATABASE_MONITORING("Database Monitoring"),
        DISTRIBUTED_DATABASES("Distributed Databases"),
        DATABASE_SHARDING("Database Sharding"),
        REPLICATION("Database Replication"),
        
        // Java Specific
        JAVA_CORE("Java Core"),
        JAVA_COLLECTIONS("Java Collections"),
        JAVA_CONCURRENCY("Java Concurrency"),
        JAVA_JVM("JVM Internals"),
        JAVA_PERFORMANCE("Java Performance"),
        
        // Spring Framework
        SPRING_CORE("Spring Core"),
        SPRING_BOOT("Spring Boot"),
        SPRING_SECURITY("Spring Security"),
        SPRING_DATA("Spring Data"),
        
        // React
        REACT_FUNDAMENTALS("React Fundamentals"),
        REACT_HOOKS("React Hooks"),
        REACT_PERFORMANCE("React Performance"),
        REACT_TESTING("React Testing"),
        
        // Behavioral
        BEHAVIORAL("Behavioral"),
        LEADERSHIP("Leadership"),
        PROBLEM_SOLVING("Problem Solving"),
        COMMUNICATION("Communication");
        
        private final String displayName;
        
        QuestionCategory(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() { return displayName; }
    }
    
    /**
     * Companies that frequently ask these questions
     */
    public enum Company {
        AMAZON("Amazon", "AMZN"),
        GOOGLE("Google", "GOOGL"),
        MICROSOFT("Microsoft", "MSFT"),
        META("Meta", "META"),
        APPLE("Apple", "AAPL"),
        NETFLIX("Netflix", "NFLX"),
        UBER("Uber", "UBER"),
        AIRBNB("Airbnb", "ABNB"),
        LINKEDIN("LinkedIn", "LNKD"),
        TWITTER("Twitter", "TWTR"),
        SALESFORCE("Salesforce", "CRM"),
        ADOBE("Adobe", "ADBE"),
        ORACLE("Oracle", "ORCL"),
        IBM("IBM", "IBM"),
        INTEL("Intel", "INTC"),
        NVIDIA("NVIDIA", "NVDA"),
        TESLA("Tesla", "TSLA"),
        SPOTIFY("Spotify", "SPOT"),
        DROPBOX("Dropbox", "DBX"),
        SLACK("Slack", "WORK"),
        ZOOM("Zoom", "ZM"),
        PALANTIR("Palantir", "PLTR"),
        SNOWFLAKE("Snowflake", "SNOW"),
        DATABRICKS("Databricks", "DBRX"),
        STRIPE("Stripe", "STRP"),
        COINBASE("Coinbase", "COIN"),
        ROBINHOOD("Robinhood", "HOOD"),
        DOORDASH("DoorDash", "DASH"),
        LYFT("Lyft", "LYFT"),
        PINTEREST("Pinterest", "PINS"),
        SNAP("Snap", "SNAP"),
        TIKTOK("TikTok", "TKTK"),
        BYTEDANCE("ByteDance", "BDNC"),
        SHOPIFY("Shopify", "SHOP"),
        SQUARE("Square", "SQ"),
        PAYPAL("PayPal", "PYPL"),
        VISA("Visa", "V"),
        MASTERCARD("Mastercard", "MA"),
        GOLDMAN_SACHS("Goldman Sachs", "GS"),
        JPMORGAN("JPMorgan Chase", "JPM"),
        MORGAN_STANLEY("Morgan Stanley", "MS"),
        CITADEL("Citadel", "CTDL"),
        TWO_SIGMA("Two Sigma", "TSIG"),
        JANE_STREET("Jane Street", "JANE"),
        DE_SHAW("D.E. Shaw", "DESH"),
        RENAISSANCE("Renaissance Technologies", "RENT"),
        BRIDGEWATER("Bridgewater Associates", "BRDG");
        
        private final String displayName;
        private final String ticker;
        
        Company(String displayName, String ticker) {
            this.displayName = displayName;
            this.ticker = ticker;
        }
        
        public String getDisplayName() { return displayName; }
        public String getTicker() { return ticker; }
    }
    
    /**
     * Get difficulty level as integer for sorting/filtering
     */
    public int getDifficultyLevel() {
        return difficulty != null ? difficulty.getLevel() : 0;
    }
    
    /**
     * Check if this is a high-frequency question
     */
    public boolean isHighFrequency() {
        return frequencyScore != null && frequencyScore >= 7;
    }
    
    /**
     * Get estimated difficulty for time planning
     */
    public String getEstimatedTimeFormatted() {
        if (estimatedTimeMinutes == null) {
            return "Unknown";
        }
        
        if (estimatedTimeMinutes < 60) {
            return estimatedTimeMinutes + " minutes";
        } else {
            int hours = estimatedTimeMinutes / 60;
            int minutes = estimatedTimeMinutes % 60;
            if (minutes == 0) {
                return hours + " hour" + (hours > 1 ? "s" : "");
            } else {
                return hours + "h " + minutes + "m";
            }
        }
    }
    
    /**
     * Get complexity information formatted for display
     */
    public String getComplexityInfo() {
        StringBuilder sb = new StringBuilder();
        if (timeComplexity != null) {
            sb.append("Time: ").append(timeComplexity);
        }
        if (spaceComplexity != null) {
            if (sb.length() > 0) sb.append(", ");
            sb.append("Space: ").append(spaceComplexity);
        }
        return sb.toString();
    }
    
    /**
     * Check if this question has code examples
     */
    public boolean hasCodeExamples() {
        return codeExamples != null && !codeExamples.trim().isEmpty();
    }
    
    /**
     * Check if this question has hints available
     */
    public boolean hasHints() {
        return hints != null && !hints.trim().isEmpty();
    }
    
    /**
     * Check if this question has follow-up questions
     */
    public boolean hasFollowUpQuestions() {
        return followUpQuestions != null && !followUpQuestions.trim().isEmpty();
    }
}