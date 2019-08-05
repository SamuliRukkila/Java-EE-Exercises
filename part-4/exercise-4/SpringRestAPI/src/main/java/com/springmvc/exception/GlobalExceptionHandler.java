package com.springmvc.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * To use the independent ErrorDetails -exception-class we need to create
 * a global exception handler with @ControllerAdvice -annotation.
 * 
 * This class handles exception specific and global exception in a single place.
 * @author samuli
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
  
  /**
   * When resource isn't found in a HTTP-query (for 
   * example trying to find book with non-existing ID).
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest req) {
    ErrorDetails errDetails = new ErrorDetails(new Date(), ex.getMessage(), req.getDescription(false));
    return new ResponseEntity<>(errDetails, HttpStatus.NOT_FOUND);
  }
  
  /**
   * When there's a normal internal server error (no connection/server error).
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest req) {
    ErrorDetails errDetails = new ErrorDetails(new Date(), ex.getMessage(), req.getDescription(false));
    return new ResponseEntity<>(errDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
