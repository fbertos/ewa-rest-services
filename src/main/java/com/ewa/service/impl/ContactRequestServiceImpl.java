package com.ewa.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewa.dao.ContactRequestRepository;
import com.ewa.model.ContactRequest;
import com.ewa.service.ContactRequestService;

@Service
public class ContactRequestServiceImpl implements ContactRequestService {
	@Autowired
	private ContactRequestRepository repository;

	public ContactRequest create(ContactRequest contactRequest) {
		return repository.save(contactRequest);
    }
	
	public void update(ContactRequest ContactRequest) {
		repository.save(ContactRequest);
	}
	
	public ContactRequest read(String id) {
		Optional<ContactRequest> contactRequest = repository.findById(id);
		return (contactRequest != null)?contactRequest.get():null;
	}
	
	public void delete(ContactRequest contactRequest) {
		repository.delete(contactRequest);
	}

	public List<ContactRequest> find(String userId, String contactId) {
	    return repository.find(userId, contactId).getContent();
	}
}
