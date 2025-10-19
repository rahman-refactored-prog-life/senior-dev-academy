import React, { useState, useEffect } from 'react'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import { Toaster } from 'react-hot-toast'

// Layout Components
import Sidebar from './components/layout/Sidebar'
import Header from './components/layout/Header'
import LoadingSpinner from './components/ui/LoadingSpinner'

// Pages
import Dashboard from './pages/Dashboard'
import LearningPath from './pages/LearningPath'
import ModuleDetail from './pages/ModuleDetail'
import TopicDetail from './pages/TopicDetail'
import InterviewPrep from './pages/InterviewPrep'
import CodePlayground from './pages/CodePlayground'
import Progress from './pages/Progress'
import Notes from './pages/Notes'
import Profile from './pages/Profile'
import NodeJSShowcase from './pages/NodeJSShowcase'
import NodeJSContent from './pages/NodeJSContent'

// Test Component
import TestComponent from './components/TestComponent'

// Context
import { AuthProvider } from './context/AuthContext'
import { LearningProvider } from './context/LearningContext'

/**
 * Main Application Component
 * 
 * Features inspired by top learning platforms:
 * - Persistent sidebar navigation (like AWS Console)
 * - Clean, professional layout
 * - Context-based state management
 * - Progressive loading with skeleton states
 * - Toast notifications for user feedback
 * - Responsive design for all devices
 */
function App() {
  const [sidebarOpen, setSidebarOpen] = useState(false)
  const [isLoading, setIsLoading] = useState(true)

  // Simulate initial app loading
  useEffect(() => {
    const timer = setTimeout(() => {
      setIsLoading(false)
    }, 1000)

    return () => clearTimeout(timer)
  }, [])

  const toggleSidebar = () => {
    setSidebarOpen(!sidebarOpen)
  }

  const closeSidebar = () => {
    setSidebarOpen(false)
  }

  if (isLoading) {
    return <LoadingSpinner />
  }

  console.log('App render - sidebarOpen:', sidebarOpen)

  return (
    <AuthProvider>
      <LearningProvider>
        <Router>
          <div className="app-layout">
            {/* Toast Notifications */}
            <Toaster
              position="top-right"
              toastOptions={{
                duration: 4000,
                style: {
                  background: 'var(--aws-white)',
                  color: 'var(--color-text-primary)',
                  border: '1px solid var(--color-border)',
                  borderRadius: 'var(--radius-md)',
                  boxShadow: 'var(--shadow-lg)',
                  fontSize: 'var(--font-size-sm)',
                  fontFamily: 'var(--font-family-primary)',
                },
                success: {
                  iconTheme: {
                    primary: 'var(--aws-success)',
                    secondary: 'var(--aws-white)',
                  },
                },
                error: {
                  iconTheme: {
                    primary: 'var(--aws-error)',
                    secondary: 'var(--aws-white)',
                  },
                },
              }}
            />

            {/* Sidebar */}
            <Sidebar 
              isOpen={sidebarOpen} 
              onClose={closeSidebar}
            />

            {/* Main Content Area */}
            <div className={`main-content ${sidebarOpen ? 'sidebar-open' : ''}`}>
              {/* Header */}
              <Header onToggleSidebar={toggleSidebar} />

              {/* Page Content */}
              <main className="page-content">
                <Routes>
                  <Route path="/" element={<Dashboard />} />
                  <Route path="/test" element={<TestComponent />} />
                  <Route path="/learning-path" element={<LearningPath />} />
                  <Route path="/modules/:moduleId" element={<ModuleDetail />} />
                  <Route path="/modules/nodejs" element={<NodeJSContent />} />
                  <Route path="/nodejs-showcase" element={<NodeJSShowcase />} />
                  <Route path="/modules/java" element={<ModuleDetail />} />
                  <Route path="/modules/spring" element={<ModuleDetail />} />
                  <Route path="/modules/react" element={<ModuleDetail />} />
                  <Route path="/modules/data-structures" element={<ModuleDetail />} />
                  <Route path="/modules/algorithms" element={<ModuleDetail />} />
                  <Route path="/modules/system-design" element={<ModuleDetail />} />
                  <Route path="/topics/:topicId" element={<TopicDetail />} />
                  <Route path="/interview-prep" element={<InterviewPrep />} />
                  <Route path="/code-playground" element={<CodePlayground />} />
                  <Route path="/progress" element={<Progress />} />
                  <Route path="/notes" element={<Notes />} />
                  <Route path="/profile" element={<Profile />} />
                </Routes>
              </main>
            </div>

          {/* Overlay for sidebar */}
          {sidebarOpen && (
            <div 
              className="sidebar-overlay show"
              onClick={closeSidebar}
              aria-hidden="true"
            />
          )}
          </div>
        </Router>
      </LearningProvider>
    </AuthProvider>
  )
}

export default App