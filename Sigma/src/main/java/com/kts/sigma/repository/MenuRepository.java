package com.kts.sigma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kts.sigma.model.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer>{

	@Query("select m from Menu m where m.id = ?1 and m.active = true")
	Menu getActiveMenu(Integer menuId);
}
