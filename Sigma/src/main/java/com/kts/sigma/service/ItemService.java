package com.kts.sigma.service;

import java.util.ArrayList;
import com.kts.sigma.dto.ItemDTO;
import com.kts.sigma.model.FoodType;
import com.kts.sigma.model.Item;

public interface ItemService {

	Iterable<ItemDTO> getAll();
	
	ItemDTO findById(Integer id);
	
	Item createNewItem(ItemDTO itemDto);
	
	void deleteById(Integer id);
	
	ArrayList<ItemDTO> getItemsBySearchTerm(String searchTerm);
	
	ArrayList<ItemDTO> getItemsByFoodType(FoodType foodType);
}
