package com.kts.sigma.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kts.sigma.Exception.ItemExistsException;
import com.kts.sigma.Exception.ItemInUseException;
import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.Utility.Mapper;
import com.kts.sigma.dto.TableDTO;
import com.kts.sigma.dto.ZoneDTO;
import com.kts.sigma.model.RestaurantTable;
import com.kts.sigma.model.TableState;
import com.kts.sigma.model.Zone;
import com.kts.sigma.repository.TableRepository;
import com.kts.sigma.repository.ZoneRepository;
import com.kts.sigma.service.ZoneService;

@Service
public class ZoneServiceImpl implements ZoneService{
	
	@Autowired
	private ZoneRepository zoneRepository;
	
	@Autowired
	private TableRepository tableRepository;
	
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
	public ZoneDTO createNewZone(ZoneDTO newZone) {
		Zone zoneWithExistingName = zoneRepository.findByName(newZone.getName());
		if (zoneWithExistingName != null) {
			throw new ItemExistsException("Zone with this name " + "(" + newZone.getName() + ") " + "already exists!");
		}
		Zone createdZone = zoneRepository.save(Mapper.mapper.map(newZone, Zone.class));
		return Mapper.mapper.map(createdZone, ZoneDTO.class);
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
			throw new ItemNotFoundException(id, "zone");
		}
		
		ZoneDTO result = Mapper.mapper.map(zone, ZoneDTO.class);
	    return result;
	}

	@Override
	public ArrayList<TableDTO> removeTableFromZone(TableDTO tableDto) {
		RestaurantTable tableForRemoval = tableRepository.getTableByIdAndState(tableDto.getId(), tableDto.getState());
		
		if (tableForRemoval == null) {
			throw new ItemNotFoundException(tableDto.getId(), "table");
		} else if (tableForRemoval.getState() != TableState.FREE) {
			throw new ItemInUseException("This table cannot be removed from this zone, "
		    		+ "because it is currently being used!");
		}
		
		Zone zone = tableForRemoval.getZone();
		
		tableForRemoval.setZone(null);
		tableRepository.save(tableForRemoval);
		
		ArrayList<RestaurantTable> zoneTables = tableRepository.findByZoneId(zone.getId());
		ArrayList<TableDTO> zoneTablesDTO = new ArrayList<TableDTO>();
		
		for (RestaurantTable rt : zoneTables) {
			TableDTO dto = Mapper.mapper.map(rt, TableDTO.class);
			zoneTablesDTO.add(dto);
		}
		
		return zoneTablesDTO;
	}

	@Override
	public TableDTO updateNumberChairs(TableDTO tableDto) {
		RestaurantTable table = tableRepository.findById(tableDto.getId()).orElse(null);
		if (table == null) {
			throw new ItemNotFoundException(tableDto.getId(), "table");
		}
		
		table.setNumberOfChairs(tableDto.getNumberOfChairs());
		tableRepository.save(table);
		return Mapper.mapper.map(tableRepository.save(table), TableDTO.class);
  }
  
	public Iterable<TableDTO> getTables(Integer id) {
		Zone zone = zoneRepository.findById(id).orElse(null);
		if(zone == null)
		{
			throw new ItemNotFoundException(id, "zone");
		}
		
		ArrayList<TableDTO> tables = new ArrayList<>();
		
		for (RestaurantTable table : zone.getTables()) {
			TableDTO dto = Mapper.mapper.map(table, TableDTO.class);
			tables.add(dto);
		}
		
		return tables;
	}
}
