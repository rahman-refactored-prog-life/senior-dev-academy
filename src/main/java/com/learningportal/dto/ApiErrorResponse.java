package com.learningportal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Standardized API error response format
 * Provides consistent error structure across all endpoints with proper HTTP status codes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {
    
    private int status;
    private String error;
    private String message;
    private String path;
    private String traceId;
    private Map<String, List<String>> fieldErrors;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    
    public ApiErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }
    
    public ApiErrorResponse(int status, String error, String message, String path) {
        this();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
    
    // Static factory methods for common error types
    public static ApiErrorResponse notFound(String message, String path) {
        return new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), "Not Found", message, path);
    }
    
    public static ApiErrorResponse badRequest(String message, String path) {
        return new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request", message, path);
    }
    
    public static ApiErrorResponse internalServerError(String message, String path) {
        return new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", message, path);
    }
    
    public static ApiErrorResponse validationError(String path, Map<String, List<String>> fieldErrors) {
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation Failed", "Invalid request data", path);
        response.setFieldErrors(fieldErrors);
        return response;
    }
    
    // Getters and setters
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    
    public String getTraceId() { return traceId; }
    public void setTraceId(String traceId) { this.traceId = traceId; }
    
    public Map<String, List<String>> getFieldErrors() { return fieldErrors; }
    public void setFieldErrors(Map<String, List<String>> fieldErrors) { this.fieldErrors = fieldErrors; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}