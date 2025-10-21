# Configuration Documentation

*Generated on Tue Oct 21 16:16:48 CDT 2025*

## Configuration Files

### application-production.yml

**File:** ``

```yaml
# Production Profile Configuration
# Activated with: --spring.profiles.active=production or SPRING_PROFILES_ACTIVE=production
# All sensitive values MUST be provided via environment variables in production

spring:
  application:
    name: ${APP_NAME:senior-dev-academy}
  
  # PostgreSQL Database Configuration - Fully Externalized
  datasource:
    url: ${DB_URL:jdbc:postgresql://localhost:5432/devacademykiro}
    username: ${DB_USERNAME:postgres}
    password: ${DB_PASSWORD}  # REQUIRED: Must be set via environment variable
    driver-class-name: ${DB_DRIVER:org.postgresql.Driver}
    
    # Connection Pool Configuration (HikariCP) - Externalized
    hikari:
      maximum-pool-size: ${DB_POOL_MAX_SIZE:20}
      minimum-idle: ${DB_POOL_MIN_IDLE:5}
      idle-timeout: ${DB_POOL_IDLE_TIMEOUT:300000}
      connection-timeout: ${DB_POOL_CONNECTION_TIMEOUT:20000}
      max-lifetime: ${DB_POOL_MAX_LIFETIME:1200000}
      leak-detection-threshold: ${DB_POOL_LEAK_DETECTION:60000}
      pool-name: "HikariCP-DevPortal-Prod"
      
  # JPA/Hibernate Configuration for PostgreSQL
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: ${JPA_DDL_AUTO:validate}  # Use validate in production, never create-drop
    show-sql: ${JPA_SHOW_SQL:false}  # Disable SQL logging in production
    properties:
      hibernate:
        format_sql: false
        use_sql_comments: false
        # Enable batch processing for better performance
        jdbc:
          batch_size: 25
        order_inserts: true
        order_updates: true
        # Enable second-level cache
        cache:
          use_second_level_cache: true
          use_query_cache: true
          region:
            factory_class: org.hibernate.cache.jcache.JCacheRegionFactory
            
  # Security Configuration - REQUIRED environment variables
  security:
    user:
      name: ${SECURITY_USER_NAME}  # REQUIRED: Must be set via environment variable
      password: ${SECURITY_USER_PASSWORD}  # REQUIRED: Must be set via environment variable
      roles: ${SECURITY_USER_ROLES:ADMIN}
      
  # Cache Configuration (Redis in production) - Externalized
  cache:
    type: ${CACHE_TYPE:redis}
    redis:
      time-to-live: ${CACHE_TTL:600000}  # 10 minutes default
      
  # Redis Configuration - Externalized
  redis:
    host: ${REDIS_HOST:localhost}
    port: ${REDIS_PORT:6379}
    password: ${REDIS_PASSWORD:}
    timeout: ${REDIS_TIMEOUT:2000ms}
    jedis:
      pool:
        max-active: ${REDIS_POOL_MAX_ACTIVE:8}
        max-idle: ${REDIS_POOL_MAX_IDLE:8}
        min-idle: ${REDIS_POOL_MIN_IDLE:0}

# Server Configuration
server:
  port: ${SERVER_PORT:3008}
  servlet:
    context-path: /api
  # Enable compression
  compression:
    enabled: true
    mime-types: text/html,text/xml,text/plain,text/css,text/javascript,application/javascript,application/json
    min-response-size: 1024
  # SSL Configuration (uncomment for HTTPS)
  # ssl:
  #   key-store: classpath:keystore.p12
  #   key-store-password: ${SSL_PASSWORD:password}
  #   key-store-type: PKCS12
  #   key-alias: tomcat
    
# Actuator Configuration (Production monitoring)
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: when-authorized
  metrics:
    export:
      prometheus:
        enabled: true
        
# Logging Configuration for Production - Externalized
logging:
  level:
    root: ${LOGGING_LEVEL_ROOT:INFO}
    com.learningportal: ${LOGGING_LEVEL_APP:INFO}
    org.springframework.security: ${LOGGING_LEVEL_SECURITY:WARN}
    org.hibernate.SQL: ${LOGGING_LEVEL_SQL:WARN}
    org.hibernate.type.descriptor.sql.BasicBinder: ${LOGGING_LEVEL_SQL_PARAMS:WARN}
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
  file:
    name: ${LOGGING_FILE:logs/senior-dev-academy.log}
    max-size: ${LOGGING_FILE_MAX_SIZE:10MB}
    max-history: ${LOGGING_FILE_MAX_HISTORY:30}
    
# Custom Application Properties
app:
  name: Senior Developer Academy
  version: 1.0.0
  description: The most comprehensive learning portal for senior developer preparation
  
  # Security settings - Externalized
  security:
    jwt:
      secret: ${JWT_SECRET}  # REQUIRED: Must be set via environment variable
      expiration: ${JWT_EXPIRATION:86400000}  # 24 hours default
      
  # File upload settings - Externalized
  upload:
    max-file-size: ${UPLOAD_MAX_FILE_SIZE:10MB}
    max-request-size: ${UPLOAD_MAX_REQUEST_SIZE:50MB}
    upload-dir: ${UPLOAD_DIR:./uploads}
    
  # Email configuration (for notifications) - Externalized
  mail:
    host: ${MAIL_HOST:smtp.gmail.com}
    port: ${MAIL_PORT:587}
    username: ${MAIL_USERNAME}  # REQUIRED if email enabled
    password: ${MAIL_PASSWORD}  # REQUIRED if email enabled
    
  # External API configurations - Externalized
  external-apis:
    github:
      token: ${GITHUB_TOKEN:}
    stackoverflow:
      key: ${STACKOVERFLOW_KEY:}
      
  # Feature flags - Externalized
  features:
    code-execution: ${FEATURE_CODE_EXECUTION:true}
    ai-assistance: ${FEATURE_AI_ASSISTANCE:true}
    progress-analytics: ${FEATURE_PROGRESS_ANALYTICS:true}
    social-features: ${FEATURE_SOCIAL_FEATURES:false}
    
  # Performance settings - Externalized
  performance:
    monitoring-enabled: ${PERFORMANCE_MONITORING_ENABLED:true}
    slow-query-threshold: ${PERFORMANCE_SLOW_QUERY_THRESHOLD:1000}
    request-timeout: ${PERFORMANCE_REQUEST_TIMEOUT:30000}```

### application-test.yml

**File:** ``

```yaml
# Test Configuration Profile
# Optimized for fast test execution and isolated test environment

spring:
  # Database Configuration for Tests
  datasource:
    url: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
    driver-class-name: org.h2.Driver
    username: sa
    password: password
    
  # JPA Configuration for Tests
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: false
    properties:
      hibernate:
        format_sql: false
        dialect: org.hibernate.dialect.H2Dialect
        
  # H2 Console (disabled in tests)
  h2:
    console:
      enabled: false
      
  # Flyway (disabled for tests - using create-drop)
  flyway:
    enabled: false
    
  # Cache Configuration (simple for tests)
  cache:
    type: simple
    
  # Security Configuration for Tests
  security:
    user:
      name: testuser
      password: testpass
      roles: USER,ADMIN
      
# Logging Configuration for Tests
logging:
  level:
    com.learningportal: INFO
    org.springframework.web: WARN
    org.springframework.security: WARN
    org.hibernate: WARN
    org.springframework.test: INFO
  pattern:
    console: "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
    
# Actuator Configuration for Tests
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
  endpoint:
    health:
      show-details: always
      
# Test-specific Configuration
test:
  database:
    cleanup: true
  performance:
    timeout: 30000
  coverage:
    minimum: 80```

### application-dev.yml

**File:** ``

```yaml
# Development Profile Configuration
# Activated with: --spring.profiles.active=dev or SPRING_PROFILES_ACTIVE=dev

spring:
  application:
    name: ${APP_NAME:comprehensive-dev-portal}
  
  # Database Configuration - PostgreSQL for Development
  datasource:
    url: ${DB_URL:jdbc:postgresql://localhost:5432/devportalkirostart}
    driverClassName: ${DB_DRIVER:org.postgresql.Driver}
    username: ${DB_USERNAME:postgres}
    password: ${DB_PASSWORD:password}
    
    # Connection Pool Configuration (HikariCP)
    hikari:
      maximum-pool-size: ${DB_POOL_MAX_SIZE:20}
      minimum-idle: ${DB_POOL_MIN_IDLE:5}
      idle-timeout: ${DB_POOL_IDLE_TIMEOUT:300000}
      connection-timeout: ${DB_POOL_CONNECTION_TIMEOUT:20000}
      max-lifetime: ${DB_POOL_MAX_LIFETIME:1200000}
      leak-detection-threshold: ${DB_POOL_LEAK_DETECTION:60000}
      pool-name: "HikariCP-DevPortal-Dev"
      connection-test-query: "SELECT 1"
    
  # JPA/Hibernate Configuration
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: ${JPA_DDL_AUTO:create-drop}
    show-sql: ${JPA_SHOW_SQL:true}
    format-sql: ${JPA_FORMAT_SQL:true}
    properties:
      hibernate:
        format_sql: true
        use_sql_comments: true
        jdbc:
          batch_size: ${JPA_BATCH_SIZE:25}
          batch_versioned_data: true
        order_inserts: true
        order_updates: true
        default_batch_fetch_size: ${JPA_FETCH_SIZE:16}
        connection:
          provider_disables_autocommit: true
        enable_lazy_load_no_trans: false
        generate_statistics: true
      
  # Security Configuration
  security:
    user:
      name: ${SECURITY_USER_NAME:admin}
      password: ${SECURITY_USER_PASSWORD:admin123}
      roles: ${SECURITY_USER_ROLES:ADMIN}
      
  # Cache Configuration
  cache:
    type: ${CACHE_TYPE:caffeine}
    caffeine:
      spec: ${CACHE_SPEC:maximumSize=1000,expireAfterWrite=10m,expireAfterAccess=5m}
    cache-names:
      - learningModules
      - learningStats
      - userProgress
      - interviewQuestions
    
  # Development Tools
  devtools:
    restart:
      enabled: true
    livereload:
      enabled: true

# Server Configuration
server:
  port: ${SERVER_PORT:3008}
  servlet:
    context-path: ${SERVER_CONTEXT_PATH:/api}
    
# Actuator Configuration
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus,env
  endpoint:
    health:
      show-details: ${MANAGEMENT_HEALTH_SHOW_DETAILS:always}
    metrics:
      enabled: true
    prometheus:
      enabled: true
  metrics:
    export:
      prometheus:
        enabled: true
      
# Logging Configuration
logging:
  level:
    root: ${LOGGING_LEVEL_ROOT:INFO}
    com.learningportal: ${LOGGING_LEVEL_APP:DEBUG}
    org.springframework.security: ${LOGGING_LEVEL_SECURITY:DEBUG}
    org.hibernate.SQL: ${LOGGING_LEVEL_SQL:DEBUG}
    org.hibernate.type.descriptor.sql.BasicBinder: ${LOGGING_LEVEL_SQL_PARAMS:TRACE}
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
  file:
    name: ${LOGGING_FILE:logs/dev-application.log}
    max-size: ${LOGGING_FILE_MAX_SIZE:10MB}
    max-history: ${LOGGING_FILE_MAX_HISTORY:30}
    
# Custom Application Properties
app:
  name: Comprehensive Developer Portal
  version: 1.0.0
  description: The most comprehensive learning portal for Java, Spring, React, and System Design
  environment: development
  
  # Performance Monitoring
  performance:
    monitoring-enabled: ${PERFORMANCE_MONITORING_ENABLED:true}
    slow-query-threshold: ${PERFORMANCE_SLOW_QUERY_THRESHOLD:1000}
    request-timeout: ${PERFORMANCE_REQUEST_TIMEOUT:30000}
  
  # Feature Flags
  features:
    code-execution: ${FEATURE_CODE_EXECUTION:true}
    ai-assistance: ${FEATURE_AI_ASSISTANCE:true}
    progress-analytics: ${FEATURE_PROGRESS_ANALYTICS:true}
    social-features: ${FEATURE_SOCIAL_FEATURES:false}
```

### application.yml

**File:** ``

```yaml
# Spring Boot Configuration - Development Profile (Default)
# This file demonstrates YAML configuration with externalized environment-specific values
# All sensitive and environment-specific values use ${ENV_VAR:default_value} pattern

spring:
  application:
    name: ${APP_NAME:comprehensive-dev-portal}
  
  # Database Configuration - Externalized for environment-specific values
  datasource:
    # PostgreSQL Database - Uses environment variables with sensible defaults
    url: ${DB_URL:jdbc:postgresql://localhost:5432/devportalkirostart}
    driverClassName: ${DB_DRIVER:org.postgresql.Driver}
    username: ${DB_USERNAME:postgres}
    password: ${DB_PASSWORD:password}
    
    # Connection Pool Configuration (HikariCP) - Optimized for Performance
    hikari:
      maximum-pool-size: 20          # Increased for better concurrency
      minimum-idle: 5               # Higher minimum for faster response
      idle-timeout: 300000          # 5 minutes
      connection-timeout: 20000     # 20 seconds
      max-lifetime: 1200000         # 20 minutes
      leak-detection-threshold: 60000 # 1 minute
      pool-name: "HikariCP-DevPortal"
      connection-test-query: "SELECT 1"
    
  # JPA/Hibernate Configuration - Optimized for Performance
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: create-drop  # Creates schema on startup, drops on shutdown
    show-sql: true  # Shows SQL queries in console (great for learning!)
    format-sql: true
    properties:
      hibernate:
        format_sql: true
        use_sql_comments: true
        # Performance optimizations
        jdbc:
          batch_size: 25
          batch_versioned_data: true
        order_inserts: true
        order_updates: true
        # Query optimization
        default_batch_fetch_size: 16
        # Connection and caching
        connection:
          provider_disables_autocommit: true
        # Lazy loading optimization
        enable_lazy_load_no_trans: false
        # Statistics for monitoring
        generate_statistics: true
      
  # Security Configuration - Externalized credentials
  security:
    user:
      name: ${SECURITY_USER_NAME:admin}
      password: ${SECURITY_USER_PASSWORD:admin123}
      roles: ${SECURITY_USER_ROLES:ADMIN}
      
  # Cache Configuration - Enhanced for Performance
  cache:
    type: caffeine
    caffeine:
      spec: maximumSize=1000,expireAfterWrite=10m,expireAfterAccess=5m
    cache-names:
      - learningModules
      - learningStats
      - userProgress
      - interviewQuestions
    
  # Development Tools
  devtools:
    restart:
      enabled: true
    livereload:
      enabled: true

# Server Configuration - REST API Server (Externalized)
server:
  port: ${SERVER_PORT:3008}
  servlet:
    context-path: ${SERVER_CONTEXT_PATH:/api}
    
# Actuator Configuration (for monitoring and health checks)
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    metrics:
      enabled: true
    prometheus:
      enabled: true
  metrics:
    export:
      prometheus:
        enabled: true,metrics,env
  endpoint:
    health:
      show-details: always
      
# Logging Configuration - Externalized log levels
logging:
  level:
    root: ${LOGGING_LEVEL_ROOT:INFO}
    com.learningportal: ${LOGGING_LEVEL_APP:DEBUG}
    org.springframework.security: ${LOGGING_LEVEL_SECURITY:DEBUG}
    org.hibernate.SQL: ${LOGGING_LEVEL_SQL:DEBUG}
    org.hibernate.type.descriptor.sql.BasicBinder: ${LOGGING_LEVEL_SQL_PARAMS:TRACE}
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
  file:
    name: ${LOGGING_FILE:logs/application.log}
    max-size: ${LOGGING_FILE_MAX_SIZE:10MB}
    max-history: ${LOGGING_FILE_MAX_HISTORY:30}
    
# Custom Application Properties
app:
  name: Comprehensive Developer Portal
  version: 1.0.0
  description: The most comprehensive learning portal for Java, Spring, React, and System Design
  features:
    - Java Fundamentals to Advanced
    - Spring Framework Ecosystem
    - React Development
    - Data Structures & Algorithms
    - System Design
    - Interview Preparation
    - Interactive Code Editor
    - Progress Tracking```

### application.properties

**File:** ``

```yaml
# PostgreSQL Database Configuration for devacademykiro
# You can override these values by setting environment variables

# Database Connection
spring.datasource.url=jdbc:postgresql://localhost:5432/devacademykirostart
spring.datasource.username=postgres
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate Configuration
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.use_sql_comments=true

# Connection Pool (HikariCP)
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=2
spring.datasource.hikari.idle-timeout=300000
spring.datasource.hikari.connection-timeout=20000
spring.datasource.hikari.leak-detection-threshold=60000

# Server Configuration
server.port=3008

# Logging
logging.level.com.learningportal=DEBUG
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE

# Application Info
spring.application.name=faang-mastery-portal```

