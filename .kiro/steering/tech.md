# Technology Stack & Build System

## Backend Architecture
- **Java 17** - Latest LTS version with modern features (complete zero-to-expert coverage)
- **Spring Boot 3.2.0** - Enterprise-grade framework with auto-configuration
- **Spring Data JPA** - Data access abstraction with custom queries
- **Hibernate** - Advanced ORM with relationship mapping and performance optimization
- **Node.js** - Complete zero-to-expert coverage (fundamentals → advanced patterns)
- **H2 Database** (Development) - In-memory database for rapid development
- **PostgreSQL** (Production) - Robust production database with connection pooling
- **Maven** - Build and dependency management with frontend integration

## Frontend Architecture
- **React 18** - Modern frontend framework with hooks and functional components
- **Vite** - Fast build tool and development server with hot reload
- **React Router** - Client-side routing and navigation
- **Monaco Editor** - VS Code-powered code editor for interactive coding
- **Lucide React** - Professional icon library
- **Axios** - HTTP client for API integration

## Design System
- **AWS-Inspired Design** - Professional, cognitive-friendly UI with glass morphism
- **CSS Custom Properties** - Consistent design tokens and theming
- **Responsive Design** - Mobile-first approach with fluid typography
- **Advanced Animations** - Hardware-accelerated transforms with cubic-bezier easing
- **Accessibility** - WCAG 2.1 AA compliant components

## Build & Development
### Common Commands
```bash
# Backend Development (Java/Spring Boot)
mvn spring-boot:run                    # Start Spring Boot server (port 8080)
mvn clean install                      # Build entire project including frontend
mvn test                              # Run backend tests

# Node.js Development (Learning Content)
node examples/nodejs/basic-server.js  # Run Node.js code examples
npm run node-examples                  # Execute Node.js learning exercises
node --inspect examples/debugging.js  # Debug Node.js examples

# Frontend Development  
cd frontend && npm run dev            # Start Vite dev server (port 3002)
cd frontend && npm run build          # Build for production
cd frontend && npm run preview        # Preview production build

# Interactive Code Terminal
# Embedded terminal in each topic for practicing:
# - Java code examples with compilation and execution
# - Node.js scripts with real-time output
# - Database queries with result visualization
# - Algorithm implementations with performance analysis

# Database Access
# H2 Console: http://localhost:8080/h2-console
# JDBC URL: jdbc:h2:mem:devportal
# Username: sa, Password: password

# Full Stack Development
mvn spring-boot:run                   # Terminal 1: Backend
cd frontend && npm run dev            # Terminal 2: Frontend
```

### Project Structure
```
comprehensive-dev-portal/
├── src/main/java/com/learningportal/     # Backend Java code
│   ├── config/                          # Configuration classes
│   ├── controller/                      # REST controllers
│   ├── service/                         # Business logic layer
│   ├── repository/                      # Data access layer
│   ├── model/                           # JPA entities
│   └── ComprehensiveDevPortalApplication.java
├── src/main/resources/
│   ├── application.yml                  # Development configuration
│   └── application-production.yml      # Production configuration
├── frontend/                            # React application
│   ├── src/
│   │   ├── components/                  # Reusable React components
│   │   ├── pages/                       # Page components
│   │   ├── context/                     # React context providers
│   │   ├── styles/                      # CSS design system
│   │   └── main.jsx                     # React entry point
│   ├── package.json                     # Frontend dependencies
│   └── vite.config.js                   # Vite configuration
└── pom.xml                              # Maven configuration
```

## Key Dependencies
### Backend
- `spring-boot-starter-web` - REST API and embedded Tomcat
- `spring-boot-starter-data-jpa` - Database operations and ORM
- `spring-boot-starter-security` - Authentication and authorization
- `h2` - Development database
- `postgresql` - Production database
- `lombok` - Reduce boilerplate code

### Frontend
- `react` & `react-dom` - Core React framework
- `react-router-dom` - Client-side routing
- `@monaco-editor/react` - Code editor integration
- `axios` - HTTP client for API calls
- `lucide-react` - Icon library
- `framer-motion` - Advanced animations

## Development Workflow
1. **Backend First**: Implement JPA entities, repositories, services, controllers
2. **Frontend Integration**: Create React components consuming REST APIs
3. **Design System**: Apply AWS-inspired styling with responsive animations
4. **Content Population**: Add learning modules and interview questions via DataInitializer
5. **Testing**: Validate both backend APIs and frontend functionality
6. **Documentation**: Update README, conversation logs, and development guides

## Configuration Management
- **Development**: H2 in-memory database, hot reload enabled
- **Production**: PostgreSQL with connection pooling, optimized builds
- **Environment Variables**: Database credentials, API keys externalized
- **Profiles**: Spring profiles for different environments (dev, prod, test)

## Performance Considerations
- **Backend**: JPA query optimization, caching strategies, connection pooling
- **Frontend**: Code splitting, lazy loading, hardware-accelerated animations
- **Database**: Proper indexing, query optimization, connection management
- **Build**: Maven frontend plugin for integrated builds, Vite for fast development