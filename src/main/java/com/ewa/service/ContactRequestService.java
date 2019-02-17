package com.ewa.service;

import java.util.List;

import com.ewa.model.ContactRequest;

/**
 * Service interface for contact request management
 * @author fbertos
 *
 */
public interface ContactRequestService {
	/**
	 * Create a contact request among users
	 * @param contactRequest Contact request object
	 * @return The new contact request with a valid ID
	 */
	public ContactRequest create(ContactRequest contactRequest);
	
	/**
	 * Update a contact request
	 * @param contactRequest Contact request object
	 */
	public void update(ContactRequest contactRequest);
	
	/**
	 * Get a contact request
	 * @param id Contact request ID
	 * @return Contact request object
	 */
	public ContactRequest read(String id);
	
	/**
	 * Delete a contact request
	 * @param contactRequest Contact request object
	 */
	public void delete(ContactRequest contactRequest);
	
	/**
	 * Find a contact request among two users if exists
	 * @param userId Source User ID 
	 * @param contactId Target User ID
	 * @return Contact request object if exists
	 */
	public List<ContactRequest> find(String userId, String contactId);
}
