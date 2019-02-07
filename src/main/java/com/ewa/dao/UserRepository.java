package com.ewa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ewa.model.User;

/**
 * DAO repository of a user
 * @author fbertos
 *
 */
public interface UserRepository extends MongoRepository<User, String> {

	/**
	 * Find users by email
	 * @param email Email
	 * @param pageable Pagination
	 * @return List of users
	 */
	@Query("{ 'email' : ?0 }")
	Page<User> findByEmail(String email, Pageable pageable);
	
	/**
	 * Find users by name
	 * @param fullName Full Name
	 * @param pageable Pagination
	 * @return List of users
	 */
	@Query("{ 'fullName' : ?0 }")
	Page<User> findByFullName(String fullName, Pageable pageable);
	
	/**
	 * Find users by text (email, name, etc)
	 * @param text Text to search
	 * @param pageable Pagination
	 * @return List of users
	 */
	@Query("{ '$or':[ { 'fullName' : { $regex : ?0 } }, "
			+ "{ 'email' : { $regex : ?0 } } ] }")
	Page<User> find(String text, Pageable pageable);
}
