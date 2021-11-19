package com.kts.sigma.service;

import java.util.ArrayList;

import com.kts.sigma.dto.ItemDTO;
import com.kts.sigma.dto.MenuDTO;
import com.kts.sigma.model.Menu;

public interface MenuService {
	Iterable<MenuDTO> getAll();
	
	MenuDTO findById(Integer id);
	
	Menu save(Menu item);
	
	void deleteById(Integer id);
	
	void addItemToMenu(ItemDTO itemDto, Integer menuId);
	
	ArrayList<ItemDTO> getItemsInMenu(Integer menuId);
	
	void removeItemFromMenu(Integer itemId, Integer menuId);
}
