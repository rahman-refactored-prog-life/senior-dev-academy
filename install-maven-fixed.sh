#!/bin/bash

echo "🚀 Installing Maven for macOS (Fixed Version)..."
echo ""

# Clean up any failed downloads
rm -f /tmp/apache-maven-3.9.6-bin.tar.gz

# Download Maven with redirect following
echo "📥 Downloading Maven 3.9.6..."
cd /tmp
curl -L -O https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz

# Verify download
if [ ! -f "apache-maven-3.9.6-bin.tar.gz" ]; then
    echo "❌ Download failed!"
    exit 1
fi

FILE_SIZE=$(stat -f%z apache-maven-3.9.6-bin.tar.gz 2>/dev/null || stat -c%s apache-maven-3.9.6-bin.tar.gz)
if [ "$FILE_SIZE" -lt 1000000 ]; then
    echo "❌ Download incomplete (only $FILE_SIZE bytes)"
    echo "🔄 Trying alternative mirror..."
    rm -f apache-maven-3.9.6-bin.tar.gz
    curl -L -O https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz
fi

echo "✅ Downloaded $(stat -f%z apache-maven-3.9.6-bin.tar.gz 2>/dev/null || stat -c%s apache-maven-3.9.6-bin.tar.gz) bytes"

# Extract Maven
echo "📦 Extracting Maven..."
sudo tar -xzf apache-maven-3.9.6-bin.tar.gz -C /usr/local

if [ $? -ne 0 ]; then
    echo "❌ Extraction failed!"
    exit 1
fi

# Create symlink
echo "🔗 Creating symlink..."
sudo ln -sf /usr/local/apache-maven-3.9.6 /usr/local/maven

# Verify installation
if [ -f "/usr/local/maven/bin/mvn" ]; then
    echo "✅ Maven binary found!"
else
    echo "❌ Maven binary not found!"
    exit 1
fi

# Add to PATH in .zshrc (if not already there)
echo "⚙️  Configuring PATH..."
if ! grep -q "M2_HOME" ~/.zshrc; then
    echo "" >> ~/.zshrc
    echo "# Maven Configuration" >> ~/.zshrc
    echo "export M2_HOME=/usr/local/maven" >> ~/.zshrc
    echo "export PATH=\$M2_HOME/bin:\$PATH" >> ~/.zshrc
    echo "✅ Added Maven to ~/.zshrc"
else
    echo "✅ Maven already in ~/.zshrc"
fi

echo ""
echo "✅ Maven installation complete!"
echo ""
echo "🔍 Verifying installation..."
/usr/local/maven/bin/mvn -version

echo ""
echo "📝 To use Maven in your current terminal, run:"
echo "   source ~/.zshrc"
echo ""
echo "Or open a new terminal window."
echo ""
echo "🎯 Test with: mvn -version"
