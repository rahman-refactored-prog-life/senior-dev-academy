import React, { useState } from 'react'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import Sidebar from './components/Sidebar'
import Header from './components/Header'
import Dashboard from './pages/Dashboard'
import JavaFundamentals from './pages/JavaFundamentals'
import SpringFramework from './pages/SpringFramework'
import ReactDevelopment from './pages/ReactDevelopment'
import DataStructures from './pages/DataStructures'
import Algorithms from './pages/Algorithms'
import SystemDesign from './pages/SystemDesign'
import InterviewPrep from './pages/InterviewPrep'
import CodeEditor from './pages/CodeEditor'
import Notes from './pages/Notes'

function App() {
  const [sidebarOpen, setSidebarOpen] = useState(false)

  const toggleSidebar = () => {
    setSidebarOpen(!sidebarOpen)
  }

  return (
    <Router>
      <div className="app">
        <Sidebar isOpen={sidebarOpen} onClose={() => setSidebarOpen(false)} />
        <div className={`main-content ${sidebarOpen ? 'sidebar-open' : ''}`}>
          <Header onToggleSidebar={toggleSidebar} />
          <main className="content">
            <Routes>
              <Route path="/" element={<Dashboard />} />
              <Route path="/java-fundamentals" element={<JavaFundamentals />} />
              <Route path="/spring-framework" element={<SpringFramework />} />
              <Route path="/react-development" element={<ReactDevelopment />} />
              <Route path="/data-structures" element={<DataStructures />} />
              <Route path="/algorithms" element={<Algorithms />} />
              <Route path="/system-design" element={<SystemDesign />} />
              <Route path="/interview-prep" element={<InterviewPrep />} />
              <Route path="/code-editor" element={<CodeEditor />} />
              <Route path="/notes" element={<Notes />} />
            </Routes>
          </main>
        </div>
      </div>
    </Router>
  )
}

export default App