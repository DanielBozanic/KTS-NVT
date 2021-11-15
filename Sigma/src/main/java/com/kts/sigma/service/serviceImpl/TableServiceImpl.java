package com.kts.sigma.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.Utility.Mapper;
import com.kts.sigma.dto.MenuDTO;
import com.kts.sigma.dto.TableDTO;
import com.kts.sigma.model.Menu;
import com.kts.sigma.model.RestaurantOrder;
import com.kts.sigma.model.RestaurantTable;
import com.kts.sigma.repository.TableRepository;
import com.kts.sigma.service.TableService;

@Service
public class TableServiceImpl implements TableService{
	@Autowired
	private TableRepository tableRepository;
	
	@Override
	public Iterable<TableDTO> getAll() {
		List<RestaurantTable> tables = tableRepository.findAll();
		ArrayList<TableDTO> results = new ArrayList<TableDTO>();
		
		for (RestaurantTable table : tables) {
			TableDTO dto = Mapper.mapper.map(table, TableDTO.class);
			results.add(dto);
		}
		
		return results;
	}
	
	@Override
	public RestaurantTable save(RestaurantTable item) {
		return tableRepository.save(item);
	}
	
	@Override
	public void deleteById(Integer id) {
		tableRepository.deleteById(id);
	}
	@Override
	public TableDTO findById(Integer id)
	{
		RestaurantTable table = tableRepository.findById(id).orElse(null);
		if(table == null)
		{
			throw new ItemNotFoundException(id);
		}
		
		TableDTO result = Mapper.mapper.map(table, TableDTO.class);
	    return result;
	}
}
