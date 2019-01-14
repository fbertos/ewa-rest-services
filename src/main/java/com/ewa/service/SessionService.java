package com.ewa.service;

import java.util.List;

import com.ewa.model.Session;
import com.ewa.search.Config;

public interface SessionService {
	public Session create(Session Session);
	public void update(Session Session);
	public Session read(String id);
	public void delete(Session Session);
	
	public List<Session> findByUserId(String userId, Config filter);
}
