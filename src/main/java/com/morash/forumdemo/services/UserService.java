/**
 * 
 */
package com.morash.forumdemo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
