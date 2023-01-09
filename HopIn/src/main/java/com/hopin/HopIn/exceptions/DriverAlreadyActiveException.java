package com.hopin.HopIn.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Shifth already ongoing!") 
public class DriverAlreadyActiveException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7226488213771312390L;

}
