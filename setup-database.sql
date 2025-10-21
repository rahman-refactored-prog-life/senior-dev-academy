-- Database setup for Comprehensive Developer Portal
-- PostgreSQL 18 compatible

-- Create database if it doesn't exist
-- Note: This needs to be run as postgres user

-- Create the database
CREATE DATABASE devacademykirostart
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE devacademykirostart TO postgres;

-- Connect to the database and create schema
\c devacademykirostart;

-- Create schema for our application
CREATE SCHEMA IF NOT EXISTS public;

-- Grant usage on schema
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO public;

-- Show database info
SELECT 'Database devacademykirostart created successfully!' as status;