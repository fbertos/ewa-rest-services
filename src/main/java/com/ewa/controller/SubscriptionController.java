package com.ewa.controller;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ewa.model.User;
import com.ewa.search.Config;
import com.ewa.service.CryptoService;
import com.ewa.service.MailService;
import com.ewa.service.TemplateService;
import com.ewa.service.UserService;

@RestController
@RequestMapping("/ewa/subscription")
public class SubscriptionController {
	@Value("${spring.data.ewa.key}")
	private String security_key;
	
	@Autowired
	private UserService service;
	
	@Autowired
	private MailService mailService;

	@Autowired
	private TemplateService templateService;

	@Autowired
	private CryptoService cryptoService;

	@PostMapping(value="", produces = "application/json")
    public @ResponseBody ResponseEntity<User> createUser(@RequestPart User user,
    		@RequestPart(value = "file", required = false) MultipartFile picture) {
		try {
			List<User> existing = service.findByEmail(user.getEmail(), new Config("email", "ASC", 0, 1));

			if (existing != null && !existing.isEmpty())
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			
			user.setStatus(User.Status.PENDING);
			
			if (user.getEmail() == null || user.getFullName() == null || user.getPassword() == null)
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			
			String passwordCry = cryptoService.encode(user.getPassword(), security_key);
			
			user.setPassword(passwordCry);
			
			if (!"image/png".equals(picture.getContentType()))
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

			user.setPicture(cryptoService.encodeBase64(picture.getBytes()));

			User newUser = service.create(user);
			 
			Map<String, Object> root = new HashMap<>();
			root.put("user", newUser);
			String security = cryptoService.encode(user.getId(), security_key);
			root.put("security", URLEncoder.encode(security, "UTF-8").replace("+", "%20"));
			String body = templateService.applyTemplate("new_user", root);
			
			//mailService.SendMail(user.getEmail(), "EWA - Please confirm the subscription", body);
			
			return ResponseEntity.status(HttpStatus.OK).body(newUser);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }
	
	@GetMapping(value="/{userId}/confirmation", produces = "text/html")
    public String enableUser(@PathVariable("userId") String userId, 
    		@RequestParam String security) {
		try {
			User user = service.read(userId);
			String calculated = cryptoService.encode(user.getId(), security_key);
			
			if (user == null || user.getStatus() != User.Status.PENDING || !calculated.equals(security))
				return "Error confirming the user email, please contact EWA Support Team.";
			
			user.setStatus(User.Status.ENABLED);
			service.update(user);
			
			return "User confirmation processed!";
		}
		catch(Exception e) {
			e.printStackTrace();
			return "Error confirming the user email, please contact EWA Support Team.";
		}
    }	
}

