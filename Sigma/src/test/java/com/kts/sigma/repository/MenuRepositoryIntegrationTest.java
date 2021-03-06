package com.kts.sigma.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.kts.sigma.constants.MenuConstants;
import com.kts.sigma.model.Menu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class MenuRepositoryIntegrationTest {

	@Autowired
	private MenuRepository menuRepository;
	
	@Test
	public void getActiveMenu_InvalidId_ReturnsNull() {
		Menu menu = menuRepository.getActiveMenu(MenuConstants.INVALID_MENU_ID);
		assertNull(menu);
	}
	
	@Test
	public void getActiveMenu_InactiveMenu_ReturnsNull() {
		Menu menu = menuRepository.getActiveMenu(MenuConstants.DB_MENU_ID_3);
		assertNull(menu);
	}
	
	@Test
	public void getActive_ValidId_ReturnsActiveMenu() {
		Menu menu = menuRepository.getActiveMenu(MenuConstants.DB_MENU_ID_1);
		
		assertEquals(MenuConstants.DB_MENU_ID_1, menu.getId());
		assertEquals(true, menu.getActive());
	}
	
	@Test
	public void getActiveNonExpiredMenus_ValidState_ReturnsNonExpiredMenus() {
		List<Menu> menus = menuRepository.getActiveNonExpiredMenus(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT));
		assertEquals(MenuConstants.DB_TOTAL_ACTIVE_MENUS_NON_EXPIRED.intValue(), menus.size());
	}
}
