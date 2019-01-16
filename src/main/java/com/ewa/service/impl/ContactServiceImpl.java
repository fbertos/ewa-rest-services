package com.ewa.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ewa.dao.ContactRepository;
import com.ewa.model.ContactRequest;
import com.ewa.service.ContactService;

@Service
public class ContactServiceImpl implements ContactService {
	@Autowired
	private ContactRepository repository;

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

	public List<ContactRequest> findBy(String userId, String contactId) {
		PageRequest request = PageRequest.of(0, 1, Sort.Direction.ASC);
	    return repository.find(userId, contactId, request).getContent();
	}
}
