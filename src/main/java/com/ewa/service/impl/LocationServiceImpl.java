package com.ewa.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ewa.dao.LocationRepository;
import com.ewa.model.Location;
import com.ewa.search.Config;
import com.ewa.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {
	@Autowired
	private LocationRepository repository;

	public Location create(Location location) {
		return repository.save(location);
    }
	
	public void update(Location location) {
		repository.save(location);
	}
	
	public Location read(String id) {
		Optional<Location> location = repository.findById(id);
		return (location != null)?location.get():null;
	}
	
	public void delete(Location location) {
		repository.delete(location);
	}

	public List<Location> find(String userId, Date date, Config filter) {
		PageRequest request = PageRequest.of(filter.getPage(), filter.getItemsperpage(), new Sort(Sort.Direction.valueOf(filter.getDirection()), filter.getOrder()));
	    List<Location> locations = repository.find(userId, date, request).getContent();
	    return locations;
	}
}
