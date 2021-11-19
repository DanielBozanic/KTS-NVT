package com.kts.sigma.service;

import com.kts.sigma.dto.ItemDTO;
import com.kts.sigma.model.Item;

public interface ItemService {

	public Iterable<ItemDTO> getAll();
	
	public ItemDTO findById(Integer id);
	
	public Item save(Item item);
	
	public void deleteById(Integer id);
}
