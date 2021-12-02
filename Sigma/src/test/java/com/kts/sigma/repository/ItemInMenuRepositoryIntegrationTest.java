package com.kts.sigma.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.kts.sigma.constants.MenuConstants;
import com.kts.sigma.constants.ItemConstants;
import com.kts.sigma.constants.ItemInMenuConstants;
import com.kts.sigma.model.ItemInMenu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

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
	public void findActiveItemInMenuByItemIdAndMenuId_InvalidMenuIdAndValidItemId_ReturnsNull() {
		ItemInMenu itemInMenu = itemInMenuRepository.findActiveItemInMenuByItemIdAndMenuId(ItemConstants.DB_ITEM_ID_1, 
				MenuConstants.INVALID_MENU_ID);
		assertNull(itemInMenu);
	}
	
	@Test
	public void findActiveItemInMenuByItemIdAndMenuId_ValidMenuIdAndInvalidItemId_ReturnsNull() {
		ItemInMenu itemInMenu = itemInMenuRepository.findActiveItemInMenuByItemIdAndMenuId(ItemConstants.INVALID_ITEM_ID, 
				MenuConstants.DB_MENU_ID_1);
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
	public void findItemInMenuByItemIdAndMenuId_InvalidMenuIdAndValidItemId_ReturnsNull() {
		ItemInMenu itemInMenu = itemInMenuRepository.findItemInMenuByItemIdAndMenuId(ItemConstants.DB_ITEM_ID_1, 
				MenuConstants.INVALID_MENU_ID);
		assertNull(itemInMenu);
	}
	
	@Test
	public void findItemInMenuByItemIdAndMenuId_ValidMenuIdAndInvalidItemId_ReturnsNull() {
		ItemInMenu itemInMenu = itemInMenuRepository.findItemInMenuByItemIdAndMenuId(ItemConstants.INVALID_ITEM_ID, 
				MenuConstants.DB_MENU_ID_1);
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
}
