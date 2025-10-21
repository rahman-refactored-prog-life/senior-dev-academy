#!/bin/bash

# Environment Validation Script
# This script validates that all required software and configurations are properly set up
# for the Comprehensive Developer Portal

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Counters
ERRORS=0
WARNINGS=0
CHECKS=0

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
    ((ERRORS++))
}

print_warning() {
    echo -e "${YELLOW}⚠${NC} $1"
    ((WARNINGS++))
}

print_info() {
    echo -e "${BLUE}ℹ${NC} $1"
}

# Check functions
check_command() {
    ((CHECKS++))
    local cmd=$1
    local name=$2
    local min_version=$3
    
    if command -v $cmd &> /dev/null; then
        local version=$($cmd --version 2>&1 | head -n 1)
        print_success "$name is installed: $version"
        return 0
    else
        print_error "$name is NOT installed (required: $min_version+)"
        return 1
    fi
}

check_java() {
    ((CHECKS++))
    if command -v java &> /dev/null; then
        local version=$(java -version 2>&1 | head -n 1 | awk -F '"' '{print $2}')
        local major_version=$(echo $version | cut -d'.' -f1)
        
        if [ "$major_version" -ge 17 ]; then
            print_success "Java is installed: $version (minimum: 17)"
            
            # Check JAVA_HOME
            if [ -z "$JAVA_HOME" ]; then
                print_warning "JAVA_HOME is not set. Set it to your JDK installation path."
            else
                print_success "JAVA_HOME is set: $JAVA_HOME"
            fi
            return 0
        else
            print_error "Java version $version is too old (minimum: 17)"
            return 1
        fi
    else
        print_error "Java is NOT installed (required: 17+)"
        return 1
    fi
}

check_maven() {
    ((CHECKS++))
    if command -v mvn &> /dev/null; then
        local version=$(mvn -version 2>&1 | head -n 1 | awk '{print $3}')
        local major=$(echo $version | cut -d'.' -f1)
        local minor=$(echo $version | cut -d'.' -f2)
        
        if [ "$major" -ge 3 ] && [ "$minor" -ge 8 ]; then
            print_success "Maven is installed: $version (minimum: 3.8.0)"
            return 0
        else
            print_error "Maven version $version is too old (minimum: 3.8.0)"
            return 1
        fi
    else
        print_error "Maven is NOT installed (required: 3.8.0+)"
        return 1
    fi
}

check_postgresql() {
    ((CHECKS++))
    if command -v psql &> /dev/null; then
        local version=$(psql --version | awk '{print $3}')
        print_success "PostgreSQL is installed: $version"
        
        # Check if PostgreSQL is running
        if pg_isready &> /dev/null; then
            print_success "PostgreSQL service is running"
        else
            print_warning "PostgreSQL service is NOT running. Start it with: brew services start postgresql (macOS) or sudo systemctl start postgresql (Linux)"
        fi
        
        # Check if database exists
        if psql -U postgres -lqt 2>/dev/null | cut -d \| -f 1 | grep -qw devportalkirostart; then
            print_success "Database 'devportalkirostart' exists"
        else
            print_warning "Database 'devportalkirostart' does NOT exist. Create it with: createdb devportalkirostart"
        fi
        
        return 0
    else
        print_error "PostgreSQL is NOT installed (required: 12+)"
        return 1
    fi
}

check_nodejs() {
    ((CHECKS++))
    if command -v node &> /dev/null; then
        local version=$(node --version | sed 's/v//')
        local major=$(echo $version | cut -d'.' -f1)
        
        if [ "$major" -ge 16 ]; then
            print_success "Node.js is installed: v$version (minimum: 16)"
            
            # Check npm
            if command -v npm &> /dev/null; then
                local npm_version=$(npm --version)
                print_success "npm is installed: $npm_version"
            else
                print_warning "npm is NOT installed"
            fi
            return 0
        else
            print_error "Node.js version $version is too old (minimum: 16)"
            return 1
        fi
    else
        print_error "Node.js is NOT installed (required: 16+)"
        return 1
    fi
}

check_git() {
    ((CHECKS++))
    if command -v git &> /dev/null; then
        local version=$(git --version | awk '{print $3}')
        print_success "Git is installed: $version"
        return 0
    else
        print_error "Git is NOT installed"
        return 1
    fi
}

check_project_structure() {
    ((CHECKS++))
    if [ -f "pom.xml" ]; then
        print_success "Project root directory detected (pom.xml found)"
        
        # Check important directories
        local dirs=("src/main/java" "src/main/resources" "frontend" "target")
        for dir in "${dirs[@]}"; do
            if [ -d "$dir" ]; then
                print_success "Directory exists: $dir"
            else
                if [ "$dir" == "target" ]; then
                    print_info "Directory missing: $dir (will be created during build)"
                else
                    print_warning "Directory missing: $dir"
                fi
            fi
        done
        return 0
    else
        print_error "Not in project root directory (pom.xml not found)"
        return 1
    fi
}

check_env_file() {
    ((CHECKS++))
    if [ -f ".env" ]; then
        print_success ".env file exists"
        
        # Check important variables
        if grep -q "DB_PASSWORD" .env; then
            print_success "DB_PASSWORD is configured in .env"
        else
            print_warning "DB_PASSWORD is NOT configured in .env"
        fi
        
        if grep -q "SPRING_PROFILES_ACTIVE" .env; then
            local profile=$(grep "SPRING_PROFILES_ACTIVE" .env | cut -d'=' -f2)
            print_success "SPRING_PROFILES_ACTIVE is set to: $profile"
        else
            print_info "SPRING_PROFILES_ACTIVE is not set (will use default)"
        fi
        
        return 0
    else
        print_warning ".env file does NOT exist. Copy from .env.example: cp .env.example .env"
        return 1
    fi
}

check_port_availability() {
    ((CHECKS++))
    local port=3008
    
    if command -v lsof &> /dev/null; then
        if lsof -Pi :$port -sTCP:LISTEN -t >/dev/null 2>&1; then
            print_warning "Port $port is already in use. The application may fail to start."
        else
            print_success "Port $port is available"
        fi
    elif command -v netstat &> /dev/null; then
        if netstat -an | grep ":$port " | grep -q LISTEN; then
            print_warning "Port $port is already in use. The application may fail to start."
        else
            print_success "Port $port is available"
        fi
    else
        print_info "Cannot check port availability (lsof/netstat not found)"
    fi
}

check_disk_space() {
    ((CHECKS++))
    local available=$(df -h . | awk 'NR==2 {print $4}' | sed 's/G//')
    
    if [ -n "$available" ]; then
        if (( $(echo "$available > 5" | bc -l) )); then
            print_success "Sufficient disk space available: ${available}GB"
        else
            print_warning "Low disk space: ${available}GB (recommended: 10GB+)"
        fi
    else
        print_info "Cannot check disk space"
    fi
}

check_memory() {
    ((CHECKS++))
    if command -v free &> /dev/null; then
        local total_mem=$(free -g | awk 'NR==2 {print $2}')
        if [ "$total_mem" -ge 4 ]; then
            print_success "Sufficient memory: ${total_mem}GB (minimum: 4GB)"
        else
            print_warning "Low memory: ${total_mem}GB (recommended: 8GB+)"
        fi
    elif command -v sysctl &> /dev/null; then
        local total_mem=$(sysctl -n hw.memsize 2>/dev/null | awk '{print int($1/1024/1024/1024)}')
        if [ -n "$total_mem" ] && [ "$total_mem" -ge 4 ]; then
            print_success "Sufficient memory: ${total_mem}GB (minimum: 4GB)"
        else
            print_warning "Low memory: ${total_mem}GB (recommended: 8GB+)"
        fi
    else
        print_info "Cannot check memory"
    fi
}

# Main execution
main() {
    print_header "Environment Validation for Comprehensive Developer Portal"
    echo ""
    
    print_header "1. Checking Required Software"
    check_java
    check_maven
    check_postgresql
    check_nodejs
    check_git
    echo ""
    
    print_header "2. Checking Project Structure"
    check_project_structure
    check_env_file
    echo ""
    
    print_header "3. Checking System Resources"
    check_port_availability
    check_disk_space
    check_memory
    echo ""
    
    # Summary
    print_header "Validation Summary"
    echo -e "Total checks: ${BLUE}$CHECKS${NC}"
    
    if [ $ERRORS -eq 0 ]; then
        echo -e "Errors: ${GREEN}$ERRORS${NC}"
    else
        echo -e "Errors: ${RED}$ERRORS${NC}"
    fi
    
    if [ $WARNINGS -eq 0 ]; then
        echo -e "Warnings: ${GREEN}$WARNINGS${NC}"
    else
        echo -e "Warnings: ${YELLOW}$WARNINGS${NC}"
    fi
    
    echo ""
    
    if [ $ERRORS -eq 0 ] && [ $WARNINGS -eq 0 ]; then
        print_success "All checks passed! Your environment is ready."
        echo ""
        print_info "Next steps:"
        echo "  1. Build the project: mvn clean install"
        echo "  2. Run the application: mvn spring-boot:run"
        echo "  3. Access the application: http://localhost:3008"
        exit 0
    elif [ $ERRORS -eq 0 ]; then
        print_warning "Validation passed with $WARNINGS warning(s). Review warnings above."
        echo ""
        print_info "You can proceed, but consider addressing the warnings."
        exit 0
    else
        print_error "Validation failed with $ERRORS error(s). Fix errors before proceeding."
        echo ""
        print_info "Refer to SETUP_GUIDE.md for detailed installation instructions."
        exit 1
    fi
}

# Run main function
main
