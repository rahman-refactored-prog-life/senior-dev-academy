import React, { createContext, useContext, useState, useEffect } from 'react'

const AuthContext = createContext()

export const useAuth = () => {
  const context = useContext(AuthContext)
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider')
  }
  return context
}

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null)
  const [isLoading, setIsLoading] = useState(true)
  const [isAuthenticated, setIsAuthenticated] = useState(false)

  useEffect(() => {
    // Simulate checking for existing session
    const checkAuth = async () => {
      try {
        // For demo purposes, set a mock user
        const mockUser = {
          id: 1,
          username: 'demo_user',
          email: 'demo@seniordevacademy.com',
          fullName: 'Demo User',
          role: 'USER'
        }
        
        setUser(mockUser)
        setIsAuthenticated(true)
      } catch (error) {
        console.error('Auth check failed:', error)
      } finally {
        setIsLoading(false)
      }
    }

    checkAuth()
  }, [])

  const login = async (username, password) => {
    setIsLoading(true)
    try {
      // Mock login - in real app, call API
      const mockUser = {
        id: 1,
        username,
        email: `${username}@seniordevacademy.com`,
        fullName: 'Demo User',
        role: 'USER'
      }
      
      setUser(mockUser)
      setIsAuthenticated(true)
      return { success: true }
    } catch (error) {
      return { success: false, error: error.message }
    } finally {
      setIsLoading(false)
    }
  }

  const logout = () => {
    setUser(null)
    setIsAuthenticated(false)
  }

  const value = {
    user,
    isAuthenticated,
    isLoading,
    login,
    logout
  }

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  )
}