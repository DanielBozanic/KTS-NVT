package com.kts.sigma.service;

import java.util.Optional;

import com.kts.sigma.model.ItemInOrder;

public interface ItemInOrderService {
	public Iterable<ItemInOrder> getAll();
	
	public Optional<ItemInOrder> findById(Integer id);
	
	public ItemInOrder save(ItemInOrder item);
	
	public void deleteById(Integer id);
}
