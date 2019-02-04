package com.ewa.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ewa.model.CircleOfTrust;
import com.ewa.model.Location;
import com.ewa.model.Session;
import com.ewa.search.Config;
import com.ewa.service.CircleOfTrustService;
import com.ewa.service.LocationService;
import com.ewa.service.SessionService;

@RestController
@RequestMapping("/ewa/location")
public class LocationController {
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private CircleOfTrustService trustService;

	@Autowired
	private LocationService locationService;
	
	@GetMapping(value="/{contactId}", produces = "application/json")
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
				if (!sessionService.check(session))
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);		
				
				List<CircleOfTrust> circles = trustService.find(session.getUserId(), contactId, new Config("userId", "ASC", 0, 1));
				
				if (circles != null && !circles.isEmpty())
					return ResponseEntity.status(HttpStatus.OK).body(locationService.find(contactId, new Date(), new Config(order, direction, page, itemsperpage)));
				
			    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }

	@PostMapping(value="", produces = "application/json")
    public @ResponseBody ResponseEntity<Location> addLocation(
    		@RequestHeader("Authorization") String sessionId,
    		@RequestParam Location location) {
		try {
			Session session = sessionService.read(sessionId);
			
			if (session != null) {
				if (!sessionService.check(session))
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);		
				
				location.setUserId(session.getUserId());
				location.setEventId(null);
				location.setId(null);
				
				Location latest = locationService.findLatest(session.getUserId());
				
				if (latest == null || (latest.getLatitud() != location.getLatitud() || latest.getLongitud() != location.getLongitud())) {
					locationService.create(location);					
				}
				else {
					latest.setDate(location.getDate());
					locationService.update(latest);
					location = latest;
				}
				
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

