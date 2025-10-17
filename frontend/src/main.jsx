import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './styles/aws-design-system.css'
import './styles/modern-design-system.css'
import './styles/aws-glow-effects.css'
import './styles/aws-console-sidebar.css'
import './styles/sidebar-override.css'
import './styles/layout.css'
import './styles/components.css'
import './styles/dashboard.css'
import './styles/pages.css'
import './styles/layout-fixes.css'

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
)