package com.kts.sigma.controller;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

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

import com.kts.sigma.constants.ItemConstants;
import com.kts.sigma.dto.ItemDTO;
import com.kts.sigma.model.Item;
import com.kts.sigma.repository.ItemRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ItemControllerIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Test
	public void getAll_ValidState_ReturnsAll() {
		ResponseEntity<ItemDTO[]> responseEntity = restTemplate
				.getForEntity("/items", ItemDTO[].class);

		ItemDTO[] tables = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void findById_ValidState_ReturnsItem() {
		ResponseEntity<ItemDTO> responseEntity = restTemplate
				.getForEntity("/items/" + ItemConstants.DB_FOOD_ID_1, ItemDTO.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(ItemConstants.DB_FOOD_ID_1, responseEntity.getBody().getId());
	}
	
	@Test
	public void findById_InValidState_ThrowsException() {
		ResponseEntity<String> responseEntity = restTemplate
				.getForEntity("/items/" + ItemConstants.INVALID_ITEM_ID, String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void deleteById_InValidState_ThrowsException() {
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/items/" + ItemConstants.INVALID_ITEM_ID, HttpMethod.DELETE, new HttpEntity<Object>(null), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void deleteById_ValidState_ReturnsNothing() {
		Item item = new Item(ItemConstants.DB_FOOD_ID_1, "Spaghett", "Spaghett", BigDecimal.ZERO);
		item.setId(null);
		item = itemRepository.save(item);
		
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/items/" + item.getId(), HttpMethod.DELETE, new HttpEntity<Object>(null), String.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	@Test
	public void createNewItem_ValidState_ReturnsItem() {
		ItemDTO item = new ItemDTO(
                1,"Food", "Food",
                new BigDecimal(3000),new BigDecimal(5000),true);
		item.setId(null);
		
		ResponseEntity<ItemDTO> responseEntity = restTemplate
				.exchange("/items/createNewItem", HttpMethod.POST, new HttpEntity<Object>(item), ItemDTO.class);
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		itemRepository.deleteById(responseEntity.getBody().getId());
	}
}
