/**
 * 
 */
package com.morash.forumdemo.services;

import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.morash.forumdemo.data.entity.User;
import com.morash.forumdemo.data.repository.UserRepository;
import com.morash.forumdemo.exceptions.UserNotFoundException;

/**
 * @author Michael
 *
 */
@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private HttpServletRequest request;
	
	public void register(User user) {
		String password = user.getPassword();
		createUser(user);
		try {
			request.login(user.getUsername(), password);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	public void createUser(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		userRepo.save(user);
	}
	
	public User findUser(Integer id) throws UserNotFoundException {
		Optional<User> user = userRepo.findById(id);
		
		if (!user.isPresent()) {
			throw new UserNotFoundException();
		}
		
		return user.get();
	}
}
