/**
 * 
 */
package com.morash.forumdemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.morash.forumdemo.data.LoginInfo;
import com.morash.forumdemo.data.constants.ErrorMessages;
import com.morash.forumdemo.data.constants.JspPaths;
import com.morash.forumdemo.data.constants.ModelKeyNames;
import com.morash.forumdemo.data.entity.User;
import com.morash.forumdemo.exceptions.UserNotFoundException;
import com.morash.forumdemo.exceptions.UserNotLoggedInException;
import com.morash.forumdemo.services.LoginService;
import com.morash.forumdemo.services.UserService;

/**
 * @author Michael
 *
 */

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private LoginService loginService;
	
	@GetMapping(value = "/current")
	public User currentUser() {
		return loginService.getCurrentUser();
	}

	@PostMapping(value = "/create")
	public RedirectView create(Model model, User newUser) {
		// Creates a new user from form input
		userService.register(newUser);
		return new RedirectView("/");
	}

	@GetMapping(value = "/view/{userId}")
	public User view(Model model, @PathVariable(name = "userId") Integer id) throws UserNotFoundException {
		// Populate model for the user view
		// Throws 404 when user id isn't recognized
		User user = userService.findUser(id);
		
		return user;
	}
}
