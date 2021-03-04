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
public class UserNotFoundException extends Exception {

}
