import React from 'react'
import { Menu, Search, User, Settings } from 'lucide-react'

const Header = ({ onToggleSidebar }) => {
  return (
    <header className="header">
      <div className="flex items-center gap-4">
        <button 
          className="hamburger-btn"
          onClick={onToggleSidebar}
          aria-label="Toggle navigation menu"
        >
          <Menu size={20} />
        </button>
        <div className="logo">
          Comprehensive Developer Portal
        </div>
      </div>
      
      <div className="flex items-center gap-4">
        <div className="search-container" style={{ position: 'relative' }}>
          <input 
            type="text" 
            placeholder="Search topics, questions, code..." 
            style={{
              padding: '0.5rem 2.5rem 0.5rem 1rem',
              border: '1px solid var(--border-light)',
              borderRadius: '4px',
              width: '300px',
              fontSize: '0.875rem'
            }}
          />
          <Search 
            size={16} 
            style={{
              position: 'absolute',
              right: '0.75rem',
              top: '50%',
              transform: 'translateY(-50%)',
              color: 'var(--text-secondary)'
            }}
          />
        </div>
        
        <button className="btn btn-secondary">
          <Settings size={16} />
          Settings
        </button>
        
        <button className="btn btn-secondary">
          <User size={16} />
          Profile
        </button>
      </div>
    </header>
  )
}

export default Header