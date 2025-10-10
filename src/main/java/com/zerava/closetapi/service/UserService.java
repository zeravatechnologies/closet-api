package com.zerava.closetapi.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import com.zerava.closetapi.model.User;
import com.zerava.closetapi.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public List<User> findAllUser(){
		List<User> listUser = userRepository.findAll();
		return listUser;
	}
	
	public User registerUser(User user){
		Optional<User> existing = userRepository.findByEmail(user.getEmail());
		if (existing.isPresent()) {
			throw new RuntimeException("Emial is already Registered");
		}
		return userRepository.save(user);
	}	
	
	public Optional<User> findByEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		return user;
	}
	
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    public void deleteUserById(Long id) {
    	userRepository.deleteById(id);
    }
	
	
	

}
