package com.ewa.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Session {
	@Id
	private String id;

	private String userId;
	
	private Date dateTime;

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

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
}
