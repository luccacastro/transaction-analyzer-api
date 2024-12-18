package com.example.transactionanalyzer.exceptions;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static com.example.transactionanalyzer.utils.ErrorMessages.INVALID_DATE_FORMAT_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Map<String, Object> createErrorResponse(String message, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", Instant.now());
        response.put("error", message);
        response.put("status", status.value());
        return response;
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        return new ResponseEntity<>(createErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InvalidDateRangeException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidDateRangeException(InvalidDateRangeException ex) {
        return new ResponseEntity<>(createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    private boolean isLocalDateException(ConversionFailedException ex) {
        return ex.getTargetType().getType().equals(java.time.LocalDate.class);
    }

    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<Map<String, Object>> handleConversionFailedException(ConversionFailedException ex) {
        String message = isLocalDateException(ex) ? INVALID_DATE_FORMAT_ERROR.formatted("yyyy-mm-dd") : "Invalid input provided. Conversion failed.";
        return new ResponseEntity<>(createErrorResponse(message, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnableToCalculateException.class)
    public ResponseEntity<Map<String, Object>> handleUnableToCalculateException(UnableToCalculateException ex) {
        return new ResponseEntity<>(createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

}
