#!/bin/bash

echo "🚀 Starting FAANG Senior Developer Mastery Portal REST API..."
echo "📍 Server will run on: http://localhost:9090/api/v1"
echo "📖 Swagger UI: http://localhost:9090/api/v1/swagger-ui.html"
echo "📊 API Docs: http://localhost:9090/api/v1/v3/api-docs"
echo ""

# Ensure PostgreSQL is running
echo "🔍 Checking PostgreSQL connection..."
if ! pg_isready -h localhost -p 5432 >/dev/null 2>&1; then
    echo "❌ PostgreSQL is not running. Please start PostgreSQL first."
    echo "   You can start it with: brew services start postgresql"
    exit 1
fi

echo "✅ PostgreSQL is running"
echo ""

# Build and run the application
echo "🔨 Building application..."
mvn clean compile -q

if [ $? -eq 0 ]; then
    echo "✅ Build successful"
    echo ""
    echo "🚀 Starting REST API server..."
    echo "   Press Ctrl+C to stop the server"
    echo ""
    
    mvn spring-boot:run
else
    echo "❌ Build failed. Please check the errors above."
    exit 1
fi