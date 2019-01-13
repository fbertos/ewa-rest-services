package com.ewa.service.impl;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import com.ewa.service.TemplateService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public class TemplateServiceImpl implements TemplateService {

	public String applyTemplate(String template, Map<String, Object> root) throws Exception {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
		cfg.setDirectoryForTemplateLoading(new File("/where/you/store/templates"));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);
		cfg.setWrapUncheckedExceptions(true);	
		
		Template temp = cfg.getTemplate("test.ftlh");
		Writer out = new StringWriter();
		temp.process(root, out);
		return out.toString();
	}
}
