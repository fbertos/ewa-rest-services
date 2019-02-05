package com.ewa.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ewa.service.LanguageService;

/**
 * Rest controller managing the application texts for any supported language
 * @author fbertos
 *
 */
@RestController
@RequestMapping("/ewa/language")
public class LanguageController {
	@Autowired
	private LanguageService service;

	/**
	 * List of supported languages
	 * @return List of languages
	 */
	@GetMapping(value="", produces = "application/json")
    public @ResponseBody ResponseEntity<HashMap<String, String>> listLanguages() {
		try {
				
		    return ResponseEntity.status(HttpStatus.OK).body(service.getLanguages());
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }

	/**
	 * Send the list of texts for the selected language
	 * @param language Supported language
	 * @return Application texts
	 */
	@GetMapping(value="/{language}", produces = "application/json")
    public @ResponseBody ResponseEntity<HashMap<String, String>> listLabels(@PathVariable("language") String language) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.getLabels(language));
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }	
}

