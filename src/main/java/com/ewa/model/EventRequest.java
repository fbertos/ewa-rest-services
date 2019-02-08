package com.ewa.model;

import org.springframework.data.annotation.Id;

/**
 * Model POJO for requests to attend events
 * @author fbertos
 *
 */
public class EventRequest {
	@Id
	private String id;

	private String userId;
	
	private String contactId;
	
	private String eventId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
}
