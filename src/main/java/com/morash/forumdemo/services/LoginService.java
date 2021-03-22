package com.morash.forumdemo.services;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.morash.forumdemo.data.LoginInfo;
import com.morash.forumdemo.data.UserPrincipal;
import com.morash.forumdemo.data.constants.SessionKeyNames;
import com.morash.forumdemo.data.entity.User;
import com.morash.forumdemo.data.repository.UserRepository;
import com.morash.forumdemo.exceptions.UserNotLoggedInException;

@Service
public class LoginService implements UserDetailsService {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private HttpSession session;
	
	public User getCurrentUser() {
		// Returns user currently logged into the session
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication instanceof AnonymousAuthenticationToken) {
			return null;
		}
		
		Optional<User> user = userRepo.findUserWithUsername(authentication.getName());
		
		if (!user.isPresent()) {
			return null;
		}
		
		return user.get();
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
		return getCurrentUser() != null;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> user = userRepo.findUserWithUsername(username);
		
		if (!user.isPresent()) {
			throw new UsernameNotFoundException(username);
		}
		
		return new UserPrincipal(user.get());
	}
}
