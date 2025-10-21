# Setup Guide - Comprehensive Developer Portal

## Overview

This guide provides step-by-step instructions for setting up the Comprehensive Developer Portal on different development machines and operating systems. Follow these instructions to ensure a consistent development environment.

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Quick Start](#quick-start)
3. [Detailed Setup Instructions](#detailed-setup-instructions)
4. [Platform-Specific Instructions](#platform-specific-instructions)
5. [Verification Steps](#verification-steps)
6. [Troubleshooting](#troubleshooting)
7. [IDE Setup](#ide-setup)

---

## Prerequisites

### Required Software

| Software | Minimum Version | Recommended Version | Purpose |
|----------|----------------|---------------------|---------|
| Java JDK | 17 | 21 | Backend runtime |
| Maven | 3.8.0 | 3.9.x | Build tool |
| PostgreSQL | 12 | 15 | Database |
| Node.js | 16.x | 20.x LTS | Frontend build |
| npm | 8.x | 10.x | Package manager |
| Git | 2.30 | Latest | Version control |

### Optional Software

| Software | Purpose |
|----------|---------|
| Redis | Production caching (optional for development) |
| Docker | Containerized deployment |
| IntelliJ IDEA / VS Code | IDE |
| Postman / Insomnia | API testing |
| pgAdmin | PostgreSQL GUI |

---

## Quick Start

For experienced developers who want to get started quickly:

```bash
# 1. Clone the repository
git clone <repository-url>
cd comprehensive-dev-portal

# 2. Set up environment variables
cp .env.example .env
# Edit .env with your database credentials

# 3. Create PostgreSQL database
createdb devportalkirostart

# 4. Build and run
mvn clean install
mvn spring-boot:run

# 5. Access the application
# Backend API: http://localhost:3008/api
# Frontend: http://localhost:3008
```

---

## Detailed Setup Instructions

### Step 1: Install Java

#### Check if Java is installed:
```bash
java -version
```

#### Install Java (if needed):

**macOS:**
```bash
# Using Homebrew
brew install openjdk@21

# Add to PATH
echo 'export PATH="/opt/homebrew/opt/openjdk@21/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install openjdk-21-jdk

# Verify installation
java -version
javac -version
```

**Windows:**
1. Download OpenJDK 21 from https://adoptium.net/
2. Run the installer
3. Add to PATH:
   - Right-click "This PC" → Properties → Advanced System Settings
   - Environment Variables → System Variables → Path → Edit
   - Add: `C:\Program Files\Eclipse Adoptium\jdk-21.x.x\bin`
4. Verify: Open new Command Prompt and run `java -version`

### Step 2: Install Maven

#### Check if Maven is installed:
```bash
mvn -version
```

#### Install Maven (if needed):

**macOS:**
```bash
brew install maven
```

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install maven
```

**Windows:**
1. Download Maven from https://maven.apache.org/download.cgi
2. Extract to `C:\Program Files\Apache\maven`
3. Add to PATH: `C:\Program Files\Apache\maven\bin`
4. Create `M2_HOME` environment variable: `C:\Program Files\Apache\maven`

### Step 3: Install PostgreSQL

#### Check if PostgreSQL is installed:
```bash
psql --version
```

#### Install PostgreSQL (if needed):

**macOS:**
```bash
# Using Homebrew
brew install postgresql@15

# Start PostgreSQL service
brew services start postgresql@15

# Create default user (if needed)
createuser -s postgres
```

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install postgresql postgresql-contrib

# Start PostgreSQL service
sudo systemctl start postgresql
sudo systemctl enable postgresql

# Set password for postgres user
sudo -u postgres psql
postgres=# \password postgres
postgres=# \q
```

**Windows:**
1. Download PostgreSQL from https://www.postgresql.org/download/windows/
2. Run the installer
3. Remember the password you set for the postgres user
4. Add to PATH: `C:\Program Files\PostgreSQL\15\bin`

### Step 4: Install Node.js and npm

#### Check if Node.js is installed:
```bash
node --version
npm --version
```

#### Install Node.js (if needed):

**macOS:**
```bash
# Using Homebrew
brew install node@20

# Or using nvm (recommended)
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.0/install.sh | bash
nvm install 20
nvm use 20
```

**Linux (Ubuntu/Debian):**
```bash
# Using NodeSource repository
curl -fsSL https://deb.nodesource.com/setup_20.x | sudo -E bash -
sudo apt-get install -y nodejs

# Or using nvm (recommended)
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.0/install.sh | bash
nvm install 20
nvm use 20
```

**Windows:**
1. Download Node.js from https://nodejs.org/
2. Run the installer (includes npm)
3. Verify: Open new Command Prompt and run `node --version`

### Step 5: Clone the Repository

```bash
# Clone the repository
git clone <repository-url>
cd comprehensive-dev-portal

# Verify you're in the correct directory
ls -la
# You should see: pom.xml, src/, frontend/, README.md, etc.
```

### Step 6: Configure Environment Variables

```bash
# Copy the example environment file
cp .env.example .env

# Edit the .env file with your settings
nano .env  # or use your preferred editor
```

**Minimum required configuration:**
```bash
# .env
SPRING_PROFILES_ACTIVE=dev
DB_PASSWORD=your_postgres_password
```

**Recommended development configuration:**
```bash
# .env
APP_NAME=comprehensive-dev-portal
SPRING_PROFILES_ACTIVE=dev
SERVER_PORT=3008

DB_URL=jdbc:postgresql://localhost:5432/devportalkirostart
DB_USERNAME=postgres
DB_PASSWORD=your_postgres_password

SECURITY_USER_NAME=admin
SECURITY_USER_PASSWORD=admin123

LOGGING_LEVEL_APP=DEBUG
```

### Step 7: Create PostgreSQL Database

```bash
# Create the database
createdb devportalkirostart

# Verify database was created
psql -l | grep devportalkirostart

# Or connect to PostgreSQL and create manually
psql -U postgres
postgres=# CREATE DATABASE devportalkirostart;
postgres=# \l
postgres=# \q
```

### Step 8: Build the Application

```bash
# Clean and build the entire project (backend + frontend)
mvn clean install

# This will:
# 1. Compile Java code
# 2. Run tests
# 3. Build frontend (React)
# 4. Package everything into a JAR file
```

**Expected output:**
```
[INFO] BUILD SUCCESS
[INFO] Total time: 2-5 minutes
```

### Step 9: Run the Application

```bash
# Run the Spring Boot application
mvn spring-boot:run

# Or run the JAR file directly
java -jar target/comprehensive-dev-portal-1.0.0.jar
```

**Expected output:**
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

...
Started ComprehensiveDevPortalApplication in X.XXX seconds
```

### Step 10: Verify Installation

```bash
# Check if the application is running
curl http://localhost:3008/api/actuator/health

# Expected response:
# {"status":"UP"}

# Access the application in your browser
# http://localhost:3008
```

---

## Platform-Specific Instructions

### macOS

#### Additional Setup:

1. **Install Xcode Command Line Tools** (if not already installed):
   ```bash
   xcode-select --install
   ```

2. **Use Homebrew for package management**:
   ```bash
   # Install Homebrew if not installed
   /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
   ```

3. **Set JAVA_HOME**:
   ```bash
   echo 'export JAVA_HOME=$(/usr/libexec/java_home -v 21)' >> ~/.zshrc
   source ~/.zshrc
   ```

#### Common Issues:

- **Port 3008 already in use**: Change `SERVER_PORT` in `.env`
- **PostgreSQL connection refused**: Ensure PostgreSQL is running: `brew services list`
- **Permission denied**: Use `sudo` for system-level installations

### Linux (Ubuntu/Debian)

#### Additional Setup:

1. **Update package lists**:
   ```bash
   sudo apt update
   sudo apt upgrade
   ```

2. **Install build essentials**:
   ```bash
   sudo apt install build-essential
   ```

3. **Set JAVA_HOME**:
   ```bash
   echo 'export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64' >> ~/.bashrc
   source ~/.bashrc
   ```

#### Common Issues:

- **PostgreSQL authentication failed**: Edit `/etc/postgresql/15/main/pg_hba.conf`
- **Port permission denied**: Use port > 1024 or run with sudo (not recommended)
- **Maven not found**: Ensure `/usr/share/maven/bin` is in PATH

### Windows

#### Additional Setup:

1. **Use PowerShell or Git Bash** for better command-line experience

2. **Set environment variables**:
   - `JAVA_HOME`: `C:\Program Files\Eclipse Adoptium\jdk-21.x.x`
   - `MAVEN_HOME`: `C:\Program Files\Apache\maven`
   - `PATH`: Add both `%JAVA_HOME%\bin` and `%MAVEN_HOME%\bin`

3. **Configure PostgreSQL**:
   - Ensure PostgreSQL service is running: Services → postgresql-x64-15
   - Add to PATH: `C:\Program Files\PostgreSQL\15\bin`

#### Common Issues:

- **Command not found**: Restart Command Prompt after setting environment variables
- **PostgreSQL password authentication failed**: Check `pg_hba.conf` in PostgreSQL data directory
- **Maven build fails**: Ensure `JAVA_HOME` points to JDK, not JRE

---

## Verification Steps

### 1. Verify Java Installation

```bash
java -version
# Expected: openjdk version "21.x.x"

javac -version
# Expected: javac 21.x.x

echo $JAVA_HOME  # Linux/Mac
echo %JAVA_HOME%  # Windows
# Expected: Path to JDK installation
```

### 2. Verify Maven Installation

```bash
mvn -version
# Expected: Apache Maven 3.9.x
# Java version: 21.x.x
```

### 3. Verify PostgreSQL Installation

```bash
psql --version
# Expected: psql (PostgreSQL) 15.x

# Test connection
psql -U postgres -d devportalkirostart -c "SELECT version();"
# Expected: PostgreSQL version information
```

### 4. Verify Node.js Installation

```bash
node --version
# Expected: v20.x.x

npm --version
# Expected: 10.x.x
```

### 5. Verify Application Build

```bash
mvn clean compile
# Expected: BUILD SUCCESS

mvn test
# Expected: Tests run: X, Failures: 0, Errors: 0
```

### 6. Verify Application Startup

```bash
# Start the application
mvn spring-boot:run

# In another terminal, test endpoints
curl http://localhost:3008/api/actuator/health
# Expected: {"status":"UP"}

curl http://localhost:3008/api/actuator/info
# Expected: Application information
```

### 7. Verify Database Connection

```bash
# Check database tables
psql -U postgres -d devportalkirostart -c "\dt"
# Expected: List of tables (learning_module, topic, interview_question, etc.)

# Check data
psql -U postgres -d devportalkirostart -c "SELECT COUNT(*) FROM learning_module;"
# Expected: Number of learning modules
```

---

## Troubleshooting

### Common Issues and Solutions

#### 1. Java Version Mismatch

**Problem**: `Unsupported class file major version` or `source value 21 is obsolete`

**Solution**:
```bash
# Check Java version
java -version

# If wrong version, set JAVA_HOME correctly
export JAVA_HOME=$(/usr/libexec/java_home -v 21)  # macOS
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64  # Linux

# Verify
echo $JAVA_HOME
java -version
```

#### 2. Maven Build Fails

**Problem**: `Failed to execute goal` or `Compilation failure`

**Solution**:
```bash
# Clean Maven cache
mvn clean

# Update dependencies
mvn dependency:resolve

# Force update
mvn clean install -U

# Skip tests if needed (not recommended)
mvn clean install -DskipTests
```

#### 3. PostgreSQL Connection Refused

**Problem**: `Connection refused` or `could not connect to server`

**Solution**:
```bash
# Check if PostgreSQL is running
# macOS
brew services list | grep postgresql

# Linux
sudo systemctl status postgresql

# Windows
# Check Services → postgresql-x64-15

# Start PostgreSQL if not running
# macOS
brew services start postgresql@15

# Linux
sudo systemctl start postgresql

# Windows
# Services → postgresql-x64-15 → Start
```

#### 4. Database Authentication Failed

**Problem**: `password authentication failed for user "postgres"`

**Solution**:
```bash
# Reset PostgreSQL password
# macOS/Linux
sudo -u postgres psql
postgres=# ALTER USER postgres PASSWORD 'newpassword';
postgres=# \q

# Update .env file
DB_PASSWORD=newpassword

# Or use peer authentication (Linux)
# Edit /etc/postgresql/15/main/pg_hba.conf
# Change: local all postgres peer
```

#### 5. Port Already in Use

**Problem**: `Port 3008 is already in use`

**Solution**:
```bash
# Find process using the port
# macOS/Linux
lsof -i :3008

# Windows
netstat -ano | findstr :3008

# Kill the process
# macOS/Linux
kill -9 <PID>

# Windows
taskkill /PID <PID> /F

# Or use a different port
# Edit .env
SERVER_PORT=3009
```

#### 6. Frontend Build Fails

**Problem**: `npm install failed` or `frontend build error`

**Solution**:
```bash
# Navigate to frontend directory
cd frontend

# Clear npm cache
npm cache clean --force

# Remove node_modules and package-lock.json
rm -rf node_modules package-lock.json

# Reinstall dependencies
npm install

# Build manually
npm run build

# Return to project root
cd ..
```

#### 7. Out of Memory Error

**Problem**: `Java heap space` or `OutOfMemoryError`

**Solution**:
```bash
# Increase Maven memory
export MAVEN_OPTS="-Xmx2048m -XX:MaxPermSize=512m"

# Or set in .mavenrc file
echo "MAVEN_OPTS=-Xmx2048m" > ~/.mavenrc

# Increase application memory
java -Xmx2048m -jar target/comprehensive-dev-portal-1.0.0.jar
```

#### 8. Lombok Not Working

**Problem**: `Cannot find symbol` for getter/setter methods

**Solution**:
```bash
# Ensure Lombok is in dependencies
mvn dependency:tree | grep lombok

# Clean and rebuild
mvn clean compile

# IDE-specific:
# IntelliJ IDEA: Install Lombok plugin and enable annotation processing
# Eclipse: Install Lombok from https://projectlombok.org/
# VS Code: Install Lombok extension
```

---

## IDE Setup

### IntelliJ IDEA

1. **Import Project**:
   - File → Open → Select `pom.xml`
   - Import as Maven project

2. **Configure JDK**:
   - File → Project Structure → Project
   - Set Project SDK to Java 21

3. **Enable Annotation Processing**:
   - Settings → Build, Execution, Deployment → Compiler → Annotation Processors
   - Check "Enable annotation processing"

4. **Install Lombok Plugin**:
   - Settings → Plugins → Search "Lombok"
   - Install and restart

5. **Configure Run Configuration**:
   - Run → Edit Configurations → Add New → Spring Boot
   - Main class: `com.learningportal.ComprehensiveDevPortalApplication`
   - Environment variables: Load from `.env`

### VS Code

1. **Install Extensions**:
   - Java Extension Pack
   - Spring Boot Extension Pack
   - Lombok Annotations Support

2. **Configure Java**:
   - Settings → Java: Home → Set to Java 21 path

3. **Configure Environment**:
   - Create `.vscode/launch.json`:
   ```json
   {
     "version": "0.2.0",
     "configurations": [
       {
         "type": "java",
         "name": "Spring Boot",
         "request": "launch",
         "mainClass": "com.learningportal.ComprehensiveDevPortalApplication",
         "envFile": "${workspaceFolder}/.env"
       }
     ]
   }
   ```

### Eclipse

1. **Import Project**:
   - File → Import → Maven → Existing Maven Projects
   - Select project directory

2. **Configure JDK**:
   - Project → Properties → Java Build Path
   - Add Library → JRE System Library → Java 21

3. **Install Lombok**:
   - Download lombok.jar from https://projectlombok.org/
   - Run: `java -jar lombok.jar`
   - Select Eclipse installation directory

4. **Enable Annotation Processing**:
   - Project → Properties → Java Compiler → Annotation Processing
   - Enable project specific settings
   - Enable annotation processing

---

## Next Steps

After successful setup:

1. **Explore the Application**:
   - Backend API: http://localhost:3008/api
   - Health Check: http://localhost:3008/api/actuator/health
   - API Documentation: http://localhost:3008/api/swagger-ui.html (if enabled)

2. **Review Documentation**:
   - [README.md](README.md) - Project overview
   - [CONFIGURATION_GUIDE.md](CONFIGURATION_GUIDE.md) - Configuration options
   - [DEVELOPMENT_GUIDE.md](DEVELOPMENT_GUIDE.md) - Development workflow

3. **Start Development**:
   - Review existing code structure
   - Run tests: `mvn test`
   - Make changes and test locally
   - Follow contribution guidelines

4. **Get Help**:
   - Check troubleshooting section above
   - Review application logs: `logs/application.log`
   - Enable DEBUG logging: `LOGGING_LEVEL_APP=DEBUG`
   - Consult team members or documentation

---

## System Requirements

### Minimum Requirements

- **CPU**: 2 cores
- **RAM**: 4 GB
- **Disk**: 5 GB free space
- **OS**: macOS 10.15+, Ubuntu 20.04+, Windows 10+

### Recommended Requirements

- **CPU**: 4+ cores
- **RAM**: 8+ GB
- **Disk**: 10+ GB free space (SSD recommended)
- **OS**: Latest stable version

---

## Support

If you encounter issues not covered in this guide:

1. Check the [Troubleshooting](#troubleshooting) section
2. Review application logs: `logs/application.log`
3. Enable DEBUG logging for more details
4. Consult the [CONFIGURATION_GUIDE.md](CONFIGURATION_GUIDE.md)
5. Contact the development team

---

**Last Updated**: 2024
**Version**: 1.0.0
