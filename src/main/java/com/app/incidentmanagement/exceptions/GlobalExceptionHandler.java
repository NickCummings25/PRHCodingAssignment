package com.app.incidentmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> inputValidationError(MethodArgumentNotValidException error){
        String defaultMessage = Objects.requireNonNull(error.getBindingResult().getFieldError()).getDefaultMessage();
        logger.warn("Validation Error: {}", error.getMessage());
        return new ResponseEntity<>("Request Body Provided is not valid: " + defaultMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFoundError(NotFoundException error){
        logger.warn("Resource Not Found Error: {}", error.getMessage());
        return new ResponseEntity<>(error.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> runtimeError(RuntimeException error){
        logger.error("Runtime Error: ", error);
        return new ResponseEntity<>("An issue with the server occurred: " + error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> genericError(Exception error){
        logger.error("Unexpected Error: ", error);
        return new ResponseEntity<>("An unexpected error occurred: " + error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
