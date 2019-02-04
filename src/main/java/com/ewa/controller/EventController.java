package com.ewa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ewa.model.Event;
import com.ewa.model.MemberOfEvent;
import com.ewa.model.Session;
import com.ewa.service.CryptoService;
import com.ewa.service.EventService;
import com.ewa.service.MemberOfEventService;
import com.ewa.service.SessionService;

@RestController
@RequestMapping("/ewa/event")
public class EventController {
	@Autowired
	private EventService service;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private CryptoService cryptoService;

	@Autowired
	private MemberOfEventService memberService;

	@Autowired
	private EventService eventService;
	
	@PostMapping(value="", produces = "application/json")
    public @ResponseBody ResponseEntity<Event> createEvent(
    		@RequestHeader("Authorization") String sessionId,
    		@RequestPart Event event,
    		@RequestPart(value = "file", required = false) MultipartFile picture) {
		try {
			Session session = sessionService.read(sessionId);
			
			if (session == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			
			if (!sessionService.check(session))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);		
			
			if (!"image/png".equals(picture.getContentType()))
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

			event.setPicture(cryptoService.encodeBase64(picture.getBytes()));
			event.setOwnerId(session.getUserId());

			Event current = service.create(event);
			 
			return ResponseEntity.status(HttpStatus.OK).body(current);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }
	
	@GetMapping(value="", produces = "application/json")
    public @ResponseBody ResponseEntity<List<Event>> listEvents(
    		@RequestHeader("Authorization") String sessionId) {
		try {
			Session session = sessionService.read(sessionId);
			
			if (session == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			
			if (!sessionService.check(session))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);		
			
			List<Event> events = service.find(session.getUserId());
			List<MemberOfEvent> members = memberService.findByUserId(session.getUserId());
			
			for (final MemberOfEvent member : members) {
				Event event = eventService.read(member.getEventId());
				
				if (!events.contains(event))
					events.add(event);
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(events);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }
	
	@PostMapping(value="/{eventId}", produces = "application/json")
    public @ResponseBody ResponseEntity<Event> updateEvent(
    		@RequestHeader("Authorization") String sessionId,
    		@PathVariable("eventId") String eventId,
    		@RequestPart Event event,
    		@RequestPart(value = "file", required = false) MultipartFile picture) {
		try {
			Session session = sessionService.read(sessionId);
			
			if (session == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			
			if (!sessionService.check(session))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);		
			
			Event currentEvent = service.read(eventId);
			
			if (currentEvent.getOwnerId() != session.getUserId())
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
			
			if (picture != null && !"image/png".equals(picture.getContentType()))
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			
			currentEvent.setAddress(event.getAddress());
			currentEvent.setDate(event.getDate());
			currentEvent.setDescription(event.getDescription());
			currentEvent.setName(event.getName());

			if (picture != null)
				currentEvent.setPicture(cryptoService.encodeBase64(picture.getBytes()));

			service.update(currentEvent);
			
			return ResponseEntity.status(HttpStatus.OK).body(currentEvent);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }	

	@DeleteMapping(value="/{eventId}", produces = "application/json")
    public @ResponseBody ResponseEntity<Event> deleteEvent(
    		@RequestHeader("Authorization") String sessionId,
    		@PathVariable("eventId") String eventId) {
		try {
			Session session = sessionService.read(sessionId);
			
			if (session == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			
			if (!sessionService.check(session))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);		
			
			Event event = service.read(eventId);
			
			if (event.getOwnerId() != session.getUserId())
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
			
			service.delete(event);
			
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }		
}

