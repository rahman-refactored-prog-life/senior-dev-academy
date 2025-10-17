---
name: "Code Quality Validation"
description: "Validate code examples and implementations for compilation and execution"
trigger: "manual"
category: "quality-assurance"
---

# Code Quality Validation Hook

## Purpose
Ensure all code examples in learning content compile, execute properly, and meet quality standards for educational use.

## Trigger Conditions
- After implementing new learning topics with code examples
- When adding interview questions with code solutions
- Before marking any phase as complete
- During quality assurance reviews

## Validation Actions

### 1. Java Code Validation
```bash
# Compile all Java examples
find . -name "*.java" -path "*/examples/*" -exec javac {} \;

# Run Java examples with test cases
java -cp . examples.JavaBasicsExample
java -cp . examples.CollectionsExample
java -cp . examples.ConcurrencyExample
```

### 2. Node.js Code Validation
```bash
# Validate Node.js syntax
find . -name "*.js" -path "*/nodejs-examples/*" -exec node --check {} \;

# Run Node.js examples
node examples/nodejs/basic-server.js
node examples/nodejs/async-patterns.js
node examples/nodejs/performance-optimization.js
```

### 3. React Code Validation
```bash
# Validate React components
cd frontend && npm run lint
cd frontend && npm run type-check

# Test React component compilation
cd frontend && npm run build
```

### 4. Database Query Validation
```sql
-- Test SQL examples against H2 database
-- Validate query syntax and execution
SELECT * FROM learning_modules WHERE category = 'JAVA_FUNDAMENTALS';
SELECT * FROM interview_questions WHERE company = 'Amazon';
```

### 5. Performance Benchmarking
```java
// Validate performance examples include timing
public class PerformanceExample {
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        // Algorithm implementation
        long endTime = System.nanoTime();
        System.out.println("Execution time: " + (endTime - startTime) + " ns");
    }
}
```

## Quality Standards Checklist
- [ ] All Java code compiles without errors
- [ ] Node.js examples execute successfully
- [ ] React components render without warnings
- [ ] Database queries return expected results
- [ ] Performance examples include timing measurements
- [ ] Code follows consistent formatting standards
- [ ] Comments explain complex algorithms and patterns
- [ ] Error handling is implemented where appropriate

## Automated Checks
1. **Syntax Validation**: Check for compilation errors
2. **Runtime Testing**: Execute examples with sample inputs
3. **Performance Verification**: Ensure timing code is included
4. **Documentation Check**: Verify code comments and explanations
5. **Best Practices**: Follow language-specific conventions

## Error Reporting
- Generate detailed error reports for failed validations
- Include line numbers and specific error messages
- Provide suggestions for fixing common issues
- Track validation history and improvement trends

## Integration with Documentation
- Update DEVELOPMENT_GUIDE.md with validation results
- Include performance benchmarks in topic sections
- Document any code quality improvements made
- Maintain quality metrics and standards