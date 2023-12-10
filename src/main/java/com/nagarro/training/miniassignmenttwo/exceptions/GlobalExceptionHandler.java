package com.nagarro.training.miniassignmenttwo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.nagarro.training.miniassignmenttwo.model.ErrorResponse;

/**
 * @author harshraj01
 * Global Exception handler class to map all the exceptions occuring 
 * and return as a ErrorResponse as a result
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	//handles all the validation exception
	@ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
	
	
	//handle all the exceptions
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
	
	
	//handles illegal argument exception
	@ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(Exception e) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
	
	
	//handles the error when an unknown URI is entered which has no handler present in the controller
	@ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Resource not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
	
	
	//handles any request timeout error occured while fetching the data from web client
	@ExceptionHandler(AsyncRequestTimeoutException.class)
    public ResponseEntity<ErrorResponse> handleTimeoutException(AsyncRequestTimeoutException e) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.REQUEST_TIMEOUT.value(), "Timeout Occured while fetching data from API");
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(errorResponse);
    }
}
