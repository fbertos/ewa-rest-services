package com.ewa.service;

import java.util.List;

import com.ewa.model.CircleOfTrust;

/**
 * Service interface for contacts among users management
 * @author fbertos
 *
 */
public interface CircleOfTrustService {
	public CircleOfTrust create(CircleOfTrust CircleOfTrust);
	public void update(CircleOfTrust CircleOfTrust);
	public CircleOfTrust read(String id);
	public void delete(CircleOfTrust CircleOfTrust);
	
	public List<CircleOfTrust> findByUserId(String userId);
	public List<CircleOfTrust> find(String userId, String contactId);
}
