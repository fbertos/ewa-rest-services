package com.ewa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ewa.model.CircleOfTrust;

/**
 * DAO Repository for linkages between contacts
 * @author fbertos
 *
 */
public interface CircleOfTrustRepository extends MongoRepository<CircleOfTrust, String> {

	/**
	 * List of relationships of a user
	 * @param userId User ID
	 * @return List of relationships
	 */
	@Query("{ 'userId' : ?0 }")
	Page<CircleOfTrust> findByUserId(String userId);
	
	/**
	 * Relationship between two users
	 * @param userId User ID
	 * @param contactId The other user ID
	 * @return Relationship if exists
	 */
	@Query(" { '$or' : [ { '$and':[ { 'userId' : ?0 }, "
			+ "{ 'contactId' : ?1 } ] }, "
			+ "{ '$and':[ { 'contactId' : ?0 }, "
			+ "{ 'userId' : ?1 } ] } ] }")
	Page<CircleOfTrust> find(String userId, String contactId);
}
