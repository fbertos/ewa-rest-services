package com.ewa.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewa.dao.ContactRequestRepository;
import com.ewa.model.ContactRequest;
import com.ewa.service.ContactRequestService;

/**
 * Service implementation for contact request management
 * @author fbertos
 *
 */
@Service
public class ContactRequestServiceImpl implements ContactRequestService {
	@Autowired
	private ContactRequestRepository repository;

	/*
	 * (non-Javadoc)
	 * @see com.ewa.service.ContactRequestService#create(com.ewa.model.ContactRequest)
	 */
	public ContactRequest create(ContactRequest contactRequest) {
		return repository.save(contactRequest);
    }
	
	/*
	 * (non-Javadoc)
	 * @see com.ewa.service.ContactRequestService#update(com.ewa.model.ContactRequest)
	 */
	public void update(ContactRequest ContactRequest) {
		repository.save(ContactRequest);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ewa.service.ContactRequestService#read(java.lang.String)
	 */
	public ContactRequest read(String id) {
		Optional<ContactRequest> contactRequest = repository.findById(id);
		return (contactRequest != null)?contactRequest.get():null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ewa.service.ContactRequestService#delete(com.ewa.model.ContactRequest)
	 */
	public void delete(ContactRequest contactRequest) {
		repository.delete(contactRequest);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ewa.service.ContactRequestService#find(java.lang.String, java.lang.String)
	 */
	public List<ContactRequest> find(String userId, String contactId) {
	    return repository.find(userId, contactId).getContent();
	}
}
