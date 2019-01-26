package com.ewa.service;

import java.util.Date;
import java.util.List;

import com.ewa.model.Location;
import com.ewa.search.Config;

public interface LocationService {
	public Location create(Location location);
	public void update(Location location);
	public Location read(String id);
	public void delete(Location location);
	
	public List<Location> find(String userId, Date date, Config filter);
}