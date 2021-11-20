package com.kts.sigma.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kts.sigma.Exception.ItemExistsException;
import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.Utility.Mapper;
import com.kts.sigma.dto.ItemDTO;
import com.kts.sigma.dto.MenuDTO;
import com.kts.sigma.model.Food;
import com.kts.sigma.model.Item;
import com.kts.sigma.model.ItemInMenu;
import com.kts.sigma.model.Menu;
import com.kts.sigma.repository.ItemInMenuRepository;
import com.kts.sigma.repository.ItemRepository;
import com.kts.sigma.repository.MenuRepository;
import com.kts.sigma.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	private MenuRepository menuRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private ItemInMenuRepository itemInMenuRepository;
	
	@Override
	public Iterable<MenuDTO> getAll() {
		List<Menu> menus = menuRepository.findAll();
		ArrayList<MenuDTO> results = new ArrayList<MenuDTO>();
		
		for (Menu menu : menus) {
			MenuDTO dto = Mapper.mapper.map(menu, MenuDTO.class);
			results.add(dto);
		}
		
		return results;
	}
	
	@Override
	public MenuDTO addMenu(MenuDTO item) {
		Menu newMenu = Mapper.mapper.map(item, Menu.class);
		return Mapper.mapper.map(menuRepository.save(newMenu), MenuDTO.class);
	}
	
	@Override
	public void deleteById(Integer id) {
		menuRepository.deleteById(id);
	}
	
	@Override
	public MenuDTO findById(Integer id)
	{
		Menu menu =  menuRepository.findById(id).orElse(null);
		if(menu == null)
		{
			throw new ItemNotFoundException(id, "menu");
		}
		
		MenuDTO result = Mapper.mapper.map(menu, MenuDTO.class);
	    return result;
	}

	@Override
	public void addItemToMenu(ItemDTO itemDto, Integer menuId) {
		Item item = itemRepository.findById(itemDto.getId()).orElse(null);
		if (item == null) {
			throw new ItemNotFoundException(itemDto.getId(), "item");
		}
		
		Menu menu = menuRepository.getActiveMenu(menuId);
		if (menu == null) {
			throw new ItemNotFoundException(menuId, "menu");
		}
		
		ItemInMenu itemInMenu = itemInMenuRepository.findItemInMenuByItemIdAndMenuId(item.getId(), menu.getId());
		if (itemInMenu != null && itemInMenu.isActive()) {
			throw new ItemExistsException("Item " + item.getName() + " is already part of this menu!");
		} else if (itemInMenu != null && !itemInMenu.isActive()) {
			itemInMenu.setActive(true);
			itemInMenu.setSellingPrice(itemDto.getSellingPrice());
			itemInMenuRepository.save(itemInMenu);
		} else {
			ItemInMenu newItemInMenu = new ItemInMenu();
			newItemInMenu.setItem(item);
			newItemInMenu.setMenu(menu);
			newItemInMenu.setSellingPrice(itemDto.getSellingPrice());
			newItemInMenu.setActive(true);
			itemInMenuRepository.save(newItemInMenu);
		}
	}

	@Override
	public ArrayList<ItemDTO> getItemsInMenu(Integer menuId) {
		ArrayList<ItemInMenu> itemsInMenu = itemInMenuRepository.getActiveItemsInMenu(menuId);
		ArrayList<ItemDTO> itemsInMenuDto = new ArrayList<ItemDTO>();
		
		for (ItemInMenu inm : itemsInMenu) {
			ItemDTO dto = Mapper.mapper.map(inm.getItem(), ItemDTO.class);
			
			if (inm.getItem() instanceof Food) {
				dto.setFood(true);
			}
			dto.setSellingPrice(inm.getSellingPrice());
			
			itemsInMenuDto.add(dto);
		}
		return itemsInMenuDto;
	}

	@Override
	public void removeItemFromMenu(Integer itemId, Integer menuId) {
		ItemInMenu itemInMenu = itemInMenuRepository.findActiveItemInMenuByItemIdAndMenuId(itemId, menuId);
		if (itemInMenu == null) {
			throw new ItemNotFoundException(itemId, "itemInMenu");
		}
		itemInMenu.setActive(false);
		itemInMenuRepository.save(itemInMenu);
	}
}
