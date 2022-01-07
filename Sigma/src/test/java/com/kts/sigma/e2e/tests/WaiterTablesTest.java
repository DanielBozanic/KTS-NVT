package com.kts.sigma.e2e.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.kts.sigma.constants.E2EConstants;
import com.kts.sigma.e2e.pages.WaiterTablesPage;

public class WaiterTablesTest {
	private WebDriver driver;
	private WaiterTablesPage page;
	
	@Before
	public void initalize() {
		System.setProperty("webdriver.chrome.driver",
                E2EConstants.CHROME_DRIVER_PATH);
		driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		page = new WaiterTablesPage(driver);
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
		
		page.getTableButton2().click();
		page.ensureIsDisplayedFreeTableForm();
		
		page.getReserveButton().click();
		page.ensureIsDisplayedCodeVerificationForm();
		
		page.setCodeVerificationInput("1001");
		page.getCodeVerificationButton().click();
		assertEquals(E2EConstants.INVALID_CODE_ERROR, page.getSnackbarMessage().getText());
	}
}
