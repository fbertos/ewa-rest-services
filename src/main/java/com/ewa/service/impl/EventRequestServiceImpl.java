package com.ewa.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ewa.dao.EventRequestRepository;
import com.ewa.model.EventRequest;
import com.ewa.search.Config;
import com.ewa.service.EventRequestService;

@Service
public class EventRequestServiceImpl implements EventRequestService {
	@Autowired
	private EventRequestRepository repository;

	public EventRequest create(EventRequest eventRequest) {
		return repository.save(eventRequest);
    }
	
	public void update(EventRequest eventRequest) {
		repository.save(eventRequest);
	}
	
	public EventRequest read(String id) {
		Optional<EventRequest> eventRequest = repository.findById(id);
		return (eventRequest != null)?eventRequest.get():null;
	}
	
	public void delete(EventRequest eventRequest) {
		repository.delete(eventRequest);
	}

	public List<EventRequest> find(String userId, String contactId, String eventId, Config filter) {
		PageRequest request = PageRequest.of(filter.getPage(), filter.getItemsperpage(), new Sort(Sort.Direction.valueOf(filter.getDirection()), filter.getOrder()));
	    return repository.find(userId, contactId, eventId, request).getContent();
	}
}
