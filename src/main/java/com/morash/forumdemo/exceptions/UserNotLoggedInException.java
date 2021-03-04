/**
 * 
 */
package com.morash.forumdemo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Michael
 *
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserNotLoggedInException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4302059530039035833L;

}
