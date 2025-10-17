---
name: "Post-Implementation Documentation Update"
description: "Automatically update all documentation files after each phase implementation"
trigger: "manual"
category: "documentation"
---

# Post-Implementation Documentation Update Hook

## Purpose
Ensure comprehensive documentation updates after every phase implementation to maintain context continuity and progress tracking.

## Trigger Conditions
- Manual execution after completing any implementation phase
- When significant code changes are made to learning content
- After adding new interview questions or topics
- When updating UI/UX components or features

## Actions Performed

### 1. Update CURRENT_STATUS.md
```markdown
## 🎯 EXACT SESSION STATE (Auto-Updated After Phase X.Y)

### **Last Completed Phase**: Phase X.Y - [Phase Description]
### **Completion Timestamp**: [Current ISO DateTime]
### **Progress Percentage**: X.X% (completed/total tasks)

### **Files Modified in Last Session**:
- [List all modified files with descriptions]

### **Next Actions for New Session**:
1. **IMMEDIATE NEXT**: [Next phase or task]
2. **Validation Required**: [What needs verification]
3. **Dependencies**: [Prerequisites for next phase]
4. **Estimated Time**: [Time estimate]
```

### 2. Update PROJECT_SCOPE_AND_TRACKING.md
- Mark completed tasks with ✅ checkboxes
- Update progress percentages for each module
- Add new deliverables and question counts
- Record milestone achievements

### 3. Update PROJECT_CONVERSATION_LOG.md
- Add session summary with technical details
- Document implementation decisions and rationale
- Include code examples and architectural changes
- Note learning outcomes and best practices

### 4. Update DEVELOPMENT_GUIDE.md
- Add implementation sections with step-by-step details
- Include code examples and explanations
- Document architectural patterns used
- Add troubleshooting information

### 5. Update README.md
- Refresh "Latest Updates" section
- Update feature lists and capabilities
- Modify project status and completion percentages
- Add new demo links or screenshots

### 6. Git Commit
- Stage all modified files
- Create descriptive commit message
- Include progress summary and next steps
- Push to remote repository

## Validation Checklist
- [ ] All 6 documentation files updated in correct order
- [ ] Progress tracking reflects actual completion status
- [ ] Next session can start immediately with clear instructions
- [ ] Code examples are tested and functional
- [ ] Git commit completed with descriptive message

## Usage Instructions
1. Complete any implementation phase
2. Run this hook manually from Kiro Hook UI
3. Verify all documentation files are updated
4. Confirm git commit is successful
5. Validate session continuity setup

## Benefits
- Zero context loss between sessions
- Accurate progress tracking
- Seamless session continuity
- Complete implementation history
- Professional documentation standards