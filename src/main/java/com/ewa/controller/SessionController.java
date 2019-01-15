package com.ewa.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ewa.model.Session;
import com.ewa.model.User;
import com.ewa.search.Config;
import com.ewa.service.CryptoService;
import com.ewa.service.SessionService;
import com.ewa.service.UserService;

@RestController
@RequestMapping("/ewa/session")
public class SessionController {
	@Value("${spring.data.ewa.key}")
	private String security_key;
	
	@Autowired
	private SessionService service;
	
	@Autowired
	private UserService userService;

	@Autowired
	private CryptoService cryptoService;

	@PostMapping(value="/{sessionId}", produces = "application/json")
    public @ResponseBody ResponseEntity<Session> updateSession(@PathVariable("sessionId") String sessionId) {
		try {
			Session session = service.read(sessionId);
			
			if (session == null)
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			
			session.setDateTime(new Date());
			
			service.update(session);
			
			return ResponseEntity.status(HttpStatus.OK).body(session);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }
	
	@GetMapping(value="/{sessionId}", produces = "application/json")
    public @ResponseBody ResponseEntity<Session> getSession(@PathVariable("sessionId") String sessionId) {
		try {
			Session session = service.read(sessionId);
			return ResponseEntity.status(HttpStatus.OK).body(session);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }
	
	@PutMapping(value="", produces = "application/json")
    public @ResponseBody ResponseEntity<Session> createSession(@RequestParam String username, 
    		@RequestParam String password) {
		try {
			List<User> user = userService.findByEmail(username, new Config("email", "ASC", 0, 1));
			
			if (user == null || user.isEmpty())
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

			String passwordCry = cryptoService.encode(password, security_key);
			
			if (!user.get(0).getPassword().equals(passwordCry) || user.get(0).getStatus() != User.Status.ENABLED)
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			
			List<Session> existing = service.findByUserId(user.get(0).getId(), new Config("email", "ASC", 0, 1));
			Session session = new Session();

			if (existing != null && !existing.isEmpty())
				session = existing.get(0);

			session.setDateTime(new Date());
			session.setUserId(user.get(0).getId());
			
			if (session.getId() != null)
				service.update(session);
			else
				service.create(session);
			
			return ResponseEntity.status(HttpStatus.OK).body(session);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }
	
	@DeleteMapping(value="/{sessionId}", produces = "application/json")
    public @ResponseBody ResponseEntity<Session> deleteSession(@PathVariable("sessionId") String sessionId) {
		try {
			Session session = service.read(sessionId);
			
			if (session == null)
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			
			session.setDateTime(new Date());
			
			service.delete(session);
			
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }
	
}

