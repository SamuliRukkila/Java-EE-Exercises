package com.springmvc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * When exception-status "NOT_FOUND" is thrown from the resources,
 * we can specify the Response Status for a specific exception 
 * with the annotation @ResponseStatus. Exception-class extends
 * global Exception -class which easily handles rest.
 * 
 * @author samuli
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {
  
  private static final long serialVersionUID = 1L;
  
  public ResourceNotFoundException(String message){
    super(message);
  }
  
}
