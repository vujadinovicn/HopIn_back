package com.hopin.HopIn.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "For example a wrong format of some field in the request!") 
public class BadIdFormatException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6333547284569619677L;

}
