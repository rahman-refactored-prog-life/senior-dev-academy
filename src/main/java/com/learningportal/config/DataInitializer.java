package com.learningportal.config;

import com.learningportal.model.*;
import com.learningportal.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Simple Data Initializer - Basic setup to get the application running
 * 
 * This class demonstrates:
 * - CommandLineRunner for startup data initialization
 * - Transactional data operations with PostgreSQL
 * - Repository pattern usage
 * - Standard Java implementations (no Lombok)
 */
// @Component - Disabled in favor of SafeDataInitializer
public class DataInitializer implements CommandLineRunner {
    
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    
    private final LearningModuleRepository moduleRepository;
    private final TopicRepository topicRepository;
    private final InterviewQuestionRepository questionRepository;
    
    public DataInitializer(LearningModuleRepository moduleRepository,
                          TopicRepository topicRepository,
                          InterviewQuestionRepository questionRepository) {
        this.moduleRepository = moduleRepository;
        this.topicRepository = topicRepository;
        this.questionRepository = questionRepository;
    }
    
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (moduleRepository.count() == 0) {
            log.info("🚀 Starting FAANG Senior Developer Mastery Portal Data Initialization...");
            
            // Create basic modules
            createNodeJsModule();
            createJavaModule();
            
            log.info("✅ Data initialization completed successfully!");
            log.info("📊 Final Statistics:");
            log.info("   📚 Total Modules: {}", moduleRepository.count());
            log.info("   📖 Total Topics: {}", topicRepository.count());
            log.info("   ❓ Total Interview Questions: {}", questionRepository.count());
            log.info("🎯 FAANG Senior Developer Mastery Portal is ready!");
        } else {
            log.info("Database already contains data, skipping initialization.");
        }
    }
    
    private void createNodeJsModule() {
        LearningModule module = new LearningModule();
        module.setName("Node.js Fundamentals");
        module.setDescription("Complete Node.js mastery from fundamentals to advanced patterns");
        module.setCategory(LearningModule.Category.PROGRAMMING_LANGUAGES);
        module.setDifficultyLevel(LearningModule.DifficultyLevel.INTERMEDIATE);
        module.setEstimatedHours(45);
        module.setSortOrder(1);
        
        moduleRepository.save(module);
        
        // Create a basic topic
        Topic topic = new Topic();
        topic.setTitle("Node.js Core Concepts and Event Loop");
        topic.setDescription("Master Node.js fundamentals: event loop, non-blocking I/O, streams, and core modules");
        topic.setContent("<h2>Node.js Core Concepts</h2><p>Learn the fundamentals of Node.js architecture and event loop.</p>");
        topic.setModule(module);
        topic.setSortOrder(1);
        topic.setEstimatedMinutes(120);
        topic.setTopicType(Topic.TopicType.LEARNING_CONTENT);
        
        topicRepository.save(topic);
        
        // Create basic interview questions
        createBasicInterviewQuestion(module, "Explain the Node.js Event Loop", "Node.js", "Amazon");
        createBasicInterviewQuestion(module, "What is the difference between setImmediate and setTimeout?", "Node.js", "Google");
        createBasicInterviewQuestion(module, "How does Node.js handle child processes?", "Node.js", "Microsoft");
        
        log.info("✅ Created Node.js Fundamentals module");
    }
    
    private void createJavaModule() {
        LearningModule module = new LearningModule();
        module.setName("Java Fundamentals");
        module.setDescription("Complete Java programming from basics to advanced concepts");
        module.setCategory(LearningModule.Category.PROGRAMMING_LANGUAGES);
        module.setDifficultyLevel(LearningModule.DifficultyLevel.BEGINNER);
        module.setEstimatedHours(120);
        module.setSortOrder(2);
        
        moduleRepository.save(module);
        
        // Create a basic topic
        Topic topic = new Topic();
        topic.setTitle("Java Basics: Variables, Data Types, and Operators");
        topic.setDescription("Master Java fundamentals: variables, primitive types, operators, and memory management");
        topic.setContent("<h2>Java Basics</h2><p>Learn the fundamentals of Java programming language.</p>");
        topic.setModule(module);
        topic.setSortOrder(1);
        topic.setEstimatedMinutes(180);
        topic.setTopicType(Topic.TopicType.LEARNING_CONTENT);
        
        topicRepository.save(topic);
        
        // Create basic interview questions
        createBasicInterviewQuestion(module, "Difference between int and Integer in Java", "Java", "Amazon");
        createBasicInterviewQuestion(module, "Explain method overloading vs overriding", "Java", "Google");
        createBasicInterviewQuestion(module, "When to use ArrayList vs LinkedList?", "Java", "Microsoft");
        
        log.info("✅ Created Java Fundamentals module");
    }
    
    private void createBasicInterviewQuestion(LearningModule module, String questionText, String topic, String company) {
        InterviewQuestion question = new InterviewQuestion();
        question.setQuestion(questionText);
        question.setAnswer("This is a sample answer for: " + questionText);
        question.setDifficulty(InterviewQuestion.Difficulty.MEDIUM);
        question.setCompany(company);
        question.setTopic(topic);
        question.setModule(module);
        question.setFrequencyScore(8);
        
        questionRepository.save(question);
    }
}