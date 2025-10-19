package com.learningportal.config;

import com.learningportal.model.*;
import com.learningportal.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * Data Initializer - Populates PostgreSQL database with comprehensive learning content
 * 
 * This class demonstrates:
 * - CommandLineRunner for startup data initialization
 * - Transactional data operations with PostgreSQL
 * - Repository pattern usage
 * - Real-world data modeling
 * - Comprehensive content creation for FAANG Senior SDE preparation
 * 
 * PostgreSQL Optimizations:
 * - Batch inserts for better performance
 * - Proper transaction boundaries
 * - UUID primary keys for distributed systems
 * - Optimized for production deployment
 */
@Component
public class DataInitializer implements CommandLineRunner {
    
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    
    private final UserRepository userRepository;
    private final LearningModuleRepository moduleRepository;
    private final TopicRepository topicRepository;
    private final InterviewQuestionRepository questionRepository;
    private final PasswordEncoder passwordEncoder;
    
    // Constructor injection (replacing @RequiredArgsConstructor)
    public DataInitializer(UserRepository userRepository,
                          LearningModuleRepository moduleRepository,
                          TopicRepository topicRepository,
                          InterviewQuestionRepository questionRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.moduleRepository = moduleRepository;
        this.topicRepository = topicRepository;
        this.questionRepository = questionRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            log.info("Initializing PostgreSQL database with comprehensive FAANG Senior SDE learning content...");
            
            // Create admin user
            createAdminUser();
            
            // Create learning modules with comprehensive content
            createJavaFundamentalsModule();
            createSpringFrameworkModule();
            createReactDevelopmentModule();
            createDataStructuresModule();
            createAlgorithmsModule();
            createSystemDesignModule();
            createDatabaseModule();
            createNodeJsFundamentalsModule();
            createInterviewPrepModule();
            
            log.info("PostgreSQL database initialization completed successfully!");
            log.info("Total modules created: {}", moduleRepository.count());
            log.info("Total topics created: {}", topicRepository.count());
            log.info("Total interview questions created: {}", questionRepository.count());
        } else {
            log.info("PostgreSQL database already contains data, skipping initialization.");
        }
    }
    
    private void createAdminUser() {
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@faangmastery.com");
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
        List<Topic> topics = new ArrayList<>();
        
        // Topic 1: Java Basics - Variables, Data Types, and Operators
        Topic javaBasicsTopic = new Topic();
        javaBasicsTopic.setTitle("Java Basics: Variables, Data Types, and Operators");
        javaBasicsTopic.setDescription("Master Java fundamentals: variables, primitive types, operators, and memory management");
        javaBasicsTopic.setContent(createJavaBasicsContent());
        javaBasicsTopic.setCodeExamples(createJavaBasicsCodeExamples());
        javaBasicsTopic.setModule(module);
        javaBasicsTopic.setSortOrder(1);
        javaBasicsTopic.setEstimatedMinutes(180);
        topics.add(javaBasicsTopic);
        
        // Topic 2: Object-Oriented Programming
        Topic oopTopic = new Topic();
        oopTopic.setTitle("Object-Oriented Programming: The Four Pillars");
        oopTopic.setDescription("Master OOP fundamentals: Encapsulation, Inheritance, Polymorphism, and Abstraction with real-world examples");
        oopTopic.setContent(createOOPContent());
        oopTopic.setCodeExamples(createOOPCodeExamples());
        oopTopic.setModule(module);
        oopTopic.setSortOrder(2);
        oopTopic.setEstimatedMinutes(240);
        topics.add(oopTopic);
        
        // Topic 3: Collections Framework
        Topic collectionsTopic = new Topic();
        collectionsTopic.setTitle("Collections Framework: Complete Guide");
        collectionsTopic.setDescription("Master Java Collections with performance analysis, real-world usage patterns, and comprehensive interview questions");
        collectionsTopic.setContent(createCollectionsContent());
        collectionsTopic.setCodeExamples(createCollectionsCodeExamples());
        collectionsTopic.setModule(module);
        collectionsTopic.setSortOrder(3);
        collectionsTopic.setEstimatedMinutes(300);
        topics.add(collectionsTopic);
        
        // Topic 4: Exception Handling
        Topic exceptionTopic = new Topic();
        exceptionTopic.setTitle("Exception Handling: Building Robust Applications");
        exceptionTopic.setDescription("Master Java exception handling with best practices, custom exceptions, and error recovery strategies");
        exceptionTopic.setContent(createExceptionHandlingContent());
        exceptionTopic.setCodeExamples(createExceptionHandlingCodeExamples());
        exceptionTopic.setModule(module);
        exceptionTopic.setSortOrder(4);
        exceptionTopic.setEstimatedMinutes(200);
        topics.add(exceptionTopic);
        
        // Batch save all topics for better PostgreSQL performance
        topicRepository.saveAll(topics);
        
        // Create interview questions for each topic
        createJavaInterviewQuestions(topics);
        
        log.info("Created {} Java topics with interview questions", topics.size());
    }
    
    private String createJavaBasicsContent() {
        return """
            <div class="topic-content">
                <h2>Java Basics: Variables, Data Types, and Operators</h2>
                
                <h3>🎯 Learning Objectives</h3>
                <ul>
                    <li>Understand Java's type system and memory model</li>
                    <li>Master primitive vs reference types</li>
                    <li>Learn variable scoping and lifetime</li>
                    <li>Practice with operators and expressions</li>
                    <li>Solve real FAANG interview questions</li>
                </ul>
                
                <h3>📊 Java's Type System</h3>
                <p>Java has a <strong>static type system</strong> where every variable must have a declared type. This provides compile-time safety and performance optimization.</p>
                
                <h4>🔢 Primitive Data Types (8 types)</h4>
                <table class="data-types-table">
                    <tr><th>Type</th><th>Size</th><th>Range</th><th>Default</th><th>Use Case</th></tr>
                    <tr><td><code>byte</code></td><td>8 bits</td><td>-128 to 127</td><td>0</td><td>Memory-sensitive arrays</td></tr>
                    <tr><td><code>short</code></td><td>16 bits</td><td>-32,768 to 32,767</td><td>0</td><td>Legacy systems</td></tr>
                    <tr><td><code>int</code></td><td>32 bits</td><td>-2³¹ to 2³¹-1</td><td>0</td><td>Most common integer type</td></tr>
                    <tr><td><code>long</code></td><td>64 bits</td><td>-2⁶³ to 2⁶³-1</td><td>0L</td><td>Large numbers, timestamps</td></tr>
                    <tr><td><code>float</code></td><td>32 bits</td><td>IEEE 754</td><td>0.0f</td><td>Graphics, scientific computing</td></tr>
                    <tr><td><code>double</code></td><td>64 bits</td><td>IEEE 754</td><td>0.0d</td><td>Default floating-point type</td></tr>
                    <tr><td><code>boolean</code></td><td>1 bit*</td><td>true/false</td><td>false</td><td>Flags, conditions</td></tr>
                    <tr><td><code>char</code></td><td>16 bits</td><td>0 to 65,535</td><td>'\\u0000'</td><td>Unicode characters</td></tr>
                </table>
                
                <h3>🔧 Variable Declaration and Initialization</h3>
                <div class="code-block">
                    <pre><code>dataType variableName;                    // Declaration
dataType variableName = value;            // Declaration + Initialization
final dataType CONSTANT_NAME = value;    // Constant (immutable)</code></pre>
                </div>
                
                <h3>⚡ Operators and Expressions</h3>
                <ul>
                    <li><strong>Arithmetic:</strong> +, -, *, /, %, ++, --</li>
                    <li><strong>Comparison:</strong> ==, !=, <, >, <=, >=</li>
                    <li><strong>Logical:</strong> &&, ||, !</li>
                    <li><strong>Bitwise:</strong> &, |, ^, ~, <<, >>, >>></li>
                </ul>
                
                <h3>💡 Best Practices</h3>
                <ul>
                    <li>Use <code>int</code> for most integer operations</li>
                    <li>Use <code>double</code> for floating-point calculations</li>
                    <li>Always initialize local variables</li>
                    <li>Use meaningful variable names</li>
                    <li>Follow camelCase naming convention</li>
                    <li>Use <code>final</code> for constants and immutable references</li>
                </ul>
            </div>
            """;
    }
    
    private String createJavaBasicsCodeExamples() {
        return """
            [
                {
                    "language": "java",
                    "title": "Primitive Data Types Demo",
                    "code": "public class JavaBasicsDemo {\\n    public static void main(String[] args) {\\n        // Integer types\\n        byte smallNumber = 127;\\n        short mediumNumber = 32767;\\n        int regularNumber = 2147483647;\\n        long bigNumber = 9223372036854775807L;\\n        \\n        // Floating-point types\\n        float precision = 3.14159f;\\n        double doublePrecision = 3.141592653589793;\\n        \\n        // Other types\\n        boolean isActive = true;\\n        char grade = 'A';\\n        \\n        System.out.printf(\\"Types: %d, %d, %d, %d%n\\", \\n                         smallNumber, mediumNumber, regularNumber, bigNumber);\\n    }\\n}",
                    "explanation": "Demonstrates all primitive data types with proper initialization and usage patterns."
                },
                {
                    "language": "java", 
                    "title": "Operators and Type Conversion",
                    "code": "public class OperatorsDemo {\\n    public static void main(String[] args) {\\n        int a = 10, b = 3;\\n        \\n        // Arithmetic operators\\n        System.out.println(\\"Division: \\" + a + \\" / \\" + b + \\" = \\" + (a / b));\\n        System.out.println(\\"Modulo: \\" + a + \\" % \\" + b + \\" = \\" + (a % b));\\n        \\n        // Type conversion\\n        double largeDouble = 123.456;\\n        int castInt = (int) largeDouble;\\n        System.out.println(\\"Cast double to int: \\" + castInt);\\n        \\n        // String parsing\\n        String numberStr = \\"42\\";\\n        int parsedInt = Integer.parseInt(numberStr);\\n        System.out.println(\\"Parsed: \\" + parsedInt);\\n    }\\n}",
                    "explanation": "Shows operator usage and type conversion techniques including explicit casting and string parsing."
                }
            ]
            """;
    }    
   
 private String createOOPContent() {
        return """
            <div class="topic-content">
                <h2>Object-Oriented Programming: The Four Pillars</h2>
                
                <h3>🎯 Learning Objectives</h3>
                <ul>
                    <li>Master the four pillars of OOP: Encapsulation, Inheritance, Polymorphism, Abstraction</li>
                    <li>Understand class design principles and relationships</li>
                    <li>Practice with real-world examples and design patterns</li>
                    <li>Solve FAANG OOP interview questions</li>
                </ul>
                
                <h3>🏛️ The Four Pillars of OOP</h3>
                
                <h4>1. 🔒 Encapsulation</h4>
                <p>Bundling data and methods that operate on that data within a single unit (class), and restricting access to internal implementation details.</p>
                <ul>
                    <li><strong>Data Hiding:</strong> Private fields with public getter/setter methods</li>
                    <li><strong>Access Modifiers:</strong> private, protected, public, package-private</li>
                    <li><strong>Benefits:</strong> Security, maintainability, flexibility</li>
                </ul>
                
                <h4>2. 🧬 Inheritance</h4>
                <p>Mechanism where a new class inherits properties and behaviors from an existing class.</p>
                <ul>
                    <li><strong>IS-A Relationship:</strong> Child class is a type of parent class</li>
                    <li><strong>Code Reusability:</strong> Avoid duplication, extend functionality</li>
                    <li><strong>Method Overriding:</strong> Child class provides specific implementation</li>
                </ul>
                
                <h4>3. 🎭 Polymorphism</h4>
                <p>Ability of objects of different types to be treated as instances of the same type through a common interface.</p>
                <ul>
                    <li><strong>Runtime Polymorphism:</strong> Method overriding, dynamic method dispatch</li>
                    <li><strong>Compile-time Polymorphism:</strong> Method overloading</li>
                    <li><strong>Interface Implementation:</strong> Multiple classes implementing same interface</li>
                </ul>
                
                <h4>4. 🎨 Abstraction</h4>
                <p>Hiding complex implementation details while showing only essential features of an object.</p>
                <ul>
                    <li><strong>Abstract Classes:</strong> Cannot be instantiated, may have abstract methods</li>
                    <li><strong>Interfaces:</strong> Contract defining what a class must do</li>
                    <li><strong>Benefits:</strong> Reduces complexity, increases reusability</li>
                </ul>
            </div>
            """;
    }
    
    private String createOOPCodeExamples() {
        return """
            [
                {
                    "language": "java",
                    "title": "Encapsulation Example",
                    "code": "public class BankAccount {\\n    private double balance;\\n    private String accountNumber;\\n    \\n    public BankAccount(String accountNumber, double initialBalance) {\\n        this.accountNumber = accountNumber;\\n        this.balance = Math.max(0, initialBalance);\\n    }\\n    \\n    public double getBalance() {\\n        return balance;\\n    }\\n    \\n    public boolean deposit(double amount) {\\n        if (amount > 0) {\\n            balance += amount;\\n            return true;\\n        }\\n        return false;\\n    }\\n    \\n    public boolean withdraw(double amount) {\\n        if (amount > 0 && amount <= balance) {\\n            balance -= amount;\\n            return true;\\n        }\\n        return false;\\n    }\\n}",
                    "explanation": "Demonstrates encapsulation with private fields and controlled access through public methods."
                },
                {
                    "language": "java",
                    "title": "Inheritance and Polymorphism",
                    "code": "abstract class Animal {\\n    protected String name;\\n    \\n    public Animal(String name) {\\n        this.name = name;\\n    }\\n    \\n    public abstract void makeSound();\\n    \\n    public void sleep() {\\n        System.out.println(name + \\" is sleeping\\");\\n    }\\n}\\n\\nclass Dog extends Animal {\\n    public Dog(String name) {\\n        super(name);\\n    }\\n    \\n    @Override\\n    public void makeSound() {\\n        System.out.println(name + \\" barks: Woof!\\");\\n    }\\n}\\n\\nclass Cat extends Animal {\\n    public Cat(String name) {\\n        super(name);\\n    }\\n    \\n    @Override\\n    public void makeSound() {\\n        System.out.println(name + \\" meows: Meow!\\");\\n    }\\n}",
                    "explanation": "Shows inheritance hierarchy with abstract base class and polymorphic method overriding."
                }
            ]
            """;
    }
    
    private String createCollectionsContent() {
        return """
            <div class="topic-content">
                <h2>Collections Framework: Complete Guide</h2>
                
                <h3>🎯 Learning Objectives</h3>
                <ul>
                    <li>Master all Java Collection interfaces and implementations</li>
                    <li>Understand performance characteristics and use cases</li>
                    <li>Learn iteration patterns and best practices</li>
                    <li>Solve complex collection-based interview problems</li>
                </ul>
                
                <h3>📊 Collections Hierarchy</h3>
                <div class="hierarchy-diagram">
                    <pre>
Collection (Interface)
├── List (Interface)
│   ├── ArrayList (Resizable array)
│   ├── LinkedList (Doubly-linked list)
│   └── Vector (Synchronized ArrayList)
├── Set (Interface)
│   ├── HashSet (Hash table)
│   ├── LinkedHashSet (Hash table + linked list)
│   └── TreeSet (Red-black tree)
└── Queue (Interface)
    ├── PriorityQueue (Binary heap)
    ├── ArrayDeque (Resizable array deque)
    └── LinkedList (Also implements Queue)

Map (Interface) - Separate hierarchy
├── HashMap (Hash table)
├── LinkedHashMap (Hash table + linked list)
├── TreeMap (Red-black tree)
└── Hashtable (Synchronized HashMap)
                    </pre>
                </div>
                
                <h3>⚡ Performance Characteristics</h3>
                <table class="performance-table">
                    <tr><th>Collection</th><th>Add</th><th>Remove</th><th>Get</th><th>Contains</th><th>Memory</th></tr>
                    <tr><td>ArrayList</td><td>O(1)*</td><td>O(n)</td><td>O(1)</td><td>O(n)</td><td>Low</td></tr>
                    <tr><td>LinkedList</td><td>O(1)</td><td>O(1)**</td><td>O(n)</td><td>O(n)</td><td>High</td></tr>
                    <tr><td>HashSet</td><td>O(1)</td><td>O(1)</td><td>N/A</td><td>O(1)</td><td>Medium</td></tr>
                    <tr><td>TreeSet</td><td>O(log n)</td><td>O(log n)</td><td>N/A</td><td>O(log n)</td><td>Medium</td></tr>
                    <tr><td>HashMap</td><td>O(1)</td><td>O(1)</td><td>O(1)</td><td>O(1)</td><td>Medium</td></tr>
                    <tr><td>TreeMap</td><td>O(log n)</td><td>O(log n)</td><td>O(log n)</td><td>O(log n)</td><td>Medium</td></tr>
                </table>
                <p><em>* Amortized, ** If you have reference to node</em></p>
            </div>
            """;
    }
    
    private String createCollectionsCodeExamples() {
        return """
            [
                {
                    "language": "java",
                    "title": "ArrayList vs LinkedList Performance",
                    "code": "import java.util.*;\\n\\npublic class CollectionsPerformance {\\n    public static void main(String[] args) {\\n        List<Integer> arrayList = new ArrayList<>();\\n        List<Integer> linkedList = new LinkedList<>();\\n        \\n        // Adding elements - ArrayList is faster for bulk operations\\n        long start = System.nanoTime();\\n        for (int i = 0; i < 100000; i++) {\\n            arrayList.add(i);\\n        }\\n        long arrayListTime = System.nanoTime() - start;\\n        \\n        start = System.nanoTime();\\n        for (int i = 0; i < 100000; i++) {\\n            linkedList.add(i);\\n        }\\n        long linkedListTime = System.nanoTime() - start;\\n        \\n        System.out.println(\\"ArrayList add time: \\" + arrayListTime / 1_000_000 + \\"ms\\");\\n        System.out.println(\\"LinkedList add time: \\" + linkedListTime / 1_000_000 + \\"ms\\");\\n    }\\n}",
                    "explanation": "Compares performance characteristics of ArrayList vs LinkedList for different operations."
                }
            ]
            """;
    }
    
    private String createExceptionHandlingContent() {
        return """
            <div class="topic-content">
                <h2>Exception Handling: Building Robust Applications</h2>
                
                <h3>🎯 Learning Objectives</h3>
                <ul>
                    <li>Master Java's exception hierarchy and handling mechanisms</li>
                    <li>Learn best practices for exception handling in production code</li>
                    <li>Understand checked vs unchecked exceptions</li>
                    <li>Create custom exceptions and error recovery strategies</li>
                </ul>
                
                <h3>🏗️ Exception Hierarchy</h3>
                <div class="hierarchy-diagram">
                    <pre>
Throwable
├── Error (Unchecked - System errors)
│   ├── OutOfMemoryError
│   ├── StackOverflowError
│   └── VirtualMachineError
└── Exception
    ├── RuntimeException (Unchecked)
    │   ├── NullPointerException
    │   ├── IllegalArgumentException
    │   ├── IndexOutOfBoundsException
    │   └── ClassCastException
    └── Checked Exceptions
        ├── IOException
        ├── SQLException
        ├── ClassNotFoundException
        └── ParseException
                    </pre>
                </div>
                
                <h3>🛡️ Exception Handling Best Practices</h3>
                <ul>
                    <li><strong>Catch Specific Exceptions:</strong> Avoid catching generic Exception</li>
                    <li><strong>Fail Fast:</strong> Validate inputs early and throw exceptions immediately</li>
                    <li><strong>Clean Up Resources:</strong> Use try-with-resources for automatic cleanup</li>
                    <li><strong>Log Appropriately:</strong> Log exceptions with context information</li>
                    <li><strong>Don't Ignore Exceptions:</strong> Always handle or propagate exceptions</li>
                </ul>
            </div>
            """;
    }
    
    private String createExceptionHandlingCodeExamples() {
        return """
            [
                {
                    "language": "java",
                    "title": "Try-With-Resources and Custom Exceptions",
                    "code": "public class FileProcessor {\\n    \\n    public static class FileProcessingException extends Exception {\\n        public FileProcessingException(String message, Throwable cause) {\\n            super(message, cause);\\n        }\\n    }\\n    \\n    public void processFile(String filename) throws FileProcessingException {\\n        // Try-with-resources automatically closes resources\\n        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename))) {\\n            String line;\\n            while ((line = reader.readLine()) != null) {\\n                processLine(line);\\n            }\\n        } catch (IOException e) {\\n            throw new FileProcessingException(\\"Failed to process file: \\" + filename, e);\\n        }\\n    }\\n    \\n    private void processLine(String line) {\\n        if (line == null || line.trim().isEmpty()) {\\n            throw new IllegalArgumentException(\\"Line cannot be null or empty\\");\\n        }\\n        // Process the line\\n    }\\n}",
                    "explanation": "Demonstrates try-with-resources, custom exceptions, and proper exception chaining."
                }
            ]
            """;
    }
    
    private void createJavaInterviewQuestions(List<Topic> topics) {
        List<InterviewQuestion> questions = new ArrayList<>();
        
        // Java Basics Questions
        questions.add(createInterviewQuestion(
            topics.get(0),
            "Difference between int and Integer in Java",
            "Explain the difference between primitive int and wrapper class Integer, including autoboxing/unboxing.",
            InterviewQuestion.DifficultyLevel.EASY,
            Arrays.asList(InterviewQuestion.Company.AMAZON, InterviewQuestion.Company.GOOGLE)
        ));
        
        questions.add(createInterviewQuestion(
            topics.get(0),
            "What happens when you divide by zero in Java?",
            "Explain the behavior of division by zero for integers vs floating-point numbers.",
            InterviewQuestion.DifficultyLevel.EASY,
            Arrays.asList(InterviewQuestion.Company.MICROSOFT, InterviewQuestion.Company.META)
        ));
        
        // OOP Questions
        questions.add(createInterviewQuestion(
            topics.get(1),
            "Explain polymorphism with a real-world example",
            "Describe runtime polymorphism and provide a practical example with code.",
            InterviewQuestion.DifficultyLevel.MEDIUM,
            Arrays.asList(InterviewQuestion.Company.AMAZON, InterviewQuestion.Company.APPLE)
        ));
        
        // Collections Questions
        questions.add(createInterviewQuestion(
            topics.get(2),
            "When to use ArrayList vs LinkedList?",
            "Compare ArrayList and LinkedList performance characteristics and use cases.",
            InterviewQuestion.DifficultyLevel.MEDIUM,
            Arrays.asList(InterviewQuestion.Company.GOOGLE, InterviewQuestion.Company.META)
        ));
        
        // Exception Handling Questions
        questions.add(createInterviewQuestion(
            topics.get(3),
            "Checked vs Unchecked Exceptions",
            "Explain the difference between checked and unchecked exceptions with examples.",
            InterviewQuestion.DifficultyLevel.MEDIUM,
            Arrays.asList(InterviewQuestion.Company.AMAZON, InterviewQuestion.Company.MICROSOFT)
        ));
        
        // Batch save all questions for better PostgreSQL performance
        questionRepository.saveAll(questions);
        
        log.info("Created {} Java interview questions", questions.size());
    }
    
    private InterviewQuestion createInterviewQuestion(Topic topic, String title, String description, 
                                                    InterviewQuestion.DifficultyLevel difficulty, 
                                                    List<InterviewQuestion.Company> companies) {
        InterviewQuestion question = new InterviewQuestion();
        question.setTitle(title);
        question.setDescription(description);
        question.setTopic(topic);
        question.setDifficultyLevel(difficulty);
        question.setCompanies(new HashSet<>(companies));
        question.setTimeComplexity("O(1)");
        question.setSpaceComplexity("O(1)");
        return question;
    }
    
    // Placeholder methods for other modules (to be implemented)
    private void createSpringFrameworkModule() {
        log.info("Spring Framework module - to be implemented");
    }
    
    private void createReactDevelopmentModule() {
        log.info("React Development module - to be implemented");
    }
    
    private void createDataStructuresModule() {
        log.info("Data Structures module - to be implemented");
    }
    
    private void createAlgorithmsModule() {
        log.info("Algorithms module - to be implemented");
    }
    
    private void createSystemDesignModule() {
        log.info("System Design module - to be implemented");
    }
    
    private void createDatabaseModule() {
        log.info("Database module - to be implemented");
    }
    
    private void createNodeJsFundamentalsModule() {
        log.info("Node.js Fundamentals module - to be implemented");
    }
    
    private void createInterviewPrepModule() {
        log.info("Interview Preparation module - to be implemented");
    }
}  
  
    private String createOOPContent() {
        return """
            <div class="topic-content">
                <h2>Object-Oriented Programming: The Four Pillars</h2>
                
                <h3>🎯 Learning Objectives</h3>
                <ul>
                    <li>Master the four pillars of OOP: Encapsulation, Inheritance, Polymorphism, Abstraction</li>
                    <li>Understand class design principles and relationships</li>
                    <li>Learn method overriding vs overloading</li>
                    <li>Practice with interfaces and abstract classes</li>
                    <li>Solve FAANG OOP interview questions</li>
                </ul>
                
                <h3>🏛️ The Four Pillars of OOP</h3>
                
                <h4>1. 🔒 Encapsulation</h4>
                <p>Bundling data and methods together while hiding internal implementation details.</p>
                <ul>
                    <li><strong>Data Hiding:</strong> Private fields with public getter/setter methods</li>
                    <li><strong>Access Modifiers:</strong> private, protected, public, package-private</li>
                    <li><strong>Benefits:</strong> Security, maintainability, flexibility</li>
                </ul>
                
                <h4>2. 🧬 Inheritance</h4>
                <p>Creating new classes based on existing classes to promote code reuse.</p>
                <ul>
                    <li><strong>IS-A Relationship:</strong> Child class is a type of parent class</li>
                    <li><strong>Method Inheritance:</strong> Child inherits parent's methods and fields</li>
                    <li><strong>Single Inheritance:</strong> Java supports single class inheritance</li>
                </ul>
                
                <h4>3. 🎭 Polymorphism</h4>
                <p>One interface, multiple implementations - objects behaving differently based on their type.</p>
                <ul>
                    <li><strong>Method Overriding:</strong> Runtime polymorphism</li>
                    <li><strong>Method Overloading:</strong> Compile-time polymorphism</li>
                    <li><strong>Interface Implementation:</strong> Multiple inheritance of type</li>
                </ul>
                
                <h4>4. 🎨 Abstraction</h4>
                <p>Hiding complex implementation details while showing only essential features.</p>
                <ul>
                    <li><strong>Abstract Classes:</strong> Partial implementation with abstract methods</li>
                    <li><strong>Interfaces:</strong> Pure abstraction with contracts</li>
                    <li><strong>Benefits:</strong> Reduced complexity, increased maintainability</li>
                </ul>
                
                <h3>💡 OOP Best Practices</h3>
                <ul>
                    <li>Favor composition over inheritance</li>
                    <li>Program to interfaces, not implementations</li>
                    <li>Follow SOLID principles</li>
                    <li>Use meaningful class and method names</li>
                    <li>Keep classes focused and cohesive</li>
                </ul>
            </div>
            """;
    }
    
    private String createOOPCodeExamples() {
        return """
            [
                {
                    "language": "java",
                    "title": "Encapsulation Example",
                    "code": "public class BankAccount {\\n    private double balance;\\n    private String accountNumber;\\n    \\n    public BankAccount(String accountNumber, double initialBalance) {\\n        this.accountNumber = accountNumber;\\n        this.balance = Math.max(0, initialBalance);\\n    }\\n    \\n    public double getBalance() {\\n        return balance;\\n    }\\n    \\n    public boolean deposit(double amount) {\\n        if (amount > 0) {\\n            balance += amount;\\n            return true;\\n        }\\n        return false;\\n    }\\n    \\n    public boolean withdraw(double amount) {\\n        if (amount > 0 && amount <= balance) {\\n            balance -= amount;\\n            return true;\\n        }\\n        return false;\\n    }\\n}",
                    "explanation": "Demonstrates encapsulation with private fields and controlled access through public methods."
                },
                {
                    "language": "java",
                    "title": "Inheritance and Polymorphism",
                    "code": "abstract class Animal {\\n    protected String name;\\n    \\n    public Animal(String name) {\\n        this.name = name;\\n    }\\n    \\n    public abstract void makeSound();\\n    \\n    public void sleep() {\\n        System.out.println(name + \\" is sleeping\\");\\n    }\\n}\\n\\nclass Dog extends Animal {\\n    public Dog(String name) {\\n        super(name);\\n    }\\n    \\n    @Override\\n    public void makeSound() {\\n        System.out.println(name + \\" says Woof!\\");\\n    }\\n}\\n\\nclass Cat extends Animal {\\n    public Cat(String name) {\\n        super(name);\\n    }\\n    \\n    @Override\\n    public void makeSound() {\\n        System.out.println(name + \\" says Meow!\\");\\n    }\\n}",
                    "explanation": "Shows inheritance hierarchy with abstract base class and polymorphic method overriding."
                }
            ]
            """;
    }
    
    private String createCollectionsContent() {
        return """
            <div class="topic-content">
                <h2>Collections Framework: Complete Guide</h2>
                
                <h3>🎯 Learning Objectives</h3>
                <ul>
                    <li>Master all Java Collection interfaces and implementations</li>
                    <li>Understand performance characteristics and use cases</li>
                    <li>Learn iteration patterns and best practices</li>
                    <li>Practice with generics and type safety</li>
                    <li>Solve collection-based interview questions</li>
                </ul>
                
                <h3>📊 Collections Hierarchy</h3>
                <div class="hierarchy-diagram">
                    <pre>
Collection (Interface)
├── List (Interface)
│   ├── ArrayList (Resizable array)
│   ├── LinkedList (Doubly-linked list)
│   └── Vector (Synchronized ArrayList)
├── Set (Interface)
│   ├── HashSet (Hash table)
│   ├── LinkedHashSet (Hash table + linked list)
│   └── TreeSet (Red-black tree)
└── Queue (Interface)
    ├── PriorityQueue (Binary heap)
    └── Deque (Interface)
        └── ArrayDeque (Resizable array)

Map (Interface)
├── HashMap (Hash table)
├── LinkedHashMap (Hash table + linked list)
└── TreeMap (Red-black tree)
                    </pre>
                </div>
                
                <h3>📋 List Implementations</h3>
                <table class="collections-table">
                    <tr><th>Implementation</th><th>Access</th><th>Insert</th><th>Delete</th><th>Use Case</th></tr>
                    <tr><td>ArrayList</td><td>O(1)</td><td>O(1)*</td><td>O(n)</td><td>Random access, frequent reads</td></tr>
                    <tr><td>LinkedList</td><td>O(n)</td><td>O(1)</td><td>O(1)</td><td>Frequent insertions/deletions</td></tr>
                    <tr><td>Vector</td><td>O(1)</td><td>O(1)*</td><td>O(n)</td><td>Thread-safe operations</td></tr>
                </table>
                
                <h3>🔍 Set Implementations</h3>
                <ul>
                    <li><strong>HashSet:</strong> Fast O(1) operations, no ordering</li>
                    <li><strong>LinkedHashSet:</strong> Maintains insertion order</li>
                    <li><strong>TreeSet:</strong> Sorted order, O(log n) operations</li>
                </ul>
                
                <h3>🗺️ Map Implementations</h3>
                <ul>
                    <li><strong>HashMap:</strong> Fast O(1) operations, no ordering</li>
                    <li><strong>LinkedHashMap:</strong> Maintains insertion/access order</li>
                    <li><strong>TreeMap:</strong> Sorted by keys, O(log n) operations</li>
                </ul>
                
                <h3>💡 Best Practices</h3>
                <ul>
                    <li>Use ArrayList for most list operations</li>
                    <li>Use HashMap for key-value mappings</li>
                    <li>Use HashSet for unique elements</li>
                    <li>Always use generics for type safety</li>
                    <li>Consider thread safety requirements</li>
                </ul>
            </div>
            """;
    }
    
    private String createCollectionsCodeExamples() {
        return """
            [
                {
                    "language": "java",
                    "title": "List Operations Demo",
                    "code": "import java.util.*;\\n\\npublic class ListDemo {\\n    public static void main(String[] args) {\\n        // ArrayList - best for random access\\n        List<String> arrayList = new ArrayList<>();\\n        arrayList.add(\\"Apple\\");\\n        arrayList.add(\\"Banana\\");\\n        arrayList.add(\\"Cherry\\");\\n        \\n        // LinkedList - best for frequent insertions\\n        List<String> linkedList = new LinkedList<>();\\n        linkedList.addAll(arrayList);\\n        linkedList.add(1, \\"Blueberry\\");\\n        \\n        // Iteration patterns\\n        for (String fruit : arrayList) {\\n            System.out.println(fruit);\\n        }\\n        \\n        // Stream operations\\n        arrayList.stream()\\n            .filter(s -> s.startsWith(\\"A\\"))\\n            .forEach(System.out::println);\\n    }\\n}",
                    "explanation": "Demonstrates ArrayList and LinkedList usage with different iteration patterns and stream operations."
                },
                {
                    "language": "java",
                    "title": "Map and Set Operations",
                    "code": "import java.util.*;\\n\\npublic class MapSetDemo {\\n    public static void main(String[] args) {\\n        // HashMap for key-value storage\\n        Map<String, Integer> scores = new HashMap<>();\\n        scores.put(\\"Alice\\", 95);\\n        scores.put(\\"Bob\\", 87);\\n        scores.put(\\"Charlie\\", 92);\\n        \\n        // Set for unique elements\\n        Set<String> uniqueNames = new HashSet<>();\\n        uniqueNames.addAll(scores.keySet());\\n        \\n        // TreeMap for sorted keys\\n        Map<String, Integer> sortedScores = new TreeMap<>(scores);\\n        \\n        // Iteration over map\\n        for (Map.Entry<String, Integer> entry : scores.entrySet()) {\\n            System.out.println(entry.getKey() + \\": \\" + entry.getValue());\\n        }\\n    }\\n}",
                    "explanation": "Shows HashMap, TreeMap, and HashSet usage with proper iteration patterns."
                }
            ]
            """;
    }
    
    private String createExceptionHandlingContent() {
        return """
            <div class="topic-content">
                <h2>Exception Handling: Building Robust Applications</h2>
                
                <h3>🎯 Learning Objectives</h3>
                <ul>
                    <li>Understand Java's exception hierarchy and types</li>
                    <li>Master try-catch-finally blocks and resource management</li>
                    <li>Learn to create custom exceptions</li>
                    <li>Practice exception handling best practices</li>
                    <li>Solve exception-related interview questions</li>
                </ul>
                
                <h3>🏗️ Exception Hierarchy</h3>
                <div class="hierarchy-diagram">
                    <pre>
Throwable
├── Error (System errors - don't catch)
│   ├── OutOfMemoryError
│   └── StackOverflowError
└── Exception
    ├── RuntimeException (Unchecked)
    │   ├── NullPointerException
    │   ├── IllegalArgumentException
    │   └── IndexOutOfBoundsException
    └── Checked Exceptions
        ├── IOException
        ├── SQLException
        └── ClassNotFoundException
                    </pre>
                </div>
                
                <h3>⚡ Exception Types</h3>
                <ul>
                    <li><strong>Checked Exceptions:</strong> Must be handled or declared (IOException, SQLException)</li>
                    <li><strong>Unchecked Exceptions:</strong> Runtime exceptions (NullPointerException, IllegalArgumentException)</li>
                    <li><strong>Errors:</strong> System-level problems (OutOfMemoryError, StackOverflowError)</li>
                </ul>
                
                <h3>🛡️ Exception Handling Mechanisms</h3>
                <ul>
                    <li><strong>try-catch:</strong> Handle exceptions gracefully</li>
                    <li><strong>finally:</strong> Cleanup code that always executes</li>
                    <li><strong>try-with-resources:</strong> Automatic resource management</li>
                    <li><strong>throws:</strong> Declare exceptions in method signature</li>
                    <li><strong>throw:</strong> Explicitly throw exceptions</li>
                </ul>
                
                <h3>💡 Best Practices</h3>
                <ul>
                    <li>Catch specific exceptions, not generic Exception</li>
                    <li>Use try-with-resources for resource management</li>
                    <li>Don't ignore exceptions - log or handle appropriately</li>
                    <li>Create meaningful custom exceptions</li>
                    <li>Use exceptions for exceptional conditions only</li>
                </ul>
            </div>
            """;
    }
    
    private String createExceptionHandlingCodeExamples() {
        return """
            [
                {
                    "language": "java",
                    "title": "Basic Exception Handling",
                    "code": "import java.io.*;\\n\\npublic class ExceptionDemo {\\n    public static void main(String[] args) {\\n        // Basic try-catch\\n        try {\\n            int result = divide(10, 0);\\n            System.out.println(\\"Result: \\" + result);\\n        } catch (ArithmeticException e) {\\n            System.out.println(\\"Error: \\" + e.getMessage());\\n        } finally {\\n            System.out.println(\\"Cleanup code\\");\\n        }\\n        \\n        // Try-with-resources\\n        try (BufferedReader reader = new BufferedReader(new FileReader(\\"file.txt\\"))) {\\n            String line = reader.readLine();\\n            System.out.println(line);\\n        } catch (IOException e) {\\n            System.out.println(\\"File error: \\" + e.getMessage());\\n        }\\n    }\\n    \\n    public static int divide(int a, int b) throws ArithmeticException {\\n        if (b == 0) {\\n            throw new ArithmeticException(\\"Division by zero\\");\\n        }\\n        return a / b;\\n    }\\n}",
                    "explanation": "Demonstrates basic exception handling patterns including try-catch-finally and try-with-resources."
                },
                {
                    "language": "java",
                    "title": "Custom Exception Example",
                    "code": "class InsufficientFundsException extends Exception {\\n    private double amount;\\n    \\n    public InsufficientFundsException(double amount) {\\n        super(\\"Insufficient funds: \\" + amount);\\n        this.amount = amount;\\n    }\\n    \\n    public double getAmount() {\\n        return amount;\\n    }\\n}\\n\\nclass BankAccount {\\n    private double balance;\\n    \\n    public BankAccount(double balance) {\\n        this.balance = balance;\\n    }\\n    \\n    public void withdraw(double amount) throws InsufficientFundsException {\\n        if (amount > balance) {\\n            throw new InsufficientFundsException(amount - balance);\\n        }\\n        balance -= amount;\\n    }\\n}",
                    "explanation": "Shows how to create and use custom exceptions with meaningful error information."
                }
            ]
            """;
    }
    
    private void createJavaInterviewQuestions(List<Topic> topics) {
        List<InterviewQuestion> questions = new ArrayList<>();
        
        // Java Basics Questions
        questions.add(createInterviewQuestion(
            topics.get(0),
            "Difference between int and Integer in Java",
            "Explain the difference between primitive int and wrapper class Integer, including autoboxing/unboxing.",
            InterviewQuestion.DifficultyLevel.EASY,
            Arrays.asList(InterviewQuestion.Company.AMAZON, InterviewQuestion.Company.GOOGLE),
            "int is a primitive type stored on stack, Integer is a wrapper class stored on heap. Autoboxing converts int to Integer automatically."
        ));
        
        questions.add(createInterviewQuestion(
            topics.get(0),
            "What happens when you compare two Integer objects using ==?",
            "Explain Integer caching and when == vs equals() should be used for Integer comparison.",
            InterviewQuestion.DifficultyLevel.MEDIUM,
            Arrays.asList(InterviewQuestion.Company.MICROSOFT, InterviewQuestion.Company.META),
            "Integer caches values from -128 to 127. For cached values, == works, but for others, use equals()."
        ));
        
        // OOP Questions
        questions.add(createInterviewQuestion(
            topics.get(1),
            "Difference between method overloading and overriding",
            "Explain the concepts of method overloading vs overriding with examples and when to use each.",
            InterviewQuestion.DifficultyLevel.EASY,
            Arrays.asList(InterviewQuestion.Company.AMAZON, InterviewQuestion.Company.APPLE),
            "Overloading: same method name, different parameters (compile-time). Overriding: same signature in subclass (runtime)."
        ));
        
        // Collections Questions
        questions.add(createInterviewQuestion(
            topics.get(2),
            "When to use ArrayList vs LinkedList?",
            "Compare ArrayList and LinkedList performance characteristics and use cases.",
            InterviewQuestion.DifficultyLevel.MEDIUM,
            Arrays.asList(InterviewQuestion.Company.GOOGLE, InterviewQuestion.Company.META),
            "ArrayList: O(1) access, O(n) insertion/deletion. LinkedList: O(n) access, O(1) insertion/deletion at known position."
        ));
        
        // Exception Handling Questions
        questions.add(createInterviewQuestion(
            topics.get(3),
            "Difference between checked and unchecked exceptions",
            "Explain the difference between checked and unchecked exceptions with examples.",
            InterviewQuestion.DifficultyLevel.EASY,
            Arrays.asList(InterviewQuestion.Company.AMAZON, InterviewQuestion.Company.MICROSOFT),
            "Checked exceptions must be handled or declared (IOException). Unchecked exceptions are runtime exceptions (NullPointerException)."
        ));
        
        // Batch save all questions for better database performance
        questionRepository.saveAll(questions);
        
        log.info("Created {} Java interview questions", questions.size());
    }
    
    private InterviewQuestion createInterviewQuestion(Topic topic, String title, String description, 
                                                    InterviewQuestion.DifficultyLevel difficulty,
                                                    List<InterviewQuestion.Company> companies, String solution) {
        InterviewQuestion question = new InterviewQuestion();
        question.setTitle(title);
        question.setDescription(description);
        question.setDifficultyLevel(difficulty);
        question.setCompanies(companies);
        question.setSolution(solution);
        question.setTopic(topic);
        return question;
    }
    
    // Additional module creation methods would follow the same pattern...
    // Removing Lombok dependencies and optimizing for database-agnostic operations
    
    private void createSpringFrameworkModule() {
        // Implementation follows same pattern as Java module
        log.info("Spring Framework module creation - to be implemented");
    }
    
    private void createReactDevelopmentModule() {
        // Implementation follows same pattern as Java module  
        log.info("React Development module creation - to be implemented");
    }
    
    private void createDataStructuresModule() {
        // Implementation follows same pattern as Java module
        log.info("Data Structures module creation - to be implemented");
    }
    
    private void createAlgorithmsModule() {
        // Implementation follows same pattern as Java module
        log.info("Algorithms module creation - to be implemented");
    }
    
    private void createSystemDesignModule() {
        // Implementation follows same pattern as Java module
        log.info("System Design module creation - to be implemented");
    }
    
    private void createDatabaseModule() {
        // Implementation follows same pattern as Java module
        log.info("Database module creation - to be implemented");
    }
    
    private void createNodeJsFundamentalsModule() {
        // Implementation follows same pattern as Java module
        log.info("Node.js Fundamentals module creation - to be implemented");
    }
    
    private void createInterviewPrepModule() {
        // Implementation follows same pattern as Java module
        log.info("Interview Preparation module creation - to be implemented");
    }
}