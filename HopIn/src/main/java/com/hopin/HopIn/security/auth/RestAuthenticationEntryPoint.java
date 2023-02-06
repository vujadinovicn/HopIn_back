package com.hopin.HopIn.security.auth;

import java.io.IOException;
import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.hopin.HopIn.validations.ExceptionDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
    	System.out.println(authException);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
	
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintValidationException(ConstraintViolationException e) {
		// 400
		return new ResponseEntity<>(new ExceptionDTO(e.getMessage().split(": ")[1]), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler (value = {MethodArgumentNotValidException.class})
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		// 400
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
	
	@ExceptionHandler(MissingServletRequestParameterException.class) 
	protected ResponseEntity<Object> handleRequestParameterMising(MissingServletRequestParameterException e) {
		// 400
		return new ResponseEntity<>("Request parameter " + e.getParameterName() + " is missing!", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HttpMessageConversionException.class)
	protected ResponseEntity<Object> handleJSONParseException(HttpMessageConversionException e) {
		// 400
		return new ResponseEntity<>(new ExceptionDTO(e.getMessage().split(": ")[1]), HttpStatus.BAD_REQUEST);
	}
    
    @ExceptionHandler (value = {AccessDeniedException.class})
    public void commence(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        // 403
        setResponseError(response, HttpServletResponse.SC_FORBIDDEN, String.format("Access Denies: %s", accessDeniedException.getMessage()));
    }
    
    @ExceptionHandler (value = {NotFoundException.class})
    public void commence(HttpServletRequest request, HttpServletResponse response, NotFoundException notFoundException) throws IOException {
        // 404
        setResponseError(response, HttpServletResponse.SC_NOT_FOUND, String.format("Not found: %s", notFoundException.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
     protected ResponseEntity<Object> handleMethodMismatchException(MethodArgumentTypeMismatchException e) {
        return new ResponseEntity<>("Path parameter " + e.getParameter() + " has bad type!", HttpStatus.BAD_REQUEST);
    }
    
    private void setResponseError(HttpServletResponse response, int errorCode, String errorMessage) throws IOException{
        response.setStatus(errorCode);
        response.getWriter().write(errorMessage);
        response.getWriter().flush();
        response.getWriter().close();
    }
}

