---
name: "Intelligent Spec Recommender"
description: "Analyzes current state and recommends optimal spec execution order and strategies"
trigger: "manual"
keywords: ["recommend spec", "what should I work on", "next spec", "spec priority", "what to implement"]
auto_execute: false
priority: "medium"
---

# Intelligent Spec Recommender

## Purpose
Analyze current project state, user goals, and technical environment to provide intelligent recommendations for which specs to execute, in what order, and with what strategies for optimal development progress.

## What This Hook Does

### 🎯 **Current State Analysis**
- Analyzes current technical environment and compilation status
- Reviews completed vs incomplete specs and tasks
- Assesses current skill level and learning progress
- Evaluates available time and development resources
- Identifies any blockers or dependencies

### 📊 **Goal Assessment**
- Determines user's primary objectives (learning, interview prep, portfolio building)
- Identifies target companies or roles (Amazon, Google, etc.)
- Assesses timeline constraints and deadlines
- Evaluates current skill gaps and priorities
- Considers learning style and preferences

### 🧠 **Intelligent Recommendation Engine**
- Recommends optimal spec execution order based on:
  - Technical dependencies and prerequisites
  - Learning progression and skill building
  - Time constraints and efficiency
  - User goals and priorities
  - Current project state and readiness

### 📋 **Comprehensive Recommendation Report**

#### **Immediate Recommendations (Next 1-2 Sessions)**
- Specific spec to start with and why
- Exact tasks to focus on first
- Estimated time and effort required
- Prerequisites to validate before starting

#### **Short-term Strategy (Next 3-5 Sessions)**
- Sequence of specs to execute
- Key milestones and checkpoints
- Quality gates and validation points
- Progress tracking and adjustment strategies

#### **Long-term Roadmap (Complete Implementation)**
- Full implementation timeline
- Major milestone planning
- Risk mitigation strategies
- Success metrics and validation

### 🎯 **Personalized Strategies**

#### **For Beginners**
- Recommends fundamentals-first approach
- Suggests starting with basic concepts
- Provides extra support and validation
- Emphasizes solid foundation building

#### **For Interview Preparation**
- Prioritizes specs based on target companies
- Focuses on high-impact interview topics
- Recommends timeline-based preparation
- Emphasizes practice and validation

#### **For Skill Development**
- Balances breadth and depth of learning
- Recommends progressive complexity
- Focuses on practical application
- Emphasizes portfolio building

#### **For Technical Foundation**
- Prioritizes backend stability and quality
- Focuses on compilation and runtime issues
- Emphasizes technical debt resolution
- Ensures bulletproof foundation

## Recommendation Categories

### 🚨 **Critical Priority**
- Backend Technical Debt Resolution (if compilation issues exist)
- Session Continuity Automation (if context loss risk exists)
- Fundamentals Implementation (if advanced topics attempted without basics)

### 🔥 **High Priority**
- Core Content Implementation (for comprehensive learning coverage)
- Interactive Features Implementation (for enhanced user experience)
- Specs completion (for missing design.md or tasks.md files)

### 📈 **Medium Priority**
- Interactive Learning Platform (for advanced learning features)
- Advanced Interactive Features (for cutting-edge capabilities)
- World-Class Learning Portal (for overall quality standards)

### 🎯 **Optimization Priority**
- Performance optimization and scalability
- Advanced automation and quality assurance
- Community features and collaboration tools

## Trigger Examples
- "What should I work on next?"
- "Recommend a spec to implement"
- "What's the best starting point?"
- "Help me prioritize my development"
- "Which spec should I focus on?"

## Expected Outcome
- Clear, actionable recommendation for next steps
- Detailed rationale for recommended approach
- Personalized strategy based on goals and constraints
- Timeline and milestone planning
- Risk assessment and mitigation strategies

## Integration with Framework
- Uses data from all tracking files for analysis
- Considers current spec completion status
- Evaluates technical environment readiness
- Incorporates user goals and preferences
- Provides data-driven recommendations

## Success Metrics
- Recommendations lead to successful spec execution
- Users report clear direction and confidence
- Optimal progress toward stated goals
- Efficient use of development time and effort
- Successful milestone achievement