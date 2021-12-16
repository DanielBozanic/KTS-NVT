package com.kts.sigma.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
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
import com.kts.sigma.model.Item;
import com.kts.sigma.model.ItemInMenu;
import com.kts.sigma.model.Menu;
import com.kts.sigma.repository.ItemInMenuRepository;
import com.kts.sigma.repository.MenuRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class MenuControllerIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private MenuRepository menuRepository;
	
	@Autowired
	private ItemInMenuRepository itemInMenuRepository;
	
	@Test
	public void findAll_ValidState_ReturnsAllMenus() {
		ResponseEntity<MenuDTO[]> responseEntity = restTemplate
				.getForEntity("/menu", MenuDTO[].class);

		MenuDTO[] menus = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(MenuConstants.DB_TOTAL_MENUS.intValue(), menus.length);
		assertEquals(MenuConstants.DB_MENU_ID_1.intValue(), menus[0].getId().intValue());
	}
	
	@Test
	public void getActiveNonExpiredMenus_ValidState_ReturnsActiveNonExpiredMenus() {
		ResponseEntity<MenuDTO[]> responseEntity = restTemplate
				.getForEntity("/menu/getActiveNonExpiredMenus", MenuDTO[].class);

		MenuDTO[] menus = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(MenuConstants.DB_TOTAL_ACTIVE_MENUS_NON_EXPIRED.intValue(), menus.length);
		assertEquals(MenuConstants.DB_MENU_ID_1.intValue(), menus[0].getId().intValue());
	}
	
	@Test
	public void addMenu_StartDateAfterEndDate_ReturnsBadRequest() {
		MenuDTO menuDto = new MenuDTO();
		menuDto.setName("Winter");
		menuDto.setStartDate(LocalDateTime.of(2022, 12, 31, 0, 0));
		menuDto.setExpirationDate(LocalDateTime.of(2022, 12, 1, 0, 0));
		menuDto.setActive(true);
		
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(
				"/menu/addMenu", menuDto, String.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
	public void addMenu_ValidState_ReturnsCreatedMenu() {
		Integer size = menuRepository.findAll().size();
		
		MenuDTO menuDto = new MenuDTO();
		menuDto.setName("Winter");
		menuDto.setStartDate(LocalDateTime.of(2023, 1, 1, 0, 0));
		menuDto.setExpirationDate(LocalDateTime.of(2023, 3, 1, 0, 0));
		menuDto.setActive(true);
		
		ResponseEntity<MenuDTO> responseEntity = restTemplate.postForEntity(
				"/menu/addMenu", menuDto, MenuDTO.class);
		
		MenuDTO menu = responseEntity.getBody();
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(size + 1, menuRepository.findAll().size());
		
		menuRepository.deleteById(menu.getId());
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
		Menu menu = new Menu();
		menu.setExpirationDate(LocalDateTime.of(2022, 3, 18, 0, 0));
		menu.setActive(true);
		
		Menu createdMenu = menuRepository.save(menu);
		
		Integer createdMenuId = createdMenu.getId();
			
		ResponseEntity<Void> responseEntity = restTemplate.exchange(
				"/menu/" + createdMenuId, HttpMethod.DELETE,
				new HttpEntity<Object>(null), Void.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(menuRepository.findById(createdMenuId).get().getActive() == false);
		
		menuRepository.deleteById(createdMenuId);
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
		Integer beforeAdd = itemInMenuRepository.getActiveItemsInMenu(MenuConstants.DB_MENU_ID_1).size();
		
		ItemDTO itemDto = new ItemDTO();
		itemDto.setId(ItemConstants.DB_ITEM_ID_2);
		
		ResponseEntity<Void> responseEntity = restTemplate.postForEntity(
				"/menu/addItemToMenu?menuId=" + MenuConstants.DB_MENU_ID_1, itemDto, Void.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(beforeAdd + 1, itemInMenuRepository.getActiveItemsInMenu(MenuConstants.DB_MENU_ID_1).size());
		
		ItemInMenu inm = itemInMenuRepository.findActiveItemInMenuByItemIdAndMenuId(ItemConstants.DB_ITEM_ID_2, MenuConstants.DB_MENU_ID_1);
		inm.setActive(false);
		itemInMenuRepository.save(inm);
	}
	
	@Test
	public void addItemToMenu_FirstTimeInMenu_ReturnsNothing() {
		Integer beforeAdd = itemInMenuRepository.getActiveItemsInMenu(MenuConstants.DB_MENU_ID_1).size();
		
		ItemDTO itemDto = new ItemDTO();
		itemDto.setId(ItemConstants.DB_ITEM_ID_4);
		
		ResponseEntity<Void> responseEntity = restTemplate.postForEntity(
				"/menu/addItemToMenu?menuId=" + MenuConstants.DB_MENU_ID_1, itemDto, Void.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(beforeAdd + 1, itemInMenuRepository.getActiveItemsInMenu(MenuConstants.DB_MENU_ID_1).size());
		
		ItemInMenu inm = itemInMenuRepository.findActiveItemInMenuByItemIdAndMenuId(ItemConstants.DB_ITEM_ID_4, MenuConstants.DB_MENU_ID_1);
		itemInMenuRepository.deleteById(inm.getId());
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
	public void getItemsInMenuByCurrentPage_ValidMenuId_ReturnsItemsInMenuForCurrentPage() {
		ResponseEntity<ItemDTO[]> responseEntity = restTemplate
				.getForEntity("/menu/getItemsInMenuByCurrentPage/" + MenuConstants.DB_MENU_ID_1 + "?currentPage=" + 
								ItemInMenuConstants.CURRENT_PAGE + "&pageSize=" + ItemInMenuConstants.PAGE_SIZE, ItemDTO[].class);

		ItemDTO[] itemsInMenu = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(ItemInMenuConstants.TOTAL_ACTIVE_ITEMS_IN_FIRST_MENU_CURRENT_PAGE.intValue(), itemsInMenu.length);
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
		Item item = new Item();
		item.setId(ItemConstants.DB_ITEM_ID_2);
		
		Menu menu = new Menu();
		menu.setId(MenuConstants.DB_MENU_ID_1);

		ItemInMenu newItemInMenu = new ItemInMenu();
		newItemInMenu.setItem(item);
		newItemInMenu.setMenu(menu);
		newItemInMenu.setSellingPrice(new BigDecimal(200));
		newItemInMenu.setActive(true);
		newItemInMenu = itemInMenuRepository.save(newItemInMenu);
		
		Integer beforeRemove = itemInMenuRepository.getActiveItemsInMenu(MenuConstants.DB_MENU_ID_1).size();
		
		ResponseEntity<Void> responseEntity = restTemplate.exchange(
				"/menu/removeItemFromMenu?itemId=" + ItemConstants.DB_ITEM_ID_2 + 
				"&menuId=" + MenuConstants.DB_MENU_ID_1, 
				HttpMethod.DELETE, 
				new HttpEntity<Object>(null), Void.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(beforeRemove - 1, itemInMenuRepository.getActiveItemsInMenu(MenuConstants.DB_MENU_ID_1).size());
	}
}
