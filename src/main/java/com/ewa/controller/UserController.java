package com.ewa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

/**
 * Rest controller managing users
 * @author fbertos
 *
 */
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

	/**
	 * Update my user information
	 * @param sessionId Session Token Id
	 * @param user User object
	 * @param picture The picture linked to the user (optional)
	 * @return The new user
	 */
	@PutMapping(value="", produces = "application/json")
    public @ResponseBody ResponseEntity<User> updateUser(
    		@RequestHeader("Authorization") String sessionId,
    		@RequestPart User user,
    		@RequestPart(value = "picture", required = false) MultipartFile picture) {
		try {
			Session session = sessionService.read(sessionId);
			
			if (session == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			
			if (!sessionService.check(session))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);		
			
			User currentUser = service.read(session.getUserId());

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
	
	/**
	 * Change the password for my user
	 * @param sessionId Session Token Id
	 * @param oldPassword Old Password
	 * @param newPassword New Password
	 * @return The user updated
	 */
	@PutMapping(value="/password", produces = "application/json")
    public @ResponseBody ResponseEntity<User> updatePassword(
    		@RequestHeader("Authorization") String sessionId,
    		@RequestParam String oldPassword,
    		@RequestParam String newPassword) {
		try {
			Session session = sessionService.read(sessionId);
			
			if (session == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}

			if (!sessionService.check(session))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);		
			
			User user = service.read(session.getUserId());

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
	
	/**
	 * Disable my user
	 * @param sessionId Session Token Id
	 * @return Status 200 if all ok
	 */
	@DeleteMapping(value="", produces = "text/html")
    public @ResponseBody ResponseEntity<User> disableUser(@RequestHeader("Authorization") String sessionId) {
		try {
			Session session = sessionService.read(sessionId);
			
			if (session == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}

			if (!sessionService.check(session))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);		
			
			User currentUser = service.read(session.getUserId());

			if (currentUser == null)
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			
			currentUser.setStatus(User.Status.DISABLED);
			service.update(currentUser);
			sessionService.delete(session);

			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }	
}

