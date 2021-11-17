package com.kts.sigma.service.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.Utility.Mapper;
import com.kts.sigma.dto.ItemInOrderDTO;
import com.kts.sigma.dto.MenuDTO;
import com.kts.sigma.dto.OrderDTO;
import com.kts.sigma.model.ItemInOrderState;
import com.kts.sigma.model.Menu;
import com.kts.sigma.model.RestaurantOrder;
import com.kts.sigma.model.RestaurantTable;
import com.kts.sigma.model.Waiter;
import com.kts.sigma.repository.OrderRepository;
import com.kts.sigma.repository.TableRepository;
import com.kts.sigma.repository.UserRepository;
import com.kts.sigma.service.ItemInOrderService;
import com.kts.sigma.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private TableRepository tableRepo;
	
	@Autowired
	private ItemInOrderService iioService;
	
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
	public RestaurantOrder save(OrderDTO item) {
		RestaurantOrder order = new RestaurantOrder();
		
		RestaurantTable table = tableRepo.findById(item.getTableId()).orElse(null);
		if(table == null) {
			throw new ItemNotFoundException(item.getTableId(), "table");
		}
		order.setTable(table);
		
		Waiter waiter = (Waiter) userRepo.findById(item.getWaiterId()).orElse(null);
		if(waiter == null) {
			throw new ItemNotFoundException(item.getWaiterId(), "table");
		}
		order.setWaiter(waiter);
		
		order.setState(item.getState());
		
		double total = 0;
		
		for (ItemInOrderDTO dto : item.getItems()) {
			
			for(int i = 0; i < dto.getQuantity(); i++) {
				total += dto.getSellingPrice().doubleValue();
			}
			
		}
		
		order.setTotalPrice(new BigDecimal(total));
		
		RestaurantOrder o = orderRepository.save(order);
		
		for (ItemInOrderDTO dto : item.getItems()) {
			
			for(int i = 0; i < dto.getQuantity(); i++) {
				dto.setOrderId(o.getId());
				dto.setState(ItemInOrderState.NEW);
				iioService.save(dto);
			}
			
		}
				
		
		return orderRepository.save(order);
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
			throw new ItemNotFoundException(id, "order");
		}
		
		OrderDTO result = Mapper.mapper.map(order, OrderDTO.class);
	    return result;
	}
}
