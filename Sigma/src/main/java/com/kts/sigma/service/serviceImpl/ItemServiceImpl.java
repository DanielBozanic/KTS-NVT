package com.kts.sigma.service.serviceImpl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.Utility.Mapper;
import com.kts.sigma.dto.ItemDTO;
import com.kts.sigma.model.Drink;
import com.kts.sigma.model.Food;
import com.kts.sigma.model.FoodType;
import com.kts.sigma.model.Item;
import com.kts.sigma.repository.DrinkRepository;
import com.kts.sigma.repository.FoodRepository;
import com.kts.sigma.repository.ItemRepository;
import com.kts.sigma.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private FoodRepository foodRepository;
	
	@Autowired
	private DrinkRepository drinkRepository;
	
	@Override
	public Iterable<ItemDTO> getAll() {
		List<Item> items = itemRepository.findAll();
		ArrayList<ItemDTO> results = new ArrayList<ItemDTO>();
		
		for (Item item : items) {
			ItemDTO dto = Mapper.mapper.map(item, ItemDTO.class);
			
			if(item instanceof Food) {
				dto.setFood(true);
			}
			
			results.add(dto);
		}
		
		return results;
	}
	
	@Override
	public List<ItemDTO> getItemsByCurrentPage(Integer currentPage, Integer pageSize) {
		Pageable page = PageRequest.of(currentPage, pageSize);
		List<Item> items = itemRepository.findAll(page).toList();
		List<ItemDTO> results = new ArrayList<ItemDTO>();
		
		for (Item item : items) {
			ItemDTO dto = Mapper.mapper.map(item, ItemDTO.class);
			
			if(item instanceof Food) {
				dto.setFood(true);
			}
			
			results.add(dto);
		}
		
		return results;
	}
	
	@Override
	public Item createNewItem(ItemDTO itemDto) {
		Item item = null;
		if (itemDto.isFood()) {
			item = Mapper.mapper.map(itemDto, Food.class);
		} else {
			item = Mapper.mapper.map(itemDto, Drink.class);
		}
		item = itemRepository.save(item);
		return item;
	}
	
	@Override
	public void deleteById(Integer id) {
		Item item = itemRepository.findById(id).orElse(null);
		if(item == null)
		{
			throw new ItemNotFoundException(id, "item");
		}
		itemRepository.deleteById(id);
	}
	
	@Override
	public ItemDTO findById(Integer id)
	{
		Item item = itemRepository.findById(id).orElse(null);
		if(item == null)
		{
			throw new ItemNotFoundException(id, "item");
		}
		ItemDTO result = Mapper.mapper.map(item, ItemDTO.class);
		if(item instanceof Food) {
			result.setFood(true);
		}
		return result;
	}

	@Override
	public ArrayList<ItemDTO> getItemsBySearchTerm(String searchTerm) {
		ArrayList<Item> items = itemRepository.search(searchTerm);
		ArrayList<ItemDTO> results = new ArrayList<ItemDTO>();
		for (Item item : items) {
			ItemDTO dto = Mapper.mapper.map(item, ItemDTO.class);
			
			if (item instanceof Food) {
				dto.setFood(true);
			}
			
			results.add(dto);
		}
		return results;
	}

	@Override
	public ArrayList<ItemDTO> getItemsByFoodType(FoodType foodType) {
		ArrayList<Food> food = foodRepository.getItemsByFoodType(foodType);
		ArrayList<ItemDTO> results = new ArrayList<ItemDTO>();
		for (Food f : food) {
			ItemDTO dto = Mapper.mapper.map(f, ItemDTO.class);
			dto.setFood(true);
			results.add(dto);
		}
		return results;
	}

	@Override
	public List<ItemDTO> getDrinks() {
		List<ItemDTO> drinksDto = new ArrayList<ItemDTO>();
		List<Drink> drinks = drinkRepository.findAll();
		
		for (Drink d : drinks) {
			ItemDTO dto = Mapper.mapper.map(d, ItemDTO.class);
			dto.setFood(false);
			drinksDto.add(dto);
		}
		return drinksDto;
	}
}
