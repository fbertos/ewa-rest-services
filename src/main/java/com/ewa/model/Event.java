package com.ewa.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

/**
 * Model POJO for planned events
 * @author fbertos
 *
 */
public class Event {
	@Id
	private String id;

	private String name;
	
	private String description;
	
	private String picture;
	
	private Location address;
	
	private String ownerId;
	
	private Date date;
	
	private Status status;
	
	public Event() {
		this.status = Status.ENABLED;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Location getAddress() {
		return address;
	}

	public void setAddress(Location address) {
		this.address = address;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public enum Status {
		ENABLED, DISABLED;
	}
	
}
