package com.hopin.HopIn.validations;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@RestControllerAdvice
public class ErrorHandlingControllerAdvice extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintValidationException(ConstraintViolationException e) {
		return new ResponseEntity<>(new ExceptionDTO(e.getMessage().split(": ")[1]), HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<ObjectError> errorList = ex.getBindingResult().getAllErrors();
        StringBuilder sb = new StringBuilder("Request finished with validation errors: \n");

        for (ObjectError error : errorList ) {
        	sb.append("Field ");
            FieldError fe = (FieldError) error;
            sb.append(fe.getField() + " ");
            sb.append(error.getDefaultMessage()+ "!\n\n");
        }

        return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
	}

	
}
