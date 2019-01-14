package com.ewa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.ewa.service.MailService;

@Service
public class MailServiceImpl implements MailService {
	@Autowired
    public JavaMailSender mailSender;	
	
	@Override
	public void SendMail(String destination, String subject, String body) throws Exception {
		MimeMessagePreparator message = mimeMessage -> {
	        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
	        messageHelper.setTo(destination);
	        messageHelper.setSubject(subject);
	        messageHelper.setText(body, true);
	    };
	    
        mailSender.send(message);
	}
}
