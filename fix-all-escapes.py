#!/usr/bin/env python3
"""
Comprehensive fix for all escape character issues in DataInitializer.java
"""

def fix_line(line):
    """Fix escape characters in a single line"""
    # Don't modify lines that are already properly escaped or are comments
    if line.strip().startswith('//') or line.strip().startswith('*'):
        return line
    
    # Fix patterns that need escaping in Java text blocks
    # These are the problematic patterns found in JavaScript/YAML code
    
    # Pattern 1: \$ followed by { (template literals and GitHub Actions)
    # Need to ensure it becomes \\$
    import re
    
    # Fix \${ that isn't already \\${
    line = re.sub(r'(?<!\\)\\(\$\{)', r'\\\\\1', line)
    
    # Fix \${{ that isn't already \\${{
    line = re.sub(r'(?<!\\)\\(\$\{\{)', r'\\\\\1', line)
    
    # Fix standalone \$ at end of lines (Dockerfile line continuation)
    line = re.sub(r'(?<!\\)\\(\$)$', r'\\\\\1', line)
    
    return line

def main():
    input_file = 'src/main/java/com/learningportal/config/DataInitializer.java'
    
    print(f"Reading {input_file}...")
    
    with open(input_file, 'r', encoding='utf-8') as f:
        lines = f.readlines()
    
    print(f"Processing {len(lines)} lines...")
    
    fixed_lines = []
    changes_made = 0
    
    for i, line in enumerate(lines, 1):
        original = line
        fixed = fix_line(line)
        
        if fixed != original:
            changes_made += 1
            print(f"  Line {i}: Fixed escape characters")
        
        fixed_lines.append(fixed)
    
    print(f"\nMade {changes_made} fixes")
    print(f"Writing fixed content back to {input_file}...")
    
    with open(input_file, 'w', encoding='utf-8') as f:
        f.writelines(fixed_lines)
    
    print("✅ All escape characters fixed!")
    print("\nNext: Run 'mvn clean compile' to verify")

if __name__ == '__main__':
    main()
