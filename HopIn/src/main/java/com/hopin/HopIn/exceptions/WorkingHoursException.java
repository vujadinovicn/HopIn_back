package com.hopin.HopIn.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Working Hours error!") 
public class WorkingHoursException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5228454815797773634L;

}
