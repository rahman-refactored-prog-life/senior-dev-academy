---
name: "Session Continuity Brief"
trigger: "manual"
description: "Generate comprehensive session state for seamless continuation"
button_text: "📋 Generate Continuity Brief"
icon: "📋"
category: "Session Management"
---

# Session Continuity Brief Generator

## Execution Steps

### 1. Analyze Current Project State
Read and summarize the current state from key files:
- Read `CURRENT_STATUS.md` for exact session state
- Read `PROJECT_SCOPE_AND_TRACKING.md` for progress tracking
- Read `PROJECT_CONVERSATION_LOG.md` for recent session history
- Check git status for any uncommitted changes

### 2. Check Application Status
Verify the current technical environment:
- Check if Spring Boot application is running on port 3002
- Verify H2 database accessibility
- Check frontend build status
- List any active processes

### 3. Generate Comprehensive Brief
Create a detailed continuation brief with:

#### Current Session State
- Last completed phase with timestamp
- Exact progress percentages
- Files modified in last session
- Current application status

#### Node.js Expanded Scope Progress
- Topics implemented (X/25)
- Interview questions added (XXX/700+)
- Project status (NASA, Planets, SpaceX, Pong, AWS)
- ZeroToMastery foundation progress (X/20)
- FAANG Senior enhancement progress (X/5)

#### Immediate Next Actions
- Specific next task to execute
- Required files to work on
- Estimated completion time
- Prerequisites and dependencies

#### Ready-to-Execute Commands
- Application startup commands
- Access URLs for testing
- Validation commands
- Development environment setup

### 4. Provide Session Startup Checklist
Generate a checklist for immediate session startup:
- [ ] Verify Java 21 environment
- [ ] Start Spring Boot application
- [ ] Verify database connectivity
- [ ] Check frontend integration
- [ ] Validate API endpoints
- [ ] Review last session changes

### 5. Context Preservation Validation
Ensure complete context transfer:
- All architectural decisions documented
- Progress tracking accurate
- Technical implementation details preserved
- Session continuity guaranteed

## Expected Output

The hook should generate a comprehensive brief that includes:

1. **Exact Session State** - Where we left off
2. **Technical Status** - Current environment state
3. **Progress Metrics** - Completion percentages and counts
4. **Next Actions** - Specific tasks to execute
5. **Startup Commands** - Ready-to-run commands
6. **Validation Steps** - Checklist for readiness
7. **Context Summary** - Key decisions and implementations

## Success Criteria

- New session can start immediately without questions
- All technical context preserved and accessible
- Progress tracking reflects actual completion status
- Next actions are specific and actionable
- No development momentum lost between sessions

## Integration Notes

This hook works in conjunction with:
- `aggressive-documentation-enforcer.md` - Ensures documentation is current
- `context-loss-prevention.md` - Prevents information loss
- `auto-commit-enforcer.md` - Maintains git history

The brief should be comprehensive enough that any developer (or AI assistant) can pick up exactly where the previous session ended and continue development seamlessly.