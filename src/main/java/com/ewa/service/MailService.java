package com.ewa.service;

/**
 * Service interface for mailing management
 * @author fbertos
 *
 */
public interface MailService {
	public void SendMail(String destination, String subject, String body) throws Exception;
}