package com.kts.sigma.controller;

import static org.junit.Assert.assertEquals;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kts.sigma.Exception.AccessForbiddenException;
import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.constants.ItemConstants;
import com.kts.sigma.constants.ItemInOrderConstants;
import com.kts.sigma.constants.OrderConstants;
import com.kts.sigma.constants.UserContants;
import com.kts.sigma.dto.ItemInOrderDTO;
import com.kts.sigma.dto.OrderDTO;
import com.kts.sigma.model.Employee;
import com.kts.sigma.model.ItemInMenu;
import com.kts.sigma.model.ItemInOrder;
import com.kts.sigma.model.ItemInOrderState;
import com.kts.sigma.model.RestaurantOrder;
import com.kts.sigma.model.User;
import com.kts.sigma.model.Waiter;
import com.kts.sigma.repository.ItemInOrderRepository;
import com.kts.sigma.service.ItemInOrderService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ItemInOrderControllerIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ItemInOrderRepository inOrderRepository;
	
	@Test
	public void getAll_ValidState_ReturnsAllItems() {
		ResponseEntity<ItemInOrderDTO[]> responseEntity = restTemplate
				.getForEntity("/item-in-order", ItemInOrderDTO[].class);

		ItemInOrderDTO[] result = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(5, result.length);
	}
	
	@Test
	public void findById_InvalidId_ReturnsNotFound() {
		ResponseEntity<String> responseEntity = restTemplate
				.getForEntity("/item-in-order/" + ItemInOrderConstants.INVALID_ITEM_IN_ORDER_ID, String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void findById_ValidId_ReturnsOrder() {
		ResponseEntity<ItemInOrderDTO> responseEntity = restTemplate
				.getForEntity("/item-in-order/" + ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1, ItemInOrderDTO.class);
		
		ItemInOrderDTO item = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1, item.getId());
	}
	
	@Test
	public void post_InvalidCode_ReturnsNothing() {
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/item-in-order/" + UserContants.INVALID_EMPLOYEE_CODE,
						HttpMethod.POST, new HttpEntity<ItemInOrderDTO>(new ItemInOrderDTO()), String.class);
		
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}
	
	@Test
	public void post_InvalidOrderId_ReturnsNothing() {
		ItemInOrderDTO item = new ItemInOrderDTO();
		item.setOrderId(OrderConstants.INVALID_ORDER_ID);
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/item-in-order/" + UserContants.DB_EMPLOYEE_ID_1_CODE,
						HttpMethod.POST, new HttpEntity<ItemInOrderDTO>(item), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void post_InvalidItemId_ReturnsNothing() {
		ItemInOrderDTO item = new ItemInOrderDTO();
		item.setOrderId(OrderConstants.DB_ORDER_ID_1);
		item.setItemId(ItemInOrderConstants.INVALID_ITEM_IN_ORDER_ID);
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/item-in-order/" + UserContants.DB_EMPLOYEE_ID_1_CODE,
						HttpMethod.POST, new HttpEntity<ItemInOrderDTO>(item), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void post_InvalidEmployeeId_ReturnsNothing() {
		ItemInOrderDTO item = new ItemInOrderDTO();
		item.setOrderId(OrderConstants.DB_ORDER_ID_1);
		item.setItemId(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
		item.setEmployeeId(UserContants.INVALID_USER_ID);
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/item-in-order/" + UserContants.DB_EMPLOYEE_ID_1_CODE,
						HttpMethod.POST, new HttpEntity<ItemInOrderDTO>(item), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void post_ValidState_ReturnsItem() {
		ItemInOrderDTO item = new ItemInOrderDTO();
		item.setOrderId(OrderConstants.DB_ORDER_ID_1);
		item.setItemId(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
		item.setEmployeeId(UserContants.DB_EMPLOYEE_ID_1);
		ResponseEntity<ItemInOrder> responseEntity = restTemplate
				.exchange("/item-in-order/" + UserContants.DB_EMPLOYEE_ID_1_CODE,
						HttpMethod.POST, new HttpEntity<ItemInOrderDTO>(item), ItemInOrder.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		inOrderRepository.deleteById(responseEntity.getBody().getId());
	}
	
	@Test
	public void put_InvalidCode_ReturnsNothing() {
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/item-in-order/" + UserContants.INVALID_EMPLOYEE_CODE,
						HttpMethod.PUT, new HttpEntity<ItemInOrderDTO>(new ItemInOrderDTO()), String.class);
		
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}
	
	@Test
	public void put_InvalidId_ReturnsNothing() {
		ItemInOrderDTO item = new ItemInOrderDTO();
		item.setId(ItemInOrderConstants.INVALID_ITEM_IN_ORDER_ID);
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/item-in-order/" + UserContants.DB_EMPLOYEE_ID_1_CODE,
						HttpMethod.PUT, new HttpEntity<ItemInOrderDTO>(item), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void put_InvalidOrderId_ReturnsNothing() {
		ItemInOrderDTO item = new ItemInOrderDTO();
		item.setId(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
		item.setOrderId(OrderConstants.INVALID_ORDER_ID);
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/item-in-order/" + UserContants.DB_EMPLOYEE_ID_1_CODE,
						HttpMethod.PUT, new HttpEntity<ItemInOrderDTO>(item), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void put_InvalidItemId_ReturnsNothing() {
		ItemInOrderDTO item = new ItemInOrderDTO();
		item.setId(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
		item.setOrderId(OrderConstants.DB_ORDER_ID_1);
		item.setItemId(ItemConstants.INVALID_ITEM_ID);
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/item-in-order/" + UserContants.DB_EMPLOYEE_ID_1_CODE,
						HttpMethod.PUT, new HttpEntity<ItemInOrderDTO>(item), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void put_InvalidEmployeeId_ReturnsNothing() {
		ItemInOrderDTO item = new ItemInOrderDTO();
		item.setId(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
		item.setOrderId(OrderConstants.DB_ORDER_ID_1);
		item.setItemId(ItemConstants.DB_ITEM_ID_1);
		item.setEmployeeId(UserContants.INVALID_USER_ID);
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/item-in-order/" + UserContants.DB_EMPLOYEE_ID_1_CODE,
						HttpMethod.PUT, new HttpEntity<ItemInOrderDTO>(item), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void put_ValidState_ReturnsNothing() {
		ItemInOrderDTO item = new ItemInOrderDTO();
		item.setId(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1);
		item.setOrderId(OrderConstants.DB_ORDER_ID_1);
		item.setItemId(ItemConstants.DB_ITEM_ID_1);
		item.setEmployeeId(UserContants.DB_EMPLOYEE_ID_1);
		ResponseEntity<ItemInOrder> responseEntity = restTemplate
				.exchange("/item-in-order/" + UserContants.DB_EMPLOYEE_ID_1_CODE,
						HttpMethod.PUT, new HttpEntity<ItemInOrderDTO>(item), ItemInOrder.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	@Test
	public void changeState_InvalidCode_ReturnsNothing() {
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/item-in-order/"
						+ ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1 + "/"
						+ "IN_PROGRESS" + "/"
						+ UserContants.INVALID_EMPLOYEE_CODE,
						HttpMethod.PUT, new HttpEntity<Object>(null), String.class);
		
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}
	
	@Test
	public void changeState_InvalidId_ReturnsNothing() {
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/item-in-order/"
						+ ItemInOrderConstants.INVALID_ITEM_IN_ORDER_ID + "/"
						+ "IN_PROGRESS" + "/"
						+ UserContants.DB_EMPLOYEE_ID_1_CODE,
						HttpMethod.PUT, new HttpEntity<Object>(null), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void changeState_InvalidState_ReturnsNothing() {
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/item-in-order/"
						+ ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_3 + "/"
						+ "IN_PROGRESS" + "/"
						+ UserContants.DB_EMPLOYEE_ID_1_CODE,
						HttpMethod.PUT, new HttpEntity<Object>(null), String.class);
		
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}
	
	@Test
	public void changeState_ValidState_ReturnsItem() {
		ResponseEntity<ItemInOrderDTO> responseEntity = restTemplate
				.exchange("/item-in-order/"
						+ ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1 + "/"
						+ "IN_PROGRESS" + "/"
						+ UserContants.DB_EMPLOYEE_ID_1_CODE,
						HttpMethod.PUT, new HttpEntity<Object>(null), ItemInOrderDTO.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1, responseEntity.getBody().getId());
	}
	
	@Test
	public void deleteById_InvalidId_ReturnsNotFound() {
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/item-in-order/" + ItemInOrderConstants.INVALID_ITEM_IN_ORDER_ID, HttpMethod.DELETE, new HttpEntity<Object>(null), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void deleteById_ValidId_ReturnsNothing() {
		ItemInOrder item = new ItemInOrder();
		item = inOrderRepository.save(item);
		
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/item-in-order/" + item.getId(), HttpMethod.DELETE, new HttpEntity<Object>(null), String.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}
