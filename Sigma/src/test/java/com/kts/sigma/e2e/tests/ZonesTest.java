package com.kts.sigma.e2e.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.kts.sigma.constants.E2EConstants;
import com.kts.sigma.e2e.pages.LoginPage;
import com.kts.sigma.e2e.pages.ZonesPage;

public class ZonesTest {

	private WebDriver driver;
	private ZonesPage zonesPage;
	private LoginPage loginPage;
	
	@Before
	public void initalize() {
		System.setProperty("webdriver.chrome.driver",
                E2EConstants.CHROME_DRIVER_PATH);
		driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		zonesPage = new ZonesPage(driver);
		loginPage = new LoginPage(driver);
	}
	
	@After
    public void shutdownBrowser() {
		driver.quit();
    }

	private void login() {
		driver.get(E2EConstants.LOGIN_URL);
		
		loginPage.setUsernameInput("admin");
		loginPage.setPasswordInput("password");
		
		loginPage.loginSubmitButtonClick();
		
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.urlContains("profile"));
	}
	
	@Test
	public void createNewZoneEmptyField() {
		login();
		
		driver.get(E2EConstants.ZONES_URL);
		
		zonesPage.getCreateNewZoneButton().click();
		zonesPage.ensureIsDisplayedCreateNewZoneForm();
		
		zonesPage.getNameField().click();
		zonesPage.getNameField().sendKeys(Keys.TAB);
		
		assertTrue(zonesPage.getNameFieldRequiredErrorMsg().isDisplayed());
		assertFalse(zonesPage.getCreateNewZoneButtonDialog().isEnabled());
	}
	
	@Test
	public void createNewZoneNameExists() {
		login();
		
		driver.get(E2EConstants.ZONES_URL);
		
		zonesPage.getCreateNewZoneButton().click();
		zonesPage.ensureIsDisplayedCreateNewZoneForm();
		
		zonesPage.setNameField("Ground floor");
		
		assertTrue(zonesPage.getCreateNewZoneButtonDialog().isEnabled());
		zonesPage.getCreateNewZoneButtonDialog().click();
		
		String responseMsg = zonesPage.getSnackbarMessage().getText();
		assertEquals(E2EConstants.ZONE_NAME_EXISTS_ERROR, responseMsg);
	}
	
	@Test
	public void createNewZoneValidInput() {
		login();
		
		driver.get(E2EConstants.ZONES_URL);
		
		int numberOfZonesBeforeAdd = zonesPage.getNumberOfZones();
		
		zonesPage.getCreateNewZoneButton().click();
		zonesPage.ensureIsDisplayedCreateNewZoneForm();
		
		zonesPage.setNameField("TEST ZONE" + UUID.randomUUID());
		
		assertTrue(zonesPage.getCreateNewZoneButtonDialog().isEnabled());
		zonesPage.getCreateNewZoneButtonDialog().click();
		
		zonesPage.ensureIsNotDisplayedCreateNewZoneForm();
		
		int numberOfZonesAfterAdd = zonesPage.getNumberOfZones();
		
		assertEquals(numberOfZonesBeforeAdd + 1, numberOfZonesAfterAdd);
	}
	
	@Test
	public void addTableToSelectedZoneEmptyField() {
		login();
		
		driver.get(E2EConstants.ZONES_URL);
		
		zonesPage.getAddTableToSelectedZoneButton().click();
		zonesPage.ensureIsDisplayedAddTableForm();
		
		zonesPage.getNumberOfChairsField().click();
		zonesPage.getNumberOfChairsField().sendKeys(Keys.TAB);
		
		assertTrue(zonesPage.getNumberOfChairsRequiredErrorMsg().isDisplayed());
		assertFalse(zonesPage.getTableButtonDialog().isEnabled());
	}
	
	@Test
	public void addTableToSelectedZoneNegativeNumber() {
		login();
		
		driver.get(E2EConstants.ZONES_URL);
		
		zonesPage.getAddTableToSelectedZoneButton().click();
		zonesPage.ensureIsDisplayedAddTableForm();
		
		zonesPage.setNumberOfChairsField("-5");
		zonesPage.getNumberOfChairsField().sendKeys(Keys.TAB);
		
		assertTrue(zonesPage.getNumberOfChairsNegativeNumberErrorMsg().isDisplayed());
		assertFalse(zonesPage.getTableButtonDialog().isEnabled());
	}
	
	@Test
	public void addTableToSelectedZoneValidInput() {
		login();
		
		driver.get(E2EConstants.ZONES_URL);
		
		int numberOfTablesBeforeAdd = zonesPage.getNumberOfTables();
		
		zonesPage.getAddTableToSelectedZoneButton().click();
		zonesPage.ensureIsDisplayedAddTableForm();
		
		zonesPage.setNumberOfChairsField("5");
		
		assertTrue(zonesPage.getTableButtonDialog().isEnabled());
		zonesPage.getTableButtonDialog().click();
		
		zonesPage.ensureIsNotDisplayedAddTableForm();
		
		int numberOfTablesAfterAdd = zonesPage.getNumberOfTables();
		
		assertEquals(numberOfTablesBeforeAdd + 1, numberOfTablesAfterAdd);
	}
	
	@Test
	public void editTableEmptyInput() {
		login();
		
		driver.get(E2EConstants.ZONES_URL);
		
		Actions act = new Actions(driver);
		act.doubleClick(zonesPage.getFirstTable()).perform();
		
		zonesPage.ensureIsDisplayedEditTableForm();
		
		zonesPage.getNumberOfChairsField().click();
		zonesPage.getNumberOfChairsField().sendKeys(Keys.TAB);
		
		assertTrue(zonesPage.getNumberOfChairsRequiredErrorMsg().isDisplayed());
		assertFalse(zonesPage.getTableButtonDialog().isEnabled());
	}
	
	@Test
	public void editTableNegativeNumber() {
		login();
		
		driver.get(E2EConstants.ZONES_URL);
		
		Actions act = new Actions(driver);
		act.doubleClick(zonesPage.getFirstTable()).perform();
		zonesPage.ensureIsDisplayedEditTableForm();
		
		zonesPage.setNumberOfChairsField("-5");
		zonesPage.getNumberOfChairsField().sendKeys(Keys.TAB);
		
		assertTrue(zonesPage.getNumberOfChairsNegativeNumberErrorMsg().isDisplayed());
		assertFalse(zonesPage.getTableButtonDialog().isEnabled());
	}
	
	@Test
	public void editTableValidInput() {
		login();
		
		driver.get(E2EConstants.ZONES_URL);
		
		Actions act = new Actions(driver);
		act.doubleClick(zonesPage.getFirstTable()).perform();
		zonesPage.ensureIsDisplayedEditTableForm();
		
		zonesPage.setNumberOfChairsField("5");
		
		assertTrue(zonesPage.getTableButtonDialog().isEnabled());
		zonesPage.getTableButtonDialog().click();
		
		zonesPage.ensureIsNotDisplayedEditTableForm();
		
		String numberOfChairsAfterEdit = zonesPage.getNumberOfChairsFirstTable();
		assertEquals("Number of chairs: 5", numberOfChairsAfterEdit);
	}
	
	@Test
	public void deleteTableFromZone() {
		login();
		
		driver.get(E2EConstants.ZONES_URL);
		
		int numberOfTablesBeforeRemove = zonesPage.getNumberOfTables();
		
		Actions act = new Actions(driver);
		act.doubleClick(zonesPage.getLastTableFromZone()).perform();
		zonesPage.ensureIsDisplayedEditTableForm();
		
		assertTrue(zonesPage.getRemoveTableButton().isEnabled());
		zonesPage.getRemoveTableButton().click();
		
		zonesPage.ensureIsNotDisplayedEditTableForm();
		
		int numberOfTablesAfterRemove = zonesPage.getNumberOfTables();
		
		assertEquals(numberOfTablesBeforeRemove - 1, numberOfTablesAfterRemove);
	}
	
	@Test
	public void changeZone() {
		login();
		
		driver.get(E2EConstants.ZONES_URL);
		
		zonesPage.setZoneSelect("2");
		
		assertEquals("Garden", zonesPage.getCurrentSelectedZone().getText());
		assertEquals(2, zonesPage.getNumberOfTables());
		
		zonesPage.setZoneSelect("1");
	}
}
