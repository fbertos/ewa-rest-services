package com.ewa.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ewa.dao.CircleOfTrustRepository;
import com.ewa.model.CircleOfTrust;
import com.ewa.search.Config;
import com.ewa.service.CircleOfTrustService;

@Service
public class CircleOfTrustServiceImpl implements CircleOfTrustService {
	@Autowired
	private CircleOfTrustRepository repository;

	public CircleOfTrust create(CircleOfTrust circleOfTrust) {
		return repository.save(circleOfTrust);
    }
	
	public void update(CircleOfTrust circleOfTrust) {
		repository.save(circleOfTrust);
	}
	
	public CircleOfTrust read(String id) {
		Optional<CircleOfTrust> circleOfTrust = repository.findById(id);
		return (circleOfTrust != null)?circleOfTrust.get():null;
	}
	
	public void delete(CircleOfTrust circleOfTrust) {
		repository.delete(circleOfTrust);
	}

	public List<CircleOfTrust> find(String userId, String contactId, Config filter) {
		PageRequest request = PageRequest.of(filter.getPage(), filter.getItemsperpage(), new Sort(Sort.Direction.valueOf(filter.getDirection()), filter.getOrder()));
	    return repository.find(userId, contactId, request).getContent();
	}

	public List<CircleOfTrust> findByUserId(String userId, Config filter) {
		PageRequest request = PageRequest.of(filter.getPage(), filter.getItemsperpage(), new Sort(Sort.Direction.valueOf(filter.getDirection()), filter.getOrder()));
	    return repository.findByUserId(userId, request).getContent();
	}

	public List<CircleOfTrust> findByContactId(String contactId, Config filter) {
		PageRequest request = PageRequest.of(filter.getPage(), filter.getItemsperpage(), new Sort(Sort.Direction.valueOf(filter.getDirection()), filter.getOrder()));
	    return repository.findByContactId(contactId, request).getContent();
	}
}
