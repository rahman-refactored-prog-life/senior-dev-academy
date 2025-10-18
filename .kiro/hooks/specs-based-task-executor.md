---
name: "Specs-Based Task Executor"
description: "Systematically execute spec tasks with validation and progress tracking"
trigger: "manual"
keywords: ["execute task", "implement task", "start task", "next task"]
auto_execute: false
priority: "high"
---

# Specs-Based Task Executor

## Purpose
Systematically execute individual tasks from specs with proper validation, progress tracking, and quality assurance to ensure consistent, high-quality implementation.

## What This Hook Does

### 🎯 **Task Execution Workflow**
1. **Task Selection and Validation**
   - Identifies current spec and next task to execute
   - Validates task prerequisites are met
   - Confirms task requirements and acceptance criteria
   - Checks for any blockers or dependencies

2. **Pre-Execution Setup**
   - Reads relevant spec files (requirements.md, design.md, tasks.md)
   - Validates current technical environment
   - Ensures all necessary context is loaded
   - Sets up quality gates and validation criteria

3. **Task Implementation**
   - Executes task following spec requirements exactly
   - Implements code following design specifications
   - Validates against acceptance criteria continuously
   - Maintains quality standards throughout implementation

4. **Post-Execution Validation**
   - Validates all code compiles and runs successfully
   - Tests functionality against task requirements
   - Updates task status in tasks.md file
   - Commits changes with descriptive message

### 📊 **Quality Assurance Integration**
- Validates all code against spec requirements
- Ensures acceptance criteria are met
- Runs automated tests and validation
- Maintains documentation synchronization

### 🔄 **Progress Tracking**
- Updates task completion status in real-time
- Tracks progress against spec milestones
- Identifies next task for continuation
- Maintains accurate progress metrics

## Trigger Examples
- "Execute next task"
- "Implement task 1.2"
- "Start backend technical debt task"
- "Continue with current spec"

## Expected Outcome
- Single task completed with full validation
- All acceptance criteria met and verified
- Progress accurately tracked and documented
- Next task clearly identified for continuation
- Zero technical debt or quality issues introduced

## Integration with Other Hooks
- Works with `session-startup-context-loader` for complete context
- Integrates with `auto-commit-enforcer` for automatic commits
- Uses `code-quality-validation` for quality assurance
- Coordinates with `progress-tracking-update` for metrics

## Success Metrics
- 100% task completion with acceptance criteria met
- Zero compilation errors or quality issues
- Accurate progress tracking and documentation
- Clear continuation point for next session