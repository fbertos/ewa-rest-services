package com.ewa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ewa.model.Contact;
import com.ewa.model.Session;
import com.ewa.search.Config;
import com.ewa.service.SessionService;
import com.ewa.service.UserService;

@RestController
@RequestMapping("/ewa/contact")
public class ContactController {
	@Autowired
	private SessionService service;
	
	@Autowired
	private UserService userService;

	@GetMapping(value="", produces = "application/json")
    public @ResponseBody ResponseEntity<List<Contact>> findContacts(
    		@RequestHeader("Authorization") String sessionId,
    		@RequestParam String q,
    		@RequestParam String order,
    		@RequestParam String direction) {
		try {
			Session session = service.read(sessionId);
			
			if (session != null) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
						userService.find(q, new Config(order, direction, 0, 1)));
			}

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }
}

