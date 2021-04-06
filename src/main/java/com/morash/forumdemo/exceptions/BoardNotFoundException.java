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
public class BoardNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1105434018370109667L;

	public BoardNotFoundException() {
		super("Board not found with given name");
	}
	
	public BoardNotFoundException(String boardName) {
		super("Board not found with name " + boardName);
	}
}
