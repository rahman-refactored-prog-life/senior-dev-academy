# 🎯 Easiest Way to Install Maven

## ⚡ **Recommended: Use Homebrew (2 commands)**

### Step 1: Install Homebrew
Copy and paste this into your terminal:
```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

**What it does:**
- Installs Homebrew (the macOS package manager)
- Takes 2-5 minutes
- Asks for your password once

### Step 2: Install Maven
```bash
brew install maven
```

**What it does:**
- Downloads and installs Maven automatically
- Configures PATH automatically
- Takes 1-2 minutes

### Step 3: Verify
```bash
mvn -version
```

**You should see:**
```
Apache Maven 3.9.6
Maven home: /opt/homebrew/Cellar/maven/3.9.6/libexec
Java version: 25
```

---

## ✅ **Why Homebrew is Better:**

1. **Automatic PATH configuration** - No manual editing needed
2. **Easy updates** - Just run `brew upgrade maven`
3. **Dependency management** - Handles everything automatically
4. **Standard for macOS** - Used by millions of developers
5. **Install other tools easily** - `brew install node`, `brew install git`, etc.

---

## 🔧 **After Homebrew is Installed:**

You can easily install any development tool:
```bash
brew install node          # Node.js
brew install postgresql    # PostgreSQL
brew install redis         # Redis
brew install docker        # Docker
brew install git           # Git
```

---

## 🚀 **Quick Start (Copy & Paste):**

```bash
# Install Homebrew
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# Install Maven
brew install maven

# Verify
mvn -version

# Done! 🎉
```

---

## 💡 **If the previous script failed:**

The download got redirected and only downloaded 196 bytes instead of the full ~9MB Maven archive.

**Solution:** Use Homebrew (above) or run the fixed script:
```bash
./install-maven-fixed.sh
```

The fixed script:
- Uses archive.apache.org (more reliable)
- Follows redirects with `-L` flag
- Verifies file size before extracting
- Has better error handling

---

## 🎯 **Bottom Line:**

**Just install Homebrew.** It's the standard way to manage development tools on macOS, and you'll use it for many other things in the future.

**2 commands, 5 minutes, done forever.** ✅
