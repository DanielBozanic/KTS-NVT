package com.kts.sigma.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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
		assertEquals(MenuConstants.DB_TOTAL_MENUS.intValue(), found.size());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void addMenu_ValidState_ReturnsCreatedMenu() {
		Integer sizeBeforeAdd = menuService.getAll().size();
		
		MenuDTO menuDto = new MenuDTO();
		menuDto.setExpirationDate(LocalDateTime.of(2022, 3, 18, 0, 0));
		menuDto.setActive(true);
		
		MenuDTO createdMenu = menuService.addMenu(menuDto);
		
		assertEquals(sizeBeforeAdd + 1, menuService.getAll().size());
		assertEquals(LocalDateTime.of(2022, 3, 18, 0, 0), createdMenu.getExpirationDate());
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void deleteMenuById_InvalidId_ThrowsException() {
		menuService.deleteMenuById(MenuConstants.INVALID_MENU_ID);
	}
	
	@Test
	public void deleteMenuById_ValidId_ReturnsNothing() {	
		menuService.deleteMenuById(MenuConstants.DB_MENU_ID_2);
		assertTrue(menuService.findById(MenuConstants.DB_MENU_ID_2).getActive() == false);
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
	@Transactional
	@Rollback(true)
	public void addItemToMenu_ReaddItemToMenu_ReturnsNothing() {
		Integer beforeAdd = menuService.getItemsInMenu(MenuConstants.DB_MENU_ID_1).size();
		
		ItemDTO itemDto = new ItemDTO();
		itemDto.setId(ItemConstants.DB_ITEM_ID_2);
		
		menuService.addItemToMenu(itemDto, MenuConstants.DB_MENU_ID_1);
		
		assertEquals(beforeAdd + 1, menuService.getItemsInMenu(MenuConstants.DB_MENU_ID_1).size());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void addItemToMenu_FirstTimeInMenu_ReturnsNothing() {
		Integer beforeAdd = menuService.getItemsInMenu(MenuConstants.DB_MENU_ID_1).size();
		
		ItemDTO itemDto = new ItemDTO();
		itemDto.setId(ItemConstants.DB_ITEM_ID_4);
		
		menuService.addItemToMenu(itemDto, MenuConstants.DB_MENU_ID_1);
		
		assertEquals(beforeAdd + 1, menuService.getItemsInMenu(MenuConstants.DB_MENU_ID_1).size());
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

	@Test
	@Transactional
	@Rollback(true)
	public void removeItemFromMenu_ValidMenuIdAndValidItemId_ReturnsNothing() {
		Integer beforeRemove = menuService.getItemsInMenu(MenuConstants.DB_MENU_ID_1).size();
		menuService.removeItemFromMenu(ItemConstants.DB_ITEM_ID_1, MenuConstants.DB_MENU_ID_1);
		assertEquals(beforeRemove - 1, menuService.getItemsInMenu(MenuConstants.DB_MENU_ID_1).size());
	}
}
