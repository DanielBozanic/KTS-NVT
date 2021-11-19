package com.kts.sigma.service;

import java.util.ArrayList;
import com.kts.sigma.dto.ItemDTO;
import com.kts.sigma.model.FoodType;

public interface ItemService {

	Iterable<ItemDTO> getAll();
	
	ItemDTO findById(Integer id);
	
	ItemDTO createNewItem(ItemDTO itemDto);
	
	void deleteById(Integer id);
	
	ArrayList<ItemDTO> getItemsBySearchTerm(String searchTerm);
	
	ArrayList<ItemDTO> getItemsByFoodType(FoodType foodType);
}
