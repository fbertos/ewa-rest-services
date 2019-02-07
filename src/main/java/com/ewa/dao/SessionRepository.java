package com.ewa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ewa.model.Session;

/**
 * DAO repository for active sessions
 * @author fbertos
 *
 */
public interface SessionRepository extends MongoRepository<Session, String> {

	/**
	 * Find sessions active for a user
	 * @param userId User ID
	 * @return List of active session if exist
	 */
	@Query("{ 'userId' : ?0 }")
	Page<Session> findByUserId(String userId);
}
