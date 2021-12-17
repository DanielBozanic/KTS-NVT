package com.kts.sigma.service;

import java.util.ArrayList;
import java.util.List;

import com.kts.sigma.dto.ItemDTO;
import com.kts.sigma.model.FoodType;
import com.kts.sigma.model.ItemInMenu;

public interface ItemInMenuService{
	public Iterable<ItemDTO> getAll();
	
	public ItemDTO findById(Integer id);
	
	public ItemInMenu save(ItemInMenu item);
	
	public void deleteById(Integer id);
	
	public ArrayList<ItemDTO> getItemsBySearchTerm(Integer menuId, String searchTerm);

	public ArrayList<ItemDTO> getItemsByFoodType(Integer menuId, FoodType foodType);

	public List<ItemDTO> getItemsByCurrentPage(Integer menuId, Integer currentPage, Integer pageSize);

	public Iterable<ItemDTO> getAllInMenu(Integer menuId);
}
