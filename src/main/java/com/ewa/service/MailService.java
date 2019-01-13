package com.ewa.service;

public interface MailService {
	public void SendMail(String destination, String subject, String body) throws Exception;
}