package com.kts.sigma.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kts.sigma.Exception.AccessForbiddenException;
import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.Utility.Mapper;
import com.kts.sigma.dto.ItemDTO;
import com.kts.sigma.dto.ItemInOrderDTO;
import com.kts.sigma.model.Bartender;
import com.kts.sigma.model.Cook;
import com.kts.sigma.model.Drink;
import com.kts.sigma.model.Employee;
import com.kts.sigma.model.Food;
import com.kts.sigma.model.ItemInMenu;
import com.kts.sigma.model.ItemInOrder;
import com.kts.sigma.model.ItemInOrderState;
import com.kts.sigma.model.RestaurantOrder;
import com.kts.sigma.model.TableState;
import com.kts.sigma.model.User;
import com.kts.sigma.repository.EmployeeRepository;
import com.kts.sigma.repository.ItemInMenuRepository;
import com.kts.sigma.repository.ItemInOrderRepository;
import com.kts.sigma.repository.OrderRepository;
import com.kts.sigma.repository.UserRepository;
import com.kts.sigma.service.ItemInOrderService;

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
	
	
	
	@Override
	public Iterable<ItemDTO> getAll() {
		List<ItemInOrder> items = itemInOrderRepository.findAll();
		ArrayList<ItemDTO> results = new ArrayList<ItemDTO>();
		
		for (ItemInOrder item : items) {
			ItemDTO dto = Mapper.mapper.map(item.getItem().getItem(), ItemDTO.class);
			
			dto.setSellingPrice(item.getItem().getSellingPrice());
			
			if(item.getItem().getItem() instanceof Food) {
				dto.setFood(true);
			}
			
			results.add(dto);
		}
		
		return results;
	}
	
	@Override
	public ItemInOrder save(ItemInOrderDTO i) {
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
	public void deleteById(Integer id) {
		itemInOrderRepository.deleteById(id);
	}
	@Override
	public ItemDTO findById(Integer id)
	{
		ItemInOrder item = itemInOrderRepository.findById(id).orElse(null);
		if(item == null)
		{
			throw new ItemNotFoundException(id, "item in order");
		}
		
		ItemDTO result = Mapper.mapper.map(item.getItem().getItem(), ItemDTO.class);
		result.setSellingPrice(item.getItem().getSellingPrice());
		
		if(item.getItem().getItem() instanceof Food) {
			result.setFood(true);
		}
		
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
	public void put(ItemInOrderDTO i) {
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
			User employee = uRepository.findById(i.getEmployeeId()).orElse(null);
			if(employee == null) {
				throw new ItemNotFoundException(i.getEmployeeId(), "employee");
			}
			item.setEmployee((Employee) employee);
		}
		
		item.setState(i.getState());
		
		
		itemInOrderRepository.save(item);
	}
}
