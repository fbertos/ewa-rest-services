package com.ewa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ewa.model.Session;
import com.ewa.model.User;
import com.ewa.service.CryptoService;
import com.ewa.service.SessionService;
import com.ewa.service.UserService;

@RestController
@RequestMapping("/ewa/user")
public class UserController {
	@Value("${spring.data.ewa.key}")
	private String security_key;
	
	@Autowired
	private UserService service;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private CryptoService cryptoService;

	@PutMapping(value="/{userId}", produces = "application/json")
    public @ResponseBody ResponseEntity<User> updateUser(
    		@RequestHeader("Authorization") String sessionId,
    		@PathVariable("userId") String userId,
    		@RequestPart User user,
    		@RequestPart(value = "file", required = false) MultipartFile picture) {
		try {
			Session session = sessionService.read(sessionId);
			
			if (session == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			
			if (!sessionService.check(session))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);		
			
			User currentUser = service.read(userId);

			if (currentUser == null)
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			
			if (user.getEmail() != null)
				currentUser.setEmail(user.getEmail());
			
			if (user.getFullName() != null)
				currentUser.setFullName(user.getFullName());
	
			if (!"image/png".equals(picture.getContentType()))
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

			currentUser.setPicture(cryptoService.encodeBase64(picture.getBytes()));

			service.update(currentUser);
			 
			return ResponseEntity.status(HttpStatus.OK).body(currentUser);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }
	
	@PutMapping(value="/{userId}/password", produces = "application/json")
    public @ResponseBody ResponseEntity<User> updatePassword(
    		@RequestHeader("Authorization") String sessionId,
    		@PathVariable("userId") String userId,
    		@RequestParam String oldPassword,
    		@RequestParam String newPassword) {
		try {
			Session session = sessionService.read(sessionId);
			
			if (session == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}

			if (!sessionService.check(session))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);		
			
			User user = service.read(userId);

			if (user == null)
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			
			String passwordCry = cryptoService.encode(oldPassword, security_key);
			String passwordCryNew = cryptoService.encode(newPassword, security_key);
			
			if (!passwordCry.equals(user.getPassword()))
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

			user.setPassword(passwordCryNew);
			
			service.update(user);
			
			return ResponseEntity.status(HttpStatus.OK).body(user);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }
	
	@DeleteMapping(value="/{userId}", produces = "text/html")
    public @ResponseBody ResponseEntity<User> deleteUser(@RequestHeader("Authorization") String sessionId,
    		@PathVariable("userId") String userId) {
		try {
			Session session = sessionService.read(sessionId);
			
			if (session == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}

			if (!sessionService.check(session))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);		
			
			User currentUser = service.read(userId);

			if (currentUser == null)
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			
			service.delete(currentUser);
			sessionService.delete(session);

			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }	
}

