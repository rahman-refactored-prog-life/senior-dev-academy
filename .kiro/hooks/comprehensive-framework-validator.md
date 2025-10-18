---
name: "Comprehensive Framework Validator"
description: "Validates entire framework integrity and identifies any gaps or inconsistencies"
trigger: "manual"
keywords: ["validate framework", "check framework", "framework status", "system check"]
auto_execute: false
priority: "high"
---

# Comprehensive Framework Validator

## Purpose
Perform comprehensive validation of the entire specs-based development framework to ensure all components are properly integrated, consistent, and ready for systematic implementation.

## What This Hook Does

### 🔍 **Framework Component Validation**

#### **Specs System Validation**
- Validates all 6+ specs have complete requirements.md files
- Checks design.md files exist and are comprehensive
- Verifies tasks.md files have granular, actionable tasks
- Ensures all specs follow EARS requirements format
- Validates task numbering and dependency relationships

#### **Hooks System Validation**
- Verifies all 20+ hooks are properly configured
- Checks hook trigger conditions and keywords
- Validates hook integration and dependencies
- Ensures hooks are visible in IDE Agent Hooks section
- Tests hook functionality and automation capabilities

#### **Documentation System Validation**
- Validates all 15+ tracking files are current and consistent
- Checks cross-references between files are accurate
- Verifies documentation update protocols are followed
- Ensures steering files are properly configured
- Validates context preservation mechanisms

#### **Quality Assurance Validation**
- Checks all quality gates are properly configured
- Validates testing and validation requirements
- Ensures compilation and runtime validation works
- Verifies progress tracking accuracy
- Tests context recovery procedures

### 📊 **Consistency Analysis**
- Cross-validates information across all documentation files
- Identifies inconsistencies or outdated information
- Checks progress tracking accuracy against actual implementation
- Validates spec task completion status
- Ensures all references and links are current

### 🚨 **Gap Identification**
- Identifies missing specs for incomplete areas
- Finds gaps in hook coverage or automation
- Discovers missing documentation or protocols
- Identifies quality assurance gaps
- Highlights areas needing attention

### 📋 **Comprehensive Report Generation**
Generates detailed report including:
- Framework component status (complete/incomplete/needs attention)
- Consistency validation results
- Gap analysis with prioritized recommendations
- Quality assurance status
- Readiness assessment for systematic implementation

## Trigger Examples
- "Validate framework"
- "Check system integrity"
- "Framework status report"
- "Comprehensive system check"

## Expected Outcome
- Complete framework validation report
- Identification of any gaps or inconsistencies
- Prioritized recommendations for improvements
- Confirmation of readiness for systematic implementation
- Action plan for addressing any issues found

## Validation Categories

### ✅ **Complete and Ready**
- All components properly configured and integrated
- No gaps or inconsistencies found
- Ready for systematic implementation

### ⚠️ **Needs Attention**
- Minor gaps or inconsistencies identified
- Recommendations provided for improvement
- Can proceed with caution

### ❌ **Critical Issues**
- Major gaps or inconsistencies found
- Framework not ready for systematic implementation
- Must address issues before proceeding

## Success Metrics
- 100% framework component validation
- Zero critical inconsistencies
- Complete gap analysis with recommendations
- Clear readiness assessment for implementation
- Actionable improvement plan when needed