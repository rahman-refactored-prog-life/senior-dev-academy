#!/bin/bash

echo "🚀 Starting Backend Only (No Frontend Build)"
echo "📍 Running on port 3008"

# Compile Java code only
mvn compile -q

# Run Spring Boot without frontend build
java -cp target/classes:$(mvn dependency:build-classpath -q -Dmdep.outputFile=/dev/stdout) \
     com.learningportal.ComprehensiveDevPortalApplication