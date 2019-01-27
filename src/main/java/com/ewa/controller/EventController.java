package com.ewa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ewa.model.Event;
import com.ewa.model.Session;
import com.ewa.service.CryptoService;
import com.ewa.service.EventService;
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
			
			if (!"image/png".equals(picture.getContentType()))
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

			event.setPicture(cryptoService.encodeBase64(picture.getBytes()));

			Event current = service.create(event);
			 
			return ResponseEntity.status(HttpStatus.OK).body(current);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }
}

