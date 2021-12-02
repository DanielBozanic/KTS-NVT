package com.kts.sigma.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.kts.sigma.Exception.ItemExistsException;
import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.constants.ItemConstants;
import com.kts.sigma.constants.ItemInMenuConstants;
import com.kts.sigma.constants.MenuConstants;
import com.kts.sigma.dto.ItemDTO;
import com.kts.sigma.dto.MenuDTO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class MenuServiceIntegrationTest {

	@Autowired
	private MenuService menuService;
	
	@Test
	public void findAll_ValidState_ReturnsAllMenus() {
		List<MenuDTO> found = menuService.getAll();
		assertEquals(MenuConstants.DB_TOTAL_MENUS.intValue(), found.size() - 2);
	}
	
	@Test
	public void addMenu_ValidState_ReturnsCreatedMenu() {
		Integer sizeBeforeAdd = menuService.getAll().size();
		
		MenuDTO menuDto = new MenuDTO();
		menuDto.setExpirationDate(LocalDateTime.of(2022, 3, 18, 0, 0));
		menuDto.setActive(true);
		
		MenuDTO createdMenu = menuService.addMenu(menuDto);
		
		assertEquals(MenuConstants.NEW_MENU_ID, createdMenu.getId());
		assertEquals(sizeBeforeAdd + 1, menuService.getAll().size());
		
		menuService.deleteMenuById(createdMenu.getId());
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void deleteMenuById_InvalidId_ThrowsException() {
		menuService.deleteMenuById(MenuConstants.INVALID_MENU_ID);
	}
	
	@Test
	public void deleteMenuById_ValidId_ReturnsNothing() {
		MenuDTO menuDto = new MenuDTO();
		menuDto.setExpirationDate(LocalDateTime.of(2022, 3, 18, 0, 0));
		menuDto.setActive(true);
		
		MenuDTO createdMenu = menuService.addMenu(menuDto);
		
		Integer createdMenuId = createdMenu.getId();
			
		menuService.deleteMenuById(createdMenuId);
		
		assertTrue(menuService.findById(createdMenuId).getActive() == false);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void findById_InvalidId_ThrowsException() {
		menuService.findById(MenuConstants.INVALID_MENU_ID);
	}
	
	@Test
	public void findById_ValidId_ReturnsMenu() {
		MenuDTO menuDto = menuService.findById(MenuConstants.DB_MENU_ID_1);
		assertEquals(MenuConstants.DB_MENU_ID_1, menuDto.getId());
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void addItemToMenu_InvalidItemId_ThrowsException() {
		ItemDTO itemDto = new ItemDTO();
		itemDto.setId(ItemConstants.INVALID_ITEM_ID);
		
		menuService.addItemToMenu(itemDto, MenuConstants.DB_MENU_ID_1);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void addItemToMenu_InvalidMenuId_ThrowsException() {
		ItemDTO itemDto = new ItemDTO();
		itemDto.setId(ItemConstants.DB_ITEM_ID_1);
		
		menuService.addItemToMenu(itemDto, MenuConstants.INVALID_MENU_ID);
	}
	
	@Test(expected = ItemExistsException.class)
	public void addItemToMenu_ItemExistsInMenu_ThrowsException() {
		ItemDTO itemDto = new ItemDTO();
		itemDto.setId(ItemConstants.DB_ITEM_ID_1);
		
		menuService.addItemToMenu(itemDto, MenuConstants.DB_MENU_ID_1);
	}
	
	@Test
	public void addItemToMenu_ValidState_ReturnsNothing() {
		Integer beforeAdd = menuService.getItemsInMenu(MenuConstants.DB_MENU_ID_1).size();
		
		ItemDTO itemDto = new ItemDTO();
		itemDto.setId(ItemConstants.DB_ITEM_ID_4);
		
		menuService.addItemToMenu(itemDto, MenuConstants.DB_MENU_ID_1);
		
		assertEquals(beforeAdd + 1, menuService.getItemsInMenu(MenuConstants.DB_MENU_ID_1).size());
		
		menuService.removeItemFromMenu(ItemConstants.DB_ITEM_ID_4, MenuConstants.DB_MENU_ID_1);
	}
	
	@Test
	public void getItemsInMenu_InvalidMenuId_ReturnsEmptyList() {
		ArrayList<ItemDTO> found = menuService.getItemsInMenu(MenuConstants.INVALID_MENU_ID);
		assertEquals(0, found.size());
	}
	
	@Test
	public void getItemsInMenu_ValidMenuId_ReturnsItemsInMenu() {
		ArrayList<ItemDTO> found = menuService.getItemsInMenu(MenuConstants.DB_MENU_ID_1);
		assertEquals(ItemInMenuConstants.TOTAL_ACTIVE_ITEMS_IN_FIRST_MENU.intValue(), found.size());
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void removeItemFromMenu_InvalidMenuIdAndInvalidItemId_ThrowsException() {
		menuService.removeItemFromMenu(ItemConstants.INVALID_ITEM_ID, MenuConstants.INVALID_MENU_ID);
	}

	@Test(expected = ItemNotFoundException.class)
	public void removeItemFromMenu_InvalidMenuIdAndValidItemId_ThrowsException() {
		menuService.removeItemFromMenu(ItemConstants.DB_ITEM_ID_1, MenuConstants.INVALID_MENU_ID);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void removeItemFromMenu_ValidMenuIdAndInvalidItemId_ThrowsException() {
		menuService.removeItemFromMenu(ItemConstants.INVALID_ITEM_ID, MenuConstants.DB_MENU_ID_1);
	}
	
	@Test
	public void removeItemFromMenu_ValidMenuIdAndValidItemId_ReturnsNothing() {
		ItemDTO itemDto = new ItemDTO();
		itemDto.setId(ItemConstants.DB_ITEM_ID_2);
		
		menuService.addItemToMenu(itemDto, MenuConstants.DB_MENU_ID_1);
		
		Integer beforeRemove = menuService.getItemsInMenu(MenuConstants.DB_MENU_ID_1).size();
		
		menuService.removeItemFromMenu(ItemConstants.DB_ITEM_ID_2, MenuConstants.DB_MENU_ID_1);
		
		assertEquals(beforeRemove - 1, menuService.getItemsInMenu(MenuConstants.DB_MENU_ID_1).size());
	}
}
