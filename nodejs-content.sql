-- Node.js Content Population Script - JPA Compatible
-- Insert comprehensive Node.js learning content matching JPA entity structure exactly

-- Clear existing data
DELETE FROM topics WHERE module_id = 1;
DELETE FROM learning_modules WHERE id = 1;

-- Insert Node.js module matching LearningModule entity
INSERT INTO learning_modules (id, name, description, category, difficulty_level, estimated_hours, sort_order, active, created_at, updated_at) 
VALUES (1, 'Node.js Fundamentals to Expert', 'Complete Node.js mastery: from basics to advanced concepts, frameworks, and enterprise patterns', 'JAVA_FUNDAMENTALS', 'INTERMEDIATE', 50, 1, true, NOW(), NOW());

-- Insert Node.js Topics matching Topic entity exactly
INSERT INTO topics (title, description, content, module_id, difficulty_level, topic_type, sort_order, active, created_at, updated_at) VALUES

-- Topic 1: Node.js Basics
('Node.js Core Concepts and Event Loop', 'Understanding Node.js fundamentals, event loop, and asynchronous programming', 
'<h2>🚀 Node.js Fundamentals</h2>
<p>Node.js is a JavaScript runtime built on Chrome''s V8 JavaScript engine that allows you to run JavaScript on the server side.</p>

<h3>🔑 Key Concepts:</h3>
<ul>
    <li><strong>Event-driven, non-blocking I/O model</strong> - Handles multiple operations concurrently</li>
    <li><strong>Single-threaded event loop</strong> - Manages all asynchronous operations</li>
    <li><strong>NPM package manager</strong> - World''s largest software registry</li>
    <li><strong>CommonJS modules</strong> - Module system for organizing code</li>
</ul>

<h3>💻 Basic HTTP Server Example:</h3>
<pre><code>const http = require(''http'');

const server = http.createServer((req, res) => {
    res.writeHead(200, {''Content-Type'': ''text/plain''});
    res.end(''Hello World from Node.js!'');
});

server.listen(3000, () => {
    console.log(''Server running on port 3000'');
});</code></pre>

<h3>🔄 Event Loop Phases:</h3>
<ol>
    <li><strong>Timer Phase</strong> - Executes setTimeout() and setInterval() callbacks</li>
    <li><strong>Pending Callbacks</strong> - Executes I/O callbacks deferred to the next loop iteration</li>
    <li><strong>Poll Phase</strong> - Fetches new I/O events and executes I/O related callbacks</li>
    <li><strong>Check Phase</strong> - Executes setImmediate() callbacks</li>
    <li><strong>Close Callbacks</strong> - Executes close event callbacks</li>
</ol>

<h3>🎯 Learning Objectives:</h3>
<ul>
    <li>Understand Node.js architecture and V8 engine</li>
    <li>Master the event loop and asynchronous programming</li>
    <li>Create basic HTTP servers and handle requests</li>
    <li>Work with modules and NPM packages</li>
</ul>', 1, 'BEGINNER', 'THEORY', 1, true, NOW(), NOW()),

-- Topic 2: Asynchronous Programming
('Asynchronous Programming: Callbacks, Promises, Async/Await', 'Master asynchronous patterns in Node.js', 
'<h2>⚡ Asynchronous Programming Mastery</h2>
<p>Learn the evolution of asynchronous programming in Node.js from callbacks to modern async/await.</p>

<h3>📞 Callbacks (Traditional Approach):</h3>
<pre><code>const fs = require(''fs'');

// Callback pattern
fs.readFile(''data.txt'', ''utf8'', (err, data) => {
    if (err) {
        console.error(''Error reading file:'', err);
        return;
    }
    console.log(''File content:'', data);
});</code></pre>

<h3>🤝 Promises (ES6):</h3>
<pre><code>const fs = require(''fs'').promises;

// Promise pattern
fs.readFile(''data.txt'', ''utf8'')
    .then(data => {
        console.log(''File content:'', data);
    })
    .catch(err => {
        console.error(''Error reading file:'', err);
    });</code></pre>

<h3>🎯 Async/Await (ES2017):</h3>
<pre><code>const fs = require(''fs'').promises;

async function readFileAsync() {
    try {
        const data = await fs.readFile(''data.txt'', ''utf8'');
        console.log(''File content:'', data);
    } catch (err) {
        console.error(''Error reading file:'', err);
    }
}

readFileAsync();</code></pre>

<h3>🔄 Promise Utilities:</h3>
<ul>
    <li><strong>Promise.all()</strong> - Wait for all promises to resolve</li>
    <li><strong>Promise.race()</strong> - Wait for first promise to resolve</li>
    <li><strong>Promise.allSettled()</strong> - Wait for all promises to settle</li>
    <li><strong>Promise.any()</strong> - Wait for first successful resolution</li>
</ul>', 1, 'INTERMEDIATE', 'CODE_EXAMPLE', 2, true, NOW(), NOW()),

-- Topic 3: Express.js Framework
('Express.js Framework and Middleware', 'Building web applications with Express.js', 
'<h2>🌐 Express.js Framework Mastery</h2>
<p>Express.js is the most popular Node.js web framework, providing robust features for web and mobile applications.</p>

<h3>🚀 Basic Express App:</h3>
<pre><code>const express = require(''express'');
const app = express();

// Middleware for parsing JSON
app.use(express.json());

// Basic route
app.get(''/'', (req, res) => {
    res.json({ message: ''Hello Express!'' });
});

// Route with parameters
app.get(''/users/:id'', (req, res) => {
    const userId = req.params.id;
    res.json({ userId, message: `User ${userId} profile` });
});

// POST route
app.post(''/users'', (req, res) => {
    const userData = req.body;
    res.status(201).json({ 
        message: ''User created'', 
        user: userData 
    });
});

app.listen(3000, () => {
    console.log(''Express server running on port 3000'');
});</code></pre>

<h3>🔧 Middleware Types:</h3>
<ul>
    <li><strong>Application-level middleware</strong> - app.use()</li>
    <li><strong>Router-level middleware</strong> - router.use()</li>
    <li><strong>Error-handling middleware</strong> - 4 parameters</li>
    <li><strong>Built-in middleware</strong> - express.static(), express.json()</li>
    <li><strong>Third-party middleware</strong> - cors, helmet, morgan</li>
</ul>

<h3>🛡️ Custom Middleware Example:</h3>
<pre><code>// Logging middleware
const logger = (req, res, next) => {
    console.log(`${req.method} ${req.path} - ${new Date().toISOString()}`);
    next();
};

// Authentication middleware
const authenticate = (req, res, next) => {
    const token = req.headers.authorization;
    if (!token) {
        return res.status(401).json({ error: ''No token provided'' });
    }
    // Verify token logic here
    next();
};

app.use(logger);
app.use(''/api'', authenticate);</code></pre>', 1, 'INTERMEDIATE', 'PRACTICAL', 3, true, NOW(), NOW()),

-- Topic 4: File System and Streams
(4, 'File I/O and Streams', 'Working with files and streams in Node.js', 
'<h2>📁 File System and Streams</h2>
<p>Master file operations and streaming data in Node.js for efficient I/O operations.</p>

<h3>📖 Reading Files:</h3>
<pre><code>const fs = require(''fs'');
const path = require(''path'');

// Synchronous (blocking)
try {
    const data = fs.readFileSync(''data.txt'', ''utf8'');
    console.log(data);
} catch (err) {
    console.error(err);
}

// Asynchronous (non-blocking)
fs.readFile(''data.txt'', ''utf8'', (err, data) => {
    if (err) throw err;
    console.log(data);
});

// Promise-based
const fsPromises = require(''fs'').promises;
async function readFileAsync() {
    try {
        const data = await fsPromises.readFile(''data.txt'', ''utf8'');
        console.log(data);
    } catch (err) {
        console.error(err);
    }
}</code></pre>

<h3>🌊 Streams:</h3>
<pre><code>const fs = require(''fs'');

// Readable stream
const readStream = fs.createReadStream(''large-file.txt'');
readStream.on(''data'', (chunk) => {
    console.log(`Received ${chunk.length} bytes`);
});

// Writable stream
const writeStream = fs.createWriteStream(''output.txt'');
writeStream.write(''Hello '');
writeStream.write(''World'');
writeStream.end();

// Pipe streams
const readStream2 = fs.createReadStream(''input.txt'');
const writeStream2 = fs.createWriteStream(''output.txt'');
readStream2.pipe(writeStream2);</code></pre>

<h3>🔄 Stream Types:</h3>
<ul>
    <li><strong>Readable</strong> - Data source (fs.createReadStream)</li>
    <li><strong>Writable</strong> - Data destination (fs.createWriteStream)</li>
    <li><strong>Duplex</strong> - Both readable and writable</li>
    <li><strong>Transform</strong> - Modify data as it passes through</li>
</ul>', 1, 'INTERMEDIATE', 'THEORY', 4, true, NOW(), NOW()),

-- Topic 5: Database Integration
(5, 'Database Integration: MongoDB and PostgreSQL', 'Connecting Node.js with databases', 
'<h2>🗄️ Database Integration</h2>
<p>Learn to connect Node.js applications with popular databases like MongoDB and PostgreSQL.</p>

<h3>🍃 MongoDB with Mongoose:</h3>
<pre><code>const mongoose = require(''mongoose'');

// Connect to MongoDB
mongoose.connect(''mongodb://localhost:27017/myapp'', {
    useNewUrlParser: true,
    useUnifiedTopology: true
});

// Define schema
const userSchema = new mongoose.Schema({
    name: { type: String, required: true },
    email: { type: String, required: true, unique: true },
    age: { type: Number, min: 0 },
    createdAt: { type: Date, default: Date.now }
});

// Create model
const User = mongoose.model(''User'', userSchema);

// CRUD operations
async function createUser() {
    const user = new User({
        name: ''John Doe'',
        email: ''john@example.com'',
        age: 30
    });
    
    try {
        const savedUser = await user.save();
        console.log(''User created:'', savedUser);
    } catch (err) {
        console.error(''Error:'', err);
    }
}</code></pre>

<h3>🐘 PostgreSQL with pg:</h3>
<pre><code>const { Pool } = require(''pg'');

// Database connection pool
const pool = new Pool({
    user: ''username'',
    host: ''localhost'',
    database: ''myapp'',
    password: ''password'',
    port: 5432,
});

// Query function
async function getUsers() {
    try {
        const result = await pool.query(''SELECT * FROM users'');
        return result.rows;
    } catch (err) {
        console.error(''Database error:'', err);
        throw err;
    }
}

// Parameterized query
async function getUserById(id) {
    try {
        const result = await pool.query(
            ''SELECT * FROM users WHERE id = $1'', 
            [id]
        );
        return result.rows[0];
    } catch (err) {
        console.error(''Database error:'', err);
        throw err;
    }
}</code></pre>', 1, 'ADVANCED', 'THEORY', 5, true, NOW(), NOW());

-- Reset sequence for topics
SELECT setval('topics_id_seq', (SELECT MAX(id) FROM topics));

-- Reset sequence for learning_modules  
SELECT setval('learning_modules_id_seq', (SELECT MAX(id) FROM learning_modules));