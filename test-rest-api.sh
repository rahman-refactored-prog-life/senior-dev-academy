#!/bin/bash

API_BASE="http://localhost:9090/api/v1"

echo "🧪 Testing FAANG Senior Developer Mastery Portal REST API"
echo "📍 API Base URL: $API_BASE"
echo ""

# Test if server is running
echo "1️⃣ Testing server health..."
curl -s "$API_BASE/actuator/health" | jq '.' 2>/dev/null || echo "Server not responding or jq not installed"
echo ""

# Test modules endpoint
echo "2️⃣ Testing GET /modules (all learning modules)..."
curl -s "$API_BASE/modules" | jq '.content[0:2]' 2>/dev/null || echo "No modules found or jq not installed"
echo ""

# Test modules statistics
echo "3️⃣ Testing GET /modules/statistics..."
curl -s "$API_BASE/modules/statistics" | jq '.' 2>/dev/null || echo "Statistics not available or jq not installed"
echo ""

# Test Swagger documentation
echo "4️⃣ Testing API documentation..."
curl -s "$API_BASE/v3/api-docs" | head -c 200
echo "..."
echo ""

echo "✅ REST API Test Complete!"
echo ""
echo "🌐 Available Endpoints:"
echo "   • GET  $API_BASE/modules - All learning modules"
echo "   • GET  $API_BASE/modules/{id} - Specific module"
echo "   • GET  $API_BASE/modules/category/{category} - Modules by category"
echo "   • GET  $API_BASE/modules/difficulty/{difficulty} - Modules by difficulty"
echo "   • GET  $API_BASE/modules/statistics - Module statistics"
echo "   • GET  $API_BASE/modules/popular - Popular modules"
echo "   • POST $API_BASE/modules - Create new module"
echo "   • PUT  $API_BASE/modules/{id} - Update module"
echo "   • DELETE $API_BASE/modules/{id} - Delete module"
echo ""
echo "📖 Interactive Documentation: $API_BASE/swagger-ui.html"