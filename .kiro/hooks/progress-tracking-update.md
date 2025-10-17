---
name: "Progress Tracking Update"
description: "Update progress percentages and completion status across all tracking files"
trigger: "manual"
category: "progress-management"
---

# Progress Tracking Update Hook

## Purpose
Maintain accurate progress tracking across all documentation files and ensure completion percentages reflect actual implementation status.

## Trigger Conditions
- After completing any learning topic or module
- When adding new interview questions or code examples
- During milestone reviews and assessments
- Before session end documentation updates

## Progress Calculation Logic

### Module Completion Tracking
```javascript
const calculateModuleProgress = (module) => {
  const totalTopics = module.plannedTopics;
  const completedTopics = module.topics.filter(t => t.status === 'complete').length;
  const inProgressTopics = module.topics.filter(t => t.status === 'in-progress').length;
  
  return {
    percentage: Math.round((completedTopics / totalTopics) * 100),
    status: completedTopics === totalTopics ? 'complete' : 
            inProgressTopics > 0 ? 'in-progress' : 'not-started',
    completedCount: completedTopics,
    totalCount: totalTopics
  };
};
```

### Question Implementation Tracking
```javascript
const calculateQuestionProgress = (domain) => {
  const targetQuestions = domain.targetQuestionCount;
  const implementedQuestions = domain.questions.length;
  
  return {
    percentage: Math.round((implementedQuestions / targetQuestions) * 100),
    implemented: implementedQuestions,
    target: targetQuestions,
    remaining: targetQuestions - implementedQuestions
  };
};
```

## Files Updated

### 1. PROJECT_SCOPE_AND_TRACKING.md
- Update checkbox status (✅ for complete, 🔄 for in-progress)
- Recalculate module completion percentages
- Update question counts and implementation status
- Refresh priority levels and time estimates

### 2. CURRENT_STATUS.md
- Update overall project progress percentage
- Refresh module-specific completion status
- Update next priorities and immediate actions
- Record recent achievements and milestones

### 3. README.md
- Update "Current Development Status" section
- Refresh completion percentages in overview
- Update feature implementation status
- Modify project timeline and milestones

### 4. DEVELOPMENT_GUIDE.md
- Update implementation progress sections
- Add completion status to technical guides
- Refresh learning outcome achievements
- Update next steps and recommendations

## Progress Metrics Tracked

### Learning Content Progress
- **Java Fundamentals**: X/7 topics complete (X%)
- **Node.js Coverage**: X/12 topics complete (X%)
- **React Development**: X/8 topics complete (X%)
- **Database Systems**: X/15 topics complete (X%)
- **Data Structures**: X/30+ structures complete (X%)
- **Algorithms**: X/25+ categories complete (X%)
- **DevOps**: X/10 topics complete (X%)
- **Cloud/AWS**: X/12 topics complete (X%)
- **Security**: X/8 topics complete (X%)
- **System Design**: X/10 topics complete (X%)

### Interview Question Progress
- **Total Questions**: X/8000+ implemented (X%)
- **Java Questions**: X/1200+ implemented (X%)
- **Node.js Questions**: X/800+ implemented (X%)
- **React Questions**: X/600+ implemented (X%)
- **Algorithm Questions**: X/1500+ implemented (X%)
- **System Design Questions**: X/800+ implemented (X%)
- **Behavioral Questions**: X/500+ implemented (X%)

### Interactive Features Progress
- **Monaco Editor Integration**: X% complete
- **Interactive Terminal**: X% complete
- **Note-Taking System**: X% complete
- **Progress Analytics**: X% complete
- **Centralized Question Hub**: X% complete

## Validation and Quality Checks
- Verify progress calculations are accurate
- Ensure completion status matches actual implementation
- Validate question counts against database records
- Check for consistency across all documentation files
- Confirm next priorities are properly set

## Automated Progress Reports
Generate comprehensive progress reports including:
- Overall project completion percentage
- Module-by-module breakdown
- Question implementation statistics
- Time spent vs. estimated time analysis
- Velocity and productivity metrics
- Projected completion timeline