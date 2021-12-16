package com.kts.sigma.service;

import java.util.ArrayList;
import java.util.List;

import com.kts.sigma.dto.ItemDTO;
import com.kts.sigma.dto.MenuDTO;

public interface MenuService {
	
	List<MenuDTO> getAll();
	
	List<MenuDTO> getActiveNonExpiredMenus();
	
	MenuDTO findById(Integer id);
	
	MenuDTO addMenu(MenuDTO item);
	
	void deleteMenuById(Integer id);
	
	void addItemToMenu(ItemDTO itemDto, Integer menuId);
	
	ArrayList<ItemDTO> getItemsInMenu(Integer menuId);
	
	List<ItemDTO> getItemsInMenuByCurrentPage(Integer menuId, Integer currentPage, Integer pageSize);
	
	Integer getNumberOfActiveItemInMenuRecordsByMenuId(Integer menuId);
	
	void removeItemFromMenu(Integer itemId, Integer menuId);
}
