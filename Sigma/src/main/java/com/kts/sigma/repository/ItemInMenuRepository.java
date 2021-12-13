package com.kts.sigma.repository;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kts.sigma.model.ItemInMenu;

public interface ItemInMenuRepository extends JpaRepository<ItemInMenu, Integer>, PagingAndSortingRepository<ItemInMenu, Integer>{

	@Query("select inm from ItemInMenu inm where inm.item.id = ?1 and inm.menu.id = ?2 and inm.active = true")
	ItemInMenu findActiveItemInMenuByItemIdAndMenuId(Integer itemId, Integer menuId);
	
	@Query("select inm from ItemInMenu inm where inm.item.id = ?1 and inm.menu.id = ?2")
	ItemInMenu findItemInMenuByItemIdAndMenuId(Integer itemId, Integer menuId);
	
	@Query("select inm from ItemInMenu inm where inm.menu.id = ?1 and inm.active = true")
	ArrayList<ItemInMenu> getActiveItemsInMenu(Integer menuId);
	
	@Query("select inm from ItemInMenu inm where inm.menu.id = ?1 and inm.active = true")
	Page<ItemInMenu> findAllActiveItemsInMenuByCurrentPage(Integer menuId, Pageable pageable);
}
