package com.ewa.service;

import java.util.List;

import com.ewa.model.CircleOfTrust;
import com.ewa.search.Config;

public interface CircleOfTrustService {
	public CircleOfTrust create(CircleOfTrust CircleOfTrust);
	public void update(CircleOfTrust CircleOfTrust);
	public CircleOfTrust read(String id);
	public void delete(CircleOfTrust CircleOfTrust);
	
	public List<CircleOfTrust> findByUserId(String userId, Config filter);
	public List<CircleOfTrust> findByContactId(String contactId, Config filter);
	public List<CircleOfTrust> find(String userId, String contactId, Config filter);
}
