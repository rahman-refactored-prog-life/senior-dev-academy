import React, { useState, useEffect } from 'react';

/**
 * Node.js Content Page
 * Displays the comprehensive Node.js curriculum from the backend DataInitializer
 */
const NodeJSContent = () => {
    const [module, setModule] = useState(null);
    const [topics, setTopics] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [selectedTopic, setSelectedTopic] = useState(null);

    useEffect(() => {
        fetchNodeJSModule();
    }, []);

    const fetchNodeJSModule = async () => {
        try {
            setLoading(true);
            setError(null);
            
            console.log('Starting API call to /api/learning-modules');
            
            // Fetch the Node.js module and its topics from the backend
            const response = await fetch('/api/learning-modules');
            console.log('API Response status:', response.status);
            
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            
            const modules = await response.json();
            console.log('Fetched modules:', modules);
            console.log('Number of modules:', modules.length);
            
            // Find the Node.js module
            const nodeJsModule = modules.find(m => {
                console.log('Checking module:', m.name);
                const hasNodeJs = m.name && m.name.toLowerCase().includes('node.js');
                const hasNodejs = m.name && m.name.toLowerCase().includes('nodejs');
                console.log('Has node.js:', hasNodeJs, 'Has nodejs:', hasNodejs);
                return hasNodeJs || hasNodejs;
            });
            
            console.log('Found Node.js module:', nodeJsModule);
            
            if (nodeJsModule) {
                console.log('Setting module:', nodeJsModule.name);
                setModule(nodeJsModule);
                
                // Fetch topics for this module
                console.log('Fetching topics for module ID:', nodeJsModule.id);
                const topicsResponse = await fetch(`/api/learning-modules/${nodeJsModule.id}/topics`);
                if (topicsResponse.ok) {
                    const topicsData = await topicsResponse.json();
                    console.log('Fetched topics:', topicsData);
                    setTopics(topicsData);
                } else {
                    console.log('Topics response not ok:', topicsResponse.status);
                }
            } else {
                console.log('No Node.js module found in:', modules);
                setError('Node.js module not found');
            }
            
        } catch (error) {
            console.error('Error fetching Node.js content:', error);
            // Fallback to hardcoded content when backend is not available
            const fallbackModule = {
                id: 1,
                name: "Node.js Fundamentals to Expert",
                description: "Complete Node.js mastery: from basics to advanced concepts, frameworks, and enterprise patterns",
                category: "BACKEND",
                difficultyLevel: "INTERMEDIATE",
                topics: [
                    {
                        id: 1,
                        title: "Node.js Basics and Event Loop",
                        description: "Understanding Node.js fundamentals, event loop, and asynchronous programming",
                        content: `
                            <h2>🚀 Node.js Fundamentals</h2>
                            <p>Node.js is a JavaScript runtime built on Chrome's V8 JavaScript engine.</p>
                            <h3>Key Concepts:</h3>
                            <ul>
                                <li><strong>Event-driven, non-blocking I/O model</strong></li>
                                <li><strong>Single-threaded event loop</strong></li>
                                <li><strong>NPM package manager</strong></li>
                                <li><strong>CommonJS modules</strong></li>
                            </ul>
                            <h3>Example Code:</h3>
                            <pre><code>const http = require('http');

const server = http.createServer((req, res) => {
    res.writeHead(200, {'Content-Type': 'text/plain'});
    res.end('Hello World from Node.js!');
});

server.listen(3000, () => {
    console.log('Server running on port 3000');
});</code></pre>
                        `,
                        difficultyLevel: "BEGINNER"
                    },
                    {
                        id: 2,
                        title: "Express.js Framework",
                        description: "Building web applications with Express.js framework",
                        content: `
                            <h2>⚡ Express.js Framework</h2>
                            <p>Express.js is a minimal and flexible Node.js web application framework.</p>
                            <h3>Features:</h3>
                            <ul>
                                <li><strong>Routing</strong> - Handle different HTTP methods and URLs</li>
                                <li><strong>Middleware</strong> - Functions that execute during request-response cycle</li>
                                <li><strong>Template engines</strong> - Render dynamic HTML</li>
                                <li><strong>Error handling</strong> - Centralized error management</li>
                            </ul>
                            <h3>Basic Express App:</h3>
                            <pre><code>const express = require('express');
const app = express();

app.get('/', (req, res) => {
    res.send('Hello Express!');
});

app.listen(3000, () => {
    console.log('Express server running on port 3000');
});</code></pre>
                        `,
                        difficultyLevel: "INTERMEDIATE"
                    }
                ]
            };
            setModule(fallbackModule);
            setError(null); // Clear error since we have fallback content
        } finally {
            setLoading(false);
        }
    };

    const handleTopicSelect = async (topic) => {
        try {
            // Fetch full topic content
            const response = await fetch(`/api/topics/${topic.id}`);
            if (response.ok) {
                const fullTopic = await response.json();
                setSelectedTopic(fullTopic);
            }
        } catch (error) {
            console.error('Error fetching topic details:', error);
        }
    };

    if (loading) {
        return (
            <div className="loading-container">
                <div className="loading-spinner"></div>
                <p>Loading comprehensive Node.js curriculum...</p>
            </div>
        );
    }

    if (error) {
        return (
            <div className="error-container">
                <h2>⚠️ Error Loading Content</h2>
                <p>{error}</p>
                <button onClick={fetchNodeJSModule} className="retry-btn">
                    🔄 Retry Loading
                </button>
            </div>
        );
    }

    if (!module) {
        return (
            <div className="no-content-container">
                <h2>📚 Node.js Content</h2>
                <p>Node.js module not found. Please check if the backend is running and the module is initialized.</p>
                <button onClick={fetchNodeJSModule} className="retry-btn">
                    🔄 Reload
                </button>
            </div>
        );
    }

    return (
        <div className="nodejs-content-page">
            <div className="page-header">
                <h1 className="page-title">🚀 {module.name}</h1>
                <p className="page-subtitle">{module.description}</p>
                
                <div className="module-stats">
                    <div className="stat-card">
                        <span className="stat-number">{topics.length}</span>
                        <span className="stat-label">Topics</span>
                    </div>
                    <div className="stat-card">
                        <span className="stat-number">{module.estimatedHours || 'N/A'}</span>
                        <span className="stat-label">Hours</span>
                    </div>
                    <div className="stat-card">
                        <span className="stat-number">{module.difficultyLevel}</span>
                        <span className="stat-label">Level</span>
                    </div>
                </div>
            </div>

            <div className="content-layout">
                {/* Topics List */}
                <div className="topics-sidebar">
                    <h3>📚 Course Topics</h3>
                    <div className="topics-list">
                        {topics.map((topic, index) => (
                            <div 
                                key={topic.id} 
                                className={`topic-item ${selectedTopic?.id === topic.id ? 'active' : ''}`}
                                onClick={() => handleTopicSelect(topic)}
                            >
                                <div className="topic-number">{index + 1}</div>
                                <div className="topic-info">
                                    <h4 className="topic-title">{topic.title}</h4>
                                    <p className="topic-description">{topic.description}</p>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>

                {/* Topic Content */}
                <div className="topic-content-area">
                    {selectedTopic ? (
                        <div className="selected-topic">
                            <div className="topic-header">
                                <h2>{selectedTopic.title}</h2>
                                <p className="topic-description">{selectedTopic.description}</p>
                            </div>
                            
                            <div 
                                className="topic-content"
                                dangerouslySetInnerHTML={{ __html: selectedTopic.content }}
                            />
                        </div>
                    ) : (
                        <div className="no-topic-selected">
                            <div className="welcome-content">
                                <h2>🎯 Welcome to Node.js Mastery</h2>
                                <p>Select a topic from the left sidebar to begin your comprehensive Node.js learning journey.</p>
                                
                                <div className="course-overview">
                                    <h3>📋 What You'll Learn</h3>
                                    <ul>
                                        <li>🏗️ Node.js Core Concepts and Event Loop</li>
                                        <li>⚡ Asynchronous Programming Patterns</li>
                                        <li>📦 Module System and Package Management</li>
                                        <li>🌐 Express.js Framework Mastery</li>
                                        <li>🚀 Performance Optimization and Scaling</li>
                                        <li>💼 Real-world Projects and Best Practices</li>
                                    </ul>
                                </div>

                                <div className="getting-started">
                                    <h3>🚀 Getting Started</h3>
                                    <p>Click on any topic in the sidebar to dive deep into comprehensive Node.js content with:</p>
                                    <ul>
                                        <li>📖 Detailed explanations and concepts</li>
                                        <li>💻 Practical code examples</li>
                                        <li>🎯 Learning objectives</li>
                                        <li>📝 Interactive note-taking</li>
                                        <li>❓ Interview questions and practice</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default NodeJSContent;