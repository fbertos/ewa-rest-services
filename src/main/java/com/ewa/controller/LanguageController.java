package com.ewa.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ewa.model.Session;
import com.ewa.service.LanguageService;
import com.ewa.service.SessionService;

@RestController
@RequestMapping("/ewa/language")
public class LanguageController {
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private LanguageService service;

	@GetMapping(value="", produces = "application/json")
    public @ResponseBody ResponseEntity<HashMap<String, String>> listLanguages(
    		@RequestHeader("Authorization") String sessionId) {
		try {
			Session session = sessionService.read(sessionId);
			
			if (session != null) {
				if (!sessionService.check(session))
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);		
				
			    return ResponseEntity.status(HttpStatus.OK).body(service.getLanguages());
			}

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }

	@GetMapping(value="/{language}", produces = "application/json")
    public @ResponseBody ResponseEntity<HashMap<String, String>> listLabels(
    		@RequestHeader("Authorization") String sessionId,
    		@PathVariable("language") String language) {
		try {
			Session session = sessionService.read(sessionId);
			
			if (session != null) {
				if (!sessionService.check(session))
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);		
				
				return ResponseEntity.status(HttpStatus.OK).body(service.getLabels(language));
			}

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }	
}

