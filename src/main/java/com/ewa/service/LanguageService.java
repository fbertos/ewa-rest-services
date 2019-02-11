package com.ewa.service;

import java.util.HashMap;

/**
 * Service interface for language management
 * @author fbertos
 *
 */
public interface LanguageService {
	public String getLabel(String key, String language);
	public HashMap<String, String> getLabels(String language);
	public HashMap<String, String> getLanguages();
}
