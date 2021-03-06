package com.kts.sigma.service;

import java.util.ArrayList;
import java.util.Set;

import com.kts.sigma.dto.ItemInOrderDTO;
import com.kts.sigma.model.ItemInOrder;
import com.kts.sigma.model.ItemInOrderState;

public interface ItemInOrderService {
	public Iterable<ItemInOrderDTO> getAll();
	
	public ItemInOrderDTO findById(Integer id);
	
	public ItemInOrder save(ItemInOrderDTO item, Integer code);
	
	public void deleteById(Integer id);
	
	public ItemInOrderDTO changeState(Integer id, ItemInOrderState state, Integer employeeCode);

	public void put(ItemInOrderDTO item, Integer code);

	public ItemInOrder saveWithoutCode(ItemInOrderDTO i);
	
	public ArrayList<ItemInOrderDTO> getOrderItems(Integer order_id);
	
	public ArrayList<ItemInOrderDTO> getFoodItemsByOrderId(Integer order_id);
	
	public ArrayList<ItemInOrderDTO> getDrinkItemsByOrderId(Integer order_id);
}
