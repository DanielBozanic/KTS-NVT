package com.kts.sigma.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kts.sigma.model.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer>{

	@Query("select m from Menu m where m.id = ?1 and m.active = true")
	Menu getActiveMenu(Integer menuId);
	
	@Query("select m from Menu m where m.active = true and ((m.startDate <= ?1 and ?1 <= m.expirationDate) or (m.startDate <= ?2 and ?2 <= m.expirationDate))")
	Menu getActiveMenuBetweenDates(LocalDateTime start, LocalDateTime end);
	
	@Query("select m from Menu m where m.active = true and m.expirationDate > ?1")
	List<Menu> getActiveNonExpiredMenus(LocalDateTime now);
}