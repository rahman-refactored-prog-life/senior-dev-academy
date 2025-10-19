package com.learningportal.config;

import com.learningportal.model.InterviewQuestion;
import com.learningportal.model.LearningModule;
import com.learningportal.model.Topic;
import com.learningportal.repository.InterviewQuestionRepository;
import com.learningportal.repository.LearningModuleRepository;
import com.learningportal.repository.TopicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    
    private final LearningModuleRepository moduleRepository;
    private final TopicRepository topicRepository;
    private final InterviewQuestionRepository questionRepository;

    public DataInitializer(
            LearningModuleRepository moduleRepository,
            TopicRepository topicRepository,
            InterviewQuestionRepository questionRepository) {
        this.moduleRepository = moduleRepository;
        this.topicRepository = topicRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        log.info("🚀 Starting FAANG Senior Developer Mastery Portal Data Initialization...");
        
        if (moduleRepository.count() > 0) {
            log.info("📚 Data already exists. Skipping initialization.");
            return;
        }
        
        try {
            createNodeJsFundamentalsModule();
            createJavaFundamentalsModule();
            
            log.info("✅ Data initialization completed successfully!");
            logStatistics();
            
        } catch (Exception e) {
            log.error("❌ Error during data initialization", e);
            throw e;
        }
    }

    private void createNodeJsFundamentalsModule() {
        log.info("📚 Creating Node.js Fundamentals module...");
        
        LearningModule module = new LearningModule();
        module.setName("Node.js Fundamentals to Expert");
        module.setDescription("Complete Node.js mastery: from fundamentals to advanced patterns, microservices, and performance optimization");
        module.setCategory(LearningModule.Category.MICROSERVICES);
        module.setDifficultyLevel(LearningModule.DifficultyLevel.INTERMEDIATE);
        module.setEstimatedHours(45);
        module.setSortOrder(1);
        
        moduleRepository.save(module);
        
        createNodeJsCoreTopic(module);
        createNodeJsInterviewQuestions(module);
        
        log.info("✅ Created Node.js Fundamentals module with {} topics and {} questions", 
                module.getTopics().size(), module.getInterviewQuestions().size());
    }

    private void createJavaFundamentalsModule() {
        log.info("📚 Creating Java Fundamentals module...");
        
        LearningModule module = new LearningModule();
        module.setName("Java Fundamentals to Expert");
        module.setDescription("Complete Java mastery from basics to advanced concepts");
        module.setCategory(LearningModule.Category.PROGRAMMING_LANGUAGES);
        module.setDifficultyLevel(LearningModule.DifficultyLevel.INTERMEDIATE);
        module.setEstimatedHours(50);
        module.setSortOrder(2);
        
        moduleRepository.save(module);
        
        createJavaBasicsTopic(module);
        
        log.info("✅ Created Java Fundamentals module");
    }

    private void createNodeJsCoreTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Node.js Core Concepts and Event Loop");
        topic.setDescription("Master Node.js fundamentals: event loop, non-blocking I/O, streams, and core modules");
        topic.setContent("Complete Node.js core concepts with event loop, streams, and performance optimization");
        topic.setEstimatedMinutes(180);
        topic.setSortOrder(1);
        topic.setModule(module);
        module.getTopics().add(topic);
        topicRepository.save(topic);
    }

    private void createJavaBasicsTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Java Basics: Variables, Data Types, and Operators");
        topic.setDescription("Master Java fundamentals including primitive types, variables, operators, and control structures");
        topic.setContent("Complete Java basics with variables, data types, operators, and control flow");
        topic.setEstimatedMinutes(120);
        topic.setSortOrder(1);
        topic.setModule(module);
        module.getTopics().add(topic);
        topicRepository.save(topic);
    }

    private void createNodeJsInterviewQuestions(LearningModule module) {
        List<InterviewQuestion> questions = new ArrayList<>();
        
        questions.addAll(Arrays.asList(
            createInterviewQuestion("Explain the Node.js event loop and its phases in detail.", 
                "HARD", "Amazon", "Node.js", module,
                "Event loop phases: 1) Timer phase 2) Pending callbacks 3) Poll 4) Check 5) Close callbacks"),
            
            createInterviewQuestion("What is the difference between process.nextTick() and setImmediate()?", 
                "MEDIUM", "Google", "Node.js", module,
                "process.nextTick() has highest priority, executes before any other async operation"),
            
            createInterviewQuestion("How does Node.js handle child processes?", 
                "HARD", "Microsoft", "Node.js", module,
                "spawn(), exec(), fork() - different ways to create child processes")
        ));
        
        module.getInterviewQuestions().addAll(questions);
        questionRepository.saveAll(questions);
        
        log.info("✅ Created {} Node.js interview questions", questions.size());
    }

    private InterviewQuestion createInterviewQuestion(String question, String difficulty, 
            String company, String topic, LearningModule module, String answer) {
        InterviewQuestion iq = new InterviewQuestion();
        iq.setQuestion(question);
        iq.setDifficulty(InterviewQuestion.Difficulty.valueOf(difficulty));
        iq.setCompany(company);
        iq.setTopic(topic);
        iq.setModule(module);
        iq.setAnswer(answer);
        iq.setFrequencyScore(8);
        return iq;
    }

    private void logStatistics() {
        long totalModules = moduleRepository.count();
        long totalTopics = topicRepository.count();
        long totalQuestions = questionRepository.count();
        
        log.info("📊 Final Statistics:");
        log.info("   📚 Total Modules: {}", totalModules);
        log.info("   📖 Total Topics: {}", totalTopics);
        log.info("   ❓ Total Interview Questions: {}", totalQuestions);
        log.info("🎯 FAANG Senior Developer Mastery Portal is ready!");
    }
}