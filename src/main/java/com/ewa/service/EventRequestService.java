package com.ewa.service;

import java.util.List;

import com.ewa.model.EventRequest;

/**
 * Service interface for event attendee request management
 * @author fbertos
 *
 */
public interface EventRequestService {
	public EventRequest create(EventRequest eventRequest);
	public void update(EventRequest eventRequest);
	public EventRequest read(String id);
	public void delete(EventRequest eventRequest);
	
	public List<EventRequest> find(String userId, String contactId, String eventId);
}
