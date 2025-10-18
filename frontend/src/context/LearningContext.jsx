import React, { createContext, useContext, useState, useEffect } from 'react'

const LearningContext = createContext()

export const useLearning = () => {
  const context = useContext(LearningContext)
  if (!context) {
    throw new Error('useLearning must be used within a LearningProvider')
  }
  return context
}

export const LearningProvider = ({ children }) => {
  const [learningStats, setLearningStats] = useState({
    completedModules: 0,
    currentStreak: 0,
    totalTimeSpent: 0,
    overallProgress: 0
  })
  const [modules, setModules] = useState([])
  const [isLoading, setIsLoading] = useState(false)

  useEffect(() => {
    // Load initial learning data
    loadLearningStats()
    loadModules()
  }, [])

  const loadLearningStats = async () => {
    try {
      // Mock data - in real app, call API
      setLearningStats({
        completedModules: 3,
        currentStreak: 7,
        totalTimeSpent: 1250, // minutes
        overallProgress: 45.5
      })
    } catch (error) {
      console.error('Failed to load learning stats:', error)
    }
  }

  const loadModules = async () => {
    setIsLoading(true)
    try {
      // Mock modules data
      const mockModules = [
        {
          id: 1,
          name: 'Java Fundamentals',
          description: 'Master Java programming from basics to advanced concepts',
          category: 'Java Fundamentals',
          difficultyLevel: 'Beginner',
          estimatedHours: 40,
          topicCount: 12,
          completionRate: 75
        },
        {
          id: 'nodejs',
          name: 'Node.js Development',
          description: 'Learn Node.js fundamentals and backend development',
          category: 'Node.js',
          difficultyLevel: 'Beginner to Intermediate',
          estimatedHours: 30,
          topicCount: 8,
          completionRate: 0
        },
        {
          id: 2,
          name: 'Spring Framework',
          description: 'Learn Spring Boot, Security, and Data',
          category: 'Spring Framework',
          difficultyLevel: 'Intermediate',
          estimatedHours: 35,
          topicCount: 10,
          completionRate: 30
        },
        {
          id: 3,
          name: 'React Development',
          description: 'Modern React with hooks and performance optimization',
          category: 'React Development',
          difficultyLevel: 'Intermediate',
          estimatedHours: 30,
          topicCount: 8,
          completionRate: 0
        }
      ]
      
      setModules(mockModules)
    } catch (error) {
      console.error('Failed to load modules:', error)
    } finally {
      setIsLoading(false)
    }
  }

  const updateProgress = async (moduleId, progress) => {
    try {
      // Update local state
      setModules(prev => prev.map(module => 
        module.id === moduleId 
          ? { ...module, completionRate: progress }
          : module
      ))
      
      // Recalculate stats
      await loadLearningStats()
    } catch (error) {
      console.error('Failed to update progress:', error)
    }
  }

  const value = {
    learningStats,
    modules,
    isLoading,
    updateProgress,
    refreshData: () => {
      loadLearningStats()
      loadModules()
    }
  }

  return (
    <LearningContext.Provider value={value}>
      {children}
    </LearningContext.Provider>
  )
}