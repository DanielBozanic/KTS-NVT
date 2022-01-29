package com.kts.sigma.service;

import java.util.ArrayList;
import java.util.List;

import com.kts.sigma.dto.ItemDTO;
import com.kts.sigma.model.FoodType;
import com.kts.sigma.model.Item;

public interface ItemService {

	Iterable<ItemDTO> getAll();
	
	List<ItemDTO> getItemsByCurrentPage(Integer currentPage, Integer pageSize);
	
	ItemDTO findById(Integer id);
	
	Item createNewItem(ItemDTO itemDto);
	
	void deleteById(Integer id);
	
	ArrayList<ItemDTO> getItemsBySearchTerm(String searchTerm);
	
	ArrayList<ItemDTO> getItemsByFoodType(FoodType foodType);
	
	List<ItemDTO> getDrinks();
}
