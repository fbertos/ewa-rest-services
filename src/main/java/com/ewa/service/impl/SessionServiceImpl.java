package com.ewa.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ewa.dao.SessionRepository;
import com.ewa.model.Session;
import com.ewa.search.Config;
import com.ewa.service.SessionService;

@Service
public class SessionServiceImpl implements SessionService {
	@Autowired
	private SessionRepository repository;

	public Session create(Session Session) {
		return repository.save(Session);
    }
	
	public void update(Session Session) {
		repository.save(Session);
	}
	
	public Session read(String id) {
		Optional<Session> Session = repository.findById(id);
		return (Session != null)?Session.get():null;
	}
	
	public void delete(Session Session) {
		repository.delete(Session);
	}

	public List<Session> findByUserId(String userId, Config filter) {
		PageRequest request = PageRequest.of(filter.getPage(), filter.getItemsperpage(), new Sort(Sort.Direction.valueOf(filter.getDirection()), filter.getOrder()));
	    return repository.findByUserId(userId, request).getContent();
	}

	public boolean check(Session session) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(session.getDateTime());
		cal.add(Calendar.MINUTE, 90);
		Date limit = cal.getTime();
		Date now = new Date();
		
		if (now.compareTo(limit) >= 0)
			return false;
		
		return true;
	}
}
