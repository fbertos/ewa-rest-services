package com.ewa.dao;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ewa.model.Location;

/**
 * DAO repository for user location
 * @author fbertos
 *
 */
public interface LocationRepository extends MongoRepository<Location, String> {
	/**
	 * Find the locations of one user after a date
	 * @param userId User ID
	 * @param date Date
	 * @param pageable Pagination
	 * @return List of locations
	 */
	@Query("{ '$and':[ { 'userId' : ?0 }, "
			+ "{ 'date' : { $gte : ?1 } } ] }")
	Page<Location> find(String userId, Date date, Pageable pageable);

	/**
	 * Find the locations for a user
	 * @param userId User ID
	 * @param pageable Pagination
	 * @return List of locations
	 */
	@Query("{ 'userId' : ?0 }")
	Page<Location> find(String userId, Pageable pageable);
}
