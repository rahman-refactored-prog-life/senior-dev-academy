# Configuration Guide

## Overview

This guide provides comprehensive documentation for all configuration options available in the Comprehensive Developer Portal application. The application uses Spring Boot's externalized configuration system, allowing you to configure the application through environment variables, property files, or command-line arguments.

## Table of Contents

1. [Configuration Profiles](#configuration-profiles)
2. [Environment Variables](#environment-variables)
3. [Database Configuration](#database-configuration)
4. [Security Configuration](#security-configuration)
5. [Cache Configuration](#cache-configuration)
6. [Logging Configuration](#logging-configuration)
7. [Performance Configuration](#performance-configuration)
8. [Feature Flags](#feature-flags)
9. [Configuration Validation](#configuration-validation)
10. [Troubleshooting](#troubleshooting)

---

## Configuration Profiles

The application supports three configuration profiles:

### Development Profile (`dev`)
- **Purpose**: Local development and testing
- **Database**: PostgreSQL (devportalkirostart)
- **Cache**: Caffeine (in-memory)
- **Logging**: DEBUG level with detailed SQL logging
- **Activation**: `SPRING_PROFILES_ACTIVE=dev`

### Test Profile (`test`)
- **Purpose**: Automated testing
- **Database**: H2 (in-memory)
- **Cache**: Simple (in-memory)
- **Logging**: INFO level
- **Activation**: `SPRING_PROFILES_ACTIVE=test`

### Production Profile (`production`)
- **Purpose**: Production deployment
- **Database**: PostgreSQL (devacademykiro)
- **Cache**: Redis (distributed)
- **Logging**: INFO/WARN level
- **Activation**: `SPRING_PROFILES_ACTIVE=production`

**How to activate a profile:**

```bash
# Using environment variable
export SPRING_PROFILES_ACTIVE=dev

# Using command-line argument
java -jar app.jar --spring.profiles.active=production

# Using Maven
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

---

## Environment Variables

### Quick Start

1. Copy the example environment file:
   ```bash
   cp .env.example .env
   ```

2. Edit `.env` with your configuration values

3. Load environment variables:
   ```bash
   # Linux/Mac
   source .env
   
   # Or use direnv (recommended)
   direnv allow
   ```

### All Available Environment Variables

#### Application Configuration

| Variable | Default | Description | Required |
|----------|---------|-------------|----------|
| `APP_NAME` | comprehensive-dev-portal | Application name | No |
| `SERVER_PORT` | 3008 | HTTP server port | No |
| `SERVER_CONTEXT_PATH` | /api | API context path | No |
| `SPRING_PROFILES_ACTIVE` | dev | Active configuration profile | No |

**Example:**
```bash
APP_NAME=my-learning-portal
SERVER_PORT=8080
SERVER_CONTEXT_PATH=/api/v1
SPRING_PROFILES_ACTIVE=dev
```

---

## Database Configuration

### PostgreSQL Configuration

#### Connection Settings

| Variable | Default | Description | Required |
|----------|---------|-------------|----------|
| `DB_URL` | jdbc:postgresql://localhost:5432/devportalkirostart | JDBC connection URL | No |
| `DB_DRIVER` | org.postgresql.Driver | JDBC driver class | No |
| `DB_USERNAME` | postgres | Database username | No |
| `DB_PASSWORD` | password | Database password | **Yes (Production)** |

**Development Example:**
```bash
DB_URL=jdbc:postgresql://localhost:5432/devportalkirostart
DB_USERNAME=postgres
DB_PASSWORD=mypassword
```

**Production Example:**
```bash
DB_URL=jdbc:postgresql://prod-db.example.com:5432/devacademykiro
DB_USERNAME=app_user
DB_PASSWORD=SecureProductionPassword123!
```

#### Connection Pool Settings (HikariCP)

| Variable | Default | Description |
|----------|---------|-------------|
| `DB_POOL_MAX_SIZE` | 20 | Maximum pool size |
| `DB_POOL_MIN_IDLE` | 5 | Minimum idle connections |
| `DB_POOL_IDLE_TIMEOUT` | 300000 | Idle timeout (ms) |
| `DB_POOL_CONNECTION_TIMEOUT` | 20000 | Connection timeout (ms) |
| `DB_POOL_MAX_LIFETIME` | 1200000 | Max connection lifetime (ms) |
| `DB_POOL_LEAK_DETECTION` | 60000 | Leak detection threshold (ms) |

**Tuning Recommendations:**

- **Small applications**: `DB_POOL_MAX_SIZE=10`
- **Medium applications**: `DB_POOL_MAX_SIZE=20` (default)
- **Large applications**: `DB_POOL_MAX_SIZE=50`

**Example:**
```bash
DB_POOL_MAX_SIZE=30
DB_POOL_MIN_IDLE=10
DB_POOL_CONNECTION_TIMEOUT=30000
```

#### JPA/Hibernate Settings

| Variable | Default | Description |
|----------|---------|-------------|
| `JPA_DDL_AUTO` | create-drop (dev), validate (prod) | Schema generation strategy |
| `JPA_SHOW_SQL` | true (dev), false (prod) | Show SQL in logs |
| `JPA_FORMAT_SQL` | true (dev), false (prod) | Format SQL in logs |
| `JPA_BATCH_SIZE` | 25 | JDBC batch size |
| `JPA_FETCH_SIZE` | 16 | Default fetch size |

**DDL Auto Options:**
- `create-drop`: Create schema on startup, drop on shutdown (development only)
- `create`: Create schema on startup (development only)
- `update`: Update schema if needed (use with caution)
- `validate`: Validate schema matches entities (production)
- `none`: No schema management

**Example:**
```bash
JPA_DDL_AUTO=validate
JPA_SHOW_SQL=false
JPA_BATCH_SIZE=50
```

---

## Security Configuration

### Basic Authentication

| Variable | Default | Description | Required |
|----------|---------|-------------|----------|
| `SECURITY_USER_NAME` | admin | Admin username | **Yes (Production)** |
| `SECURITY_USER_PASSWORD` | admin123 | Admin password | **Yes (Production)** |
| `SECURITY_USER_ROLES` | ADMIN | User roles | No |

**⚠️ Production Security Requirements:**
- Username must NOT be "admin"
- Password must be at least 12 characters
- Password must include uppercase, lowercase, numbers, and special characters

**Example:**
```bash
SECURITY_USER_NAME=portal_admin
SECURITY_USER_PASSWORD=MySecureP@ssw0rd2024!
SECURITY_USER_ROLES=ADMIN,USER
```

### JWT Configuration

| Variable | Default | Description | Required |
|----------|---------|-------------|----------|
| `JWT_SECRET` | - | JWT signing secret | **Yes (Production)** |
| `JWT_EXPIRATION` | 86400000 | Token expiration (ms, 24h) | No |

**JWT Secret Requirements:**
- Minimum 32 characters
- Use cryptographically secure random string
- Never commit to version control

**Generate secure JWT secret:**
```bash
# Linux/Mac
openssl rand -base64 32

# Or use online generator (for development only)
```

**Example:**
```bash
JWT_SECRET=YourVeryLongAndSecureRandomStringHere123456789
JWT_EXPIRATION=3600000  # 1 hour
```

---

## Cache Configuration

### Cache Type

| Variable | Default | Description |
|----------|---------|-------------|
| `CACHE_TYPE` | caffeine (dev), redis (prod) | Cache implementation |
| `CACHE_SPEC` | maximumSize=1000,expireAfterWrite=10m | Caffeine cache spec |
| `CACHE_TTL` | 600000 | Redis TTL (ms, 10 min) |

### Caffeine Cache (Development)

**Example:**
```bash
CACHE_TYPE=caffeine
CACHE_SPEC=maximumSize=2000,expireAfterWrite=15m,expireAfterAccess=5m
```

**Spec Options:**
- `maximumSize`: Maximum number of entries
- `expireAfterWrite`: Expire after write time
- `expireAfterAccess`: Expire after last access
- `recordStats`: Enable statistics

### Redis Cache (Production)

| Variable | Default | Description |
|----------|---------|-------------|
| `REDIS_HOST` | localhost | Redis server host |
| `REDIS_PORT` | 6379 | Redis server port |
| `REDIS_PASSWORD` | - | Redis password |
| `REDIS_TIMEOUT` | 2000 | Connection timeout (ms) |
| `REDIS_POOL_MAX_ACTIVE` | 8 | Max active connections |
| `REDIS_POOL_MAX_IDLE` | 8 | Max idle connections |
| `REDIS_POOL_MIN_IDLE` | 0 | Min idle connections |

**Example:**
```bash
CACHE_TYPE=redis
REDIS_HOST=redis.example.com
REDIS_PORT=6379
REDIS_PASSWORD=SecureRedisPassword
REDIS_TIMEOUT=5000
REDIS_POOL_MAX_ACTIVE=16
```

---

## Logging Configuration

### Log Levels

| Variable | Default | Description |
|----------|---------|-------------|
| `LOGGING_LEVEL_ROOT` | INFO | Root logger level |
| `LOGGING_LEVEL_APP` | DEBUG (dev), INFO (prod) | Application logger level |
| `LOGGING_LEVEL_SECURITY` | DEBUG (dev), WARN (prod) | Security logger level |
| `LOGGING_LEVEL_SQL` | DEBUG (dev), WARN (prod) | SQL logger level |
| `LOGGING_LEVEL_SQL_PARAMS` | TRACE (dev), WARN (prod) | SQL parameter logger level |

**Log Level Options:**
- `TRACE`: Most detailed
- `DEBUG`: Detailed debugging information
- `INFO`: General information
- `WARN`: Warning messages
- `ERROR`: Error messages
- `OFF`: Disable logging

**Development Example:**
```bash
LOGGING_LEVEL_ROOT=INFO
LOGGING_LEVEL_APP=DEBUG
LOGGING_LEVEL_SQL=DEBUG
LOGGING_LEVEL_SQL_PARAMS=TRACE
```

**Production Example:**
```bash
LOGGING_LEVEL_ROOT=WARN
LOGGING_LEVEL_APP=INFO
LOGGING_LEVEL_SECURITY=WARN
LOGGING_LEVEL_SQL=WARN
```

### Log File Configuration

| Variable | Default | Description |
|----------|---------|-------------|
| `LOGGING_FILE` | logs/application.log | Log file path |
| `LOGGING_FILE_MAX_SIZE` | 10MB | Max log file size |
| `LOGGING_FILE_MAX_HISTORY` | 30 | Max log file history (days) |

**Example:**
```bash
LOGGING_FILE=/var/log/learning-portal/app.log
LOGGING_FILE_MAX_SIZE=50MB
LOGGING_FILE_MAX_HISTORY=90
```

---

## Performance Configuration

### Monitoring Settings

| Variable | Default | Description |
|----------|---------|-------------|
| `PERFORMANCE_MONITORING_ENABLED` | true | Enable performance monitoring |
| `PERFORMANCE_SLOW_QUERY_THRESHOLD` | 1000 | Slow query threshold (ms) |
| `PERFORMANCE_REQUEST_TIMEOUT` | 30000 | Request timeout (ms) |

**Example:**
```bash
PERFORMANCE_MONITORING_ENABLED=true
PERFORMANCE_SLOW_QUERY_THRESHOLD=500
PERFORMANCE_REQUEST_TIMEOUT=60000
```

### Actuator/Monitoring

| Variable | Default | Description |
|----------|---------|-------------|
| `MANAGEMENT_ENDPOINTS_ENABLED` | true | Enable actuator endpoints |
| `MANAGEMENT_HEALTH_SHOW_DETAILS` | always (dev), when-authorized (prod) | Health details visibility |

**Available Actuator Endpoints:**
- `/actuator/health` - Application health
- `/actuator/info` - Application info
- `/actuator/metrics` - Application metrics
- `/actuator/prometheus` - Prometheus metrics

---

## Feature Flags

| Variable | Default | Description |
|----------|---------|-------------|
| `FEATURE_CODE_EXECUTION` | true | Enable code execution feature |
| `FEATURE_AI_ASSISTANCE` | true | Enable AI assistance |
| `FEATURE_PROGRESS_ANALYTICS` | true | Enable progress analytics |
| `FEATURE_SOCIAL_FEATURES` | false | Enable social features |

**Example:**
```bash
FEATURE_CODE_EXECUTION=true
FEATURE_AI_ASSISTANCE=false
FEATURE_PROGRESS_ANALYTICS=true
FEATURE_SOCIAL_FEATURES=true
```

---

## Configuration Validation

The application includes automatic configuration validation that runs at startup. It checks:

1. **Required Configuration**: All required environment variables are set
2. **Valid Values**: Configuration values are within acceptable ranges
3. **Security**: Production security requirements are met
4. **Database**: Database connection is valid
5. **Performance**: Performance settings are appropriate for the environment

### Validation Errors

If validation fails, the application will:
1. Log detailed error messages
2. Fail to start
3. Provide clear instructions for fixing the issues

**Example Error:**
```
CONFIGURATION ERRORS DETECTED (2 errors):
1. Database password is REQUIRED in production. Set DB_PASSWORD environment variable.
2. Security password is REQUIRED in production. Set SECURITY_USER_PASSWORD environment variable.
```

### Validation Warnings

Warnings indicate potential issues but don't prevent startup:

**Example Warning:**
```
CONFIGURATION WARNINGS (1 warnings):
1. Using in-memory cache (Caffeine) in production. Consider using Redis for distributed caching.
```

---

## Troubleshooting

### Common Issues

#### 1. Application Won't Start

**Symptom**: Application fails to start with configuration errors

**Solution**:
```bash
# Check configuration validation errors in logs
tail -f logs/application.log

# Verify all required environment variables are set
env | grep -E "DB_|SECURITY_|SERVER_"

# Test database connection
psql -h localhost -U postgres -d devportalkirostart
```

#### 2. Database Connection Fails

**Symptom**: `Connection refused` or `Authentication failed`

**Solution**:
```bash
# Verify PostgreSQL is running
sudo systemctl status postgresql

# Check connection settings
echo $DB_URL
echo $DB_USERNAME

# Test connection manually
psql -h localhost -U postgres -d devportalkirostart

# Check firewall rules
sudo ufw status
```

#### 3. Port Already in Use

**Symptom**: `Port 3008 is already in use`

**Solution**:
```bash
# Find process using the port
lsof -i :3008

# Kill the process
kill -9 <PID>

# Or use a different port
export SERVER_PORT=3009
```

#### 4. Configuration Not Loading

**Symptom**: Application uses default values instead of environment variables

**Solution**:
```bash
# Verify environment variables are exported
export DB_PASSWORD=mypassword

# Check if .env file is loaded
source .env

# Verify Spring profile is active
echo $SPRING_PROFILES_ACTIVE

# Check application logs for loaded configuration
grep "Active Profile" logs/application.log
```

#### 5. Cache Issues

**Symptom**: Stale data or cache errors

**Solution**:
```bash
# Clear Caffeine cache (restart application)
# Or clear Redis cache
redis-cli FLUSHALL

# Verify cache configuration
curl http://localhost:3008/api/actuator/metrics/cache.size
```

### Getting Help

If you encounter issues not covered here:

1. Check application logs: `logs/application.log`
2. Enable DEBUG logging: `LOGGING_LEVEL_APP=DEBUG`
3. Review configuration validation output
4. Check Spring Boot documentation: https://docs.spring.io/spring-boot/docs/current/reference/html/
5. Review project README.md for setup instructions

---

## Configuration Examples

### Minimal Development Setup

```bash
# .env
SPRING_PROFILES_ACTIVE=dev
DB_PASSWORD=postgres
```

### Complete Development Setup

```bash
# .env
APP_NAME=my-learning-portal
SPRING_PROFILES_ACTIVE=dev
SERVER_PORT=3008

DB_URL=jdbc:postgresql://localhost:5432/devportalkirostart
DB_USERNAME=postgres
DB_PASSWORD=mypassword
DB_POOL_MAX_SIZE=20

SECURITY_USER_NAME=admin
SECURITY_USER_PASSWORD=admin123

LOGGING_LEVEL_APP=DEBUG
LOGGING_LEVEL_SQL=DEBUG

FEATURE_CODE_EXECUTION=true
FEATURE_AI_ASSISTANCE=true
```

### Production Setup

```bash
# .env (or set via deployment platform)
APP_NAME=senior-dev-academy
SPRING_PROFILES_ACTIVE=production
SERVER_PORT=8080

DB_URL=jdbc:postgresql://prod-db.example.com:5432/devacademykiro
DB_USERNAME=app_user
DB_PASSWORD=SecureProductionPassword123!
DB_POOL_MAX_SIZE=50
DB_POOL_MIN_IDLE=10

SECURITY_USER_NAME=portal_admin
SECURITY_USER_PASSWORD=MySecureP@ssw0rd2024!
JWT_SECRET=YourVeryLongAndSecureRandomStringHere123456789

CACHE_TYPE=redis
REDIS_HOST=redis.example.com
REDIS_PORT=6379
REDIS_PASSWORD=SecureRedisPassword

LOGGING_LEVEL_ROOT=WARN
LOGGING_LEVEL_APP=INFO
LOGGING_FILE=/var/log/learning-portal/app.log

PERFORMANCE_MONITORING_ENABLED=true
PERFORMANCE_SLOW_QUERY_THRESHOLD=500

FEATURE_CODE_EXECUTION=true
FEATURE_AI_ASSISTANCE=true
FEATURE_PROGRESS_ANALYTICS=true
```

---

## Best Practices

1. **Never commit sensitive data**: Use `.env` files (gitignored) or environment variables
2. **Use strong passwords**: Minimum 12 characters with mixed case, numbers, and symbols
3. **Validate configuration**: Review validation output on every deployment
4. **Monitor performance**: Enable performance monitoring in production
5. **Use appropriate log levels**: DEBUG for development, INFO/WARN for production
6. **Configure connection pools**: Tune based on expected load
7. **Enable caching**: Use Redis in production for distributed caching
8. **Set timeouts**: Configure appropriate timeouts for your use case
9. **Use profiles**: Separate configuration for dev, test, and production
10. **Document changes**: Update this guide when adding new configuration options

---

## Configuration Reference

For the complete list of Spring Boot configuration properties, see:
- [Spring Boot Common Application Properties](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html)
- [Spring Boot Externalized Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config)
