---
name: "Auto-Commit Progress"
description: "Automatically commit changes with descriptive message after each major implementation"
trigger: manual
---

# Auto-Commit Progress Hook

## Trigger
**Manual**: After implementing a topic, feature, or completing a phase

## Objective
Quickly commit changes with a well-formatted message without manual approval

## Execution

```bash
# Stage all changes
git add .

# Create commit with auto-generated message based on changes
git commit -m "🚀 [Auto-Commit] Progress Update

Changes:
$(git diff --cached --stat)

Timestamp: $(date)
Session: Continuous Node.js Implementation"

# Optional: Push to remote
# git push origin main
```

## Smart Commit Message Generation

Analyze changes and create descriptive commit:
- If DataInitializer.java modified → "Node.js Topic X Implementation"
- If documentation files → "Documentation Update - Session X"
- If multiple files → "Multi-file Update - [brief description]"

## Usage

Simply say: "commit" or "save progress"

## Benefits
- Fast commits without approval dialogs
- Consistent commit message format
- Automatic change detection
- Git history preservation
