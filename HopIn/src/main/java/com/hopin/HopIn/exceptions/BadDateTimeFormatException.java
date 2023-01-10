package com.hopin.HopIn.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Bad Date format, or future date is sent!") 
public class BadDateTimeFormatException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1932428948765472135L;

}
