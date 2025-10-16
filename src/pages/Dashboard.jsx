import React from 'react'
import { Link } from 'react-router-dom'
import { 
  Coffee, 
  Leaf, 
  Atom, 
  Database, 
  Zap, 
  Network, 
  MessageSquare, 
  Code,
  Target,
  Clock,
  TrendingUp,
  Award
} from 'lucide-react'

const Dashboard = () => {
  const learningPaths = [
    {
      title: 'Java Mastery Path',
      description: 'From basics to advanced concepts, JVM internals, and performance optimization',
      icon: Coffee,
      path: '/java-fundamentals',
      progress: 0,
      estimatedHours: 120,
      topics: ['Core Java', 'Collections', 'Concurrency', 'JVM Internals', 'Performance Tuning']
    },
    {
      title: 'Spring Ecosystem',
      description: 'Spring Boot, Security, Data, Cloud, and Microservices architecture',
      icon: Leaf,
      path: '/spring-framework',
      progress: 0,
      estimatedHours: 80,
      topics: ['Spring Boot', 'Spring Security', 'Spring Data', 'Spring Cloud', 'Testing']
    },
    {
      title: 'React Development',
      description: 'Modern React with hooks, context, performance optimization, and Next.js',
      icon: Atom,
      path: '/react-development',
      progress: 0,
      estimatedHours: 60,
      topics: ['Hooks', 'Context API', 'Performance', 'Testing', 'Next.js']
    },
    {
      title: 'Data Structures',
      description: 'Arrays, LinkedLists, Trees, Graphs, and advanced data structures',
      icon: Database,
      path: '/data-structures',
      progress: 0,
      estimatedHours: 40,
      topics: ['Arrays', 'Trees', 'Graphs', 'Hash Tables', 'Advanced Structures']
    },
    {
      title: 'Algorithms',
      description: 'Sorting, searching, dynamic programming, and algorithmic thinking',
      icon: Zap,
      path: '/algorithms',
      progress: 0,
      estimatedHours: 50,
      topics: ['Sorting', 'Searching', 'Dynamic Programming', 'Greedy', 'Graph Algorithms']
    },
    {
      title: 'System Design',
      description: 'Scalable systems, distributed architecture, and real-world case studies',
      icon: Network,
      path: '/system-design',
      progress: 0,
      estimatedHours: 70,
      topics: ['Scalability', 'Databases', 'Caching', 'Load Balancing', 'Microservices']
    }
  ]

  const interviewStats = {
    totalQuestions: 2847,
    companiesIncluded: ['Amazon', 'Google', 'Microsoft', 'Meta', 'Apple', 'Netflix', 'Uber', 'Airbnb'],
    difficultyBreakdown: {
      easy: 892,
      medium: 1456,
      hard: 499
    }
  }

  return (
    <div>
      {/* Hero Section */}
      <div className="card" style={{ 
        background: 'linear-gradient(135deg, var(--primary-blue) 0%, var(--secondary-blue) 100%)',
        color: 'var(--white)',
        marginBottom: '2rem'
      }}>
        <div style={{ textAlign: 'center', padding: '2rem 0' }}>
          <h1 style={{ fontSize: '2.5rem', marginBottom: '1rem', color: 'var(--white)' }}>
            Master Software Engineering
          </h1>
          <p style={{ fontSize: '1.2rem', marginBottom: '2rem', color: '#e1e5e9' }}>
            The most comprehensive learning portal for Java, React, System Design, and FAANG interviews
          </p>
          <div className="flex justify-center gap-4">
            <Link to="/code-editor" className="btn btn-primary" style={{ fontSize: '1rem', padding: '0.75rem 1.5rem' }}>
              <Code size={20} />
              Start Coding
            </Link>
            <Link to="/interview-prep" className="btn btn-secondary" style={{ 
              fontSize: '1rem', 
              padding: '0.75rem 1.5rem',
              backgroundColor: 'rgba(255, 255, 255, 0.1)',
              border: '1px solid rgba(255, 255, 255, 0.3)',
              color: 'var(--white)'
            }}>
              <MessageSquare size={20} />
              Interview Questions
            </Link>
          </div>
        </div>
      </div>

      {/* Quick Stats */}
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))', gap: '1rem', marginBottom: '2rem' }}>
        <div className="card" style={{ textAlign: 'center' }}>
          <Target size={32} style={{ color: 'var(--accent-orange)', marginBottom: '0.5rem' }} />
          <h3>{interviewStats.totalQuestions.toLocaleString()}</h3>
          <p>Interview Questions</p>
        </div>
        <div className="card" style={{ textAlign: 'center' }}>
          <Award size={32} style={{ color: 'var(--success-green)', marginBottom: '0.5rem' }} />
          <h3>{interviewStats.companiesIncluded.length}</h3>
          <p>Top Tech Companies</p>
        </div>
        <div className="card" style={{ textAlign: 'center' }}>
          <Clock size={32} style={{ color: 'var(--primary-blue)', marginBottom: '0.5rem' }} />
          <h3>420+</h3>
          <p>Hours of Content</p>
        </div>
        <div className="card" style={{ textAlign: 'center' }}>
          <TrendingUp size={32} style={{ color: 'var(--accent-orange)', marginBottom: '0.5rem' }} />
          <h3>0%</h3>
          <p>Overall Progress</p>
        </div>
      </div>

      {/* Learning Paths */}
      <div className="card">
        <div className="card-header">
          <h2 className="card-title">Learning Paths</h2>
          <p style={{ margin: 0, color: 'var(--text-secondary)' }}>
            Structured curriculum designed for senior developer roles
          </p>
        </div>

        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(350px, 1fr))', gap: '1.5rem' }}>
          {learningPaths.map((path, index) => {
            const Icon = path.icon
            return (
              <Link 
                key={index}
                to={path.path}
                style={{ textDecoration: 'none', color: 'inherit' }}
              >
                <div className="card" style={{ 
                  height: '100%',
                  transition: 'transform 0.2s, box-shadow 0.2s',
                  cursor: 'pointer'
                }}
                onMouseEnter={(e) => {
                  e.currentTarget.style.transform = 'translateY(-2px)'
                  e.currentTarget.style.boxShadow = '0 4px 12px rgba(0, 0, 0, 0.15)'
                }}
                onMouseLeave={(e) => {
                  e.currentTarget.style.transform = 'translateY(0)'
                  e.currentTarget.style.boxShadow = '0 1px 3px rgba(0, 0, 0, 0.1)'
                }}>
                  <div className="flex items-center gap-3 mb-3">
                    <Icon size={24} style={{ color: 'var(--accent-orange)' }} />
                    <h3 style={{ margin: 0 }}>{path.title}</h3>
                  </div>
                  
                  <p style={{ marginBottom: '1rem' }}>{path.description}</p>
                  
                  <div style={{ marginBottom: '1rem' }}>
                    <div className="flex justify-between items-center mb-2">
                      <span style={{ fontSize: '0.875rem', color: 'var(--text-secondary)' }}>
                        Progress: {path.progress}%
                      </span>
                      <span style={{ fontSize: '0.875rem', color: 'var(--text-secondary)' }}>
                        ~{path.estimatedHours}h
                      </span>
                    </div>
                    <div className="progress-bar">
                      <div 
                        className="progress-fill" 
                        style={{ width: `${path.progress}%` }}
                      ></div>
                    </div>
                  </div>
                  
                  <div>
                    <div style={{ fontSize: '0.875rem', color: 'var(--text-secondary)', marginBottom: '0.5rem' }}>
                      Key Topics:
                    </div>
                    <div style={{ display: 'flex', flexWrap: 'wrap', gap: '0.5rem' }}>
                      {path.topics.map((topic, topicIndex) => (
                        <span 
                          key={topicIndex}
                          style={{
                            fontSize: '0.75rem',
                            padding: '0.25rem 0.5rem',
                            backgroundColor: 'var(--light-blue)',
                            color: 'var(--text-primary)',
                            borderRadius: '12px'
                          }}
                        >
                          {topic}
                        </span>
                      ))}
                    </div>
                  </div>
                </div>
              </Link>
            )
          })}
        </div>
      </div>

      {/* Interview Preparation Overview */}
      <div className="card">
        <div className="card-header">
          <h2 className="card-title">Interview Preparation</h2>
          <Link to="/interview-prep" className="btn btn-primary">
            Start Practicing
          </Link>
        </div>

        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))', gap: '1rem' }}>
          <div style={{ textAlign: 'center', padding: '1rem' }}>
            <div style={{ fontSize: '2rem', fontWeight: 'bold', color: 'var(--success-green)' }}>
              {interviewStats.difficultyBreakdown.easy}
            </div>
            <div style={{ color: 'var(--text-secondary)' }}>Easy Questions</div>
          </div>
          <div style={{ textAlign: 'center', padding: '1rem' }}>
            <div style={{ fontSize: '2rem', fontWeight: 'bold', color: 'var(--accent-orange)' }}>
              {interviewStats.difficultyBreakdown.medium}
            </div>
            <div style={{ color: 'var(--text-secondary)' }}>Medium Questions</div>
          </div>
          <div style={{ textAlign: 'center', padding: '1rem' }}>
            <div style={{ fontSize: '2rem', fontWeight: 'bold', color: 'var(--error-red)' }}>
              {interviewStats.difficultyBreakdown.hard}
            </div>
            <div style={{ color: 'var(--text-secondary)' }}>Hard Questions</div>
          </div>
        </div>

        <div style={{ marginTop: '1rem', padding: '1rem', backgroundColor: 'var(--light-blue)', borderRadius: '8px' }}>
          <h4 style={{ marginBottom: '0.5rem' }}>Companies Covered:</h4>
          <div style={{ display: 'flex', flexWrap: 'wrap', gap: '0.5rem' }}>
            {interviewStats.companiesIncluded.map((company, index) => (
              <span 
                key={index}
                style={{
                  padding: '0.25rem 0.75rem',
                  backgroundColor: 'var(--white)',
                  border: '1px solid var(--border-light)',
                  borderRadius: '16px',
                  fontSize: '0.875rem',
                  fontWeight: '500'
                }}
              >
                {company}
              </span>
            ))}
          </div>
        </div>
      </div>
    </div>
  )
}

export default Dashboard