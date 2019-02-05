package com.ewa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ewa.model.Event;

/**
 * DAO Repository for events
 * @author fbertos
 *
 */
public interface EventRepository extends MongoRepository<Event, String> {
	/**
	 * List of events owning one user
	 * @param ownerId User ID
	 * @return List of events
	 */
	@Query("{ '$and':[ { 'ownerId' : ?0 }, "
			+ "{ 'status' : 'ENABLED' } ] }")	
	Page<Event> find(String ownerId);	
}
