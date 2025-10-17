import React from 'react'
import { Link } from 'react-router-dom'
import { Zap, CheckCircle, Trophy, Target, Code, Rocket, Star, Clock } from 'lucide-react'

const NodeJSShowcase = () => {
  const stats = [
    { label: 'Topics Complete', value: '25/25', icon: CheckCircle },
    { label: 'Interview Questions', value: '700+', icon: Target },
    { label: 'Learning Hours', value: '50+', icon: Clock },
    { label: 'Code Examples', value: '100+', icon: Code }
  ]

  const phases = [
    {
      title: 'ZeroToMastery Foundation',
      subtitle: 'Topics 1-20',
      topics: [
        'Node.js Core & Event Loop',
        'Async Programming Mastery',
        'Express.js Framework',
        'File I/O & Streams',
        'NASA Mission Control Project',
        'Testing with Jest',
        'MongoDB Integration',
        'REST API Development',
        'Authentication & Security',
        'Deployment & CI/CD',
        'GraphQL Implementation',
        'WebSockets & Real-Time',
        'Microservices Architecture',
        'Serverless & AWS Lambda',
        'Docker & Kubernetes',
        'Monitoring & Logging',
        'Security Best Practices'
      ],
      color: '#667eea'
    },
    {
      title: 'FAANG Senior Enhancement',
      subtitle: 'Topics 21-25',
      topics: [
        'Advanced Performance Tuning',
        'Distributed Systems',
        'Event-Driven Architecture',
        'Production Debugging',
        'Scalability Patterns'
      ],
      color: '#f093fb'
    }
  ]

  const projects = [
    {
      name: 'NASA Mission Control',
      description: 'Full-stack mission control dashboard with real-time telemetry',
      tech: ['Node.js', 'Express', 'React', 'WebSockets']
    },
    {
      name: 'Kepler Planets Analysis',
      description: 'Space telescope data processing with advanced streams',
      tech: ['Node.js', 'Streams', 'CSV Processing', 'Data Analysis']
    },
    {
      name: 'SpaceX API Integration',
      description: 'Production-grade API client with caching and retry logic',
      tech: ['Node.js', 'REST APIs', 'Redis', 'Rate Limiting']
    }
  ]

  return (
    <div className="showcase-page" style={{ padding: '40px', maxWidth: '1400px', margin: '0 auto' }}>
      {/* Hero Section */}
      <div style={{
        background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
        borderRadius: '20px',
        padding: '60px',
        color: 'white',
        textAlign: 'center',
        marginBottom: '40px',
        boxShadow: '0 20px 60px rgba(102, 126, 234, 0.3)'
      }}>
        <div style={{ fontSize: '80px', marginBottom: '20px' }}>🎉</div>
        <h1 style={{ fontSize: '48px', margin: '0 0 16px 0', fontWeight: 'bold' }}>
          Node.js Complete Mastery
        </h1>
        <p style={{ fontSize: '24px', opacity: 0.9, marginBottom: '32px' }}>
          100% Achievement Unlocked • Industry-Leading Curriculum
        </p>
        <div style={{ display: 'flex', gap: '16px', justifyContent: 'center', flexWrap: 'wrap' }}>
          <div style={{ background: 'rgba(255,255,255,0.2)', padding: '12px 24px', borderRadius: '24px' }}>
            <strong>25</strong> Topics
          </div>
          <div style={{ background: 'rgba(255,255,255,0.2)', padding: '12px 24px', borderRadius: '24px' }}>
            <strong>700+</strong> Questions
          </div>
          <div style={{ background: 'rgba(255,255,255,0.2)', padding: '12px 24px', borderRadius: '24px' }}>
            <strong>50+</strong> Hours
          </div>
          <div style={{ background: 'rgba(255,255,255,0.2)', padding: '12px 24px', borderRadius: '24px' }}>
            <strong>100+</strong> Examples
          </div>
        </div>
      </div>

      {/* Stats Grid */}
      <div style={{ 
        display: 'grid', 
        gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))', 
        gap: '24px',
        marginBottom: '40px'
      }}>
        {stats.map((stat, index) => {
          const Icon = stat.icon
          return (
            <div key={index} style={{
              background: 'white',
              padding: '24px',
              borderRadius: '12px',
              boxShadow: '0 4px 12px rgba(0,0,0,0.1)',
              textAlign: 'center'
            }}>
              <Icon size={32} style={{ color: '#667eea', marginBottom: '12px' }} />
              <div style={{ fontSize: '32px', fontWeight: 'bold', color: '#667eea' }}>
                {stat.value}
              </div>
              <div style={{ color: '#666', marginTop: '8px' }}>{stat.label}</div>
            </div>
          )
        })}
      </div>

      {/* Curriculum Phases */}
      <div style={{ marginBottom: '40px' }}>
        <h2 style={{ fontSize: '32px', marginBottom: '24px', textAlign: 'center' }}>
          Complete Curriculum
        </h2>
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(400px, 1fr))', gap: '24px' }}>
          {phases.map((phase, index) => (
            <div key={index} style={{
              background: 'white',
              borderRadius: '12px',
              padding: '32px',
              boxShadow: '0 4px 12px rgba(0,0,0,0.1)',
              borderTop: `4px solid ${phase.color}`
            }}>
              <h3 style={{ fontSize: '24px', marginBottom: '8px', color: phase.color }}>
                {phase.title}
              </h3>
              <p style={{ color: '#666', marginBottom: '20px' }}>{phase.subtitle}</p>
              <div style={{ display: 'flex', flexDirection: 'column', gap: '12px' }}>
                {phase.topics.map((topic, idx) => (
                  <div key={idx} style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
                    <CheckCircle size={20} style={{ color: '#10b981', flexShrink: 0 }} />
                    <span>{topic}</span>
                  </div>
                ))}
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* Projects Showcase */}
      <div style={{ marginBottom: '40px' }}>
        <h2 style={{ fontSize: '32px', marginBottom: '24px', textAlign: 'center' }}>
          Production Projects
        </h2>
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(350px, 1fr))', gap: '24px' }}>
          {projects.map((project, index) => (
            <div key={index} style={{
              background: 'white',
              borderRadius: '12px',
              padding: '24px',
              boxShadow: '0 4px 12px rgba(0,0,0,0.1)'
            }}>
              <Rocket size={32} style={{ color: '#667eea', marginBottom: '16px' }} />
              <h3 style={{ fontSize: '20px', marginBottom: '8px' }}>{project.name}</h3>
              <p style={{ color: '#666', marginBottom: '16px' }}>{project.description}</p>
              <div style={{ display: 'flex', gap: '8px', flexWrap: 'wrap' }}>
                {project.tech.map((tech, idx) => (
                  <span key={idx} style={{
                    background: '#f3f4f6',
                    padding: '4px 12px',
                    borderRadius: '12px',
                    fontSize: '14px',
                    color: '#667eea'
                  }}>
                    {tech}
                  </span>
                ))}
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* Call to Action */}
      <div style={{
        background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
        borderRadius: '12px',
        padding: '40px',
        textAlign: 'center',
        color: 'white'
      }}>
        <Trophy size={48} style={{ marginBottom: '16px' }} />
        <h2 style={{ fontSize: '32px', marginBottom: '16px' }}>
          Ready for FAANG Senior Roles
        </h2>
        <p style={{ fontSize: '18px', opacity: 0.9, marginBottom: '24px' }}>
          This comprehensive curriculum prepares you for L5/L6 positions at top tech companies
        </p>
        <Link 
          to="/modules/nodejs" 
          className="btn"
          style={{
            background: 'white',
            color: '#667eea',
            padding: '12px 32px',
            borderRadius: '8px',
            fontWeight: 'bold',
            textDecoration: 'none',
            display: 'inline-block'
          }}
        >
          Explore All Topics →
        </Link>
      </div>
    </div>
  )
}

export default NodeJSShowcase
