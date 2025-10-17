import React from 'react'
import { useParams, Link } from 'react-router-dom'
import { Coffee, Leaf, Atom, Database, Zap, Network, Play, CheckCircle, Clock, ArrowLeft, BookOpen } from 'lucide-react'

const ModuleDetail = () => {
  const { moduleId } = useParams()
  
  const moduleData = {
    nodejs: {
      title: 'Node.js Complete Mastery',
      description: '🎉 100% COMPLETE! Master Node.js from zero to FAANG senior level with 25 comprehensive topics, 700+ interview questions, and production projects.',
      icon: Zap,
      difficulty: 'Beginner to Expert',
      estimatedHours: 50,
      progress: 100,
      topics: [
        // ZeroToMastery Foundation (1-5)
        { id: 1, title: 'Node.js Core Concepts and Event Loop', completed: true, duration: '180 min', questions: 25 },
        { id: 2, title: 'Asynchronous Programming: Callbacks, Promises, Async/Await', completed: true, duration: '200 min', questions: 25 },
        { id: 3, title: 'Node.js Modules and Package Management', completed: true, duration: '160 min', questions: 25 },
        { id: 4, title: 'Express.js Framework and Middleware', completed: true, duration: '180 min', questions: 25 },
        { id: 5, title: 'Node.js Performance Optimization and Scaling', completed: true, duration: '200 min', questions: 25 },
        
        // ZeroToMastery Foundation (6-10)
        { id: 6, title: 'File I/O & Streams: Planets Project', completed: true, duration: '180 min', questions: 40 },
        { id: 7, title: 'Web Servers & HTTP Fundamentals', completed: true, duration: '150 min', questions: 35 },
        { id: 8, title: 'Full-Stack NASA Project', completed: true, duration: '240 min', questions: 50 },
        { id: 9, title: 'Testing APIs with Jest & Supertest', completed: true, duration: '180 min', questions: 45 },
        { id: 10, title: 'Database Integration: MongoDB & Mongoose', completed: true, duration: '200 min', questions: 50 },
        
        // Advanced Topics (11-15)
        { id: 11, title: 'REST API Integration: SpaceX Project', completed: true, duration: '180 min', questions: 45 },
        { id: 12, title: 'Authentication & Security: JWT, OAuth2, Auth0', completed: true, duration: '200 min', questions: 50 },
        { id: 13, title: 'Deployment & CI/CD Pipelines', completed: true, duration: '180 min', questions: 40 },
        { id: 14, title: 'GraphQL Implementation', completed: true, duration: '200 min', questions: 35 },
        { id: 15, title: 'WebSockets & Real-Time Applications', completed: true, duration: '180 min', questions: 45 },
        
        // Expert Topics (16-20)
        { id: 16, title: 'Microservices Architecture', completed: true, duration: '240 min', questions: 50 },
        { id: 17, title: 'Serverless & Cloud: AWS Lambda', completed: true, duration: '200 min', questions: 40 },
        { id: 18, title: 'Docker & Kubernetes', completed: true, duration: '220 min', questions: 45 },
        { id: 19, title: 'Monitoring & Logging', completed: true, duration: '180 min', questions: 35 },
        { id: 20, title: 'Security Best Practices', completed: true, duration: '200 min', questions: 40 },
        
        // FAANG Senior Enhancement (21-25)
        { id: 21, title: 'Advanced Performance Tuning', completed: true, duration: '240 min', questions: 50 },
        { id: 22, title: 'Distributed Systems', completed: true, duration: '260 min', questions: 55 },
        { id: 23, title: 'Event-Driven Architecture', completed: true, duration: '240 min', questions: 50 },
        { id: 24, title: 'Production Debugging', completed: true, duration: '220 min', questions: 45 },
        { id: 25, title: 'Scalability Patterns', completed: true, duration: '260 min', questions: 55 }
      ]
    },
    java: {
      title: 'Java Fundamentals',
      description: 'Master Java programming from basics to advanced concepts including OOP, collections, and concurrency.',
      icon: Coffee,
      difficulty: 'Beginner to Intermediate',
      estimatedHours: 40,
      progress: 75,
      topics: [
        { id: 1, title: 'Variables and Data Types', completed: true, duration: '45 min' },
        { id: 2, title: 'Control Structures', completed: true, duration: '60 min' },
        { id: 3, title: 'Object-Oriented Programming', completed: true, duration: '90 min' },
        { id: 4, title: 'Collections Framework', completed: false, duration: '75 min' },
        { id: 5, title: 'Exception Handling', completed: false, duration: '50 min' },
        { id: 6, title: 'Multithreading & Concurrency', completed: false, duration: '120 min' }
      ]
    },
    spring: {
      title: 'Spring Framework',
      description: 'Learn Spring Boot, Spring Security, Spring Data, and microservices architecture.',
      icon: Leaf,
      difficulty: 'Intermediate',
      estimatedHours: 35,
      progress: 30,
      topics: [
        { id: 1, title: 'Spring Boot Fundamentals', completed: true, duration: '60 min' },
        { id: 2, title: 'Dependency Injection', completed: false, duration: '45 min' },
        { id: 3, title: 'Spring Data JPA', completed: false, duration: '90 min' },
        { id: 4, title: 'Spring Security', completed: false, duration: '120 min' },
        { id: 5, title: 'RESTful APIs', completed: false, duration: '75 min' }
      ]
    },
    react: {
      title: 'React Development',
      description: 'Modern React development with hooks, context, performance optimization, and testing.',
      icon: Atom,
      difficulty: 'Intermediate',
      estimatedHours: 30,
      progress: 0,
      topics: [
        { id: 1, title: 'React Fundamentals', completed: false, duration: '60 min' },
        { id: 2, title: 'Hooks and State Management', completed: false, duration: '90 min' },
        { id: 3, title: 'Context API', completed: false, duration: '45 min' },
        { id: 4, title: 'Performance Optimization', completed: false, duration: '75 min' },
        { id: 5, title: 'Testing React Applications', completed: false, duration: '60 min' }
      ]
    }
  }

  const module = moduleData[moduleId] || {
    title: 'Module Not Found',
    description: 'This module is coming soon...',
    icon: BookOpen,
    difficulty: 'Unknown',
    estimatedHours: 0,
    progress: 0,
    topics: []
  }

  const Icon = module.icon
  const completedTopics = module.topics.filter(topic => topic.completed).length

  return (
    <div className="page-container">
      <div className="module-header">
        <Link to="/learning-path" className="btn btn-ghost">
          <ArrowLeft size={16} />
          Back to Learning Path
        </Link>
        
        <div className="module-title-section">
          <div className="module-icon-large">
            <Icon size={32} />
          </div>
          <div>
            <h1>{module.title}</h1>
            <p>{module.description}</p>
            <div className="module-meta">
              <span className="badge badge-secondary">{module.difficulty}</span>
              <span className="meta-item">
                <Clock size={14} />
                {module.estimatedHours} hours
              </span>
              <span className="meta-item">
                {completedTopics}/{module.topics.length} topics completed
              </span>
            </div>
          </div>
        </div>
      </div>

      <div className="module-content">
        <div className="module-progress-card">
          <h3>Your Progress</h3>
          <div className="progress-stats">
            <div className="stat">
              <div className="stat-value">{module.progress}%</div>
              <div className="stat-label">Complete</div>
            </div>
            <div className="stat">
              <div className="stat-value">{completedTopics}</div>
              <div className="stat-label">Topics Done</div>
            </div>
            <div className="stat">
              <div className="stat-value">{module.topics.length - completedTopics}</div>
              <div className="stat-label">Remaining</div>
            </div>
          </div>
          <div className="progress">
            <div 
              className="progress-bar" 
              style={{ width: `${module.progress}%` }}
            ></div>
          </div>
        </div>

        <div className="topics-section">
          <h3>Course Content</h3>
          <div className="topics-list">
            {module.topics.map((topic, index) => (
              <div key={topic.id} className={`topic-item ${topic.completed ? 'completed' : ''}`}>
                <div className="topic-number">
                  {topic.completed ? (
                    <CheckCircle size={20} className="completed-icon" />
                  ) : (
                    <span className="topic-index">{index + 1}</span>
                  )}
                </div>
                <div className="topic-content">
                  <h4>{topic.title}</h4>
                  <div className="topic-meta">
                    <span className="topic-duration">
                      <Clock size={14} />
                      {topic.duration}
                    </span>
                  </div>
                </div>
                <div className="topic-action">
                  <button className="btn btn-ghost btn-sm">
                    <Play size={14} />
                    {topic.completed ? 'Review' : 'Start'}
                  </button>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  )
}

export default ModuleDetail