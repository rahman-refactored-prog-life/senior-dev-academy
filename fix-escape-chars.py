#!/usr/bin/env python3
"""
Fix escape characters in DataInitializer.java
Properly escapes JavaScript code embedded in Java text blocks
"""

import re
import sys

def fix_escape_characters(content):
    """
    Fix escape characters in Java text blocks containing JavaScript
    """
    # Already escaped sequences that should not be double-escaped
    # We need to be careful not to double-escape
    
    # Fix template literals: ${...} needs to be \\${...}
    # But only if not already escaped
    content = re.sub(r'(?<!\\)\\(\$\{)', r'\\\\\1', content)
    
    # Fix GitHub Actions variables: ${{ ... }} needs to be \\${{ ... }}
    # But only if not already escaped  
    content = re.sub(r'(?<!\\)\\(\$\{\{)', r'\\\\\1', content)
    
    return content

def main():
    input_file = 'src/main/java/com/learningportal/config/DataInitializer.java'
    
    print(f"Reading {input_file}...")
    
    try:
        with open(input_file, 'r', encoding='utf-8') as f:
            content = f.read()
    except Exception as e:
        print(f"Error reading file: {e}")
        sys.exit(1)
    
    print("Fixing escape characters...")
    fixed_content = fix_escape_characters(content)
    
    print(f"Writing fixed content back to {input_file}...")
    
    try:
        with open(input_file, 'w', encoding='utf-8') as f:
            f.write(fixed_content)
    except Exception as e:
        print(f"Error writing file: {e}")
        sys.exit(1)
    
    print("✅ Escape characters fixed successfully!")
    print("\nNext steps:")
    print("1. Run: mvn clean compile")
    print("2. If successful, run: mvn spring-boot:run")

if __name__ == '__main__':
    main()
