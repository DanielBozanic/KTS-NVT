package com.kts.sigma.service;

import java.util.Optional;

import com.kts.sigma.model.RestaurantOrder;

public interface OrderService {
	public Iterable<RestaurantOrder> getAll();
	
	public Optional<RestaurantOrder> findById(Integer id);
	
	public RestaurantOrder save(RestaurantOrder item);
	
	public void deleteById(Integer id);
}
