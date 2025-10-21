# API Documentation

## Overview

The Learning Portal API provides comprehensive access to learning modules, interview questions, progress tracking, and system monitoring capabilities. The API follows RESTful design principles and provides extensive documentation through Swagger/OpenAPI.

## Quick Start

### Base URL
- **Development**: `http://localhost:8080`
- **Production**: `https://api.learningportal.com`

### Interactive Documentation
- **Swagger UI**: `{BASE_URL}/swagger-ui/index.html`
- **OpenAPI Spec**: `{BASE_URL}/v3/api-docs`

### Authentication
Most endpoints require authentication. Obtain an access token through the authentication endpoints and include it in the `Authorization` header:

```http
Authorization: Bearer <your-access-token>
```

## API Categories

### 1. Learning Content APIs

#### Learning Modules
- **Base Path**: `/modules`
- **Description**: Access to comprehensive learning modules covering Java, Node.js, React, Data Structures, Algorithms, and more
- **Features**: Module listing, detailed content, progress tracking, interactive examples

#### Java Content
- **Base Path**: `/java`
- **Description**: Complete Java curriculum from fundamentals to advanced topics
- **Features**: 500+ interview questions, code examples, best practices

#### Node.js Content
- **Base Path**: `/nodejs`
- **Description**: Comprehensive Node.js mastery curriculum
- **Features**: 700+ interview questions, project-based learning, production patterns

#### Database Content
- **Base Path**: `/databases`
- **Description**: SQL and NoSQL database content and interview preparation
- **Features**: Query optimization, schema design, distributed systems

#### System Design
- **Base Path**: `/system-design`
- **Description**: System design patterns and interview preparation
- **Features**: Scalability patterns, real-world case studies, architecture examples

### 2. Interview Preparation APIs

#### Behavioral Interviews
- **Base Path**: `/behavioral`
- **Description**: Behavioral interview preparation including Amazon Leadership Principles
- **Features**: STAR method framework, leadership scenarios, practice questions

#### Cheatsheets & Summaries
- **Base Path**: `/cheatsheets`
- **Description**: Quick reference guides and topic summaries
- **Features**: Condensed knowledge, quick review materials, exam preparation

### 3. System Monitoring APIs

#### Health Monitoring
- **Base Path**: `/api/health`
- **Description**: Comprehensive health monitoring and alerting system
- **Features**: System health checks, performance metrics, alerting configuration

#### Performance Monitoring
- **Base Path**: `/api/performance`
- **Description**: Performance metrics and monitoring endpoints
- **Features**: Response time tracking, throughput metrics, resource utilization

#### Database Performance
- **Base Path**: `/api/database/performance`
- **Description**: Database performance monitoring and optimization
- **Features**: Query performance, connection pool metrics, optimization recommendations

#### Cache Management
- **Base Path**: `/api/cache`
- **Description**: Cache monitoring and management endpoints
- **Features**: Cache statistics, hit/miss ratios, cache invalidation

### 4. System Administration APIs

#### Application Startup
- **Base Path**: `/api/startup`
- **Description**: Application startup validation and health monitoring
- **Features**: Startup sequence validation, dependency checks, configuration validation

#### System Status
- **Base Path**: `/system`
- **Description**: System monitoring and status endpoints
- **Features**: Overall system health, component status, diagnostic information

#### Module Verification
- **Base Path**: `/verification`
- **Description**: Verify all learning modules and components exist
- **Features**: Content validation, integrity checks, completeness verification

## Common Response Formats

### Success Response
```json
{
  "success": true,
  "data": {
    // Response data
  },
  "timestamp": "2024-01-01T12:00:00Z",
  "requestId": "uuid-string"
}
```

### Error Response
```json
{
  "success": false,
  "error": {
    "code": "ERROR_CODE",
    "message": "Human-readable error message",
    "details": "Additional error details",
    "timestamp": "2024-01-01T12:00:00Z",
    "requestId": "uuid-string"
  }
}
```

### Paginated Response
```json
{
  "success": true,
  "data": {
    "content": [
      // Array of items
    ],
    "page": {
      "number": 0,
      "size": 20,
      "totalElements": 100,
      "totalPages": 5
    }
  }
}
```

## HTTP Status Codes

| Code | Description | Usage |
|------|-------------|-------|
| 200 | OK | Successful GET, PUT, PATCH requests |
| 201 | Created | Successful POST requests |
| 204 | No Content | Successful DELETE requests |
| 400 | Bad Request | Invalid request parameters or body |
| 401 | Unauthorized | Missing or invalid authentication |
| 403 | Forbidden | Insufficient permissions |
| 404 | Not Found | Resource not found |
| 409 | Conflict | Resource conflict (e.g., duplicate creation) |
| 422 | Unprocessable Entity | Validation errors |
| 429 | Too Many Requests | Rate limit exceeded |
| 500 | Internal Server Error | Server-side errors |
| 503 | Service Unavailable | Service temporarily unavailable |

## Rate Limiting

API requests are limited to prevent abuse and ensure fair usage:

- **Authenticated Users**: 1000 requests per hour
- **Anonymous Users**: 100 requests per hour
- **Burst Limit**: 50 requests per minute

Rate limit headers are included in all responses:
```http
X-RateLimit-Limit: 1000
X-RateLimit-Remaining: 999
X-RateLimit-Reset: 1640995200
```

## Error Handling

### Validation Errors
```json
{
  "success": false,
  "error": {
    "code": "VALIDATION_ERROR",
    "message": "Request validation failed",
    "details": {
      "field1": ["Error message 1", "Error message 2"],
      "field2": ["Error message 3"]
    }
  }
}
```

### Authentication Errors
```json
{
  "success": false,
  "error": {
    "code": "AUTHENTICATION_REQUIRED",
    "message": "Authentication required to access this resource",
    "details": "Please provide a valid access token in the Authorization header"
  }
}
```

### Authorization Errors
```json
{
  "success": false,
  "error": {
    "code": "INSUFFICIENT_PERMISSIONS",
    "message": "Insufficient permissions to access this resource",
    "details": "Required role: ADMIN, Current role: USER"
  }
}
```

## Request/Response Examples

### Get Learning Modules
```http
GET /modules?page=0&size=10&category=java

Response:
{
  "success": true,
  "data": {
    "content": [
      {
        "id": 1,
        "title": "Java Fundamentals",
        "description": "Complete Java fundamentals course",
        "category": "java",
        "difficulty": "beginner",
        "estimatedHours": 40,
        "topics": [
          {
            "id": 1,
            "title": "Variables and Data Types",
            "completed": true
          }
        ]
      }
    ],
    "page": {
      "number": 0,
      "size": 10,
      "totalElements": 25,
      "totalPages": 3
    }
  }
}
```

### Get System Health
```http
GET /api/health/status

Response:
{
  "success": true,
  "data": {
    "status": "UP",
    "components": {
      "database": {
        "status": "UP",
        "details": {
          "connectionPool": "healthy",
          "responseTime": "5ms"
        }
      },
      "cache": {
        "status": "UP",
        "details": {
          "hitRatio": "85%",
          "memoryUsage": "45%"
        }
      }
    },
    "timestamp": "2024-01-01T12:00:00Z"
  }
}
```

## SDK and Client Libraries

### JavaScript/TypeScript
```javascript
import { LearningPortalAPI } from '@learningportal/api-client';

const api = new LearningPortalAPI({
  baseURL: 'https://api.learningportal.com',
  apiKey: 'your-api-key'
});

// Get learning modules
const modules = await api.modules.list({ category: 'java' });

// Get system health
const health = await api.health.getStatus();
```

### Java
```java
import com.learningportal.client.LearningPortalClient;

LearningPortalClient client = LearningPortalClient.builder()
    .baseUrl("https://api.learningportal.com")
    .apiKey("your-api-key")
    .build();

// Get learning modules
List<Module> modules = client.modules().list(
    ModuleListRequest.builder()
        .category("java")
        .build()
);
```

## Testing

### Postman Collection
A comprehensive Postman collection is available for testing all API endpoints:
- **Collection URL**: `{BASE_URL}/postman/collection.json`
- **Environment**: `{BASE_URL}/postman/environment.json`

### cURL Examples
```bash
# Get learning modules
curl -X GET "http://localhost:8080/modules" \
  -H "Authorization: Bearer your-token" \
  -H "Content-Type: application/json"

# Get system health
curl -X GET "http://localhost:8080/api/health/status" \
  -H "Content-Type: application/json"
```

## Versioning

The API uses URL-based versioning:
- **Current Version**: v1 (default, no version prefix required)
- **Future Versions**: `/v2/modules`, `/v3/modules`, etc.

Version-specific documentation is available at:
- **v1**: `{BASE_URL}/swagger-ui/index.html`
- **v2**: `{BASE_URL}/v2/swagger-ui/index.html`

## Support

For API support and questions:
- **Documentation**: This documentation and Swagger UI
- **Issues**: GitHub repository issues
- **Email**: api-support@learningportal.com
- **Response Time**: 24-48 hours for non-critical issues

## Changelog

### Version 1.0.0 (Current)
- Initial API release
- Complete learning content endpoints
- System monitoring and health checks
- Authentication and authorization
- Comprehensive documentation

### Upcoming Features
- Real-time notifications via WebSocket
- Advanced analytics and reporting
- Mobile-optimized endpoints
- GraphQL API support