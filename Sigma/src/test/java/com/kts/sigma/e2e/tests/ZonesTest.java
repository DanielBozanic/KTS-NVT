package com.kts.sigma.e2e.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import com.kts.sigma.constants.E2EConstants;
import com.kts.sigma.e2e.pages.ZonesPage;

public class ZonesTest {

	private WebDriver driver;
	private ZonesPage zonesPage;
	
	@Before
	public void initalize() {
		System.setProperty("webdriver.chrome.driver",
                E2EConstants.CHROME_DRIVER_PATH);
		driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		zonesPage = new ZonesPage(driver);
	}
	
	@After
    public void shutdownBrowser() {
		driver.quit();
    }
	
	@Test
	public void createNewZoneEmptyField() {
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
		driver.get(E2EConstants.ZONES_URL);
		
		int numberOfZonesBeforeAdd = zonesPage.getNumberOfZones();
		
		zonesPage.getCreateNewZoneButton().click();
		zonesPage.ensureIsDisplayedCreateNewZoneForm();
		
		zonesPage.setNameField("2nd floor");
		
		assertTrue(zonesPage.getCreateNewZoneButtonDialog().isEnabled());
		zonesPage.getCreateNewZoneButtonDialog().click();
		
		zonesPage.ensureIsNotDisplayedCreateNewZoneForm();
		
		int numberOfZonesAfterAdd = zonesPage.getNumberOfZones();
		
		assertEquals(numberOfZonesBeforeAdd + 1, numberOfZonesAfterAdd);
	}
	
	@Test
	public void addTableToSelectedZoneEmptyField() {
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
		driver.get(E2EConstants.ZONES_URL);
		
		zonesPage.getAddTableToSelectedZoneButton().click();
		zonesPage.ensureIsDisplayedAddTableForm();
		
		zonesPage.setNumberOfChairsField("-5");
		
		assertTrue(zonesPage.getNumberOfChairsNegativeNumberErrorMsg().isDisplayed());
		assertFalse(zonesPage.getTableButtonDialog().isEnabled());
	}
	
	@Test
	public void addTableToSelectedZoneValidInput() {
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
		driver.get(E2EConstants.ZONES_URL);
		
		Actions act = new Actions(driver);
		act.doubleClick(zonesPage.getFirstTable()).perform();
		zonesPage.ensureIsDisplayedEditTableForm();
		
		zonesPage.setNumberOfChairsField("-5");
		
		assertTrue(zonesPage.getNumberOfChairsNegativeNumberErrorMsg().isDisplayed());
		assertFalse(zonesPage.getTableButtonDialog().isEnabled());
	}
	
	@Test
	public void editTableValidInput() {
		driver.get(E2EConstants.ZONES_URL);
		
		Actions act = new Actions(driver);
		act.doubleClick(zonesPage.getFirstTable()).perform();
		zonesPage.ensureIsDisplayedEditTableForm();
		
		zonesPage.setNumberOfChairsField("5");
		
		assertTrue(zonesPage.getTableButtonDialog().isEnabled());
		zonesPage.getTableButtonDialog().click();
		
		zonesPage.ensureIsNotDisplayedEditTableForm();
		
		String numberOfChairsAfterEdit = zonesPage.getNumberOfChairsFirstTable();
		
		assertEquals("5", numberOfChairsAfterEdit);
	}
	
	@Test
	public void deleteTableFromZone() {
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
	public void dragTableInZone() {
		driver.get(E2EConstants.ZONES_URL);
		
		int initialX = 733;
		int initialY = 258;
		
		zonesPage.dragAndDropFirstTable();
		
		assertNotEquals(initialX, zonesPage.getFirstTable().getLocation().getX());
		assertNotEquals(initialY, zonesPage.getFirstTable().getLocation().getY());
	}
}
