package com.kts.sigma.e2e.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.kts.sigma.constants.E2EConstants;
import com.kts.sigma.e2e.pages.WaiterOrderPage;
import com.kts.sigma.e2e.pages.WaiterTablesPage;

public class WaiterTablesTest {
	
	private WebDriver driver;
	private WaiterTablesPage page;
	private WaiterOrderPage orderPage;
	
	@Before
	public void initalize() {
		System.setProperty("webdriver.chrome.driver",
				OsUtils.isMacOs() ? E2EConstants.CHROME_DRIVER_PATH_MACOS : E2EConstants.CHROME_DRIVER_PATH);
		driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		page = new WaiterTablesPage(driver);
		orderPage = new WaiterOrderPage(driver);
	}
	
	@After
    public void shutdownBrowser() {
		driver.quit();
    }
	
	@Test
	public void getTablesForGarden() {
		driver.get(E2EConstants.TABLES_URL);
		
		page.setZoneSelect(2);
		assertEquals(2, page.getNumberOfTables());
	}
	
	@Test
	public void reserveTableValidCode() {
		driver.get(E2EConstants.TABLES_URL);
		
		page.ensureIsDisplayedTables();
		page.getTableButton1().click();
		page.ensureIsDisplayedFreeTableForm();
		
		page.getReserveButton().click();
		page.ensureIsDisplayedCodeVerificationForm();
		
		page.setCodeVerificationInput("1000");
		page.getCodeVerificationButton().click();
		assertEquals("Successfully reserved table", page.getSnackbarMessage().getText());
	}
	
	@Test
	public void reserveTableInValidCode() {
		driver.get(E2EConstants.TABLES_URL);
		
		page.ensureIsDisplayedTables();
		page.getTableButton5().click();
		page.ensureIsDisplayedFreeTableForm();
		
		page.getReserveButton().click();
		page.ensureIsDisplayedCodeVerificationForm();
		
		page.setCodeVerificationInput("1001");
		page.getCodeVerificationButton().click();
		assertEquals(E2EConstants.INVALID_CODE_ERROR, page.getSnackbarMessage().getText());
	}
	
	@Test
	public void orderValidCode() {
		driver.get(E2EConstants.TABLES_URL);
		
		page.ensureIsDisplayedTables();
		page.getTableButton2().click();
		orderPage.ensureIsDisplayedOrderPage();
		assertEquals(E2EConstants.ORDER_URL, driver.getCurrentUrl());
		
		orderPage.getFirstCarouselItemButton().click();
		orderPage.getFirstCarouselItemButton().click();
		orderPage.getSecondCarouselItemButton().click();
		orderPage.getOrderCreationButton().click();
		
		orderPage.ensureIsDisplayedCodeVerificationForm();
		orderPage.setCodeVerificationInput("1000");
		orderPage.getCodeVerificationButton().click();
		assertEquals("Successfully created order", page.getSnackbarMessage().getText());
	}
	
	@Test
	public void orderInValidCode() {
		driver.get(E2EConstants.TABLES_URL);
		
		page.ensureIsDisplayedTables();
		page.getTableButton5().click();
		page.ensureIsDisplayedFreeTableForm();
		page.getOrderButton().click();
		orderPage.ensureIsDisplayedOrderPage();
		assertEquals(E2EConstants.ORDER_URL, driver.getCurrentUrl());
		
		orderPage.getFirstCarouselItemButton().click();
		orderPage.getFirstCarouselItemButton().click();
		orderPage.getSecondCarouselItemButton().click();
		orderPage.getOrderCreationButton().click();
		
		orderPage.ensureIsDisplayedCodeVerificationForm();
		orderPage.setCodeVerificationInput("1001");
		orderPage.getCodeVerificationButton().click();
		assertEquals(E2EConstants.INVALID_CODE_ERROR, page.getSnackbarMessage().getText());
	}
	
	@Test
	public void orderNoItems() {
		driver.get(E2EConstants.TABLES_URL);
		
		page.ensureIsDisplayedTables();
		page.getTableButton5().click();
		page.ensureIsDisplayedFreeTableForm();
		page.getOrderButton().click();
		orderPage.ensureIsDisplayedOrderPage();
		assertEquals(E2EConstants.ORDER_URL, driver.getCurrentUrl());
		
		orderPage.getOrderCreationButton().click();
		assertEquals("Order has to have at least 1 item", page.getSnackbarMessage().getText());
	}
	
	@Test
	public void chargeValidCode() {
		driver.get(E2EConstants.TABLES_URL);
		
		page.ensureIsDisplayedTables();
		page.getTableButton4().click();
		page.ensureIsDisplayedPaymentForm();
		page.getPayButton().click();
		
		page.ensureIsDisplayedCodeVerificationForm();
		page.setCodeVerificationInput("1000");
		page.getCodeVerificationButton().click();
		assertEquals("Successfully charged order", page.getSnackbarMessage().getText());
	}
	
	@Test
	public void chargeInValidCode() {
		driver.get(E2EConstants.TABLES_URL);
		
		page.ensureIsDisplayedTables();
		page.getTableButton7().click();
		page.ensureIsDisplayedPaymentForm();
		page.getPayButton().click();
		
		page.ensureIsDisplayedCodeVerificationForm();
		page.setCodeVerificationInput("1001");
		page.getCodeVerificationButton().click();
		assertEquals(E2EConstants.INVALID_CODE_ERROR, page.getSnackbarMessage().getText());
	}
	
	@Test
	public void removeOrderValidCode() {
		driver.get(E2EConstants.TABLES_URL);
		
		page.ensureIsDisplayedTables();
		page.getTableButton6().click();
		page.ensureIsDisplayedOrderForm();
		page.getRemoveOrderButton().click();
		
		page.ensureIsDisplayedCodeVerificationForm();
		page.setCodeVerificationInput("1000");
		page.getCodeVerificationButton().click();
		assertEquals("Order removed", page.getSnackbarMessage().getText());
	}
	
	@Test
	public void removeOrderInValidCode() {
		driver.get(E2EConstants.TABLES_URL);
		
		page.ensureIsDisplayedTables();
		page.getTableButton8().click();
		page.ensureIsDisplayedOrderForm();
		page.getRemoveOrderButton().click();
		
		page.ensureIsDisplayedCodeVerificationForm();
		page.setCodeVerificationInput("1001");
		page.getCodeVerificationButton().click();
		assertEquals(E2EConstants.INVALID_CODE_ERROR, page.getSnackbarMessage().getText());
	}
	
	@Test
	public void removeItemValidCode() {
		driver.get(E2EConstants.TABLES_URL);
		
		page.ensureIsDisplayedTables();
		page.getTableButton3().click();
		page.ensureIsDisplayedOrderForm();
		page.getRemoveItem1().click();
		
		page.ensureIsDisplayedCodeVerificationForm();
		page.setCodeVerificationInput("1000");
		page.getCodeVerificationButton().click();
		assertEquals("Coca cola removed from order.", page.getSnackbarMessage().getText());
	}
	
	@Test
	public void removeItemInValidCode() {
		driver.get(E2EConstants.TABLES_URL);
		
		page.ensureIsDisplayedTables();
		page.getTableButton3().click();
		page.ensureIsDisplayedOrderForm();
		page.getRemoveItem2().click();
		
		page.ensureIsDisplayedCodeVerificationForm();
		page.setCodeVerificationInput("1001");
		page.getCodeVerificationButton().click();
		assertEquals(E2EConstants.INVALID_CODE_ERROR, page.getSnackbarMessage().getText());
	}
	
	@Test
	public void deliverItemValidCode() {
		driver.get(E2EConstants.TABLES_URL);
		
		page.ensureIsDisplayedTables();
		page.getTableButton3().click();
		page.ensureIsDisplayedOrderForm();
		page.getDeliverItem6().click();
		
		page.ensureIsDisplayedCodeVerificationForm();
		page.setCodeVerificationInput("1000");
		page.getCodeVerificationButton().click();
		assertEquals("Successfully delivered item", page.getSnackbarMessage().getText());
	}
	
	@Test
	public void deliverItemInValidCode() {
		driver.get(E2EConstants.TABLES_URL);
		
		page.ensureIsDisplayedTables();
		page.getTableButton3().click();
		page.ensureIsDisplayedOrderForm();
		page.getDeliverItem7().click();
		
		page.ensureIsDisplayedCodeVerificationForm();
		page.setCodeVerificationInput("1001");
		page.getCodeVerificationButton().click();
		assertEquals(E2EConstants.INVALID_CODE_ERROR, page.getSnackbarMessage().getText());
	}
	
	@Test
	public void addItemsValidCode() {
		driver.get(E2EConstants.TABLES_URL);
		
		page.ensureIsDisplayedTables();
		page.getTableButton3().click();
		page.ensureIsDisplayedOrderForm();
		page.getAddItemsButton().click();
		orderPage.ensureIsDisplayedOrderPage();
		assertEquals(E2EConstants.ADD_ITEMS_URL, driver.getCurrentUrl());
		
		orderPage.getFirstCarouselItemButton().click();
		orderPage.getFirstCarouselItemButton().click();
		orderPage.getSecondCarouselItemButton().click();
		orderPage.getOrderCreationButton().click();
		
		orderPage.ensureIsDisplayedCodeVerificationForm();
		orderPage.setCodeVerificationInput("1000");
		orderPage.getCodeVerificationButton().click();
		assertEquals("Successfully added items to order", page.getSnackbarMessage().getText());
	}
	
	@Test
	public void addItemsInValidCode() {
		driver.get(E2EConstants.TABLES_URL);
		
		page.ensureIsDisplayedTables();
		page.getTableButton3().click();
		page.ensureIsDisplayedOrderForm();
		page.getAddItemsButton().click();
		orderPage.ensureIsDisplayedOrderPage();
		assertEquals(E2EConstants.ADD_ITEMS_URL, driver.getCurrentUrl());
		
		orderPage.getFirstCarouselItemButton().click();
		orderPage.getFirstCarouselItemButton().click();
		orderPage.getSecondCarouselItemButton().click();
		orderPage.getOrderCreationButton().click();
		
		orderPage.ensureIsDisplayedCodeVerificationForm();
		orderPage.setCodeVerificationInput("1001");
		orderPage.getCodeVerificationButton().click();
		assertEquals(E2EConstants.INVALID_CODE_ERROR, page.getSnackbarMessage().getText());
	}
	
	@Test
	public void addItemsNoItems() {
		driver.get(E2EConstants.TABLES_URL);
		
		page.ensureIsDisplayedTables();
		page.getTableButton3().click();
		page.ensureIsDisplayedOrderForm();
		page.getAddItemsButton().click();
		orderPage.ensureIsDisplayedOrderPage();
		assertEquals(E2EConstants.ADD_ITEMS_URL, driver.getCurrentUrl());
		
		orderPage.getOrderCreationButton().click();
		assertEquals("You could have just clicked cancel...", page.getSnackbarMessage().getText());
	}
}
