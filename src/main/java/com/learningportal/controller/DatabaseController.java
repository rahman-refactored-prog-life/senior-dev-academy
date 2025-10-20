package com.learningportal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Database Controller
 * 
 * Provides endpoints for SQL, NoSQL database content and interview questions.
 */
@RestController
@RequestMapping("/databases")
@Tag(name = "Databases", description = "SQL and NoSQL database content and interview preparation")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DatabaseController {
    
    @Operation(
        summary = "Get SQL curriculum overview",
        description = "Returns comprehensive SQL learning path and topics"
    )
    @GetMapping("/sql/curriculum")
    public ResponseEntity<Map<String, Object>> getSQLCurriculum() {
        Map<String, Object> curriculum = new HashMap<>();
        
        curriculum.put("title", "SQL Database Mastery");
        curriculum.put("description", "Complete SQL curriculum from basics to advanced query optimization");
        curriculum.put("totalTopics", 12);
        curriculum.put("estimatedHours", 90);
        curriculum.put("difficultyLevel", "Intermediate");
        
        List<Map<String, Object>> topics = Arrays.asList(
            createTopicMap("SQL Fundamentals", "SELECT, WHERE, ORDER BY, basic operations", 15),
            createTopicMap("Joins and Relationships", "INNER, LEFT, RIGHT, FULL OUTER joins", 20),
            createTopicMap("Aggregate Functions", "COUNT, SUM, AVG, GROUP BY, HAVING", 18),
            createTopicMap("Subqueries", "Correlated and non-correlated subqueries", 15),
            createTopicMap("Window Functions", "ROW_NUMBER, RANK, LAG, LEAD", 25),
            createTopicMap("Common Table Expressions", "CTEs, recursive queries", 20),
            createTopicMap("Indexes and Performance", "B-tree indexes, query optimization", 30),
            createTopicMap("Stored Procedures", "Functions, procedures, triggers", 25),
            createTopicMap("Database Design", "Normalization, ER diagrams", 35),
            createTopicMap("Transactions and ACID", "Isolation levels, concurrency", 20),
            createTopicMap("Advanced SQL", "Pivoting, unpivoting, advanced patterns", 25),
            createTopicMap("Query Optimization", "Execution plans, performance tuning", 30)
        );
        
        curriculum.put("topics", topics);
        curriculum.put("interviewQuestions", 45);
        
        return ResponseEntity.ok(curriculum);
    }
    
    @Operation(
        summary = "Get NoSQL curriculum overview",
        description = "Returns comprehensive NoSQL learning path and topics"
    )
    @GetMapping("/nosql/curriculum")
    public ResponseEntity<Map<String, Object>> getNoSQLCurriculum() {
        Map<String, Object> curriculum = new HashMap<>();
        
        curriculum.put("title", "NoSQL Database Mastery");
        curriculum.put("description", "Complete NoSQL curriculum covering MongoDB, Redis, Cassandra, and more");
        curriculum.put("totalTopics", 10);
        curriculum.put("estimatedHours", 120);
        curriculum.put("difficultyLevel", "Advanced");
        
        List<Map<String, Object>> topics = Arrays.asList(
            createTopicMap("NoSQL Fundamentals", "CAP theorem, ACID vs BASE", 20),
            createTopicMap("Document Databases", "MongoDB operations and schema design", 30),
            createTopicMap("Key-Value Stores", "Redis data structures and use cases", 25),
            createTopicMap("Column-Family", "Cassandra architecture and data modeling", 35),
            createTopicMap("Graph Databases", "Neo4j and graph algorithms", 30),
            createTopicMap("MongoDB Advanced", "Aggregation pipeline, indexing", 40),
            createTopicMap("Redis Advanced", "Pub/Sub, clustering, persistence", 25),
            createTopicMap("Data Modeling", "Schema design for different NoSQL types", 30),
            createTopicMap("Consistency Models", "Eventual consistency, strong consistency", 20),
            createTopicMap("Performance & Scaling", "Sharding, replication, optimization", 35)
        );
        
        curriculum.put("topics", topics);
        curriculum.put("interviewQuestions", 35);
        
        return ResponseEntity.ok(curriculum);
    }
    
    @Operation(
        summary = "Get SQL interview questions",
        description = "Returns common SQL interview questions with solutions"
    )
    @GetMapping("/sql/interview-questions")
    public ResponseEntity<Map<String, Object>> getSQLInterviewQuestions() {
        Map<String, Object> questions = new HashMap<>();
        
        questions.put("title", "SQL Interview Questions");
        questions.put("description", "Most commonly asked SQL questions in technical interviews");
        
        List<Map<String, Object>> questionList = Arrays.asList(
            createSQLQuestionMap("Find the second highest salary", "SELECT MAX(salary) FROM employees WHERE salary < (SELECT MAX(salary) FROM employees)", "Medium"),
            createSQLQuestionMap("Find employees earning more than their managers", "SELECT e.name FROM employees e JOIN employees m ON e.manager_id = m.id WHERE e.salary > m.salary", "Medium"),
            createSQLQuestionMap("Calculate running totals", "SELECT id, amount, SUM(amount) OVER (ORDER BY id) as running_total FROM transactions", "Hard"),
            createSQLQuestionMap("Find duplicate records", "SELECT email, COUNT(*) FROM users GROUP BY email HAVING COUNT(*) > 1", "Easy"),
            createSQLQuestionMap("Top N records in each group", "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (PARTITION BY department ORDER BY salary DESC) as rn FROM employees) WHERE rn <= 3", "Hard"),
            createSQLQuestionMap("Calculate year-over-year growth", "SELECT year, revenue, LAG(revenue) OVER (ORDER BY year) as prev_year, (revenue - LAG(revenue) OVER (ORDER BY year)) / LAG(revenue) OVER (ORDER BY year) * 100 as growth FROM sales", "Hard")
        );
        
        questions.put("questions", questionList);
        questions.put("totalQuestions", questionList.size());
        
        return ResponseEntity.ok(questions);
    }
    
    @Operation(
        summary = "Get NoSQL interview questions",
        description = "Returns common NoSQL interview questions and concepts"
    )
    @GetMapping("/nosql/interview-questions")
    public ResponseEntity<Map<String, Object>> getNoSQLInterviewQuestions() {
        Map<String, Object> questions = new HashMap<>();
        
        questions.put("title", "NoSQL Interview Questions");
        questions.put("description", "Essential NoSQL questions for technical interviews");
        
        List<Map<String, Object>> questionList = Arrays.asList(
            createNoSQLQuestionMap("When would you choose MongoDB over PostgreSQL?", "Document-based data, flexible schema, horizontal scaling needs", "Medium"),
            createNoSQLQuestionMap("Explain CAP theorem", "Consistency, Availability, Partition tolerance - can only guarantee 2 out of 3", "Hard"),
            createNoSQLQuestionMap("How does Redis handle persistence?", "RDB snapshots and AOF (Append Only File) for durability", "Medium"),
            createNoSQLQuestionMap("What is eventual consistency?", "System will become consistent over time, but not immediately", "Medium"),
            createNoSQLQuestionMap("Explain MongoDB sharding", "Horizontal partitioning of data across multiple servers", "Hard"),
            createNoSQLQuestionMap("Redis data structures and use cases", "Strings (cache), Lists (queues), Sets (unique items), Hashes (objects), Sorted Sets (leaderboards)", "Medium")
        );
        
        questions.put("questions", questionList);
        questions.put("totalQuestions", questionList.size());
        
        return ResponseEntity.ok(questions);
    }
    
    @Operation(
        summary = "Get database comparison",
        description = "Returns comparison between different database types"
    )
    @GetMapping("/comparison")
    public ResponseEntity<Map<String, Object>> getDatabaseComparison() {
        Map<String, Object> comparison = new HashMap<>();
        
        comparison.put("title", "Database Types Comparison");
        comparison.put("description", "When to use different types of databases");
        
        List<Map<String, Object>> databases = Arrays.asList(
            createDBComparisonMap("Relational (SQL)", 
                                Arrays.asList("ACID compliance", "Complex queries", "Structured data", "Strong consistency"),
                                Arrays.asList("Financial systems", "E-commerce", "CRM systems"),
                                Arrays.asList("PostgreSQL", "MySQL", "Oracle", "SQL Server")),
            createDBComparisonMap("Document (NoSQL)",
                                Arrays.asList("Flexible schema", "JSON-like documents", "Horizontal scaling", "Rapid development"),
                                Arrays.asList("Content management", "Catalogs", "User profiles"),
                                Arrays.asList("MongoDB", "CouchDB", "Amazon DocumentDB")),
            createDBComparisonMap("Key-Value (NoSQL)",
                                Arrays.asList("Simple data model", "High performance", "Caching", "Session storage"),
                                Arrays.asList("Caching", "Session management", "Shopping carts"),
                                Arrays.asList("Redis", "Amazon DynamoDB", "Riak")),
            createDBComparisonMap("Column-Family (NoSQL)",
                                Arrays.asList("Wide columns", "Time-series data", "High write throughput", "Distributed"),
                                Arrays.asList("Time-series data", "IoT applications", "Logging"),
                                Arrays.asList("Cassandra", "HBase", "Amazon SimpleDB")),
            createDBComparisonMap("Graph (NoSQL)",
                                Arrays.asList("Relationships", "Graph algorithms", "Connected data", "Pattern matching"),
                                Arrays.asList("Social networks", "Recommendation engines", "Fraud detection"),
                                Arrays.asList("Neo4j", "Amazon Neptune", "ArangoDB"))
        );
        
        comparison.put("databases", databases);
        
        return ResponseEntity.ok(comparison);
    }
    
    // Helper methods
    private Map<String, Object> createTopicMap(String title, String description, int estimatedMinutes) {
        Map<String, Object> topic = new HashMap<>();
        topic.put("title", title);
        topic.put("description", description);
        topic.put("estimatedMinutes", estimatedMinutes);
        return topic;
    }
    
    private Map<String, Object> createSQLQuestionMap(String question, String solution, String difficulty) {
        Map<String, Object> questionMap = new HashMap<>();
        questionMap.put("question", question);
        questionMap.put("solution", solution);
        questionMap.put("difficulty", difficulty);
        return questionMap;
    }
    
    private Map<String, Object> createNoSQLQuestionMap(String question, String answer, String difficulty) {
        Map<String, Object> questionMap = new HashMap<>();
        questionMap.put("question", question);
        questionMap.put("answer", answer);
        questionMap.put("difficulty", difficulty);
        return questionMap;
    }
    
    private Map<String, Object> createDBComparisonMap(String type, List<String> characteristics, List<String> useCases, List<String> examples) {
        Map<String, Object> db = new HashMap<>();
        db.put("type", type);
        db.put("characteristics", characteristics);
        db.put("useCases", useCases);
        db.put("examples", examples);
        return db;
    }
}