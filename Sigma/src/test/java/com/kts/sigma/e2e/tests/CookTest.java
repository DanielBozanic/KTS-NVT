package com.kts.sigma.e2e.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.kts.sigma.constants.E2EConstants;
import com.kts.sigma.e2e.pages.CookPage;
import com.kts.sigma.e2e.pages.FoodDrinksPage;

public class CookTest {
	
	private WebDriver driver;
	private CookPage page;
	
	@Before
	public void initalize() {
		System.setProperty("webdriver.chrome.driver",
				OsUtils.isMacOs() ? E2EConstants.CHROME_DRIVER_PATH_MACOS : E2EConstants.CHROME_DRIVER_PATH);
		driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		page = new CookPage(driver);
	}
	
	@After
    public void shutdownBrowser() {
		driver.quit();
    }

	@Test
	public void SetNewOrderToInProgress() {
		driver.get(E2EConstants.COOK_URL);
		
		page.ensureIsDisplayedNewStartingOrder();
		page.getStartOrderButton6().click();
		
		assertEquals("Order changed to In progress", page.getSnackbarMessage().getText());
	}
	
	@Test
	public void SetItemToInProgressInvalidCode() {
		driver.get(E2EConstants.COOK_URL);
		
		page.ensureIsDisplayedstartItemButton15();
		page.getStartItemButton15().click();
		
		page.ensureIsDisplayedCodeVerificationForm();
		
		page.setCodeVerificationInput("9999");
		page.getCodeVerificationButton().click();
		assertEquals(E2EConstants.INVALID_CODE_ERROR, page.getSnackbarMessage().getText());

	}
	
	@Test
	public void SetItemToInProgressValidCode() {
		driver.get(E2EConstants.COOK_URL);
		
		page.ensureIsDisplayedstartItemButton14();
		page.getStartItemButton14().click();
		
		page.ensureIsDisplayedCodeVerificationForm();
		
		page.setCodeVerificationInput("3456");
		page.getCodeVerificationButton().click();
		assertEquals("Crazy appetizer for table 12, has changed to In progress.", page.getSnackbarMessage().getText());
	}
	
	@Test
	public void SetItemToDoneInvalidCode() {
		driver.get(E2EConstants.COOK_URL);
		
		page.ensureIsDisplayedFinishedItemButton16();
		page.getFinishItemButton16().click();
		
		page.ensureIsDisplayedCodeVerificationForm();
		
		page.setCodeVerificationInput("9999");
		page.getCodeVerificationButton().click();
		assertEquals(E2EConstants.INVALID_CODE_ERROR, page.getSnackbarMessage().getText());

	}
	
	@Test
	public void SetItemToDoneValidCode() {
		driver.get(E2EConstants.COOK_URL);
		
		page.ensureIsDisplayedFinishedItemButton17();
		page.getFinishItemButton17().click();
		
		page.ensureIsDisplayedCodeVerificationForm();
		
		page.setCodeVerificationInput("3456");
		page.getCodeVerificationButton().click();
		assertEquals("Crazy appetizer for table 12, has changed to To deliver.", page.getSnackbarMessage().getText());
	}
	
	@Test
	public void SetLastItemOfOrderToDoneValidCode() {
		driver.get(E2EConstants.COOK_URL);
		
		page.ensureIsDisplayedFinishedItemButton18();
		page.getFinishItemButton18().click();
		
		page.ensureIsDisplayedCodeVerificationForm();
		
		page.setCodeVerificationInput("3456");
		page.getCodeVerificationButton().click();
		assertEquals("Crazy appetizer for table 13, has changed to To deliver.", page.getSnackbarMessage().getText());
		
		page.ensureIsRemovedFinishedOrder();
	}
}
