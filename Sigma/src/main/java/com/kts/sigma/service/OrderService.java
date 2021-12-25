package com.kts.sigma.service;


import com.kts.sigma.dto.ItemInOrderDTO;
import com.kts.sigma.dto.OrderDTO;
import com.kts.sigma.model.OrderState;
import com.kts.sigma.model.RestaurantOrder;

public interface OrderService {
	public Iterable<OrderDTO> getAll();
	
	public Iterable<OrderDTO> getAllFoodOrders();
	
	public Iterable<OrderDTO> getAllDrinkOrders();
	
	public OrderDTO findById(Integer id);
	
	public RestaurantOrder save(OrderDTO item, Integer code);
	
	public void deleteById(Integer id, Integer code);

	public Iterable<ItemInOrderDTO> getAllItems(Integer id);
	
	public Iterable<ItemInOrderDTO> getAllDrinks(Integer id);
	
	public Iterable<ItemInOrderDTO> getAllFoods(Integer id);

	public ItemInOrderDTO addItemToOrder(ItemInOrderDTO item, Integer code, Integer orderId);

	public void removeItemFromOrder(Integer itemId, Integer code, Integer orderId);

	public void changeState(OrderState state, Integer code, Integer orderId);
	
	public void changeStateWithoutCode(OrderState state, Integer orderId);
}
