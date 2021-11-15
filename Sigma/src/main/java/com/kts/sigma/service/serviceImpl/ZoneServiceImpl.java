package com.kts.sigma.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.Utility.Mapper;
import com.kts.sigma.dto.MenuDTO;
import com.kts.sigma.dto.ZoneDTO;
import com.kts.sigma.model.Menu;
import com.kts.sigma.model.Zone;
import com.kts.sigma.repository.ZoneRepository;
import com.kts.sigma.service.ZoneService;

@Service
public class ZoneServiceImpl implements ZoneService{
	@Autowired
	private ZoneRepository zoneRepository;
	
	@Override
	public Iterable<ZoneDTO> getAll() {
		List<Zone> zones = zoneRepository.findAll();
		ArrayList<ZoneDTO> results = new ArrayList<ZoneDTO>();
		
		for (Zone zone : zones) {
			ZoneDTO dto = Mapper.mapper.map(zone, ZoneDTO.class);
			results.add(dto);
		}
		
		return results;
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
	public ZoneDTO findById(Integer id)
	{
		Zone zone = zoneRepository.findById(id).orElse(null);
		if(zone == null)
		{
			throw new ItemNotFoundException(id);
		}
		
		ZoneDTO result = Mapper.mapper.map(zone, ZoneDTO.class);
	    return result;
	}
}
