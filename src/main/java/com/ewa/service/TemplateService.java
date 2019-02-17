package com.ewa.service;

import java.util.Map;

/**
 * Service interface for freemark templates use
 * @author fbertos
 *
 */
public interface TemplateService {
	public String applyTemplate(String template,  Map<String, Object> root) throws Exception;
}
