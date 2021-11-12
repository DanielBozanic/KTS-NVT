package com.kts.sigma.service;

import java.util.Optional;

import com.kts.sigma.model.Zone;

public interface ZoneService {
	public Iterable<Zone> getAll();
	
	public Optional<Zone> findById(Integer id);
	
	public Zone save(Zone Zone);
	
	public void deleteById(Integer id);
}
