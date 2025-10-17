import React, { useState } from 'react'
import { Search, Filter, Star, Clock, Building, ChevronRight } from 'lucide-react'

const InterviewPrep = () => {
  const [selectedDifficulty, setSelectedDifficulty] = useState('all')
  const [selectedCompany, setSelectedCompany] = useState('all')
  const [searchQuery, setSearchQuery] = useState('')

  const questions = [
    {
      id: 1,
      title: 'Two Sum Problem',
      difficulty: 'Easy',
      company: 'Amazon',
      category: 'Arrays',
      timeEstimate: '15 min',
      frequency: 9,
      description: 'Given an array of integers, return indices of two numbers that add up to target.'
    },
    {
      id: 2,
      title: 'Reverse Linked List',
      difficulty: 'Easy',
      company: 'Google',
      category: 'Linked Lists',
      timeEstimate: '20 min',
      frequency: 8,
      description: 'Reverse a singly linked list iteratively and recursively.'
    },
    {
      id: 3,
      title: 'Valid Parentheses',
      difficulty: 'Easy',
      company: 'Microsoft',
      category: 'Stack',
      timeEstimate: '15 min',
      frequency: 7,
      description: 'Determine if the input string has valid parentheses.'
    },
    {
      id: 4,
      title: 'Binary Tree Level Order Traversal',
      difficulty: 'Medium',
      company: 'Meta',
      category: 'Trees',
      timeEstimate: '25 min',
      frequency: 8,
      description: 'Return the level order traversal of a binary tree.'
    },
    {
      id: 5,
      title: 'Design URL Shortener',
      difficulty: 'Hard',
      company: 'Netflix',
      category: 'System Design',
      timeEstimate: '45 min',
      frequency: 9,
      description: 'Design a URL shortening service like bit.ly or tinyurl.'
    }
  ]

  const getDifficultyColor = (difficulty) => {
    switch (difficulty) {
      case 'Easy': return 'var(--aws-success)'
      case 'Medium': return 'var(--aws-warning)'
      case 'Hard': return 'var(--aws-error)'
      default: return 'var(--color-text-muted)'
    }
  }

  const filteredQuestions = questions.filter(question => {
    const matchesDifficulty = selectedDifficulty === 'all' || question.difficulty === selectedDifficulty
    const matchesCompany = selectedCompany === 'all' || question.company === selectedCompany
    const matchesSearch = question.title.toLowerCase().includes(searchQuery.toLowerCase()) ||
                         question.category.toLowerCase().includes(searchQuery.toLowerCase())
    
    return matchesDifficulty && matchesCompany && matchesSearch
  })

  return (
    <div className="page-container">
      <div className="page-header">
        <h1>Interview Preparation</h1>
        <p>Practice with 2000+ questions from top tech companies</p>
      </div>

      {/* Filters */}
      <div className="interview-filters">
        <div className="search-section">
          <div className="search-container">
            <Search size={16} className="search-icon" />
            <input
              type="text"
              placeholder="Search questions by title or category..."
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              className="search-input"
            />
          </div>
        </div>

        <div className="filter-section">
          <div className="filter-group">
            <label>Difficulty:</label>
            <select 
              value={selectedDifficulty} 
              onChange={(e) => setSelectedDifficulty(e.target.value)}
              className="filter-select"
            >
              <option value="all">All Levels</option>
              <option value="Easy">Easy</option>
              <option value="Medium">Medium</option>
              <option value="Hard">Hard</option>
            </select>
          </div>

          <div className="filter-group">
            <label>Company:</label>
            <select 
              value={selectedCompany} 
              onChange={(e) => setSelectedCompany(e.target.value)}
              className="filter-select"
            >
              <option value="all">All Companies</option>
              <option value="Amazon">Amazon</option>
              <option value="Google">Google</option>
              <option value="Microsoft">Microsoft</option>
              <option value="Meta">Meta</option>
              <option value="Netflix">Netflix</option>
            </select>
          </div>
        </div>
      </div>

      {/* Questions List */}
      <div className="questions-container">
        <div className="questions-header">
          <h3>Questions ({filteredQuestions.length})</h3>
          <div className="sort-options">
            <Filter size={16} />
            <span>Sort by Frequency</span>
          </div>
        </div>

        <div className="questions-list">
          {filteredQuestions.map((question) => (
            <div key={question.id} className="question-card">
              <div className="question-header">
                <div className="question-title-section">
                  <h4>{question.title}</h4>
                  <div className="question-meta">
                    <span 
                      className="difficulty-badge"
                      style={{ color: getDifficultyColor(question.difficulty) }}
                    >
                      {question.difficulty}
                    </span>
                    <span className="company-badge">
                      <Building size={12} />
                      {question.company}
                    </span>
                    <span className="category-badge">{question.category}</span>
                  </div>
                </div>
                <div className="question-stats">
                  <div className="frequency-score">
                    <Star size={14} />
                    <span>{question.frequency}/10</span>
                  </div>
                  <div className="time-estimate">
                    <Clock size={14} />
                    <span>{question.timeEstimate}</span>
                  </div>
                </div>
              </div>

              <p className="question-description">{question.description}</p>

              <div className="question-actions">
                <button className="btn btn-primary btn-sm">
                  Solve Problem
                  <ChevronRight size={14} />
                </button>
                <button className="btn btn-secondary btn-sm">
                  View Solution
                </button>
              </div>
            </div>
          ))}
        </div>

        {filteredQuestions.length === 0 && (
          <div className="no-results">
            <p>No questions match your current filters.</p>
            <button 
              className="btn btn-secondary"
              onClick={() => {
                setSelectedDifficulty('all')
                setSelectedCompany('all')
                setSearchQuery('')
              }}
            >
              Clear Filters
            </button>
          </div>
        )}
      </div>
    </div>
  )
}

export default InterviewPrep