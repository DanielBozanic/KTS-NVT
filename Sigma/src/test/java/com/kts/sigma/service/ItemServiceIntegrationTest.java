package com.kts.sigma.service;

import static org.junit.Assert.assertTrue;
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

import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.constants.ItemConstants;
import com.kts.sigma.dto.ItemDTO;
import com.kts.sigma.model.Item;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ItemServiceIntegrationTest {

	@Autowired
	ItemService itemService;
	
	@Test
	public void getAll_ValidState_ReturnsAll(){
		List<ItemDTO> items = (List<ItemDTO>) itemService.getAll();
		assertEquals(items.size(), ItemConstants.DB_TOTAL_ITEMS);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void createNewItem_ValidState_ReturnsItem() {
		 ItemDTO item = new ItemDTO(
	                1,"Food", "Food",
	                new BigDecimal(3000),new BigDecimal(5000),true);
		item.setId(null);
		Item i = itemService.createNewItem(item);
		assertEquals(((List<ItemDTO>)itemService.getAll()).size(), ItemConstants.DB_TOTAL_ITEMS+1);
		assertEquals(i.getId(), ItemConstants.DB_TOTAL_ITEMS+1);
	}
	
	@Test
	public void findById_ValidId_ReturnsItem() {
		ItemDTO i = itemService.findById(ItemConstants.DB_FOOD_ID_1);
		assertEquals(i.getId(), ItemConstants.DB_FOOD_ID_1);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void deleteById_ValidId_ReturnsNothing() {
		 ItemDTO item = new ItemDTO(
	                1,"Food", "Food",
	                new BigDecimal(3000),new BigDecimal(5000),true);
		item.setId(null);
		Item i = itemService.createNewItem(item);
		assertEquals(((List<ItemDTO>)itemService.getAll()).size(), ItemConstants.DB_TOTAL_ITEMS+1);
		
		itemService.deleteById(i.getId());
		assertEquals(((List<ItemDTO>)itemService.getAll()).size(), ItemConstants.DB_TOTAL_ITEMS);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void findById_InValidId_ThrowsException() {
		ItemDTO i = itemService.findById(ItemConstants.INVALID_ITEM_ID);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void deleteById_InValidId_ThrowsException() {
		itemService.deleteById(ItemConstants.INVALID_ITEM_ID);
	}
}
