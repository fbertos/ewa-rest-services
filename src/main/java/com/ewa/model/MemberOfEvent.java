package com.ewa.model;

import org.springframework.data.annotation.Id;

/**
 * Model POJO for event attendees
 * @author fbertos
 *
 */
public class MemberOfEvent {
	@Id
	private String id;

	private String userId;
	
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

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
}
