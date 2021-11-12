package com.kts.sigma.service.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kts.sigma.model.RestaurantOrder;
import com.kts.sigma.repository.OrderRepository;
import com.kts.sigma.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	public Iterable<RestaurantOrder> getAll() {
		return orderRepository.findAll();
	}
	
	@Override
	public RestaurantOrder save(RestaurantOrder item) {
		return orderRepository.save(item);
	}
	
	@Override
	public void deleteById(Integer id) {
		orderRepository.deleteById(id);
	}
	@Override
	public Optional<RestaurantOrder> findById(Integer id)
	{
		return orderRepository.findById(id);
	}
}
