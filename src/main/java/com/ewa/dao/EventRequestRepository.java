package com.ewa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ewa.model.EventRequest;

public interface EventRequestRepository extends MongoRepository<EventRequest, String> {
	@Query("{ '$and':[ { 'userId' : ?0 }, "
			+ "{ 'contactId' : ?1 }, "
			+ "{ 'eventId' : ?2 } ] }")
	Page<EventRequest> find(String userId, String contactId, String eventId, Pageable pageable);
}
