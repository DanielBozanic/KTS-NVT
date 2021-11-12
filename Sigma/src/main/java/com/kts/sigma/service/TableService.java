package com.kts.sigma.service;

import java.util.Optional;

import com.kts.sigma.model.RestaurantTable;

public interface TableService {
	public Iterable<RestaurantTable> getAll();
	
	public Optional<RestaurantTable> findById(Integer id);
	
	public RestaurantTable save(RestaurantTable item);
	
	public void deleteById(Integer id);
}
