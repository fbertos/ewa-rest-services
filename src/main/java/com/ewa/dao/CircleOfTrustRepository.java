package com.ewa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ewa.model.CircleOfTrust;

public interface CircleOfTrustRepository extends MongoRepository<CircleOfTrust, String> {

	@Query("{ 'userId' : ?0 }")
	Page<CircleOfTrust> findByUserId(String userId);
	
	@Query(" { '$or' : [ { '$and':[ { 'userId' : ?0 }, "
			+ "{ 'contactId' : ?1 } ] }, "
			+ "{ '$and':[ { 'contactId' : ?0 }, "
			+ "{ 'userId' : ?1 } ] } ] }")
	Page<CircleOfTrust> find(String userId, String contactId);
}
