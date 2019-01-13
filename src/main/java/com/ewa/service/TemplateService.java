package com.ewa.service;

import java.util.Map;

public interface TemplateService {
	public String applyTemplate(String template,  Map<String, Object> root) throws Exception;
}
