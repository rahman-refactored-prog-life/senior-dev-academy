import React from 'react'
import { Link } from 'react-router-dom'
import { Coffee, Leaf, Atom, Database, Zap, Network, CheckCircle, Clock, ArrowRight } from 'lucide-react'

const LearningPath = () => {
  const learningPath = [
    {
      id: 'java',
      title: 'Java Fundamentals',
      description: 'Master Java programming from basics to advanced concepts',
      icon: Coffee,
      status: 'in-progress',
      progress: 75,
      estimatedHours: 40,
      topics: ['Variables & Data Types', 'OOP Concepts', 'Collections', 'Concurrency']
    },
    {
      id: 'spring',
      title: 'Spring Framework',
      description: 'Learn Spring Boot, Security, and Data',
      icon: Leaf,
      status: 'in-progress',
      progress: 30,
      estimatedHours: 35,
      topics: ['Spring Boot', 'Spring Security', 'Spring Data', 'Testing']
    },
    {
      id: 'react',
      title: 'React Development',
      description: 'Modern React with hooks and performance optimization',
      icon: Atom,
      status: 'not-started',
      progress: 0,
      estimatedHours: 30,
      topics: ['Components', 'Hooks', 'State Management', 'Performance']
    },
    {
      id: 'data-structures',
      title: 'Data Structures',
      description: 'Arrays, LinkedLists, Trees, and Graphs',
      icon: Database,
      status: 'not-started',
      progress: 0,
      estimatedHours: 25,
      topics: ['Arrays', 'Trees', 'Graphs', 'Hash Tables']
    },
    {
      id: 'algorithms',
      title: 'Algorithms',
      description: 'Sorting, searching, and dynamic programming',
      icon: Zap,
      status: 'not-started',
      progress: 0,
      estimatedHours: 30,
      topics: ['Sorting', 'Searching', 'Dynamic Programming', 'Greedy']
    },
    {
      id: 'system-design',
      title: 'System Design',
      description: 'Scalable systems and distributed architecture',
      icon: Network,
      status: 'not-started',
      progress: 0,
      estimatedHours: 40,
      topics: ['Scalability', 'Databases', 'Caching', 'Load Balancing']
    }
  ]

  const getStatusColor = (status) => {
    switch (status) {
      case 'completed': return 'var(--aws-success)'
      case 'in-progress': return 'var(--color-primary)'
      case 'not-started': return 'var(--color-text-muted)'
      default: return 'var(--color-text-muted)'
    }
  }

  const getStatusIcon = (status) => {
    switch (status) {
      case 'completed': return CheckCircle
      case 'in-progress': return Clock
      case 'not-started': return Clock
      default: return Clock
    }
  }

  return (
    <div className="page-container">
      <div className="page-header">
        <h1>Your Learning Path</h1>
        <p>Structured curriculum designed for senior developer roles at top tech companies</p>
      </div>

      <div className="learning-path-container">
        {learningPath.map((module, index) => {
          const Icon = module.icon
          const StatusIcon = getStatusIcon(module.status)
          
          return (
            <div key={module.id} className="learning-path-item">
              <div className="path-connector">
                {index < learningPath.length - 1 && (
                  <div className="connector-line"></div>
                )}
              </div>
              
              <div className="path-card">
                <div className="path-header">
                  <div className="path-icon">
                    <Icon size={24} />
                  </div>
                  <div className="path-status">
                    <StatusIcon size={16} style={{ color: getStatusColor(module.status) }} />
                    <span style={{ color: getStatusColor(module.status) }}>
                      {module.status.replace('-', ' ').toUpperCase()}
                    </span>
                  </div>
                </div>
                
                <div className="path-content">
                  <h3>{module.title}</h3>
                  <p>{module.description}</p>
                  
                  <div className="path-progress">
                    <div className="progress-info">
                      <span>Progress: {module.progress}%</span>
                      <span>{module.estimatedHours}h estimated</span>
                    </div>
                    <div className="progress">
                      <div 
                        className="progress-bar" 
                        style={{ width: `${module.progress}%` }}
                      ></div>
                    </div>
                  </div>
                  
                  <div className="path-topics">
                    <h4>Key Topics:</h4>
                    <div className="topic-tags">
                      {module.topics.map((topic, topicIndex) => (
                        <span key={topicIndex} className="topic-tag">
                          {topic}
                        </span>
                      ))}
                    </div>
                  </div>
                  
                  <div className="path-actions">
                    <Link 
                      to={`/modules/${module.id}`} 
                      className="btn btn-primary"
                    >
                      {module.progress > 0 ? 'Continue Learning' : 'Start Module'}
                      <ArrowRight size={16} />
                    </Link>
                  </div>
                </div>
              </div>
            </div>
          )
        })}
      </div>
    </div>
  )
}

export default LearningPath