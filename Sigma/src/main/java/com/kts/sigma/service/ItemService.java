package com.kts.sigma.service;

import java.util.Optional;

import com.kts.sigma.model.Item;

public interface ItemService {

	public Iterable<Item> getAll();
	
	public Optional<Item> findById(Integer id);
	
	public Item save(Item item);
	
	public void deleteById(Integer id);
}
