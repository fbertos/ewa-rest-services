package com.ewa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ewa.model.MemberOfEvent;

/**
 * DAO repository for event attendees
 * @author fbertos
 *
 */
public interface MemberOfEventRepository extends MongoRepository<MemberOfEvent, String> {
	/**
	 * Find the event membership of a user
	 * @param userId User ID
	 * @return List of memberships
	 */
	@Query("{ 'userId' : ?0 }")
	Page<MemberOfEvent> findByUserId(String userId);
	
	/**
	 * Find if a user is member of a event
	 * @param userId User ID
	 * @param eventId Event ID
	 * @return List of membership if exists
	 */
	@Query("{ '$and':[ { 'userId' : ?0 }, "
			+ "{ 'eventId' : ?1 } ] }")
	Page<MemberOfEvent> find(String userId, String eventId);
	
	/**
	 * Find the user membership for a event
	 * @param eventId Event ID
	 * @return List of members
	 */
	@Query("{ 'eventId' : ?0 }")
	Page<MemberOfEvent> find(String eventId);	
}
