---
name: "Session Startup Context Loader"
description: "Automatically reads all critical .md files at session start to ensure complete context"
trigger: "session_start"
auto_execute: true
priority: "critical"
keywords: ["load complete context", "session startup", "read all files"]
---

# Session Startup Context Loader

## Purpose
Automatically load all critical documentation files at the start of every session to ensure complete context preservation and prevent any information loss.

## What This Hook Does

### 🔍 **Comprehensive File Reading**
Automatically reads all critical .md files in the correct order:

#### **Phase 1: Core Context Files (CRITICAL)**
1. `COMPLETE_PROJECT_CONTEXT.md` - Complete scope and gap analysis
2. `CURRENT_STATUS.md` - Real-time development state
3. `PROJECT_SCOPE_AND_TRACKING.md` - Master project reference
4. `FRAMEWORK_COMPLETION_SUMMARY.md` - Complete framework status

#### **Phase 2: Specs and Implementation (HIGH)**
5. `.kiro/specs/SPECS_OVERVIEW.md` - All available specs
6. All spec requirements.md files in priority order
7. All spec design.md files for active specs
8. All spec tasks.md files for current implementation

#### **Phase 3: Automation and Protocols (HIGH)**
9. `PROJECT_AUTOMATION_MANAGER.md` - Automation framework
10. `.kiro/steering/documentation-update-protocol.md` - Update protocols
11. `.kiro/steering/specs-based-development-enforcement.md` - Development methodology
12. `.kiro/hooks/README.md` - Available automation hooks

#### **Phase 4: Quality and Standards (MEDIUM)**
13. `AUTOMATION_REVIEW_CHECKLIST.md` - Quality validation
14. `.kiro/steering/world-class-learning-standards.md` - Quality standards
15. `SENIOR_DEVELOPER_READINESS_ANALYSIS.md` - FAANG readiness
16. `DEVELOPMENT_GUIDE.md` - Technical implementation guide

#### **Phase 5: Conversation and Progress (MEDIUM)**
17. `PROJECT_CONVERSATION_LOG.md` - Complete conversation history
18. `README.md` - Project overview and setup
19. All other .md files in root directory

### 🎯 **Context Validation**
- Cross-validates information across multiple files
- Identifies any inconsistencies or missing information
- Generates comprehensive session startup summary
- Provides exact continuation point for development

### 📊 **Session Startup Report**
Generates detailed report including:
- Current project state and progress
- Active specs and next tasks
- Technical environment status
- Recommended next actions
- Any issues or blockers identified

## Trigger Conditions
- **Automatic**: Every new session start
- **Manual**: Say "load complete context" or "session startup"
- **Recovery**: When context appears incomplete or inconsistent

## Expected Outcome
- Complete project context loaded and validated
- All critical information available for decision making
- Clear understanding of current state and next actions
- Zero context loss or confusion at session start

## Implementation Notes
This hook ensures that every session starts with complete context, preventing the confusion and context loss that can occur when starting fresh sessions. It's the foundation for all other automation and ensures consistent, informed development decisions.

## Success Metrics
- 100% of critical files read and validated
- Complete context available within 30 seconds of session start
- Zero sessions starting without full context
- Consistent development decisions based on complete information