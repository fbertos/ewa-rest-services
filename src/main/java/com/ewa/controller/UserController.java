package com.ewa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ewa.model.User;
import com.ewa.search.Config;
import com.ewa.service.UserService;

@RestController
@RequestMapping("/ewa/user")
public class UserController {
	@Autowired
	private UserService service;
	
	@RequestMapping(value="", produces = "application/json")
	@PostMapping
    public @ResponseBody ResponseEntity<User> createUser(@RequestBody User user) {
		List<User> existing = service.findByEmail(user.getEmail(), new Config("email", "ASC", 0, 1));
		
		
		if (existing != null && !existing.isEmpty())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		
		user.setStatus(User.Status.PENDING);
		User newUser = service.create(user);
		
		return ResponseEntity.status(HttpStatus.OK).body(newUser);
    }
}

