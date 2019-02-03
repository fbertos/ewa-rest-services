package com.ewa.service;

import java.util.List;

import com.ewa.model.MemberOfEvent;
import com.ewa.search.Config;

public interface MemberOfEventService {
	public MemberOfEvent create(MemberOfEvent memberOfEvent);
	public void update(MemberOfEvent memberOfEvent);
	public MemberOfEvent read(String id);
	public void delete(MemberOfEvent memberOfEvent);
	
	public List<MemberOfEvent> findByUserId(String userId, Config filter);
	public List<MemberOfEvent> findByUserId(String userId);
	public List<MemberOfEvent> find(String userId, String eventId, Config filter);
}
