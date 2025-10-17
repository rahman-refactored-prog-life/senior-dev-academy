#!/bin/bash

echo "🚀 Installing Maven for macOS..."
echo ""

# Download Maven
echo "📥 Downloading Maven 3.9.6..."
cd /tmp
curl -O https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz

# Extract Maven
echo "📦 Extracting Maven..."
sudo tar -xzf apache-maven-3.9.6-bin.tar.gz -C /usr/local

# Create symlink
echo "🔗 Creating symlink..."
sudo ln -sf /usr/local/apache-maven-3.9.6 /usr/local/maven

# Add to PATH in .zshrc
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

# Source the file
source ~/.zshrc

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
