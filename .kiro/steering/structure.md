# Project Organization & Folder Structure

## Overall Architecture
This is a full-stack learning portal with Spring Boot backend and React frontend, organized as a Maven project with integrated frontend build.

## Root Level Structure
```
comprehensive-dev-portal/
├── .kiro/                               # Kiro steering and configuration
│   └── steering/                        # AI assistant guidance documents
├── src/                                 # Backend Java source code
├── frontend/                            # React frontend application
├── target/                              # Maven build output
├── pom.xml                              # Maven project configuration
├── README.md                            # Project overview and setup
├── DEVELOPMENT_GUIDE.md                 # Comprehensive development guide
├── PROJECT_CONVERSATION_LOG.md          # Complete conversation history
├── PROJECT_SCOPE_AND_TRACKING.md       # Master project reference
├── CURRENT_STATUS.md                    # Real-time development state
├── PROJECT_AUTOMATION_MANAGER.md        # Automation framework
├── AUTOMATION_REVIEW_CHECKLIST.md       # Review and approval checklist
└── SENIOR_DEVELOPER_READINESS_ANALYSIS.md # FAANG interview readiness
```

## Backend Structure (src/main/java/com/learningportal/)
```
src/main/java/com/learningportal/
├── ComprehensiveDevPortalApplication.java  # Main Spring Boot application
├── config/
│   └── DataInitializer.java               # Database content initialization
├── controller/                            # REST API endpoints
│   ├── UserController.java               # User management APIs
│   └── LearningModuleController.java     # Learning content APIs
├── service/                               # Business logic layer
│   ├── UserService.java                  # User business operations
│   ├── LearningModuleService.java        # Learning content operations
│   └── UserProgressService.java          # Analytics and progress tracking
├── repository/                            # Data access layer
│   ├── UserRepository.java               # User data operations
│   ├── LearningModuleRepository.java     # Module data operations
│   ├── UserProgressRepository.java       # Progress data operations
│   ├── InterviewQuestionRepository.java  # Question data operations
│   └── UserNoteRepository.java           # Notes data operations
└── model/                                 # JPA entities
    ├── User.java                          # User entity with authentication
    ├── LearningModule.java                # Learning module entity
    ├── Topic.java                         # Topic entity with relationships
    ├── InterviewQuestion.java             # Interview question entity
    ├── UserProgress.java                  # Progress tracking entity
    └── UserNote.java                      # Note-taking entity
```

## Frontend Structure (frontend/src/)
```
frontend/src/
├── main.jsx                              # React application entry point
├── App.jsx                               # Main application component
├── components/                           # Reusable React components
│   ├── layout/                           # Layout components
│   │   ├── Header.jsx                    # Professional header with search
│   │   └── Sidebar.jsx                   # GitHub-style navigation
│   ├── modern/                           # Modern UI components
│   │   ├── ModernLandingPage.jsx         # AWS-inspired landing page
│   │   └── ModernDashboard.jsx           # Enhanced dashboard
│   └── ui/                               # Basic UI components
│       └── LoadingSpinner.jsx            # Loading states
├── pages/                                # Page components
│   ├── Dashboard.jsx                     # Interactive dashboard
│   ├── LearningPath.jsx                  # Learning progression
│   ├── ModuleDetail.jsx                  # Module content view
│   ├── InterviewPrep.jsx                 # Interview preparation
│   └── CodePlayground.jsx                # Interactive coding environment
├── context/                              # React context providers
│   ├── AuthContext.jsx                   # Authentication state
│   └── LearningContext.jsx               # Learning progress state
└── styles/                               # CSS design system
    ├── aws-design-system.css             # AWS-inspired base styles
    ├── modern-design-system.css          # Advanced design tokens
    ├── aws-glow-effects.css              # Animation and effects
    ├── layout.css                        # Layout and navigation
    ├── components.css                    # Component-specific styles
    └── dashboard.css                     # Dashboard styling
```

## Configuration Files
```
src/main/resources/
├── application.yml                       # Development configuration
├── application-production.yml           # Production PostgreSQL config
└── static/                              # Served static files (built frontend)

frontend/
├── package.json                         # Frontend dependencies and scripts
├── vite.config.js                       # Vite build configuration
└── dist/                                # Built frontend (copied to static/)
```

## Documentation Structure
- **README.md** - Project overview, setup instructions, current status
- **DEVELOPMENT_GUIDE.md** - Step-by-step technical implementation guide
- **PROJECT_CONVERSATION_LOG.md** - Complete conversation history for context
- **PROJECT_SCOPE_AND_TRACKING.md** - Master project reference and progress
- **CURRENT_STATUS.md** - Real-time development state and session continuity
- **PROJECT_AUTOMATION_MANAGER.md** - Comprehensive automation framework
- **AUTOMATION_REVIEW_CHECKLIST.md** - Review checklist for automation phases
- **SENIOR_DEVELOPER_READINESS_ANALYSIS.md** - FAANG interview preparation analysis

## Key Architectural Patterns

### Backend Patterns
- **Layered Architecture**: Controller → Service → Repository → Entity
- **Dependency Injection**: Constructor injection for all dependencies
- **Repository Pattern**: Spring Data JPA with custom queries
- **DTO Pattern**: Separate data transfer objects for API responses
- **Configuration Classes**: Centralized configuration management

### Frontend Patterns
- **Component Composition**: Reusable components with props and children
- **Context Pattern**: Global state management with React Context
- **Custom Hooks**: Reusable stateful logic extraction
- **Route-based Code Splitting**: Lazy loading for performance
- **CSS Modules**: Scoped styling with design system tokens

### Data Flow
1. **Frontend** makes HTTP requests via Axios
2. **Controllers** handle REST endpoints and validation
3. **Services** implement business logic and orchestration
4. **Repositories** handle database operations via JPA
5. **Entities** represent database tables with relationships

## Content Organization Strategy

### Complete Learning Domains (Zero to Expert)
- **Java**: Fundamentals → Advanced → JVM Internals → Performance Tuning
- **Node.js**: Fundamentals → Advanced Patterns → Microservices → Performance
- **React**: Fundamentals → Advanced Patterns → Performance Optimization
- **Databases**: SQL → NoSQL → Distributed Systems → Optimization
- **Data Structures**: All 30+ structures (basic → advanced → obscure)
- **Algorithms**: All categories with optimization techniques
- **DevOps**: CI/CD → Containerization → Orchestration → Monitoring
- **Cloud/AWS**: General concepts → AWS Solutions Architect Associate Exam Prep
- **Security**: Application → Infrastructure → Best Practices
- **System Design**: Scalability → Distributed Systems → Real-world Case Studies

### Learning Content Structure
- **Modules** contain multiple **Topics** with embedded code examples
- **Topics** contain **Interactive Code Terminal** for hands-on practice
- **Topics** contain embedded **Interview Questions** with multiple solutions
- **Questions** have **Complexity Analysis** and **Optimization Paths**
- **Progress** tracked per user per topic with spaced repetition analytics

### Dual Question Organization System
1. **Contextual Integration**: Questions embedded within relevant topic sections
   - Immediate practice after learning concepts
   - Code examples with terminal execution
   - Multiple solution approaches with explanations
2. **Centralized Question Hub**: All 8000+ questions organized by:
   - **Subject**: Java, React, Algorithms, System Design, etc.
   - **Topic**: Arrays, Trees, Spring Security, etc.
   - **Company**: Amazon, Google, Meta, Microsoft, etc.
   - **Difficulty**: Easy → Medium → Hard → Expert

### Advanced Note-Taking System
- **Embedded Notes**: Rich text editor in every topic section
  - Code snippet support with syntax highlighting
  - Mathematical formula rendering (LaTeX)
  - Cross-topic linking and references
- **Centralized Note Hub**: Smart categorization and organization
  - Auto-tagging by topic and difficulty
  - Search across all notes with fuzzy matching
  - Knowledge graph visualization
- **Export & Collaboration**: PDF, Markdown, sharing capabilities

## Build Integration
- **Maven Frontend Plugin** handles Node.js/npm installation and React build
- **Built frontend** copied to `src/main/resources/static/` for Spring Boot serving
- **Single JAR deployment** contains both backend and frontend
- **Development mode** runs frontend and backend servers separately with proxy