---
name: "Automated Git Integration"
description: "Automatically commits all changes with comprehensive documentation after every successful task completion"
trigger: "manual"
keywords: ["commit changes", "git commit", "save progress", "commit and document"]
auto_execute: false
priority: "high"
---

# Automated Git Integration

## Purpose
Automatically commit all changes with comprehensive documentation after every successful task completion, ensuring no work is lost and maintaining detailed version history with proper context preservation.

## What This Hook Does

### 🔄 **Comprehensive Change Analysis**
- Analyzes all modified files since last commit
- Identifies specific changes made in current session
- Categorizes changes by type (code, documentation, configuration)
- Validates all changes are properly documented

### 📝 **Intelligent Commit Message Generation**
- Creates descriptive commit messages based on actual changes
- Includes task completion status and progress updates
- References specific spec requirements and acceptance criteria
- Adds session context and continuation information

### 🛡️ **Quality Validation Before Commit**
- Ensures all code compiles successfully
- Validates all documentation is updated
- Checks progress tracking accuracy
- Confirms no critical issues introduced

### 📊 **Documentation Synchronization**
- Updates all 15+ tracking files with current progress
- Ensures cross-validation of information
- Maintains context preservation across all documents
- Validates specs-based development compliance

## Commit Message Format

```
feat(spec-name): Complete task X.Y - [Task Description]

- Implemented: [Specific implementation details]
- Updated: [Documentation files updated]
- Progress: [Current completion percentage]
- Next: [Next task or continuation point]

Specs: [Spec requirements references]
Files: [List of modified files]
Session: [Session context and timestamp]
```

## Pre-Commit Validation Checklist

### ✅ **Code Quality Gates**
- [ ] All Java code compiles without errors or warnings
- [ ] All tests pass (if applicable)
- [ ] No syntax errors in any modified files
- [ ] All new code follows project standards

### ✅ **Documentation Gates**
- [ ] All tracking files updated with current progress
- [ ] Task completion status updated in tasks.md
- [ ] Cross-references validated across documents
- [ ] Session context preserved for next session

### ✅ **Specs Compliance Gates**
- [ ] Changes align with spec requirements
- [ ] Acceptance criteria met for completed tasks
- [ ] No deviation from approved design
- [ ] Quality standards maintained

## Integration with Other Hooks

### **Works With:**
- `specs-based-task-executor` - Commits after task completion
- `bulletproof-session-handoff` - Commits during session end
- `comprehensive-framework-validator` - Validates before commit
- `session-startup-context-loader` - Provides context for commit messages

### **Triggers After:**
- Successful task completion
- Documentation updates
- Quality validation passes
- User requests commit

## Success Metrics
- 100% of completed work committed and backed up
- Zero work loss between sessions
- Complete version history with context
- Accurate progress tracking in git history

## Usage Examples
- "Commit changes" - Commits current work with analysis
- "Save progress" - Quick commit with progress update
- "Commit and document" - Full commit with documentation sync
- "Git commit task 1.2" - Commits specific task completion