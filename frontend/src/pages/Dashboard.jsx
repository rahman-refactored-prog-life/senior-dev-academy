import React from 'react'
import { Link } from 'react-router-dom'
import { 
  BookOpen, 
  Clock, 
  Target, 
  TrendingUp,
  Coffee,
  Leaf,
  Atom,
  Database,
  Zap,
  Network,
  ArrowRight,
  Code,
  MessageSquare,
  StickyNote,
  BarChart3
} from 'lucide-react'
import { useLearning } from '../context/LearningContext'
import { useAuth } from '../context/AuthContext'

const Dashboard = () => {
  const { learningStats, modules } = useLearning()
  const { user } = useAuth()

  const quickStats = [
    {
      title: 'Modules Completed',
      value: learningStats.completedModules,
      icon: BookOpen,
      color: 'var(--aws-success)',
      change: '+2 this week'
    },
    {
      title: 'Learning Streak',
      value: `${learningStats.currentStreak} days`,
      icon: Target,
      color: 'var(--color-primary)',
      change: 'Keep it up!'
    },
    {
      title: 'Time Invested',
      value: `${Math.floor(learningStats.totalTimeSpent / 60)}h ${learningStats.totalTimeSpent % 60}m`,
      icon: Clock,
      color: 'var(--aws-info)',
      change: '+3.5h this week'
    },
    {
      title: 'Overall Progress',
      value: `${learningStats.overallProgress}%`,
      icon: TrendingUp,
      color: 'var(--aws-success)',
      change: '+12% this month'
    }
  ]

  const recentAchievements = [
    {
      title: 'Java Master',
      description: 'Completed Java Fundamentals',
      icon: '🏆',
      date: '2 days ago'
    },
    {
      title: 'Streak Champion',
      description: '7-day learning streak',
      icon: '🔥',
      date: '1 week ago'
    },
    {
      title: 'Code Warrior',
      description: 'Solved 50 coding problems',
      icon: '⚔️',
      date: '2 weeks ago'
    }
  ]

  const upcomingMilestones = [
    {
      title: 'Spring Boot Expert',
      description: 'Complete Spring Framework module',
      progress: 30,
      target: 100
    },
    {
      title: 'React Developer',
      description: 'Start React Development',
      progress: 0,
      target: 100
    }
  ]

  return (
    <div className="dashboard">
      {/* Welcome Section */}
      <div className="dashboard-header">
        <div>
          <h1>Welcome back, {user?.fullName || 'Developer'}! 👋</h1>
          <p className="text-secondary">
            Ready to continue your journey to senior developer mastery?
          </p>
        </div>
        <div className="dashboard-actions">
          <Link to="/code-playground" className="btn btn-primary btn-glow">
            <Code size={18} />
            Start Coding
          </Link>
          <Link to="/interview-prep" className="btn btn-outline">
            <MessageSquare size={18} />
            Practice Questions
          </Link>
        </div>
      </div>

      {/* Quick Stats */}
      <div className="stats-grid">
        {quickStats.map((stat, index) => {
          const Icon = stat.icon
          return (
            <div key={index} className="stat-card stat-card-glow">
              <div className="stat-header">
                <div className="stat-icon" style={{ color: stat.color }}>
                  <Icon size={24} />
                </div>
                <div className="stat-change">{stat.change}</div>
              </div>
              <div className="stat-content">
                <div className="stat-value">{stat.value}</div>
                <div className="stat-title">{stat.title}</div>
              </div>
            </div>
          )
        })}
      </div>

      {/* Main Content Grid */}
      <div className="dashboard-grid">
        {/* Learning Modules */}
        <div className="dashboard-section">
          <div className="section-header">
            <h2>Continue Learning</h2>
            <Link to="/learning-path" className="btn btn-ghost btn-sm">
              View All <ArrowRight size={16} />
            </Link>
          </div>
          <div className="modules-grid">
            {modules.slice(0, 3).map((module) => {
              const iconMap = {
                'Java Fundamentals': Coffee,
                'Spring Framework': Leaf,
                'React Development': Atom,
                'Data Structures': Database,
                'Algorithms': Zap,
                'System Design': Network
              }
              const Icon = iconMap[module.category] || BookOpen
              
              return (
                <Link 
                  key={module.id} 
                  to={`/modules/${module.id}`}
                  className="module-card module-card-glow"
                >
                  <div className="module-header">
                    <Icon size={20} className="module-icon" />
                    <span className="module-difficulty">{module.difficultyLevel}</span>
                  </div>
                  <h3>{module.name}</h3>
                  <p>{module.description}</p>
                  <div className="module-progress">
                    <div className="progress">
                      <div 
                        className="progress-bar" 
                        style={{ width: `${module.completionRate}%` }}
                      ></div>
                    </div>
                    <span className="progress-text">{module.completionRate}%</span>
                  </div>
                  <div className="module-meta">
                    <span>{module.topicCount} topics</span>
                    <span>{module.estimatedHours}h</span>
                  </div>
                </Link>
              )
            })}
          </div>
        </div>

        {/* Recent Achievements */}
        <div className="dashboard-section">
          <div className="section-header">
            <h2>Recent Achievements</h2>
            <Link to="/profile" className="btn btn-ghost btn-sm">
              View All <ArrowRight size={16} />
            </Link>
          </div>
          <div className="achievements-list">
            {recentAchievements.map((achievement, index) => (
              <div key={index} className="achievement-item">
                <div className="achievement-icon">{achievement.icon}</div>
                <div className="achievement-content">
                  <h4>{achievement.title}</h4>
                  <p>{achievement.description}</p>
                  <span className="achievement-date">{achievement.date}</span>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Upcoming Milestones */}
        <div className="dashboard-section">
          <div className="section-header">
            <h2>Upcoming Milestones</h2>
          </div>
          <div className="milestones-list">
            {upcomingMilestones.map((milestone, index) => (
              <div key={index} className="milestone-item">
                <div className="milestone-content">
                  <h4>{milestone.title}</h4>
                  <p>{milestone.description}</p>
                  <div className="milestone-progress">
                    <div className="progress">
                      <div 
                        className="progress-bar" 
                        style={{ width: `${(milestone.progress / milestone.target) * 100}%` }}
                      ></div>
                    </div>
                    <span className="progress-text">
                      {milestone.progress}/{milestone.target}
                    </span>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Quick Actions */}
        <div className="dashboard-section">
          <div className="section-header">
            <h2>Quick Actions</h2>
          </div>
          <div className="quick-actions">
            <Link to="/interview-prep" className="quick-action-btn aws-glow-card">
              <MessageSquare size={24} />
              <span>Practice Interview Questions</span>
            </Link>
            <Link to="/code-playground" className="quick-action-btn aws-glow-card">
              <Code size={24} />
              <span>Open Code Playground</span>
            </Link>
            <Link to="/notes" className="quick-action-btn aws-glow-card">
              <StickyNote size={24} />
              <span>Review My Notes</span>
            </Link>
            <Link to="/progress" className="quick-action-btn aws-glow-card">
              <BarChart3 size={24} />
              <span>View Progress Analytics</span>
            </Link>
          </div>
        </div>
      </div>

      {/* Floating Action Button */}
      <button className="floating-action floating-action-glow" title="Quick Start Learning">
        <BookOpen size={24} color="white" />
      </button>
    </div>
  )
}

export default Dashboard