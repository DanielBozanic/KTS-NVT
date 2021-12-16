package com.kts.sigma.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kts.sigma.model.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer>{

	@Query("select m from Menu m where m.id = ?1 and m.active = true")
	Menu getActiveMenu(Integer menuId);
	
	@Query("select m from Menu m where m.active = true and (m.expirationDate > ?1 or m.expirationDate = null)")
	List<Menu> getActiveNonExpiredMenus(LocalDateTime now);
}