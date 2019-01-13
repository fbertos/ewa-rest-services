package com.ewa.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class User {
	@Id
	public ObjectId id;
	  	
	private String username;
	
	private String fullName;
	
	private String picture;

	private Status status;
	
	public User() {
	}
	
	public enum Status {
		DISABLED, CONNECTED, DISCONNECTED, REMOVED;
	}
}
