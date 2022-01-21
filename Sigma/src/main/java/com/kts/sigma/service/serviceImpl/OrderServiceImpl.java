package com.kts.sigma.service.serviceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kts.sigma.Exception.AccessForbiddenException;
import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.Exception.ItemNotValidException;
import com.kts.sigma.Utility.Mapper;
import com.kts.sigma.dto.ItemInOrderDTO;
import com.kts.sigma.dto.OrderDTO;
import com.kts.sigma.model.Bartender;
import com.kts.sigma.model.Cook;
import com.kts.sigma.model.Drink;
import com.kts.sigma.model.Employee;
import com.kts.sigma.model.Food;
import com.kts.sigma.model.ItemInOrder;
import com.kts.sigma.model.ItemInOrderState;
import com.kts.sigma.model.OrderState;
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
import com.kts.sigma.service.ItemService;
import com.kts.sigma.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private TableRepository tableRepo;
	
	@Autowired
	private ItemInOrderService iioService;
	
	@Autowired
	private ItemService iService;
	
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
			dto.setItems(iioService.getOrderItems(order.getId()));
			results.add(dto);
		}
		
		return results;
	}
	
	@Override
	public Iterable<OrderDTO> getAllFoodOrders() {
		List<RestaurantOrder> orders = orderRepository.findAll();
		ArrayList<OrderDTO> results = new ArrayList<OrderDTO>();
		
		for (RestaurantOrder order : orders) {

			OrderDTO dto = Mapper.mapper.map(order, OrderDTO.class);
			dto.setItems(iioService.getFoodItemsByOrderId(order.getId()));
			if(dto.getItems().size() > 0)
				results.add(dto);
		}
		
		return results;
	}
	
	@Override
	public Iterable<OrderDTO> getAllDrinkOrders() {
		List<RestaurantOrder> orders = orderRepository.findAll();
		ArrayList<OrderDTO> results = new ArrayList<OrderDTO>();
		
		for (RestaurantOrder order : orders) {

			OrderDTO dto = Mapper.mapper.map(order, OrderDTO.class);
			dto.setItems(iioService.getDrinkItemsByOrderId(order.getId()));
			if(dto.getItems().size() > 0)
				results.add(dto);
		}
		
		return results;
	}
	
	@Override
	public RestaurantOrder save(OrderDTO item, Integer code) {
		
		if(item.getTableId() == null) {
			throw new ItemNotValidException(item.getId(), "order DTO", "table id");
		}
		
		Employee worker = empRep.findByCode(code);
		if(worker == null || !(worker instanceof Waiter))
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
		
		order.setWaiter((Waiter) worker);
		
		order.setState(OrderState.NEW);
		
		double total = 0;
		
		if( (item.getItems() == null) || (item.getItems().size() == 0) ) {
			throw new ItemNotValidException(item.getId(), "order DTO", "items");
		}
		
		for (ItemInOrderDTO dto : item.getItems()) {
			
			if(dto.getQuantity() == null) {
				throw new ItemNotValidException(item.getId(), "order DTO", "quantity of item");
			}
			
			for(int i = 0; i < dto.getQuantity(); i++) {
				
				if(dto.getSellingPrice() == null) {
					throw new ItemNotValidException(item.getId(), "order DTO", "selling price of item");
				}
				
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
				
		
		return o;
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
		
		RestaurantTable table = order.getTable();
		table.setState(TableState.FREE);
		tableRepo.save(table);
		
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
	@Transactional
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
	@Transactional
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
	@Transactional
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
			item.setOrderId(order.getId());
			item.setState(ItemInOrderState.NEW);
			ItemInOrder iio = iioService.saveWithoutCode(item);
			price = price.add(item.getSellingPrice());
			
			if(iio.getItem().getItem() instanceof Food) {
				item.setFood(true);
			}else {
				item.setFood(false);
			}
		}
		
		order.setTotalPrice(order.getTotalPrice().add(price));
		orderRepository.save(order);
		
		return item;
	}

	@Override
	@Transactional
	public ItemInOrder removeItemFromOrder(Integer itemId, Integer code, Integer orderId) {
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
				
				if(items.size() == 0) {
					RestaurantTable table = order.getTable();
					table.setState(TableState.FREE);
					tableRepo.save(table);
					order.setTable(table);
					order.setState(OrderState.CHARGED);
					order.setTotalPrice(BigDecimal.ZERO);
				}
				
				orderRepository.save(order);
				return  item;
			}
		}
		
		throw new ItemNotFoundException(itemId, "item in order");
	}

	@Override
	public void changeState(OrderState state, Integer code, Integer orderId) {
		RestaurantOrder order = orderRepository.findById(orderId).orElse(null);
		if(order == null)
		{
			throw new ItemNotFoundException(orderId, "order");
		}
		
		Employee worker = empRep.findByCode(code);
		//!(worker instanceof Waiter) || worker.getId() != order.getWaiter().getId()
		if(worker == null)
		{
			throw new AccessForbiddenException();
		}
		
		order.setState(state);
		orderRepository.save(order);
		
	}
	
	@Override
	public void changeStateWithoutCode(OrderState state, Integer orderId) {
		RestaurantOrder order = orderRepository.findById(orderId).orElse(null);
		if(order == null)
		{
			throw new ItemNotFoundException(orderId, "order");
		}
		order.setState(state);
		orderRepository.save(order);
		
	}
}
