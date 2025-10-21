# Developer Onboarding Guide

## Welcome to the Learning Portal Project! 🎉

This comprehensive guide will help you get up and running with the Learning Portal development environment quickly and efficiently.

## Table of Contents

1. [Project Overview](#project-overview)
2. [Prerequisites](#prerequisites)
3. [Environment Setup](#environment-setup)
4. [Development Workflow](#development-workflow)
5. [Code Standards](#code-standards)
6. [Testing Guidelines](#testing-guidelines)
7. [Deployment Process](#deployment-process)
8. [Troubleshooting](#troubleshooting)
9. [Resources and Support](#resources-and-support)

## Project Overview

### Mission
Build **THE MOST COMPREHENSIVE LEARNING PORTAL IN THE WORLD** for FAANG senior developer preparation, covering complete zero-to-expert mastery across all major technology domains.

### Technology Stack
- **Backend**: Java 21, Spring Boot 3.2.0, Spring Data JPA, PostgreSQL/H2
- **Frontend**: React 18, Vite, Monaco Editor, Modern CSS
- **Build Tools**: Maven, npm/yarn
- **Testing**: JUnit 5, Mockito, React Testing Library
- **Documentation**: Swagger/OpenAPI, JavaDoc, Markdown

### Architecture
```
comprehensive-dev-portal/
├── src/main/java/           # Backend Java code
├── frontend/src/            # React frontend code
├── docs/                    # Comprehensive documentation
├── scripts/                 # Build and deployment scripts
└── .kiro/                   # Kiro AI assistant configuration
```

## Prerequisites

### Required Software

#### 1. Java Development Kit (JDK) 21
```bash
# Check Java version
java -version

# Should output: openjdk version "21.x.x"
```

**Installation:**
- **macOS**: `brew install openjdk@21`
- **Windows**: Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
- **Linux**: `sudo apt install openjdk-21-jdk` (Ubuntu/Debian)

#### 2. Node.js (v18 or higher)
```bash
# Check Node.js version
node --version

# Should output: v18.x.x or higher
```

**Installation:**
- **All Platforms**: Download from [nodejs.org](https://nodejs.org/)
- **macOS**: `brew install node`
- **Using nvm**: `nvm install 18 && nvm use 18`

#### 3. Maven (v3.8 or higher)
```bash
# Check Maven version
mvn --version

# Should output: Apache Maven 3.8.x or higher
```

**Installation:**
- **macOS**: `brew install maven`
- **Windows**: Download from [maven.apache.org](https://maven.apache.org/download.cgi)
- **Linux**: `sudo apt install maven`

#### 4. Git
```bash
# Check Git version
git --version

# Should output: git version 2.x.x
```

#### 5. IDE (Recommended)
- **IntelliJ IDEA** (Ultimate or Community Edition)
- **VS Code** with Java Extension Pack
- **Eclipse** with Spring Tools Suite

### Optional Tools
- **Docker** (for containerized development)
- **PostgreSQL** (for local database development)
- **Redis** (for caching development)
- **Postman** (for API testing)

## Environment Setup

### 1. Clone the Repository
```bash
git clone https://github.com/your-org/comprehensive-dev-portal.git
cd comprehensive-dev-portal
```

### 2. Backend Setup

#### Install Dependencies
```bash
# Install Maven dependencies
mvn clean install

# This will:
# - Download all Java dependencies
# - Compile the backend code
# - Run tests
# - Build the frontend and integrate it
```

#### Configure Database
The application uses H2 for development by default. No additional setup required.

For PostgreSQL (optional):
```bash
# Install PostgreSQL
brew install postgresql  # macOS
sudo apt install postgresql  # Linux

# Create database
createdb learning_portal_dev

# Update application-dev.yml with your database credentials
```

#### Environment Variables
Create a `.env` file in the project root:
```bash
# Copy the example environment file
cp .env.example .env

# Edit the file with your specific configuration
# Most defaults work for development
```

### 3. Frontend Setup

#### Install Dependencies
```bash
cd frontend
npm install

# Or using yarn
yarn install
```

#### Development Server
```bash
# Start frontend development server (port 3002)
npm run dev

# Or using yarn
yarn dev
```

### 4. Full Application Startup

#### Option 1: Integrated Development (Recommended)
```bash
# Start backend (includes built frontend)
mvn spring-boot:run

# Application will be available at:
# - Backend API: http://localhost:8080
# - Frontend: http://localhost:8080 (served by Spring Boot)
# - API Documentation: http://localhost:8080/swagger-ui/index.html
```

#### Option 2: Separate Development Servers
```bash
# Terminal 1: Start backend
mvn spring-boot:run

# Terminal 2: Start frontend dev server
cd frontend && npm run dev

# Application will be available at:
# - Backend API: http://localhost:8080
# - Frontend: http://localhost:3002 (with hot reload)
```

### 5. Verify Installation

#### Health Check
```bash
# Check application health
curl http://localhost:8080/actuator/health

# Expected response:
# {"status":"UP"}
```

#### API Test
```bash
# Test API endpoint
curl http://localhost:8080/modules

# Should return learning modules data
```

#### Frontend Test
Open your browser and navigate to:
- **Integrated**: http://localhost:8080
- **Separate**: http://localhost:3002

You should see the Learning Portal homepage.

## Development Workflow

### 1. Branch Strategy
```bash
# Create feature branch
git checkout -b feature/your-feature-name

# Make changes and commit
git add .
git commit -m "feat: add new feature description"

# Push and create pull request
git push origin feature/your-feature-name
```

### 2. Code Changes

#### Backend Development
```bash
# Make Java changes in src/main/java/
# Run tests
mvn test

# Start application to test changes
mvn spring-boot:run
```

#### Frontend Development
```bash
# Make React changes in frontend/src/
# Development server auto-reloads
cd frontend && npm run dev

# Build for production testing
npm run build
```

### 3. Testing Your Changes

#### Unit Tests
```bash
# Run backend tests
mvn test

# Run frontend tests
cd frontend && npm test
```

#### Integration Testing
```bash
# Run full test suite
mvn verify

# Test API endpoints
curl -X GET http://localhost:8080/modules
```

#### Manual Testing
1. Start the application
2. Test your changes in the browser
3. Verify API functionality with Swagger UI
4. Check console for errors

### 4. Code Quality

#### Backend Quality Checks
```bash
# Run static analysis (if configured)
mvn sonar:sonar

# Check code formatting
mvn spotless:check

# Apply code formatting
mvn spotless:apply
```

#### Frontend Quality Checks
```bash
cd frontend

# Run linting
npm run lint

# Fix linting issues
npm run lint:fix

# Check formatting
npm run format:check

# Apply formatting
npm run format
```

## Code Standards

### Java Code Standards

#### 1. Package Structure
```java
com.learningportal.
├── controller/     # REST controllers
├── service/        # Business logic
├── repository/     # Data access
├── model/          # JPA entities
├── dto/            # Data transfer objects
├── config/         # Configuration classes
├── exception/      # Custom exceptions
└── util/           # Utility classes
```

#### 2. Naming Conventions
- **Classes**: PascalCase (`LearningModuleService`)
- **Methods**: camelCase (`findModuleById`)
- **Variables**: camelCase (`moduleId`)
- **Constants**: UPPER_SNAKE_CASE (`MAX_RETRY_ATTEMPTS`)
- **Packages**: lowercase (`com.learningportal.service`)

#### 3. Documentation Requirements
```java
/**
 * Service for managing learning modules and their content.
 * 
 * This service provides comprehensive functionality for:
 * - Creating and updating learning modules
 * - Managing module content and structure
 * - Tracking user progress through modules
 * 
 * @author Your Name
 * @version 1.0
 * @since 2024-01-01
 */
@Service
public class LearningModuleService {
    
    /**
     * Retrieves a learning module by its unique identifier.
     * 
     * @param moduleId the unique identifier of the module
     * @return the learning module if found
     * @throws ModuleNotFoundException if no module exists with the given ID
     */
    public LearningModule findById(Long moduleId) {
        // Implementation
    }
}
```

#### 4. Error Handling
```java
// Use specific exceptions
throw new ModuleNotFoundException("Module not found with ID: " + moduleId);

// Log errors appropriately
log.error("Failed to process module {}: {}", moduleId, e.getMessage(), e);

// Return proper HTTP status codes
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ModuleNotFoundException extends RuntimeException {
    // Implementation
}
```

### React Code Standards

#### 1. Component Structure
```javascript
// Use functional components with hooks
import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';

/**
 * Learning module card component for displaying module information.
 * 
 * @param {Object} props - Component props
 * @param {Object} props.module - The learning module data
 * @param {Function} props.onSelect - Callback when module is selected
 */
const ModuleCard = ({ module, onSelect }) => {
    const [isLoading, setIsLoading] = useState(false);
    
    useEffect(() => {
        // Component logic
    }, [module]);
    
    return (
        <div className="module-card">
            {/* Component JSX */}
        </div>
    );
};

ModuleCard.propTypes = {
    module: PropTypes.object.isRequired,
    onSelect: PropTypes.func.isRequired
};

export default ModuleCard;
```

#### 2. Naming Conventions
- **Components**: PascalCase (`ModuleCard`)
- **Files**: PascalCase for components (`ModuleCard.jsx`)
- **Variables**: camelCase (`moduleData`)
- **CSS Classes**: kebab-case (`module-card`)

#### 3. State Management
```javascript
// Use Context for global state
const LearningContext = createContext();

// Use local state for component-specific data
const [modules, setModules] = useState([]);

// Use custom hooks for reusable logic
const useModules = () => {
    // Hook implementation
};
```

## Testing Guidelines

### Backend Testing

#### 1. Unit Tests
```java
@ExtendWith(MockitoExtension.class)
class LearningModuleServiceTest {
    
    @Mock
    private LearningModuleRepository repository;
    
    @InjectMocks
    private LearningModuleService service;
    
    @Test
    void shouldReturnModuleWhenFound() {
        // Given
        Long moduleId = 1L;
        LearningModule expectedModule = new LearningModule();
        when(repository.findById(moduleId)).thenReturn(Optional.of(expectedModule));
        
        // When
        LearningModule result = service.findById(moduleId);
        
        // Then
        assertThat(result).isEqualTo(expectedModule);
    }
}
```

#### 2. Integration Tests
```java
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
class LearningModuleControllerIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void shouldReturnModulesWhenRequested() {
        // When
        ResponseEntity<String> response = restTemplate.getForEntity("/modules", String.class);
        
        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
```

### Frontend Testing

#### 1. Component Tests
```javascript
import { render, screen, fireEvent } from '@testing-library/react';
import ModuleCard from './ModuleCard';

describe('ModuleCard', () => {
    const mockModule = {
        id: 1,
        title: 'Test Module',
        description: 'Test Description'
    };
    
    test('renders module information', () => {
        render(<ModuleCard module={mockModule} onSelect={jest.fn()} />);
        
        expect(screen.getByText('Test Module')).toBeInTheDocument();
        expect(screen.getByText('Test Description')).toBeInTheDocument();
    });
    
    test('calls onSelect when clicked', () => {
        const mockOnSelect = jest.fn();
        render(<ModuleCard module={mockModule} onSelect={mockOnSelect} />);
        
        fireEvent.click(screen.getByText('Test Module'));
        
        expect(mockOnSelect).toHaveBeenCalledWith(mockModule);
    });
});
```

## Deployment Process

### Development Deployment
```bash
# Build the application
mvn clean package

# Run the built JAR
java -jar target/comprehensive-dev-portal-1.0.0.jar

# Or use Maven
mvn spring-boot:run
```

### Production Deployment
```bash
# Build for production
mvn clean package -Pprod

# Deploy to server
scp target/comprehensive-dev-portal-1.0.0.jar user@server:/opt/learning-portal/

# Start application on server
java -jar -Dspring.profiles.active=production comprehensive-dev-portal-1.0.0.jar
```

## Troubleshooting

### Common Issues

#### 1. Compilation Errors
```bash
# Clean and rebuild
mvn clean compile

# Check Java version
java -version

# Verify Maven settings
mvn -version
```

#### 2. Port Conflicts
```bash
# Check what's using port 8080
lsof -i :8080

# Kill process if needed
kill -9 <PID>

# Or change port in application.yml
server:
  port: 8081
```

#### 3. Database Connection Issues
```bash
# Check H2 console
http://localhost:8080/h2-console

# JDBC URL: jdbc:h2:mem:devportal
# Username: sa
# Password: password
```

#### 4. Frontend Build Issues
```bash
cd frontend

# Clear node modules and reinstall
rm -rf node_modules package-lock.json
npm install

# Clear npm cache
npm cache clean --force
```

### Getting Help

#### 1. Check Logs
```bash
# Application logs
tail -f logs/learning-portal.log

# Spring Boot logs
mvn spring-boot:run | grep ERROR

# Frontend logs
cd frontend && npm run dev
```

#### 2. Debug Mode
```bash
# Start backend in debug mode
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"

# Connect debugger to port 5005
```

#### 3. Health Checks
```bash
# Application health
curl http://localhost:8080/actuator/health

# Detailed health info
curl http://localhost:8080/actuator/health/db
```

## Resources and Support

### Documentation
- **API Documentation**: http://localhost:8080/swagger-ui/index.html
- **Architecture Docs**: [docs/architecture/README.md](docs/architecture/README.md)
- **Troubleshooting**: [TROUBLESHOOTING_GUIDE.md](TROUBLESHOOTING_GUIDE.md)

### Development Tools
- **H2 Console**: http://localhost:8080/h2-console
- **Actuator Endpoints**: http://localhost:8080/actuator
- **Frontend Dev Server**: http://localhost:3002

### Learning Resources
- **Spring Boot**: https://spring.io/guides
- **React**: https://reactjs.org/docs
- **Java 21**: https://openjdk.org/projects/jdk/21/
- **Maven**: https://maven.apache.org/guides/

### Support Channels
- **Team Chat**: #learning-portal-dev
- **Issues**: GitHub repository issues
- **Code Reviews**: Pull request discussions
- **Documentation**: This guide and linked resources

### Next Steps

1. **Complete Environment Setup**: Follow all setup steps above
2. **Explore the Codebase**: Start with the main application class and controllers
3. **Run Tests**: Ensure all tests pass in your environment
4. **Make a Small Change**: Try adding a simple endpoint or component
5. **Read Documentation**: Familiarize yourself with the architecture and APIs
6. **Join Team Meetings**: Participate in standups and planning sessions

Welcome to the team! 🚀

If you encounter any issues during setup or have questions about the development process, don't hesitate to reach out to the team. We're here to help you succeed!