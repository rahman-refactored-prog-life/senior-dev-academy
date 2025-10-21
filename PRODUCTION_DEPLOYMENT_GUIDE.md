# Production Deployment Guide

## Overview

This guide provides comprehensive instructions for deploying the Comprehensive Developer Portal to a production environment. It covers server setup, security configuration, deployment automation, and monitoring.

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Server Setup](#server-setup)
3. [Security Configuration](#security-configuration)
4. [Database Setup](#database-setup)
5. [Application Deployment](#application-deployment)
6. [Reverse Proxy Configuration](#reverse-proxy-configuration)
7. [SSL/TLS Configuration](#ssltls-configuration)
8. [Monitoring and Alerting](#monitoring-and-alerting)
9. [Backup and Recovery](#backup-and-recovery)
10. [Troubleshooting](#troubleshooting)

---

## Prerequisites

### Server Requirements

**Minimum Specifications:**
- **CPU**: 2 cores
- **RAM**: 4 GB
- **Disk**: 20 GB SSD
- **OS**: Ubuntu 20.04 LTS or later, CentOS 8+, or similar

**Recommended Specifications:**
- **CPU**: 4+ cores
- **RAM**: 8+ GB
- **Disk**: 50+ GB SSD
- **OS**: Ubuntu 22.04 LTS

### Required Software

- Java 21 (OpenJDK)
- PostgreSQL 15+
- Redis 7+ (for caching)
- Nginx or Apache (reverse proxy)
- Certbot (for SSL certificates)
- systemd (service management)

### Required Access

- Root or sudo access
- SSH access to server
- Domain name (for SSL)
- Database credentials
- Firewall configuration access

---

## Server Setup

### 1. Update System

```bash
# Update package lists
sudo apt update
sudo apt upgrade -y

# Install essential tools
sudo apt install -y curl wget git vim htop
```

### 2. Install Java

```bash
# Install OpenJDK 21
sudo apt install -y openjdk-21-jdk

# Verify installation
java -version

# Set JAVA_HOME
echo 'export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64' | sudo tee -a /etc/environment
source /etc/environment
```

### 3. Install PostgreSQL

```bash
# Install PostgreSQL
sudo apt install -y postgresql postgresql-contrib

# Start and enable service
sudo systemctl start postgresql
sudo systemctl enable postgresql

# Verify installation
sudo -u postgres psql --version
```

### 4. Install Redis

```bash
# Install Redis
sudo apt install -y redis-server

# Configure Redis for production
sudo nano /etc/redis/redis.conf
# Set: maxmemory 256mb
# Set: maxmemory-policy allkeys-lru
# Set: requirepass YOUR_REDIS_PASSWORD

# Restart Redis
sudo systemctl restart redis-server
sudo systemctl enable redis-server

# Verify installation
redis-cli ping
```

### 5. Install Nginx

```bash
# Install Nginx
sudo apt install -y nginx

# Start and enable service
sudo systemctl start nginx
sudo systemctl enable nginx

# Verify installation
nginx -v
```

---

## Security Configuration

### 1. Firewall Setup

```bash
# Install UFW (if not installed)
sudo apt install -y ufw

# Allow SSH
sudo ufw allow 22/tcp

# Allow HTTP and HTTPS
sudo ufw allow 80/tcp
sudo ufw allow 443/tcp

# Allow PostgreSQL (only from localhost)
sudo ufw allow from 127.0.0.1 to any port 5432

# Enable firewall
sudo ufw enable

# Check status
sudo ufw status
```

### 2. Create Application User

```bash
# Create dedicated user (optional but recommended)
sudo useradd -r -s /bin/false appuser

# Create application directory
sudo mkdir -p /opt/comprehensive-dev-portal
sudo chown appuser:appuser /opt/comprehensive-dev-portal
```

### 3. Secure PostgreSQL

```bash
# Set strong password for postgres user
sudo -u postgres psql
postgres=# ALTER USER postgres PASSWORD 'STRONG_PASSWORD_HERE';
postgres=# \q

# Configure authentication
sudo nano /etc/postgresql/15/main/pg_hba.conf
# Change: local all postgres peer
# To: local all postgres md5

# Restart PostgreSQL
sudo systemctl restart postgresql
```

### 4. Generate Strong Secrets

```bash
# Generate JWT secret (32+ characters)
openssl rand -base64 32

# Generate secure passwords
openssl rand -base64 24
```

---

## Database Setup

### 1. Create Production Database

```bash
# Connect to PostgreSQL
sudo -u postgres psql

# Create database
postgres=# CREATE DATABASE devacademykiro;

# Create application user
postgres=# CREATE USER appuser WITH ENCRYPTED PASSWORD 'STRONG_PASSWORD';

# Grant privileges
postgres=# GRANT ALL PRIVILEGES ON DATABASE devacademykiro TO appuser;

# Exit
postgres=# \q
```

### 2. Configure Database Connection

```bash
# Test connection
psql -h localhost -U appuser -d devacademykiro

# If successful, note the connection details for deployment
```

### 3. Database Optimization

```bash
# Edit PostgreSQL configuration
sudo nano /etc/postgresql/15/main/postgresql.conf

# Recommended settings for production:
shared_buffers = 256MB
effective_cache_size = 1GB
maintenance_work_mem = 64MB
checkpoint_completion_target = 0.9
wal_buffers = 16MB
default_statistics_target = 100
random_page_cost = 1.1
effective_io_concurrency = 200
work_mem = 4MB
min_wal_size = 1GB
max_wal_size = 4GB
max_connections = 100

# Restart PostgreSQL
sudo systemctl restart postgresql
```

---

## Application Deployment

### 1. Build Application

```bash
# On your development machine
mvn clean package -DskipTests

# Verify JAR file
ls -lh target/comprehensive-dev-portal-1.0.0.jar
```

### 2. Transfer to Server

```bash
# Copy JAR file to server
scp target/comprehensive-dev-portal-1.0.0.jar user@server:/tmp/

# Copy deployment script
scp deploy-production.sh user@server:/tmp/
```

### 3. Set Environment Variables

```bash
# On the server, create environment file
sudo nano /opt/comprehensive-dev-portal/.env

# Add production configuration:
SPRING_PROFILES_ACTIVE=production
SERVER_PORT=8080

# Database Configuration
DB_URL=jdbc:postgresql://localhost:5432/devacademykiro
DB_USERNAME=appuser
DB_PASSWORD=YOUR_DB_PASSWORD

# Security Configuration
SECURITY_USER_NAME=admin_prod
SECURITY_USER_PASSWORD=YOUR_ADMIN_PASSWORD
JWT_SECRET=YOUR_JWT_SECRET

# Redis Configuration
CACHE_TYPE=redis
REDIS_HOST=localhost
REDIS_PORT=6379
REDIS_PASSWORD=YOUR_REDIS_PASSWORD

# Logging Configuration
LOGGING_LEVEL_ROOT=INFO
LOGGING_LEVEL_APP=INFO
LOGGING_FILE=/var/log/comprehensive-dev-portal/application.log

# Performance Configuration
DB_POOL_MAX_SIZE=50
DB_POOL_MIN_IDLE=10
PERFORMANCE_MONITORING_ENABLED=true
```

### 4. Run Deployment Script

```bash
# Make script executable
chmod +x /tmp/deploy-production.sh

# Load environment variables
source /opt/comprehensive-dev-portal/.env

# Run deployment
sudo -E /tmp/deploy-production.sh
```

### 5. Verify Deployment

```bash
# Check service status
sudo systemctl status comprehensive-dev-portal

# Check application health
curl http://localhost:8080/api/actuator/health

# Check logs
sudo journalctl -u comprehensive-dev-portal -n 50
```

---

## Reverse Proxy Configuration

### Nginx Configuration

```bash
# Create Nginx configuration
sudo nano /etc/nginx/sites-available/comprehensive-dev-portal

# Add configuration:
server {
    listen 80;
    server_name your-domain.com www.your-domain.com;

    # Redirect HTTP to HTTPS
    return 301 https://$server_name$request_uri;
}

server {
    listen 443 ssl http2;
    server_name your-domain.com www.your-domain.com;

    # SSL Configuration (will be added by Certbot)
    # ssl_certificate /etc/letsencrypt/live/your-domain.com/fullchain.pem;
    # ssl_certificate_key /etc/letsencrypt/live/your-domain.com/privkey.pem;

    # Security headers
    add_header X-Frame-Options "SAMEORIGIN" always;
    add_header X-Content-Type-Options "nosniff" always;
    add_header X-XSS-Protection "1; mode=block" always;
    add_header Referrer-Policy "no-referrer-when-downgrade" always;
    add_header Content-Security-Policy "default-src 'self' http: https: data: blob: 'unsafe-inline'" always;

    # Logging
    access_log /var/log/nginx/comprehensive-dev-portal-access.log;
    error_log /var/log/nginx/comprehensive-dev-portal-error.log;

    # Proxy settings
    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        
        # WebSocket support
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
        
        # Timeouts
        proxy_connect_timeout 60s;
        proxy_send_timeout 60s;
        proxy_read_timeout 60s;
    }

    # Static files caching
    location ~* \.(jpg|jpeg|png|gif|ico|css|js|svg|woff|woff2|ttf|eot)$ {
        proxy_pass http://localhost:8080;
        expires 1y;
        add_header Cache-Control "public, immutable";
    }

    # Health check endpoint
    location /api/actuator/health {
        proxy_pass http://localhost:8080/api/actuator/health;
        access_log off;
    }
}

# Enable site
sudo ln -s /etc/nginx/sites-available/comprehensive-dev-portal /etc/nginx/sites-enabled/

# Test configuration
sudo nginx -t

# Reload Nginx
sudo systemctl reload nginx
```

---

## SSL/TLS Configuration

### Using Let's Encrypt (Certbot)

```bash
# Install Certbot
sudo apt install -y certbot python3-certbot-nginx

# Obtain SSL certificate
sudo certbot --nginx -d your-domain.com -d www.your-domain.com

# Follow prompts:
# - Enter email address
# - Agree to terms
# - Choose to redirect HTTP to HTTPS

# Verify auto-renewal
sudo certbot renew --dry-run

# Certificate will auto-renew via cron/systemd timer
```

### Manual SSL Configuration

```bash
# If using custom certificates
sudo mkdir -p /etc/ssl/private
sudo mkdir -p /etc/ssl/certs

# Copy certificates
sudo cp your-cert.crt /etc/ssl/certs/
sudo cp your-key.key /etc/ssl/private/

# Set permissions
sudo chmod 600 /etc/ssl/private/your-key.key

# Update Nginx configuration with certificate paths
```

---

## Monitoring and Alerting

### 1. Application Monitoring

```bash
# Access Prometheus metrics
curl http://localhost:8080/api/actuator/prometheus

# Access application metrics
curl http://localhost:8080/api/actuator/metrics
```

### 2. Log Monitoring

```bash
# View application logs
sudo journalctl -u comprehensive-dev-portal -f

# View Nginx logs
sudo tail -f /var/log/nginx/comprehensive-dev-portal-access.log
sudo tail -f /var/log/nginx/comprehensive-dev-portal-error.log

# View application log file
sudo tail -f /var/log/comprehensive-dev-portal/application.log
```

### 3. System Monitoring

```bash
# Install monitoring tools
sudo apt install -y htop iotop nethogs

# Monitor system resources
htop

# Monitor disk I/O
sudo iotop

# Monitor network
sudo nethogs
```

### 4. Health Check Script

```bash
# Create health check script
sudo nano /usr/local/bin/health-check.sh

#!/bin/bash
HEALTH_URL="http://localhost:8080/api/actuator/health"
RESPONSE=$(curl -s $HEALTH_URL)

if echo "$RESPONSE" | grep -q "UP"; then
    echo "Application is healthy"
    exit 0
else
    echo "Application is unhealthy"
    # Send alert (email, Slack, etc.)
    exit 1
fi

# Make executable
sudo chmod +x /usr/local/bin/health-check.sh

# Add to cron (check every 5 minutes)
sudo crontab -e
*/5 * * * * /usr/local/bin/health-check.sh
```

---

## Backup and Recovery

### 1. Database Backup

```bash
# Create backup script
sudo nano /usr/local/bin/backup-database.sh

#!/bin/bash
BACKUP_DIR="/opt/backups/database"
DATE=$(date +%Y%m%d_%H%M%S)
DB_NAME="devacademykiro"
DB_USER="appuser"

mkdir -p $BACKUP_DIR

# Create backup
pg_dump -U $DB_USER -h localhost $DB_NAME | gzip > $BACKUP_DIR/${DB_NAME}_${DATE}.sql.gz

# Keep only last 30 days
find $BACKUP_DIR -name "*.sql.gz" -mtime +30 -delete

echo "Backup completed: ${DB_NAME}_${DATE}.sql.gz"

# Make executable
sudo chmod +x /usr/local/bin/backup-database.sh

# Schedule daily backups at 2 AM
sudo crontab -e
0 2 * * * /usr/local/bin/backup-database.sh
```

### 2. Application Backup

```bash
# Backup application files
sudo tar -czf /opt/backups/app_$(date +%Y%m%d).tar.gz \
    /opt/comprehensive-dev-portal \
    /etc/nginx/sites-available/comprehensive-dev-portal \
    /etc/systemd/system/comprehensive-dev-portal.service
```

### 3. Database Restore

```bash
# Restore from backup
gunzip -c /opt/backups/database/devacademykiro_20240101_020000.sql.gz | \
    psql -U appuser -h localhost devacademykiro
```

---

## Troubleshooting

### Application Won't Start

```bash
# Check service status
sudo systemctl status comprehensive-dev-portal

# Check logs
sudo journalctl -u comprehensive-dev-portal -n 100

# Check if port is available
sudo lsof -i :8080

# Verify environment variables
sudo systemctl show comprehensive-dev-portal --property=Environment
```

### Database Connection Issues

```bash
# Test database connection
psql -h localhost -U appuser -d devacademykiro

# Check PostgreSQL logs
sudo tail -f /var/log/postgresql/postgresql-15-main.log

# Verify PostgreSQL is running
sudo systemctl status postgresql
```

### High Memory Usage

```bash
# Check Java heap usage
jstat -gc $(pgrep -f comprehensive-dev-portal)

# Adjust JVM memory in service file
sudo nano /etc/systemd/system/comprehensive-dev-portal.service
# Modify: -Xms512m -Xmx2048m

# Reload and restart
sudo systemctl daemon-reload
sudo systemctl restart comprehensive-dev-portal
```

### SSL Certificate Issues

```bash
# Check certificate expiration
sudo certbot certificates

# Renew certificate manually
sudo certbot renew

# Test Nginx configuration
sudo nginx -t
```

---

## Maintenance Tasks

### Regular Maintenance

**Daily:**
- Monitor application logs
- Check system resources
- Verify backups completed

**Weekly:**
- Review error logs
- Check disk space
- Update security patches

**Monthly:**
- Review performance metrics
- Optimize database
- Update dependencies

### Update Application

```bash
# Build new version
mvn clean package

# Transfer to server
scp target/comprehensive-dev-portal-1.0.0.jar user@server:/tmp/

# Deploy update
sudo -E /tmp/deploy-production.sh

# Verify deployment
curl http://localhost:8080/api/actuator/health
```

### Rollback

```bash
# Rollback to previous version
sudo /opt/comprehensive-dev-portal/deploy-production.sh rollback

# Verify rollback
sudo systemctl status comprehensive-dev-portal
```

---

## Security Best Practices

1. **Keep Software Updated**: Regularly update OS, Java, PostgreSQL, and dependencies
2. **Use Strong Passwords**: Minimum 16 characters with mixed case, numbers, and symbols
3. **Enable Firewall**: Only allow necessary ports
4. **Use SSL/TLS**: Always use HTTPS in production
5. **Regular Backups**: Automate daily database and application backups
6. **Monitor Logs**: Set up log monitoring and alerting
7. **Limit Access**: Use SSH keys, disable root login, use sudo
8. **Security Headers**: Configure proper security headers in Nginx
9. **Rate Limiting**: Implement rate limiting to prevent abuse
10. **Regular Audits**: Perform security audits and penetration testing

---

## Support

For deployment issues:
1. Check application logs: `sudo journalctl -u comprehensive-dev-portal -n 100`
2. Review this guide and troubleshooting section
3. Check [TROUBLESHOOTING_GUIDE.md](TROUBLESHOOTING_GUIDE.md)
4. Contact development team with:
   - Error messages
   - Log excerpts
   - System information
   - Steps to reproduce

---

**Last Updated**: 2024
**Version**: 1.0.0
