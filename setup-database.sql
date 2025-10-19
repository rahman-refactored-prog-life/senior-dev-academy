-- PostgreSQL Database Setup for FAANG Senior Developer Mastery Portal
-- Run this script to set up the database and schema

-- Connect to PostgreSQL as superuser (postgres)
-- psql -U postgres -h localhost

-- Create the database if it doesn't exist
CREATE DATABASE devacademykiro;

-- Connect to the devacademykiro database
\c devacademykiro;

-- Create a user for the application (optional, for better security)
-- CREATE USER faang_user WITH PASSWORD 'secure_password_123';

-- Verify setup
SELECT current_database(), current_user;

-- Show existing tables (should be empty initially)
\dt

COMMENT ON DATABASE devacademykiro IS 'FAANG Senior Developer Mastery Portal Database';

-- Exit
\q