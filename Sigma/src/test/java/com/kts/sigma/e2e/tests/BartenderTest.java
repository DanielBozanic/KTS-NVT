package com.kts.sigma.e2e.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.kts.sigma.constants.E2EConstants;
import com.kts.sigma.e2e.pages.BartenderPage;

public class BartenderTest {
	private WebDriver driver;
	private BartenderPage page;
	
	@Before
	public void initalize() {
		System.setProperty("webdriver.chrome.driver",
				OsUtils.isMacOs() ? E2EConstants.CHROME_DRIVER_PATH_MACOS : E2EConstants.CHROME_DRIVER_PATH);
		driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		page = new BartenderPage(driver);
	}
	
	@After
    public void shutdownBrowser() {
		driver.quit();
    }

	@Test
	public void SetNewOrderToInProgress() {
		driver.get(E2EConstants.BARTENDER_URL);
		
		page.ensureIsDisplayedNewStartingOrder();
		page.getStartOrderButton9().click();
		
		assertEquals("Order changed to In progress", page.getSnackbarMessage().getText());
	}
	
	@Test
	public void SetItemToInProgressInvalidCode() {
		driver.get(E2EConstants.BARTENDER_URL);
		
		page.ensureIsDisplayedstartItemButton21();
		page.getStartItemButton21().click();
		
		page.ensureIsDisplayedCodeVerificationForm();
		
		page.setCodeVerificationInput("9999");
		page.getCodeVerificationButton().click();
		assertEquals(E2EConstants.INVALID_CODE_ERROR, page.getSnackbarMessage().getText());

	}
	
	@Test
	public void SetItemToInProgressValidCode() {
		driver.get(E2EConstants.BARTENDER_URL);
		
		page.ensureIsDisplayedstartItemButton20();
		page.getStartItemButton20().click();
		
		page.ensureIsDisplayedCodeVerificationForm();
		
		page.setCodeVerificationInput("2345");
		page.getCodeVerificationButton().click();
		assertEquals("Coca Cola for table 15, has changed to In progress.", page.getSnackbarMessage().getText());
	}
	
	@Test
	public void SetItemToDoneInvalidCode() {
		driver.get(E2EConstants.BARTENDER_URL);
		
		page.ensureIsDisplayedFinishedItemButton22();
		page.getFinishItemButton22().click();
		
		page.ensureIsDisplayedCodeVerificationForm();
		
		page.setCodeVerificationInput("9999");
		page.getCodeVerificationButton().click();
		assertEquals(E2EConstants.INVALID_CODE_ERROR, page.getSnackbarMessage().getText());

	}
	
	@Test
	public void SetItemToDoneValidCode() {
		driver.get(E2EConstants.BARTENDER_URL);
		
		page.ensureIsDisplayedFinishedItemButton23();
		page.getFinishItemButton23().click();
		
		page.ensureIsDisplayedCodeVerificationForm();
		
		page.setCodeVerificationInput("2345");
		page.getCodeVerificationButton().click();
		assertEquals("Coca Cola for table 15, has changed to To deliver.", page.getSnackbarMessage().getText());
	}
	
	@Test
	public void SetLastItemOfOrderToDoneValidCode() {
		driver.get(E2EConstants.BARTENDER_URL);
		
		page.ensureIsDisplayedFinishedItemButton24();
		page.getFinishItemButton24().click();
		
		page.ensureIsDisplayedCodeVerificationForm();
		
		page.setCodeVerificationInput("2345");
		page.getCodeVerificationButton().click();
		assertEquals("Coca Cola for table 16, has changed to To deliver.", page.getSnackbarMessage().getText());
		
		page.ensureIsRemovedFinishedOrder();
	}
}
