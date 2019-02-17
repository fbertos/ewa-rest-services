package com.ewa.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewa.dao.CircleOfTrustRepository;
import com.ewa.model.CircleOfTrust;
import com.ewa.service.CircleOfTrustService;

/**
 * Service implementation for contacts among users
 * @author fbertos
 *
 */
@Service
public class CircleOfTrustServiceImpl implements CircleOfTrustService {
	@Autowired
	private CircleOfTrustRepository repository;

	/*
	 * (non-Javadoc)
	 * @see com.ewa.service.CircleOfTrustService#create(com.ewa.model.CircleOfTrust)
	 */
	public CircleOfTrust create(CircleOfTrust circleOfTrust) {
		return repository.save(circleOfTrust);
    }
	
	/*
	 * (non-Javadoc)
	 * @see com.ewa.service.CircleOfTrustService#update(com.ewa.model.CircleOfTrust)
	 */
	public void update(CircleOfTrust circleOfTrust) {
		repository.save(circleOfTrust);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ewa.service.CircleOfTrustService#read(java.lang.String)
	 */
	public CircleOfTrust read(String id) {
		Optional<CircleOfTrust> circleOfTrust = repository.findById(id);
		return (circleOfTrust != null)?circleOfTrust.get():null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ewa.service.CircleOfTrustService#delete(com.ewa.model.CircleOfTrust)
	 */
	public void delete(CircleOfTrust circleOfTrust) {
		repository.delete(circleOfTrust);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ewa.service.CircleOfTrustService#find(java.lang.String, java.lang.String)
	 */
	public List<CircleOfTrust> find(String userId, String contactId) {
	    return repository.find(userId, contactId).getContent();
	}

	/*
	 * (non-Javadoc)
	 * @see com.ewa.service.CircleOfTrustService#findByUserId(java.lang.String)
	 */
	public List<CircleOfTrust> findByUserId(String userId) {
	    return repository.findByUserId(userId).getContent();
	}
}
