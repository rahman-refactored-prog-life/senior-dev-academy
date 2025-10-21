# Troubleshooting Guide

## Overview

This guide provides solutions to common issues encountered when setting up and running the Comprehensive Developer Portal. Issues are organized by category for easy navigation.

## Table of Contents

1. [Installation Issues](#installation-issues)
2. [Build Issues](#build-issues)
3. [Database Issues](#database-issues)
4. [Runtime Issues](#runtime-issues)
5. [Configuration Issues](#configuration-issues)
6. [Performance Issues](#performance-issues)
7. [IDE-Specific Issues](#ide-specific-issues)
8. [Platform-Specific Issues](#platform-specific-issues)

---

## Installation Issues

### Java Installation Problems

#### Issue: Wrong Java Version

**Symptoms:**
```
Unsupported class file major version 65
source value 21 is obsolete
```

**Solution:**
```bash
# Check current Java version
java -version

# macOS - Install correct version
brew install openjdk@21
brew link --force openjdk@21

# Set JAVA_HOME
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
echo 'export JAVA_HOME=$(/usr/libexec/java_home -v 21)' >> ~/.zshrc

# Linux - Install correct version
sudo apt install openjdk-21-jdk
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
echo 'export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64' >> ~/.bashrc

# Verify
java -version
echo $JAVA_HOME
```

#### Issue: JAVA_HOME Not Set

**Symptoms:**
```
JAVA_HOME is not set
Cannot find Java
```

**Solution:**
```bash
# Find Java installation
# macOS
/usr/libexec/java_home -V

# Linux
update-alternatives --config java

# Set JAVA_HOME permanently
# macOS (zsh)
echo 'export JAVA_HOME=$(/usr/libexec/java_home -v 21)' >> ~/.zshrc
source ~/.zshrc

# Linux (bash)
echo 'export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64' >> ~/.bashrc
source ~/.bashrc

# Windows
# System Properties → Environment Variables → New
# Variable: JAVA_HOME
# Value: C:\Program Files\Eclipse Adoptium\jdk-21.x.x
```

### Maven Installation Problems

#### Issue: Maven Not Found

**Symptoms:**
```
mvn: command not found
```

**Solution:**
```bash
# macOS
brew install maven

# Linux
sudo apt install maven

# Windows
# Download from https://maven.apache.org/download.cgi
# Extract to C:\Program Files\Apache\maven
# Add to PATH: C:\Program Files\Apache\maven\bin

# Verify
mvn -version
```

#### Issue: Maven Uses Wrong Java Version

**Symptoms:**
```
Maven is using Java 11 instead of Java 21
```

**Solution:**
```bash
# Set JAVA_HOME before running Maven
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
mvn -version

# Or create ~/.mavenrc
echo "JAVA_HOME=$(/usr/libexec/java_home -v 21)" > ~/.mavenrc
```

### PostgreSQL Installation Problems

#### Issue: PostgreSQL Service Won't Start

**Symptoms:**
```
could not connect to server
Connection refused
```

**Solution:**
```bash
# macOS
brew services start postgresql@15
brew services list

# Linux
sudo systemctl start postgresql
sudo systemctl status postgresql
sudo systemctl enable postgresql

# Windows
# Services → postgresql-x64-15 → Start
# Or: net start postgresql-x64-15

# Check if running
pg_isready
```

#### Issue: PostgreSQL Port Conflict

**Symptoms:**
```
Port 5432 is already in use
```

**Solution:**
```bash
# Find process using port 5432
# macOS/Linux
lsof -i :5432

# Windows
netstat -ano | findstr :5432

# Kill the process or change PostgreSQL port
# Edit postgresql.conf
# port = 5433

# Update DB_URL in .env
DB_URL=jdbc:postgresql://localhost:5433/devportalkirostart
```

---

## Build Issues

### Maven Build Failures

#### Issue: Compilation Errors

**Symptoms:**
```
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin
Compilation failure
```

**Solution:**
```bash
# Clean and rebuild
mvn clean compile

# Force update dependencies
mvn clean install -U

# Skip tests temporarily
mvn clean install -DskipTests

# Check for specific errors
mvn clean compile -X  # Debug mode
```

#### Issue: Dependency Resolution Failures

**Symptoms:**
```
Could not resolve dependencies
Failed to collect dependencies
```

**Solution:**
```bash
# Clear Maven cache
rm -rf ~/.m2/repository

# Force update
mvn clean install -U

# Check Maven settings
cat ~/.m2/settings.xml

# Use Maven Central explicitly
mvn clean install -Dmaven.repo.local=~/.m2/repository
```

#### Issue: Out of Memory During Build

**Symptoms:**
```
Java heap space
OutOfMemoryError
```

**Solution:**
```bash
# Increase Maven memory
export MAVEN_OPTS="-Xmx2048m -XX:MaxPermSize=512m"

# Or create ~/.mavenrc
echo "MAVEN_OPTS=-Xmx2048m" > ~/.mavenrc

# Verify
echo $MAVEN_OPTS
mvn -version
```

### Frontend Build Failures

#### Issue: npm install Fails

**Symptoms:**
```
npm ERR! code EACCES
npm ERR! permission denied
```

**Solution:**
```bash
# Fix npm permissions (don't use sudo)
mkdir ~/.npm-global
npm config set prefix '~/.npm-global'
echo 'export PATH=~/.npm-global/bin:$PATH' >> ~/.bashrc
source ~/.bashrc

# Or reinstall Node.js with nvm
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.0/install.sh | bash
nvm install 20
nvm use 20
```

#### Issue: Frontend Build Timeout

**Symptoms:**
```
npm ERR! network timeout
```

**Solution:**
```bash
# Increase npm timeout
npm config set fetch-timeout 60000
npm config set fetch-retries 5

# Use different registry
npm config set registry https://registry.npmjs.org/

# Clear npm cache
npm cache clean --force

# Retry
cd frontend
npm install
```

---

## Database Issues

### Connection Issues

#### Issue: Cannot Connect to PostgreSQL

**Symptoms:**
```
org.postgresql.util.PSQLException: Connection refused
```

**Solution:**
```bash
# 1. Check if PostgreSQL is running
pg_isready

# 2. Check connection settings
psql -h localhost -U postgres -d devportalkirostart

# 3. Verify .env configuration
cat .env | grep DB_

# 4. Test connection manually
psql -h localhost -U postgres -c "SELECT version();"

# 5. Check PostgreSQL logs
# macOS
tail -f /usr/local/var/log/postgres.log

# Linux
sudo tail -f /var/log/postgresql/postgresql-15-main.log
```

#### Issue: Authentication Failed

**Symptoms:**
```
password authentication failed for user "postgres"
```

**Solution:**
```bash
# Reset PostgreSQL password
sudo -u postgres psql
postgres=# ALTER USER postgres PASSWORD 'newpassword';
postgres=# \q

# Update .env
DB_PASSWORD=newpassword

# Or use peer authentication (Linux only)
# Edit /etc/postgresql/15/main/pg_hba.conf
# Change: local all postgres peer
sudo systemctl restart postgresql
```

#### Issue: Database Does Not Exist

**Symptoms:**
```
database "devportalkirostart" does not exist
```

**Solution:**
```bash
# Create database
createdb devportalkirostart

# Or using psql
psql -U postgres
postgres=# CREATE DATABASE devportalkirostart;
postgres=# \l
postgres=# \q

# Verify
psql -U postgres -d devportalkirostart -c "SELECT current_database();"
```

### Schema Issues

#### Issue: Table Already Exists

**Symptoms:**
```
relation "learning_module" already exists
```

**Solution:**
```bash
# Option 1: Drop and recreate database
dropdb devportalkirostart
createdb devportalkirostart

# Option 2: Change JPA DDL strategy
# In .env or application.yml
JPA_DDL_AUTO=update  # or validate

# Option 3: Manually drop tables
psql -U postgres -d devportalkirostart
devportalkirostart=# DROP TABLE IF EXISTS learning_module CASCADE;
devportalkirostart=# \q
```

#### Issue: Foreign Key Constraint Violation

**Symptoms:**
```
violates foreign key constraint
```

**Solution:**
```bash
# Check data initialization order in SafeDataInitializer
# Ensure parent records are created before child records

# Or temporarily disable constraints
psql -U postgres -d devportalkirostart
devportalkirostart=# SET CONSTRAINTS ALL DEFERRED;
devportalkirostart=# \q

# Or use JPA DDL auto create-drop
JPA_DDL_AUTO=create-drop
```

---

## Runtime Issues

### Application Startup Failures

#### Issue: Port Already in Use

**Symptoms:**
```
Port 3008 is already in use
Web server failed to start
```

**Solution:**
```bash
# Find process using port
# macOS/Linux
lsof -i :3008
kill -9 <PID>

# Windows
netstat -ano | findstr :3008
taskkill /PID <PID> /F

# Or change port in .env
SERVER_PORT=3009
```

#### Issue: Bean Creation Failed

**Symptoms:**
```
Error creating bean with name 'dataSource'
BeanCreationException
```

**Solution:**
```bash
# Check database configuration
cat .env | grep DB_

# Verify database is running
pg_isready

# Check application logs
tail -f logs/application.log

# Enable debug logging
LOGGING_LEVEL_ROOT=DEBUG
mvn spring-boot:run
```

#### Issue: Application Crashes on Startup

**Symptoms:**
```
Application run failed
java.lang.RuntimeException
```

**Solution:**
```bash
# Check logs for specific error
tail -100 logs/application.log

# Run with debug logging
LOGGING_LEVEL_ROOT=DEBUG mvn spring-boot:run

# Check configuration validation
# Look for "CONFIGURATION ERRORS DETECTED" in logs

# Verify all required environment variables
./validate-environment.sh
```

### Runtime Errors

#### Issue: NullPointerException

**Symptoms:**
```
java.lang.NullPointerException
```

**Solution:**
```bash
# Enable detailed logging
LOGGING_LEVEL_APP=DEBUG

# Check stack trace in logs
tail -100 logs/application.log

# Verify data initialization
psql -U postgres -d devportalkirostart -c "SELECT COUNT(*) FROM learning_module;"

# Check for missing @Autowired or @Service annotations
```

#### Issue: 404 Not Found

**Symptoms:**
```
404 error when accessing endpoints
```

**Solution:**
```bash
# Check server context path
echo $SERVER_CONTEXT_PATH

# Verify endpoint URL
# Correct: http://localhost:3008/api/actuator/health
# Wrong: http://localhost:3008/actuator/health

# Check controller mappings
curl http://localhost:3008/api/actuator/mappings

# Verify application is running
curl http://localhost:3008/api/actuator/health
```

---

## Configuration Issues

### Environment Variable Issues

#### Issue: Environment Variables Not Loading

**Symptoms:**
```
Using default values instead of .env values
```

**Solution:**
```bash
# Ensure .env file exists
ls -la .env

# Load environment variables
source .env

# Or use direnv
brew install direnv
echo 'eval "$(direnv hook zsh)"' >> ~/.zshrc
direnv allow

# Verify variables are set
env | grep DB_
env | grep SERVER_
```

#### Issue: Configuration Validation Fails

**Symptoms:**
```
CONFIGURATION ERRORS DETECTED
Configuration validation failed
```

**Solution:**
```bash
# Check validation errors in logs
tail -50 logs/application.log | grep "CONFIGURATION"

# Set required environment variables
# For production:
export DB_PASSWORD=SecurePassword123
export SECURITY_USER_NAME=admin
export SECURITY_USER_PASSWORD=SecureAdminPass123

# Verify configuration
./validate-environment.sh
```

---

## Performance Issues

### Slow Application Startup

**Symptoms:**
```
Application takes > 60 seconds to start
```

**Solution:**
```bash
# Increase JVM memory
export MAVEN_OPTS="-Xmx2048m"

# Disable unnecessary features
FEATURE_AI_ASSISTANCE=false
FEATURE_SOCIAL_FEATURES=false

# Use H2 for development (faster)
# In application-dev.yml, switch to H2

# Check for slow queries
LOGGING_LEVEL_SQL=DEBUG
```

### Slow Database Queries

**Symptoms:**
```
Queries taking > 1 second
```

**Solution:**
```bash
# Enable query logging
JPA_SHOW_SQL=true
LOGGING_LEVEL_SQL=DEBUG

# Increase connection pool
DB_POOL_MAX_SIZE=50
DB_POOL_MIN_IDLE=10

# Add database indexes
psql -U postgres -d devportalkirostart
devportalkirostart=# CREATE INDEX idx_module_name ON learning_module(name);

# Enable query caching
CACHE_TYPE=caffeine
```

---

## IDE-Specific Issues

### IntelliJ IDEA

#### Issue: Lombok Not Working

**Solution:**
1. Install Lombok plugin: Settings → Plugins → Search "Lombok"
2. Enable annotation processing: Settings → Build → Compiler → Annotation Processors
3. Rebuild project: Build → Rebuild Project

#### Issue: Cannot Resolve Symbols

**Solution:**
1. Invalidate caches: File → Invalidate Caches → Invalidate and Restart
2. Reimport Maven project: Right-click pom.xml → Maven → Reload Project
3. Check Project SDK: File → Project Structure → Project → SDK

### VS Code

#### Issue: Java Extension Not Working

**Solution:**
1. Install Java Extension Pack
2. Set Java home: Settings → Java: Home
3. Reload window: Cmd/Ctrl + Shift + P → Reload Window

### Eclipse

#### Issue: Lombok Not Generating Methods

**Solution:**
1. Download lombok.jar from https://projectlombok.org/
2. Run: `java -jar lombok.jar`
3. Select Eclipse installation
4. Restart Eclipse

---

## Platform-Specific Issues

### macOS

#### Issue: Command Line Tools Missing

**Solution:**
```bash
xcode-select --install
```

#### Issue: Homebrew Permissions

**Solution:**
```bash
sudo chown -R $(whoami) /usr/local/Homebrew
brew doctor
```

### Linux

#### Issue: PostgreSQL Authentication

**Solution:**
```bash
# Edit pg_hba.conf
sudo nano /etc/postgresql/15/main/pg_hba.conf

# Change to:
# local   all   postgres   peer
# local   all   all        md5

sudo systemctl restart postgresql
```

### Windows

#### Issue: Path Too Long

**Solution:**
```powershell
# Enable long paths
New-ItemProperty -Path "HKLM:\SYSTEM\CurrentControlSet\Control\FileSystem" -Name "LongPathsEnabled" -Value 1 -PropertyType DWORD -Force
```

#### Issue: PowerShell Execution Policy

**Solution:**
```powershell
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
```

---

## Getting Additional Help

If your issue is not covered here:

1. **Check Logs**: `tail -100 logs/application.log`
2. **Enable Debug Logging**: `LOGGING_LEVEL_APP=DEBUG`
3. **Run Environment Validation**: `./validate-environment.sh`
4. **Check Configuration**: Review `CONFIGURATION_GUIDE.md`
5. **Review Setup Guide**: See `SETUP_GUIDE.md`
6. **Search Documentation**: Check Spring Boot and PostgreSQL docs
7. **Contact Team**: Reach out to development team with:
   - Error message
   - Stack trace from logs
   - Environment details (OS, Java version, etc.)
   - Steps to reproduce

---

## Useful Commands

### Diagnostic Commands

```bash
# Check Java
java -version
echo $JAVA_HOME

# Check Maven
mvn -version

# Check PostgreSQL
pg_isready
psql -U postgres -l

# Check Node.js
node --version
npm --version

# Check application
curl http://localhost:3008/api/actuator/health

# Check logs
tail -f logs/application.log

# Check environment
env | grep -E "DB_|SERVER_|SPRING_"

# Validate environment
./validate-environment.sh
```

### Recovery Commands

```bash
# Clean build
mvn clean install

# Reset database
dropdb devportalkirostart && createdb devportalkirostart

# Clear caches
mvn clean
rm -rf ~/.m2/repository
npm cache clean --force

# Restart services
brew services restart postgresql  # macOS
sudo systemctl restart postgresql  # Linux
```

---

**Last Updated**: 2024
**Version**: 1.0.0
