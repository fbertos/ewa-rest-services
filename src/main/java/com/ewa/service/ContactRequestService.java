package com.ewa.service;

import java.util.List;

import com.ewa.model.ContactRequest;

public interface ContactRequestService {
	public ContactRequest create(ContactRequest contactRequest);
	public void update(ContactRequest contactRequest);
	public ContactRequest read(String id);
	public void delete(ContactRequest contactRequest);
	
	public List<ContactRequest> find(String userId, String contactId);
}
