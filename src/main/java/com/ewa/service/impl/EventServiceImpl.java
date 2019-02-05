package com.ewa.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewa.dao.EventRepository;
import com.ewa.model.Event;
import com.ewa.service.EventService;

@Service
public class EventServiceImpl implements EventService {
	@Autowired
	private EventRepository repository;

	public Event create(Event event) {
		return repository.save(event);
    }
	
	public void update(Event event) {
		repository.save(event);
	}
	
	public Event read(String id) {
		Optional<Event> event = repository.findById(id);
		return (event != null)?event.get():null;
	}
	
	public void delete(Event event) {
		repository.delete(event);
	}

	public List<Event> find(String ownerId) {
	    return repository.find(ownerId).getContent();
	}
}
