package com.kts.sigma.service;

import java.util.Optional;

import com.kts.sigma.dto.TableDTO;
import com.kts.sigma.model.RestaurantTable;
import com.kts.sigma.model.TableState;

public interface TableService {
	public Iterable<TableDTO> getAll();
	
	public TableDTO findById(Integer id);
	
	public RestaurantTable save(RestaurantTable item);
	
	public void deleteById(Integer id);
	
	public void changeState(Integer id, TableState state);
}
