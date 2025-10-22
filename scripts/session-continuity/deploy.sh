#!/bin/bash

# Session Continuity System - Automated Deployment Script
# This script automates the deployment of the session continuity system
# across different environments (development, staging, production)

set -euo pipefail

# Configuration
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"
LOG_FILE="$PROJECT_ROOT/logs/deployment-$(date +%Y%m%d_%H%M%S).log"
CONFIG_DIR="$PROJECT_ROOT/config/session-continuity"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Logging function
log() {
    local level=$1
    shift
    local message="$*"
    local timestamp=$(date '+%Y-%m-%d %H:%M:%S')
    
    echo -e "${timestamp} [${level}] ${message}" | tee -a "$LOG_FILE"
    
    case $level in
        "ERROR")   echo -e "${RED}[ERROR]${NC} $message" ;;
        "SUCCESS") echo -e "${GREEN}[SUCCESS]${NC} $message" ;;
        "WARNING") echo -e "${YELLOW}[WARNING]${NC} $message" ;;
        "INFO")    echo -e "${BLUE}[INFO]${NC} $message" ;;
    esac
}

# Error handling
error_exit() {
    log "ERROR" "$1"
    exit 1
}

# Success message
success_message() {
    log "SUCCESS" "$1"
}

# Warning message
warning_message() {
    log "WARNING" "$1"
}

# Info message
info_message() {
    log "INFO" "$1"
}

# Usage information
usage() {
    cat << EOF
Usage: $0 [OPTIONS] ENVIRONMENT

Deploy Session Continuity System to specified environment

ENVIRONMENTS:
    dev         Development environment
    staging     Staging environment  
    prod        Production environment

OPTIONS:
    -h, --help              Show this help message
    -v, --verbose           Enable verbose output
    -d, --dry-run          Show what would be deployed without executing
    -f, --force            Force deployment even if validation fails
    -b, --backup           Create backup before deployment
    -r, --rollback VERSION Rollback to specified version
    --skip-tests           Skip test execution during deployment
    --config-only          Deploy only configuration changes

EXAMPLES:
    $0 dev                  Deploy to development environment
    $0 prod --backup        Deploy to production with backup
    $0 staging --dry-run    Show staging deployment plan
    $0 --rollback v1.2.3    Rollback to version 1.2.3

EOF
}

# Parse command line arguments
parse_arguments() {
    ENVIRONMENT=""
    VERBOSE=false
    DRY_RUN=false
    FORCE=false
    BACKUP=false
    ROLLBACK_VERSION=""
    SKIP_TESTS=false
    CONFIG_ONLY=false
    
    while [[ $# -gt 0 ]]; do
        case $1 in
            -h|--help)
                usage
                exit 0
                ;;
            -v|--verbose)
                VERBOSE=true
                shift
                ;;
            -d|--dry-run)
                DRY_RUN=true
                shift
                ;;
            -f|--force)
                FORCE=true
                shift
                ;;
            -b|--backup)
                BACKUP=true
                shift
                ;;
            -r|--rollback)
                ROLLBACK_VERSION="$2"
                shift 2
                ;;
            --skip-tests)
                SKIP_TESTS=true
                shift
                ;;
            --config-only)
                CONFIG_ONLY=true
                shift
                ;;
            dev|staging|prod)
                ENVIRONMENT="$1"
                shift
                ;;
            *)
                error_exit "Unknown option: $1"
                ;;
        esac
    done
    
    if [[ -z "$ENVIRONMENT" && -z "$ROLLBACK_VERSION" ]]; then
        error_exit "Environment must be specified. Use --help for usage information."
    fi
}

# Validate environment
validate_environment() {
    info_message "Validating deployment environment: $ENVIRONMENT"
    
    # Check if environment configuration exists
    local env_config="$CONFIG_DIR/$ENVIRONMENT.yml"
    if [[ ! -f "$env_config" ]]; then
        error_exit "Environment configuration not found: $env_config"
    fi
    
    # Validate Java version
    if ! command -v java &> /dev/null; then
        error_exit "Java is not installed or not in PATH"
    fi
    
    local java_version=$(java -version 2>&1 | head -n1 | cut -d'"' -f2)
    info_message "Java version: $java_version"
    
    # Validate Maven
    if ! command -v mvn &> /dev/null; then
        error_exit "Maven is not installed or not in PATH"
    fi
    
    local maven_version=$(mvn -version | head -n1)
    info_message "Maven version: $maven_version"
    
    # Check database connectivity (environment-specific)
    case $ENVIRONMENT in
        "dev")
            validate_h2_database
            ;;
        "staging"|"prod")
            validate_postgresql_database
            ;;
    esac
    
    success_message "Environment validation completed"
}

# Validate H2 database (development)
validate_h2_database() {
    info_message "Validating H2 database configuration"
    # H2 is embedded, no external validation needed
    success_message "H2 database validation completed"
}

# Validate PostgreSQL database (staging/production)
validate_postgresql_database() {
    info_message "Validating PostgreSQL database connectivity"
    
    local db_host=$(grep "host:" "$CONFIG_DIR/$ENVIRONMENT.yml" | cut -d':' -f2 | xargs)
    local db_port=$(grep "port:" "$CONFIG_DIR/$ENVIRONMENT.yml" | cut -d':' -f2 | xargs)
    local db_name=$(grep "database:" "$CONFIG_DIR/$ENVIRONMENT.yml" | cut -d':' -f2 | xargs)
    
    if command -v pg_isready &> /dev/null; then
        if pg_isready -h "$db_host" -p "$db_port" -d "$db_name" &> /dev/null; then
            success_message "PostgreSQL database is accessible"
        else
            error_exit "Cannot connect to PostgreSQL database: $db_host:$db_port/$db_name"
        fi
    else
        warning_message "pg_isready not available, skipping database connectivity check"
    fi
}

# Create backup
create_backup() {
    if [[ "$BACKUP" == true ]]; then
        info_message "Creating backup before deployment"
        
        local backup_dir="$PROJECT_ROOT/backups/$(date +%Y%m%d_%H%M%S)"
        mkdir -p "$backup_dir"
        
        # Backup configuration files
        cp -r "$CONFIG_DIR" "$backup_dir/"
        
        # Backup documentation files
        cp *.md "$backup_dir/" 2>/dev/null || true
        cp -r docs/ "$backup_dir/" 2>/dev/null || true
        
        # Backup database (if applicable)
        case $ENVIRONMENT in
            "staging"|"prod")
                backup_postgresql_database "$backup_dir"
                ;;
        esac
        
        # Create backup manifest
        cat > "$backup_dir/manifest.txt" << EOF
Backup created: $(date)
Environment: $ENVIRONMENT
Git commit: $(git rev-parse HEAD)
Git branch: $(git rev-parse --abbrev-ref HEAD)
EOF
        
        success_message "Backup created: $backup_dir"
        echo "$backup_dir" > "$PROJECT_ROOT/.last-backup"
    fi
}

# Backup PostgreSQL database
backup_postgresql_database() {
    local backup_dir="$1"
    info_message "Creating PostgreSQL database backup"
    
    local db_host=$(grep "host:" "$CONFIG_DIR/$ENVIRONMENT.yml" | cut -d':' -f2 | xargs)
    local db_port=$(grep "port:" "$CONFIG_DIR/$ENVIRONMENT.yml" | cut -d':' -f2 | xargs)
    local db_name=$(grep "database:" "$CONFIG_DIR/$ENVIRONMENT.yml" | cut -d':' -f2 | xargs)
    local db_user=$(grep "username:" "$CONFIG_DIR/$ENVIRONMENT.yml" | cut -d':' -f2 | xargs)
    
    if command -v pg_dump &> /dev/null; then
        pg_dump -h "$db_host" -p "$db_port" -U "$db_user" -d "$db_name" \
                -f "$backup_dir/database_backup.sql" \
                --verbose --no-password
        success_message "Database backup completed"
    else
        warning_message "pg_dump not available, skipping database backup"
    fi
}

# Build application
build_application() {
    if [[ "$CONFIG_ONLY" == true ]]; then
        info_message "Skipping application build (config-only deployment)"
        return 0
    fi
    
    info_message "Building application for $ENVIRONMENT environment"
    
    cd "$PROJECT_ROOT"
    
    # Clean previous builds
    mvn clean
    
    # Run tests (unless skipped)
    if [[ "$SKIP_TESTS" == false ]]; then
        info_message "Running tests"
        mvn test -Dspring.profiles.active="$ENVIRONMENT"
    else
        warning_message "Skipping tests as requested"
    fi
    
    # Build application
    mvn package -DskipTests -Dspring.profiles.active="$ENVIRONMENT"
    
    # Verify build artifacts
    local jar_file="target/comprehensive-dev-portal-*.jar"
    if ls $jar_file 1> /dev/null 2>&1; then
        success_message "Application build completed successfully"
    else
        error_exit "Build failed - JAR file not found"
    fi
}

# Deploy configuration
deploy_configuration() {
    info_message "Deploying configuration for $ENVIRONMENT environment"
    
    local env_config="$CONFIG_DIR/$ENVIRONMENT.yml"
    local target_config="$PROJECT_ROOT/src/main/resources/application-$ENVIRONMENT.yml"
    
    # Copy environment-specific configuration
    cp "$env_config" "$target_config"
    
    # Deploy session continuity specific configuration
    deploy_session_continuity_config
    
    success_message "Configuration deployment completed"
}

# Deploy session continuity specific configuration
deploy_session_continuity_config() {
    info_message "Deploying session continuity configuration"
    
    # Create session continuity directories
    mkdir -p "$PROJECT_ROOT/data/session-continuity"
    mkdir -p "$PROJECT_ROOT/logs/session-continuity"
    mkdir -p "$PROJECT_ROOT/backups/session-continuity"
    
    # Set appropriate permissions
    chmod 755 "$PROJECT_ROOT/data/session-continuity"
    chmod 755 "$PROJECT_ROOT/logs/session-continuity"
    chmod 755 "$PROJECT_ROOT/backups/session-continuity"
    
    # Deploy environment-specific session continuity settings
    case $ENVIRONMENT in
        "dev")
            deploy_dev_session_config
            ;;
        "staging")
            deploy_staging_session_config
            ;;
        "prod")
            deploy_prod_session_config
            ;;
    esac
}

# Deploy development session continuity configuration
deploy_dev_session_config() {
    cat > "$PROJECT_ROOT/src/main/resources/session-continuity-dev.yml" << EOF
session-continuity:
  enabled: true
  capture:
    auto-capture: true
    capture-interval: 30s
    timeout: 5s
  documentation:
    sync-enabled: true
    sync-interval: 60s
    validation-enabled: true
  quality-gates:
    compilation:
      enabled: true
      block-on-failure: false  # Allow development with warnings
    documentation:
      enabled: true
      required-files: 11
    progress:
      enabled: true
      accuracy-threshold: 0.9
  recovery:
    auto-recovery: true
    confidence-threshold: 0.8
  performance:
    capture-parallel: true
    sync-async: true
    cache-enabled: true
EOF
}

# Deploy staging session continuity configuration
deploy_staging_session_config() {
    cat > "$PROJECT_ROOT/src/main/resources/session-continuity-staging.yml" << EOF
session-continuity:
  enabled: true
  capture:
    auto-capture: true
    capture-interval: 60s
    timeout: 10s
  documentation:
    sync-enabled: true
    sync-interval: 120s
    validation-enabled: true
  quality-gates:
    compilation:
      enabled: true
      block-on-failure: true
    documentation:
      enabled: true
      required-files: 11
    progress:
      enabled: true
      accuracy-threshold: 0.95
  recovery:
    auto-recovery: true
    confidence-threshold: 0.9
  performance:
    capture-parallel: true
    sync-async: true
    cache-enabled: true
    monitoring-enabled: true
EOF
}

# Deploy production session continuity configuration
deploy_prod_session_config() {
    cat > "$PROJECT_ROOT/src/main/resources/session-continuity-prod.yml" << EOF
session-continuity:
  enabled: true
  capture:
    auto-capture: true
    capture-interval: 300s  # 5 minutes
    timeout: 15s
  documentation:
    sync-enabled: true
    sync-interval: 600s     # 10 minutes
    validation-enabled: true
  quality-gates:
    compilation:
      enabled: true
      block-on-failure: true
    documentation:
      enabled: true
      required-files: 11
    progress:
      enabled: true
      accuracy-threshold: 0.98
  recovery:
    auto-recovery: true
    confidence-threshold: 0.95
  performance:
    capture-parallel: true
    sync-async: true
    cache-enabled: true
    monitoring-enabled: true
    alerting-enabled: true
  security:
    encryption-enabled: true
    audit-logging: true
EOF
}

# Deploy application
deploy_application() {
    if [[ "$CONFIG_ONLY" == true ]]; then
        info_message "Skipping application deployment (config-only deployment)"
        return 0
    fi
    
    info_message "Deploying application to $ENVIRONMENT environment"
    
    case $ENVIRONMENT in
        "dev")
            deploy_development
            ;;
        "staging")
            deploy_staging
            ;;
        "prod")
            deploy_production
            ;;
    esac
}

# Deploy to development environment
deploy_development() {
    info_message "Deploying to development environment"
    
    # Stop existing application (if running)
    pkill -f "comprehensive-dev-portal" || true
    
    # Start application with development profile
    cd "$PROJECT_ROOT"
    nohup java -jar target/comprehensive-dev-portal-*.jar \
        --spring.profiles.active=dev \
        --server.port=8080 \
        > logs/application-dev.log 2>&1 &
    
    # Wait for application to start
    wait_for_application_startup "http://localhost:8080"
    
    success_message "Development deployment completed"
}

# Deploy to staging environment
deploy_staging() {
    info_message "Deploying to staging environment"
    
    # Deploy using systemd service (if available)
    if systemctl is-active --quiet session-continuity-staging; then
        systemctl stop session-continuity-staging
    fi
    
    # Copy JAR to staging location
    cp target/comprehensive-dev-portal-*.jar /opt/session-continuity/staging/
    
    # Start staging service
    systemctl start session-continuity-staging
    systemctl enable session-continuity-staging
    
    # Wait for application to start
    wait_for_application_startup "http://staging.session-continuity.local:8080"
    
    success_message "Staging deployment completed"
}

# Deploy to production environment
deploy_production() {
    info_message "Deploying to production environment"
    
    # Production deployment with blue-green strategy
    deploy_blue_green_production
    
    success_message "Production deployment completed"
}

# Blue-green deployment for production
deploy_blue_green_production() {
    info_message "Executing blue-green deployment"
    
    # Determine current and target environments
    local current_env=$(get_current_production_environment)
    local target_env=$(get_target_production_environment "$current_env")
    
    info_message "Current environment: $current_env, Target environment: $target_env"
    
    # Deploy to target environment
    deploy_to_environment "$target_env"
    
    # Health check on target environment
    if health_check_environment "$target_env"; then
        # Switch traffic to target environment
        switch_production_traffic "$target_env"
        
        # Stop old environment
        stop_environment "$current_env"
        
        success_message "Blue-green deployment completed successfully"
    else
        error_exit "Health check failed on target environment: $target_env"
    fi
}

# Get current production environment
get_current_production_environment() {
    # Logic to determine current production environment (blue or green)
    if systemctl is-active --quiet session-continuity-blue; then
        echo "blue"
    else
        echo "green"
    fi
}

# Get target production environment
get_target_production_environment() {
    local current="$1"
    if [[ "$current" == "blue" ]]; then
        echo "green"
    else
        echo "blue"
    fi
}

# Deploy to specific environment
deploy_to_environment() {
    local env="$1"
    info_message "Deploying to $env environment"
    
    # Copy JAR to environment-specific location
    cp target/comprehensive-dev-portal-*.jar "/opt/session-continuity/$env/"
    
    # Start environment-specific service
    systemctl start "session-continuity-$env"
    
    # Wait for startup
    local port=$(get_environment_port "$env")
    wait_for_application_startup "http://localhost:$port"
}

# Get port for environment
get_environment_port() {
    local env="$1"
    case $env in
        "blue")  echo "8080" ;;
        "green") echo "8081" ;;
        *)       echo "8080" ;;
    esac
}

# Health check for environment
health_check_environment() {
    local env="$1"
    local port=$(get_environment_port "$env")
    local health_url="http://localhost:$port/actuator/health"
    
    info_message "Performing health check on $env environment"
    
    local max_attempts=30
    local attempt=1
    
    while [[ $attempt -le $max_attempts ]]; do
        if curl -f -s "$health_url" > /dev/null; then
            success_message "Health check passed for $env environment"
            return 0
        fi
        
        info_message "Health check attempt $attempt/$max_attempts failed, retrying..."
        sleep 10
        ((attempt++))
    done
    
    error_exit "Health check failed for $env environment after $max_attempts attempts"
}

# Switch production traffic
switch_production_traffic() {
    local target_env="$1"
    info_message "Switching production traffic to $target_env environment"
    
    # Update load balancer configuration (implementation depends on setup)
    # This is a placeholder for actual load balancer integration
    update_load_balancer_config "$target_env"
    
    success_message "Traffic switched to $target_env environment"
}

# Update load balancer configuration
update_load_balancer_config() {
    local target_env="$1"
    local target_port=$(get_environment_port "$target_env")
    
    # Example: Update nginx configuration
    if command -v nginx &> /dev/null; then
        # Update upstream configuration
        sed -i "s/server localhost:[0-9]*/server localhost:$target_port/" /etc/nginx/sites-available/session-continuity
        nginx -s reload
    fi
}

# Stop environment
stop_environment() {
    local env="$1"
    info_message "Stopping $env environment"
    
    systemctl stop "session-continuity-$env"
    systemctl disable "session-continuity-$env"
    
    success_message "$env environment stopped"
}

# Wait for application startup
wait_for_application_startup() {
    local health_url="$1/actuator/health"
    local max_attempts=30
    local attempt=1
    
    info_message "Waiting for application startup..."
    
    while [[ $attempt -le $max_attempts ]]; do
        if curl -f -s "$health_url" > /dev/null 2>&1; then
            success_message "Application started successfully"
            return 0
        fi
        
        info_message "Startup check attempt $attempt/$max_attempts..."
        sleep 10
        ((attempt++))
    done
    
    error_exit "Application failed to start after $max_attempts attempts"
}

# Post-deployment validation
post_deployment_validation() {
    info_message "Running post-deployment validation"
    
    # Validate session continuity functionality
    validate_session_continuity
    
    # Validate documentation synchronization
    validate_documentation_sync
    
    # Validate quality gates
    validate_quality_gates
    
    # Performance validation
    validate_performance
    
    success_message "Post-deployment validation completed"
}

# Validate session continuity functionality
validate_session_continuity() {
    info_message "Validating session continuity functionality"
    
    local base_url="http://localhost:8080"
    
    # Test session capture
    if curl -f -s "$base_url/api/continuity/session/capture" > /dev/null; then
        success_message "Session capture validation passed"
    else
        error_exit "Session capture validation failed"
    fi
    
    # Test context validation
    if curl -f -s "$base_url/api/continuity/context/validate" > /dev/null; then
        success_message "Context validation passed"
    else
        error_exit "Context validation failed"
    fi
}

# Validate documentation synchronization
validate_documentation_sync() {
    info_message "Validating documentation synchronization"
    
    local base_url="http://localhost:8080"
    
    # Test documentation sync
    if curl -f -s "$base_url/api/continuity/docs/sync/status" > /dev/null; then
        success_message "Documentation sync validation passed"
    else
        error_exit "Documentation sync validation failed"
    fi
}

# Validate quality gates
validate_quality_gates() {
    info_message "Validating quality gates"
    
    local base_url="http://localhost:8080"
    
    # Test quality gates
    if curl -f -s "$base_url/api/continuity/quality-gates/status" > /dev/null; then
        success_message "Quality gates validation passed"
    else
        error_exit "Quality gates validation failed"
    fi
}

# Validate performance
validate_performance() {
    info_message "Validating performance metrics"
    
    local base_url="http://localhost:8080"
    
    # Test performance endpoints
    if curl -f -s "$base_url/api/continuity/performance/metrics" > /dev/null; then
        success_message "Performance validation passed"
    else
        warning_message "Performance validation failed (non-critical)"
    fi
}

# Rollback functionality
rollback_deployment() {
    local version="$1"
    info_message "Rolling back to version: $version"
    
    # Find backup for version
    local backup_dir=$(find "$PROJECT_ROOT/backups" -name "*$version*" | head -1)
    
    if [[ -z "$backup_dir" ]]; then
        error_exit "Backup not found for version: $version"
    fi
    
    info_message "Using backup: $backup_dir"
    
    # Stop current application
    case $ENVIRONMENT in
        "dev")
            pkill -f "comprehensive-dev-portal" || true
            ;;
        "staging"|"prod")
            systemctl stop "session-continuity-$ENVIRONMENT"
            ;;
    esac
    
    # Restore configuration
    cp -r "$backup_dir/config/" "$PROJECT_ROOT/"
    
    # Restore documentation
    cp "$backup_dir"/*.md "$PROJECT_ROOT/" 2>/dev/null || true
    
    # Restore database (if applicable)
    if [[ -f "$backup_dir/database_backup.sql" ]]; then
        restore_database "$backup_dir/database_backup.sql"
    fi
    
    # Restart application
    deploy_application
    
    success_message "Rollback completed successfully"
}

# Restore database
restore_database() {
    local backup_file="$1"
    info_message "Restoring database from backup"
    
    local db_host=$(grep "host:" "$CONFIG_DIR/$ENVIRONMENT.yml" | cut -d':' -f2 | xargs)
    local db_port=$(grep "port:" "$CONFIG_DIR/$ENVIRONMENT.yml" | cut -d':' -f2 | xargs)
    local db_name=$(grep "database:" "$CONFIG_DIR/$ENVIRONMENT.yml" | cut -d':' -f2 | xargs)
    local db_user=$(grep "username:" "$CONFIG_DIR/$ENVIRONMENT.yml" | cut -d':' -f2 | xargs)
    
    if command -v psql &> /dev/null; then
        psql -h "$db_host" -p "$db_port" -U "$db_user" -d "$db_name" -f "$backup_file"
        success_message "Database restored successfully"
    else
        error_exit "psql not available for database restoration"
    fi
}

# Dry run functionality
dry_run() {
    info_message "=== DRY RUN MODE - No changes will be made ==="
    
    echo "Deployment Plan for $ENVIRONMENT:"
    echo "================================"
    echo "1. Environment validation"
    echo "2. Create backup: $BACKUP"
    echo "3. Build application: $([ "$CONFIG_ONLY" == true ] && echo "SKIPPED" || echo "YES")"
    echo "4. Deploy configuration"
    echo "5. Deploy application: $([ "$CONFIG_ONLY" == true ] && echo "SKIPPED" || echo "YES")"
    echo "6. Post-deployment validation"
    echo ""
    echo "Configuration files to be deployed:"
    echo "- $CONFIG_DIR/$ENVIRONMENT.yml"
    echo "- Session continuity configuration"
    echo ""
    echo "Services to be restarted:"
    case $ENVIRONMENT in
        "dev")
            echo "- Java application (port 8080)"
            ;;
        "staging")
            echo "- session-continuity-staging service"
            ;;
        "prod")
            echo "- Blue-green deployment (session-continuity-blue/green)"
            ;;
    esac
    
    info_message "=== END DRY RUN ==="
}

# Main deployment function
main() {
    # Create log directory
    mkdir -p "$(dirname "$LOG_FILE")"
    
    info_message "Starting Session Continuity System deployment"
    info_message "Log file: $LOG_FILE"
    
    # Parse arguments
    parse_arguments "$@"
    
    # Handle rollback
    if [[ -n "$ROLLBACK_VERSION" ]]; then
        rollback_deployment "$ROLLBACK_VERSION"
        exit 0
    fi
    
    # Handle dry run
    if [[ "$DRY_RUN" == true ]]; then
        dry_run
        exit 0
    fi
    
    # Main deployment flow
    validate_environment
    create_backup
    build_application
    deploy_configuration
    deploy_application
    post_deployment_validation
    
    success_message "Session Continuity System deployment completed successfully"
    info_message "Environment: $ENVIRONMENT"
    info_message "Deployment log: $LOG_FILE"
}

# Execute main function with all arguments
main "$@"