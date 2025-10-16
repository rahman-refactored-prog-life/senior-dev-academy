package com.learningportal.config;

import com.learningportal.model.*;
import com.learningportal.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Data Initializer - Populates the database with comprehensive learning content
 * 
 * This class demonstrates:
 * - CommandLineRunner for startup data initialization
 * - Transactional data operations
 * - Repository pattern usage
 * - Real-world data modeling
 * - Comprehensive content creation for learning portal
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    
    private final UserRepository userRepository;
    private final LearningModuleRepository moduleRepository;
    private final TopicRepository topicRepository;
    private final InterviewQuestionRepository questionRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            log.info("Initializing database with comprehensive learning content...");
            
            // Create admin user
            createAdminUser();
            
            // Create learning modules with comprehensive content
            createJavaFundamentalsModule();
            createSpringFrameworkModule();
            createReactDevelopmentModule();
            createDataStructuresModule();
            createAlgorithmsModule();
            createSystemDesignModule();
            createInterviewPrepModule();
            
            log.info("Database initialization completed successfully!");
        } else {
            log.info("Database already contains data, skipping initialization.");
        }
    }
    
    private void createAdminUser() {
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@seniordevacademy.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setFirstName("System");
        admin.setLastName("Administrator");
        admin.setRole(User.Role.ADMIN);
        admin.setActive(true);
        
        userRepository.save(admin);
        log.info("Created admin user: {}", admin.getUsername());
    }
    
    private void createJavaFundamentalsModule() {
        LearningModule javaModule = new LearningModule();
        javaModule.setName("Java Fundamentals");
        javaModule.setDescription("Complete Java programming from basics to advanced concepts");
        javaModule.setDetailedContent("Master Java programming language with comprehensive coverage of core concepts, object-oriented programming, collections, concurrency, and JVM internals.");
        javaModule.setCategory(LearningModule.Category.JAVA_FUNDAMENTALS);
        javaModule.setDifficultyLevel(LearningModule.DifficultyLevel.BEGINNER);
        javaModule.setEstimatedHours(120);
        javaModule.setSortOrder(1);
        
        moduleRepository.save(javaModule);
        
        // Create topics for Java Fundamentals
        createJavaTopics(javaModule);
        
        log.info("Created Java Fundamentals module with topics");
    }
    
    private void createJavaTopics(LearningModule module) {
        // Topic 1: Variables and Data Types
        Topic variablesTopic = new Topic();
        variablesTopic.setTitle("Variables and Data Types");
        variablesTopic.setDescription("Understanding Java variables, primitive data types, and type conversion");
        variablesTopic.setContent("""
            <h2>Java Variables and Data Types</h2>
            
            <h3>What are Variables?</h3>
            <p>Variables in Java are containers that store data values. Each variable has a specific type that determines what kind of data it can hold.</p>
            
            <h3>Primitive Data Types</h3>
            <ul>
                <li><strong>byte</strong>: 8-bit signed integer (-128 to 127)</li>
                <li><strong>short</strong>: 16-bit signed integer (-32,768 to 32,767)</li>
                <li><strong>int</strong>: 32-bit signed integer (-2^31 to 2^31-1)</li>
                <li><strong>long</strong>: 64-bit signed integer (-2^63 to 2^63-1)</li>
                <li><strong>float</strong>: 32-bit floating point</li>
                <li><strong>double</strong>: 64-bit floating point</li>
                <li><strong>boolean</strong>: true or false</li>
                <li><strong>char</strong>: 16-bit Unicode character</li>
            </ul>
            
            <h3>Variable Declaration and Initialization</h3>
            <p>Variables must be declared before use and can be initialized at declaration or later.</p>
            """);
        
        variablesTopic.setCodeExamples("""
            [
                {
                    "language": "java",
                    "code": "// Variable declaration and initialization\\npublic class VariablesExample {\\n    public static void main(String[] args) {\\n        // Primitive data types\\n        int age = 25;\\n        double salary = 75000.50;\\n        boolean isEmployed = true;\\n        char grade = 'A';\\n        \\n        // String (reference type)\\n        String name = \\"John Doe\\";\\n        \\n        // Type conversion\\n        int intValue = 100;\\n        long longValue = intValue; // Implicit conversion\\n        int backToInt = (int) longValue; // Explicit conversion\\n        \\n        System.out.println(\\"Name: \\" + name);\\n        System.out.println(\\"Age: \\" + age);\\n        System.out.println(\\"Salary: $\\" + salary);\\n    }\\n}",
                    "explanation": "This example demonstrates variable declaration, initialization, and type conversion in Java."
                }
            ]
            """);
        
        variablesTopic.setKeyConcepts("""
            ["Variables", "Primitive Data Types", "Type Conversion", "Memory Management", "Scope"]
            """);
        
        variablesTopic.setTopicType(Topic.TopicType.THEORY);
        variablesTopic.setEstimatedMinutes(45);
        variablesTopic.setSortOrder(1);
        variablesTopic.setDifficultyLevel(LearningModule.DifficultyLevel.BEGINNER);
        variablesTopic.setModule(module);
        
        topicRepository.save(variablesTopic);
        
        // Create interview questions for this topic
        createVariablesInterviewQuestions(variablesTopic);
        
        // Topic 2: Object-Oriented Programming
        Topic oopTopic = new Topic();
        oopTopic.setTitle("Object-Oriented Programming Concepts");
        oopTopic.setDescription("Classes, objects, inheritance, polymorphism, encapsulation, and abstraction");
        oopTopic.setContent("""
            <h2>Object-Oriented Programming in Java</h2>
            
            <h3>The Four Pillars of OOP</h3>
            
            <h4>1. Encapsulation</h4>
            <p>Bundling data and methods that operate on that data within a single unit (class). Access modifiers control visibility.</p>
            
            <h4>2. Inheritance</h4>
            <p>Mechanism where a new class inherits properties and methods from an existing class. Promotes code reusability.</p>
            
            <h4>3. Polymorphism</h4>
            <p>Ability of objects to take multiple forms. Achieved through method overriding and method overloading.</p>
            
            <h4>4. Abstraction</h4>
            <p>Hiding complex implementation details while showing only essential features. Achieved through abstract classes and interfaces.</p>
            """);
        
        oopTopic.setCodeExamples("""
            [
                {
                    "language": "java",
                    "code": "// Encapsulation example\\npublic class BankAccount {\\n    private double balance; // Private field\\n    private String accountNumber;\\n    \\n    public BankAccount(String accountNumber, double initialBalance) {\\n        this.accountNumber = accountNumber;\\n        this.balance = initialBalance;\\n    }\\n    \\n    // Getter method\\n    public double getBalance() {\\n        return balance;\\n    }\\n    \\n    // Setter method with validation\\n    public void deposit(double amount) {\\n        if (amount > 0) {\\n            balance += amount;\\n        }\\n    }\\n    \\n    public boolean withdraw(double amount) {\\n        if (amount > 0 && amount <= balance) {\\n            balance -= amount;\\n            return true;\\n        }\\n        return false;\\n    }\\n}",
                    "explanation": "Demonstrates encapsulation with private fields and public methods for controlled access."
                },
                {
                    "language": "java",
                    "code": "// Inheritance example\\npublic class Animal {\\n    protected String name;\\n    protected int age;\\n    \\n    public void eat() {\\n        System.out.println(name + \\" is eating\\");\\n    }\\n    \\n    public void sleep() {\\n        System.out.println(name + \\" is sleeping\\");\\n    }\\n}\\n\\npublic class Dog extends Animal {\\n    private String breed;\\n    \\n    public Dog(String name, int age, String breed) {\\n        this.name = name;\\n        this.age = age;\\n        this.breed = breed;\\n    }\\n    \\n    public void bark() {\\n        System.out.println(name + \\" is barking\\");\\n    }\\n    \\n    @Override\\n    public void eat() {\\n        System.out.println(name + \\" the dog is eating dog food\\");\\n    }\\n}",
                    "explanation": "Shows inheritance with method overriding for polymorphic behavior."
                }
            ]
            """);
        
        oopTopic.setKeyConcepts("""
            ["Classes", "Objects", "Encapsulation", "Inheritance", "Polymorphism", "Abstraction", "Access Modifiers"]
            """);
        
        oopTopic.setTopicType(Topic.TopicType.THEORY);
        oopTopic.setEstimatedMinutes(90);
        oopTopic.setSortOrder(2);
        oopTopic.setDifficultyLevel(LearningModule.DifficultyLevel.INTERMEDIATE);
        oopTopic.setModule(module);
        
        topicRepository.save(oopTopic);
        createOOPInterviewQuestions(oopTopic);
    }
    
    private void createVariablesInterviewQuestions(Topic topic) {
        // Question 1: Basic variables
        InterviewQuestion q1 = new InterviewQuestion();
        q1.setTitle("Difference between primitive and reference data types in Java");
        q1.setDescription("Explain the key differences between primitive data types and reference data types in Java. Provide examples and discuss memory allocation.");
        q1.setSolution("""
            <h3>Primitive vs Reference Data Types</h3>
            
            <h4>Primitive Data Types:</h4>
            <ul>
                <li>Store actual values directly in memory</li>
                <li>Allocated on the stack</li>
                <li>8 types: byte, short, int, long, float, double, boolean, char</li>
                <li>Default values are provided (0, false, etc.)</li>
                <li>Passed by value</li>
            </ul>
            
            <h4>Reference Data Types:</h4>
            <ul>
                <li>Store references (addresses) to objects in memory</li>
                <li>Objects allocated on the heap, references on the stack</li>
                <li>Include classes, interfaces, arrays</li>
                <li>Default value is null</li>
                <li>Passed by reference</li>
            </ul>
            """);
        
        q1.setCodeExamples("""
            [
                {
                    "language": "java",
                    "code": "public class DataTypesExample {\\n    public static void main(String[] args) {\\n        // Primitive types\\n        int a = 10;\\n        int b = a; // Copy of value\\n        b = 20;\\n        System.out.println(\\"a = \\" + a); // Still 10\\n        \\n        // Reference types\\n        StringBuilder sb1 = new StringBuilder(\\"Hello\\");\\n        StringBuilder sb2 = sb1; // Copy of reference\\n        sb2.append(\\" World\\");\\n        System.out.println(sb1.toString()); // \\"Hello World\\"\\n    }\\n}",
                    "explanation": "Demonstrates the difference in behavior between primitive and reference types."
                }
            ]
            """);
        
        q1.setHints("""
            ["Think about memory allocation", "Consider what happens during assignment", "Remember stack vs heap"]
            """);
        
        q1.setFollowUpQuestions("""
            ["What happens when you pass primitives vs objects to methods?", "How does garbage collection work with reference types?", "What is autoboxing and unboxing?"]
            """);
        
        q1.setTags("""
            ["java-fundamentals", "data-types", "memory-management", "primitives", "references"]
            """);
        
        q1.setDifficulty(InterviewQuestion.Difficulty.EASY);
        q1.setCategory(InterviewQuestion.QuestionCategory.JAVA_CORE);
        q1.setCompany(InterviewQuestion.Company.AMAZON);
        q1.setFrequencyScore(9);
        q1.setEstimatedTimeMinutes(15);
        q1.setTimeComplexity("N/A");
        q1.setSpaceComplexity("N/A");
        q1.setTopic(topic);
        
        questionRepository.save(q1);
    }
    
    private void createOOPInterviewQuestions(Topic topic) {
        // Question 1: Inheritance vs Composition
        InterviewQuestion q1 = new InterviewQuestion();
        q1.setTitle("When to use Inheritance vs Composition in Java?");
        q1.setDescription("Explain the difference between inheritance and composition. When would you choose one over the other? Provide examples of both approaches.");
        q1.setSolution("""
            <h3>Inheritance vs Composition</h3>
            
            <h4>Inheritance (IS-A relationship):</h4>
            <ul>
                <li>Creates a parent-child relationship</li>
                <li>Child class inherits all public/protected members</li>
                <li>Tight coupling between classes</li>
                <li>Use when there's a clear IS-A relationship</li>
            </ul>
            
            <h4>Composition (HAS-A relationship):</h4>
            <ul>
                <li>One class contains instances of other classes</li>
                <li>Loose coupling, more flexible</li>
                <li>Easier to test and maintain</li>
                <li>Preferred approach in most cases</li>
            </ul>
            
            <h4>When to use each:</h4>
            <p><strong>Inheritance:</strong> When you have a clear IS-A relationship and want to leverage polymorphism.</p>
            <p><strong>Composition:</strong> When you want to reuse functionality without tight coupling.</p>
            """);
        
        q1.setCodeExamples("""
            [
                {
                    "language": "java",
                    "code": "// Inheritance example\\nclass Vehicle {\\n    protected String brand;\\n    protected int year;\\n    \\n    public void start() {\\n        System.out.println(\\"Vehicle starting\\");\\n    }\\n}\\n\\nclass Car extends Vehicle {\\n    private int doors;\\n    \\n    @Override\\n    public void start() {\\n        System.out.println(\\"Car engine starting\\");\\n    }\\n}",
                    "explanation": "Inheritance: Car IS-A Vehicle"
                },
                {
                    "language": "java",
                    "code": "// Composition example\\nclass Engine {\\n    public void start() {\\n        System.out.println(\\"Engine starting\\");\\n    }\\n}\\n\\nclass Car {\\n    private Engine engine; // HAS-A relationship\\n    private String brand;\\n    \\n    public Car() {\\n        this.engine = new Engine();\\n    }\\n    \\n    public void start() {\\n        engine.start(); // Delegating to composed object\\n    }\\n}",
                    "explanation": "Composition: Car HAS-A Engine"
                }
            ]
            """);
        
        q1.setDifficulty(InterviewQuestion.Difficulty.MEDIUM);
        q1.setCategory(InterviewQuestion.QuestionCategory.JAVA_CORE);
        q1.setCompany(InterviewQuestion.Company.GOOGLE);
        q1.setFrequencyScore(8);
        q1.setEstimatedTimeMinutes(20);
        q1.setTopic(topic);
        
        questionRepository.save(q1);
    }
    
    private void createSpringFrameworkModule() {
        LearningModule springModule = new LearningModule();
        springModule.setName("Spring Framework");
        springModule.setDescription("Master Spring Boot, Spring Security, Spring Data, and microservices architecture");
        springModule.setDetailedContent("Comprehensive coverage of the Spring ecosystem including dependency injection, AOP, Spring Boot auto-configuration, security, data access, and cloud-native development.");
        springModule.setCategory(LearningModule.Category.SPRING_FRAMEWORK);
        springModule.setDifficultyLevel(LearningModule.DifficultyLevel.INTERMEDIATE);
        springModule.setEstimatedHours(80);
        springModule.setSortOrder(2);
        
        moduleRepository.save(springModule);
        log.info("Created Spring Framework module");
    }
    
    private void createReactDevelopmentModule() {
        LearningModule reactModule = new LearningModule();
        reactModule.setName("React Development");
        reactModule.setDescription("Modern React development with hooks, context, performance optimization, and Next.js");
        reactModule.setDetailedContent("Complete React development course covering functional components, hooks, state management, performance optimization, testing, and modern development practices.");
        reactModule.setCategory(LearningModule.Category.REACT_DEVELOPMENT);
        reactModule.setDifficultyLevel(LearningModule.DifficultyLevel.INTERMEDIATE);
        reactModule.setEstimatedHours(60);
        reactModule.setSortOrder(3);
        
        moduleRepository.save(reactModule);
        log.info("Created React Development module");
    }
    
    private void createDataStructuresModule() {
        LearningModule dsModule = new LearningModule();
        dsModule.setName("Data Structures");
        dsModule.setDescription("Arrays, LinkedLists, Trees, Graphs, and advanced data structures with complexity analysis");
        dsModule.setDetailedContent("Comprehensive study of fundamental and advanced data structures with implementation details, complexity analysis, and real-world applications.");
        dsModule.setCategory(LearningModule.Category.DATA_STRUCTURES);
        dsModule.setDifficultyLevel(LearningModule.DifficultyLevel.INTERMEDIATE);
        dsModule.setEstimatedHours(40);
        dsModule.setSortOrder(4);
        
        moduleRepository.save(dsModule);
        log.info("Created Data Structures module");
    }
    
    private void createAlgorithmsModule() {
        LearningModule algoModule = new LearningModule();
        algoModule.setName("Algorithms");
        algoModule.setDescription("Sorting, searching, dynamic programming, and algorithmic problem-solving techniques");
        algoModule.setDetailedContent("Master algorithmic thinking with comprehensive coverage of sorting, searching, graph algorithms, dynamic programming, and optimization techniques.");
        algoModule.setCategory(LearningModule.Category.ALGORITHMS);
        algoModule.setDifficultyLevel(LearningModule.DifficultyLevel.ADVANCED);
        algoModule.setEstimatedHours(50);
        algoModule.setSortOrder(5);
        
        moduleRepository.save(algoModule);
        log.info("Created Algorithms module");
    }
    
    private void createSystemDesignModule() {
        LearningModule systemModule = new LearningModule();
        systemModule.setName("System Design");
        systemModule.setDescription("Scalable systems, distributed architecture, and real-world case studies");
        systemModule.setDetailedContent("Learn to design large-scale distributed systems with focus on scalability, reliability, availability, and performance optimization.");
        systemModule.setCategory(LearningModule.Category.SYSTEM_DESIGN);
        systemModule.setDifficultyLevel(LearningModule.DifficultyLevel.EXPERT);
        systemModule.setEstimatedHours(70);
        systemModule.setSortOrder(6);
        
        moduleRepository.save(systemModule);
        log.info("Created System Design module");
    }
    
    private void createInterviewPrepModule() {
        LearningModule interviewModule = new LearningModule();
        interviewModule.setName("Interview Preparation");
        interviewModule.setDescription("Technical questions, behavioral interviews, and FAANG preparation strategies");
        interviewModule.setDetailedContent("Comprehensive interview preparation covering technical questions, system design interviews, behavioral questions, and company-specific preparation strategies.");
        interviewModule.setCategory(LearningModule.Category.INTERVIEW_PREP);
        interviewModule.setDifficultyLevel(LearningModule.DifficultyLevel.ADVANCED);
        interviewModule.setEstimatedHours(60);
        interviewModule.setSortOrder(7);
        
        moduleRepository.save(interviewModule);
        log.info("Created Interview Preparation module");
    }
}