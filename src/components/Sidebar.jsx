import React from 'react'
import { Link, useLocation } from 'react-router-dom'
import { 
  Home, 
  Coffee, 
  Leaf, 
  Atom, 
  Database, 
  Zap, 
  Network, 
  MessageSquare, 
  Code, 
  BookOpen,
  X,
  ChevronRight
} from 'lucide-react'

const Sidebar = ({ isOpen, onClose }) => {
  const location = useLocation()

  const navigationSections = [
    {
      title: 'Overview',
      items: [
        { path: '/', label: 'Dashboard', icon: Home },
        { path: '/notes', label: 'My Notes', icon: BookOpen },
        { path: '/code-editor', label: 'Code Playground', icon: Code }
      ]
    },
    {
      title: 'Core Technologies',
      items: [
        { path: '/java-fundamentals', label: 'Java Fundamentals', icon: Coffee },
        { path: '/spring-framework', label: 'Spring Framework', icon: Leaf },
        { path: '/react-development', label: 'React Development', icon: Atom }
      ]
    },
    {
      title: 'Computer Science',
      items: [
        { path: '/data-structures', label: 'Data Structures', icon: Database },
        { path: '/algorithms', label: 'Algorithms', icon: Zap },
        { path: '/system-design', label: 'System Design', icon: Network }
      ]
    },
    {
      title: 'Interview Preparation',
      items: [
        { path: '/interview-prep', label: 'Technical Questions', icon: MessageSquare }
      ]
    }
  ]

  return (
    <aside className={`sidebar ${isOpen ? 'open' : ''}`}>
      <div style={{ padding: '1rem', borderBottom: '1px solid var(--secondary-blue)' }}>
        <div className="flex items-center justify-between">
          <h2 style={{ color: 'var(--white)', fontSize: '1.1rem', margin: 0 }}>
            Learning Portal
          </h2>
          <button 
            onClick={onClose}
            style={{ 
              background: 'none', 
              border: 'none', 
              color: 'var(--white)', 
              cursor: 'pointer',
              padding: '0.25rem'
            }}
          >
            <X size={20} />
          </button>
        </div>
      </div>

      <nav>
        {navigationSections.map((section, sectionIndex) => (
          <div key={sectionIndex} className="nav-section">
            <div className="nav-section-title">
              {section.title}
            </div>
            {section.items.map((item, itemIndex) => {
              const Icon = item.icon
              const isActive = location.pathname === item.path
              
              return (
                <Link
                  key={itemIndex}
                  to={item.path}
                  className={`nav-item ${isActive ? 'active' : ''}`}
                  onClick={onClose}
                >
                  <Icon className="nav-item-icon" size={16} />
                  {item.label}
                  {isActive && <ChevronRight size={16} style={{ marginLeft: 'auto' }} />}
                </Link>
              )
            })}
          </div>
        ))}
      </nav>

      <div style={{ 
        position: 'absolute', 
        bottom: '1rem', 
        left: '1rem', 
        right: '1rem',
        padding: '1rem',
        backgroundColor: 'var(--secondary-blue)',
        borderRadius: '8px',
        fontSize: '0.75rem',
        color: '#8c9196'
      }}>
        <div style={{ marginBottom: '0.5rem', fontWeight: '600', color: 'var(--white)' }}>
          Progress Tracker
        </div>
        <div>Java Fundamentals: 0%</div>
        <div>Spring Framework: 0%</div>
        <div>React Development: 0%</div>
        <div>Interview Prep: 0%</div>
      </div>
    </aside>
  )
}

export default Sidebar