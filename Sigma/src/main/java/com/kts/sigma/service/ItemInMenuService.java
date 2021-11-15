package com.kts.sigma.service;

import java.util.Optional;

import com.kts.sigma.dto.ItemDTO;
import com.kts.sigma.model.ItemInMenu;

public interface ItemInMenuService{
	public Iterable<ItemDTO> getAll();
	
	public ItemDTO findById(Integer id);
	
	public ItemInMenu save(ItemInMenu item);
	
	public void deleteById(Integer id);
}
