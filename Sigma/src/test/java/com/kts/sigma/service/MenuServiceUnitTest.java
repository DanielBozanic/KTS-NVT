package com.kts.sigma.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.kts.sigma.model.Item;
import com.kts.sigma.model.ItemInMenu;
import com.kts.sigma.model.Menu;
import com.kts.sigma.repository.ItemInMenuRepository;
import com.kts.sigma.repository.ItemRepository;
import com.kts.sigma.repository.MenuRepository;
import com.kts.sigma.Exception.ItemExistsException;
import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.constants.MenuConstants;
import com.kts.sigma.constants.ItemConstants;
import com.kts.sigma.constants.ItemInMenuConstants;
import com.kts.sigma.dto.ItemDTO;
import com.kts.sigma.dto.MenuDTO;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MenuServiceUnitTest {

	@Autowired
	private MenuService menuService;
	
	@MockBean
	private MenuRepository menuRepositoryMock;
	
	@MockBean
	private ItemRepository itemRepositoryMock;
	
	@MockBean
	private ItemInMenuRepository itemInMenuRepositoryMock;
	
	@Test
	public void findAll_ValidState_ReturnsAllMenus() {
		List<Menu> menus = new ArrayList<Menu>();

		Menu menu1 = new Menu();
		menu1.setId(MenuConstants.DB_MENU_ID_1);
		menu1.setExpirationDate(LocalDateTime.of(2022, 10, 7, 0, 0));
		menu1.setActive(true);
		menus.add(menu1);
		
		Menu menu2 = new Menu();
		menu1.setId(MenuConstants.DB_MENU_ID_2);
		menu1.setExpirationDate(LocalDateTime.of(2022, 6, 1, 0, 0));
		menu1.setActive(true);
		menus.add(menu2);
		
		Menu menu3 = new Menu();
		menu1.setId(MenuConstants.DB_MENU_ID_3);
		menu1.setExpirationDate(LocalDateTime.of(2020, 10, 7, 0, 0));
		menu1.setActive(false);
		menus.add(menu3);
		
		Menu menu4 = new Menu();
		menu1.setId(MenuConstants.DB_MENU_ID_4);
		menu1.setExpirationDate(LocalDateTime.of(2022, 2, 5, 0, 0));
		menu1.setActive(true);
		menus.add(menu4);
		
		given(menuRepositoryMock.findAll()).willReturn(menus);
		
		List<MenuDTO> found = menuService.getAll();
		
		verify(menuRepositoryMock, times(1)).findAll();
		
		assertEquals(MenuConstants.DB_TOTAL_MENUS.intValue(), found.size());
	}
	
	@Test
	public void addMenu_ValidState_ReturnsCreatedMenu() {	
		Menu savedMenu = new Menu();
		savedMenu.setId(MenuConstants.NEW_MENU_ID);
		savedMenu.setExpirationDate(LocalDateTime.of(2022, 3, 18, 0, 0));
		savedMenu.setActive(true);
		
		given(menuRepositoryMock.save(any(Menu.class))).willReturn(savedMenu);
		
		MenuDTO menuDto = new MenuDTO();
		menuDto.setExpirationDate(LocalDateTime.of(2022, 3, 18, 0, 0));
		menuDto.setActive(true);
		
		MenuDTO createdMenu = menuService.addMenu(menuDto);
		
		verify(menuRepositoryMock, times(1)).save(any(Menu.class));
		
		assertEquals(MenuConstants.NEW_MENU_ID, createdMenu.getId());
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void deleteMenuById_InvalidId_ThrowsException() {
		given(menuRepositoryMock.getActiveMenu(MenuConstants.INVALID_MENU_ID)).willReturn(null);
		menuService.deleteMenuById(MenuConstants.INVALID_MENU_ID);
		verify(menuRepositoryMock, times(1)).getActiveMenu(MenuConstants.INVALID_MENU_ID);
	}
	
	@Test
	public void deleteMenuById_ValidId_ReturnsNothing() {
		Menu menu = new Menu();
		menu.setId(MenuConstants.DB_MENU_ID_4);
		menu.setActive(true);
		
		Menu savedMenu = new Menu();
		savedMenu.setId(MenuConstants.DB_MENU_ID_4);
		savedMenu.setActive(false);

		given(menuRepositoryMock.getActiveMenu(MenuConstants.DB_MENU_ID_4)).willReturn(menu);
		given(menuRepositoryMock.save(any(Menu.class))).willReturn(savedMenu);
		
		menuService.deleteMenuById(MenuConstants.DB_MENU_ID_4);
		
		verify(menuRepositoryMock, times(1)).getActiveMenu(MenuConstants.DB_MENU_ID_4);
		verify(menuRepositoryMock, times(1)).save(any(Menu.class));
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void findById_InvalidId_ThrowsException() {
		given(menuRepositoryMock.findById(MenuConstants.INVALID_MENU_ID)).willReturn(Optional.ofNullable(null));
		
		menuService.findById(MenuConstants.INVALID_MENU_ID);
		
		verify(menuRepositoryMock, times(1)).findById(MenuConstants.INVALID_MENU_ID);
	}
	
	@Test
	public void findById_ValidId_ReturnsMenu() {
		Menu menu = new Menu();
		menu.setId(MenuConstants.DB_MENU_ID_1);
		
		given(menuRepositoryMock.findById(MenuConstants.DB_MENU_ID_1)).willReturn(Optional.of(menu));
		
		MenuDTO menuDto = menuService.findById(MenuConstants.DB_MENU_ID_1);
		
		verify(menuRepositoryMock, times(1)).findById(MenuConstants.DB_MENU_ID_1);
		
		assertEquals(MenuConstants.DB_MENU_ID_1, menuDto.getId());
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void addItemToMenu_InvalidItemId_ThrowsException() {
		given(itemRepositoryMock.findById(ItemConstants.INVALID_ITEM_ID)).willReturn(Optional.ofNullable(null));
		
		ItemDTO itemDto = new ItemDTO();
		itemDto.setId(ItemConstants.INVALID_ITEM_ID);
		
		menuService.addItemToMenu(itemDto, MenuConstants.DB_MENU_ID_1);
		
		verify(itemRepositoryMock, times(1)).findById(ItemConstants.INVALID_ITEM_ID);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void addItemToMenu_InvalidMenuId_ThrowsException() {
		Item item = new Item();
		item.setId(ItemConstants.DB_ITEM_ID_1);
		
		given(itemRepositoryMock.findById(ItemConstants.DB_ITEM_ID_1)).willReturn(Optional.of(item));
		given(menuRepositoryMock.getActiveMenu(MenuConstants.INVALID_MENU_ID)).willReturn(null);
		
		ItemDTO itemDto = new ItemDTO();
		itemDto.setId(ItemConstants.DB_ITEM_ID_1);
		
		menuService.addItemToMenu(itemDto, MenuConstants.INVALID_MENU_ID);
		
		verify(itemRepositoryMock, times(1)).findById(ItemConstants.DB_ITEM_ID_1);
		verify(menuRepositoryMock, times(1)).getActiveMenu(MenuConstants.INVALID_MENU_ID);
	}
	
	@Test(expected = ItemExistsException.class)
	public void addItemToMenu_ItemExistsInMenu_ThrowsException() {
		Item item = new Item();
		item.setId(ItemConstants.DB_ITEM_ID_1);
		
		Menu menu = new Menu();
		menu.setId(MenuConstants.DB_MENU_ID_1);
		
		ItemInMenu itemInMenu = new ItemInMenu();
		itemInMenu.setActive(true);
		itemInMenu.setItem(item);
		itemInMenu.setMenu(menu);
		
		given(itemRepositoryMock.findById(ItemConstants.DB_ITEM_ID_1)).willReturn(Optional.of(item));
		given(menuRepositoryMock.getActiveMenu(MenuConstants.DB_MENU_ID_1)).willReturn(menu);
		given(itemInMenuRepositoryMock.findItemInMenuByItemIdAndMenuId(ItemConstants.DB_ITEM_ID_1, MenuConstants.DB_MENU_ID_1)).willReturn(itemInMenu);
		
		ItemDTO itemDto = new ItemDTO();
		itemDto.setId(ItemConstants.DB_ITEM_ID_1);
		
		menuService.addItemToMenu(itemDto, MenuConstants.DB_MENU_ID_1);
		
		verify(itemRepositoryMock, times(1)).findById(ItemConstants.DB_ITEM_ID_1);
		verify(menuRepositoryMock, times(1)).getActiveMenu(MenuConstants.DB_MENU_ID_1);
		verify(itemInMenuRepositoryMock, times(1)).findItemInMenuByItemIdAndMenuId(ItemConstants.DB_ITEM_ID_1, MenuConstants.DB_MENU_ID_1);
	}
	
	@Test
	public void addItemToMenu_ReaddItemToMenu_ReturnsNothing() {
		Item item = new Item();
		item.setId(ItemConstants.DB_ITEM_ID_4);
		
		Menu menu = new Menu();
		menu.setId(MenuConstants.DB_MENU_ID_1);
		
		ItemInMenu itemInMenu = new ItemInMenu();
		itemInMenu.setActive(false);
		itemInMenu.setSellingPrice(new BigDecimal(500));
		itemInMenu.setItem(item);
		itemInMenu.setMenu(menu);
			
		ItemInMenu updateItemInMenu = new ItemInMenu();
		updateItemInMenu.setActive(true);
		updateItemInMenu.setSellingPrice(new BigDecimal(600));
		
		given(itemRepositoryMock.findById(ItemConstants.DB_ITEM_ID_4)).willReturn(Optional.of(item));
		given(menuRepositoryMock.getActiveMenu(MenuConstants.DB_MENU_ID_1)).willReturn(menu);
		given(itemInMenuRepositoryMock.findItemInMenuByItemIdAndMenuId(ItemConstants.DB_ITEM_ID_4, MenuConstants.DB_MENU_ID_1)).willReturn(itemInMenu);
		given(itemInMenuRepositoryMock.save(any(ItemInMenu.class))).willReturn(updateItemInMenu);
		
		ItemDTO itemDto = new ItemDTO();
		itemDto.setId(ItemConstants.DB_ITEM_ID_4);
		
		menuService.addItemToMenu(itemDto, MenuConstants.DB_MENU_ID_1);
		
		verify(itemRepositoryMock, times(1)).findById(ItemConstants.DB_ITEM_ID_4);
		verify(menuRepositoryMock, times(1)).getActiveMenu(MenuConstants.DB_MENU_ID_1);
		verify(itemInMenuRepositoryMock, times(1)).findItemInMenuByItemIdAndMenuId(ItemConstants.DB_ITEM_ID_4, MenuConstants.DB_MENU_ID_1);
		verify(itemInMenuRepositoryMock, times(1)).save(any(ItemInMenu.class));
	}
	
	@Test
	public void addItemToMenu_FirstTimeInMenu_ReturnsNothing() {
		Item item = new Item();
		item.setId(ItemConstants.DB_ITEM_ID_4);
		
		Menu menu = new Menu();
		menu.setId(MenuConstants.DB_MENU_ID_1);
			
		ItemInMenu newItemInMenu = new ItemInMenu();
		newItemInMenu.setActive(true);
		newItemInMenu.setSellingPrice(new BigDecimal(500));
		newItemInMenu.setItem(item);
		newItemInMenu.setMenu(menu);
		
		given(itemRepositoryMock.findById(ItemConstants.DB_ITEM_ID_4)).willReturn(Optional.of(item));
		given(menuRepositoryMock.getActiveMenu(MenuConstants.DB_MENU_ID_1)).willReturn(menu);
		given(itemInMenuRepositoryMock.findItemInMenuByItemIdAndMenuId(ItemConstants.DB_ITEM_ID_4, MenuConstants.DB_MENU_ID_1)).willReturn(null);
		given(itemInMenuRepositoryMock.save(any(ItemInMenu.class))).willReturn(newItemInMenu);
		
		ItemDTO itemDto = new ItemDTO();
		itemDto.setId(ItemConstants.DB_ITEM_ID_4);
		
		menuService.addItemToMenu(itemDto, MenuConstants.DB_MENU_ID_1);
		
		verify(itemRepositoryMock, times(1)).findById(ItemConstants.DB_ITEM_ID_4);
		verify(menuRepositoryMock, times(1)).getActiveMenu(MenuConstants.DB_MENU_ID_1);
		verify(itemInMenuRepositoryMock, times(1)).findItemInMenuByItemIdAndMenuId(ItemConstants.DB_ITEM_ID_4, MenuConstants.DB_MENU_ID_1);
		verify(itemInMenuRepositoryMock, times(1)).save(any(ItemInMenu.class));
	}
	
	@Test
	public void getItemsInMenu_ValidMenuId_ReturnsItemsInMenu() {
		ArrayList<ItemInMenu> itemsInMenu = new ArrayList<ItemInMenu>();
		
		Item item1 = new Item();
		item1.setId(ItemConstants.DB_ITEM_ID_1);
		
		Item item3 = new Item();
		item3.setId(ItemConstants.DB_ITEM_ID_3);
		
		Menu menu = new Menu();
		menu.setId(MenuConstants.DB_MENU_ID_1);
		
		ItemInMenu itemInMenu1 = new ItemInMenu();
		itemInMenu1.setId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
		itemInMenu1.setActive(true);
		itemInMenu1.setSellingPrice(new BigDecimal(100));
		itemInMenu1.setItem(item1);
		itemInMenu1.setMenu(menu);
		
		ItemInMenu itemInMenu2 = new ItemInMenu();
		itemInMenu1.setId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_2);
		itemInMenu2.setActive(true);
		itemInMenu2.setSellingPrice(new BigDecimal(150));
		itemInMenu2.setItem(item3);
		itemInMenu2.setMenu(menu);
		
		itemsInMenu.add(itemInMenu1);
		itemsInMenu.add(itemInMenu2);
		
		given(itemInMenuRepositoryMock.getActiveItemsInMenu(MenuConstants.DB_MENU_ID_1)).willReturn(itemsInMenu);
		
		ArrayList<ItemDTO> found = menuService.getItemsInMenu(MenuConstants.DB_MENU_ID_1);
		
		verify(itemInMenuRepositoryMock, times(1)).getActiveItemsInMenu(MenuConstants.DB_MENU_ID_1);
		
		assertEquals(ItemInMenuConstants.TOTAL_ACTIVE_ITEMS_IN_FIRST_MENU.intValue(), found.size());
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void removeItemFromMenu_InvalidMenuIdAndInvalidItemId_ThrowsException() {
		given(itemInMenuRepositoryMock.findActiveItemInMenuByItemIdAndMenuId(ItemConstants.INVALID_ITEM_ID, MenuConstants.INVALID_MENU_ID)).willReturn(null);
		menuService.removeItemFromMenu(ItemConstants.INVALID_ITEM_ID, MenuConstants.INVALID_MENU_ID);
		verify(itemInMenuRepositoryMock, times(1)).findActiveItemInMenuByItemIdAndMenuId(ItemConstants.INVALID_ITEM_ID, MenuConstants.INVALID_MENU_ID);
	}
	
	@Test
	public void removeItemFromMenu_ValidMenuIdAndValidItemId_ReturnsNothing() {
		ItemInMenu savedItemInMenu = new ItemInMenu();
		savedItemInMenu.setId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
		savedItemInMenu.setActive(false);
		
		ItemInMenu itemInMenu = new ItemInMenu();
		itemInMenu.setId(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);

		given(itemInMenuRepositoryMock.findActiveItemInMenuByItemIdAndMenuId(ItemConstants.DB_ITEM_ID_1, MenuConstants.DB_MENU_ID_1)).willReturn(itemInMenu);
		given(itemInMenuRepositoryMock.save(any(ItemInMenu.class))).willReturn(savedItemInMenu);
		
		menuService.removeItemFromMenu(ItemConstants.DB_ITEM_ID_1, MenuConstants.DB_MENU_ID_1);
		
		verify(itemInMenuRepositoryMock, times(1)).findActiveItemInMenuByItemIdAndMenuId(ItemConstants.DB_ITEM_ID_1, MenuConstants.DB_MENU_ID_1);
		verify(itemInMenuRepositoryMock, times(1)).save(any(ItemInMenu.class));
	}
}
