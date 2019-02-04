package com.ewa.model;

import org.springframework.data.annotation.Id;

public class User {
	@Id
	private String id;
	  	
	private String fullName;
	
	private String picture;
	
	private String email;
	
	private String password;
	
	private String preferredContactId;
	
	private String language;

	private Status status;
	
	public User() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public enum Status {
		PENDING, ENABLED, DISABLED;
	}
	
	public Contact toContact() {
		return new Contact(this.id, this.fullName, this.picture, this.email);
	}

	public String getPreferredContactId() {
		return preferredContactId;
	}

	public void setPreferredContactId(String preferredContactId) {
		this.preferredContactId = preferredContactId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
}
