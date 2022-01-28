package com.kts.sigma.service;

import java.awt.MenuItem;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.kts.sigma.Exception.AccessForbiddenException;
import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.Utility.Mapper;
import com.kts.sigma.constants.ItemConstants;
import com.kts.sigma.constants.ItemInMenuConstants;
import com.kts.sigma.constants.ItemInOrderConstants;
import com.kts.sigma.constants.OrderConstants;
import com.kts.sigma.constants.UserContants;
import com.kts.sigma.dto.ItemInOrderDTO;
import com.kts.sigma.model.Bartender;
import com.kts.sigma.model.Cook;
import com.kts.sigma.model.Drink;
import com.kts.sigma.model.Employee;
import com.kts.sigma.model.Food;
import com.kts.sigma.model.Item;
import com.kts.sigma.model.ItemInMenu;
import com.kts.sigma.model.ItemInOrder;
import com.kts.sigma.model.ItemInOrderState;
import com.kts.sigma.model.Menu;
import com.kts.sigma.model.RestaurantOrder;
import com.kts.sigma.model.RestaurantTable;
import com.kts.sigma.model.User;
import com.kts.sigma.repository.EmployeeRepository;
import com.kts.sigma.repository.ItemInMenuRepository;
import com.kts.sigma.repository.ItemInOrderRepository;
import com.kts.sigma.repository.OrderRepository;
import com.kts.sigma.repository.UserRepository;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemInOrderServiceUnitTest {
	
	@Autowired
	private ItemInOrderService itemInOrderService;
	
	@MockBean
	private ItemInOrderRepository itemInOrderRepositoryMock;
	
	@MockBean
	private EmployeeRepository employeeRepositoryMock;
	
	@MockBean
	private OrderRepository oRepositoryMock;
	
	@MockBean
	private UserRepository uRepositoryMock;
	
	@MockBean
	private ItemInMenuRepository iimRepositoryMock;
	
	@Test
	public void getAll_ValidState_ReturnsAll() {
		
		List<ItemInOrder> items = new ArrayList<ItemInOrder>();
		
		ItemInMenu menuItem = new ItemInMenu(
				ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1,
				new BigDecimal(3000),
				new Item(
						ItemConstants.DB_ITEM_ID_1,
						"Coffee",
						"With Sugar",
						new BigDecimal(2000),
						"Image"),
				new Menu(),
				true);
		
		items.add(new ItemInOrder(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1, menuItem));
		items.add(new ItemInOrder(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_2, menuItem));
		items.add(new ItemInOrder(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_3, menuItem));
		items.add(new ItemInOrder(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_4, menuItem));
		items.add(new ItemInOrder(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_5, menuItem));
		
		given(itemInOrderRepositoryMock.findAll()).willReturn(items);
		
		List<ItemInOrderDTO> results = (List<ItemInOrderDTO>) itemInOrderService.getAll();
		
		verify(itemInOrderRepositoryMock, times(1)).findAll();		
		assertEquals(results.size(), ItemInOrderConstants.TOTAL_ITEMS_IN_ORDER.intValue());
	}
	
	@Test(expected = AccessForbiddenException.class)
	public void save_InvalidCode_ReturnsNothing() {
		given(employeeRepositoryMock.findByCode(UserContants.INVALID_EMPLOYEE_CODE)).willReturn(null);
		itemInOrderService.save(new ItemInOrderDTO(), UserContants.INVALID_EMPLOYEE_CODE);
		verify(employeeRepositoryMock, times(1)).findByCode(UserContants.INVALID_EMPLOYEE_CODE);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void save_InvalidOrderId_ReturnsNothing() {
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(new Employee());
		given(oRepositoryMock.findById(OrderConstants.INVALID_ORDER_ID)).willReturn(Optional.empty());
		
		ItemInOrderDTO itemDTO = new ItemInOrderDTO();
		itemDTO.setId(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
		itemDTO.setOrderId(OrderConstants.INVALID_ORDER_ID);
		
		itemInOrderService.save(itemDTO, UserContants.DB_EMPLOYEE_ID_1_CODE);
		verify(employeeRepositoryMock, times(1)).findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
		verify(oRepositoryMock, times(1)).findById(OrderConstants.INVALID_ORDER_ID);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void save_InvalidItemId_ReturnsNothing() {
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(new Employee());
		given(oRepositoryMock.findById(OrderConstants.DB_ORDER_ID_1)).willReturn(Optional.of(new RestaurantOrder()));
		given(iimRepositoryMock.findById(ItemConstants.INVALID_ITEM_ID)).willReturn(Optional.empty());
		
		ItemInOrderDTO itemDTO = new ItemInOrderDTO();
		itemDTO.setId(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
		itemDTO.setOrderId(OrderConstants.DB_ORDER_ID_1);
		itemDTO.setItemId(ItemConstants.DB_ITEM_ID_1);
		
		itemInOrderService.save(itemDTO, UserContants.DB_EMPLOYEE_ID_1_CODE);
		
		verify(employeeRepositoryMock, times(1)).findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
		verify(oRepositoryMock, times(1)).findById(OrderConstants.DB_ORDER_ID_1);
		verify(iimRepositoryMock, times(1)).findById(ItemConstants.INVALID_ITEM_ID);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void save_InvalidEmployeeId_ReturnsNothing() {
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(new Employee());
		given(oRepositoryMock.findById(OrderConstants.DB_ORDER_ID_1)).willReturn(Optional.of(new RestaurantOrder()));
		given(iimRepositoryMock.findById(ItemConstants.DB_ITEM_ID_1)).willReturn(Optional.of(new ItemInMenu()));
		given(uRepositoryMock.findById(UserContants.INVALID_USER_ID)).willReturn(Optional.empty());
		
		ItemInOrderDTO itemDTO = new ItemInOrderDTO();
		itemDTO.setId(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
		itemDTO.setOrderId(OrderConstants.DB_ORDER_ID_1);
		itemDTO.setItemId(ItemConstants.DB_ITEM_ID_1);
		itemDTO.setEmployeeId(UserContants.INVALID_USER_ID);
		
		itemInOrderService.save(itemDTO, UserContants.DB_EMPLOYEE_ID_1_CODE);
		
		verify(employeeRepositoryMock, times(1)).findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
		verify(oRepositoryMock, times(1)).findById(OrderConstants.DB_ORDER_ID_1);
		verify(iimRepositoryMock, times(1)).findById(ItemConstants.DB_ITEM_ID_1);
		verify(uRepositoryMock, times(1)).findById(UserContants.INVALID_USER_ID);
	}
	
	@Test
	public void save_ValidState_ReturnsItem() {
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(new Employee());
		given(oRepositoryMock.findById(OrderConstants.DB_ORDER_ID_1)).willReturn(Optional.of(new RestaurantOrder()));
		given(iimRepositoryMock.findById(ItemConstants.DB_ITEM_ID_1)).willReturn(Optional.of(new ItemInMenu()));
		given(uRepositoryMock.findById(UserContants.DB_EMPLOYEE_ID_1)).willReturn(Optional.of(new Employee()));
		
		ItemInOrderDTO itemDTO = new ItemInOrderDTO();
		itemDTO.setId(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
		itemDTO.setOrderId(OrderConstants.DB_ORDER_ID_1);
		itemDTO.setItemId(ItemConstants.DB_ITEM_ID_1);
		itemDTO.setEmployeeId(UserContants.DB_EMPLOYEE_ID_1);
		itemDTO.setState(ItemInOrderState.TO_DELIVER);
		
		given(itemInOrderRepositoryMock.save(any(ItemInOrder.class))).willReturn(new ItemInOrder());
		
		ItemInOrder savedItem = itemInOrderService.save(itemDTO, UserContants.DB_EMPLOYEE_ID_1_CODE);
		
		verify(employeeRepositoryMock, times(1)).findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
		verify(oRepositoryMock, times(1)).findById(OrderConstants.DB_ORDER_ID_1);
		verify(iimRepositoryMock, times(1)).findById(ItemConstants.DB_ITEM_ID_1);
		verify(uRepositoryMock, times(1)).findById(UserContants.DB_EMPLOYEE_ID_1);
		verify(itemInOrderRepositoryMock, times(1)).save(any(ItemInOrder.class));
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void saveWithoutCode_InvalidId_ReturnsNothing() {
		given(iimRepositoryMock.findById(ItemInOrderConstants.INVALID_ITEM_IN_ORDER_ID)).willReturn(Optional.empty());
		
		ItemInOrderDTO itemDTO = new ItemInOrderDTO();
		itemDTO.setId(ItemInOrderConstants.INVALID_ITEM_IN_ORDER_ID);
		itemDTO.setItemId(ItemInOrderConstants.INVALID_ITEM_IN_ORDER_ID);
		
		itemInOrderService.saveWithoutCode(itemDTO);
		verify(iimRepositoryMock, times(1)).findById(ItemInOrderConstants.INVALID_ITEM_IN_ORDER_ID);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void saveWithoutCode_InvalidEmployeeId_ReturnsNothing() {
		given(iimRepositoryMock.findById(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1)).willReturn(Optional.of(new ItemInMenu()));
		given(uRepositoryMock.findById(UserContants.INVALID_USER_ID)).willReturn(Optional.empty());
		
		ItemInOrderDTO itemDTO = new ItemInOrderDTO();
		itemDTO.setId(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
		itemDTO.setItemId(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
		itemDTO.setEmployeeId(UserContants.INVALID_USER_ID);
		
		itemInOrderService.saveWithoutCode(itemDTO);
		verify(iimRepositoryMock, times(1)).findById(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
		verify(uRepositoryMock, times(1)).findById(UserContants.INVALID_USER_ID);
	}
	
	@Test
	public void saveWithoutCode_ValidItem_ReturnsItem() {
		given(iimRepositoryMock.findById(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1)).willReturn(Optional.of(new ItemInMenu()));
		
		Employee employee = new Employee();
		employee.setId(UserContants.DB_EMPLOYEE_ID_1);
		
		given(uRepositoryMock.findById(UserContants.DB_EMPLOYEE_ID_1)).willReturn(Optional.of(employee));
		
		ItemInOrderDTO itemDTO = new ItemInOrderDTO();
		itemDTO.setId(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
		itemDTO.setItemId(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
		itemDTO.setEmployeeId(UserContants.DB_EMPLOYEE_ID_1);
		itemDTO.setState(ItemInOrderState.NEW);
		
		ItemInOrder savedItem = new ItemInOrder();
		savedItem.setId(itemDTO.getId());
		savedItem.setState(itemDTO.getState());
		
		given(itemInOrderRepositoryMock.save(any(ItemInOrder.class))).willReturn(savedItem);
		
		ItemInOrder result = itemInOrderService.saveWithoutCode(itemDTO);
		verify(iimRepositoryMock, times(1)).findById(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
		verify(uRepositoryMock, times(1)).findById(UserContants.DB_EMPLOYEE_ID_1);
		
		assertEquals(result.getState(), itemDTO.getState());
	}

	@Test(expected = ItemNotFoundException.class)
	public void deleteById_InvalidId_ReturnsNothing() {
		given(itemInOrderRepositoryMock.findById(ItemInOrderConstants.INVALID_ITEM_IN_ORDER_ID)).willReturn(Optional.empty());
		itemInOrderService.deleteById(ItemInOrderConstants.INVALID_ITEM_IN_ORDER_ID);
		verify(itemInOrderRepositoryMock, times(1)).findById(ItemInOrderConstants.INVALID_ITEM_IN_ORDER_ID);
	}
	@Test
	public void deleteById_ValidId_ReturnsNothing() {
		
		ItemInOrder item = new ItemInOrder();
		
		given(itemInOrderRepositoryMock.findById(ItemInOrderConstants.DB_DELETE_ITEM_IN_ORDER_ID)).willReturn(Optional.of(item));
		itemInOrderService.deleteById(ItemInOrderConstants.DB_DELETE_ITEM_IN_ORDER_ID);
		verify(itemInOrderRepositoryMock, times(1)).findById(ItemInOrderConstants.DB_DELETE_ITEM_IN_ORDER_ID);
	}

	@Test(expected = ItemNotFoundException.class)
	public void findById_InvalidId_ReturnsNothing()
	{
		given(itemInOrderRepositoryMock.findById(ItemInOrderConstants.INVALID_ITEM_IN_ORDER_ID)).willReturn(Optional.empty());
		itemInOrderService.findById(ItemInOrderConstants.INVALID_ITEM_IN_ORDER_ID);
		verify(itemInOrderRepositoryMock, times(1)).findById(ItemInOrderConstants.INVALID_ITEM_IN_ORDER_ID);
	}
	
	@Test
	public void findById_ValidId_ReturnsItem()
	{
		ItemInMenu menuItem = new ItemInMenu(
				ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1,
				new BigDecimal(3000),
				new Item(
						ItemConstants.DB_ITEM_ID_1,
						"Coffee",
						"With Sugar",
						new BigDecimal(2000),
						"Image"),
				new Menu(),
				true);
		given(itemInOrderRepositoryMock.findById(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1)).willReturn(
				Optional.of(new ItemInOrder(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1, menuItem))
				);
		itemInOrderService.findById(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
		verify(itemInOrderRepositoryMock, times(1)).findById(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
	}

	@Test(expected = AccessForbiddenException.class)
	public void changeState_InvalidCode_ReturnsNothing() {
		given(employeeRepositoryMock.findByCode(UserContants.INVALID_EMPLOYEE_CODE)).willReturn(null);
		itemInOrderService.changeState(
				UserContants.DB_EMPLOYEE_ID_1,
				ItemInOrderState.NEW,
				UserContants.INVALID_EMPLOYEE_CODE);
		verify(employeeRepositoryMock, times(1)).findByCode(UserContants.INVALID_EMPLOYEE_CODE);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void changeState_InvalidItemId_ReturnsNothing() {
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(new Employee());
		given(itemInOrderRepositoryMock.findById(ItemInOrderConstants.INVALID_ITEM_IN_ORDER_ID)).willReturn(Optional.empty());
		itemInOrderService.changeState(
				ItemInOrderConstants.INVALID_ITEM_IN_ORDER_ID,
				ItemInOrderState.DONE,
				UserContants.DB_EMPLOYEE_ID_1_CODE);
		verify(employeeRepositoryMock, times(1)).findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
		verify(itemInOrderRepositoryMock, times(1)).findById(ItemInOrderConstants.INVALID_ITEM_IN_ORDER_ID);
	}
	
	@Test
	public void changeState_Invalid_ReturnsNothing() {
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(new Cook());
		ItemInOrder item = new ItemInOrder();
		ItemInMenu menuItem = new ItemInMenu();
		menuItem.item = new Food();
		item.setItem(menuItem);
		menuItem.item.setName("Pizza");
		
		RestaurantTable t = new RestaurantTable();
		t.setTableNumber(6);
		RestaurantOrder o = new RestaurantOrder();
		o.setTable(t);
		o.setId(2);
		
		ItemInOrder iio = new ItemInOrder();
		iio.setItem(menuItem);
		iio.setOrder(o);
		given(itemInOrderRepositoryMock.findById(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1)).willReturn(Optional.of(item));
		given(itemInOrderRepositoryMock.save(any(ItemInOrder.class))).willReturn(iio);
		
		itemInOrderService.changeState(
				ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1,
				ItemInOrderState.IN_PROGRESS,
				UserContants.DB_EMPLOYEE_ID_1_CODE);
		verify(employeeRepositoryMock, times(1)).findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
		verify(itemInOrderRepositoryMock, times(1)).findById(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
		verify(itemInOrderRepositoryMock, times(1)).save(any(ItemInOrder.class));
	}
	
	@Test(expected = AccessForbiddenException.class)
	public void put_InvalidCode_ReturnsNothing() {
		given(employeeRepositoryMock.findByCode(UserContants.INVALID_EMPLOYEE_CODE)).willReturn(null);
		itemInOrderService.put(
				new ItemInOrderDTO(),
				UserContants.INVALID_EMPLOYEE_CODE);
		verify(employeeRepositoryMock, times(1)).findByCode(UserContants.INVALID_EMPLOYEE_CODE);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void put_InvalidItemId_ReturnsNothing() {
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(new Employee());
		given(itemInOrderRepositoryMock.findById(ItemInOrderConstants.INVALID_ITEM_IN_ORDER_ID)).willReturn(Optional.empty());
		ItemInOrderDTO itemDTO = new ItemInOrderDTO();
		itemDTO.setId(ItemInOrderConstants.INVALID_ITEM_IN_ORDER_ID);
		itemInOrderService.put(itemDTO, UserContants.DB_EMPLOYEE_ID_1_CODE);
		verify(employeeRepositoryMock, times(1)).findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
		verify(itemInOrderRepositoryMock, times(1)).findById(ItemInOrderConstants.INVALID_ITEM_IN_ORDER_ID);
	}
	
	@Test
	public void put_ValidState_ReturnsItemDTO() {
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(new Employee());
		given(itemInOrderRepositoryMock.findById(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1)).willReturn(Optional.of(new ItemInOrder()));
		given(oRepositoryMock.findById(OrderConstants.DB_ORDER_ID_1)).willReturn(Optional.of(new RestaurantOrder()));
		given(iimRepositoryMock.findById(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1)).willReturn(Optional.of(new ItemInMenu()));
		given(employeeRepositoryMock.findById(UserContants.DB_EMPLOYEE_ID_1)).willReturn(Optional.of(new Employee()));
		given(itemInOrderRepositoryMock.save(any(ItemInOrder.class))).willReturn(new ItemInOrder());
		
		ItemInOrderDTO itemDTO = new ItemInOrderDTO();
		itemDTO.setId(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
		itemDTO.setOrderId(OrderConstants.DB_ORDER_ID_1);
		itemDTO.setItemId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
		itemDTO.setEmployeeId(UserContants.DB_EMPLOYEE_ID_1);
		
		itemInOrderService.put(itemDTO, UserContants.DB_EMPLOYEE_ID_1_CODE);
		
		verify(employeeRepositoryMock, times(1)).findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
		verify(itemInOrderRepositoryMock, times(1)).findById(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
		verify(oRepositoryMock, times(1)).findById(OrderConstants.DB_ORDER_ID_1);
		verify(iimRepositoryMock, times(1)).findById(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
		verify(employeeRepositoryMock, times(1)).findById(UserContants.DB_EMPLOYEE_ID_1);
		verify(itemInOrderRepositoryMock, times(1)).save(any(ItemInOrder.class));
	}
}
