package com.kts.sigma.service.serviceImpl;

import java.util.Optional;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kts.sigma.model.RestaurantOrder;
import com.kts.sigma.model.RestaurantTable;
import com.kts.sigma.repository.TableRepository;
import com.kts.sigma.service.TableService;

@Service
public class TableServiceImpl implements TableService{
	@Autowired
	private TableRepository tableRepository;
	
	@Override
	public Iterable<RestaurantTable> getAll() {
		return tableRepository.findAll();
	}
	
	@Override
	public RestaurantTable save(RestaurantTable item) {
		return tableRepository.save(item);
	}
	
	@Override
	public void deleteById(Integer id) {
		tableRepository.deleteById(id);
	}
	@Override
	public Optional<RestaurantTable> findById(Integer id)
	{
		return tableRepository.findById(id);
	}
}
