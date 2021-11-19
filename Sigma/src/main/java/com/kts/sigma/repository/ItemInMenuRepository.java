package com.kts.sigma.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kts.sigma.model.Item;
import com.kts.sigma.model.ItemInMenu;

public interface ItemInMenuRepository extends JpaRepository<ItemInMenu, Integer>{

	@Query("select inm from ItemInMenu inm where inm.item.id = ?1 and inm.menu.id = ?2")
	ItemInMenu findByItemIdAndMenuId(Integer itemId, Integer menuId);
	
	@Query("select inm.item from ItemInMenu inm where inm.menu.id = ?1 and inm.active = true")
	ArrayList<Item> getItemsInMenu(Integer menuId);
}
