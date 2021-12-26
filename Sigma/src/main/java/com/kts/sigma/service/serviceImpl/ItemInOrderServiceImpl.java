package com.kts.sigma.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kts.sigma.Exception.AccessForbiddenException;
import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.Utility.Mapper;
import com.kts.sigma.dto.ItemInOrderDTO;
import com.kts.sigma.model.Bartender;
import com.kts.sigma.model.Cook;
import com.kts.sigma.model.Drink;
import com.kts.sigma.model.Employee;
import com.kts.sigma.model.Food;
import com.kts.sigma.model.ItemInMenu;
import com.kts.sigma.model.ItemInOrder;
import com.kts.sigma.model.ItemInOrderState;
import com.kts.sigma.model.OrderState;
import com.kts.sigma.model.RestaurantOrder;
import com.kts.sigma.model.User;
import com.kts.sigma.repository.EmployeeRepository;
import com.kts.sigma.repository.ItemInMenuRepository;
import com.kts.sigma.repository.ItemInOrderRepository;
import com.kts.sigma.repository.OrderRepository;
import com.kts.sigma.repository.UserRepository;
import com.kts.sigma.service.ItemInOrderService;
import com.kts.sigma.service.ItemService;
import com.kts.sigma.service.OrderService;

@Service
public class ItemInOrderServiceImpl implements ItemInOrderService{
	@Autowired
	private ItemInOrderRepository itemInOrderRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private OrderRepository oRepository;
	
	@Autowired
	private UserRepository uRepository;
	
	@Autowired
	private ItemInMenuRepository iimRepository;
	
	@Autowired
	private ItemService iService;
	
	
	@Override
	public Iterable<ItemInOrderDTO> getAll() {
		List<ItemInOrder> items = itemInOrderRepository.findAll();
		ArrayList<ItemInOrderDTO> results = new ArrayList<ItemInOrderDTO>();
		
		for (ItemInOrder item : items) {
			ItemInOrderDTO dto = Mapper.mapper.map(item, ItemInOrderDTO.class);
			
			if (iService.findById(item.getItem().item.getId()).isFood()) {
				dto.setFood(true);
			}
			
			dto.setDescription(item.getItem().getItem().getDescription());
			dto.setName(item.getItem().getItem().getName());
			dto.setSellingPrice(item.getItem().getSellingPrice());
			
			results.add(dto);
		}
		
		return results;
	}
	
	@Override
	public ArrayList<ItemInOrderDTO> getFoodItemsByOrderId(Integer order_id) {
		List<ItemInOrder> items = itemInOrderRepository.getByOrderId(order_id);
		ArrayList<ItemInOrderDTO> results = new ArrayList<ItemInOrderDTO>();
		
		for (ItemInOrder item : items) {
			
			ItemInOrderDTO dto = Mapper.mapper.map(item, ItemInOrderDTO.class);
			
			if (iService.findById(item.getItem().item.getId()).isFood()) {
				dto.setFood(true);
			}
			
			dto.setDescription(item.getItem().getItem().getDescription());
			dto.setName(item.getItem().getItem().getName());
			dto.setSellingPrice(item.getItem().getSellingPrice());
			if(dto.isFood())
				results.add(dto);
		}
		
		return results;
	}
	
	@Override
	public ArrayList<ItemInOrderDTO> getDrinkItemsByOrderId(Integer order_id) {
		List<ItemInOrder> items = itemInOrderRepository.getByOrderId(order_id);
		ArrayList<ItemInOrderDTO> results = new ArrayList<ItemInOrderDTO>();
		
		for (ItemInOrder item : items) {
			ItemInOrderDTO dto = Mapper.mapper.map(item, ItemInOrderDTO.class);
			
			if (iService.findById(item.getItem().item.getId()).isFood()) {
				dto.setFood(true);
			}
			
			dto.setDescription(item.getItem().getItem().getDescription());
			dto.setName(item.getItem().getItem().getName());
			dto.setSellingPrice(item.getItem().getSellingPrice());
			if(!dto.isFood())
				results.add(dto);
		}
		
		return results;
	}
	
	@Override
	public ArrayList<ItemInOrderDTO> getOrderItems(Integer order_id) {
		List<ItemInOrder> items = itemInOrderRepository.getByOrderId(order_id);
		ArrayList<ItemInOrderDTO> results = new ArrayList<ItemInOrderDTO>();
		
		for (ItemInOrder item : items) {
			ItemInOrderDTO dto = Mapper.mapper.map(item, ItemInOrderDTO.class);
			
			if (iService.findById(item.getItem().item.getId()).isFood()) {
				dto.setFood(true);
			}
			
			dto.setDescription(item.getItem().getItem().getDescription());
			dto.setName(item.getItem().getItem().getName());
			dto.setSellingPrice(item.getItem().getSellingPrice());
			
			results.add(dto);
		}
		
		return results;
	}

	@Override
	public ItemInOrder save(ItemInOrderDTO i, Integer code) {
		Employee worker = employeeRepository.findByCode(code);
		if(worker == null)
		{
			throw new AccessForbiddenException();
		}
		
		ItemInOrder item = new ItemInOrder();
		item.setId(i.getId());
		
		RestaurantOrder order = oRepository.findById(i.getOrderId()).orElse(null);
		if(order == null) {
			throw new ItemNotFoundException(i.getOrderId(), "order");
		}
		item.setOrder(order);
		
		ItemInMenu iim = iimRepository.findById(i.getItemId()).orElse(null);
		if(iim == null) {
			throw new ItemNotFoundException(i.getItemId(), "item in menu");
		}
		item.setItem(iim);
		
		if(i.getEmployeeId() != null) {
			User employee = uRepository.findById(i.getEmployeeId()).orElse(null);
			if(employee == null) {
				throw new ItemNotFoundException(i.getEmployeeId(), "employee");
			}
			item.setEmployee((Employee) employee);
		}
		
		item.setState(i.getState());
		
		return itemInOrderRepository.save(item);
	}
	
	@Override
	public ItemInOrder saveWithoutCode(ItemInOrderDTO i) {
		ItemInOrder item = new ItemInOrder();
//		item.setId(i.getId());
		item.setOrder(oRepository.getOne(i.getOrderId()));
		
		ItemInMenu iim = iimRepository.findById(i.getItemId()).orElse(null);
		if(iim == null) {
			throw new ItemNotFoundException(i.getItemId(), "item in menu");
		}
		item.setItem(iim);
		
		if(i.getEmployeeId() != null) {
			User employee = uRepository.findById(i.getEmployeeId()).orElse(null);
			if(employee == null) {
				throw new ItemNotFoundException(i.getEmployeeId(), "employee");
			}
			item.setEmployee((Employee) employee);
		}
		
		item.setState(i.getState());
		
		return itemInOrderRepository.save(item);
	}
	
	@Override
	public void deleteById(Integer id) {
		ItemInOrder item = itemInOrderRepository.findById(id).orElse(null);
		if(item == null)
		{
			throw new ItemNotFoundException(id, "item in order");
		}
		itemInOrderRepository.deleteById(id);
	}
	
	@Override
	public ItemInOrderDTO findById(Integer id)
	{
		ItemInOrder item = itemInOrderRepository.findById(id).orElse(null);
		if(item == null)
		{
			throw new ItemNotFoundException(id, "item in order");
		}
		
		ItemInOrderDTO result = Mapper.mapper.map(item, ItemInOrderDTO.class);
		result.setDescription(item.getItem().getItem().getDescription());
		result.setName(item.getItem().getItem().getName());
		result.setSellingPrice(item.getItem().getSellingPrice());
		
		return result;
	}

	@Override
	public ItemInOrderDTO changeState(Integer id, ItemInOrderState state, Integer employeeCode) {
		
		Employee employee = employeeRepository.findByCode(employeeCode);
		if(employee == null)
		{
			throw new AccessForbiddenException();
		}
		
		ItemInOrder item = itemInOrderRepository.findById(id).orElse(null);
		if(item == null)
		{
			throw new ItemNotFoundException(id, "item in order");
		}
		
		boolean allAreDone = true;
		for (ItemInOrder i : item.getOrder().items) {
			if(i.getState() != ItemInOrderState.TO_DELIVER)
			{
				allAreDone = false;
			}
		}
		
		if(allAreDone)
		{
			item.getOrder().setState(OrderState.DONE);
			oRepository.save(item.getOrder());
		}

		if(state == ItemInOrderState.IN_PROGRESS)
		{
			if(employee instanceof Cook && item.getItem().item instanceof Food)
			{
				item.setEmployee(employee);
			}
			else if(employee instanceof Bartender && item.getItem().item instanceof Drink)
			{
				item.setEmployee(employee);
			}
			else
			{
				throw new AccessForbiddenException();
			}
		}
		
		item.setState(state);
		ItemInOrder returnItem = itemInOrderRepository.save(item);
		
		return Mapper.mapper.map(returnItem, ItemInOrderDTO.class);
	}

	@Override
	public void put(ItemInOrderDTO i, Integer code) {
		Employee worker = employeeRepository.findByCode(code);
		if(worker == null)
		{
			throw new AccessForbiddenException();
		}
		
		ItemInOrder check = itemInOrderRepository.findById(i.getId()).orElse(null);
		if(check == null)
		{
			throw new ItemNotFoundException(i.getId(), "item in order");
		}
		
		
		ItemInOrder item = new ItemInOrder();
		item.setId(i.getId());
		
		RestaurantOrder order = oRepository.findById(i.getOrderId()).orElse(null);
		if(order == null) {
			throw new ItemNotFoundException(i.getOrderId(), "order");
		}
		item.setOrder(order);
		
		ItemInMenu iim = iimRepository.findById(i.getItemId()).orElse(null);
		if(iim == null) {
			throw new ItemNotFoundException(i.getItemId(), "item in menu");
		}
		item.setItem(iim);
		
		if(i.getEmployeeId() != null) {
			Employee employee = employeeRepository.findById(i.getEmployeeId()).orElse(null);
			if(employee == null) {
				throw new ItemNotFoundException(i.getEmployeeId(), "employee");
			}
			item.setEmployee((Employee) employee);
		}
		
		item.setState(i.getState());
		
		
		itemInOrderRepository.save(item);
	}
}
