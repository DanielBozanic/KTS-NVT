package com.kts.sigma.service.serviceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kts.sigma.Exception.AccessForbiddenException;
import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.Utility.Mapper;
import com.kts.sigma.dto.ItemInOrderDTO;
import com.kts.sigma.dto.OrderDTO;
import com.kts.sigma.model.Drink;
import com.kts.sigma.model.Employee;
import com.kts.sigma.model.Food;
import com.kts.sigma.model.ItemInOrder;
import com.kts.sigma.model.ItemInOrderState;
import com.kts.sigma.model.RestaurantOrder;
import com.kts.sigma.model.RestaurantTable;
import com.kts.sigma.model.TableState;
import com.kts.sigma.model.Waiter;
import com.kts.sigma.repository.EmployeeRepository;
import com.kts.sigma.repository.ItemInOrderRepository;
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
	
	@Autowired
	private ItemInOrderRepository iioRepo;
	
	@Autowired
	private EmployeeRepository empRep;
	
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
	public RestaurantOrder save(OrderDTO item, Integer code) {
		Employee worker = empRep.findByCode(code);
		if(worker == null || !(worker instanceof Waiter) || worker.getId() != item.getWaiterId())
		{
			throw new AccessForbiddenException();
		}
		
		RestaurantOrder order = new RestaurantOrder();
		order.setOrderDateTime(LocalDateTime.now());
		
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
				iioService.saveWithoutCode(dto);
			}
			
		}
		
		table.setState(TableState.IN_PROGRESS);
		tableRepo.save(table);
				
		
		return orderRepository.save(order);
	}
	
	@Override
	public void deleteById(Integer id, Integer code) {
		RestaurantOrder order = orderRepository.findById(id).orElse(null);
		if(order == null)
		{
			throw new ItemNotFoundException(id, "order");
		}
		
		Employee worker = empRep.findByCode(code);
		if(worker == null || !(worker instanceof Waiter) || worker.getId() != order.getWaiter().getId())
		{
			throw new AccessForbiddenException();
		}
		
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

	@Override
	public Iterable<ItemInOrderDTO> getAllItems(Integer id) {
		RestaurantOrder order = orderRepository.findById(id).orElse(null);
		if(order == null)
		{
			throw new ItemNotFoundException(id, "order");
		}
		
		ArrayList<ItemInOrderDTO> dtos = new ArrayList<>();
		
		for (ItemInOrder item : order.getItems()) {
			ItemInOrderDTO dto = Mapper.mapper.map(item, ItemInOrderDTO.class);
			dto.setDescription(item.getItem().getItem().getDescription());
			dto.setName(item.getItem().getItem().getName());
			dto.setSellingPrice(item.getItem().getSellingPrice());
			
			if(item.getItem().getItem() instanceof Food) {
				dto.setFood(true);
			}else {
				dto.setFood(false);
			}
			
			dtos.add(dto);
		}
		
		return dtos;
	}

	@Override
	public Iterable<ItemInOrderDTO> getAllDrinks(Integer id) {
		RestaurantOrder order = orderRepository.findById(id).orElse(null);
		if(order == null)
		{
			throw new ItemNotFoundException(id, "order");
		}
		
		ArrayList<ItemInOrderDTO> dtos = new ArrayList<>();
		
		for (ItemInOrder item : order.getItems()) {
			if(item.getItem().getItem() instanceof Drink) {
				ItemInOrderDTO dto = Mapper.mapper.map(item, ItemInOrderDTO.class);
				dto.setDescription(item.getItem().getItem().getDescription());
				dto.setName(item.getItem().getItem().getName());
				dto.setSellingPrice(item.getItem().getSellingPrice());
				dto.setFood(false);
				dtos.add(dto);
			}
		}
		
		return dtos;
	}

	@Override
	public Iterable<ItemInOrderDTO> getAllFoods(Integer id) {
		RestaurantOrder order = orderRepository.findById(id).orElse(null);
		if(order == null)
		{
			throw new ItemNotFoundException(id, "order");
		}
		
		ArrayList<ItemInOrderDTO> dtos = new ArrayList<>();
		
		for (ItemInOrder item : order.getItems()) {
			if(item.getItem().getItem() instanceof Food) {
				ItemInOrderDTO dto = Mapper.mapper.map(item, ItemInOrderDTO.class);
				dto.setDescription(item.getItem().getItem().getDescription());
				dto.setName(item.getItem().getItem().getName());
				dto.setSellingPrice(item.getItem().getSellingPrice());
				dto.setFood(true);
				dtos.add(dto);
			}
		}
		
		return dtos;
	}

	@Override
	public ItemInOrderDTO addItemToOrder(ItemInOrderDTO item, Integer code, Integer orderId) {
		RestaurantOrder order = orderRepository.findById(orderId).orElse(null);
		if(order == null)
		{
			throw new ItemNotFoundException(orderId, "order");
		}
		
		Employee worker = empRep.findByCode(code);
		if(worker == null || !(worker instanceof Waiter) || worker.getId() != order.getWaiter().getId())
		{
			throw new AccessForbiddenException();
		}
		
		BigDecimal price = BigDecimal.ZERO;
		
		for(int i = 0; i < item.getQuantity(); i++) {
			price = price.add(item.getSellingPrice());
			item.setOrderId(order.getId());
			item.setState(ItemInOrderState.NEW);
			iioService.saveWithoutCode(item);
		}
		
		order.setTotalPrice(order.getTotalPrice().add(price));
		orderRepository.save(order);
		
		return item;
	}

	@Override
	public void removeItemFromOrder(Integer itemId, Integer code, Integer orderId) {
		RestaurantOrder order = orderRepository.findById(orderId).orElse(null);
		if(order == null)
		{
			throw new ItemNotFoundException(orderId, "order");
		}
		
		Employee worker = empRep.findByCode(code);
		if(worker == null || !(worker instanceof Waiter) || worker.getId() != order.getWaiter().getId())
		{
			throw new AccessForbiddenException();
		}
		
		Set<ItemInOrder> items = order.getItems();
		
		for (ItemInOrder item : items) {
			if(item.getId() == itemId) {
				iioService.deleteById(itemId);
				
				items.remove(item);
				order.setItems(items);
				order.setTotalPrice(order.getTotalPrice().subtract(item.getItem().getSellingPrice()));
				orderRepository.save(order);
				return;
			}
		}
		
		throw new ItemNotFoundException(itemId, "item in order");
	}
}
