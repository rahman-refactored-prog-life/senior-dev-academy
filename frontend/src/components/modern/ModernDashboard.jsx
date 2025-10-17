import React, { useState, useEffect } from 'react'
import { 
  BookOpen, 
  Target, 
  Zap, 
  TrendingUp, 
  Calendar,
  Clock,
  Award,
  Users,
  ArrowRight,
  Play,
  Pause,
  MoreHorizontal,
  ChevronRight,
  Database,
  Code,
  Brain,
  Network
} from 'lucide-react'

/**
 * Modern Dashboard - Advanced UI with micro-interactions
 * 
 * Features:
 * - Real-time progress animations
 * - Interactive learning cards
 * - Advanced data visualizations
 * - Smooth state transitions
 * - Glass morphism design
 */
const ModernDashboard = () => {
  const [activeModule, setActiveModule] = useState(null)
  const [studyTime, setStudyTime] = useState(0)
  const [isStudying, setIsStudying] = useState(false)

  useEffect(() => {
    let interval
    if (isStudying) {
      interval = setInterval(() => {
        setStudyTime(prev => prev + 1)
      }, 1000)
    }
    return () => clearInterval(interval)
  }, [isStudying])

  const learningModules = [
    {
      id: 1,
      name: "Java Fundamentals",
      description: "Master core Java concepts and OOP principles",
      progress: 85,
      totalTopics: 12,
      completedTopics: 10,
      estimatedTime: "2h 30m",
      difficulty: "Beginner",
      icon: Code,
      color: "blue",
      gradient: "from-blue-500 to-blue-600",
      nextTopic: "Exception Handling",
      recentActivity: "Completed: Inheritance & Polymorphism"
    },
    {
      id: 2,
      name: "Database Systems",
      description: "SQL, NoSQL, and database optimization techniques",
      progress: 60,
      totalTopics: 16,
      completedTopics: 9,
      estimatedTime: "4h 15m",
      difficulty: "Intermediate",
      icon: Database,
      color: "green",
      gradient: "from-green-500 to-green-600",
      nextTopic: "Query Optimization",
      recentActivity: "Started: NoSQL Databases"
    },
    {
      id: 3,
      name: "System Design",
      description: "Scalable architecture and distributed systems",
      progress: 35,
      totalTopics: 20,
      completedTopics: 7,
      estimatedTime: "6h 45m",
      difficulty: "Advanced",
      icon: Network,
      color: "purple",
      gradient: "from-purple-500 to-purple-600",
      nextTopic: "Load Balancing",
      recentActivity: "In Progress: Caching Strategies"
    },
    {
      id: 4,
      name: "Algorithms",
      description: "Problem-solving and algorithmic thinking",
      progress: 90,
      totalTopics: 14,
      completedTopics: 13,
      estimatedTime: "1h 20m",
      difficulty: "Advanced",
      icon: Brain,
      color: "orange",
      gradient: "from-orange-500 to-orange-600",
      nextTopic: "Graph Algorithms",
      recentActivity: "Completed: Dynamic Programming"
    }
  ]

  const weeklyProgress = [
    { day: 'Mon', hours: 2.5, completed: 3 },
    { day: 'Tue', hours: 1.8, completed: 2 },
    { day: 'Wed', hours: 3.2, completed: 4 },
    { day: 'Thu', hours: 2.1, completed: 2 },
    { day: 'Fri', hours: 4.0, completed: 5 },
    { day: 'Sat', hours: 1.5, completed: 1 },
    { day: 'Sun', hours: 2.8, completed: 3 }
  ]

  const achievements = [
    {
      title: "Java Master",
      description: "Completed Java Fundamentals",
      icon: Award,
      color: "yellow",
      unlocked: true,
      date: "2 days ago"
    },
    {
      title: "Database Expert",
      description: "Master SQL and NoSQL concepts",
      icon: Database,
      color: "green",
      unlocked: false,
      progress: 60
    },
    {
      title: "Algorithm Ninja",
      description: "Solve 100 algorithm problems",
      icon: Zap,
      color: "purple",
      unlocked: false,
      progress: 87
    }
  ]

  const formatTime = (seconds) => {
    const hours = Math.floor(seconds / 3600)
    const minutes = Math.floor((seconds % 3600) / 60)
    const secs = seconds % 60
    return `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`
  }

  return (
    <div className="modern-dashboard p-8 min-h-screen bg-gradient-to-br from-neutral-50 to-blue-50/30">
      {/* Header Section */}
      <div className="mb-8">
        <div className="flex items-center justify-between mb-6">
          <div>
            <h1 className="text-4xl font-bold text-gradient mb-2">
              Welcome back, Developer! 👋
            </h1>
            <p className="text-neutral-600 text-lg">
              Continue your journey to senior-level mastery
            </p>
          </div>
          
          {/* Study Timer */}
          <div className="glass-card p-6">
            <div className="flex items-center gap-4">
              <div className="text-center">
                <div className="text-2xl font-mono font-bold text-gradient">
                  {formatTime(studyTime)}
                </div>
                <div className="text-sm text-neutral-600">Study Time</div>
              </div>
              <button
                onClick={() => setIsStudying(!isStudying)}
                className={`btn-modern ${isStudying ? 'btn-primary' : 'btn-ghost'}`}
              >
                {isStudying ? <Pause size={20} /> : <Play size={20} />}
                {isStudying ? 'Pause' : 'Start'}
              </button>
            </div>
          </div>
        </div>

        {/* Quick Stats */}
        <div className="modern-grid grid-cols-4 mb-8">
          <div className="glass-card text-center interactive-element">
            <TrendingUp size={32} className="mx-auto mb-3 text-blue-600" />
            <div className="text-2xl font-bold text-gradient">73%</div>
            <div className="text-neutral-600">Overall Progress</div>
          </div>
          <div className="glass-card text-center interactive-element">
            <Target size={32} className="mx-auto mb-3 text-green-600" />
            <div className="text-2xl font-bold text-gradient">156</div>
            <div className="text-neutral-600">Questions Solved</div>
          </div>
          <div className="glass-card text-center interactive-element">
            <Clock size={32} className="mx-auto mb-3 text-purple-600" />
            <div className="text-2xl font-bold text-gradient">24h</div>
            <div className="text-neutral-600">This Week</div>
          </div>
          <div className="glass-card text-center interactive-element">
            <Award size={32} className="mx-auto mb-3 text-orange-600" />
            <div className="text-2xl font-bold text-gradient">7</div>
            <div className="text-neutral-600">Streak Days</div>
          </div>
        </div>
      </div>

      {/* Main Content Grid */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        
        {/* Learning Modules */}
        <div className="lg:col-span-2">
          <div className="flex items-center justify-between mb-6">
            <h2 className="text-2xl font-bold">Continue Learning</h2>
            <button className="btn-modern btn-ghost btn-sm">
              View All <ArrowRight size={16} />
            </button>
          </div>
          
          <div className="space-y-4">
            {learningModules.map((module, index) => (
              <div
                key={module.id}
                className={`glass-card hover:scale-[1.02] transition-all duration-300 cursor-pointer ${
                  activeModule === module.id ? 'ring-2 ring-blue-500/50' : ''
                }`}
                onClick={() => setActiveModule(activeModule === module.id ? null : module.id)}
                style={{ animationDelay: `${index * 100}ms` }}
              >
                <div className="flex items-start gap-4">
                  {/* Module Icon */}
                  <div className={`w-12 h-12 rounded-xl bg-gradient-to-r ${module.gradient} flex items-center justify-center flex-shrink-0`}>
                    <module.icon size={24} className="text-white" />
                  </div>
                  
                  {/* Module Info */}
                  <div className="flex-1 min-w-0">
                    <div className="flex items-center justify-between mb-2">
                      <h3 className="font-semibold text-lg">{module.name}</h3>
                      <span className={`px-2 py-1 rounded-full text-xs font-medium bg-${module.color}-100 text-${module.color}-700`}>
                        {module.difficulty}
                      </span>
                    </div>
                    
                    <p className="text-neutral-600 mb-3">{module.description}</p>
                    
                    {/* Progress Bar */}
                    <div className="mb-3">
                      <div className="flex justify-between text-sm mb-1">
                        <span>{module.completedTopics}/{module.totalTopics} topics</span>
                        <span>{module.progress}%</span>
                      </div>
                      <div className="progress-modern" data-progress={module.progress}></div>
                    </div>
                    
                    {/* Module Stats */}
                    <div className="flex items-center justify-between text-sm text-neutral-600">
                      <span>⏱️ {module.estimatedTime} remaining</span>
                      <span>📚 Next: {module.nextTopic}</span>
                    </div>
                    
                    {/* Expanded Content */}
                    {activeModule === module.id && (
                      <div className="mt-4 pt-4 border-t border-neutral-200 animate-fade-in-up">
                        <div className="text-sm text-neutral-600 mb-3">
                          Recent Activity: {module.recentActivity}
                        </div>
                        <div className="flex gap-2">
                          <button className="btn-modern btn-primary btn-sm">
                            Continue Learning
                          </button>
                          <button className="btn-modern btn-ghost btn-sm">
                            View Details
                          </button>
                        </div>
                      </div>
                    )}
                  </div>
                  
                  <ChevronRight 
                    size={20} 
                    className={`text-neutral-400 transition-transform ${
                      activeModule === module.id ? 'rotate-90' : ''
                    }`} 
                  />
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Sidebar */}
        <div className="space-y-6">
          
          {/* Weekly Progress Chart */}
          <div className="glass-card">
            <h3 className="font-semibold mb-4 flex items-center gap-2">
              <TrendingUp size={20} />
              Weekly Progress
            </h3>
            <div className="space-y-3">
              {weeklyProgress.map((day, index) => (
                <div key={day.day} className="flex items-center gap-3">
                  <div className="w-8 text-sm font-medium text-neutral-600">{day.day}</div>
                  <div className="flex-1">
                    <div className="flex justify-between text-xs mb-1">
                      <span>{day.hours}h</span>
                      <span>{day.completed} topics</span>
                    </div>
                    <div className="h-2 bg-neutral-200 rounded-full overflow-hidden">
                      <div 
                        className="h-full bg-gradient-to-r from-blue-500 to-purple-600 rounded-full transition-all duration-1000"
                        style={{ 
                          width: `${(day.hours / 4) * 100}%`,
                          animationDelay: `${index * 100}ms`
                        }}
                      ></div>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>

          {/* Achievements */}
          <div className="glass-card">
            <h3 className="font-semibold mb-4 flex items-center gap-2">
              <Award size={20} />
              Achievements
            </h3>
            <div className="space-y-3">
              {achievements.map((achievement, index) => (
                <div 
                  key={index} 
                  className={`p-3 rounded-lg border transition-all duration-300 ${
                    achievement.unlocked 
                      ? 'bg-gradient-to-r from-yellow-50 to-orange-50 border-yellow-200' 
                      : 'bg-neutral-50 border-neutral-200'
                  }`}
                >
                  <div className="flex items-center gap-3">
                    <div className={`w-8 h-8 rounded-lg flex items-center justify-center ${
                      achievement.unlocked 
                        ? `bg-${achievement.color}-500 text-white` 
                        : 'bg-neutral-300 text-neutral-600'
                    }`}>
                      <achievement.icon size={16} />
                    </div>
                    <div className="flex-1 min-w-0">
                      <div className="font-medium text-sm">{achievement.title}</div>
                      <div className="text-xs text-neutral-600">{achievement.description}</div>
                      {achievement.unlocked ? (
                        <div className="text-xs text-green-600 mt-1">✓ {achievement.date}</div>
                      ) : (
                        <div className="mt-1">
                          <div className="h-1 bg-neutral-200 rounded-full overflow-hidden">
                            <div 
                              className="h-full bg-gradient-to-r from-blue-500 to-purple-600 rounded-full transition-all duration-1000"
                              style={{ width: `${achievement.progress}%` }}
                            ></div>
                          </div>
                          <div className="text-xs text-neutral-600 mt-1">{achievement.progress}%</div>
                        </div>
                      )}
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>

          {/* Quick Actions */}
          <div className="glass-card">
            <h3 className="font-semibold mb-4">Quick Actions</h3>
            <div className="space-y-2">
              <button className="w-full btn-modern btn-ghost justify-start">
                <BookOpen size={16} />
                Practice Interview Questions
              </button>
              <button className="w-full btn-modern btn-ghost justify-start">
                <Code size={16} />
                Open Code Playground
              </button>
              <button className="w-full btn-modern btn-ghost justify-start">
                <Users size={16} />
                Join Study Group
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default ModernDashboard