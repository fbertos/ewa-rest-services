package com.ewa.service;

import java.util.List;

import com.ewa.model.User;
import com.ewa.search.Config;

public interface UserService {
	public User create(User user);
	public void update(User user);
	public User read(String id);
	public void delete(User user);
	
	public List<User> findByFullName(String fullName, Config filter);
	public List<User> findByEmail(String email, Config filter);
}
