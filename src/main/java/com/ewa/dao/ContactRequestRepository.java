package com.ewa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ewa.model.ContactRequest;

public interface ContactRequestRepository extends MongoRepository<ContactRequest, String> {
	@Query("{ '$and':[ { 'userId' : ?0 }, "
			+ "{ 'contactId' : ?1 } ] }")
	Page<ContactRequest> find(String userId, String contactId, Pageable pageable);
}
