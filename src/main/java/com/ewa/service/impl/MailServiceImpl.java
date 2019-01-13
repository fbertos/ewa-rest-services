package com.ewa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.ewa.service.MailService;

public class MailServiceImpl implements MailService {
	@Autowired
    public JavaMailSender mailSender;	
	
	@Override
	public void SendMail(String destination, String subject, String body) throws Exception {
		SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo(destination); 
        message.setSubject(subject); 
        message.setText(body);
        mailSender.send(message);
	}
}
