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
import com.kts.sigma.model.ItemInMenu;
import com.kts.sigma.model.ItemInOrder;
import com.kts.sigma.repository.ItemInOrderRepository;
import com.kts.sigma.service.ItemInOrderService;

@Service
public class ItemInOrderServiceImpl implements ItemInOrderService{
	@Autowired
	private ItemInOrderRepository itemInOrderRepository;
	
	@Override
	public Iterable<ItemDTO> getAll() {
		List<ItemInOrder> items = itemInOrderRepository.findAll();
		ArrayList<ItemDTO> results = new ArrayList<ItemDTO>();
		
		for (ItemInOrder item : items) {
			ItemDTO dto = Mapper.mapper.map(item.getItem().getItem(), ItemDTO.class);
			
			dto.setSellingPrice(item.getItem().getSellingPrice());
			
			if(item.getItem().getItem() instanceof Food) {
				dto.setFood(true);
			}
			
			results.add(dto);
		}
		
		return results;
	}
	
	@Override
	public ItemInOrder save(ItemInOrder item) {
		return itemInOrderRepository.save(item);
	}
	
	@Override
	public void deleteById(Integer id) {
		itemInOrderRepository.deleteById(id);
	}
	@Override
	public ItemDTO findById(Integer id)
	{
		ItemInOrder item = itemInOrderRepository.findById(id).orElse(null);
		if(item == null)
		{
			throw new ItemNotFoundException(id);
		}
		
		ItemDTO result = Mapper.mapper.map(item.getItem().getItem(), ItemDTO.class);
		result.setSellingPrice(item.getItem().getSellingPrice());
		
		if(item.getItem().getItem() instanceof Food) {
			result.setFood(true);
		}
		
		return result;
	}
}
