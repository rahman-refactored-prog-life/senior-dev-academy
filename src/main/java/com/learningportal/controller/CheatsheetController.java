package com.learningportal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Cheatsheet Controller
 * 
 * Provides endpoints for quick reference guides, cheatsheets, and topic summaries.
 */
@RestController
@RequestMapping("/cheatsheets")
@Tag(name = "Cheatsheets & Summaries", description = "Quick reference guides and topic summaries")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CheatsheetController {
    
    @Operation(
        summary = "Get algorithms cheatsheet",
        description = "Returns quick reference for common algorithms with time/space complexity"
    )
    @GetMapping("/algorithms")
    public ResponseEntity<Map<String, Object>> getAlgorithmsCheatsheet() {
        Map<String, Object> cheatsheet = new HashMap<>();
        
        cheatsheet.put("title", "Algorithms Cheatsheet");
        cheatsheet.put("description", "Quick reference for common algorithms and their complexities");
        
        Map<String, List<Map<String, Object>>> categories = new HashMap<>();
        
        categories.put("Sorting Algorithms", Arrays.asList(
            createAlgorithmMap("Bubble Sort", "O(n²)", "O(1)", "Simple but inefficient"),
            createAlgorithmMap("Selection Sort", "O(n²)", "O(1)", "Finds minimum and swaps"),
            createAlgorithmMap("Insertion Sort", "O(n²)", "O(1)", "Good for small datasets"),
            createAlgorithmMap("Merge Sort", "O(n log n)", "O(n)", "Divide and conquer, stable"),
            createAlgorithmMap("Quick Sort", "O(n log n)", "O(log n)", "Fast average case"),
            createAlgorithmMap("Heap Sort", "O(n log n)", "O(1)", "Uses heap data structure")
        ));
        
        categories.put("Search Algorithms", Arrays.asList(
            createAlgorithmMap("Linear Search", "O(n)", "O(1)", "Check each element"),
            createAlgorithmMap("Binary Search", "O(log n)", "O(1)", "Requires sorted array"),
            createAlgorithmMap("DFS", "O(V + E)", "O(V)", "Depth-first traversal"),
            createAlgorithmMap("BFS", "O(V + E)", "O(V)", "Breadth-first traversal")
        ));
        
        categories.put("Dynamic Programming", Arrays.asList(
            createAlgorithmMap("Fibonacci", "O(n)", "O(n)", "Classic DP example"),
            createAlgorithmMap("Knapsack", "O(nW)", "O(nW)", "Optimization problem"),
            createAlgorithmMap("LCS", "O(mn)", "O(mn)", "Longest common subsequence"),
            createAlgorithmMap("Edit Distance", "O(mn)", "O(mn)", "String transformation")
        ));
        
        categories.put("Graph Algorithms", Arrays.asList(
            createAlgorithmMap("Dijkstra", "O((V + E) log V)", "O(V)", "Shortest path"),
            createAlgorithmMap("Bellman-Ford", "O(VE)", "O(V)", "Handles negative weights"),
            createAlgorithmMap("Floyd-Warshall", "O(V³)", "O(V²)", "All pairs shortest path"),
            createAlgorithmMap("Kruskal's", "O(E log E)", "O(V)", "Minimum spanning tree")
        ));
        
        cheatsheet.put("categories", categories);
        
        return ResponseEntity.ok(cheatsheet);
    }
    
    @Operation(
        summary = "Get data structures cheatsheet",
        description = "Returns quick reference for data structures with operations complexity"
    )
    @GetMapping("/data-structures")
    public ResponseEntity<Map<String, Object>> getDataStructuresCheatsheet() {
        Map<String, Object> cheatsheet = new HashMap<>();
        
        cheatsheet.put("title", "Data Structures Cheatsheet");
        cheatsheet.put("description", "Quick reference for data structures and their operations");
        
        List<Map<String, Object>> structures = Arrays.asList(
            createDataStructureMap("Array", 
                                 "O(1)", "O(n)", "O(n)", "O(n)",
                                 "Fixed size, contiguous memory",
                                 Arrays.asList("Random access", "Cache friendly", "Simple")),
            
            createDataStructureMap("Dynamic Array", 
                                 "O(1)", "O(n)", "O(1)*", "O(n)",
                                 "Resizable array",
                                 Arrays.asList("Flexible size", "Amortized insertion", "ArrayList/Vector")),
            
            createDataStructureMap("Linked List", 
                                 "O(n)", "O(n)", "O(1)", "O(1)",
                                 "Nodes connected by pointers",
                                 Arrays.asList("Dynamic size", "Easy insertion/deletion", "No random access")),
            
            createDataStructureMap("Stack", 
                                 "O(n)", "O(n)", "O(1)", "O(1)",
                                 "LIFO (Last In, First Out)",
                                 Arrays.asList("Function calls", "Expression evaluation", "Undo operations")),
            
            createDataStructureMap("Queue", 
                                 "O(n)", "O(n)", "O(1)", "O(1)",
                                 "FIFO (First In, First Out)",
                                 Arrays.asList("BFS", "Task scheduling", "Buffer")),
            
            createDataStructureMap("Hash Table", 
                                 "O(1)*", "O(1)*", "O(1)*", "O(1)*",
                                 "Key-value pairs with hash function",
                                 Arrays.asList("Fast lookup", "Dictionary", "Cache")),
            
            createDataStructureMap("Binary Search Tree", 
                                 "O(log n)*", "O(log n)*", "O(log n)*", "O(log n)*",
                                 "Ordered binary tree",
                                 Arrays.asList("Sorted data", "Range queries", "In-order traversal")),
            
            createDataStructureMap("Heap", 
                                 "O(n)", "O(log n)", "O(log n)", "O(log n)",
                                 "Complete binary tree with heap property",
                                 Arrays.asList("Priority queue", "Heap sort", "Top K problems")),
            
            createDataStructureMap("Trie", 
                                 "O(m)", "O(m)", "O(m)", "O(m)",
                                 "Prefix tree for strings",
                                 Arrays.asList("Autocomplete", "Spell checker", "IP routing"))
        );
        
        cheatsheet.put("structures", structures);
        
        return ResponseEntity.ok(cheatsheet);
    }
    
    @Operation(
        summary = "Get system design cheatsheet",
        description = "Returns quick reference for system design concepts and numbers"
    )
    @GetMapping("/system-design")
    public ResponseEntity<Map<String, Object>> getSystemDesignCheatsheet() {
        Map<String, Object> cheatsheet = new HashMap<>();
        
        cheatsheet.put("title", "System Design Cheatsheet");
        cheatsheet.put("description", "Essential concepts and numbers for system design interviews");
        
        Map<String, Object> concepts = new HashMap<>();
        
        concepts.put("Scalability Patterns", Arrays.asList(
            "Load Balancing - Distribute traffic across servers",
            "Horizontal Scaling - Add more servers",
            "Vertical Scaling - Upgrade server hardware",
            "Database Sharding - Partition data across databases",
            "Caching - Store frequently accessed data",
            "CDN - Geographically distributed content",
            "Microservices - Decompose into small services",
            "Message Queues - Asynchronous communication"
        ));
        
        concepts.put("Database Concepts", Arrays.asList(
            "ACID - Atomicity, Consistency, Isolation, Durability",
            "CAP Theorem - Consistency, Availability, Partition tolerance",
            "SQL vs NoSQL - Structured vs flexible schema",
            "Replication - Master-slave, master-master",
            "Sharding - Horizontal partitioning",
            "Indexing - B-tree, hash, bitmap indexes",
            "Normalization - Reduce data redundancy",
            "Denormalization - Optimize for read performance"
        ));
        
        concepts.put("Caching Strategies", Arrays.asList(
            "Cache-aside - Application manages cache",
            "Write-through - Write to cache and database",
            "Write-behind - Write to cache, async to database",
            "Refresh-ahead - Proactively refresh cache",
            "TTL - Time to live for cache entries",
            "LRU - Least recently used eviction",
            "Cache levels - Browser, CDN, application, database"
        ));
        
        Map<String, String> importantNumbers = new HashMap<>();
        importantNumbers.put("L1 cache reference", "0.5 ns");
        importantNumbers.put("Main memory reference", "100 ns");
        importantNumbers.put("SSD random read", "150,000 ns");
        importantNumbers.put("Network round trip (same datacenter)", "500,000 ns");
        importantNumbers.put("Disk seek", "10,000,000 ns");
        importantNumbers.put("Network round trip (CA to Netherlands)", "150,000,000 ns");
        
        Map<String, String> capacityNumbers = new HashMap<>();
        capacityNumbers.put("Web server QPS", "1,000");
        capacityNumbers.put("SQL database QPS", "1,000");
        capacityNumbers.put("NoSQL database QPS", "10,000");
        capacityNumbers.put("Cache server QPS", "100,000 - 1,000,000");
        
        cheatsheet.put("concepts", concepts);
        cheatsheet.put("importantNumbers", importantNumbers);
        cheatsheet.put("capacityNumbers", capacityNumbers);
        
        return ResponseEntity.ok(cheatsheet);
    }
    
    @Operation(
        summary = "Get Java concepts summary",
        description = "Returns concise summary of all Java fundamentals and advanced concepts"
    )
    @GetMapping("/java-summary")
    public ResponseEntity<Map<String, Object>> getJavaSummary() {
        Map<String, Object> summary = new HashMap<>();
        
        summary.put("title", "Java Concepts Summary");
        summary.put("description", "Comprehensive summary of Java fundamentals and advanced topics");
        
        Map<String, List<String>> topics = new HashMap<>();
        
        topics.put("Core Concepts", Arrays.asList(
            "OOP Principles: Encapsulation, Inheritance, Polymorphism, Abstraction",
            "Data Types: Primitives (int, double, boolean) vs Objects (Integer, Double, Boolean)",
            "Variables: Local, instance, static variables and their scope",
            "Methods: Overloading, overriding, static methods, final methods",
            "Classes: Abstract classes, final classes, nested classes, inner classes"
        ));
        
        topics.put("Collections Framework", Arrays.asList(
            "List: ArrayList (dynamic array), LinkedList (doubly-linked)",
            "Set: HashSet (hash table), TreeSet (red-black tree), LinkedHashSet",
            "Map: HashMap (hash table), TreeMap (red-black tree), LinkedHashMap",
            "Queue: PriorityQueue (heap), ArrayDeque (resizable array)",
            "Iterators: Iterator, ListIterator, enhanced for-loop"
        ));
        
        topics.put("Exception Handling", Arrays.asList(
            "Checked Exceptions: Must be caught or declared (IOException, SQLException)",
            "Unchecked Exceptions: Runtime exceptions (NullPointerException, IllegalArgumentException)",
            "Try-catch-finally: Exception handling blocks",
            "Custom Exceptions: Creating application-specific exceptions",
            "Best Practices: Specific exceptions, proper logging, resource cleanup"
        ));
        
        topics.put("Concurrency", Arrays.asList(
            "Threads: Creating threads, thread lifecycle, synchronization",
            "Synchronized: Methods and blocks, intrinsic locks",
            "Concurrent Collections: ConcurrentHashMap, CopyOnWriteArrayList",
            "Executor Framework: ThreadPoolExecutor, ScheduledExecutorService",
            "Atomic Classes: AtomicInteger, AtomicReference for lock-free programming"
        ));
        
        topics.put("Memory Management", Arrays.asList(
            "Heap vs Stack: Object storage vs method calls and local variables",
            "Garbage Collection: Mark and sweep, generational GC, G1GC",
            "Memory Leaks: Strong references, listeners, static collections",
            "JVM Tuning: Heap size, GC algorithms, monitoring tools",
            "Memory Profiling: JVisualVM, JProfiler, heap dumps"
        ));
        
        summary.put("topics", topics);
        
        return ResponseEntity.ok(summary);
    }
    
    @Operation(
        summary = "Get all available cheatsheets",
        description = "Returns list of all available cheatsheets and summaries"
    )
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllCheatsheets() {
        Map<String, Object> all = new HashMap<>();
        
        all.put("title", "All Cheatsheets & Summaries");
        all.put("description", "Complete collection of quick reference guides");
        
        List<Map<String, Object>> cheatsheets = Arrays.asList(
            createCheatsheetInfo("Algorithms", "/cheatsheets/algorithms", "Common algorithms with time/space complexity"),
            createCheatsheetInfo("Data Structures", "/cheatsheets/data-structures", "Data structures and operations complexity"),
            createCheatsheetInfo("System Design", "/cheatsheets/system-design", "System design patterns and important numbers"),
            createCheatsheetInfo("Java Summary", "/cheatsheets/java-summary", "Complete Java concepts summary"),
            createCheatsheetInfo("SQL Quick Reference", "/databases/sql/interview-questions", "Common SQL patterns and queries"),
            createCheatsheetInfo("NoSQL Concepts", "/databases/nosql/interview-questions", "NoSQL database concepts and use cases"),
            createCheatsheetInfo("Amazon Leadership Principles", "/behavioral/amazon-leadership-principles", "All 16 Amazon Leadership Principles"),
            createCheatsheetInfo("STAR Method", "/behavioral/star-method", "Framework for behavioral interviews"),
            createCheatsheetInfo("Behavioral Questions", "/behavioral/common-questions", "Most common behavioral interview questions")
        );
        
        all.put("cheatsheets", cheatsheets);
        all.put("totalCheatsheets", cheatsheets.size());
        
        return ResponseEntity.ok(all);
    }
    
    // Helper methods
    private Map<String, Object> createAlgorithmMap(String name, String timeComplexity, String spaceComplexity, String description) {
        Map<String, Object> algorithm = new HashMap<>();
        algorithm.put("name", name);
        algorithm.put("timeComplexity", timeComplexity);
        algorithm.put("spaceComplexity", spaceComplexity);
        algorithm.put("description", description);
        return algorithm;
    }
    
    private Map<String, Object> createDataStructureMap(String name, String access, String search, String insertion, String deletion, String description, List<String> useCases) {
        Map<String, Object> structure = new HashMap<>();
        structure.put("name", name);
        structure.put("access", access);
        structure.put("search", search);
        structure.put("insertion", insertion);
        structure.put("deletion", deletion);
        structure.put("description", description);
        structure.put("useCases", useCases);
        return structure;
    }
    
    private Map<String, Object> createCheatsheetInfo(String title, String endpoint, String description) {
        Map<String, Object> info = new HashMap<>();
        info.put("title", title);
        info.put("endpoint", endpoint);
        info.put("description", description);
        return info;
    }
}