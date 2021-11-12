package com.kts.sigma.service.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kts.sigma.model.ItemInOrder;
import com.kts.sigma.repository.ItemInOrderRepository;
import com.kts.sigma.service.ItemInOrderService;

@Service
public class ItemInOrderServiceImpl implements ItemInOrderService{
	@Autowired
	private ItemInOrderRepository itemInOrderRepository;
	
	@Override
	public Iterable<ItemInOrder> getAll() {
		return itemInOrderRepository.findAll();
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
	public Optional<ItemInOrder> findById(Integer id)
	{
		return itemInOrderRepository.findById(id);
	}
}
