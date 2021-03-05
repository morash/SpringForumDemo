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
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6540678265890692201L;

}
