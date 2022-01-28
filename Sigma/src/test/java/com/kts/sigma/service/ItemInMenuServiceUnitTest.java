package com.kts.sigma.service;

import com.kts.sigma.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
		given(itemInMenuRepositoryMock.findById(ItemInMenuConstants.INVALID_ITEM_ID)).willReturn(Optional.empty());
		itemInMenuService.deleteById(ItemInMenuConstants.INVALID_ITEM_ID);
		verify(itemInMenuRepositoryMock, times(1)).findById(ItemInMenuConstants.INVALID_ITEM_ID);
	}
	
	@Test
	public void deleteItem_ValidId_ReturnsNothing()
	{
		ItemInMenu item = new ItemInMenu(
				ItemInMenuConstants.DB_DELETE_ITEM_ID,
				new BigDecimal(3000),new Item(),new Menu(),true);
		
		given(itemInMenuRepositoryMock.findById(ItemInMenuConstants.DB_DELETE_ITEM_ID)).willReturn(Optional.of(item));
		itemInMenuService.deleteById(ItemInMenuConstants.DB_DELETE_ITEM_ID);
		verify(itemInMenuRepositoryMock, times(1)).findById(ItemInMenuConstants.DB_DELETE_ITEM_ID);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void findById_InvalidId_ReturnsNothing()
	{
		given(itemInMenuRepositoryMock.findById(ItemInMenuConstants.INVALID_ITEM_ID)).willReturn(Optional.empty());
		itemInMenuService.findById(ItemInMenuConstants.INVALID_ITEM_ID);
		verify(itemInMenuRepositoryMock, times(1)).findById(ItemInMenuConstants.INVALID_ITEM_ID);
	}
	
	@Test
	public void findById_ValidId_ReturnsItem()
	{
		ItemInMenu item = new ItemInMenu(
				ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1,
				new BigDecimal(3000),new Item(),new Menu(),true);
		
		given(itemInMenuRepositoryMock.findById(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1)).willReturn(Optional.of(item));
		itemInMenuService.findById(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
		verify(itemInMenuRepositoryMock, times(1)).findById(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
	}

	@Test
	public void getItemsBySearchTerm_ValidId_ReturnsItems(){
		List<ItemInMenu> items = new ArrayList<ItemInMenu>();
		Menu menu = new Menu();
		Item item = new Item();
		item.setName("pizza");
		items.add(new ItemInMenu(
				ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1,
				new BigDecimal(3000),item,menu,true));
		items.add(new ItemInMenu(
				ItemInMenuConstants.DB_ITEM_IN_MENU_ID_2,
				new BigDecimal(3000),new Item(),menu,true));
		items.add(new ItemInMenu(
				ItemInMenuConstants.DB_ITEM_IN_MENU_ID_3,
				new BigDecimal(3000),new Item(),menu,true));
		items.add(new ItemInMenu(
				ItemInMenuConstants.DB_ITEM_IN_MENU_ID_4,
				new BigDecimal(3000),new Item(),menu,true));
		given(itemInMenuRepositoryMock.findAllActiveItemsInMenuBySearchterm(ItemInMenuConstants.VALID_MENU_ID,"pizza")).willReturn((ArrayList<ItemInMenu>) items);

		ArrayList<ItemDTO> result = (ArrayList<ItemDTO>) itemInMenuService.getItemsBySearchTerm(ItemInMenuConstants.VALID_MENU_ID,"pizza");
		verify(itemInMenuRepositoryMock, times(0)).findAllActiveItemsInMenuBySearchterm(ItemInMenuConstants.INVALID_MENU_ID,"pizza");

		assertEquals(ItemInMenuConstants.TOTAL_ITEMS_IN_MENU.intValue(), result.size());

	}

	@Test
	public void getItemsBySearchTerm_InvalidId_ReturnsNothing(){
		List<ItemInMenu> items = new ArrayList<ItemInMenu>();
		given(itemInMenuRepositoryMock.findAllActiveItemsInMenuBySearchterm(ItemInMenuConstants.INVALID_MENU_ID,"pizza")).willReturn((ArrayList<ItemInMenu>) items);
		ArrayList<ItemDTO> result = (ArrayList<ItemDTO>) itemInMenuService.getItemsBySearchTerm(ItemInMenuConstants.INVALID_MENU_ID,"pizza");
		verify(itemInMenuRepositoryMock, times(1)).findAllActiveItemsInMenuBySearchterm(ItemInMenuConstants.INVALID_MENU_ID,"pizza");
		assertEquals(0, result.size());
	}

	@Test
	public void getItemsByFoodType_ValidId_ReturnsItems(){
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
		given(itemInMenuRepositoryMock.findAllActiveItemsInMenuByFoodType(ItemInMenuConstants.VALID_MENU_ID, FoodType.MAIN_COURSE)).willReturn((ArrayList<ItemInMenu>) items);

		ArrayList<ItemDTO> result = (ArrayList<ItemDTO>) itemInMenuService.getItemsByFoodType(ItemInMenuConstants.VALID_MENU_ID,FoodType.MAIN_COURSE);
		verify(itemInMenuRepositoryMock, times(0)).findAllActiveItemsInMenuByFoodType(ItemInMenuConstants.INVALID_MENU_ID,FoodType.MAIN_COURSE);

		assertEquals(ItemInMenuConstants.TOTAL_ITEMS_IN_MENU.intValue(), result.size());

	}

	@Test
	public void getItemsByFoodType_InvalidId_ReturnsNothing(){
		List<ItemInMenu> items = new ArrayList<ItemInMenu>();
		given(itemInMenuRepositoryMock.findAllActiveItemsInMenuByFoodType(ItemInMenuConstants.INVALID_MENU_ID, FoodType.MAIN_COURSE)).willReturn((ArrayList<ItemInMenu>) items);

		ArrayList<ItemDTO> result = (ArrayList<ItemDTO>) itemInMenuService.getItemsByFoodType(ItemInMenuConstants.VALID_MENU_ID,FoodType.MAIN_COURSE);
		verify(itemInMenuRepositoryMock, times(0)).findAllActiveItemsInMenuByFoodType(ItemInMenuConstants.INVALID_MENU_ID,FoodType.MAIN_COURSE);
		assertEquals(0, result.size());
	}

	@Test
	public void getAllInMenu_ValidId_ReturnsItems(){
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
		given(itemInMenuRepositoryMock.getActiveItemsInMenu(ItemInMenuConstants.VALID_MENU_ID)).willReturn((ArrayList<ItemInMenu>) items);

		ArrayList<ItemDTO> result = (ArrayList<ItemDTO>) itemInMenuService.getAllInMenu(ItemInMenuConstants.VALID_MENU_ID);
		verify(itemInMenuRepositoryMock, times(0)).getActiveItemsInMenu(ItemInMenuConstants.INVALID_MENU_ID);

		assertEquals(ItemInMenuConstants.TOTAL_ITEMS_IN_MENU.intValue(), result.size());

	}

	@Test
	public void getAllInMenu_InvalidId_ReturnsNothing(){
		List<ItemInMenu> items = new ArrayList<ItemInMenu>();
		given(itemInMenuRepositoryMock.getActiveItemsInMenu(ItemInMenuConstants.INVALID_MENU_ID)).willReturn((ArrayList<ItemInMenu>) items);

		ArrayList<ItemDTO> result = (ArrayList<ItemDTO>) itemInMenuService.getAllInMenu(ItemInMenuConstants.INVALID_MENU_ID);
		verify(itemInMenuRepositoryMock, times(1)).getActiveItemsInMenu(ItemInMenuConstants.INVALID_MENU_ID);

		assertEquals(0, result.size());

	}
}
