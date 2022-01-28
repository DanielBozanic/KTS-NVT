package com.kts.sigma.service;

import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.constants.ItemInMenuConstants;
import com.kts.sigma.constants.OrderConstants;
import com.kts.sigma.constants.ZoneConstants;
import com.kts.sigma.dto.ItemDTO;
import com.kts.sigma.dto.ItemInOrderDTO;
import com.kts.sigma.dto.OrderDTO;
import com.kts.sigma.dto.ZoneDTO;
import com.kts.sigma.model.*;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ItemInMenuServiceIntegrationTest {

    @Autowired
    ItemInMenuService itemInMenuService;

    @Test
    public void getAll_ValidState_ReturnsAllItemInMenu() {
        List<ItemDTO> found = (List<ItemDTO>) itemInMenuService.getAll();
        assertEquals(ItemInMenuConstants.TOTAL_ITEMS_IN_MENU.intValue(), found.size());
    }

    @Test(expected = ItemNotFoundException.class)
    public void findById_InvalidId_ThrowsException() {
        itemInMenuService.findById(ItemInMenuConstants.INVALID_ITEM_ID);
    }

    @Test
    public void findById_ValidId_ReturnsItem() {
        ItemDTO itemDTO = itemInMenuService.findById(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
        assertEquals(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1, itemDTO.getId());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void save_ValidItemInMenu_ReturnsItemInOrder() {
        ItemInMenu item = new ItemInMenu();
        item.setItem(new Item());
        item.setSellingPrice(new BigDecimal(3000));
        item.setActive(true);
        item.setMenu(new Menu());
        ItemInMenu itemSaved = itemInMenuService.save(item);
        Assertions.assertEquals(itemSaved.getSellingPrice(), item.getSellingPrice());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void deleteById_ValidId_ReturnsNothing() {
        int found = ((List<ItemDTO>) itemInMenuService.getAll()).size();

        itemInMenuService.deleteById(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);

        int found2 = ((List<ItemDTO>) itemInMenuService.getAll()).size();

        Assertions.assertEquals(found - 1, found2);
    }

    @Test(expected = ItemNotFoundException.class)
    public void deleteById_InvalidId_ThrowsException() {
        itemInMenuService.deleteById(ItemInMenuConstants.INVALID_ITEM_ID);
    }

    @Test
    public void getItemsBySearchTerm_ValidState_ReturnsItemsInOrder() {
        ArrayList<ItemDTO> items = itemInMenuService.getItemsBySearchTerm(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1,ItemInMenuConstants.VALID_SEARCH_TERM);
        Assertions.assertEquals(0, items.size());
    }

    @Test
    public void getItemsBySearchTerm_InvalidState_ReturnsNothing() {
        ArrayList<ItemDTO> items = itemInMenuService.getItemsBySearchTerm(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1,ItemInMenuConstants.INVALID_SEARCH_TERM);
        Assertions.assertEquals(0, items.size());
    }

    @Test
    public void getItemsByFoodType_ValidState_ReturnsItemsInOrder() {
        ArrayList<ItemDTO> items = itemInMenuService.getItemsByFoodType(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1, FoodType.SALAD);
        Assertions.assertEquals(1, items.size());
    }

    @Test
    public void getItemsByFoodType_InvalidState_ReturnsNothing() {
        ArrayList<ItemDTO> items = itemInMenuService.getItemsByFoodType(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1,FoodType.MAIN_COURSE);
        Assertions.assertEquals(0, items.size());
    }

    @Test
    public void getItemsByCurrentPage_ValidState_ReturnsItemsInOrder() {
        List<ItemDTO> items = itemInMenuService.getItemsByCurrentPage(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1,ItemInMenuConstants.CURRENT_PAGE, ItemInMenuConstants.PAGE_SIZE);
        Assertions.assertEquals(2, items.size());
    }

    @Test
    public void getItemsByCurrentPage_InvalidState_ReturnsNothing() {
        List<ItemDTO> items = itemInMenuService.getItemsByCurrentPage(ItemInMenuConstants.INVALID_MENU_ID,ItemInMenuConstants.CURRENT_PAGE, ItemInMenuConstants.PAGE_SIZE);
        Assertions.assertEquals(0, items.size());
    }

    @Test
    public void getAllInMenu_ValidState_ReturnsItemsInOrder() {
        List<ItemDTO> items = (List<ItemDTO>) itemInMenuService.getAllInMenu(ItemInMenuConstants.DB_ITEM_IN_MENU_ID_1);
        Assertions.assertEquals(2, items.size());
    }

    @Test
    public void getAllInMenu_InvalidState_ReturnsNothing() {
        List<ItemDTO> items = (List<ItemDTO>) itemInMenuService.getAllInMenu(ItemInMenuConstants.INVALID_MENU_ID);
        Assertions.assertEquals(0, items.size());
    }


}
