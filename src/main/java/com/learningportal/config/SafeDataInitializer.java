package com.learningportal.config;

import com.learningportal.model.*;
import com.learningportal.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Safe Data Initializer - Phased initialization with proper ordering and error handling
 * 
 * This class implements:
 * - Phased initialization with @Order annotations
 * - Reference data initialization before relational data
 * - Comprehensive error handling and rollback mechanisms
 * - Transaction management for data safety
 * - Detailed logging and progress tracking
 */
@Component
public class SafeDataInitializer {
    
    private static final Logger log = LoggerFactory.getLogger(SafeDataInitializer.class);
    
    private final LearningModuleRepository moduleRepository;
    private final TopicRepository topicRepository;
    private final InterviewQuestionRepository questionRepository;
    private final TransactionTemplate transactionTemplate;
    
    // Track initialization state
    private boolean phase1Complete = false;
    private boolean phase2Complete = false;
    private boolean initializationComplete = false;
    
    // Store created entities for rollback if needed
    private final List<LearningModule> createdModules = new ArrayList<>();
    private final List<Topic> createdTopics = new ArrayList<>();
    private final List<InterviewQuestion> createdQuestions = new ArrayList<>();
    
    public SafeDataInitializer(LearningModuleRepository moduleRepository,
                              TopicRepository topicRepository,
                              InterviewQuestionRepository questionRepository,
                              TransactionTemplate transactionTemplate) {
        this.moduleRepository = moduleRepository;
        this.topicRepository = topicRepository;
        this.questionRepository = questionRepository;
        this.transactionTemplate = transactionTemplate;
    }
    
    /**
     * Phase 1: Initialize reference data (entities with no foreign key dependencies)
     * This includes: LearningModules (parent entities)
     */
    @EventListener
    @Order(1)
    @Transactional
    public void initializeReferenceData(ApplicationReadyEvent event) {
        if (phase1Complete || shouldSkipInitialization()) {
            return;
        }
        
        log.info("🚀 Phase 1: Starting reference data initialization...");
        
        try {
            // Initialize learning modules (no foreign key dependencies)
            initializeLearningModules();
            
            phase1Complete = true;
            log.info("✅ Phase 1: Reference data initialization completed successfully");
            
        } catch (Exception e) {
            log.error("❌ Phase 1: Reference data initialization failed", e);
            rollbackPhase1();
            throw new DataInitializationException("Phase 1 initialization failed", e);
        }
    }
    
    /**
     * Phase 2: Initialize relational data (entities with foreign key dependencies)
     * This includes: Topics, InterviewQuestions (child entities)
     */
    @EventListener
    @Order(2)
    @Transactional
    public void initializeRelationalData(ApplicationReadyEvent event) {
        if (phase2Complete || !phase1Complete || shouldSkipInitialization()) {
            return;
        }
        
        log.info("🚀 Phase 2: Starting relational data initialization...");
        
        try {
            // Initialize topics (depend on modules)
            initializeTopics();
            
            // Initialize interview questions (depend on modules)
            initializeInterviewQuestions();
            
            phase2Complete = true;
            initializationComplete = true;
            
            logFinalStatistics();
            log.info("✅ Phase 2: Relational data initialization completed successfully");
            log.info("🎯 FAANG Senior Developer Mastery Portal is ready!");
            
        } catch (Exception e) {
            log.error("❌ Phase 2: Relational data initialization failed", e);
            rollbackPhase2();
            throw new DataInitializationException("Phase 2 initialization failed", e);
        }
    }
    
    /**
     * Emergency rollback for Phase 1 data
     */
    private void rollbackPhase1() {
        log.warn("🔄 Rolling back Phase 1 data...");
        
        try {
            transactionTemplate.execute(status -> {
                // Delete created modules
                for (LearningModule module : createdModules) {
                    if (moduleRepository.existsById(module.getId())) {
                        moduleRepository.delete(module);
                        log.debug("Rolled back module: {}", module.getName());
                    }
                }
                createdModules.clear();
                return null;
            });
            
            log.info("✅ Phase 1 rollback completed");
            
        } catch (Exception rollbackException) {
            log.error("❌ Phase 1 rollback failed", rollbackException);
        }
    }
    
    /**
     * Emergency rollback for Phase 2 data
     */
    private void rollbackPhase2() {
        log.warn("🔄 Rolling back Phase 2 data...");
        
        try {
            transactionTemplate.execute(status -> {
                // Delete created questions first (child entities)
                for (InterviewQuestion question : createdQuestions) {
                    if (questionRepository.existsById(question.getId())) {
                        questionRepository.delete(question);
                        log.debug("Rolled back question: {}", question.getId());
                    }
                }
                createdQuestions.clear();
                
                // Delete created topics
                for (Topic topic : createdTopics) {
                    if (topicRepository.existsById(topic.getId())) {
                        topicRepository.delete(topic);
                        log.debug("Rolled back topic: {}", topic.getTitle());
                    }
                }
                createdTopics.clear();
                
                return null;
            });
            
            log.info("✅ Phase 2 rollback completed");
            
        } catch (Exception rollbackException) {
            log.error("❌ Phase 2 rollback failed", rollbackException);
        }
    }
    
    /**
     * Check if initialization should be skipped (data already exists)
     */
    private boolean shouldSkipInitialization() {
        long moduleCount = moduleRepository.count();
        if (moduleCount > 0) {
            log.info("Database already contains {} modules, skipping initialization", moduleCount);
            return true;
        }
        return false;
    }
    
    /**
     * Initialize learning modules (reference data - no foreign keys)
     */
    private void initializeLearningModules() {
        log.info("📚 Creating learning modules...");
        
        // Create Node.js module
        LearningModule nodeJsModule = createLearningModule(
            "Node.js Fundamentals",
            "Complete Node.js mastery from fundamentals to advanced patterns",
            LearningModule.Category.PROGRAMMING_LANGUAGES,
            LearningModule.DifficultyLevel.INTERMEDIATE,
            45,
            1
        );
        createdModules.add(nodeJsModule);
        
        // Create Java module
        LearningModule javaModule = createLearningModule(
            "Java Fundamentals",
            "Complete Java programming from basics to advanced concepts",
            LearningModule.Category.PROGRAMMING_LANGUAGES,
            LearningModule.DifficultyLevel.BEGINNER,
            120,
            2
        );
        createdModules.add(javaModule);
        
        // Create React module
        LearningModule reactModule = createLearningModule(
            "React Development",
            "Modern React development with hooks, context, and best practices",
            LearningModule.Category.FRONTEND,
            LearningModule.DifficultyLevel.INTERMEDIATE,
            80,
            3
        );
        createdModules.add(reactModule);
        
        // Create Data Structures module
        LearningModule dataStructuresModule = createLearningModule(
            "Data Structures & Algorithms",
            "Complete mastery of data structures and algorithms for FAANG interviews",
            LearningModule.Category.DATA_STRUCTURES,
            LearningModule.DifficultyLevel.ADVANCED,
            100,
            4
        );
        createdModules.add(dataStructuresModule);
        
        // Create Practice Questions module
        LearningModule practiceModule = createLearningModule(
            "Practice Questions",
            "Comprehensive practice questions across all topics with detailed solutions",
            LearningModule.Category.INTERVIEW_PREP,
            LearningModule.DifficultyLevel.INTERMEDIATE,
            60,
            5
        );
        createdModules.add(practiceModule);
        
        // Create FAANG Interview Questions module
        LearningModule faangModule = createLearningModule(
            "FAANG Interview Questions",
            "Real interview questions from Facebook, Amazon, Apple, Netflix, and Google",
            LearningModule.Category.INTERVIEW_PREP,
            LearningModule.DifficultyLevel.EXPERT,
            150,
            6
        );
        createdModules.add(faangModule);
        
        // Create System Design module
        LearningModule systemDesignModule = createLearningModule(
            "System Design",
            "Large-scale distributed systems design for senior engineer interviews",
            LearningModule.Category.SYSTEM_DESIGN,
            LearningModule.DifficultyLevel.EXPERT,
            200,
            7
        );
        createdModules.add(systemDesignModule);
        
        // Create SQL module
        LearningModule sqlModule = createLearningModule(
            "SQL Database Design",
            "Complete SQL mastery from basics to advanced query optimization",
            LearningModule.Category.DATABASES,
            LearningModule.DifficultyLevel.INTERMEDIATE,
            90,
            8
        );
        createdModules.add(sqlModule);
        
        // Create NoSQL module
        LearningModule nosqlModule = createLearningModule(
            "NoSQL Databases",
            "MongoDB, Cassandra, Redis, and other NoSQL database systems",
            LearningModule.Category.DATABASES,
            LearningModule.DifficultyLevel.ADVANCED,
            120,
            9
        );
        createdModules.add(nosqlModule);
        
        // Create Amazon Leadership Principles module
        LearningModule amazonLPModule = createLearningModule(
            "Amazon Leadership Principles",
            "Master all 16 Amazon Leadership Principles with STAR method examples",
            LearningModule.Category.INTERVIEW_PREP,
            LearningModule.DifficultyLevel.INTERMEDIATE,
            40,
            10
        );
        createdModules.add(amazonLPModule);
        
        // Create Behavioral Questions module
        LearningModule behavioralModule = createLearningModule(
            "Behavioral Questions",
            "Common behavioral interview questions with structured STAR responses",
            LearningModule.Category.INTERVIEW_PREP,
            LearningModule.DifficultyLevel.BEGINNER,
            30,
            11
        );
        createdModules.add(behavioralModule);
        
        // Create Cheatsheets module
        LearningModule cheatsheetsModule = createLearningModule(
            "Cheatsheets",
            "Quick reference guides for algorithms, data structures, and system design",
            LearningModule.Category.INTERVIEW_PREP,
            LearningModule.DifficultyLevel.BEGINNER,
            20,
            12
        );
        createdModules.add(cheatsheetsModule);
        
        // Create Summaries module
        LearningModule summariesModule = createLearningModule(
            "Topic Summaries",
            "Concise summaries of all major topics for quick review and revision",
            LearningModule.Category.INTERVIEW_PREP,
            LearningModule.DifficultyLevel.BEGINNER,
            25,
            13
        );
        createdModules.add(summariesModule);
        
        log.info("✅ Created {} learning modules", createdModules.size());
    }
    
    /**
     * Initialize topics (relational data - depends on modules)
     */
    private void initializeTopics() {
        log.info("📖 Creating topics...");
        
        // Find modules to create topics for
        LearningModule nodeJsModule = findModuleByName("Node.js Fundamentals");
        LearningModule javaModule = findModuleByName("Java Fundamentals");
        LearningModule reactModule = findModuleByName("React Development");
        LearningModule dataStructuresModule = findModuleByName("Data Structures & Algorithms");
        LearningModule practiceModule = findModuleByName("Practice Questions");
        LearningModule faangModule = findModuleByName("FAANG Interview Questions");
        LearningModule systemDesignModule = findModuleByName("System Design");
        LearningModule sqlModule = findModuleByName("SQL Database Design");
        LearningModule nosqlModule = findModuleByName("NoSQL Databases");
        LearningModule amazonLPModule = findModuleByName("Amazon Leadership Principles");
        LearningModule behavioralModule = findModuleByName("Behavioral Questions");
        LearningModule cheatsheetsModule = findModuleByName("Cheatsheets");
        LearningModule summariesModule = findModuleByName("Topic Summaries");
        
        if (nodeJsModule != null) {
            Topic nodeJsTopic = createTopic(
                "Node.js Core Concepts and Event Loop",
                "Master Node.js fundamentals: event loop, non-blocking I/O, streams, and core modules",
                "<h2>Node.js Core Concepts</h2><p>Learn the fundamentals of Node.js architecture and event loop.</p>",
                nodeJsModule,
                1,
                120,
                Topic.TopicType.LEARNING_CONTENT
            );
            createdTopics.add(nodeJsTopic);
        }
        
        if (javaModule != null) {
            Topic javaTopic = createTopic(
                "Java Basics: Variables, Data Types, and Operators",
                "Master Java fundamentals: variables, primitive types, operators, and memory management",
                "<h2>Java Basics</h2><p>Learn the fundamentals of Java programming language.</p>",
                javaModule,
                1,
                180,
                Topic.TopicType.LEARNING_CONTENT
            );
            createdTopics.add(javaTopic);
        }
        
        if (reactModule != null) {
            Topic reactTopic = createTopic(
                "React Hooks and State Management",
                "Master React hooks: useState, useEffect, useContext, and custom hooks",
                "<h2>React Hooks</h2><p>Learn modern React development with hooks.</p>",
                reactModule,
                1,
                150,
                Topic.TopicType.LEARNING_CONTENT
            );
            createdTopics.add(reactTopic);
        }
        
        if (dataStructuresModule != null) {
            Topic arraysTopic = createTopic(
                "Arrays and Strings",
                "Master array manipulation, string algorithms, and two-pointer techniques",
                "<h2>Arrays and Strings</h2><p>Essential data structures for coding interviews.</p>",
                dataStructuresModule,
                1,
                120,
                Topic.TopicType.LEARNING_CONTENT
            );
            createdTopics.add(arraysTopic);
            
            Topic linkedListTopic = createTopic(
                "Linked Lists",
                "Singly, doubly linked lists, and advanced pointer manipulation",
                "<h2>Linked Lists</h2><p>Master pointer-based data structures.</p>",
                dataStructuresModule,
                2,
                90,
                Topic.TopicType.LEARNING_CONTENT
            );
            createdTopics.add(linkedListTopic);
            
            Topic treesTopic = createTopic(
                "Trees and Graphs",
                "Binary trees, BSTs, graph traversal algorithms (DFS, BFS)",
                "<h2>Trees and Graphs</h2><p>Complex data structures and traversal algorithms.</p>",
                dataStructuresModule,
                3,
                180,
                Topic.TopicType.LEARNING_CONTENT
            );
            createdTopics.add(treesTopic);
        }
        
        if (practiceModule != null) {
            Topic easyPracticeTopic = createTopic(
                "Easy Practice Problems",
                "Beginner-friendly coding problems to build confidence",
                "<h2>Easy Practice</h2><p>Start your coding interview preparation here.</p>",
                practiceModule,
                1,
                60,
                Topic.TopicType.PRACTICE_EXERCISE
            );
            createdTopics.add(easyPracticeTopic);
            
            Topic mediumPracticeTopic = createTopic(
                "Medium Practice Problems",
                "Intermediate coding challenges for skill development",
                "<h2>Medium Practice</h2><p>Build your problem-solving skills.</p>",
                practiceModule,
                2,
                90,
                Topic.TopicType.PRACTICE_EXERCISE
            );
            createdTopics.add(mediumPracticeTopic);
        }
        
        if (faangModule != null) {
            Topic faangSystemDesignTopic = createTopic(
                "FAANG System Design Questions",
                "Real system design questions from top tech companies",
                "<h2>System Design</h2><p>Master large-scale system architecture.</p>",
                faangModule,
                1,
                240,
                Topic.TopicType.INTERVIEW_QUESTION
            );
            createdTopics.add(faangSystemDesignTopic);
            
            Topic faangCodingTopic = createTopic(
                "FAANG Coding Challenges",
                "Actual coding questions asked at FAANG companies",
                "<h2>FAANG Coding</h2><p>Real interview questions with solutions.</p>",
                faangModule,
                2,
                180,
                Topic.TopicType.INTERVIEW_QUESTION
            );
            createdTopics.add(faangCodingTopic);
        }
        
        if (systemDesignModule != null) {
            Topic scalabilityTopic = createTopic(
                "Scalability and Load Balancing",
                "Design systems that handle millions of users with proper load distribution",
                "<h2>Scalability</h2><p>Learn to design systems for massive scale.</p>",
                systemDesignModule,
                1,
                180,
                Topic.TopicType.LEARNING_CONTENT
            );
            createdTopics.add(scalabilityTopic);
            
            Topic databaseDesignTopic = createTopic(
                "Database Design and Sharding",
                "Design databases for large-scale applications with proper partitioning",
                "<h2>Database Design</h2><p>Master database architecture for scale.</p>",
                systemDesignModule,
                2,
                150,
                Topic.TopicType.LEARNING_CONTENT
            );
            createdTopics.add(databaseDesignTopic);
            
            Topic cachingTopic = createTopic(
                "Caching Strategies",
                "Implement effective caching with Redis, Memcached, and CDNs",
                "<h2>Caching</h2><p>Optimize performance with smart caching.</p>",
                systemDesignModule,
                3,
                120,
                Topic.TopicType.LEARNING_CONTENT
            );
            createdTopics.add(cachingTopic);
        }
        
        if (sqlModule != null) {
            Topic sqlBasicsTopic = createTopic(
                "SQL Fundamentals",
                "Master SELECT, JOIN, GROUP BY, and basic SQL operations",
                "<h2>SQL Basics</h2><p>Foundation of relational database queries.</p>",
                sqlModule,
                1,
                90,
                Topic.TopicType.LEARNING_CONTENT
            );
            createdTopics.add(sqlBasicsTopic);
            
            Topic sqlAdvancedTopic = createTopic(
                "Advanced SQL and Query Optimization",
                "Window functions, CTEs, query performance tuning, and indexing",
                "<h2>Advanced SQL</h2><p>Master complex queries and optimization.</p>",
                sqlModule,
                2,
                120,
                Topic.TopicType.LEARNING_CONTENT
            );
            createdTopics.add(sqlAdvancedTopic);
        }
        
        if (nosqlModule != null) {
            Topic mongodbTopic = createTopic(
                "MongoDB Document Database",
                "Master MongoDB operations, aggregation pipeline, and schema design",
                "<h2>MongoDB</h2><p>Leading document-based NoSQL database.</p>",
                nosqlModule,
                1,
                100,
                Topic.TopicType.LEARNING_CONTENT
            );
            createdTopics.add(mongodbTopic);
            
            Topic redisTopic = createTopic(
                "Redis In-Memory Database",
                "Redis data structures, caching patterns, and pub/sub messaging",
                "<h2>Redis</h2><p>High-performance in-memory data store.</p>",
                nosqlModule,
                2,
                80,
                Topic.TopicType.LEARNING_CONTENT
            );
            createdTopics.add(redisTopic);
        }
        
        if (amazonLPModule != null) {
            Topic customerObsessionTopic = createTopic(
                "Customer Obsession",
                "Amazon's #1 Leadership Principle with STAR method examples",
                "<h2>Customer Obsession</h2><p>Start with the customer and work backwards.</p>",
                amazonLPModule,
                1,
                30,
                Topic.TopicType.LEARNING_CONTENT
            );
            createdTopics.add(customerObsessionTopic);
            
            Topic ownershipTopic = createTopic(
                "Ownership",
                "Think long term and don't sacrifice long-term value for short-term results",
                "<h2>Ownership</h2><p>Act on behalf of the entire company.</p>",
                amazonLPModule,
                2,
                30,
                Topic.TopicType.LEARNING_CONTENT
            );
            createdTopics.add(ownershipTopic);
            
            Topic inventSimplifyTopic = createTopic(
                "Invent and Simplify",
                "Expect and require innovation and invention from teams",
                "<h2>Invent and Simplify</h2><p>Simplicity is the ultimate sophistication.</p>",
                amazonLPModule,
                3,
                30,
                Topic.TopicType.LEARNING_CONTENT
            );
            createdTopics.add(inventSimplifyTopic);
        }
        
        if (behavioralModule != null) {
            Topic starMethodTopic = createTopic(
                "STAR Method Framework",
                "Structure behavioral responses with Situation, Task, Action, Result",
                "<h2>STAR Method</h2><p>Framework for behavioral interview success.</p>",
                behavioralModule,
                1,
                45,
                Topic.TopicType.LEARNING_CONTENT
            );
            createdTopics.add(starMethodTopic);
            
            Topic commonBehavioralTopic = createTopic(
                "Common Behavioral Questions",
                "Most frequently asked behavioral questions with sample responses",
                "<h2>Common Questions</h2><p>Prepare for the most common scenarios.</p>",
                behavioralModule,
                2,
                60,
                Topic.TopicType.INTERVIEW_QUESTION
            );
            createdTopics.add(commonBehavioralTopic);
        }
        
        if (cheatsheetsModule != null) {
            Topic algoCheatsheetTopic = createTopic(
                "Algorithms Cheatsheet",
                "Quick reference for sorting, searching, and graph algorithms",
                "<h2>Algorithms</h2><p>Time/space complexity and implementation patterns.</p>",
                cheatsheetsModule,
                1,
                20,
                Topic.TopicType.CODE_EXAMPLE
            );
            createdTopics.add(algoCheatsheetTopic);
            
            Topic dataStructureCheatsheetTopic = createTopic(
                "Data Structures Cheatsheet",
                "Quick reference for arrays, trees, graphs, and hash tables",
                "<h2>Data Structures</h2><p>Operations and use cases summary.</p>",
                cheatsheetsModule,
                2,
                20,
                Topic.TopicType.CODE_EXAMPLE
            );
            createdTopics.add(dataStructureCheatsheetTopic);
            
            Topic systemDesignCheatsheetTopic = createTopic(
                "System Design Cheatsheet",
                "Key concepts, patterns, and numbers for system design interviews",
                "<h2>System Design</h2><p>Essential patterns and scalability numbers.</p>",
                cheatsheetsModule,
                3,
                25,
                Topic.TopicType.CODE_EXAMPLE
            );
            createdTopics.add(systemDesignCheatsheetTopic);
        }
        
        if (summariesModule != null) {
            Topic javaSummaryTopic = createTopic(
                "Java Concepts Summary",
                "Concise summary of all Java fundamentals and advanced concepts",
                "<h2>Java Summary</h2><p>Everything you need to know about Java.</p>",
                summariesModule,
                1,
                30,
                Topic.TopicType.LEARNING_CONTENT
            );
            createdTopics.add(javaSummaryTopic);
            
            Topic algorithmsSummaryTopic = createTopic(
                "Algorithms Summary",
                "Key algorithms and their applications in coding interviews",
                "<h2>Algorithms Summary</h2><p>Essential algorithms for interviews.</p>",
                summariesModule,
                2,
                25,
                Topic.TopicType.LEARNING_CONTENT
            );
            createdTopics.add(algorithmsSummaryTopic);
            
            Topic systemDesignSummaryTopic = createTopic(
                "System Design Summary",
                "Core system design principles and common architecture patterns",
                "<h2>System Design Summary</h2><p>Key concepts for system design interviews.</p>",
                summariesModule,
                3,
                35,
                Topic.TopicType.LEARNING_CONTENT
            );
            createdTopics.add(systemDesignSummaryTopic);
        }
        
        log.info("✅ Created {} topics", createdTopics.size());
    }
    
    /**
     * Initialize interview questions (relational data - depends on modules)
     */
    private void initializeInterviewQuestions() {
        log.info("❓ Creating interview questions...");
        
        // Find modules to create questions for
        LearningModule nodeJsModule = findModuleByName("Node.js Fundamentals");
        LearningModule javaModule = findModuleByName("Java Fundamentals");
        LearningModule reactModule = findModuleByName("React Development");
        LearningModule dataStructuresModule = findModuleByName("Data Structures & Algorithms");
        LearningModule practiceModule = findModuleByName("Practice Questions");
        LearningModule faangModule = findModuleByName("FAANG Interview Questions");
        LearningModule systemDesignModule = findModuleByName("System Design");
        LearningModule sqlModule = findModuleByName("SQL Database Design");
        LearningModule nosqlModule = findModuleByName("NoSQL Databases");
        LearningModule amazonLPModule = findModuleByName("Amazon Leadership Principles");
        LearningModule behavioralModule = findModuleByName("Behavioral Questions");
        
        if (nodeJsModule != null) {
            createInterviewQuestionsForModule(nodeJsModule, "Node.js", new String[][]{
                {"Explain the Node.js Event Loop", "Amazon"},
                {"What is the difference between setImmediate and setTimeout?", "Google"},
                {"How does Node.js handle child processes?", "Microsoft"},
                {"Explain Node.js streams and their types", "Meta"},
                {"What is the purpose of the cluster module in Node.js?", "Apple"}
            });
        }
        
        if (javaModule != null) {
            createInterviewQuestionsForModule(javaModule, "Java", new String[][]{
                {"Difference between int and Integer in Java", "Amazon"},
                {"Explain method overloading vs overriding", "Google"},
                {"When to use ArrayList vs LinkedList?", "Microsoft"},
                {"What is the difference between == and equals() in Java?", "Meta"},
                {"Explain Java memory management and garbage collection", "Apple"}
            });
        }
        
        if (reactModule != null) {
            createInterviewQuestionsForModule(reactModule, "React", new String[][]{
                {"What are React hooks and why were they introduced?", "Amazon"},
                {"Explain the difference between useState and useReducer", "Google"},
                {"How does React's virtual DOM work?", "Microsoft"},
                {"What is the purpose of useEffect hook?", "Meta"},
                {"Explain React context and when to use it", "Apple"}
            });
        }
        
        if (dataStructuresModule != null) {
            createInterviewQuestionsForModule(dataStructuresModule, "Data Structures", new String[][]{
                {"Implement a function to reverse a linked list", "Amazon"},
                {"Find the maximum depth of a binary tree", "Google"},
                {"Detect if a linked list has a cycle", "Microsoft"},
                {"Implement a stack using queues", "Meta"},
                {"Find the lowest common ancestor of two nodes in a BST", "Apple"},
                {"Implement breadth-first search for a graph", "Amazon"},
                {"Find the kth largest element in an array", "Google"},
                {"Merge two sorted linked lists", "Microsoft"}
            });
        }
        
        if (practiceModule != null) {
            createInterviewQuestionsForModule(practiceModule, "Practice", new String[][]{
                {"Two Sum - Find two numbers that add up to target", "LeetCode"},
                {"Valid Parentheses - Check if brackets are balanced", "LeetCode"},
                {"Maximum Subarray - Find contiguous subarray with largest sum", "LeetCode"},
                {"Climbing Stairs - Count ways to reach the top", "LeetCode"},
                {"Best Time to Buy and Sell Stock", "LeetCode"},
                {"Valid Palindrome - Check if string is palindrome", "LeetCode"}
            });
        }
        
        if (faangModule != null) {
            createInterviewQuestionsForModule(faangModule, "FAANG", new String[][]{
                {"Design a URL shortener like bit.ly", "Amazon"},
                {"Design Instagram's photo sharing system", "Meta"},
                {"Design a chat system like WhatsApp", "Meta"},
                {"Design Netflix's video streaming service", "Netflix"},
                {"Design Google's search engine", "Google"},
                {"Design Amazon's recommendation system", "Amazon"},
                {"Design Apple's iCloud storage system", "Apple"},
                {"Design a distributed cache system", "Google"},
                {"Design a ride-sharing service like Uber", "Amazon"},
                {"Design a social media feed like Twitter", "Meta"},
                {"Serialize and Deserialize Binary Tree", "Amazon"},
                {"Word Ladder - Transform one word to another", "Google"},
                {"Alien Dictionary - Find order of characters", "Meta"},
                {"Meeting Rooms II - Minimum conference rooms needed", "Google"},
                {"Trapping Rain Water - Calculate trapped rainwater", "Amazon"}
            });
        }
        
        if (systemDesignModule != null) {
            createInterviewQuestionsForModule(systemDesignModule, "System Design", new String[][]{
                {"How would you design a scalable web crawler?", "Google"},
                {"Design a distributed key-value store", "Amazon"},
                {"How would you design a notification system?", "Meta"},
                {"Design a rate limiter for an API", "Amazon"},
                {"How would you design a search autocomplete system?", "Google"},
                {"Design a distributed message queue", "Amazon"},
                {"How would you design a video streaming service?", "Netflix"},
                {"Design a global file storage system", "Google"},
                {"How would you design a real-time gaming leaderboard?", "Amazon"},
                {"Design a distributed logging system", "Google"}
            });
        }
        
        if (sqlModule != null) {
            createInterviewQuestionsForModule(sqlModule, "SQL", new String[][]{
                {"Write a query to find the second highest salary", "Amazon"},
                {"Find employees who earn more than their managers", "Google"},
                {"Write a query to find duplicate records", "Microsoft"},
                {"Calculate running totals using window functions", "Meta"},
                {"Find the top N records in each group", "Amazon"},
                {"Write a query to pivot data from rows to columns", "Google"},
                {"Find gaps in sequential data", "Microsoft"},
                {"Calculate year-over-year growth rates", "Amazon"},
                {"Write a recursive CTE to traverse hierarchical data", "Google"},
                {"Optimize a slow-running query", "Meta"}
            });
        }
        
        if (nosqlModule != null) {
            createInterviewQuestionsForModule(nosqlModule, "NoSQL", new String[][]{
                {"When would you choose MongoDB over PostgreSQL?", "Amazon"},
                {"Explain MongoDB aggregation pipeline", "Google"},
                {"How does Redis handle persistence?", "Microsoft"},
                {"Design a schema for a social media app in MongoDB", "Meta"},
                {"Explain Redis data structures and use cases", "Amazon"},
                {"How would you implement caching with Redis?", "Google"},
                {"Compare ACID vs BASE properties", "Amazon"},
                {"Explain eventual consistency in distributed systems", "Google"},
                {"How does Cassandra handle data distribution?", "Netflix"},
                {"Design a time-series database schema", "Amazon"}
            });
        }
        
        if (amazonLPModule != null) {
            createInterviewQuestionsForModule(amazonLPModule, "Amazon LP", new String[][]{
                {"Tell me about a time you obsessed over a customer", "Amazon"},
                {"Describe a time you took ownership of a problem", "Amazon"},
                {"Give an example of when you invented and simplified", "Amazon"},
                {"Tell me about a time you were right a lot", "Amazon"},
                {"Describe a time you learned and were curious", "Amazon"},
                {"Give an example of when you hired and developed the best", "Amazon"},
                {"Tell me about a time you insisted on the highest standards", "Amazon"},
                {"Describe a time you thought big", "Amazon"},
                {"Give an example of when you had bias for action", "Amazon"},
                {"Tell me about a time you were frugal", "Amazon"},
                {"Describe a time you earned trust", "Amazon"},
                {"Give an example of when you dove deep", "Amazon"},
                {"Tell me about a time you had backbone and disagreed", "Amazon"},
                {"Describe a time you delivered results", "Amazon"},
                {"Give an example of when you strived to be Earth's best employer", "Amazon"},
                {"Tell me about a time you took success and scale seriously", "Amazon"}
            });
        }
        
        if (behavioralModule != null) {
            createInterviewQuestionsForModule(behavioralModule, "Behavioral", new String[][]{
                {"Tell me about yourself", "General"},
                {"Why do you want to work here?", "General"},
                {"What are your strengths and weaknesses?", "General"},
                {"Describe a challenging project you worked on", "General"},
                {"Tell me about a time you failed", "General"},
                {"How do you handle conflict with team members?", "General"},
                {"Describe a time you had to learn something quickly", "General"},
                {"Tell me about a time you disagreed with your manager", "General"},
                {"How do you prioritize your work?", "General"},
                {"Where do you see yourself in 5 years?", "General"},
                {"Tell me about a time you had to work with a difficult person", "General"},
                {"Describe your ideal work environment", "General"}
            });
        }
        
        log.info("✅ Created {} interview questions", createdQuestions.size());
    }
    
    // Helper methods
    
    private LearningModule createLearningModule(String name, String description, 
                                              LearningModule.Category category,
                                              LearningModule.DifficultyLevel difficultyLevel,
                                              int estimatedHours, int sortOrder) {
        LearningModule module = new LearningModule();
        module.setName(name);
        module.setDescription(description);
        module.setCategory(category);
        module.setDifficultyLevel(difficultyLevel);
        module.setEstimatedHours(estimatedHours);
        module.setSortOrder(sortOrder);
        
        LearningModule savedModule = moduleRepository.save(module);
        log.debug("Created module: {} (ID: {})", savedModule.getName(), savedModule.getId());
        return savedModule;
    }
    
    private Topic createTopic(String title, String description, String content,
                            LearningModule module, int sortOrder, int estimatedMinutes,
                            Topic.TopicType topicType) {
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setDescription(description);
        topic.setContent(content);
        topic.setModule(module);
        topic.setSortOrder(sortOrder);
        topic.setEstimatedMinutes(estimatedMinutes);
        topic.setTopicType(topicType);
        
        Topic savedTopic = topicRepository.save(topic);
        log.debug("Created topic: {} (ID: {})", savedTopic.getTitle(), savedTopic.getId());
        return savedTopic;
    }
    
    private void createInterviewQuestionsForModule(LearningModule module, String topic, String[][] questionsData) {
        for (String[] questionData : questionsData) {
            String questionText = questionData[0];
            String company = questionData[1];
            
            InterviewQuestion question = new InterviewQuestion();
            question.setQuestion(questionText);
            question.setAnswer("This is a comprehensive answer for: " + questionText + 
                             ". This question is commonly asked at " + company + 
                             " and requires understanding of " + topic + " fundamentals.");
            question.setDifficulty(InterviewQuestion.Difficulty.MEDIUM);
            question.setCompany(company);
            question.setTopic(topic);
            question.setModule(module);
            question.setFrequencyScore(8);
            question.setTags(topic.toLowerCase() + ",interview,faang");
            
            InterviewQuestion savedQuestion = questionRepository.save(question);
            createdQuestions.add(savedQuestion);
            log.debug("Created question: {} for {} (ID: {})", 
                     questionText, company, savedQuestion.getId());
        }
    }
    
    private LearningModule findModuleByName(String name) {
        return createdModules.stream()
                           .filter(module -> module.getName().equals(name))
                           .findFirst()
                           .orElse(null);
    }
    
    private void logFinalStatistics() {
        log.info("📊 Final Statistics:");
        log.info("   📚 Total Modules: {}", moduleRepository.count());
        log.info("   📖 Total Topics: {}", topicRepository.count());
        log.info("   ❓ Total Interview Questions: {}", questionRepository.count());
        
        // Log breakdown by category
        for (LearningModule.Category category : LearningModule.Category.values()) {
            long count = createdModules.stream()
                                     .filter(module -> module.getCategory() == category)
                                     .count();
            if (count > 0) {
                log.info("   📂 {}: {} modules", category.getDisplayName(), count);
            }
        }
    }
    
    // Getters for testing and monitoring
    public boolean isPhase1Complete() { return phase1Complete; }
    public boolean isPhase2Complete() { return phase2Complete; }
    public boolean isInitializationComplete() { return initializationComplete; }
    public int getCreatedModulesCount() { return createdModules.size(); }
    public int getCreatedTopicsCount() { return createdTopics.size(); }
    public int getCreatedQuestionsCount() { return createdQuestions.size(); }
    
    /**
     * Custom exception for data initialization failures
     */
    public static class DataInitializationException extends RuntimeException {
        public DataInitializationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}