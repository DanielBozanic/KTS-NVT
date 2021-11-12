package com.kts.sigma.service;

import java.util.Optional;

import com.kts.sigma.model.ItemInMenu;

public interface ItemInMenuService{
	public Iterable<ItemInMenu> getAll();
	
	public Optional<ItemInMenu> findById(Integer id);
	
	public ItemInMenu save(ItemInMenu item);
	
	public void deleteById(Integer id);
}
