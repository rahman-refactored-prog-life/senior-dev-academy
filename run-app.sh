#!/bin/bash

echo "🚀 Starting FAANG Senior Developer Mastery Portal..."
echo "📊 Database: PostgreSQL (devacademykiro)"
echo "🌐 API Documentation: http://localhost:3008/swagger-ui.html"
echo ""

# Test database connection first
echo "🔍 Testing database connection..."
if command -v psql &> /dev/null; then
    ./test-db-connection.sh
    if [ $? -ne 0 ]; then
        echo "❌ Database connection failed. Please fix database setup first."
        exit 1
    fi
else
    echo "⚠️  psql not found. Skipping database connection test."
    echo "   Make sure PostgreSQL is running and accessible."
fi

echo ""
echo "🏃 Starting Spring Boot application..."

# Load environment variables if .env file exists
if [ -f .env ]; then
    echo "📄 Loading environment variables from .env file..."
    export $(cat .env | grep -v '^#' | xargs)
fi

# Set Java version compatibility
export JAVA_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED"

# Run with Spring Boot
mvn spring-boot:run -Dspring-boot.run.jvmArguments="$JAVA_OPTS"