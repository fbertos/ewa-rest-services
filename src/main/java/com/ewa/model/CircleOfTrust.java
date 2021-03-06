package com.ewa.model;

import org.springframework.data.annotation.Id;

/**
 * Model POJO for contact relationships between users
 * @author fbertos
 *
 */
public class CircleOfTrust {
	@Id
	private String id;

	private String userId;
	
	private String contactId;

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
}
