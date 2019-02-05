package com.ewa.dao;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ewa.model.Location;

public interface LocationRepository extends MongoRepository<Location, String> {
	@Query("{ '$and':[ { 'userId' : ?0 }, "
			+ "{ 'date' : { $gte : ?1 } } ] }")
	Page<Location> find(String userId, Date date, Pageable pageable);

	@Query("{ 'userId' : ?0 }")
	Page<Location> findLatest(String userId, Pageable pageable);
}
