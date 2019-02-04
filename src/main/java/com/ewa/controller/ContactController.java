package com.ewa.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ewa.model.CircleOfTrust;
import com.ewa.model.Contact;
import com.ewa.model.Location;
import com.ewa.model.Session;
import com.ewa.model.User;
import com.ewa.search.Config;
import com.ewa.service.CircleOfTrustService;
import com.ewa.service.LocationService;
import com.ewa.service.SessionService;
import com.ewa.service.UserService;

@RestController
@RequestMapping("/ewa/contact")
public class ContactController {
	@Autowired
	private UserService service;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private CircleOfTrustService trustService;

	@GetMapping(value="", produces = "application/json")
    public @ResponseBody ResponseEntity<List<Contact>> listContacts(
    		@RequestHeader("Authorization") String sessionId,
    		@RequestParam String order,
    		@RequestParam String direction,
    		@RequestParam int page,
    		@RequestParam int itemsperpage) {
		try {
			Session session = sessionService.read(sessionId);
			
			if (session != null) {
				if (!sessionService.check(session))
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);		
				
				List<CircleOfTrust> circles = trustService.findByUserId(session.getUserId(), new Config(order, direction, page, itemsperpage));
			    List<Contact> contacts = circles.stream().filter(circle -> {
			    	User user = service.read(circle.getContactId());
			    	return user.getStatus() == User.Status.ENABLED;
			    }).map(circle -> {
			    	User user = service.read(circle.getContactId());
			    	Contact contact = user.toContact();
			    	Location location = locationService.findLatest(user.getId());
			    	contact.setKnownLocation(location);
			    	return user.toContact();
			    }).collect(Collectors.toList());
			    
			    return ResponseEntity.status(HttpStatus.OK).body(contacts);
			}

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }

	@DeleteMapping(value="/{contactId}", produces = "application/json")
    public @ResponseBody ResponseEntity<Contact> deleteContact(
    		@RequestHeader("Authorization") String sessionId,
    		@PathVariable("contactId") String contactId) {
		try {
			Session session = sessionService.read(sessionId);
			
			if (session != null) {
				if (!sessionService.check(session))
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);		
				
				List<CircleOfTrust> circles = trustService.find(session.getUserId(), contactId, new Config("userId", "ASC", 0, 1));
				
				if (circles != null && !circles.isEmpty())
					trustService.delete(circles.get(0));
				
			    return ResponseEntity.status(HttpStatus.OK).body(null);
			}

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }	
}

