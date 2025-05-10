package com.example.ecom.exception;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.ecom.auth.payload.response.Result;
import com.example.ecom.exception.token.RefreshTokenException;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Result> handleValidationErrors(MethodArgumentNotValidException ex) {
    String errorMsg = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.joining(", "));
    logger.error("Validation error: {}", errorMsg);
    return ResponseEntity.badRequest().body(
        new Result(false, errorMsg, null));
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<Result> handleRuntime(RuntimeException ex) {
    logger.error("Runtime error: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new Result(false, ex.getMessage(), null));
  }

 
  @ExceptionHandler(RefreshTokenException.class)
  public ResponseEntity<Result> handleTokenRefreshException(RefreshTokenException ex) {
    logger.error("Token refresh error: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(new Result(false, ex.getMessage(), null));
  }
  
  @ExceptionHandler(IllegalArgumentException.class)
public ResponseEntity<Result> handleIllegalArgument(IllegalArgumentException ex) {
    logger.error("Illegal argument error: {}", ex.getMessage());
    return ResponseEntity.badRequest()
        .body(new Result(false, ex.getMessage(), null));
}

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Result> handleOtherExceptions(Exception ex) {
    logger.error("Unexpected error: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new Result(false, "An unexpected error occurred", null));
  }
}
