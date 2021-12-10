package com.kts.sigma.controller;

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
import org.springframework.web.bind.annotation.RequestBody;

import static org.junit.Assert.assertEquals;

import com.kts.sigma.constants.ItemInOrderConstants;
import com.kts.sigma.dto.ItemDTO;
import com.kts.sigma.model.ItemInMenu;
import com.kts.sigma.repository.ItemInMenuRepository;
import com.kts.sigma.service.ItemInMenuService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ItemInMenuControllerIntegrationTest {
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ItemInMenuRepository itemInMenuRepository;
	
	@Test
	public void getAll_ValidState_ReturnsAllItemsInMenu(){
		ResponseEntity<ItemDTO[]> responseEntity = restTemplate
				.getForEntity("/item-in-menu", ItemDTO[].class);
		ItemDTO[] orders = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(4, orders.length);
	}
	
	@Test
	public void getOne_InvalidId_ReturnsNotFound()
	{
		ResponseEntity<String> responseEntity = restTemplate
				.getForEntity("/item-in-menu/" + ItemInOrderConstants.INVALID_ITEM_IN_ORDER_ID, String.class);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void getOne_ValidId_ReturnsItem()
	{
		ResponseEntity<ItemDTO> responseEntity = restTemplate
				.getForEntity("/item-in-menu/" + ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1, ItemDTO.class);
		ItemDTO item = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(ItemInOrderConstants.DB_ITEM_IN_ORDER_ID_1, item.getId());
	}
	
	@Test
	public void post_ValidState_ReturnsItem()
	{
		ItemInMenu item = new ItemInMenu();
		
		ResponseEntity<ItemInMenu> responseEntity = restTemplate
				.postForEntity("/item-in-menu", item, ItemInMenu.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		itemInMenuRepository.deleteById(responseEntity.getBody().getId());
	}
	
	@Test
	public void delete_InvalidId_ReturnsNothing()
	{
		ResponseEntity<String> responseEntity = restTemplate
				.exchange(
						"/item-in-menu/"+ItemInOrderConstants.INVALID_ITEM_IN_ORDER_ID, HttpMethod.DELETE,
						new HttpEntity<Object>(null), String.class);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	@Test
	public void delete_ValidId_ReturnsNothing()
	{
		ItemInMenu item = new ItemInMenu();
		item = itemInMenuRepository.save(item);
		
		ResponseEntity<String> responseEntity = restTemplate
				.exchange(
						"/item-in-menu/"+item.getId(), HttpMethod.DELETE,
						new HttpEntity<Object>(null), String.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}
