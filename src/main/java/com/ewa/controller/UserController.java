package com.ewa.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ewa.model.CircleOfTrust;
import com.ewa.model.Contact;
import com.ewa.model.Location;
import com.ewa.model.Session;
import com.ewa.model.User;
import com.ewa.search.Config;
import com.ewa.service.CircleOfTrustService;
import com.ewa.service.CryptoService;
import com.ewa.service.LocationService;
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

	@Autowired
	private CircleOfTrustService trustService;

	@Autowired
	private LocationService locationService;
	
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
			
			User currentUser = service.read(userId);

			if (currentUser == null)
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			
			service.delete(currentUser);

			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }	

	@GetMapping(value="/contact", produces = "application/json")
    public @ResponseBody ResponseEntity<List<Contact>> listContacts(
    		@RequestHeader("Authorization") String sessionId,
    		@RequestParam String order,
    		@RequestParam String direction,
    		@RequestParam int page,
    		@RequestParam int itemsperpage) {
		try {
			Session session = sessionService.read(sessionId);
			
			if (session != null) {
				List<CircleOfTrust> circles = trustService.findByUserId(session.getUserId(), new Config(order, direction, page, itemsperpage));
			    List<Contact> contacts = circles.stream().map(circle -> {
			    	User user = service.read(circle.getContactId());
			    	return user.toContact();
			    }).collect(Collectors.toList());
			    
			    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(contacts);
			}

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }

	@DeleteMapping(value="/contact/{contactId}", produces = "application/json")
    public @ResponseBody ResponseEntity<Contact> deleteContact(
    		@RequestHeader("Authorization") String sessionId,
    		@PathVariable("contactId") String contactId) {
		try {
			Session session = sessionService.read(sessionId);
			
			if (session != null) {
				List<CircleOfTrust> circles = trustService.find(session.getUserId(), contactId, new Config("userId", "ASC", 0, 1));
				
				if (circles != null && !circles.isEmpty())
					trustService.delete(circles.get(0));
				
			    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }
	
	@GetMapping(value="/contact/{contactId}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<Location>> locateContact(
    		@RequestHeader("Authorization") String sessionId,
    		@PathVariable("contactId") String contactId,
    		@RequestParam Date date,
			@RequestParam String order,
			@RequestParam String direction,
			@RequestParam int page,
			@RequestParam int itemsperpage) {
		try {
			Session session = sessionService.read(sessionId);
			
			if (session != null) {
				List<CircleOfTrust> circles = trustService.find(session.getUserId(), contactId, new Config("userId", "ASC", 0, 1));
				
				if (circles != null && !circles.isEmpty())
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(locationService.find(contactId, new Date(), new Config(order, direction, page, itemsperpage)));
				
			    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }

	@PostMapping(value="/contact/address", produces = "application/json")
    public @ResponseBody ResponseEntity<Location> addLocation(
    		@RequestHeader("Authorization") String sessionId,
    		@RequestPart Location location) {
		try {
			Session session = sessionService.read(sessionId);
			
			if (session != null) {
				location.setUserId(session.getUserId());
				location.setEventId(null);
				location.setId(null);
				
				locationService.create(location);
				
			    return ResponseEntity.status(HttpStatus.OK).body(location);
			}

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }
	
}

