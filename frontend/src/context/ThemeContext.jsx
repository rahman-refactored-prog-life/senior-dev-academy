import React, { createContext, useContext, useState, useEffect } from 'react';

const ThemeContext = createContext();

export const useTheme = () => {
  const context = useContext(ThemeContext);
  if (!context) {
    throw new Error('useTheme must be used within a ThemeProvider');
  }
  return context;
};

export const ThemeProvider = ({ children }) => {
  const [theme, setTheme] = useState(() => {
    // Check localStorage first, then system preference
    const savedTheme = localStorage.getItem('learning-portal-theme');
    if (savedTheme) {
      return savedTheme;
    }
    
    // Check system preference
    if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {
      return 'dark';
    }
    
    return 'light';
  });

  const [fontSize, setFontSize] = useState(() => {
    const savedFontSize = localStorage.getItem('learning-portal-font-size');
    return savedFontSize || 'medium';
  });

  const [reducedMotion, setReducedMotion] = useState(() => {
    const savedMotion = localStorage.getItem('learning-portal-reduced-motion');
    if (savedMotion !== null) {
      return savedMotion === 'true';
    }
    
    // Check system preference
    return window.matchMedia && window.matchMedia('(prefers-reduced-motion: reduce)').matches;
  });

  const [highContrast, setHighContrast] = useState(() => {
    const savedContrast = localStorage.getItem('learning-portal-high-contrast');
    return savedContrast === 'true';
  });

  // Theme configurations
  const themes = {
    light: {
      name: 'Light',
      colors: {
        background: 'var(--color-neutral-50)',
        surface: 'var(--color-white)',
        primary: 'var(--color-primary-600)',
        secondary: 'var(--color-secondary-600)',
        text: 'var(--color-neutral-800)',
        textSecondary: 'var(--color-neutral-600)',
        border: 'var(--color-neutral-200)',
        accent: 'var(--color-accent-500)',
      },
      shadows: {
        sm: 'var(--shadow-sm)',
        md: 'var(--shadow-md)',
        lg: 'var(--shadow-lg)',
        xl: 'var(--shadow-xl)',
      }
    },
    dark: {
      name: 'Dark',
      colors: {
        background: 'var(--color-neutral-900)',
        surface: 'var(--color-neutral-800)',
        primary: 'var(--color-primary-400)',
        secondary: 'var(--color-secondary-400)',
        text: 'var(--color-neutral-100)',
        textSecondary: 'var(--color-neutral-300)',
        border: 'var(--color-neutral-700)',
        accent: 'var(--color-accent-400)',
      },
      shadows: {
        sm: '0 1px 2px 0 rgba(0, 0, 0, 0.3)',
        md: '0 4px 6px -1px rgba(0, 0, 0, 0.4)',
        lg: '0 10px 15px -3px rgba(0, 0, 0, 0.4)',
        xl: '0 20px 25px -5px rgba(0, 0, 0, 0.4)',
      }
    },
    system: {
      name: 'System',
      colors: {}, // Will use CSS custom properties that adapt to system
      shadows: {}
    }
  };

  const fontSizes = {
    small: {
      name: 'Small',
      scale: 0.875,
      className: 'font-size-small'
    },
    medium: {
      name: 'Medium',
      scale: 1,
      className: 'font-size-medium'
    },
    large: {
      name: 'Large',
      scale: 1.125,
      className: 'font-size-large'
    },
    xlarge: {
      name: 'Extra Large',
      scale: 1.25,
      className: 'font-size-xlarge'
    }
  };

  // Apply theme to document
  useEffect(() => {
    const root = document.documentElement;
    
    // Apply theme class
    root.className = root.className.replace(/theme-\\w+/g, '');
    root.classList.add(`theme-${theme}`);
    
    // Apply font size class
    root.className = root.className.replace(/font-size-\\w+/g, '');
    root.classList.add(fontSizes[fontSize].className);
    
    // Apply accessibility preferences
    if (reducedMotion) {
      root.classList.add('reduced-motion');
    } else {
      root.classList.remove('reduced-motion');
    }
    
    if (highContrast) {
      root.classList.add('high-contrast');
    } else {
      root.classList.remove('high-contrast');
    }
    
    // Apply CSS custom properties for current theme
    if (themes[theme] && themes[theme].colors) {
      Object.entries(themes[theme].colors).forEach(([key, value]) => {
        root.style.setProperty(`--theme-${key}`, value);
      });
      
      Object.entries(themes[theme].shadows).forEach(([key, value]) => {
        root.style.setProperty(`--theme-shadow-${key}`, value);
      });
    }
    
    // Save to localStorage
    localStorage.setItem('learning-portal-theme', theme);
    localStorage.setItem('learning-portal-font-size', fontSize);
    localStorage.setItem('learning-portal-reduced-motion', reducedMotion.toString());
    localStorage.setItem('learning-portal-high-contrast', highContrast.toString());
  }, [theme, fontSize, reducedMotion, highContrast]);

  // Listen for system theme changes
  useEffect(() => {
    if (theme === 'system') {
      const mediaQuery = window.matchMedia('(prefers-color-scheme: dark)');
      const handleChange = (e) => {
        // Update CSS custom properties based on system preference
        const systemTheme = e.matches ? 'dark' : 'light';
        const root = document.documentElement;
        root.className = root.className.replace(/theme-\\w+/g, '');
        root.classList.add(`theme-${systemTheme}`);
      };
      
      mediaQuery.addEventListener('change', handleChange);
      return () => mediaQuery.removeEventListener('change', handleChange);
    }
  }, [theme]);

  const toggleTheme = () => {
    const themeOrder = ['light', 'dark', 'system'];
    const currentIndex = themeOrder.indexOf(theme);
    const nextIndex = (currentIndex + 1) % themeOrder.length;
    setTheme(themeOrder[nextIndex]);
  };

  const setThemeMode = (mode) => {
    if (themes[mode]) {
      setTheme(mode);
    }
  };

  const increaseFontSize = () => {
    const sizeOrder = ['small', 'medium', 'large', 'xlarge'];
    const currentIndex = sizeOrder.indexOf(fontSize);
    if (currentIndex < sizeOrder.length - 1) {
      setFontSize(sizeOrder[currentIndex + 1]);
    }
  };

  const decreaseFontSize = () => {
    const sizeOrder = ['small', 'medium', 'large', 'xlarge'];
    const currentIndex = sizeOrder.indexOf(fontSize);
    if (currentIndex > 0) {
      setFontSize(sizeOrder[currentIndex - 1]);
    }
  };

  const resetPreferences = () => {
    setTheme('light');
    setFontSize('medium');
    setReducedMotion(false);
    setHighContrast(false);
  };

  const value = {
    // Current state
    theme,
    fontSize,
    reducedMotion,
    highContrast,
    
    // Theme data
    themes,
    fontSizes,
    currentTheme: themes[theme],
    currentFontSize: fontSizes[fontSize],
    
    // Actions
    setTheme: setThemeMode,
    toggleTheme,
    setFontSize,
    increaseFontSize,
    decreaseFontSize,
    setReducedMotion,
    setHighContrast,
    resetPreferences,
    
    // Computed values
    isDark: theme === 'dark' || (theme === 'system' && window.matchMedia('(prefers-color-scheme: dark)').matches),
    isLight: theme === 'light' || (theme === 'system' && !window.matchMedia('(prefers-color-scheme: dark)').matches),
    isSystem: theme === 'system',
  };

  return (
    <ThemeContext.Provider value={value}>
      {children}
    </ThemeContext.Provider>
  );
};

export default ThemeProvider;