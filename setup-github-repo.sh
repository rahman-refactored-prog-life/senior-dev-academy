#!/bin/bash

# GitHub Repository Setup Script
# This script helps create and push to the senior-dev-academy repository

echo "🚀 Senior Dev Academy - GitHub Repository Setup"
echo "================================================"
echo ""

# Check if we're in a git repository
if [ ! -d ".git" ]; then
    echo "❌ Error: Not in a git repository. Please run this from the project root."
    exit 1
fi

# Check if remote already exists
if git remote get-url origin >/dev/null 2>&1; then
    echo "✅ Git remote 'origin' already configured:"
    git remote get-url origin
    echo ""
else
    echo "⚠️  No remote 'origin' found. Please create the GitHub repository first:"
    echo ""
    echo "1. Go to https://github.com/ahman-refactored-prog-life"
    echo "2. Click 'New repository'"
    echo "3. Repository name: senior-dev-academy"
    echo "4. Description: The most comprehensive learning portal for Java, Spring, React, System Design, and FAANG interview preparation"
    echo "5. Make it Public"
    echo "6. Don't initialize with README (we have one)"
    echo "7. Click 'Create repository'"
    echo ""
    echo "Then run this command to add the remote:"
    echo "git remote add origin https://github.com/ahman-refactored-prog-life/senior-dev-academy.git"
    echo ""
    exit 1
fi

# Check if we have commits to push
if [ -z "$(git log --oneline 2>/dev/null)" ]; then
    echo "❌ Error: No commits found. Please commit your changes first."
    exit 1
fi

# Show current status
echo "📊 Current Git Status:"
echo "====================="
git status --short
echo ""

# Show commits ready to push
echo "📝 Commits ready to push:"
echo "========================"
git log --oneline --graph -10
echo ""

# Ask for confirmation
read -p "🤔 Do you want to push these commits to GitHub? (y/N): " -n 1 -r
echo ""

if [[ $REPLY =~ ^[Yy]$ ]]; then
    echo "🚀 Pushing to GitHub..."
    
    # Push to main branch
    if git push -u origin main; then
        echo ""
        echo "🎉 SUCCESS! Repository pushed to GitHub!"
        echo "🌐 View your repository at: https://github.com/ahman-refactored-prog-life/senior-dev-academy"
        echo ""
        echo "📋 Next steps:"
        echo "- Visit the repository URL above"
        echo "- Add a repository description if needed"
        echo "- Configure repository settings"
        echo "- Add topics/tags for discoverability"
        echo ""
    else
        echo ""
        echo "❌ Push failed. Please check the error messages above."
        echo "💡 Common solutions:"
        echo "- Make sure the repository exists on GitHub"
        echo "- Check your GitHub credentials"
        echo "- Verify the remote URL is correct"
        echo ""
    fi
else
    echo "⏸️  Push cancelled. Run this script again when ready."
fi

echo ""
echo "📚 Project Information:"
echo "======================"
echo "Project: Senior Developer Academy"
echo "Description: Comprehensive learning portal for senior developer preparation"
echo "Technologies: Java 17, Spring Boot 3.2, React 18, PostgreSQL, Maven"
echo "Features: Interactive learning, 2000+ interview questions, code execution, progress tracking"
echo ""