package com.ewa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ewa.model.User;
import com.ewa.service.UserService;

@RestController
@RequestMapping("")
public class UserController {
	@Autowired
	private UserService mongo;
	
	@RequestMapping(value="/read")
	@GetMapping
    public @ResponseBody User getUser(@RequestHeader("Authorization") String ticket) {
		String port = System.getProperty("server.port");
		return new User("test from " + port);
    }
	
	@RequestMapping(value="/create")
	@PostMapping
    public @ResponseBody User createUser(@RequestHeader("Authorization") String ticket, @RequestBody User user) {
		if (ticket == null || !ticket.equals("3423fdsfddd432434dffs20018!!")) {
			return null;
		}
		
		return new User("test");
    }

	@RequestMapping(value="/file")
	@PostMapping
    public @ResponseBody User createUserWithFile(@RequestHeader("Authorization") String ticket, 
    		@RequestPart User user,
    		@RequestPart(value = "file", required = false) MultipartFile file) {
		if (ticket == null || !ticket.equals("3423fdsfddd432434dffs20018!!")) {
			return null;
		}
		
		return new User("test");
    }
}

