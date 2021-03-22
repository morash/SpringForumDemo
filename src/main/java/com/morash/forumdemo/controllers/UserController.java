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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.morash.forumdemo.data.LoginInfo;
import com.morash.forumdemo.data.constants.ErrorMessages;
import com.morash.forumdemo.data.constants.JspPaths;
import com.morash.forumdemo.data.constants.ModelKeyNames;
import com.morash.forumdemo.data.entity.User;
import com.morash.forumdemo.exceptions.UserNotFoundException;
import com.morash.forumdemo.services.LoginService;
import com.morash.forumdemo.services.UserService;

/**
 * @author Michael
 *
 */

@Controller
@RequestMapping(value = "/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private LoginService loginService;

	@GetMapping(value = "/create")
	public String serveCreateForm(Model model) {
		// Serves the new user page
		return JspPaths.USER_CREATE;
	}

	@PostMapping(value = "/create")
	public RedirectView create(Model model, User newUser) {
		// Creates a new user from form input
		userService.register(newUser);
		return new RedirectView("/");
	}

	@GetMapping(value = "/login")
	public String serveLoginPage(Model model) {
		// Serves the login page
		return JspPaths.USER_LOGIN;
	}

	@GetMapping(value = "/view/{userId}")
	public String view(Model model, @PathVariable(name = "userId") Integer id) throws UserNotFoundException {
		// Populate model for the user view
		// Throws 404 when user id isn't recognized
		User user = userService.findUser(id);
		model.addAttribute(ModelKeyNames.USER, user);

		return JspPaths.USER_VIEW;
	}
}
