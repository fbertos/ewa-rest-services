package com.ewa.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

/**
 * Model POJO for location of contacts
 * @author fbertos
 *
 */
public class Location {
	@Id
	private String id;

	private String userId;
	
	private String longitud;
	
	private String latitud;
	
	private Date date;
	
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

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
