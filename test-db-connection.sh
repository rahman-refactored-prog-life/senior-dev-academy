#!/bin/bash

echo "🔍 Testing PostgreSQL Database Connection..."
echo "Database: devacademykiro"
echo "Port: 5432"
echo ""

# Test basic connection
echo "1. Testing basic PostgreSQL connection..."
psql -U postgres -h localhost -d devacademykiro -c "SELECT version();" 2>/dev/null

if [ $? -eq 0 ]; then
    echo "✅ PostgreSQL connection successful!"
else
    echo "❌ PostgreSQL connection failed!"
    echo "Please ensure:"
    echo "  - PostgreSQL is running on localhost:5432"
    echo "  - Database 'devacademykiro' exists"
    echo "  - User 'postgres' has access"
    echo ""
    echo "To create the database, run:"
    echo "  psql -U postgres -h localhost -f setup-database.sql"
    exit 1
fi

echo ""
echo "2. Testing table creation permissions..."
psql -U postgres -h localhost -d devacademykiro -c "
CREATE TABLE IF NOT EXISTS test_table (id SERIAL PRIMARY KEY, name VARCHAR(50)); 
DROP TABLE IF EXISTS test_table;
" 2>/dev/null

if [ $? -eq 0 ]; then
    echo "✅ Table creation permissions OK!"
else
    echo "❌ Table creation failed!"
    exit 1
fi

echo ""
echo "🎉 Database setup is ready for the FAANG Mastery Portal!"
echo ""
echo "Connection details:"
echo "  URL: jdbc:postgresql://localhost:5432/devacademykiro"
echo "  Username: postgres"
echo "  Password: password"
echo ""
echo "To start the application:"
echo "  ./run-app.sh"
echo "  or"
echo "  mvn spring-boot:run"