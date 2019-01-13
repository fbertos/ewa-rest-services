package com.ewa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ewa.dao.UserRepository;
import com.ewa.model.User;
import com.ewa.search.Filter;

@Service
public class UserService {
	@Autowired
	private UserRepository repository;

	public User save(User u) {
		return repository.save(u);
    }
	
	public List<User> findByName(String name, Filter filter) {
		PageRequest request = PageRequest.of(filter.getPage(), filter.getItemsperpage(), new Sort(Sort.Direction.valueOf(filter.getDirection()), filter.getOrder()));
	    return repository.findByName(name, request).getContent();
	}
}
