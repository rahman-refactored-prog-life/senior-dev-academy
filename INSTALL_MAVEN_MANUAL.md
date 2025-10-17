# 🚀 Manual Maven Installation - Step by Step

Your `.zshrc` is already configured! You just need to install Maven.

## ✅ **Your Current Setup:**
- **Java**: ✅ Installed (OpenJDK 25)
- **PATH Configuration**: ✅ Already in ~/.zshrc
- **Maven Binary**: ❌ Need to install

---

## 📋 **Option 1: Install with Homebrew (EASIEST - Recommended)**

### Step 1: Install Homebrew
Open Terminal and run:
```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

### Step 2: Install Maven
```bash
brew install maven
```

### Step 3: Test it
```bash
mvn -version
```

**Done!** Homebrew handles everything automatically.

---

## 📋 **Option 2: Manual Download (If you prefer)**

### Step 1: Download Maven
Open Terminal and run:
```bash
cd ~/Downloads
curl -O https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz
```

### Step 2: Extract and Install
```bash
sudo tar -xzf apache-maven-3.9.6-bin.tar.gz -C /usr/local
sudo ln -sf /usr/local/apache-maven-3.9.6 /usr/local/maven
```
(It will ask for your password)

### Step 3: Reload your shell configuration
```bash
source ~/.zshrc
```

### Step 4: Test it
```bash
mvn -version
```

You should see:
```
Apache Maven 3.9.6
Maven home: /usr/local/maven
Java version: 25, vendor: Oracle Corporation
```

---

## 🎓 **Understanding What Happened:**

### Your ~/.zshrc file contains:
```bash
# Maven Configuration
export M2_HOME=/usr/local/maven
export PATH=$M2_HOME/bin:$PATH
```

**What this means:**
- `M2_HOME=/usr/local/maven` - Tells the system where Maven is installed
- `PATH=$M2_HOME/bin:$PATH` - Adds Maven's `bin` folder to your PATH
- Now when you type `mvn`, the shell knows to look in `/usr/local/maven/bin/`

### To see your current PATH:
```bash
echo $PATH
```

### To reload .zshrc after changes:
```bash
source ~/.zshrc
```

---

## 🔍 **Troubleshooting:**

### If `mvn` command not found:
```bash
# Check if Maven is installed
ls -la /usr/local/maven/bin/mvn

# If not, install it using Option 1 or 2 above

# Reload shell config
source ~/.zshrc

# Try again
mvn -version
```

### If you get permission errors:
```bash
sudo chown -R $(whoami) /usr/local/maven
```

---

## ✅ **After Maven Works:**

### Test the project:
```bash
cd ~/path/to/Java-Comprehensive-Study-Guide
mvn clean compile
mvn spring-boot:run
```

---

## 💡 **My Recommendation:**

**Use Homebrew (Option 1)** - It's the standard for macOS developers and makes installing tools super easy. Just run:

```bash
# Install Homebrew
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# Install Maven
brew install maven

# Done!
mvn -version
```

That's it! 🎉
