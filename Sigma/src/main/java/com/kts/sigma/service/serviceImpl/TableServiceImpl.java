package com.kts.sigma.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kts.sigma.Exception.AccessForbiddenException;
import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.Utility.Mapper;
import com.kts.sigma.dto.TableDTO;
import com.kts.sigma.model.Employee;
import com.kts.sigma.model.RestaurantTable;
import com.kts.sigma.model.TableState;
import com.kts.sigma.repository.EmployeeRepository;
import com.kts.sigma.repository.TableRepository;
import com.kts.sigma.service.TableService;

@Service
public class TableServiceImpl implements TableService {
	
	@Autowired
	private TableRepository tableRepository;
	
	@Autowired
	private EmployeeRepository emRepo;
	
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
	public TableDTO addTable(TableDTO tableDto) {
		Integer maxTableNumber = tableRepository.findMaxTableNumber();
		if (maxTableNumber == null) {
			tableDto.setTableNumber(1);
		} else {
			tableDto.setTableNumber(maxTableNumber + 1);
		}
		tableDto.setState(TableState.FREE);
		RestaurantTable savedtable = tableRepository.save(Mapper.mapper.map(tableDto, RestaurantTable.class));
		return Mapper.mapper.map(savedtable, TableDTO.class);
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
			throw new ItemNotFoundException(id, "table");
		}
		
		TableDTO result = Mapper.mapper.map(table, TableDTO.class);
	    return result;
	}

	@Override
	public void changeState(Integer id, TableState state, Integer code) {
		Employee worker = emRepo.findByCode(code);
		if(worker == null)
		{
			throw new AccessForbiddenException();
		}
		
		RestaurantTable table = tableRepository.findById(id).orElse(null);
		if(table == null)
		{
			throw new ItemNotFoundException(id, "table");
		}
		
		table.setState(state);
		tableRepository.save(table);
	}
}
