package com.ewa.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.ewa.service.LanguageService;

public class LanguageServiceImpl implements LanguageService {
	private HashMap<String, Properties> properties = null;

	public String getLabel(String key, String language) {
		Properties props = this.getProperties(language);
		return props.getProperty(key);
	}

	public HashMap<String, String> getLabels(String language) {
		HashMap<String, String> labels = new HashMap<String, String>();
		Properties props = this.getProperties(language);
		String[] keys = (String[])props.keySet().toArray();
		
		for (int i=0; i<keys.length; i++)
			labels.put(keys[i], props.getProperty(keys[i]));

		return labels;
	}
	
	public HashMap<String, String> getLanguages() {
		HashMap<String, String> languages = new HashMap<String, String>();
		languages.put("es_ES", "Español");
		languages.put("en_EN", "English");
		return languages;
	}

	
	private Properties getProperties(String language) {
		try {
			if (properties == null || !properties.containsKey(language)) {
				if (properties == null)
					properties = new HashMap<String, Properties>();
				
				Resource resource = new ClassPathResource("languages/app_" + language + ".properties");
				Properties props = PropertiesLoaderUtils.loadProperties(resource);
				properties.put(language, props);
				return props;
			}
			
			return properties.get(language);
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
