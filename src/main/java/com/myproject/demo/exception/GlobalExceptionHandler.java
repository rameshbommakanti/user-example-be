package com.myproject.demo.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.myproject.demo.user.dto.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	protected ResponseEntity<ErrorResponse> handleConflict(RuntimeException ex, WebRequest request) {
		return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value()),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { UserNotFoundException.class })
	protected ResponseEntity<ErrorResponse> handleConflict(UserNotFoundException ex, WebRequest request) {
		return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value()),
				HttpStatus.NOT_FOUND);
	}
}
