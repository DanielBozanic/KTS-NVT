package com.kts.sigma.repository;

import com.kts.sigma.model.FoodType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.kts.sigma.constants.MenuConstants;
import com.kts.sigma.constants.ItemConstants;
import com.kts.sigma.constants.ItemInMenuConstants;
import com.kts.sigma.model.ItemInMenu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ItemInMenuRepositoryIntegrationTest {
	
	@Autowired
	private ItemInMenuRepository itemInMenuRepository;
	
	@Test
	public void findActiveItemInMenuByItemIdAndMenuId_InvalidMenuIdAndInvalidItemId_ReturnsNull() {
		ItemInMenu itemInMenu = itemInMenuRepository.findActiveItemInMenuByItemIdAndMenuId(ItemConstants.INVALID_ITEM_ID, 
				MenuConstants.INVALID_MENU_ID);
		assertNull(itemInMenu);
	}
	
	@Test
	public void findActiveItemInMenuByItemIdAndMenuId_ValidMenuIdAndValidItemId_ReturnsItemInMenu() {
		ItemInMenu itemInMenu = itemInMenuRepository.findActiveItemInMenuByItemIdAndMenuId(ItemConstants.DB_ITEM_ID_1, 
				MenuConstants.DB_MENU_ID_1);
		
		assertEquals(ItemConstants.DB_ITEM_ID_1, itemInMenu.getItem().getId());
		assertEquals(MenuConstants.DB_MENU_ID_1, itemInMenu.getMenu().getId());
	}
	
	@Test
	public void findItemInMenuByItemIdAndMenuId_InvalidMenuIdAndInvalidItemId_ReturnsNull() {
		ItemInMenu itemInMenu = itemInMenuRepository.findItemInMenuByItemIdAndMenuId(ItemConstants.INVALID_ITEM_ID, 
				MenuConstants.INVALID_MENU_ID);
		assertNull(itemInMenu);
	}
	
	@Test
	public void findItemInMenuByItemIdAndMenuId_ValidMenuIdAndValidItemId_ReturnsItemInMenu() {
		ItemInMenu itemInMenu = itemInMenuRepository.findItemInMenuByItemIdAndMenuId(ItemConstants.DB_ITEM_ID_2, 
				MenuConstants.DB_MENU_ID_1);
		
		assertEquals(ItemConstants.DB_ITEM_ID_2, itemInMenu.getItem().getId());
		assertEquals(MenuConstants.DB_MENU_ID_1, itemInMenu.getMenu().getId());
	}
	
	@Test
	public void getActiveItemsInMenu_InvalidMenuId_ReturnsEmptyList() {
		ArrayList<ItemInMenu> itemsInMenu = itemInMenuRepository.getActiveItemsInMenu(MenuConstants.INVALID_MENU_ID);
		assertEquals(0, itemsInMenu.size());
	}
	
	@Test
	public void getActiveItemsInMenu_ValidMenuId_ReturnsItemsInMenu() {
		ArrayList<ItemInMenu> itemsInMenu = itemInMenuRepository.getActiveItemsInMenu(MenuConstants.DB_MENU_ID_1);
		assertEquals(ItemInMenuConstants.TOTAL_ACTIVE_ITEMS_IN_FIRST_MENU.intValue(), itemsInMenu.size());
	}
	
	@Test
	public void findAllActiveItemsInMenuByCurrentPage_InvalidMenuId_ReturnsEmptyList() {
		Pageable page = PageRequest.of(ItemInMenuConstants.CURRENT_PAGE, ItemInMenuConstants.PAGE_SIZE);
		List<ItemInMenu> itemsInMenu = itemInMenuRepository.findAllActiveItemsInMenuByCurrentPage(MenuConstants.INVALID_MENU_ID, page).toList();
		assertEquals(0, itemsInMenu.size());
	}
	
	@Test
	public void findAllActiveItemsInMenuByCurrentPage_ValidMenuId_ReturnsItemsInMenu() {
		Pageable page = PageRequest.of(ItemInMenuConstants.CURRENT_PAGE, ItemInMenuConstants.PAGE_SIZE);
		List<ItemInMenu> itemsInMenu = itemInMenuRepository.findAllActiveItemsInMenuByCurrentPage(MenuConstants.DB_MENU_ID_1, page).toList();
		assertEquals(ItemInMenuConstants.TOTAL_ACTIVE_ITEMS_IN_FIRST_MENU_CURRENT_PAGE.intValue(), itemsInMenu.size());
	}

	@Test
	public void findAllActiveItemsInMenuByFoodType_ValidMenuId_ReturnsItemsInMenu(){
		ArrayList<ItemInMenu> itemsInMenu = itemInMenuRepository.findAllActiveItemsInMenuByFoodType(MenuConstants.DB_MENU_ID_1, FoodType.DESERT);
		assertEquals(MenuConstants.DB_TOTAL_DESSERTS_IN_MENU.intValue(), itemsInMenu.size());
	}

	@Test
	public void findAllActiveItemsInMenuByFoodType_InvalidMenuId_ReturnsItemsInMenu(){
		ArrayList<ItemInMenu> itemsInMenu = itemInMenuRepository.findAllActiveItemsInMenuByFoodType(MenuConstants.INVALID_MENU_ID, FoodType.APPETISER);
		assertEquals(0, itemsInMenu.size());
	}

	@Test
	public void getNumberOfActiveItemInMenuRecordsByMenuId_ValidMenuId_ReturnsNumberOfActiveItemInMenu() {
		Integer numberOfActive = itemInMenuRepository.getNumberOfActiveItemInMenuRecordsByMenuId(MenuConstants.DB_MENU_ID_1);
		assertEquals(MenuConstants.DB_TOTAL_ACTIVE_ITEMS_IN_MENU_1, numberOfActive);
	}
}
