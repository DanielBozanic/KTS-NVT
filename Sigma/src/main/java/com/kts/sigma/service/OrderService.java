package com.kts.sigma.service;


import com.kts.sigma.dto.ItemInOrderDTO;
import com.kts.sigma.dto.OrderDTO;
import com.kts.sigma.model.RestaurantOrder;

public interface OrderService {
	public Iterable<OrderDTO> getAll();
	
	public OrderDTO findById(Integer id);
	
	public RestaurantOrder save(OrderDTO item, Integer code);
	
	public void deleteById(Integer id);

	public Iterable<ItemInOrderDTO> getAllItems(Integer id);
	
	public Iterable<ItemInOrderDTO> getAllDrinks(Integer id);
	
	public Iterable<ItemInOrderDTO> getAllFoods(Integer id);
}
