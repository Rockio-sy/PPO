package org.academo.academo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.academo.academo.Exception.CustomException;
import org.academo.academo.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex, HttpServletRequest request) {
        switch (ex.getLogLevel()) {
            case "ERROR" -> logger.error("{}", ex.getMessage(), ex);
            case "WARN" -> logger.warn("{}", ex.getMessage(), ex.getCause());
            case "INFO" -> logger.info("{}", ex.getMessage());
            default -> logger.error("Undefined error occurred {}", ex.getMessage(), ex);
        }
        ErrorResponse res = new ErrorResponse(ex.getMessage(), ex.getHttpStatus());
        return new ResponseEntity<>(res, res.status());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logger.warn("Validation exception in {}", ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
