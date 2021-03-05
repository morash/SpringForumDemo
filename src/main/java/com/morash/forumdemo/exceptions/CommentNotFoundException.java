package com.morash.forumdemo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CommentNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3481801425488535130L;

}
