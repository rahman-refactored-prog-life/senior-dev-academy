#!/bin/bash

# Production Deployment Script
# This script automates the deployment of the Comprehensive Developer Portal to production

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Configuration
APP_NAME="comprehensive-dev-portal"
JAR_NAME="comprehensive-dev-portal-1.0.0.jar"
DEPLOY_DIR="/opt/${APP_NAME}"
LOG_DIR="/var/log/${APP_NAME}"
SERVICE_NAME="${APP_NAME}.service"
BACKUP_DIR="/opt/${APP_NAME}/backups"

# Print functions
print_header() {
    echo -e "${BLUE}========================================${NC}"
    echo -e "${BLUE}$1${NC}"
    echo -e "${BLUE}========================================${NC}"
}

print_success() {
    echo -e "${GREEN}✓${NC} $1"
}

print_error() {
    echo -e "${RED}✗${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}⚠${NC} $1"
}

print_info() {
    echo -e "${BLUE}ℹ${NC} $1"
}

# Check if running as root
check_root() {
    if [ "$EUID" -ne 0 ]; then
        print_error "This script must be run as root or with sudo"
        exit 1
    fi
}

# Pre-deployment checks
pre_deployment_checks() {
    print_header "Pre-Deployment Checks"
    
    # Check if JAR file exists
    if [ ! -f "target/${JAR_NAME}" ]; then
        print_error "JAR file not found: target/${JAR_NAME}"
        print_info "Run 'mvn clean package' first"
        exit 1
    fi
    print_success "JAR file found"
    
    # Check Java version
    if command -v java &> /dev/null; then
        local version=$(java -version 2>&1 | head -n 1 | awk -F '"' '{print $2}')
        print_success "Java is installed: $version"
    else
        print_error "Java is not installed"
        exit 1
    fi
    
    # Check PostgreSQL
    if command -v psql &> /dev/null; then
        print_success "PostgreSQL is installed"
    else
        print_warning "PostgreSQL client not found. Ensure database is accessible."
    fi
    
    # Check required environment variables
    local required_vars=("DB_PASSWORD" "SECURITY_USER_NAME" "SECURITY_USER_PASSWORD" "JWT_SECRET")
    local missing_vars=()
    
    for var in "${required_vars[@]}"; do
        if [ -z "${!var}" ]; then
            missing_vars+=("$var")
        fi
    done
    
    if [ ${#missing_vars[@]} -gt 0 ]; then
        print_error "Missing required environment variables:"
        for var in "${missing_vars[@]}"; do
            echo "  - $var"
        done
        print_info "Set these variables before deployment"
        exit 1
    fi
    print_success "All required environment variables are set"
    
    echo ""
}

# Create deployment directories
create_directories() {
    print_header "Creating Deployment Directories"
    
    mkdir -p "${DEPLOY_DIR}"
    mkdir -p "${LOG_DIR}"
    mkdir -p "${BACKUP_DIR}"
    mkdir -p "${DEPLOY_DIR}/config"
    
    print_success "Directories created"
    echo ""
}

# Backup existing deployment
backup_existing() {
    print_header "Backing Up Existing Deployment"
    
    if [ -f "${DEPLOY_DIR}/${JAR_NAME}" ]; then
        local timestamp=$(date +%Y%m%d_%H%M%S)
        local backup_file="${BACKUP_DIR}/${JAR_NAME}.${timestamp}"
        
        cp "${DEPLOY_DIR}/${JAR_NAME}" "${backup_file}"
        print_success "Backup created: ${backup_file}"
        
        # Keep only last 5 backups
        ls -t "${BACKUP_DIR}"/*.jar 2>/dev/null | tail -n +6 | xargs -r rm
        print_info "Old backups cleaned up (keeping last 5)"
    else
        print_info "No existing deployment to backup"
    fi
    
    echo ""
}

# Stop existing service
stop_service() {
    print_header "Stopping Existing Service"
    
    if systemctl is-active --quiet "${SERVICE_NAME}"; then
        systemctl stop "${SERVICE_NAME}"
        print_success "Service stopped"
    else
        print_info "Service is not running"
    fi
    
    echo ""
}

# Deploy application
deploy_application() {
    print_header "Deploying Application"
    
    # Copy JAR file
    cp "target/${JAR_NAME}" "${DEPLOY_DIR}/"
    print_success "JAR file deployed"
    
    # Set permissions
    chown -R root:root "${DEPLOY_DIR}"
    chmod 755 "${DEPLOY_DIR}"
    chmod 644 "${DEPLOY_DIR}/${JAR_NAME}"
    print_success "Permissions set"
    
    # Create application.properties for production
    cat > "${DEPLOY_DIR}/config/application.properties" << EOF
# Production Configuration
spring.profiles.active=production
server.port=\${SERVER_PORT:8080}

# Database Configuration
spring.datasource.url=\${DB_URL}
spring.datasource.username=\${DB_USERNAME}
spring.datasource.password=\${DB_PASSWORD}

# Security Configuration
spring.security.user.name=\${SECURITY_USER_NAME}
spring.security.user.password=\${SECURITY_USER_PASSWORD}

# JWT Configuration
app.security.jwt.secret=\${JWT_SECRET}

# Logging Configuration
logging.file.name=${LOG_DIR}/application.log
logging.level.root=INFO
logging.level.com.learningportal=INFO
EOF
    
    print_success "Configuration file created"
    echo ""
}

# Create systemd service
create_service() {
    print_header "Creating Systemd Service"
    
    cat > "/etc/systemd/system/${SERVICE_NAME}" << EOF
[Unit]
Description=Comprehensive Developer Portal
After=network.target postgresql.service

[Service]
Type=simple
User=root
WorkingDirectory=${DEPLOY_DIR}
ExecStart=/usr/bin/java -Xms512m -Xmx2048m -jar ${DEPLOY_DIR}/${JAR_NAME} --spring.config.location=${DEPLOY_DIR}/config/application.properties
Restart=on-failure
RestartSec=10
StandardOutput=journal
StandardError=journal
SyslogIdentifier=${APP_NAME}

# Environment variables
Environment="SPRING_PROFILES_ACTIVE=production"
Environment="DB_URL=${DB_URL}"
Environment="DB_USERNAME=${DB_USERNAME}"
Environment="DB_PASSWORD=${DB_PASSWORD}"
Environment="SECURITY_USER_NAME=${SECURITY_USER_NAME}"
Environment="SECURITY_USER_PASSWORD=${SECURITY_USER_PASSWORD}"
Environment="JWT_SECRET=${JWT_SECRET}"
Environment="SERVER_PORT=${SERVER_PORT:-8080}"

[Install]
WantedBy=multi-user.target
EOF
    
    # Reload systemd
    systemctl daemon-reload
    print_success "Systemd service created"
    
    # Enable service
    systemctl enable "${SERVICE_NAME}"
    print_success "Service enabled for auto-start"
    
    echo ""
}

# Start service
start_service() {
    print_header "Starting Service"
    
    systemctl start "${SERVICE_NAME}"
    sleep 5
    
    if systemctl is-active --quiet "${SERVICE_NAME}"; then
        print_success "Service started successfully"
    else
        print_error "Service failed to start"
        print_info "Check logs: journalctl -u ${SERVICE_NAME} -n 50"
        exit 1
    fi
    
    echo ""
}

# Health check
health_check() {
    print_header "Health Check"
    
    local port="${SERVER_PORT:-8080}"
    local max_attempts=30
    local attempt=1
    
    print_info "Waiting for application to be ready..."
    
    while [ $attempt -le $max_attempts ]; do
        if curl -s "http://localhost:${port}/api/actuator/health" | grep -q "UP"; then
            print_success "Application is healthy"
            return 0
        fi
        
        echo -n "."
        sleep 2
        ((attempt++))
    done
    
    echo ""
    print_error "Application health check failed"
    print_info "Check logs: journalctl -u ${SERVICE_NAME} -n 50"
    exit 1
}

# Post-deployment tasks
post_deployment() {
    print_header "Post-Deployment Tasks"
    
    # Set log rotation
    cat > "/etc/logrotate.d/${APP_NAME}" << EOF
${LOG_DIR}/*.log {
    daily
    rotate 30
    compress
    delaycompress
    missingok
    notifempty
    create 0644 root root
}
EOF
    print_success "Log rotation configured"
    
    # Display service status
    systemctl status "${SERVICE_NAME}" --no-pager
    
    echo ""
}

# Display deployment summary
deployment_summary() {
    print_header "Deployment Summary"
    
    local port="${SERVER_PORT:-8080}"
    
    echo -e "${GREEN}Deployment completed successfully!${NC}"
    echo ""
    echo "Application Details:"
    echo "  - Name: ${APP_NAME}"
    echo "  - Version: 1.0.0"
    echo "  - Port: ${port}"
    echo "  - Deploy Directory: ${DEPLOY_DIR}"
    echo "  - Log Directory: ${LOG_DIR}"
    echo ""
    echo "Service Management:"
    echo "  - Start: sudo systemctl start ${SERVICE_NAME}"
    echo "  - Stop: sudo systemctl stop ${SERVICE_NAME}"
    echo "  - Restart: sudo systemctl restart ${SERVICE_NAME}"
    echo "  - Status: sudo systemctl status ${SERVICE_NAME}"
    echo "  - Logs: sudo journalctl -u ${SERVICE_NAME} -f"
    echo ""
    echo "Application URLs:"
    echo "  - Health: http://localhost:${port}/api/actuator/health"
    echo "  - Metrics: http://localhost:${port}/api/actuator/metrics"
    echo "  - Info: http://localhost:${port}/api/actuator/info"
    echo ""
    echo "Next Steps:"
    echo "  1. Configure reverse proxy (nginx/Apache)"
    echo "  2. Set up SSL/TLS certificates"
    echo "  3. Configure firewall rules"
    echo "  4. Set up monitoring and alerting"
    echo "  5. Configure backup procedures"
    echo ""
}

# Rollback function
rollback() {
    print_header "Rolling Back Deployment"
    
    # Stop service
    systemctl stop "${SERVICE_NAME}"
    
    # Find latest backup
    local latest_backup=$(ls -t "${BACKUP_DIR}"/*.jar 2>/dev/null | head -n 1)
    
    if [ -n "$latest_backup" ]; then
        cp "$latest_backup" "${DEPLOY_DIR}/${JAR_NAME}"
        print_success "Rolled back to: $latest_backup"
        
        # Start service
        systemctl start "${SERVICE_NAME}"
        print_success "Service restarted"
    else
        print_error "No backup found for rollback"
        exit 1
    fi
}

# Main deployment flow
main() {
    print_header "Production Deployment - ${APP_NAME}"
    echo ""
    
    # Check if rollback requested
    if [ "$1" == "rollback" ]; then
        rollback
        exit 0
    fi
    
    # Run deployment steps
    check_root
    pre_deployment_checks
    create_directories
    backup_existing
    stop_service
    deploy_application
    create_service
    start_service
    health_check
    post_deployment
    deployment_summary
}

# Run main function
main "$@"
