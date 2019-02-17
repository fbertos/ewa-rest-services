package com.ewa.service;

import java.util.List;

import com.ewa.model.CircleOfTrust;

/**
 * Service interface for contacts among users management
 * @author fbertos
 *
 */
public interface CircleOfTrustService {
	/**
	 * Create a contact relationship among users
	 * @param CircleOfTrust Relationship to be created
	 * @return New relationship created with valid ID
	 */
	public CircleOfTrust create(CircleOfTrust CircleOfTrust);
	
	/**
	 * Update a contact relationship
	 * @param CircleOfTrust Relationship to be updated
	 */
	public void update(CircleOfTrust CircleOfTrust);
	
	/**
	 * Get the relationship among two users from the ID
	 * @param id ID
	 * @return Relationship object
	 */
	public CircleOfTrust read(String id);
	
	/**
	 * Delete a relationship among two users
	 * @param CircleOfTrust Relationship object
	 */
	public void delete(CircleOfTrust CircleOfTrust);
	
	/**
	 * Find a relationship among two users if exists
	 * @param userId The user source of the relationship
	 * @return Relationship object
	 */
	public List<CircleOfTrust> findByUserId(String userId);
	
	/**
	 * Find the list of relationships for one user
	 * @param userId The user ID source of the relationship
	 * @param contactId The user ID target of the relationship
	 * @return List of relationships object
	 */
	public List<CircleOfTrust> find(String userId, String contactId);
}
