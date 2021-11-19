package com.kts.sigma.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.Utility.Mapper;
import com.kts.sigma.dto.ItemDTO;
import com.kts.sigma.model.Food;
import com.kts.sigma.model.ItemInMenu;
import com.kts.sigma.repository.ItemInMenuRepository;
import com.kts.sigma.service.ItemInMenuService;

@Service
public class ItemInMenuServiceImpl implements ItemInMenuService{
	@Autowired
	private ItemInMenuRepository itemInMenuRepository;
	
	@Override
	public Iterable<ItemDTO> getAll() {
		List<ItemInMenu> items = itemInMenuRepository.findAll();
		ArrayList<ItemDTO> results = new ArrayList<ItemDTO>();
		
		for (ItemInMenu item : items) {
			ItemDTO dto = Mapper.mapper.map(item.getItem(), ItemDTO.class);
			
			dto.setSellingPrice(item.getSellingPrice());
			
			if(item.getItem() instanceof Food) {
				dto.setFood(true);
			}
			
			results.add(dto);
		}
		
		return results;
	}
	
	@Override
	public ItemInMenu save(ItemInMenu item) {
		return itemInMenuRepository.save(item);
	}
	
	@Override
	public void deleteById(Integer id) {
		itemInMenuRepository.deleteById(id);
	}
	
	@Override
	public ItemDTO findById(Integer id)
	{
		ItemInMenu item = itemInMenuRepository.findById(id).orElse(null);
		if(item == null)
		{
			throw new ItemNotFoundException(id, "item in menu");
		}
		
		ItemDTO result = Mapper.mapper.map(item.getItem(), ItemDTO.class);
		result.setSellingPrice(item.getSellingPrice());
		
		if(item.getItem() instanceof Food) {
			result.setFood(true);
		}
		
		return result;
	}
}
