package com.kts.sigma.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.kts.sigma.Exception.AccessForbiddenException;
import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.Exception.ItemNotValidException;
import com.kts.sigma.constants.ItemConstants;
import com.kts.sigma.constants.ItemInMenuConstants;
import com.kts.sigma.constants.ItemInOrderConstants;
import com.kts.sigma.constants.MenuConstants;
import com.kts.sigma.constants.OrderConstants;
import com.kts.sigma.constants.TableConstants;
import com.kts.sigma.constants.UserContants;
import com.kts.sigma.dto.ItemInOrderDTO;
import com.kts.sigma.dto.MenuDTO;
import com.kts.sigma.dto.OrderDTO;
import com.kts.sigma.model.Drink;
import com.kts.sigma.model.Employee;
import com.kts.sigma.model.Food;
import com.kts.sigma.model.FoodType;
import com.kts.sigma.model.Item;
import com.kts.sigma.model.ItemInMenu;
import com.kts.sigma.model.ItemInOrder;
import com.kts.sigma.model.ItemInOrderState;
import com.kts.sigma.model.Menu;
import com.kts.sigma.model.OrderState;
import com.kts.sigma.model.RestaurantOrder;
import com.kts.sigma.model.RestaurantTable;
import com.kts.sigma.model.TableState;
import com.kts.sigma.model.Waiter;
import com.kts.sigma.repository.EmployeeRepository;
import com.kts.sigma.repository.ItemInMenuRepository;
import com.kts.sigma.repository.ItemInOrderRepository;
import com.kts.sigma.repository.ItemRepository;
import com.kts.sigma.repository.MenuRepository;
import com.kts.sigma.repository.OrderRepository;
import com.kts.sigma.repository.TableRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderServiceUnitTest {
	@Autowired
	OrderService orderService;
	
	@MockBean
	OrderRepository orderRepositoryMock;
	
	@MockBean
	ItemInMenuRepository itemInMenuRepositoryMock;
	
	@MockBean
	ItemInOrderRepository itemInOrderRepositoryMock;
	
	@MockBean
	MenuRepository menuRepositoryMock;
	
	@MockBean
	ItemRepository itemRepositoryMock;
	
	@MockBean
	TableRepository tableRepositoryMock;
	
	@MockBean
	ItemInOrderService itemInOrderMock;
	
	@MockBean
	EmployeeRepository employeeRepositoryMock;
	
	@Test
	public void findAll_ValidState_ReturnsAllOrders() {
		List<RestaurantOrder> orders = new ArrayList<RestaurantOrder>();

		RestaurantOrder restaurantOrder1 = new RestaurantOrder();
		restaurantOrder1.setId(OrderConstants.DB_ORDER_ID_1);
		orders.add(restaurantOrder1);
		
		RestaurantOrder restaurantOrder2 = new RestaurantOrder();
		restaurantOrder2.setId(OrderConstants.DB_ORDER_ID_2);
		orders.add(restaurantOrder2);
		
		given(orderRepositoryMock.findAll()).willReturn(orders);
		
		List<OrderDTO> found = (List<OrderDTO>) orderService.getAll();
		
		verify(orderRepositoryMock, times(1)).findAll();
		
		assertEquals(OrderConstants.DB_TOTAL_ORDERS.intValue(), found.size());
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void findById_InvalidId_ThrowsException() {
		given(orderRepositoryMock.findById(OrderConstants.INVALID_ORDER_ID)).willReturn(Optional.empty());
		
		orderService.findById(OrderConstants.INVALID_ORDER_ID);
		
		verify(orderRepositoryMock, times(1)).findById(OrderConstants.INVALID_ORDER_ID);
	}
	
	@Test
	public void findById_ValidId_ReturnsOrder() {
		RestaurantOrder order = new RestaurantOrder();
		order.setId(OrderConstants.DB_ORDER_ID_1);
		
		given(orderRepositoryMock.findById(OrderConstants.DB_ORDER_ID_1)).willReturn(Optional.of(order));
		
		OrderDTO orderDto = orderService.findById(OrderConstants.DB_ORDER_ID_1);
		
		verify(orderRepositoryMock, times(1)).findById(OrderConstants.DB_ORDER_ID_1);
		
		assertEquals(OrderConstants.DB_ORDER_ID_1, orderDto.getId());
	}
	
	@Test
	public void getAllItems_ValidState_ReturnsAllItemsInOrder() {
		List<RestaurantOrder> orders = initializeOrders();
		
		given(orderRepositoryMock.findById(OrderConstants.DB_ORDER_ID_1)).willReturn(Optional.of(orders.get(0)));
		
		List<ItemInOrderDTO> found = (List<ItemInOrderDTO>) orderService.getAllItems(OrderConstants.DB_ORDER_ID_1);
		
		verify(orderRepositoryMock, times(1)).findById(OrderConstants.DB_ORDER_ID_1);
		
		assertEquals(OrderConstants.TOTAL_ITEMS_IN_ORDER1.intValue(), found.size());
		
		
		given(orderRepositoryMock.findById(OrderConstants.DB_ORDER_ID_2)).willReturn(Optional.of(orders.get(1)));
		
		List<ItemInOrderDTO> found2 = (List<ItemInOrderDTO>) orderService.getAllItems(OrderConstants.DB_ORDER_ID_2);
		
		verify(orderRepositoryMock, times(1)).findById(OrderConstants.DB_ORDER_ID_2);
		
		assertEquals(OrderConstants.TOTAL_ITEMS_IN_ORDER2.intValue(), found2.size());
	}
		
	@Test
	public void getAllDrinks_ValidState_ReturnsAllDrinksInOrder() {
		List<RestaurantOrder> orders = initializeOrders();
		
		given(orderRepositoryMock.findById(OrderConstants.DB_ORDER_ID_1)).willReturn(Optional.of(orders.get(0)));
		
		List<ItemInOrderDTO> found = (List<ItemInOrderDTO>) orderService.getAllDrinks(OrderConstants.DB_ORDER_ID_1);
		
		verify(orderRepositoryMock, times(1)).findById(OrderConstants.DB_ORDER_ID_1);
		
		assertEquals(OrderConstants.TOTAL_DRINKS_IN_ORDER1.intValue(), found.size());
		
		
		given(orderRepositoryMock.findById(OrderConstants.DB_ORDER_ID_2)).willReturn(Optional.of(orders.get(1)));
		
		List<ItemInOrderDTO> found2 = (List<ItemInOrderDTO>) orderService.getAllDrinks(OrderConstants.DB_ORDER_ID_2);
		
		verify(orderRepositoryMock, times(1)).findById(OrderConstants.DB_ORDER_ID_2);
		
		assertEquals(OrderConstants.TOTAL_DRINKS_IN_ORDER2.intValue(), found2.size());
	}
	
	@Test
	public void getAllFoods_ValidState_ReturnsAllFoodsInOrder() {
		List<RestaurantOrder> orders = initializeOrders();
		
		given(orderRepositoryMock.findById(OrderConstants.DB_ORDER_ID_1)).willReturn(Optional.of(orders.get(0)));
		
		List<ItemInOrderDTO> found = (List<ItemInOrderDTO>) orderService.getAllFoods(OrderConstants.DB_ORDER_ID_1);
		
		verify(orderRepositoryMock, times(1)).findById(OrderConstants.DB_ORDER_ID_1);
		
		assertEquals(OrderConstants.TOTAL_FOODS_IN_ORDER1.intValue(), found.size());
		
		
		given(orderRepositoryMock.findById(OrderConstants.DB_ORDER_ID_2)).willReturn(Optional.of(orders.get(1)));
		
		List<ItemInOrderDTO> found2 = (List<ItemInOrderDTO>) orderService.getAllFoods(OrderConstants.DB_ORDER_ID_2);
		
		verify(orderRepositoryMock, times(1)).findById(OrderConstants.DB_ORDER_ID_2);
		
		assertEquals(OrderConstants.TOTAL_FOODS_IN_ORDER2.intValue(), found2.size());
	}
	
	@Test
	public void deleteById_ValidOrderIdValidCode_ReturnsNothing() {
		Waiter waiter = new Waiter();
		waiter.setId(UserContants.DB_EMPLOYEE_ID_1);
		waiter.setCode(UserContants.DB_EMPLOYEE_ID_1_CODE);

		RestaurantOrder order = new RestaurantOrder();
		order.setId(OrderConstants.DB_ORDER_ID_1);
		order.setWaiter(waiter);
		
		RestaurantTable table = new RestaurantTable();
		table.setId(TableConstants.DB_TABLE_ID_1);
		table.setState(TableState.IN_PROGRESS);
		order.setTable(table);
		
		given(orderRepositoryMock.findById(OrderConstants.DB_ORDER_ID_1)).willReturn(Optional.of(order));
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(waiter);
		
		orderService.deleteById(OrderConstants.DB_ORDER_ID_1, UserContants.DB_EMPLOYEE_ID_1_CODE);
		
		verify(orderRepositoryMock, times(1)).deleteById(OrderConstants.DB_ORDER_ID_1);
		verify(employeeRepositoryMock, times(1)).findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void deleteById_InvalidOrderId_ThrowsException() {
		
		Waiter waiter = new Waiter();
		waiter.setId(UserContants.DB_EMPLOYEE_ID_1);
		waiter.setCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
		
		given(orderRepositoryMock.findById(OrderConstants.DB_ORDER_ID_1)).willReturn(Optional.empty());
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(waiter);
		
		orderService.deleteById(OrderConstants.DB_ORDER_ID_1, UserContants.DB_EMPLOYEE_ID_1_CODE);
	}
	
	@Test(expected = AccessForbiddenException.class)
	public void deleteById_InvalidCode_ThrowsException() {
		RestaurantOrder order = new RestaurantOrder();
		order.setId(OrderConstants.DB_ORDER_ID_1);
		
		given(orderRepositoryMock.findById(OrderConstants.DB_ORDER_ID_1)).willReturn(Optional.of(order));
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(null);
		
		orderService.deleteById(OrderConstants.DB_ORDER_ID_1, UserContants.DB_EMPLOYEE_ID_1_CODE);
	}
	
	@Test
	public void changeState_ValidOrderIdValidCode_ReturnsNothing() {
		Waiter waiter = new Waiter();
		waiter.setId(UserContants.DB_EMPLOYEE_ID_1);
		waiter.setCode(UserContants.DB_EMPLOYEE_ID_1_CODE);

		RestaurantOrder order = new RestaurantOrder();
		order.setId(OrderConstants.DB_ORDER_ID_1);
		order.setWaiter(waiter);
		
		given(orderRepositoryMock.findById(OrderConstants.DB_ORDER_ID_1)).willReturn(Optional.of(order));
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(waiter);
		
		orderService.changeState(OrderState.IN_PROGRESS, UserContants.DB_EMPLOYEE_ID_1_CODE, OrderConstants.DB_ORDER_ID_1);
		
		verify(orderRepositoryMock, times(1)).findById(OrderConstants.DB_ORDER_ID_1);
		verify(employeeRepositoryMock, times(1)).findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void changeState_InValidOrderId_ThrowsException() {
		Waiter waiter = new Waiter();
		waiter.setId(UserContants.DB_EMPLOYEE_ID_1);
		waiter.setCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
		
		given(orderRepositoryMock.findById(OrderConstants.DB_ORDER_ID_1)).willReturn(Optional.empty());
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(waiter);
		
		orderService.changeState(OrderState.IN_PROGRESS, UserContants.DB_EMPLOYEE_ID_1_CODE, OrderConstants.DB_ORDER_ID_1);
	}
	
	@Test(expected = AccessForbiddenException.class)
	public void changeState_InValidCode_ThrowsException() {
		RestaurantOrder order = new RestaurantOrder();
		order.setId(OrderConstants.DB_ORDER_ID_1);
		
		given(orderRepositoryMock.findById(OrderConstants.DB_ORDER_ID_1)).willReturn(Optional.of(order));
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(null);
		
		orderService.changeState(OrderState.IN_PROGRESS, UserContants.DB_EMPLOYEE_ID_1_CODE, OrderConstants.DB_ORDER_ID_1);
		
		verify(orderRepositoryMock, times(1)).findById(OrderConstants.DB_ORDER_ID_1);
		verify(employeeRepositoryMock, times(1)).findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
	}
	
	@Test
 	public void addItemToOrder_ValidOrderIdValidItemValidCode_ReturnsItem() {
		Food food2 = new Food();
		food2.setId(ItemConstants.DB_FOOD_ID_2);
		food2.setName("Cesar Salad");
		food2.setDescription("yummy");
		food2.setType(FoodType.SALAD);
		food2.setBuyingPrice(BigDecimal.valueOf(100.00));
		
		Menu menu = new Menu();
		menu.setId(MenuConstants.DB_MENU_ID_1);
		menu.setActive(true);
		
		ItemInMenu iim2 = new ItemInMenu();
		iim2.setId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_2);
		iim2.setItem(food2);
		iim2.setMenu(menu);
		iim2.setSellingPrice(BigDecimal.valueOf(250.00));
		
		ItemInOrderDTO item = new ItemInOrderDTO();
		item.setOrderId(1);
		item.setSellingPrice(BigDecimal.valueOf(350.00));
		item.setItemId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_2);
		item.setQuantity(2);
		
		Waiter waiter = new Waiter();
		waiter.setId(UserContants.DB_EMPLOYEE_ID_1);
		waiter.setCode(UserContants.DB_EMPLOYEE_ID_1_CODE);

		RestaurantOrder order = new RestaurantOrder();
		order.setId(OrderConstants.DB_ORDER_ID_1);
		order.setTotalPrice(BigDecimal.valueOf(1000));
		order.setWaiter(waiter);
		
		ItemInOrder iio = new ItemInOrder();
		iio.setId(1);
		iio.setItem(iim2);
		iio.setOrder(order);
		
		given(orderRepositoryMock.findById(OrderConstants.DB_ORDER_ID_1)).willReturn(Optional.of(order));
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(waiter);
		given(itemInMenuRepositoryMock.findById(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_2)).willReturn(Optional.of(iim2));
		given(itemInOrderMock.saveWithoutCode(item)).willReturn(iio);
		
		ItemInOrderDTO newItem = orderService.addItemToOrder(item, UserContants.DB_EMPLOYEE_ID_1_CODE, OrderConstants.DB_ORDER_ID_1);
		
		verify(orderRepositoryMock, times(1)).findById(OrderConstants.DB_ORDER_ID_1);
		verify(employeeRepositoryMock, times(1)).findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
	}
	
	@Test
 	public void removeItemFromOrder_ValidOrderIdValidItemValidCode_ReturnsItem() {
		List<RestaurantOrder> orders = initializeOrders();
		
		Food food2 = new Food();
		food2.setId(ItemConstants.DB_FOOD_ID_2);
		food2.setName("Cesar Salad");
		food2.setDescription("yummy");
		food2.setType(FoodType.SALAD);
		food2.setBuyingPrice(BigDecimal.valueOf(100.00));
		
		Menu menu = new Menu();
		menu.setId(MenuConstants.DB_MENU_ID_1);
		menu.setActive(true);
		
		ItemInMenu iim2 = new ItemInMenu();
		iim2.setId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_2);
		iim2.setItem(food2);
		iim2.setMenu(menu);
		iim2.setSellingPrice(BigDecimal.valueOf(250.00));
		
		ItemInOrder iio3 = new ItemInOrder();
		iio3.setId(OrderConstants.DB_ITEM_IN_ORDER1_ID_3);
		iio3.setOrder(orders.get(0));
		iio3.setState(ItemInOrderState.NEW);
		iio3.setItem(iim2);
		
		Waiter waiter = new Waiter();
		waiter.setId(UserContants.DB_EMPLOYEE_ID_1);
		waiter.setCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
		
		orders.get(0).setWaiter(waiter);
		
		given(orderRepositoryMock.findById(OrderConstants.DB_ORDER_ID_1)).willReturn(Optional.of(orders.get(0)));
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(waiter);
		
		orderService.removeItemFromOrder(OrderConstants.DB_ITEM_IN_ORDER1_ID_3, UserContants.DB_EMPLOYEE_ID_1_CODE, OrderConstants.DB_ORDER_ID_1);
		
		verify(orderRepositoryMock, times(1)).findById(OrderConstants.DB_ORDER_ID_1);
		verify(employeeRepositoryMock, times(1)).findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
	}
	
	@Test(expected = ItemNotFoundException.class)
 	public void removeItemFromOrder_InValidOrderId_ThrowsException() {
		List<RestaurantOrder> orders = initializeOrders();
		
		Food food2 = new Food();
		food2.setId(ItemConstants.DB_FOOD_ID_2);
		food2.setName("Cesar Salad");
		food2.setDescription("yummy");
		food2.setType(FoodType.SALAD);
		food2.setBuyingPrice(BigDecimal.valueOf(100.00));
		
		Menu menu = new Menu();
		menu.setId(MenuConstants.DB_MENU_ID_1);
		menu.setActive(true);
		
		ItemInMenu iim2 = new ItemInMenu();
		iim2.setId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_2);
		iim2.setItem(food2);
		iim2.setMenu(menu);
		iim2.setSellingPrice(BigDecimal.valueOf(250.00));
		
		ItemInOrder iio3 = new ItemInOrder();
		iio3.setId(OrderConstants.DB_ITEM_IN_ORDER1_ID_3);
		iio3.setOrder(orders.get(0));
		iio3.setState(ItemInOrderState.NEW);
		iio3.setItem(iim2);
		
		Waiter waiter = new Waiter();
		waiter.setId(UserContants.DB_EMPLOYEE_ID_1);
		waiter.setCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
		
		orders.get(0).setWaiter(waiter);
		
		given(orderRepositoryMock.findById(OrderConstants.DB_ORDER_ID_1)).willReturn(Optional.empty());
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(waiter);
		given(itemInOrderRepositoryMock.findById(OrderConstants.DB_ITEM_IN_ORDER1_ID_3)).willReturn(Optional.of(iio3));
		
		orderService.removeItemFromOrder(OrderConstants.DB_ITEM_IN_ORDER1_ID_3, UserContants.DB_EMPLOYEE_ID_1_CODE, OrderConstants.DB_ORDER_ID_1);
	}
	
	@Test(expected = AccessForbiddenException.class)
 	public void removeItemFromOrder_InValidCode_ThrowsException() {
		List<RestaurantOrder> orders = initializeOrders();
		
		Food food2 = new Food();
		food2.setId(ItemConstants.DB_FOOD_ID_2);
		food2.setName("Cesar Salad");
		food2.setDescription("yummy");
		food2.setType(FoodType.SALAD);
		food2.setBuyingPrice(BigDecimal.valueOf(100.00));
		
		Menu menu = new Menu();
		menu.setId(MenuConstants.DB_MENU_ID_1);
		menu.setActive(true);
		
		ItemInMenu iim2 = new ItemInMenu();
		iim2.setId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_2);
		iim2.setItem(food2);
		iim2.setMenu(menu);
		iim2.setSellingPrice(BigDecimal.valueOf(250.00));
		
		ItemInOrder iio3 = new ItemInOrder();
		iio3.setId(OrderConstants.DB_ITEM_IN_ORDER1_ID_3);
		iio3.setOrder(orders.get(0));
		iio3.setState(ItemInOrderState.NEW);
		iio3.setItem(iim2);
		
		Waiter waiter = new Waiter();
		waiter.setId(UserContants.DB_EMPLOYEE_ID_1);
		waiter.setCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
		
		orders.get(0).setWaiter(waiter);
		
		given(orderRepositoryMock.findById(OrderConstants.DB_ORDER_ID_1)).willReturn(Optional.of(orders.get(0)));
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(null);
		given(itemInOrderRepositoryMock.findById(OrderConstants.DB_ITEM_IN_ORDER1_ID_3)).willReturn(Optional.of(iio3));
		
		orderService.removeItemFromOrder(OrderConstants.DB_ITEM_IN_ORDER1_ID_3, UserContants.DB_EMPLOYEE_ID_1_CODE, OrderConstants.DB_ORDER_ID_1);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void addItemToOrder_InvalidItemId_ThrowsException() {
		List<RestaurantOrder> orders = initializeOrders();
		
		Food food2 = new Food();
		food2.setId(ItemConstants.DB_FOOD_ID_2);
		food2.setName("Cesar Salad");
		food2.setDescription("yummy");
		food2.setType(FoodType.SALAD);
		food2.setBuyingPrice(BigDecimal.valueOf(100.00));
		
		Menu menu = new Menu();
		menu.setId(MenuConstants.DB_MENU_ID_1);
		menu.setActive(true);
		
		ItemInMenu iim2 = new ItemInMenu();
		iim2.setId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_2);
		iim2.setItem(food2);
		iim2.setMenu(menu);
		iim2.setSellingPrice(BigDecimal.valueOf(250.00));
		
		ItemInOrderDTO item = new ItemInOrderDTO();
		item.setOrderId(1);
		item.setSellingPrice(BigDecimal.valueOf(350.00));
		item.setItemId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_2);
		item.setQuantity(2);
		
		Waiter waiter = new Waiter();
		waiter.setId(UserContants.DB_EMPLOYEE_ID_1);
		waiter.setCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
		
		orders.get(0).setWaiter(waiter);
		
		ItemInOrder iio3 = new ItemInOrder();
		iio3.setId(OrderConstants.DB_ITEM_IN_ORDER1_ID_3);
		iio3.setOrder(orders.get(0));
		iio3.setState(ItemInOrderState.NEW);
		iio3.setItem(iim2);
		
		given(orderRepositoryMock.findById(OrderConstants.DB_ORDER_ID_1)).willReturn(Optional.of(orders.get(0)));
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(waiter);
		given(itemInMenuRepositoryMock.findById(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_2)).willReturn(Optional.empty());
		given(itemInOrderMock.saveWithoutCode(item)).willThrow(ItemNotFoundException.class);
		
		ItemInOrderDTO newItem = orderService.addItemToOrder(item, UserContants.DB_EMPLOYEE_ID_1_CODE, OrderConstants.DB_ORDER_ID_1);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void addItemToOrder_InvalidOrder_ThrowsException() {
		List<RestaurantOrder> orders = initializeOrders();
		
		Food food2 = new Food();
		food2.setId(ItemConstants.DB_FOOD_ID_2);
		food2.setName("Cesar Salad");
		food2.setDescription("yummy");
		food2.setType(FoodType.SALAD);
		food2.setBuyingPrice(BigDecimal.valueOf(100.00));
		
		Menu menu = new Menu();
		menu.setId(MenuConstants.DB_MENU_ID_1);
		menu.setActive(true);
		
		ItemInMenu iim2 = new ItemInMenu();
		iim2.setId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_2);
		iim2.setItem(food2);
		iim2.setMenu(menu);
		iim2.setSellingPrice(BigDecimal.valueOf(250.00));
		
		ItemInOrderDTO item = new ItemInOrderDTO();
		item.setOrderId(1);
		item.setSellingPrice(BigDecimal.valueOf(350.00));
		item.setItemId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_2);
		item.setQuantity(2);
		
		Waiter waiter = new Waiter();
		waiter.setId(UserContants.DB_EMPLOYEE_ID_1);
		waiter.setCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
		
		orders.get(0).setWaiter(waiter);
		
		given(orderRepositoryMock.findById(OrderConstants.DB_ORDER_ID_1)).willReturn(Optional.empty());
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(waiter);
		given(itemInMenuRepositoryMock.findById(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_2)).willReturn(Optional.of(iim2));
		
		ItemInOrderDTO newItem = orderService.addItemToOrder(item, UserContants.DB_EMPLOYEE_ID_1_CODE, OrderConstants.DB_ORDER_ID_1);
	}
	
	@Test(expected = AccessForbiddenException.class)
	public void addItemToOrder_InvalidCode_ThrowsException() {
		List<RestaurantOrder> orders = initializeOrders();
		
		Food food2 = new Food();
		food2.setId(ItemConstants.DB_FOOD_ID_2);
		food2.setName("Cesar Salad");
		food2.setDescription("yummy");
		food2.setType(FoodType.SALAD);
		food2.setBuyingPrice(BigDecimal.valueOf(100.00));
		
		Menu menu = new Menu();
		menu.setId(MenuConstants.DB_MENU_ID_1);
		menu.setActive(true);
		
		ItemInMenu iim2 = new ItemInMenu();
		iim2.setId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_2);
		iim2.setItem(food2);
		iim2.setMenu(menu);
		iim2.setSellingPrice(BigDecimal.valueOf(250.00));
		
		ItemInOrderDTO item = new ItemInOrderDTO();
		item.setOrderId(1);
		item.setSellingPrice(BigDecimal.valueOf(350.00));
		item.setItemId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_2);
		item.setQuantity(2);
		
		given(orderRepositoryMock.findById(OrderConstants.DB_ORDER_ID_1)).willReturn(Optional.of(orders.get(0)));
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(null);
		given(itemInMenuRepositoryMock.findById(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_2)).willReturn(Optional.of(iim2));
		
		ItemInOrderDTO newItem = orderService.addItemToOrder(item, UserContants.DB_EMPLOYEE_ID_1_CODE, OrderConstants.DB_ORDER_ID_1);
		
		verify(orderRepositoryMock, times(1)).findById(OrderConstants.DB_ORDER_ID_1);
		verify(employeeRepositoryMock, times(1)).findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
	}
	
	@Test
	public void save_ValidOrderValidcode_ReturnsOrder() {
		List<ItemInOrderDTO> items = new ArrayList<ItemInOrderDTO>();
		OrderDTO order = new OrderDTO();
		order.setWaiterId(UserContants.DB_EMPLOYEE_ID_1);
		order.setTableId(TableConstants.DB_TABLE_ID_1);
		
		RestaurantTable table = new RestaurantTable();
		table.setId(TableConstants.DB_TABLE_ID_1);
		
		Waiter waiter = new Waiter();
		waiter.setId(UserContants.DB_EMPLOYEE_ID_1);
		waiter.setCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
		
		ItemInMenu item = new ItemInMenu();
		item.setId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
		
		ItemInOrderDTO dto = new ItemInOrderDTO();
		dto.setItemId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
		dto.setQuantity(2);
		dto.setSellingPrice(BigDecimal.valueOf(300));
		items.add(dto);
		order.setItems(items);
		
		RestaurantOrder o = new RestaurantOrder();
		o.setId(OrderConstants.NEW_ORDER_ID);
		
		given(tableRepositoryMock.findById(TableConstants.DB_TABLE_ID_1)).willReturn(Optional.of(table));
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(waiter);
		given(orderRepositoryMock.getOne(OrderConstants.NEW_ORDER_ID)).willReturn(o);
		given(orderRepositoryMock.save(Mockito.any())).willReturn(o);
		
		RestaurantOrder returned = orderService.save(order, UserContants.DB_EMPLOYEE_ID_1_CODE);
		
		verify(tableRepositoryMock, times(1)).findById(TableConstants.DB_TABLE_ID_1);
		verify(employeeRepositoryMock, times(1)).findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
		verify(orderRepositoryMock, times(1)).save(Mockito.any());
	}
	
	@Test(expected = AccessForbiddenException.class)
	public void save_InValidcode_ThrowsException() {
		List<ItemInOrderDTO> items = new ArrayList<ItemInOrderDTO>();
		OrderDTO order = new OrderDTO();
		order.setWaiterId(UserContants.DB_EMPLOYEE_ID_1);
		order.setTableId(TableConstants.DB_TABLE_ID_1);
		
		RestaurantTable table = new RestaurantTable();
		table.setId(TableConstants.DB_TABLE_ID_1);
		
		Waiter waiter = new Waiter();
		waiter.setId(UserContants.DB_EMPLOYEE_ID_1);
		waiter.setCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
		
		ItemInMenu item = new ItemInMenu();
		item.setId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
		
		ItemInOrderDTO dto = new ItemInOrderDTO();
		dto.setItemId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
		dto.setQuantity(2);
		dto.setSellingPrice(BigDecimal.valueOf(300));
		items.add(dto);
		order.setItems(items);
		
		RestaurantOrder o = new RestaurantOrder();
		o.setId(OrderConstants.NEW_ORDER_ID);
		
		given(tableRepositoryMock.findById(TableConstants.DB_TABLE_ID_1)).willReturn(Optional.of(table));
		given(itemInMenuRepositoryMock.findById(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1)).willReturn(Optional.of(item));
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(null);
		given(orderRepositoryMock.save(Mockito.any())).willReturn(o);
		
		RestaurantOrder returned = orderService.save(order, UserContants.DB_EMPLOYEE_ID_1_CODE);
	}
	
	@Test(expected = ItemNotValidException.class)
	public void save_InValidOrderNoTableID_ThrowsException() {
		List<ItemInOrderDTO> items = new ArrayList<ItemInOrderDTO>();
		OrderDTO order = new OrderDTO();
		order.setWaiterId(UserContants.DB_EMPLOYEE_ID_1);
		
		RestaurantTable table = new RestaurantTable();
		table.setId(TableConstants.DB_TABLE_ID_1);
		
		Waiter waiter = new Waiter();
		waiter.setId(UserContants.DB_EMPLOYEE_ID_1);
		waiter.setCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
		
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(waiter);
		
		RestaurantOrder returned = orderService.save(order, UserContants.DB_EMPLOYEE_ID_1_CODE);
	}
	
	@Test(expected = ItemNotValidException.class)
	public void save_InValidOrderItemHasNoQuantity_ThrowsException() {
		List<ItemInOrderDTO> items = new ArrayList<ItemInOrderDTO>();
		OrderDTO order = new OrderDTO();
		order.setWaiterId(UserContants.DB_EMPLOYEE_ID_1);
		order.setTableId(TableConstants.DB_TABLE_ID_1);
		
		RestaurantTable table = new RestaurantTable();
		table.setId(TableConstants.DB_TABLE_ID_1);
		
		Waiter waiter = new Waiter();
		waiter.setId(UserContants.DB_EMPLOYEE_ID_1);
		waiter.setCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
		
		ItemInMenu item = new ItemInMenu();
		item.setId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
		
		ItemInOrderDTO dto = new ItemInOrderDTO();
		dto.setItemId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
		dto.setSellingPrice(BigDecimal.valueOf(300));
		items.add(dto);
		order.setItems(items);
		
		RestaurantOrder o = new RestaurantOrder();
		o.setId(OrderConstants.NEW_ORDER_ID);
		
		given(tableRepositoryMock.findById(TableConstants.DB_TABLE_ID_1)).willReturn(Optional.of(table));
		given(itemInMenuRepositoryMock.findById(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1)).willReturn(Optional.of(item));
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(waiter);
		given(orderRepositoryMock.save(Mockito.any())).willReturn(o);
		
		RestaurantOrder returned = orderService.save(order, UserContants.DB_EMPLOYEE_ID_1_CODE);
	}

	@Test(expected = ItemNotFoundException.class)
	public void save_InValidOrderItemHasInvalidId_ThrowsException() {
		List<ItemInOrderDTO> items = new ArrayList<ItemInOrderDTO>();
		OrderDTO order = new OrderDTO();
		order.setWaiterId(UserContants.DB_EMPLOYEE_ID_1);
		order.setTableId(TableConstants.DB_TABLE_ID_1);
		
		RestaurantTable table = new RestaurantTable();
		table.setId(TableConstants.DB_TABLE_ID_1);
		
		Waiter waiter = new Waiter();
		waiter.setId(UserContants.DB_EMPLOYEE_ID_1);
		waiter.setCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
		
		ItemInOrderDTO dto = new ItemInOrderDTO();
		dto.setItemId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
		dto.setQuantity(2);
		dto.setSellingPrice(BigDecimal.valueOf(600.00));
		items.add(dto);
		order.setItems(items);
		
		RestaurantOrder o = new RestaurantOrder();
		o.setId(OrderConstants.NEW_ORDER_ID);
		
		given(tableRepositoryMock.findById(TableConstants.DB_TABLE_ID_1)).willReturn(Optional.of(table));
		given(itemInMenuRepositoryMock.findById(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1)).willReturn(Optional.empty());
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(waiter);
		given(orderRepositoryMock.save(Mockito.any())).willReturn(o);
		given(itemInOrderMock.saveWithoutCode(dto)).willThrow(ItemNotFoundException.class);
		
		RestaurantOrder returned = orderService.save(order, UserContants.DB_EMPLOYEE_ID_1_CODE);
	}
	
	public ArrayList<RestaurantOrder> initializeOrders(){
		ArrayList<RestaurantOrder> orders = new ArrayList<RestaurantOrder>();
		
		Food food1 = new Food();
		food1.setId(ItemConstants.DB_FOOD_ID_1);
		food1.setName("Spaghetti Bolognese");
		food1.setDescription("yummy");
		food1.setType(FoodType.MAIN_COURSE);
		food1.setBuyingPrice(BigDecimal.valueOf(200.00));
		
		Food food2 = new Food();
		food2.setId(ItemConstants.DB_FOOD_ID_2);
		food2.setName("Cesar Salad");
		food2.setDescription("yummy");
		food2.setType(FoodType.SALAD);
		food2.setBuyingPrice(BigDecimal.valueOf(100.00));
		
		Food food3 = new Food();
		food3.setId(ItemConstants.DB_FOOD_ID_3);
		food3.setName("Vulcan Cake");
		food3.setDescription("yummy");
		food3.setType(FoodType.DESERT);
		food3.setBuyingPrice(BigDecimal.valueOf(150.00));
		
		Drink drink1 = new Drink();
		drink1.setId(ItemConstants.DB_DRINK_ID_1);
		drink1.setName("Coca Cola");
		drink1.setDescription("yummy");
		drink1.setBuyingPrice(BigDecimal.valueOf(50.00));
		
		Menu menu = new Menu();
		menu.setId(MenuConstants.DB_MENU_ID_1);
		menu.setActive(true);
		
		ItemInMenu iim1 = new ItemInMenu();
		iim1.setId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
		iim1.setItem(food1);
		iim1.setMenu(menu);
		iim1.setSellingPrice(BigDecimal.valueOf(350.00));
		
		ItemInMenu iim2 = new ItemInMenu();
		iim2.setId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_2);
		iim2.setItem(food2);
		iim2.setMenu(menu);
		iim2.setSellingPrice(BigDecimal.valueOf(250.00));
		
		ItemInMenu iim3 = new ItemInMenu();
		iim3.setId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_3);
		iim3.setItem(drink1);
		iim3.setMenu(menu);
		iim3.setSellingPrice(BigDecimal.valueOf(110.00));
		
		ItemInMenu iim4 = new ItemInMenu();
		iim4.setId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_4);
		iim4.setItem(food3);
		iim4.setMenu(menu);
		iim4.setSellingPrice(BigDecimal.valueOf(290.00));
		
		Set<ItemInOrder> items1 = new HashSet<>();
		Set<ItemInOrder> items2 = new  HashSet<>();
		
		RestaurantOrder restaurantOrder1 = new RestaurantOrder();
		restaurantOrder1.setId(OrderConstants.DB_ORDER_ID_1);
		restaurantOrder1.setOrderDateTime(LocalDateTime.now());
		restaurantOrder1.setState(OrderState.NEW);
		restaurantOrder1.setTotalPrice(BigDecimal.valueOf(1100.00));
		
		RestaurantOrder restaurantOrder2 = new RestaurantOrder();
		restaurantOrder2.setId(OrderConstants.DB_ORDER_ID_2);
		restaurantOrder2.setOrderDateTime(LocalDateTime.now());
		restaurantOrder2.setState(OrderState.NEW);
		restaurantOrder2.setTotalPrice(BigDecimal.valueOf(600.00));
		
		ItemInOrder iio1 = new ItemInOrder();
		iio1.setId(OrderConstants.DB_ITEM_IN_ORDER1_ID_1);
		iio1.setOrder(restaurantOrder1);
		iio1.setState(ItemInOrderState.NEW);
		iio1.setItem(iim1);
		items1.add(iio1);
		
		ItemInOrder iio2 = new ItemInOrder();
		iio2.setId(OrderConstants.DB_ITEM_IN_ORDER1_ID_2);
		iio2.setOrder(restaurantOrder1);
		iio2.setState(ItemInOrderState.NEW);
		iio2.setItem(iim1);
		items1.add(iio2);
		
		ItemInOrder iio3 = new ItemInOrder();
		iio3.setId(OrderConstants.DB_ITEM_IN_ORDER1_ID_3);
		iio3.setOrder(restaurantOrder1);
		iio3.setState(ItemInOrderState.NEW);
		iio3.setItem(iim2);
		items1.add(iio3);
		
		ItemInOrder iio4 = new ItemInOrder();
		iio4.setId(OrderConstants.DB_ITEM_IN_ORDER1_ID_4);
		iio4.setOrder(restaurantOrder1);
		iio4.setState(ItemInOrderState.NEW);
		iio4.setItem(iim3);
		items1.add(iio4);
		
		restaurantOrder1.setItems(items1);
		
		ItemInOrder iio5 = new ItemInOrder();
		iio5.setId(OrderConstants.DB_ITEM_IN_ORDER2_ID_1);
		iio5.setOrder(restaurantOrder2);
		iio5.setState(ItemInOrderState.NEW);
		iio5.setItem(iim4);
		items2.add(iio5);
		
		ItemInOrder iio6 = new ItemInOrder();
		iio6.setId(OrderConstants.DB_ITEM_IN_ORDER2_ID_2);
		iio6.setOrder(restaurantOrder2);
		iio6.setState(ItemInOrderState.NEW);
		iio6.setItem(iim4);
		items2.add(iio6);
		
		ItemInOrder iio7 = new ItemInOrder();
		iio7.setId(OrderConstants.DB_ITEM_IN_ORDER2_ID_3);
		iio7.setOrder(restaurantOrder2);
		iio7.setState(ItemInOrderState.NEW);
		iio7.setItem(iim4);
		items2.add(iio7);
		
		restaurantOrder2.setItems(items2);
		
		orders.add(restaurantOrder1);
		orders.add(restaurantOrder2);
		
		return orders;
	}

	
}
