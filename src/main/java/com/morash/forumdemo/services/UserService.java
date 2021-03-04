/**
 * 
 */
package com.morash.forumdemo.services;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.morash.forumdemo.data.LoginInfo;
import com.morash.forumdemo.data.constants.SessionKeyNames;
import com.morash.forumdemo.data.entity.User;
import com.morash.forumdemo.data.repository.UserRepository;
import com.morash.forumdemo.exceptions.UserNotFoundException;
import com.morash.forumdemo.exceptions.UserNotLoggedInException;

/**
 * @author Michael
 *
 */
@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private HttpSession session;
	
	public User getCurrentUser() {
		// Returns user currently logged into the session
		return (User) session.getAttribute(SessionKeyNames.USER_KEY);
	}
	
	public void setCurrentUser(User user) {
		// Sets the sessions current user
		session.setAttribute(SessionKeyNames.USER_KEY, null);
	}
	
	public User requiredLogin() throws UserNotLoggedInException {
		// Returns user currently logged into the session
		// Throws a UserNotLoggedInException if no user is logged in
		if (!isLoggedIn()) {
			throw new UserNotLoggedInException();
		}
		
		return getCurrentUser();
	}
	
	public boolean isLoggedIn() {
		return getCurrentUser() == null;
	}
	
	public boolean attemptLogin(LoginInfo loginInfo) {
		// Attempts to login with the provided credentials and updates the current user if successful
		// returns boolean indicating success
		Optional<User> user = userRepo.findUserWithUsername(loginInfo.getUsername());
		
		if (user.isPresent()) {
			if (user.get().getPassword() == loginInfo.getPassword()) {
				setCurrentUser(user.get());
				return true;
			}
		}
		
		return false;
	}
	
	public void logout() {
		//Sets the current user to null
		setCurrentUser(null);
	}
	
	public void createUser(User user) {
		userRepo.save(user);
	}
	
	public User findUser(Integer id) throws UserNotFoundException {
		Optional<User> user = userRepo.findById(id);
		
		if (user.isEmpty()) {
			throw new UserNotFoundException();
		}
		
		return user.get();
	}
}
