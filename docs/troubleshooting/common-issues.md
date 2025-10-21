# Common Issues and Solutions

## Overview

This guide provides step-by-step resolution procedures for the most common issues encountered in the Learning Portal system. Each issue includes symptoms, root causes, and detailed resolution steps.

## Table of Contents

1. [Application Startup Issues](#application-startup-issues)
2. [Database Connection Problems](#database-connection-problems)
3. [Compilation and Build Errors](#compilation-and-build-errors)
4. [Performance Issues](#performance-issues)
5. [Frontend Loading Problems](#frontend-loading-problems)
6. [API Endpoint Errors](#api-endpoint-errors)
7. [Authentication and Authorization Issues](#authentication-and-authorization-issues)
8. [Caching Problems](#caching-problems)
9. [Logging and Monitoring Issues](#logging-and-monitoring-issues)
10. [Environment Configuration Problems](#environment-configuration-problems)

---

## Application Startup Issues

### Issue 1: Application Fails to Start - Port Already in Use

**Symptoms:**
```
***************************
APPLICATION FAILED TO START
***************************

Description:
Web server failed to start. Port 8080 was already in use.

Action:
Identify and stop the process that's listening on port 8080 or configure a different port.
```

**Root Cause:** Another process is using port 8080.

**Resolution Steps:**

1. **Identify the process using port 8080:**
   ```bash
   # On macOS/Linux
   lsof -i :8080
   
   # On Windows
   netstat -ano | findstr :8080
   ```

2. **Kill the conflicting process:**
   ```bash
   # On macOS/Linux
   kill -9 <PID>
   
   # On Windows
   taskkill /PID <PID> /F
   ```

3. **Alternative: Change application port:**
   ```yaml
   # In application.yml
   server:
     port: 8081
   ```

4. **Verify resolution:**
   ```bash
   mvn spring-boot:run
   # Should start successfully on the configured port
   ```

### Issue 2: Bean Creation Failure - Circular Dependencies

**Symptoms:**
```
***************************
APPLICATION FAILED TO START
***************************

Description:
The dependencies of some of the beans in the application context form a cycle
```

**Root Cause:** Circular dependency between Spring beans.

**Resolution Steps:**

1. **Identify circular dependencies in logs:**
   ```
   Look for: "Relying upon circular references is discouraged"
   ```

2. **Common solutions:**
   - Use `@Lazy` annotation on one of the dependencies
   - Refactor to eliminate circular dependency
   - Use setter injection instead of constructor injection

3. **Example fix:**
   ```java
   @Service
   public class ServiceA {
       private final ServiceB serviceB;
       
       public ServiceA(@Lazy ServiceB serviceB) {
           this.serviceB = serviceB;
       }
   }
   ```

### Issue 3: Database Schema Creation Failure

**Symptoms:**
```
Caused by: org.h2.jdbc.JdbcSQLSyntaxErrorException: 
Table "LEARNING_MODULE" already exists
```

**Root Cause:** Database schema conflicts or improper JPA configuration.

**Resolution Steps:**

1. **Clear H2 database (development):**
   ```bash
   # Stop application
   # Delete H2 database files (if persistent)
   rm -f *.db
   ```

2. **Check JPA configuration:**
   ```yaml
   # In application.yml
   spring:
     jpa:
       hibernate:
         ddl-auto: create-drop  # For development
   ```

3. **Verify entity annotations:**
   ```java
   @Entity
   @Table(name = "learning_module")
   public class LearningModule {
       // Ensure proper annotations
   }
   ```

---

## Database Connection Problems

### Issue 1: H2 Database Connection Refused

**Symptoms:**
```
Connection is broken: "java.net.ConnectException: Connection refused"
```

**Root Cause:** H2 database configuration issues.

**Resolution Steps:**

1. **Check H2 configuration:**
   ```yaml
   # In application.yml
   spring:
     datasource:
       url: jdbc:h2:mem:devportal
       username: sa
       password: password
       driver-class-name: org.h2.Driver
     h2:
       console:
         enabled: true
   ```

2. **Verify H2 console access:**
   ```
   URL: http://localhost:8080/h2-console
   JDBC URL: jdbc:h2:mem:devportal
   Username: sa
   Password: password
   ```

3. **Check for conflicting database configurations:**
   ```bash
   grep -r "datasource" src/main/resources/
   ```

### Issue 2: PostgreSQL Connection Timeout

**Symptoms:**
```
org.postgresql.util.PSQLException: Connection to localhost:5432 refused
```

**Root Cause:** PostgreSQL server not running or incorrect configuration.

**Resolution Steps:**

1. **Start PostgreSQL service:**
   ```bash
   # macOS
   brew services start postgresql
   
   # Linux
   sudo systemctl start postgresql
   
   # Windows
   net start postgresql-x64-13
   ```

2. **Verify PostgreSQL is running:**
   ```bash
   pg_isready -h localhost -p 5432
   ```

3. **Check connection configuration:**
   ```yaml
   # In application-production.yml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/learning_portal
       username: ${DB_USERNAME:postgres}
       password: ${DB_PASSWORD:password}
   ```

4. **Test connection manually:**
   ```bash
   psql -h localhost -p 5432 -U postgres -d learning_portal
   ```

---

## Compilation and Build Errors

### Issue 1: Lombok Annotations Not Working

**Symptoms:**
```
java: cannot find symbol
  symbol:   method getId()
  location: variable module of type com.learningportal.model.LearningModule
```

**Root Cause:** Lombok not properly configured in IDE or build system.

**Resolution Steps:**

1. **Verify Lombok dependency in pom.xml:**
   ```xml
   <dependency>
       <groupId>org.projectlombok</groupId>
       <artifactId>lombok</artifactId>
       <optional>true</optional>
   </dependency>
   ```

2. **Configure IDE (IntelliJ IDEA):**
   - Install Lombok plugin
   - Enable annotation processing: Settings → Build → Compiler → Annotation Processors

3. **Clean and rebuild:**
   ```bash
   mvn clean compile
   ```

4. **Verify Lombok version compatibility:**
   ```bash
   mvn dependency:tree | grep lombok
   ```

### Issue 2: Maven Build Failure - Frontend Integration

**Symptoms:**
```
[ERROR] Failed to execute goal com.github.eirslett:frontend-maven-plugin
```

**Root Cause:** Node.js/npm not properly installed or configured.

**Resolution Steps:**

1. **Check Node.js installation:**
   ```bash
   node --version
   npm --version
   ```

2. **Clear npm cache:**
   ```bash
   cd frontend
   rm -rf node_modules package-lock.json
   npm cache clean --force
   npm install
   ```

3. **Build frontend manually:**
   ```bash
   cd frontend
   npm run build
   ```

4. **Check Maven frontend plugin configuration:**
   ```xml
   <plugin>
       <groupId>com.github.eirslett</groupId>
       <artifactId>frontend-maven-plugin</artifactId>
       <version>1.12.1</version>
   </plugin>
   ```

---

## Performance Issues

### Issue 1: Slow API Response Times

**Symptoms:**
- API endpoints taking > 2 seconds to respond
- High CPU usage
- Database connection pool exhaustion

**Root Cause:** Inefficient queries, missing indexes, or connection pool issues.

**Resolution Steps:**

1. **Enable SQL logging:**
   ```yaml
   logging:
     level:
       org.hibernate.SQL: DEBUG
       org.hibernate.type.descriptor.sql.BasicBinder: TRACE
   ```

2. **Check for N+1 query problems:**
   ```java
   // Use @EntityGraph or JOIN FETCH
   @Query("SELECT m FROM LearningModule m JOIN FETCH m.topics")
   List<LearningModule> findAllWithTopics();
   ```

3. **Configure connection pool:**
   ```yaml
   spring:
     datasource:
       hikari:
         maximum-pool-size: 20
         minimum-idle: 5
         connection-timeout: 30000
   ```

4. **Add database indexes:**
   ```sql
   CREATE INDEX idx_module_category ON learning_module(category);
   CREATE INDEX idx_topic_module_id ON topic(module_id);
   ```

### Issue 2: High Memory Usage

**Symptoms:**
- OutOfMemoryError exceptions
- Frequent garbage collection
- Application becoming unresponsive

**Root Cause:** Memory leaks, large object retention, or insufficient heap size.

**Resolution Steps:**

1. **Increase heap size:**
   ```bash
   java -Xmx2g -Xms1g -jar application.jar
   ```

2. **Enable garbage collection logging:**
   ```bash
   java -XX:+PrintGC -XX:+PrintGCDetails -jar application.jar
   ```

3. **Check for memory leaks:**
   ```java
   // Use try-with-resources for auto-closeable resources
   try (Connection conn = dataSource.getConnection()) {
       // Use connection
   }
   ```

4. **Configure JPA batch processing:**
   ```yaml
   spring:
     jpa:
       properties:
         hibernate:
           jdbc:
             batch_size: 20
   ```

---

## Frontend Loading Problems

### Issue 1: White Screen on Application Load

**Symptoms:**
- Browser shows blank white page
- No console errors initially
- Network requests not being made

**Root Cause:** JavaScript bundle loading failure or routing issues.

**Resolution Steps:**

1. **Check browser console for errors:**
   ```
   Open Developer Tools → Console tab
   Look for JavaScript errors or failed network requests
   ```

2. **Verify static resource serving:**
   ```bash
   curl http://localhost:8080/static/js/main.js
   # Should return JavaScript content
   ```

3. **Check React Router configuration:**
   ```javascript
   // Ensure proper Router setup
   import { BrowserRouter as Router } from 'react-router-dom';
   
   function App() {
     return (
       <Router>
         <Routes>
           <Route path="/" element={<HomePage />} />
         </Routes>
       </Router>
     );
   }
   ```

4. **Rebuild frontend:**
   ```bash
   cd frontend
   npm run build
   mvn clean package
   ```

### Issue 2: API Calls Failing with CORS Errors

**Symptoms:**
```
Access to fetch at 'http://localhost:8080/api/modules' from origin 'http://localhost:3002' 
has been blocked by CORS policy
```

**Root Cause:** CORS not properly configured for development.

**Resolution Steps:**

1. **Configure CORS in Spring Boot:**
   ```java
   @Configuration
   public class WebConfig implements WebMvcConfigurer {
       @Override
       public void addCorsMappings(CorsRegistry registry) {
           registry.addMapping("/api/**")
                   .allowedOrigins("http://localhost:3002")
                   .allowedMethods("GET", "POST", "PUT", "DELETE")
                   .allowCredentials(true);
       }
   }
   ```

2. **Add CORS headers to controllers:**
   ```java
   @RestController
   @CrossOrigin(origins = "http://localhost:3002")
   public class ModuleController {
       // Controller methods
   }
   ```

3. **Configure proxy in Vite (development):**
   ```javascript
   // vite.config.js
   export default defineConfig({
     server: {
       proxy: {
         '/api': 'http://localhost:8080'
       }
     }
   });
   ```

---

## API Endpoint Errors

### Issue 1: 404 Not Found for Valid Endpoints

**Symptoms:**
```
HTTP 404 - Not Found
Request URL: http://localhost:8080/api/modules
```

**Root Cause:** Controller mapping issues or servlet context problems.

**Resolution Steps:**

1. **Verify controller mapping:**
   ```java
   @RestController
   @RequestMapping("/api/modules")  // Check this path
   public class ModuleController {
       
       @GetMapping  // This creates /api/modules endpoint
       public List<Module> getAllModules() {
           return moduleService.findAll();
       }
   }
   ```

2. **Check application context path:**
   ```yaml
   # In application.yml
   server:
     servlet:
       context-path: /  # Should be / for root context
   ```

3. **Verify endpoint registration:**
   ```bash
   # Check actuator endpoints
   curl http://localhost:8080/actuator/mappings
   ```

4. **Test with full path:**
   ```bash
   curl -v http://localhost:8080/api/modules
   ```

### Issue 2: 500 Internal Server Error

**Symptoms:**
```
HTTP 500 - Internal Server Error
```

**Root Cause:** Unhandled exceptions in controller or service layer.

**Resolution Steps:**

1. **Check application logs:**
   ```bash
   tail -f logs/learning-portal.log
   # Look for stack traces and error messages
   ```

2. **Add global exception handler:**
   ```java
   @RestControllerAdvice
   public class GlobalExceptionHandler {
       
       @ExceptionHandler(Exception.class)
       public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
           log.error("Unexpected error", e);
           return ResponseEntity.status(500)
                   .body(new ErrorResponse("Internal server error"));
       }
   }
   ```

3. **Add proper validation:**
   ```java
   @PostMapping
   public ResponseEntity<Module> createModule(@Valid @RequestBody ModuleDto dto) {
       // Validation will throw exception if invalid
       Module module = moduleService.create(dto);
       return ResponseEntity.ok(module);
   }
   ```

---

## Authentication and Authorization Issues

### Issue 1: JWT Token Validation Failure

**Symptoms:**
```
HTTP 401 - Unauthorized
Invalid JWT token
```

**Root Cause:** Token expiration, invalid signature, or configuration issues.

**Resolution Steps:**

1. **Check token expiration:**
   ```java
   // Decode JWT to check expiration
   Claims claims = Jwts.parser()
           .setSigningKey(secretKey)
           .parseClaimsJws(token)
           .getBody();
   Date expiration = claims.getExpiration();
   ```

2. **Verify JWT configuration:**
   ```yaml
   jwt:
     secret: ${JWT_SECRET:your-secret-key}
     expiration: 86400000  # 24 hours in milliseconds
   ```

3. **Check token format:**
   ```
   Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
   ```

4. **Validate signing key:**
   ```java
   @Value("${jwt.secret}")
   private String jwtSecret;
   
   // Ensure secret is consistent across restarts
   ```

---

## Caching Problems

### Issue 1: Cache Not Working - Always Hitting Database

**Symptoms:**
- Database queries executed on every request
- No cache hit logs
- Poor performance despite caching configuration

**Root Cause:** Cache not properly configured or cache keys not matching.

**Resolution Steps:**

1. **Verify cache configuration:**
   ```java
   @Configuration
   @EnableCaching
   public class CacheConfig {
       
       @Bean
       public CacheManager cacheManager() {
           return new ConcurrentMapCacheManager("modules", "topics");
       }
   }
   ```

2. **Check cache annotations:**
   ```java
   @Service
   public class ModuleService {
       
       @Cacheable(value = "modules", key = "#id")
       public Module findById(Long id) {
           return moduleRepository.findById(id).orElse(null);
       }
   }
   ```

3. **Enable cache logging:**
   ```yaml
   logging:
     level:
       org.springframework.cache: DEBUG
   ```

4. **Test cache manually:**
   ```java
   @Autowired
   private CacheManager cacheManager;
   
   public void testCache() {
       Cache cache = cacheManager.getCache("modules");
       cache.put("test", "value");
       String value = cache.get("test", String.class);
   }
   ```

---

## Logging and Monitoring Issues

### Issue 1: Logs Not Appearing in Files

**Symptoms:**
- Console shows logs but files are empty
- Log files not being created
- Missing log entries

**Root Cause:** Logback configuration issues or file permissions.

**Resolution Steps:**

1. **Check logback configuration:**
   ```xml
   <!-- In logback-spring.xml -->
   <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
       <file>logs/learning-portal.log</file>
       <!-- Ensure file path is writable -->
   </appender>
   ```

2. **Verify log directory permissions:**
   ```bash
   mkdir -p logs
   chmod 755 logs
   ```

3. **Check log levels:**
   ```yaml
   logging:
     level:
       com.learningportal: DEBUG
       root: INFO
   ```

4. **Test logging manually:**
   ```java
   private static final Logger log = LoggerFactory.getLogger(YourClass.class);
   
   public void testMethod() {
       log.info("Test log message");
       log.error("Test error message");
   }
   ```

---

## Environment Configuration Problems

### Issue 1: Environment Variables Not Loading

**Symptoms:**
- Default values used instead of environment variables
- Configuration not matching expected values
- Properties not being resolved

**Root Cause:** Environment variables not properly set or Spring not reading them.

**Resolution Steps:**

1. **Verify environment variables:**
   ```bash
   echo $DB_USERNAME
   echo $DB_PASSWORD
   ```

2. **Check Spring property resolution:**
   ```yaml
   spring:
     datasource:
       username: ${DB_USERNAME:defaultuser}
       password: ${DB_PASSWORD:defaultpass}
   ```

3. **Use .env file for development:**
   ```bash
   # Create .env file
   DB_USERNAME=devuser
   DB_PASSWORD=devpass
   
   # Load in application
   source .env
   mvn spring-boot:run
   ```

4. **Verify property loading:**
   ```java
   @Value("${DB_USERNAME}")
   private String dbUsername;
   
   @PostConstruct
   public void logConfig() {
       log.info("DB Username: {}", dbUsername);
   }
   ```

---

## Emergency Procedures

### Complete System Recovery

If the system is completely broken and won't start:

1. **Reset to known good state:**
   ```bash
   git stash  # Save current changes
   git checkout main
   git pull origin main
   ```

2. **Clean rebuild:**
   ```bash
   mvn clean
   rm -rf target/
   rm -rf frontend/node_modules/
   rm -rf frontend/dist/
   mvn clean install
   ```

3. **Reset database:**
   ```bash
   # For H2 (development)
   rm -f *.db
   
   # For PostgreSQL
   dropdb learning_portal_dev
   createdb learning_portal_dev
   ```

4. **Start fresh:**
   ```bash
   mvn spring-boot:run
   ```

### Getting Additional Help

If these solutions don't resolve your issue:

1. **Check application logs:** `logs/learning-portal.log`
2. **Enable debug logging:** Set `logging.level.com.learningportal=DEBUG`
3. **Use health endpoints:** `http://localhost:8080/actuator/health`
4. **Contact the development team:** Include logs and steps to reproduce
5. **Create a GitHub issue:** With detailed error information

Remember to always check the logs first - they usually contain the specific error information needed to diagnose and resolve issues quickly.