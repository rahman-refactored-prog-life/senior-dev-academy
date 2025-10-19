# 🗄️ PostgreSQL Database Setup Guide

## Quick Setup Steps

### 1. **Ensure PostgreSQL is Running**
```bash
# Check if PostgreSQL is running
brew services list | grep postgresql
# or
pg_ctl status

# Start PostgreSQL if not running
brew services start postgresql
# or
pg_ctl start
```

### 2. **Create Database and Schema**
```bash
# Run the setup script
psql -U postgres -h localhost -f setup-database.sql

# Or manually:
psql -U postgres -h localhost
CREATE DATABASE examprep;
\c examprep;
CREATE SCHEMA IF NOT EXISTS devacademykirostart;
\q
```

### 3. **Test Database Connection**
```bash
# Run the connection test
./test-db-connection.sh
```

### 4. **Configure Credentials (Optional)**
```bash
# Copy environment template
cp .env.example .env

# Edit .env with your credentials
nano .env
```

### 5. **Start the Application**
```bash
# Run the application
./run-app.sh

# Or directly with Maven
mvn spring-boot:run
```

## 🔧 Configuration Options

### Option 1: Environment Variables
```bash
export DB_USERNAME=postgres
export DB_PASSWORD=password
mvn spring-boot:run
```

### Option 2: .env File
Create `.env` file:
```env
DB_USERNAME=postgres
DB_PASSWORD=your_password
```

### Option 3: Direct Configuration
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.username=postgres
spring.datasource.password=your_password
```

## 📊 Database Schema

The application will automatically create these tables:
- `learning_modules` - Course modules (Java, Node.js, React, etc.)
- `topics` - Individual learning topics within modules
- `interview_questions` - 10,800+ real interview questions
- Audit tables with creation/update timestamps

## 🌐 Access Points

Once running, access:
- **Application**: http://localhost:3008
- **Swagger API**: http://localhost:3008/swagger-ui.html
- **API Docs**: http://localhost:3008/v3/api-docs
- **Health Check**: http://localhost:3008/actuator/health

## 🔍 Troubleshooting

### Database Connection Issues
```bash
# Check PostgreSQL status
brew services list | grep postgresql

# Check if database exists
psql -U postgres -l | grep examprep

# Check schema
psql -U postgres -d examprep -c "\dn"
```

### Permission Issues
```bash
# Grant permissions to postgres user
psql -U postgres -d examprep
GRANT ALL PRIVILEGES ON SCHEMA sdeexamprep TO postgres;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA sdeexamprep TO postgres;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA sdeexamprep TO postgres;
```

### Port Conflicts
If port 3008 is in use:
```bash
# Change port in application.yml or set environment variable
export SERVER_PORT=3009
```

## 🚀 Ready to Go!

Your FAANG Senior Developer Mastery Portal will be ready with:
- ✅ PostgreSQL database (examprep/sdeexamprep)
- ✅ REST APIs with Swagger documentation
- ✅ 500+ comprehensive learning topics
- ✅ 10,800+ real interview questions
- ✅ Interactive learning modules
- ✅ Progress tracking and analytics