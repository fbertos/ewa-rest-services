package com.ewa.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewa.dao.MemberOfEventRepository;
import com.ewa.model.MemberOfEvent;
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

	public List<MemberOfEvent> findByUserId(String userId) {
	    return repository.findByUserId(userId).getContent();
	}
	
	public List<MemberOfEvent> find(String userId, String eventId) {
	    return repository.find(userId, eventId).getContent();
	}
	
	public List<MemberOfEvent> find(String eventId) {
	    return repository.find(eventId).getContent();
	}
	
}
