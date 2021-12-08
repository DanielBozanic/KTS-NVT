package com.kts.sigma.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.kts.sigma.Exception.AccessForbiddenException;
import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.Exception.ItemNotValidException;
import com.kts.sigma.constants.ItemInMenuConstants;
import com.kts.sigma.constants.ItemInOrderConstants;
import com.kts.sigma.constants.OrderConstants;
import com.kts.sigma.constants.TableConstants;
import com.kts.sigma.constants.UserContants;
import com.kts.sigma.dto.ItemInOrderDTO;
import com.kts.sigma.dto.OrderDTO;
import com.kts.sigma.dto.TableDTO;
import com.kts.sigma.model.OrderState;
import com.kts.sigma.model.TableState;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class OrderServiceIntegrationTest {

	@Autowired
	OrderService orderService;
	
	@Test
	public void findAll_ValidState_ReturnsAllOrders() {
		List<OrderDTO> orders = (List<OrderDTO>) orderService.getAll();
		
		assertEquals(OrderConstants.DB_TOTAL_ORDERS, orders.size());
	}
	
	@Test
	public void findById_ValidId_ReturnsOrder() {
		OrderDTO found = orderService.findById(OrderConstants.DB_ORDER_ID_1);
		
		assertNotNull(found);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void findById_InValidId_ThrowsException() {
		orderService.findById(OrderConstants.INVALID_ORDER_ID);
	}
	
	@Test
	public void getAllItems_ValidState_ReturnsAllItemsInOrder() {
		List<ItemInOrderDTO> items = (List<ItemInOrderDTO>) orderService.getAllItems(OrderConstants.DB_ORDER_ID_1);
		
		assertEquals(OrderConstants.TOTAL_ITEMS_IN_ORDER1, items.size());
	}
	
	@Test
	public void getAllDrinks_ValidState_ReturnsAllDrinksInOrder() {
		List<ItemInOrderDTO> items = (List<ItemInOrderDTO>) orderService.getAllDrinks(OrderConstants.DB_ORDER_ID_1);
		
		assertEquals(2, items.size());
	}
	
	@Test
	public void getAllFoods_ValidState_ReturnsAllFoodsInOrder() {
		List<ItemInOrderDTO> items = (List<ItemInOrderDTO>) orderService.getAllFoods(OrderConstants.DB_ORDER_ID_1);
		
		assertEquals(2, items.size());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void deleteById_ValidIdValidCode_ReturnsNothing() {
		int found = ((List<OrderDTO>) orderService.getAll()).size();
		
		orderService.deleteById(OrderConstants.DB_ORDER_ID_1, 1000);
		
		int found2 = ((List<OrderDTO>) orderService.getAll()).size();
		
		assertEquals(found - 1, found2);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void deleteById_InValidId_ThrowsException() {
		orderService.deleteById(OrderConstants.INVALID_ORDER_ID, 1000);
	}
	
	@Test(expected = AccessForbiddenException.class)
	public void deleteById_InValidCode_ThrowsException() {
		orderService.deleteById(OrderConstants.DB_ORDER_ID_1, UserContants.INVALID_EMPLOYEE_CODE);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void changeState_ValidIdValidCode_ReturnsNothing() {
		orderService.changeState(OrderState.IN_PROGRESS, 1000, OrderConstants.DB_ORDER_ID_1);
		
		OrderState found = orderService.findById(OrderConstants.DB_ORDER_ID_1).getState();
		
		assertEquals(found.ordinal(), OrderState.IN_PROGRESS.ordinal());
	}

	@Test(expected = ItemNotFoundException.class)
	public void changeState_InValidId_ThrowsException() {
		orderService.changeState(OrderState.IN_PROGRESS, 1000, OrderConstants.INVALID_ORDER_ID);
	}
	
	@Test(expected = AccessForbiddenException.class)
	public void changeState_InValidCode_ThrowsException() {
		orderService.changeState(OrderState.IN_PROGRESS, UserContants.INVALID_EMPLOYEE_CODE, OrderConstants.DB_ORDER_ID_1);
	}
	
	@Test
	@Transactional
	@Rollback(true)
 	public void addItemToOrder_ValidOrderIdValidItemValidCode_ReturnsItem() {
//		OrderDTO dto = new OrderDTO();
//		dto.setTableId(TableConstants.DB_TABLE_ID_4);
//		dto.setWaiterId(UserContants.DB_EMPLOYEE_ID_1);
//		
//		List<ItemInOrderDTO> items = new ArrayList<ItemInOrderDTO>();
//		
		ItemInOrderDTO dto1 = new ItemInOrderDTO();
		dto1.setItemId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
		dto1.setQuantity(3);
		dto1.setSellingPrice(BigDecimal.valueOf(300));
//		items.add(dto1);
//		
//		ItemInOrderDTO dto2 = new ItemInOrderDTO();
//		dto2.setItemId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_2);
//		dto2.setQuantity(2);
//		dto2.setSellingPrice(BigDecimal.valueOf(100));
//		items.add(dto2);
		OrderDTO dto = orderService.findById(OrderConstants.DB_ORDER_ID_1);
		BigDecimal old = dto.getTotalPrice();
		
		orderService.addItemToOrder(dto1, 1000, OrderConstants.DB_ORDER_ID_1);
		
		BigDecimal found = orderService.findById(OrderConstants.DB_ORDER_ID_1).getTotalPrice();
		
		assertEquals(old.intValue() + 900, found.intValue());
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void addItemToOrder_InValidOrderId_ReturnsItem() {
		ItemInOrderDTO dto1 = new ItemInOrderDTO();
		dto1.setItemId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
		dto1.setQuantity(3);
		dto1.setSellingPrice(BigDecimal.valueOf(300));
		
		orderService.addItemToOrder(dto1, 1000, OrderConstants.INVALID_ORDER_ID);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void addItemToOrder_InValidItem_ReturnsItem() {
		ItemInOrderDTO dto1 = new ItemInOrderDTO();
		dto1.setItemId(ItemInMenuConstants.INVALID_ITEM_ID);
		dto1.setQuantity(3);
		dto1.setSellingPrice(BigDecimal.valueOf(300));
		
		orderService.addItemToOrder(dto1, 1000, OrderConstants.DB_ORDER_ID_1);
	}
	
	@Test(expected = AccessForbiddenException.class)
	public void addItemToOrder_InValidCode_ReturnsItem() {
		ItemInOrderDTO dto1 = new ItemInOrderDTO();
		dto1.setItemId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
		dto1.setQuantity(3);
		dto1.setSellingPrice(BigDecimal.valueOf(300));
		
		orderService.addItemToOrder(dto1, UserContants.INVALID_EMPLOYEE_CODE, OrderConstants.DB_ORDER_ID_1);
	}
	
	@Test
	@Transactional
	@Rollback(true)
 	public void removeItemFromOrder_ValidOrderIdValidItemValidCode_ReturnsItem() {
		int old = ((List<ItemInOrderDTO>) orderService.getAllItems(OrderConstants.DB_ORDER_ID_1)).size();
		
		orderService.removeItemFromOrder(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1, 1000, OrderConstants.DB_ORDER_ID_1);
		
		int found = ((List<ItemInOrderDTO>) orderService.getAllItems(OrderConstants.DB_ORDER_ID_1)).size();
		
		assertEquals(old - 1, found);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void removeItemFromOrder_InValidOrderId_ThrowsException() {
		orderService.removeItemFromOrder(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1, 1000, OrderConstants.INVALID_ORDER_ID);
	}
	
	@Test(expected = ItemNotFoundException.class)
	@Transactional
	public void removeItemFromOrder_InValidItemId_ThrowsException() {
		orderService.removeItemFromOrder(ItemInOrderConstants.INVALID_ITEM_IN_ORDER_ID, 1000, OrderConstants.DB_ORDER_ID_1);
	}
	
	@Test(expected = AccessForbiddenException.class)
	public void removeItemFromOrder_InValidCode_ThrowsException() {
		orderService.removeItemFromOrder(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1, UserContants.INVALID_EMPLOYEE_CODE, OrderConstants.DB_ORDER_ID_1);
	}
	
	@Test
	@Transactional
	@Rollback(true)
 	public void save_ValidOrderValidCode_ReturnsOrder() {
		OrderDTO dto = new OrderDTO();
		dto.setTableId(TableConstants.DB_TABLE_ID_4);
		dto.setWaiterId(UserContants.DB_EMPLOYEE_ID_1);
		
		List<ItemInOrderDTO> items = new ArrayList<ItemInOrderDTO>();
		
		ItemInOrderDTO dto1 = new ItemInOrderDTO();
		dto1.setItemId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
		dto1.setQuantity(3);
		dto1.setSellingPrice(BigDecimal.valueOf(300));
		items.add(dto1);
		
		ItemInOrderDTO dto2 = new ItemInOrderDTO();
		dto2.setItemId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_2);
		dto2.setQuantity(2);
		dto2.setSellingPrice(BigDecimal.valueOf(100));
		items.add(dto2);
		
		dto.setItems(items);
		
		orderService.save(dto, 1000);
		
		OrderDTO found = orderService.findById(3);
		
		assertNotNull(found);
	}
	
	@Test(expected = AccessForbiddenException.class)
 	public void save_InValidCode_ThrowsException() {
		OrderDTO dto = new OrderDTO();
		dto.setTableId(TableConstants.DB_TABLE_ID_4);
		dto.setWaiterId(UserContants.DB_EMPLOYEE_ID_1);
		
		List<ItemInOrderDTO> items = new ArrayList<ItemInOrderDTO>();
		
		ItemInOrderDTO dto1 = new ItemInOrderDTO();
		dto1.setItemId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
		dto1.setQuantity(3);
		dto1.setSellingPrice(BigDecimal.valueOf(300));
		items.add(dto1);
		
		ItemInOrderDTO dto2 = new ItemInOrderDTO();
		dto2.setItemId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_2);
		dto2.setQuantity(2);
		dto2.setSellingPrice(BigDecimal.valueOf(100));
		items.add(dto2);
		
		dto.setItems(items);
		
		orderService.save(dto, UserContants.INVALID_EMPLOYEE_CODE);
	}
	
	@Test(expected = ItemNotFoundException.class)
 	public void save_InValidOrderNoTableID_ThrowsException() {
		OrderDTO dto = new OrderDTO();
		dto.setTableId(TableConstants.INVALID_TABLE_ID);
		dto.setWaiterId(UserContants.DB_EMPLOYEE_ID_1);
		
		List<ItemInOrderDTO> items = new ArrayList<ItemInOrderDTO>();
		
		ItemInOrderDTO dto1 = new ItemInOrderDTO();
		dto1.setItemId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
		dto1.setQuantity(3);
		dto1.setSellingPrice(BigDecimal.valueOf(300));
		items.add(dto1);
		
		ItemInOrderDTO dto2 = new ItemInOrderDTO();
		dto2.setItemId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_2);
		dto2.setQuantity(2);
		dto2.setSellingPrice(BigDecimal.valueOf(100));
		items.add(dto2);
		
		dto.setItems(items);
		
		orderService.save(dto, 1000);
	}
	
	@Test(expected = ItemNotValidException.class)
 	public void save_InValidOrderItemHasNoQuantity_ThrowsException() {
		OrderDTO dto = new OrderDTO();
		dto.setTableId(TableConstants.DB_TABLE_ID_1);
		dto.setWaiterId(UserContants.DB_EMPLOYEE_ID_1);
		
		List<ItemInOrderDTO> items = new ArrayList<ItemInOrderDTO>();
		
		ItemInOrderDTO dto1 = new ItemInOrderDTO();
		dto1.setItemId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
		dto1.setSellingPrice(BigDecimal.valueOf(300));
		items.add(dto1);
		
		ItemInOrderDTO dto2 = new ItemInOrderDTO();
		dto2.setItemId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_2);
		dto2.setQuantity(2);
		dto2.setSellingPrice(BigDecimal.valueOf(100));
		items.add(dto2);
		
		dto.setItems(items);
		
		orderService.save(dto, 1000);
	}
	
	@Test(expected = ItemNotFoundException.class)
 	public void save_InValidOrderItemDoesntExist_ThrowsException() {
		OrderDTO dto = new OrderDTO();
		dto.setTableId(TableConstants.DB_TABLE_ID_1);
		dto.setWaiterId(UserContants.DB_EMPLOYEE_ID_1);
		
		List<ItemInOrderDTO> items = new ArrayList<ItemInOrderDTO>();
		
		ItemInOrderDTO dto1 = new ItemInOrderDTO();
		dto1.setItemId(ItemInMenuConstants.INVALID_ITEM_ID);
		dto1.setQuantity(3);
		dto1.setSellingPrice(BigDecimal.valueOf(300));
		items.add(dto1);
		
		ItemInOrderDTO dto2 = new ItemInOrderDTO();
		dto2.setItemId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_2);
		dto2.setQuantity(2);
		dto2.setSellingPrice(BigDecimal.valueOf(100));
		items.add(dto2);
		
		dto.setItems(items);
		
		orderService.save(dto, 1000);
	}
}
