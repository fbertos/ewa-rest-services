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

import com.ewa.model.EventRequest;
import com.ewa.model.Session;
import com.ewa.model.User;
import com.ewa.service.EventRequestService;
import com.ewa.service.SessionService;
import com.ewa.service.UserService;

/**
 * Rest controller managing a request to participate in an event
 * @author fbertos
 *
 */
@RestController
@RequestMapping("/ewa/event/request")
public class EventRequestController {
	@Autowired
	private SessionService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EventRequestService eventService;

	/**
	 * Send a request for an event to a contact
	 * @param sessionId Session Token Id
	 * @param contactId Contact ID
	 * @param eventId Event ID
	 * @return The new event request
	 */
	@PostMapping(value="/{contactId}/{eventId}", produces = "application/json")
    public @ResponseBody ResponseEntity<EventRequest> requestEvent(
    		@RequestHeader("Authorization") String sessionId,
    		@PathVariable("contactId") String contactId,
    		@PathVariable("eventId") String eventId) {
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

			List<EventRequest> list = eventService.find(session.getUserId(), contactId, eventId);
			
			if (list != null && !list.isEmpty())
				return ResponseEntity.status(HttpStatus.OK).body(list.get(0));
			
			EventRequest request = new EventRequest();
			request.setContactId(contactId);
			request.setUserId(session.getUserId());
			
			eventService.create(request);
			
			return ResponseEntity.status(HttpStatus.OK).body(request);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }
	
	/**
	 * Reject a request for an event coming from a contact
	 * @param sessionId Session Token Id
	 * @param contactId Contact ID
	 * @param eventId Event ID
	 * @return Status 200 if all ok
	 */
	@DeleteMapping(value="/{contactId}/{eventId}", produces = "application/json")
    public @ResponseBody ResponseEntity<EventRequest> rejectEvent(
    		@RequestHeader("Authorization") String sessionId,
    		@PathVariable("contactId") String contactId,
    		@PathVariable("eventId") String eventId) {
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

			List<EventRequest> list = eventService.find(session.getUserId(), contactId, eventId);
			
			if (list != null && !list.isEmpty()) {
				EventRequest request = list.get(0);
				eventService.delete(request);
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }
}

