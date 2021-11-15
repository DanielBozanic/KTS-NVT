package com.kts.sigma.service.serviceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.Utility.Mapper;
import com.kts.sigma.dto.ItemDTO;
import com.kts.sigma.model.Food;
import com.kts.sigma.model.Item;
import com.kts.sigma.repository.ItemRepository;
import com.kts.sigma.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private ItemRepository itemRepository;
	
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
	public Item save(Item item) {
		return itemRepository.save(item);
	}
	
	@Override
	public void deleteById(Integer id) {
		itemRepository.deleteById(id);
	}
	
	@Override
	public ItemDTO findById(Integer id)
	{
		Item item = itemRepository.findById(id).orElse(null);
		if(item == null)
		{
			throw new ItemNotFoundException(id);
		}
		ItemDTO result = Mapper.mapper.map(item, ItemDTO.class);
		if(item instanceof Food) {
			result.setFood(true);
		}
		return result;
	}
}
