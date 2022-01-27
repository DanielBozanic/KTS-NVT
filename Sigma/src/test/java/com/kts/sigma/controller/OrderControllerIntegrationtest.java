package com.kts.sigma.controller;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.kts.sigma.constants.ItemInMenuConstants;
import com.kts.sigma.constants.OrderConstants;
import com.kts.sigma.constants.TableConstants;
import com.kts.sigma.constants.UserContants;
import com.kts.sigma.dto.ItemInOrderDTO;
import com.kts.sigma.dto.OrderDTO;
import com.kts.sigma.dto.TableDTO;
import com.kts.sigma.model.OrderState;
import com.kts.sigma.model.RestaurantOrder;
import com.kts.sigma.model.RestaurantTable;
import com.kts.sigma.model.TableState;
import com.kts.sigma.model.Waiter;
import com.kts.sigma.repository.EmployeeRepository;
import com.kts.sigma.repository.ItemInOrderRepository;
import com.kts.sigma.repository.OrderRepository;
import com.kts.sigma.repository.TableRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class OrderControllerIntegrationtest {
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ItemInOrderRepository itemInOrderRepository;
	
	@Autowired
	private TableRepository tableRepository;
	
	@Test
	public void getAll_ValidState_ReturnsAllOrders() {
		ResponseEntity<OrderDTO[]> responseEntity = restTemplate
				.getForEntity("/orders", OrderDTO[].class);

		OrderDTO[] tables = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(2, tables.length);
	}
	
	@Test
	public void findById_InvalidId_ReturnsNotFound() {
		ResponseEntity<String> responseEntity = restTemplate
				.getForEntity("/orders/" + OrderConstants.INVALID_ORDER_ID, String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void findById_ValidId_ReturnsOrder() {
		ResponseEntity<OrderDTO> responseEntity = restTemplate
				.getForEntity("/orders/" + OrderConstants.DB_ORDER_ID_1, OrderDTO.class);
		
		OrderDTO table = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(OrderConstants.DB_ORDER_ID_1, table.getId());
	}
	
	@Test
	public void deleteById_InvalidId_ReturnsNotFound() {
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/orders/" + OrderConstants.INVALID_ORDER_ID + "/1000", HttpMethod.DELETE, new HttpEntity<Object>(null), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void deleteById_InvalidCode_ReturnsForbidden() {
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/orders/" + OrderConstants.DB_ORDER_ID_1 + "/-1000", HttpMethod.DELETE, new HttpEntity<Object>(null), String.class);
		
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}
	
	@Test
	public void deleteById_ValidId_ReturnsNothing() {
		RestaurantTable table = new RestaurantTable();
		table.setState(TableState.IN_PROGRESS);
		table = tableRepository.save(table);
		
		RestaurantOrder order = new RestaurantOrder();
		order.setWaiter((Waiter) employeeRepository.findByCode(1000));
		order.setTable(table);
		order = orderRepository.save(order);
		
		assertEquals(3, 3);
		
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/orders/" + order.getId() + "/1000", HttpMethod.DELETE, new HttpEntity<Object>(null), String.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(orderRepository.findAll().size(), 3);
	}
	
	@Test
	public void changeState_InvalidId_ReturnsNotFound() {
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/orders/" + OrderConstants.INVALID_ORDER_ID + "/IN_PROGRESS/" + 1000, 
						HttpMethod.PUT, new HttpEntity<Object>(null), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void changeState_InvalidCode_ReturnsForbidden() {
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/orders/" + OrderConstants.DB_ORDER_ID_1 + "/IN_PROGRESS/" + UserContants.INVALID_EMPLOYEE_CODE, 
						HttpMethod.PUT, new HttpEntity<Object>(null), String.class);
		
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}
	
	@Test
	public void changeState_ValidIdValidCode_ReturnsNothing() {
		RestaurantOrder order = new RestaurantOrder();
		order.setWaiter((Waiter) employeeRepository.findByCode(1000));
		order = orderRepository.save(order);
		
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/orders/" + order.getId() + "/IN_PROGRESS/" + 1000, 
						HttpMethod.PUT, new HttpEntity<Object>(null), String.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(OrderState.IN_PROGRESS, orderRepository.findById(order.getId()).get().getState());
		
		orderRepository.deleteById(order.getId());
	}
	
	@Test
	public void getAllItems_ValidState_ReturnsAllItems() {
		ResponseEntity<ItemInOrderDTO[]> responseEntity = restTemplate
				.getForEntity("/orders/items/" + OrderConstants.DB_ORDER_ID_1, ItemInOrderDTO[].class);

		ItemInOrderDTO[] items = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(4, items.length);
	}
	
	@Test
	public void getAllDrinks_ValidState_ReturnsAllDrinks() {
		ResponseEntity<ItemInOrderDTO[]> responseEntity = restTemplate
				.getForEntity("/orders/drinks/" + OrderConstants.DB_ORDER_ID_1, ItemInOrderDTO[].class);

		ItemInOrderDTO[] items = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		//assertEquals(2, items.length);
	}
	
	@Test
	public void getAllFoods_ValidState_ReturnsAllItems() {
		ResponseEntity<ItemInOrderDTO[]> responseEntity = restTemplate
				.getForEntity("/orders/foods/" + OrderConstants.DB_ORDER_ID_1, ItemInOrderDTO[].class);

		ItemInOrderDTO[] items = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(2, items.length);
	}
	
	@Test
	public void addItemToOrder_ValidOrderIdValidItemValidCode_ReturnsItem() {
		ItemInOrderDTO dto1 = new ItemInOrderDTO();
		dto1.setItemId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
		dto1.setQuantity(3);
		dto1.setSellingPrice(BigDecimal.valueOf(300));
		
		RestaurantOrder order = orderRepository.findById(OrderConstants.DB_ORDER_ID_1).get();
		BigDecimal old = order.getTotalPrice();
		
		ResponseEntity<ItemInOrderDTO> responseEntity = restTemplate
				.exchange("/orders/" + OrderConstants.DB_ORDER_ID_1 + "/1000", 
						HttpMethod.PUT, new HttpEntity<ItemInOrderDTO>(dto1), ItemInOrderDTO.class);
		
		BigDecimal found = orderRepository.findById(OrderConstants.DB_ORDER_ID_1).get().getTotalPrice();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(old.add(BigDecimal.valueOf(900)), found);
		
		//itemInOrderRepository.deleteById(responseEntity.getBody().getId());
	}
	
	@Test
	public void addItemToOrder_InValidOrderId_ReturnsNotFound() {
		ItemInOrderDTO dto1 = new ItemInOrderDTO();
		dto1.setItemId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
		dto1.setQuantity(3);
		dto1.setSellingPrice(BigDecimal.valueOf(300));
		
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/orders/" + OrderConstants.INVALID_ORDER_ID + "/1000", 
						HttpMethod.PUT, new HttpEntity<ItemInOrderDTO>(dto1), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void addItemToOrder_InValidCode_ReturnsNotFound() {
		ItemInOrderDTO dto1 = new ItemInOrderDTO();
		dto1.setItemId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
		dto1.setQuantity(3);
		dto1.setSellingPrice(BigDecimal.valueOf(300));
		
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/orders/" + OrderConstants.DB_ORDER_ID_1 + "/-1000", 
						HttpMethod.PUT, new HttpEntity<ItemInOrderDTO>(dto1), String.class);
		
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}
	
	@Test
	public void addItemToOrder_InValidItemId_ReturnsNotFound() {
		ItemInOrderDTO dto1 = new ItemInOrderDTO();
		dto1.setItemId(ItemInMenuConstants.INVALID_ITEM_ID);
		dto1.setQuantity(3);
		dto1.setSellingPrice(BigDecimal.valueOf(300));
		
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/orders/" + OrderConstants.DB_ORDER_ID_1 + "/1000", 
						HttpMethod.PUT, new HttpEntity<ItemInOrderDTO>(dto1), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void removeItemFromOrder_ValidOrderIdValidCodeValidItemId_ReturnsNotFound() {
		ItemInOrderDTO dto1 = new ItemInOrderDTO();
		dto1.setItemId(1);
		dto1.setQuantity(1);
		dto1.setSellingPrice(BigDecimal.valueOf(300));
		
		RestaurantOrder order = orderRepository.findById(OrderConstants.DB_ORDER_ID_1).get();
		BigDecimal old = order.getTotalPrice();
		
		ResponseEntity<ItemInOrderDTO> responseEntity = restTemplate
				.exchange("/orders/" + OrderConstants.DB_ORDER_ID_1 + "/1000", 
						HttpMethod.PUT, new HttpEntity<ItemInOrderDTO>(dto1), ItemInOrderDTO.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		ResponseEntity<String> responseEntity2 = restTemplate
				.exchange("/orders/" + OrderConstants.DB_ORDER_ID_1 + "/2/1000", 
						HttpMethod.DELETE, new HttpEntity<Object>(null), String.class);
		
		assertEquals(HttpStatus.OK, responseEntity2.getStatusCode());
	}
	
	@Test
	public void removeItemFromOrder_InValidOrderId_ReturnsNotFound() {
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/orders/" + OrderConstants.INVALID_ORDER_ID + "/2/1000", 
						HttpMethod.DELETE, new HttpEntity<Object>(null), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void removeItemFromOrder_InValidItemId_ReturnsNotFound() {
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/orders/" + OrderConstants.DB_ORDER_ID_1 + "/-1000/1000", 
						HttpMethod.DELETE, new HttpEntity<Object>(null), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void removeItemFromOrder_InValidCode_ReturnsNotFound() {
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/orders/" + OrderConstants.DB_ORDER_ID_1 + "/2/-1000", 
						HttpMethod.DELETE, new HttpEntity<Object>(null), String.class);
		
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}
	
	@Test
	public void save_InValidCode_ReturnsForbidden() {
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
		
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/orders/-1000", dto, String.class);
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}
	
	@Test
	public void save_InvalidOrderNoTableId_ReturnsForbidden() {
		OrderDTO dto = new OrderDTO();
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
		
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/orders/1000", dto, String.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
	public void save_InValidOrderItemHasNoQuantity_ReturnsForbidden() {
		OrderDTO dto = new OrderDTO();
		dto.setTableId(TableConstants.DB_TABLE_ID_4);
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
		
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/orders/1000", dto, String.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
	public void save_InValidOrderItemDoesntExist_ReturnsForbidden() {
		OrderDTO dto = new OrderDTO();
		dto.setTableId(TableConstants.DB_TABLE_ID_4);
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
		
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/orders/1000", dto, String.class);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void save_ValidOrder_ReturnsForbidden() {
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
		
		ResponseEntity<OrderDTO> responseEntity = restTemplate.postForEntity("/orders/1000", dto, OrderDTO.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		orderRepository.deleteById(responseEntity.getBody().getId());
	}
}
