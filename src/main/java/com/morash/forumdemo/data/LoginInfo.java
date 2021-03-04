package com.morash.forumdemo.data;

import org.apache.catalina.User;

public class LoginInfo {
	private String redirectOnSuccess = "/";
	private String username;
	private String password;
	
	public String getRedirectOnSuccess() {
		return redirectOnSuccess;
	}
	
	public void setRedirectOnSuccess(String redirect) {
		this.redirectOnSuccess = redirect;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
