package com.ewa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ewa.model.ContactRequest;
import com.ewa.model.Session;
import com.ewa.model.User;
import com.ewa.service.ContactRequestService;
import com.ewa.service.SessionService;
import com.ewa.service.UserService;

/**
 * Rest controller managing contact requests from one user to another
 * @author fbertos
 *
 */
@RestController
@RequestMapping("/ewa/contact/request")
public class ContactRequestController {
	@Autowired
	private SessionService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ContactRequestService contactService;
	
	/**
	 * Send a contact request from one user to another
	 * @param sessionId Session Token Id
	 * @param contactId Contact ID to be in contact
	 * @return New contact request created
	 */
	@PostMapping(value="/{contactId}", produces = "application/json")
    public @ResponseBody ResponseEntity<ContactRequest> requestContact(
    		@RequestHeader("Authorization") String sessionId,
    		@PathVariable("contactId") String contactId) {
		try {
			Session session = service.read(sessionId);
			
			if (session == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}

			if (!service.check(session))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);		

			User user = userService.read(contactId);

			if (user == null)
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

			List<ContactRequest> list = contactService.find(session.getUserId(), contactId);
			
			if (list != null && !list.isEmpty())
				return ResponseEntity.status(HttpStatus.OK).body(list.get(0));
			
			ContactRequest request = new ContactRequest();
			request.setContactId(contactId);
			request.setUserId(session.getUserId());
			
			contactService.create(request);
			
			return ResponseEntity.status(HttpStatus.OK).body(request);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }
	
	/**
	 * Reject a contact request received from an user
	 * @param sessionId Session Token Id
	 * @param contactId Contact ID that sends the request
	 * @return Status 200 if all ok
	 */
	@DeleteMapping(value="/{contactId}", produces = "application/json")
    public @ResponseBody ResponseEntity<ContactRequest> rejectContact(
    		@RequestHeader("Authorization") String sessionId,
    		@PathVariable("contactId") String contactId) {
		try {
			Session session = service.read(sessionId);
			
			if (session == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}

			if (!service.check(session))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);		
			
			User user = userService.read(contactId);

			if (user == null)
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

			List<ContactRequest> list = contactService.find(session.getUserId(), contactId);
			
			if (list != null && !list.isEmpty()) {
				ContactRequest request = list.get(0);
				contactService.delete(request);
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }
}

