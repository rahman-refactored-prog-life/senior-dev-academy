# Disaster Recovery and Backup Procedures

## Overview

This document outlines comprehensive disaster recovery (DR) and backup procedures for the Learning Portal system to ensure business continuity and data protection in case of system failures, data corruption, or catastrophic events.

## Table of Contents

1. [Disaster Recovery Strategy](#disaster-recovery-strategy)
2. [Backup Procedures](#backup-procedures)
3. [Recovery Procedures](#recovery-procedures)
4. [Business Continuity Planning](#business-continuity-planning)
5. [Testing and Validation](#testing-and-validation)
6. [Incident Response](#incident-response)
7. [Monitoring and Alerting](#monitoring-and-alerting)

---

## Disaster Recovery Strategy

### Recovery Objectives

#### Recovery Time Objective (RTO)
- **Critical Systems**: 4 hours maximum downtime
- **Non-Critical Systems**: 24 hours maximum downtime
- **Data Recovery**: 1 hour maximum for recent data

#### Recovery Point Objective (RPO)
- **Database**: Maximum 15 minutes of data loss
- **Application Files**: Maximum 1 hour of data loss
- **User Content**: Maximum 5 minutes of data loss

### Disaster Scenarios

#### 1. Hardware Failure
- **Scope**: Single server or component failure
- **Impact**: Partial service degradation
- **Recovery Strategy**: Failover to redundant systems

#### 2. Data Center Outage
- **Scope**: Complete data center unavailability
- **Impact**: Full service outage
- **Recovery Strategy**: Failover to secondary data center

#### 3. Data Corruption
- **Scope**: Database or file system corruption
- **Impact**: Data integrity issues
- **Recovery Strategy**: Restore from backups

#### 4. Security Breach
- **Scope**: Unauthorized access or data breach
- **Impact**: Data confidentiality compromise
- **Recovery Strategy**: Incident response and system restoration

#### 5. Natural Disaster
- **Scope**: Regional infrastructure damage
- **Impact**: Extended service outage
- **Recovery Strategy**: Geographic failover

---

## Backup Procedures

### Database Backup Strategy

#### 1. PostgreSQL Continuous Backup

```bash
#!/bin/bash
# postgresql-backup.sh - Automated PostgreSQL backup script

# Configuration
DB_NAME="learning_portal"
DB_USER="postgres"
BACKUP_DIR="/var/backups/postgresql"
RETENTION_DAYS=30
S3_BUCKET="learning-portal-backups"

# Create backup directory
mkdir -p $BACKUP_DIR

# Generate timestamp
TIMESTAMP=$(date +"%Y%m%d_%H%M%S")
BACKUP_FILE="$BACKUP_DIR/${DB_NAME}_${TIMESTAMP}.sql"

# Create database dump
pg_dump -h localhost -U $DB_USER -d $DB_NAME \
    --verbose --clean --no-owner --no-privileges \
    --format=custom > $BACKUP_FILE

# Compress backup
gzip $BACKUP_FILE

# Upload to S3
aws s3 cp ${BACKUP_FILE}.gz s3://$S3_BUCKET/database/

# Clean up old local backups
find $BACKUP_DIR -name "*.sql.gz" -mtime +$RETENTION_DAYS -delete

# Log backup completion
echo "$(date): Database backup completed - ${BACKUP_FILE}.gz" >> /var/log/backup.log
```

#### 2. Point-in-Time Recovery Setup

```bash
# Enable WAL archiving in postgresql.conf
wal_level = replica
archive_mode = on
archive_command = 'aws s3 cp %p s3://learning-portal-backups/wal/%f'
max_wal_senders = 3
checkpoint_completion_target = 0.9
```

#### 3. Automated Backup Scheduling

```bash
# Add to crontab
# Full backup daily at 2 AM
0 2 * * * /opt/scripts/postgresql-backup.sh

# WAL backup every 5 minutes
*/5 * * * * /opt/scripts/wal-backup.sh

# Backup verification daily at 3 AM
0 3 * * * /opt/scripts/verify-backup.sh
```

### Application Backup Strategy

#### 1. Application Files Backup

```bash
#!/bin/bash
# application-backup.sh - Application files backup script

# Configuration
APP_DIR="/opt/learning-portal"
BACKUP_DIR="/var/backups/application"
RETENTION_DAYS=7
S3_BUCKET="learning-portal-backups"

# Create backup
TIMESTAMP=$(date +"%Y%m%d_%H%M%S")
BACKUP_FILE="$BACKUP_DIR/application_${TIMESTAMP}.tar.gz"

# Create compressed archive
tar -czf $BACKUP_FILE \
    --exclude='*.log' \
    --exclude='tmp/*' \
    --exclude='cache/*' \
    $APP_DIR

# Upload to S3
aws s3 cp $BACKUP_FILE s3://$S3_BUCKET/application/

# Clean up old backups
find $BACKUP_DIR -name "application_*.tar.gz" -mtime +$RETENTION_DAYS -delete

echo "$(date): Application backup completed - $BACKUP_FILE" >> /var/log/backup.log
```

#### 2. Configuration Backup

```bash
#!/bin/bash
# config-backup.sh - Configuration files backup script

# Configuration directories
CONFIG_DIRS=(
    "/etc/nginx"
    "/etc/ssl"
    "/opt/learning-portal/config"
    "/etc/systemd/system/learning-portal.service"
)

BACKUP_DIR="/var/backups/config"
TIMESTAMP=$(date +"%Y%m%d_%H%M%S")
BACKUP_FILE="$BACKUP_DIR/config_${TIMESTAMP}.tar.gz"

# Create backup
tar -czf $BACKUP_FILE "${CONFIG_DIRS[@]}"

# Upload to S3
aws s3 cp $BACKUP_FILE s3://learning-portal-backups/config/

echo "$(date): Configuration backup completed - $BACKUP_FILE" >> /var/log/backup.log
```

### User Data Backup Strategy

#### 1. User-Generated Content Backup

```bash
#!/bin/bash
# user-data-backup.sh - User data backup script

# Configuration
USER_DATA_DIR="/var/lib/learning-portal/user-data"
BACKUP_DIR="/var/backups/user-data"
S3_BUCKET="learning-portal-backups"

# Incremental backup using rsync
TIMESTAMP=$(date +"%Y%m%d_%H%M%S")
BACKUP_PATH="$BACKUP_DIR/$TIMESTAMP"

# Create incremental backup
rsync -av --link-dest="$BACKUP_DIR/latest" \
    $USER_DATA_DIR/ $BACKUP_PATH/

# Update latest symlink
rm -f "$BACKUP_DIR/latest"
ln -s $BACKUP_PATH "$BACKUP_DIR/latest"

# Sync to S3
aws s3 sync $BACKUP_PATH s3://$S3_BUCKET/user-data/$TIMESTAMP/

echo "$(date): User data backup completed - $BACKUP_PATH" >> /var/log/backup.log
```

---

## Recovery Procedures

### Database Recovery

#### 1. Full Database Restore

```bash
#!/bin/bash
# restore-database.sh - Database restoration script

# Configuration
BACKUP_FILE="$1"
DB_NAME="learning_portal"
DB_USER="postgres"

if [ -z "$BACKUP_FILE" ]; then
    echo "Usage: $0 <backup_file>"
    exit 1
fi

# Stop application
systemctl stop learning-portal

# Drop existing database
dropdb -U $DB_USER $DB_NAME

# Create new database
createdb -U $DB_USER $DB_NAME

# Restore from backup
if [[ $BACKUP_FILE == *.gz ]]; then
    gunzip -c $BACKUP_FILE | pg_restore -U $DB_USER -d $DB_NAME
else
    pg_restore -U $DB_USER -d $DB_NAME $BACKUP_FILE
fi

# Start application
systemctl start learning-portal

echo "Database restoration completed from $BACKUP_FILE"
```

#### 2. Point-in-Time Recovery

```bash
#!/bin/bash
# point-in-time-recovery.sh - PITR script

# Configuration
RECOVERY_TARGET_TIME="$1"
BASE_BACKUP_PATH="$2"
WAL_ARCHIVE_PATH="s3://learning-portal-backups/wal/"

if [ -z "$RECOVERY_TARGET_TIME" ] || [ -z "$BASE_BACKUP_PATH" ]; then
    echo "Usage: $0 <recovery_time> <base_backup_path>"
    echo "Example: $0 '2024-01-01 12:00:00' /var/backups/base/20240101_120000"
    exit 1
fi

# Stop PostgreSQL
systemctl stop postgresql

# Clear data directory
rm -rf /var/lib/postgresql/13/main/*

# Restore base backup
tar -xzf $BASE_BACKUP_PATH -C /var/lib/postgresql/13/main/

# Create recovery configuration
cat > /var/lib/postgresql/13/main/recovery.conf << EOF
restore_command = 'aws s3 cp s3://learning-portal-backups/wal/%f %p'
recovery_target_time = '$RECOVERY_TARGET_TIME'
recovery_target_action = 'promote'
EOF

# Set permissions
chown -R postgres:postgres /var/lib/postgresql/13/main/

# Start PostgreSQL
systemctl start postgresql

echo "Point-in-time recovery initiated to $RECOVERY_TARGET_TIME"
```

### Application Recovery

#### 1. Application Restore

```bash
#!/bin/bash
# restore-application.sh - Application restoration script

BACKUP_FILE="$1"
APP_DIR="/opt/learning-portal"

if [ -z "$BACKUP_FILE" ]; then
    echo "Usage: $0 <backup_file>"
    exit 1
fi

# Stop application
systemctl stop learning-portal

# Backup current installation
mv $APP_DIR ${APP_DIR}.backup.$(date +%s)

# Create application directory
mkdir -p $APP_DIR

# Restore from backup
tar -xzf $BACKUP_FILE -C /

# Set permissions
chown -R learning-portal:learning-portal $APP_DIR

# Start application
systemctl start learning-portal

echo "Application restoration completed from $BACKUP_FILE"
```

#### 2. Configuration Restore

```bash
#!/bin/bash
# restore-config.sh - Configuration restoration script

CONFIG_BACKUP="$1"

if [ -z "$CONFIG_BACKUP" ]; then
    echo "Usage: $0 <config_backup_file>"
    exit 1
fi

# Backup current configuration
tar -czf /tmp/config_backup_$(date +%s).tar.gz \
    /etc/nginx /etc/ssl /opt/learning-portal/config

# Restore configuration
tar -xzf $CONFIG_BACKUP -C /

# Reload services
systemctl reload nginx
systemctl restart learning-portal

echo "Configuration restoration completed from $CONFIG_BACKUP"
```

---

## Business Continuity Planning

### Failover Procedures

#### 1. Automatic Failover Setup

```yaml
# docker-compose-ha.yml - High Availability Setup
version: '3.8'
services:
  app-primary:
    image: learning-portal:latest
    environment:
      - DATABASE_URL=postgresql://primary-db:5432/learning_portal
      - REDIS_URL=redis://redis-cluster:6379
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/actuator/health"]
      interval: 30s
      timeout: 10s
      retries: 3
  
  app-secondary:
    image: learning-portal:latest
    environment:
      - DATABASE_URL=postgresql://secondary-db:5432/learning_portal
      - REDIS_URL=redis://redis-cluster:6379
    depends_on:
      - app-primary
    profiles:
      - failover
  
  load-balancer:
    image: nginx:alpine
    ports:
      - "80:80"
      - "443:443"
    volumes:
      - ./nginx-ha.conf:/etc/nginx/nginx.conf
    depends_on:
      - app-primary
```

#### 2. Load Balancer Configuration

```nginx
# nginx-ha.conf - High Availability Load Balancer
upstream learning_portal {
    server app-primary:8080 max_fails=3 fail_timeout=30s;
    server app-secondary:8080 backup;
}

server {
    listen 80;
    server_name learningportal.com;
    
    location /health {
        access_log off;
        return 200 "healthy\n";
    }
    
    location / {
        proxy_pass http://learning_portal;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        
        # Health check
        proxy_next_upstream error timeout invalid_header http_500 http_502 http_503;
        proxy_connect_timeout 5s;
        proxy_send_timeout 10s;
        proxy_read_timeout 10s;
    }
}
```

### Geographic Redundancy

#### 1. Multi-Region Setup

```bash
#!/bin/bash
# setup-geographic-redundancy.sh

# Primary region (us-east-1)
PRIMARY_REGION="us-east-1"
PRIMARY_DB_ENDPOINT="primary-db.us-east-1.rds.amazonaws.com"

# Secondary region (us-west-2)
SECONDARY_REGION="us-west-2"
SECONDARY_DB_ENDPOINT="secondary-db.us-west-2.rds.amazonaws.com"

# Setup cross-region database replication
aws rds create-db-cluster-snapshot \
    --region $PRIMARY_REGION \
    --db-cluster-identifier learning-portal-primary \
    --db-cluster-snapshot-identifier learning-portal-snapshot-$(date +%s)

# Copy snapshot to secondary region
aws rds copy-db-cluster-snapshot \
    --region $SECONDARY_REGION \
    --source-region $PRIMARY_REGION \
    --source-db-cluster-snapshot-identifier learning-portal-snapshot-$(date +%s) \
    --target-db-cluster-snapshot-identifier learning-portal-replica-snapshot

# Create read replica in secondary region
aws rds create-db-cluster \
    --region $SECONDARY_REGION \
    --db-cluster-identifier learning-portal-replica \
    --engine postgres \
    --snapshot-identifier learning-portal-replica-snapshot
```

---

## Testing and Validation

### Backup Verification

#### 1. Automated Backup Testing

```bash
#!/bin/bash
# test-backup-integrity.sh - Backup integrity testing

BACKUP_FILE="$1"
TEST_DB="learning_portal_test"

if [ -z "$BACKUP_FILE" ]; then
    echo "Usage: $0 <backup_file>"
    exit 1
fi

# Create test database
createdb $TEST_DB

# Restore backup to test database
if [[ $BACKUP_FILE == *.gz ]]; then
    gunzip -c $BACKUP_FILE | pg_restore -d $TEST_DB
else
    pg_restore -d $TEST_DB $BACKUP_FILE
fi

# Run integrity checks
psql -d $TEST_DB -c "
    SELECT 
        schemaname,
        tablename,
        n_tup_ins,
        n_tup_upd,
        n_tup_del
    FROM pg_stat_user_tables;
"

# Verify critical tables
CRITICAL_TABLES=("users" "learning_modules" "topics" "user_progress")
for table in "${CRITICAL_TABLES[@]}"; do
    count=$(psql -d $TEST_DB -t -c "SELECT COUNT(*) FROM $table;")
    echo "Table $table: $count rows"
done

# Clean up test database
dropdb $TEST_DB

echo "Backup integrity test completed for $BACKUP_FILE"
```

### Disaster Recovery Testing

#### 1. DR Drill Procedure

```bash
#!/bin/bash
# dr-drill.sh - Disaster Recovery Drill

# Configuration
DR_ENVIRONMENT="dr-test"
BACKUP_DATE="$1"

if [ -z "$BACKUP_DATE" ]; then
    echo "Usage: $0 <backup_date>"
    echo "Example: $0 20240101"
    exit 1
fi

echo "Starting DR drill for backup date: $BACKUP_DATE"

# 1. Setup DR environment
docker-compose -f docker-compose-dr.yml up -d

# 2. Restore database
./restore-database.sh /var/backups/postgresql/learning_portal_${BACKUP_DATE}_*.sql.gz

# 3. Restore application
./restore-application.sh /var/backups/application/application_${BACKUP_DATE}_*.tar.gz

# 4. Restore configuration
./restore-config.sh /var/backups/config/config_${BACKUP_DATE}_*.tar.gz

# 5. Run health checks
sleep 30
curl -f http://localhost:8080/actuator/health || {
    echo "Health check failed"
    exit 1
}

# 6. Run smoke tests
./run-smoke-tests.sh

# 7. Generate DR drill report
./generate-dr-report.sh $BACKUP_DATE

echo "DR drill completed successfully"
```

#### 2. Recovery Time Testing

```bash
#!/bin/bash
# measure-recovery-time.sh - Recovery time measurement

START_TIME=$(date +%s)

echo "Starting recovery time measurement at $(date)"

# Simulate failure
systemctl stop learning-portal
systemctl stop postgresql

# Start recovery
./restore-database.sh $1
./restore-application.sh $2

# Wait for service to be healthy
while ! curl -f http://localhost:8080/actuator/health > /dev/null 2>&1; do
    sleep 5
done

END_TIME=$(date +%s)
RECOVERY_TIME=$((END_TIME - START_TIME))

echo "Recovery completed in $RECOVERY_TIME seconds"
echo "$(date): Recovery time: ${RECOVERY_TIME}s" >> /var/log/recovery-metrics.log

# Check if RTO is met (4 hours = 14400 seconds)
if [ $RECOVERY_TIME -gt 14400 ]; then
    echo "WARNING: Recovery time exceeds RTO of 4 hours"
    # Send alert
    ./send-alert.sh "RTO_EXCEEDED" "Recovery time: ${RECOVERY_TIME}s"
fi
```

---

## Incident Response

### Incident Classification

#### Severity Levels

1. **Critical (P1)**
   - Complete service outage
   - Data loss or corruption
   - Security breach
   - Response time: 15 minutes

2. **High (P2)**
   - Partial service degradation
   - Performance issues affecting users
   - Non-critical data issues
   - Response time: 1 hour

3. **Medium (P3)**
   - Minor functionality issues
   - Non-user-facing problems
   - Response time: 4 hours

4. **Low (P4)**
   - Cosmetic issues
   - Enhancement requests
   - Response time: 24 hours

### Incident Response Procedures

#### 1. Incident Detection and Alerting

```bash
#!/bin/bash
# incident-detection.sh - Automated incident detection

# Check application health
if ! curl -f http://localhost:8080/actuator/health > /dev/null 2>&1; then
    ./trigger-incident.sh "P1" "APPLICATION_DOWN" "Application health check failed"
fi

# Check database connectivity
if ! pg_isready -h localhost -p 5432 > /dev/null 2>&1; then
    ./trigger-incident.sh "P1" "DATABASE_DOWN" "Database connectivity check failed"
fi

# Check disk space
DISK_USAGE=$(df / | awk 'NR==2 {print $5}' | sed 's/%//')
if [ $DISK_USAGE -gt 90 ]; then
    ./trigger-incident.sh "P2" "DISK_SPACE_LOW" "Disk usage: ${DISK_USAGE}%"
fi

# Check memory usage
MEMORY_USAGE=$(free | awk 'NR==2{printf "%.0f", $3*100/$2}')
if [ $MEMORY_USAGE -gt 90 ]; then
    ./trigger-incident.sh "P2" "MEMORY_HIGH" "Memory usage: ${MEMORY_USAGE}%"
fi
```

#### 2. Incident Response Workflow

```bash
#!/bin/bash
# incident-response.sh - Incident response workflow

SEVERITY="$1"
INCIDENT_TYPE="$2"
DESCRIPTION="$3"

# Generate incident ID
INCIDENT_ID="INC-$(date +%Y%m%d%H%M%S)"

# Log incident
echo "$(date): [$INCIDENT_ID] $SEVERITY - $INCIDENT_TYPE: $DESCRIPTION" >> /var/log/incidents.log

# Notify on-call team
case $SEVERITY in
    "P1")
        # Critical - immediate notification
        ./notify-oncall.sh "$INCIDENT_ID" "$DESCRIPTION" "CRITICAL"
        ./start-war-room.sh "$INCIDENT_ID"
        ;;
    "P2")
        # High - notify team lead
        ./notify-team-lead.sh "$INCIDENT_ID" "$DESCRIPTION"
        ;;
    "P3"|"P4")
        # Medium/Low - create ticket
        ./create-ticket.sh "$INCIDENT_ID" "$DESCRIPTION" "$SEVERITY"
        ;;
esac

# Start automated recovery if applicable
case $INCIDENT_TYPE in
    "APPLICATION_DOWN")
        ./auto-restart-application.sh
        ;;
    "DATABASE_DOWN")
        ./auto-restart-database.sh
        ;;
    "DISK_SPACE_LOW")
        ./cleanup-logs.sh
        ;;
esac

echo "Incident response initiated for $INCIDENT_ID"
```

---

## Monitoring and Alerting

### Backup Monitoring

#### 1. Backup Success Monitoring

```bash
#!/bin/bash
# monitor-backups.sh - Backup monitoring script

# Check if backups completed successfully
BACKUP_LOG="/var/log/backup.log"
CURRENT_DATE=$(date +%Y%m%d)

# Check database backup
if ! grep -q "${CURRENT_DATE}.*Database backup completed" $BACKUP_LOG; then
    ./send-alert.sh "BACKUP_FAILED" "Database backup failed for $CURRENT_DATE"
fi

# Check application backup
if ! grep -q "${CURRENT_DATE}.*Application backup completed" $BACKUP_LOG; then
    ./send-alert.sh "BACKUP_FAILED" "Application backup failed for $CURRENT_DATE"
fi

# Check backup file sizes
DB_BACKUP_SIZE=$(find /var/backups/postgresql -name "*${CURRENT_DATE}*" -exec du -b {} \; | awk '{sum+=$1} END {print sum}')
if [ $DB_BACKUP_SIZE -lt 1000000 ]; then  # Less than 1MB
    ./send-alert.sh "BACKUP_SIZE_ANOMALY" "Database backup size unusually small: $DB_BACKUP_SIZE bytes"
fi
```

#### 2. Recovery Testing Alerts

```bash
#!/bin/bash
# monitor-recovery-tests.sh - Recovery testing monitoring

# Check if weekly DR test was performed
LAST_WEEK=$(date -d "7 days ago" +%Y%m%d)
DR_LOG="/var/log/dr-tests.log"

if ! grep -q "$LAST_WEEK.*DR drill completed" $DR_LOG; then
    ./send-alert.sh "DR_TEST_OVERDUE" "Weekly DR test not performed"
fi

# Check recovery time metrics
LATEST_RECOVERY_TIME=$(tail -1 /var/log/recovery-metrics.log | awk '{print $4}' | sed 's/s//')
if [ $LATEST_RECOVERY_TIME -gt 7200 ]; then  # More than 2 hours
    ./send-alert.sh "RECOVERY_TIME_HIGH" "Latest recovery time: ${LATEST_RECOVERY_TIME}s"
fi
```

This comprehensive disaster recovery and backup documentation ensures that the Learning Portal system can recover quickly from various failure scenarios while maintaining data integrity and minimizing business impact.