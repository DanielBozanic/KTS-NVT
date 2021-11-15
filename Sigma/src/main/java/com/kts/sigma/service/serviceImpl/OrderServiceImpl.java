package com.kts.sigma.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.Utility.Mapper;
import com.kts.sigma.dto.MenuDTO;
import com.kts.sigma.dto.OrderDTO;
import com.kts.sigma.model.Menu;
import com.kts.sigma.model.RestaurantOrder;
import com.kts.sigma.repository.OrderRepository;
import com.kts.sigma.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	public Iterable<OrderDTO> getAll() {
		List<RestaurantOrder> orders = orderRepository.findAll();
		ArrayList<OrderDTO> results = new ArrayList<OrderDTO>();
		
		for (RestaurantOrder order : orders) {
			OrderDTO dto = Mapper.mapper.map(order, OrderDTO.class);
			results.add(dto);
		}
		
		return results;
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
	public OrderDTO findById(Integer id)
	{
		RestaurantOrder order = orderRepository.findById(id).orElse(null);
		if(order == null)
		{
			throw new ItemNotFoundException(id);
		}
		
		OrderDTO result = Mapper.mapper.map(order, OrderDTO.class);
	    return result;
	}
}
