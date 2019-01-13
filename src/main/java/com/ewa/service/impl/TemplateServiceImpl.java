package com.ewa.service.impl;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ewa.service.TemplateService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

@Service
public class TemplateServiceImpl implements TemplateService {

	public String applyTemplate(String template, Map<String, Object> root) throws Exception {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
		cfg.setClassForTemplateLoading(this.getClass(), "/templates/");
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);
		cfg.setWrapUncheckedExceptions(true);	
		
		Template temp = cfg.getTemplate(template + ".ftl");
		Writer out = new StringWriter();
		temp.process(root, out);
		return out.toString();
	}
}
