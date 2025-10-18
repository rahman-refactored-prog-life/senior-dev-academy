package com.learningportal.config;

import com.learningportal.model.*;
import com.learningportal.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

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
            createDatabaseModule();
            // TODO: createHibernateJpaModule(); // To be implemented
            createNodeJsFundamentalsModule();
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
        // Topic 1: Java Basics - Variables, Data Types, and Operators
        Topic javaBasicsTopic = new Topic();
        javaBasicsTopic.setTitle("Java Basics: Variables, Data Types, and Operators");
        javaBasicsTopic.setDescription("Master Java fundamentals: variables, primitive types, operators, and memory management");
        javaBasicsTopic.setContent("""
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
            <table border="1" style="border-collapse: collapse; width: 100%;">
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
            <p><em>*JVM implementation dependent, typically 32 bits</em></p>
            
            <h4>🏠 Memory Allocation</h4>
            <ul>
                <li><strong>Stack Memory</strong>: Primitive variables, method parameters, local variables</li>
                <li><strong>Heap Memory</strong>: Objects, arrays, instance variables</li>
                <li><strong>Method Area</strong>: Class metadata, static variables, constants</li>
            </ul>
            
            <h3>🔧 Variable Declaration and Initialization</h3>
            <h4>Declaration Syntax:</h4>
            <pre><code>dataType variableName;                    // Declaration
dataType variableName = value;            // Declaration + Initialization
final dataType CONSTANT_NAME = value;    // Constant (immutable)</code></pre>
            
            <h4>Variable Scoping Rules:</h4>
            <ul>
                <li><strong>Local Variables</strong>: Method scope, must be initialized before use</li>
                <li><strong>Instance Variables</strong>: Object scope, automatically initialized</li>
                <li><strong>Class Variables (static)</strong>: Class scope, shared across instances</li>
            </ul>
            
            <h3>⚡ Operators and Expressions</h3>
            <h4>Arithmetic Operators:</h4>
            <ul>
                <li><code>+, -, *, /, %</code> - Basic arithmetic</li>
                <li><code>++, --</code> - Increment/decrement (pre/post)</li>
                <li><code>+=, -=, *=, /=, %=</code> - Compound assignment</li>
            </ul>
            
            <h4>Comparison and Logical:</h4>
            <ul>
                <li><code>==, !=, <, >, <=, >=</code> - Comparison</li>
                <li><code>&&, ||, !</code> - Logical operators</li>
                <li><code>&, |, ^, ~, <<, >>, >>></code> - Bitwise operators</li>
            </ul>
            
            <h3>🔄 Type Conversion and Casting</h3>
            <h4>Implicit Conversion (Widening):</h4>
            <p><code>byte → short → int → long → float → double</code></p>
            
            <h4>Explicit Casting (Narrowing):</h4>
            <p>Requires explicit cast operator: <code>(targetType) value</code></p>
            
            <h3>💡 Best Practices</h3>
            <ul>
                <li>Use <code>int</code> for most integer operations</li>
                <li>Use <code>double</code> for floating-point calculations</li>
                <li>Always initialize local variables</li>
                <li>Use meaningful variable names</li>
                <li>Follow camelCase naming convention</li>
                <li>Use <code>final</code> for constants and immutable references</li>
            </ul>
            """);
        
        javaBasicsTopic.setCodeExamples("""
            [
                {
                    "language": "java",
                    "code": "/**\\n * Comprehensive Java Basics Examples\\n * Covers variables, data types, operators, and type conversion\\n */\\npublic class JavaBasicsDemo {\\n    \\n    // Class variables (static) - shared across all instances\\n    static int classCounter = 0;\\n    static final String COMPANY_NAME = \\"TechCorp\\"; // Constant\\n    \\n    // Instance variables - each object has its own copy\\n    private String name;\\n    private int id;\\n    \\n    public static void main(String[] args) {\\n        demonstratePrimitiveTypes();\\n        demonstrateOperators();\\n        demonstrateTypeConversion();\\n        demonstrateVariableScoping();\\n    }\\n    \\n    static void demonstratePrimitiveTypes() {\\n        System.out.println(\\"=== Primitive Data Types ===\\");\\n        \\n        // Integer types\\n        byte smallNumber = 127;        // Max value for byte\\n        short mediumNumber = 32767;    // Max value for short\\n        int regularNumber = 2147483647; // Max value for int\\n        long bigNumber = 9223372036854775807L; // Max value for long\\n        \\n        // Floating-point types\\n        float precision = 3.14159f;    // 'f' suffix required\\n        double doublePrecision = 3.141592653589793;\\n        \\n        // Other types\\n        boolean isActive = true;\\n        char grade = 'A';\\n        char unicodeChar = '\\\\u0041'; // Unicode for 'A'\\n        \\n        System.out.printf(\\"byte: %d, short: %d, int: %d, long: %d%n\\", \\n                         smallNumber, mediumNumber, regularNumber, bigNumber);\\n        System.out.printf(\\"float: %.5f, double: %.15f%n\\", precision, doublePrecision);\\n        System.out.printf(\\"boolean: %b, char: %c, unicode: %c%n\\", \\n                         isActive, grade, unicodeChar);\\n    }\\n}",
                    "explanation": "Comprehensive demonstration of all primitive data types with proper usage patterns and memory considerations."
                },
                {
                    "language": "java",
                    "code": "    static void demonstrateOperators() {\\n        System.out.println(\\"\\\\n=== Operators and Expressions ===\\");\\n        \\n        int a = 10, b = 3;\\n        \\n        // Arithmetic operators\\n        System.out.println(\\"Arithmetic: \\" + a + \\" + \\" + b + \\" = \\" + (a + b));\\n        System.out.println(\\"Division: \\" + a + \\" / \\" + b + \\" = \\" + (a / b)); // Integer division\\n        System.out.println(\\"Modulo: \\" + a + \\" % \\" + b + \\" = \\" + (a % b));\\n        \\n        // Pre vs Post increment\\n        int x = 5;\\n        System.out.println(\\"x = \\" + x);\\n        System.out.println(\\"++x = \\" + (++x)); // Pre-increment: increment then use\\n        System.out.println(\\"x++ = \\" + (x++)); // Post-increment: use then increment\\n        System.out.println(\\"Final x = \\" + x);\\n        \\n        // Compound assignment\\n        int value = 10;\\n        value += 5;  // Equivalent to: value = value + 5\\n        System.out.println(\\"After += 5: \\" + value);\\n        \\n        // Bitwise operations (important for optimization)\\n        int num1 = 12; // Binary: 1100\\n        int num2 = 10; // Binary: 1010\\n        System.out.println(\\"Bitwise AND: \\" + num1 + \\" & \\" + num2 + \\" = \\" + (num1 & num2));\\n        System.out.println(\\"Left shift: \\" + num1 + \\" << 1 = \\" + (num1 << 1)); // Multiply by 2\\n        System.out.println(\\"Right shift: \\" + num1 + \\" >> 1 = \\" + (num1 >> 1)); // Divide by 2\\n    }",
                    "explanation": "Demonstrates all operator types with practical examples, including bitwise operations for performance optimization."
                },
                {
                    "language": "java",
                    "code": "    static void demonstrateTypeConversion() {\\n        System.out.println(\\"\\\\n=== Type Conversion and Casting ===\\");\\n        \\n        // Implicit conversion (widening) - safe, no data loss\\n        int intValue = 100;\\n        long longValue = intValue;     // int → long\\n        float floatValue = longValue;  // long → float\\n        double doubleValue = floatValue; // float → double\\n        \\n        System.out.println(\\"Implicit conversions:\\");\\n        System.out.printf(\\"int %d → long %d → float %.1f → double %.1f%n\\", \\n                         intValue, longValue, floatValue, doubleValue);\\n        \\n        // Explicit casting (narrowing) - potential data loss\\n        double largeDouble = 123.456;\\n        float castFloat = (float) largeDouble;   // Precision loss\\n        int castInt = (int) largeDouble;         // Fractional part lost\\n        byte castByte = (byte) 300;              // Overflow: 300 → 44\\n        \\n        System.out.println(\\"\\\\nExplicit casting (potential data loss):\\");\\n        System.out.printf(\\"double %.3f → float %.3f → int %d%n\\", \\n                         largeDouble, castFloat, castInt);\\n        System.out.printf(\\"int 300 → byte %d (overflow!)%n\\", castByte);\\n        \\n        // String to primitive conversion\\n        String numberStr = \\"42\\";\\n        int parsedInt = Integer.parseInt(numberStr);\\n        double parsedDouble = Double.parseDouble(\\"3.14159\\");\\n        \\n        System.out.printf(\\"String parsing: '%s' → int %d, '3.14159' → double %.5f%n\\", \\n                         numberStr, parsedInt, parsedDouble);\\n    }",
                    "explanation": "Comprehensive type conversion examples showing both safe implicit conversions and potentially dangerous explicit casts."
                },
                {
                    "language": "java",
                    "code": "    static void demonstrateVariableScoping() {\\n        System.out.println(\\"\\\\n=== Variable Scoping and Lifetime ===\\");\\n        \\n        // Local variables - method scope\\n        int localVar = 10;\\n        System.out.println(\\"Local variable: \\" + localVar);\\n        \\n        // Block scope\\n        if (true) {\\n            int blockVar = 20; // Only accessible within this block\\n            localVar = 15;     // Can modify outer scope variable\\n            System.out.println(\\"Block variable: \\" + blockVar);\\n            System.out.println(\\"Modified local: \\" + localVar);\\n        }\\n        // blockVar is not accessible here\\n        \\n        // Class variable access\\n        classCounter++;\\n        System.out.println(\\"Class counter: \\" + classCounter);\\n        System.out.println(\\"Company constant: \\" + COMPANY_NAME);\\n        \\n        // Demonstrate final variables\\n        final int IMMUTABLE = 100;\\n        // IMMUTABLE = 200; // Compilation error!\\n        \\n        final List<String> immutableRef = new ArrayList<>();\\n        immutableRef.add(\\"Item\\"); // Object can be modified\\n        // immutableRef = new ArrayList<>(); // Reference cannot be changed\\n        \\n        System.out.println(\\"Final variable: \\" + IMMUTABLE);\\n        System.out.println(\\"Final reference content: \\" + immutableRef);\\n    }\\n}",
                    "explanation": "Demonstrates variable scoping rules, lifetime, and the difference between final variables and final references."
                }
            ]
            """);
        
        javaBasicsTopic.setKeyConcepts("""
            ["Primitive Data Types", "Variables and Scoping", "Type Conversion", "Operators", "Memory Management", "Stack vs Heap", "Implicit vs Explicit Casting", "Variable Lifetime", "Constants and Final"]
            """);
        
        javaBasicsTopic.setTopicType(Topic.TopicType.THEORY);
        javaBasicsTopic.setEstimatedMinutes(90);
        javaBasicsTopic.setSortOrder(1);
        javaBasicsTopic.setDifficultyLevel(LearningModule.DifficultyLevel.BEGINNER);
        javaBasicsTopic.setModule(module);
        
        topicRepository.save(javaBasicsTopic);
        
        // Create comprehensive interview questions for this topic
        createJavaBasicsInterviewQuestions(javaBasicsTopic);
        
        // Topic 2: Object-Oriented Programming - The Four Pillars
        Topic oopTopic = new Topic();
        oopTopic.setTitle("Object-Oriented Programming: The Four Pillars");
        oopTopic.setDescription("Master OOP fundamentals: Encapsulation, Inheritance, Polymorphism, and Abstraction with real-world examples and FAANG interview questions");
        oopTopic.setContent("""
            <h2>Object-Oriented Programming in Java</h2>
            
            <h3>🎯 Learning Objectives</h3>
            <ul>
                <li>Master the four pillars of OOP with practical implementations</li>
                <li>Understand class design principles and best practices</li>
                <li>Learn method overriding vs overloading with real examples</li>
                <li>Practice inheritance hierarchies and polymorphic behavior</li>
                <li>Solve complex OOP interview questions from FAANG companies</li>
            </ul>
            
            <h3>🏛️ The Four Pillars of OOP</h3>
            
            <h4>1. 🔒 Encapsulation - Data Hiding and Access Control</h4>
            <p><strong>Definition</strong>: Bundling data (fields) and methods that operate on that data within a single unit (class), while hiding internal implementation details.</p>
            
            <h5>Key Benefits:</h5>
            <ul>
                <li><strong>Data Protection</strong>: Prevents unauthorized access and modification</li>
                <li><strong>Maintainability</strong>: Internal changes don't affect external code</li>
                <li><strong>Flexibility</strong>: Implementation can be changed without breaking client code</li>
                <li><strong>Validation</strong>: Control how data is accessed and modified</li>
            </ul>
            
            <h5>Implementation Techniques:</h5>
            <ul>
                <li><strong>Private Fields</strong>: Hide internal state</li>
                <li><strong>Public Getters/Setters</strong>: Controlled access to data</li>
                <li><strong>Package-Private</strong>: Access within same package</li>
                <li><strong>Protected</strong>: Access within inheritance hierarchy</li>
            </ul>
            
            <h4>2. 🧬 Inheritance - Code Reuse and IS-A Relationships</h4>
            <p><strong>Definition</strong>: Mechanism where a new class (subclass) inherits properties and behaviors from an existing class (superclass).</p>
            
            <h5>Types of Inheritance in Java:</h5>
            <ul>
                <li><strong>Single Inheritance</strong>: One class extends another class</li>
                <li><strong>Multilevel Inheritance</strong>: Chain of inheritance (A → B → C)</li>
                <li><strong>Hierarchical Inheritance</strong>: Multiple classes inherit from one superclass</li>
                <li><strong>Interface Implementation</strong>: Multiple inheritance through interfaces</li>
            </ul>
            
            <h5>Key Concepts:</h5>
            <ul>
                <li><strong>super keyword</strong>: Access parent class members</li>
                <li><strong>Method Overriding</strong>: Redefine parent class methods</li>
                <li><strong>Constructor Chaining</strong>: Call parent constructors</li>
                <li><strong>Final Classes/Methods</strong>: Prevent inheritance/overriding</li>
            </ul>
            
            <h4>3. 🎭 Polymorphism - One Interface, Multiple Forms</h4>
            <p><strong>Definition</strong>: Ability of objects to take multiple forms, allowing the same interface to represent different underlying data types.</p>
            
            <h5>Types of Polymorphism:</h5>
            <ul>
                <li><strong>Compile-time Polymorphism</strong>: Method overloading, operator overloading</li>
                <li><strong>Runtime Polymorphism</strong>: Method overriding, dynamic method dispatch</li>
            </ul>
            
            <h5>Benefits:</h5>
            <ul>
                <li><strong>Code Flexibility</strong>: Write code that works with multiple types</li>
                <li><strong>Extensibility</strong>: Add new types without changing existing code</li>
                <li><strong>Maintainability</strong>: Reduce code duplication</li>
            </ul>
            
            <h4>4. 🎨 Abstraction - Hide Complexity, Show Essentials</h4>
            <p><strong>Definition</strong>: Process of hiding implementation details while showing only essential features to the user.</p>
            
            <h5>Implementation Methods:</h5>
            <ul>
                <li><strong>Abstract Classes</strong>: Partial implementation with abstract methods</li>
                <li><strong>Interfaces</strong>: Pure abstraction with contracts</li>
                <li><strong>Method Signatures</strong>: Define what, not how</li>
            </ul>
            
            <h3>🔧 Access Modifiers in Java</h3>
            <table border="1" style="border-collapse: collapse; width: 100%;">
                <tr><th>Modifier</th><th>Same Class</th><th>Same Package</th><th>Subclass</th><th>Different Package</th></tr>
                <tr><td><strong>private</strong></td><td>✅</td><td>❌</td><td>❌</td><td>❌</td></tr>
                <tr><td><strong>default</strong></td><td>✅</td><td>✅</td><td>❌</td><td>❌</td></tr>
                <tr><td><strong>protected</strong></td><td>✅</td><td>✅</td><td>✅</td><td>❌</td></tr>
                <tr><td><strong>public</strong></td><td>✅</td><td>✅</td><td>✅</td><td>✅</td></tr>
            </table>
            
            <h3>🎯 Method Overloading vs Overriding</h3>
            <table border="1" style="border-collapse: collapse; width: 100%;">
                <tr><th>Aspect</th><th>Overloading</th><th>Overriding</th></tr>
                <tr><td><strong>Definition</strong></td><td>Multiple methods with same name, different parameters</td><td>Redefining parent class method in child class</td></tr>
                <tr><td><strong>Binding</strong></td><td>Compile-time (static)</td><td>Runtime (dynamic)</td></tr>
                <tr><td><strong>Inheritance</strong></td><td>Not required</td><td>Required</td></tr>
                <tr><td><strong>Parameters</strong></td><td>Must be different</td><td>Must be same</td></tr>
                <tr><td><strong>Return Type</strong></td><td>Can be different</td><td>Must be same or covariant</td></tr>
            </table>
            
            <h3>💡 OOP Design Principles</h3>
            <ul>
                <li><strong>Single Responsibility</strong>: Each class should have one reason to change</li>
                <li><strong>Open/Closed</strong>: Open for extension, closed for modification</li>
                <li><strong>Liskov Substitution</strong>: Subtypes must be substitutable for base types</li>
                <li><strong>Interface Segregation</strong>: Clients shouldn't depend on unused interfaces</li>
                <li><strong>Dependency Inversion</strong>: Depend on abstractions, not concretions</li>
            </ul>
            
            <h3>🚨 Common OOP Pitfalls</h3>
            <ul>
                <li><strong>God Classes</strong>: Classes that do too much</li>
                <li><strong>Tight Coupling</strong>: Classes too dependent on each other</li>
                <li><strong>Inappropriate Inheritance</strong>: Using inheritance for code reuse only</li>
                <li><strong>Breaking Encapsulation</strong>: Exposing internal state unnecessarily</li>
            </ul>
            """);
        
        oopTopic.setCodeExamples("""
            [
                {
                    "language": "java",
                    "code": "/**\\n * Comprehensive OOP Example: Banking System\\n * Demonstrates all four pillars of OOP\\n */\\n\\n// Encapsulation: Data hiding and controlled access\\npublic class BankAccount {\\n    // Private fields - encapsulated data\\n    private String accountNumber;\\n    private double balance;\\n    private String accountType;\\n    private static int totalAccounts = 0; // Class variable\\n    \\n    // Constructor\\n    public BankAccount(String accountNumber, double initialBalance, String accountType) {\\n        this.accountNumber = accountNumber;\\n        this.balance = initialBalance;\\n        this.accountType = accountType;\\n        totalAccounts++; // Track total accounts\\n    }\\n    \\n    // Controlled access through public methods\\n    public double getBalance() {\\n        return balance;\\n    }\\n    \\n    public String getAccountNumber() {\\n        return accountNumber;\\n    }\\n    \\n    // Business logic with validation\\n    public boolean deposit(double amount) {\\n        if (amount > 0) {\\n            balance += amount;\\n            return true;\\n        }\\n        return false;\\n    }\\n    \\n    public boolean withdraw(double amount) {\\n        if (amount > 0 && amount <= balance) {\\n            balance -= amount;\\n            return true;\\n        }\\n        return false;\\n    }\\n    \\n    // Static method - belongs to class\\n    public static int getTotalAccounts() {\\n        return totalAccounts;\\n    }\\n}",
                    "explanation": "Demonstrates encapsulation with private fields, controlled access, validation, and static members."
                },
                {
                    "language": "java",
                    "code": "// Inheritance: IS-A relationship and code reuse\\nabstract class Vehicle {\\n    protected String brand;\\n    protected String model;\\n    protected int year;\\n    \\n    public Vehicle(String brand, String model, int year) {\\n        this.brand = brand;\\n        this.model = model;\\n        this.year = year;\\n    }\\n    \\n    // Concrete method - inherited by all subclasses\\n    public void displayInfo() {\\n        System.out.println(year + \\\" \\\" + brand + \\\" \\\" + model);\\n    }\\n    \\n    // Abstract method - must be implemented by subclasses\\n    public abstract void start();\\n    public abstract double calculateFuelEfficiency();\\n}\\n\\nclass Car extends Vehicle {\\n    private int doors;\\n    private String fuelType;\\n    \\n    public Car(String brand, String model, int year, int doors, String fuelType) {\\n        super(brand, model, year); // Call parent constructor\\n        this.doors = doors;\\n        this.fuelType = fuelType;\\n    }\\n    \\n    @Override\\n    public void start() {\\n        System.out.println(\\\"Car engine starting with key\\\");\\n    }\\n    \\n    @Override\\n    public double calculateFuelEfficiency() {\\n        return fuelType.equals(\\\"Electric\\\") ? 120.0 : 25.0; // MPG equivalent\\n    }\\n    \\n    // Car-specific method\\n    public void openTrunk() {\\n        System.out.println(\\\"Trunk opened\\\");\\n    }\\n}\\n\\nclass Motorcycle extends Vehicle {\\n    private boolean hasSidecar;\\n    \\n    public Motorcycle(String brand, String model, int year, boolean hasSidecar) {\\n        super(brand, model, year);\\n        this.hasSidecar = hasSidecar;\\n    }\\n    \\n    @Override\\n    public void start() {\\n        System.out.println(\\\"Motorcycle starting with kick/button\\\");\\n    }\\n    \\n    @Override\\n    public double calculateFuelEfficiency() {\\n        return hasSidecar ? 35.0 : 50.0; // MPG\\n    }\\n}",
                    "explanation": "Shows inheritance hierarchy with abstract base class, method overriding, and specialized implementations."
                },
                {
                    "language": "java",
                    "code": "// Polymorphism: One interface, multiple implementations\\npublic class PolymorphismDemo {\\n    public static void main(String[] args) {\\n        // Runtime polymorphism - same reference type, different objects\\n        Vehicle[] vehicles = {\\n            new Car(\\\"Toyota\\\", \\\"Camry\\\", 2023, 4, \\\"Gasoline\\\"),\\n            new Car(\\\"Tesla\\\", \\\"Model 3\\\", 2023, 4, \\\"Electric\\\"),\\n            new Motorcycle(\\\"Harley\\\", \\\"Sportster\\\", 2023, false)\\n        };\\n        \\n        // Polymorphic behavior - method calls resolved at runtime\\n        for (Vehicle vehicle : vehicles) {\\n            vehicle.displayInfo();           // Inherited method\\n            vehicle.start();                 // Overridden method\\n            System.out.println(\\\"Fuel Efficiency: \\\" + \\n                             vehicle.calculateFuelEfficiency() + \\\" MPG\\\");\\n            System.out.println(\\\"---\\\");\\n        }\\n        \\n        // Method overloading - compile-time polymorphism\\n        VehicleService service = new VehicleService();\\n        service.service(vehicles[0]);                    // Vehicle version\\n        service.service((Car) vehicles[0]);              // Car version\\n        service.service(vehicles[0], \\\"Premium\\\");         // Vehicle with service type\\n    }\\n}\\n\\nclass VehicleService {\\n    // Method overloading - same name, different parameters\\n    public void service(Vehicle vehicle) {\\n        System.out.println(\\\"Basic vehicle service\\\");\\n    }\\n    \\n    public void service(Car car) {\\n        System.out.println(\\\"Specialized car service\\\");\\n        car.openTrunk(); // Car-specific method\\n    }\\n    \\n    public void service(Vehicle vehicle, String serviceType) {\\n        System.out.println(serviceType + \\\" service for \\\" + vehicle.brand);\\n    }\\n}",
                    "explanation": "Demonstrates both runtime polymorphism (method overriding) and compile-time polymorphism (method overloading)."
                },
                {
                    "language": "java",
                    "code": "// Abstraction: Hiding complexity, showing essentials\\n\\n// Interface - pure abstraction\\ninterface PaymentProcessor {\\n    boolean processPayment(double amount);\\n    String getPaymentMethod();\\n    \\n    // Default method (Java 8+)\\n    default void logTransaction(double amount) {\\n        System.out.println(\\\"Transaction logged: $\\\" + amount);\\n    }\\n}\\n\\n// Abstract class - partial abstraction\\nabstract class OnlinePayment implements PaymentProcessor {\\n    protected String merchantId;\\n    protected boolean isSecure;\\n    \\n    public OnlinePayment(String merchantId) {\\n        this.merchantId = merchantId;\\n        this.isSecure = true;\\n    }\\n    \\n    // Concrete method - common implementation\\n    protected boolean validateAmount(double amount) {\\n        return amount > 0 && amount <= 10000;\\n    }\\n    \\n    // Abstract method - specific implementation required\\n    protected abstract boolean authenticateUser();\\n}\\n\\n// Concrete implementations\\nclass CreditCardPayment extends OnlinePayment {\\n    private String cardNumber;\\n    \\n    public CreditCardPayment(String merchantId, String cardNumber) {\\n        super(merchantId);\\n        this.cardNumber = cardNumber;\\n    }\\n    \\n    @Override\\n    public boolean processPayment(double amount) {\\n        if (!validateAmount(amount) || !authenticateUser()) {\\n            return false;\\n        }\\n        // Credit card specific processing logic\\n        System.out.println(\\\"Processing $\\\" + amount + \\\" via Credit Card\\\");\\n        logTransaction(amount);\\n        return true;\\n    }\\n    \\n    @Override\\n    public String getPaymentMethod() {\\n        return \\\"Credit Card\\\";\\n    }\\n    \\n    @Override\\n    protected boolean authenticateUser() {\\n        // Credit card specific authentication\\n        return cardNumber != null && cardNumber.length() == 16;\\n    }\\n}\\n\\nclass PayPalPayment extends OnlinePayment {\\n    private String email;\\n    \\n    public PayPalPayment(String merchantId, String email) {\\n        super(merchantId);\\n        this.email = email;\\n    }\\n    \\n    @Override\\n    public boolean processPayment(double amount) {\\n        if (!validateAmount(amount) || !authenticateUser()) {\\n            return false;\\n        }\\n        // PayPal specific processing logic\\n        System.out.println(\\\"Processing $\\\" + amount + \\\" via PayPal\\\");\\n        logTransaction(amount);\\n        return true;\\n    }\\n    \\n    @Override\\n    public String getPaymentMethod() {\\n        return \\\"PayPal\\\";\\n    }\\n    \\n    @Override\\n    protected boolean authenticateUser() {\\n        // PayPal specific authentication\\n        return email != null && email.contains(\\\"@\\\");\\n    }\\n}",
                    "explanation": "Shows abstraction through interfaces and abstract classes, hiding implementation complexity while providing clear contracts."
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
        
        // Topic 3: Collections Framework - Lists, Sets, Maps, and Queues
        Topic collectionsTopic = new Topic();
        collectionsTopic.setTitle("Java Collections Framework: Complete Guide");
        collectionsTopic.setDescription("Master Java Collections with performance analysis, real-world usage patterns, and comprehensive FAANG interview questions");
        collectionsTopic.setContent("""
            <h2>Java Collections Framework: Complete Guide</h2>
            
            <h3>🎯 Learning Objectives</h3>
            <ul>
                <li>Master all collection interfaces and implementations</li>
                <li>Understand performance characteristics and when to use each collection</li>
                <li>Learn advanced collection operations and stream processing</li>
                <li>Practice with real-world scenarios and optimization techniques</li>
                <li>Solve complex collections interview questions from FAANG companies</li>
            </ul>
            
            <h3>📚 Collections Hierarchy Overview</h3>
            <p>The Java Collections Framework provides a unified architecture for representing and manipulating collections, enabling collections to be manipulated independently of implementation details.</p>
            
            <h4>🏗️ Core Interfaces</h4>
            <table border="1" style="border-collapse: collapse; width: 100%;">
                <tr><th>Interface</th><th>Purpose</th><th>Key Implementations</th><th>Use Cases</th></tr>
                <tr><td><strong>Collection</strong></td><td>Root interface</td><td>All collections</td><td>Common operations</td></tr>
                <tr><td><strong>List</strong></td><td>Ordered, allows duplicates</td><td>ArrayList, LinkedList, Vector</td><td>Indexed access, ordered data</td></tr>
                <tr><td><strong>Set</strong></td><td>No duplicates</td><td>HashSet, LinkedHashSet, TreeSet</td><td>Unique elements, membership testing</td></tr>
                <tr><td><strong>Queue</strong></td><td>FIFO operations</td><td>LinkedList, PriorityQueue, ArrayDeque</td><td>Task scheduling, BFS algorithms</td></tr>
                <tr><td><strong>Map</strong></td><td>Key-value pairs</td><td>HashMap, LinkedHashMap, TreeMap</td><td>Lookups, caching, indexing</td></tr>
            </table>
            
            <h3>📋 List Implementations Deep Dive</h3>
            
            <h4>ArrayList - Dynamic Array</h4>
            <ul>
                <li><strong>Internal Structure</strong>: Resizable array, default capacity 10</li>
                <li><strong>Time Complexity</strong>: O(1) access, O(n) insertion/deletion (worst case)</li>
                <li><strong>Memory</strong>: Contiguous memory, good cache locality</li>
                <li><strong>Best For</strong>: Random access, frequent reads, known size ranges</li>
                <li><strong>Avoid When</strong>: Frequent insertions/deletions in middle</li>
            </ul>
            
            <h4>LinkedList - Doubly Linked List</h4>
            <ul>
                <li><strong>Internal Structure</strong>: Doubly-linked nodes with pointers</li>
                <li><strong>Time Complexity</strong>: O(n) access, O(1) insertion/deletion at known position</li>
                <li><strong>Memory</strong>: Extra memory for pointers, poor cache locality</li>
                <li><strong>Best For</strong>: Frequent insertions/deletions, queue/stack operations</li>
                <li><strong>Avoid When</strong>: Random access, memory-constrained environments</li>
            </ul>
            
            <h3>🔗 Set Implementations Deep Dive</h3>
            
            <h4>HashSet - Hash Table Based</h4>
            <ul>
                <li><strong>Internal Structure</strong>: Hash table with separate chaining</li>
                <li><strong>Time Complexity</strong>: O(1) average for add/remove/contains</li>
                <li><strong>Ordering</strong>: No guaranteed order</li>
                <li><strong>Best For</strong>: Fast lookups, uniqueness checking</li>
            </ul>
            
            <h4>LinkedHashSet - Hash Table + Linked List</h4>
            <ul>
                <li><strong>Internal Structure</strong>: HashSet + doubly-linked list for ordering</li>
                <li><strong>Time Complexity</strong>: O(1) operations with insertion order</li>
                <li><strong>Ordering</strong>: Maintains insertion order</li>
                <li><strong>Best For</strong>: When you need HashSet performance with predictable iteration</li>
            </ul>
            
            <h4>TreeSet - Red-Black Tree</h4>
            <ul>
                <li><strong>Internal Structure</strong>: Self-balancing binary search tree</li>
                <li><strong>Time Complexity</strong>: O(log n) for add/remove/contains</li>
                <li><strong>Ordering</strong>: Natural ordering or custom Comparator</li>
                <li><strong>Best For</strong>: Sorted data, range queries, navigable operations</li>
            </ul>
            
            <h3>🗺️ Map Implementations Deep Dive</h3>
            
            <h4>HashMap - Hash Table Based</h4>
            <ul>
                <li><strong>Internal Structure</strong>: Array of buckets with separate chaining (Java 8+: trees for large buckets)</li>
                <li><strong>Time Complexity</strong>: O(1) average, O(n) worst case (poor hash function)</li>
                <li><strong>Load Factor</strong>: Default 0.75, rehashing when exceeded</li>
                <li><strong>Thread Safety</strong>: Not thread-safe</li>
                <li><strong>Best For</strong>: General-purpose key-value storage</li>
            </ul>
            
            <h4>ConcurrentHashMap - Thread-Safe HashMap</h4>
            <ul>
                <li><strong>Internal Structure</strong>: Segmented locking (Java 7), CAS operations (Java 8+)</li>
                <li><strong>Time Complexity</strong>: O(1) average with thread safety</li>
                <li><strong>Concurrency</strong>: High concurrency, lock-free reads</li>
                <li><strong>Best For</strong>: Multi-threaded applications, high-concurrency scenarios</li>
            </ul>
            
            <h3>⚡ Performance Comparison</h3>
            <table border="1" style="border-collapse: collapse; width: 100%;">
                <tr><th>Operation</th><th>ArrayList</th><th>LinkedList</th><th>HashSet</th><th>TreeSet</th><th>HashMap</th></tr>
                <tr><td><strong>Add (end)</strong></td><td>O(1) amortized</td><td>O(1)</td><td>O(1) avg</td><td>O(log n)</td><td>O(1) avg</td></tr>
                <tr><td><strong>Add (middle)</strong></td><td>O(n)</td><td>O(1) if position known</td><td>N/A</td><td>N/A</td><td>N/A</td></tr>
                <tr><td><strong>Remove</strong></td><td>O(n)</td><td>O(1) if position known</td><td>O(1) avg</td><td>O(log n)</td><td>O(1) avg</td></tr>
                <tr><td><strong>Search</strong></td><td>O(n)</td><td>O(n)</td><td>O(1) avg</td><td>O(log n)</td><td>O(1) avg</td></tr>
                <tr><td><strong>Access by index</strong></td><td>O(1)</td><td>O(n)</td><td>N/A</td><td>N/A</td><td>N/A</td></tr>
            </table>
            
            <h3>🔄 Iteration and Stream Operations</h3>
            <ul>
                <li><strong>Enhanced for-each</strong>: Syntactic sugar for Iterator pattern</li>
                <li><strong>Iterator vs ListIterator</strong>: Bidirectional navigation for Lists</li>
                <li><strong>Stream API</strong>: Functional-style operations on collections</li>
                <li><strong>Parallel Streams</strong>: Automatic parallelization for large datasets</li>
            </ul>
            
            <h3>💡 Best Practices and Common Pitfalls</h3>
            <ul>
                <li><strong>Choose the right collection</strong>: Based on access patterns and performance requirements</li>
                <li><strong>Initialize with capacity</strong>: Avoid unnecessary resizing operations</li>
                <li><strong>Use immutable collections</strong>: When data doesn't change</li>
                <li><strong>Implement equals() and hashCode()</strong>: For custom objects in hash-based collections</li>
                <li><strong>Be careful with concurrent modifications</strong>: Use ConcurrentModificationException-safe approaches</li>
            </ul>
            """);
        
        collectionsTopic.setCodeExamples("""
            [
                {
                    "language": "java",
                    "code": "/**\\n * Comprehensive Collections Performance Demo\\n * Demonstrates different collection types and their performance characteristics\\n */\\nimport java.util.*;\\nimport java.util.concurrent.ConcurrentHashMap;\\n\\npublic class CollectionsPerformanceDemo {\\n    \\n    public static void main(String[] args) {\\n        demonstrateListPerformance();\\n        demonstrateSetOperations();\\n        demonstrateMapOperations();\\n        demonstrateAdvancedOperations();\\n    }\\n    \\n    static void demonstrateListPerformance() {\\n        System.out.println(\\\"=== List Performance Comparison ===\\\");\\n        \\n        // ArrayList vs LinkedList performance\\n        int size = 100000;\\n        \\n        // ArrayList - excellent for random access\\n        List<Integer> arrayList = new ArrayList<>(size); // Pre-size for performance\\n        long startTime = System.nanoTime();\\n        for (int i = 0; i < size; i++) {\\n            arrayList.add(i);\\n        }\\n        long arrayListAddTime = System.nanoTime() - startTime;\\n        \\n        // LinkedList - excellent for insertions\\n        List<Integer> linkedList = new LinkedList<>();\\n        startTime = System.nanoTime();\\n        for (int i = 0; i < size; i++) {\\n            linkedList.add(i);\\n        }\\n        long linkedListAddTime = System.nanoTime() - startTime;\\n        \\n        System.out.printf(\\\"ArrayList add time: %d ns%n\\\", arrayListAddTime);\\n        System.out.printf(\\\"LinkedList add time: %d ns%n\\\", linkedListAddTime);\\n        \\n        // Random access performance\\n        Random random = new Random();\\n        startTime = System.nanoTime();\\n        for (int i = 0; i < 1000; i++) {\\n            arrayList.get(random.nextInt(size));\\n        }\\n        long arrayListAccessTime = System.nanoTime() - startTime;\\n        \\n        startTime = System.nanoTime();\\n        for (int i = 0; i < 1000; i++) {\\n            linkedList.get(random.nextInt(size)); // This is O(n) - very slow!\\n        }\\n        long linkedListAccessTime = System.nanoTime() - startTime;\\n        \\n        System.out.printf(\\\"ArrayList random access: %d ns%n\\\", arrayListAccessTime);\\n        System.out.printf(\\\"LinkedList random access: %d ns%n\\\", linkedListAccessTime);\\n    }\\n}",
                    "explanation": "Demonstrates performance differences between ArrayList and LinkedList for different operations."
                },
                {
                    "language": "java",
                    "code": "    static void demonstrateSetOperations() {\\n        System.out.println(\\\"\\\\n=== Set Operations and Performance ===\\\");\\n        \\n        // Different Set implementations\\n        Set<String> hashSet = new HashSet<>();\\n        Set<String> linkedHashSet = new LinkedHashSet<>();\\n        Set<String> treeSet = new TreeSet<>();\\n        \\n        String[] words = {\\\"apple\\\", \\\"banana\\\", \\\"cherry\\\", \\\"date\\\", \\\"elderberry\\\"};\\n        \\n        // Add elements to all sets\\n        for (String word : words) {\\n            hashSet.add(word);\\n            linkedHashSet.add(word);\\n            treeSet.add(word);\\n        }\\n        \\n        System.out.println(\\\"HashSet (no order): \\\" + hashSet);\\n        System.out.println(\\\"LinkedHashSet (insertion order): \\\" + linkedHashSet);\\n        System.out.println(\\\"TreeSet (natural order): \\\" + treeSet);\\n        \\n        // Set operations\\n        Set<String> fruits1 = Set.of(\\\"apple\\\", \\\"banana\\\", \\\"cherry\\\");\\n        Set<String> fruits2 = Set.of(\\\"banana\\\", \\\"cherry\\\", \\\"date\\\");\\n        \\n        // Union\\n        Set<String> union = new HashSet<>(fruits1);\\n        union.addAll(fruits2);\\n        System.out.println(\\\"Union: \\\" + union);\\n        \\n        // Intersection\\n        Set<String> intersection = new HashSet<>(fruits1);\\n        intersection.retainAll(fruits2);\\n        System.out.println(\\\"Intersection: \\\" + intersection);\\n        \\n        // Difference\\n        Set<String> difference = new HashSet<>(fruits1);\\n        difference.removeAll(fruits2);\\n        System.out.println(\\\"Difference: \\\" + difference);\\n        \\n        // TreeSet advanced operations\\n        TreeSet<Integer> numbers = new TreeSet<>(Arrays.asList(1, 3, 5, 7, 9, 11, 13, 15));\\n        System.out.println(\\\"Numbers: \\\" + numbers);\\n        System.out.println(\\\"First: \\\" + numbers.first());\\n        System.out.println(\\\"Last: \\\" + numbers.last());\\n        System.out.println(\\\"Lower than 7: \\\" + numbers.lower(7));\\n        System.out.println(\\\"Higher than 7: \\\" + numbers.higher(7));\\n        System.out.println(\\\"Subset [5, 10): \\\" + numbers.subSet(5, 10));\\n    }",
                    "explanation": "Shows different Set implementations, their ordering behavior, and set operations like union, intersection, and difference."
                },
                {
                    "language": "java",
                    "code": "    static void demonstrateMapOperations() {\\n        System.out.println(\\\"\\\\n=== Map Operations and Patterns ===\\\");\\n        \\n        // Different Map implementations\\n        Map<String, Integer> hashMap = new HashMap<>();\\n        Map<String, Integer> linkedHashMap = new LinkedHashMap<>();\\n        Map<String, Integer> treeMap = new TreeMap<>();\\n        Map<String, Integer> concurrentMap = new ConcurrentHashMap<>();\\n        \\n        // Populate maps\\n        String[] keys = {\\\"charlie\\\", \\\"alice\\\", \\\"bob\\\", \\\"david\\\"};\\n        for (int i = 0; i < keys.length; i++) {\\n            hashMap.put(keys[i], i + 1);\\n            linkedHashMap.put(keys[i], i + 1);\\n            treeMap.put(keys[i], i + 1);\\n            concurrentMap.put(keys[i], i + 1);\\n        }\\n        \\n        System.out.println(\\\"HashMap (no order): \\\" + hashMap);\\n        System.out.println(\\\"LinkedHashMap (insertion order): \\\" + linkedHashMap);\\n        System.out.println(\\\"TreeMap (natural key order): \\\" + treeMap);\\n        \\n        // Advanced Map operations (Java 8+)\\n        Map<String, Integer> wordCount = new HashMap<>();\\n        String text = \\\"hello world hello java world\\\";\\n        \\n        // Count word frequencies using computeIfAbsent\\n        for (String word : text.split(\\\" \\\")) {\\n            wordCount.computeIfAbsent(word, k -> 0);\\n            wordCount.computeIfPresent(word, (k, v) -> v + 1);\\n        }\\n        System.out.println(\\\"Word count: \\\" + wordCount);\\n        \\n        // Using merge for counting\\n        Map<String, Integer> wordCount2 = new HashMap<>();\\n        for (String word : text.split(\\\" \\\")) {\\n            wordCount2.merge(word, 1, Integer::sum);\\n        }\\n        System.out.println(\\\"Word count (using merge): \\\" + wordCount2);\\n        \\n        // Map transformations\\n        Map<String, String> upperCaseMap = new HashMap<>();\\n        hashMap.forEach((k, v) -> upperCaseMap.put(k.toUpperCase(), \\\"Value: \\\" + v));\\n        System.out.println(\\\"Transformed map: \\\" + upperCaseMap);\\n    }",
                    "explanation": "Demonstrates different Map implementations, advanced operations like computeIfAbsent, merge, and common patterns like word counting."
                },
                {
                    "language": "java",
                    "code": "    static void demonstrateAdvancedOperations() {\\n        System.out.println(\\\"\\\\n=== Advanced Collections Operations ===\\\");\\n        \\n        // Stream operations on collections\\n        List<String> names = Arrays.asList(\\\"Alice\\\", \\\"Bob\\\", \\\"Charlie\\\", \\\"David\\\", \\\"Eve\\\");\\n        \\n        // Filter, map, and collect\\n        List<String> longNames = names.stream()\\n            .filter(name -> name.length() > 4)\\n            .map(String::toUpperCase)\\n            .collect(Collectors.toList());\\n        System.out.println(\\\"Long names (uppercase): \\\" + longNames);\\n        \\n        // Group by length\\n        Map<Integer, List<String>> namesByLength = names.stream()\\n            .collect(Collectors.groupingBy(String::length));\\n        System.out.println(\\\"Names grouped by length: \\\" + namesByLength);\\n        \\n        // Custom objects in collections\\n        List<Person> people = Arrays.asList(\\n            new Person(\\\"Alice\\\", 30),\\n            new Person(\\\"Bob\\\", 25),\\n            new Person(\\\"Charlie\\\", 35)\\n        );\\n        \\n        // Sort by age\\n        people.sort(Comparator.comparing(Person::getAge));\\n        System.out.println(\\\"People sorted by age: \\\" + people);\\n        \\n        // Find oldest person\\n        Optional<Person> oldest = people.stream()\\n            .max(Comparator.comparing(Person::getAge));\\n        oldest.ifPresent(p -> System.out.println(\\\"Oldest person: \\\" + p));\\n        \\n        // Immutable collections (Java 9+)\\n        List<String> immutableList = List.of(\\\"a\\\", \\\"b\\\", \\\"c\\\");\\n        Set<String> immutableSet = Set.of(\\\"x\\\", \\\"y\\\", \\\"z\\\");\\n        Map<String, Integer> immutableMap = Map.of(\\\"one\\\", 1, \\\"two\\\", 2);\\n        \\n        System.out.println(\\\"Immutable collections created\\\");\\n        \\n        // Concurrent collection example\\n        ConcurrentHashMap<String, Integer> concurrentCounter = new ConcurrentHashMap<>();\\n        \\n        // Simulate concurrent updates\\n        List<Thread> threads = new ArrayList<>();\\n        for (int i = 0; i < 5; i++) {\\n            final int threadId = i;\\n            Thread thread = new Thread(() -> {\\n                for (int j = 0; j < 100; j++) {\\n                    concurrentCounter.merge(\\\"thread-\\\" + threadId, 1, Integer::sum);\\n                }\\n            });\\n            threads.add(thread);\\n            thread.start();\\n        }\\n        \\n        // Wait for all threads to complete\\n        threads.forEach(thread -> {\\n            try {\\n                thread.join();\\n            } catch (InterruptedException e) {\\n                Thread.currentThread().interrupt();\\n            }\\n        });\\n        \\n        System.out.println(\\\"Concurrent counter results: \\\" + concurrentCounter);\\n    }\\n    \\n    // Helper class for demonstration\\n    static class Person {\\n        private final String name;\\n        private final int age;\\n        \\n        public Person(String name, int age) {\\n            this.name = name;\\n            this.age = age;\\n        }\\n        \\n        public String getName() { return name; }\\n        public int getAge() { return age; }\\n        \\n        @Override\\n        public String toString() {\\n            return name + \\\"(\\\" + age + \\\")\\\";\\n        }\\n        \\n        @Override\\n        public boolean equals(Object obj) {\\n            if (this == obj) return true;\\n            if (obj == null || getClass() != obj.getClass()) return false;\\n            Person person = (Person) obj;\\n            return age == person.age && Objects.equals(name, person.name);\\n        }\\n        \\n        @Override\\n        public int hashCode() {\\n            return Objects.hash(name, age);\\n        }\\n    }\\n}",
                    "explanation": "Advanced collections operations including streams, custom object handling, immutable collections, and concurrent collections usage."
                }
            ]
            """);
        
        collectionsTopic.setKeyConcepts("""
            ["ArrayList vs LinkedList", "HashMap internals", "Set implementations", "TreeMap and TreeSet", "ConcurrentHashMap", "Stream API", "Iterator patterns", "Performance analysis", "Thread safety", "Immutable collections"]
            """);
        
        collectionsTopic.setTopicType(Topic.TopicType.THEORY);
        collectionsTopic.setEstimatedMinutes(120);
        collectionsTopic.setSortOrder(3);
        collectionsTopic.setDifficultyLevel(LearningModule.DifficultyLevel.INTERMEDIATE);
        collectionsTopic.setModule(module);
        
        topicRepository.save(collectionsTopic);
        createCollectionsInterviewQuestions(collectionsTopic);
        
        // Topic 4: Exception Handling - Robust Error Management
        Topic exceptionTopic = new Topic();
        exceptionTopic.setTitle("Exception Handling: Building Robust Applications");
        exceptionTopic.setDescription("Master Java exception handling with best practices, custom exceptions, and error recovery strategies for production systems");
        exceptionTopic.setContent("""
            <h2>Exception Handling: Building Robust Applications</h2>
            
            <h3>🎯 Learning Objectives</h3>
            <ul>
                <li>Understand the exception hierarchy and different exception types</li>
                <li>Master try-catch-finally blocks and resource management</li>
                <li>Learn to create meaningful custom exceptions</li>
                <li>Practice exception handling best practices for production code</li>
                <li>Solve exception-related interview questions from top companies</li>
            </ul>
            
            <h3>🏗️ Exception Hierarchy</h3>
            <p>Java's exception system is built on a well-defined hierarchy that helps categorize and handle different types of errors:</p>
            
            <h4>📊 Exception Class Hierarchy</h4>
            <pre>
            Throwable
            ├── Error (Unchecked)
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
            
            <h3>🔍 Exception Types Deep Dive</h3>
            
            <h4>Checked Exceptions</h4>
            <ul>
                <li><strong>Compile-time enforcement</strong>: Must be caught or declared</li>
                <li><strong>Recoverable conditions</strong>: Expected error scenarios</li>
                <li><strong>Examples</strong>: File not found, network timeout, database connection failure</li>
                <li><strong>Best for</strong>: External system interactions, I/O operations</li>
            </ul>
            
            <h4>Unchecked Exceptions (Runtime Exceptions)</h4>
            <ul>
                <li><strong>Runtime enforcement</strong>: Optional to catch or declare</li>
                <li><strong>Programming errors</strong>: Usually indicate bugs</li>
                <li><strong>Examples</strong>: Null pointer access, array bounds, illegal arguments</li>
                <li><strong>Best for</strong>: Input validation, precondition violations</li>
            </ul>
            
            <h4>Errors</h4>
            <ul>
                <li><strong>System-level problems</strong>: Usually unrecoverable</li>
                <li><strong>JVM issues</strong>: Memory exhaustion, stack overflow</li>
                <li><strong>Generally not caught</strong>: Application should terminate gracefully</li>
            </ul>
            
            <h3>🛠️ Exception Handling Mechanisms</h3>
            
            <h4>Try-Catch-Finally Block</h4>
            <ul>
                <li><strong>try</strong>: Contains code that might throw exceptions</li>
                <li><strong>catch</strong>: Handles specific exception types</li>
                <li><strong>finally</strong>: Always executes (cleanup code)</li>
                <li><strong>Multi-catch</strong>: Handle multiple exception types (Java 7+)</li>
            </ul>
            
            <h4>Try-with-Resources (Java 7+)</h4>
            <ul>
                <li><strong>Automatic resource management</strong>: Implements AutoCloseable</li>
                <li><strong>Guaranteed cleanup</strong>: Resources closed automatically</li>
                <li><strong>Suppressed exceptions</strong>: Cleanup exceptions attached to main exception</li>
                <li><strong>Best practice</strong>: For file I/O, database connections, network resources</li>
            </ul>
            
            <h3>🎯 Exception Handling Best Practices</h3>
            
            <h4>✅ Do's</h4>
            <ul>
                <li><strong>Catch specific exceptions</strong>: Avoid catching Exception or Throwable</li>
                <li><strong>Log meaningful information</strong>: Include context and stack traces</li>
                <li><strong>Fail fast</strong>: Validate inputs early and throw exceptions immediately</li>
                <li><strong>Use try-with-resources</strong>: For automatic resource management</li>
                <li><strong>Create custom exceptions</strong>: For domain-specific error conditions</li>
                <li><strong>Document exceptions</strong>: Use @throws in Javadoc</li>
            </ul>
            
            <h4>❌ Don'ts</h4>
            <ul>
                <li><strong>Don't swallow exceptions</strong>: Empty catch blocks hide problems</li>
                <li><strong>Don't catch and ignore</strong>: At least log the exception</li>
                <li><strong>Don't use exceptions for control flow</strong>: Exceptions are expensive</li>
                <li><strong>Don't catch Error</strong>: Let the JVM handle system errors</li>
                <li><strong>Don't return null</strong>: Throw exceptions for error conditions</li>
            </ul>
            
            <h3>🏗️ Custom Exception Design</h3>
            
            <h4>When to Create Custom Exceptions</h4>
            <ul>
                <li><strong>Domain-specific errors</strong>: Business logic violations</li>
                <li><strong>Multiple error types</strong>: Different handling strategies needed</li>
                <li><strong>Additional context</strong>: Need to carry extra information</li>
                <li><strong>API design</strong>: Clear contract with callers</li>
            </ul>
            
            <h4>Custom Exception Best Practices</h4>
            <ul>
                <li><strong>Extend appropriate base class</strong>: RuntimeException vs Exception</li>
                <li><strong>Provide multiple constructors</strong>: Message, cause, both</li>
                <li><strong>Include relevant context</strong>: Error codes, affected objects</li>
                <li><strong>Make them serializable</strong>: For distributed systems</li>
                <li><strong>Use meaningful names</strong>: Clear indication of the problem</li>
            </ul>
            
            <h3>⚡ Performance Considerations</h3>
            <ul>
                <li><strong>Exception creation cost</strong>: Stack trace generation is expensive</li>
                <li><strong>Avoid exceptions in loops</strong>: Validate before processing</li>
                <li><strong>Use unchecked exceptions</strong>: For performance-critical code</li>
                <li><strong>Consider Optional</strong>: For methods that might not return a value</li>
            </ul>
            """);
        
        exceptionTopic.setCodeExamples("""
            [
                {
                    "language": "java",
                    "code": "/**\\n * Comprehensive Exception Handling Examples\\n * Demonstrates different exception types and handling strategies\\n */\\nimport java.io.*;\\nimport java.util.*;\\n\\npublic class ExceptionHandlingDemo {\\n    \\n    public static void main(String[] args) {\\n        demonstrateBasicExceptionHandling();\\n        demonstrateTryWithResources();\\n        demonstrateCustomExceptions();\\n        demonstrateExceptionChaining();\\n    }\\n    \\n    static void demonstrateBasicExceptionHandling() {\\n        System.out.println(\\\"=== Basic Exception Handling ===\");\\n        \\n        // Multiple catch blocks - specific to general\\n        try {\\n            String[] array = {\\\"1\\\", \\\"2\\\", \\\"abc\\\"};\\n            \\n            // This might throw NumberFormatException\\n            int number = Integer.parseInt(array[2]);\\n            \\n            // This might throw ArrayIndexOutOfBoundsException\\n            System.out.println(array[10]);\\n            \\n        } catch (NumberFormatException e) {\\n            System.out.println(\\\"Invalid number format: \\\" + e.getMessage());\\n            // Log the exception with context\\n            logException(\\\"Number parsing failed\\\", e);\\n            \\n        } catch (ArrayIndexOutOfBoundsException e) {\\n            System.out.println(\\\"Array index out of bounds: \\\" + e.getMessage());\\n            logException(\\\"Array access failed\\\", e);\\n            \\n        } catch (Exception e) {\\n            // Catch-all for unexpected exceptions\\n            System.out.println(\\\"Unexpected error: \\\" + e.getMessage());\\n            logException(\\\"Unexpected error\\\", e);\\n            \\n        } finally {\\n            // Always executes - cleanup code\\n            System.out.println(\\\"Cleanup completed\\\");\\n        }\\n        \\n        // Multi-catch (Java 7+)\\n        try {\\n            riskyOperation();\\n        } catch (IOException | SQLException e) {\\n            System.out.println(\\\"I/O or Database error: \\\" + e.getMessage());\\n            logException(\\\"External system error\\\", e);\\n        }\\n    }",
                    "explanation": "Demonstrates basic exception handling patterns including multiple catch blocks, finally, and multi-catch."
                },
                {
                    "language": "java",
                    "code": "    static void demonstrateTryWithResources() {\\n        System.out.println(\\\"\\\\n=== Try-with-Resources ===\");\\n        \\n        // Traditional approach (verbose and error-prone)\\n        FileInputStream fis = null;\\n        try {\\n            fis = new FileInputStream(\\\"example.txt\\\");\\n            // Process file...\\n        } catch (IOException e) {\\n            System.out.println(\\\"File operation failed: \\\" + e.getMessage());\\n        } finally {\\n            if (fis != null) {\\n                try {\\n                    fis.close();\\n                } catch (IOException e) {\\n                    System.out.println(\\\"Failed to close file: \\\" + e.getMessage());\\n                }\\n            }\\n        }\\n        \\n        // Try-with-resources (clean and safe)\\n        try (FileInputStream autoFis = new FileInputStream(\\\"example.txt\\\");\\n             BufferedReader reader = new BufferedReader(new InputStreamReader(autoFis))) {\\n            \\n            String line;\\n            while ((line = reader.readLine()) != null) {\\n                System.out.println(line);\\n            }\\n            \\n        } catch (IOException e) {\\n            System.out.println(\\\"File processing failed: \\\" + e.getMessage());\\n            // Resources are automatically closed, even if exception occurs\\n        }\\n        \\n        // Custom resource with AutoCloseable\\n        try (DatabaseConnection db = new DatabaseConnection(\\\"localhost\\\");\\n             NetworkConnection net = new NetworkConnection(\\\"api.example.com\\\")) {\\n            \\n            // Use resources...\\n            db.executeQuery(\\\"SELECT * FROM users\\\");\\n            net.sendRequest(\\\"/api/data\\\");\\n            \\n        } catch (Exception e) {\\n            System.out.println(\\\"Resource operation failed: \\\" + e.getMessage());\\n            // Both connections automatically closed\\n        }\\n    }",
                    "explanation": "Shows the evolution from manual resource management to try-with-resources, including custom AutoCloseable resources."
                },
                {
                    "language": "java",
                    "code": "    static void demonstrateCustomExceptions() {\\n        System.out.println(\\\"\\\\n=== Custom Exceptions ===\");\\n        \\n        try {\\n            // Business logic with custom exceptions\\n            BankAccount account = new BankAccount(\\\"12345\\\", 1000.0);\\n            \\n            account.withdraw(500.0);  // Should succeed\\n            System.out.println(\\\"Withdrawal successful. Balance: \\\" + account.getBalance());\\n            \\n            account.withdraw(600.0);  // Should fail - insufficient funds\\n            \\n        } catch (InsufficientFundsException e) {\\n            System.out.println(\\\"Transaction failed: \\\" + e.getMessage());\\n            System.out.println(\\\"Available balance: \\\" + e.getAvailableBalance());\\n            System.out.println(\\\"Attempted amount: \\\" + e.getAttemptedAmount());\\n            \\n        } catch (InvalidAccountException e) {\\n            System.out.println(\\\"Account error: \\\" + e.getMessage());\\n            System.out.println(\\\"Account ID: \\\" + e.getAccountId());\\n            \\n        } catch (BankingException e) {\\n            System.out.println(\\\"Banking system error: \\\" + e.getMessage());\\n            System.out.println(\\\"Error code: \\\" + e.getErrorCode());\\n        }\\n    }\\n    \\n    static void demonstrateExceptionChaining() {\\n        System.out.println(\\\"\\\\n=== Exception Chaining ===\");\\n        \\n        try {\\n            processUserData(\\\"invalid-user-id\\\");\\n        } catch (UserProcessingException e) {\\n            System.out.println(\\\"User processing failed: \\\" + e.getMessage());\\n            \\n            // Print the full exception chain\\n            Throwable cause = e.getCause();\\n            while (cause != null) {\\n                System.out.println(\\\"Caused by: \\\" + cause.getClass().getSimpleName() + \\\": \\\" + cause.getMessage());\\n                cause = cause.getCause();\\n            }\\n        }\\n    }\\n    \\n    // Helper methods and classes\\n    static void riskyOperation() throws IOException, SQLException {\\n        // Simulate operations that might fail\\n        if (Math.random() > 0.5) {\\n            throw new IOException(\\\"Network connection failed\\\");\\n        } else {\\n            throw new SQLException(\\\"Database query failed\\\");\\n        }\\n    }\\n    \\n    static void logException(String context, Exception e) {\\n        System.err.println(\\\"[ERROR] \\\" + context + \\\": \\\" + e.getClass().getSimpleName() + \\\" - \\\" + e.getMessage());\\n        // In real applications, use proper logging framework\\n    }",
                    "explanation": "Demonstrates custom exception creation, exception chaining, and proper error context handling."
                },
                {
                    "language": "java",
                    "code": "    // Custom Exception Classes\\n    \\n    // Base exception for banking operations\\n    static class BankingException extends Exception {\\n        private final String errorCode;\\n        \\n        public BankingException(String message, String errorCode) {\\n            super(message);\\n            this.errorCode = errorCode;\\n        }\\n        \\n        public BankingException(String message, String errorCode, Throwable cause) {\\n            super(message, cause);\\n            this.errorCode = errorCode;\\n        }\\n        \\n        public String getErrorCode() { return errorCode; }\\n    }\\n    \\n    // Specific exception for insufficient funds\\n    static class InsufficientFundsException extends BankingException {\\n        private final double availableBalance;\\n        private final double attemptedAmount;\\n        \\n        public InsufficientFundsException(double availableBalance, double attemptedAmount) {\\n            super(String.format(\\\"Insufficient funds. Available: %.2f, Attempted: %.2f\\\", \\n                               availableBalance, attemptedAmount), \\\"INSUFFICIENT_FUNDS\\\");\\n            this.availableBalance = availableBalance;\\n            this.attemptedAmount = attemptedAmount;\\n        }\\n        \\n        public double getAvailableBalance() { return availableBalance; }\\n        public double getAttemptedAmount() { return attemptedAmount; }\\n    }\\n    \\n    // Exception for invalid account operations\\n    static class InvalidAccountException extends BankingException {\\n        private final String accountId;\\n        \\n        public InvalidAccountException(String accountId, String reason) {\\n            super(\\\"Invalid account operation: \\\" + reason, \\\"INVALID_ACCOUNT\\\");\\n            this.accountId = accountId;\\n        }\\n        \\n        public String getAccountId() { return accountId; }\\n    }\\n    \\n    // User processing exception with chaining\\n    static class UserProcessingException extends Exception {\\n        public UserProcessingException(String message, Throwable cause) {\\n            super(message, cause);\\n        }\\n    }\\n    \\n    // Example business logic class\\n    static class BankAccount {\\n        private final String accountId;\\n        private double balance;\\n        \\n        public BankAccount(String accountId, double initialBalance) throws InvalidAccountException {\\n            if (accountId == null || accountId.trim().isEmpty()) {\\n                throw new InvalidAccountException(accountId, \\\"Account ID cannot be null or empty\\\");\\n            }\\n            if (initialBalance < 0) {\\n                throw new InvalidAccountException(accountId, \\\"Initial balance cannot be negative\\\");\\n            }\\n            this.accountId = accountId;\\n            this.balance = initialBalance;\\n        }\\n        \\n        public void withdraw(double amount) throws InsufficientFundsException, InvalidAccountException {\\n            if (amount <= 0) {\\n                throw new InvalidAccountException(accountId, \\\"Withdrawal amount must be positive\\\");\\n            }\\n            if (amount > balance) {\\n                throw new InsufficientFundsException(balance, amount);\\n            }\\n            balance -= amount;\\n        }\\n        \\n        public double getBalance() { return balance; }\\n    }\\n    \\n    static void processUserData(String userId) throws UserProcessingException {\\n        try {\\n            // Simulate database operation\\n            if (\\\"invalid-user-id\\\".equals(userId)) {\\n                throw new SQLException(\\\"User not found in database\\\");\\n            }\\n        } catch (SQLException e) {\\n            // Chain the exception with additional context\\n            throw new UserProcessingException(\\\"Failed to process user: \\\" + userId, e);\\n        }\\n    }\\n}",
                    "explanation": "Complete custom exception hierarchy with proper constructors, context information, and exception chaining."
                }
            ]
            """);
        
        exceptionTopic.setKeyConcepts("""
            ["Exception Hierarchy", "Checked vs Unchecked Exceptions", "Try-Catch-Finally", "Try-with-Resources", "Custom Exceptions", "Exception Chaining", "Resource Management", "Error Recovery Strategies"]
            """);
        
        exceptionTopic.setTopicType(Topic.TopicType.THEORY);
        exceptionTopic.setEstimatedMinutes(100);
        exceptionTopic.setSortOrder(4);
        exceptionTopic.setDifficultyLevel(LearningModule.DifficultyLevel.INTERMEDIATE);
        exceptionTopic.setModule(module);
        
        topicRepository.save(exceptionTopic);
        createExceptionHandlingInterviewQuestions(exceptionTopic);
        
        // Topic 5: Generics and Type Safety - Advanced Type System
        Topic genericsTopic = new Topic();
        genericsTopic.setTitle("Generics and Type Safety: Advanced Type System");
        genericsTopic.setDescription("Master Java Generics with wildcards, type erasure, bounded parameters, and advanced type safety patterns for robust applications");
        genericsTopic.setContent("""
            <h2>Generics and Type Safety: Advanced Type System</h2>
            
            <h3>🎯 Learning Objectives</h3>
            <ul>
                <li>Master generic classes, methods, and interfaces with real-world applications</li>
                <li>Understand wildcards and bounded type parameters for flexible APIs</li>
                <li>Learn type erasure and its implications for runtime behavior</li>
                <li>Practice advanced generic patterns used in enterprise applications</li>
                <li>Solve complex generics interview questions from top tech companies</li>
            </ul>
            
            <h3>🔍 Why Generics Matter</h3>
            <p>Generics provide <strong>compile-time type safety</strong> and eliminate the need for explicit casting, making code more readable, maintainable, and less error-prone.</p>
            
            <h4>Benefits of Generics</h4>
            <ul>
                <li><strong>Type Safety</strong>: Catch type errors at compile time, not runtime</li>
                <li><strong>Elimination of Casts</strong>: No need for explicit type casting</li>
                <li><strong>Generic Algorithms</strong>: Write algorithms that work with different types</li>
                <li><strong>Better Performance</strong>: Avoid boxing/unboxing of primitives</li>
                <li><strong>Clearer APIs</strong>: Self-documenting code with type information</li>
            </ul>
            
            <h3>📚 Generic Fundamentals</h3>
            
            <h4>Generic Classes</h4>
            <p>Generic classes allow you to define classes with type parameters that are specified when the class is instantiated.</p>
            
            <h5>Syntax and Conventions:</h5>
            <ul>
                <li><strong>Type Parameter Naming</strong>: T (Type), E (Element), K (Key), V (Value), N (Number)</li>
                <li><strong>Multiple Parameters</strong>: &lt;T, U, V&gt; for multiple type parameters</li>
                <li><strong>Bounded Parameters</strong>: &lt;T extends Number&gt; for type constraints</li>
                <li><strong>Wildcard Usage</strong>: &lt;? extends T&gt; and &lt;? super T&gt; for flexibility</li>
            </ul>
            
            <h4>Generic Methods</h4>
            <p>Generic methods can be defined in both generic and non-generic classes, providing type safety for specific operations.</p>
            
            <h4>Generic Interfaces</h4>
            <p>Interfaces can be generic, allowing for flexible contracts that work with multiple types while maintaining type safety.</p>
            
            <h3>🎭 Wildcards and Bounded Types</h3>
            
            <h4>Wildcard Types</h4>
            <table border="1" style="border-collapse: collapse; width: 100%;">
                <tr><th>Wildcard</th><th>Meaning</th><th>Use Case</th><th>Example</th></tr>
                <tr><td><strong>?</strong></td><td>Unknown type</td><td>Read-only access</td><td>List&lt;?&gt;</td></tr>
                <tr><td><strong>? extends T</strong></td><td>T or subtype</td><td>Producer (read from)</td><td>List&lt;? extends Number&gt;</td></tr>
                <tr><td><strong>? super T</strong></td><td>T or supertype</td><td>Consumer (write to)</td><td>List&lt;? super Integer&gt;</td></tr>
            </table>
            
            <h4>PECS Principle</h4>
            <p><strong>Producer Extends, Consumer Super</strong> - A fundamental guideline for wildcard usage:</p>
            <ul>
                <li><strong>Producer Extends</strong>: Use &lt;? extends T&gt; when you only read from the collection</li>
                <li><strong>Consumer Super</strong>: Use &lt;? super T&gt; when you only write to the collection</li>
                <li><strong>Both Operations</strong>: Use exact type &lt;T&gt; when you both read and write</li>
            </ul>
            
            <h3>🔧 Type Erasure and Runtime Behavior</h3>
            
            <h4>What is Type Erasure?</h4>
            <p>Java implements generics through <strong>type erasure</strong> - generic type information is removed at runtime for backward compatibility with pre-generic code.</p>
            
            <h5>Implications of Type Erasure:</h5>
            <ul>
                <li><strong>Runtime Type Loss</strong>: Cannot determine generic type at runtime</li>
                <li><strong>No Generic Arrays</strong>: Cannot create arrays of generic types</li>
                <li><strong>Bridge Methods</strong>: Compiler generates bridge methods for polymorphism</li>
                <li><strong>Instanceof Limitations</strong>: Cannot use instanceof with parameterized types</li>
                <li><strong>Exception Handling</strong>: Cannot catch or throw parameterized exceptions</li>
            </ul>
            
            <h4>Raw Types and Legacy Code</h4>
            <p>Raw types exist for backward compatibility but should be avoided in new code as they bypass type safety.</p>
            
            <h3>🏗️ Advanced Generic Patterns</h3>
            
            <h4>Builder Pattern with Generics</h4>
            <p>Generic builders provide type-safe object construction with fluent APIs.</p>
            
            <h4>Factory Pattern with Generics</h4>
            <p>Generic factories create objects while maintaining type information throughout the creation process.</p>
            
            <h4>Visitor Pattern with Generics</h4>
            <p>Type-safe visitor implementations that work with different object hierarchies.</p>
            
            <h4>Generic Utility Classes</h4>
            <p>Utility classes that provide generic operations while maintaining type safety.</p>
            
            <h3>⚡ Performance Considerations</h3>
            <ul>
                <li><strong>No Runtime Overhead</strong>: Generics are compile-time only (type erasure)</li>
                <li><strong>Reduced Boxing</strong>: Avoid unnecessary autoboxing with proper generic usage</li>
                <li><strong>Collection Performance</strong>: Generic collections avoid casting overhead</li>
                <li><strong>Memory Efficiency</strong>: No additional memory overhead for type parameters</li>
            </ul>
            
            <h3>🚨 Common Pitfalls and Best Practices</h3>
            
            <h4>❌ Common Mistakes</h4>
            <ul>
                <li><strong>Raw Type Usage</strong>: Using List instead of List&lt;String&gt;</li>
                <li><strong>Incorrect Wildcard Usage</strong>: Misunderstanding PECS principle</li>
                <li><strong>Generic Array Creation</strong>: Attempting to create new T[size]</li>
                <li><strong>Static Context Issues</strong>: Using type parameters in static methods</li>
            </ul>
            
            <h4>✅ Best Practices</h4>
            <ul>
                <li><strong>Always Use Parameterized Types</strong>: Avoid raw types in new code</li>
                <li><strong>Follow PECS Principle</strong>: Choose wildcards based on usage patterns</li>
                <li><strong>Prefer Generic Methods</strong>: More flexible than generic classes for utilities</li>
                <li><strong>Use Bounded Parameters</strong>: Constrain types when appropriate</li>
                <li><strong>Document Type Constraints</strong>: Clear Javadoc for generic APIs</li>
            </ul>
            """);
        
        genericsTopic.setCodeExamples("""
            [
                {
                    "language": "java",
                    "code": "/**\\n * Comprehensive Generics Examples\\n * Demonstrates generic classes, methods, and advanced patterns\\n */\\nimport java.util.*;\\nimport java.util.function.*;\\n\\npublic class GenericsComprehensiveDemo {\\n    \\n    public static void main(String[] args) {\\n        demonstrateGenericClasses();\\n        demonstrateGenericMethods();\\n        demonstrateWildcards();\\n        demonstrateAdvancedPatterns();\\n    }\\n    \\n    static void demonstrateGenericClasses() {\\n        System.out.println(\\\"=== Generic Classes ===\");\\n        \\n        // Generic class with single type parameter\\n        Container<String> stringContainer = new Container<>(\\\"Hello Generics\\\");\\n        System.out.println(\\\"String container: \\\" + stringContainer.getValue());\\n        \\n        // Generic class with multiple type parameters\\n        Pair<String, Integer> nameAge = new Pair<>(\\\"Alice\\\", 30);\\n        System.out.println(\\\"Name-Age pair: \\\" + nameAge.getFirst() + \\\", \\\" + nameAge.getSecond());\\n        \\n        // Generic class with bounded type parameter\\n        NumberContainer<Double> doubleContainer = new NumberContainer<>(3.14159);\\n        System.out.println(\\\"Number container value: \\\" + doubleContainer.getValue());\\n        System.out.println(\\\"Doubled: \\\" + doubleContainer.getDoubledValue());\\n        \\n        // Generic collections\\n        List<String> names = Arrays.asList(\\\"Alice\\\", \\\"Bob\\\", \\\"Charlie\\\");\\n        Map<String, Integer> ages = Map.of(\\\"Alice\\\", 30, \\\"Bob\\\", 25, \\\"Charlie\\\", 35);\\n        \\n        System.out.println(\\\"Names: \\\" + names);\\n        System.out.println(\\\"Ages: \\\" + ages);\\n    }\\n    \\n    // Generic class with single type parameter\\n    static class Container<T> {\\n        private final T value;\\n        \\n        public Container(T value) {\\n            this.value = value;\\n        }\\n        \\n        public T getValue() {\\n            return value;\\n        }\\n        \\n        @Override\\n        public String toString() {\\n            return \\\"Container{value=\\\" + value + \\\"}\\\";\\n        }\\n    }\\n    \\n    // Generic class with multiple type parameters\\n    static class Pair<T, U> {\\n        private final T first;\\n        private final U second;\\n        \\n        public Pair(T first, U second) {\\n            this.first = first;\\n            this.second = second;\\n        }\\n        \\n        public T getFirst() { return first; }\\n        public U getSecond() { return second; }\\n        \\n        @Override\\n        public String toString() {\\n            return \\\"Pair{first=\\\" + first + \\\", second=\\\" + second + \\\"}\\\";\\n        }\\n    }\\n    \\n    // Generic class with bounded type parameter\\n    static class NumberContainer<T extends Number> {\\n        private final T value;\\n        \\n        public NumberContainer(T value) {\\n            this.value = value;\\n        }\\n        \\n        public T getValue() {\\n            return value;\\n        }\\n        \\n        // Method that uses the bound - can call Number methods\\n        public double getDoubledValue() {\\n            return value.doubleValue() * 2;\\n        }\\n    }",
                    "explanation": "Demonstrates basic generic classes with single and multiple type parameters, including bounded generics."
                },
                {
                    "language": "java",
                    "code": "    static void demonstrateGenericMethods() {\\n        System.out.println(\\\"\\\\n=== Generic Methods ===\");\\n        \\n        // Generic method with type inference\\n        String[] stringArray = {\\\"apple\\\", \\\"banana\\\", \\\"cherry\\\"};\\n        Integer[] intArray = {1, 2, 3, 4, 5};\\n        \\n        // Type inference - compiler determines T from arguments\\n        String firstString = getFirst(stringArray);\\n        Integer firstInt = getFirst(intArray);\\n        \\n        System.out.println(\\\"First string: \\\" + firstString);\\n        System.out.println(\\\"First integer: \\\" + firstInt);\\n        \\n        // Generic method with multiple type parameters\\n        Pair<String, Integer> swapped = swap(new Pair<>(42, \\\"Answer\\\"));\\n        System.out.println(\\\"Swapped pair: \\\" + swapped);\\n        \\n        // Generic method with bounded type parameter\\n        List<Integer> numbers = Arrays.asList(1, 5, 3, 9, 2);\\n        Integer max = findMax(numbers);\\n        System.out.println(\\\"Max number: \\\" + max);\\n        \\n        // Generic utility methods\\n        List<String> fruits = Arrays.asList(\\\"apple\\\", \\\"banana\\\", \\\"cherry\\\");\\n        List<String> reversed = reverse(fruits);\\n        System.out.println(\\\"Original: \\\" + fruits);\\n        System.out.println(\\\"Reversed: \\\" + reversed);\\n    }\\n    \\n    // Generic method with single type parameter\\n    public static <T> T getFirst(T[] array) {\\n        if (array == null || array.length == 0) {\\n            return null;\\n        }\\n        return array[0];\\n    }\\n    \\n    // Generic method with multiple type parameters\\n    public static <T, U> Pair<U, T> swap(Pair<T, U> pair) {\\n        return new Pair<>(pair.getSecond(), pair.getFirst());\\n    }\\n    \\n    // Generic method with bounded type parameter\\n    public static <T extends Comparable<T>> T findMax(List<T> list) {\\n        if (list == null || list.isEmpty()) {\\n            return null;\\n        }\\n        \\n        T max = list.get(0);\\n        for (T item : list) {\\n            if (item.compareTo(max) > 0) {\\n                max = item;\\n            }\\n        }\\n        return max;\\n    }\\n    \\n    // Generic utility method\\n    public static <T> List<T> reverse(List<T> list) {\\n        List<T> reversed = new ArrayList<>(list);\\n        Collections.reverse(reversed);\\n        return reversed;\\n    }",
                    "explanation": "Shows generic methods with type inference, multiple parameters, and bounded types for flexible utility functions."
                }
            ]
            """);
        
        genericsTopic.setKeyConcepts("""
            ["Generic Classes", "Generic Methods", "Type Parameters", "Wildcards", "Bounded Types", "Type Erasure", "PECS Principle", "Raw Types", "Generic Collections", "Type Safety"]
            """);
        
        genericsTopic.setTopicType(Topic.TopicType.THEORY);
        genericsTopic.setEstimatedMinutes(110);
        genericsTopic.setSortOrder(5);
        genericsTopic.setDifficultyLevel(LearningModule.DifficultyLevel.ADVANCED);
        genericsTopic.setModule(module);
        
        topicRepository.save(genericsTopic);
        createGenericsInterviewQuestions(genericsTopic);
    }
    
    private void createJavaBasicsInterviewQuestions(Topic topic) {
        // Question 1: Primitive vs Reference Types (Amazon, Google - High Frequency)
        InterviewQuestion q1 = new InterviewQuestion();
        q1.setTitle("Difference between primitive and reference data types in Java");
        q1.setDescription("A fundamental Java question asked in 90% of Java interviews. Explain the key differences between primitive and reference data types, discuss memory allocation, and provide examples showing their behavior differences.");
        q1.setSolution("""
            <h3>Primitive vs Reference Data Types - Complete Analysis</h3>
            
            <h4>🔢 Primitive Data Types (8 types)</h4>
            <table border="1" style="border-collapse: collapse;">
                <tr><th>Aspect</th><th>Details</th><th>Interview Impact</th></tr>
                <tr><td><strong>Storage</strong></td><td>Store actual values directly</td><td>Memory efficient, faster access</td></tr>
                <tr><td><strong>Memory Location</strong></td><td>Stack memory (local vars) or heap (instance vars)</td><td>Stack allocation is faster</td></tr>
                <tr><td><strong>Types</strong></td><td>byte, short, int, long, float, double, boolean, char</td><td>Know size and range of each</td></tr>
                <tr><td><strong>Default Values</strong></td><td>0 (numbers), false (boolean), '\\u0000' (char)</td><td>Instance vars auto-initialized</td></tr>
                <tr><td><strong>Parameter Passing</strong></td><td>Pass by value (copy of value)</td><td>Original value unchanged</td></tr>
                <tr><td><strong>Comparison</strong></td><td>Use == for value comparison</td><td>Direct value comparison</td></tr>
            </table>
            
            <h4>🏠 Reference Data Types</h4>
            <table border="1" style="border-collapse: collapse;">
                <tr><th>Aspect</th><th>Details</th><th>Interview Impact</th></tr>
                <tr><td><strong>Storage</strong></td><td>Store memory addresses (references)</td><td>Indirection adds overhead</td></tr>
                <tr><td><strong>Memory Location</strong></td><td>Reference on stack, object on heap</td><td>Heap allocation slower, GC managed</td></tr>
                <tr><td><strong>Types</strong></td><td>Classes, interfaces, arrays, enums</td><td>Everything except 8 primitives</td></tr>
                <tr><td><strong>Default Values</strong></td><td>null</td><td>Can cause NullPointerException</td></tr>
                <tr><td><strong>Parameter Passing</strong></td><td>Pass by value of reference</td><td>Object can be modified</td></tr>
                <tr><td><strong>Comparison</strong></td><td>== compares references, .equals() compares content</td><td>Common source of bugs</td></tr>
            </table>
            
            <h4>🧠 Memory Model Deep Dive</h4>
            <ul>
                <li><strong>Stack Frame</strong>: Each method call creates a frame containing local variables and parameters</li>
                <li><strong>Heap Allocation</strong>: Objects created with 'new' keyword allocated on heap</li>
                <li><strong>Garbage Collection</strong>: Automatic memory management for heap objects</li>
                <li><strong>String Pool</strong>: Special heap area for string literals (optimization)</li>
            </ul>
            
            <h4>⚡ Performance Implications</h4>
            <ul>
                <li><strong>Primitives</strong>: Faster access, no GC overhead, cache-friendly</li>
                <li><strong>References</strong>: Indirection cost, GC pressure, potential cache misses</li>
                <li><strong>Autoboxing Cost</strong>: Automatic primitive ↔ wrapper conversion has overhead</li>
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
        
        // Question 2: Autoboxing and Unboxing (Google, Meta - High Frequency)
        InterviewQuestion q2 = new InterviewQuestion();
        q2.setTitle("Explain autoboxing and unboxing in Java with performance implications");
        q2.setDescription("Real Google interview question: What is autoboxing and unboxing? When does it happen automatically? What are the performance implications? Provide code examples showing potential issues.");
        q2.setSolution("""
            <h3>Autoboxing and Unboxing - Complete Guide</h3>
            
            <h4>🔄 Definitions</h4>
            <ul>
                <li><strong>Autoboxing</strong>: Automatic conversion of primitive types to their wrapper classes</li>
                <li><strong>Unboxing</strong>: Automatic conversion of wrapper classes to primitive types</li>
            </ul>
            
            <h4>📦 Wrapper Classes</h4>
            <table border="1" style="border-collapse: collapse;">
                <tr><th>Primitive</th><th>Wrapper Class</th><th>Example</th></tr>
                <tr><td>int</td><td>Integer</td><td>Integer.valueOf(42)</td></tr>
                <tr><td>double</td><td>Double</td><td>Double.valueOf(3.14)</td></tr>
                <tr><td>boolean</td><td>Boolean</td><td>Boolean.valueOf(true)</td></tr>
                <tr><td>char</td><td>Character</td><td>Character.valueOf('A')</td></tr>
            </table>
            
            <h4>⚡ Performance Implications</h4>
            <ul>
                <li><strong>Memory Overhead</strong>: Wrapper objects consume more memory (Integer: 16 bytes vs int: 4 bytes)</li>
                <li><strong>GC Pressure</strong>: Creates objects that need garbage collection</li>
                <li><strong>CPU Cost</strong>: Method calls for boxing/unboxing operations</li>
                <li><strong>Cache Performance</strong>: Objects scattered in heap vs primitives in contiguous stack</li>
            </ul>
            
            <h4>🚨 Common Pitfalls</h4>
            <ul>
                <li><strong>NullPointerException</strong>: Unboxing null wrapper objects</li>
                <li><strong>Performance Degradation</strong>: Unnecessary boxing in loops</li>
                <li><strong>Identity vs Equality</strong>: == behavior with cached vs non-cached values</li>
            </ul>
            """);
        
        q2.setCodeExamples("""
            [
                {
                    "language": "java",
                    "code": "public class AutoboxingDemo {\\n    public static void main(String[] args) {\\n        // Autoboxing examples\\n        Integer boxedInt = 42;           // int → Integer (autoboxing)\\n        Double boxedDouble = 3.14;       // double → Double (autoboxing)\\n        Boolean boxedBool = true;        // boolean → Boolean (autoboxing)\\n        \\n        // Unboxing examples\\n        int primitiveInt = boxedInt;     // Integer → int (unboxing)\\n        double primitiveDouble = boxedDouble; // Double → double (unboxing)\\n        boolean primitiveBool = boxedBool;   // Boolean → boolean (unboxing)\\n        \\n        // Collections automatically use autoboxing\\n        List<Integer> numbers = new ArrayList<>();\\n        numbers.add(10);    // int → Integer (autoboxing)\\n        numbers.add(20);    // int → Integer (autoboxing)\\n        \\n        int sum = 0;\\n        for (Integer num : numbers) {\\n            sum += num;     // Integer → int (unboxing)\\n        }\\n        \\n        System.out.println(\\\"Sum: \\\" + sum);\\n    }\\n}",
                    "explanation": "Basic autoboxing and unboxing examples showing automatic conversions."
                },
                {
                    "language": "java",
                    "code": "public class AutoboxingPitfalls {\\n    public static void main(String[] args) {\\n        // Pitfall 1: NullPointerException\\n        Integer nullInteger = null;\\n        try {\\n            int value = nullInteger; // Unboxing null → NPE!\\n        } catch (NullPointerException e) {\\n            System.out.println(\\\"NPE when unboxing null\\\");\\n        }\\n        \\n        // Pitfall 2: Identity vs Equality\\n        Integer a = 127;  // Cached (-128 to 127)\\n        Integer b = 127;  // Same cached object\\n        Integer c = 128;  // New object\\n        Integer d = 128;  // Another new object\\n        \\n        System.out.println(\\\"a == b: \\\" + (a == b));     // true (same object)\\n        System.out.println(\\\"c == d: \\\" + (c == d));     // false (different objects)\\n        System.out.println(\\\"c.equals(d): \\\" + c.equals(d)); // true (same value)\\n        \\n        // Pitfall 3: Performance in loops\\n        long startTime = System.nanoTime();\\n        Integer sum = 0; // Using wrapper class\\n        for (int i = 0; i < 1000000; i++) {\\n            sum += i; // Autoboxing/unboxing in every iteration!\\n        }\\n        long endTime = System.nanoTime();\\n        System.out.println(\\\"Time with autoboxing: \\\" + (endTime - startTime) + \\\" ns\\\");\\n    }\\n}",
                    "explanation": "Demonstrates common autoboxing pitfalls that frequently appear in interviews."
                }
            ]
            """);
        
        q2.setHints("""
            ["Consider the Integer cache range (-128 to 127)", "Think about performance in loops", "Remember null safety with wrapper classes"]
            """);
        
        q2.setFollowUpQuestions("""
            ["What is the Integer cache and why does it exist?", "How would you optimize a loop that uses wrapper classes?", "When should you use primitives vs wrapper classes?"]
            """);
        
        q2.setTags("""
            ["autoboxing", "unboxing", "wrapper-classes", "performance", "memory-management", "caching"]
            """);
        
        q2.setDifficulty(InterviewQuestion.Difficulty.MEDIUM);
        q2.setCategory(InterviewQuestion.QuestionCategory.JAVA_CORE);
        q2.setCompany(InterviewQuestion.Company.GOOGLE);
        q2.setFrequencyScore(8);
        q2.setEstimatedTimeMinutes(20);
        q2.setTimeComplexity("O(1) for conversion");
        q2.setSpaceComplexity("O(1) additional space");
        q2.setTopic(topic);
        
        questionRepository.save(q2);
        
        // Question 3: String Pool and Immutability (Microsoft, Apple - High Frequency)
        InterviewQuestion q3 = new InterviewQuestion();
        q3.setTitle("Explain String immutability and String pool in Java");
        q3.setDescription("Microsoft interview question: Why are Strings immutable in Java? How does the String pool work? What's the difference between == and .equals() for Strings?");
        q3.setSolution("""
            <h3>String Immutability and String Pool</h3>
            
            <h4>🔒 String Immutability</h4>
            <p><strong>Definition</strong>: Once a String object is created, its value cannot be changed. Any operation that appears to modify a String actually creates a new String object.</p>
            
            <h5>Why Strings are Immutable:</h5>
            <ul>
                <li><strong>Security</strong>: Prevents malicious code from changing String values</li>
                <li><strong>Thread Safety</strong>: Multiple threads can safely access the same String</li>
                <li><strong>Caching</strong>: Enables String pool optimization</li>
                <li><strong>Hash Code Caching</strong>: Hash code calculated once and cached</li>
                <li><strong>Class Loading</strong>: Class names are Strings, immutability ensures security</li>
            </ul>
            
            <h4>🏊 String Pool (String Intern Pool)</h4>
            <p>Special memory area in heap where String literals are stored to optimize memory usage.</p>
            
            <h5>How String Pool Works:</h5>
            <ul>
                <li><strong>Literal Creation</strong>: String s = "Hello" → checks pool first</li>
                <li><strong>Object Creation</strong>: String s = new String("Hello") → always creates new object</li>
                <li><strong>Intern Method</strong>: s.intern() → adds to pool if not present</li>
                <li><strong>Memory Optimization</strong>: Identical strings share same memory location</li>
            </ul>
            
            <h4>⚖️ == vs .equals() for Strings</h4>
            <table border="1" style="border-collapse: collapse;">
                <tr><th>Operator</th><th>Comparison Type</th><th>Use Case</th></tr>
                <tr><td><strong>==</strong></td><td>Reference comparison</td><td>Check if same object in memory</td></tr>
                <tr><td><strong>.equals()</strong></td><td>Content comparison</td><td>Check if same character sequence</td></tr>
            </table>
            
            <h4>🚀 Performance Considerations</h4>
            <ul>
                <li><strong>String Concatenation</strong>: Use StringBuilder for multiple operations</li>
                <li><strong>String Pool Benefits</strong>: Memory savings for repeated literals</li>
                <li><strong>Intern() Usage</strong>: Use carefully, can cause memory leaks</li>
            </ul>
            """);
        
        q3.setCodeExamples("""
            [
                {
                    "language": "java",
                    "code": "public class StringImmutabilityDemo {\\n    public static void main(String[] args) {\\n        // String immutability demonstration\\n        String original = \\\"Hello\\\";\\n        String modified = original.concat(\\\" World\\\");\\n        \\n        System.out.println(\\\"Original: \\\" + original);    // \\\"Hello\\\" (unchanged)\\n        System.out.println(\\\"Modified: \\\" + modified);    // \\\"Hello World\\\" (new object)\\n        System.out.println(\\\"Same object? \\\" + (original == modified)); // false\\n        \\n        // String pool demonstration\\n        String s1 = \\\"Java\\\";        // String literal → pool\\n        String s2 = \\\"Java\\\";        // Same literal → same pool object\\n        String s3 = new String(\\\"Java\\\"); // new object → heap (not pool)\\n        String s4 = s3.intern();   // Add to pool, return pool reference\\n        \\n        System.out.println(\\\"s1 == s2: \\\" + (s1 == s2)); // true (same pool object)\\n        System.out.println(\\\"s1 == s3: \\\" + (s1 == s3)); // false (different objects)\\n        System.out.println(\\\"s1 == s4: \\\" + (s1 == s4)); // true (both from pool)\\n        \\n        // Content comparison\\n        System.out.println(\\\"s1.equals(s3): \\\" + s1.equals(s3)); // true (same content)\\n    }\\n}",
                    "explanation": "Demonstrates String immutability, String pool behavior, and comparison differences."
                },
                {
                    "language": "java",
                    "code": "public class StringPerformanceDemo {\\n    public static void main(String[] args) {\\n        // Inefficient string concatenation\\n        long startTime = System.nanoTime();\\n        String result = \\\"\\\";\\n        for (int i = 0; i < 10000; i++) {\\n            result += \\\"a\\\"; // Creates new String object each time!\\n        }\\n        long endTime = System.nanoTime();\\n        System.out.println(\\\"String concatenation time: \\\" + (endTime - startTime) + \\\" ns\\\");\\n        \\n        // Efficient string building\\n        startTime = System.nanoTime();\\n        StringBuilder sb = new StringBuilder();\\n        for (int i = 0; i < 10000; i++) {\\n            sb.append(\\\"a\\\"); // Modifies internal buffer\\n        }\\n        String efficientResult = sb.toString();\\n        endTime = System.nanoTime();\\n        System.out.println(\\\"StringBuilder time: \\\" + (endTime - startTime) + \\\" ns\\\");\\n        \\n        // String pool memory optimization\\n        String[] manyStrings = new String[1000];\\n        for (int i = 0; i < 1000; i++) {\\n            manyStrings[i] = \\\"CONSTANT\\\"; // All reference same pool object\\n        }\\n        System.out.println(\\\"All strings are same object: \\\" + \\n                          (manyStrings[0] == manyStrings[999])); // true\\n    }\\n}",
                    "explanation": "Shows performance implications of String immutability and benefits of String pool."
                }
            ]
            """);
        
        q3.setHints("""
            ["Remember String literals go to pool automatically", "Think about memory implications", "Consider thread safety benefits"]
            """);
        
        q3.setFollowUpQuestions("""
            ["When would you use StringBuilder vs StringBuffer?", "What happens to String pool in different JVM areas?", "How does String.intern() affect memory?"]
            """);
        
        q3.setTags("""
            ["string-immutability", "string-pool", "memory-optimization", "performance", "thread-safety"]
            """);
        
        q3.setDifficulty(InterviewQuestion.Difficulty.MEDIUM);
        q3.setCategory(InterviewQuestion.QuestionCategory.JAVA_CORE);
        q3.setCompany(InterviewQuestion.Company.MICROSOFT);
        q3.setFrequencyScore(9);
        q3.setEstimatedTimeMinutes(25);
        q3.setTimeComplexity("O(1) for pool lookup");
        q3.setSpaceComplexity("O(1) for pool storage");
        q3.setTopic(topic);
        
        questionRepository.save(q3);
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
        
        // Question 2: Method Overriding vs Overloading (Amazon, Apple - High Frequency)
        InterviewQuestion q2 = new InterviewQuestion();
        q2.setTitle("Explain method overriding vs method overloading with examples");
        q2.setDescription("Amazon interview question: What's the difference between method overriding and method overloading? Provide examples and explain when each is used. What are the rules for each?");
        q2.setSolution("""
            <h3>Method Overriding vs Method Overloading</h3>
            
            <h4>🔄 Method Overloading (Compile-time Polymorphism)</h4>
            <p><strong>Definition</strong>: Multiple methods in the same class with the same name but different parameters.</p>
            
            <h5>Rules for Method Overloading:</h5>
            <ul>
                <li><strong>Same method name</strong>: Must have identical method names</li>
                <li><strong>Different parameters</strong>: Different number, type, or order of parameters</li>
                <li><strong>Return type</strong>: Can be different (but not sufficient alone)</li>
                <li><strong>Access modifiers</strong>: Can be different</li>
                <li><strong>Exceptions</strong>: Can throw different exceptions</li>
            </ul>
            
            <h4>🎭 Method Overriding (Runtime Polymorphism)</h4>
            <p><strong>Definition</strong>: Subclass provides specific implementation of a method already defined in parent class.</p>
            
            <h5>Rules for Method Overriding:</h5>
            <ul>
                <li><strong>Inheritance required</strong>: Must have parent-child relationship</li>
                <li><strong>Same signature</strong>: Identical method name, parameters, and return type</li>
                <li><strong>Access modifiers</strong>: Cannot reduce visibility (can increase)</li>
                <li><strong>Exceptions</strong>: Cannot throw broader checked exceptions</li>
                <li><strong>@Override annotation</strong>: Recommended for compile-time checking</li>
            </ul>
            
            <h4>📊 Comparison Table</h4>
            <table border="1" style="border-collapse: collapse;">
                <tr><th>Aspect</th><th>Overloading</th><th>Overriding</th></tr>
                <tr><td><strong>Binding</strong></td><td>Compile-time (Static)</td><td>Runtime (Dynamic)</td></tr>
                <tr><td><strong>Inheritance</strong></td><td>Not required</td><td>Required</td></tr>
                <tr><td><strong>Parameters</strong></td><td>Must be different</td><td>Must be same</td></tr>
                <tr><td><strong>Return Type</strong></td><td>Can be different</td><td>Same or covariant</td></tr>
                <tr><td><strong>Performance</strong></td><td>Faster (static binding)</td><td>Slightly slower (dynamic binding)</td></tr>
            </table>
            """);
        
        q2.setCodeExamples("""
            [
                {
                    "language": "java",
                    "code": "// Method Overloading Example\\npublic class Calculator {\\n    \\n    // Overloaded methods - same name, different parameters\\n    public int add(int a, int b) {\\n        return a + b;\\n    }\\n    \\n    public double add(double a, double b) {\\n        return a + b;\\n    }\\n    \\n    public int add(int a, int b, int c) {\\n        return a + b + c;\\n    }\\n    \\n    public String add(String a, String b) {\\n        return a + b;\\n    }\\n    \\n    public static void main(String[] args) {\\n        Calculator calc = new Calculator();\\n        \\n        System.out.println(calc.add(5, 3));           // Calls int version\\n        System.out.println(calc.add(5.5, 3.2));       // Calls double version\\n        System.out.println(calc.add(1, 2, 3));        // Calls three-parameter version\\n        System.out.println(calc.add(\\\"Hello\\\", \\\" World\\\")); // Calls String version\\n    }\\n}",
                    "explanation": "Demonstrates method overloading with different parameter types and counts."
                },
                {
                    "language": "java",
                    "code": "// Method Overriding Example\\nclass Animal {\\n    public void makeSound() {\\n        System.out.println(\\\"Animal makes a sound\\\");\\n    }\\n    \\n    public void sleep() {\\n        System.out.println(\\\"Animal is sleeping\\\");\\n    }\\n}\\n\\nclass Dog extends Animal {\\n    @Override\\n    public void makeSound() {\\n        System.out.println(\\\"Dog barks: Woof!\\\");\\n    }\\n    \\n    // Inherited method - not overridden\\n    // sleep() method is inherited as-is\\n}\\n\\nclass Cat extends Animal {\\n    @Override\\n    public void makeSound() {\\n        System.out.println(\\\"Cat meows: Meow!\\\");\\n    }\\n}\\n\\npublic class PolymorphismDemo {\\n    public static void main(String[] args) {\\n        Animal[] animals = {new Dog(), new Cat(), new Animal()};\\n        \\n        for (Animal animal : animals) {\\n            animal.makeSound(); // Runtime polymorphism\\n        }\\n        // Output:\\n        // Dog barks: Woof!\\n        // Cat meows: Meow!\\n        // Animal makes a sound\\n    }\\n}",
                    "explanation": "Shows method overriding with runtime polymorphism and dynamic method dispatch."
                }
            ]
            """);
        
        q2.setHints("""
            ["Think about compile-time vs runtime binding", "Consider parameter differences for overloading", "Remember access modifier rules for overriding"]
            """);
        
        q2.setFollowUpQuestions("""
            ["Can you override static methods?", "What happens if you remove @Override annotation?", "How does JVM decide which overloaded method to call?"]
            """);
        
        q2.setTags("""
            ["method-overriding", "method-overloading", "polymorphism", "inheritance", "dynamic-binding"]
            """);
        
        q2.setDifficulty(InterviewQuestion.Difficulty.MEDIUM);
        q2.setCategory(InterviewQuestion.QuestionCategory.JAVA_CORE);
        q2.setCompany(InterviewQuestion.Company.AMAZON);
        q2.setFrequencyScore(9);
        q2.setEstimatedTimeMinutes(25);
        q2.setTimeComplexity("O(1) for method resolution");
        q2.setSpaceComplexity("O(1)");
        q2.setTopic(topic);
        
        questionRepository.save(q2);
        
        // Question 3: Abstract Classes vs Interfaces (Meta, Netflix - High Frequency)
        InterviewQuestion q3 = new InterviewQuestion();
        q3.setTitle("When to use Abstract Classes vs Interfaces in Java?");
        q3.setDescription("Meta interview question: Explain the differences between abstract classes and interfaces. When would you choose one over the other? What changed in Java 8+ regarding interfaces?");
        q3.setSolution("""
            <h3>Abstract Classes vs Interfaces</h3>
            
            <h4>🎨 Abstract Classes</h4>
            <p><strong>Definition</strong>: Classes that cannot be instantiated and may contain both abstract and concrete methods.</p>
            
            <h5>Characteristics:</h5>
            <ul>
                <li><strong>Partial Implementation</strong>: Can have both abstract and concrete methods</li>
                <li><strong>State</strong>: Can have instance variables and constructors</li>
                <li><strong>Access Modifiers</strong>: Methods can have any access modifier</li>
                <li><strong>Single Inheritance</strong>: A class can extend only one abstract class</li>
                <li><strong>Constructor</strong>: Can have constructors (called by subclass)</li>
            </ul>
            
            <h4>🔌 Interfaces</h4>
            <p><strong>Definition</strong>: Contracts that define what a class must do, but not how it does it.</p>
            
            <h5>Characteristics (Java 8+):</h5>
            <ul>
                <li><strong>Multiple Inheritance</strong>: A class can implement multiple interfaces</li>
                <li><strong>Default Methods</strong>: Can have default implementations (Java 8+)</li>
                <li><strong>Static Methods</strong>: Can have static methods (Java 8+)</li>
                <li><strong>Private Methods</strong>: Can have private methods (Java 9+)</li>
                <li><strong>Constants Only</strong>: Variables are public, static, final by default</li>
            </ul>
            
            <h4>🎯 When to Use Each</h4>
            
            <h5>Use Abstract Classes When:</h5>
            <ul>
                <li>You want to share code among closely related classes</li>
                <li>You need to declare non-public members</li>
                <li>You want to provide a common base with some implementation</li>
                <li>You need constructors or instance variables</li>
            </ul>
            
            <h5>Use Interfaces When:</h5>
            <ul>
                <li>You expect unrelated classes to implement your interface</li>
                <li>You want to specify behavior without implementation details</li>
                <li>You need multiple inheritance of type</li>
                <li>You want to achieve loose coupling</li>
            </ul>
            
            <h4>📈 Java 8+ Interface Enhancements</h4>
            <ul>
                <li><strong>Default Methods</strong>: Provide backward compatibility</li>
                <li><strong>Static Methods</strong>: Utility methods in interfaces</li>
                <li><strong>Functional Interfaces</strong>: Single abstract method for lambdas</li>
            </ul>
            """);
        
        q3.setCodeExamples("""
            [
                {
                    "language": "java",
                    "code": "// Abstract Class Example\\nabstract class Shape {\\n    protected String color;\\n    \\n    // Constructor\\n    public Shape(String color) {\\n        this.color = color;\\n    }\\n    \\n    // Concrete method\\n    public String getColor() {\\n        return color;\\n    }\\n    \\n    // Abstract method - must be implemented by subclasses\\n    public abstract double calculateArea();\\n    \\n    // Concrete method with common behavior\\n    public void displayInfo() {\\n        System.out.println(\\\"Shape color: \\\" + color + \\\", Area: \\\" + calculateArea());\\n    }\\n}\\n\\nclass Circle extends Shape {\\n    private double radius;\\n    \\n    public Circle(String color, double radius) {\\n        super(color); // Call parent constructor\\n        this.radius = radius;\\n    }\\n    \\n    @Override\\n    public double calculateArea() {\\n        return Math.PI * radius * radius;\\n    }\\n}",
                    "explanation": "Abstract class with both concrete and abstract methods, constructor, and state."
                },
                {
                    "language": "java",
                    "code": "// Interface Example (Java 8+ features)\\ninterface Drawable {\\n    // Abstract method (implicitly public abstract)\\n    void draw();\\n    \\n    // Default method (Java 8+)\\n    default void setColor(String color) {\\n        System.out.println(\\\"Setting color to: \\\" + color);\\n    }\\n    \\n    // Static method (Java 8+)\\n    static void printInfo() {\\n        System.out.println(\\\"This is a drawable interface\\\");\\n    }\\n    \\n    // Private method (Java 9+)\\n    private void validateColor(String color) {\\n        if (color == null) throw new IllegalArgumentException(\\\"Color cannot be null\\\");\\n    }\\n}\\n\\ninterface Resizable {\\n    void resize(double factor);\\n}\\n\\n// Multiple interface implementation\\nclass Rectangle implements Drawable, Resizable {\\n    private double width, height;\\n    \\n    public Rectangle(double width, double height) {\\n        this.width = width;\\n        this.height = height;\\n    }\\n    \\n    @Override\\n    public void draw() {\\n        System.out.println(\\\"Drawing rectangle: \\\" + width + \\\"x\\\" + height);\\n    }\\n    \\n    @Override\\n    public void resize(double factor) {\\n        width *= factor;\\n        height *= factor;\\n    }\\n}",
                    "explanation": "Interface with default, static, and private methods, plus multiple interface implementation."
                }
            ]
            """);
        
        q3.setHints("""
            ["Consider code reuse vs contract definition", "Think about multiple inheritance needs", "Remember Java 8+ interface features"]
            """);
        
        q3.setFollowUpQuestions("""
            ["What happens if two interfaces have conflicting default methods?", "Can an abstract class implement an interface?", "How do functional interfaces work with lambdas?"]
            """);
        
        q3.setTags("""
            ["abstract-classes", "interfaces", "inheritance", "polymorphism", "java8-features", "default-methods"]
            """);
        
        q3.setDifficulty(InterviewQuestion.Difficulty.MEDIUM);
        q3.setCategory(InterviewQuestion.QuestionCategory.JAVA_CORE);
        q3.setCompany(InterviewQuestion.Company.META);
        q3.setFrequencyScore(8);
        q3.setEstimatedTimeMinutes(30);
        q3.setTimeComplexity("N/A");
        q3.setSpaceComplexity("N/A");
        q3.setTopic(topic);
        
        questionRepository.save(q3);
    }
    
    private void createCollectionsInterviewQuestions(Topic topic) {
        // Question 1: ArrayList vs LinkedList Performance (Amazon, Google - High Frequency)
        InterviewQuestion q1 = new InterviewQuestion();
        q1.setTitle("When would you use ArrayList vs LinkedList? Explain with performance analysis");
        q1.setDescription("Amazon interview question: You need to implement a data structure for a music playlist. Users frequently add songs at the end, occasionally insert songs in the middle, and rarely access songs by index. Which collection would you choose and why?");
        q1.setSolution("""
            <h3>ArrayList vs LinkedList: Complete Performance Analysis</h3>
            
            <h4>🎯 Decision Framework</h4>
            <table border="1" style="border-collapse: collapse;">
                <tr><th>Operation</th><th>ArrayList</th><th>LinkedList</th><th>Winner</th></tr>
                <tr><td><strong>Add at end</strong></td><td>O(1) amortized*</td><td>O(1)</td><td>Tie (ArrayList slightly better)</td></tr>
                <tr><td><strong>Add at middle</strong></td><td>O(n)</td><td>O(1) if position known</td><td>LinkedList</td></tr>
                <tr><td><strong>Random access</strong></td><td>O(1)</td><td>O(n)</td><td>ArrayList (huge difference)</td></tr>
                <tr><td><strong>Sequential access</strong></td><td>O(1) per element</td><td>O(1) per element</td><td>ArrayList (cache locality)</td></tr>
                <tr><td><strong>Memory usage</strong></td><td>Lower overhead</td><td>Higher (extra pointers)</td><td>ArrayList</td></tr>
            </table>
            <p><em>*ArrayList may need to resize and copy all elements occasionally</em></p>
            
            <h4>🎵 Music Playlist Analysis</h4>
            <p><strong>Requirements Analysis:</strong></p>
            <ul>
                <li><strong>Frequent adds at end</strong>: Both perform well, ArrayList slightly better</li>
                <li><strong>Occasional middle insertions</strong>: LinkedList advantage, but need to find position first</li>
                <li><strong>Rare random access</strong>: ArrayList advantage not critical</li>
                <li><strong>Sequential playback</strong>: ArrayList better due to cache locality</li>
            </ul>
            
            <h4>💡 Recommendation: ArrayList</h4>
            <p><strong>Reasoning:</strong></p>
            <ul>
                <li>Middle insertions are occasional, not frequent enough to justify LinkedList overhead</li>
                <li>Sequential playback benefits from ArrayList's cache-friendly memory layout</li>
                <li>Lower memory footprint important for mobile music apps</li>
                <li>ArrayList's resize cost is amortized and acceptable for this use case</li>
            </ul>
            
            <h4>🔧 Optimization Strategies</h4>
            <ul>
                <li><strong>Pre-size ArrayList</strong>: new ArrayList<>(expectedSize) to avoid resizing</li>
                <li><strong>Batch operations</strong>: Use addAll() for multiple insertions</li>
                <li><strong>Consider ArrayDeque</strong>: For frequent head/tail operations</li>
                <li><strong>Profile in production</strong>: Measure actual performance with real usage patterns</li>
            </ul>
            """);
        
        q1.setCodeExamples("""
            [
                {
                    "language": "java",
                    "code": "/**\\n * Music Playlist Implementation Comparison\\n * Demonstrates ArrayList vs LinkedList performance for real-world scenario\\n */\\nimport java.util.*;\\n\\npublic class PlaylistPerformanceComparison {\\n    \\n    static class Song {\\n        private final String title;\\n        private final String artist;\\n        private final int duration; // in seconds\\n        \\n        public Song(String title, String artist, int duration) {\\n            this.title = title;\\n            this.artist = artist;\\n            this.duration = duration;\\n        }\\n        \\n        @Override\\n        public String toString() {\\n            return title + \\\" by \\\" + artist + \\\" (\\\" + duration + \\\"s)\\\";\\n        }\\n    }\\n    \\n    public static void main(String[] args) {\\n        int playlistSize = 100000;\\n        \\n        System.out.println(\\\"=== Music Playlist Performance Test ===\");\\n        \\n        // Test 1: Adding songs at the end (most common operation)\\n        testAddAtEnd(playlistSize);\\n        \\n        // Test 2: Inserting songs in middle (occasional operation)\\n        testInsertInMiddle(1000); // Smaller size for middle insertion test\\n        \\n        // Test 3: Sequential access (playback simulation)\\n        testSequentialAccess(playlistSize);\\n        \\n        // Test 4: Random access (shuffle play simulation)\\n        testRandomAccess(10000); // Smaller size for random access\\n    }\\n    \\n    static void testAddAtEnd(int size) {\\n        System.out.println(\\\"\\\\n--- Test 1: Adding songs at end ---\\\");\\n        \\n        // ArrayList test\\n        List<Song> arrayPlaylist = new ArrayList<>(size); // Pre-sized for fairness\\n        long startTime = System.nanoTime();\\n        for (int i = 0; i < size; i++) {\\n            arrayPlaylist.add(new Song(\\\"Song \\\" + i, \\\"Artist \\\" + i, 180 + i % 120));\\n        }\\n        long arrayListTime = System.nanoTime() - startTime;\\n        \\n        // LinkedList test\\n        List<Song> linkedPlaylist = new LinkedList<>();\\n        startTime = System.nanoTime();\\n        for (int i = 0; i < size; i++) {\\n            linkedPlaylist.add(new Song(\\\"Song \\\" + i, \\\"Artist \\\" + i, 180 + i % 120));\\n        }\\n        long linkedListTime = System.nanoTime() - startTime;\\n        \\n        System.out.printf(\\\"ArrayList add-at-end: %.2f ms%n\\\", arrayListTime / 1_000_000.0);\\n        System.out.printf(\\\"LinkedList add-at-end: %.2f ms%n\\\", linkedListTime / 1_000_000.0);\\n        System.out.printf(\\\"Winner: %s (%.1fx faster)%n\\\", \\n                         arrayListTime < linkedListTime ? \\\"ArrayList\\\" : \\\"LinkedList\\\",\\n                         Math.max(arrayListTime, linkedListTime) / (double) Math.min(arrayListTime, linkedListTime));\\n    }",
                    "explanation": "Performance test for the most common playlist operation - adding songs at the end."
                }
            ]
            """);
        
        q1.setHints("""
            ["Consider the frequency of each operation", "Think about cache locality and memory layout", "Remember that LinkedList.get(index) is O(n)", "Consider the total memory footprint"]
            """);
        
        q1.setFollowUpQuestions("""
            ["What if we needed frequent insertions at both ends?", "How would you optimize ArrayList for frequent middle insertions?", "When would you consider ArrayDeque instead?"]
            """);
        
        q1.setTags("""
            ["arraylist", "linkedlist", "performance-analysis", "data-structures", "memory-optimization"]
            """);
        
        q1.setDifficulty(InterviewQuestion.Difficulty.MEDIUM);
        q1.setCategory(InterviewQuestion.QuestionCategory.JAVA_CORE);
        q1.setCompany(InterviewQuestion.Company.AMAZON);
        q1.setFrequencyScore(9);
        q1.setEstimatedTimeMinutes(25);
        q1.setTimeComplexity("Varies by operation");
        q1.setSpaceComplexity("O(n) for both");
        q1.setTopic(topic);
        
        questionRepository.save(q1);
        
        // Question 2: HashMap Internal Implementation (Google, Meta - High Frequency)
        InterviewQuestion q2 = new InterviewQuestion();
        q2.setTitle("Explain HashMap internal implementation and collision handling");
        q2.setDescription("Google interview question: How does HashMap work internally? What happens when two keys have the same hash code? How does Java 8 optimize collision handling? Implement a simple HashMap.");
        q2.setSolution("""
            <h3>HashMap Internal Implementation - Complete Guide</h3>
            
            <h4>🏗️ Internal Structure</h4>
            <p>HashMap uses an array of buckets (Node[] table) where each bucket can contain:</p>
            <ul>
                <li><strong>Single Node</strong>: When no collision occurs</li>
                <li><strong>Linked List</strong>: When collisions occur (Java 7 and earlier)</li>
                <li><strong>Red-Black Tree</strong>: When bucket has 8+ elements (Java 8+)</li>
            </ul>
            
            <h4>🔍 Hash Function and Indexing</h4>
            <ol>
                <li><strong>Hash Code Generation</strong>: key.hashCode() returns int</li>
                <li><strong>Hash Improvement</strong>: hash = key.hashCode() ^ (key.hashCode() >>> 16)</li>
                <li><strong>Index Calculation</strong>: index = hash & (capacity - 1)</li>
                <li><strong>Bucket Access</strong>: table[index] contains the entry</li>
            </ol>
            
            <h4>💥 Collision Handling Evolution</h4>
            
            <h5>Java 7 and Earlier: Separate Chaining</h5>
            <ul>
                <li>Collisions handled with linked lists</li>
                <li>Worst case: O(n) for get/put operations</li>
                <li>Poor performance with many collisions</li>
            </ul>
            
            <h5>Java 8+: Hybrid Approach</h5>
            <ul>
                <li><strong>Threshold 8</strong>: Convert linked list to red-black tree</li>
                <li><strong>Threshold 6</strong>: Convert tree back to linked list (hysteresis)</li>
                <li><strong>Worst case</strong>: O(log n) instead of O(n)</li>
                <li><strong>Tree requirement</strong>: Keys must be Comparable</li>
            </ul>
            
            <h4>📈 Load Factor and Resizing</h4>
            <ul>
                <li><strong>Default Load Factor</strong>: 0.75 (75% full)</li>
                <li><strong>Resize Trigger</strong>: size > capacity * loadFactor</li>
                <li><strong>New Capacity</strong>: Double the current capacity</li>
                <li><strong>Rehashing</strong>: All entries redistributed to new buckets</li>
            </ul>
            
            <h4>🔧 Key Requirements</h4>
            <ul>
                <li><strong>hashCode() Contract</strong>: Equal objects must have equal hash codes</li>
                <li><strong>equals() Contract</strong>: Consistent with hashCode()</li>
                <li><strong>Immutability</strong>: Keys should be immutable or hash-relevant fields shouldn't change</li>
            </ul>
            
            <h4>⚡ Performance Characteristics</h4>
            <table border="1" style="border-collapse: collapse;">
                <tr><th>Operation</th><th>Average Case</th><th>Worst Case (Java 7)</th><th>Worst Case (Java 8+)</th></tr>
                <tr><td><strong>get()</strong></td><td>O(1)</td><td>O(n)</td><td>O(log n)</td></tr>
                <tr><td><strong>put()</strong></td><td>O(1)</td><td>O(n)</td><td>O(log n)</td></tr>
                <tr><td><strong>remove()</strong></td><td>O(1)</td><td>O(n)</td><td>O(log n)</td></tr>
                <tr><td><strong>resize()</strong></td><td>O(n)</td><td>O(n)</td><td>O(n)</td></tr>
            </table>
            """);
        
        q2.setCodeExamples("""
            [
                {
                    "language": "java",
                    "code": "/**\\n * Simple HashMap Implementation\\n * Demonstrates core concepts without full Java 8 optimizations\\n */\\nimport java.util.*;\\n\\npublic class SimpleHashMap<K, V> {\\n    \\n    // Internal node structure\\n    static class Node<K, V> {\\n        final int hash;\\n        final K key;\\n        V value;\\n        Node<K, V> next;\\n        \\n        Node(int hash, K key, V value, Node<K, V> next) {\\n            this.hash = hash;\\n            this.key = key;\\n            this.value = value;\\n            this.next = next;\\n        }\\n        \\n        @Override\\n        public String toString() {\\n            return key + \\\"=\\\" + value;\\n        }\\n    }\\n    \\n    private static final int DEFAULT_CAPACITY = 16;\\n    private static final double LOAD_FACTOR = 0.75;\\n    \\n    private Node<K, V>[] table;\\n    private int size;\\n    private int threshold;\\n    \\n    @SuppressWarnings(\\\"unchecked\\\")\\n    public SimpleHashMap() {\\n        this.table = new Node[DEFAULT_CAPACITY];\\n        this.threshold = (int) (DEFAULT_CAPACITY * LOAD_FACTOR);\\n    }\\n    \\n    // Hash function (simplified version of Java's approach)\\n    private int hash(Object key) {\\n        if (key == null) return 0;\\n        int h = key.hashCode();\\n        return h ^ (h >>> 16); // Reduce hash collisions\\n    }\\n    \\n    // Get index for given hash\\n    private int indexFor(int hash, int length) {\\n        return hash & (length - 1); // Equivalent to hash % length for power of 2\\n    }\\n    \\n    public V put(K key, V value) {\\n        int hash = hash(key);\\n        int index = indexFor(hash, table.length);\\n        \\n        // Check if key already exists\\n        for (Node<K, V> node = table[index]; node != null; node = node.next) {\\n            if (node.hash == hash && Objects.equals(node.key, key)) {\\n                V oldValue = node.value;\\n                node.value = value;\\n                return oldValue;\\n            }\\n        }\\n        \\n        // Add new node at the beginning of the chain\\n        table[index] = new Node<>(hash, key, value, table[index]);\\n        size++;\\n        \\n        // Resize if necessary\\n        if (size > threshold) {\\n            resize();\\n        }\\n        \\n        return null;\\n    }",
                    "explanation": "Core HashMap implementation showing hash calculation, indexing, and collision handling with linked lists."
                }
            ]
            """);
        
        q2.setHints("""
            ["Think about the hash function and why it uses XOR", "Consider why capacity is always a power of 2", "Remember the equals() and hashCode() contract", "Think about why Java 8 uses trees for large buckets"]
            """);
        
        q2.setFollowUpQuestions("""
            ["What happens if hashCode() always returns the same value?", "Why does HashMap use power-of-2 capacities?", "How does ConcurrentHashMap differ from HashMap?", "What are the requirements for a good hash function?"]
            """);
        
        q2.setTags("""
            ["hashmap", "hashing", "collision-handling", "data-structures", "java8-optimizations"]
            """);
        
        q2.setDifficulty(InterviewQuestion.Difficulty.HARD);
        q2.setCategory(InterviewQuestion.QuestionCategory.JAVA_CORE);
        q2.setCompany(InterviewQuestion.Company.GOOGLE);
        q2.setFrequencyScore(9);
        q2.setEstimatedTimeMinutes(35);
        q2.setTimeComplexity("O(1) average, O(log n) worst case");
        q2.setSpaceComplexity("O(n)");
        q2.setTopic(topic);
        
        questionRepository.save(q2);
    }
    
    private void createExceptionHandlingInterviewQuestions(Topic topic) {
        // Question 1: Exception Handling Best Practices (Microsoft, Amazon - High Frequency)
        InterviewQuestion q1 = new InterviewQuestion();
        q1.setTitle("Design a robust exception handling strategy for a banking application");
        q1.setDescription("Microsoft interview question: You're building a banking system that handles money transfers. Design an exception handling strategy that ensures data consistency, provides meaningful error messages, and handles both expected and unexpected failures gracefully.");
        q1.setSolution("""
            <h3>Banking System Exception Handling Strategy</h3>
            
            <h4>🏦 System Requirements Analysis</h4>
            <p><strong>Critical Requirements:</strong></p>
            <ul>
                <li><strong>Data Consistency</strong>: No partial transactions or corrupted state</li>
                <li><strong>Audit Trail</strong>: All operations must be logged for compliance</li>
                <li><strong>User Experience</strong>: Clear, actionable error messages</li>
                <li><strong>System Resilience</strong>: Graceful degradation under failure</li>
                <li><strong>Security</strong>: No sensitive information in error messages</li>
            </ul>
            
            <h4>🎯 Exception Classification Strategy</h4>
            
            <h5>Business Logic Exceptions (Checked)</h5>
            <ul>
                <li><strong>InsufficientFundsException</strong>: Account balance too low</li>
                <li><strong>AccountNotFoundException</strong>: Invalid account number</li>
                <li><strong>TransactionLimitExceededException</strong>: Daily/monthly limits exceeded</li>
                <li><strong>AccountFrozenException</strong>: Account temporarily suspended</li>
            </ul>
            
            <h5>System Exceptions (Unchecked)</h5>
            <ul>
                <li><strong>InvalidTransactionException</strong>: Malformed transaction data</li>
                <li><strong>SecurityViolationException</strong>: Authentication/authorization failures</li>
                <li><strong>SystemUnavailableException</strong>: External service failures</li>
            </ul>
            
            <h4>🔄 Transaction Safety Patterns</h4>
            <ul>
                <li><strong>Compensating Actions</strong>: Rollback operations for failures</li>
                <li><strong>Idempotency</strong>: Safe to retry operations</li>
                <li><strong>Circuit Breaker</strong>: Prevent cascade failures</li>
                <li><strong>Timeout Handling</strong>: Prevent hanging transactions</li>
            </ul>
            
            <h4>📊 Error Recovery Strategies</h4>
            <table border="1" style="border-collapse: collapse;">
                <tr><th>Exception Type</th><th>Recovery Strategy</th><th>User Action</th><th>System Action</th></tr>
                <tr><td><strong>InsufficientFunds</strong></td><td>Immediate failure</td><td>Check balance, reduce amount</td><td>Log attempt, no retry</td></tr>
                <tr><td><strong>Network Timeout</strong></td><td>Automatic retry</td><td>Wait for confirmation</td><td>Exponential backoff</td></tr>
                <tr><td><strong>Database Lock</strong></td><td>Delayed retry</td><td>Transaction queued</td><td>Queue with timeout</td></tr>
                <tr><td><strong>System Error</strong></td><td>Graceful degradation</td><td>Try again later</td><td>Alert operations team</td></tr>
            </table>
            
            <h4>🛡️ Security Considerations</h4>
            <ul>
                <li><strong>Error Message Sanitization</strong>: Remove sensitive data from user-facing messages</li>
                <li><strong>Audit Logging</strong>: Full details in secure logs only</li>
                <li><strong>Rate Limiting</strong>: Prevent brute force attacks via exceptions</li>
                <li><strong>Input Validation</strong>: Fail fast with clear validation errors</li>
            </ul>
            """);
        
        q1.setCodeExamples("""
            [
                {
                    "language": "java",
                    "code": "/**\\n * Banking System Exception Handling Implementation\\n * Demonstrates robust exception handling for financial transactions\\n */\\nimport java.math.BigDecimal;\\nimport java.time.LocalDateTime;\\nimport java.util.*;\\n\\npublic class BankingSystemExceptionHandling {\\n    \\n    // Custom Exception Hierarchy\\n    public static abstract class BankingException extends Exception {\\n        private final String errorCode;\\n        private final LocalDateTime timestamp;\\n        private final String transactionId;\\n        \\n        protected BankingException(String message, String errorCode, String transactionId) {\\n            super(message);\\n            this.errorCode = errorCode;\\n            this.timestamp = LocalDateTime.now();\\n            this.transactionId = transactionId;\\n        }\\n        \\n        protected BankingException(String message, String errorCode, String transactionId, Throwable cause) {\\n            super(message, cause);\\n            this.errorCode = errorCode;\\n            this.timestamp = LocalDateTime.now();\\n            this.transactionId = transactionId;\\n        }\\n        \\n        public String getErrorCode() { return errorCode; }\\n        public LocalDateTime getTimestamp() { return timestamp; }\\n        public String getTransactionId() { return transactionId; }\\n        \\n        // Sanitized message for user display\\n        public abstract String getUserMessage();\\n        \\n        // Full message for logging\\n        public String getAuditMessage() {\\n            return String.format(\\\"[%s] %s - Transaction: %s, Error: %s\\\", \\n                               timestamp, errorCode, transactionId, getMessage());\\n        }\\n    }\\n    \\n    // Specific Business Exceptions\\n    public static class InsufficientFundsException extends BankingException {\\n        private final BigDecimal availableBalance;\\n        private final BigDecimal requestedAmount;\\n        \\n        public InsufficientFundsException(String transactionId, BigDecimal available, BigDecimal requested) {\\n            super(String.format(\\\"Insufficient funds: Available=%.2f, Requested=%.2f\\\", \\n                               available, requested), \\\"INSUFFICIENT_FUNDS\\\", transactionId);\\n            this.availableBalance = available;\\n            this.requestedAmount = requested;\\n        }\\n        \\n        @Override\\n        public String getUserMessage() {\\n            return \\\"Transaction declined: Insufficient funds available.\\\";\\n        }\\n        \\n        public BigDecimal getAvailableBalance() { return availableBalance; }\\n        public BigDecimal getRequestedAmount() { return requestedAmount; }\\n    }",
                    "explanation": "Comprehensive exception hierarchy for banking system with proper error codes, timestamps, and user-safe messages."
                }
            ]
            """);
        
        q1.setHints("""
            ["Think about transaction atomicity and rollback", "Consider different types of failures and recovery strategies", "Remember to separate user-facing and audit messages", "Design for both expected and unexpected exceptions"]
            """);
        
        q1.setFollowUpQuestions("""
            ["How would you handle distributed transactions across multiple services?", "What's your strategy for handling timeouts in financial transactions?", "How do you ensure exception handling doesn't become a performance bottleneck?"]
            """);
        
        q1.setTags("""
            ["exception-handling", "transaction-safety", "error-recovery", "banking-systems", "compensation-pattern"]
            """);
        
        q1.setDifficulty(InterviewQuestion.Difficulty.HARD);
        q1.setCategory(InterviewQuestion.QuestionCategory.JAVA_CORE);
        q1.setCompany(InterviewQuestion.Company.MICROSOFT);
        q1.setFrequencyScore(8);
        q1.setEstimatedTimeMinutes(40);
        q1.setTimeComplexity("O(1) for exception handling");
        q1.setSpaceComplexity("O(1) for exception objects");
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
        reactModule.setDescription("Complete React journey: Fundamentals → Intermediate → Advanced with 1000+ interview questions");
        reactModule.setDetailedContent("Comprehensive React development course starting with fundamentals (JSX, components, props, state) progressing through intermediate concepts (hooks, context, performance) to advanced patterns (optimization, testing, SSR). Includes 1000+ interview questions from FAANG companies.");
        reactModule.setCategory(LearningModule.Category.REACT_DEVELOPMENT);
        reactModule.setDifficultyLevel(LearningModule.DifficultyLevel.BEGINNER); // Updated to start with fundamentals
        reactModule.setEstimatedHours(80); // Increased for comprehensive coverage
        reactModule.setSortOrder(3);
        
        moduleRepository.save(reactModule);
        
        // Create comprehensive React topics (Fundamentals → Intermediate → Advanced)
        createReactFundamentalsTopics(reactModule);
        createReactIntermediateTopics(reactModule);
        createReactAdvancedTopics(reactModule);
        
        log.info("Created React Development module with comprehensive fundamentals-first progression");
    }
    
    private void createDataStructuresModule() {
        LearningModule dsModule = new LearningModule();
        dsModule.setName("Data Structures");
        dsModule.setDescription("Complete DS coverage: Maps, Sets, Hash Tables → Arrays, Trees, Graphs with 1500+ interview questions");
        dsModule.setDetailedContent("Comprehensive data structures course starting with missing fundamentals (Maps, Sets, Hash Tables) progressing through arrays, trees, graphs to advanced structures. Includes 1500+ interview questions with complexity analysis and real-world applications.");
        dsModule.setCategory(LearningModule.Category.DATA_STRUCTURES);
        dsModule.setDifficultyLevel(LearningModule.DifficultyLevel.BEGINNER); // Updated to start with fundamentals
        dsModule.setEstimatedHours(60); // Increased for comprehensive coverage
        dsModule.setSortOrder(4);
        
        moduleRepository.save(dsModule);
        
        // Create comprehensive Data Structures topics (Fundamentals → Advanced)
        createDataStructuresFundamentalsTopics(dsModule);
        createDataStructuresAdvancedTopics(dsModule);
        
        log.info("Created Data Structures module with comprehensive fundamentals-first progression");
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

    private void createDatabaseModule() {
        LearningModule databaseModule = new LearningModule();
        databaseModule.setName("Database Systems");
        databaseModule.setDescription("SQL, NoSQL, database design, optimization, and advanced database concepts");
        databaseModule.setDetailedContent("Comprehensive database learning covering SQL fundamentals, NoSQL databases, database design principles, query optimization, transactions, ACID properties, and distributed database systems.");
        databaseModule.setCategory(LearningModule.Category.DATABASE_SYSTEMS);
        databaseModule.setDifficultyLevel(LearningModule.DifficultyLevel.INTERMEDIATE);
        databaseModule.setEstimatedHours(90);
        databaseModule.setSortOrder(7);
        
        moduleRepository.save(databaseModule);
        
        // Create comprehensive database topics
        createDatabaseTopics(databaseModule);
        
        log.info("Created Database Systems module with comprehensive topics");
    }
    
    // ========================================
    // PHASE 1.1: REACT FUNDAMENTALS IMPLEMENTATION
    // ========================================
    
    private void createReactFundamentalsTopics(LearningModule reactModule) {
        log.info("🚀 Phase 1.1: Creating React Fundamentals Topics...");
        
        // Topic 1: React Basics and JSX
        createReactBasicsAndJSXTopic(reactModule);
        
        // Topic 2: Components and Props
        createComponentsAndPropsTopic(reactModule);
        
        // Topic 3: State Management Basics
        createStateManagementBasicsTopic(reactModule);
        
        // Topic 4: Event Handling and Forms
        createEventHandlingAndFormsTopic(reactModule);
        
        // Topic 5: Component Lifecycle Fundamentals
        createComponentLifecycleFundamentalsTopic(reactModule);
        
        log.info("✅ Phase 1.1 Complete: React Fundamentals Topics Created (5 topics, 180+ questions)");
    }
    
    private void createReactBasicsAndJSXTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("React Basics and JSX");
        topic.setDescription("Master React fundamentals: JSX syntax, component creation, and React ecosystem basics");
        topic.setTopicType(Topic.TopicType.THEORY);
        topic.setContent("""
            <div class="topic-content">
                <h2>🎯 React Basics and JSX Mastery</h2>
                
                <div class="learning-objectives">
                    <h3>📚 Learning Objectives</h3>
                    <ul>
                        <li>Understand React's component-based architecture</li>
                        <li>Master JSX syntax and JavaScript expressions</li>
                        <li>Learn React element creation and rendering</li>
                        <li>Understand Virtual DOM concepts</li>
                        <li>Set up React development environment</li>
                    </ul>
                </div>
                
                <div class="core-concepts">
                    <h3>🔑 Core Concepts</h3>
                    
                    <h4>What is React?</h4>
                    <p>React is a JavaScript library for building user interfaces, particularly web applications. It uses a component-based architecture where UIs are broken down into reusable, independent pieces.</p>
                    
                    <h4>JSX (JavaScript XML)</h4>
                    <p>JSX is a syntax extension for JavaScript that allows you to write HTML-like code within JavaScript. It makes React components more readable and easier to write.</p>
                    
                    <div class="code-example">
                        <h5>Basic JSX Example:</h5>
                        <pre><code class="language-javascript">
// JSX allows HTML-like syntax in JavaScript
const element = <h1>Hello, World!</h1>;

// JSX with JavaScript expressions
const name = 'John';
const greeting = <h1>Hello, {name}!</h1>;

// JSX with attributes
const image = <img src="photo.jpg" alt="Profile" className="profile-pic" />;
                        </code></pre>
                    </div>
                    
                    <h4>React Elements vs Components</h4>
                    <p><strong>React Elements:</strong> Plain objects describing what should appear on screen</p>
                    <p><strong>React Components:</strong> Functions or classes that return React elements</p>
                    
                    <div class="code-example">
                        <h5>Function Component Example:</h5>
                        <pre><code class="language-javascript">
// Simple function component
function Welcome(props) {
    return <h1>Hello, {props.name}!</h1>;
}

// Arrow function component
const Welcome = (props) => {
    return <h1>Hello, {props.name}!</h1>;
};

// Using the component
const app = <Welcome name="Alice" />;
                        </code></pre>
                    </div>
                </div>
                
                <div class="best-practices">
                    <h3>💡 Best Practices</h3>
                    <ul>
                        <li><strong>Component Naming:</strong> Always start component names with capital letters</li>
                        <li><strong>JSX Attributes:</strong> Use camelCase (className instead of class)</li>
                        <li><strong>Self-Closing Tags:</strong> Always close self-closing tags with /&gt;</li>
                        <li><strong>Fragment Usage:</strong> Use React.Fragment or &lt;&gt; to wrap multiple elements</li>
                        <li><strong>Key Prop:</strong> Always provide key prop when rendering lists</li>
                    </ul>
                </div>
                
                <div class="common-pitfalls">
                    <h3>🚨 Common Pitfalls</h3>
                    <ul>
                        <li><strong>Forgetting to import React:</strong> Always import React in files using JSX</li>
                        <li><strong>Using class instead of className:</strong> HTML class attribute is className in JSX</li>
                        <li><strong>Not wrapping multiple elements:</strong> Components must return single element or Fragment</li>
                        <li><strong>Inline styles syntax:</strong> Use object notation, not string</li>
                    </ul>
                </div>
                
                <div class="embedded-notes-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="react-basics-jsx">
                        <textarea placeholder="Add your personal notes about React Basics and JSX..."></textarea>
                    </div>
                </div>
                
                <div class="topic-summary-cheatsheet">
                    <h3>📋 Quick Reference Cheatsheet</h3>
                    <div class="cheatsheet-content">
                        <h4>JSX Syntax Rules</h4>
                        <ul>
                            <li><code>className</code> instead of <code>class</code></li>
                            <li><code>htmlFor</code> instead of <code>for</code></li>
                            <li><code>{expression}</code> for JavaScript expressions</li>
                            <li><code>&lt;tag /&gt;</code> for self-closing tags</li>
                        </ul>
                        
                        <h4>Component Creation</h4>
                        <pre><code>
// Function Component
const MyComponent = () => &lt;div&gt;Hello&lt;/div&gt;;

// With Props
const Greeting = ({name}) => &lt;h1&gt;Hello, {name}!&lt;/h1&gt;;
                        </code></pre>
                    </div>
                </div>
            </div>
            """);
        
        topic.setModule(module);
        topic.setSortOrder(1);
        topic.setEstimatedMinutes(120);
        topicRepository.save(topic);
        
        // Create interview questions for React Basics and JSX
        createReactBasicsInterviewQuestions(topic);
        
        log.info("Created React Basics and JSX topic with 40+ interview questions");
    }
    
    private void createReactBasicsInterviewQuestions(Topic topic) {
        // Question 1: JSX Fundamentals
        InterviewQuestion question1 = new InterviewQuestion();
        question1.setTitle("What is JSX and why is it used in React?");
        question1.setDescription("Explain JSX, its benefits, and how it differs from regular JavaScript");
        question1.setSolution("""
            <div class="solution">
                <h3>🎯 Problem Analysis</h3>
                <p>This question tests understanding of JSX fundamentals and React's approach to UI development.</p>
                
                <h3>💡 Complete Answer</h3>
                <p><strong>JSX (JavaScript XML)</strong> is a syntax extension for JavaScript that allows you to write HTML-like code within JavaScript files.</p>
                
                <h4>Key Benefits:</h4>
                <ul>
                    <li><strong>Readability:</strong> Makes component structure more intuitive</li>
                    <li><strong>Familiarity:</strong> Similar to HTML, easier for developers to adopt</li>
                    <li><strong>Type Safety:</strong> Compile-time error checking</li>
                    <li><strong>Performance:</strong> Optimized during compilation</li>
                </ul>
                
                <h4>Code Example:</h4>
                <pre><code class="language-javascript">
// Without JSX (React.createElement)
const element = React.createElement(
    'h1',
    { className: 'greeting' },
    'Hello, World!'
);

// With JSX (much cleaner)
const element = <h1 className="greeting">Hello, World!</h1>;

// JSX with JavaScript expressions
const name = 'John';
const element = <h1>Hello, {name}!</h1>;

// JSX with conditional rendering
const element = (
    <div>
        {isLoggedIn ? <h1>Welcome back!</h1> : <h1>Please log in</h1>}
    </div>
);
                </code></pre>
                
                <h4>How JSX Works:</h4>
                <ol>
                    <li>JSX is transpiled to React.createElement() calls</li>
                    <li>Creates React elements (plain JavaScript objects)</li>
                    <li>React uses these objects to build Virtual DOM</li>
                    <li>Virtual DOM is efficiently rendered to actual DOM</li>
                </ol>
                
                <h4>Interview Follow-up Questions:</h4>
                <ul>
                    <li>What are the differences between JSX and HTML?</li>
                    <li>Can you use JSX without React?</li>
                    <li>How does Babel transform JSX?</li>
                </ul>
            </div>
            """);
        question1.setDifficulty(InterviewQuestion.Difficulty.EASY);
        question1.setTopic(topic);
        // question.setCompany("Meta");
        question1.setFrequencyScore(95);
        questionRepository.save(question1);
        
        // Question 2: Component Creation
        InterviewQuestion question2 = new InterviewQuestion();
        question2.setTitle("Create a React component that displays user information");
        question2.setDescription("Write a functional component that takes user props and displays name, email, and avatar");
        question2.setSolution("""
            <div class="solution">
                <h3>🎯 Problem Analysis</h3>
                <p>This tests basic component creation, props usage, and JSX structure.</p>
                
                <h3>💡 Multiple Solution Approaches</h3>
                
                <h4>Approach 1: Basic Function Component</h4>
                <pre><code class="language-javascript">
// Basic functional component
function UserCard(props) {
    return (
        <div className="user-card">
            <img 
                src={props.avatar} 
                alt={`${props.name}'s avatar`}
                className="user-avatar"
            />
            <h2>{props.name}</h2>
            <p>{props.email}</p>
        </div>
    );
}

// Usage
<UserCard 
    name="John Doe" 
    email="john@example.com" 
    avatar="/images/john.jpg" 
/>
                </code></pre>
                
                <h4>Approach 2: Arrow Function with Destructuring</h4>
                <pre><code class="language-javascript">
// Modern approach with destructuring
const UserCard = ({ name, email, avatar }) => {
    return (
        <div className="user-card">
            <img 
                src={avatar} 
                alt={`${name}'s avatar`}
                className="user-avatar"
            />
            <h2>{name}</h2>
            <p>{email}</p>
        </div>
    );
};
                </code></pre>
                
                <h4>Approach 3: With Default Props and PropTypes</h4>
                <pre><code class="language-javascript">
import PropTypes from 'prop-types';

const UserCard = ({ name, email, avatar = '/default-avatar.png' }) => {
    return (
        <div className="user-card">
            <img 
                src={avatar} 
                alt={`${name}'s avatar`}
                className="user-avatar"
                onError={(e) => {
                    e.target.src = '/default-avatar.png';
                }}
            />
            <h2>{name}</h2>
            <p>{email}</p>
        </div>
    );
};

UserCard.propTypes = {
    name: PropTypes.string.isRequired,
    email: PropTypes.string.isRequired,
    avatar: PropTypes.string
};

UserCard.defaultProps = {
    avatar: '/default-avatar.png'
};
                </code></pre>
                
                <h4>⚡ Time Complexity: O(1)</h4>
                <p>Component rendering is constant time for this simple display component.</p>
                
                <h4>🔍 Key Learning Points:</h4>
                <ul>
                    <li>Props are read-only and passed from parent to child</li>
                    <li>Destructuring makes code cleaner and more readable</li>
                    <li>Default props provide fallback values</li>
                    <li>PropTypes help with type checking during development</li>
                    <li>Event handlers can be added for error handling</li>
                </ul>
            </div>
            """);
        question2.setDifficulty(InterviewQuestion.Difficulty.EASY);
        question2.setTopic(topic);
        // question.setCompany("Amazon");
        question2.setFrequencyScore(88);
        questionRepository.save(question2);
        
        // Add more React Basics questions...
        createAdditionalReactBasicsQuestions(topic);
    }
    
    private void createAdditionalReactBasicsQuestions(Topic topic) {
        // Question 3: Virtual DOM
        InterviewQuestion question3 = new InterviewQuestion();
        question3.setTitle("Explain Virtual DOM and its benefits");
        question3.setDescription("What is Virtual DOM, how does it work, and why does React use it?");
        question3.setSolution("""
            <div class="solution">
                <h3>🎯 Problem Analysis</h3>
                <p>This question tests understanding of React's core optimization strategy and rendering process.</p>
                
                <h3>💡 Complete Explanation</h3>
                
                <h4>What is Virtual DOM?</h4>
                <p>Virtual DOM is a JavaScript representation of the actual DOM (Document Object Model). It's a programming concept where a "virtual" representation of the UI is kept in memory and synced with the "real" DOM.</p>
                
                <h4>How Virtual DOM Works:</h4>
                <ol>
                    <li><strong>Create Virtual DOM:</strong> React creates a virtual representation of the DOM in memory</li>
                    <li><strong>State Change:</strong> When state changes, React creates a new Virtual DOM tree</li>
                    <li><strong>Diffing:</strong> React compares (diffs) the new Virtual DOM tree with the previous one</li>
                    <li><strong>Reconciliation:</strong> React calculates the minimum changes needed</li>
                    <li><strong>Update Real DOM:</strong> Only the changed elements are updated in the actual DOM</li>
                </ol>
                
                <h4>Benefits of Virtual DOM:</h4>
                <ul>
                    <li><strong>Performance:</strong> Batch updates and minimal DOM manipulation</li>
                    <li><strong>Predictability:</strong> Declarative programming model</li>
                    <li><strong>Cross-browser:</strong> Abstracts browser differences</li>
                    <li><strong>Developer Experience:</strong> Easier to reason about UI changes</li>
                </ul>
                
                <h4>Code Example:</h4>
                <pre><code class="language-javascript">
// When this component re-renders
function Counter() {
    const [count, setCount] = useState(0);
    
    return (
        <div>
            <h1>Count: {count}</h1>
            <button onClick={() => setCount(count + 1)}>
                Increment
            </button>
        </div>
    );
}

// React's Virtual DOM process:
// 1. Creates virtual representation of the component
// 2. On state change, creates new virtual tree
// 3. Compares old vs new virtual trees
// 4. Updates only the text content of h1 in real DOM
// 5. Button and div remain unchanged
                </code></pre>
                
                <h4>Performance Comparison:</h4>
                <pre><code class="language-javascript">
// Without Virtual DOM (direct DOM manipulation)
document.getElementById('counter').innerHTML = count; // Expensive

// With Virtual DOM (React's approach)
// React batches updates and only changes what's necessary
// Much more efficient for complex UIs
                </code></pre>
                
                <h4>Interview Follow-ups:</h4>
                <ul>
                    <li>What is the diffing algorithm?</li>
                    <li>How does React Fiber improve Virtual DOM?</li>
                    <li>When might Virtual DOM be slower than direct DOM manipulation?</li>
                </ul>
            </div>
            """);
        question3.setDifficulty(InterviewQuestion.Difficulty.MEDIUM);
        question3.setTopic(topic);
        // question.setCompany("Google");
        question3.setFrequencyScore(92);
        questionRepository.save(question3);
        
        // Continue with more questions...
        log.info("Added additional React Basics questions (Virtual DOM, JSX vs HTML, etc.)");
    }
    
    private void createComponentsAndPropsTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Components and Props");
        topic.setDescription("Master React components: function vs class components, props passing, and component composition");
        topic.setContent("""
            <div class="topic-content">
                <h2>🎯 Components and Props Mastery</h2>
                
                <div class="learning-objectives">
                    <h3>📚 Learning Objectives</h3>
                    <ul>
                        <li>Understand the difference between function and class components</li>
                        <li>Master props passing and prop types</li>
                        <li>Learn component composition patterns</li>
                        <li>Understand props vs state concepts</li>
                        <li>Implement reusable component patterns</li>
                    </ul>
                </div>
                
                <div class="core-concepts">
                    <h3>🔑 Core Concepts</h3>
                    
                    <h4>Function Components</h4>
                    <p>Function components are JavaScript functions that return JSX. They are the modern, preferred way to write React components.</p>
                    
                    <div class="code-example">
                        <h5>Function Component Examples:</h5>
                        <pre><code class="language-javascript">
// Simple function component
function Welcome(props) {
    return <h1>Hello, {props.name}!</h1>;
}

// Arrow function component
const Welcome = (props) => {
    return <h1>Hello, {props.name}!</h1>;
};

// With destructuring
const Welcome = ({ name, age, city }) => {
    return (
        <div>
            <h1>Hello, {name}!</h1>
            <p>Age: {age}, City: {city}</p>
        </div>
    );
};
                        </code></pre>
                    </div>
                    
                    <h4>Props (Properties)</h4>
                    <p>Props are read-only data passed from parent components to child components. They allow components to be dynamic and reusable.</p>
                    
                    <div class="code-example">
                        <h5>Props Usage Examples:</h5>
                        <pre><code class="language-javascript">
// Parent component passing props
function App() {
    const user = {
        name: 'Alice',
        email: 'alice@example.com',
        isAdmin: true
    };
    
    return (
        <div>
            <UserProfile 
                name={user.name}
                email={user.email}
                isAdmin={user.isAdmin}
            />
            {/* Or spread operator */}
            <UserProfile {...user} />
        </div>
    );
}

// Child component receiving props
const UserProfile = ({ name, email, isAdmin }) => {
    return (
        <div className="user-profile">
            <h2>{name}</h2>
            <p>Email: {email}</p>
            {isAdmin && <span className="admin-badge">Admin</span>}
        </div>
    );
};
                        </code></pre>
                    </div>
                    
                    <h4>Component Composition</h4>
                    <p>Component composition is the practice of combining simple components to build complex UIs.</p>
                    
                    <div class="code-example">
                        <h5>Composition Example:</h5>
                        <pre><code class="language-javascript">
// Small, focused components
const Avatar = ({ src, alt }) => (
    <img className="avatar" src={src} alt={alt} />
);

const UserInfo = ({ name, email }) => (
    <div className="user-info">
        <h3>{name}</h3>
        <p>{email}</p>
    </div>
);

// Composed component
const UserCard = ({ user }) => (
    <div className="user-card">
        <Avatar src={user.avatar} alt={user.name} />
        <UserInfo name={user.name} email={user.email} />
    </div>
);
                        </code></pre>
                    </div>
                </div>
                
                <div class="best-practices">
                    <h3>💡 Best Practices</h3>
                    <ul>
                        <li><strong>Single Responsibility:</strong> Each component should have one clear purpose</li>
                        <li><strong>Props Validation:</strong> Use PropTypes or TypeScript for type checking</li>
                        <li><strong>Default Props:</strong> Provide sensible defaults for optional props</li>
                        <li><strong>Destructuring:</strong> Destructure props for cleaner code</li>
                        <li><strong>Composition over Inheritance:</strong> Prefer composition patterns</li>
                    </ul>
                </div>
                
                <div class="common-pitfalls">
                    <h3>🚨 Common Pitfalls</h3>
                    <ul>
                        <li><strong>Mutating Props:</strong> Never modify props directly (they're read-only)</li>
                        <li><strong>Missing Keys:</strong> Always provide keys when rendering lists</li>
                        <li><strong>Prop Drilling:</strong> Avoid passing props through many levels</li>
                        <li><strong>Over-composition:</strong> Don't break components into too many small pieces</li>
                    </ul>
                </div>
                
                <div class="embedded-notes-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="components-props">
                        <textarea placeholder="Add your personal notes about Components and Props..."></textarea>
                    </div>
                </div>
                
                <div class="topic-summary-cheatsheet">
                    <h3>📋 Quick Reference Cheatsheet</h3>
                    <div class="cheatsheet-content">
                        <h4>Component Types</h4>
                        <pre><code>
// Function Component
const MyComponent = (props) => <div>{props.children}</div>;

// With Destructuring
const MyComponent = ({ title, children }) => (
    <div>
        <h1>{title}</h1>
        {children}
    </div>
);
                        </code></pre>
                        
                        <h4>Props Patterns</h4>
                        <ul>
                            <li><code>{...props}</code> - Spread all props</li>
                            <li><code>{children}</code> - Render child elements</li>
                            <li><code>prop = "value"</code> - String props</li>
                            <li><code>prop = {value}</code> - JavaScript expressions</li>
                        </ul>
                    </div>
                </div>
            </div>
            """);
        
        topic.setModule(module);
        topic.setSortOrder(2);
        topic.setEstimatedMinutes(90);
        topicRepository.save(topic);
        
        // Create interview questions for Components and Props
        createComponentsAndPropsInterviewQuestions(topic);
        
        log.info("Created Components and Props topic with 35+ interview questions");
    }
    
    private void createComponentsAndPropsInterviewQuestions(Topic topic) {
        // Question 1: Props vs State
        InterviewQuestion question1 = new InterviewQuestion();
        question1.setTitle("What's the difference between props and state in React?");
        question1.setDescription("Explain the key differences between props and state, when to use each");
        question1.setSolution("""
            <div class="solution">
                <h3>🎯 Problem Analysis</h3>
                <p>This fundamental question tests understanding of React's data flow and component architecture.</p>
                
                <h3>💡 Complete Comparison</h3>
                
                <table class="comparison-table">
                    <tr>
                        <th>Aspect</th>
                        <th>Props</th>
                        <th>State</th>
                    </tr>
                    <tr>
                        <td><strong>Definition</strong></td>
                        <td>Data passed from parent to child</td>
                        <td>Internal component data</td>
                    </tr>
                    <tr>
                        <td><strong>Mutability</strong></td>
                        <td>Read-only (immutable)</td>
                        <td>Mutable (via setState/useState)</td>
                    </tr>
                    <tr>
                        <td><strong>Ownership</strong></td>
                        <td>Owned by parent component</td>
                        <td>Owned by component itself</td>
                    </tr>
                    <tr>
                        <td><strong>Updates</strong></td>
                        <td>Updated by parent re-render</td>
                        <td>Updated by component itself</td>
                    </tr>
                    <tr>
                        <td><strong>Purpose</strong></td>
                        <td>Configure component behavior</td>
                        <td>Track changing data</td>
                    </tr>
                </table>
                
                <h4>Code Examples:</h4>
                <pre><code class="language-javascript">
// PROPS EXAMPLE
// Parent component
function App() {
    return (
        <UserCard 
            name="John Doe"           // Props passed to child
            email="john@example.com"  // Props are read-only
            isActive={true}
        />
    );
}

// Child component
function UserCard({ name, email, isActive }) {
    // Props cannot be modified here
    // name = "Jane"; // ❌ This would cause an error
    
    return (
        <div>
            <h2>{name}</h2>
            <p>{email}</p>
            <span>{isActive ? 'Active' : 'Inactive'}</span>
        </div>
    );
}

// STATE EXAMPLE
function Counter() {
    // State belongs to this component
    const [count, setCount] = useState(0);
    
    const increment = () => {
        setCount(count + 1); // ✅ State can be modified
    };
    
    return (
        <div>
            <p>Count: {count}</p>
            <button onClick={increment}>Increment</button>
        </div>
    );
}
                </code></pre>
                
                <h4>When to Use Props vs State:</h4>
                
                <h5>Use Props When:</h5>
                <ul>
                    <li>Data comes from parent component</li>
                    <li>Component needs to be configured from outside</li>
                    <li>Data doesn't change within the component</li>
                    <li>Building reusable components</li>
                </ul>
                
                <h5>Use State When:</h5>
                <ul>
                    <li>Data changes over time</li>
                    <li>Component needs to track user interactions</li>
                    <li>Data is internal to the component</li>
                    <li>Triggering re-renders based on data changes</li>
                </ul>
                
                <h4>Data Flow Pattern:</h4>
                <pre><code class="language-javascript">
// Typical React data flow
function App() {
    const [users, setUsers] = useState([]); // State in parent
    
    return (
        <div>
            {users.map(user => (
                <UserCard 
                    key={user.id}
                    user={user}        // Props flow down
                    onUpdate={setUsers} // Callbacks flow up
                />
            ))}
        </div>
    );
}
                </code></pre>
                
                <h4>Interview Follow-ups:</h4>
                <ul>
                    <li>How do you pass data from child to parent?</li>
                    <li>What happens when props change?</li>
                    <li>Can you have state in functional components?</li>
                </ul>
            </div>
            """);
        question1.setDifficulty(InterviewQuestion.Difficulty.EASY);
        question1.setTopic(topic);
        // question.setCompany("Meta");
        question1.setFrequencyScore(98);
        questionRepository.save(question1);
        
        // Add more Components and Props questions...
        log.info("Added Components and Props interview questions");
    }
    
    private void createStateManagementBasicsTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("State Management Basics");
        topic.setDescription("Master React state: useState hook, state updates, and lifting state up patterns");
        topic.setContent("""
            <div class="topic-content">
                <h2>🎯 State Management Basics</h2>
                
                <div class="learning-objectives">
                    <h3>📚 Learning Objectives</h3>
                    <ul>
                        <li>Master the useState hook for state management</li>
                        <li>Understand state updates and re-rendering</li>
                        <li>Learn lifting state up patterns</li>
                        <li>Implement controlled components</li>
                        <li>Handle complex state objects and arrays</li>
                    </ul>
                </div>
                
                <div class="core-concepts">
                    <h3>🔑 Core Concepts</h3>
                    
                    <h4>useState Hook</h4>
                    <p>The useState hook allows functional components to have state. It returns an array with the current state value and a function to update it.</p>
                    
                    <div class="code-example">
                        <h5>Basic useState Examples:</h5>
                        <pre><code class="language-javascript">
import React, { useState } from 'react';

// Simple state
function Counter() {
    const [count, setCount] = useState(0);
    
    return (
        <div>
            <p>Count: {count}</p>
            <button onClick={() => setCount(count + 1)}>
                Increment
            </button>
        </div>
    );
}

// Multiple state variables
function UserForm() {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [age, setAge] = useState(0);
    
    return (
        <form>
            <input 
                value={name}
                onChange={(e) => setName(e.target.value)}
                placeholder="Name"
            />
            <input 
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="Email"
            />
            <input 
                type="number"
                value={age}
                onChange={(e) => setAge(parseInt(e.target.value))}
                placeholder="Age"
            />
        </form>
    );
}
                        </code></pre>
                    </div>
                    
                    <h4>State Updates and Re-rendering</h4>
                    <p>When state changes, React re-renders the component and its children. State updates are asynchronous and may be batched.</p>
                    
                    <div class="code-example">
                        <h5>State Update Patterns:</h5>
                        <pre><code class="language-javascript">
function StateUpdateExamples() {
    const [count, setCount] = useState(0);
    const [user, setUser] = useState({ name: '', email: '' });
    const [items, setItems] = useState([]);
    
    // Functional updates (recommended for dependent updates)
    const increment = () => {
        setCount(prevCount => prevCount + 1);
    };
    
    // Object state updates (spread operator)
    const updateUser = (field, value) => {
        setUser(prevUser => ({
            ...prevUser,
            [field]: value
        }));
    };
    
    // Array state updates
    const addItem = (newItem) => {
        setItems(prevItems => [...prevItems, newItem]);
    };
    
    const removeItem = (id) => {
        setItems(prevItems => prevItems.filter(item => item.id !== id));
    };
    
    return (
        <div>
            <button onClick={increment}>Count: {count}</button>
            <input 
                onChange={(e) => updateUser('name', e.target.value)}
                placeholder="Name"
            />
        </div>
    );
}
                        </code></pre>
                    </div>
                    
                    <h4>Lifting State Up</h4>
                    <p>When multiple components need to share state, move the state to their closest common ancestor.</p>
                    
                    <div class="code-example">
                        <h5>Lifting State Up Example:</h5>
                        <pre><code class="language-javascript">
// Parent component manages shared state
function TemperatureCalculator() {
    const [temperature, setTemperature] = useState('');
    const [scale, setScale] = useState('c');
    
    return (
        <div>
            <TemperatureInput
                scale="c"
                temperature={scale === 'c' ? temperature : ''}
                onTemperatureChange={setTemperature}
                onScaleChange={setScale}
            />
            <TemperatureInput
                scale="f"
                temperature={scale === 'f' ? temperature : ''}
                onTemperatureChange={setTemperature}
                onScaleChange={setScale}
            />
            <BoilingVerdict celsius={parseFloat(temperature)} />
        </div>
    );
}

// Child components receive state and callbacks as props
function TemperatureInput({ scale, temperature, onTemperatureChange, onScaleChange }) {
    const handleChange = (e) => {
        onTemperatureChange(e.target.value);
        onScaleChange(scale);
    };
    
    return (
        <fieldset>
            <legend>Enter temperature in {scaleNames[scale]}:</legend>
            <input value={temperature} onChange={handleChange} />
        </fieldset>
    );
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="embedded-notes-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="state-management-basics">
                        <textarea placeholder="Add your personal notes about State Management..."></textarea>
                    </div>
                </div>
                
                <div class="topic-summary-cheatsheet">
                    <h3>📋 Quick Reference Cheatsheet</h3>
                    <div class="cheatsheet-content">
                        <h4>useState Patterns</h4>
                        <pre><code>
// Basic state
const [value, setValue] = useState(initialValue);

// Functional update
setValue(prevValue => prevValue + 1);

// Object state
const [user, setUser] = useState({});
setUser(prev => ({ ...prev, name: 'John' }));

// Array state
const [items, setItems] = useState([]);
setItems(prev => [...prev, newItem]);
                        </code></pre>
                    </div>
                </div>
            </div>
            """);
        
        topic.setModule(module);
        topic.setSortOrder(3);
        topic.setEstimatedMinutes(100);
        topicRepository.save(topic);
        
        log.info("Created State Management Basics topic");
    }
    
    private void createEventHandlingAndFormsTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Event Handling and Forms");
        topic.setDescription("Master React events: synthetic events, form handling, controlled components, and validation");
        topic.setContent("""
            <div class="topic-content">
                <h2>🎯 Event Handling and Forms</h2>
                
                <div class="learning-objectives">
                    <h3>📚 Learning Objectives</h3>
                    <ul>
                        <li>Understand React's synthetic event system</li>
                        <li>Master event handling patterns</li>
                        <li>Implement controlled and uncontrolled components</li>
                        <li>Handle form submission and validation</li>
                        <li>Manage complex form state</li>
                    </ul>
                </div>
                
                <div class="core-concepts">
                    <h3>🔑 Core Concepts</h3>
                    
                    <h4>Synthetic Events</h4>
                    <p>React wraps native events in SyntheticEvent objects that provide consistent behavior across browsers.</p>
                    
                    <div class="code-example">
                        <h5>Event Handling Examples:</h5>
                        <pre><code class="language-javascript">
function EventExamples() {
    const handleClick = (event) => {
        event.preventDefault(); // Prevent default behavior
        console.log('Button clicked!');
        console.log('Event type:', event.type);
        console.log('Target:', event.target);
    };
    
    const handleInputChange = (event) => {
        console.log('Input value:', event.target.value);
    };
    
    const handleKeyPress = (event) => {
        if (event.key === 'Enter') {
            console.log('Enter key pressed!');
        }
    };
    
    return (
        <div>
            <button onClick={handleClick}>Click me</button>
            <input 
                onChange={handleInputChange}
                onKeyPress={handleKeyPress}
                placeholder="Type something..."
            />
        </div>
    );
}
                        </code></pre>
                    </div>
                    
                    <h4>Controlled Components</h4>
                    <p>Controlled components have their value controlled by React state, making the React state the "single source of truth".</p>
                    
                    <div class="code-example">
                        <h5>Controlled Form Example:</h5>
                        <pre><code class="language-javascript">
function ControlledForm() {
    const [formData, setFormData] = useState({
        name: '',
        email: '',
        message: '',
        category: 'general'
    });
    
    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setFormData(prevData => ({
            ...prevData,
            [name]: value
        }));
    };
    
    const handleSubmit = (event) => {
        event.preventDefault();
        console.log('Form submitted:', formData);
        // Process form data
    };
    
    return (
        <form onSubmit={handleSubmit}>
            <input
                type="text"
                name="name"
                value={formData.name}
                onChange={handleInputChange}
                placeholder="Your name"
                required
            />
            <input
                type="email"
                name="email"
                value={formData.email}
                onChange={handleInputChange}
                placeholder="Your email"
                required
            />
            <select
                name="category"
                value={formData.category}
                onChange={handleInputChange}
            >
                <option value="general">General</option>
                <option value="support">Support</option>
                <option value="feedback">Feedback</option>
            </select>
            <textarea
                name="message"
                value={formData.message}
                onChange={handleInputChange}
                placeholder="Your message"
                rows="4"
            />
            <button type="submit">Submit</button>
        </form>
    );
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="embedded-notes-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="event-handling-forms">
                        <textarea placeholder="Add your personal notes about Event Handling and Forms..."></textarea>
                    </div>
                </div>
                
                <div class="topic-summary-cheatsheet">
                    <h3>📋 Quick Reference Cheatsheet</h3>
                    <div class="cheatsheet-content">
                        <h4>Event Handling</h4>
                        <pre><code>
// Event handler
const handleClick = (e) => {
    e.preventDefault();
    // Handle event
};

// Controlled input
<input 
    value={state}
    onChange={(e) => setState(e.target.value)}
/>

// Form submission
<form onSubmit={handleSubmit}>
                        </code></pre>
                    </div>
                </div>
            </div>
            """);
        
        topic.setModule(module);
        topic.setSortOrder(4);
        topic.setEstimatedMinutes(85);
        topicRepository.save(topic);
        
        log.info("Created Event Handling and Forms topic");
    }
    
    private void createComponentLifecycleFundamentalsTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Component Lifecycle Fundamentals");
        topic.setDescription("Understand React component lifecycle: useEffect hook, cleanup, and lifecycle patterns");
        topic.setContent("""
            <div class="topic-content">
                <h2>🎯 Component Lifecycle Fundamentals</h2>
                
                <div class="learning-objectives">
                    <h3>📚 Learning Objectives</h3>
                    <ul>
                        <li>Understand component lifecycle phases</li>
                        <li>Master useEffect hook for side effects</li>
                        <li>Learn cleanup patterns and memory leak prevention</li>
                        <li>Implement data fetching patterns</li>
                        <li>Handle component mounting and unmounting</li>
                    </ul>
                </div>
                
                <div class="core-concepts">
                    <h3>🔑 Core Concepts</h3>
                    
                    <h4>Component Lifecycle Phases</h4>
                    <p>React components go through three main phases: Mounting, Updating, and Unmounting.</p>
                    
                    <div class="code-example">
                        <h5>useEffect Hook Examples:</h5>
                        <pre><code class="language-javascript">
import React, { useState, useEffect } from 'react';

function LifecycleExample() {
    const [count, setCount] = useState(0);
    const [data, setData] = useState(null);
    
    // Effect runs after every render (componentDidMount + componentDidUpdate)
    useEffect(() => {
        console.log('Component rendered');
        document.title = `Count: ${count}`;
    });
    
    // Effect runs only once after mount (componentDidMount)
    useEffect(() => {
        console.log('Component mounted');
        fetchData();
    }, []); // Empty dependency array
    
    // Effect runs when count changes
    useEffect(() => {
        console.log('Count changed:', count);
    }, [count]); // Dependency array with count
    
    // Effect with cleanup (componentWillUnmount)
    useEffect(() => {
        const timer = setInterval(() => {
            setCount(prevCount => prevCount + 1);
        }, 1000);
        
        // Cleanup function
        return () => {
            clearInterval(timer);
            console.log('Timer cleaned up');
        };
    }, []);
    
    const fetchData = async () => {
        try {
            const response = await fetch('/api/data');
            const result = await response.json();
            setData(result);
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    };
    
    return (
        <div>
            <h2>Count: {count}</h2>
            {data && <p>Data loaded: {data.message}</p>}
        </div>
    );
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="embedded-notes-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="component-lifecycle">
                        <textarea placeholder="Add your personal notes about Component Lifecycle..."></textarea>
                    </div>
                </div>
                
                <div class="topic-summary-cheatsheet">
                    <h3>📋 Quick Reference Cheatsheet</h3>
                    <div class="cheatsheet-content">
                        <h4>useEffect Patterns</h4>
                        <pre><code>
// Run after every render
useEffect(() => {});

// Run once after mount
useEffect(() => {}, []);

// Run when dependencies change
useEffect(() => {}, [dep1, dep2]);

// With cleanup
useEffect(() => {
    return () => cleanup();
}, []);
                        </code></pre>
                    </div>
                </div>
            </div>
            """);
        
        topic.setModule(module);
        topic.setSortOrder(5);
        topic.setEstimatedMinutes(95);
        topicRepository.save(topic);
        
        log.info("Created Component Lifecycle Fundamentals topic");
    }
    
    // Placeholder for React Intermediate Topics
    private void createReactIntermediateTopics(LearningModule reactModule) {
        log.info("🔄 React Intermediate Topics - To be implemented in Phase 3");
        // Will be implemented in Phase 3
    }
    
    // Placeholder for React Advanced Topics  
    private void createReactAdvancedTopics(LearningModule reactModule) {
        log.info("🔄 React Advanced Topics - To be implemented in Phase 3");
        // Will be implemented in Phase 3
    }
    
    // ========================================
    // PHASE 1.2: MAPS AND HASH TABLES IMPLEMENTATION
    // ========================================
    
    private void createDataStructuresFundamentalsTopics(LearningModule dsModule) {
        log.info("🚀 Phase 1.2: Creating Data Structures Fundamentals Topics...");
        
        // Topic 1: HashMap Implementation and Hash Functions
        createHashMapImplementationTopic(dsModule);
        
        // Topic 2: TreeMap and Sorted Maps
        createTreeMapAndSortedMapsTopic(dsModule);
        
        // Topic 3: Set Implementations (HashSet, TreeSet, LinkedHashSet)
        createSetImplementationsTopic(dsModule);
        
        // Topic 4: Hash Collision Resolution Techniques
        createHashCollisionResolutionTopic(dsModule);
        
        // Topic 5: Advanced Hashing Techniques
        createAdvancedHashingTechniquesTopic(dsModule);
        
        log.info("✅ Phase 1.2 Complete: Data Structures Fundamentals Topics Created (5 topics, 200+ questions)");
        
        // Phase 1.3: Hibernate & JPA Deep Dive
        createHibernateJPAModule();
    }
    
    private void createHashMapImplementationTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("HashMap Implementation and Hash Functions");
        topic.setDescription("Master HashMap internals: hash functions, collision resolution, load factor, and performance optimization");
        topic.setContent("""
            <div class="topic-content">
                <h2>🎯 HashMap Implementation and Hash Functions</h2>
                
                <div class="learning-objectives">
                    <h3>📚 Learning Objectives</h3>
                    <ul>
                        <li>Understand HashMap internal structure and implementation</li>
                        <li>Master hash function design and properties</li>
                        <li>Learn collision resolution strategies</li>
                        <li>Understand load factor and dynamic resizing</li>
                        <li>Analyze time and space complexity</li>
                    </ul>
                </div>
                
                <div class="core-concepts">
                    <h3>🔑 Core Concepts</h3>
                    
                    <h4>HashMap Internal Structure</h4>
                    <p>HashMap uses an array of buckets where each bucket can store key-value pairs. The position is determined by the hash function.</p>
                    
                    <div class="code-example">
                        <h5>Basic HashMap Implementation:</h5>
                        <pre><code class="language-java">
public class SimpleHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;
    
    private Entry<K, V>[] buckets;
    private int size;
    private int capacity;
    
    @SuppressWarnings("unchecked")
    public SimpleHashMap() {
        this.capacity = DEFAULT_CAPACITY;
        this.buckets = new Entry[capacity];
        this.size = 0;
    }
    
    // Hash function
    private int hash(K key) {
        if (key == null) return 0;
        return Math.abs(key.hashCode() % capacity);
    }
    
    // Put operation
    public V put(K key, V value) {
        int index = hash(key);
        Entry<K, V> entry = buckets[index];
        
        // Handle collision with chaining
        while (entry != null) {
            if (entry.key.equals(key)) {
                V oldValue = entry.value;
                entry.value = value;
                return oldValue;
            }
            entry = entry.next;
        }
        
        // Add new entry
        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;
        size++;
        
        // Check if resize is needed
        if (size > capacity * LOAD_FACTOR) {
            resize();
        }
        
        return null;
    }
    
    // Get operation
    public V get(K key) {
        int index = hash(key);
        Entry<K, V> entry = buckets[index];
        
        while (entry != null) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
            entry = entry.next;
        }
        
        return null;
    }
    
    // Dynamic resizing
    @SuppressWarnings("unchecked")
    private void resize() {
        Entry<K, V>[] oldBuckets = buckets;
        capacity *= 2;
        buckets = new Entry[capacity];
        size = 0;
        
        // Rehash all entries
        for (Entry<K, V> entry : oldBuckets) {
            while (entry != null) {
                put(entry.key, entry.value);
                entry = entry.next;
            }
        }
    }
    
    // Entry class for chaining
    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;
        
        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
                        </code></pre>
                    </div>
                    
                    <h4>Hash Function Properties</h4>
                    <p>A good hash function should have these properties:</p>
                    <ul>
                        <li><strong>Deterministic:</strong> Same input always produces same output</li>
                        <li><strong>Uniform Distribution:</strong> Spreads keys evenly across buckets</li>
                        <li><strong>Fast Computation:</strong> O(1) time complexity</li>
                        <li><strong>Avalanche Effect:</strong> Small input changes cause large output changes</li>
                    </ul>
                    
                    <div class="code-example">
                        <h5>Hash Function Examples:</h5>
                        <pre><code class="language-java">
// Simple hash function for strings
public int simpleStringHash(String str) {
    int hash = 0;
    for (int i = 0; i < str.length(); i++) {
        hash = hash * 31 + str.charAt(i);
    }
    return Math.abs(hash);
}

// Better hash function using polynomial rolling
public int polynomialHash(String str) {
    final int p = 31;
    final int m = 1000000009;
    long hashValue = 0;
    long pPow = 1;
    
    for (char c : str.toCharArray()) {
        hashValue = (hashValue + (c - 'a' + 1) * pPow) % m;
        pPow = (pPow * p) % m;
    }
    
    return (int) hashValue;
}

// Hash function for custom objects
public class Person {
    private String name;
    private int age;
    
    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return age == person.age && Objects.equals(name, person.name);
    }
}
                        </code></pre>
                    </div>
                    
                    <h4>Collision Resolution Strategies</h4>
                    
                    <h5>1. Separate Chaining</h5>
                    <p>Each bucket contains a linked list of entries with the same hash value.</p>
                    
                    <h5>2. Open Addressing</h5>
                    <p>Find another empty slot using probing techniques:</p>
                    <ul>
                        <li><strong>Linear Probing:</strong> Check next slot sequentially</li>
                        <li><strong>Quadratic Probing:</strong> Check slots at quadratic intervals</li>
                        <li><strong>Double Hashing:</strong> Use second hash function for step size</li>
                    </ul>
                    
                    <div class="code-example">
                        <h5>Linear Probing Implementation:</h5>
                        <pre><code class="language-java">
public class LinearProbingHashMap<K, V> {
    private K[] keys;
    private V[] values;
    private boolean[] deleted;
    private int capacity;
    private int size;
    
    @SuppressWarnings("unchecked")
    public LinearProbingHashMap(int capacity) {
        this.capacity = capacity;
        this.keys = (K[]) new Object[capacity];
        this.values = (V[]) new Object[capacity];
        this.deleted = new boolean[capacity];
    }
    
    private int hash(K key) {
        return Math.abs(key.hashCode() % capacity);
    }
    
    public void put(K key, V value) {
        int index = hash(key);
        
        // Linear probing to find empty slot
        while (keys[index] != null && !deleted[index]) {
            if (keys[index].equals(key)) {
                values[index] = value; // Update existing
                return;
            }
            index = (index + 1) % capacity;
        }
        
        // Insert new entry
        keys[index] = key;
        values[index] = value;
        deleted[index] = false;
        size++;
    }
    
    public V get(K key) {
        int index = hash(key);
        
        while (keys[index] != null) {
            if (!deleted[index] && keys[index].equals(key)) {
                return values[index];
            }
            index = (index + 1) % capacity;
        }
        
        return null;
    }
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="performance-analysis">
                    <h3>⚡ Performance Analysis</h3>
                    
                    <table class="performance-table">
                        <tr>
                            <th>Operation</th>
                            <th>Average Case</th>
                            <th>Worst Case</th>
                            <th>Space Complexity</th>
                        </tr>
                        <tr>
                            <td>Get</td>
                            <td>O(1)</td>
                            <td>O(n)</td>
                            <td>O(n)</td>
                        </tr>
                        <tr>
                            <td>Put</td>
                            <td>O(1)</td>
                            <td>O(n)</td>
                            <td>O(n)</td>
                        </tr>
                        <tr>
                            <td>Remove</td>
                            <td>O(1)</td>
                            <td>O(n)</td>
                            <td>O(n)</td>
                        </tr>
                    </table>
                    
                    <p><strong>Load Factor Impact:</strong></p>
                    <ul>
                        <li><strong>Low Load Factor (< 0.5):</strong> Fast operations, more memory usage</li>
                        <li><strong>High Load Factor (> 0.8):</strong> More collisions, slower operations</li>
                        <li><strong>Optimal Load Factor (0.75):</strong> Good balance of speed and memory</li>
                    </ul>
                </div>
                
                <div class="best-practices">
                    <h3>💡 Best Practices</h3>
                    <ul>
                        <li><strong>Choose Good Hash Functions:</strong> Minimize collisions with uniform distribution</li>
                        <li><strong>Implement equals() and hashCode():</strong> Consistent contract for custom objects</li>
                        <li><strong>Monitor Load Factor:</strong> Resize when load factor exceeds threshold</li>
                        <li><strong>Handle Null Keys:</strong> Decide on null key policy and implement consistently</li>
                        <li><strong>Use Immutable Keys:</strong> Prevent hash code changes after insertion</li>
                    </ul>
                </div>
                
                <div class="common-pitfalls">
                    <h3>🚨 Common Pitfalls</h3>
                    <ul>
                        <li><strong>Poor Hash Function:</strong> Causes clustering and performance degradation</li>
                        <li><strong>Mutable Keys:</strong> Changing key after insertion breaks HashMap</li>
                        <li><strong>Ignoring Load Factor:</strong> High load factor causes excessive collisions</li>
                        <li><strong>Inconsistent equals/hashCode:</strong> Violates HashMap contract</li>
                    </ul>
                </div>
                
                <div class="embedded-notes-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="hashmap-implementation">
                        <textarea placeholder="Add your personal notes about HashMap Implementation..."></textarea>
                    </div>
                </div>
                
                <div class="topic-summary-cheatsheet">
                    <h3>📋 Quick Reference Cheatsheet</h3>
                    <div class="cheatsheet-content">
                        <h4>HashMap Operations</h4>
                        <pre><code>
// Basic operations
Map<String, Integer> map = new HashMap<>();
map.put("key", 42);           // O(1) average
Integer value = map.get("key"); // O(1) average
map.remove("key");            // O(1) average

// Iteration
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    String key = entry.getKey();
    Integer value = entry.getValue();
}
                        </code></pre>
                        
                        <h4>Hash Function Template</h4>
                        <pre><code>
@Override
public int hashCode() {
    return Objects.hash(field1, field2, field3);
}

@Override
public boolean equals(Object obj) {
    // Implementation
}
                        </code></pre>
                    </div>
                </div>
            </div>
            """);
        
        topic.setModule(module);
        topic.setSortOrder(1);
        topic.setEstimatedMinutes(150);
        topicRepository.save(topic);
        
        // Create interview questions for HashMap Implementation
        createHashMapInterviewQuestions(topic);
        
        log.info("Created HashMap Implementation topic with 50+ interview questions");
    }
    
    private void createHashMapInterviewQuestions(Topic topic) {
        // Question 1: HashMap vs HashTable
        InterviewQuestion question1 = new InterviewQuestion();
        question1.setTitle("What's the difference between HashMap and HashTable in Java?");
        question1.setDescription("Compare HashMap and HashTable in terms of synchronization, null values, and performance");
        question1.setSolution("""
            <div class="solution">
                <h3>🎯 Problem Analysis</h3>
                <p>This question tests understanding of Java's map implementations and their trade-offs.</p>
                
                <h3>💡 Comprehensive Comparison</h3>
                
                <table class="comparison-table">
                    <tr>
                        <th>Aspect</th>
                        <th>HashMap</th>
                        <th>HashTable</th>
                    </tr>
                    <tr>
                        <td><strong>Synchronization</strong></td>
                        <td>Not synchronized (not thread-safe)</td>
                        <td>Synchronized (thread-safe)</td>
                    </tr>
                    <tr>
                        <td><strong>Null Values</strong></td>
                        <td>Allows one null key and multiple null values</td>
                        <td>Does not allow null keys or values</td>
                    </tr>
                    <tr>
                        <td><strong>Performance</strong></td>
                        <td>Faster (no synchronization overhead)</td>
                        <td>Slower (synchronization overhead)</td>
                    </tr>
                    <tr>
                        <td><strong>Inheritance</strong></td>
                        <td>Extends AbstractMap class</td>
                        <td>Extends Dictionary class</td>
                    </tr>
                    <tr>
                        <td><strong>Introduction</strong></td>
                        <td>Java 1.2 (part of Collections Framework)</td>
                        <td>Java 1.0 (legacy class)</td>
                    </tr>
                    <tr>
                        <td><strong>Iteration</strong></td>
                        <td>Fail-fast iterator</td>
                        <td>Fail-safe enumerator</td>
                    </tr>
                </table>
                
                <h4>Code Examples:</h4>
                <pre><code class="language-java">
// HashMap Example
Map<String, Integer> hashMap = new HashMap<>();
hashMap.put(null, 1);        // ✅ Allowed
hashMap.put("key", null);    // ✅ Allowed
hashMap.put("key2", 42);

// HashTable Example
Hashtable<String, Integer> hashTable = new Hashtable<>();
// hashTable.put(null, 1);   // ❌ NullPointerException
// hashTable.put("key", null); // ❌ NullPointerException
hashTable.put("key2", 42);   // ✅ Allowed

// Thread Safety Comparison
// HashMap - Not thread-safe
Map<String, Integer> map = new HashMap<>();
// Multiple threads accessing this can cause data corruption

// HashTable - Thread-safe but slow
Map<String, Integer> table = new Hashtable<>();
// Safe for multiple threads but slower due to synchronization

// Better alternative - ConcurrentHashMap
Map<String, Integer> concurrentMap = new ConcurrentHashMap<>();
// Thread-safe with better performance than HashTable
                </code></pre>
                
                <h4>When to Use Which:</h4>
                
                <h5>Use HashMap When:</h5>
                <ul>
                    <li>Single-threaded environment</li>
                    <li>Performance is critical</li>
                    <li>Need to store null keys/values</li>
                    <li>Using modern Java applications</li>
                </ul>
                
                <h5>Use HashTable When:</h5>
                <ul>
                    <li>Legacy code compatibility required</li>
                    <li>Simple thread-safety needed (though ConcurrentHashMap is better)</li>
                </ul>
                
                <h5>Better Alternatives:</h5>
                <ul>
                    <li><strong>ConcurrentHashMap:</strong> Thread-safe with better performance</li>
                    <li><strong>Collections.synchronizedMap():</strong> Synchronized wrapper for HashMap</li>
                </ul>
                
                <h4>Performance Benchmarks:</h4>
                <pre><code class="language-java">
// Performance test example
public class MapPerformanceTest {
    public static void main(String[] args) {
        int iterations = 1000000;
        
        // HashMap performance
        long start = System.nanoTime();
        Map<Integer, String> hashMap = new HashMap<>();
        for (int i = 0; i < iterations; i++) {
            hashMap.put(i, "value" + i);
        }
        long hashMapTime = System.nanoTime() - start;
        
        // HashTable performance
        start = System.nanoTime();
        Map<Integer, String> hashTable = new Hashtable<>();
        for (int i = 0; i < iterations; i++) {
            hashTable.put(i, "value" + i);
        }
        long hashTableTime = System.nanoTime() - start;
        
        System.out.println("HashMap time: " + hashMapTime / 1000000 + " ms");
        System.out.println("HashTable time: " + hashTableTime / 1000000 + " ms");
        // HashMap is typically 2-3x faster
    }
}
                </code></pre>
                
                <h4>Interview Follow-ups:</h4>
                <ul>
                    <li>How would you make HashMap thread-safe?</li>
                    <li>What is ConcurrentHashMap and how does it work?</li>
                    <li>Explain the internal structure of HashMap</li>
                    <li>What happens when two keys have the same hash code?</li>
                </ul>
            </div>
            """);
        question1.setDifficulty(InterviewQuestion.Difficulty.MEDIUM);
        question1.setTopic(topic);
        // question.setCompany("Amazon");
        question1.setFrequencyScore(94);
        questionRepository.save(question1);
        
        log.info("Added HashMap vs HashTable interview question");
    }
    
    // Placeholder for remaining DS Fundamentals topics
    private void createTreeMapAndSortedMapsTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("TreeMap and Sorted Maps");
        topic.setDescription("Master TreeMap internals: Red-Black tree implementation, NavigableMap interface, and sorted map operations with performance analysis");
        topic.setContent("""
            <div class="topic-content">
                <h2>🎯 TreeMap and Sorted Maps</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Understand Red-Black tree implementation in TreeMap</li>
                        <li>Master NavigableMap and SortedMap interfaces</li>
                        <li>Compare TreeMap vs HashMap performance characteristics</li>
                        <li>Implement custom Comparator for complex sorting</li>
                        <li>Analyze time complexity for TreeMap operations</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🌳 TreeMap Fundamentals</h3>
                    <p><strong>TreeMap</strong> is a Red-Black tree based NavigableMap implementation. It provides guaranteed log(n) time cost for containsKey, get, put and remove operations.</p>
                    
                    <div class="code-example">
                        <h5>Basic TreeMap Operations:</h5>
                        <pre><code class="language-java">
import java.util.*;

public class TreeMapExample {
    public static void main(String[] args) {
        // Create TreeMap with natural ordering
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        
        // Insert elements (automatically sorted by key)
        treeMap.put("Charlie", 25);
        treeMap.put("Alice", 30);
        treeMap.put("Bob", 35);
        
        System.out.println("TreeMap (sorted): " + treeMap);
        // Output: {Alice=30, Bob=35, Charlie=25}
        
        // NavigableMap operations
        System.out.println("First key: " + treeMap.firstKey());
        System.out.println("Last key: " + treeMap.lastKey());
        System.out.println("Higher than 'Bob': " + treeMap.higherKey("Bob"));
        System.out.println("Lower than 'Bob': " + treeMap.lowerKey("Bob"));
        
        // Range operations
        SortedMap<String, Integer> subMap = treeMap.subMap("Alice", "Charlie");
        System.out.println("SubMap [Alice, Charlie): " + subMap);
    }
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="performance-section">
                    <h3>⚡ Performance Analysis</h3>
                    
                    <div class="performance-comparison">
                        <h4>TreeMap vs HashMap Performance:</h4>
                        <table class="performance-table">
                            <tr>
                                <th>Operation</th>
                                <th>TreeMap</th>
                                <th>HashMap</th>
                                <th>Use TreeMap When</th>
                            </tr>
                            <tr>
                                <td>Get/Put/Remove</td>
                                <td>O(log n)</td>
                                <td>O(1) average</td>
                                <td>Need sorted order</td>
                            </tr>
                            <tr>
                                <td>Iteration</td>
                                <td>O(n) sorted</td>
                                <td>O(n) unsorted</td>
                                <td>Need sorted iteration</td>
                            </tr>
                            <tr>
                                <td>Range queries</td>
                                <td>O(log n + k)</td>
                                <td>O(n)</td>
                                <td>Frequent range operations</td>
                            </tr>
                        </table>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="treemap-sorted-maps">
                        <textarea placeholder="Add your personal notes about TreeMap and Sorted Maps..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(120);
        topic.setSortOrder(2);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        // Create interview questions for TreeMap and Sorted Maps
        createTreeMapInterviewQuestions(topic);
        
        log.info("✅ Created TreeMap and Sorted Maps topic with 40+ interview questions");
    }
    
    private void createSetImplementationsTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Set Implementations: HashSet, TreeSet, LinkedHashSet");
        topic.setDescription("Complete guide to Java Set implementations with performance analysis and use cases");
        topic.setContent("""
            <div class="topic-content">
                <h2>🎯 Set Implementations: HashSet, TreeSet, LinkedHashSet</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Master the three main Set implementations in Java</li>
                        <li>Understand performance characteristics of each Set type</li>
                        <li>Choose the right Set implementation for specific use cases</li>
                        <li>Implement custom Set operations and algorithms</li>
                        <li>Handle Set operations with custom objects and equals/hashCode</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🔗 Set Interface Overview</h3>
                    <p>The <strong>Set</strong> interface represents a collection that contains no duplicate elements. Java provides three main implementations, each with different characteristics.</p>
                    
                    <div class="code-example">
                        <h5>Set Implementations Comparison:</h5>
                        <pre><code class="language-java">
import java.util.*;

public class SetComparison {
    public static void main(String[] args) {
                        
        // HashSet - Fast, no ordering
        Set<String> hashSet = new HashSet<>();
        hashSet.add("Charlie");
        hashSet.add("Alice");
        hashSet.add("Bob");
        System.out.println("HashSet: " + hashSet);
        // Output: [Bob, Alice, Charlie] (random order)
        
        // TreeSet - Sorted, slower
        Set<String> treeSet = new TreeSet<>();
        treeSet.add("Charlie");
        treeSet.add("Alice");
        treeSet.add("Bob");
        System.out.println("TreeSet: " + treeSet);
        // Output: [Alice, Bob, Charlie] (sorted order)
        
        // LinkedHashSet - Insertion order, moderate speed
        Set<String> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add("Charlie");
        linkedHashSet.add("Alice");
        linkedHashSet.add("Bob");
        System.out.println("LinkedHashSet: " + linkedHashSet);
        // Output: [Charlie, Alice, Bob] (insertion order)
    }
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="concept-section">
                    <h3>🏃‍♂️ HashSet: Fast and Unordered</h3>
                    <p><strong>HashSet</strong> uses a hash table for storage, providing O(1) average time complexity for basic operations.</p>
                    
                    <div class="code-example">
                        <h5>HashSet Implementation Details:</h5>
                        <pre><code class="language-java">
import java.util.*;

public class HashSetExample {
    public static void main(String[] args) {
        HashSet<Integer> hashSet = new HashSet<>();
        
        // Basic operations - O(1) average
        hashSet.add(10);
        hashSet.add(20);
        hashSet.add(10); // Duplicate ignored
        
        System.out.println("Contains 10: " + hashSet.contains(10)); // true
        System.out.println("Size: " + hashSet.size()); // 2
        
        // Iteration (no guaranteed order)
        System.out.println("Elements:");
        for (Integer num : hashSet) {
            System.out.println(num);
        }
        
        // Set operations
        HashSet<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3, 4));
        HashSet<Integer> set2 = new HashSet<>(Arrays.asList(3, 4, 5, 6));
        
        // Union
        HashSet<Integer> union = new HashSet<>(set1);
        union.addAll(set2);
        System.out.println("Union: " + union); // [1, 2, 3, 4, 5, 6]
        
        // Intersection
        HashSet<Integer> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        System.out.println("Intersection: " + intersection); // [3, 4]
        
        // Difference
        HashSet<Integer> difference = new HashSet<>(set1);
        difference.removeAll(set2);
        System.out.println("Difference: " + difference); // [1, 2]
    }
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="concept-section">
                    <h3>🌳 TreeSet: Sorted and Navigable</h3>
                    <p><strong>TreeSet</strong> uses a Red-Black tree (TreeMap internally) to maintain elements in sorted order.</p>
                    
                    <div class="code-example">
                        <h5>TreeSet Advanced Operations:</h5>
                        <pre><code class="language-java">
import java.util.*;

public class TreeSetExample {
    public static void main(String[] args) {
        TreeSet<Integer> treeSet = new TreeSet<>();
        
        // Add elements (automatically sorted)
        treeSet.addAll(Arrays.asList(50, 20, 80, 10, 30, 70, 90));
        System.out.println("TreeSet: " + treeSet);
        // Output: [10, 20, 30, 50, 70, 80, 90]
        
        // NavigableSet operations
        System.out.println("First: " + treeSet.first()); // 10
        System.out.println("Last: " + treeSet.last()); // 90
        System.out.println("Lower than 50: " + treeSet.lower(50)); // 30
        System.out.println("Higher than 50: " + treeSet.higher(50)); // 70
        System.out.println("Floor of 45: " + treeSet.floor(45)); // 30
        System.out.println("Ceiling of 45: " + treeSet.ceiling(45)); // 50
        
        // Range operations
        SortedSet<Integer> headSet = treeSet.headSet(50);
        System.out.println("Head set (< 50): " + headSet); // [10, 20, 30]
        
        SortedSet<Integer> tailSet = treeSet.tailSet(50);
        System.out.println("Tail set (>= 50): " + tailSet); // [50, 70, 80, 90]
        
        NavigableSet<Integer> subSet = treeSet.subSet(20, true, 80, false);
        System.out.println("Sub set [20, 80): " + subSet); // [20, 30, 50, 70]
        
        // Descending iteration
        NavigableSet<Integer> descendingSet = treeSet.descendingSet();
        System.out.println("Descending: " + descendingSet);
        // Output: [90, 80, 70, 50, 30, 20, 10]
        
        // Poll operations
        System.out.println("Poll first: " + treeSet.pollFirst()); // 10
        System.out.println("Poll last: " + treeSet.pollLast()); // 90
        System.out.println("After polling: " + treeSet);
    }
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="concept-section">
                    <h3>🔗 LinkedHashSet: Insertion Order Preserved</h3>
                    <p><strong>LinkedHashSet</strong> maintains insertion order while providing hash table performance.</p>
                    
                    <div class="code-example">
                        <h5>LinkedHashSet Use Cases:</h5>
                        <pre><code class="language-java">
import java.util.*;

public class LinkedHashSetExample {
    public static void main(String[] args) {
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
        
        // Maintains insertion order
        linkedHashSet.add("First");
        linkedHashSet.add("Second");
        linkedHashSet.add("Third");
        linkedHashSet.add("Second"); // Duplicate ignored, order unchanged
        
        System.out.println("LinkedHashSet: " + linkedHashSet);
        // Output: [First, Second, Third]
        
        // Use case: Remove duplicates while preserving order
        List<String> listWithDuplicates = Arrays.asList(
            "apple", "banana", "apple", "cherry", "banana", "date"
        );
        
        LinkedHashSet<String> uniqueItems = new LinkedHashSet<>(listWithDuplicates);
        List<String> uniqueList = new ArrayList<>(uniqueItems);
        
        System.out.println("Original: " + listWithDuplicates);
        System.out.println("Unique (order preserved): " + uniqueList);
        // Output: [apple, banana, cherry, date]
        
        // Performance comparison
        long startTime = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            linkedHashSet.contains("Second");
        }
        long linkedHashSetTime = System.nanoTime() - startTime;
        
        TreeSet<String> treeSet = new TreeSet<>(linkedHashSet);
        startTime = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            treeSet.contains("Second");
        }
        long treeSetTime = System.nanoTime() - startTime;
        
        System.out.println("LinkedHashSet lookup time: " + linkedHashSetTime / 1000 + " μs");
        System.out.println("TreeSet lookup time: " + treeSetTime / 1000 + " μs");
    }
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="performance-section">
                    <h3>⚡ Performance Comparison</h3>
                    
                    <div class="performance-comparison">
                        <table class="performance-table">
                            <tr>
                                <th>Operation</th>
                                <th>HashSet</th>
                                <th>LinkedHashSet</th>
                                <th>TreeSet</th>
                            </tr>
                            <tr>
                                <td>Add</td>
                                <td>O(1) average</td>
                                <td>O(1) average</td>
                                <td>O(log n)</td>
                            </tr>
                            <tr>
                                <td>Remove</td>
                                <td>O(1) average</td>
                                <td>O(1) average</td>
                                <td>O(log n)</td>
                            </tr>
                            <tr>
                                <td>Contains</td>
                                <td>O(1) average</td>
                                <td>O(1) average</td>
                                <td>O(log n)</td>
                            </tr>
                            <tr>
                                <td>Iteration</td>
                                <td>O(n) random</td>
                                <td>O(n) insertion order</td>
                                <td>O(n) sorted</td>
                            </tr>
                            <tr>
                                <td>Memory</td>
                                <td>Lowest</td>
                                <td>Medium (extra links)</td>
                                <td>Highest (tree nodes)</td>
                            </tr>
                        </table>
                    </div>
                </div>
                
                <div class="best-practices">
                    <h3>✅ Best Practices</h3>
                    <ul>
                        <li><strong>Use HashSet when</strong>: Need fastest lookups and don't care about order</li>
                        <li><strong>Use TreeSet when</strong>: Need sorted iteration or range operations</li>
                        <li><strong>Use LinkedHashSet when</strong>: Need to preserve insertion order with fast lookups</li>
                        <li><strong>equals() and hashCode()</strong>: Always override both for custom objects in HashSet/LinkedHashSet</li>
                        <li><strong>Comparable/Comparator</strong>: Ensure consistent ordering for TreeSet</li>
                    </ul>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="set-implementations">
                        <textarea placeholder="Add your personal notes about Set implementations..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(100);
        topic.setSortOrder(3);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        // Create interview questions for Set Implementations
        createSetImplementationsInterviewQuestions(topic);
        
        log.info("✅ Created Set Implementations topic with 35+ interview questions");
    }
    
    private void createHashCollisionResolutionTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Hash Collision Resolution Techniques");
        topic.setDescription("Advanced collision handling: chaining, open addressing, Robin Hood hashing, and performance optimization");
        topic.setContent("""
            <div class="topic-content">
                <h2>🎯 Hash Collision Resolution Techniques</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Understand different collision resolution strategies</li>
                        <li>Implement chaining and open addressing techniques</li>
                        <li>Analyze performance characteristics of each approach</li>
                        <li>Master advanced techniques like Robin Hood hashing</li>
                        <li>Optimize hash table performance for real-world scenarios</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>💥 Hash Collisions: The Problem</h3>
                    <p>Hash collisions occur when different keys produce the same hash value. Even with perfect hash functions, collisions are inevitable due to the <strong>Pigeonhole Principle</strong>.</p>
                    
                    <div class="code-example">
                        <h5>Collision Demonstration:</h5>
                        <pre><code class="language-java">
public class CollisionDemo {
    public static void main(String[] args) {
        // Even good hash functions have collisions
        String str1 = "Aa";
        String str2 = "BB";
        
        System.out.println("str1.hashCode(): " + str1.hashCode()); // 2112
        System.out.println("str2.hashCode(): " + str2.hashCode()); // 2112
        System.out.println("Collision detected: " + (str1.hashCode() == str2.hashCode()));
        
        // Birthday paradox: With just 23 people, 50% chance of collision
        // With hash tables: √(2 * table_size * ln(2)) ≈ 1.2 * √table_size
        int tableSize = 1000;
        int expectedCollisions = (int)(1.2 * Math.sqrt(tableSize));
        System.out.println("Expected collisions for table size " + tableSize + ": " + expectedCollisions);
    }
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="concept-section">
                    <h3>🔗 Separate Chaining</h3>
                    <p>Store colliding elements in linked lists (or other data structures) at each table position.</p>
                    
                    <div class="code-example">
                        <h5>Chaining Implementation:</h5>
                        <pre><code class="language-java">
import java.util.*;

public class ChainingHashTable<K, V> {
    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;
        
        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private Node<K, V>[] table;
    private int size;
    private int capacity;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;
    
    @SuppressWarnings("unchecked")
    public ChainingHashTable(int initialCapacity) {
        this.capacity = initialCapacity;
        this.table = new Node[capacity];
        this.size = 0;
    }
    
    private int hash(K key) {
        if (key == null) return 0;
        return Math.abs(key.hashCode()) % capacity;
    }
    
    public V put(K key, V value) {
        if ((double) size / capacity > LOAD_FACTOR_THRESHOLD) {
            resize();
        }
        
        int index = hash(key);
        Node<K, V> head = table[index];
        
        // Check if key already exists
        Node<K, V> current = head;
        while (current != null) {
            if (Objects.equals(current.key, key)) {
                V oldValue = current.value;
                current.value = value;
                return oldValue;
            }
            current = current.next;
        }
        
        // Add new node at the beginning of chain
        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = head;
        table[index] = newNode;
        size++;
        return null;
    }
    
    public V get(K key) {
        int index = hash(key);
        Node<K, V> current = table[index];
        
        while (current != null) {
            if (Objects.equals(current.key, key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }
    
    public V remove(K key) {
        int index = hash(key);
        Node<K, V> current = table[index];
        Node<K, V> prev = null;
        
        while (current != null) {
            if (Objects.equals(current.key, key)) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    private void resize() {
        Node<K, V>[] oldTable = table;
        capacity *= 2;
        table = new Node[capacity];
        size = 0;
        
        // Rehash all elements
        for (Node<K, V> head : oldTable) {
            Node<K, V> current = head;
            while (current != null) {
                put(current.key, current.value);
                current = current.next;
            }
        }
    }
    
    public void printStatistics() {
        int[] chainLengths = new int[capacity];
        int maxChainLength = 0;
        int nonEmptyBuckets = 0;
        
        for (int i = 0; i < capacity; i++) {
            int length = 0;
            Node<K, V> current = table[i];
            while (current != null) {
                length++;
                current = current.next;
            }
            chainLengths[i] = length;
            if (length > 0) nonEmptyBuckets++;
            maxChainLength = Math.max(maxChainLength, length);
        }
        
        System.out.println("Hash Table Statistics:");
        System.out.println("Size: " + size + ", Capacity: " + capacity);
        System.out.println("Load Factor: " + (double) size / capacity);
        System.out.println("Non-empty buckets: " + nonEmptyBuckets);
        System.out.println("Max chain length: " + maxChainLength);
    }
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="concept-section">
                    <h3>🎯 Open Addressing</h3>
                    <p>Store all elements directly in the table array. When collision occurs, probe for next available slot.</p>
                    
                    <div class="code-example">
                        <h5>Linear Probing Implementation:</h5>
                        <pre><code class="language-java">
public class LinearProbingHashTable<K, V> {
    private static class Entry<K, V> {
        K key;
        V value;
        boolean deleted; // For lazy deletion
        
        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.deleted = false;
        }
    }
    
    private Entry<K, V>[] table;
    private int size;
    private int capacity;
    private static final double LOAD_FACTOR_THRESHOLD = 0.5; // Lower for open addressing
    
    @SuppressWarnings("unchecked")
    public LinearProbingHashTable(int initialCapacity) {
        this.capacity = initialCapacity;
        this.table = new Entry[capacity];
        this.size = 0;
    }
    
    private int hash(K key) {
        if (key == null) return 0;
        return Math.abs(key.hashCode()) % capacity;
    }
    
    public V put(K key, V value) {
        if ((double) size / capacity > LOAD_FACTOR_THRESHOLD) {
            resize();
        }
        
        int index = hash(key);
        
        // Linear probing
        while (table[index] != null && !table[index].deleted) {
            if (Objects.equals(table[index].key, key)) {
                V oldValue = table[index].value;
                table[index].value = value;
                return oldValue;
            }
            index = (index + 1) % capacity; // Wrap around
        }
        
        // Found empty slot or deleted slot
        table[index] = new Entry<>(key, value);
        size++;
        return null;
    }
    
    public V get(K key) {
        int index = hash(key);
        
        while (table[index] != null) {
            if (!table[index].deleted && Objects.equals(table[index].key, key)) {
                return table[index].value;
            }
            index = (index + 1) % capacity;
        }
        return null;
    }
    
    public V remove(K key) {
        int index = hash(key);
        
        while (table[index] != null) {
            if (!table[index].deleted && Objects.equals(table[index].key, key)) {
                V value = table[index].value;
                table[index].deleted = true; // Lazy deletion
                size--;
                return value;
            }
            index = (index + 1) % capacity;
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    private void resize() {
        Entry<K, V>[] oldTable = table;
        capacity *= 2;
        table = new Entry[capacity];
        size = 0;
        
        for (Entry<K, V> entry : oldTable) {
            if (entry != null && !entry.deleted) {
                put(entry.key, entry.value);
            }
        }
    }
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="concept-section">
                    <h3>🏰 Robin Hood Hashing</h3>
                    <p>Advanced open addressing technique that minimizes variance in probe distances.</p>
                    
                    <div class="code-example">
                        <h5>Robin Hood Hashing Concept:</h5>
                        <pre><code class="language-java">
public class RobinHoodHashTable<K, V> {
    private static class Entry<K, V> {
        K key;
        V value;
        int probeDistance;
        
        Entry(K key, V value, int probeDistance) {
            this.key = key;
            this.value = value;
            this.probeDistance = probeDistance;
        }
    }
    
    private Entry<K, V>[] table;
    private int size;
    private int capacity;
    
    public V put(K key, V value) {
        int index = hash(key);
        Entry<K, V> newEntry = new Entry<>(key, value, 0);
        
        while (true) {
            if (table[index] == null) {
                table[index] = newEntry;
                size++;
                return null;
            }
            
            // If existing entry has traveled further, swap (Robin Hood principle)
            if (table[index].probeDistance < newEntry.probeDistance) {
                Entry<K, V> temp = table[index];
                table[index] = newEntry;
                newEntry = temp;
            }
            
            // Check for key match after potential swap
            if (Objects.equals(table[index].key, key)) {
                V oldValue = table[index].value;
                table[index].value = value;
                return oldValue;
            }
            
            index = (index + 1) % capacity;
            newEntry.probeDistance++;
        }
    }
    
    // Robin Hood hashing benefits:
    // 1. Reduces variance in lookup times
    // 2. Better cache performance
    // 3. Bounded worst-case probe distance
    // 4. More uniform distribution of elements
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="performance-section">
                    <h3>⚡ Performance Comparison</h3>
                    
                    <div class="performance-comparison">
                        <table class="performance-table">
                            <tr>
                                <th>Technique</th>
                                <th>Average Case</th>
                                <th>Worst Case</th>
                                <th>Memory Overhead</th>
                                <th>Cache Performance</th>
                            </tr>
                            <tr>
                                <td>Separate Chaining</td>
                                <td>O(1 + α)</td>
                                <td>O(n)</td>
                                <td>High (pointers)</td>
                                <td>Poor (pointer chasing)</td>
                            </tr>
                            <tr>
                                <td>Linear Probing</td>
                                <td>O(1)</td>
                                <td>O(n)</td>
                                <td>Low</td>
                                <td>Excellent (locality)</td>
                            </tr>
                            <tr>
                                <td>Quadratic Probing</td>
                                <td>O(1)</td>
                                <td>O(n)</td>
                                <td>Low</td>
                                <td>Good</td>
                            </tr>
                            <tr>
                                <td>Robin Hood</td>
                                <td>O(1)</td>
                                <td>O(√n)</td>
                                <td>Low</td>
                                <td>Excellent</td>
                            </tr>
                        </table>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="hash-collision-resolution">
                        <textarea placeholder="Add your personal notes about hash collision resolution..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(90);
        topic.setSortOrder(4);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created Hash Collision Resolution topic with 30+ interview questions");
    }
    
    private void createAdvancedHashingTechniquesTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Advanced Hashing Techniques");
        topic.setDescription("Consistent hashing, cuckoo hashing, and distributed hash tables for scalable systems");
        topic.setContent("""
            <div class="topic-content">
                <h2>🎯 Advanced Hashing Techniques</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Master consistent hashing for distributed systems</li>
                        <li>Understand cuckoo hashing for guaranteed O(1) lookups</li>
                        <li>Implement distributed hash tables (DHT)</li>
                        <li>Design hash functions for specific use cases</li>
                        <li>Optimize hashing for real-world scalable applications</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🔄 Consistent Hashing</h3>
                    <p><strong>Consistent Hashing</strong> solves the problem of rehashing when nodes are added/removed in distributed systems.</p>
                    
                    <div class="code-example">
                        <h5>Consistent Hashing Implementation:</h5>
                        <pre><code class="language-java">
import java.util.*;

public class ConsistentHashing<T> {
    private final SortedMap<Long, T> ring = new TreeMap<>();
    private final int virtualNodes;
    
    public ConsistentHashing(int virtualNodes) {
        this.virtualNodes = virtualNodes;
    }
    
    public void addNode(T node) {
        for (int i = 0; i < virtualNodes; i++) {
            long hash = hash(node.toString() + ":" + i);
            ring.put(hash, node);
        }
    }
    
    public void removeNode(T node) {
        for (int i = 0; i < virtualNodes; i++) {
            long hash = hash(node.toString() + ":" + i);
            ring.remove(hash);
        }
    }
    
    public T getNode(String key) {
        if (ring.isEmpty()) return null;
        
        long hash = hash(key);
        SortedMap<Long, T> tailMap = ring.tailMap(hash);
        
        // If no node found after this hash, wrap around to first node
        Long nodeHash = tailMap.isEmpty() ? ring.firstKey() : tailMap.firstKey();
        return ring.get(nodeHash);
    }
    
    private long hash(String input) {
        // Simple hash function (use better hash in production)
        return input.hashCode() & 0x7FFFFFFFL;
    }
    
    public void printRingDistribution(List<String> keys) {
        Map<T, Integer> distribution = new HashMap<>();
        
        for (String key : keys) {
            T node = getNode(key);
            distribution.merge(node, 1, Integer::sum);
        }
        
        System.out.println("Key distribution across nodes:");
        distribution.forEach((node, count) -> 
            System.out.println(node + ": " + count + " keys"));
    }
    
    public static void main(String[] args) {
        ConsistentHashing<String> ch = new ConsistentHashing<>(150); // 150 virtual nodes
        
        // Add servers
        ch.addNode("Server1");
        ch.addNode("Server2");
        ch.addNode("Server3");
        
        // Generate test keys
        List<String> keys = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            keys.add("key" + i);
        }
        
        System.out.println("Initial distribution:");
        ch.printRingDistribution(keys);
        
        // Add new server
        ch.addNode("Server4");
        System.out.println("\\nAfter adding Server4:");
        ch.printRingDistribution(keys);
        
        // Remove server
        ch.removeNode("Server2");
        System.out.println("\\nAfter removing Server2:");
        ch.printRingDistribution(keys);
    }
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="concept-section">
                    <h3>🦆 Cuckoo Hashing</h3>
                    <p><strong>Cuckoo Hashing</strong> guarantees O(1) worst-case lookup time using two hash functions and two tables.</p>
                    
                    <div class="code-example">
                        <h5>Cuckoo Hashing Implementation:</h5>
                        <pre><code class="language-java">
public class CuckooHashTable<K, V> {
    private static class Entry<K, V> {
        K key;
        V value;
        
        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private Entry<K, V>[] table1;
    private Entry<K, V>[] table2;
    private int capacity;
    private int size;
    private static final int MAX_ITERATIONS = 8;
    
    @SuppressWarnings("unchecked")
    public CuckooHashTable(int capacity) {
        this.capacity = capacity;
        this.table1 = new Entry[capacity];
        this.table2 = new Entry[capacity];
        this.size = 0;
    }
    
    private int hash1(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }
    
    private int hash2(K key) {
        return Math.abs(key.hashCode() * 31 + 17) % capacity;
    }
    
    public V get(K key) {
        int pos1 = hash1(key);
        if (table1[pos1] != null && Objects.equals(table1[pos1].key, key)) {
            return table1[pos1].value;
        }
        
        int pos2 = hash2(key);
        if (table2[pos2] != null && Objects.equals(table2[pos2].key, key)) {
            return table2[pos2].value;
        }
        
        return null; // O(1) guaranteed lookup!
    }
    
    public V put(K key, V value) {
        // Check if key already exists
        V existing = get(key);
        if (existing != null) {
            // Update existing value
            int pos1 = hash1(key);
            if (table1[pos1] != null && Objects.equals(table1[pos1].key, key)) {
                table1[pos1].value = value;
                return existing;
            }
            int pos2 = hash2(key);
            if (table2[pos2] != null && Objects.equals(table2[pos2].key, key)) {
                table2[pos2].value = value;
                return existing;
            }
        }
        
        Entry<K, V> newEntry = new Entry<>(key, value);
        if (insertEntry(newEntry)) {
            size++;
            return null;
        } else {
            // Rehash if insertion fails
            rehash();
            insertEntry(newEntry);
            size++;
            return null;
        }
    }
    
    private boolean insertEntry(Entry<K, V> entry) {
        Entry<K, V> current = entry;
        
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            // Try table1
            int pos1 = hash1(current.key);
            if (table1[pos1] == null) {
                table1[pos1] = current;
                return true;
            }
            
            // Evict from table1, try table2
            Entry<K, V> evicted = table1[pos1];
            table1[pos1] = current;
            current = evicted;
            
            int pos2 = hash2(current.key);
            if (table2[pos2] == null) {
                table2[pos2] = current;
                return true;
            }
            
            // Evict from table2, continue loop
            evicted = table2[pos2];
            table2[pos2] = current;
            current = evicted;
        }
        
        return false; // Cycle detected, need rehash
    }
    
    @SuppressWarnings("unchecked")
    private void rehash() {
        Entry<K, V>[] oldTable1 = table1;
        Entry<K, V>[] oldTable2 = table2;
        
        capacity = capacity * 2;
        table1 = new Entry[capacity];
        table2 = new Entry[capacity];
        size = 0;
        
        // Reinsert all entries
        for (Entry<K, V> entry : oldTable1) {
            if (entry != null) {
                put(entry.key, entry.value);
            }
        }
        for (Entry<K, V> entry : oldTable2) {
            if (entry != null) {
                put(entry.key, entry.value);
            }
        }
    }
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="concept-section">
                    <h3>🌐 Distributed Hash Tables (DHT)</h3>
                    <p>DHTs distribute data across multiple nodes in a network, providing scalability and fault tolerance.</p>
                    
                    <div class="code-example">
                        <h5>Simple DHT Implementation:</h5>
                        <pre><code class="language-java">
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DistributedHashTable {
    
    static class Node {
        private final String nodeId;
        private final Map<String, String> storage = new ConcurrentHashMap<>();
        private final Set<Node> neighbors = new HashSet<>();
        
        public Node(String nodeId) {
            this.nodeId = nodeId;
        }
        
        public void put(String key, String value) {
            Node responsibleNode = findResponsibleNode(key);
            responsibleNode.storage.put(key, value);
            
            // Replicate to neighbors for fault tolerance
            for (Node neighbor : responsibleNode.neighbors) {
                neighbor.storage.put(key + "_replica", value);
            }
        }
        
        public String get(String key) {
            Node responsibleNode = findResponsibleNode(key);
            String value = responsibleNode.storage.get(key);
            
            // If not found, try replicas
            if (value == null) {
                for (Node neighbor : responsibleNode.neighbors) {
                    value = neighbor.storage.get(key + "_replica");
                    if (value != null) break;
                }
            }
            
            return value;
        }
        
        private Node findResponsibleNode(String key) {
            // Simplified: use consistent hashing to find responsible node
            long hash = key.hashCode() & 0x7FFFFFFFL;
            // In real DHT, this would use routing table
            return this; // Simplified for demo
        }
        
        public void addNeighbor(Node neighbor) {
            this.neighbors.add(neighbor);
            neighbor.neighbors.add(this);
        }
        
        @Override
        public String toString() {
            return "Node{" + nodeId + ", storage=" + storage.size() + " items}";
        }
    }
    
    public static void main(String[] args) {
        // Create DHT network
        Node node1 = new Node("Node1");
        Node node2 = new Node("Node2");
        Node node3 = new Node("Node3");
        
        // Connect nodes
        node1.addNeighbor(node2);
        node2.addNeighbor(node3);
        node3.addNeighbor(node1);
        
        // Store data
        node1.put("user:123", "Alice");
        node2.put("user:456", "Bob");
        node3.put("user:789", "Charlie");
        
        // Retrieve data
        System.out.println("Retrieved: " + node1.get("user:123"));
        System.out.println("Retrieved: " + node2.get("user:456"));
        System.out.println("Retrieved: " + node3.get("user:789"));
    }
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="performance-section">
                    <h3>⚡ Advanced Hashing Comparison</h3>
                    
                    <div class="performance-comparison">
                        <table class="performance-table">
                            <tr>
                                <th>Technique</th>
                                <th>Lookup Time</th>
                                <th>Insert Time</th>
                                <th>Use Case</th>
                                <th>Complexity</th>
                            </tr>
                            <tr>
                                <td>Consistent Hashing</td>
                                <td>O(log n)</td>
                                <td>O(log n)</td>
                                <td>Distributed systems</td>
                                <td>Medium</td>
                            </tr>
                            <tr>
                                <td>Cuckoo Hashing</td>
                                <td>O(1) worst</td>
                                <td>O(1) amortized</td>
                                <td>Real-time systems</td>
                                <td>High</td>
                            </tr>
                            <tr>
                                <td>DHT</td>
                                <td>O(log n)</td>
                                <td>O(log n)</td>
                                <td>P2P networks</td>
                                <td>Very High</td>
                            </tr>
                        </table>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="advanced-hashing-techniques">
                        <textarea placeholder="Add your personal notes about advanced hashing techniques..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(110);
        topic.setSortOrder(5);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created Advanced Hashing Techniques topic with 45+ interview questions");
    }
    
    // Placeholder for DS Advanced Topics
    private void createDataStructuresAdvancedTopics(LearningModule dsModule) {
        log.info("🔄 Data Structures Advanced Topics - To be implemented in Phase 4");
        // Will be implemented in Phase 4
    }
    
    private void createDatabaseTopics(LearningModule module) {
        // Topic 1: SQL Fundamentals
        Topic sqlFundamentals = new Topic();
        sqlFundamentals.setTitle("SQL Fundamentals");
        sqlFundamentals.setDescription("Core SQL concepts, queries, joins, and data manipulation");
        sqlFundamentals.setContent("""
            <h2>SQL Fundamentals</h2>
            
            <h3>What is SQL?</h3>
            <p>SQL (Structured Query Language) is a standard language for managing and manipulating relational databases. It allows you to create, read, update, and delete data efficiently.</p>
            
            <h3>Basic SQL Operations (CRUD)</h3>
            <ul>
                <li><strong>CREATE</strong>: Insert new data</li>
                <li><strong>READ</strong>: Query and retrieve data</li>
                <li><strong>UPDATE</strong>: Modify existing data</li>
                <li><strong>DELETE</strong>: Remove data</li>
            </ul>
            
            <h3>SQL Query Structure</h3>
            <p>A typical SQL query follows this structure:</p>
            <pre>SELECT columns FROM table WHERE condition ORDER BY column;</pre>
            
            <h3>Types of SQL Joins</h3>
            <ul>
                <li><strong>INNER JOIN</strong>: Returns matching records from both tables</li>
                <li><strong>LEFT JOIN</strong>: Returns all records from left table and matching from right</li>
                <li><strong>RIGHT JOIN</strong>: Returns all records from right table and matching from left</li>
                <li><strong>FULL OUTER JOIN</strong>: Returns all records when there's a match in either table</li>
            </ul>
            """);
        
        sqlFundamentals.setCodeExamples("""
            [
                {
                    "language": "sql",
                    "code": "-- Creating a table\\nCREATE TABLE employees (\\n    id SERIAL PRIMARY KEY,\\n    name VARCHAR(100) NOT NULL,\\n    email VARCHAR(100) UNIQUE,\\n    department VARCHAR(50),\\n    salary DECIMAL(10,2),\\n    hire_date DATE\\n);\\n\\n-- Inserting data\\nINSERT INTO employees (name, email, department, salary, hire_date)\\nVALUES \\n    ('John Doe', 'john@company.com', 'Engineering', 75000.00, '2023-01-15'),\\n    ('Jane Smith', 'jane@company.com', 'Marketing', 65000.00, '2023-02-01'),\\n    ('Bob Johnson', 'bob@company.com', 'Engineering', 80000.00, '2022-12-10');",
                    "explanation": "Basic table creation and data insertion with proper data types and constraints."
                },
                {
                    "language": "sql",
                    "code": "-- Basic SELECT queries\\nSELECT * FROM employees;\\n\\n-- Filtering with WHERE\\nSELECT name, salary FROM employees \\nWHERE department = 'Engineering' AND salary > 70000;\\n\\n-- Sorting results\\nSELECT name, salary FROM employees \\nORDER BY salary DESC;\\n\\n-- Aggregate functions\\nSELECT department, COUNT(*) as employee_count, AVG(salary) as avg_salary\\nFROM employees \\nGROUP BY department\\nHAVING COUNT(*) > 1;",
                    "explanation": "Common SELECT operations with filtering, sorting, and aggregation."
                },
                {
                    "language": "sql",
                    "code": "-- JOIN operations\\nCREATE TABLE departments (\\n    id SERIAL PRIMARY KEY,\\n    name VARCHAR(50),\\n    manager_id INTEGER\\n);\\n\\n-- INNER JOIN\\nSELECT e.name, e.salary, d.name as department_name\\nFROM employees e\\nINNER JOIN departments d ON e.department = d.name;\\n\\n-- LEFT JOIN to include all employees\\nSELECT e.name, e.salary, d.name as department_name\\nFROM employees e\\nLEFT JOIN departments d ON e.department = d.name;",
                    "explanation": "JOIN operations to combine data from multiple tables."
                }
            ]
            """);
        
        sqlFundamentals.setKeyConcepts("""
            ["SELECT", "INSERT", "UPDATE", "DELETE", "JOIN", "WHERE", "GROUP BY", "ORDER BY", "Aggregate Functions"]
            """);
        
        sqlFundamentals.setTopicType(Topic.TopicType.THEORY);
        sqlFundamentals.setEstimatedMinutes(120);
        sqlFundamentals.setSortOrder(1);
        sqlFundamentals.setDifficultyLevel(LearningModule.DifficultyLevel.BEGINNER);
        sqlFundamentals.setModule(module);
        
        topicRepository.save(sqlFundamentals);
        createSQLInterviewQuestions(sqlFundamentals);
        
        // Topic 2: Database Design and Normalization
        Topic dbDesign = new Topic();
        dbDesign.setTitle("Database Design and Normalization");
        dbDesign.setDescription("Entity-Relationship modeling, normalization forms, and database design principles");
        dbDesign.setContent("""
            <h2>Database Design and Normalization</h2>
            
            <h3>Entity-Relationship (ER) Modeling</h3>
            <p>ER modeling is a method for designing databases by identifying entities, attributes, and relationships.</p>
            
            <h4>Key Components:</h4>
            <ul>
                <li><strong>Entities</strong>: Objects or concepts (e.g., Customer, Order, Product)</li>
                <li><strong>Attributes</strong>: Properties of entities (e.g., Customer name, Order date)</li>
                <li><strong>Relationships</strong>: Connections between entities (e.g., Customer places Order)</li>
            </ul>
            
            <h3>Database Normalization</h3>
            <p>Normalization is the process of organizing data to reduce redundancy and improve data integrity.</p>
            
            <h4>Normal Forms:</h4>
            <ul>
                <li><strong>1NF (First Normal Form)</strong>: Eliminate repeating groups</li>
                <li><strong>2NF (Second Normal Form)</strong>: Eliminate partial dependencies</li>
                <li><strong>3NF (Third Normal Form)</strong>: Eliminate transitive dependencies</li>
                <li><strong>BCNF (Boyce-Codd Normal Form)</strong>: Stricter version of 3NF</li>
            </ul>
            
            <h3>Design Principles</h3>
            <ul>
                <li>Identify entities and relationships clearly</li>
                <li>Choose appropriate data types</li>
                <li>Define primary and foreign keys properly</li>
                <li>Consider indexing for performance</li>
                <li>Plan for scalability</li>
            </ul>
            """);
        
        dbDesign.setCodeExamples("""
            [
                {
                    "language": "sql",
                    "code": "-- Example: E-commerce Database Design\\n\\n-- Customers table\\nCREATE TABLE customers (\\n    customer_id SERIAL PRIMARY KEY,\\n    first_name VARCHAR(50) NOT NULL,\\n    last_name VARCHAR(50) NOT NULL,\\n    email VARCHAR(100) UNIQUE NOT NULL,\\n    phone VARCHAR(20),\\n    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP\\n);\\n\\n-- Products table\\nCREATE TABLE products (\\n    product_id SERIAL PRIMARY KEY,\\n    name VARCHAR(100) NOT NULL,\\n    description TEXT,\\n    price DECIMAL(10,2) NOT NULL,\\n    stock_quantity INTEGER DEFAULT 0,\\n    category_id INTEGER,\\n    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP\\n);\\n\\n-- Orders table\\nCREATE TABLE orders (\\n    order_id SERIAL PRIMARY KEY,\\n    customer_id INTEGER REFERENCES customers(customer_id),\\n    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\\n    total_amount DECIMAL(10,2),\\n    status VARCHAR(20) DEFAULT 'pending'\\n);",
                    "explanation": "Well-designed tables with proper relationships and constraints."
                },
                {
                    "language": "sql",
                    "code": "-- Normalization example: Before (1NF violation)\\nCREATE TABLE orders_bad (\\n    order_id INTEGER,\\n    customer_name VARCHAR(100),\\n    products VARCHAR(500), -- Multiple products in one field\\n    quantities VARCHAR(100) -- Multiple quantities in one field\\n);\\n\\n-- After normalization (proper design)\\nCREATE TABLE order_items (\\n    order_item_id SERIAL PRIMARY KEY,\\n    order_id INTEGER REFERENCES orders(order_id),\\n    product_id INTEGER REFERENCES products(product_id),\\n    quantity INTEGER NOT NULL,\\n    unit_price DECIMAL(10,2) NOT NULL\\n);",
                    "explanation": "Example showing how to fix normalization violations."
                }
            ]
            """);
        
        dbDesign.setKeyConcepts("""
            ["ER Modeling", "Normalization", "Primary Keys", "Foreign Keys", "Relationships", "Data Integrity"]
            """);
        
        dbDesign.setTopicType(Topic.TopicType.THEORY);
        dbDesign.setEstimatedMinutes(90);
        dbDesign.setSortOrder(2);
        dbDesign.setDifficultyLevel(LearningModule.DifficultyLevel.INTERMEDIATE);
        dbDesign.setModule(module);
        
        topicRepository.save(dbDesign);
        
        // Topic 3: NoSQL Databases
        Topic noSqlTopic = new Topic();
        noSqlTopic.setTitle("NoSQL Databases");
        noSqlTopic.setDescription("Document, Key-Value, Column-family, and Graph databases");
        noSqlTopic.setContent("""
            <h2>NoSQL Databases</h2>
            
            <h3>What is NoSQL?</h3>
            <p>NoSQL databases are non-relational databases designed to handle large volumes of unstructured or semi-structured data. They offer flexibility, scalability, and performance for modern applications.</p>
            
            <h3>Types of NoSQL Databases</h3>
            
            <h4>1. Document Databases (MongoDB, CouchDB)</h4>
            <ul>
                <li>Store data as documents (JSON, BSON)</li>
                <li>Flexible schema</li>
                <li>Good for content management, catalogs</li>
            </ul>
            
            <h4>2. Key-Value Stores (Redis, DynamoDB)</h4>
            <ul>
                <li>Simple key-value pairs</li>
                <li>Extremely fast access</li>
                <li>Good for caching, session storage</li>
            </ul>
            
            <h4>3. Column-Family (Cassandra, HBase)</h4>
            <ul>
                <li>Data stored in column families</li>
                <li>Highly scalable</li>
                <li>Good for time-series data, IoT</li>
            </ul>
            
            <h4>4. Graph Databases (Neo4j, Amazon Neptune)</h4>
            <ul>
                <li>Data stored as nodes and relationships</li>
                <li>Excellent for connected data</li>
                <li>Good for social networks, recommendations</li>
            </ul>
            
            <h3>When to Use NoSQL</h3>
            <ul>
                <li>Rapid development with changing requirements</li>
                <li>Large scale and high performance needs</li>
                <li>Unstructured or semi-structured data</li>
                <li>Horizontal scaling requirements</li>
            </ul>
            """);
        
        noSqlTopic.setCodeExamples("""
            [
                {
                    "language": "javascript",
                    "code": "// MongoDB Example\\n// Insert document\\ndb.users.insertOne({\\n  name: \\"John Doe\\",\\n  email: \\"john@example.com\\",\\n  age: 30,\\n  address: {\\n    street: \\"123 Main St\\",\\n    city: \\"New York\\",\\n    zipCode: \\"10001\\"\\n  },\\n  hobbies: [\\"reading\\", \\"swimming\\", \\"coding\\"]\\n});\\n\\n// Query documents\\ndb.users.find({ age: { $gte: 25 } });\\n\\n// Update document\\ndb.users.updateOne(\\n  { email: \\"john@example.com\\" },\\n  { $set: { age: 31 }, $push: { hobbies: \\"hiking\\" } }\\n);",
                    "explanation": "MongoDB document operations showing flexible schema and nested data."
                },
                {
                    "language": "javascript",
                    "code": "// Redis Key-Value Example\\n// Set key-value pairs\\nredis.set(\\"user:1001\\", JSON.stringify({\\n  name: \\"John Doe\\",\\n  email: \\"john@example.com\\"\\n}));\\n\\n// Set with expiration\\nredis.setex(\\"session:abc123\\", 3600, \\"user_data\\");\\n\\n// Get value\\nconst userData = redis.get(\\"user:1001\\");\\n\\n// Hash operations\\nredis.hset(\\"user:1001\\", \\"name\\", \\"John Doe\\");\\nredis.hset(\\"user:1001\\", \\"email\\", \\"john@example.com\\");\\nredis.hgetall(\\"user:1001\\");",
                    "explanation": "Redis operations for caching and session management."
                }
            ]
            """);
        
        noSqlTopic.setKeyConcepts("""
            ["Document Databases", "Key-Value Stores", "Column-Family", "Graph Databases", "Scalability", "Flexible Schema"]
            """);
        
        noSqlTopic.setTopicType(Topic.TopicType.THEORY);
        noSqlTopic.setEstimatedMinutes(75);
        noSqlTopic.setSortOrder(3);
        noSqlTopic.setDifficultyLevel(LearningModule.DifficultyLevel.INTERMEDIATE);
        noSqlTopic.setModule(module);
        
        topicRepository.save(noSqlTopic);
        
        // Topic 4: Database Performance and Optimization
        Topic performanceTopic = new Topic();
        performanceTopic.setTitle("Database Performance and Optimization");
        performanceTopic.setDescription("Indexing, query optimization, and performance tuning techniques");
        performanceTopic.setContent("""
            <h2>Database Performance and Optimization</h2>
            
            <h3>Database Indexing</h3>
            <p>Indexes are data structures that improve query performance by creating shortcuts to data.</p>
            
            <h4>Types of Indexes:</h4>
            <ul>
                <li><strong>Primary Index</strong>: Automatically created for primary keys</li>
                <li><strong>Secondary Index</strong>: Created on non-primary key columns</li>
                <li><strong>Composite Index</strong>: Covers multiple columns</li>
                <li><strong>Unique Index</strong>: Ensures uniqueness and improves performance</li>
                <li><strong>Partial Index</strong>: Index on subset of data</li>
            </ul>
            
            <h3>Query Optimization Techniques</h3>
            <ul>
                <li>Use appropriate WHERE clauses</li>
                <li>Avoid SELECT * when possible</li>
                <li>Use LIMIT for large result sets</li>
                <li>Optimize JOIN operations</li>
                <li>Use EXISTS instead of IN for subqueries</li>
                <li>Analyze query execution plans</li>
            </ul>
            
            <h3>Performance Monitoring</h3>
            <ul>
                <li>Query execution time analysis</li>
                <li>Index usage statistics</li>
                <li>Database connection monitoring</li>
                <li>Resource utilization tracking</li>
            </ul>
            """);
        
        performanceTopic.setCodeExamples("""
            [
                {
                    "language": "sql",
                    "code": "-- Creating indexes for performance\\n\\n-- Single column index\\nCREATE INDEX idx_employee_department ON employees(department);\\n\\n-- Composite index\\nCREATE INDEX idx_employee_dept_salary ON employees(department, salary);\\n\\n-- Unique index\\nCREATE UNIQUE INDEX idx_employee_email ON employees(email);\\n\\n-- Partial index (PostgreSQL)\\nCREATE INDEX idx_active_employees ON employees(name) \\nWHERE status = 'active';\\n\\n-- Analyzing index usage\\nEXPLAIN ANALYZE SELECT * FROM employees \\nWHERE department = 'Engineering' AND salary > 70000;",
                    "explanation": "Various types of indexes and how to analyze their effectiveness."
                },
                {
                    "language": "sql",
                    "code": "-- Query optimization examples\\n\\n-- Bad: Using SELECT *\\nSELECT * FROM employees WHERE department = 'Engineering';\\n\\n-- Good: Select only needed columns\\nSELECT name, salary FROM employees WHERE department = 'Engineering';\\n\\n-- Bad: Using functions in WHERE clause\\nSELECT * FROM employees WHERE UPPER(name) = 'JOHN DOE';\\n\\n-- Good: Store data in consistent format\\nSELECT * FROM employees WHERE name = 'John Doe';\\n\\n-- Using EXISTS instead of IN\\nSELECT * FROM employees e\\nWHERE EXISTS (\\n    SELECT 1 FROM projects p \\n    WHERE p.employee_id = e.id\\n);",
                    "explanation": "Common query optimization techniques for better performance."
                }
            ]
            """);
        
        performanceTopic.setKeyConcepts("""
            ["Indexing", "Query Optimization", "Execution Plans", "Performance Monitoring", "Database Tuning"]
            """);
        
        performanceTopic.setTopicType(Topic.TopicType.PRACTICAL);
        performanceTopic.setEstimatedMinutes(100);
        performanceTopic.setSortOrder(4);
        performanceTopic.setDifficultyLevel(LearningModule.DifficultyLevel.ADVANCED);
        performanceTopic.setModule(module);
        
        topicRepository.save(performanceTopic);
    }
    
    private void createSQLInterviewQuestions(Topic topic) {
        // Question 1: SQL Joins
        InterviewQuestion q1 = new InterviewQuestion();
        q1.setTitle("Explain different types of SQL JOINs with examples");
        q1.setDescription("Describe INNER JOIN, LEFT JOIN, RIGHT JOIN, and FULL OUTER JOIN. Provide examples and explain when to use each type.");
        q1.setSolution("""
            <h3>SQL JOIN Types</h3>
            
            <h4>1. INNER JOIN</h4>
            <p>Returns only matching records from both tables.</p>
            <p><strong>Use case:</strong> When you need only records that exist in both tables.</p>
            
            <h4>2. LEFT JOIN (LEFT OUTER JOIN)</h4>
            <p>Returns all records from the left table and matching records from the right table.</p>
            <p><strong>Use case:</strong> When you want all records from the main table, even if no match exists.</p>
            
            <h4>3. RIGHT JOIN (RIGHT OUTER JOIN)</h4>
            <p>Returns all records from the right table and matching records from the left table.</p>
            <p><strong>Use case:</strong> Less common, similar to LEFT JOIN but from the other perspective.</p>
            
            <h4>4. FULL OUTER JOIN</h4>
            <p>Returns all records when there's a match in either table.</p>
            <p><strong>Use case:</strong> When you need all records from both tables, regardless of matches.</p>
            """);
        
        q1.setCodeExamples("""
            [
                {
                    "language": "sql",
                    "code": "-- Sample tables\\nCREATE TABLE customers (id INT, name VARCHAR(50));\\nCREATE TABLE orders (id INT, customer_id INT, amount DECIMAL(10,2));\\n\\nINSERT INTO customers VALUES (1, 'Alice'), (2, 'Bob'), (3, 'Charlie');\\nINSERT INTO orders VALUES (1, 1, 100.00), (2, 1, 200.00), (3, 2, 150.00);\\n\\n-- INNER JOIN: Only customers with orders\\nSELECT c.name, o.amount\\nFROM customers c\\nINNER JOIN orders o ON c.id = o.customer_id;\\n-- Result: Alice (100.00), Alice (200.00), Bob (150.00)\\n\\n-- LEFT JOIN: All customers, with or without orders\\nSELECT c.name, o.amount\\nFROM customers c\\nLEFT JOIN orders o ON c.id = o.customer_id;\\n-- Result: Alice (100.00), Alice (200.00), Bob (150.00), Charlie (NULL)",
                    "explanation": "Practical examples showing the difference between INNER and LEFT JOINs."
                }
            ]
            """);
        
        q1.setHints("""
            ["Think about what happens to unmatched records", "Consider the business requirement", "Remember NULL values in outer joins"]
            """);
        
        q1.setDifficulty(InterviewQuestion.Difficulty.MEDIUM);
        q1.setCategory(InterviewQuestion.QuestionCategory.DATABASE);
        q1.setCompany(InterviewQuestion.Company.MICROSOFT);
        q1.setFrequencyScore(9);
        q1.setEstimatedTimeMinutes(15);
        q1.setTopic(topic);
        
        questionRepository.save(q1);
        
        // Question 2: Database Normalization
        InterviewQuestion q2 = new InterviewQuestion();
        q2.setTitle("What is database normalization and why is it important?");
        q2.setDescription("Explain the concept of database normalization, describe the first three normal forms, and provide an example of normalizing a denormalized table.");
        q2.setSolution("""
            <h3>Database Normalization</h3>
            
            <p><strong>Definition:</strong> Normalization is the process of organizing data in a database to reduce redundancy and improve data integrity.</p>
            
            <h4>Benefits:</h4>
            <ul>
                <li>Eliminates data redundancy</li>
                <li>Reduces storage space</li>
                <li>Prevents data anomalies (insert, update, delete)</li>
                <li>Improves data consistency</li>
                <li>Makes maintenance easier</li>
            </ul>
            
            <h4>Normal Forms:</h4>
            
            <h5>1NF (First Normal Form):</h5>
            <ul>
                <li>Each column contains atomic (indivisible) values</li>
                <li>No repeating groups or arrays</li>
                <li>Each row is unique</li>
            </ul>
            
            <h5>2NF (Second Normal Form):</h5>
            <ul>
                <li>Must be in 1NF</li>
                <li>No partial dependencies on composite primary keys</li>
                <li>Non-key attributes depend on the entire primary key</li>
            </ul>
            
            <h5>3NF (Third Normal Form):</h5>
            <ul>
                <li>Must be in 2NF</li>
                <li>No transitive dependencies</li>
                <li>Non-key attributes depend only on the primary key</li>
            </ul>
            """);
        
        q2.setCodeExamples("""
            [
                {
                    "language": "sql",
                    "code": "-- Unnormalized table (violates 1NF)\\nCREATE TABLE student_courses_bad (\\n    student_id INT,\\n    student_name VARCHAR(50),\\n    courses VARCHAR(200), -- Multiple courses in one field\\n    instructor_names VARCHAR(200) -- Multiple instructors\\n);\\n\\n-- 1NF: Separate rows for each course\\nCREATE TABLE student_courses_1nf (\\n    student_id INT,\\n    student_name VARCHAR(50),\\n    course_id INT,\\n    course_name VARCHAR(50),\\n    instructor_name VARCHAR(50)\\n);\\n\\n-- 3NF: Separate tables to eliminate redundancy\\nCREATE TABLE students (\\n    student_id INT PRIMARY KEY,\\n    student_name VARCHAR(50)\\n);\\n\\nCREATE TABLE courses (\\n    course_id INT PRIMARY KEY,\\n    course_name VARCHAR(50),\\n    instructor_name VARCHAR(50)\\n);\\n\\nCREATE TABLE enrollments (\\n    student_id INT,\\n    course_id INT,\\n    PRIMARY KEY (student_id, course_id),\\n    FOREIGN KEY (student_id) REFERENCES students(student_id),\\n    FOREIGN KEY (course_id) REFERENCES courses(course_id)\\n);",
                    "explanation": "Example showing progression from unnormalized to 3NF."
                }
            ]
            """);
        
        q2.setDifficulty(InterviewQuestion.Difficulty.MEDIUM);
        q2.setCategory(InterviewQuestion.QuestionCategory.DATABASE);
        q2.setCompany(InterviewQuestion.Company.AMAZON);
        q2.setFrequencyScore(8);
        q2.setEstimatedTimeMinutes(20);
        q2.setTopic(topic);
        
        questionRepository.save(q2);
    }   
 
    private void createTreeMapInterviewQuestions(Topic topic) {
        List<InterviewQuestion> questions = Arrays.asList(
            // TreeMap vs HashMap comparison questions
            createInterviewQuestion(
                "TreeMap vs HashMap: When to Use Which?",
                "Explain the key differences between TreeMap and HashMap. When would you choose TreeMap over HashMap?",
                "MEDIUM",
                "GOOGLE",
                Arrays.asList("DATA_STRUCTURES", "JAVA"),
                """
                **Key Differences:**
                
                **TreeMap:**
                - Red-Black tree implementation
                - O(log n) for get/put/remove operations
                - Maintains sorted order of keys
                - Implements NavigableMap interface
                - No null keys allowed
                - Higher memory overhead
                
                **HashMap:**
                - Hash table implementation
                - O(1) average for get/put/remove operations
                - No ordering guarantee
                - Allows one null key
                - Lower memory overhead
                
                **Use TreeMap when:**
                - Need sorted iteration over keys
                - Frequent range queries (subMap, headMap, tailMap)
                - Need NavigableMap operations (lowerKey, higherKey, etc.)
                - Consistent ordering is required
                
                **Use HashMap when:**
                - Need fastest possible lookups
                - Don't care about key ordering
                - Memory usage is critical
                - Null keys are needed
                
                **Code Example:**
                ```java
                // TreeMap for sorted operations
                TreeMap<String, Integer> treeMap = new TreeMap<>();
                treeMap.put("Charlie", 3);
                treeMap.put("Alice", 1);
                treeMap.put("Bob", 2);
                System.out.println(treeMap); // {Alice=1, Bob=2, Charlie=3}
                
                // Range operations
                SortedMap<String, Integer> range = treeMap.subMap("Alice", "Charlie");
                
                // HashMap for fast lookups
                HashMap<String, Integer> hashMap = new HashMap<>();
                hashMap.put(null, 0); // Allowed in HashMap
                ```
                """,
                Arrays.asList(
                    "What is the time complexity of TreeMap operations?",
                    "How does TreeMap maintain sorted order?",
                    "Can TreeMap have duplicate keys?",
                    "What happens if you try to put a null key in TreeMap?"
                ),
                topic
            ),
            
            // Red-Black tree implementation question
            createInterviewQuestion(
                "Red-Black Tree Properties in TreeMap",
                "Explain the Red-Black tree properties that TreeMap uses to maintain balance. How do these properties ensure O(log n) operations?",
                "HARD",
                "AMAZON",
                Arrays.asList("DATA_STRUCTURES", "ALGORITHMS"),
                """
                **Red-Black Tree Properties:**
                
                1. **Every node is either red or black**
                2. **Root is always black**
                3. **Red nodes cannot have red children** (no two red nodes adjacent)
                4. **Every path from root to null has same number of black nodes** (black-height property)
                5. **All leaves (null nodes) are black**
                
                **How these ensure O(log n):**
                - Properties 3 and 4 ensure the tree is approximately balanced
                - The longest path (alternating red-black) is at most twice the shortest path (all black)
                - Maximum height is 2 * log₂(n + 1), guaranteeing O(log n) operations
                
                **Balancing Operations:**
                ```java
                // Simplified rotation for balancing
                private void rotateLeft(Node p) {
                    if (p != null) {
                        Node r = p.right;
                        p.right = r.left;
                        if (r.left != null) r.left.parent = p;
                        r.parent = p.parent;
                        if (p.parent == null) root = r;
                        else if (p.parent.left == p) p.parent.left = r;
                        else p.parent.right = r;
                        r.left = p;
                        p.parent = r;
                    }
                }
                
                // Color fixing after insertion
                private void fixAfterInsertion(Node x) {
                    x.color = RED; // New nodes are red
                    
                    while (x != null && x != root && x.parent.color == RED) {
                        // Rebalancing logic based on uncle's color
                        // and position of violation
                    }
                    root.color = BLACK; // Root is always black
                }
                ```
                
                **Why Red-Black over AVL:**
                - Fewer rotations during insertion/deletion
                - Better for applications with frequent modifications
                - TreeMap prioritizes modification performance over strict balancing
                """,
                Arrays.asList(
                    "What is the maximum height of a Red-Black tree with n nodes?",
                    "Why are new nodes initially colored red?",
                    "How many rotations are needed for Red-Black tree insertion?",
                    "What is the black-height of a Red-Black tree?"
                ),
                topic
            ),
            
            // NavigableMap interface question
            createInterviewQuestion(
                "NavigableMap Interface Operations",
                "Implement a method that finds all entries in a TreeMap within a given range [start, end] and returns them in descending order.",
                "MEDIUM",
                "META",
                Arrays.asList("DATA_STRUCTURES", "JAVA"),
                """
                **Solution using NavigableMap operations:**
                
                ```java
                import java.util.*;
                
                public class RangeQuery {
                    public static <K extends Comparable<K>, V> List<Map.Entry<K, V>> 
                           getEntriesInRangeDescending(TreeMap<K, V> map, K start, K end) {
                        
                        List<Map.Entry<K, V>> result = new ArrayList<>();
                        
                        // Method 1: Using subMap and descendingMap
                        NavigableMap<K, V> rangeMap = map.subMap(start, true, end, true);
                        NavigableMap<K, V> descendingRange = rangeMap.descendingMap();
                        
                        for (Map.Entry<K, V> entry : descendingRange.entrySet()) {
                            result.add(entry);
                        }
                        
                        return result;
                    }
                    
                    // Alternative implementation using navigation methods
                    public static <K extends Comparable<K>, V> List<Map.Entry<K, V>> 
                           getEntriesInRangeDescendingAlt(TreeMap<K, V> map, K start, K end) {
                        
                        List<Map.Entry<K, V>> result = new ArrayList<>();
                        
                        // Start from the highest key <= end
                        Map.Entry<K, V> current = map.floorEntry(end);
                        
                        while (current != null && current.getKey().compareTo(start) >= 0) {
                            result.add(current);
                            current = map.lowerEntry(current.getKey());
                        }
                        
                        return result;
                    }
                    
                    public static void main(String[] args) {
                        TreeMap<Integer, String> map = new TreeMap<>();
                        map.put(10, "Ten");
                        map.put(20, "Twenty");
                        map.put(30, "Thirty");
                        map.put(40, "Forty");
                        map.put(50, "Fifty");
                        
                        List<Map.Entry<Integer, String>> range = 
                            getEntriesInRangeDescending(map, 15, 45);
                        
                        System.out.println("Entries in range [15, 45] (descending):");
                        range.forEach(entry -> 
                            System.out.println(entry.getKey() + " -> " + entry.getValue()));
                        // Output: 40 -> Forty, 30 -> Thirty, 20 -> Twenty
                    }
                }
                ```
                
                **Key NavigableMap Methods Used:**
                - `subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive)`
                - `descendingMap()` - returns reverse-ordered view
                - `floorEntry(K key)` - largest key <= given key
                - `lowerEntry(K key)` - largest key < given key
                - `ceilingEntry(K key)` - smallest key >= given key
                - `higherEntry(K key)` - smallest key > given key
                
                **Time Complexity:** O(log n + k) where k is the number of entries in range
                """,
                Arrays.asList(
                    "What's the difference between floorEntry and lowerEntry?",
                    "How does descendingMap() work internally?",
                    "What happens if start > end in subMap?",
                    "Can you modify the original map through a subMap view?"
                ),
                topic
            ),
            
            // Custom Comparator question
            createInterviewQuestion(
                "Custom Comparator for Complex Sorting",
                "Design a TreeMap that stores Employee objects sorted by salary (descending), then by name (ascending), then by hire date (most recent first). Handle null values appropriately.",
                "MEDIUM",
                "MICROSOFT",
                Arrays.asList("DATA_STRUCTURES", "JAVA"),
                """
                **Complete Solution with Custom Comparator:**
                
                ```java
                import java.time.LocalDate;
                import java.util.*;
                
                class Employee {
                    private String name;
                    private Integer salary;
                    private LocalDate hireDate;
                    
                    public Employee(String name, Integer salary, LocalDate hireDate) {
                        this.name = name;
                        this.salary = salary;
                        this.hireDate = hireDate;
                    }
                    
                    // Getters
                    public String getName() { return name; }
                    public Integer getSalary() { return salary; }
                    public LocalDate getHireDate() { return hireDate; }
                    
                    @Override
                    public String toString() {
                        return String.format("Employee{name='%s', salary=%d, hireDate=%s}", 
                                           name, salary, hireDate);
                    }
                }
                
                public class EmployeeTreeMap {
                    public static void main(String[] args) {
                        // Custom comparator with null handling
                        Comparator<Employee> employeeComparator = (e1, e2) -> {
                            // Handle null objects
                            if (e1 == null && e2 == null) return 0;
                            if (e1 == null) return 1;  // nulls last
                            if (e2 == null) return -1;
                            
                            // 1. Compare by salary (descending) - handle nulls
                            Integer sal1 = e1.getSalary();
                            Integer sal2 = e2.getSalary();
                            
                            if (sal1 == null && sal2 == null) {
                                // Both null salaries, continue to next criteria
                            } else if (sal1 == null) {
                                return 1; // null salary goes last
                            } else if (sal2 == null) {
                                return -1;
                            } else {
                                int salaryCompare = Integer.compare(sal2, sal1); // Descending
                                if (salaryCompare != 0) return salaryCompare;
                            }
                            
                            // 2. Compare by name (ascending) - handle nulls
                            String name1 = e1.getName();
                            String name2 = e2.getName();
                            
                            if (name1 == null && name2 == null) {
                                // Both null names, continue to next criteria
                            } else if (name1 == null) {
                                return 1; // null name goes last
                            } else if (name2 == null) {
                                return -1;
                            } else {
                                int nameCompare = name1.compareTo(name2); // Ascending
                                if (nameCompare != 0) return nameCompare;
                            }
                            
                            // 3. Compare by hire date (most recent first) - handle nulls
                            LocalDate date1 = e1.getHireDate();
                            LocalDate date2 = e2.getHireDate();
                            
                            if (date1 == null && date2 == null) return 0;
                            if (date1 == null) return 1; // null date goes last
                            if (date2 == null) return -1;
                            
                            return date2.compareTo(date1); // Descending (most recent first)
                        };
                        
                        // Alternative using Comparator.comparing() with null handling
                        Comparator<Employee> alternativeComparator = 
                            Comparator.<Employee, Integer>comparing(
                                Employee::getSalary, 
                                Comparator.nullsLast(Comparator.reverseOrder())
                            )
                            .thenComparing(
                                Employee::getName, 
                                Comparator.nullsLast(Comparator.naturalOrder())
                            )
                            .thenComparing(
                                Employee::getHireDate, 
                                Comparator.nullsLast(Comparator.reverseOrder())
                            );
                        
                        TreeMap<Employee, String> employeeMap = new TreeMap<>(employeeComparator);
                        
                        // Add employees
                        employeeMap.put(new Employee("Alice", 90000, LocalDate.of(2020, 1, 15)), "Engineering");
                        employeeMap.put(new Employee("Bob", 90000, LocalDate.of(2019, 6, 10)), "Engineering");
                        employeeMap.put(new Employee("Charlie", 95000, LocalDate.of(2021, 3, 20)), "Management");
                        employeeMap.put(new Employee("Alice", 85000, LocalDate.of(2018, 9, 5)), "Marketing");
                        employeeMap.put(new Employee("David", null, LocalDate.of(2022, 1, 1)), "Intern");
                        
                        System.out.println("Employees sorted by salary(desc), name(asc), hireDate(recent first):");
                        employeeMap.forEach((employee, department) -> 
                            System.out.println(employee + " -> " + department));
                    }
                }
                ```
                
                **Key Points:**
                1. **Null Handling**: Always check for null values in comparators
                2. **Consistent Ordering**: Ensure compareTo() is consistent with equals()
                3. **Multiple Criteria**: Use thenComparing() for cleaner code
                4. **Performance**: Custom comparators should be efficient (O(1) comparisons)
                
                **Output:**
                ```
                Employee{name='Charlie', salary=95000, hireDate=2021-03-20} -> Management
                Employee{name='Alice', salary=90000, hireDate=2020-01-15} -> Engineering
                Employee{name='Bob', salary=90000, hireDate=2019-06-10} -> Engineering
                Employee{name='Alice', salary=85000, hireDate=2018-09-05} -> Marketing
                Employee{name='David', salary=null, hireDate=2022-01-01} -> Intern
                ```
                """,
                Arrays.asList(
                    "What happens if compareTo() is not consistent with equals()?",
                    "How do you handle null values in Comparator.comparing()?",
                    "Can you change the comparison logic after TreeMap creation?",
                    "What's the performance impact of complex comparators?"
                ),
                topic
            ),
            
            // Performance optimization question
            createInterviewQuestion(
                "TreeMap Performance Optimization",
                "You have a TreeMap with 1 million entries and need to perform frequent range queries. The current implementation is too slow. How would you optimize it?",
                "HARD",
                "NETFLIX",
                Arrays.asList("PERFORMANCE", "DATA_STRUCTURES"),
                """
                **Performance Optimization Strategies:**
                
                **1. Use Appropriate Data Structure:**
                ```java
                // For frequent range queries, TreeMap is actually optimal
                // But consider these alternatives for specific use cases:
                
                // ConcurrentSkipListMap for concurrent access
                ConcurrentSkipListMap<Integer, String> concurrentMap = new ConcurrentSkipListMap<>();
                
                // Custom B-Tree for disk-based storage
                // Or use database with proper indexing
                ```
                
                **2. Optimize Range Query Implementation:**
                ```java
                public class OptimizedRangeQueries {
                    private TreeMap<Integer, String> map = new TreeMap<>();
                    
                    // Efficient range query with streaming
                    public Stream<Map.Entry<Integer, String>> getRangeStream(int start, int end) {
                        return map.subMap(start, true, end, true)
                                  .entrySet()
                                  .stream();
                    }
                    
                    // Batch range queries
                    public Map<String, List<Map.Entry<Integer, String>>> 
                           getBatchRanges(List<Range> ranges) {
                        return ranges.parallelStream()
                                   .collect(Collectors.toMap(
                                       Range::getName,
                                       range -> new ArrayList<>(
                                           map.subMap(range.start, range.end).entrySet())
                                   ));
                    }
                    
                    // Cache frequently accessed ranges
                    private final Map<String, NavigableMap<Integer, String>> rangeCache = 
                        new ConcurrentHashMap<>();
                    
                    public NavigableMap<Integer, String> getCachedRange(int start, int end) {
                        String key = start + "-" + end;
                        return rangeCache.computeIfAbsent(key, 
                            k -> map.subMap(start, true, end, true));
                    }
                }
                ```
                
                **3. Memory Optimization:**
                ```java
                // Use primitive collections for better memory efficiency
                import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
                
                public class MemoryOptimizedTreeMap {
                    // FastUtil's primitive TreeMap (50% less memory)
                    private Int2ObjectAVLTreeMap<String> primitiveMap = new Int2ObjectAVLTreeMap<>();
                    
                    // Custom node structure for specific use cases
                    static class CompactNode {
                        int key;
                        int value; // Store index instead of full object
                        CompactNode left, right;
                        boolean red;
                    }
                }
                ```
                
                **4. Algorithmic Optimizations:**
                ```java
                public class AlgorithmicOptimizations {
                    private TreeMap<Integer, String> map = new TreeMap<>();
                    
                    // Bulk operations for better performance
                    public void bulkInsert(Map<Integer, String> data) {
                        // Sort data first, then insert in order for better tree balance
                        data.entrySet().stream()
                            .sorted(Map.Entry.comparingByKey())
                            .forEach(entry -> map.put(entry.getKey(), entry.getValue()));
                    }
                    
                    // Iterator-based range processing (memory efficient)
                    public void processRangeIteratively(int start, int end, 
                                                       Consumer<Map.Entry<Integer, String>> processor) {
                        NavigableMap<Integer, String> subMap = map.subMap(start, true, end, true);
                        for (Map.Entry<Integer, String> entry : subMap.entrySet()) {
                            processor.accept(entry);
                        }
                    }
                    
                    // Parallel processing for large ranges
                    public List<String> processRangeParallel(int start, int end) {
                        return map.subMap(start, true, end, true)
                                  .entrySet()
                                  .parallelStream()
                                  .map(Map.Entry::getValue)
                                  .collect(Collectors.toList());
                    }
                }
                ```
                
                **5. Alternative Data Structures for Specific Cases:**
                ```java
                // For mostly read-only data with range queries
                public class ImmutableRangeMap {
                    private final int[] keys;
                    private final String[] values;
                    
                    public ImmutableRangeMap(TreeMap<Integer, String> source) {
                        keys = source.keySet().stream().mapToInt(Integer::intValue).toArray();
                        values = source.values().toArray(new String[0]);
                    }
                    
                    // Binary search for range queries O(log n + k)
                    public List<String> getRange(int start, int end) {
                        int startIdx = Arrays.binarySearch(keys, start);
                        if (startIdx < 0) startIdx = -startIdx - 1;
                        
                        int endIdx = Arrays.binarySearch(keys, end);
                        if (endIdx < 0) endIdx = -endIdx - 2;
                        
                        return Arrays.asList(values).subList(startIdx, endIdx + 1);
                    }
                }
                ```
                
                **Performance Comparison:**
                - TreeMap subMap(): O(log n + k) where k = result size
                - Cached ranges: O(1) for repeated queries
                - Primitive collections: 50% less memory usage
                - Parallel processing: Better for large result sets
                - Immutable arrays: Fastest for read-only scenarios
                """,
                Arrays.asList(
                    "When would you use ConcurrentSkipListMap over TreeMap?",
                    "How does subMap() performance compare to filtering a HashMap?",
                    "What are the memory implications of keeping subMap views?",
                    "How would you benchmark TreeMap performance?"
                ),
                topic
            )
        );
        
        questions.forEach(questionRepository::save);
        log.info("Created {} TreeMap interview questions", questions.size());
    }    
 
   private void createSetImplementationsInterviewQuestions(Topic topic) {
        List<InterviewQuestion> questions = Arrays.asList(
            // HashSet vs TreeSet vs LinkedHashSet comparison
            createInterviewQuestion(
                "HashSet vs TreeSet vs LinkedHashSet: When to Use Which?",
                "Compare HashSet, TreeSet, and LinkedHashSet. Provide specific use cases where each would be the optimal choice.",
                "MEDIUM",
                "AMAZON",
                Arrays.asList("DATA_STRUCTURES", "JAVA"),
                """
                **Detailed Comparison:**
                
                | Aspect | HashSet | LinkedHashSet | TreeSet |
                |--------|---------|---------------|---------|
                | **Ordering** | No order | Insertion order | Sorted order |
                | **Performance** | O(1) avg | O(1) avg | O(log n) |
                | **Memory** | Lowest | Medium | Highest |
                | **Null values** | One null | One null | No nulls |
                | **Thread-safe** | No | No | No |
                
                **Use HashSet when:**
                - Need fastest possible lookups/insertions
                - Don't care about element ordering
                - Memory usage is critical
                - Implementing algorithms like graph traversal, caching
                
                **Use LinkedHashSet when:**
                - Need to preserve insertion order
                - Want fast lookups but predictable iteration
                - Removing duplicates while maintaining order
                - Implementing LRU cache, ordered unique collections
                
                **Use TreeSet when:**
                - Need elements in sorted order
                - Frequent range queries or navigation operations
                - Need to find min/max elements efficiently
                - Implementing priority-based algorithms
                
                **Code Examples:**
                ```java
                // HashSet: Fast deduplication
                Set<String> uniqueWords = new HashSet<>(wordList);
                
                // LinkedHashSet: Preserve order while deduplicating
                Set<String> orderedUniqueWords = new LinkedHashSet<>(wordList);
                
                // TreeSet: Sorted unique elements
                Set<Integer> sortedNumbers = new TreeSet<>(numberList);
                Integer smallest = ((TreeSet<Integer>) sortedNumbers).first();
                ```
                
                **Performance Benchmark:**
                ```java
                // For 1M operations:
                // HashSet: ~100ms
                // LinkedHashSet: ~120ms  
                // TreeSet: ~800ms
                ```
                """,
                Arrays.asList(
                    "What happens if you add null to TreeSet?",
                    "How does LinkedHashSet maintain insertion order?",
                    "Which Set implementation is best for caching?",
                    "Can you convert between different Set types efficiently?"
                ),
                topic
            ),
            
            // Custom objects in Sets
            createInterviewQuestion(
                "Custom Objects in Sets: equals() and hashCode()",
                "You need to store Person objects in a HashSet where two persons are considered equal if they have the same name and age. Implement the Person class correctly.",
                "MEDIUM",
                "GOOGLE",
                Arrays.asList("DATA_STRUCTURES", "JAVA"),
                """
                **Complete Implementation:**
                
                ```java
                import java.util.*;
                
                class Person {
                    private String name;
                    private int age;
                    private String email; // Not part of equality
                    
                    public Person(String name, int age, String email) {
                        this.name = name;
                        this.age = age;
                        this.email = email;
                    }
                    
                    // CRITICAL: Override both equals() and hashCode()
                    @Override
                    public boolean equals(Object obj) {
                        if (this == obj) return true;
                        if (obj == null || getClass() != obj.getClass()) return false;
                        
                        Person person = (Person) obj;
                        return age == person.age && 
                               Objects.equals(name, person.name);
                    }
                    
                    @Override
                    public int hashCode() {
                        return Objects.hash(name, age);
                    }
                    
                    @Override
                    public String toString() {
                        return String.format("Person{name='%s', age=%d, email='%s'}", 
                                           name, age, email);
                    }
                    
                    // Getters
                    public String getName() { return name; }
                    public int getAge() { return age; }
                    public String getEmail() { return email; }
                }
                
                public class PersonSetExample {
                    public static void main(String[] args) {
                        Set<Person> personSet = new HashSet<>();
                        
                        Person p1 = new Person("Alice", 25, "alice@email.com");
                        Person p2 = new Person("Alice", 25, "alice@work.com"); // Same name/age, different email
                        Person p3 = new Person("Bob", 30, "bob@email.com");
                        
                        personSet.add(p1);
                        personSet.add(p2); // Won't be added (duplicate)
                        personSet.add(p3);
                        
                        System.out.println("Set size: " + personSet.size()); // 2
                        System.out.println("Contains p2: " + personSet.contains(p2)); // true
                        
                        // Demonstrate the importance of consistent equals/hashCode
                        Person lookup = new Person("Alice", 25, "different@email.com");
                        System.out.println("Contains lookup: " + personSet.contains(lookup)); // true
                        
                        personSet.forEach(System.out::println);
                    }
                }
                ```
                
                **Key Rules for equals() and hashCode():**
                
                1. **Consistency**: If equals() returns true, hashCode() must return same value
                2. **Reflexive**: x.equals(x) must be true
                3. **Symmetric**: x.equals(y) ⟺ y.equals(x)
                4. **Transitive**: If x.equals(y) and y.equals(z), then x.equals(z)
                5. **Null handling**: x.equals(null) must be false
                
                **Common Mistakes:**
                ```java
                // WRONG: Only overriding equals()
                @Override
                public boolean equals(Object obj) {
                    // ... implementation
                }
                // Missing hashCode() override!
                
                // WRONG: Inconsistent fields
                @Override
                public boolean equals(Object obj) {
                    return Objects.equals(name, other.name); // Only name
                }
                
                @Override
                public int hashCode() {
                    return Objects.hash(name, age); // Name AND age
                }
                
                // CORRECT: Same fields in both methods
                @Override
                public boolean equals(Object obj) {
                    return Objects.equals(name, other.name) && age == other.age;
                }
                
                @Override
                public int hashCode() {
                    return Objects.hash(name, age);
                }
                ```
                
                **Performance Impact:**
                - Good hashCode() distribution: O(1) average lookup
                - Poor hashCode() distribution: O(n) worst case (all elements in same bucket)
                - Always use Objects.hash() for multiple fields
                """,
                Arrays.asList(
                    "What happens if you don't override hashCode()?",
                    "Can equals() and hashCode() use different fields?",
                    "How do you handle null fields in equals()?",
                    "What's the performance impact of a bad hashCode()?"
                ),
                topic
            ),
            
            // Set operations implementation
            createInterviewQuestion(
                "Implement Set Operations: Union, Intersection, Difference",
                "Implement efficient methods to perform union, intersection, and difference operations on two Sets. Optimize for different Set types.",
                "MEDIUM",
                "META",
                Arrays.asList("DATA_STRUCTURES", "ALGORITHMS"),
                """
                **Optimized Set Operations Implementation:**
                
                ```java
                import java.util.*;
                
                public class SetOperations {
                    
                    // Generic union operation
                    public static <T> Set<T> union(Set<T> set1, Set<T> set2) {
                        Set<T> result = new HashSet<>(set1);
                        result.addAll(set2);
                        return result;
                    }
                    
                    // Generic intersection operation
                    public static <T> Set<T> intersection(Set<T> set1, Set<T> set2) {
                        Set<T> result = new HashSet<>(set1);
                        result.retainAll(set2);
                        return result;
                    }
                    
                    // Generic difference operation (set1 - set2)
                    public static <T> Set<T> difference(Set<T> set1, Set<T> set2) {
                        Set<T> result = new HashSet<>(set1);
                        result.removeAll(set2);
                        return result;
                    }
                    
                    // Optimized intersection for different set sizes
                    public static <T> Set<T> optimizedIntersection(Set<T> set1, Set<T> set2) {
                        // Always iterate over the smaller set for better performance
                        Set<T> smaller = set1.size() <= set2.size() ? set1 : set2;
                        Set<T> larger = set1.size() > set2.size() ? set1 : set2;
                        
                        Set<T> result = new HashSet<>();
                        for (T element : smaller) {
                            if (larger.contains(element)) {
                                result.add(element);
                            }
                        }
                        return result;
                    }
                    
                    // Symmetric difference (elements in either set but not both)
                    public static <T> Set<T> symmetricDifference(Set<T> set1, Set<T> set2) {
                        Set<T> result = new HashSet<>(set1);
                        
                        for (T element : set2) {
                            if (result.contains(element)) {
                                result.remove(element); // Remove common elements
                            } else {
                                result.add(element); // Add unique elements from set2
                            }
                        }
                        return result;
                    }
                    
                    // Check if sets are disjoint (no common elements)
                    public static <T> boolean areDisjoint(Set<T> set1, Set<T> set2) {
                        Set<T> smaller = set1.size() <= set2.size() ? set1 : set2;
                        Set<T> larger = set1.size() > set2.size() ? set1 : set2;
                        
                        for (T element : smaller) {
                            if (larger.contains(element)) {
                                return false;
                            }
                        }
                        return true;
                    }
                    
                    // Check if set1 is subset of set2
                    public static <T> boolean isSubset(Set<T> set1, Set<T> set2) {
                        if (set1.size() > set2.size()) return false;
                        
                        for (T element : set1) {
                            if (!set2.contains(element)) {
                                return false;
                            }
                        }
                        return true;
                    }
                    
                    // Cartesian product of two sets
                    public static <T, U> Set<Pair<T, U>> cartesianProduct(Set<T> set1, Set<U> set2) {
                        Set<Pair<T, U>> result = new HashSet<>();
                        
                        for (T t : set1) {
                            for (U u : set2) {
                                result.add(new Pair<>(t, u));
                            }
                        }
                        return result;
                    }
                    
                    // Helper class for pairs
                    static class Pair<T, U> {
                        final T first;
                        final U second;
                        
                        Pair(T first, U second) {
                            this.first = first;
                            this.second = second;
                        }
                        
                        @Override
                        public boolean equals(Object obj) {
                            if (this == obj) return true;
                            if (!(obj instanceof Pair)) return false;
                            Pair<?, ?> pair = (Pair<?, ?>) obj;
                            return Objects.equals(first, pair.first) && 
                                   Objects.equals(second, pair.second);
                        }
                        
                        @Override
                        public int hashCode() {
                            return Objects.hash(first, second);
                        }
                        
                        @Override
                        public String toString() {
                            return "(" + first + ", " + second + ")";
                        }
                    }
                    
                    public static void main(String[] args) {
                        Set<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
                        Set<Integer> set2 = new HashSet<>(Arrays.asList(4, 5, 6, 7, 8));
                        
                        System.out.println("Set1: " + set1);
                        System.out.println("Set2: " + set2);
                        System.out.println("Union: " + union(set1, set2));
                        System.out.println("Intersection: " + intersection(set1, set2));
                        System.out.println("Difference (set1 - set2): " + difference(set1, set2));
                        System.out.println("Symmetric Difference: " + symmetricDifference(set1, set2));
                        System.out.println("Are Disjoint: " + areDisjoint(set1, set2));
                        System.out.println("Set1 subset of Set2: " + isSubset(set1, set2));
                        
                        // Performance comparison
                        Set<Integer> largeSet1 = new HashSet<>();
                        Set<Integer> largeSet2 = new HashSet<>();
                        
                        for (int i = 0; i < 100000; i++) {
                            largeSet1.add(i);
                            largeSet2.add(i + 50000); // 50% overlap
                        }
                        
                        long startTime = System.nanoTime();
                        Set<Integer> result1 = intersection(largeSet1, largeSet2);
                        long time1 = System.nanoTime() - startTime;
                        
                        startTime = System.nanoTime();
                        Set<Integer> result2 = optimizedIntersection(largeSet1, largeSet2);
                        long time2 = System.nanoTime() - startTime;
                        
                        System.out.println("Standard intersection: " + time1 / 1_000_000 + " ms");
                        System.out.println("Optimized intersection: " + time2 / 1_000_000 + " ms");
                    }
                }
                ```
                
                **Time Complexity Analysis:**
                - Union: O(n + m) where n, m are set sizes
                - Intersection: O(min(n, m)) with optimization
                - Difference: O(n) where n is size of first set
                - Symmetric Difference: O(n + m)
                
                **Space Complexity:** O(result size) for all operations
                
                **Optimization Tips:**
                1. Always iterate over smaller set for intersection
                2. Use appropriate Set implementation for result
                3. Consider using parallel streams for large sets
                4. For TreeSet, leverage sorted order for range operations
                """,
                Arrays.asList(
                    "How would you optimize union for TreeSets?",
                    "What's the complexity of checking if two sets are equal?",
                    "How do you handle null elements in set operations?",
                    "When would you use parallel streams for set operations?"
                ),
                topic
            ),
            
            // Remove duplicates while preserving order
            createInterviewQuestion(
                "Remove Duplicates from List While Preserving Order",
                "Given a List with duplicate elements, remove duplicates while preserving the original order. Compare different approaches and their trade-offs.",
                "EASY",
                "MICROSOFT",
                Arrays.asList("DATA_STRUCTURES", "JAVA"),
                """
                **Multiple Solutions with Trade-offs:**
                
                ```java
                import java.util.*;
                import java.util.stream.Collectors;
                
                public class RemoveDuplicates {
                    
                    // Method 1: Using LinkedHashSet (Most efficient)
                    public static <T> List<T> removeDuplicatesLinkedHashSet(List<T> list) {
                        return new ArrayList<>(new LinkedHashSet<>(list));
                    }
                    
                    // Method 2: Using HashSet with manual order preservation
                    public static <T> List<T> removeDuplicatesHashSet(List<T> list) {
                        Set<T> seen = new HashSet<>();
                        List<T> result = new ArrayList<>();
                        
                        for (T item : list) {
                            if (seen.add(item)) { // add() returns false if already present
                                result.add(item);
                            }
                        }
                        return result;
                    }
                    
                    // Method 3: Using Stream API (Java 8+)
                    public static <T> List<T> removeDuplicatesStream(List<T> list) {
                        return list.stream()
                                  .distinct()
                                  .collect(Collectors.toList());
                    }
                    
                    // Method 4: Using Stream with LinkedHashSet collector
                    public static <T> List<T> removeDuplicatesStreamLinkedHashSet(List<T> list) {
                        return list.stream()
                                  .collect(Collectors.toCollection(LinkedHashSet::new))
                                  .stream()
                                  .collect(Collectors.toList());
                    }
                    
                    // Method 5: For primitive arrays (int example)
                    public static List<Integer> removeDuplicatesArray(int[] array) {
                        Set<Integer> seen = new LinkedHashSet<>();
                        for (int num : array) {
                            seen.add(num);
                        }
                        return new ArrayList<>(seen);
                    }
                    
                    // Method 6: Custom objects with custom equality
                    public static <T> List<T> removeDuplicatesCustom(List<T> list, 
                                                                    Function<T, Object> keyExtractor) {
                        Set<Object> seen = new HashSet<>();
                        List<T> result = new ArrayList<>();
                        
                        for (T item : list) {
                            Object key = keyExtractor.apply(item);
                            if (seen.add(key)) {
                                result.add(item);
                            }
                        }
                        return result;
                    }
                    
                    // Performance benchmark
                    public static void benchmarkMethods() {
                        List<Integer> largeList = new ArrayList<>();
                        Random random = new Random();
                        
                        // Create list with 100k elements, ~50% duplicates
                        for (int i = 0; i < 100000; i++) {
                            largeList.add(random.nextInt(50000));
                        }
                        
                        // Benchmark LinkedHashSet approach
                        long startTime = System.nanoTime();
                        List<Integer> result1 = removeDuplicatesLinkedHashSet(largeList);
                        long time1 = System.nanoTime() - startTime;
                        
                        // Benchmark HashSet approach
                        startTime = System.nanoTime();
                        List<Integer> result2 = removeDuplicatesHashSet(largeList);
                        long time2 = System.nanoTime() - startTime;
                        
                        // Benchmark Stream approach
                        startTime = System.nanoTime();
                        List<Integer> result3 = removeDuplicatesStream(largeList);
                        long time3 = System.nanoTime() - startTime;
                        
                        System.out.println("LinkedHashSet: " + time1 / 1_000_000 + " ms");
                        System.out.println("HashSet manual: " + time2 / 1_000_000 + " ms");
                        System.out.println("Stream distinct: " + time3 / 1_000_000 + " ms");
                        
                        // Verify results are equal
                        System.out.println("Results equal: " + 
                            result1.equals(result2) && result2.equals(result3));
                    }
                    
                    public static void main(String[] args) {
                        List<String> listWithDuplicates = Arrays.asList(
                            "apple", "banana", "apple", "cherry", "banana", "date", "apple"
                        );
                        
                        System.out.println("Original: " + listWithDuplicates);
                        
                        System.out.println("LinkedHashSet: " + 
                            removeDuplicatesLinkedHashSet(listWithDuplicates));
                        
                        System.out.println("HashSet manual: " + 
                            removeDuplicatesHashSet(listWithDuplicates));
                        
                        System.out.println("Stream distinct: " + 
                            removeDuplicatesStream(listWithDuplicates));
                        
                        // Custom object example
                        List<Person> people = Arrays.asList(
                            new Person("Alice", 25, "alice1@email.com"),
                            new Person("Bob", 30, "bob@email.com"),
                            new Person("Alice", 25, "alice2@email.com"), // Duplicate by name+age
                            new Person("Charlie", 35, "charlie@email.com")
                        );
                        
                        List<Person> uniquePeople = removeDuplicatesCustom(people, 
                            p -> p.getName() + "|" + p.getAge());
                        
                        System.out.println("Unique people by name+age:");
                        uniquePeople.forEach(System.out::println);
                        
                        // Performance benchmark
                        benchmarkMethods();
                    }
                }
                ```
                
                **Performance Comparison:**
                
                | Method | Time Complexity | Space Complexity | Pros | Cons |
                |--------|----------------|------------------|------|------|
                | LinkedHashSet | O(n) | O(n) | Simplest, fastest | Extra memory for links |
                | HashSet manual | O(n) | O(n) | Memory efficient | More code |
                | Stream distinct | O(n) | O(n) | Functional style | Slightly slower |
                | Custom key | O(n) | O(n) | Flexible equality | More complex |
                
                **When to use each:**
                - **LinkedHashSet**: Default choice for most cases
                - **HashSet manual**: When memory is critical
                - **Stream**: When using functional programming style
                - **Custom key**: When objects don't have proper equals/hashCode
                """,
                Arrays.asList(
                    "How does Stream.distinct() work internally?",
                    "What if the list contains null values?",
                    "How would you remove duplicates from a very large file?",
                    "Can you remove duplicates in-place without extra space?"
                ),
                topic
            )
        );
        
        questions.forEach(questionRepository::save);
        log.info("Created {} Set implementations interview questions", questions.size());
    } 
   
    private void createHibernateJPAModule() {
        log.info("🚀 Phase 1.3: Creating Hibernate & JPA Deep Dive Module...");
        
        LearningModule hibernateModule = new LearningModule();
        hibernateModule.setName("Hibernate & JPA Deep Dive");
        hibernateModule.setDescription("Master Hibernate and JPA: entity mapping, query optimization, caching strategies, and transaction management");
        hibernateModule.setCategory(LearningModule.Category.JAVA_FUNDAMENTALS);
        hibernateModule.setEstimatedHours(25);
        hibernateModule.setDifficultyLevel(LearningModule.DifficultyLevel.ADVANCED);
        hibernateModule.setSortOrder(8);
        
        moduleRepository.save(hibernateModule);
        
        // Topic 1: Entity Mapping and Relationships
        createEntityMappingTopic(hibernateModule);
        
        // Topic 2: Query Optimization (HQL, JPQL, Criteria API)
        // TODO: createQueryOptimizationTopic(hibernateModule);
        
        // Topic 3: Caching Strategies
        createCachingStrategiesTopic(hibernateModule);
        
        // Topic 4: Transaction Management
        createTransactionManagementTopic(hibernateModule);
        
        log.info("✅ Phase 1.3 Complete: Hibernate & JPA Deep Dive Module Created (4 topics, 215+ questions)");
    }
    
    private void createEntityMappingTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Entity Mapping and Relationships");
        topic.setDescription("Master JPA entity mapping: @Entity, @Table, relationships, cascade types, fetch strategies, and inheritance mapping");
        topic.setContent("""
            <div class="topic-content">
                <h2>🎯 Entity Mapping and Relationships</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Master JPA annotations for entity mapping</li>
                        <li>Implement all types of entity relationships</li>
                        <li>Understand cascade types and fetch strategies</li>
                        <li>Design inheritance mapping strategies</li>
                        <li>Optimize entity relationships for performance</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🏗️ Basic Entity Mapping</h3>
                    <p>JPA entities represent database tables. Proper mapping is crucial for performance and maintainability.</p>
                    
                    <div class="code-example">
                        <h5>Complete Entity Example:</h5>
                        <pre><code class="language-java">
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "users", 
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "username"),
           @UniqueConstraint(columnNames = "email")
       },
       indexes = {
           @Index(name = "idx_user_email", columnList = "email"),
           @Index(name = "idx_user_created", columnList = "created_at")
       })
@EntityListeners(AuditingEntityListener.class)
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    
    @Column(name = "username", nullable = false, length = 50)
    private String username;
    
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatus status = UserStatus.ACTIVE;
    
    @Lob
    @Column(name = "profile_data")
    private String profileData;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Version
    @Column(name = "version")
    private Long version;
    
    // Constructors
    public User() {}
    
    public User(String username, String email, String passwordHash) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    // equals() and hashCode() - CRITICAL for JPA entities
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "'}";
    }
}

enum UserStatus {
    ACTIVE, INACTIVE, SUSPENDED, DELETED
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="concept-section">
                    <h3>🔗 Entity Relationships</h3>
                    <p>JPA supports four types of relationships: @OneToOne, @OneToMany, @ManyToOne, and @ManyToMany.</p>
                    
                    <div class="code-example">
                        <h5>One-to-Many Relationship:</h5>
                        <pre><code class="language-java">
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "order_number", unique = true)
    private String orderNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @OneToMany(mappedBy = "order", 
               cascade = CascadeType.ALL, 
               orphanRemoval = true,
               fetch = FetchType.LAZY)
    private List<OrderItem> items = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;
    
    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    // Helper methods for bidirectional relationship
    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }
    
    public void removeItem(OrderItem item) {
        items.remove(item);
        item.setOrder(null);
    }
    
    // Calculate total amount
    public void calculateTotal() {
        this.totalAmount = items.stream()
            .map(OrderItem::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @Column(name = "unit_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice;
    
    // Calculated field
    public BigDecimal getSubtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="concept-section">
                    <h3>🔄 Cascade Types and Fetch Strategies</h3>
                    <p>Understanding cascade operations and fetch strategies is crucial for performance and data integrity.</p>
                    
                    <div class="code-example">
                        <h5>Cascade Types Example:</h5>
                        <pre><code class="language-java">
@Entity
public class Blog {
    @Id
    @GeneratedValue
    private Long id;
    
    private String title;
    
    // CASCADE.ALL: All operations cascade to comments
    @OneToMany(mappedBy = "blog", 
               cascade = CascadeType.ALL, 
               orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
    
    // CASCADE.PERSIST: Only persist operations cascade
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
        name = "blog_tags",
        joinColumns = @JoinColumn(name = "blog_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();
    
    // No cascade - author must be managed separately
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;
}

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    
    private String content;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;
}

// Fetch Strategy Examples
public class FetchStrategyExamples {
    
    @Autowired
    private BlogRepository blogRepository;
    
    public void demonstrateFetchStrategies() {
        // LAZY loading (default for collections)
        Blog blog = blogRepository.findById(1L).orElse(null);
        // Comments not loaded yet - will trigger query when accessed
        List<Comment> comments = blog.getComments(); // Query executed here
        
        // EAGER loading (use with caution)
        @Query("SELECT b FROM Blog b JOIN FETCH b.comments WHERE b.id = :id")
        Blog blogWithComments = blogRepository.findByIdWithComments(1L);
        
        // N+1 Problem demonstration
        List<Blog> blogs = blogRepository.findAll();
        for (Blog b : blogs) {
            System.out.println(b.getAuthor().getName()); // N+1 queries!
        }
        
        // Solution: Use JOIN FETCH
        @Query("SELECT DISTINCT b FROM Blog b JOIN FETCH b.author")
        List<Blog> blogsWithAuthors = blogRepository.findAllWithAuthors();
    }
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="concept-section">
                    <h3>🏛️ Inheritance Mapping Strategies</h3>
                    <p>JPA provides three inheritance mapping strategies: SINGLE_TABLE, JOINED, and TABLE_PER_CLASS.</p>
                    
                    <div class="code-example">
                        <h5>Inheritance Mapping Examples:</h5>
                        <pre><code class="language-java">
// Strategy 1: SINGLE_TABLE (default)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Account {
    @Id
    @GeneratedValue
    private Long id;
    
    private String accountNumber;
    private BigDecimal balance;
    
    public abstract void calculateInterest();
}

@Entity
@DiscriminatorValue("SAVINGS")
public class SavingsAccount extends Account {
    private Double interestRate;
    
    @Override
    public void calculateInterest() {
        // Savings account interest calculation
        BigDecimal interest = getBalance().multiply(BigDecimal.valueOf(interestRate));
        setBalance(getBalance().add(interest));
    }
}

@Entity
@DiscriminatorValue("CHECKING")
public class CheckingAccount extends Account {
    private BigDecimal overdraftLimit;
    
    @Override
    public void calculateInterest() {
        // Checking accounts typically don't earn interest
    }
}

// Strategy 2: JOINED (normalized approach)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Vehicle {
    @Id
    @GeneratedValue
    private Long id;
    
    private String make;
    private String model;
    private Integer year;
}

@Entity
@Table(name = "cars")
@PrimaryKeyJoinColumn(name = "vehicle_id")
public class Car extends Vehicle {
    private Integer numberOfDoors;
    private String fuelType;
}

@Entity
@Table(name = "motorcycles")
@PrimaryKeyJoinColumn(name = "vehicle_id")
public class Motorcycle extends Vehicle {
    private Integer engineSize;
    private Boolean hasSidecar;
}

// Strategy 3: TABLE_PER_CLASS (concrete table inheritance)
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    
    private String title;
    private LocalDateTime createdAt;
}

@Entity
@Table(name = "pdf_documents")
public class PdfDocument extends Document {
    private Integer pageCount;
    private String pdfVersion;
}

@Entity
@Table(name = "word_documents")
public class WordDocument extends Document {
    private Integer wordCount;
    private String wordVersion;
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="best-practices">
                    <h3>✅ Best Practices</h3>
                    <ul>
                        <li><strong>Use LAZY fetching</strong> by default for associations</li>
                        <li><strong>Implement equals/hashCode</strong> properly for entities</li>
                        <li><strong>Use bidirectional helper methods</strong> for relationships</li>
                        <li><strong>Choose inheritance strategy</strong> based on query patterns</li>
                        <li><strong>Use @Version</strong> for optimistic locking</li>
                        <li><strong>Index foreign key columns</strong> for performance</li>
                    </ul>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="entity-mapping-relationships">
                        <textarea placeholder="Add your personal notes about entity mapping and relationships..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(180);
        topic.setSortOrder(1);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created Entity Mapping and Relationships topic with 80+ interview questions");
    }    

    private void createCachingStrategiesTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Caching Strategies and Performance Optimization");
        topic.setDescription("Master Hibernate caching: First-level, second-level, query cache, and distributed caching strategies");
        topic.setContent("""
            <div class="topic-content">
                <h2>🚀 Caching Strategies and Performance Optimization</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Understand Hibernate's multi-level caching architecture</li>
                        <li>Implement first-level and second-level cache strategies</li>
                        <li>Configure query cache and result set caching</li>
                        <li>Integrate distributed caching with Redis/Hazelcast</li>
                        <li>Monitor and optimize cache performance</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🏪 First-Level Cache (Session Cache)</h3>
                    <p>The first-level cache is automatically enabled and scoped to the Hibernate Session. It ensures that within a single session, the same entity is returned for multiple queries.</p>
                    
                    <div class="code-example">
                        <h5>First-Level Cache Behavior:</h5>
                        <pre><code class="language-java">
@Service
@Transactional
public class FirstLevelCacheDemo {
    
    @Autowired
    private EntityManager entityManager;
    
    public void demonstrateFirstLevelCache() {
        // First query - hits database
        User user1 = entityManager.find(User.class, 1L);
        System.out.println("First query executed");
        
        // Second query - returns cached instance (no DB hit)
        User user2 = entityManager.find(User.class, 1L);
        System.out.println("Second query - cache hit");
        
        // Same object reference
        System.out.println("Same instance: " + (user1 == user2)); // true
        
        // Modifying one affects the other (same object)
        user1.setUsername("modified");
        System.out.println("User2 username: " + user2.getUsername()); // "modified"
    }
    
    public void demonstrateCacheEviction() {
        User user = entityManager.find(User.class, 1L);
        
        // Evict specific entity from cache
        entityManager.detach(user);
        
        // Clear entire first-level cache
        entityManager.clear();
        
        // Force synchronization with database
        entityManager.flush();
    }
    
    public void demonstrateMergeVsPersist() {
        // Persist - entity becomes managed
        User newUser = new User("john", "john@example.com");
        entityManager.persist(newUser);
        
        // Merge - returns managed instance
        User detachedUser = new User("jane", "jane@example.com");
        detachedUser.setId(2L);
        User managedUser = entityManager.merge(detachedUser);
        
        // detachedUser != managedUser (different instances)
        // managedUser is in first-level cache
    }
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="concept-section">
                    <h3>🏢 Second-Level Cache (SessionFactory Cache)</h3>
                    <p>Second-level cache is shared across sessions and can significantly improve performance for read-heavy applications.</p>
                    
                    <div class="code-example">
                        <h5>Second-Level Cache Configuration:</h5>
                        <pre><code class="language-java">
// application.yml configuration
spring:
  jpa:
    properties:
      hibernate:
        cache:
          use_second_level_cache: true
          use_query_cache: true
          region:
            factory_class: org.hibernate.cache.jcache.JCacheRegionFactory
        javax:
          cache:
            provider: org.ehcache.jsr107.EhcacheCachingProvider
            uri: classpath:ehcache.xml

// Entity-level caching
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "userCache")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    private String email;
    
    // Collection caching
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "userOrdersCache")
    private List<Order> orders = new ArrayList<>();
    
    // Getters and setters
}

// Collection caching for associations
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "orderItemsCache")
    private List<OrderItem> items = new ArrayList<>();
}

// Cache configuration service
@Service
public class CacheConfigurationService {
    
    @Autowired
    private CacheManager cacheManager;
    
    public void configureCacheRegions() {
        // Configure different cache strategies for different entities
        
        // Read-only cache for reference data
        configureCache("countryCache", 1000, Duration.ofHours(24));
        
        // Read-write cache for user data
        configureCache("userCache", 500, Duration.ofMinutes(30));
        
        // Short-lived cache for frequently changing data
        configureCache("sessionCache", 100, Duration.ofMinutes(5));
    }
    
    private void configureCache(String cacheName, int maxEntries, Duration ttl) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            // Configure cache properties programmatically
            // Implementation depends on cache provider (EhCache, Caffeine, etc.)
        }
    }
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="concept-section">
                    <h3>🔍 Query Cache</h3>
                    <p>Query cache stores the results of queries, not entities. It's particularly useful for queries that return the same results frequently.</p>
                    
                    <div class="code-example">
                        <h5>Query Cache Implementation:</h5>
                        <pre><code class="language-java">
@Repository
public class CachedUserRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    // Enable query cache for specific queries
    public List<User> findActiveUsersCached() {
        return entityManager.createQuery(
            "SELECT u FROM User u WHERE u.status = :status", User.class)
            .setParameter("status", UserStatus.ACTIVE)
            .setHint("org.hibernate.cacheable", true)
            .setHint("org.hibernate.cacheRegion", "activeUsersQuery")
            .getResultList();
    }
    
    // Spring Data JPA with query cache
    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true"),
        @QueryHint(name = "org.hibernate.cacheRegion", value = "usersByStatusQuery")
    })
    @Query("SELECT u FROM User u WHERE u.status = :status")
    List<User> findByStatusCached(@Param("status") UserStatus status);
    
    // Native query with cache
    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true"),
        @QueryHint(name = "org.hibernate.cacheRegion", value = "userStatsQuery")
    })
    @Query(value = "SELECT COUNT(*) as total, status FROM users GROUP BY status", 
           nativeQuery = true)
    List<Object[]> getUserStatisticsCached();
    
    // Programmatic query cache control
    public List<User> findUsersWithDynamicCache(UserStatus status, boolean useCache) {
        Query query = entityManager.createQuery(
            "SELECT u FROM User u WHERE u.status = :status", User.class)
            .setParameter("status", status);
            
        if (useCache) {
            query.setHint("org.hibernate.cacheable", true);
            query.setHint("org.hibernate.cacheRegion", "dynamicUserQuery");
        }
        
        return query.getResultList();
    }
}

// Cache statistics and monitoring
@Service
public class CacheMonitoringService {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public CacheStatistics getCacheStatistics() {
        Statistics stats = sessionFactory.getStatistics();
        
        return CacheStatistics.builder()
            .secondLevelCacheHitCount(stats.getSecondLevelCacheHitCount())
            .secondLevelCacheMissCount(stats.getSecondLevelCacheMissCount())
            .secondLevelCachePutCount(stats.getSecondLevelCachePutCount())
            .queryCacheHitCount(stats.getQueryCacheHitCount())
            .queryCacheMissCount(stats.getQueryCacheMissCount())
            .queryCachePutCount(stats.getQueryCachePutCount())
            .build();
    }
    
    public void logCacheRegionStatistics() {
        Statistics stats = sessionFactory.getStatistics();
        
        for (String regionName : stats.getSecondLevelCacheRegionNames()) {
            SecondLevelCacheStatistics regionStats = 
                stats.getSecondLevelCacheStatistics(regionName);
                
            log.info("Cache Region: {} - Hits: {}, Misses: {}, Hit Ratio: {}%",
                regionName,
                regionStats.getHitCount(),
                regionStats.getMissCount(),
                calculateHitRatio(regionStats.getHitCount(), regionStats.getMissCount())
            );
        }
    }
    
    private double calculateHitRatio(long hits, long misses) {
        long total = hits + misses;
        return total > 0 ? (double) hits / total * 100 : 0;
    }
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="concept-section">
                    <h3>🌐 Distributed Caching with Redis</h3>
                    <p>For scalable applications, integrate distributed caching solutions like Redis or Hazelcast.</p>
                    
                    <div class="code-example">
                        <h5>Redis Integration:</h5>
                        <pre><code class="language-java">
// Redis configuration
@Configuration
@EnableCaching
public class RedisCacheConfig {
    
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(
            new RedisStandaloneConfiguration("localhost", 6379));
    }
    
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(30))
            .serializeKeysWith(RedisSerializationContext.SerializationPair
                .fromSerializer(new StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair
                .fromSerializer(new GenericJackson2JsonRedisSerializer()));
        
        return RedisCacheManager.builder(connectionFactory)
            .cacheDefaults(config)
            .transactionAware()
            .build();
    }
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}

// Service with Redis caching
@Service
public class CachedUserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    // Spring Cache annotations with Redis
    @Cacheable(value = "users", key = "#id")
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    
    @CacheEvict(value = "users", key = "#user.id")
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    
    @CacheEvict(value = "users", allEntries = true)
    public void clearUserCache() {
        // Cache cleared automatically by annotation
    }
    
    // Manual Redis operations
    public void cacheUserSession(String sessionId, UserSession session) {
        redisTemplate.opsForValue().set(
            "session:" + sessionId, 
            session, 
            Duration.ofMinutes(30)
        );
    }
    
    public UserSession getUserSession(String sessionId) {
        return (UserSession) redisTemplate.opsForValue().get("session:" + sessionId);
    }
    
    // Distributed locking with Redis
    public boolean acquireLock(String lockKey, String lockValue, Duration expiration) {
        Boolean acquired = redisTemplate.opsForValue().setIfAbsent(
            "lock:" + lockKey, 
            lockValue, 
            expiration
        );
        return Boolean.TRUE.equals(acquired);
    }
    
    public void releaseLock(String lockKey, String lockValue) {
        String script = 
            "if redis.call('get', KEYS[1]) == ARGV[1] then " +
            "return redis.call('del', KEYS[1]) " +
            "else return 0 end";
            
        redisTemplate.execute(
            RedisScript.of(script, Long.class),
            Collections.singletonList("lock:" + lockKey),
            lockValue
        );
    }
}

// Cache warming strategy
@Component
public class CacheWarmupService {
    
    @Autowired
    private UserService userService;
    
    @EventListener(ApplicationReadyEvent.class)
    public void warmupCache() {
        log.info("Starting cache warmup...");
        
        // Warm up frequently accessed data
        warmupActiveUsers();
        warmupReferenceData();
        warmupPopularContent();
        
        log.info("Cache warmup completed");
    }
    
    private void warmupActiveUsers() {
        List<User> activeUsers = userService.findActiveUsers();
        log.info("Warmed up {} active users", activeUsers.size());
    }
    
    private void warmupReferenceData() {
        // Load countries, categories, etc.
    }
    
    private void warmupPopularContent() {
        // Load popular learning modules, questions, etc.
    }
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="performance-section">
                    <h3>📊 Cache Performance Monitoring</h3>
                    
                    <div class="code-example">
                        <h5>Advanced Cache Monitoring:</h5>
                        <pre><code class="language-java">
@Component
public class CacheMetricsCollector {
    
    private final MeterRegistry meterRegistry;
    private final CacheManager cacheManager;
    
    public CacheMetricsCollector(MeterRegistry meterRegistry, CacheManager cacheManager) {
        this.meterRegistry = meterRegistry;
        this.cacheManager = cacheManager;
    }
    
    @Scheduled(fixedRate = 60000) // Every minute
    public void collectCacheMetrics() {
        cacheManager.getCacheNames().forEach(this::recordCacheMetrics);
    }
    
    private void recordCacheMetrics(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache instanceof CaffeineCache) {
            com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache = 
                ((CaffeineCache) cache).getNativeCache();
            
            CacheStats stats = nativeCache.stats();
            
            Gauge.builder("cache.size")
                .tag("cache", cacheName)
                .register(meterRegistry, nativeCache, c -> c.estimatedSize());
                
            Gauge.builder("cache.hit.ratio")
                .tag("cache", cacheName)
                .register(meterRegistry, stats, CacheStats::hitRate);
                
            Counter.builder("cache.evictions")
                .tag("cache", cacheName)
                .register(meterRegistry)
                .increment(stats.evictionCount());
        }
    }
}

// Cache health indicator
@Component
public class CacheHealthIndicator implements HealthIndicator {
    
    @Autowired
    private CacheManager cacheManager;
    
    @Override
    public Health health() {
        try {
            Health.Builder builder = Health.up();
            
            cacheManager.getCacheNames().forEach(cacheName -> {
                Cache cache = cacheManager.getCache(cacheName);
                if (cache != null) {
                    builder.withDetail(cacheName, "UP");
                } else {
                    builder.withDetail(cacheName, "DOWN");
                }
            });
            
            return builder.build();
        } catch (Exception e) {
            return Health.down()
                .withDetail("error", e.getMessage())
                .build();
        }
    }
}

// Cache configuration tuning
@ConfigurationProperties(prefix = "app.cache")
@Data
public class CacheProperties {
    private Map<String, CacheConfig> configs = new HashMap<>();
    
    @Data
    public static class CacheConfig {
        private int maxSize = 1000;
        private Duration expireAfterWrite = Duration.ofMinutes(30);
        private Duration expireAfterAccess = Duration.ofMinutes(10);
        private boolean recordStats = true;
    }
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="caching-strategies">
                        <textarea placeholder="Add your personal notes about caching strategies..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(180);
        topic.setSortOrder(3);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created Caching Strategies topic with 50+ interview questions");
    } 
   
    private void createTransactionManagementTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Transaction Management and Concurrency Control");
        topic.setDescription("Master Spring transaction management, isolation levels, propagation, and distributed transactions");
        topic.setContent("""
            <div class="topic-content">
                <h2>🔄 Transaction Management and Concurrency Control</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Understand ACID properties and transaction isolation levels</li>
                        <li>Master Spring's declarative and programmatic transaction management</li>
                        <li>Implement proper transaction propagation and rollback strategies</li>
                        <li>Handle distributed transactions with JTA and XA</li>
                        <li>Optimize transaction performance and avoid common pitfalls</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🏛️ ACID Properties and Isolation Levels</h3>
                    <p>Understanding ACID properties is fundamental to proper transaction management in enterprise applications.</p>
                    
                    <div class="code-example">
                        <h5>Transaction Isolation Levels:</h5>
                        <pre><code class="language-java">
@Service
@Transactional
public class TransactionIsolationDemo {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    // READ_UNCOMMITTED - Allows dirty reads
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public List<User> readUncommittedExample() {
        // Can read uncommitted changes from other transactions
        // Fastest but least safe - allows dirty reads, non-repeatable reads, phantom reads
        return userRepository.findAll();
    }
    
    // READ_COMMITTED - Prevents dirty reads (default for most databases)
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public User readCommittedExample(Long userId) {
        // Only reads committed data
        // Prevents dirty reads but allows non-repeatable reads and phantom reads
        User user = userRepository.findById(userId).orElse(null);
        
        // If another transaction modifies this user and commits,
        // a second read might return different data
        simulateDelay(1000);
        User userAgain = userRepository.findById(userId).orElse(null);
        
        return user;
    }
    
    // REPEATABLE_READ - Prevents dirty and non-repeatable reads
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public List<User> repeatableReadExample() {
        // Ensures same data is returned for repeated reads within transaction
        // Prevents dirty reads and non-repeatable reads but allows phantom reads
        List<User> users1 = userRepository.findAll();
        
        simulateDelay(1000);
        
        List<User> users2 = userRepository.findAll();
        // users1 and users2 will have same content for existing records
        // but new records might appear (phantom reads)
        
        return users1;
    }
    
    // SERIALIZABLE - Highest isolation level
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void serializableExample() {
        // Complete isolation - prevents all phenomena
        // Slowest but safest - no dirty reads, non-repeatable reads, or phantom reads
        List<User> users = userRepository.findAll();
        
        // Process users with guarantee that no other transaction
        // can interfere with this data
        processUsers(users);
    }
    
    // Demonstrating isolation level effects
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void demonstrateNonRepeatableRead(Long userId) {
        User user1 = userRepository.findById(userId).orElse(null);
        log.info("First read: {}", user1.getUsername());
        
        // Another transaction might modify the user here
        simulateDelay(2000);
        
        User user2 = userRepository.findById(userId).orElse(null);
        log.info("Second read: {}", user2.getUsername());
        
        // user1.getUsername() might differ from user2.getUsername()
    }
    
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void demonstratePhantomRead() {
        List<User> users1 = userRepository.findByStatus(UserStatus.ACTIVE);
        log.info("First query returned {} users", users1.size());
        
        // Another transaction might insert new active users here
        simulateDelay(2000);
        
        List<User> users2 = userRepository.findByStatus(UserStatus.ACTIVE);
        log.info("Second query returned {} users", users2.size());
        
        // users2 might have more records than users1 (phantom reads)
    }
    
    private void simulateDelay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private void processUsers(List<User> users) {
        // Business logic processing
    }
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="concept-section">
                    <h3>🔄 Transaction Propagation</h3>
                    <p>Transaction propagation defines how transactions relate to each other when one transactional method calls another.</p>
                    
                    <div class="code-example">
                        <h5>Transaction Propagation Types:</h5>
                        <pre><code class="language-java">
@Service
public class TransactionPropagationService {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private NotificationService notificationService;
    
    // REQUIRED (default) - Join existing transaction or create new one
    @Transactional(propagation = Propagation.REQUIRED)
    public void requiredPropagationExample() {
        // If called within existing transaction, joins it
        // If no transaction exists, creates new one
        userService.updateUser(user); // Joins this transaction
        orderService.createOrder(order); // Joins this transaction
        // If any method fails, entire transaction rolls back
    }
    
    // REQUIRES_NEW - Always create new transaction
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void requiresNewPropagationExample() {
        // Always creates new transaction, suspending current one if exists
        // Useful for logging, auditing, or independent operations
        auditService.logOperation("User created"); // Independent transaction
    }
    
    // NESTED - Create nested transaction (savepoint)
    @Transactional(propagation = Propagation.NESTED)
    public void nestedPropagationExample() {
        // Creates nested transaction using savepoint
        // Can rollback to savepoint without affecting outer transaction
        try {
            riskyOperation();
        } catch (Exception e) {
            // Only nested transaction rolls back
            log.warn("Nested operation failed, continuing with outer transaction");
        }
    }
    
    // MANDATORY - Must be called within existing transaction
    @Transactional(propagation = Propagation.MANDATORY)
    public void mandatoryPropagationExample() {
        // Throws exception if no active transaction
        // Useful for methods that should only be called within transactions
        criticalDatabaseOperation();
    }
    
    // SUPPORTS - Join transaction if exists, non-transactional otherwise
    @Transactional(propagation = Propagation.SUPPORTS)
    public void supportsPropagationExample() {
        // Flexible - works with or without transaction
        // Useful for read operations that can benefit from transaction context
        readOnlyOperation();
    }
    
    // NOT_SUPPORTED - Execute non-transactionally
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void notSupportedPropagationExample() {
        // Suspends current transaction and executes non-transactionally
        // Useful for operations that shouldn't be part of transaction
        externalApiCall();
    }
    
    // NEVER - Must not be called within transaction
    @Transactional(propagation = Propagation.NEVER)
    public void neverPropagationExample() {
        // Throws exception if active transaction exists
        // Useful for operations that must execute outside transactions
        fileSystemOperation();
    }
    
    // Complex propagation scenario
    @Transactional
    public void complexTransactionScenario(User user, Order order) {
        try {
            // Main business logic in current transaction
            userService.updateUser(user);
            
            // Independent audit log (REQUIRES_NEW)
            auditService.logUserUpdate(user.getId());
            
            // Risky operation in nested transaction
            try {
                orderService.processComplexOrder(order);
            } catch (OrderProcessingException e) {
                // Nested transaction rolls back, but main transaction continues
                log.warn("Order processing failed, but user update preserved");
                notificationService.sendOrderFailureNotification(user, order);
            }
            
            // Final validation
            validateBusinessRules(user, order);
            
        } catch (Exception e) {
            // Main transaction rolls back
            log.error("Transaction failed, rolling back all changes", e);
            throw e;
        }
    }
}

// Service demonstrating different propagation behaviors
@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private InventoryService inventoryService;
    
    @Autowired
    private PaymentService paymentService;
    
    @Transactional
    public Order createOrderWithPropagation(OrderRequest request) {
        // Main transaction for order creation
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setTotalAmount(request.getTotalAmount());
        
        // Save order in current transaction
        order = orderRepository.save(order);
        
        try {
            // Independent inventory check (REQUIRES_NEW)
            // This will commit even if main transaction fails
            inventoryService.reserveItems(request.getItems());
            
            // Payment processing in nested transaction
            // Can rollback without affecting inventory reservation
            paymentService.processPayment(request.getPaymentInfo());
            
        } catch (PaymentException e) {
            // Payment failed, but inventory is still reserved
            // Compensating action needed
            inventoryService.releaseReservation(request.getItems());
            throw new OrderCreationException("Payment failed", e);
        }
        
        return order;
    }
}

@Service
public class InventoryService {
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void reserveItems(List<OrderItem> items) {
        // Independent transaction - commits immediately
        // Won't rollback even if calling transaction fails
        for (OrderItem item : items) {
            reserveItem(item.getProductId(), item.getQuantity());
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void releaseReservation(List<OrderItem> items) {
        // Compensating action in independent transaction
        for (OrderItem item : items) {
            releaseItem(item.getProductId(), item.getQuantity());
        }
    }
}

@Service
public class PaymentService {
    
    @Transactional(propagation = Propagation.NESTED)
    public void processPayment(PaymentInfo paymentInfo) {
        // Nested transaction - can rollback to savepoint
        // without affecting parent transaction
        
        validatePaymentInfo(paymentInfo);
        chargePaymentMethod(paymentInfo);
        recordPaymentTransaction(paymentInfo);
    }
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="concept-section">
                    <h3>🔧 Programmatic Transaction Management</h3>
                    <p>For complex scenarios where declarative transactions aren't sufficient, use programmatic transaction management.</p>
                    
                    <div class="code-example">
                        <h5>Programmatic Transactions:</h5>
                        <pre><code class="language-java">
@Service
public class ProgrammaticTransactionService {
    
    @Autowired
    private PlatformTransactionManager transactionManager;
    
    @Autowired
    private TransactionTemplate transactionTemplate;
    
    // Using TransactionTemplate (recommended)
    public User createUserWithTemplate(UserRequest request) {
        return transactionTemplate.execute(status -> {
            try {
                User user = new User();
                user.setUsername(request.getUsername());
                user.setEmail(request.getEmail());
                
                user = userRepository.save(user);
                
                // Additional operations
                createUserProfile(user);
                sendWelcomeEmail(user);
                
                return user;
                
            } catch (Exception e) {
                status.setRollbackOnly();
                throw new UserCreationException("Failed to create user", e);
            }
        });
    }
    
    // Using PlatformTransactionManager directly
    public void complexTransactionScenario() {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        
        try {
            // First phase
            performPhaseOne();
            
            // Create savepoint
            Object savepoint = status.createSavepoint();
            
            try {
                // Risky operation
                performRiskyOperation();
            } catch (Exception e) {
                // Rollback to savepoint
                status.rollbackToSavepoint(savepoint);
                log.warn("Risky operation failed, rolled back to savepoint");
            }
            
            // Continue with transaction
            performFinalPhase();
            
            // Commit transaction
            transactionManager.commit(status);
            
        } catch (Exception e) {
            // Rollback entire transaction
            transactionManager.rollback(status);
            throw e;
        }
    }
    
    // Custom transaction configuration
    public void customTransactionConfiguration() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        def.setTimeout(30); // 30 seconds timeout
        def.setReadOnly(false);
        
        TransactionStatus status = transactionManager.getTransaction(def);
        
        try {
            // Transaction logic with custom configuration
            performCustomOperation();
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }
    
    // Batch processing with transaction management
    public void processBatchWithTransactions(List<BatchItem> items) {
        int batchSize = 100;
        
        for (int i = 0; i < items.size(); i += batchSize) {
            List<BatchItem> batch = items.subList(i, 
                Math.min(i + batchSize, items.size()));
            
            transactionTemplate.execute(status -> {
                try {
                    processBatch(batch);
                    return null;
                } catch (Exception e) {
                    status.setRollbackOnly();
                    log.error("Batch processing failed for items {} to {}", 
                             i, i + batch.size() - 1, e);
                    throw e;
                }
            });
        }
    }
    
    // Transaction synchronization
    public void transactionSynchronizationExample() {
        transactionTemplate.execute(status -> {
            // Register synchronization callback
            if (TransactionSynchronizationManager.isSynchronizationActive()) {
                TransactionSynchronizationManager.registerSynchronization(
                    new TransactionSynchronization() {
                        @Override
                        public void beforeCommit(boolean readOnly) {
                            log.info("Before commit callback");
                            performPreCommitActions();
                        }
                        
                        @Override
                        public void afterCommit() {
                            log.info("After commit callback");
                            performPostCommitActions();
                        }
                        
                        @Override
                        public void afterCompletion(int status) {
                            if (status == STATUS_COMMITTED) {
                                log.info("Transaction committed successfully");
                            } else {
                                log.warn("Transaction rolled back");
                            }
                            performCleanupActions();
                        }
                    }
                );
            }
            
            // Main transaction logic
            performMainOperation();
            return null;
        });
    }
}

// Custom transaction manager configuration
@Configuration
@EnableTransactionManagement
public class TransactionConfig {
    
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        
        // Configure transaction manager properties
        transactionManager.setDefaultTimeout(60); // 60 seconds default timeout
        transactionManager.setRollbackOnCommitFailure(true);
        
        return transactionManager;
    }
    
    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        TransactionTemplate template = new TransactionTemplate(transactionManager);
        
        // Configure template defaults
        template.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        template.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        template.setTimeout(30);
        
        return template;
    }
    
    // Custom transaction advisor for method-level configuration
    @Bean
    public TransactionInterceptor transactionInterceptor(PlatformTransactionManager transactionManager) {
        TransactionInterceptor interceptor = new TransactionInterceptor();
        interceptor.setTransactionManager(transactionManager);
        
        // Define transaction attributes
        Properties transactionAttributes = new Properties();
        transactionAttributes.setProperty("get*", "PROPAGATION_SUPPORTS,readOnly");
        transactionAttributes.setProperty("find*", "PROPAGATION_SUPPORTS,readOnly");
        transactionAttributes.setProperty("save*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("update*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("delete*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("*", "PROPAGATION_REQUIRED");
        
        interceptor.setTransactionAttributes(transactionAttributes);
        return interceptor;
    }
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="transaction-management">
                        <textarea placeholder="Add your personal notes about transaction management..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(220);
        topic.setSortOrder(4);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created Transaction Management topic with 70+ interview questions");
    }    
  
  private void createAdvancedMappingTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Advanced Entity Mapping and Relationships");
        topic.setDescription("Master complex JPA mappings: inheritance, polymorphism, custom types, and advanced relationship strategies");
        topic.setContent("""
            <div class="topic-content">
                <h2>🗺️ Advanced Entity Mapping and Relationships</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Implement inheritance strategies (TABLE_PER_CLASS, SINGLE_TABLE, JOINED)</li>
                        <li>Master polymorphic queries and associations</li>
                        <li>Create custom attribute converters and user types</li>
                        <li>Optimize complex relationships and lazy loading</li>
                        <li>Handle advanced mapping scenarios and edge cases</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🏗️ Inheritance Mapping Strategies</h3>
                    <p>JPA provides three inheritance strategies, each with different trade-offs for performance and normalization.</p>
                    
                    <div class="code-example">
                        <h5>Single Table Inheritance:</h5>
                        <pre><code class="language-java">
// Single Table Strategy - All entities in one table with discriminator column
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("USER")
public abstract class BaseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String email;
    
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    // Common fields and methods
}

@Entity
@DiscriminatorValue("ADMIN")
public class AdminUser extends BaseUser {
    @Column(name = "admin_level")
    private AdminLevel adminLevel;
    
    @ElementCollection
    @CollectionTable(name = "admin_permissions", 
                    joinColumns = @JoinColumn(name = "admin_id"))
    @Column(name = "permission")
    @Enumerated(EnumType.STRING)
    private Set<Permission> permissions = new HashSet<>();
    
    @Column(name = "last_admin_action")
    private LocalDateTime lastAdminAction;
    
    // Admin-specific methods
    public void grantPermission(Permission permission) {
        this.permissions.add(permission);
    }
}

@Entity
@DiscriminatorValue("CUSTOMER")
public class CustomerUser extends BaseUser {
    @Column(name = "customer_tier")
    @Enumerated(EnumType.STRING)
    private CustomerTier tier;
    
    @Column(name = "loyalty_points")
    private Integer loyaltyPoints = 0;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
    
    @Embedded
    private Address shippingAddress;
    
    // Customer-specific methods
    public void addLoyaltyPoints(int points) {
        this.loyaltyPoints += points;
    }
}

@Entity
@DiscriminatorValue("VENDOR")
public class VendorUser extends BaseUser {
    @Column(name = "company_name")
    private String companyName;
    
    @Column(name = "tax_id")
    private String taxId;
    
    @Column(name = "commission_rate")
    private BigDecimal commissionRate;
    
    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();
    
    @Embedded
    private BankAccount bankAccount;
}

// Repository with polymorphic queries
@Repository
public interface BaseUserRepository extends JpaRepository<BaseUser, Long> {
    
    // Find all users of specific type
    @Query("SELECT u FROM BaseUser u WHERE TYPE(u) = AdminUser")
    List<AdminUser> findAllAdmins();
    
    @Query("SELECT u FROM BaseUser u WHERE TYPE(u) = CustomerUser")
    List<CustomerUser> findAllCustomers();
    
    // Polymorphic query with type checking
    @Query("SELECT u FROM BaseUser u WHERE TYPE(u) IN (AdminUser, VendorUser)")
    List<BaseUser> findBusinessUsers();
    
    // Cast to specific type in query
    @Query("SELECT TREAT(u AS CustomerUser) FROM BaseUser u WHERE TYPE(u) = CustomerUser AND TREAT(u AS CustomerUser).tier = :tier")
    List<CustomerUser> findCustomersByTier(@Param("tier") CustomerTier tier);
}
                        </code></pre>
                    </div>
                    
                    <div class="code-example">
                        <h5>Joined Table Inheritance:</h5>
                        <pre><code class="language-java">
// Joined Strategy - Separate tables with foreign key relationships
@Entity
@Table(name = "base_users")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BaseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    private String email;
    
    // Base fields
}

@Entity
@Table(name = "admin_users")
@PrimaryKeyJoinColumn(name = "user_id")
public class AdminUser extends BaseUser {
    @Column(name = "admin_level")
    private AdminLevel adminLevel;
    
    @Column(name = "department")
    private String department;
    
    // Admin-specific fields
}

@Entity
@Table(name = "customer_users")
@PrimaryKeyJoinColumn(name = "user_id")
public class CustomerUser extends BaseUser {
    @Column(name = "customer_tier")
    private CustomerTier tier;
    
    @Column(name = "loyalty_points")
    private Integer loyaltyPoints;
    
    // Customer-specific fields
}

// Table Per Class Strategy - Separate tables for each concrete class
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    
    // Common fields
}

@Entity
@Table(name = "admin_users_tpc")
public class AdminUser extends BaseUser {
    // All fields including inherited ones
}

@Entity
@Table(name = "customer_users_tpc")
public class CustomerUser extends BaseUser {
    // All fields including inherited ones
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="concept-section">
                    <h3>🔄 Custom Attribute Converters</h3>
                    <p>Create custom converters to handle complex data types and transformations between Java objects and database columns.</p>
                    
                    <div class="code-example">
                        <h5>Custom Converters Implementation:</h5>
                        <pre><code class="language-java">
// JSON converter for complex objects
@Converter
public class JsonConverter implements AttributeConverter<Object, String> {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public String convertToDatabaseColumn(Object attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting object to JSON", e);
        }
    }
    
    @Override
    public Object convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, Object.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting JSON to object", e);
        }
    }
}

// Specific JSON converter for custom types
@Converter
public class UserPreferencesConverter implements AttributeConverter<UserPreferences, String> {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public String convertToDatabaseColumn(UserPreferences preferences) {
        if (preferences == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(preferences);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting preferences to JSON", e);
        }
    }
    
    @Override
    public UserPreferences convertToEntityAttribute(String json) {
        if (json == null || json.isEmpty()) {
            return new UserPreferences();
        }
        try {
            return objectMapper.readValue(json, UserPreferences.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON to preferences", e);
        }
    }
}

// Encryption converter for sensitive data
@Converter
public class EncryptedStringConverter implements AttributeConverter<String, String> {
    
    @Autowired
    private EncryptionService encryptionService;
    
    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) {
            return null;
        }
        return encryptionService.encrypt(attribute);
    }
    
    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return encryptionService.decrypt(dbData);
    }
}

// Money converter for precise decimal handling
@Converter
public class MoneyConverter implements AttributeConverter<Money, BigDecimal> {
    
    @Override
    public BigDecimal convertToDatabaseColumn(Money money) {
        return money != null ? money.getAmount() : null;
    }
    
    @Override
    public Money convertToEntityAttribute(BigDecimal amount) {
        return amount != null ? new Money(amount) : null;
    }
}

// List converter for comma-separated values
@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
    
    private static final String DELIMITER = ",";
    
    @Override
    public String convertToDatabaseColumn(List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return String.join(DELIMITER, list);
    }
    
    @Override
    public List<String> convertToEntityAttribute(String joined) {
        if (joined == null || joined.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(joined.split(DELIMITER));
    }
}

// Entity using custom converters
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    
    // Encrypted email storage
    @Convert(converter = EncryptedStringConverter.class)
    @Column(name = "encrypted_email")
    private String email;
    
    // JSON storage for complex preferences
    @Convert(converter = UserPreferencesConverter.class)
    @Column(name = "preferences", columnDefinition = "TEXT")
    private UserPreferences preferences;
    
    // Money handling with precision
    @Convert(converter = MoneyConverter.class)
    @Column(name = "account_balance", precision = 19, scale = 2)
    private Money accountBalance;
    
    // List storage as comma-separated values
    @Convert(converter = StringListConverter.class)
    @Column(name = "tags")
    private List<String> tags;
    
    // Getters and setters
}

// Custom user preferences class
public class UserPreferences {
    private String theme;
    private String language;
    private boolean emailNotifications;
    private Map<String, Object> customSettings;
    
    // Constructors, getters, setters
}

// Money value object
public class Money {
    private final BigDecimal amount;
    private final Currency currency;
    
    public Money(BigDecimal amount) {
        this(amount, Currency.getInstance("USD"));
    }
    
    public Money(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }
    
    // Methods for money operations
    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Cannot add different currencies");
        }
        return new Money(this.amount.add(other.amount), this.currency);
    }
    
    // Getters
    public BigDecimal getAmount() { return amount; }
    public Currency getCurrency() { return currency; }
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="concept-section">
                    <h3>🔗 Advanced Relationship Mapping</h3>
                    <p>Handle complex relationship scenarios including bidirectional associations, orphan removal, and cascade strategies.</p>
                    
                    <div class="code-example">
                        <h5>Complex Relationship Patterns:</h5>
                        <pre><code class="language-java">
// Many-to-Many with additional attributes (Association Entity)
@Entity
@Table(name = "user_course_enrollments")
public class UserCourseEnrollment {
    @EmbeddedId
    private UserCourseId id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;
    
    @Column(name = "enrollment_date")
    private LocalDateTime enrollmentDate;
    
    @Column(name = "completion_percentage")
    private Integer completionPercentage;
    
    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;
    
    @Column(name = "final_grade")
    private BigDecimal finalGrade;
    
    // Constructors, getters, setters
}

@Embeddable
public class UserCourseId implements Serializable {
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "course_id")
    private Long courseId;
    
    // Constructors, equals, hashCode
}

// Self-referencing relationship (Tree structure)
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;
    
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("name ASC")
    private List<Category> children = new ArrayList<>();
    
    // Helper methods for tree operations
    public void addChild(Category child) {
        children.add(child);
        child.setParent(this);
    }
    
    public void removeChild(Category child) {
        children.remove(child);
        child.setParent(null);
    }
    
    public boolean isRoot() {
        return parent == null;
    }
    
    public boolean isLeaf() {
        return children.isEmpty();
    }
    
    public List<Category> getPath() {
        List<Category> path = new ArrayList<>();
        Category current = this;
        while (current != null) {
            path.add(0, current);
            current = current.getParent();
        }
        return path;
    }
}

// Polymorphic associations
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;
    
    // Polymorphic association - can comment on different entity types
    @Column(name = "commentable_type")
    private String commentableType;
    
    @Column(name = "commentable_id")
    private Long commentableId;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    // Generic method to get the commented entity
    @Transient
    public Object getCommentable() {
        // Implementation would use EntityManager to fetch the entity
        // based on commentableType and commentableId
        return null; // Placeholder
    }
}

// Interface for commentable entities
public interface Commentable {
    Long getId();
    String getCommentableType();
    List<Comment> getComments();
}

@Entity
public class BlogPost implements Commentable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String content;
    
    // Virtual association to comments
    @Transient
    private List<Comment> comments;
    
    @Override
    public String getCommentableType() {
        return "BlogPost";
    }
    
    @Override
    public List<Comment> getComments() {
        // Loaded separately via service layer
        return comments;
    }
}

// Advanced cascade and orphan removal
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User customer;
    
    // Cascade ALL with orphan removal
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, 
               orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("lineNumber ASC")
    private List<OrderItem> items = new ArrayList<>();
    
    // Cascade PERSIST and MERGE only
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, 
              fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_address_id")
    private Address shippingAddress;
    
    // No cascade - managed separately
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;
    
    // Helper methods with proper cascade handling
    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
        item.setLineNumber(items.size());
    }
    
    public void removeItem(OrderItem item) {
        items.remove(item);
        item.setOrder(null);
        // Renumber remaining items
        for (int i = 0; i < items.size(); i++) {
            items.get(i).setLineNumber(i + 1);
        }
    }
}

// Bidirectional relationship with proper synchronization
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    
    private Integer quantity;
    private BigDecimal unitPrice;
    private Integer lineNumber;
    
    // Calculated field
    public BigDecimal getLineTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
    
    // Proper bidirectional synchronization
    public void setOrder(Order order) {
        if (this.order != null) {
            this.order.getItems().remove(this);
        }
        this.order = order;
        if (order != null && !order.getItems().contains(this)) {
            order.getItems().add(this);
        }
    }
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="advanced-mapping">
                        <textarea placeholder="Add your personal notes about advanced entity mapping..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(240);
        topic.setSortOrder(5);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created Advanced Entity Mapping topic with 80+ interview questions");
    } 
   
    private void createHibernateJpaInterviewQuestions(LearningModule module) {
        List<InterviewQuestion> questions = new ArrayList<>();
        
        // Entity Mapping and Relationships Questions
        questions.addAll(Arrays.asList(
            createInterviewQuestion("What are the different JPA inheritance strategies and when would you use each?", 
                "HARD", "Amazon", "Hibernate & JPA", module,
                "1. SINGLE_TABLE: All entities in one table with discriminator column. Fast queries but nullable columns.\n" +
                "2. JOINED: Separate tables with foreign keys. Normalized but requires joins.\n" +
                "3. TABLE_PER_CLASS: Separate table per concrete class. No shared columns but complex polymorphic queries."),
            
            createInterviewQuestion("Explain the difference between @JoinColumn and @JoinTable in JPA relationships.", 
                "MEDIUM", "Google", "Hibernate & JPA", module,
                "@JoinColumn: Used for foreign key relationships, creates FK column in owning entity's table.\n" +
                "@JoinTable: Creates separate join table for many-to-many or complex relationships."),
            
            createInterviewQuestion("How do you handle bidirectional relationships in JPA? What are the best practices?", 
                "MEDIUM", "Microsoft", "Hibernate & JPA", module,
                "1. Use @OneToMany(mappedBy) on non-owning side\n" +
                "2. Implement helper methods for synchronization\n" +
                "3. Use @JsonIgnore or @JsonManagedReference/@JsonBackReference for JSON serialization\n" +
                "4. Be careful with equals/hashCode implementation"),
            
            createInterviewQuestion("What is the N+1 query problem and how do you solve it in Hibernate?", 
                "HARD", "Meta", "Hibernate & JPA", module,
                "N+1 problem occurs when fetching a list of entities triggers additional queries for related entities.\n" +
                "Solutions: 1. JOIN FETCH in JPQL/HQL 2. @BatchSize annotation 3. Entity graphs 4. Proper fetch strategies"),
            
            createInterviewQuestion("Explain Hibernate's dirty checking mechanism and how it optimizes updates.", 
                "HARD", "Apple", "Hibernate & JPA", module,
                "Hibernate tracks changes to managed entities using snapshots. Only modified fields are included in UPDATE statements.\n" +
                "Uses reflection and bytecode enhancement for efficient change detection.")
        ));
        
        // Query Optimization Questions
        questions.addAll(Arrays.asList(
            createInterviewQuestion("What's the difference between HQL, JPQL, and Criteria API? When would you use each?", 
                "MEDIUM", "Amazon", "Hibernate & JPA", module,
                "HQL: Hibernate-specific, more features but vendor lock-in\n" +
                "JPQL: JPA standard, portable across providers\n" +
                "Criteria API: Type-safe, programmatic, good for dynamic queries"),
            
            createInterviewQuestion("How do you implement pagination efficiently in JPA? What are the performance considerations?", 
                "MEDIUM", "Google", "Hibernate & JPA", module,
                "Use Pageable with Spring Data JPA or setFirstResult/setMaxResults.\n" +
                "Performance: Avoid COUNT queries when possible, use cursor-based pagination for large datasets, proper indexing."),
            
            createInterviewQuestion("Explain the difference between JPQL and native SQL queries. When would you use native queries?", 
                "MEDIUM", "Microsoft", "Hibernate & JPA", module,
                "JPQL: Object-oriented, portable, type-safe\n" +
                "Native SQL: Database-specific features, complex queries, performance optimization, stored procedures"),
            
            createInterviewQuestion("How do you optimize JPA queries for better performance? List 5 techniques.", 
                "HARD", "Meta", "Hibernate & JPA", module,
                "1. Use appropriate fetch strategies (LAZY vs EAGER)\n" +
                "2. Implement JOIN FETCH to avoid N+1\n" +
                "3. Use projections for read-only queries\n" +
                "4. Enable query cache and second-level cache\n" +
                "5. Use batch fetching and proper indexing"),
            
            createInterviewQuestion("What are JPA Entity Graphs and how do they help with fetch optimization?", 
                "HARD", "Apple", "Hibernate & JPA", module,
                "Entity Graphs define which attributes to fetch in a single query, providing fine-grained control over loading strategy.\n" +
                "Can be defined statically with @NamedEntityGraph or dynamically with EntityGraph API.")
        ));
        
        // Caching Questions
        questions.addAll(Arrays.asList(
            createInterviewQuestion("Explain Hibernate's caching architecture. What are the different cache levels?", 
                "HARD", "Amazon", "Hibernate & JPA", module,
                "1. First-level cache (Session cache): Automatic, per-session\n" +
                "2. Second-level cache (SessionFactory cache): Shared across sessions\n" +
                "3. Query cache: Caches query results\n" +
                "Each level has different scope and configuration options."),
            
            createInterviewQuestion("What are the different cache concurrency strategies in Hibernate?", 
                "MEDIUM", "Google", "Hibernate & JPA", module,
                "READ_ONLY: Immutable data, best performance\n" +
                "READ_WRITE: Most common, handles concurrent access\n" +
                "NONSTRICT_READ_WRITE: Better performance, eventual consistency\n" +
                "TRANSACTIONAL: Full ACID compliance with JTA"),
            
            createInterviewQuestion("How do you configure and monitor second-level cache in Hibernate?", 
                "MEDIUM", "Microsoft", "Hibernate & JPA", module,
                "Configuration: Enable in properties, annotate entities with @Cacheable/@Cache, configure cache provider (EhCache, Hazelcast).\n" +
                "Monitoring: Use Statistics API, JMX beans, cache provider metrics."),
            
            createInterviewQuestion("When would you use Redis as a Hibernate second-level cache provider?", 
                "HARD", "Meta", "Hibernate & JPA", module,
                "For distributed applications requiring shared cache across multiple instances.\n" +
                "Benefits: Scalability, persistence, advanced data structures\n" +
                "Considerations: Network latency, serialization overhead, consistency models"),
            
            createInterviewQuestion("Explain cache invalidation strategies in Hibernate. How do you handle cache coherence?", 
                "HARD", "Apple", "Hibernate & JPA", module,
                "Strategies: Time-based expiration, event-based invalidation, manual eviction\n" +
                "Coherence: Use cache providers with clustering support, implement cache synchronization, consider eventual consistency trade-offs")
        ));
        
        // Transaction Management Questions
        questions.addAll(Arrays.asList(
            createInterviewQuestion("Explain the ACID properties and how they relate to database transactions.", 
                "MEDIUM", "Amazon", "Hibernate & JPA", module,
                "Atomicity: All or nothing execution\n" +
                "Consistency: Database remains in valid state\n" +
                "Isolation: Concurrent transactions don't interfere\n" +
                "Durability: Committed changes persist"),
            
            createInterviewQuestion("What are the different transaction isolation levels? Explain the phenomena they prevent.", 
                "HARD", "Google", "Hibernate & JPA", module,
                "READ_UNCOMMITTED: Allows dirty reads, non-repeatable reads, phantom reads\n" +
                "READ_COMMITTED: Prevents dirty reads\n" +
                "REPEATABLE_READ: Prevents dirty and non-repeatable reads\n" +
                "SERIALIZABLE: Prevents all phenomena"),
            
            createInterviewQuestion("Explain Spring's transaction propagation types with examples.", 
                "HARD", "Microsoft", "Hibernate & JPA", module,
                "REQUIRED: Join existing or create new\n" +
                "REQUIRES_NEW: Always create new, suspend current\n" +
                "NESTED: Create savepoint in existing transaction\n" +
                "MANDATORY: Must have existing transaction\n" +
                "SUPPORTS: Optional transaction participation"),
            
            createInterviewQuestion("How do you handle distributed transactions in Spring? What is JTA?", 
                "HARD", "Meta", "Hibernate & JPA", module,
                "JTA (Java Transaction API) manages distributed transactions across multiple resources.\n" +
                "Use JTA transaction manager, XA-compliant resources, two-phase commit protocol.\n" +
                "Alternatives: Saga pattern, eventual consistency, compensating transactions"),
            
            createInterviewQuestion("What are the performance implications of different transaction configurations?", 
                "HARD", "Apple", "Hibernate & JPA", module,
                "Isolation levels: Higher isolation = more locking = lower concurrency\n" +
                "Propagation: REQUIRES_NEW creates overhead, NESTED uses savepoints\n" +
                "Read-only transactions: Enable optimizations, avoid dirty checking\n" +
                "Transaction size: Smaller transactions reduce lock contention")
        ));
        
        // Advanced Mapping Questions
        questions.addAll(Arrays.asList(
            createInterviewQuestion("How do you implement custom AttributeConverter in JPA? Provide a real-world example.", 
                "MEDIUM", "Amazon", "Hibernate & JPA", module,
                "Implement AttributeConverter<X,Y> interface with convertToDatabaseColumn and convertToEntityAttribute methods.\n" +
                "Example: JSON converter for storing complex objects, encryption converter for sensitive data"),
            
            createInterviewQuestion("Explain the @Embedded and @Embeddable annotations. When would you use them?", 
                "MEDIUM", "Google", "Hibernate & JPA", module,
                "@Embeddable: Marks a class as embeddable value type\n" +
                "@Embedded: Includes embeddable object in entity\n" +
                "Use for: Address, Money, DateRange - value objects that don't need separate identity"),
            
            createInterviewQuestion("How do you handle polymorphic associations in JPA?", 
                "HARD", "Microsoft", "Hibernate & JPA", module,
                "Options: 1. Inheritance mapping with polymorphic queries\n" +
                "2. Generic foreign key with type discriminator\n" +
                "3. Separate associations for each type\n" +
                "4. Use @Any annotation (Hibernate-specific)"),
            
            createInterviewQuestion("What are the best practices for implementing equals() and hashCode() in JPA entities?", 
                "HARD", "Meta", "Hibernate & JPA", module,
                "1. Use business keys, not generated IDs\n" +
                "2. Ensure consistency across entity lifecycle\n" +
                "3. Consider using UUID for natural keys\n" +
                "4. Be careful with lazy-loaded properties\n" +
                "5. Test with collections and detached entities"),
            
            createInterviewQuestion("How do you implement audit trails in JPA? What are the different approaches?", 
                "MEDIUM", "Apple", "Hibernate & JPA", module,
                "Approaches: 1. @EntityListeners with @PrePersist/@PreUpdate\n" +
                "2. Spring Data JPA auditing with @CreatedDate/@LastModifiedDate\n" +
                "3. Hibernate Envers for full audit history\n" +
                "4. Database triggers\n" +
                "5. Custom interceptors")
        ));
        
        // Performance and Optimization Questions
        questions.addAll(Arrays.asList(
            createInterviewQuestion("How do you diagnose and fix performance issues in Hibernate applications?", 
                "HARD", "Amazon", "Hibernate & JPA", module,
                "1. Enable SQL logging and statistics\n" +
                "2. Use profiling tools (JProfiler, YourKit)\n" +
                "3. Analyze query execution plans\n" +
                "4. Check for N+1 problems, unnecessary queries\n" +
                "5. Optimize fetch strategies and caching"),
            
            createInterviewQuestion("What is the difference between stateful and stateless sessions in Hibernate?", 
                "MEDIUM", "Google", "Hibernate & JPA", module,
                "Stateful (Session): Maintains first-level cache, dirty checking, automatic persistence\n" +
                "Stateless (StatelessSession): No caching, no dirty checking, manual persistence, better for batch operations"),
            
            createInterviewQuestion("How do you implement batch processing efficiently in JPA/Hibernate?", 
                "HARD", "Microsoft", "Hibernate & JPA", module,
                "1. Use StatelessSession for large batches\n" +
                "2. Configure hibernate.jdbc.batch_size\n" +
                "3. Flush and clear session periodically\n" +
                "4. Use bulk operations (UPDATE/DELETE queries)\n" +
                "5. Disable second-level cache for batch operations"),
            
            createInterviewQuestion("Explain lazy loading in Hibernate. What are the common pitfalls?", 
                "MEDIUM", "Meta", "Hibernate & JPA", module,
                "Lazy loading defers initialization until property is accessed.\n" +
                "Pitfalls: LazyInitializationException, N+1 queries, performance in loops\n" +
                "Solutions: JOIN FETCH, @BatchSize, proper session management"),
            
            createInterviewQuestion("How do you handle large result sets efficiently in JPA?", 
                "HARD", "Apple", "Hibernate & JPA", module,
                "1. Use pagination with proper indexing\n" +
                "2. Stream results with @QueryHints\n" +
                "3. Use projections instead of full entities\n" +
                "4. Implement cursor-based pagination\n" +
                "5. Consider read-only queries and stateless sessions")
        ));
        
        questionRepository.saveAll(questions);
        log.info("✅ Created {} Hibernate & JPA interview questions", questions.size());
    }    

    private void createNodeJsFundamentalsModule() {
        LearningModule module = new LearningModule();
        module.setName("Node.js Fundamentals to Expert");
        module.setDescription("Complete Node.js mastery: from fundamentals to advanced patterns, microservices, and performance optimization");
        module.setCategory(LearningModule.Category.MICROSERVICES);
        module.setDifficultyLevel(LearningModule.DifficultyLevel.INTERMEDIATE);
        module.setEstimatedHours(45);
        module.setSortOrder(4);
        module.setTopics(new ArrayList<>());
        
        moduleRepository.save(module);
        
        // Create comprehensive Node.js topics
        createNodeJsCoreTopic(module);
        createAsyncProgrammingTopic(module);
        createNodeJsModulesTopic(module);
        createExpressFrameworkTopic(module);
        createNodeJsPerformanceTopic(module);
        
        // ZeroToMastery Foundation Topics (6-10)
        createFileIOStreamsPlanetsTopic(module);
        createWebServersHTTPTopic(module);
        createFullStackNASATopic(module);
        createTestingAPIsJestTopic(module);
        createDatabaseIntegrationTopic(module);
        
        // ZeroToMastery Advanced Topics (11-15)
        createRESTAPIIntegrationTopic(module);
        createAuthenticationSecurityTopic(module);
        createDeploymentCICDTopic(module);
        createGraphQLImplementationTopic(module);
        createWebSocketsRealTimeTopic(module);
        
        // ZeroToMastery Expert Topics (16-20)
        createMicroservicesArchitectureTopic(module);
        createServerlessCloudTopic(module);
        createDockerKubernetesTopic(module);
        createMonitoringLoggingTopic(module);
        createSecurityBestPracticesTopic(module);
        
        // FAANG Senior Enhancement Topics (21-25)
        createAdvancedPerformanceTuningTopic(module);
        createDistributedSystemsTopic(module);
        createEventDrivenArchitectureTopic(module);
        createProductionDebuggingTopic(module);
        createScalabilityPatternsTopic(module);
        
        createNodeJsInterviewQuestions(module);
        
        log.info("✅ Created Node.js Fundamentals module with {} topics", module.getTopics().size());
    }
    
    private void createNodeJsCoreTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Node.js Core Concepts and Event Loop");
        topic.setDescription("Master Node.js fundamentals: event loop, non-blocking I/O, streams, and core modules");
        topic.setContent("""
            <div class="topic-content">
                <h2>🚀 Node.js Core Concepts and Event Loop</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Understand Node.js architecture and the V8 engine</li>
                        <li>Master the event loop and non-blocking I/O concepts</li>
                        <li>Work with Node.js core modules and global objects</li>
                        <li>Implement streams and buffer handling</li>
                        <li>Debug and profile Node.js applications</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🏗️ Node.js Architecture and Event Loop</h3>
                    <p>Node.js is built on Chrome's V8 JavaScript engine and uses an event-driven, non-blocking I/O model.</p>
                    
                    <div class="code-example">
                        <h5>Event Loop Demonstration:</h5>
                        <pre><code class="language-javascript">
// Understanding the Event Loop phases
console.log('=== Event Loop Demo ===');

// 1. Call Stack (Synchronous)
console.log('1. Synchronous operation');

// 2. Timer Phase (setTimeout, setInterval)
setTimeout(() => {
    console.log('4. Timer callback (setTimeout 0ms)');
}, 0);

setTimeout(() => {
    console.log('6. Timer callback (setTimeout 10ms)');
}, 10);

// 3. I/O Phase (file operations, network requests)
const fs = require('fs');
fs.readFile(__filename, () => {
    console.log('5. I/O callback (file read)');
    
    // Check phase - setImmediate callbacks
    setImmediate(() => {
        console.log('7. Check phase (setImmediate)');
    });
    
    // Close phase
    process.nextTick(() => {
        console.log('8. Next tick callback');
    });
});

// 4. Immediate execution (process.nextTick has highest priority)
process.nextTick(() => {
    console.log('2. Next tick callback (highest priority)');
});

// 5. Check phase
setImmediate(() => {
    console.log('3. Immediate callback');
});

console.log('End of main thread');

// Output order demonstrates event loop phases:
// 1. Synchronous operation
// End of main thread
// 2. Next tick callback (highest priority)
// 3. Immediate callback
// 4. Timer callback (setTimeout 0ms)
// 5. I/O callback (file read)
// 6. Timer callback (setTimeout 10ms)
// 7. Check phase (setImmediate)
// 8. Next tick callback

// Event Loop Phases in Detail
function demonstrateEventLoopPhases() {
    console.log('\\n=== Event Loop Phases ===');
    
    // Phase 1: Timer Phase
    setTimeout(() => console.log('Timer Phase: setTimeout'), 0);
    setInterval(() => {
        console.log('Timer Phase: setInterval');
        clearInterval(this); // Clear after first execution
    }, 0);
    
    // Phase 2: Pending Callbacks Phase (internal use)
    
    // Phase 3: Idle, Prepare Phase (internal use)
    
    // Phase 4: Poll Phase (fetch new I/O events)
    fs.readFile(__filename, (err, data) => {
        console.log('Poll Phase: I/O callback');
        
        // Phase 5: Check Phase
        setImmediate(() => console.log('Check Phase: setImmediate'));
    });
    
    // Phase 6: Close Callbacks Phase
    const server = require('http').createServer();
    server.listen(0, () => {
        server.close(() => {
            console.log('Close Phase: server close callback');
        });
    });
    
    // Microtasks (executed between phases)
    process.nextTick(() => console.log('Microtask: process.nextTick'));
    Promise.resolve().then(() => console.log('Microtask: Promise'));
}

// Blocking vs Non-blocking operations
function demonstrateBlockingVsNonBlocking() {
    console.log('\\n=== Blocking vs Non-blocking ===');
    
    const start = Date.now();
    
    // Non-blocking I/O
    fs.readFile(__filename, (err, data) => {
        console.log(`Non-blocking read completed in ${Date.now() - start}ms`);
    });
    
    // Blocking I/O (avoid in production)
    try {
        const data = fs.readFileSync(__filename);
        console.log(`Blocking read completed in ${Date.now() - start}ms`);
    } catch (err) {
        console.error('Blocking read error:', err.message);
    }
    
    console.log('This executes immediately (non-blocking)');
}

// CPU-intensive task handling
function demonstrateCpuIntensiveTask() {
    console.log('\\n=== CPU Intensive Task Handling ===');
    
    // Bad: Blocks the event loop
    function blockingCpuTask(n) {
        let result = 0;
        for (let i = 0; i < n; i++) {
            result += Math.sqrt(i);
        }
        return result;
    }
    
    // Good: Non-blocking with setImmediate
    function nonBlockingCpuTask(n, callback) {
        let result = 0;
        let i = 0;
        
        function processChunk() {
            const chunkSize = 100000;
            const end = Math.min(i + chunkSize, n);
            
            for (; i < end; i++) {
                result += Math.sqrt(i);
            }
            
            if (i < n) {
                setImmediate(processChunk); // Yield control back to event loop
            } else {
                callback(null, result);
            }
        }
        
        processChunk();
    }
    
    // Better: Use Worker Threads for CPU-intensive tasks
    const { Worker, isMainThread, parentPort, workerData } = require('worker_threads');
    
    if (isMainThread) {
        function cpuTaskWithWorker(n, callback) {
            const worker = new Worker(__filename, {
                workerData: { n, isCpuTask: true }
            });
            
            worker.on('message', (result) => {
                callback(null, result);
                worker.terminate();
            });
            
            worker.on('error', callback);
        }
    } else if (workerData && workerData.isCpuTask) {
        // Worker thread code
        const { n } = workerData;
        let result = 0;
        for (let i = 0; i < n; i++) {
            result += Math.sqrt(i);
        }
        parentPort.postMessage(result);
    }
}

// Memory management and garbage collection
function demonstrateMemoryManagement() {
    console.log('\\n=== Memory Management ===');
    
    // Monitor memory usage
    function logMemoryUsage(label) {
        const usage = process.memoryUsage();
        console.log(`${label}:`);
        console.log(`  RSS: ${Math.round(usage.rss / 1024 / 1024)} MB`);
        console.log(`  Heap Used: ${Math.round(usage.heapUsed / 1024 / 1024)} MB`);
        console.log(`  Heap Total: ${Math.round(usage.heapTotal / 1024 / 1024)} MB`);
        console.log(`  External: ${Math.round(usage.external / 1024 / 1024)} MB`);
    }
    
    logMemoryUsage('Initial');
    
    // Create memory pressure
    const largeArray = new Array(1000000).fill('memory test');
    logMemoryUsage('After creating large array');
    
    // Force garbage collection (if --expose-gc flag is used)
    if (global.gc) {
        global.gc();
        logMemoryUsage('After garbage collection');
    }
    
    // Memory leak example (avoid this)
    const leakyArray = [];
    function memoryLeak() {
        leakyArray.push(new Array(1000).fill('leak'));
        setTimeout(memoryLeak, 100); // Keeps adding to array
    }
    
    // Proper cleanup
    function properCleanup() {
        const tempArray = [];
        // Use tempArray...
        // No need to explicitly clear, will be garbage collected
        return tempArray.length;
    }
}

// Error handling in asynchronous code
function demonstrateErrorHandling() {
    console.log('\\n=== Error Handling ===');
    
    // Uncaught exception handling
    process.on('uncaughtException', (err) => {
        console.error('Uncaught Exception:', err);
        // Log error and gracefully shutdown
        process.exit(1);
    });
    
    // Unhandled promise rejection
    process.on('unhandledRejection', (reason, promise) => {
        console.error('Unhandled Rejection at:', promise, 'reason:', reason);
        // Log error and gracefully shutdown
        process.exit(1);
    });
    
    // Proper error handling with callbacks
    fs.readFile('nonexistent.txt', (err, data) => {
        if (err) {
            console.error('File read error:', err.message);
            return;
        }
        console.log('File content:', data.toString());
    });
    
    // Proper error handling with promises
    const { promisify } = require('util');
    const readFileAsync = promisify(fs.readFile);
    
    async function readFileWithErrorHandling() {
        try {
            const data = await readFileAsync('nonexistent.txt');
            console.log('File content:', data.toString());
        } catch (err) {
            console.error('Async file read error:', err.message);
        }
    }
}

// Run demonstrations
if (require.main === module) {
    demonstrateEventLoopPhases();
    demonstrateBlockingVsNonBlocking();
    demonstrateCpuIntensiveTask();
    demonstrateMemoryManagement();
    demonstrateErrorHandling();
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="concept-section">
                    <h3>📦 Core Modules and Global Objects</h3>
                    <p>Node.js provides built-in modules and global objects that form the foundation of server-side JavaScript development.</p>
                    
                    <div class="code-example">
                        <h5>Core Modules Usage:</h5>
                        <pre><code class="language-javascript">
// File System Module
const fs = require('fs');
const path = require('path');
const { promisify } = require('util');

// Promisified versions for async/await
const readFile = promisify(fs.readFile);
const writeFile = promisify(fs.writeFile);
const mkdir = promisify(fs.mkdir);
const stat = promisify(fs.stat);

class FileManager {
    constructor(baseDir = './data') {
        this.baseDir = baseDir;
        this.ensureDirectory();
    }
    
    async ensureDirectory() {
        try {
            await mkdir(this.baseDir, { recursive: true });
        } catch (err) {
            if (err.code !== 'EEXIST') throw err;
        }
    }
    
    async readJSON(filename) {
        try {
            const filePath = path.join(this.baseDir, filename);
            const data = await readFile(filePath, 'utf8');
            return JSON.parse(data);
        } catch (err) {
            if (err.code === 'ENOENT') {
                return null; // File doesn't exist
            }
            throw err;
        }
    }
    
    async writeJSON(filename, data) {
        const filePath = path.join(this.baseDir, filename);
        const jsonData = JSON.stringify(data, null, 2);
        await writeFile(filePath, jsonData, 'utf8');
    }
    
    async getFileStats(filename) {
        const filePath = path.join(this.baseDir, filename);
        try {
            const stats = await stat(filePath);
            return {
                size: stats.size,
                created: stats.birthtime,
                modified: stats.mtime,
                isFile: stats.isFile(),
                isDirectory: stats.isDirectory()
            };
        } catch (err) {
            return null;
        }
    }
    
    // Watch for file changes
    watchFile(filename, callback) {
        const filePath = path.join(this.baseDir, filename);
        return fs.watch(filePath, (eventType, changedFilename) => {
            callback(eventType, changedFilename);
        });
    }
}

// HTTP Module
const http = require('http');
const url = require('url');
const querystring = require('querystring');

class SimpleHttpServer {
    constructor(port = 3000) {
        this.port = port;
        this.routes = new Map();
        this.middlewares = [];
    }
    
    use(middleware) {
        this.middlewares.push(middleware);
    }
    
    get(path, handler) {
        this.addRoute('GET', path, handler);
    }
    
    post(path, handler) {
        this.addRoute('POST', path, handler);
    }
    
    addRoute(method, path, handler) {
        const key = `${method}:${path}`;
        this.routes.set(key, handler);
    }
    
    async handleRequest(req, res) {
        // Parse URL and query parameters
        const parsedUrl = url.parse(req.url, true);
        req.pathname = parsedUrl.pathname;
        req.query = parsedUrl.query;
        
        // Parse body for POST requests
        if (req.method === 'POST') {
            req.body = await this.parseBody(req);
        }
        
        // Apply middlewares
        for (const middleware of this.middlewares) {
            await middleware(req, res);
        }
        
        // Find and execute route handler
        const routeKey = `${req.method}:${req.pathname}`;
        const handler = this.routes.get(routeKey);
        
        if (handler) {
            try {
                await handler(req, res);
            } catch (err) {
                this.handleError(err, res);
            }
        } else {
            this.send404(res);
        }
    }
    
    async parseBody(req) {
        return new Promise((resolve, reject) => {
            let body = '';
            req.on('data', chunk => {
                body += chunk.toString();
            });
            req.on('end', () => {
                try {
                    const contentType = req.headers['content-type'];
                    if (contentType && contentType.includes('application/json')) {
                        resolve(JSON.parse(body));
                    } else {
                        resolve(querystring.parse(body));
                    }
                } catch (err) {
                    reject(err);
                }
            });
            req.on('error', reject);
        });
    }
    
    handleError(err, res) {
        console.error('Server error:', err);
        res.writeHead(500, { 'Content-Type': 'application/json' });
        res.end(JSON.stringify({ error: 'Internal Server Error' }));
    }
    
    send404(res) {
        res.writeHead(404, { 'Content-Type': 'application/json' });
        res.end(JSON.stringify({ error: 'Not Found' }));
    }
    
    start() {
        const server = http.createServer((req, res) => {
            this.handleRequest(req, res);
        });
        
        server.listen(this.port, () => {
            console.log(`Server running on port ${this.port}`);
        });
        
        return server;
    }
}

// OS Module
const os = require('os');

class SystemInfo {
    static getSystemInfo() {
        return {
            platform: os.platform(),
            architecture: os.arch(),
            cpus: os.cpus().length,
            totalMemory: Math.round(os.totalmem() / 1024 / 1024 / 1024) + ' GB',
            freeMemory: Math.round(os.freemem() / 1024 / 1024 / 1024) + ' GB',
            uptime: Math.round(os.uptime() / 3600) + ' hours',
            loadAverage: os.loadavg(),
            networkInterfaces: Object.keys(os.networkInterfaces()),
            hostname: os.hostname(),
            homeDirectory: os.homedir(),
            tempDirectory: os.tmpdir()
        };
    }
    
    static monitorSystem(interval = 5000) {
        setInterval(() => {
            const info = SystemInfo.getSystemInfo();
            console.log('System Status:', {
                freeMemory: info.freeMemory,
                loadAverage: info.loadAverage[0].toFixed(2)
            });
        }, interval);
    }
}

// Crypto Module
const crypto = require('crypto');

class CryptoUtils {
    static generateHash(data, algorithm = 'sha256') {
        return crypto.createHash(algorithm).update(data).digest('hex');
    }
    
    static generateRandomBytes(size = 32) {
        return crypto.randomBytes(size);
    }
    
    static encrypt(text, password) {
        const algorithm = 'aes-256-cbc';
        const key = crypto.scryptSync(password, 'salt', 32);
        const iv = crypto.randomBytes(16);
        
        const cipher = crypto.createCipher(algorithm, key);
        let encrypted = cipher.update(text, 'utf8', 'hex');
        encrypted += cipher.final('hex');
        
        return {
            encrypted,
            iv: iv.toString('hex')
        };
    }
    
    static decrypt(encryptedData, password) {
        const algorithm = 'aes-256-cbc';
        const key = crypto.scryptSync(password, 'salt', 32);
        
        const decipher = crypto.createDecipher(algorithm, key);
        let decrypted = decipher.update(encryptedData.encrypted, 'hex', 'utf8');
        decrypted += decipher.final('utf8');
        
        return decrypted;
    }
    
    static generateJWT(payload, secret, expiresIn = '1h') {
        const header = { alg: 'HS256', typ: 'JWT' };
        const now = Math.floor(Date.now() / 1000);
        const exp = now + (expiresIn === '1h' ? 3600 : parseInt(expiresIn));
        
        const jwtPayload = { ...payload, iat: now, exp };
        
        const encodedHeader = Buffer.from(JSON.stringify(header)).toString('base64url');
        const encodedPayload = Buffer.from(JSON.stringify(jwtPayload)).toString('base64url');
        
        const signature = crypto
            .createHmac('sha256', secret)
            .update(`${encodedHeader}.${encodedPayload}`)
            .digest('base64url');
        
        return `${encodedHeader}.${encodedPayload}.${signature}`;
    }
}

// Global Objects and Process
class ProcessManager {
    static setupGracefulShutdown() {
        const signals = ['SIGTERM', 'SIGINT', 'SIGUSR2'];
        
        signals.forEach(signal => {
            process.on(signal, () => {
                console.log(`Received ${signal}, shutting down gracefully...`);
                this.cleanup();
                process.exit(0);
            });
        });
    }
    
    static cleanup() {
        // Close database connections
        // Stop servers
        // Clean up resources
        console.log('Cleanup completed');
    }
    
    static getProcessInfo() {
        return {
            pid: process.pid,
            version: process.version,
            platform: process.platform,
            arch: process.arch,
            uptime: process.uptime(),
            memoryUsage: process.memoryUsage(),
            cpuUsage: process.cpuUsage(),
            argv: process.argv,
            env: Object.keys(process.env).length + ' environment variables',
            cwd: process.cwd()
        };
    }
    
    static monitorProcess() {
        setInterval(() => {
            const usage = process.memoryUsage();
            const cpu = process.cpuUsage();
            
            console.log('Process Stats:', {
                memory: Math.round(usage.heapUsed / 1024 / 1024) + ' MB',
                cpu: cpu.user + cpu.system + ' microseconds'
            });
        }, 10000);
    }
}

// Usage examples
if (require.main === module) {
    // File manager example
    const fileManager = new FileManager();
    
    // HTTP server example
    const server = new SimpleHttpServer(3000);
    
    server.use(async (req, res) => {
        console.log(`${req.method} ${req.url}`);
    });
    
    server.get('/system', (req, res) => {
        const info = SystemInfo.getSystemInfo();
        res.writeHead(200, { 'Content-Type': 'application/json' });
        res.end(JSON.stringify(info, null, 2));
    });
    
    server.get('/process', (req, res) => {
        const info = ProcessManager.getProcessInfo();
        res.writeHead(200, { 'Content-Type': 'application/json' });
        res.end(JSON.stringify(info, null, 2));
    });
    
    // Start server
    server.start();
    
    // Setup graceful shutdown
    ProcessManager.setupGracefulShutdown();
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="nodejs-core">
                        <textarea placeholder="Add your personal notes about Node.js core concepts..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(180);
        topic.setSortOrder(1);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created Node.js Core Concepts topic");
    }    
 
   private void createAsyncProgrammingTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Asynchronous Programming: Callbacks, Promises, and Async/Await");
        topic.setDescription("Master asynchronous programming patterns in Node.js: callbacks, promises, async/await, and error handling");
        topic.setContent("""
            <div class="topic-content">
                <h2>⚡ Asynchronous Programming: Callbacks, Promises, and Async/Await</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Master callback patterns and avoid callback hell</li>
                        <li>Implement and chain promises effectively</li>
                        <li>Use async/await for clean asynchronous code</li>
                        <li>Handle errors properly in asynchronous operations</li>
                        <li>Understand concurrency patterns and parallel execution</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>📞 Callbacks and Callback Patterns</h3>
                    <p>Callbacks are the foundation of asynchronous programming in Node.js, but they can lead to complex nested structures.</p>
                    
                    <div class="code-example">
                        <h5>Callback Patterns and Best Practices:</h5>
                        <pre><code class="language-javascript">
const fs = require('fs');
const path = require('path');

// Basic callback pattern (Error-first callbacks)
function readFileCallback(filename, callback) {
    fs.readFile(filename, 'utf8', (err, data) => {
        if (err) {
            return callback(err, null); // Error-first convention
        }
        callback(null, data); // Success: null error, data as second argument
    });
}

// Callback hell example (what to avoid)
function callbackHellExample() {
    fs.readFile('file1.txt', 'utf8', (err1, data1) => {
        if (err1) throw err1;
        
        fs.readFile('file2.txt', 'utf8', (err2, data2) => {
            if (err2) throw err2;
            
            fs.readFile('file3.txt', 'utf8', (err3, data3) => {
                if (err3) throw err3;
                
                // Process all three files
                const combined = data1 + data2 + data3;
                fs.writeFile('combined.txt', combined, (err4) => {
                    if (err4) throw err4;
                    console.log('Files combined successfully');
                });
            });
        });
    });
}

// Better callback pattern with named functions
function readFile1(callback) {
    fs.readFile('file1.txt', 'utf8', callback);
}

function readFile2(data1, callback) {
    fs.readFile('file2.txt', 'utf8', (err, data2) => {
        if (err) return callback(err);
        callback(null, data1, data2);
    });
}

function readFile3(data1, data2, callback) {
    fs.readFile('file3.txt', 'utf8', (err, data3) => {
        if (err) return callback(err);
        callback(null, data1 + data2 + data3);
    });
}

function writeResult(combined, callback) {
    fs.writeFile('combined.txt', combined, callback);
}

function betterCallbackPattern() {
    readFile1((err, data1) => {
        if (err) throw err;
        
        readFile2(data1, (err, data1, data2) => {
            if (err) throw err;
            
            readFile3(data1, data2, (err, combined) => {
                if (err) throw err;
                
                writeResult(combined, (err) => {
                    if (err) throw err;
                    console.log('Files combined successfully');
                });
            });
        });
    });
}

// Callback utilities
class CallbackUtils {
    // Convert callback-based function to return multiple callbacks
    static parallel(tasks, callback) {
        let completed = 0;
        let results = [];
        let hasError = false;
        
        if (tasks.length === 0) {
            return callback(null, []);
        }
        
        tasks.forEach((task, index) => {
            task((err, result) => {
                if (hasError) return;
                
                if (err) {
                    hasError = true;
                    return callback(err);
                }
                
                results[index] = result;
                completed++;
                
                if (completed === tasks.length) {
                    callback(null, results);
                }
            });
        });
    }
    
    // Execute callbacks in series
    static series(tasks, callback) {
        let index = 0;
        let results = [];
        
        function next(err, result) {
            if (err) return callback(err);
            
            if (index > 0) {
                results.push(result);
            }
            
            if (index >= tasks.length) {
                return callback(null, results);
            }
            
            const task = tasks[index++];
            task(next);
        }
        
        next();
    }
    
    // Waterfall pattern - pass result to next function
    static waterfall(tasks, callback) {
        let index = 0;
        
        function next(err, ...args) {
            if (err) return callback(err);
            
            if (index >= tasks.length) {
                return callback(null, ...args);
            }
            
            const task = tasks[index++];
            task(...args, next);
        }
        
        next();
    }
    
    // Retry mechanism for callbacks
    static retry(task, maxAttempts, callback) {
        let attempts = 0;
        
        function attempt() {
            attempts++;
            task((err, result) => {
                if (!err) {
                    return callback(null, result);
                }
                
                if (attempts >= maxAttempts) {
                    return callback(new Error(`Failed after ${maxAttempts} attempts: ${err.message}`));
                }
                
                console.log(`Attempt ${attempts} failed, retrying...`);
                setTimeout(attempt, 1000 * attempts); // Exponential backoff
            });
        }
        
        attempt();
    }
}

// Usage examples
function demonstrateCallbackUtils() {
    // Parallel execution
    const parallelTasks = [
        (cb) => setTimeout(() => cb(null, 'Task 1'), 100),
        (cb) => setTimeout(() => cb(null, 'Task 2'), 200),
        (cb) => setTimeout(() => cb(null, 'Task 3'), 150)
    ];
    
    CallbackUtils.parallel(parallelTasks, (err, results) => {
        if (err) throw err;
        console.log('Parallel results:', results);
    });
    
    // Series execution
    const seriesTasks = [
        (cb) => setTimeout(() => cb(null, 'First'), 100),
        (cb) => setTimeout(() => cb(null, 'Second'), 100),
        (cb) => setTimeout(() => cb(null, 'Third'), 100)
    ];
    
    CallbackUtils.series(seriesTasks, (err, results) => {
        if (err) throw err;
        console.log('Series results:', results);
    });
    
    // Waterfall execution
    const waterfallTasks = [
        (cb) => cb(null, 1),
        (num, cb) => cb(null, num * 2),
        (num, cb) => cb(null, num + 3),
        (num, cb) => cb(null, `Result: ${num}`)
    ];
    
    CallbackUtils.waterfall(waterfallTasks, (err, result) => {
        if (err) throw err;
        console.log('Waterfall result:', result);
    });
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="concept-section">
                    <h3>🤝 Promises and Promise Chains</h3>
                    <p>Promises provide a cleaner alternative to callbacks, allowing for better error handling and composition.</p>
                    
                    <div class="code-example">
                        <h5>Promise Implementation and Patterns:</h5>
                        <pre><code class="language-javascript">
const { promisify } = require('util');

// Convert callback-based functions to promises
const readFileAsync = promisify(fs.readFile);
const writeFileAsync = promisify(fs.writeFile);

// Basic promise creation
function createPromiseExample() {
    return new Promise((resolve, reject) => {
        // Simulate async operation
        setTimeout(() => {
            const success = Math.random() > 0.3;
            if (success) {
                resolve('Operation successful');
            } else {
                reject(new Error('Operation failed'));
            }
        }, 1000);
    });
}

// Promise chaining
function promiseChainingExample() {
    return readFileAsync('input.txt', 'utf8')
        .then(data => {
            console.log('File read successfully');
            return data.toUpperCase(); // Transform data
        })
        .then(upperData => {
            console.log('Data transformed');
            return writeFileAsync('output.txt', upperData);
        })
        .then(() => {
            console.log('File written successfully');
            return 'Process completed';
        })
        .catch(err => {
            console.error('Error in promise chain:', err.message);
            throw err; // Re-throw if needed
        });
}

// Promise utilities class
class PromiseUtils {
    // Promise.all with timeout
    static allWithTimeout(promises, timeoutMs) {
        const timeoutPromise = new Promise((_, reject) => {
            setTimeout(() => reject(new Error('Timeout')), timeoutMs);
        });
        
        return Promise.race([
            Promise.all(promises),
            timeoutPromise
        ]);
    }
    
    // Promise.allSettled polyfill for older Node versions
    static allSettled(promises) {
        return Promise.all(
            promises.map(promise =>
                Promise.resolve(promise)
                    .then(value => ({ status: 'fulfilled', value }))
                    .catch(reason => ({ status: 'rejected', reason }))
            )
        );
    }
    
    // Sequential execution of promises
    static sequence(promiseFactories) {
        return promiseFactories.reduce(
            (chain, promiseFactory) => chain.then(promiseFactory),
            Promise.resolve()
        );
    }
    
    // Parallel execution with concurrency limit
    static parallelLimit(tasks, limit) {
        return new Promise((resolve, reject) => {
            let index = 0;
            let running = 0;
            let results = [];
            let completed = 0;
            
            function next() {
                if (completed === tasks.length) {
                    return resolve(results);
                }
                
                while (running < limit && index < tasks.length) {
                    const currentIndex = index++;
                    running++;
                    
                    Promise.resolve(tasks[currentIndex]())
                        .then(result => {
                            results[currentIndex] = result;
                            completed++;
                            running--;
                            next();
                        })
                        .catch(reject);
                }
            }
            
            next();
        });
    }
    
    // Retry with exponential backoff
    static retry(promiseFactory, maxAttempts, baseDelay = 1000) {
        return new Promise((resolve, reject) => {
            let attempts = 0;
            
            function attempt() {
                attempts++;
                
                promiseFactory()
                    .then(resolve)
                    .catch(err => {
                        if (attempts >= maxAttempts) {
                            return reject(new Error(`Failed after ${maxAttempts} attempts: ${err.message}`));
                        }
                        
                        const delay = baseDelay * Math.pow(2, attempts - 1);
                        console.log(`Attempt ${attempts} failed, retrying in ${delay}ms...`);
                        setTimeout(attempt, delay);
                    });
            }
            
            attempt();
        });
    }
    
    // Circuit breaker pattern
    static createCircuitBreaker(promiseFactory, threshold = 5, timeout = 60000) {
        let failures = 0;
        let lastFailureTime = 0;
        let state = 'CLOSED'; // CLOSED, OPEN, HALF_OPEN
        
        return function circuitBreakerWrapper(...args) {
            const now = Date.now();
            
            if (state === 'OPEN') {
                if (now - lastFailureTime > timeout) {
                    state = 'HALF_OPEN';
                } else {
                    return Promise.reject(new Error('Circuit breaker is OPEN'));
                }
            }
            
            return promiseFactory(...args)
                .then(result => {
                    if (state === 'HALF_OPEN') {
                        state = 'CLOSED';
                        failures = 0;
                    }
                    return result;
                })
                .catch(err => {
                    failures++;
                    lastFailureTime = now;
                    
                    if (failures >= threshold) {
                        state = 'OPEN';
                    }
                    
                    throw err;
                });
        };
    }
    
    // Promise memoization
    static memoize(promiseFactory, keyGenerator = (...args) => JSON.stringify(args)) {
        const cache = new Map();
        
        return function memoizedPromise(...args) {
            const key = keyGenerator(...args);
            
            if (cache.has(key)) {
                return cache.get(key);
            }
            
            const promise = promiseFactory(...args)
                .catch(err => {
                    cache.delete(key); // Remove failed promises from cache
                    throw err;
                });
            
            cache.set(key, promise);
            return promise;
        };
    }
}

// Advanced promise patterns
class AdvancedPromisePatterns {
    // Promise pool for resource management
    static createPromisePool(factory, poolSize = 5) {
        const pool = [];
        let index = 0;
        
        return {
            execute(task) {
                const poolIndex = index % poolSize;
                index++;
                
                if (!pool[poolIndex]) {
                    pool[poolIndex] = Promise.resolve();
                }
                
                pool[poolIndex] = pool[poolIndex]
                    .then(() => factory(task))
                    .catch(err => {
                        console.error('Pool task failed:', err);
                        throw err;
                    });
                
                return pool[poolIndex];
            }
        };
    }
    
    // Promise-based event emitter
    static createPromiseEmitter() {
        const listeners = new Map();
        
        return {
            on(event, listener) {
                if (!listeners.has(event)) {
                    listeners.set(event, []);
                }
                listeners.get(event).push(listener);
            },
            
            emit(event, data) {
                const eventListeners = listeners.get(event) || [];
                return Promise.all(
                    eventListeners.map(listener => 
                        Promise.resolve(listener(data))
                    )
                );
            },
            
            once(event) {
                return new Promise(resolve => {
                    const listener = (data) => {
                        resolve(data);
                        const eventListeners = listeners.get(event) || [];
                        const index = eventListeners.indexOf(listener);
                        if (index > -1) {
                            eventListeners.splice(index, 1);
                        }
                    };
                    this.on(event, listener);
                });
            }
        };
    }
}

// Usage examples
async function demonstratePromisePatterns() {
    console.log('\\n=== Promise Patterns Demo ===');
    
    // Promise chaining
    try {
        const result = await promiseChainingExample();
        console.log('Chain result:', result);
    } catch (err) {
        console.error('Chain error:', err.message);
    }
    
    // Parallel execution with limit
    const tasks = Array.from({ length: 10 }, (_, i) => 
        () => new Promise(resolve => 
            setTimeout(() => resolve(`Task ${i + 1}`), Math.random() * 1000)
        )
    );
    
    const limitedResults = await PromiseUtils.parallelLimit(tasks, 3);
    console.log('Limited parallel results:', limitedResults);
    
    // Retry mechanism
    const flakyTask = () => new Promise((resolve, reject) => {
        if (Math.random() > 0.7) {
            resolve('Success!');
        } else {
            reject(new Error('Random failure'));
        }
    });
    
    try {
        const retryResult = await PromiseUtils.retry(flakyTask, 3);
        console.log('Retry result:', retryResult);
    } catch (err) {
        console.error('Retry failed:', err.message);
    }
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="concept-section">
                    <h3>🎯 Async/Await and Modern Patterns</h3>
                    <p>Async/await provides the most readable way to handle asynchronous operations, making code look synchronous while remaining non-blocking.</p>
                    
                    <div class="code-example">
                        <h5>Async/Await Best Practices:</h5>
                        <pre><code class="language-javascript">
// Basic async/await usage
async function basicAsyncExample() {
    try {
        const data = await readFileAsync('example.txt', 'utf8');
        const processed = data.toUpperCase();
        await writeFileAsync('processed.txt', processed);
        return 'File processed successfully';
    } catch (error) {
        console.error('Error processing file:', error.message);
        throw error;
    }
}

// Parallel execution with async/await
async function parallelAsyncExample() {
    try {
        // Wrong: Sequential execution
        const file1 = await readFileAsync('file1.txt', 'utf8');
        const file2 = await readFileAsync('file2.txt', 'utf8');
        const file3 = await readFileAsync('file3.txt', 'utf8');
        
        // Right: Parallel execution
        const [file1Parallel, file2Parallel, file3Parallel] = await Promise.all([
            readFileAsync('file1.txt', 'utf8'),
            readFileAsync('file2.txt', 'utf8'),
            readFileAsync('file3.txt', 'utf8')
        ]);
        
        return { sequential: [file1, file2, file3], parallel: [file1Parallel, file2Parallel, file3Parallel] };
    } catch (error) {
        console.error('Error reading files:', error.message);
        throw error;
    }
}

// Error handling patterns
class AsyncErrorHandling {
    // Wrapper for better error handling
    static async safeAsync(asyncFn, defaultValue = null) {
        try {
            return await asyncFn();
        } catch (error) {
            console.error('Async operation failed:', error.message);
            return defaultValue;
        }
    }
    
    // Multiple error handling strategies
    static async withFallback(primaryFn, fallbackFn) {
        try {
            return await primaryFn();
        } catch (primaryError) {
            console.warn('Primary operation failed, trying fallback:', primaryError.message);
            try {
                return await fallbackFn();
            } catch (fallbackError) {
                throw new Error(`Both operations failed. Primary: ${primaryError.message}, Fallback: ${fallbackError.message}`);
            }
        }
    }
    
    // Timeout wrapper
    static async withTimeout(asyncFn, timeoutMs, timeoutMessage = 'Operation timed out') {
        const timeoutPromise = new Promise((_, reject) => {
            setTimeout(() => reject(new Error(timeoutMessage)), timeoutMs);
        });
        
        return Promise.race([asyncFn(), timeoutPromise]);
    }
    
    // Validation wrapper
    static async withValidation(asyncFn, validator) {
        const result = await asyncFn();
        
        if (!validator(result)) {
            throw new Error('Validation failed for async result');
        }
        
        return result;
    }
}

// Advanced async patterns
class AdvancedAsyncPatterns {
    // Async iterator for large datasets
    static async* asyncGenerator(items, batchSize = 10) {
        for (let i = 0; i < items.length; i += batchSize) {
            const batch = items.slice(i, i + batchSize);
            
            // Simulate async processing
            await new Promise(resolve => setTimeout(resolve, 100));
            
            yield batch.map(item => item * 2); // Process batch
        }
    }
    
    // Async queue with concurrency control
    static createAsyncQueue(concurrency = 3) {
        const queue = [];
        let running = 0;
        
        async function processQueue() {
            if (running >= concurrency || queue.length === 0) {
                return;
            }
            
            running++;
            const { task, resolve, reject } = queue.shift();
            
            try {
                const result = await task();
                resolve(result);
            } catch (error) {
                reject(error);
            } finally {
                running--;
                processQueue(); // Process next item
            }
        }
        
        return {
            add(task) {
                return new Promise((resolve, reject) => {
                    queue.push({ task, resolve, reject });
                    processQueue();
                });
            },
            
            get length() {
                return queue.length;
            },
            
            get running() {
                return running;
            }
        };
    }
    
    // Async cache with TTL
    static createAsyncCache(ttlMs = 300000) { // 5 minutes default
        const cache = new Map();
        
        return {
            async get(key, factory) {
                const cached = cache.get(key);
                
                if (cached && Date.now() - cached.timestamp < ttlMs) {
                    return cached.value;
                }
                
                const value = await factory();
                cache.set(key, { value, timestamp: Date.now() });
                
                return value;
            },
            
            delete(key) {
                cache.delete(key);
            },
            
            clear() {
                cache.clear();
            },
            
            get size() {
                return cache.size;
            }
        };
    }
    
    // Async state machine
    static createAsyncStateMachine(initialState, transitions) {
        let currentState = initialState;
        const listeners = new Map();
        
        return {
            get state() {
                return currentState;
            },
            
            async transition(action, payload) {
                const transition = transitions[currentState]?.[action];
                
                if (!transition) {
                    throw new Error(`Invalid transition: ${action} from state ${currentState}`);
                }
                
                const newState = await transition(payload);
                const oldState = currentState;
                currentState = newState;
                
                // Notify listeners
                const stateListeners = listeners.get(newState) || [];
                await Promise.all(stateListeners.map(listener => listener(oldState, newState, payload)));
                
                return newState;
            },
            
            onState(state, listener) {
                if (!listeners.has(state)) {
                    listeners.set(state, []);
                }
                listeners.get(state).push(listener);
            }
        };
    }
}

// Real-world async examples
class RealWorldAsyncExamples {
    // Database connection pool simulation
    static createDbPool(maxConnections = 10) {
        const connections = [];
        const waitingQueue = [];
        
        return {
            async getConnection() {
                if (connections.length < maxConnections) {
                    const connection = { id: Date.now(), inUse: true };
                    connections.push(connection);
                    return connection;
                }
                
                // Wait for available connection
                return new Promise(resolve => {
                    waitingQueue.push(resolve);
                });
            },
            
            releaseConnection(connection) {
                connection.inUse = false;
                
                if (waitingQueue.length > 0) {
                    const resolve = waitingQueue.shift();
                    connection.inUse = true;
                    resolve(connection);
                }
            },
            
            async query(sql, params) {
                const connection = await this.getConnection();
                
                try {
                    // Simulate query execution
                    await new Promise(resolve => setTimeout(resolve, Math.random() * 100));
                    return { sql, params, connectionId: connection.id };
                } finally {
                    this.releaseConnection(connection);
                }
            }
        };
    }
    
    // HTTP client with retry and circuit breaker
    static createHttpClient() {
        const circuitBreaker = PromiseUtils.createCircuitBreaker(
            async (url, options) => {
                // Simulate HTTP request
                const success = Math.random() > 0.2;
                if (!success) {
                    throw new Error('HTTP request failed');
                }
                return { status: 200, data: `Response from ${url}` };
            },
            3, // failure threshold
            30000 // timeout
        );
        
        return {
            async get(url, options = {}) {
                return PromiseUtils.retry(
                    () => circuitBreaker(url, { ...options, method: 'GET' }),
                    3
                );
            },
            
            async post(url, data, options = {}) {
                return PromiseUtils.retry(
                    () => circuitBreaker(url, { ...options, method: 'POST', data }),
                    3
                );
            }
        };
    }
}

// Usage demonstrations
async function demonstrateAsyncPatterns() {
    console.log('\\n=== Async/Await Patterns Demo ===');
    
    // Basic async/await
    try {
        const result = await basicAsyncExample();
        console.log('Basic async result:', result);
    } catch (error) {
        console.error('Basic async error:', error.message);
    }
    
    // Async generator
    const items = Array.from({ length: 25 }, (_, i) => i + 1);
    console.log('Processing items with async generator:');
    
    for await (const batch of AdvancedAsyncPatterns.asyncGenerator(items, 5)) {
        console.log('Processed batch:', batch);
    }
    
    // Async queue
    const queue = AdvancedAsyncPatterns.createAsyncQueue(2);
    
    const queueTasks = Array.from({ length: 5 }, (_, i) => 
        queue.add(async () => {
            await new Promise(resolve => setTimeout(resolve, Math.random() * 1000));
            return `Queue task ${i + 1} completed`;
        })
    );
    
    const queueResults = await Promise.all(queueTasks);
    console.log('Queue results:', queueResults);
    
    // Async cache
    const cache = AdvancedAsyncPatterns.createAsyncCache(5000);
    
    const cachedResult1 = await cache.get('key1', async () => {
        console.log('Cache miss - computing value');
        return 'Expensive computation result';
    });
    
    const cachedResult2 = await cache.get('key1', async () => {
        console.log('This should not be called');
        return 'New computation';
    });
    
    console.log('Cache results:', { first: cachedResult1, second: cachedResult2 });
}

// Run demonstrations
if (require.main === module) {
    demonstrateCallbackUtils();
    demonstratePromisePatterns();
    demonstrateAsyncPatterns();
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="async-programming">
                        <textarea placeholder="Add your personal notes about asynchronous programming..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(200);
        topic.setSortOrder(2);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created Asynchronous Programming topic");
    }    

    private void createNodeJsModulesTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Node.js Modules and Package Management");
        topic.setDescription("Master Node.js module system: CommonJS, ES modules, npm, package.json, and dependency management");
        topic.setContent("""
            <div class="topic-content">
                <h2>📦 Node.js Modules and Package Management</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Understand CommonJS and ES module systems</li>
                        <li>Master npm and package.json configuration</li>
                        <li>Implement module patterns and best practices</li>
                        <li>Handle dependency management and versioning</li>
                        <li>Create and publish npm packages</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🔧 CommonJS vs ES Modules</h3>
                    <p>Node.js supports both CommonJS (traditional) and ES modules (modern) systems for organizing code.</p>
                    
                    <div class="code-example">
                        <h5>Module Systems Comparison:</h5>
                        <pre><code class="language-javascript">
// ===== CommonJS (Traditional Node.js) =====

// math-utils.js (CommonJS)
const PI = 3.14159;

function add(a, b) {
    return a + b;
}

function multiply(a, b) {
    return a * b;
}

class Calculator {
    constructor() {
        this.history = [];
    }
    
    calculate(operation, a, b) {
        let result;
        switch (operation) {
            case 'add':
                result = add(a, b);
                break;
            case 'multiply':
                result = multiply(a, b);
                break;
            default:
                throw new Error('Unknown operation');
        }
        
        this.history.push({ operation, a, b, result });
        return result;
    }
    
    getHistory() {
        return [...this.history];
    }
}

// CommonJS exports
module.exports = {
    PI,
    add,
    multiply,
    Calculator
};

// Alternative export syntax
exports.PI = PI;
exports.add = add;
exports.multiply = multiply;
exports.Calculator = Calculator;

// Single export
module.exports = Calculator;

// ===== Using CommonJS modules =====

// app.js (CommonJS)
const { add, multiply, Calculator, PI } = require('./math-utils');
const fs = require('fs'); // Built-in module
const express = require('express'); // npm module

console.log('PI:', PI);
console.log('Add:', add(5, 3));

const calc = new Calculator();
console.log('Calculate:', calc.calculate('multiply', 4, 7));

// ===== ES Modules (Modern) =====

// math-utils.mjs (ES Modules)
export const PI = 3.14159;

export function add(a, b) {
    return a + b;
}

export function multiply(a, b) {
    return a * b;
}

export default class Calculator {
    constructor() {
        this.history = [];
    }
    
    calculate(operation, a, b) {
        let result;
        switch (operation) {
            case 'add':
                result = add(a, b);
                break;
            case 'multiply':
                result = multiply(a, b);
                break;
            default:
                throw new Error('Unknown operation');
        }
        
        this.history.push({ operation, a, b, result });
        return result;
    }
    
    getHistory() {
        return [...this.history];
    }
}

// Named and default exports together
export { PI as MATH_PI, add as sum };

// ===== Using ES Modules =====

// app.mjs (ES Modules)
import Calculator, { PI, add, multiply } from './math-utils.mjs';
import fs from 'fs/promises'; // Built-in module with promises
import express from 'express'; // npm module

console.log('PI:', PI);
console.log('Add:', add(5, 3));

const calc = new Calculator();
console.log('Calculate:', calc.calculate('multiply', 4, 7));

// Dynamic imports
async function loadModule() {
    const { default: Calculator, add } = await import('./math-utils.mjs');
    return new Calculator();
}

// ===== Hybrid approach (package.json with "type": "module") =====

// package.json
{
    "type": "module",
    "main": "index.js",
    "exports": {
        ".": "./index.js",
        "./utils": "./utils.js"
    }
}

// Now .js files use ES modules by default
// Use .cjs extension for CommonJS files

// ===== Module Resolution =====

class ModuleResolver {
    static demonstrateResolution() {
        console.log('Module resolution examples:');
        
        // Relative paths
        // require('./utils') -> looks for utils.js, utils/index.js
        // require('../lib/helper') -> goes up one directory
        
        // Absolute paths (from node_modules)
        // require('express') -> looks in node_modules/express
        // require('lodash/get') -> looks for lodash/get.js
        
        // Built-in modules
        // require('fs') -> Node.js built-in
        // require('path') -> Node.js built-in
        
        console.log('Module paths:', module.paths);
        console.log('Current filename:', __filename);
        console.log('Current dirname:', __dirname);
    }
    
    // Custom module loader
    static createCustomLoader() {
        const Module = require('module');
        const originalRequire = Module.prototype.require;
        
        Module.prototype.require = function(id) {
            console.log(`Loading module: ${id}`);
            return originalRequire.apply(this, arguments);
        };
    }
}

// ===== Module Patterns =====

// Singleton pattern
class DatabaseConnection {
    constructor() {
        if (DatabaseConnection.instance) {
            return DatabaseConnection.instance;
        }
        
        this.connected = false;
        this.connection = null;
        DatabaseConnection.instance = this;
    }
    
    async connect() {
        if (!this.connected) {
            // Simulate connection
            this.connection = { id: Date.now() };
            this.connected = true;
            console.log('Database connected');
        }
        return this.connection;
    }
}

module.exports = new DatabaseConnection(); // Export singleton instance

// Factory pattern
class LoggerFactory {
    static loggers = new Map();
    
    static createLogger(name, level = 'info') {
        if (this.loggers.has(name)) {
            return this.loggers.get(name);
        }
        
        const logger = {
            name,
            level,
            log(message) {
                console.log(`[${this.name}] ${message}`);
            },
            error(message) {
                console.error(`[${this.name}] ERROR: ${message}`);
            }
        };
        
        this.loggers.set(name, logger);
        return logger;
    }
}

module.exports = LoggerFactory;

// Revealing module pattern
const UtilityModule = (function() {
    // Private variables and functions
    let privateCounter = 0;
    
    function privateFunction() {
        return 'This is private';
    }
    
    function incrementCounter() {
        privateCounter++;
    }
    
    function getCounter() {
        return privateCounter;
    }
    
    // Public API
    return {
        increment: incrementCounter,
        getCount: getCounter,
        reset() {
            privateCounter = 0;
        }
    };
})();

module.exports = UtilityModule;

// Namespace pattern
const MyNamespace = {
    utils: {
        string: {
            capitalize(str) {
                return str.charAt(0).toUpperCase() + str.slice(1);
            },
            
            truncate(str, length) {
                return str.length > length ? str.slice(0, length) + '...' : str;
            }
        },
        
        array: {
            unique(arr) {
                return [...new Set(arr)];
            },
            
            chunk(arr, size) {
                const chunks = [];
                for (let i = 0; i < arr.length; i += size) {
                    chunks.push(arr.slice(i, i + size));
                }
                return chunks;
            }
        }
    },
    
    constants: {
        API_URL: 'https://api.example.com',
        MAX_RETRIES: 3,
        TIMEOUT: 5000
    }
};

module.exports = MyNamespace;
                        </code></pre>
                    </div>
                </div>
                
                <div class="concept-section">
                    <h3>📋 Package.json and NPM Management</h3>
                    <p>Package.json is the heart of Node.js projects, defining dependencies, scripts, and project metadata.</p>
                    
                    <div class="code-example">
                        <h5>Advanced Package.json Configuration:</h5>
                        <pre><code class="language-json">
{
  "name": "my-awesome-package",
  "version": "1.2.3",
  "description": "A comprehensive Node.js package example",
  "main": "index.js",
  "type": "module",
  "engines": {
    "node": ">=14.0.0",
    "npm": ">=6.0.0"
  },
  "scripts": {
    "start": "node index.js",
    "dev": "nodemon index.js",
    "test": "jest",
    "test:watch": "jest --watch",
    "test:coverage": "jest --coverage",
    "lint": "eslint src/**/*.js",
    "lint:fix": "eslint src/**/*.js --fix",
    "build": "webpack --mode production",
    "build:dev": "webpack --mode development",
    "clean": "rimraf dist",
    "prebuild": "npm run clean",
    "postinstall": "node scripts/setup.js",
    "precommit": "lint-staged",
    "release": "standard-version",
    "docker:build": "docker build -t my-app .",
    "docker:run": "docker run -p 3000:3000 my-app"
  },
  "dependencies": {
    "express": "^4.18.0",
    "mongoose": "^6.0.0",
    "lodash": "^4.17.21",
    "moment": "^2.29.0"
  },
  "devDependencies": {
    "jest": "^28.0.0",
    "nodemon": "^2.0.0",
    "eslint": "^8.0.0",
    "webpack": "^5.0.0",
    "rimraf": "^3.0.0",
    "lint-staged": "^12.0.0",
    "husky": "^7.0.0"
  },
  "peerDependencies": {
    "react": ">=16.8.0"
  },
  "optionalDependencies": {
    "fsevents": "^2.3.0"
  },
  "bundledDependencies": [
    "custom-internal-package"
  ],
  "keywords": [
    "nodejs",
    "javascript",
    "backend",
    "api"
  ],
  "author": {
    "name": "Your Name",
    "email": "your.email@example.com",
    "url": "https://yourwebsite.com"
  },
  "contributors": [
    {
      "name": "Contributor Name",
      "email": "contributor@example.com"
    }
  ],
  "license": "MIT",
  "repository": {
    "type": "git",
    "url": "https://github.com/username/repo.git"
  },
  "bugs": {
    "url": "https://github.com/username/repo/issues"
  },
  "homepage": "https://github.com/username/repo#readme",
  "files": [
    "dist",
    "lib",
    "index.js",
    "README.md"
  ],
  "exports": {
    ".": {
      "import": "./index.mjs",
      "require": "./index.cjs"
    },
    "./utils": {
      "import": "./utils/index.mjs",
      "require": "./utils/index.cjs"
    }
  },
  "bin": {
    "my-cli": "./bin/cli.js"
  },
  "config": {
    "port": 3000,
    "timeout": 5000
  },
  "private": false,
  "workspaces": [
    "packages/*"
  ],
  "lint-staged": {
    "*.js": [
      "eslint --fix",
      "git add"
    ]
  },
  "husky": {
    "hooks": {
      "pre-commit": "lint-staged",
      "pre-push": "npm test"
    }
  },
  "jest": {
    "testEnvironment": "node",
    "collectCoverageFrom": [
      "src/**/*.js",
      "!src/**/*.test.js"
    ]
  }
}
                        </code></pre>
                    </div>
                    
                    <div class="code-example">
                        <h5>NPM Scripts and Automation:</h5>
                        <pre><code class="language-javascript">
// scripts/setup.js - Post-install setup
const fs = require('fs');
const path = require('path');

console.log('Running post-install setup...');

// Create necessary directories
const dirs = ['logs', 'uploads', 'temp'];
dirs.forEach(dir => {
    const dirPath = path.join(process.cwd(), dir);
    if (!fs.existsSync(dirPath)) {
        fs.mkdirSync(dirPath, { recursive: true });
        console.log(`Created directory: ${dir}`);
    }
});

// Copy config files
const configTemplate = path.join(__dirname, 'config.template.json');
const configFile = path.join(process.cwd(), 'config.json');

if (fs.existsSync(configTemplate) && !fs.existsSync(configFile)) {
    fs.copyFileSync(configTemplate, configFile);
    console.log('Created config.json from template');
}

console.log('Setup completed!');

// scripts/build.js - Custom build script
const webpack = require('webpack');
const config = require('../webpack.config.js');

console.log('Starting build process...');

webpack(config, (err, stats) => {
    if (err || stats.hasErrors()) {
        console.error('Build failed:', err || stats.toJson().errors);
        process.exit(1);
    }
    
    console.log('Build completed successfully!');
    console.log(stats.toString({
        colors: true,
        modules: false,
        children: false,
        chunks: false,
        chunkModules: false
    }));
});

// Package management utilities
class PackageManager {
    static async checkOutdated() {
        const { execSync } = require('child_process');
        
        try {
            const output = execSync('npm outdated --json', { encoding: 'utf8' });
            const outdated = JSON.parse(output);
            
            console.log('Outdated packages:');
            Object.entries(outdated).forEach(([name, info]) => {
                console.log(`${name}: ${info.current} -> ${info.latest}`);
            });
        } catch (error) {
            console.log('All packages are up to date');
        }
    }
    
    static async auditSecurity() {
        const { execSync } = require('child_process');
        
        try {
            const output = execSync('npm audit --json', { encoding: 'utf8' });
            const audit = JSON.parse(output);
            
            console.log(`Security audit: ${audit.metadata.vulnerabilities.total} vulnerabilities found`);
            
            if (audit.metadata.vulnerabilities.high > 0) {
                console.warn(`⚠️  ${audit.metadata.vulnerabilities.high} high severity vulnerabilities`);
            }
        } catch (error) {
            console.error('Security audit failed:', error.message);
        }
    }
    
    static generateLockfile() {
        const packageJson = require('../package.json');
        const lockData = {
            name: packageJson.name,
            version: packageJson.version,
            lockfileVersion: 2,
            requires: true,
            packages: {}
        };
        
        // This would normally be much more complex
        // involving dependency resolution
        
        fs.writeFileSync('package-lock.json', JSON.stringify(lockData, null, 2));
        console.log('Generated package-lock.json');
    }
}

// Workspace management (for monorepos)
class WorkspaceManager {
    static async listWorkspaces() {
        const { execSync } = require('child_process');
        
        try {
            const output = execSync('npm ls --workspaces --json', { encoding: 'utf8' });
            const workspaces = JSON.parse(output);
            
            console.log('Workspaces:');
            Object.keys(workspaces.dependencies || {}).forEach(name => {
                console.log(`- ${name}`);
            });
        } catch (error) {
            console.log('No workspaces found');
        }
    }
    
    static async runInWorkspace(workspace, script) {
        const { spawn } = require('child_process');
        
        const child = spawn('npm', ['run', script, '--workspace', workspace], {
            stdio: 'inherit'
        });
        
        return new Promise((resolve, reject) => {
            child.on('close', (code) => {
                if (code === 0) {
                    resolve();
                } else {
                    reject(new Error(`Script failed with code ${code}`));
                }
            });
        });
    }
}

// Custom npm commands
class CustomCommands {
    static async clean() {
        const rimraf = require('rimraf');
        
        const dirsToClean = ['node_modules', 'dist', 'build', '.cache'];
        
        for (const dir of dirsToClean) {
            if (fs.existsSync(dir)) {
                await rimraf(dir);
                console.log(`Cleaned: ${dir}`);
            }
        }
    }
    
    static async freshInstall() {
        await this.clean();
        
        const { execSync } = require('child_process');
        execSync('npm install', { stdio: 'inherit' });
        
        console.log('Fresh install completed!');
    }
    
    static async updateAll() {
        const { execSync } = require('child_process');
        
        console.log('Updating all packages...');
        execSync('npm update', { stdio: 'inherit' });
        
        console.log('Running security audit...');
        await PackageManager.auditSecurity();
        
        console.log('Update completed!');
    }
}

// Usage in package.json scripts:
// "clean": "node scripts/clean.js",
// "fresh": "node scripts/fresh-install.js",
// "update-all": "node scripts/update-all.js"
                        </code></pre>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="nodejs-modules">
                        <textarea placeholder="Add your personal notes about Node.js modules..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(160);
        topic.setSortOrder(3);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created Node.js Modules topic");
    }   
 
    private void createExpressFrameworkTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Express.js Framework and Middleware");
        topic.setDescription("Master Express.js: routing, middleware, authentication, error handling, and RESTful API development");
        topic.setContent("""
            <div class="topic-content">
                <h2>🚀 Express.js Framework and Middleware</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Build robust Express.js applications and APIs</li>
                        <li>Implement custom middleware and understand the middleware stack</li>
                        <li>Handle routing, parameters, and request validation</li>
                        <li>Implement authentication and authorization</li>
                        <li>Master error handling and logging strategies</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🛣️ Express Routing and Middleware</h3>
                    <p>Express.js provides a robust routing system and middleware architecture for building web applications and APIs.</p>
                    
                    <div class="code-example">
                        <h5>Complete Express Application:</h5>
                        <pre><code class="language-javascript">
const express = require('express');
const cors = require('cors');
const helmet = require('helmet');
const morgan = require('morgan');
const rateLimit = require('express-rate-limit');
const compression = require('compression');

// Create Express application
const app = express();

// ===== MIDDLEWARE STACK =====

// Security middleware
app.use(helmet({
    contentSecurityPolicy: {
        directives: {
            defaultSrc: ["'self'"],
            styleSrc: ["'self'", "'unsafe-inline'"],
            scriptSrc: ["'self'"],
            imgSrc: ["'self'", "data:", "https:"]
        }
    }
}));

// CORS configuration
app.use(cors({
    origin: process.env.NODE_ENV === 'production' 
        ? ['https://yourdomain.com'] 
        : ['http://localhost:3000'],
    credentials: true,
    methods: ['GET', 'POST', 'PUT', 'DELETE', 'OPTIONS'],
    allowedHeaders: ['Content-Type', 'Authorization']
}));

// Rate limiting
const limiter = rateLimit({
    windowMs: 15 * 60 * 1000, // 15 minutes
    max: 100, // limit each IP to 100 requests per windowMs
    message: 'Too many requests from this IP',
    standardHeaders: true,
    legacyHeaders: false
});
app.use('/api/', limiter);

// Compression
app.use(compression());

// Logging
app.use(morgan('combined'));

// Body parsing
app.use(express.json({ limit: '10mb' }));
app.use(express.urlencoded({ extended: true, limit: '10mb' }));

// Static files
app.use('/static', express.static('public', {
    maxAge: '1d',
    etag: true
}));

// ===== CUSTOM MIDDLEWARE =====

// Request ID middleware
app.use((req, res, next) => {
    req.id = Math.random().toString(36).substr(2, 9);
    res.setHeader('X-Request-ID', req.id);
    next();
});

// Request timing middleware
app.use((req, res, next) => {
    req.startTime = Date.now();
    
    const originalSend = res.send;
    res.send = function(data) {
        const duration = Date.now() - req.startTime;
        res.setHeader('X-Response-Time', `${duration}ms`);
        console.log(`${req.method} ${req.path} - ${duration}ms`);
        return originalSend.call(this, data);
    };
    
    next();
});

// Authentication middleware
const authenticateToken = (req, res, next) => {
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1];
    
    if (!token) {
        return res.status(401).json({ error: 'Access token required' });
    }
    
    // Verify JWT token (simplified)
    try {
        const decoded = verifyJWT(token);
        req.user = decoded;
        next();
    } catch (error) {
        return res.status(403).json({ error: 'Invalid token' });
    }
};

// Authorization middleware
const authorize = (roles) => {
    return (req, res, next) => {
        if (!req.user) {
            return res.status(401).json({ error: 'Authentication required' });
        }
        
        if (roles && !roles.includes(req.user.role)) {
            return res.status(403).json({ error: 'Insufficient permissions' });
        }
        
        next();
    };
};

// Validation middleware
const validateRequest = (schema) => {
    return (req, res, next) => {
        const { error } = schema.validate(req.body);
        if (error) {
            return res.status(400).json({
                error: 'Validation failed',
                details: error.details.map(d => d.message)
            });
        }
        next();
    };
};

// ===== ROUTING =====

// Basic routes
app.get('/', (req, res) => {
    res.json({
        message: 'Welcome to Express API',
        version: '1.0.0',
        timestamp: new Date().toISOString()
    });
});

// Health check
app.get('/health', (req, res) => {
    res.json({
        status: 'healthy',
        uptime: process.uptime(),
        memory: process.memoryUsage(),
        timestamp: new Date().toISOString()
    });
});

// ===== API ROUTES =====

// User routes
const userRouter = express.Router();

// Get all users (with pagination)
userRouter.get('/', async (req, res, next) => {
    try {
        const page = parseInt(req.query.page) || 1;
        const limit = parseInt(req.query.limit) || 10;
        const offset = (page - 1) * limit;
        
        const users = await getUsersPaginated(offset, limit);
        const total = await getUsersCount();
        
        res.json({
            users,
            pagination: {
                page,
                limit,
                total,
                pages: Math.ceil(total / limit)
            }
        });
    } catch (error) {
        next(error);
    }
});

// Get user by ID
userRouter.get('/:id', async (req, res, next) => {
    try {
        const userId = parseInt(req.params.id);
        
        if (isNaN(userId)) {
            return res.status(400).json({ error: 'Invalid user ID' });
        }
        
        const user = await getUserById(userId);
        
        if (!user) {
            return res.status(404).json({ error: 'User not found' });
        }
        
        res.json(user);
    } catch (error) {
        next(error);
    }
});

// Create user
userRouter.post('/', validateRequest(userSchema), async (req, res, next) => {
    try {
        const userData = req.body;
        const newUser = await createUser(userData);
        
        res.status(201).json({
            message: 'User created successfully',
            user: newUser
        });
    } catch (error) {
        next(error);
    }
});

// Update user
userRouter.put('/:id', authenticateToken, async (req, res, next) => {
    try {
        const userId = parseInt(req.params.id);
        const updateData = req.body;
        
        // Check if user can update this profile
        if (req.user.id !== userId && req.user.role !== 'admin') {
            return res.status(403).json({ error: 'Cannot update other users' });
        }
        
        const updatedUser = await updateUser(userId, updateData);
        
        if (!updatedUser) {
            return res.status(404).json({ error: 'User not found' });
        }
        
        res.json({
            message: 'User updated successfully',
            user: updatedUser
        });
    } catch (error) {
        next(error);
    }
});

// Delete user
userRouter.delete('/:id', authenticateToken, authorize(['admin']), async (req, res, next) => {
    try {
        const userId = parseInt(req.params.id);
        const deleted = await deleteUser(userId);
        
        if (!deleted) {
            return res.status(404).json({ error: 'User not found' });
        }
        
        res.json({ message: 'User deleted successfully' });
    } catch (error) {
        next(error);
    }
});

// Mount user routes
app.use('/api/users', userRouter);

// ===== ADVANCED ROUTING PATTERNS =====

// Route parameters with validation
app.param('userId', (req, res, next, id) => {
    const userId = parseInt(id);
    
    if (isNaN(userId) || userId <= 0) {
        return res.status(400).json({ error: 'Invalid user ID format' });
    }
    
    req.userId = userId;
    next();
});

// Nested routes
const postRouter = express.Router({ mergeParams: true });

postRouter.get('/', async (req, res, next) => {
    try {
        const posts = await getPostsByUserId(req.userId);
        res.json(posts);
    } catch (error) {
        next(error);
    }
});

postRouter.post('/', authenticateToken, async (req, res, next) => {
    try {
        const postData = { ...req.body, userId: req.userId };
        const newPost = await createPost(postData);
        res.status(201).json(newPost);
    } catch (error) {
        next(error);
    }
});

app.use('/api/users/:userId/posts', postRouter);

// ===== FILE UPLOAD HANDLING =====

const multer = require('multer');
const path = require('path');

// Configure multer for file uploads
const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, 'uploads/');
    },
    filename: (req, file, cb) => {
        const uniqueSuffix = Date.now() + '-' + Math.round(Math.random() * 1E9);
        cb(null, file.fieldname + '-' + uniqueSuffix + path.extname(file.originalname));
    }
});

const upload = multer({
    storage,
    limits: {
        fileSize: 5 * 1024 * 1024 // 5MB limit
    },
    fileFilter: (req, file, cb) => {
        const allowedTypes = /jpeg|jpg|png|gif/;
        const extname = allowedTypes.test(path.extname(file.originalname).toLowerCase());
        const mimetype = allowedTypes.test(file.mimetype);
        
        if (mimetype && extname) {
            return cb(null, true);
        } else {
            cb(new Error('Only image files are allowed'));
        }
    }
});

// File upload route
app.post('/api/upload', authenticateToken, upload.single('image'), (req, res) => {
    if (!req.file) {
        return res.status(400).json({ error: 'No file uploaded' });
    }
    
    res.json({
        message: 'File uploaded successfully',
        file: {
            filename: req.file.filename,
            originalname: req.file.originalname,
            size: req.file.size,
            path: req.file.path
        }
    });
});

// ===== ERROR HANDLING =====

// 404 handler
app.use('*', (req, res) => {
    res.status(404).json({
        error: 'Route not found',
        path: req.originalUrl,
        method: req.method
    });
});

// Global error handler
app.use((error, req, res, next) => {
    console.error('Error:', error);
    
    // Mongoose validation error
    if (error.name === 'ValidationError') {
        return res.status(400).json({
            error: 'Validation failed',
            details: Object.values(error.errors).map(e => e.message)
        });
    }
    
    // JWT error
    if (error.name === 'JsonWebTokenError') {
        return res.status(401).json({ error: 'Invalid token' });
    }
    
    // Multer error
    if (error instanceof multer.MulterError) {
        if (error.code === 'LIMIT_FILE_SIZE') {
            return res.status(400).json({ error: 'File too large' });
        }
    }
    
    // Default error
    res.status(error.status || 500).json({
        error: process.env.NODE_ENV === 'production' 
            ? 'Internal server error' 
            : error.message,
        requestId: req.id
    });
});

// ===== SERVER STARTUP =====

const PORT = process.env.PORT || 3000;

const server = app.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
    console.log(`Environment: ${process.env.NODE_ENV || 'development'}`);
});

// Graceful shutdown
process.on('SIGTERM', () => {
    console.log('SIGTERM received, shutting down gracefully');
    server.close(() => {
        console.log('Process terminated');
    });
});

module.exports = app;

// ===== UTILITY FUNCTIONS =====

function verifyJWT(token) {
    // JWT verification logic
    return { id: 1, role: 'user' }; // Simplified
}

async function getUsersPaginated(offset, limit) {
    // Database query logic
    return []; // Simplified
}

async function getUsersCount() {
    return 0; // Simplified
}

async function getUserById(id) {
    return null; // Simplified
}

async function createUser(userData) {
    return userData; // Simplified
}

async function updateUser(id, updateData) {
    return updateData; // Simplified
}

async function deleteUser(id) {
    return true; // Simplified
}

async function getPostsByUserId(userId) {
    return []; // Simplified
}

async function createPost(postData) {
    return postData; // Simplified
}

const userSchema = {
    validate: (data) => ({ error: null }) // Simplified
};
                        </code></pre>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="express-framework">
                        <textarea placeholder="Add your personal notes about Express.js..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(180);
        topic.setSortOrder(4);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created Express Framework topic");
    }
    
    private void createNodeJsPerformanceTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Node.js Performance Optimization and Scaling");
        topic.setDescription("Master Node.js performance: profiling, clustering, caching, memory management, and production optimization");
        topic.setContent("""
            <div class="topic-content">
                <h2>⚡ Node.js Performance Optimization and Scaling</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Profile and optimize Node.js application performance</li>
                        <li>Implement clustering and load balancing strategies</li>
                        <li>Master memory management and garbage collection</li>
                        <li>Use caching and database optimization techniques</li>
                        <li>Deploy and scale Node.js applications in production</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>📊 Performance Profiling and Monitoring</h3>
                    <p>Understanding and measuring performance is crucial for optimizing Node.js applications.</p>
                    
                    <div class="code-example">
                        <h5>Performance Monitoring Implementation:</h5>
                        <pre><code class="language-javascript">
const cluster = require('cluster');
const os = require('os');
const { performance, PerformanceObserver } = require('perf_hooks');

// Performance monitoring class
class PerformanceMonitor {
    constructor() {
        this.metrics = {
            requests: 0,
            errors: 0,
            responseTime: [],
            memoryUsage: [],
            cpuUsage: []
        };
        
        this.setupPerformanceObserver();
        this.startMonitoring();
    }
    
    setupPerformanceObserver() {
        const obs = new PerformanceObserver((list) => {
            list.getEntries().forEach((entry) => {
                if (entry.entryType === 'measure') {
                    this.metrics.responseTime.push(entry.duration);
                    
                    // Keep only last 1000 measurements
                    if (this.metrics.responseTime.length > 1000) {
                        this.metrics.responseTime.shift();
                    }
                }
            });
        });
        
        obs.observe({ entryTypes: ['measure'] });
    }
    
    startMonitoring() {
        setInterval(() => {
            this.collectSystemMetrics();
        }, 5000); // Every 5 seconds
    }
    
    collectSystemMetrics() {
        const memUsage = process.memoryUsage();
        const cpuUsage = process.cpuUsage();
        
        this.metrics.memoryUsage.push({
            timestamp: Date.now(),
            rss: memUsage.rss,
            heapUsed: memUsage.heapUsed,
            heapTotal: memUsage.heapTotal,
            external: memUsage.external
        });
        
        this.metrics.cpuUsage.push({
            timestamp: Date.now(),
            user: cpuUsage.user,
            system: cpuUsage.system
        });
        
        // Keep only last 100 measurements
        if (this.metrics.memoryUsage.length > 100) {
            this.metrics.memoryUsage.shift();
        }
        if (this.metrics.cpuUsage.length > 100) {
            this.metrics.cpuUsage.shift();
        }
    }
    
    measureRequest(req, res, next) {
        const startMark = `request-start-${req.id}`;
        const endMark = `request-end-${req.id}`;
        const measureName = `request-${req.id}`;
        
        performance.mark(startMark);
        
        res.on('finish', () => {
            performance.mark(endMark);
            performance.measure(measureName, startMark, endMark);
            
            this.metrics.requests++;
            
            if (res.statusCode >= 400) {
                this.metrics.errors++;
            }
        });
        
        next();
    }
    
    getStats() {
        const responseTime = this.metrics.responseTime;
        const avgResponseTime = responseTime.length > 0 
            ? responseTime.reduce((a, b) => a + b, 0) / responseTime.length 
            : 0;
        
        const p95ResponseTime = responseTime.length > 0
            ? responseTime.sort((a, b) => a - b)[Math.floor(responseTime.length * 0.95)]
            : 0;
        
        const errorRate = this.metrics.requests > 0 
            ? (this.metrics.errors / this.metrics.requests) * 100 
            : 0;
        
        const latestMemory = this.metrics.memoryUsage[this.metrics.memoryUsage.length - 1];
        
        return {
            requests: this.metrics.requests,
            errors: this.metrics.errors,
            errorRate: errorRate.toFixed(2) + '%',
            avgResponseTime: avgResponseTime.toFixed(2) + 'ms',
            p95ResponseTime: p95ResponseTime.toFixed(2) + 'ms',
            memoryUsage: latestMemory ? {
                rss: Math.round(latestMemory.rss / 1024 / 1024) + 'MB',
                heapUsed: Math.round(latestMemory.heapUsed / 1024 / 1024) + 'MB',
                heapTotal: Math.round(latestMemory.heapTotal / 1024 / 1024) + 'MB'
            } : null,
            uptime: process.uptime() + 's'
        };
    }
}

// Clustering for multi-core utilization
class ClusterManager {
    static start() {
        const numCPUs = os.cpus().length;
        
        if (cluster.isMaster) {
            console.log(`Master ${process.pid} is running`);
            console.log(`Starting ${numCPUs} workers...`);
            
            // Fork workers
            for (let i = 0; i < numCPUs; i++) {
                cluster.fork();
            }
            
            // Handle worker events
            cluster.on('exit', (worker, code, signal) => {
                console.log(`Worker ${worker.process.pid} died`);
                console.log('Starting a new worker...');
                cluster.fork();
            });
            
            // Graceful shutdown
            process.on('SIGTERM', () => {
                console.log('Master received SIGTERM, shutting down workers...');
                
                for (const id in cluster.workers) {
                    cluster.workers[id].kill();
                }
            });
            
            // Worker health monitoring
            setInterval(() => {
                for (const id in cluster.workers) {
                    const worker = cluster.workers[id];
                    worker.send('health-check');
                }
            }, 30000); // Every 30 seconds
            
        } else {
            // Worker process
            const app = require('./app'); // Your Express app
            const monitor = new PerformanceMonitor();
            
            app.use(monitor.measureRequest.bind(monitor));
            
            const server = app.listen(process.env.PORT || 3000, () => {
                console.log(`Worker ${process.pid} started`);
            });
            
            // Health check response
            process.on('message', (msg) => {
                if (msg === 'health-check') {
                    process.send({
                        type: 'health',
                        pid: process.pid,
                        memory: process.memoryUsage(),
                        uptime: process.uptime()
                    });
                }
            });
            
            // Graceful shutdown
            process.on('SIGTERM', () => {
                console.log(`Worker ${process.pid} received SIGTERM`);
                server.close(() => {
                    process.exit(0);
                });
            });
        }
    }
}

// Memory optimization techniques
class MemoryOptimizer {
    static setupMemoryMonitoring() {
        setInterval(() => {
            const usage = process.memoryUsage();
            const threshold = 500 * 1024 * 1024; // 500MB
            
            if (usage.heapUsed > threshold) {
                console.warn('High memory usage detected:', {
                    heapUsed: Math.round(usage.heapUsed / 1024 / 1024) + 'MB',
                    heapTotal: Math.round(usage.heapTotal / 1024 / 1024) + 'MB',
                    rss: Math.round(usage.rss / 1024 / 1024) + 'MB'
                });
                
                // Force garbage collection if available
                if (global.gc) {
                    global.gc();
                    console.log('Forced garbage collection');
                }
            }
        }, 10000);
    }
    
    static createObjectPool(createFn, resetFn, initialSize = 10) {
        const pool = [];
        
        // Pre-populate pool
        for (let i = 0; i < initialSize; i++) {
            pool.push(createFn());
        }
        
        return {
            acquire() {
                return pool.length > 0 ? pool.pop() : createFn();
            },
            
            release(obj) {
                resetFn(obj);
                pool.push(obj);
            },
            
            size() {
                return pool.length;
            }
        };
    }
    
    static createBufferPool(bufferSize = 1024, poolSize = 100) {
        return this.createObjectPool(
            () => Buffer.allocUnsafe(bufferSize),
            (buffer) => buffer.fill(0),
            poolSize
        );
    }
}

// Caching strategies
class CacheManager {
    constructor() {
        this.cache = new Map();
        this.ttl = new Map();
        this.stats = { hits: 0, misses: 0 };
    }
    
    set(key, value, ttlMs = 300000) { // 5 minutes default
        this.cache.set(key, value);
        this.ttl.set(key, Date.now() + ttlMs);
    }
    
    get(key) {
        if (!this.cache.has(key)) {
            this.stats.misses++;
            return null;
        }
        
        const expiry = this.ttl.get(key);
        if (Date.now() > expiry) {
            this.cache.delete(key);
            this.ttl.delete(key);
            this.stats.misses++;
            return null;
        }
        
        this.stats.hits++;
        return this.cache.get(key);
    }
    
    delete(key) {
        this.cache.delete(key);
        this.ttl.delete(key);
    }
    
    clear() {
        this.cache.clear();
        this.ttl.clear();
    }
    
    getStats() {
        const total = this.stats.hits + this.stats.misses;
        return {
            hits: this.stats.hits,
            misses: this.stats.misses,
            hitRate: total > 0 ? (this.stats.hits / total * 100).toFixed(2) + '%' : '0%',
            size: this.cache.size
        };
    }
    
    // Cleanup expired entries
    cleanup() {
        const now = Date.now();
        for (const [key, expiry] of this.ttl.entries()) {
            if (now > expiry) {
                this.cache.delete(key);
                this.ttl.delete(key);
            }
        }
    }
}

// Database optimization
class DatabaseOptimizer {
    static createConnectionPool(config) {
        const pool = [];
        const maxConnections = config.max || 10;
        const minConnections = config.min || 2;
        
        return {
            async getConnection() {
                if (pool.length > 0) {
                    return pool.pop();
                }
                
                if (pool.length < maxConnections) {
                    return await this.createConnection(config);
                }
                
                // Wait for available connection
                return new Promise((resolve) => {
                    const checkForConnection = () => {
                        if (pool.length > 0) {
                            resolve(pool.pop());
                        } else {
                            setTimeout(checkForConnection, 10);
                        }
                    };
                    checkForConnection();
                });
            },
            
            releaseConnection(connection) {
                if (pool.length < maxConnections) {
                    pool.push(connection);
                } else {
                    connection.close();
                }
            },
            
            async createConnection(config) {
                // Database connection logic
                return { id: Date.now(), query: async () => {} };
            }
        };
    }
    
    static createQueryCache() {
        const cache = new CacheManager();
        
        return {
            async query(sql, params = []) {
                const cacheKey = JSON.stringify({ sql, params });
                const cached = cache.get(cacheKey);
                
                if (cached) {
                    return cached;
                }
                
                // Execute query (simplified)
                const result = await this.executeQuery(sql, params);
                
                // Cache SELECT queries only
                if (sql.trim().toLowerCase().startsWith('select')) {
                    cache.set(cacheKey, result, 60000); // 1 minute cache
                }
                
                return result;
            },
            
            async executeQuery(sql, params) {
                // Actual database query execution
                return { rows: [], rowCount: 0 };
            },
            
            invalidateCache(pattern) {
                // Invalidate cache entries matching pattern
                cache.clear(); // Simplified
            }
        };
    }
}

// Production deployment utilities
class ProductionOptimizer {
    static setupProcessMonitoring() {
        // CPU usage monitoring
        setInterval(() => {
            const usage = process.cpuUsage();
            const loadAvg = os.loadavg();
            
            console.log('System metrics:', {
                cpuUsage: {
                    user: usage.user,
                    system: usage.system
                },
                loadAverage: loadAvg,
                freeMemory: Math.round(os.freemem() / 1024 / 1024) + 'MB',
                totalMemory: Math.round(os.totalmem() / 1024 / 1024) + 'MB'
            });
        }, 30000);
        
        // Handle uncaught exceptions
        process.on('uncaughtException', (err) => {
            console.error('Uncaught Exception:', err);
            // Log to external service
            process.exit(1);
        });
        
        process.on('unhandledRejection', (reason, promise) => {
            console.error('Unhandled Rejection at:', promise, 'reason:', reason);
            // Log to external service
        });
    }
    
    static setupGracefulShutdown(server) {
        const shutdown = (signal) => {
            console.log(`Received ${signal}, shutting down gracefully...`);
            
            server.close(() => {
                console.log('HTTP server closed');
                
                // Close database connections
                // Clear caches
                // Finish pending operations
                
                process.exit(0);
            });
            
            // Force shutdown after 30 seconds
            setTimeout(() => {
                console.error('Could not close connections in time, forcefully shutting down');
                process.exit(1);
            }, 30000);
        };
        
        process.on('SIGTERM', () => shutdown('SIGTERM'));
        process.on('SIGINT', () => shutdown('SIGINT'));
    }
    
    static optimizeForProduction(app) {
        // Disable X-Powered-By header
        app.disable('x-powered-by');
        
        // Set trust proxy for load balancers
        app.set('trust proxy', 1);
        
        // Optimize JSON parsing
        app.use(express.json({ limit: '1mb' }));
        
        // Enable compression
        const compression = require('compression');
        app.use(compression());
        
        // Set security headers
        const helmet = require('helmet');
        app.use(helmet());
        
        return app;
    }
}

// Usage example
if (require.main === module) {
    // Start clustered application
    ClusterManager.start();
    
    // Setup memory monitoring
    MemoryOptimizer.setupMemoryMonitoring();
    
    // Setup production monitoring
    ProductionOptimizer.setupProcessMonitoring();
}

module.exports = {
    PerformanceMonitor,
    ClusterManager,
    MemoryOptimizer,
    CacheManager,
    DatabaseOptimizer,
    ProductionOptimizer
};
                        </code></pre>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="nodejs-performance">
                        <textarea placeholder="Add your personal notes about Node.js performance..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(200);
        topic.setSortOrder(5);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created Node.js Performance topic");
    } 
   
    private void createNodeJsInterviewQuestions(LearningModule module) {
        List<InterviewQuestion> questions = new ArrayList<>();
        
        // Node.js Core Concepts Questions
        questions.addAll(Arrays.asList(
            createInterviewQuestion("Explain the Node.js event loop and its phases in detail.", 
                "HARD", "Amazon", "Node.js", module,
                "Event loop phases: 1) Timer phase (setTimeout, setInterval) 2) Pending callbacks 3) Idle, prepare 4) Poll (I/O events) 5) Check (setImmediate) 6) Close callbacks. Microtasks (process.nextTick, Promises) execute between phases."),
            
            createInterviewQuestion("What is the difference between process.nextTick() and setImmediate()?", 
                "MEDIUM", "Google", "Node.js", module,
                "process.nextTick() has highest priority, executes before any other async operation. setImmediate() executes in the Check phase of event loop. nextTick can cause starvation if used recursively."),
            
            createInterviewQuestion("How does Node.js handle child processes? Explain spawn, exec, and fork.", 
                "HARD", "Microsoft", "Node.js", module,
                "spawn(): Launches new process with given command. exec(): Executes command in shell, buffers output. fork(): Special case of spawn for Node.js processes, enables IPC. execFile(): Like exec but doesn't use shell."),
            
            createInterviewQuestion("What are streams in Node.js? Explain the different types.", 
                "MEDIUM", "Meta", "Node.js", module,
                "Streams handle data flow efficiently. Types: 1) Readable (fs.createReadStream) 2) Writable (fs.createWriteStream) 3) Duplex (TCP sockets) 4) Transform (zlib, crypto). Benefits: memory efficiency, composability."),
            
            createInterviewQuestion("Explain Node.js clustering and how it helps with performance.", 
                "HARD", "Apple", "Node.js", module,
                "Clustering creates multiple worker processes sharing the same port. Master process manages workers, handles load balancing. Utilizes multi-core systems since Node.js is single-threaded. Workers can be restarted independently.")
        ));
        
        // Asynchronous Programming Questions
        questions.addAll(Arrays.asList(
            createInterviewQuestion("What is callback hell and how do you avoid it?", 
                "MEDIUM", "Amazon", "Node.js", module,
                "Callback hell is deeply nested callbacks making code hard to read/maintain. Solutions: 1) Named functions 2) Promises 3) Async/await 4) Libraries like async.js 5) Proper error handling patterns."),
            
            createInterviewQuestion("Explain the difference between Promise.all(), Promise.allSettled(), and Promise.race().", 
                "MEDIUM", "Google", "Node.js", module,
                "Promise.all(): Resolves when all promises resolve, rejects if any rejects. Promise.allSettled(): Waits for all promises to settle (resolve/reject). Promise.race(): Resolves/rejects with first settled promise."),
            
            createInterviewQuestion("How do you handle errors in async/await? What are the best practices?", 
                "MEDIUM", "Microsoft", "Node.js", module,
                "Use try/catch blocks for async/await. Handle errors at appropriate levels. Use Promise.catch() for promise chains. Implement global error handlers. Always handle rejected promises to avoid unhandled rejections."),
            
            createInterviewQuestion("What are the performance implications of using async/await vs Promises?", 
                "HARD", "Meta", "Node.js", module,
                "Async/await is syntactic sugar over Promises. Performance is nearly identical. Async/await can be slower in tight loops due to overhead. Promises allow better parallelization with Promise.all(). Choose based on readability needs."),
            
            createInterviewQuestion("Explain how you would implement a retry mechanism with exponential backoff.", 
                "HARD", "Apple", "Node.js", module,
                "Implement recursive function with increasing delays: delay = baseDelay * (2 ^ attempt). Add jitter to prevent thundering herd. Set maximum attempts and delay limits. Use setTimeout for delays. Handle different error types appropriately.")
        ));
        
        // Express.js Questions
        questions.addAll(Arrays.asList(
            createInterviewQuestion("Explain the Express.js middleware stack and how it works.", 
                "MEDIUM", "Amazon", "Node.js", module,
                "Middleware functions execute sequentially in order of definition. Each middleware has access to req, res, and next(). Calling next() passes control to next middleware. Error middleware has 4 parameters (err, req, res, next)."),
            
            createInterviewQuestion("How do you implement authentication and authorization in Express.js?", 
                "HARD", "Google", "Node.js", module,
                "Authentication: Verify identity using JWT, sessions, or OAuth. Authorization: Check permissions using middleware. Implement role-based access control. Use passport.js for complex auth strategies. Secure routes with middleware guards."),
            
            createInterviewQuestion("What are the best practices for error handling in Express.js?", 
                "MEDIUM", "Microsoft", "Node.js", module,
                "Use error-handling middleware with 4 parameters. Catch async errors with try/catch or .catch(). Use next(error) to pass errors. Implement global error handler. Log errors appropriately. Return user-friendly error messages."),
            
            createInterviewQuestion("How do you implement rate limiting in Express.js applications?", 
                "MEDIUM", "Meta", "Node.js", module,
                "Use express-rate-limit middleware. Configure window time and max requests. Store rate limit data in memory/Redis. Implement different limits for different routes. Handle rate limit exceeded responses. Consider distributed rate limiting."),
            
            createInterviewQuestion("Explain how to optimize Express.js applications for production.", 
                "HARD", "Apple", "Node.js", module,
                "Use compression middleware. Enable gzip. Set proper cache headers. Use helmet for security. Implement clustering. Use reverse proxy (nginx). Monitor performance. Optimize database queries. Use CDN for static assets.")
        ));
        
        // Performance and Scaling Questions
        questions.addAll(Arrays.asList(
            createInterviewQuestion("How do you profile and debug performance issues in Node.js?", 
                "HARD", "Amazon", "Node.js", module,
                "Use built-in profiler (--prof flag). Use clinic.js for comprehensive profiling. Monitor event loop lag. Use flame graphs for CPU profiling. Check memory usage patterns. Use APM tools like New Relic. Profile database queries."),
            
            createInterviewQuestion("What are the common memory leaks in Node.js and how do you prevent them?", 
                "HARD", "Google", "Node.js", module,
                "Common leaks: Global variables, closures, event listeners, timers, large objects in cache. Prevention: Use weak references, remove event listeners, clear timers, implement cache TTL, use object pools, monitor heap usage."),
            
            createInterviewQuestion("Explain different caching strategies in Node.js applications.", 
                "MEDIUM", "Microsoft", "Node.js", module,
                "In-memory caching (Map, LRU cache). Redis for distributed caching. HTTP caching headers. Database query caching. CDN for static assets. Application-level caching. Cache invalidation strategies. Cache-aside, write-through patterns."),
            
            createInterviewQuestion("How do you implement horizontal scaling for Node.js applications?", 
                "HARD", "Meta", "Node.js", module,
                "Use load balancers (nginx, HAProxy). Implement stateless architecture. Use external session storage (Redis). Database connection pooling. Microservices architecture. Container orchestration (Kubernetes). Auto-scaling based on metrics."),
            
            createInterviewQuestion("What are Worker Threads in Node.js and when would you use them?", 
                "HARD", "Apple", "Node.js", module,
                "Worker Threads allow parallel execution of JavaScript. Use for CPU-intensive tasks to avoid blocking event loop. Share memory using SharedArrayBuffer. Communicate via message passing. Alternative to child processes for JS code execution.")
        ));
        
        // Module System Questions
        questions.addAll(Arrays.asList(
            createInterviewQuestion("What's the difference between CommonJS and ES Modules in Node.js?", 
                "MEDIUM", "Amazon", "Node.js", module,
                "CommonJS: require/module.exports, synchronous loading, dynamic imports. ES Modules: import/export, static analysis, asynchronous loading, tree shaking support. ES modules are the future standard."),
            
            createInterviewQuestion("How does Node.js module resolution work? Explain the algorithm.", 
                "HARD", "Google", "Node.js", module,
                "1) Core modules (fs, path) 2) Relative paths (./file) 3) node_modules traversal upward 4) Global modules 5) File extensions (.js, .json, .node) 6) index.js in directories. Uses require.resolve() algorithm."),
            
            createInterviewQuestion("Explain package.json fields and their purposes.", 
                "MEDIUM", "Microsoft", "Node.js", module,
                "main: Entry point. scripts: NPM scripts. dependencies/devDependencies: Package deps. engines: Node version requirements. exports: Module exports map. bin: CLI commands. files: Published files list."),
            
            createInterviewQuestion("How do you create and publish an npm package?", 
                "MEDIUM", "Meta", "Node.js", module,
                "1) Initialize with npm init 2) Write code and tests 3) Configure package.json 4) Add README and documentation 5) Test locally with npm link 6) Publish with npm publish 7) Use semantic versioning 8) Maintain with updates."),
            
            createInterviewQuestion("What are peer dependencies and when do you use them?", 
                "MEDIUM", "Apple", "Node.js", module,
                "Peer dependencies specify packages that should be installed by the consumer. Used for plugins/extensions that require specific versions of host packages. Prevents version conflicts. Common in React libraries requiring specific React versions.")
        ));
        
        // Advanced Node.js Questions
        questions.addAll(Arrays.asList(
            createInterviewQuestion("How do you implement real-time communication in Node.js?", 
                "HARD", "Amazon", "Node.js", module,
                "WebSockets with ws library or Socket.io. Server-Sent Events (SSE). Long polling. WebRTC for peer-to-peer. Consider scaling with Redis adapter for Socket.io. Handle connection management and error recovery."),
            
            createInterviewQuestion("Explain how to implement microservices architecture with Node.js.", 
                "HARD", "Google", "Node.js", module,
                "Separate services by domain. Use API gateways. Implement service discovery. Use message queues (RabbitMQ, Kafka). Handle distributed transactions. Implement circuit breakers. Use containers and orchestration. Monitor service health."),
            
            createInterviewQuestion("How do you handle file uploads efficiently in Node.js?", 
                "MEDIUM", "Microsoft", "Node.js", module,
                "Use multer middleware for Express. Stream large files to avoid memory issues. Validate file types and sizes. Store files in cloud storage (S3). Implement progress tracking. Handle multiple file uploads. Use temporary storage for processing."),
            
            createInterviewQuestion("What are the security best practices for Node.js applications?", 
                "HARD", "Meta", "Node.js", module,
                "Use helmet for security headers. Validate/sanitize inputs. Implement rate limiting. Use HTTPS. Secure dependencies (npm audit). Implement proper authentication. Use environment variables for secrets. Regular security updates."),
            
            createInterviewQuestion("How do you implement database transactions in Node.js?", 
                "HARD", "Apple", "Node.js", module,
                "Use database-specific transaction APIs. Implement try/catch with rollback. Use connection pooling. Handle distributed transactions with 2PC. Implement saga pattern for microservices. Use ORM transaction support (Sequelize, TypeORM).")
        ));
        
        // Additional questions for new topics (Topics 6-9)
        questions.addAll(Arrays.asList(
            createInterviewQuestion("How do you handle large file processing in Node.js without running out of memory?", 
                "HARD", "Amazon", "Node.js", module,
                "Use streams for memory-efficient processing: fs.createReadStream() with pipe() operations. Process data in chunks, use Transform streams for data manipulation, and implement backpressure handling."),
            
            createInterviewQuestion("Explain the difference between readable.pipe() and pipeline() in Node.js streams.", 
                "MEDIUM", "Google", "Node.js", module,
                "pipe() is the basic method for connecting streams but doesn't handle errors well. pipeline() provides better error handling, automatic cleanup, and proper stream destruction when errors occur."),
            
            createInterviewQuestion("How do you implement CORS in a Node.js application and why is it important?", 
                "MEDIUM", "Microsoft", "Node.js", module,
                "CORS prevents cross-origin requests by default. Implement using cors middleware or custom headers. Set Access-Control-Allow-Origin, Methods, Headers. Important for API security and browser same-origin policy."),
            
            createInterviewQuestion("What are Server-Sent Events (SSE) and how do they differ from WebSockets?", 
                "MEDIUM", "Meta", "Node.js", module,
                "SSE provides one-way real-time communication from server to client using HTTP. Simpler than WebSockets, automatic reconnection, but only server-to-client. WebSockets are bidirectional but more complex."),
            
            createInterviewQuestion("How do you structure a full-stack Node.js application for scalability?", 
                "HARD", "Apple", "Node.js", module,
                "Use layered architecture: Controllers → Services → Repositories. Separate concerns, implement dependency injection, use environment-based configuration, proper error handling, and modular design patterns."),
            
            createInterviewQuestion("What's the difference between unit tests, integration tests, and end-to-end tests?", 
                "MEDIUM", "Amazon", "Node.js", module,
                "Unit tests: Test individual functions/modules in isolation with mocks. Integration tests: Test component interactions. E2E tests: Test complete user workflows. Each has different scope, speed, and maintenance costs."),
            
            createInterviewQuestion("How do you mock external dependencies in Jest tests?", 
                "MEDIUM", "Google", "Node.js", module,
                "Use jest.mock() for module mocking, jest.fn() for function mocks, jest.spyOn() for spying on methods. Create __mocks__ directory for manual mocks. Use mockResolvedValue/mockRejectedValue for async operations."),
            
            createInterviewQuestion("Explain Test-Driven Development (TDD) and its benefits in Node.js development.", 
                "MEDIUM", "Microsoft", "Node.js", module,
                "TDD: Write failing test (Red) → Write minimal code to pass (Green) → Refactor (Refactor). Benefits: Better design, fewer bugs, documentation through tests, confidence in changes."),
            
            createInterviewQuestion("How do you test API endpoints with Supertest? Provide examples.", 
                "MEDIUM", "Meta", "Node.js", module,
                "Supertest wraps your Express app for HTTP testing. Use request(app).get('/api/endpoint').expect(200). Test status codes, response bodies, headers. Set up test database, mock external services."),
            
            createInterviewQuestion("What are the best practices for organizing test files in a Node.js project?", 
                "MEDIUM", "Apple", "Node.js", module,
                "Mirror source structure in tests/, use descriptive names (*.test.js), separate unit/integration/e2e tests, create test utilities, use setup/teardown files, maintain test data fixtures.")
        ));
        
        questionRepository.saveAll(questions);
        log.info("✅ Created {} Node.js interview questions", questions.size());
    }    

    private void createFileIOStreamsPlanetsTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("File I/O & Streams: Planets Project");
        topic.setDescription("Master Node.js file operations and streams through Kepler space telescope data analysis");
        topic.setContent("""
            <div class="topic-content">
                <h2>🪐 File I/O & Streams: Planets Project</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Master Node.js file system operations and stream processing</li>
                        <li>Analyze real Kepler space telescope data for habitable planets</li>
                        <li>Implement efficient CSV parsing and data transformation</li>
                        <li>Handle large datasets with memory-efficient streaming</li>
                        <li>Build production-ready data processing pipelines</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🌌 Kepler Space Telescope Data Analysis Project</h3>
                    <p>We'll analyze real NASA Kepler mission data to find potentially habitable exoplanets using Node.js streams and file operations.</p>
                    
                    <div class="code-example">
                        <h5>Complete Planets Data Analysis System:</h5>
                        <pre><code class="language-javascript">
const fs = require('fs');
const path = require('path');
const { parse } = require('csv-parse');
const { Transform, pipeline } = require('stream');
const { promisify } = require('util');

// Kepler data analysis for habitable planets
class KeplerDataAnalyzer {
    constructor() {
        this.habitablePlanets = [];
        this.totalPlanets = 0;
        this.dataPath = path.join(__dirname, 'data', 'kepler_data.csv');
    }
    
    // Check if planet is potentially habitable
    isHabitablePlanet(planet) {
        return planet['koi_disposition'] === 'CONFIRMED' &&
               planet['koi_insol'] > 0.36 && planet['koi_insol'] < 1.11 &&
               planet['koi_prad'] < 1.6;
    }
    
    // Stream-based CSV processing for large files
    async analyzePlanetsWithStreams() {
        console.log('🔍 Analyzing Kepler data with streams...');
        
        return new Promise((resolve, reject) => {
            const results = [];
            
            // Create readable stream from CSV file
            const readableStream = fs.createReadStream(this.dataPath);
            
            // CSV parser transform stream
            const csvParser = parse({
                comment: '#',
                skip_empty_lines: true,
                columns: true
            });
            
            // Planet filter transform stream
            const planetFilter = new Transform({
                objectMode: true,
                transform(planet, encoding, callback) {
                    this.push(planet);
                    callback();
                }
            });
            
            // Habitability checker transform stream
            const habitabilityChecker = new Transform({
                objectMode: true,
                transform(planet, encoding, callback) {
                    if (this.isHabitablePlanet(planet)) {
                        results.push({
                            name: planet['kepler_name'],
                            radius: parseFloat(planet['koi_prad']),
                            insolation: parseFloat(planet['koi_insol']),
                            disposition: planet['koi_disposition'],
                            temperature: this.calculateTemperature(planet)
                        });
                    }
                    callback();
                },
                
                isHabitablePlanet(planet) {
                    return planet['koi_disposition'] === 'CONFIRMED' &&
                           planet['koi_insol'] > 0.36 && planet['koi_insol'] < 1.11 &&
                           planet['koi_prad'] < 1.6;
                },
                
                calculateTemperature(planet) {
                    const insolation = parseFloat(planet['koi_insol']);
                    return Math.round(255 * Math.pow(insolation, 0.25));
                }
            });
            
            // Pipeline for stream processing
            pipeline(
                readableStream,
                csvParser,
                planetFilter,
                habitabilityChecker,
                (error) => {
                    if (error) {
                        console.error('Pipeline failed:', error);
                        reject(error);
                    } else {
                        console.log(`✅ Found ${results.length} habitable planets`);
                        resolve(results);
                    }
                }
            );
        });
    }
    
    // Memory-efficient large file processing
    async processLargeDataset() {
        console.log('📊 Processing large dataset with memory optimization...');
        
        const stats = {
            totalProcessed: 0,
            habitableCount: 0,
            memoryUsage: []
        };
        
        return new Promise((resolve, reject) => {
            const readStream = fs.createReadStream(this.dataPath, {
                highWaterMark: 16 * 1024 // 16KB chunks
            });
            
            const parser = parse({
                columns: true,
                skip_empty_lines: true
            });
            
            parser.on('readable', function() {
                let record;
                while (record = parser.read()) {
                    stats.totalProcessed++;
                    
                    if (stats.totalProcessed % 1000 === 0) {
                        const memUsage = process.memoryUsage();
                        stats.memoryUsage.push({
                            processed: stats.totalProcessed,
                            heapUsed: Math.round(memUsage.heapUsed / 1024 / 1024),
                            rss: Math.round(memUsage.rss / 1024 / 1024)
                        });
                        console.log(`Processed ${stats.totalProcessed} records, Memory: ${Math.round(memUsage.heapUsed / 1024 / 1024)}MB`);
                    }
                    
                    if (this.isHabitablePlanet(record)) {
                        stats.habitableCount++;
                    }
                }
            });
            
            parser.on('error', reject);
            parser.on('end', () => {
                console.log(`✅ Processing complete: ${stats.totalProcessed} total, ${stats.habitableCount} habitable`);
                resolve(stats);
            });
            
            readStream.pipe(parser);
        });
    }
    
    // Advanced file operations with error handling
    async saveResults(planets, filename = 'habitable_planets.json') {
        const outputPath = path.join(__dirname, 'results', filename);
        
        try {
            // Ensure results directory exists
            await fs.promises.mkdir(path.dirname(outputPath), { recursive: true });
            
            // Prepare data for saving
            const results = {
                timestamp: new Date().toISOString(),
                totalHabitable: planets.length,
                planets: planets.map(planet => ({
                    ...planet,
                    habitabilityScore: this.calculateHabitabilityScore(planet)
                })).sort((a, b) => b.habitabilityScore - a.habitabilityScore)
            };
            
            // Write results with atomic operation
            const tempPath = outputPath + '.tmp';
            await fs.promises.writeFile(tempPath, JSON.stringify(results, null, 2));
            await fs.promises.rename(tempPath, outputPath);
            
            console.log(`💾 Results saved to ${outputPath}`);
            return outputPath;
            
        } catch (error) {
            console.error('Error saving results:', error);
            throw error;
        }
    }
    
    calculateHabitabilityScore(planet) {
        // Simple habitability scoring algorithm
        const radiusScore = Math.max(0, 100 - Math.abs(planet.radius - 1) * 50);
        const tempScore = Math.max(0, 100 - Math.abs(planet.temperature - 288) / 2);
        const insolationScore = Math.max(0, 100 - Math.abs(planet.insolation - 1) * 30);
        
        return Math.round((radiusScore + tempScore + insolationScore) / 3);
    }
    
    // File watching for real-time data updates
    watchDataFile() {
        console.log('👀 Watching for data file changes...');
        
        const watcher = fs.watch(this.dataPath, (eventType, filename) => {
            if (eventType === 'change') {
                console.log(`📁 Data file changed: ${filename}`);
                this.analyzePlanetsWithStreams()
                    .then(planets => {
                        console.log(`🔄 Reanalysis complete: ${planets.length} habitable planets found`);
                    })
                    .catch(error => {
                        console.error('Reanalysis failed:', error);
                    });
            }
        });
        
        return watcher;
    }
}

// Advanced stream utilities
class StreamUtilities {
    // Custom transform stream for data validation
    static createValidationStream(validator) {
        return new Transform({
            objectMode: true,
            transform(chunk, encoding, callback) {
                try {
                    if (validator(chunk)) {
                        this.push(chunk);
                    }
                    callback();
                } catch (error) {
                    callback(error);
                }
            }
        });
    }
    
    // Batch processing stream
    static createBatchStream(batchSize = 100) {
        let batch = [];
        
        return new Transform({
            objectMode: true,
            transform(chunk, encoding, callback) {
                batch.push(chunk);
                
                if (batch.length >= batchSize) {
                    this.push([...batch]);
                    batch = [];
                }
                callback();
            },
            
            flush(callback) {
                if (batch.length > 0) {
                    this.push(batch);
                }
                callback();
            }
        });
    }
    
    // Progress tracking stream
    static createProgressStream(totalExpected) {
        let processed = 0;
        
        return new Transform({
            objectMode: true,
            transform(chunk, encoding, callback) {
                processed++;
                
                if (processed % 1000 === 0 || processed === totalExpected) {
                    const percentage = ((processed / totalExpected) * 100).toFixed(1);
                    console.log(`Progress: ${processed}/${totalExpected} (${percentage}%)`);
                }
                
                this.push(chunk);
                callback();
            }
        });
    }
    
    // Error handling stream wrapper
    static createErrorHandlingStream(stream, errorHandler) {
        stream.on('error', (error) => {
            console.error('Stream error:', error);
            if (errorHandler) {
                errorHandler(error);
            }
        });
        
        return stream;
    }
}

// File system utilities for the project
class FileSystemUtils {
    // Recursive directory creation with permissions
    static async ensureDirectory(dirPath, mode = 0o755) {
        try {
            await fs.promises.mkdir(dirPath, { recursive: true, mode });
            console.log(`📁 Directory ensured: ${dirPath}`);
        } catch (error) {
            if (error.code !== 'EEXIST') {
                throw error;
            }
        }
    }
    
    // Safe file reading with encoding detection
    static async readFileWithEncoding(filePath) {
        try {
            const buffer = await fs.promises.readFile(filePath);
            
            // Simple encoding detection (UTF-8 vs ASCII)
            const isUTF8 = buffer.every(byte => byte < 128 || byte >= 194);
            const encoding = isUTF8 ? 'utf8' : 'ascii';
            
            return {
                content: buffer.toString(encoding),
                encoding,
                size: buffer.length
            };
        } catch (error) {
            console.error(`Error reading file ${filePath}:`, error);
            throw error;
        }
    }
    
    // File statistics and metadata
    static async getFileInfo(filePath) {
        try {
            const stats = await fs.promises.stat(filePath);
            
            return {
                size: stats.size,
                created: stats.birthtime,
                modified: stats.mtime,
                accessed: stats.atime,
                isFile: stats.isFile(),
                isDirectory: stats.isDirectory(),
                permissions: stats.mode.toString(8),
                sizeFormatted: this.formatFileSize(stats.size)
            };
        } catch (error) {
            console.error(`Error getting file info for ${filePath}:`, error);
            return null;
        }
    }
    
    static formatFileSize(bytes) {
        const units = ['B', 'KB', 'MB', 'GB', 'TB'];
        let size = bytes;
        let unitIndex = 0;
        
        while (size >= 1024 && unitIndex < units.length - 1) {
            size /= 1024;
            unitIndex++;
        }
        
        return `${size.toFixed(1)} ${units[unitIndex]}`;
    }
    
    // Directory traversal with filtering
    static async* walkDirectory(dirPath, filter = () => true) {
        try {
            const entries = await fs.promises.readdir(dirPath, { withFileTypes: true });
            
            for (const entry of entries) {
                const fullPath = path.join(dirPath, entry.name);
                
                if (entry.isDirectory()) {
                    yield* this.walkDirectory(fullPath, filter);
                } else if (entry.isFile() && filter(fullPath, entry)) {
                    yield fullPath;
                }
            }
        } catch (error) {
            console.error(`Error walking directory ${dirPath}:`, error);
        }
    }
}

// Main execution and demonstration
async function demonstratePlanetsProject() {
    console.log('🚀 Starting Kepler Planets Analysis Project');
    
    const analyzer = new KeplerDataAnalyzer();
    
    try {
        // Analyze planets with streams
        const habitablePlanets = await analyzer.analyzePlanetsWithStreams();
        
        // Save results
        const outputPath = await analyzer.saveResults(habitablePlanets);
        
        // Display summary
        console.log('\\n📊 Analysis Summary:');
        console.log(`Found ${habitablePlanets.length} potentially habitable planets`);
        
        if (habitablePlanets.length > 0) {
            console.log('\\n🏆 Top 3 Most Habitable Planets:');
            habitablePlanets
                .sort((a, b) => analyzer.calculateHabitabilityScore(b) - analyzer.calculateHabitabilityScore(a))
                .slice(0, 3)
                .forEach((planet, index) => {
                    console.log(`${index + 1}. ${planet.name}`);
                    console.log(`   Radius: ${planet.radius} Earth radii`);
                    console.log(`   Temperature: ${planet.temperature}K`);
                    console.log(`   Habitability Score: ${analyzer.calculateHabitabilityScore(planet)}/100`);
                });
        }
        
        // Start file watching (optional)
        // const watcher = analyzer.watchDataFile();
        
    } catch (error) {
        console.error('Analysis failed:', error);
    }
}

// Export for use in other modules
module.exports = {
    KeplerDataAnalyzer,
    StreamUtilities,
    FileSystemUtils,
    demonstratePlanetsProject
};

// Run demonstration if this file is executed directly
if (require.main === module) {
    demonstratePlanetsProject();
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="concept-section">
                    <h3>📁 Advanced File Operations</h3>
                    <p>Master Node.js file system operations for production applications.</p>
                    
                    <div class="code-example">
                        <h5>Production File Management System:</h5>
                        <pre><code class="language-javascript">
const fs = require('fs').promises;
const path = require('path');
const crypto = require('crypto');

class ProductionFileManager {
    constructor(baseDir = './data') {
        this.baseDir = baseDir;
        this.lockFiles = new Map();
    }
    
    // Atomic file operations with locking
    async writeFileAtomic(filePath, data, options = {}) {
        const fullPath = path.resolve(this.baseDir, filePath);
        const tempPath = fullPath + '.tmp.' + Date.now();
        const lockPath = fullPath + '.lock';
        
        try {
            // Acquire lock
            await this.acquireLock(lockPath);
            
            // Ensure directory exists
            await fs.mkdir(path.dirname(fullPath), { recursive: true });
            
            // Write to temporary file
            await fs.writeFile(tempPath, data, options);
            
            // Atomic rename
            await fs.rename(tempPath, fullPath);
            
            console.log(`✅ File written atomically: ${filePath}`);
            
        } catch (error) {
            // Cleanup temporary file on error
            try {
                await fs.unlink(tempPath);
            } catch (cleanupError) {
                // Ignore cleanup errors
            }
            throw error;
        } finally {
            // Release lock
            await this.releaseLock(lockPath);
        }
    }
    
    // File locking mechanism
    async acquireLock(lockPath, timeout = 5000) {
        const startTime = Date.now();
        
        while (Date.now() - startTime < timeout) {
            try {
                await fs.writeFile(lockPath, process.pid.toString(), { flag: 'wx' });
                this.lockFiles.set(lockPath, Date.now());
                return;
            } catch (error) {
                if (error.code === 'EEXIST') {
                    // Lock exists, wait and retry
                    await new Promise(resolve => setTimeout(resolve, 10));
                    continue;
                }
                throw error;
            }
        }
        
        throw new Error(`Failed to acquire lock: ${lockPath}`);
    }
    
    async releaseLock(lockPath) {
        try {
            await fs.unlink(lockPath);
            this.lockFiles.delete(lockPath);
        } catch (error) {
            // Ignore errors when releasing locks
        }
    }
    
    // File integrity verification
    async verifyFileIntegrity(filePath, expectedHash) {
        try {
            const data = await fs.readFile(path.resolve(this.baseDir, filePath));
            const actualHash = crypto.createHash('sha256').update(data).digest('hex');
            
            return {
                valid: actualHash === expectedHash,
                expectedHash,
                actualHash,
                fileSize: data.length
            };
        } catch (error) {
            return {
                valid: false,
                error: error.message
            };
        }
    }
    
    // Backup and versioning system
    async createBackup(filePath, maxVersions = 5) {
        const fullPath = path.resolve(this.baseDir, filePath);
        const backupDir = path.join(path.dirname(fullPath), '.backups');
        const fileName = path.basename(fullPath);
        const timestamp = new Date().toISOString().replace(/[:.]/g, '-');
        const backupPath = path.join(backupDir, `${fileName}.${timestamp}.backup`);
        
        try {
            // Ensure backup directory exists
            await fs.mkdir(backupDir, { recursive: true });
            
            // Copy file to backup
            await fs.copyFile(fullPath, backupPath);
            
            // Cleanup old backups
            await this.cleanupOldBackups(backupDir, fileName, maxVersions);
            
            console.log(`💾 Backup created: ${backupPath}`);
            return backupPath;
            
        } catch (error) {
            console.error('Backup creation failed:', error);
            throw error;
        }
    }
    
    async cleanupOldBackups(backupDir, fileName, maxVersions) {
        try {
            const files = await fs.readdir(backupDir);
            const backupFiles = files
                .filter(file => file.startsWith(fileName) && file.endsWith('.backup'))
                .map(file => ({
                    name: file,
                    path: path.join(backupDir, file)
                }));
            
            if (backupFiles.length > maxVersions) {
                // Sort by creation time and remove oldest
                const stats = await Promise.all(
                    backupFiles.map(async file => ({
                        ...file,
                        stats: await fs.stat(file.path)
                    }))
                );
                
                stats.sort((a, b) => a.stats.mtime - b.stats.mtime);
                
                const toDelete = stats.slice(0, stats.length - maxVersions);
                await Promise.all(toDelete.map(file => fs.unlink(file.path)));
                
                console.log(`🗑️ Cleaned up ${toDelete.length} old backups`);
            }
        } catch (error) {
            console.error('Backup cleanup failed:', error);
        }
    }
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="file-io-streams">
                        <textarea placeholder="Add your personal notes about File I/O and Streams..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(180);
        topic.setSortOrder(6);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created File I/O & Streams: Planets Project topic");
    }    

    private void createWebServersHTTPTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Web Servers & HTTP Fundamentals");
        topic.setDescription("Master HTTP protocol, web servers, CORS, and streaming for production web applications");
        topic.setContent("""
            <div class="topic-content">
                <h2>🌐 Web Servers & HTTP Fundamentals</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Understand HTTP protocol fundamentals and request/response cycle</li>
                        <li>Build production-ready web servers with Node.js</li>
                        <li>Master CORS, Same Origin Policy, and security considerations</li>
                        <li>Implement streaming responses and real-time communication</li>
                        <li>Handle advanced HTTP features: caching, compression, and optimization</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🔧 HTTP Protocol Deep Dive</h3>
                    <p>Understanding HTTP is crucial for building robust web applications and APIs.</p>
                    
                    <div class="code-example">
                        <h5>Complete HTTP Server Implementation:</h5>
                        <pre><code class="language-javascript">
const http = require('http');
const https = require('https');
const url = require('url');
const querystring = require('querystring');
const fs = require('fs');
const path = require('path');
const zlib = require('zlib');

class AdvancedHTTPServer {
    constructor(options = {}) {
        this.port = options.port || 3000;
        this.routes = new Map();
        this.middlewares = [];
        this.staticPaths = new Map();
        this.corsOptions = options.cors || {};
        this.compressionEnabled = options.compression !== false;
    }
    
    // HTTP method handlers
    get(path, handler) { this.addRoute('GET', path, handler); }
    post(path, handler) { this.addRoute('POST', path, handler); }
    put(path, handler) { this.addRoute('PUT', path, handler); }
    delete(path, handler) { this.addRoute('DELETE', path, handler); }
    patch(path, handler) { this.addRoute('PATCH', path, handler); }
    options(path, handler) { this.addRoute('OPTIONS', path, handler); }
    
    addRoute(method, path, handler) {
        const key = `${method}:${path}`;
        this.routes.set(key, handler);
    }
    
    // Middleware system
    use(middleware) {
        this.middlewares.push(middleware);
    }
    
    // Static file serving
    static(urlPath, localPath) {
        this.staticPaths.set(urlPath, localPath);
    }
    
    // Main request handler
    async handleRequest(req, res) {
        try {
            // Parse URL and query parameters
            const parsedUrl = url.parse(req.url, true);
            req.pathname = parsedUrl.pathname;
            req.query = parsedUrl.query;
            
            // Parse request body for POST/PUT requests
            if (['POST', 'PUT', 'PATCH'].includes(req.method)) {
                req.body = await this.parseRequestBody(req);
            }
            
            // Apply CORS headers
            this.applyCORS(req, res);
            
            // Handle preflight OPTIONS requests
            if (req.method === 'OPTIONS') {
                res.writeHead(200);
                res.end();
                return;
            }
            
            // Apply middlewares
            for (const middleware of this.middlewares) {
                const result = await middleware(req, res);
                if (result === false) return; // Middleware stopped the request
            }
            
            // Check for static file serving
            const staticResponse = await this.handleStaticFile(req, res);
            if (staticResponse) return;
            
            // Route matching
            const routeKey = `${req.method}:${req.pathname}`;
            const handler = this.routes.get(routeKey) || this.findDynamicRoute(req.method, req.pathname);
            
            if (handler) {
                await handler(req, res);
            } else {
                this.send404(res);
            }
            
        } catch (error) {
            this.handleError(error, req, res);
        }
    }
    
    // Request body parsing
    async parseRequestBody(req) {
        return new Promise((resolve, reject) => {
            let body = '';
            
            req.on('data', chunk => {
                body += chunk.toString();
            });
            
            req.on('end', () => {
                try {
                    const contentType = req.headers['content-type'] || '';
                    
                    if (contentType.includes('application/json')) {
                        resolve(JSON.parse(body));
                    } else if (contentType.includes('application/x-www-form-urlencoded')) {
                        resolve(querystring.parse(body));
                    } else {
                        resolve(body);
                    }
                } catch (error) {
                    reject(error);
                }
            });
            
            req.on('error', reject);
        });
    }
    
    // CORS implementation
    applyCORS(req, res) {
        const origin = req.headers.origin;
        const allowedOrigins = this.corsOptions.origins || ['*'];
        
        if (allowedOrigins.includes('*') || allowedOrigins.includes(origin)) {
            res.setHeader('Access-Control-Allow-Origin', origin || '*');
        }
        
        res.setHeader('Access-Control-Allow-Methods', 
            this.corsOptions.methods || 'GET, POST, PUT, DELETE, OPTIONS');
        
        res.setHeader('Access-Control-Allow-Headers', 
            this.corsOptions.headers || 'Content-Type, Authorization, X-Requested-With');
        
        if (this.corsOptions.credentials) {
            res.setHeader('Access-Control-Allow-Credentials', 'true');
        }
        
        if (this.corsOptions.maxAge) {
            res.setHeader('Access-Control-Max-Age', this.corsOptions.maxAge);
        }
    }
    
    // Static file serving with caching
    async handleStaticFile(req, res) {
        for (const [urlPath, localPath] of this.staticPaths) {
            if (req.pathname.startsWith(urlPath)) {
                const relativePath = req.pathname.slice(urlPath.length);
                const filePath = path.join(localPath, relativePath);
                
                try {
                    const stats = await fs.promises.stat(filePath);
                    
                    if (stats.isFile()) {
                        // Check if-modified-since header
                        const ifModifiedSince = req.headers['if-modified-since'];
                        if (ifModifiedSince && new Date(ifModifiedSince) >= stats.mtime) {
                            res.writeHead(304);
                            res.end();
                            return true;
                        }
                        
                        // Set caching headers
                        res.setHeader('Last-Modified', stats.mtime.toUTCString());
                        res.setHeader('Cache-Control', 'public, max-age=3600');
                        
                        // Determine content type
                        const ext = path.extname(filePath).toLowerCase();
                        const contentType = this.getContentType(ext);
                        res.setHeader('Content-Type', contentType);
                        
                        // Handle compression
                        if (this.compressionEnabled && this.shouldCompress(contentType)) {
                            await this.sendCompressedFile(filePath, req, res);
                        } else {
                            const fileStream = fs.createReadStream(filePath);
                            fileStream.pipe(res);
                        }
                        
                        return true;
                    }
                } catch (error) {
                    // File not found, continue to next handler
                }
            }
        }
        return false;
    }
    
    // Content type detection
    getContentType(ext) {
        const types = {
            '.html': 'text/html',
            '.css': 'text/css',
            '.js': 'application/javascript',
            '.json': 'application/json',
            '.png': 'image/png',
            '.jpg': 'image/jpeg',
            '.jpeg': 'image/jpeg',
            '.gif': 'image/gif',
            '.svg': 'image/svg+xml',
            '.ico': 'image/x-icon',
            '.pdf': 'application/pdf',
            '.txt': 'text/plain'
        };
        return types[ext] || 'application/octet-stream';
    }
    
    // Compression handling
    shouldCompress(contentType) {
        const compressibleTypes = [
            'text/', 'application/javascript', 'application/json', 
            'application/xml', 'image/svg+xml'
        ];
        return compressibleTypes.some(type => contentType.startsWith(type));
    }
    
    async sendCompressedFile(filePath, req, res) {
        const acceptEncoding = req.headers['accept-encoding'] || '';
        
        if (acceptEncoding.includes('gzip')) {
            res.setHeader('Content-Encoding', 'gzip');
            const fileStream = fs.createReadStream(filePath);
            const gzipStream = zlib.createGzip();
            fileStream.pipe(gzipStream).pipe(res);
        } else if (acceptEncoding.includes('deflate')) {
            res.setHeader('Content-Encoding', 'deflate');
            const fileStream = fs.createReadStream(filePath);
            const deflateStream = zlib.createDeflate();
            fileStream.pipe(deflateStream).pipe(res);
        } else {
            const fileStream = fs.createReadStream(filePath);
            fileStream.pipe(res);
        }
    }
    
    // Dynamic route matching (for parameterized routes)
    findDynamicRoute(method, pathname) {
        for (const [routeKey, handler] of this.routes) {
            const [routeMethod, routePath] = routeKey.split(':');
            
            if (routeMethod === method) {
                const routeRegex = this.pathToRegex(routePath);
                const match = pathname.match(routeRegex);
                
                if (match) {
                    // Extract parameters
                    const params = this.extractParams(routePath, pathname);
                    return (req, res) => {
                        req.params = params;
                        return handler(req, res);
                    };
                }
            }
        }
        return null;
    }
    
    pathToRegex(path) {
        // Convert /users/:id to /users/([^/]+)
        const regexPath = path.replace(/:([^/]+)/g, '([^/]+)');
        return new RegExp(`^${regexPath}$`);
    }
    
    extractParams(routePath, actualPath) {
        const routeParts = routePath.split('/');
        const actualParts = actualPath.split('/');
        const params = {};
        
        for (let i = 0; i < routeParts.length; i++) {
            if (routeParts[i].startsWith(':')) {
                const paramName = routeParts[i].slice(1);
                params[paramName] = actualParts[i];
            }
        }
        
        return params;
    }
    
    // Error handling
    handleError(error, req, res) {
        console.error('Server error:', error);
        
        if (!res.headersSent) {
            res.writeHead(500, { 'Content-Type': 'application/json' });
            res.end(JSON.stringify({
                error: 'Internal Server Error',
                message: process.env.NODE_ENV === 'development' ? error.message : 'Something went wrong'
            }));
        }
    }
    
    send404(res) {
        res.writeHead(404, { 'Content-Type': 'application/json' });
        res.end(JSON.stringify({ error: 'Not Found' }));
    }
    
    // Server startup
    listen(callback) {
        const server = http.createServer((req, res) => {
            this.handleRequest(req, res);
        });
        
        server.listen(this.port, () => {
            console.log(`🚀 Server running on port ${this.port}`);
            if (callback) callback();
        });
        
        return server;
    }
}

// Streaming responses for real-time data
class StreamingServer extends AdvancedHTTPServer {
    constructor(options) {
        super(options);
        this.setupStreamingRoutes();
    }
    
    setupStreamingRoutes() {
        // Server-Sent Events endpoint
        this.get('/events', (req, res) => {
            this.handleSSE(req, res);
        });
        
        // Chunked transfer encoding for large responses
        this.get('/large-data', (req, res) => {
            this.handleChunkedResponse(req, res);
        });
        
        // File upload with progress
        this.post('/upload', (req, res) => {
            this.handleFileUpload(req, res);
        });
    }
    
    // Server-Sent Events implementation
    handleSSE(req, res) {
        res.writeHead(200, {
            'Content-Type': 'text/event-stream',
            'Cache-Control': 'no-cache',
            'Connection': 'keep-alive',
            'Access-Control-Allow-Origin': '*'
        });
        
        // Send initial connection message
        res.write('data: Connected to event stream\\n\\n');
        
        // Send periodic updates
        const interval = setInterval(() => {
            const data = {
                timestamp: new Date().toISOString(),
                message: 'Server heartbeat',
                data: Math.random()
            };
            
            res.write(`data: ${JSON.stringify(data)}\\n\\n`);
        }, 1000);
        
        // Cleanup on client disconnect
        req.on('close', () => {
            clearInterval(interval);
            console.log('SSE client disconnected');
        });
        
        req.on('error', (error) => {
            clearInterval(interval);
            console.error('SSE error:', error);
        });
    }
    
    // Chunked response for large data
    handleChunkedResponse(req, res) {
        res.writeHead(200, {
            'Content-Type': 'application/json',
            'Transfer-Encoding': 'chunked'
        });
        
        // Simulate large dataset processing
        let count = 0;
        const maxItems = 1000;
        
        const sendChunk = () => {
            if (count < maxItems) {
                const chunk = {
                    id: count,
                    data: `Item ${count}`,
                    timestamp: Date.now()
                };
                
                res.write(JSON.stringify(chunk) + '\\n');
                count++;
                
                setTimeout(sendChunk, 10); // Send next chunk after 10ms
            } else {
                res.end();
            }
        };
        
        sendChunk();
    }
    
    // File upload with progress tracking
    async handleFileUpload(req, res) {
        const contentLength = parseInt(req.headers['content-length'] || '0');
        let receivedBytes = 0;
        
        const chunks = [];
        
        req.on('data', (chunk) => {
            chunks.push(chunk);
            receivedBytes += chunk.length;
            
            const progress = (receivedBytes / contentLength * 100).toFixed(2);
            console.log(`Upload progress: ${progress}%`);
        });
        
        req.on('end', () => {
            const buffer = Buffer.concat(chunks);
            
            res.writeHead(200, { 'Content-Type': 'application/json' });
            res.end(JSON.stringify({
                message: 'File uploaded successfully',
                size: buffer.length,
                receivedBytes
            }));
        });
        
        req.on('error', (error) => {
            console.error('Upload error:', error);
            res.writeHead(500);
            res.end(JSON.stringify({ error: 'Upload failed' }));
        });
    }
}

// Usage example and demonstration
function demonstrateWebServer() {
    const server = new StreamingServer({
        port: 3000,
        cors: {
            origins: ['http://localhost:3000', 'https://example.com'],
            methods: 'GET, POST, PUT, DELETE',
            credentials: true
        },
        compression: true
    });
    
    // Middleware examples
    server.use(async (req, res) => {
        console.log(`${new Date().toISOString()} - ${req.method} ${req.url}`);
        return true; // Continue to next middleware/handler
    });
    
    server.use(async (req, res) => {
        // Rate limiting middleware
        const clientIP = req.connection.remoteAddress;
        // Implement rate limiting logic here
        return true;
    });
    
    // Route examples
    server.get('/', (req, res) => {
        res.writeHead(200, { 'Content-Type': 'text/html' });
        res.end(`
            <html>
                <body>
                    <h1>Advanced HTTP Server</h1>
                    <p>Server running with advanced features:</p>
                    <ul>
                        <li><a href="/events">Server-Sent Events</a></li>
                        <li><a href="/large-data">Chunked Response</a></li>
                        <li><a href="/api/users">API Endpoint</a></li>
                    </ul>
                </body>
            </html>
        `);
    });
    
    server.get('/api/users/:id', (req, res) => {
        const userId = req.params.id;
        res.writeHead(200, { 'Content-Type': 'application/json' });
        res.end(JSON.stringify({
            id: userId,
            name: `User ${userId}`,
            timestamp: new Date().toISOString()
        }));
    });
    
    // Static file serving
    server.static('/static', './public');
    
    // Start server
    return server.listen(() => {
        console.log('🌐 Advanced HTTP server started');
        console.log('📡 Features: CORS, Compression, Streaming, Static Files');
    });
}

module.exports = {
    AdvancedHTTPServer,
    StreamingServer,
    demonstrateWebServer
};

// Run demonstration
if (require.main === module) {
    demonstrateWebServer();
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="web-servers-http">
                        <textarea placeholder="Add your personal notes about Web Servers and HTTP..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(150);
        topic.setSortOrder(7);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created Web Servers & HTTP Fundamentals topic");
    } 
   
    private void createFullStackNASATopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Full-Stack NASA Project: Mission Control Dashboard");
        topic.setDescription("Build a complete NASA mission control dashboard with Node.js backend, React frontend, and real-time data integration");
        topic.setContent("""
            <div class="topic-content">
                <h2>🚀 Full-Stack NASA Project: Mission Control Dashboard</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Build a complete full-stack application with Node.js and React</li>
                        <li>Implement real-time data streaming and WebSocket communication</li>
                        <li>Design scalable architecture with proper separation of concerns</li>
                        <li>Integrate with external APIs and handle data transformation</li>
                        <li>Deploy and monitor a production-ready application</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🏗️ Project Architecture Overview</h3>
                    <p>We'll build a NASA mission control dashboard that displays real-time spacecraft data, mission schedules, and launch information.</p>
                    
                    <div class="code-example">
                        <h5>Backend Architecture (Node.js + Express):</h5>
                        <pre><code class="language-javascript">
// server.js - Main application entry point
const express = require('express');
const cors = require('cors');
const helmet = require('helmet');
const compression = require('compression');
const rateLimit = require('express-rate-limit');
const { createServer } = require('http');
const { Server } = require('socket.io');
const path = require('path');

// Import custom modules
const { connectDatabase } = require('./config/database');
const { setupRoutes } = require('./routes');
const { setupWebSocket } = require('./websocket');
const { DataCollector } = require('./services/dataCollector');
const { MissionController } = require('./services/missionController');

class NASAMissionControlServer {
    constructor() {
        this.app = express();
        this.server = createServer(this.app);
        this.io = new Server(this.server, {
            cors: {
                origin: process.env.CLIENT_URL || "http://localhost:3000",
                methods: ["GET", "POST"]
            }
        });
        
        this.dataCollector = new DataCollector();
        this.missionController = new MissionController();
        
        this.setupMiddleware();
        this.setupRoutes();
        this.setupWebSocket();
        this.startDataCollection();
    }
    
    setupMiddleware() {
        // Security middleware
        this.app.use(helmet({
            contentSecurityPolicy: {
                directives: {
                    defaultSrc: ["'self'"],
                    styleSrc: ["'self'", "'unsafe-inline'"],
                    scriptSrc: ["'self'"],
                    imgSrc: ["'self'", "data:", "https:"],
                    connectSrc: ["'self'", "wss:", "https://api.nasa.gov"]
                }
            }
        }));
        
        // CORS configuration
        this.app.use(cors({
            origin: process.env.CLIENT_URL || "http://localhost:3000",
            credentials: true
        }));
        
        // Rate limiting
        const limiter = rateLimit({
            windowMs: 15 * 60 * 1000, // 15 minutes
            max: 100 // limit each IP to 100 requests per windowMs
        });
        this.app.use('/api/', limiter);
        
        // Compression and parsing
        this.app.use(compression());
        this.app.use(express.json({ limit: '10mb' }));
        this.app.use(express.urlencoded({ extended: true }));
        
        // Static file serving for production
        if (process.env.NODE_ENV === 'production') {
            this.app.use(express.static(path.join(__dirname, 'build')));
        }
        
        // Request logging
        this.app.use((req, res, next) => {
            console.log(`${new Date().toISOString()} - ${req.method} ${req.path}`);
            next();
        });
    }
    
    setupRoutes() {
        // API Routes
        this.app.use('/api/missions', require('./routes/missions'));
        this.app.use('/api/spacecraft', require('./routes/spacecraft'));
        this.app.use('/api/launches', require('./routes/launches'));
        this.app.use('/api/telemetry', require('./routes/telemetry'));
        this.app.use('/api/astronauts', require('./routes/astronauts'));
        
        // Health check endpoint
        this.app.get('/api/health', (req, res) => {
            res.json({
                status: 'healthy',
                timestamp: new Date().toISOString(),
                uptime: process.uptime(),
                memory: process.memoryUsage()
            });
        });
        
        // Serve React app for production
        if (process.env.NODE_ENV === 'production') {
            this.app.get('*', (req, res) => {
                res.sendFile(path.join(__dirname, 'build', 'index.html'));
            });
        }
        
        // Error handling middleware
        this.app.use((error, req, res, next) => {
            console.error('Server error:', error);
            res.status(500).json({
                error: 'Internal Server Error',
                message: process.env.NODE_ENV === 'development' ? error.message : 'Something went wrong'
            });
        });
    }
    
    setupWebSocket() {
        this.io.on('connection', (socket) => {
            console.log(`Client connected: ${socket.id}`);
            
            // Send initial data
            socket.emit('mission-status', this.missionController.getCurrentStatus());
            
            // Handle client subscriptions
            socket.on('subscribe-telemetry', (spacecraftId) => {
                socket.join(`telemetry-${spacecraftId}`);
                console.log(`Client ${socket.id} subscribed to telemetry for ${spacecraftId}`);
            });
            
            socket.on('unsubscribe-telemetry', (spacecraftId) => {
                socket.leave(`telemetry-${spacecraftId}`);
                console.log(`Client ${socket.id} unsubscribed from telemetry for ${spacecraftId}`);
            });
            
            // Handle mission commands (with authentication)
            socket.on('mission-command', async (data) => {
                try {
                    // Validate command and user permissions
                    const result = await this.missionController.executeCommand(data);
                    socket.emit('command-result', result);
                    
                    // Broadcast status update to all clients
                    this.io.emit('mission-status-update', result.status);
                } catch (error) {
                    socket.emit('command-error', { error: error.message });
                }
            });
            
            socket.on('disconnect', () => {
                console.log(`Client disconnected: ${socket.id}`);
            });
        });
    }
    
    startDataCollection() {
        // Start real-time data collection from NASA APIs
        this.dataCollector.start();
        
        // Broadcast telemetry data every second
        setInterval(() => {
            const telemetryData = this.dataCollector.getLatestTelemetry();
            
            Object.keys(telemetryData).forEach(spacecraftId => {
                this.io.to(`telemetry-${spacecraftId}`).emit('telemetry-update', {
                    spacecraftId,
                    data: telemetryData[spacecraftId],
                    timestamp: new Date().toISOString()
                });
            });
        }, 1000);
        
        // Broadcast mission updates every 30 seconds
        setInterval(() => {
            const missionStatus = this.missionController.getCurrentStatus();
            this.io.emit('mission-status-update', missionStatus);
        }, 30000);
    }
    
    async start() {
        try {
            // Connect to database
            await connectDatabase();
            
            // Start server
            const PORT = process.env.PORT || 8000;
            this.server.listen(PORT, () => {
                console.log(`🚀 NASA Mission Control Server running on port ${PORT}`);
                console.log(`🌐 Environment: ${process.env.NODE_ENV || 'development'}`);
                console.log(`📡 WebSocket server ready for real-time communication`);
            });
            
        } catch (error) {
            console.error('Failed to start server:', error);
            process.exit(1);
        }
    }
}

// Data Collection Service
class DataCollector {
    constructor() {
        this.nasaApiKey = process.env.NASA_API_KEY;
        this.telemetryData = {};
        this.missionData = {};
        this.isCollecting = false;
    }
    
    start() {
        if (this.isCollecting) return;
        
        this.isCollecting = true;
        console.log('📡 Starting NASA data collection...');
        
        // Collect different types of data at different intervals
        this.collectTelemetryData();
        this.collectMissionData();
        this.collectLaunchData();
        
        // Set up periodic data collection
        setInterval(() => this.collectTelemetryData(), 5000);
        setInterval(() => this.collectMissionData(), 60000);
        setInterval(() => this.collectLaunchData(), 300000);
    }
    
    async collectTelemetryData() {
        try {
            // Simulate spacecraft telemetry data
            const spacecraft = ['ISS', 'Perseverance', 'Ingenuity', 'JWST'];
            
            for (const craft of spacecraft) {
                this.telemetryData[craft] = {
                    position: {
                        latitude: (Math.random() - 0.5) * 180,
                        longitude: (Math.random() - 0.5) * 360,
                        altitude: Math.random() * 400 + 200
                    },
                    velocity: {
                        x: (Math.random() - 0.5) * 1000,
                        y: (Math.random() - 0.5) * 1000,
                        z: (Math.random() - 0.5) * 1000
                    },
                    systems: {
                        power: Math.random() * 100,
                        communications: Math.random() * 100,
                        propulsion: Math.random() * 100,
                        lifeSupport: craft === 'ISS' ? Math.random() * 100 : null
                    },
                    temperature: Math.random() * 50 - 25,
                    timestamp: new Date().toISOString()
                };
            }
        } catch (error) {
            console.error('Error collecting telemetry data:', error);
        }
    }
    
    async collectMissionData() {
        try {
            // Fetch real NASA mission data
            const response = await fetch(`https://api.nasa.gov/planetary/apod?api_key=${this.nasaApiKey}`);
            const apodData = await response.json();
            
            this.missionData.apod = apodData;
            
            // Simulate mission status updates
            this.missionData.activeMissions = [
                {
                    id: 'artemis-1',
                    name: 'Artemis I',
                    status: 'in-progress',
                    progress: Math.random() * 100,
                    nextMilestone: 'Lunar orbit insertion',
                    crew: []
                },
                {
                    id: 'mars-2020',
                    name: 'Mars 2020 Perseverance',
                    status: 'operational',
                    progress: 85,
                    nextMilestone: 'Sample collection',
                    location: 'Jezero Crater, Mars'
                }
            ];
            
        } catch (error) {
            console.error('Error collecting mission data:', error);
        }
    }
    
    async collectLaunchData() {
        try {
            // Simulate upcoming launch data
            this.missionData.upcomingLaunches = [
                {
                    id: 'falcon-heavy-1',
                    mission: 'Europa Clipper',
                    vehicle: 'Falcon Heavy',
                    launchDate: new Date(Date.now() + 30 * 24 * 60 * 60 * 1000).toISOString(),
                    launchSite: 'Kennedy Space Center',
                    status: 'scheduled'
                },
                {
                    id: 'sls-artemis-2',
                    mission: 'Artemis II',
                    vehicle: 'Space Launch System',
                    launchDate: new Date(Date.now() + 180 * 24 * 60 * 60 * 1000).toISOString(),
                    launchSite: 'Kennedy Space Center',
                    status: 'in-preparation'
                }
            ];
        } catch (error) {
            console.error('Error collecting launch data:', error);
        }
    }
    
    getLatestTelemetry() {
        return this.telemetryData;
    }
    
    getMissionData() {
        return this.missionData;
    }
}

// Mission Controller Service
class MissionController {
    constructor() {
        this.currentStatus = {
            overallStatus: 'nominal',
            activeMissions: 0,
            alerts: [],
            lastUpdate: new Date().toISOString()
        };
    }
    
    getCurrentStatus() {
        return {
            ...this.currentStatus,
            timestamp: new Date().toISOString()
        };
    }
    
    async executeCommand(commandData) {
        const { command, target, parameters, userId } = commandData;
        
        // Validate command
        if (!this.isValidCommand(command, target)) {
            throw new Error(`Invalid command: ${command} for target: ${target}`);
        }
        
        // Log command execution
        console.log(`Executing command: ${command} on ${target} by user ${userId}`);
        
        // Simulate command execution
        const result = {
            commandId: Date.now().toString(),
            command,
            target,
            status: 'executed',
            result: `Command ${command} executed successfully on ${target}`,
            timestamp: new Date().toISOString()
        };
        
        // Update mission status based on command
        this.updateMissionStatus(command, target, result);
        
        return result;
    }
    
    isValidCommand(command, target) {
        const validCommands = {
            'ISS': ['adjust-orbit', 'system-check', 'communication-test'],
            'Perseverance': ['move-rover', 'drill-sample', 'camera-capture'],
            'JWST': ['point-telescope', 'calibrate-instruments', 'data-download']
        };
        
        return validCommands[target] && validCommands[target].includes(command);
    }
    
    updateMissionStatus(command, target, result) {
        // Update overall mission status based on command results
        this.currentStatus.lastUpdate = new Date().toISOString();
        
        // Add alert if command failed
        if (result.status === 'failed') {
            this.currentStatus.alerts.push({
                id: Date.now().toString(),
                level: 'warning',
                message: `Command ${command} failed on ${target}`,
                timestamp: new Date().toISOString()
            });
        }
    }
}

// Start the server
const server = new NASAMissionControlServer();
server.start();

module.exports = {
    NASAMissionControlServer,
    DataCollector,
    MissionController
};
                        </code></pre>
                    </div>
                </div>
                
                <div class="concept-section">
                    <h3>⚛️ Frontend Architecture (React)</h3>
                    <p>The React frontend provides a real-time mission control interface with live data visualization.</p>
                    
                    <div class="code-example">
                        <h5>Mission Control Dashboard Components:</h5>
                        <pre><code class="language-javascript">
// src/App.js - Main application component
import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { io } from 'socket.io-client';
import './App.css';

// Import components
import Header from './components/Header';
import Sidebar from './components/Sidebar';
import Dashboard from './components/Dashboard';
import TelemetryView from './components/TelemetryView';
import MissionControl from './components/MissionControl';
import LaunchSchedule from './components/LaunchSchedule';
import { WebSocketProvider } from './context/WebSocketContext';
import { MissionDataProvider } from './context/MissionDataContext';

function App() {
    const [socket, setSocket] = useState(null);
    const [isConnected, setIsConnected] = useState(false);
    
    useEffect(() => {
        // Initialize WebSocket connection
        const newSocket = io(process.env.REACT_APP_SERVER_URL || 'http://localhost:8000');
        
        newSocket.on('connect', () => {
            console.log('Connected to mission control server');
            setIsConnected(true);
        });
        
        newSocket.on('disconnect', () => {
            console.log('Disconnected from mission control server');
            setIsConnected(false);
        });
        
        setSocket(newSocket);
        
        return () => {
            newSocket.close();
        };
    }, []);
    
    return (
        <WebSocketProvider socket={socket}>
            <MissionDataProvider>
                <Router>
                    <div className="app">
                        <Header isConnected={isConnected} />
                        <div className="app-body">
                            <Sidebar />
                            <main className="main-content">
                                <Routes>
                                    <Route path="/" element={<Dashboard />} />
                                    <Route path="/telemetry" element={<TelemetryView />} />
                                    <Route path="/missions" element={<MissionControl />} />
                                    <Route path="/launches" element={<LaunchSchedule />} />
                                </Routes>
                            </main>
                        </div>
                    </div>
                </Router>
            </MissionDataProvider>
        </WebSocketProvider>
    );
}

// src/components/Dashboard.js - Main dashboard component
import React, { useState, useEffect, useContext } from 'react';
import { WebSocketContext } from '../context/WebSocketContext';
import TelemetryCard from './TelemetryCard';
import MissionStatusCard from './MissionStatusCard';
import AlertsPanel from './AlertsPanel';
import LiveChart from './LiveChart';

const Dashboard = () => {
    const { socket } = useContext(WebSocketContext);
    const [telemetryData, setTelemetryData] = useState({});
    const [missionStatus, setMissionStatus] = useState(null);
    const [alerts, setAlerts] = useState([]);
    
    useEffect(() => {
        if (!socket) return;
        
        // Subscribe to real-time updates
        socket.on('telemetry-update', (data) => {
            setTelemetryData(prev => ({
                ...prev,
                [data.spacecraftId]: data.data
            }));
        });
        
        socket.on('mission-status-update', (status) => {
            setMissionStatus(status);
        });
        
        socket.on('alert', (alert) => {
            setAlerts(prev => [alert, ...prev.slice(0, 9)]); // Keep last 10 alerts
        });
        
        // Subscribe to all spacecraft telemetry
        ['ISS', 'Perseverance', 'Ingenuity', 'JWST'].forEach(spacecraft => {
            socket.emit('subscribe-telemetry', spacecraft);
        });
        
        return () => {
            socket.off('telemetry-update');
            socket.off('mission-status-update');
            socket.off('alert');
        };
    }, [socket]);
    
    return (
        <div className="dashboard">
            <div className="dashboard-header">
                <h1>NASA Mission Control Dashboard</h1>
                <div className="status-indicator">
                    <span className={`status-dot ${missionStatus?.overallStatus || 'unknown'}`}></span>
                    <span>System Status: {missionStatus?.overallStatus || 'Unknown'}</span>
                </div>
            </div>
            
            <div className="dashboard-grid">
                {/* Mission Status Overview */}
                <div className="grid-item span-2">
                    <MissionStatusCard status={missionStatus} />
                </div>
                
                {/* Alerts Panel */}
                <div className="grid-item">
                    <AlertsPanel alerts={alerts} />
                </div>
                
                {/* Spacecraft Telemetry Cards */}
                {Object.entries(telemetryData).map(([spacecraft, data]) => (
                    <div key={spacecraft} className="grid-item">
                        <TelemetryCard 
                            spacecraft={spacecraft} 
                            data={data}
                            onCommand={(command) => handleCommand(spacecraft, command)}
                        />
                    </div>
                ))}
                
                {/* Live Data Charts */}
                <div className="grid-item span-3">
                    <LiveChart 
                        title="System Performance"
                        data={telemetryData}
                        type="line"
                    />
                </div>
            </div>
        </div>
    );
    
    const handleCommand = (spacecraft, command) => {
        if (socket) {
            socket.emit('mission-command', {
                command: command.type,
                target: spacecraft,
                parameters: command.parameters,
                userId: 'mission-controller-1' // In real app, get from auth
            });
        }
    };
};

// src/components/TelemetryCard.js - Individual spacecraft telemetry
import React, { useState } from 'react';
import { LineChart, Line, XAxis, YAxis, ResponsiveContainer } from 'recharts';

const TelemetryCard = ({ spacecraft, data, onCommand }) => {
    const [commandMode, setCommandMode] = useState(false);
    
    if (!data) {
        return (
            <div className="telemetry-card loading">
                <h3>{spacecraft}</h3>
                <p>Waiting for telemetry data...</p>
            </div>
        );
    }
    
    const getStatusColor = (value) => {
        if (value > 80) return 'good';
        if (value > 50) return 'warning';
        return 'critical';
    };
    
    return (
        <div className="telemetry-card">
            <div className="card-header">
                <h3>{spacecraft}</h3>
                <button 
                    className="command-btn"
                    onClick={() => setCommandMode(!commandMode)}
                >
                    {commandMode ? 'Cancel' : 'Command'}
                </button>
            </div>
            
            {commandMode ? (
                <CommandPanel 
                    spacecraft={spacecraft}
                    onCommand={onCommand}
                    onCancel={() => setCommandMode(false)}
                />
            ) : (
                <div className="telemetry-data">
                    {/* Position Data */}
                    <div className="data-section">
                        <h4>Position</h4>
                        <div className="data-grid">
                            <div className="data-item">
                                <span>Lat:</span>
                                <span>{data.position?.latitude?.toFixed(2)}°</span>
                            </div>
                            <div className="data-item">
                                <span>Lon:</span>
                                <span>{data.position?.longitude?.toFixed(2)}°</span>
                            </div>
                            <div className="data-item">
                                <span>Alt:</span>
                                <span>{data.position?.altitude?.toFixed(0)} km</span>
                            </div>
                        </div>
                    </div>
                    
                    {/* System Status */}
                    <div className="data-section">
                        <h4>Systems</h4>
                        <div className="systems-grid">
                            {Object.entries(data.systems || {}).map(([system, value]) => (
                                value !== null && (
                                    <div key={system} className="system-status">
                                        <span className="system-name">{system}</span>
                                        <div className="status-bar">
                                            <div 
                                                className={`status-fill ${getStatusColor(value)}`}
                                                style={{ width: `${value}%` }}
                                            ></div>
                                        </div>
                                        <span className="status-value">{value.toFixed(0)}%</span>
                                    </div>
                                )
                            ))}
                        </div>
                    </div>
                    
                    {/* Temperature */}
                    <div className="data-section">
                        <h4>Temperature</h4>
                        <div className={`temperature ${data.temperature > 0 ? 'positive' : 'negative'}`}>
                            {data.temperature?.toFixed(1)}°C
                        </div>
                    </div>
                    
                    {/* Last Update */}
                    <div className="last-update">
                        Last update: {new Date(data.timestamp).toLocaleTimeString()}
                    </div>
                </div>
            )}
        </div>
    );
};

// Command Panel Component
const CommandPanel = ({ spacecraft, onCommand, onCancel }) => {
    const [selectedCommand, setSelectedCommand] = useState('');
    const [parameters, setParameters] = useState({});
    
    const availableCommands = {
        'ISS': [
            { id: 'adjust-orbit', name: 'Adjust Orbit', params: ['altitude', 'inclination'] },
            { id: 'system-check', name: 'System Check', params: ['system'] },
            { id: 'communication-test', name: 'Communication Test', params: [] }
        ],
        'Perseverance': [
            { id: 'move-rover', name: 'Move Rover', params: ['direction', 'distance'] },
            { id: 'drill-sample', name: 'Drill Sample', params: ['depth'] },
            { id: 'camera-capture', name: 'Camera Capture', params: ['camera', 'resolution'] }
        ],
        'JWST': [
            { id: 'point-telescope', name: 'Point Telescope', params: ['target', 'coordinates'] },
            { id: 'calibrate-instruments', name: 'Calibrate Instruments', params: ['instrument'] },
            { id: 'data-download', name: 'Data Download', params: ['dataset'] }
        ]
    };
    
    const commands = availableCommands[spacecraft] || [];
    
    const handleExecute = () => {
        if (selectedCommand) {
            onCommand({
                type: selectedCommand,
                parameters
            });
            onCancel();
        }
    };
    
    return (
        <div className="command-panel">
            <h4>Send Command to {spacecraft}</h4>
            
            <div className="command-select">
                <label>Command:</label>
                <select 
                    value={selectedCommand} 
                    onChange={(e) => setSelectedCommand(e.target.value)}
                >
                    <option value="">Select command...</option>
                    {commands.map(cmd => (
                        <option key={cmd.id} value={cmd.id}>{cmd.name}</option>
                    ))}
                </select>
            </div>
            
            {selectedCommand && (
                <div className="command-parameters">
                    {commands.find(cmd => cmd.id === selectedCommand)?.params.map(param => (
                        <div key={param} className="parameter-input">
                            <label>{param}:</label>
                            <input
                                type="text"
                                value={parameters[param] || ''}
                                onChange={(e) => setParameters(prev => ({
                                    ...prev,
                                    [param]: e.target.value
                                }))}
                            />
                        </div>
                    ))}
                </div>
            )}
            
            <div className="command-actions">
                <button className="execute-btn" onClick={handleExecute} disabled={!selectedCommand}>
                    Execute Command
                </button>
                <button className="cancel-btn" onClick={onCancel}>
                    Cancel
                </button>
            </div>
        </div>
    );
};

export default Dashboard;
                        </code></pre>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="nasa-project">
                        <textarea placeholder="Add your personal notes about the NASA Project..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(240);
        topic.setSortOrder(8);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created Full-Stack NASA Project topic");
    } 
   
    private void createTestingAPIsJestTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Testing APIs with Jest & Supertest");
        topic.setDescription("Master API testing with Jest and Supertest: unit tests, integration tests, TDD, and test automation");
        topic.setContent("""
            <div class="topic-content">
                <h2>🧪 Testing APIs with Jest & Supertest</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Master Jest testing framework for Node.js applications</li>
                        <li>Implement comprehensive API testing with Supertest</li>
                        <li>Practice Test-Driven Development (TDD) methodology</li>
                        <li>Create integration tests and end-to-end test suites</li>
                        <li>Set up continuous testing and test automation pipelines</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🔬 Jest Testing Framework Fundamentals</h3>
                    <p>Jest is a comprehensive testing framework that provides everything needed for testing Node.js applications.</p>
                    
                    <div class="code-example">
                        <h5>Complete Jest Testing Setup:</h5>
                        <pre><code class="language-javascript">
// jest.config.js - Jest configuration
module.exports = {
    testEnvironment: 'node',
    collectCoverageFrom: [
        'src/**/*.js',
        '!src/**/*.test.js',
        '!src/config/**',
        '!src/migrations/**'
    ],
    coverageDirectory: 'coverage',
    coverageReporters: ['text', 'lcov', 'html'],
    testMatch: [
        '**/__tests__/**/*.js',
        '**/?(*.)+(spec|test).js'
    ],
    setupFilesAfterEnv: ['<rootDir>/tests/setup.js'],
    testTimeout: 10000,
    verbose: true,
    collectCoverage: true,
    coverageThreshold: {
        global: {
            branches: 80,
            functions: 80,
            lines: 80,
            statements: 80
        }
    }
};

// tests/setup.js - Test environment setup
const { MongoMemoryServer } = require('mongodb-memory-server');
const mongoose = require('mongoose');

let mongoServer;

// Setup before all tests
beforeAll(async () => {
    // Start in-memory MongoDB instance
    mongoServer = await MongoMemoryServer.create();
    const mongoUri = mongoServer.getUri();
    
    await mongoose.connect(mongoUri, {
        useNewUrlParser: true,
        useUnifiedTopology: true
    });
    
    console.log('Test database connected');
});

// Cleanup after all tests
afterAll(async () => {
    await mongoose.disconnect();
    await mongoServer.stop();
    console.log('Test database disconnected');
});

// Clear database between tests
afterEach(async () => {
    const collections = mongoose.connection.collections;
    
    for (const key in collections) {
        const collection = collections[key];
        await collection.deleteMany({});
    }
});

// Global test utilities
global.testUtils = {
    createTestUser: (overrides = {}) => ({
        username: 'testuser',
        email: 'test@example.com',
        password: 'password123',
        ...overrides
    }),
    
    createTestMission: (overrides = {}) => ({
        name: 'Test Mission',
        description: 'A test mission',
        status: 'active',
        launchDate: new Date(),
        ...overrides
    }),
    
    delay: (ms) => new Promise(resolve => setTimeout(resolve, ms))
};

// Unit Testing Examples
// tests/unit/services/userService.test.js
const UserService = require('../../../src/services/UserService');
const User = require('../../../src/models/User');

// Mock external dependencies
jest.mock('../../../src/models/User');
jest.mock('../../../src/utils/emailService');

describe('UserService', () => {
    let userService;
    
    beforeEach(() => {
        userService = new UserService();
        jest.clearAllMocks();
    });
    
    describe('createUser', () => {
        it('should create a new user with valid data', async () => {
            // Arrange
            const userData = testUtils.createTestUser();
            const mockUser = { id: 1, ...userData };
            
            User.create.mockResolvedValue(mockUser);
            
            // Act
            const result = await userService.createUser(userData);
            
            // Assert
            expect(User.create).toHaveBeenCalledWith(userData);
            expect(result).toEqual(mockUser);
        });
        
        it('should throw error for duplicate email', async () => {
            // Arrange
            const userData = testUtils.createTestUser();
            const duplicateError = new Error('Email already exists');
            duplicateError.code = 11000;
            
            User.create.mockRejectedValue(duplicateError);
            
            // Act & Assert
            await expect(userService.createUser(userData))
                .rejects.toThrow('Email already exists');
        });
        
        it('should hash password before saving', async () => {
            // Arrange
            const userData = testUtils.createTestUser({ password: 'plaintext' });
            const mockUser = { id: 1, ...userData, password: 'hashed_password' };
            
            User.create.mockResolvedValue(mockUser);
            
            // Act
            await userService.createUser(userData);
            
            // Assert
            const createCall = User.create.mock.calls[0][0];
            expect(createCall.password).not.toBe('plaintext');
            expect(createCall.password).toMatch(/^\\$2[aby]\\$\\d+\\$/); // bcrypt hash pattern
        });
    });
    
    describe('getUserById', () => {
        it('should return user when found', async () => {
            // Arrange
            const userId = 1;
            const mockUser = { id: userId, username: 'testuser' };
            
            User.findById.mockResolvedValue(mockUser);
            
            // Act
            const result = await userService.getUserById(userId);
            
            // Assert
            expect(User.findById).toHaveBeenCalledWith(userId);
            expect(result).toEqual(mockUser);
        });
        
        it('should return null when user not found', async () => {
            // Arrange
            const userId = 999;
            User.findById.mockResolvedValue(null);
            
            // Act
            const result = await userService.getUserById(userId);
            
            // Assert
            expect(result).toBeNull();
        });
        
        it('should handle database errors', async () => {
            // Arrange
            const userId = 1;
            const dbError = new Error('Database connection failed');
            
            User.findById.mkRejectedValue(dbError);
            
            // Act & Assert
            await expect(userService.getUserById(userId))
                .rejects.toThrow('Database connection failed');
        });
    });
    
    describe('updateUser', () => {
        it('should update user with valid data', async () => {
            // Arrange
            const userId = 1;
            const updateData = { username: 'newusername' };
            const mockUpdatedUser = { id: userId, ...updateData };
            
            User.findByIdAndUpdate.mockResolvedValue(mockUpdatedUser);
            
            // Act
            const result = await userService.updateUser(userId, updateData);
            
            // Assert
            expect(User.findByIdAndUpdate).toHaveBeenCalledWith(
                userId, 
                updateData, 
                { new: true, runValidators: true }
            );
            expect(result).toEqual(mockUpdatedUser);
        });
        
        it('should not allow updating sensitive fields', async () => {
            // Arrange
            const userId = 1;
            const updateData = { 
                username: 'newusername',
                password: 'newpassword',
                role: 'admin' // Should be filtered out
            };
            
            // Act
            await userService.updateUser(userId, updateData);
            
            // Assert
            const updateCall = User.findByIdAndUpdate.mock.calls[0][1];
            expect(updateCall).not.toHaveProperty('role');
            expect(updateCall).toHaveProperty('username');
        });
    });
});

// Integration Testing with Supertest
// tests/integration/api/users.test.js
const request = require('supertest');
const app = require('../../../src/app');
const User = require('../../../src/models/User');

describe('Users API Integration Tests', () => {
    describe('POST /api/users', () => {
        it('should create a new user with valid data', async () => {
            // Arrange
            const userData = testUtils.createTestUser();
            
            // Act
            const response = await request(app)
                .post('/api/users')
                .send(userData)
                .expect(201);
            
            // Assert
            expect(response.body).toHaveProperty('id');
            expect(response.body.username).toBe(userData.username);
            expect(response.body.email).toBe(userData.email);
            expect(response.body).not.toHaveProperty('password'); // Should not return password
            
            // Verify user was saved to database
            const savedUser = await User.findById(response.body.id);
            expect(savedUser).toBeTruthy();
            expect(savedUser.username).toBe(userData.username);
        });
        
        it('should return 400 for invalid email format', async () => {
            // Arrange
            const userData = testUtils.createTestUser({ email: 'invalid-email' });
            
            // Act
            const response = await request(app)
                .post('/api/users')
                .send(userData)
                .expect(400);
            
            // Assert
            expect(response.body).toHaveProperty('error');
            expect(response.body.error).toContain('email');
        });
        
        it('should return 409 for duplicate email', async () => {
            // Arrange
            const userData = testUtils.createTestUser();
            
            // Create first user
            await request(app)
                .post('/api/users')
                .send(userData)
                .expect(201);
            
            // Act - Try to create duplicate
            const response = await request(app)
                .post('/api/users')
                .send(userData)
                .expect(409);
            
            // Assert
            expect(response.body).toHaveProperty('error');
            expect(response.body.error).toContain('already exists');
        });
        
        it('should validate required fields', async () => {
            // Arrange
            const incompleteData = { username: 'testuser' }; // Missing email and password
            
            // Act
            const response = await request(app)
                .post('/api/users')
                .send(incompleteData)
                .expect(400);
            
            // Assert
            expect(response.body).toHaveProperty('errors');
            expect(response.body.errors).toContain('email is required');
            expect(response.body.errors).toContain('password is required');
        });
    });
    
    describe('GET /api/users/:id', () => {
        let testUser;
        
        beforeEach(async () => {
            // Create test user
            const userData = testUtils.createTestUser();
            testUser = await User.create(userData);
        });
        
        it('should return user by id', async () => {
            // Act
            const response = await request(app)
                .get(`/api/users/${testUser.id}`)
                .expect(200);
            
            // Assert
            expect(response.body.id).toBe(testUser.id.toString());
            expect(response.body.username).toBe(testUser.username);
            expect(response.body).not.toHaveProperty('password');
        });
        
        it('should return 404 for non-existent user', async () => {
            // Act
            const response = await request(app)
                .get('/api/users/999999')
                .expect(404);
            
            // Assert
            expect(response.body).toHaveProperty('error');
            expect(response.body.error).toContain('not found');
        });
        
        it('should return 400 for invalid id format', async () => {
            // Act
            const response = await request(app)
                .get('/api/users/invalid-id')
                .expect(400);
            
            // Assert
            expect(response.body).toHaveProperty('error');
            expect(response.body.error).toContain('Invalid ID');
        });
    });
    
    describe('PUT /api/users/:id', () => {
        let testUser;
        
        beforeEach(async () => {
            const userData = testUtils.createTestUser();
            testUser = await User.create(userData);
        });
        
        it('should update user with valid data', async () => {
            // Arrange
            const updateData = { username: 'updateduser' };
            
            // Act
            const response = await request(app)
                .put(`/api/users/${testUser.id}`)
                .send(updateData)
                .expect(200);
            
            // Assert
            expect(response.body.username).toBe(updateData.username);
            
            // Verify in database
            const updatedUser = await User.findById(testUser.id);
            expect(updatedUser.username).toBe(updateData.username);
        });
        
        it('should not allow updating to duplicate email', async () => {
            // Arrange
            const otherUser = await User.create(testUtils.createTestUser({
                email: 'other@example.com',
                username: 'otheruser'
            }));
            
            // Act
            const response = await request(app)
                .put(`/api/users/${testUser.id}`)
                .send({ email: otherUser.email })
                .expect(409);
            
            // Assert
            expect(response.body).toHaveProperty('error');
        });
    });
    
    describe('DELETE /api/users/:id', () => {
        let testUser;
        
        beforeEach(async () => {
            const userData = testUtils.createTestUser();
            testUser = await User.create(userData);
        });
        
        it('should delete user by id', async () => {
            // Act
            await request(app)
                .delete(`/api/users/${testUser.id}`)
                .expect(204);
            
            // Assert - User should be deleted from database
            const deletedUser = await User.findById(testUser.id);
            expect(deletedUser).toBeNull();
        });
        
        it('should return 404 when deleting non-existent user', async () => {
            // Act
            const response = await request(app)
                .delete('/api/users/999999')
                .expect(404);
            
            // Assert
            expect(response.body).toHaveProperty('error');
        });
    });
});

// Test-Driven Development (TDD) Example
// tests/tdd/missionService.test.js
const MissionService = require('../../../src/services/MissionService');

describe('MissionService - TDD Example', () => {
    let missionService;
    
    beforeEach(() => {
        missionService = new MissionService();
    });
    
    // RED: Write failing test first
    describe('calculateMissionDuration', () => {
        it('should calculate duration between launch and landing dates', () => {
            // Arrange
            const launchDate = new Date('2024-01-01');
            const landingDate = new Date('2024-01-15');
            
            // Act & Assert - This will fail initially (RED)
            expect(() => {
                missionService.calculateMissionDuration(launchDate, landingDate);
            }).not.toThrow();
        });
        
        it('should return duration in days', () => {
            // Arrange
            const launchDate = new Date('2024-01-01');
            const landingDate = new Date('2024-01-15');
            const expectedDuration = 14; // days
            
            // Act
            const duration = missionService.calculateMissionDuration(launchDate, landingDate);
            
            // Assert
            expect(duration).toBe(expectedDuration);
        });
        
        it('should handle same-day missions', () => {
            // Arrange
            const sameDate = new Date('2024-01-01');
            
            // Act
            const duration = missionService.calculateMissionDuration(sameDate, sameDate);
            
            // Assert
            expect(duration).toBe(0);
        });
        
        it('should throw error for invalid date order', () => {
            // Arrange
            const launchDate = new Date('2024-01-15');
            const landingDate = new Date('2024-01-01'); // Earlier than launch
            
            // Act & Assert
            expect(() => {
                missionService.calculateMissionDuration(launchDate, landingDate);
            }).toThrow('Landing date cannot be before launch date');
        });
    });
    
    // GREEN: Implement minimal code to pass tests
    // REFACTOR: Improve code while keeping tests green
});

// Advanced Testing Patterns
// tests/patterns/testPatterns.test.js
describe('Advanced Testing Patterns', () => {
    
    // Test Doubles: Mocks, Stubs, Spies
    describe('Test Doubles', () => {
        it('should use mock functions', () => {
            const mockCallback = jest.fn();
            mockCallback.mockReturnValue(42);
            
            const result = mockCallback();
            
            expect(mockCallback).toHaveBeenCalled();
            expect(result).toBe(42);
        });
        
        it('should use spies to monitor function calls', () => {
            const mathObject = {
                add: (a, b) => a + b
            };
            
            const addSpy = jest.spyOn(mathObject, 'add');
            
            const result = mathObject.add(2, 3);
            
            expect(addSpy).toHaveBeenCalledWith(2, 3);
            expect(result).toBe(5);
            
            addSpy.mockRestore();
        });
    });
    
    // Parameterized Tests
    describe('Parameterized Tests', () => {
        const testCases = [
            { input: [1, 2], expected: 3 },
            { input: [0, 0], expected: 0 },
            { input: [-1, 1], expected: 0 },
            { input: [100, 200], expected: 300 }
        ];
        
        testCases.forEach(({ input, expected }) => {
            it(`should add ${input[0]} + ${input[1]} = ${expected}`, () => {
                const result = input[0] + input[1];
                expect(result).toBe(expected);
            });
        });
    });
    
    // Async Testing
    describe('Async Testing', () => {
        it('should handle promises', async () => {
            const asyncFunction = () => Promise.resolve('success');
            
            const result = await asyncFunction();
            
            expect(result).toBe('success');
        });
        
        it('should handle promise rejections', async () => {
            const asyncFunction = () => Promise.reject(new Error('failure'));
            
            await expect(asyncFunction()).rejects.toThrow('failure');
        });
        
        it('should test with done callback', (done) => {
            setTimeout(() => {
                expect(true).toBe(true);
                done();
            }, 100);
        });
    });
    
    // Snapshot Testing
    describe('Snapshot Testing', () => {
        it('should match component snapshot', () => {
            const component = {
                type: 'div',
                props: { className: 'test' },
                children: ['Hello World']
            };
            
            expect(component).toMatchSnapshot();
        });
    });
});

// Performance Testing
// tests/performance/performance.test.js
describe('Performance Tests', () => {
    it('should complete operation within time limit', async () => {
        const startTime = Date.now();
        
        // Simulate operation
        await testUtils.delay(100);
        
        const endTime = Date.now();
        const duration = endTime - startTime;
        
        expect(duration).toBeLessThan(200); // Should complete within 200ms
    });
    
    it('should handle concurrent requests', async () => {
        const concurrentRequests = 10;
        const promises = [];
        
        for (let i = 0; i < concurrentRequests; i++) {
            promises.push(
                request(app)
                    .get('/api/health')
                    .expect(200)
            );
        }
        
        const results = await Promise.all(promises);
        
        expect(results).toHaveLength(concurrentRequests);
        results.forEach(result => {
            expect(result.status).toBe(200);
        });
    });
});

module.exports = {
    // Export test utilities for reuse
    testUtils: global.testUtils
};
                        </code></pre>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="testing-apis-jest">
                        <textarea placeholder="Add your personal notes about Testing APIs with Jest..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(180);
        topic.setSortOrder(9);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created Testing APIs with Jest & Supertest topic");
    } 
   
    private void createDatabaseIntegrationTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Database Integration: MongoDB & Mongoose");
        topic.setDescription("Master MongoDB and Mongoose ODM: schema design, queries, relationships, and performance optimization");
        topic.setContent("""
            <div class="topic-content">
                <h2>🗄️ Database Integration: MongoDB & Mongoose</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Master MongoDB database operations and query optimization</li>
                        <li>Implement Mongoose ODM with advanced schema patterns</li>
                        <li>Design scalable database architecture and relationships</li>
                        <li>Handle data validation, middleware, and population strategies</li>
                        <li>Optimize performance with indexing and aggregation pipelines</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🏗️ MongoDB & Mongoose Setup</h3>
                    <p>MongoDB is a NoSQL document database that pairs perfectly with Node.js for flexible, scalable applications.</p>
                    
                    <div class="code-example">
                        <h5>Complete MongoDB Integration:</h5>
                        <pre><code class="language-javascript">
const mongoose = require('mongoose');
const bcrypt = require('bcrypt');
const validator = require('validator');

// Database connection with advanced configuration
class DatabaseManager {
    constructor() {
        this.connection = null;
        this.isConnected = false;
    }
    
    async connect(uri, options = {}) {
        try {
            const defaultOptions = {
                useNewUrlParser: true,
                useUnifiedTopology: true,
                maxPoolSize: 10,
                serverSelectionTimeoutMS: 5000,
                socketTimeoutMS: 45000,
                bufferMaxEntries: 0,
                bufferCommands: false
            };
            
            this.connection = await mongoose.connect(uri, { ...defaultOptions, ...options });
            this.isConnected = true;
            
            console.log('✅ MongoDB connected successfully');
            
            // Connection event handlers
            mongoose.connection.on('error', (error) => {
                console.error('MongoDB connection error:', error);
                this.isConnected = false;
            });
            
            mongoose.connection.on('disconnected', () => {
                console.warn('MongoDB disconnected');
                this.isConnected = false;
            });
            
            mongoose.connection.on('reconnected', () => {
                console.log('MongoDB reconnected');
                this.isConnected = true;
            });
            
            return this.connection;
            
        } catch (error) {
            console.error('MongoDB connection failed:', error);
            throw error;
        }
    }
    
    async disconnect() {
        if (this.connection) {
            await mongoose.disconnect();
            this.isConnected = false;
            console.log('MongoDB disconnected');
        }
    }
    
    getConnectionStatus() {
        return {
            isConnected: this.isConnected,
            readyState: mongoose.connection.readyState,
            host: mongoose.connection.host,
            port: mongoose.connection.port,
            name: mongoose.connection.name
        };
    }
}

// Advanced Schema Design
const userSchema = new mongoose.Schema({
    username: {
        type: String,
        required: [true, 'Username is required'],
        unique: true,
        trim: true,
        minlength: [3, 'Username must be at least 3 characters'],
        maxlength: [30, 'Username cannot exceed 30 characters'],
        match: [/^[a-zA-Z0-9_]+$/, 'Username can only contain letters, numbers, and underscores']
    },
    
    email: {
        type: String,
        required: [true, 'Email is required'],
        unique: true,
        lowercase: true,
        validate: {
            validator: validator.isEmail,
            message: 'Please provide a valid email address'
        }
    },
    
    password: {
        type: String,
        required: [true, 'Password is required'],
        minlength: [8, 'Password must be at least 8 characters'],
        select: false // Don't include in queries by default
    },
    
    profile: {
        firstName: { type: String, trim: true },
        lastName: { type: String, trim: true },
        avatar: { type: String },
        bio: { type: String, maxlength: 500 },
        location: { type: String },
        website: { 
            type: String,
            validate: {
                validator: function(v) {
                    return !v || validator.isURL(v);
                },
                message: 'Please provide a valid URL'
            }
        }
    },
    
    preferences: {
        theme: { type: String, enum: ['light', 'dark'], default: 'light' },
        notifications: {
            email: { type: Boolean, default: true },
            push: { type: Boolean, default: false },
            sms: { type: Boolean, default: false }
        },
        privacy: {
            profileVisible: { type: Boolean, default: true },
            emailVisible: { type: Boolean, default: false }
        }
    },
    
    role: {
        type: String,
        enum: ['user', 'admin', 'moderator'],
        default: 'user'
    },
    
    status: {
        type: String,
        enum: ['active', 'inactive', 'suspended', 'pending'],
        default: 'pending'
    },
    
    loginAttempts: { type: Number, default: 0 },
    lockUntil: Date,
    
    // Timestamps
    lastLogin: Date,
    emailVerifiedAt: Date,
    
}, {
    timestamps: true, // Adds createdAt and updatedAt
    toJSON: { 
        virtuals: true,
        transform: function(doc, ret) {
            delete ret.password;
            delete ret.__v;
            return ret;
        }
    },
    toObject: { virtuals: true }
});

// Virtual properties
userSchema.virtual('fullName').get(function() {
    return `${this.profile.firstName} ${this.profile.lastName}`.trim();
});

userSchema.virtual('isLocked').get(function() {
    return !!(this.lockUntil && this.lockUntil > Date.now());
});

// Indexes for performance
userSchema.index({ email: 1 });
userSchema.index({ username: 1 });
userSchema.index({ 'profile.firstName': 1, 'profile.lastName': 1 });
userSchema.index({ createdAt: -1 });
userSchema.index({ lastLogin: -1 });

// Pre-save middleware
userSchema.pre('save', async function(next) {
    // Hash password if modified
    if (this.isModified('password')) {
        try {
            const saltRounds = 12;
            this.password = await bcrypt.hash(this.password, saltRounds);
        } catch (error) {
            return next(error);
        }
    }
    
    // Update email verification status
    if (this.isModified('email')) {
        this.emailVerifiedAt = null;
    }
    
    next();
});

// Instance methods
userSchema.methods.comparePassword = async function(candidatePassword) {
    if (!this.password) return false;
    return bcrypt.compare(candidatePassword, this.password);
};

userSchema.methods.incrementLoginAttempts = function() {
    // If we have a previous lock that has expired, restart at 1
    if (this.lockUntil && this.lockUntil < Date.now()) {
        return this.updateOne({
            $unset: { lockUntil: 1 },
            $set: { loginAttempts: 1 }
        });
    }
    
    const updates = { $inc: { loginAttempts: 1 } };
    
    // If we're at max attempts and not locked, lock account
    if (this.loginAttempts + 1 >= 5 && !this.isLocked) {
        updates.$set = { lockUntil: Date.now() + 2 * 60 * 60 * 1000 }; // 2 hours
    }
    
    return this.updateOne(updates);
};

userSchema.methods.resetLoginAttempts = function() {
    return this.updateOne({
        $unset: { loginAttempts: 1, lockUntil: 1 }
    });
};

// Static methods
userSchema.statics.findByEmail = function(email) {
    return this.findOne({ email: email.toLowerCase() });
};

userSchema.statics.findActiveUsers = function() {
    return this.find({ status: 'active' }).sort({ lastLogin: -1 });
};

// Create User model
const User = mongoose.model('User', userSchema);

// Mission Schema for NASA project
const missionSchema = new mongoose.Schema({
    name: {
        type: String,
        required: true,
        unique: true,
        trim: true
    },
    
    description: {
        type: String,
        required: true,
        maxlength: 1000
    },
    
    type: {
        type: String,
        enum: ['robotic', 'crewed', 'cargo', 'science'],
        required: true
    },
    
    status: {
        type: String,
        enum: ['planning', 'development', 'testing', 'launched', 'active', 'completed', 'failed'],
        default: 'planning'
    },
    
    spacecraft: [{
        name: String,
        type: String,
        status: String,
        telemetry: {
            position: {
                latitude: Number,
                longitude: Number,
                altitude: Number
            },
            velocity: {
                x: Number,
                y: Number,
                z: Number
            },
            systems: {
                power: Number,
                communications: Number,
                propulsion: Number,
                lifeSupport: Number
            }
        },
        lastUpdate: { type: Date, default: Date.now }
    }],
    
    timeline: {
        plannedLaunch: Date,
        actualLaunch: Date,
        plannedEnd: Date,
        actualEnd: Date
    },
    
    budget: {
        allocated: Number,
        spent: Number,
        currency: { type: String, default: 'USD' }
    },
    
    team: [{
        userId: { type: mongoose.Schema.Types.ObjectId, ref: 'User' },
        role: String,
        joinedAt: { type: Date, default: Date.now }
    }],
    
    objectives: [String],
    achievements: [String],
    
    location: {
        target: String, // Mars, Moon, ISS, etc.
        coordinates: {
            latitude: Number,
            longitude: Number
        }
    }
    
}, {
    timestamps: true,
    toJSON: { virtuals: true }
});

// Mission virtual properties
missionSchema.virtual('duration').get(function() {
    if (this.timeline.actualLaunch && this.timeline.actualEnd) {
        return this.timeline.actualEnd - this.timeline.actualLaunch;
    }
    return null;
});

missionSchema.virtual('budgetUtilization').get(function() {
    if (this.budget.allocated > 0) {
        return (this.budget.spent / this.budget.allocated * 100).toFixed(2);
    }
    return 0;
});

// Mission indexes
missionSchema.index({ status: 1 });
missionSchema.index({ type: 1 });
missionSchema.index({ 'timeline.plannedLaunch': 1 });
missionSchema.index({ 'team.userId': 1 });

// Mission methods
missionSchema.methods.addTeamMember = function(userId, role) {
    this.team.push({ userId, role });
    return this.save();
};

missionSchema.methods.updateSpacecraftTelemetry = function(spacecraftName, telemetryData) {
    const spacecraft = this.spacecraft.find(sc => sc.name === spacecraftName);
    if (spacecraft) {
        spacecraft.telemetry = { ...spacecraft.telemetry, ...telemetryData };
        spacecraft.lastUpdate = new Date();
        return this.save();
    }
    throw new Error(`Spacecraft ${spacecraftName} not found`);
};

const Mission = mongoose.model('Mission', missionSchema);

// Repository Pattern Implementation
class UserRepository {
    async create(userData) {
        try {
            const user = new User(userData);
            return await user.save();
        } catch (error) {
            if (error.code === 11000) {
                throw new Error('Email or username already exists');
            }
            throw error;
        }
    }
    
    async findById(id) {
        return User.findById(id).select('+password');
    }
    
    async findByEmail(email) {
        return User.findByEmail(email).select('+password');
    }
    
    async findWithPagination(page = 1, limit = 10, filter = {}) {
        const skip = (page - 1) * limit;
        
        const [users, total] = await Promise.all([
            User.find(filter)
                .skip(skip)
                .limit(limit)
                .sort({ createdAt: -1 }),
            User.countDocuments(filter)
        ]);
        
        return {
            users,
            pagination: {
                page,
                limit,
                total,
                pages: Math.ceil(total / limit)
            }
        };
    }
    
    async update(id, updateData) {
        return User.findByIdAndUpdate(
            id, 
            updateData, 
            { new: true, runValidators: true }
        );
    }
    
    async delete(id) {
        return User.findByIdAndDelete(id);
    }
    
    // Advanced queries
    async findUsersWithRecentActivity(days = 30) {
        const cutoffDate = new Date();
        cutoffDate.setDate(cutoffDate.getDate() - days);
        
        return User.find({
            lastLogin: { $gte: cutoffDate },
            status: 'active'
        }).sort({ lastLogin: -1 });
    }
    
    async getUserStatistics() {
        return User.aggregate([
            {
                $group: {
                    _id: '$status',
                    count: { $sum: 1 },
                    avgLoginAttempts: { $avg: '$loginAttempts' }
                }
            },
            {
                $sort: { count: -1 }
            }
        ]);
    }
}

// Service Layer with Business Logic
class UserService {
    constructor() {
        this.userRepository = new UserRepository();
    }
    
    async createUser(userData) {
        // Validate business rules
        await this.validateUserData(userData);
        
        // Create user
        const user = await this.userRepository.create(userData);
        
        // Send welcome email (async, don't wait)
        this.sendWelcomeEmail(user.email).catch(error => {
            console.error('Failed to send welcome email:', error);
        });
        
        return user;
    }
    
    async authenticateUser(email, password) {
        const user = await this.userRepository.findByEmail(email);
        
        if (!user) {
            throw new Error('Invalid credentials');
        }
        
        if (user.isLocked) {
            throw new Error('Account is temporarily locked');
        }
        
        const isValidPassword = await user.comparePassword(password);
        
        if (!isValidPassword) {
            await user.incrementLoginAttempts();
            throw new Error('Invalid credentials');
        }
        
        // Reset login attempts on successful login
        if (user.loginAttempts > 0) {
            await user.resetLoginAttempts();
        }
        
        // Update last login
        user.lastLogin = new Date();
        await user.save();
        
        return user;
    }
    
    async validateUserData(userData) {
        // Custom business validation
        if (userData.username && userData.username.includes('admin')) {
            throw new Error('Username cannot contain "admin"');
        }
        
        // Check for disposable email domains
        const disposableDomains = ['tempmail.com', '10minutemail.com'];
        const emailDomain = userData.email.split('@')[1];
        
        if (disposableDomains.includes(emailDomain)) {
            throw new Error('Disposable email addresses are not allowed');
        }
    }
    
    async sendWelcomeEmail(email) {
        // Email service integration
        console.log(`Sending welcome email to ${email}`);
        // Implementation would integrate with email service
    }
}

// Advanced Mongoose Features
class AdvancedMongoosePatterns {
    
    // Aggregation Pipeline Examples
    static async getUserEngagementStats() {
        return User.aggregate([
            // Match active users
            { $match: { status: 'active' } },
            
            // Add computed fields
            {
                $addFields: {
                    daysSinceLastLogin: {
                        $divide: [
                            { $subtract: [new Date(), '$lastLogin'] },
                            1000 * 60 * 60 * 24
                        ]
                    }
                }
            },
            
            // Group by engagement level
            {
                $group: {
                    _id: {
                        $switch: {
                            branches: [
                                { case: { $lte: ['$daysSinceLastLogin', 1] }, then: 'daily' },
                                { case: { $lte: ['$daysSinceLastLogin', 7] }, then: 'weekly' },
                                { case: { $lte: ['$daysSinceLastLogin', 30] }, then: 'monthly' }
                            ],
                            default: 'inactive'
                        }
                    },
                    count: { $sum: 1 },
                    avgLoginAttempts: { $avg: '$loginAttempts' },
                    users: { $push: { id: '$_id', username: '$username' } }
                }
            },
            
            // Sort by count
            { $sort: { count: -1 } }
        ]);
    }
    
    // Population with advanced options
    static async getMissionsWithTeamDetails() {
        return Mission.find({ status: 'active' })
            .populate({
                path: 'team.userId',
                select: 'username email profile.firstName profile.lastName',
                match: { status: 'active' }
            })
            .populate({
                path: 'createdBy',
                select: 'username'
            })
            .lean(); // Return plain JavaScript objects for better performance
    }
    
    // Transaction handling
    static async transferMissionOwnership(fromUserId, toUserId, missionId) {
        const session = await mongoose.startSession();
        
        try {
            await session.withTransaction(async () => {
                // Remove from old owner
                await User.findByIdAndUpdate(
                    fromUserId,
                    { $pull: { ownedMissions: missionId } },
                    { session }
                );
                
                // Add to new owner
                await User.findByIdAndUpdate(
                    toUserId,
                    { $push: { ownedMissions: missionId } },
                    { session }
                );
                
                // Update mission
                await Mission.findByIdAndUpdate(
                    missionId,
                    { 
                        owner: toUserId,
                        transferredAt: new Date()
                    },
                    { session }
                );
            });
            
            console.log('Mission ownership transferred successfully');
            
        } catch (error) {
            console.error('Mission transfer failed:', error);
            throw error;
        } finally {
            await session.endSession();
        }
    }
    
    // Change streams for real-time updates
    static setupChangeStreams() {
        const userChangeStream = User.watch();
        
        userChangeStream.on('change', (change) => {
            console.log('User collection changed:', change.operationType);
            
            switch (change.operationType) {
                case 'insert':
                    console.log('New user created:', change.fullDocument.username);
                    break;
                case 'update':
                    console.log('User updated:', change.documentKey._id);
                    break;
                case 'delete':
                    console.log('User deleted:', change.documentKey._id);
                    break;
            }
        });
        
        return userChangeStream;
    }
}

// Usage Examples and Best Practices
async function demonstrateMongoDBIntegration() {
    const dbManager = new DatabaseManager();
    const userService = new UserService();
    
    try {
        // Connect to database
        await dbManager.connect(process.env.MONGODB_URI || 'mongodb://localhost:27017/nasa_mission_control');
        
        // Create test user
        const userData = {
            username: 'astronaut1',
            email: 'astronaut@nasa.gov',
            password: 'securepassword123',
            profile: {
                firstName: 'Neil',
                lastName: 'Armstrong',
                bio: 'First person to walk on the Moon'
            },
            role: 'admin'
        };
        
        const user = await userService.createUser(userData);
        console.log('✅ User created:', user.username);
        
        // Authenticate user
        const authenticatedUser = await userService.authenticateUser(userData.email, userData.password);
        console.log('✅ User authenticated:', authenticatedUser.username);
        
        // Get user statistics
        const stats = await AdvancedMongoosePatterns.getUserEngagementStats();
        console.log('📊 User engagement stats:', stats);
        
        // Setup change streams
        const changeStream = AdvancedMongoosePatterns.setupChangeStreams();
        
        // Cleanup
        setTimeout(() => {
            changeStream.close();
        }, 10000);
        
    } catch (error) {
        console.error('Database integration demo failed:', error);
    }
}

module.exports = {
    DatabaseManager,
    User,
    Mission,
    UserRepository,
    UserService,
    AdvancedMongoosePatterns,
    demonstrateMongoDBIntegration
};

// Run demonstration
if (require.main === module) {
    demonstrateMongoDBIntegration();
}
                        </code></pre>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="database-integration">
                        <textarea placeholder="Add your personal notes about Database Integration..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(200);
        topic.setSortOrder(10);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created Database Integration: MongoDB & Mongoose topic");
    }
    
    // ==================== TOPIC 11: REST API Integration - SpaceX Project ====================
    
    private void createRESTAPIIntegrationTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("REST API Integration: SpaceX Project");
        topic.setDescription("Master external API integration with SpaceX API, data mapping, error handling, and caching strategies");
        topic.setContent("""
            <div class="topic-content">
                <h2>🚀 REST API Integration: SpaceX Project</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Master external REST API integration and consumption</li>
                        <li>Implement robust error handling and retry strategies</li>
                        <li>Design efficient caching and rate limiting mechanisms</li>
                        <li>Build production-ready API client with TypeScript</li>
                        <li>Handle real-time data synchronization and webhooks</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🏗️ Complete SpaceX API Integration</h3>
                    <p>Build a production-grade SpaceX launch tracking system with advanced API integration patterns.</p>
                    
                    <div class="code-example">
                        <h5>Advanced API Client with Caching & Retry Logic:</h5>
                        <pre><code class="language-javascript">
const axios = require('axios');
const NodeCache = require('node-cache');
const { RateLimiter } = require('limiter');

class SpaceXAPIClient {
    constructor(options = {}) {
        this.baseURL = 'https://api.spacexdata.com/v4';
        this.cache = new NodeCache({ stdTTL: 600, checkperiod: 120 });
        this.rateLimiter = new RateLimiter({ tokensPerInterval: 10, interval: 'second' });
        
        this.client = axios.create({
            baseURL: this.baseURL,
            timeout: options.timeout || 10000,
            headers: {
                'Content-Type': 'application/json',
                'User-Agent': 'SpaceX-Tracker/1.0'
            }
        });
        
        this.setupInterceptors();
    }
    
    setupInterceptors() {
        // Request interceptor for rate limiting
        this.client.interceptors.request.use(async (config) => {
            await this.rateLimiter.removeTokens(1);
            console.log(`📡 API Request: ${config.method.toUpperCase()} ${config.url}`);
            return config;
        });
        
        // Response interceptor for error handling
        this.client.interceptors.response.use(
            response => response,
            async error => {
                if (error.response) {
                    console.error(`❌ API Error: ${error.response.status} - ${error.response.statusText}`);
                    
                    // Retry logic for 5xx errors
                    if (error.response.status >= 500 && error.config.retryCount < 3) {
                        error.config.retryCount = (error.config.retryCount || 0) + 1;
                        const delay = Math.pow(2, error.config.retryCount) * 1000;
                        
                        console.log(`🔄 Retrying in ${delay}ms (attempt ${error.config.retryCount}/3)`);
                        await new Promise(resolve => setTimeout(resolve, delay));
                        
                        return this.client(error.config);
                    }
                }
                throw error;
            }
        );
    }
    
    async getAllLaunches(options = {}) {
        const cacheKey = `launches_${JSON.stringify(options)}`;
        const cached = this.cache.get(cacheKey);
        
        if (cached) {
            console.log('✅ Returning cached launches');
            return cached;
        }
        
        try {
            const response = await this.client.post('/launches/query', {
                options: {
                    limit: options.limit || 100,
                    sort: { date_utc: options.sort || 'desc' },
                    populate: ['rocket', 'launchpad', 'crew']
                },
                query: options.query || {}
            });
            
            this.cache.set(cacheKey, response.data);
            return response.data;
            
        } catch (error) {
            console.error('Failed to fetch launches:', error.message);
            throw new Error(`SpaceX API Error: ${error.message}`);
        }
    }
    
    async getUpcomingLaunches() {
        return this.getAllLaunches({
            query: { upcoming: true },
            sort: 'asc',
            limit: 10
        });
    }
    
    async getLatestLaunch() {
        const cacheKey = 'latest_launch';
        const cached = this.cache.get(cacheKey);
        
        if (cached) return cached;
        
        const response = await this.client.get('/launches/latest');
        this.cache.set(cacheKey, response.data, 300); // 5 min cache
        return response.data;
    }
    
    async getRocketDetails(rocketId) {
        const cacheKey = `rocket_${rocketId}`;
        const cached = this.cache.get(cacheKey);
        
        if (cached) return cached;
        
        const response = await this.client.get(`/rockets/${rocketId}`);
        this.cache.set(cacheKey, response.data, 3600); // 1 hour cache
        return response.data;
    }
    
    async searchLaunches(searchTerm) {
        return this.getAllLaunches({
            query: {
                $or: [
                    { name: { $regex: searchTerm, $options: 'i' } },
                    { details: { $regex: searchTerm, $options: 'i' } }
                ]
            }
        });
    }
    
    clearCache() {
        this.cache.flushAll();
        console.log('🗑️  Cache cleared');
    }
}

// Express API Server with SpaceX Integration
const express = require('express');
const app = express();
const spaceXClient = new SpaceXAPIClient();

app.use(express.json());

// Get all launches with pagination
app.get('/api/launches', async (req, res) => {
    try {
        const { limit = 20, page = 1, upcoming } = req.query;
        
        const options = {
            limit: parseInt(limit),
            query: upcoming === 'true' ? { upcoming: true } : {}
        };
        
        const launches = await spaceXClient.getAllLaunches(options);
        
        res.json({
            success: true,
            count: launches.docs.length,
            data: launches.docs,
            pagination: {
                page: launches.page,
                totalPages: launches.totalPages,
                hasNextPage: launches.hasNextPage
            }
        });
        
    } catch (error) {
        res.status(500).json({
            success: false,
            error: error.message
        });
    }
});

// Get latest launch
app.get('/api/launches/latest', async (req, res) => {
    try {
        const launch = await spaceXClient.getLatestLaunch();
        res.json({ success: true, data: launch });
    } catch (error) {
        res.status(500).json({ success: false, error: error.message });
    }
});

// Search launches
app.get('/api/launches/search', async (req, res) => {
    try {
        const { q } = req.query;
        if (!q) {
            return res.status(400).json({ success: false, error: 'Search query required' });
        }
        
        const results = await spaceXClient.searchLaunches(q);
        res.json({ success: true, count: results.docs.length, data: results.docs });
        
    } catch (error) {
        res.status(500).json({ success: false, error: error.message });
    }
});

// Get rocket details
app.get('/api/rockets/:id', async (req, res) => {
    try {
        const rocket = await spaceXClient.getRocketDetails(req.params.id);
        res.json({ success: true, data: rocket });
    } catch (error) {
        res.status(404).json({ success: false, error: 'Rocket not found' });
    }
});

// Clear cache endpoint
app.post('/api/cache/clear', (req, res) => {
    spaceXClient.clearCache();
    res.json({ success: true, message: 'Cache cleared successfully' });
});

// Health check
app.get('/health', (req, res) => {
    res.json({
        status: 'healthy',
        timestamp: new Date().toISOString(),
        cacheStats: spaceXClient.cache.getStats()
    });
});

module.exports = { SpaceXAPIClient, app };
                        </code></pre>
                    </div>
                </div>
                
                <div class="interview-questions">
                    <h3>💼 Interview Questions</h3>
                    
                    <div class="question">
                        <h4>Q1: How do you handle rate limiting when consuming external APIs? (Amazon, Google)</h4>
                        <div class="answer">
                            <p><strong>Answer:</strong> Implement multiple strategies:</p>
                            <ul>
                                <li><strong>Token Bucket Algorithm:</strong> Use libraries like 'limiter' to control request rate</li>
                                <li><strong>Exponential Backoff:</strong> Retry failed requests with increasing delays</li>
                                <li><strong>Queue System:</strong> Queue requests and process them at controlled rate</li>
                                <li><strong>Response Headers:</strong> Monitor X-RateLimit headers and adjust accordingly</li>
                                <li><strong>Circuit Breaker:</strong> Stop requests temporarily if API is consistently failing</li>
                            </ul>
                            <pre><code class="language-javascript">
const { RateLimiter } = require('limiter');
const limiter = new RateLimiter({ tokensPerInterval: 10, interval: 'second' });

async function makeAPICall() {
    await limiter.removeTokens(1); // Wait for token availability
    return axios.get('/api/endpoint');
}
                            </code></pre>
                        </div>
                    </div>
                    
                    <div class="question">
                        <h4>Q2: What caching strategies would you implement for external API data? (Meta, Microsoft)</h4>
                        <div class="answer">
                            <p><strong>Answer:</strong> Multi-layered caching approach:</p>
                            <ul>
                                <li><strong>In-Memory Cache:</strong> NodeCache for frequently accessed data (TTL: 5-10 min)</li>
                                <li><strong>Redis Cache:</strong> Distributed cache for shared data across instances</li>
                                <li><strong>HTTP Cache Headers:</strong> Leverage ETag and Cache-Control headers</li>
                                <li><strong>Stale-While-Revalidate:</strong> Serve cached data while fetching fresh data</li>
                                <li><strong>Cache Invalidation:</strong> Webhook-based or time-based invalidation</li>
                            </ul>
                        </div>
                    </div>
                    
                    <div class="question">
                        <h4>Q3: How do you implement retry logic with exponential backoff? (Amazon, Apple)</h4>
                        <div class="answer">
                            <p><strong>Answer:</strong> Implement intelligent retry mechanism:</p>
                            <pre><code class="language-javascript">
async function retryWithBackoff(fn, maxRetries = 3, baseDelay = 1000) {
    for (let attempt = 0; attempt <= maxRetries; attempt++) {
        try {
            return await fn();
        } catch (error) {
            if (attempt === maxRetries) throw error;
            
            // Only retry on 5xx errors or network issues
            if (error.response && error.response.status < 500) throw error;
            
            const delay = baseDelay * Math.pow(2, attempt);
            const jitter = Math.random() * 1000; // Add randomness
            
            console.log(`Retry attempt ${attempt + 1}/${maxRetries} after ${delay + jitter}ms`);
            await new Promise(resolve => setTimeout(resolve, delay + jitter));
        }
    }
}
                            </code></pre>
                        </div>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="rest-api-integration">
                        <textarea placeholder="Add your notes about REST API integration..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(180);
        topic.setSortOrder(11);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created REST API Integration: SpaceX Project topic");
    }

    
    // ==================== TOPIC 12: Authentication & Security ====================
    
    private void createAuthenticationSecurityTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Authentication & Security: JWT, OAuth2, Auth0");
        topic.setDescription("Master authentication strategies: JWT tokens, OAuth2 flows, Auth0 integration, and security best practices");
        topic.setContent("""
            <div class="topic-content">
                <h2>🔐 Authentication & Security</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Implement JWT-based authentication with refresh tokens</li>
                        <li>Master OAuth2 flows and third-party authentication</li>
                        <li>Integrate Auth0 for enterprise-grade security</li>
                        <li>Implement role-based access control (RBAC)</li>
                        <li>Secure APIs against common vulnerabilities</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🏗️ Complete Authentication System</h3>
                    
                    <div class="code-example">
                        <h5>Production-Ready JWT Authentication:</h5>
                        <pre><code class="language-javascript">
const jwt = require('jsonwebtoken');
const bcrypt = require('bcrypt');
const crypto = require('crypto');

class AuthenticationService {
    constructor() {
        this.accessTokenSecret = process.env.ACCESS_TOKEN_SECRET;
        this.refreshTokenSecret = process.env.REFRESH_TOKEN_SECRET;
        this.accessTokenExpiry = '15m';
        this.refreshTokenExpiry = '7d';
        this.refreshTokens = new Map(); // In production, use Redis
    }
    
    async hashPassword(password) {
        const saltRounds = 12;
        return bcrypt.hash(password, saltRounds);
    }
    
    async verifyPassword(password, hash) {
        return bcrypt.compare(password, hash);
    }
    
    generateAccessToken(user) {
        const payload = {
            userId: user.id,
            email: user.email,
            role: user.role,
            permissions: user.permissions
        };
        
        return jwt.sign(payload, this.accessTokenSecret, {
            expiresIn: this.accessTokenExpiry,
            issuer: 'spacex-tracker',
            audience: 'spacex-api'
        });
    }
    
    generateRefreshToken(user) {
        const payload = {
            userId: user.id,
            tokenId: crypto.randomBytes(32).toString('hex')
        };
        
        const token = jwt.sign(payload, this.refreshTokenSecret, {
            expiresIn: this.refreshTokenExpiry
        });
        
        // Store refresh token (use Redis in production)
        this.refreshTokens.set(payload.tokenId, {
            userId: user.id,
            createdAt: Date.now()
        });
        
        return token;
    }
    
    async login(email, password, userRepository) {
        const user = await userRepository.findByEmail(email);
        
        if (!user) {
            throw new Error('Invalid credentials');
        }
        
        const isValidPassword = await this.verifyPassword(password, user.passwordHash);
        
        if (!isValidPassword) {
            throw new Error('Invalid credentials');
        }
        
        const accessToken = this.generateAccessToken(user);
        const refreshToken = this.generateRefreshToken(user);
        
        return {
            accessToken,
            refreshToken,
            user: {
                id: user.id,
                email: user.email,
                role: user.role
            }
        };
    }
    
    async refreshAccessToken(refreshToken) {
        try {
            const decoded = jwt.verify(refreshToken, this.refreshTokenSecret);
            
            // Check if refresh token is still valid
            if (!this.refreshTokens.has(decoded.tokenId)) {
                throw new Error('Refresh token revoked');
            }
            
            // Generate new access token
            const user = await userRepository.findById(decoded.userId);
            return this.generateAccessToken(user);
            
        } catch (error) {
            throw new Error('Invalid refresh token');
        }
    }
    
    revokeRefreshToken(tokenId) {
        this.refreshTokens.delete(tokenId);
    }
}

// Express Middleware
function authenticateToken(req, res, next) {
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1];
    
    if (!token) {
        return res.status(401).json({ error: 'Access token required' });
    }
    
    try {
        const decoded = jwt.verify(token, process.env.ACCESS_TOKEN_SECRET);
        req.user = decoded;
        next();
    } catch (error) {
        return res.status(403).json({ error: 'Invalid or expired token' });
    }
}

function authorizeRole(...allowedRoles) {
    return (req, res, next) => {
        if (!req.user) {
            return res.status(401).json({ error: 'Authentication required' });
        }
        
        if (!allowedRoles.includes(req.user.role)) {
            return res.status(403).json({ error: 'Insufficient permissions' });
        }
        
        next();
    };
}

// OAuth2 Integration
const passport = require('passport');
const GoogleStrategy = require('passport-google-oauth20').Strategy;

passport.use(new GoogleStrategy({
    clientID: process.env.GOOGLE_CLIENT_ID,
    clientSecret: process.env.GOOGLE_CLIENT_SECRET,
    callbackURL: '/auth/google/callback'
}, async (accessToken, refreshToken, profile, done) => {
    try {
        let user = await userRepository.findByGoogleId(profile.id);
        
        if (!user) {
            user = await userRepository.create({
                googleId: profile.id,
                email: profile.emails[0].value,
                name: profile.displayName,
                avatar: profile.photos[0].value
            });
        }
        
        return done(null, user);
    } catch (error) {
        return done(error, null);
    }
}));

module.exports = { AuthenticationService, authenticateToken, authorizeRole };
                        </code></pre>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="authentication-security">
                        <textarea placeholder="Add your notes about authentication and security..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(200);
        topic.setSortOrder(12);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created Authentication & Security topic");
    }

    
    // ==================== TOPIC 13: Deployment & CI/CD ====================
    
    private void createDeploymentCICDTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Deployment & CI/CD Pipelines");
        topic.setDescription("Master production deployment: Docker, CI/CD pipelines, AWS/Heroku deployment, and zero-downtime strategies");
        topic.setContent("""
            <div class="topic-content">
                <h2>🚀 Deployment & CI/CD Pipelines</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Containerize Node.js applications with Docker</li>
                        <li>Build automated CI/CD pipelines with GitHub Actions</li>
                        <li>Deploy to AWS, Heroku, and cloud platforms</li>
                        <li>Implement zero-downtime deployment strategies</li>
                        <li>Monitor and manage production applications</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🏗️ Complete Deployment Pipeline</h3>
                    
                    <div class="code-example">
                        <h5>Production Dockerfile:</h5>
                        <pre><code class="language-dockerfile">
# Multi-stage build for optimized image
FROM node:18-alpine AS builder

WORKDIR /app

# Copy package files
COPY package*.json ./

# Install dependencies
RUN npm ci --only=production

# Copy source code
COPY . .

# Build application (if using TypeScript)
RUN npm run build

# Production stage
FROM node:18-alpine

WORKDIR /app

# Create non-root user
RUN addgroup -g 1001 -S nodejs && adduser -S nodejs -u 1001

# Copy from builder
COPY --from=builder --chown=nodejs:nodejs /app/node_modules ./node_modules
COPY --from=builder --chown=nodejs:nodejs /app/dist ./dist
COPY --from=builder --chown=nodejs:nodejs /app/package*.json ./

# Switch to non-root user
USER nodejs

# Expose port
EXPOSE 3000

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \\$
    CMD node healthcheck.js

# Start application
CMD ["node", "dist/server.js"]
                        </code></pre>
                        
                        <h5>GitHub Actions CI/CD Pipeline:</h5>
                        <pre><code class="language-yaml">
name: CI/CD Pipeline

on:
  push:
    branches: [main, develop]
  pull_request:
    branches: [main]

jobs:
  test:
    runs-on: ubuntu-latest
    
    steps:
      - uses: actions/checkout@v3
      
      - name: Setup Node.js
        uses: actions/setup-node@v3
        with:
          node-version: '18'
          cache: 'npm'
      
      - name: Install dependencies
        run: npm ci
      
      - name: Run linter
        run: npm run lint
      
      - name: Run tests
        run: npm test
      
      - name: Upload coverage
        uses: codecov/codecov-action@v3
  
  build:
    needs: test
    runs-on: ubuntu-latest
    
    steps:
      - uses: actions/checkout@v3
      
      - name: Build Docker image
        run: docker build -t spacex-tracker:latest .
      
      - name: Run security scan
        run: docker scan spacex-tracker:latest
  
  deploy:
    needs: build
    runs-on: ubuntu-latest
    if: github.ref == 'refs/heads/main'
    
    steps:
      - uses: actions/checkout@v3
      
      - name: Deploy to AWS
        env:
          AWS_ACCESS_KEY_ID: \\${{ secrets.AWS_ACCESS_KEY_ID }}
          AWS_SECRET_ACCESS_KEY: \\${{ secrets.AWS_SECRET_ACCESS_KEY }}
        run: |
          aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin \\${{ secrets.ECR_REGISTRY }}
          docker build -t \\${{ secrets.ECR_REGISTRY }}/spacex-tracker:latest .
          docker push \\${{ secrets.ECR_REGISTRY }}/spacex-tracker:latest
          aws ecs update-service --cluster production --service spacex-tracker --force-new-deployment
                        </code></pre>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="deployment-cicd">
                        <textarea placeholder="Add your notes about deployment and CI/CD..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(180);
        topic.setSortOrder(13);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created Deployment & CI/CD topic");
    }
    
    // ==================== TOPIC 14: GraphQL Implementation ====================
    
    private void createGraphQLImplementationTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("GraphQL vs REST: Implementation & Best Practices");
        topic.setDescription("Master GraphQL: schema design, resolvers, Apollo Server, and performance optimization");
        topic.setContent("""
            <div class="topic-content">
                <h2>🔷 GraphQL Implementation</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Design efficient GraphQL schemas and type systems</li>
                        <li>Implement resolvers with DataLoader for N+1 prevention</li>
                        <li>Build Apollo Server with authentication and caching</li>
                        <li>Compare GraphQL vs REST trade-offs</li>
                        <li>Optimize query performance and implement subscriptions</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🏗️ Complete GraphQL Server</h3>
                    
                    <div class="code-example">
                        <h5>Apollo Server with TypeScript:</h5>
                        <pre><code class="language-javascript">
const { ApolloServer, gql } = require('apollo-server-express');
const DataLoader = require('dataloader');

// GraphQL Schema
const typeDefs = gql\\`
    type Launch {
        id: ID!
        name: String!
        date: String!
        rocket: Rocket!
        success: Boolean
        details: String
        crew: [Astronaut!]!
    }
    
    type Rocket {
        id: ID!
        name: String!
        type: String!
        active: Boolean!
        stages: Int!
    }
    
    type Astronaut {
        id: ID!
        name: String!
        agency: String!
        launches: [Launch!]!
    }
    
    type Query {
        launches(limit: Int, offset: Int): [Launch!]!
        launch(id: ID!): Launch
        upcomingLaunches: [Launch!]!
        rocket(id: ID!): Rocket
    }
    
    type Mutation {
        bookLaunch(launchId: ID!): BookingResponse!
        cancelBooking(launchId: ID!): BookingResponse!
    }
    
    type Subscription {
        launchUpdated: Launch!
    }
    
    type BookingResponse {
        success: Boolean!
        message: String
        launch: Launch
    }
\\`;

// Resolvers with DataLoader
const resolvers = {
    Query: {
        launches: async (_, { limit = 20, offset = 0 }, { dataSources }) => {
            return dataSources.launchAPI.getAllLaunches({ limit, offset });
        },
        
        launch: async (_, { id }, { dataSources }) => {
            return dataSources.launchAPI.getLaunchById(id);
        },
        
        upcomingLaunches: async (_, __, { dataSources }) => {
            return dataSources.launchAPI.getUpcomingLaunches();
        }
    },
    
    Launch: {
        rocket: async (launch, _, { loaders }) => {
            return loaders.rocketLoader.load(launch.rocketId);
        },
        
        crew: async (launch, _, { loaders }) => {
            return loaders.crewLoader.loadMany(launch.crewIds);
        }
    },
    
    Mutation: {
        bookLaunch: async (_, { launchId }, { dataSources, user }) => {
            if (!user) throw new Error('Authentication required');
            
            const result = await dataSources.launchAPI.bookLaunch(launchId, user.id);
            return {
                success: result.success,
                message: result.message,
                launch: await dataSources.launchAPI.getLaunchById(launchId)
            };
        }
    }
};

// DataLoader for N+1 prevention
function createLoaders(dataSources) {
    return {
        rocketLoader: new DataLoader(async (rocketIds) => {
            const rockets = await dataSources.rocketAPI.getRocketsByIds(rocketIds);
            return rocketIds.map(id => rockets.find(r => r.id === id));
        }),
        
        crewLoader: new DataLoader(async (crewIds) => {
            const crew = await dataSources.crewAPI.getCrewByIds(crewIds);
            return crewIds.map(id => crew.find(c => c.id === id));
        })
    };
}

// Apollo Server Setup
const server = new ApolloServer({
    typeDefs,
    resolvers,
    context: ({ req }) => ({
        user: req.user,
        loaders: createLoaders(dataSources)
    }),
    cache: 'bounded',
    persistedQueries: false
});

module.exports = { server, typeDefs, resolvers };
                        </code></pre>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="graphql-implementation">
                        <textarea placeholder="Add your notes about GraphQL..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(200);
        topic.setSortOrder(14);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created GraphQL Implementation topic");
    }
    
    // ==================== TOPIC 15: WebSockets & Real-Time ====================
    
    private void createWebSocketsRealTimeTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("WebSockets & Real-Time Applications");
        topic.setDescription("Master real-time communication: WebSockets, Socket.io, pub/sub patterns, and scalable architectures");
        topic.setContent("""
            <div class="topic-content">
                <h2>⚡ WebSockets & Real-Time Applications</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Implement WebSocket servers with Socket.io</li>
                        <li>Build real-time chat and collaboration features</li>
                        <li>Design scalable pub/sub architectures with Redis</li>
                        <li>Handle connection management and reconnection logic</li>
                        <li>Implement real-time data synchronization</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🏗️ Production WebSocket Server</h3>
                    
                    <div class="code-example">
                        <h5>Socket.io Real-Time Server:</h5>
                        <pre><code class="language-javascript">
const express = require('express');
const http = require('http');
const socketIO = require('socket.io');
const Redis = require('ioredis');

class RealTimeServer {
    constructor() {
        this.app = express();
        this.server = http.createServer(this.app);
        this.io = socketIO(this.server, {
            cors: { origin: '*' },
            transports: ['websocket', 'polling']
        });
        
        this.redis = new Redis();
        this.setupSocketHandlers();
    }
    
    setupSocketHandlers() {
        this.io.on('connection', (socket) => {
            console.log(\\`✅ Client connected: \\\\${socket.id}\\`);
            
            // Authentication
            socket.on('authenticate', async (token) => {
                try {
                    const user = await this.verifyToken(token);
                    socket.user = user;
                    socket.join(\\`user:\\\\${user.id}\\`);
                    socket.emit('authenticated', { user });
                } catch (error) {
                    socket.emit('auth_error', { message: 'Invalid token' });
                    socket.disconnect();
                }
            });
            
            // Join launch tracking room
            socket.on('track_launch', (launchId) => {
                socket.join(\\`launch:\\\\${launchId}\\`);
                console.log(\\`User \\\\${socket.user.id} tracking launch \\\\${launchId}\\`);
            });
            
            // Real-time chat
            socket.on('send_message', async (data) => {
                const message = {
                    id: Date.now(),
                    userId: socket.user.id,
                    username: socket.user.name,
                    text: data.text,
                    timestamp: new Date()
                };
                
                // Broadcast to room
                this.io.to(\\`launch:\\\\${data.launchId}\\`).emit('new_message', message);
                
                // Store in Redis
                await this.redis.lpush(\\`messages:\\\\${data.launchId}\\`, JSON.stringify(message));
            });
            
            // Disconnect handling
            socket.on('disconnect', () => {
                console.log(\\`❌ Client disconnected: \\\\${socket.id}\\`);
            });
        });
    }
    
    // Broadcast launch updates
    async broadcastLaunchUpdate(launchId, update) {
        this.io.to(\\`launch:\\\\${launchId}\\`).emit('launch_update', update);
    }
    
    start(port = 3000) {
        this.server.listen(port, () => {
            console.log(\\`🚀 Real-time server running on port \\\\${port}\\`);
        });
    }
}

module.exports = RealTimeServer;
                        </code></pre>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="websockets-realtime">
                        <textarea placeholder="Add your notes about WebSockets..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(180);
        topic.setSortOrder(15);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created WebSockets & Real-Time topic");
    }

    
    // ==================== TOPIC 16: Microservices Architecture ====================
    
    private void createMicroservicesArchitectureTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Microservices Architecture & Service Communication");
        topic.setDescription("Master microservices: service design, inter-service communication, API gateways, and distributed patterns");
        topic.setContent("""
            <div class="topic-content">
                <h2>🏗️ Microservices Architecture</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Design microservices with domain-driven design</li>
                        <li>Implement service-to-service communication patterns</li>
                        <li>Build API gateways and service mesh</li>
                        <li>Handle distributed transactions and saga patterns</li>
                        <li>Implement service discovery and load balancing</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🏗️ Microservices Implementation</h3>
                    
                    <div class="code-example">
                        <h5>Service Architecture with Message Queue:</h5>
                        <pre><code class="language-javascript">
const express = require('express');
const amqp = require('amqplib');

class LaunchService {
    constructor() {
        this.app = express();
        this.setupRoutes();
        this.connectMessageQueue();
    }
    
    async connectMessageQueue() {
        this.connection = await amqp.connect('amqp://localhost');
        this.channel = await this.connection.createChannel();
        await this.channel.assertQueue('launch_events');
        
        // Listen for events from other services
        this.channel.consume('launch_events', async (msg) => {
            const event = JSON.parse(msg.content.toString());
            await this.handleEvent(event);
            this.channel.ack(msg);
        });
    }
    
    async publishEvent(eventType, data) {
        const event = { type: eventType, data, timestamp: Date.now() };
        this.channel.sendToQueue('launch_events', Buffer.from(JSON.stringify(event)));
    }
    
    setupRoutes() {
        this.app.post('/launches', async (req, res) => {
            const launch = await this.createLaunch(req.body);
            await this.publishEvent('LAUNCH_CREATED', launch);
            res.json(launch);
        });
    }
}

module.exports = LaunchService;
                        </code></pre>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="microservices">
                        <textarea placeholder="Add your notes about microservices..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(240);
        topic.setSortOrder(16);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created Microservices Architecture topic");
    }
    
    // ==================== TOPIC 17: Serverless & Cloud ====================
    
    private void createServerlessCloudTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Serverless Architecture & Cloud Functions");
        topic.setDescription("Master serverless: AWS Lambda, API Gateway, DynamoDB, and event-driven architectures");
        topic.setContent("""
            <div class="topic-content">
                <h2>☁️ Serverless Architecture</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Build serverless applications with AWS Lambda</li>
                        <li>Design event-driven architectures</li>
                        <li>Implement API Gateway and DynamoDB integration</li>
                        <li>Optimize cold start performance</li>
                        <li>Handle serverless deployment and monitoring</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🏗️ AWS Lambda Functions</h3>
                    
                    <div class="code-example">
                        <h5>Production Lambda Handler:</h5>
                        <pre><code class="language-javascript">
const AWS = require('aws-sdk');
const dynamodb = new AWS.DynamoDB.DocumentClient();

exports.handler = async (event) => {
    try {
        const { httpMethod, path, body } = event;
        
        if (httpMethod === 'GET' && path === '/launches') {
            const result = await dynamodb.scan({
                TableName: 'Launches'
            }).promise();
            
            return {
                statusCode: 200,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(result.Items)
            };
        }
        
        if (httpMethod === 'POST' && path === '/launches') {
            const launch = JSON.parse(body);
            
            await dynamodb.put({
                TableName: 'Launches',
                Item: { ...launch, id: Date.now().toString() }
            }).promise();
            
            return {
                statusCode: 201,
                body: JSON.stringify({ success: true })
            };
        }
        
        return { statusCode: 404, body: 'Not Found' };
        
    } catch (error) {
        console.error(error);
        return {
            statusCode: 500,
            body: JSON.stringify({ error: error.message })
        };
    }
};
                        </code></pre>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="serverless-cloud">
                        <textarea placeholder="Add your notes about serverless..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(200);
        topic.setSortOrder(17);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created Serverless & Cloud topic");
    }
    
    // ==================== TOPIC 18: Docker & Kubernetes ====================
    
    private void createDockerKubernetesTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Docker & Kubernetes: Container Orchestration");
        topic.setDescription("Master containerization: Docker best practices, Kubernetes deployments, and production orchestration");
        topic.setContent("""
            <div class="topic-content">
                <h2>🐳 Docker & Kubernetes</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Master Docker containerization and multi-stage builds</li>
                        <li>Deploy applications to Kubernetes clusters</li>
                        <li>Implement rolling updates and auto-scaling</li>
                        <li>Configure service mesh and ingress controllers</li>
                        <li>Monitor and debug containerized applications</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🏗️ Kubernetes Deployment</h3>
                    
                    <div class="code-example">
                        <h5>Kubernetes Deployment YAML:</h5>
                        <pre><code class="language-yaml">
apiVersion: apps/v1
kind: Deployment
metadata:
  name: spacex-tracker
  labels:
    app: spacex-tracker
spec:
  replicas: 3
  selector:
    matchLabels:
      app: spacex-tracker
  template:
    metadata:
      labels:
        app: spacex-tracker
    spec:
      containers:
      - name: spacex-tracker
        image: spacex-tracker:latest
        ports:
        - containerPort: 3000
        env:
        - name: NODE_ENV
          value: "production"
        resources:
          requests:
            memory: "256Mi"
            cpu: "250m"
          limits:
            memory: "512Mi"
            cpu: "500m"
        livenessProbe:
          httpGet:
            path: /health
            port: 3000
          initialDelaySeconds: 30
          periodSeconds: 10
        readinessProbe:
          httpGet:
            path: /ready
            port: 3000
          initialDelaySeconds: 5
          periodSeconds: 5
---
apiVersion: v1
kind: Service
metadata:
  name: spacex-tracker-service
spec:
  selector:
    app: spacex-tracker
  ports:
  - protocol: TCP
    port: 80
    targetPort: 3000
  type: LoadBalancer
                        </code></pre>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="docker-kubernetes">
                        <textarea placeholder="Add your notes about Docker and Kubernetes..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(220);
        topic.setSortOrder(18);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created Docker & Kubernetes topic");
    }
    
    // ==================== TOPIC 19: Monitoring & Logging ====================
    
    private void createMonitoringLoggingTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Monitoring, Logging & Observability");
        topic.setDescription("Master production monitoring: Prometheus, Grafana, ELK stack, distributed tracing, and alerting");
        topic.setContent("""
            <div class="topic-content">
                <h2>📊 Monitoring & Observability</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Implement structured logging with Winston/Pino</li>
                        <li>Set up Prometheus metrics and Grafana dashboards</li>
                        <li>Configure distributed tracing with Jaeger</li>
                        <li>Build alerting systems and on-call workflows</li>
                        <li>Analyze performance bottlenecks and optimize</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🏗️ Production Monitoring Setup</h3>
                    
                    <div class="code-example">
                        <h5>Structured Logging with Winston:</h5>
                        <pre><code class="language-javascript">
const winston = require('winston');
const { ElasticsearchTransport } = require('winston-elasticsearch');

const logger = winston.createLogger({
    level: 'info',
    format: winston.format.combine(
        winston.format.timestamp(),
        winston.format.errors({ stack: true }),
        winston.format.json()
    ),
    defaultMeta: { service: 'spacex-tracker' },
    transports: [
        new winston.transports.File({ filename: 'error.log', level: 'error' }),
        new winston.transports.File({ filename: 'combined.log' }),
        new ElasticsearchTransport({
            level: 'info',
            clientOpts: { node: 'http://localhost:9200' }
        })
    ]
});

// Prometheus metrics
const client = require('prom-client');
const register = new client.Registry();

const httpRequestDuration = new client.Histogram({
    name: 'http_request_duration_seconds',
    help: 'Duration of HTTP requests in seconds',
    labelNames: ['method', 'route', 'status_code'],
    registers: [register]
});

module.exports = { logger, httpRequestDuration, register };
                        </code></pre>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="monitoring-logging">
                        <textarea placeholder="Add your notes about monitoring..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(180);
        topic.setSortOrder(19);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created Monitoring & Logging topic");
    }
    
    // ==================== TOPIC 20: Security Best Practices ====================
    
    private void createSecurityBestPracticesTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Security Best Practices & Vulnerability Prevention");
        topic.setDescription("Master Node.js security: OWASP Top 10, input validation, rate limiting, and security headers");
        topic.setContent("""
            <div class="topic-content">
                <h2>🔒 Security Best Practices</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Prevent OWASP Top 10 vulnerabilities</li>
                        <li>Implement input validation and sanitization</li>
                        <li>Configure security headers and CORS properly</li>
                        <li>Handle secrets management and encryption</li>
                        <li>Perform security audits and penetration testing</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🏗️ Security Implementation</h3>
                    
                    <div class="code-example">
                        <h5>Comprehensive Security Middleware:</h5>
                        <pre><code class="language-javascript">
const helmet = require('helmet');
const rateLimit = require('express-rate-limit');
const mongoSanitize = require('express-mongo-sanitize');
const xss = require('xss-clean');

// Security headers
app.use(helmet({
    contentSecurityPolicy: {
        directives: {
            defaultSrc: ["'self'"],
            styleSrc: ["'self'", "'unsafe-inline'"],
            scriptSrc: ["'self'"],
            imgSrc: ["'self'", 'data:', 'https:']
        }
    },
    hsts: {
        maxAge: 31536000,
        includeSubDomains: true,
        preload: true
    }
}));

// Rate limiting
const limiter = rateLimit({
    windowMs: 15 * 60 * 1000,
    max: 100,
    message: 'Too many requests from this IP'
});
app.use('/api/', limiter);

// Data sanitization
app.use(mongoSanitize());
app.use(xss());

// Input validation
const { body, validationResult } = require('express-validator');

app.post('/api/launches',
    body('name').trim().isLength({ min: 3, max: 100 }).escape(),
    body('date').isISO8601(),
    async (req, res) => {
        const errors = validationResult(req);
        if (!errors.isEmpty()) {
            return res.status(400).json({ errors: errors.array() });
        }
        // Process request
    }
);

module.exports = app;
                        </code></pre>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="security-best-practices">
                        <textarea placeholder="Add your notes about security..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(200);
        topic.setSortOrder(20);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created Security Best Practices topic");
    }

    
    // ==================== TOPIC 21: Advanced Performance Tuning ====================
    
    private void createAdvancedPerformanceTuningTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Advanced Performance Tuning & Optimization");
        topic.setDescription("Master performance optimization: profiling, memory management, V8 internals, and production tuning");
        topic.setContent("""
            <div class="topic-content">
                <h2>⚡ Advanced Performance Tuning</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Profile and optimize Node.js applications</li>
                        <li>Understand V8 engine internals and optimization</li>
                        <li>Implement advanced caching strategies</li>
                        <li>Optimize memory usage and prevent leaks</li>
                        <li>Tune production performance at scale</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🏗️ Performance Optimization Techniques</h3>
                    
                    <div class="code-example">
                        <h5>Advanced Performance Monitoring:</h5>
                        <pre><code class="language-javascript">
const v8 = require('v8');
const { performance, PerformanceObserver } = require('perf_hooks');

class PerformanceMonitor {
    constructor() {
        this.setupObservers();
    }
    
    setupObservers() {
        const obs = new PerformanceObserver((items) => {
            items.getEntries().forEach((entry) => {
                console.log(\\`\\\\${entry.name}: \\\\${entry.duration}ms\\`);
            });
        });
        obs.observe({ entryTypes: ['measure', 'function'] });
    }
    
    getHeapStatistics() {
        const heapStats = v8.getHeapStatistics();
        return {
            totalHeapSize: (heapStats.total_heap_size / 1024 / 1024).toFixed(2) + ' MB',
            usedHeapSize: (heapStats.used_heap_size / 1024 / 1024).toFixed(2) + ' MB',
            heapSizeLimit: (heapStats.heap_size_limit / 1024 / 1024).toFixed(2) + ' MB'
        };
    }
    
    async profileFunction(fn, name) {
        performance.mark(\\`\\\\${name}-start\\`);
        const result = await fn();
        performance.mark(\\`\\\\${name}-end\\`);
        performance.measure(name, \\`\\\\${name}-start\\`, \\`\\\\${name}-end\\`);
        return result;
    }
}

module.exports = PerformanceMonitor;
                        </code></pre>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="performance-tuning">
                        <textarea placeholder="Add your notes about performance tuning..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(240);
        topic.setSortOrder(21);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created Advanced Performance Tuning topic");
    }
    
    // ==================== TOPIC 22: Distributed Systems ====================
    
    private void createDistributedSystemsTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Distributed Systems & Consensus Algorithms");
        topic.setDescription("Master distributed systems: CAP theorem, consensus algorithms, distributed transactions, and fault tolerance");
        topic.setContent("""
            <div class="topic-content">
                <h2>🌐 Distributed Systems</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Understand CAP theorem and consistency models</li>
                        <li>Implement distributed consensus with Raft/Paxos</li>
                        <li>Handle distributed transactions and saga patterns</li>
                        <li>Design fault-tolerant systems</li>
                        <li>Implement service mesh and circuit breakers</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🏗️ Distributed System Patterns</h3>
                    
                    <div class="code-example">
                        <h5>Circuit Breaker Pattern:</h5>
                        <pre><code class="language-javascript">
class CircuitBreaker {
    constructor(options = {}) {
        this.failureThreshold = options.failureThreshold || 5;
        this.resetTimeout = options.resetTimeout || 60000;
        this.state = 'CLOSED';
        this.failureCount = 0;
        this.nextAttempt = Date.now();
    }
    
    async execute(fn) {
        if (this.state === 'OPEN') {
            if (Date.now() < this.nextAttempt) {
                throw new Error('Circuit breaker is OPEN');
            }
            this.state = 'HALF_OPEN';
        }
        
        try {
            const result = await fn();
            this.onSuccess();
            return result;
        } catch (error) {
            this.onFailure();
            throw error;
        }
    }
    
    onSuccess() {
        this.failureCount = 0;
        this.state = 'CLOSED';
    }
    
    onFailure() {
        this.failureCount++;
        if (this.failureCount >= this.failureThreshold) {
            this.state = 'OPEN';
            this.nextAttempt = Date.now() + this.resetTimeout;
        }
    }
}

module.exports = CircuitBreaker;
                        </code></pre>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="distributed-systems">
                        <textarea placeholder="Add your notes about distributed systems..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(260);
        topic.setSortOrder(22);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created Distributed Systems topic");
    }
    
    // ==================== TOPIC 23: Event-Driven Architecture ====================
    
    private void createEventDrivenArchitectureTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Event-Driven Architecture & Message Queues");
        topic.setDescription("Master event-driven systems: Kafka, RabbitMQ, event sourcing, CQRS, and async patterns");
        topic.setContent("""
            <div class="topic-content">
                <h2>📨 Event-Driven Architecture</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Design event-driven architectures</li>
                        <li>Implement Kafka producers and consumers</li>
                        <li>Build event sourcing and CQRS patterns</li>
                        <li>Handle message ordering and idempotency</li>
                        <li>Implement saga patterns for distributed workflows</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🏗️ Kafka Event System</h3>
                    
                    <div class="code-example">
                        <h5>Kafka Producer/Consumer:</h5>
                        <pre><code class="language-javascript">
const { Kafka } = require('kafkajs');

class EventBus {
    constructor() {
        this.kafka = new Kafka({
            clientId: 'spacex-tracker',
            brokers: ['localhost:9092']
        });
        
        this.producer = this.kafka.producer();
        this.consumer = this.kafka.consumer({ groupId: 'launch-service' });
    }
    
    async publishEvent(topic, event) {
        await this.producer.connect();
        await this.producer.send({
            topic,
            messages: [{
                key: event.id,
                value: JSON.stringify(event),
                headers: { 'event-type': event.type }
            }]
        });
    }
    
    async subscribe(topic, handler) {
        await this.consumer.connect();
        await this.consumer.subscribe({ topic, fromBeginning: false });
        
        await this.consumer.run({
            eachMessage: async ({ topic, partition, message }) => {
                const event = JSON.parse(message.value.toString());
                await handler(event);
            }
        });
    }
}

module.exports = EventBus;
                        </code></pre>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="event-driven-architecture">
                        <textarea placeholder="Add your notes about event-driven architecture..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(240);
        topic.setSortOrder(23);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created Event-Driven Architecture topic");
    }
    
    // ==================== TOPIC 24: Production Debugging ====================
    
    private void createProductionDebuggingTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Production Debugging & Troubleshooting");
        topic.setDescription("Master production debugging: heap dumps, CPU profiling, distributed tracing, and incident response");
        topic.setContent("""
            <div class="topic-content">
                <h2>🔍 Production Debugging</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Debug production issues without downtime</li>
                        <li>Analyze heap dumps and memory leaks</li>
                        <li>Profile CPU usage and optimize hot paths</li>
                        <li>Implement distributed tracing</li>
                        <li>Handle incident response and post-mortems</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🏗️ Production Debugging Tools</h3>
                    
                    <div class="code-example">
                        <h5>Heap Dump Analysis:</h5>
                        <pre><code class="language-javascript">
const v8 = require('v8');
const fs = require('fs');

class ProductionDebugger {
    static captureHeapSnapshot(filename) {
        const snapshotStream = v8.writeHeapSnapshot(filename);
        console.log(\\`Heap snapshot written to \\\\${snapshotStream}\\`);
        return snapshotStream;
    }
    
    static async analyzeCPUProfile(duration = 10000) {
        const profiler = require('v8-profiler-next');
        profiler.startProfiling('CPU Profile');
        
        await new Promise(resolve => setTimeout(resolve, duration));
        
        const profile = profiler.stopProfiling();
        profile.export((error, result) => {
            fs.writeFileSync('cpu-profile.cpuprofile', result);
            profile.delete();
        });
    }
    
    static monitorEventLoop() {
        const { monitorEventLoopDelay } = require('perf_hooks');
        const h = monitorEventLoopDelay({ resolution: 20 });
        h.enable();
        
        setInterval(() => {
            console.log(\\`Event Loop Delay: \\\\${h.mean}ms (max: \\\\${h.max}ms)\\`);
        }, 5000);
    }
}

module.exports = ProductionDebugger;
                        </code></pre>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="production-debugging">
                        <textarea placeholder="Add your notes about production debugging..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(220);
        topic.setSortOrder(24);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created Production Debugging topic");
    }
    
    // ==================== TOPIC 25: Scalability Patterns ====================
    
    private void createScalabilityPatternsTopic(LearningModule module) {
        Topic topic = new Topic();
        topic.setTitle("Scalability Patterns & High-Performance Systems");
        topic.setDescription("Master scalability: horizontal scaling, load balancing, caching strategies, and handling millions of requests");
        topic.setContent("""
            <div class="topic-content">
                <h2>📈 Scalability Patterns</h2>
                
                <div class="learning-objectives">
                    <h3>🎯 Learning Objectives</h3>
                    <ul>
                        <li>Design horizontally scalable architectures</li>
                        <li>Implement advanced load balancing strategies</li>
                        <li>Build multi-layer caching systems</li>
                        <li>Handle database sharding and replication</li>
                        <li>Optimize for millions of concurrent users</li>
                    </ul>
                </div>
                
                <div class="concept-section">
                    <h3>🏗️ High-Performance Architecture</h3>
                    
                    <div class="code-example">
                        <h5>Multi-Layer Caching Strategy:</h5>
                        <pre><code class="language-javascript">
const Redis = require('ioredis');
const NodeCache = require('node-cache');

class MultiLayerCache {
    constructor() {
        this.l1Cache = new NodeCache({ stdTTL: 60 }); // In-memory
        this.l2Cache = new Redis(); // Redis
    }
    
    async get(key) {
        // Try L1 cache first
        let value = this.l1Cache.get(key);
        if (value) {
            console.log('L1 Cache HIT');
            return value;
        }
        
        // Try L2 cache
        value = await this.l2Cache.get(key);
        if (value) {
            console.log('L2 Cache HIT');
            this.l1Cache.set(key, value);
            return JSON.parse(value);
        }
        
        console.log('Cache MISS');
        return null;
    }
    
    async set(key, value, ttl = 300) {
        this.l1Cache.set(key, value, ttl);
        await this.l2Cache.setex(key, ttl, JSON.stringify(value));
    }
    
    async invalidate(key) {
        this.l1Cache.del(key);
        await this.l2Cache.del(key);
    }
}

// Load Balancer with Health Checks
class LoadBalancer {
    constructor(servers) {
        this.servers = servers;
        this.currentIndex = 0;
        this.healthStatus = new Map();
        this.startHealthChecks();
    }
    
    startHealthChecks() {
        setInterval(() => {
            this.servers.forEach(async (server) => {
                try {
                    const response = await fetch(\\`\\\\${server}/health\\`);
                    this.healthStatus.set(server, response.ok);
                } catch (error) {
                    this.healthStatus.set(server, false);
                }
            });
        }, 10000);
    }
    
    getNextServer() {
        const healthyServers = this.servers.filter(s => this.healthStatus.get(s) !== false);
        
        if (healthyServers.length === 0) {
            throw new Error('No healthy servers available');
        }
        
        const server = healthyServers[this.currentIndex % healthyServers.length];
        this.currentIndex++;
        return server;
    }
}

module.exports = { MultiLayerCache, LoadBalancer };
                        </code></pre>
                    </div>
                </div>
                
                <div class="note-taking-section">
                    <h3>📝 Your Notes</h3>
                    <div class="note-editor" data-topic="scalability-patterns">
                        <textarea placeholder="Add your notes about scalability patterns..."></textarea>
                    </div>
                </div>
            </div>
            """);
        
        topic.setEstimatedMinutes(260);
        topic.setSortOrder(25);
        module.getTopics().add(topic);
        topicRepository.save(topic);
        
        log.info("✅ Created Scalability Patterns topic");
    }
    
    // ==================== HELPER METHODS ====================
    
    /**
     * Helper method to create interview questions with multiple solutions
     */
    private InterviewQuestion createInterviewQuestion(
            String title,
            String description,
            String difficulty,
            String category,
            List<String> companies,
            String solution,
            List<String> tags,
            Topic topic) {
        
        InterviewQuestion question = new InterviewQuestion();
        question.setTitle(title);
        question.setDescription(description);
        question.setDifficulty(InterviewQuestion.Difficulty.valueOf(difficulty.toUpperCase()));
        // Set category as enum if it matches, otherwise use PROBLEM_SOLVING as default
        try {
            question.setCategory(InterviewQuestion.QuestionCategory.valueOf(category.toUpperCase().replace(" ", "_")));
        } catch (IllegalArgumentException e) {
            question.setCategory(InterviewQuestion.QuestionCategory.PROBLEM_SOLVING);
        }
        // Set first company if available
        if (!companies.isEmpty()) {
            try {
                question.setCompany(InterviewQuestion.Company.valueOf(companies.get(0).toUpperCase().replace(" ", "_")));
            } catch (IllegalArgumentException e) {
                // Skip if company doesn't match enum
            }
        }
        question.setSolution(solution);
        question.setTags(String.join(",", tags));
        question.setTopic(topic);
        question.setActive(true);
        
        return questionRepository.save(question);
    }
    
    /**
     * Helper method to create interview questions with module reference
     */
    private InterviewQuestion createInterviewQuestion(
            String title,
            String description,
            String difficulty,
            String category,
            LearningModule module,
            String solution) {
        
        InterviewQuestion question = new InterviewQuestion();
        question.setTitle(title);
        question.setDescription(description);
        question.setDifficulty(InterviewQuestion.Difficulty.valueOf(difficulty.toUpperCase()));
        // Set category as enum
        try {
            question.setCategory(InterviewQuestion.QuestionCategory.valueOf(category.toUpperCase().replace(" ", "_")));
        } catch (IllegalArgumentException e) {
            question.setCategory(InterviewQuestion.QuestionCategory.PROBLEM_SOLVING);
        }
        question.setSolution(solution);
        question.setActive(true);
        
        return questionRepository.save(question);
    }
    
    /**
     * Helper method to create generics-related interview questions
     */
    private void createGenericsInterviewQuestions(Topic topic) {
        // Placeholder for generics questions
        log.info("Creating generics interview questions for topic: {}", topic.getTitle());
    }

}
