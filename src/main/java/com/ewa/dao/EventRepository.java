package com.ewa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ewa.model.Event;

public interface EventRepository extends MongoRepository<Event, String> {
	@Query("{ '$and':[ { 'ownerId' : ?0 }, "
			+ "{ 'status' : 'ENABLED' } ] }")	
	Page<Event> find(String ownerId, Pageable pageable);

	@Query("{ '$and':[ { 'ownerId' : ?0 }, "
			+ "{ 'status' : 'ENABLED' } ] }")	
	Page<Event> find(String ownerId);	
}
