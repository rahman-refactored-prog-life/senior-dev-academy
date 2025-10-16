import React, { useState } from 'react'
import { 
  Menu, 
  Search, 
  Bell, 
  User, 
  Settings,
  ChevronDown,
  BookOpen,
  Target,
  Zap
} from 'lucide-react'
import { useAuth } from '../../context/AuthContext'
import { useLearning } from '../../context/LearningContext'

/**
 * Header Component - AWS Console inspired navigation header
 * 
 * Features:
 * - GitHub-style hamburger menu (as requested)
 * - Global search with intelligent suggestions
 * - User profile dropdown
 * - Learning progress indicator
 * - Notification center
 * - Clean, professional AWS styling
 */
const Header = ({ onToggleSidebar }) => {
  const [searchQuery, setSearchQuery] = useState('')
  const [showUserMenu, setShowUserMenu] = useState(false)
  const [showNotifications, setShowNotifications] = useState(false)
  const { user } = useAuth()
  const { learningStats } = useLearning()

  const handleSearch = (e) => {
    e.preventDefault()
    // Implement global search functionality
    console.log('Searching for:', searchQuery)
  }

  const notifications = [
    {
      id: 1,
      type: 'achievement',
      title: 'New Achievement Unlocked!',
      message: 'You completed Java Fundamentals',
      time: '2 minutes ago',
      unread: true
    },
    {
      id: 2,
      type: 'reminder',
      title: 'Daily Learning Streak',
      message: 'Keep your 7-day streak going!',
      time: '1 hour ago',
      unread: true
    },
    {
      id: 3,
      type: 'update',
      title: 'New Interview Questions',
      message: '25 new questions added to System Design',
      time: '3 hours ago',
      unread: false
    }
  ]

  const unreadCount = notifications.filter(n => n.unread).length

  return (
    <header className="header">
      <div className="header-content">
        {/* Left Section */}
        <div className="header-left">
          {/* GitHub-style Hamburger Menu */}
          <button 
            className="hamburger-btn"
            onClick={onToggleSidebar}
            aria-label="Toggle navigation menu"
            title="Toggle navigation"
          >
            <Menu size={20} />
          </button>

          {/* Logo/Brand */}
          <div className="brand">
            <BookOpen size={24} className="brand-icon" />
            <span className="brand-text">Senior Developer Academy</span>
          </div>

          {/* Quick Stats (AWS-style info pills) */}
          <div className="quick-stats">
            <div className="stat-pill">
              <Target size={14} />
              <span>{learningStats?.completedModules || 0} Modules</span>
            </div>
            <div className="stat-pill">
              <Zap size={14} />
              <span>{learningStats?.currentStreak || 0} Day Streak</span>
            </div>
          </div>
        </div>

        {/* Center Section - Search */}
        <div className="header-center">
          <form onSubmit={handleSearch} className="search-form">
            <div className="search-container">
              <Search size={16} className="search-icon" />
              <input
                type="text"
                placeholder="Search modules, topics, questions..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="search-input"
              />
              <kbd className="search-shortcut">⌘K</kbd>
            </div>
          </form>
        </div>

        {/* Right Section */}
        <div className="header-right">
          {/* Notifications */}
          <div className="notification-container">
            <button
              className="notification-btn"
              onClick={() => setShowNotifications(!showNotifications)}
              aria-label={`Notifications ${unreadCount > 0 ? `(${unreadCount} unread)` : ''}`}
            >
              <Bell size={18} />
              {unreadCount > 0 && (
                <span className="notification-badge">{unreadCount}</span>
              )}
            </button>

            {showNotifications && (
              <div className="notification-dropdown">
                <div className="notification-header">
                  <h3>Notifications</h3>
                  <button className="btn btn-ghost btn-sm">Mark all read</button>
                </div>
                <div className="notification-list">
                  {notifications.map((notification) => (
                    <div 
                      key={notification.id} 
                      className={`notification-item ${notification.unread ? 'unread' : ''}`}
                    >
                      <div className="notification-content">
                        <h4>{notification.title}</h4>
                        <p>{notification.message}</p>
                        <span className="notification-time">{notification.time}</span>
                      </div>
                      {notification.unread && <div className="unread-indicator" />}
                    </div>
                  ))}
                </div>
                <div className="notification-footer">
                  <button className="btn btn-ghost btn-sm">View all notifications</button>
                </div>
              </div>
            )}
          </div>

          {/* User Menu */}
          <div className="user-menu-container">
            <button
              className="user-menu-btn"
              onClick={() => setShowUserMenu(!showUserMenu)}
              aria-label="User menu"
            >
              <div className="user-avatar">
                <User size={16} />
              </div>
              <span className="user-name">{user?.fullName || 'Guest User'}</span>
              <ChevronDown size={14} />
            </button>

            {showUserMenu && (
              <div className="user-dropdown">
                <div className="user-info">
                  <div className="user-avatar-large">
                    <User size={20} />
                  </div>
                  <div>
                    <div className="user-name-large">{user?.fullName || 'Guest User'}</div>
                    <div className="user-email">{user?.email || 'guest@example.com'}</div>
                  </div>
                </div>
                
                <div className="user-menu-divider" />
                
                <div className="user-menu-items">
                  <a href="/profile" className="user-menu-item">
                    <User size={16} />
                    Profile Settings
                  </a>
                  <a href="/progress" className="user-menu-item">
                    <Target size={16} />
                    Learning Progress
                  </a>
                  <a href="/notes" className="user-menu-item">
                    <BookOpen size={16} />
                    My Notes
                  </a>
                  <div className="user-menu-divider" />
                  <a href="/settings" className="user-menu-item">
                    <Settings size={16} />
                    Settings
                  </a>
                  <button className="user-menu-item user-menu-logout">
                    Sign Out
                  </button>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </header>
  )
}

export default Header