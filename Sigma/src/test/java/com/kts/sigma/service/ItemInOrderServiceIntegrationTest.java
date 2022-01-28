package com.kts.sigma.service;

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
import com.kts.sigma.constants.ItemConstants;
import com.kts.sigma.constants.ItemInMenuConstants;
import com.kts.sigma.constants.ItemInOrderConstants;
import com.kts.sigma.constants.OrderConstants;
import com.kts.sigma.constants.TableConstants;
import com.kts.sigma.constants.UserContants;
import com.kts.sigma.dto.ItemInOrderDTO;
import com.kts.sigma.dto.OrderDTO;
import com.kts.sigma.model.ItemInOrder;
import com.kts.sigma.model.ItemInOrderState;
import com.kts.sigma.model.OrderState;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ItemInOrderServiceIntegrationTest {
	@Autowired
	ItemInOrderService itemInOrderService;
	
	@Test
	public void findAll_ValidState_ReturnsAllItemsInOrders() {
		List<ItemInOrderDTO> orders = (List<ItemInOrderDTO>) itemInOrderService.getAll();
		assertEquals(ItemInOrderConstants.TOTAL_ITEMS_IN_ORDER, orders.size());
	}
	
	@Test
	public void getFoodItemsByOrderId_ValidState_ReturnsAllFoodItems() {
		List<ItemInOrderDTO> orders = itemInOrderService.getFoodItemsByOrderId(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
		assertEquals(ItemInOrderConstants.TOTAL_FOOD_ITEMS_FOR_ORDER1, orders.size());
	}
	
	@Test
	public void getDrinkItemsByOrderId_ValidState_ReturnsAllFoodItems() {
		List<ItemInOrderDTO> orders = itemInOrderService.getDrinkItemsByOrderId(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
		assertEquals(ItemInOrderConstants.TOTAL_DRINK_ITEMS_FOR_ORDER1, orders.size());
	}
	
	@Test(expected = AccessForbiddenException.class)
	public void save_ValidItemInOrderInvalidCode_ThrowsException() {
		itemInOrderService.save(new ItemInOrderDTO(), UserContants.INVALID_EMPLOYEE_CODE);
	}
	@Test(expected = ItemNotFoundException.class)
	public void save_InvalidOrderIdValidCode_ThrowsException() {
		ItemInOrderDTO dto1 = new ItemInOrderDTO();
		dto1.setOrderId(OrderConstants.INVALID_ORDER_ID);	
		itemInOrderService.save(dto1, UserContants.DB_EMPLOYEE_ID_1_CODE);
	}
	@Test(expected = ItemNotFoundException.class)
	public void save_InvalidItemInMenuValidCode_ThrowsException() {
		ItemInOrderDTO dto1 = new ItemInOrderDTO();
		dto1.setOrderId(OrderConstants.DB_ITEM_IN_ORDER1_ID_1);
		dto1.setItemId(ItemConstants.INVALID_ITEM_ID);
		
		itemInOrderService.save(dto1, UserContants.DB_EMPLOYEE_ID_1_CODE);
	}
	@Test(expected = ItemNotFoundException.class)
	public void save_InvalidEmployeeValidCode_ThrowsException() {
		ItemInOrderDTO dto1 = new ItemInOrderDTO();
		dto1.setOrderId(OrderConstants.DB_ITEM_IN_ORDER1_ID_1);
		dto1.setItemId(ItemConstants.DB_ITEM_ID_1);
		dto1.setEmployeeId(UserContants.INVALID_USER_ID);
		
		itemInOrderService.save(dto1, UserContants.DB_EMPLOYEE_ID_1_CODE);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void save_ValidItemInOrderValidCode_ReturnsItemInOrder() {
		ItemInOrderDTO dto1 = new ItemInOrderDTO();
		dto1.setOrderId(OrderConstants.DB_ITEM_IN_ORDER1_ID_1);
		dto1.setItemId(ItemConstants.DB_ITEM_ID_1);
		dto1.setEmployeeId(UserContants.DB_EMPLOYEE_ID_1);
		itemInOrderService.save(dto1, UserContants.DB_EMPLOYEE_ID_1_CODE);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void saveWithoutCode_InvalidItemInMenu_ThrowsException() {
		ItemInOrderDTO dto1 = new ItemInOrderDTO();
		dto1.setOrderId(OrderConstants.DB_ITEM_IN_ORDER1_ID_1);
		dto1.setItemId(ItemConstants.INVALID_ITEM_ID);
		
		itemInOrderService.saveWithoutCode(dto1);
	}
	@Test(expected = ItemNotFoundException.class)
	public void saveWithoutCode_InvalidEmployee_ThrowsException() {
		ItemInOrderDTO dto1 = new ItemInOrderDTO();
		dto1.setOrderId(OrderConstants.DB_ITEM_IN_ORDER1_ID_1);
		dto1.setItemId(ItemConstants.DB_ITEM_ID_1);
		dto1.setEmployeeId(UserContants.INVALID_USER_ID);
		
		itemInOrderService.saveWithoutCode(dto1);
	}
	@Test
	@Transactional
	@Rollback(true)
	public void saveWithoutCode_ValidItemInOrder_ReturnsItemInOrder() {
		ItemInOrderDTO dto1 = new ItemInOrderDTO();
		dto1.setOrderId(OrderConstants.DB_ITEM_IN_ORDER1_ID_1);
		dto1.setItemId(ItemConstants.DB_ITEM_ID_1);
		dto1.setEmployeeId(UserContants.DB_EMPLOYEE_ID_1);
		itemInOrderService.saveWithoutCode(dto1);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void deleteById_InvalidId_ThrowsException() {
		itemInOrderService.deleteById(ItemInOrderConstants.INVALID_ITEM_IN_ORDER_ID);
	}
	@Test
	@Transactional
	@Rollback(true)
	public void deleteById_ValidId_ReturnsNothing() {
		int found = ((List<ItemInOrderDTO>) itemInOrderService.getAll()).size();
		itemInOrderService.deleteById(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
		int found2 = ((List<ItemInOrderDTO>) itemInOrderService.getAll()).size();
		assertEquals(found - 1, found2);
	}
	
	@Test
	public void findById_ValidId_ReturnsOrder() {
		ItemInOrderDTO found = itemInOrderService.findById(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
		assertNotNull(found);
	}
	@Test(expected = ItemNotFoundException.class)
	public void findById_InValidId_ThrowsException() {
		itemInOrderService.findById(ItemInOrderConstants.INVALID_ITEM_IN_ORDER_ID);
	}
	
	@Test(expected = AccessForbiddenException.class)
	public void changeState_InvalidCode_ThrowsException() {
		itemInOrderService.changeState(
				ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1,
				ItemInOrderState.DONE,
				UserContants.INVALID_EMPLOYEE_CODE);
	}
	@Test(expected = ItemNotFoundException.class)
	public void changeState_ValidCodeInvalidItemInOrder_ThrowsException() {
		itemInOrderService.changeState(
				ItemInOrderConstants.INVALID_ITEM_IN_ORDER_ID,
				ItemInOrderState.DONE,
				UserContants.DB_EMPLOYEE_ID_1_CODE);
	}
	@Test
	@Transactional
	@Rollback(true)
	public void changeState_ValidCodeStateToDeliver_ReturnsItemInOrder() {
		ItemInOrderDTO dto = itemInOrderService.changeState(
				ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1,
				ItemInOrderState.TO_DELIVER,
				UserContants.DB_EMPLOYEE_ID_1_CODE);
		
		assertEquals(dto.getState(), ItemInOrderState.TO_DELIVER);
	}
	@Test
	@Transactional
	@Rollback(true)
	public void changeState_ValidCodeStateToDeliverAllDone_ReturnsItemInOrder() {
		ItemInOrderDTO dto = itemInOrderService.changeState(
				ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1,
				ItemInOrderState.TO_DELIVER,
				UserContants.DB_EMPLOYEE_ID_1_CODE);
		
		assertEquals(dto.getState(), ItemInOrderState.TO_DELIVER);
	}
	@Test
	@Transactional
	@Rollback(true)
	public void changeState_ValidCodeStateDone_ReturnsItemInOrder() {
		ItemInOrderDTO dto = itemInOrderService.changeState(
				ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1,
				ItemInOrderState.DONE,
				UserContants.DB_EMPLOYEE_ID_1_CODE);
		
		assertEquals(dto.getState(), ItemInOrderState.DONE);
	}
	@Test
	@Transactional
	@Rollback(true)
	public void changeState_ValidCodeStateDoneAllDone_ReturnsItemInOrder() {
		ItemInOrderDTO dto = itemInOrderService.changeState(
				ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_2,
				ItemInOrderState.DONE,
				UserContants.DB_EMPLOYEE_ID_1_CODE);
		
		assertEquals(dto.getState(), ItemInOrderState.DONE);
	}
	@Test(expected = AccessForbiddenException.class)
	public void changeState_ValidCodeStateInProgressCookEmployee_ThrowsException() {
		ItemInOrderDTO dto = itemInOrderService.changeState(
				ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_2,
				ItemInOrderState.IN_PROGRESS,
				UserContants.DB_COOK_EMPLOYEE_CODE);
	}
	@Test
	@Transactional
	@Rollback(true)
	public void changeState_ValidCodeStateInProgressCookEmployee_ReturnsItemInOrder() {
		ItemInOrderDTO dto = itemInOrderService.changeState(
				ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_3,
				ItemInOrderState.IN_PROGRESS,
				UserContants.DB_COOK_EMPLOYEE_CODE);
		
		assertEquals(dto.getState(), ItemInOrderState.IN_PROGRESS);
	}
	@Test
	@Transactional
	@Rollback(true)
	public void changeState_ValidCodeStateInProgressBartenderEmployee_ReturnsItemInOrder() {
		ItemInOrderDTO dto = itemInOrderService.changeState(
				ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1,
				ItemInOrderState.IN_PROGRESS,
				UserContants.DB_EMPLOYEE_ID_1_CODE);
		assertEquals(dto.getState(), ItemInOrderState.IN_PROGRESS);
	}
	
	@Test(expected = AccessForbiddenException.class)
	public void put_InvalidCode_ThrowsException() {
		itemInOrderService.put(new ItemInOrderDTO(), UserContants.INVALID_EMPLOYEE_CODE);
	}
	@Test(expected = ItemNotFoundException.class)
	public void put_ValidCodeInvalidItemInOrderId_ThrowsException() {
		ItemInOrderDTO dto = new ItemInOrderDTO();
		dto.setId(ItemInOrderConstants.INVALID_ITEM_IN_ORDER_ID);
		itemInOrderService.put(dto, UserContants.DB_EMPLOYEE_ID_1_CODE);
	}
	@Test(expected = ItemNotFoundException.class)
	public void put_ValidCodeInvalidOrderId_ThrowsException() {
		ItemInOrderDTO dto = new ItemInOrderDTO();
		dto.setId(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
		dto.setOrderId(OrderConstants.INVALID_ORDER_ID);
		itemInOrderService.put(dto, UserContants.DB_EMPLOYEE_ID_1_CODE);
	}
	@Test(expected = ItemNotFoundException.class)
	public void put_ValidCodeInvalidItemInMenuId_ThrowsException() {
		ItemInOrderDTO dto = new ItemInOrderDTO();
		dto.setId(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
		dto.setOrderId(OrderConstants.DB_ORDER_ID_1);
		dto.setItemId(ItemInMenuConstants.INVALID_ITEM_ID);
		itemInOrderService.put(dto, UserContants.DB_EMPLOYEE_ID_1_CODE);
	}
	@Test
	@Transactional
	@Rollback(true)
	public void put_ValidCodeValidItemInOrder_ReturnsNothing() {
		ItemInOrderDTO dto = new ItemInOrderDTO();
		dto.setId(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
		dto.setOrderId(OrderConstants.DB_ORDER_ID_1);
		dto.setState(ItemInOrderState.DONE);
		dto.setItemId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
		dto.setEmployeeId(UserContants.DB_EMPLOYEE_ID_1);
		itemInOrderService.put(dto, UserContants.DB_EMPLOYEE_ID_1_CODE);
		ItemInOrderDTO iioDTO = itemInOrderService.findById(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
		assertEquals(iioDTO.getState(), ItemInOrderState.DONE);
	}
}
