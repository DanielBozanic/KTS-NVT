package com.kts.sigma.service.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kts.sigma.model.Zone;
import com.kts.sigma.repository.ZoneRepository;
import com.kts.sigma.service.ZoneService;

@Service
public class ZoneServiceImpl implements ZoneService{
	@Autowired
	private ZoneRepository zoneRepository;
	
	@Override
	public Iterable<Zone> getAll() {
		return zoneRepository.findAll();
	}
	
	@Override
	public Zone save(Zone item) {
		return zoneRepository.save(item);
	}
	
	@Override
	public void deleteById(Integer id) {
		zoneRepository.deleteById(id);
	}
	@Override
	public Optional<Zone> findById(Integer id)
	{
		return zoneRepository.findById(id);
	}
}
