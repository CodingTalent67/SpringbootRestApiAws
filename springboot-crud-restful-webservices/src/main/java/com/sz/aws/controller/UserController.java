package com.sz.aws.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sz.aws.entity.User;
import com.sz.aws.exception.ResourceNotFoundException;
import com.sz.aws.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserRepository UserRepository;

	//get all users
	@GetMapping
	public List<User> getAllUsers(){
		return this.UserRepository.findAll();
		
	}
	
	//get user by id
	@GetMapping("/{id}")
	public User getUserById(@PathVariable (value="id") long userId) {
		return this.UserRepository.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User not found with id :" +userId));
		
	}
	
	//create user
	@PostMapping
	public User createUser(@RequestBody User user) {
		return this.UserRepository.save(user);
		
	}
	
	//update user
	@PutMapping("/{id}")
	public User updateUser(@RequestBody User user,@PathVariable (value="id") long userId) {
		User existingUser=this.UserRepository.findById(userId)
		.orElseThrow(()->new ResourceNotFoundException("User not found with id :" +userId));
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setEmail(user.getEmail());
		return this.UserRepository.save(existingUser);
		
	}
	
	//delete user by id
	
	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUserById(@PathVariable (value="id") long userId) {
		User existingUser=this.UserRepository.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User not found with id :" +userId));
		this.UserRepository.delete(existingUser);
		return ResponseEntity.ok() .build();
		
	}
	
}
