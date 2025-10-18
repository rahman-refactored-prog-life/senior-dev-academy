# 🚨 AGGRESSIVE DOCUMENTATION ENFORCER

## Hook Configuration
```yaml
name: "Aggressive Documentation Enforcer"
trigger: "on_file_save"
priority: "CRITICAL"
auto_execute: true
```

## Purpose
MANDATORY documentation updates after ANY significant change to prevent context loss.

## Execution Rules
1. **TRIGGER**: Any file save in src/, frontend/, or root config files
2. **ACTION**: Automatically update all 9 documentation files
3. **VALIDATION**: Ensure no context is lost between sessions
4. **COMMIT**: Auto-commit with descriptive messages

## Files to Auto-Update
1. CURRENT_STATUS.md - Session state and continuation point
2. PROJECT_SCOPE_AND_TRACKING.md - Progress tracking
3. PROJECT_CONVERSATION_LOG.md - Session documentation
4. DEVELOPMENT_GUIDE.md - Technical implementation details
5. README.md - Latest achievements and status
6. PROJECT_AUTOMATION_MANAGER.md - Automation progress
7. AUTOMATION_REVIEW_CHECKLIST.md - Quality validation
8. SENIOR_DEVELOPER_READINESS_ANALYSIS.md - Skill assessment
9. Git commit with comprehensive message

## Auto-Execution Script
```bash
#!/bin/bash
# Update timestamp
echo "Last updated: $(date -u +"%Y-%m-%dT%H:%M:%SZ")" >> CURRENT_STATUS.md

# Auto-commit with context preservation
git add .
git commit -m "🤖 AUTO-DOC: Context preservation after file changes

Files modified: $MODIFIED_FILES
Timestamp: $(date -u +"%Y-%m-%dT%H:%M:%SZ")
Session continuity: PRESERVED"
```

## Validation Checklist
- [ ] All 9 files updated
- [ ] Progress percentages accurate
- [ ] Next session instructions clear
- [ ] Code preservation verified
- [ ] Context fully captured