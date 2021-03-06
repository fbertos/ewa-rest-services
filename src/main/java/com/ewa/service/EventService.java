package com.ewa.service;

import java.util.List;

import com.ewa.model.Event;

/**
 * Service interface for event management
 * @author fbertos
 *
 */
public interface EventService {
	public Event create(Event event);
	public void update(Event event);
	public Event read(String id);
	public void delete(Event event);
	
	public List<Event> find(String ownerId);
}
