package com.zerava.closetapi.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins="https/localhost")
public class AuthController {
	@Autowired
	AuthenticationManager authManager;
	
	@PostMapping("/login")
	public String login(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword())
				);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return "Login successful for: " + loginRequest.getEmail();
		
	}
	
	static class LoginRequest{
		private String email;
		private String password; 
		
		public String getEmail() {return email;}
		public void setEmail(String email) {this.email = email;}
		public String getPassword() {return password;}
		public void setPassword(String password) {this.password = password;}
	}
}



