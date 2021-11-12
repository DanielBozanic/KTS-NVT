package com.kts.sigma.service.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kts.sigma.model.ItemInMenu;
import com.kts.sigma.repository.ItemInMenuRepository;
import com.kts.sigma.service.ItemInMenuService;

@Service
public class ItemInMenuServiceImpl implements ItemInMenuService{
	@Autowired
	private ItemInMenuRepository itemInMenuRepository;
	
	@Override
	public Iterable<ItemInMenu> getAll() {
		return itemInMenuRepository.findAll();
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
	public Optional<ItemInMenu> findById(Integer id)
	{
		return itemInMenuRepository.findById(id);
	}
}
