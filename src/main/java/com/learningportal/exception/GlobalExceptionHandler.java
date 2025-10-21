package com.learningportal.exception;

import com.learningportal.dto.ApiErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEntityNotFoundException(
            EntityNotFoundException ex, WebRequest request) {
        
        logger.warn("Entity not found: {} - Request: {}", ex.getMessage(), request.getDescription(false));
        
        String path = request.getDescription(false).replace("uri=", "");
        ApiErrorResponse errorResponse = ApiErrorResponse.notFound(ex.getMessage(), path);
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, WebRequest request) {
        
        logger.warn("Validation failed: {} - Request: {}", ex.getMessage(), request.getDescription(false));
        
        String path = request.getDescription(false).replace("uri=", "");
        
        Map<String, List<String>> fieldErrors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .collect(Collectors.groupingBy(
                FieldError::getField,
                Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
            ));
        
        ApiErrorResponse errorResponse = ApiErrorResponse.validationError(path, fieldErrors);
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolationException(
            ConstraintViolationException ex, WebRequest request) {
        
        logger.warn("Constraint violation: {} - Request: {}", ex.getMessage(), request.getDescription(false));
        
        String path = request.getDescription(false).replace("uri=", "");
        
        Map<String, List<String>> fieldErrors = ex.getConstraintViolations()
            .stream()
            .collect(Collectors.groupingBy(
                violation -> violation.getPropertyPath().toString(),
                Collectors.mapping(violation -> violation.getMessage(), Collectors.toList())
            ));
        
        ApiErrorResponse errorResponse = ApiErrorResponse.validationError(path, fieldErrors);
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleTypeMismatchException(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        
        logger.warn("Type mismatch: {} - Request: {}", ex.getMessage(), request.getDescription(false));
        
        String path = request.getDescription(false).replace("uri=", "");
        String message = String.format("Invalid value '%s' for parameter '%s'. Expected type: %s", 
            ex.getValue(), ex.getName(), ex.getRequiredType().getSimpleName());
        
        ApiErrorResponse errorResponse = ApiErrorResponse.badRequest(message, path);
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        
        logger.warn("Invalid argument: {} - Request: {}", ex.getMessage(), request.getDescription(false));
        
        String path = request.getDescription(false).replace("uri=", "");
        ApiErrorResponse errorResponse = ApiErrorResponse.badRequest(ex.getMessage(), path);
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(
            Exception ex, WebRequest request) {
        
        logger.error("Unexpected error: {} - Request: {}", ex.getMessage(), request.getDescription(false), ex);
        
        String path = request.getDescription(false).replace("uri=", "");
        ApiErrorResponse errorResponse = ApiErrorResponse.internalServerError(
            "An unexpected error occurred. Please try again later.", path);
        
        // Add trace ID for debugging in development
        errorResponse.setTraceId(UUID.randomUUID().toString());
        
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}