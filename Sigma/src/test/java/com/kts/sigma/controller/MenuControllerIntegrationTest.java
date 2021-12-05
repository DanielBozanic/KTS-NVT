package com.kts.sigma.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

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
import com.kts.sigma.constants.ItemInMenuConstants;
import com.kts.sigma.constants.MenuConstants;
import com.kts.sigma.dto.ItemDTO;
import com.kts.sigma.dto.MenuDTO;
import com.kts.sigma.service.MenuService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class MenuControllerIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private MenuService menuService;
	
	@Test
	public void findAll_ValidState_ReturnsAllMenus() {
		ResponseEntity<MenuDTO[]> responseEntity = restTemplate
				.getForEntity("/menu", MenuDTO[].class);

		MenuDTO[] menus = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(MenuConstants.DB_TOTAL_MENUS + 2, menus.length);
		assertEquals(MenuConstants.DB_MENU_ID_1.intValue(), menus[0].getId().intValue());
	}
	
	@Test
	public void addMenu_ValidState_ReturnsCreatedMenu() {
		Integer size = menuService.getAll().size();
		
		MenuDTO menuDto = new MenuDTO();
		menuDto.setExpirationDate(LocalDateTime.of(2022, 3, 18, 0, 0));
		menuDto.setActive(true);
		
		ResponseEntity<MenuDTO> responseEntity = restTemplate.postForEntity(
				"/menu/addMenu", menuDto, MenuDTO.class);
		
		MenuDTO menu = responseEntity.getBody();
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(size + 1, menuService.getAll().size());
		
		menuService.deleteMenuById(menu.getId());
	}
	
	@Test
	public void deleteMenuById_InvalidId_ReturnsNotFound() {
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"/menu/" + MenuConstants.INVALID_MENU_ID, HttpMethod.DELETE, 
				new HttpEntity<Object>(null), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void deleteMenuById_ValidId_ReturnsNothing() {
		MenuDTO menuDto = new MenuDTO();
		menuDto.setExpirationDate(LocalDateTime.of(2022, 3, 18, 0, 0));
		menuDto.setActive(true);
		
		MenuDTO createdMenu = menuService.addMenu(menuDto);
		
		Integer createdMenuId = createdMenu.getId();
			
		ResponseEntity<Void> responseEntity = restTemplate.exchange(
				"/menu/" + createdMenuId, HttpMethod.DELETE,
				new HttpEntity<Object>(null), Void.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(menuService.findById(createdMenuId).getActive() == false);
	}
	
	@Test
	public void findById_InvalidId_ReturnsNotFound() {
		ResponseEntity<String> responseEntity = restTemplate
				.getForEntity("/menu/" + MenuConstants.INVALID_MENU_ID , String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void findById_ValidId_ReturnsMenu() {
		ResponseEntity<MenuDTO> responseEntity = restTemplate
				.getForEntity("/menu/" + MenuConstants.DB_MENU_ID_1 , MenuDTO.class);
		
		MenuDTO menu = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(MenuConstants.DB_MENU_ID_1, menu.getId());
	}
	
	@Test
	public void addItemToMenu_InvalidItemId_ReturnsNotFound() {
		ItemDTO itemDto = new ItemDTO();
		itemDto.setId(ItemConstants.INVALID_ITEM_ID);
		
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(
				"/menu/addItemToMenu?menuId=" + MenuConstants.DB_MENU_ID_1, itemDto, String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void addItemToMenu_InvalidMenuId_ReturnsNotFound() {
		ItemDTO itemDto = new ItemDTO();
		itemDto.setId(ItemConstants.DB_ITEM_ID_1);
		
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(
				"/menu/addItemToMenu?menuId=" + MenuConstants.INVALID_MENU_ID, itemDto, String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void addItemToMenu_ItemExistsInMenu_ReturnsBadRequest() {
		ItemDTO itemDto = new ItemDTO();
		itemDto.setId(ItemConstants.DB_ITEM_ID_1);
		
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(
				"/menu/addItemToMenu?menuId=" + MenuConstants.DB_MENU_ID_1, itemDto, String.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
	public void addItemToMenu_ReaddItemToMenu_ReturnsNothing() {
		Integer beforeAdd = menuService.getItemsInMenu(MenuConstants.DB_MENU_ID_1).size();
		
		ItemDTO itemDto = new ItemDTO();
		itemDto.setId(ItemConstants.DB_ITEM_ID_2);
		
		ResponseEntity<Void> responseEntity = restTemplate.postForEntity(
				"/menu/addItemToMenu?menuId=" + MenuConstants.DB_MENU_ID_1, itemDto, Void.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(beforeAdd + 1, menuService.getItemsInMenu(MenuConstants.DB_MENU_ID_1).size());
		
		menuService.removeItemFromMenu(ItemConstants.DB_ITEM_ID_2, MenuConstants.DB_MENU_ID_1);
	}
	
	@Test
	public void addItemToMenu_FirstTimeInMenu_ReturnsNothing() {
		Integer beforeAdd = menuService.getItemsInMenu(MenuConstants.DB_MENU_ID_1).size();
		
		ItemDTO itemDto = new ItemDTO();
		itemDto.setId(ItemConstants.DB_ITEM_ID_4);
		
		ResponseEntity<Void> responseEntity = restTemplate.postForEntity(
				"/menu/addItemToMenu?menuId=" + MenuConstants.DB_MENU_ID_1, itemDto, Void.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(beforeAdd + 1, menuService.getItemsInMenu(MenuConstants.DB_MENU_ID_1).size());
		
		menuService.removeItemFromMenu(ItemConstants.DB_ITEM_ID_4, MenuConstants.DB_MENU_ID_1);
	}
	
	@Test
	public void getItemsInMenu_ValidMenuId_ReturnsItemsInMenu() {
		ResponseEntity<ItemDTO[]> responseEntity = restTemplate
				.getForEntity("/menu/getItemsInMenu/" + MenuConstants.DB_MENU_ID_1, ItemDTO[].class);

		ItemDTO[] itemsInMenu = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(ItemInMenuConstants.TOTAL_ACTIVE_ITEMS_IN_FIRST_MENU.intValue(), itemsInMenu.length);
	}
	
	@Test
	public void removeItemFromMenu_InvalidMenuIdAndInvalidItemId_ReturnsNotFound() {
		ResponseEntity<String> responseEntity = restTemplate.exchange(
					"/menu/removeItemFromMenu?itemId=" + ItemConstants.INVALID_ITEM_ID + 
					"&menuId=" + MenuConstants.INVALID_MENU_ID, 
					HttpMethod.DELETE, 
					new HttpEntity<Object>(null), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void removeItemFromMenu_ValidMenuIdAndValidItemId_ReturnsNothing() {
		ItemDTO itemDto = new ItemDTO();
		itemDto.setId(ItemConstants.DB_ITEM_ID_2);
		
		menuService.addItemToMenu(itemDto, MenuConstants.DB_MENU_ID_1);
		
		Integer beforeRemove = menuService.getItemsInMenu(MenuConstants.DB_MENU_ID_1).size();
		
		ResponseEntity<Void> responseEntity = restTemplate.exchange(
				"/menu/removeItemFromMenu?itemId=" + ItemConstants.DB_ITEM_ID_2 + 
				"&menuId=" + MenuConstants.DB_MENU_ID_1, 
				HttpMethod.DELETE, 
				new HttpEntity<Object>(null), Void.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(beforeRemove - 1, menuService.getItemsInMenu(MenuConstants.DB_MENU_ID_1).size());
	}
}
