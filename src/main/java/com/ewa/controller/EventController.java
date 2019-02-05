package com.ewa.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

import com.ewa.model.CircleOfTrust;
import com.ewa.model.Contact;
import com.ewa.model.Event;
import com.ewa.model.Location;
import com.ewa.model.MemberOfEvent;
import com.ewa.model.Session;
import com.ewa.model.User;
import com.ewa.service.CircleOfTrustService;
import com.ewa.service.CryptoService;
import com.ewa.service.EventService;
import com.ewa.service.LocationService;
import com.ewa.service.MemberOfEventService;
import com.ewa.service.SessionService;
import com.ewa.service.UserService;

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
	private CircleOfTrustService circleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LocationService locationService;	
	
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
			
			Date limit = this.getLimitDate();
			
			for (final MemberOfEvent member : members) {
				Event event = eventService.read(member.getEventId());
				
				if (!events.contains(event) && event.getStatus() == Event.Status.ENABLED && limit.before(event.getDate()))
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
			
			event.setStatus(Event.Status.DISABLED);
			service.update(event);
			
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }
	
	@GetMapping(value="/{eventId}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<Contact>> getEvent(
    		@RequestHeader("Authorization") String sessionId,
    		@PathVariable("eventId") String eventId) {
		try {
			Session session = sessionService.read(sessionId);
			
			if (session == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			
			if (!sessionService.check(session))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);		
			
			Event currentEvent = service.read(eventId);
			List<MemberOfEvent> member = memberService.find(session.getUserId(), eventId);
			
			if (currentEvent.getOwnerId() != session.getUserId() || member == null || member.isEmpty())
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
			
			List<MemberOfEvent> members = memberService.find(eventId);
			
			List<Contact> contacts = members.stream().map(m -> {
				User user = userService.read(m.getUserId());
				List<CircleOfTrust> circle = circleService.find(session.getUserId(), m.getUserId());
				Contact contact = user.toUnknownContact();
				
				if (circle != null && !circle.isEmpty()) {
					contact = user.toContact();
					Location location = locationService.findLatest(user.getId());
					contact.setKnownLocation(location);
				}
				
				return contact;
			}).collect(Collectors.toList());
			
			return ResponseEntity.status(HttpStatus.OK).body(contacts);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }	
	
	private Date getLimitDate() {
		Date today = new Date();
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(today); 
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}
	
}

