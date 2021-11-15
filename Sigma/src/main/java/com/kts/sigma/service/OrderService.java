package com.kts.sigma.service;

import java.util.Optional;

import com.kts.sigma.dto.OrderDTO;
import com.kts.sigma.model.RestaurantOrder;

public interface OrderService {
	public Iterable<OrderDTO> getAll();
	
	public OrderDTO findById(Integer id);
	
	public RestaurantOrder save(RestaurantOrder item);
	
	public void deleteById(Integer id);
}
