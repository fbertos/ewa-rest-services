package com.ewa.service.impl;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ewa.dao.UserRepository;
import com.ewa.model.User;
import com.ewa.search.Config;
import com.ewa.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository repository;

	public User create(User user) {
		return repository.save(user);
    }
	
	public void update(User user) {
		repository.save(user);
	}
	
	public User read(ObjectId id) {
		Optional<User> user = repository.findById(id.toHexString());
		return (user != null)?user.get():null;
	}
	
	public void delete(User user) {
		repository.delete(user);
	}

	public List<User> findByFullName(String fullName, Config filter) {
		PageRequest request = PageRequest.of(filter.getPage(), filter.getItemsperpage(), new Sort(Sort.Direction.valueOf(filter.getDirection()), filter.getOrder()));
	    return repository.findByFullName(fullName, request).getContent();
	}
	
	public List<User> findByEmail(String email, Config filter) {
		PageRequest request = PageRequest.of(filter.getPage(), filter.getItemsperpage(), new Sort(Sort.Direction.valueOf(filter.getDirection()), filter.getOrder()));
	    return repository.findByEmail(email, request).getContent();
	}
}
