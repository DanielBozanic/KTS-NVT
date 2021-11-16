package com.kts.sigma.service;

import java.util.ArrayList;

import com.kts.sigma.dto.TableDTO;
import com.kts.sigma.dto.ZoneDTO;

public interface ZoneService {
	Iterable<ZoneDTO> getAll();
	
	ZoneDTO findById(Integer id);
	
	ZoneDTO createNewZone(ZoneDTO newZone);
	
	TableDTO updateNumberChairs(TableDTO tableDto);
	
	void deleteById(Integer id);
	
	ArrayList<TableDTO> removeTableFromZone(TableDTO tableDto);
	
	ArrayList<TableDTO> getZoneTables(Integer id);
	
}
