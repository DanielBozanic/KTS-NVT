package com.kts.sigma.service;

import com.kts.sigma.constants.ItemConstants;
import com.kts.sigma.repository.FoodRepository;
import com.kts.sigma.repository.ItemInOrderRepository;
import com.kts.sigma.repository.ItemRepository;
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
public class ItemServiceUnitTest {

    @Autowired
    private ItemService itemService;
    @MockBean
    private ItemRepository itemRepositoryMock;
    @MockBean
    private FoodRepository foodRepositoryMock;

    @Test
    public void getAll_ValidState_ReturnsAll()
    {
        List<Item> items = new ArrayList<Item>();

        items.add(new Item(
                ItemConstants.DB_ITEM_ID_1,"Food", "Food",
                new BigDecimal(3000)));
        items.add(new Item(
                ItemConstants.DB_ITEM_ID_2,"Food", "Food",
                new BigDecimal(3000)));
        items.add(new Item(
                ItemConstants.DB_ITEM_ID_3,"Food", "Food",
                new BigDecimal(3000)));
        items.add(new Item(
                ItemConstants.DB_ITEM_ID_4,"Food", "Food",
                new BigDecimal(3000)));


        given(itemRepositoryMock.findAll()).willReturn(items);

        ArrayList<ItemDTO> result = (ArrayList<ItemDTO>) itemService.getAll();

        verify(itemRepositoryMock, times(1)).findAll();

        assertEquals(ItemConstants.DB_TOTAL_ITEMS.intValue(), result.size());
    }

    @Test
    public void createNewItem_Valid_ReturnsCreatedItem() {
        ItemDTO item = new ItemDTO(
                1,"Food", "Food",
                new BigDecimal(3000),new BigDecimal(5000),true);

        given(itemRepositoryMock.save(any(Item.class))).willReturn(any(Item.class));
        Item savedItem = itemService.createNewItem(item);
        verify(itemRepositoryMock,times(1)).save(any(Item.class));
    }

    @Test
    public void deleteById_ValidId_ReturnsNothing()
    {
        Item item = new Item(
                ItemConstants.DB_DELETE_ITEM_ID,"","",
                new BigDecimal(3000));
        itemService.deleteById(ItemConstants.DB_DELETE_ITEM_ID);
    }

    @Test(expected = ItemNotFoundException.class)
    public void findById_InvalidId_ReturnsNothing()
    {
        given(itemRepositoryMock.getOne(ItemConstants.INVALID_ITEM_ID)).willReturn(null);
        itemService.findById(ItemConstants.INVALID_ITEM_ID);
        verify(itemRepositoryMock, times(1)).getOne(ItemConstants.INVALID_ITEM_ID);
    }


}
