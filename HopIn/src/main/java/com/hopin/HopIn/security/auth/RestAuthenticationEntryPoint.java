package com.hopin.HopIn.security.auth;

import java.io.IOException;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
    	System.out.println(authException);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
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

    @ExceptionHandler (value = {Exception.class})
    public void commence(HttpServletRequest request, HttpServletResponse response, Exception exception) throws IOException {
        // 403
        setResponseError(response, HttpServletResponse.SC_FORBIDDEN, String.format("Access Denies: %s", exception.getMessage()));
    }
    
    private void setResponseError(HttpServletResponse response, int errorCode, String errorMessage) throws IOException{
        response.setStatus(errorCode);
        response.getWriter().write(errorMessage);
        response.getWriter().flush();
        response.getWriter().close();
    }
}

