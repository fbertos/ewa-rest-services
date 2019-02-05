package com.ewa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ewa.model.ContactRequest;

/**
 * DAO Repository for contact requests
 * @author fbertos
 *
 */
public interface ContactRequestRepository extends MongoRepository<ContactRequest, String> {
	
	/**
	 * Find if a contact request exists between two users
	 * @param userId User ID
	 * @param contactId The other User ID
	 * @return ContactRequest if exists
	 */
	@Query("{ '$and':[ { 'userId' : ?0 }, "
			+ "{ 'contactId' : ?1 } ] }")
	Page<ContactRequest> find(String userId, String contactId);
}
