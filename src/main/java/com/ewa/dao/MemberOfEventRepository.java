package com.ewa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ewa.model.MemberOfEvent;

public interface MemberOfEventRepository extends MongoRepository<MemberOfEvent, String> {
	@Query("{ 'userId' : ?0 }")
	Page<MemberOfEvent> findByUserId(String userId);
	
	@Query("{ '$and':[ { 'userId' : ?0 }, "
			+ "{ 'eventId' : ?1 } ] }")
	Page<MemberOfEvent> find(String userId, String eventId);
	
	@Query("{ 'eventId' : ?0 }")
	Page<MemberOfEvent> find(String eventId);	
}
