package com.zerava.closetapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zerava.closetapi.model.User;
import com.zerava.closetapi.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService; 
	
	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody User user){
		User savedUser = userService.registerUser(user);
		return ResponseEntity.ok(savedUser);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers(){
		return ResponseEntity.ok(userService.findAllUser());
	}
	
	@GetMapping("{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id){
		return userService.getUserById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
	
	public ResponseEntity<Void> deleteUserByid(@PathVariable Long id){
		userService.deleteUserById(id);
		return ResponseEntity.noContent().build();
	}
	
	
	
	
	
	
	
	
	

}
