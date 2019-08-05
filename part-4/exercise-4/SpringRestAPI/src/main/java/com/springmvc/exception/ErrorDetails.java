package com.springmvc.exception;

import java.util.Date;

/**
 * Framework independent response structure for this application.
 * It's creates a specific error message with timestamp, error-message
 * and details which is send back to the HTTP-caller.
 * 
 * @author samuli
 *
 */
public class ErrorDetails {

  private Date timestamp;
  private String message;
  private String details;
  
  
  /* CONSTRUCTOR */
  
  public ErrorDetails(Date ts, String msg, String details) {
    super();
    this.timestamp = ts;
    this.message = msg;
    this.details = details;
  }
  
  
  /* GETTERS FOR THE ATTRIBUTES */
  
  public Date getTimestamp() {
    return timestamp;
  }
  
  public String getMessage() {
      return message;
  }
  
  public String getDetails() {
      return details;
  }
  
}
