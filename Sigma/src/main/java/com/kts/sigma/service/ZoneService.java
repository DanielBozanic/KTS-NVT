package com.kts.sigma.service;

import java.util.List;

import com.kts.sigma.dto.TableDTO;
import com.kts.sigma.dto.ZoneDTO;

public interface ZoneService {
	List<ZoneDTO> getAll();
	
	ZoneDTO findById(Integer id);
	
	ZoneDTO createNewZone(ZoneDTO newZone);
	
	TableDTO updateNumberChairs(TableDTO tableDto);
	
	void deleteById(Integer id);
	
	void removeTableFromZone(TableDTO tableDto);
	
	List<TableDTO> getTables(Integer id);
}
