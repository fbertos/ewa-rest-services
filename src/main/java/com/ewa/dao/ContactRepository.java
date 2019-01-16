package com.ewa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ewa.model.ContactRequest;

public interface ContactRepository extends MongoRepository<ContactRequest, String> {

	@Query("{ 'email' : ?0 }")
	Page<ContactRequest> findByEmail(String email, Pageable pageable);
	
	@Query("{ 'fullName' : ?0 }")
	Page<ContactRequest> findByFullName(String fullName, Pageable pageable);
	
	@Query("{ '$and':[ { 'userId' : ?0 }, "
			+ "{ 'contactId' : ?1 } ] }")
	Page<ContactRequest> find(String userId, String contactId, Pageable pageable);
}
