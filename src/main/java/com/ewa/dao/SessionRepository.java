package com.ewa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ewa.model.Session;

public interface SessionRepository extends MongoRepository<Session, String> {

	@Query("{ 'userId' : ?0 }")
	Page<Session> findByUserId(String userId);
}
