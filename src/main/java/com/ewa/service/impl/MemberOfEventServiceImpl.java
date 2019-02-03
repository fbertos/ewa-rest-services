package com.ewa.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ewa.dao.MemberOfEventRepository;
import com.ewa.model.MemberOfEvent;
import com.ewa.search.Config;
import com.ewa.service.MemberOfEventService;

@Service
public class MemberOfEventServiceImpl implements MemberOfEventService {
	@Autowired
	private MemberOfEventRepository repository;

	public MemberOfEvent create(MemberOfEvent memberOfEvent) {
		return repository.save(memberOfEvent);
    }
	
	public void update(MemberOfEvent memberOfEvent) {
		repository.save(memberOfEvent);
	}
	
	public MemberOfEvent read(String id) {
		Optional<MemberOfEvent> memberOfEvent = repository.findById(id);
		return (memberOfEvent != null)?memberOfEvent.get():null;
	}
	
	public void delete(MemberOfEvent memberOfEvent) {
		repository.delete(memberOfEvent);
	}

	public List<MemberOfEvent> find(String userId, String contactId, String eventId, Config filter) {
		PageRequest request = PageRequest.of(filter.getPage(), filter.getItemsperpage(), new Sort(Sort.Direction.valueOf(filter.getDirection()), filter.getOrder()));
	    return repository.find(userId, contactId, eventId, request).getContent();
	}

	public List<MemberOfEvent> findByUserId(String userId, String eventId, Config filter) {
		PageRequest request = PageRequest.of(filter.getPage(), filter.getItemsperpage(), new Sort(Sort.Direction.valueOf(filter.getDirection()), filter.getOrder()));
	    return repository.findByUserId(userId, eventId, request).getContent();
	}

	public List<MemberOfEvent> findByContactId(String contactId, String eventId, Config filter) {
		PageRequest request = PageRequest.of(filter.getPage(), filter.getItemsperpage(), new Sort(Sort.Direction.valueOf(filter.getDirection()), filter.getOrder()));
	    return repository.findByContactId(contactId, eventId, request).getContent();
	}
}
