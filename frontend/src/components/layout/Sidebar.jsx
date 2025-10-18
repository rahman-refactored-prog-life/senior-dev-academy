import React from 'react'
import { Link, useLocation } from 'react-router-dom'
import { 
  Home, 
  BookOpen, 
  Code, 
  MessageSquare, 
  BarChart3, 
  StickyNote, 
  User,
  ChevronLeft,
  ChevronRight,
  Coffee,
  Leaf,
  Atom,
  Database,
  Zap,
  Network
} from 'lucide-react'

const Sidebar = ({ isOpen, onClose }) => {
  const location = useLocation()

  const navigationSections = [
    {
      title: 'Overview',
      items: [
        { path: '/', label: 'Dashboard', icon: Home },
        { path: '/modern-dashboard', label: 'Modern Dashboard', icon: BarChart3 },
        { path: '/learning-path', label: 'Learning Path', icon: BookOpen },
        { path: '/progress', label: 'My Progress', icon: BarChart3 }
      ]
    },
    {
      title: 'Modern UI',
      items: [
        { path: '/modern', label: 'Landing Page', icon: Zap }
      ]
    },
    {
      title: 'Learning Modules',
      items: [
        { path: '/modules/java', label: 'Java Fundamentals', icon: Coffee },
        { path: '/modules/nodejs', label: 'Node.js', icon: Zap },
        { path: '/modules/spring', label: 'Spring Framework', icon: Leaf },
        { path: '/modules/react', label: 'React Development', icon: Atom },
        { path: '/modules/data-structures', label: 'Data Structures', icon: Database },
        { path: '/modules/algorithms', label: 'Algorithms', icon: Code },
        { path: '/modules/system-design', label: 'System Design', icon: Network }
      ]
    },
    {
      title: 'Practice & Tools',
      items: [
        { path: '/interview-prep', label: 'Interview Prep', icon: MessageSquare },
        { path: '/code-playground', label: 'Code Playground', icon: Code },
        { path: '/notes', label: 'My Notes', icon: StickyNote }
      ]
    },
    {
      title: 'Account',
      items: [
        { path: '/profile', label: 'Profile', icon: User }
      ]
    }
  ]

  return (
    <>
      <aside className={`aws-console-sidebar ${isOpen ? 'open' : ''}`}>
        {/* Sidebar Header */}
        <div className="sidebar-header">
          <div className="sidebar-brand">
            <BookOpen size={24} className="brand-icon" />
            <span className="brand-text">Learning Portal</span>
          </div>
          <button 
            onClick={onClose}
            className="sidebar-close-btn"
            aria-label="Close sidebar"
            title="Close navigation"
          >
            <ChevronLeft size={20} />
          </button>
        </div>

        {/* Navigation */}
        <nav className="sidebar-nav">
          {navigationSections.map((section, sectionIndex) => (
            <div key={sectionIndex} className="nav-section">
              <div className="nav-section-title">
                {section.title}
              </div>
              <div className="nav-items">
                {section.items.map((item, itemIndex) => {
                  const Icon = item.icon
                  const isActive = location.pathname === item.path
                  
                  return (
                    <Link
                      key={itemIndex}
                      to={item.path}
                      className={`nav-item ${isActive ? 'active' : ''} ${item.highlight ? 'highlight' : ''}`}
                      onClick={onClose}
                      title={item.highlight ? 'Complete Node.js curriculum with 25 topics and 700+ questions!' : ''}
                    >
                      <Icon className="nav-item-icon" size={18} />
                      <span className="nav-item-label">{item.label}</span>
                      {isActive && <ChevronRight size={16} className="nav-item-arrow" />}
                    </Link>
                  )
                })}
              </div>
            </div>
          ))}
        </nav>

        {/* Progress Summary */}
        <div className="sidebar-footer">
          <div className="progress-summary">
            <h4>Learning Progress</h4>
            <div className="progress-item">
              <span>Java Fundamentals</span>
              <div className="progress">
                <div className="progress-bar" style={{ width: '75%' }}></div>
              </div>
              <span className="progress-text">75%</span>
            </div>
            <div className="progress-item">
              <span>Spring Framework</span>
              <div className="progress">
                <div className="progress-bar" style={{ width: '30%' }}></div>
              </div>
              <span className="progress-text">30%</span>
            </div>
            <div className="progress-item">
              <span>React Development</span>
              <div className="progress">
                <div className="progress-bar" style={{ width: '0%' }}></div>
              </div>
              <span className="progress-text">0%</span>
            </div>
          </div>
        </div>
      </aside>
    </>
  )
}

export default Sidebar