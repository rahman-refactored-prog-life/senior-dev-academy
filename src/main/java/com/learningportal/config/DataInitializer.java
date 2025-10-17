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
            createDatabaseModule();
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
        
        topic.setLearningModule(module);
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
        question1.setCompany("Meta");
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
        question2.setCompany("Amazon");
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
        question3.setCompany("Google");
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
        
        topic.setLearningModule(module);
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
        question1.setCompany("Meta");
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
        
        topic.setLearningModule(module);
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
        
        topic.setLearningModule(module);
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
        
        topic.setLearningModule(module);
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
        
        topic.setLearningModule(module);
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
        question1.setCompany("Amazon");
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