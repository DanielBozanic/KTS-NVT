package com.kts.sigma.service;

import java.util.Optional;

import com.kts.sigma.dto.ItemDTO;
import com.kts.sigma.dto.ItemInOrderDTO;
import com.kts.sigma.model.ItemInOrder;
import com.kts.sigma.model.ItemInOrderState;

public interface ItemInOrderService {
	public Iterable<ItemDTO> getAll();
	
	public ItemDTO findById(Integer id);
	
	public ItemInOrder save(ItemInOrderDTO item);
	
	public void deleteById(Integer id);
	
	public ItemInOrderDTO changeState(Integer id, ItemInOrderState state, Integer employeeCode);

	public void put(ItemInOrderDTO item);
}
