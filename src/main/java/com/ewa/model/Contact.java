package com.ewa.model;

/**
 * Model POJO for contact information
 * @author fbertos
 *
 */
public class Contact {
	private String id;
	private String fullName;
	private String picture;
	private String email;
	private Location knownLocation;
	
	public Contact(String id, String fullName, String picture, String email) {
		this.id = id;
		this.fullName = fullName;
		this.picture = picture;
		this.email = email;
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

	public Location getKnownLocation() {
		return knownLocation;
	}

	public void setKnownLocation(Location knownLocation) {
		this.knownLocation = knownLocation;
	}
}
