#!/bin/bash

# generate-docs.sh - Automated documentation generation script
# This script is integrated into the Maven build process to ensure
# documentation is always up-to-date with the current codebase.

set -e

# Configuration
PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
DOCS_DIR="$PROJECT_ROOT/docs"
API_DOCS_DIR="$DOCS_DIR/api/generated"
BUILD_DIR="$PROJECT_ROOT/target"
LOG_FILE="$BUILD_DIR/documentation-generation.log"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Logging functions
log_info() {
    echo -e "${BLUE}[INFO]${NC} $1" | tee -a "$LOG_FILE"
}

log_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1" | tee -a "$LOG_FILE"
}

log_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1" | tee -a "$LOG_FILE"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1" | tee -a "$LOG_FILE"
}

# Initialize logging
mkdir -p "$BUILD_DIR"
echo "Documentation generation started at $(date)" > "$LOG_FILE"

log_info "Starting automated documentation generation"
log_info "Project root: $PROJECT_ROOT"

# Check prerequisites
check_prerequisites() {
    log_info "Checking prerequisites..."
    
    # Check if Maven is available
    if ! command -v mvn &> /dev/null; then
        log_error "Maven is not installed or not in PATH"
        exit 1
    fi
    
    # Check if application is running (for live documentation generation)
    if curl -f http://localhost:8080/actuator/health &> /dev/null; then
        log_info "Application is running - will generate live documentation"
        APP_RUNNING=true
    else
        log_warning "Application is not running - will generate static documentation only"
        APP_RUNNING=false
    fi
    
    # Create documentation directories
    mkdir -p "$API_DOCS_DIR"
    mkdir -p "$DOCS_DIR/architecture/generated"
    mkdir -p "$DOCS_DIR/troubleshooting/generated"
    
    log_success "Prerequisites check completed"
}

# Generate API documentation from OpenAPI spec
generate_api_docs() {
    log_info "Generating API documentation..."
    
    if [ "$APP_RUNNING" = true ]; then
        # Generate from running application
        log_info "Fetching OpenAPI specification from running application"
        
        # Download OpenAPI spec
        curl -s http://localhost:8080/v3/api-docs > "$API_DOCS_DIR/openapi.json"
        
        # Generate documentation using the application's endpoint
        curl -s http://localhost:8080/actuator/documentation > "$API_DOCS_DIR/generation-report.json"
        
        log_success "Live API documentation generated"
    else
        # Generate static documentation
        log_info "Generating static API documentation from code analysis"
        
        # Use Maven to compile and analyze code
        mvn -q compile exec:java -Dexec.mainClass="com.learningportal.documentation.StaticDocumentationGenerator" \
            -Dexec.args="$API_DOCS_DIR" || {
            log_warning "Static documentation generation failed, using fallback method"
            generate_fallback_docs
        }
    fi
}

# Generate fallback documentation when application is not running
generate_fallback_docs() {
    log_info "Generating fallback documentation from source code analysis"
    
    # Analyze Java source files for REST endpoints
    find "$PROJECT_ROOT/src/main/java" -name "*.java" -exec grep -l "@RestController\|@Controller" {} \; | while read -r file; do
        controller_name=$(basename "$file" .java)
        log_info "Analyzing controller: $controller_name"
        
        # Extract endpoint information (simplified)
        grep -n "@.*Mapping\|@GetMapping\|@PostMapping\|@PutMapping\|@DeleteMapping" "$file" >> "$API_DOCS_DIR/endpoints-raw.txt" || true
    done
    
    # Generate basic endpoint list
    if [ -f "$API_DOCS_DIR/endpoints-raw.txt" ]; then
        {
            echo "# API Endpoints (Generated from Source Analysis)"
            echo ""
            echo "*Generated on $(date)*"
            echo ""
            echo "## Discovered Endpoints"
            echo ""
            
            while IFS= read -r line; do
                if [[ $line == *"Mapping"* ]]; then
                    echo "- $line"
                fi
            done < "$API_DOCS_DIR/endpoints-raw.txt"
        } > "$API_DOCS_DIR/endpoints-fallback.md"
        
        rm -f "$API_DOCS_DIR/endpoints-raw.txt"
        log_success "Fallback documentation generated"
    fi
}

# Generate architectural documentation
generate_architecture_docs() {
    log_info "Generating architectural documentation..."
    
    # Generate component dependency graph
    generate_dependency_graph
    
    # Generate database schema documentation
    generate_schema_docs
    
    # Generate configuration documentation
    generate_config_docs
    
    log_success "Architectural documentation generated"
}

# Generate component dependency graph
generate_dependency_graph() {
    log_info "Generating component dependency graph..."
    
    # Use Maven dependency plugin to generate dependency tree
    mvn -q dependency:tree -DoutputFile="$DOCS_DIR/architecture/generated/dependency-tree.txt" || {
        log_warning "Failed to generate dependency tree"
        return 1
    }
    
    # Convert to markdown format
    {
        echo "# Component Dependencies"
        echo ""
        echo "*Generated on $(date)*"
        echo ""
        echo "## Maven Dependency Tree"
        echo ""
        echo '```'
        cat "$DOCS_DIR/architecture/generated/dependency-tree.txt"
        echo '```'
    } > "$DOCS_DIR/architecture/generated/dependencies.md"
    
    log_info "Component dependency graph generated"
}

# Generate database schema documentation
generate_schema_docs() {
    log_info "Generating database schema documentation..."
    
    # Find JPA entities
    entity_files=$(find "$PROJECT_ROOT/src/main/java" -name "*.java" -exec grep -l "@Entity" {} \;)
    
    if [ -n "$entity_files" ]; then
        {
            echo "# Database Schema Documentation"
            echo ""
            echo "*Generated on $(date)*"
            echo ""
            echo "## JPA Entities"
            echo ""
            
            for file in $entity_files; do
                entity_name=$(basename "$file" .java)
                echo "### $entity_name"
                echo ""
                echo "**File:** \`$(realpath --relative-to="$PROJECT_ROOT" "$file")\`"
                echo ""
                
                # Extract table name if specified
                table_annotation=$(grep -n "@Table" "$file" || true)
                if [ -n "$table_annotation" ]; then
                    echo "**Table:** $table_annotation"
                    echo ""
                fi
                
                # Extract fields with JPA annotations
                echo "**Fields:**"
                echo ""
                grep -n "@Column\|@Id\|@GeneratedValue\|@JoinColumn\|@OneToMany\|@ManyToOne\|@ManyToMany" "$file" | while IFS= read -r line; do
                    echo "- $line"
                done
                echo ""
            done
        } > "$DOCS_DIR/architecture/generated/database-schema.md"
        
        log_info "Database schema documentation generated"
    else
        log_warning "No JPA entities found"
    fi
}

# Generate configuration documentation
generate_config_docs() {
    log_info "Generating configuration documentation..."
    
    # Find all application.yml and application.properties files
    config_files=$(find "$PROJECT_ROOT/src/main/resources" -name "application*.yml" -o -name "application*.properties")
    
    if [ -n "$config_files" ]; then
        {
            echo "# Configuration Documentation"
            echo ""
            echo "*Generated on $(date)*"
            echo ""
            echo "## Configuration Files"
            echo ""
            
            for file in $config_files; do
                config_name=$(basename "$file")
                echo "### $config_name"
                echo ""
                echo "**File:** \`$(realpath --relative-to="$PROJECT_ROOT" "$file")\`"
                echo ""
                echo '```yaml'
                cat "$file"
                echo '```'
                echo ""
            done
        } > "$DOCS_DIR/architecture/generated/configuration.md"
        
        log_info "Configuration documentation generated"
    fi
}

# Validate documentation completeness
validate_documentation() {
    log_info "Validating documentation completeness..."
    
    local validation_errors=0
    
    # Check for required documentation files
    required_files=(
        "$DOCS_DIR/README.md"
        "$DOCS_DIR/api/README.md"
        "$DOCS_DIR/architecture/README.md"
        "$DOCS_DIR/deployment/README.md"
        "$DOCS_DIR/troubleshooting/README.md"
    )
    
    for file in "${required_files[@]}"; do
        if [ ! -f "$file" ]; then
            log_error "Required documentation file missing: $file"
            ((validation_errors++))
        fi
    done
    
    # Check for empty or outdated files
    find "$DOCS_DIR" -name "*.md" -size 0 | while IFS= read -r empty_file; do
        log_warning "Empty documentation file found: $empty_file"
    done
    
    # Check for files older than 30 days
    find "$DOCS_DIR" -name "*.md" -mtime +30 | while IFS= read -r old_file; do
        log_warning "Documentation file may be outdated (>30 days): $old_file"
    done
    
    if [ $validation_errors -eq 0 ]; then
        log_success "Documentation validation passed"
        return 0
    else
        log_error "Documentation validation failed with $validation_errors errors"
        return 1
    fi
}

# Generate documentation index
generate_index() {
    log_info "Generating documentation index..."
    
    {
        echo "# Learning Portal Documentation Index"
        echo ""
        echo "*Generated automatically on $(date)*"
        echo ""
        echo "## Available Documentation"
        echo ""
        
        # Find all markdown files and organize them
        find "$DOCS_DIR" -name "*.md" -not -path "*/generated/*" | sort | while IFS= read -r file; do
            relative_path=$(realpath --relative-to="$DOCS_DIR" "$file")
            title=$(head -n 1 "$file" | sed 's/^# //' || echo "$(basename "$file" .md)")
            echo "- [$title]($relative_path)"
        done
        
        echo ""
        echo "## Generated Documentation"
        echo ""
        
        find "$DOCS_DIR" -path "*/generated/*.md" | sort | while IFS= read -r file; do
            relative_path=$(realpath --relative-to="$DOCS_DIR" "$file")
            title=$(head -n 1 "$file" | sed 's/^# //' || echo "$(basename "$file" .md)")
            echo "- [$title]($relative_path) *(auto-generated)*"
        done
        
        echo ""
        echo "## Documentation Statistics"
        echo ""
        
        total_files=$(find "$DOCS_DIR" -name "*.md" | wc -l)
        generated_files=$(find "$DOCS_DIR" -path "*/generated/*.md" | wc -l)
        manual_files=$((total_files - generated_files))
        
        echo "- Total documentation files: $total_files"
        echo "- Manual documentation files: $manual_files"
        echo "- Generated documentation files: $generated_files"
        echo "- Last updated: $(date)"
        
    } > "$DOCS_DIR/INDEX.md"
    
    log_success "Documentation index generated"
}

# Update documentation metadata
update_metadata() {
    log_info "Updating documentation metadata..."
    
    # Create metadata file
    {
        echo "{"
        echo "  \"lastGenerated\": \"$(date -Iseconds)\","
        echo "  \"generator\": \"generate-docs.sh\","
        echo "  \"version\": \"1.0.0\","
        echo "  \"statistics\": {"
        echo "    \"totalFiles\": $(find "$DOCS_DIR" -name "*.md" | wc -l),"
        echo "    \"generatedFiles\": $(find "$DOCS_DIR" -path "*/generated/*.md" | wc -l),"
        echo "    \"apiEndpoints\": $(grep -r "@.*Mapping" "$PROJECT_ROOT/src/main/java" | wc -l || echo 0)"
        echo "  }"
        echo "}"
    } > "$DOCS_DIR/metadata.json"
    
    log_success "Documentation metadata updated"
}

# Clean up old generated files
cleanup_old_files() {
    log_info "Cleaning up old generated files..."
    
    # Remove files older than 7 days from generated directories
    find "$DOCS_DIR" -path "*/generated/*" -name "*.md" -mtime +7 -delete || true
    find "$DOCS_DIR" -path "*/generated/*" -name "*.json" -mtime +7 -delete || true
    
    log_info "Cleanup completed"
}

# Main execution
main() {
    log_info "=== Documentation Generation Started ==="
    
    # Check prerequisites
    check_prerequisites
    
    # Generate different types of documentation
    generate_api_docs
    generate_architecture_docs
    
    # Validate and organize documentation
    validate_documentation
    generate_index
    update_metadata
    
    # Cleanup
    cleanup_old_files
    
    log_success "=== Documentation Generation Completed ==="
    log_info "Documentation available in: $DOCS_DIR"
    log_info "Generation log: $LOG_FILE"
    
    # Print summary
    echo ""
    echo "Documentation Generation Summary:"
    echo "================================"
    echo "Total files: $(find "$DOCS_DIR" -name "*.md" | wc -l)"
    echo "Generated files: $(find "$DOCS_DIR" -path "*/generated/*.md" | wc -l)"
    echo "Documentation index: $DOCS_DIR/INDEX.md"
    echo "Metadata: $DOCS_DIR/metadata.json"
    echo ""
}

# Handle script arguments
case "${1:-}" in
    --validate-only)
        log_info "Running validation only"
        check_prerequisites
        validate_documentation
        ;;
    --api-only)
        log_info "Generating API documentation only"
        check_prerequisites
        generate_api_docs
        ;;
    --architecture-only)
        log_info "Generating architecture documentation only"
        check_prerequisites
        generate_architecture_docs
        ;;
    --help|-h)
        echo "Usage: $0 [OPTIONS]"
        echo ""
        echo "Options:"
        echo "  --validate-only      Run documentation validation only"
        echo "  --api-only          Generate API documentation only"
        echo "  --architecture-only Generate architecture documentation only"
        echo "  --help, -h          Show this help message"
        echo ""
        echo "Default: Generate all documentation"
        ;;
    *)
        main
        ;;
esac