package com.kts.sigma.service.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kts.sigma.model.Item;
import com.kts.sigma.repository.ItemRepository;
import com.kts.sigma.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private ItemRepository itemRepository;
	
	@Override
	public Iterable<Item> getAll() {
		return itemRepository.findAll();
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
	public Optional<Item> findById(Integer id)
	{
		return itemRepository.findById(id);
	}
}
