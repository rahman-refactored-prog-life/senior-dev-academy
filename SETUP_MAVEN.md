# Maven Installation Guide for macOS

## ✅ Current Status
- **Java**: ✅ Installed (OpenJDK 25)
- **Maven**: ❌ Not installed
- **Shell**: zsh

---

## 🚀 **Option 1: Install with Homebrew (RECOMMENDED - Easiest)**

### Step 1: Install Homebrew (if not installed)
```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

### Step 2: Install Maven
```bash
brew install maven
```

### Step 3: Verify Installation
```bash
mvn -version
```

**That's it!** Homebrew automatically handles PATH configuration.

---

## 🔧 **Option 2: Manual Installation (More Control)**

### Step 1: Download Maven
```bash
cd ~/Downloads
curl -O https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz
```

### Step 2: Extract to /usr/local
```bash
sudo tar -xzf apache-maven-3.9.6-bin.tar.gz -C /usr/local
sudo ln -s /usr/local/apache-maven-3.9.6 /usr/local/maven
```

### Step 3: Add Maven to PATH
Edit your `~/.zshrc` file:
```bash
nano ~/.zshrc
```

Add these lines at the end:
```bash
# Maven Configuration
export M2_HOME=/usr/local/maven
export PATH=$M2_HOME/bin:$PATH
```

### Step 4: Apply Changes
```bash
source ~/.zshrc
```

### Step 5: Verify Installation
```bash
mvn -version
```

---

## 📚 **Understanding PATH on macOS**

### What is PATH?
PATH is an environment variable that tells your shell where to find executable programs.

### How to View Your Current PATH
```bash
echo $PATH
```

### How to Add to PATH (zsh)
Edit `~/.zshrc`:
```bash
nano ~/.zshrc
```

Add this line:
```bash
export PATH="/path/to/your/program:$PATH"
```

Apply changes:
```bash
source ~/.zshrc
```

### Common PATH Locations on macOS
- `/usr/local/bin` - User-installed programs (Homebrew)
- `/usr/bin` - System programs
- `/opt/homebrew/bin` - Homebrew on Apple Silicon Macs
- `~/bin` - Your personal scripts

---

## 🎯 **Quick PATH Tips**

### 1. Check if a command is in PATH
```bash
which mvn
which java
which node
```

### 2. Temporarily add to PATH (current session only)
```bash
export PATH="/new/path:$PATH"
```

### 3. Permanently add to PATH
Add to `~/.zshrc` (for zsh) or `~/.bash_profile` (for bash):
```bash
export PATH="/new/path:$PATH"
```

### 4. Reload shell configuration
```bash
source ~/.zshrc
```

### 5. Check Java installation
```bash
java -version
/usr/libexec/java_home -V  # Shows all installed Java versions
```

---

## 🔍 **Troubleshooting**

### Maven command not found after installation
```bash
# Check if Maven is installed
ls -la /usr/local/maven/bin/mvn

# Check your PATH
echo $PATH

# Reload shell config
source ~/.zshrc

# Try full path
/usr/local/maven/bin/mvn -version
```

### Multiple Java versions
```bash
# List all Java versions
/usr/libexec/java_home -V

# Set specific Java version
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
```

### Permission issues
```bash
# If you get permission errors
sudo chown -R $(whoami) /usr/local/maven
```

---

## ✅ **After Maven is Installed**

### Test the project
```bash
cd ~/path/to/Java-Comprehensive-Study-Guide
mvn clean compile
mvn spring-boot:run
```

### Common Maven commands
```bash
mvn clean              # Clean build artifacts
mvn compile            # Compile the code
mvn test               # Run tests
mvn package            # Create JAR file
mvn spring-boot:run    # Run Spring Boot app
mvn clean install      # Full build with dependencies
```

---

## 🎓 **Learning Resources**

### Understanding PATH
- PATH is searched left-to-right
- First match wins
- Use `:` to separate multiple paths
- `$PATH` references the existing PATH

### Example PATH
```bash
/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin
```

This means the shell looks for commands in:
1. `/usr/local/bin` first
2. Then `/usr/bin`
3. Then `/bin`
4. And so on...

---

## 🚀 **Recommended: Use Homebrew**

Homebrew is the standard package manager for macOS and handles all PATH configuration automatically. It's what most developers use.

**Install Homebrew + Maven in 2 commands:**
```bash
# Install Homebrew
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# Install Maven
brew install maven
```

Done! 🎉
