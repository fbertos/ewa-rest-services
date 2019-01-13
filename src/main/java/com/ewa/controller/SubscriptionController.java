package com.ewa.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ewa.model.User;
import com.ewa.search.Config;
import com.ewa.service.CryptoService;
import com.ewa.service.MailService;
import com.ewa.service.TemplateService;
import com.ewa.service.UserService;

@RestController
@RequestMapping("/ewa/subscription")
public class SubscriptionController {
	@Autowired
	private UserService service;
	
	@Autowired
	private MailService mailService;

	@Autowired
	private TemplateService templateService;

	@Autowired
	private CryptoService cryptoService;

	@RequestMapping(value="", produces = "application/json")
	@PostMapping
    public @ResponseBody ResponseEntity<User> createUser(@RequestBody User user) {
		try {
			List<User> existing = service.findByEmail(user.getEmail(), new Config("email", "ASC", 0, 1));
			
			if (existing != null && !existing.isEmpty())
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			
			user.setStatus(User.Status.PENDING);
			User newUser = service.create(user);
			 
			Map<String, Object> root = new HashMap<>();
			root.put("user", newUser);
			String security = cryptoService.encode(user.getId(), "password-2018-!@ewa--#&@");
			root.put("security", security);
			String body = templateService.applyTemplate("new_user", root);
			System.out.println(body);
			mailService.SendMail(user.getEmail(), "Please confirm the subscription", body);
			
			return ResponseEntity.status(HttpStatus.OK).body(newUser);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }
	
	/*
	@RequestMapping(value="/{userId}/status", produces = "text/html")
	@GetMapping
    public String enableUser(@PathVariable("userId") String userId, 
    		@RequestParam String security) {
		User user = service.read(userId);
		// String calculated = cryptoService.encode(user.getId(), user.getPassword());
		
		String calculated = "";
		
		if (user == null || user.getStatus() != User.Status.PENDING || !calculated.equals(security))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		
		user.setStatus(User.Status.ENABLED);
		service.update(user);
		
		return ResponseEntity.status(HttpStatus.OK).body(user);
    }	
    */
}

