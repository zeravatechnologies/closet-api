package com.zerava.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@GetMapping("/")
	public String home() {
		return "Hello, Closet API is Running!";
	}
	
	@GetMapping("/hello")
	public String hello() {
		return "Hello, Welcome Dhiraj.";
	}

}
