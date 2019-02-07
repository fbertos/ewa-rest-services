package com.ewa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ewa.model.EventRequest;

/**
 * DAO repository for requests to attend an event
 * @author fbertos
 *
 */
public interface EventRequestRepository extends MongoRepository<EventRequest, String> {
	/**
	 * Find an event request between two contacts for an event
	 * @param userId User ID
	 * @param contactId Other UserID
	 * @param eventId Even tID
	 * @return Event request if exists
	 */
	@Query("{ '$and':[ { 'userId' : ?0 }, "
			+ "{ 'contactId' : ?1 }, "
			+ "{ 'eventId' : ?2 } ] }")
	Page<EventRequest> find(String userId, String contactId, String eventId);
}
