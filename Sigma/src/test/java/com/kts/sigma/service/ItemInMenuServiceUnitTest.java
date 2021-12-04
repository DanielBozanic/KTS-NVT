package com.kts.sigma.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.kts.sigma.repository.ItemInMenuRepository;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.Utility.Mapper;
import com.kts.sigma.constants.ItemInMenuConstants;
import com.kts.sigma.dto.ItemDTO;
import com.kts.sigma.model.Food;
import com.kts.sigma.model.Item;
import com.kts.sigma.model.ItemInMenu;
import com.kts.sigma.model.ItemInOrder;
import com.kts.sigma.model.Menu;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemInMenuServiceUnitTest {

	@Autowired
	private ItemInMenuService itemInMenuService;
	
	@MockBean
	private ItemInMenuRepository itemInMenuRepositoryMock;
	
	@Test
	public void getAll_ValidState_ReturnsAll()
	{
		List<ItemInMenu> items = new ArrayList<ItemInMenu>();
		
		Menu menu = new Menu();
		
		items.add(new ItemInMenu(
				ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1,
				new BigDecimal(3000),new Item(),menu,true));
		items.add(new ItemInMenu(
				ItemInMenuConstants.DB_ITEM_IN_MENU_ID_2,
				new BigDecimal(3000),new Item(),menu,true));
		items.add(new ItemInMenu(
				ItemInMenuConstants.DB_ITEM_IN_MENU_ID_3,
				new BigDecimal(3000),new Item(),menu,true));
		items.add(new ItemInMenu(
				ItemInMenuConstants.DB_ITEM_IN_MENU_ID_4,
				new BigDecimal(3000),new Item(),menu,true));
		
		given(itemInMenuRepositoryMock.findAll()).willReturn(items);
		
		ArrayList<ItemDTO> result = (ArrayList<ItemDTO>) itemInMenuService.getAll();
		
		verify(itemInMenuRepositoryMock, times(1)).findAll();
		
		assertEquals(ItemInMenuConstants.TOTAL_ITEMS_IN_MENU.intValue(), result.size());
	}

	@Test
	public void saveItem_Valid_ReturnsSavedItem() {
		ItemInMenu item = new ItemInMenu(
				ItemInMenuConstants.NEW_ITEM_IN_MENU_ID,
				new BigDecimal(3000),new Item(),new Menu(),true);
		given(itemInMenuRepositoryMock.save(any(ItemInMenu.class))).willReturn(item);
		ItemInMenu savedItem = itemInMenuService.save(item);
		verify(itemInMenuRepositoryMock,times(1)).save(any(ItemInMenu.class));
		assertEquals(item.getId().intValue(), savedItem.getId().intValue());
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void deleteItem_InvalidId_ThrowsException() {
		given(itemInMenuRepositoryMock.getOne(ItemInMenuConstants.INVALID_ITEM_ID)).willReturn(null);
		itemInMenuService.deleteById(ItemInMenuConstants.INVALID_ITEM_ID);
		verify(itemInMenuRepositoryMock, times(1)).getOne(ItemInMenuConstants.INVALID_ITEM_ID);
	}
	
	@Test
	public void deleteItem_ValidId_ReturnsNothing()
	{
		ItemInMenu item = new ItemInMenu(
				ItemInMenuConstants.DB_DELETE_ITEM_ID,
				new BigDecimal(3000),new Item(),new Menu(),true);
		
		given(itemInMenuRepositoryMock.getOne(ItemInMenuConstants.DB_DELETE_ITEM_ID)).willReturn(item);
		itemInMenuService.deleteById(ItemInMenuConstants.DB_DELETE_ITEM_ID);
		verify(itemInMenuRepositoryMock, times(1)).getOne(ItemInMenuConstants.DB_DELETE_ITEM_ID);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void findById_InvalidId_ReturnsNothing()
	{
		given(itemInMenuRepositoryMock.getOne(ItemInMenuConstants.INVALID_ITEM_ID)).willReturn(null);
		itemInMenuService.findById(ItemInMenuConstants.INVALID_ITEM_ID);
		verify(itemInMenuRepositoryMock, times(1)).getOne(ItemInMenuConstants.INVALID_ITEM_ID);
	}
	
	@Test
	public void findById_ValidId_ReturnsItem()
	{
		ItemInMenu item = new ItemInMenu(
				ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1,
				new BigDecimal(3000),new Item(),new Menu(),true);
		
		given(itemInMenuRepositoryMock.getOne(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1)).willReturn(item);
		itemInMenuService.findById(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
		verify(itemInMenuRepositoryMock, times(1)).getOne(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
	}
}
