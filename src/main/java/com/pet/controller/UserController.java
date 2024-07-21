package com.pet.controller;


import java.util.List;
import com.pet.db.UserRepo;
import com.pet.model.User;
import com.pet.model.UserLogin;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@CrossOrigin

@RestController
@RequestMapping("api/users")
public class UserController {

	@Autowired
	private UserRepo userRepo;
	
	@GetMapping("/")
	public List<User> getAllUsers()
	{
		return userRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public User getUserById(@PathVariable int id) {
		Optional<User> u = userRepo.findById(id);
		
		if(u.isPresent()) {
			return u.get();
		}
		
		else {
			System.err.println("User["+id+"] does not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found: id ["+ id + "]");
			
		}
		
	}
	
	@PostMapping("")
			public User addUser(@RequestBody User user) {
		return userRepo.save(user);
	}
	@PutMapping("/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User user) {
		User u = null;
		if (id != user.getId()) {
			System.err.println("User Id does not match path id.");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
		} else if (!userRepo.existsById(id)) {
			System.err.println("Vendor does not exist for id: " + id);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist");
		} else
			try {
				u = userRepo.save(user); // update user as body where id = id
			} catch (Exception e) {
				System.err.println(e);
				throw e;
			}
		return u;

	}

	@DeleteMapping("/{id}")
	public boolean deleteUser(@PathVariable int id) {
		boolean success = false;

		if (userRepo.existsById(id)) {
			userRepo.deleteById(id);
			success = true;
		} else {
			System.err.println("Delete Error: No User exists for id: " + id);
			success = false;
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist");
		}

		return success;
	}

	@PostMapping("/login")
	public User login(@RequestBody UserLogin ul) {
		User user = userRepo.findByEmailAndPassword(ul.getEmail(), ul.getPassword());

		if (user == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email and password not found");
		}
		return user;
	}

	
}
