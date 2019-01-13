package com.ewa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ewa.model.User;

public interface UserRepository extends MongoRepository<User, String> {

	@Query("{ 'name' : ?0 }")
	Page<User> findByName(String name, Pageable pageable);
}
