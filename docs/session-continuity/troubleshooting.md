# Session Continuity System - Troubleshooting Guide

## Overview

This guide provides comprehensive troubleshooting procedures for the Session Continuity & Context Preservation System. It covers common issues, diagnostic procedures, and resolution steps.

## Table of Contents

1. [Diagnostic Tools](#diagnostic-tools)
2. [Common Issues](#common-issues)
3. [Error Codes Reference](#error-codes-reference)
4. [Recovery Procedures](#recovery-procedures)
5. [Performance Issues](#performance-issues)
6. [Emergency Procedures](#emergency-procedures)

## Diagnostic Tools

### System Health Check

```bash
# Comprehensive system health check
curl http://localhost:8080/api/continuity/health/comprehensive

# Quick health status
curl http://localhost:8080/api/continuity/health/status

# Component-specific health
curl http://localhost:8080/api/continuity/health/component/{componentName}
```

### Log Analysis

```bash
# View session continuity logs
tail -f logs/session-continuity.log

# Search for specific errors
grep -i "error\|exception\|failed" logs/session-continuity.log

# View last 100 log entries
tail -100 logs/session-continuity.log
```

### Context Validation

```bash
# Validate current context
curl http://localhost:8080/api/continuity/context/validate

# Check context completeness
curl http://localhost:8080/api/continuity/context/completeness

# Analyze context integrity
curl http://localhost:8080/api/continuity/context/integrity
```

## Common Issues

### Issue 1: Context Capture Failures

#### Symptoms
- Session state not saved at session end
- Missing continuation points
- Incomplete technical environment capture
- Error messages about file access

#### Diagnostic Steps

1. **Check File Permissions**
   ```bash
   # Verify documentation directory permissions
   ls -la docs/
   
   # Check specific file permissions
   ls -la CURRENT_STATUS.md PROJECT_SCOPE_AND_TRACKING.md
   ```

2. **Verify Disk Space**
   ```bash
   # Check available disk space
   df -h
   
   # Check inode usage
   df -i
   ```

3. **Test Session Capture**
   ```bash
   # Manual session capture test
   curl -X POST http://localhost:8080/api/continuity/session/capture/test
   ```

#### Resolution Steps

1. **Fix File Permissions**
   ```bash
   # Set correct permissions for documentation files
   chmod 755 docs/
   chmod 644 docs/*.md
   chmod 644 *.md
   ```

2. **Free Disk Space**
   ```bash
   # Clean temporary files
   rm -rf target/
   mvn clean
   
   # Remove old log files
   find logs/ -name "*.log.*" -mtime +7 -delete
   ```

3. **Restart Session Capture Service**
   ```bash
   # Restart the application
   mvn spring-boot:stop
   mvn spring-boot:run
   ```

### Issue 2: Documentation Synchronization Errors

#### Symptoms
- Inconsistent information across documentation files
- Missing updates in some files
- Conflict resolution failures
- Cross-reference validation errors

#### Diagnostic Steps

1. **Check Synchronization Status**
   ```bash
   # Get current sync status
   curl http://localhost:8080/api/continuity/docs/sync/status
   
   # Check for conflicts
   curl http://localhost:8080/api/continuity/docs/conflicts
   ```

2. **Validate File Integrity**
   ```bash
   # Check file checksums
   curl http://localhost:8080/api/continuity/docs/integrity
   
   # Verify cross-references
   curl http://localhost:8080/api/continuity/docs/cross-references/validate
   ```

3. **Analyze Update History**
   ```bash
   # Review recent updates
   curl http://localhost:8080/api/continuity/docs/update-history
   ```

#### Resolution Steps

1. **Force Synchronization**
   ```bash
   # Force complete synchronization
   curl -X POST http://localhost:8080/api/continuity/docs/sync-force
   ```

2. **Resolve Conflicts Manually**
   ```bash
   # Get conflict details
   curl http://localhost:8080/api/continuity/docs/conflicts/details
   
   # Resolve specific conflict
   curl -X POST http://localhost:8080/api/continuity/docs/conflicts/resolve \
     -H "Content-Type: application/json" \
     -d '{"conflictId": "conflict-123", "resolution": "use-latest"}'
   ```

3. **Rebuild Cross-References**
   ```bash
   # Rebuild all cross-references
   curl -X POST http://localhost:8080/api/continuity/docs/cross-references/rebuild
   ```

### Issue 3: Quality Gate Failures

#### Symptoms
- Session completion blocked
- Compilation errors preventing progression
- Documentation validation failures
- Progress tracking inconsistencies

#### Diagnostic Steps

1. **Check Quality Gate Status**
   ```bash
   # Get overall quality gate status
   curl http://localhost:8080/api/continuity/quality-gates/status
   
   # Check specific gate
   curl http://localhost:8080/api/continuity/quality-gates/compilation/status
   ```

2. **Analyze Compilation Issues**
   ```bash
   # Run compilation check
   mvn clean compile -X
   
   # Check for dependency issues
   mvn dependency:analyze
   ```

3. **Validate Documentation**
   ```bash
   # Check documentation completeness
   curl http://localhost:8080/api/continuity/docs/completeness
   ```

#### Resolution Steps

1. **Fix Compilation Errors**
   ```bash
   # Clean and recompile
   mvn clean compile
   
   # Fix dependency issues
   mvn dependency:resolve
   ```

2. **Update Missing Documentation**
   ```bash
   # Force documentation update
   curl -X POST http://localhost:8080/api/continuity/docs/update-missing
   ```

3. **Emergency Bypass (Use Sparingly)**
   ```bash
   # Bypass quality gates with justification
   curl -X POST http://localhost:8080/api/continuity/quality-gates/bypass \
     -H "Content-Type: application/json" \
     -d '{
       "gateName": "compilation",
       "justification": "Emergency deployment - compilation issues will be fixed in next session",
       "approver": "senior-developer",
       "timeLimit": "24h"
     }'
   ```

### Issue 4: Context Recovery Problems

#### Symptoms
- Missing context after session restart
- Incomplete context reconstruction
- Low confidence scores in recovery
- Unable to determine continuation point

#### Diagnostic Steps

1. **Analyze Available Sources**
   ```bash
   # Check recovery sources
   curl http://localhost:8080/api/continuity/recovery/sources/analyze
   
   # Get source reliability scores
   curl http://localhost:8080/api/continuity/recovery/sources/reliability
   ```

2. **Test Recovery Process**
   ```bash
   # Test context reconstruction
   curl -X POST http://localhost:8080/api/continuity/recovery/test
   ```

3. **Check Git History**
   ```bash
   # Review recent commits
   git log --oneline -10
   
   # Check for session-related commits
   git log --grep="session" --oneline
   ```

#### Resolution Steps

1. **Manual Context Reconstruction**
   ```bash
   # Analyze git history for context clues
   git log --stat -5
   
   # Check documentation file history
   git log --follow CURRENT_STATUS.md
   ```

2. **Rebuild Context from Sources**
   ```bash
   # Force context reconstruction
   curl -X POST http://localhost:8080/api/continuity/recovery/reconstruct-force
   
   # Validate reconstruction
   curl http://localhost:8080/api/continuity/recovery/validate
   ```

3. **Create New Session State**
   ```bash
   # Initialize new session state
   curl -X POST http://localhost:8080/api/continuity/session/initialize-new
   ```

### Issue 5: Performance Degradation

#### Symptoms
- Slow session capture (> 5 seconds)
- Documentation updates taking too long (> 10 seconds)
- Context recovery exceeding time limits (> 30 seconds)
- High memory or CPU usage

#### Diagnostic Steps

1. **Check Performance Metrics**
   ```bash
   # Get performance statistics
   curl http://localhost:8080/api/continuity/performance/metrics
   
   # Check resource usage
   curl http://localhost:8080/api/continuity/performance/resources
   ```

2. **Analyze Bottlenecks**
   ```bash
   # Profile session capture
   curl http://localhost:8080/api/continuity/performance/profile/capture
   
   # Profile documentation sync
   curl http://localhost:8080/api/continuity/performance/profile/sync
   ```

#### Resolution Steps

1. **Optimize Configuration**
   ```yaml
   # application.yml - Performance optimizations
   session-continuity:
     performance:
       capture-timeout: 3000ms
       sync-batch-size: 5
       validation-threads: 2
       cache-enabled: true
   ```

2. **Clean Up Resources**
   ```bash
   # Clear caches
   curl -X POST http://localhost:8080/api/continuity/cache/clear
   
   # Optimize file storage
   curl -X POST http://localhost:8080/api/continuity/storage/optimize
   ```

3. **Restart Services**
   ```bash
   # Restart application for fresh state
   mvn spring-boot:stop
   mvn spring-boot:run
   ```

## Error Codes Reference

### Context Capture Errors (SC001-SC099)

| Code | Description | Severity | Resolution |
|------|-------------|----------|------------|
| SC001 | Context capture timeout | High | Increase timeout, check system resources |
| SC002 | File permission denied | High | Fix file permissions with chmod |
| SC003 | Insufficient disk space | Critical | Free disk space, clean temporary files |
| SC004 | Session state corruption | High | Restore from backup, reinitialize session |
| SC005 | Technical environment capture failed | Medium | Verify Java/Maven installation |

### Documentation Sync Errors (SC100-SC199)

| Code | Description | Severity | Resolution |
|------|-------------|----------|------------|
| SC101 | Documentation sync failure | High | Force synchronization, check file access |
| SC102 | Cross-reference validation error | Medium | Rebuild cross-references |
| SC103 | Conflict resolution timeout | Medium | Resolve conflicts manually |
| SC104 | File integrity check failed | High | Restore from git history |
| SC105 | Update ordering violation | Low | Reorder updates, force sync |

### Quality Gate Errors (SC200-SC299)

| Code | Description | Severity | Resolution |
|------|-------------|----------|------------|
| SC201 | Compilation gate failure | Critical | Fix compilation errors |
| SC202 | Documentation gate failure | High | Update missing documentation |
| SC203 | Progress gate failure | Medium | Correct progress metrics |
| SC204 | Quality assurance gate failure | High | Address quality issues |
| SC205 | Emergency bypass expired | Medium | Renew bypass or fix issues |

### Recovery Errors (SC300-SC399)

| Code | Description | Severity | Resolution |
|------|-------------|----------|------------|
| SC301 | Recovery source unavailable | High | Check git history, verify files |
| SC302 | Context reconstruction failed | High | Manual reconstruction required |
| SC303 | Validation threshold not met | Medium | Review accuracy, manual intervention |
| SC304 | Missing critical information | Critical | Restore from backup sources |
| SC305 | Recovery confidence too low | Medium | Use manual reconstruction |

## Recovery Procedures

### Complete System Recovery

When the session continuity system is completely non-functional:

1. **Emergency Shutdown**
   ```bash
   # Stop all services
   mvn spring-boot:stop
   pkill -f "spring-boot"
   ```

2. **Backup Current State**
   ```bash
   # Create backup of current files
   mkdir -p backups/$(date +%Y%m%d_%H%M%S)
   cp *.md backups/$(date +%Y%m%d_%H%M%S)/
   cp -r docs/ backups/$(date +%Y%m%d_%H%M%S)/
   ```

3. **Restore from Git**
   ```bash
   # Find last known good commit
   git log --grep="session-continuity" --oneline -5
   
   # Reset to last good state (if necessary)
   git reset --hard <commit-hash>
   ```

4. **Reinitialize System**
   ```bash
   # Clean build
   mvn clean install
   
   # Initialize session continuity
   mvn spring-boot:run -Dspring-boot.run.arguments="--init-session-continuity"
   ```

### Partial Recovery

When only specific components are failing:

1. **Component-Specific Recovery**
   ```bash
   # Restart specific component
   curl -X POST http://localhost:8080/api/continuity/component/restart/{componentName}
   
   # Reset component state
   curl -X POST http://localhost:8080/api/continuity/component/reset/{componentName}
   ```

2. **Data Recovery**
   ```bash
   # Recover from redundant sources
   curl -X POST http://localhost:8080/api/continuity/recovery/redundant-sources
   
   # Validate recovered data
   curl http://localhost:8080/api/continuity/recovery/validate-data
   ```

## Performance Issues

### Monitoring Performance

```bash
# Real-time performance monitoring
curl http://localhost:8080/api/continuity/performance/monitor

# Performance history
curl http://localhost:8080/api/continuity/performance/history

# Resource utilization
curl http://localhost:8080/api/continuity/performance/resources
```

### Performance Optimization

1. **Configuration Tuning**
   ```yaml
   # Optimized configuration
   session-continuity:
     performance:
       capture-parallel: true
       sync-async: true
       validation-lazy: true
       cache-size: 1000
   ```

2. **Resource Management**
   ```bash
   # Monitor memory usage
   jstat -gc $(pgrep java)
   
   # Monitor CPU usage
   top -p $(pgrep java)
   ```

## Emergency Procedures

### Critical System Failure

1. **Immediate Actions**
   ```bash
   # Stop all processes
   pkill -f "session-continuity"
   
   # Preserve current state
   tar -czf emergency-backup-$(date +%Y%m%d_%H%M%S).tar.gz *.md docs/
   ```

2. **Assessment**
   ```bash
   # Check system integrity
   find . -name "*.md" -exec wc -l {} \;
   
   # Verify git repository
   git fsck --full
   ```

3. **Recovery Strategy**
   ```bash
   # Determine recovery approach
   if [ -f "CURRENT_STATUS.md" ]; then
     echo "Attempting partial recovery"
   else
     echo "Full recovery required"
   fi
   ```

### Data Corruption

1. **Detect Corruption**
   ```bash
   # Check file integrity
   curl http://localhost:8080/api/continuity/integrity/check-all
   
   # Verify checksums
   curl http://localhost:8080/api/continuity/integrity/checksums
   ```

2. **Isolate Corruption**
   ```bash
   # Identify corrupted files
   curl http://localhost:8080/api/continuity/integrity/corrupted-files
   
   # Assess impact
   curl http://localhost:8080/api/continuity/integrity/impact-analysis
   ```

3. **Restore Clean Data**
   ```bash
   # Restore from git
   git checkout HEAD -- <corrupted-file>
   
   # Rebuild from sources
   curl -X POST http://localhost:8080/api/continuity/rebuild/from-sources
   ```

## Support Escalation

### When to Escalate

- Critical system failures lasting > 30 minutes
- Data corruption affecting multiple files
- Performance degradation > 50% for > 1 hour
- Security-related issues
- Recovery procedures failing repeatedly

### Escalation Information

Provide the following information when escalating:

1. **System State**
   ```bash
   # Collect system information
   curl http://localhost:8080/api/continuity/support/system-info > system-info.json
   ```

2. **Error Logs**
   ```bash
   # Collect relevant logs
   tail -1000 logs/session-continuity.log > error-logs.txt
   ```

3. **Configuration**
   ```bash
   # Export configuration
   curl http://localhost:8080/api/continuity/support/config-export > config.json
   ```

4. **Recent Changes**
   ```bash
   # Git history
   git log --oneline -20 > recent-changes.txt
   ```

---

*This troubleshooting guide provides comprehensive procedures for resolving issues with the Session Continuity System. For additional support, consult the user guide and system documentation.*