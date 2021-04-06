/**
 * 
 */
package com.morash.forumdemo.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

/**
 * @author Michael
 *
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
			.antMatchers("/board/create", "/post/create/*", "/comment/create/**", "/user/current").authenticated()
			.anyRequest().permitAll()
		.and().exceptionHandling()
			.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
		.and().formLogin()
			.loginProcessingUrl("/user/login")
			.failureHandler((request, response, error) -> {
				response.sendError(HttpStatus.NOT_ACCEPTABLE.value());
			})
			.successHandler((request, response, auth) -> {
				response.setStatus(HttpStatus.NO_CONTENT.value());
			})
		.and().logout()
			.logoutUrl("/user/logout");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
