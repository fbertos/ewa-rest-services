package com.ewa.service;

import java.util.List;

import com.ewa.model.Session;

/**
 * Service interface for user session management 
 * @author fbertos
 *
 */
public interface SessionService {
	public Session create(Session Session);
	public void update(Session Session);
	public Session read(String id);
	public void delete(Session Session);
	
	public boolean check(Session session);
	public List<Session> findByUserId(String userId);
}
