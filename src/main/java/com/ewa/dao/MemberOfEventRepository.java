package com.ewa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ewa.model.MemberOfEvent;

public interface MemberOfEventRepository extends MongoRepository<MemberOfEvent, String> {

	@Query("{ '$and':[ { 'userId' : ?0 }, "
			+ "{ 'eventId' : ?1 } ] }")
	Page<MemberOfEvent> findByUserId(String userId, String eventId, Pageable pageable);
	
	@Query("{ '$and':[ { 'contactId' : ?0 }, "
			+ "{ 'eventId' : ?1 } ] }")
	Page<MemberOfEvent> findByContactId(String contactId, String eventId, Pageable pageable);

	@Query("{ '$and':[ { 'userId' : ?0 }, "
			+ "{ 'contactId' : ?1 }, "
			+ "{ 'eventId' : ?2 } ] }")
	Page<MemberOfEvent> find(String userId, String contactId, String eventId, Pageable pageable);
}
