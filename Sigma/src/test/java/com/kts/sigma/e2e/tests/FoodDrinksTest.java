package com.kts.sigma.e2e.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.kts.sigma.constants.E2EConstants;
import com.kts.sigma.e2e.pages.FoodDrinksPage;
import com.kts.sigma.e2e.pages.LoginPage;

public class FoodDrinksTest {

	private WebDriver driver;
	private FoodDrinksPage foodDrinksPage;
	private LoginPage loginPage;
	
	@Before
	public void initalize() {
		System.setProperty("webdriver.chrome.driver",
				OsUtils.isMacOs() ? E2EConstants.CHROME_DRIVER_PATH_MACOS : E2EConstants.CHROME_DRIVER_PATH);
		driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		foodDrinksPage = new FoodDrinksPage(driver);
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
	public void createNewItemEmptyInputFields() {
		login();
		
		driver.get(E2EConstants.FOOD_DRINKS_URL);
		
		foodDrinksPage.getCreateNewItemButton().click();
		foodDrinksPage.ensureIsDisplayedCreateNewItemForm();
		
		foodDrinksPage.getItemNameField().click();
		foodDrinksPage.getBuyingPriceField().click();
		foodDrinksPage.getDescriptionField().click();
		foodDrinksPage.setCategorySelect("SALAD");
		
		assertTrue(foodDrinksPage.getItemNameFieldRequiredErrorMsg().isDisplayed());
		assertTrue(foodDrinksPage.getBuyingPriceFieldRequiredErrorMsg().isDisplayed());
		assertTrue(foodDrinksPage.getDescriptionFieldRequiredErrorMsg().isDisplayed());
		
		assertFalse(foodDrinksPage.getCreateNewItemButtonDialog().isEnabled());
	}
	
	@Test
	public void createNewItemNegativeBuyingPrice() {
		login();
		
		driver.get(E2EConstants.FOOD_DRINKS_URL);
		
		foodDrinksPage.getCreateNewItemButton().click();
		foodDrinksPage.ensureIsDisplayedCreateNewItemForm();
		
		foodDrinksPage.setItemNameField("Sprite");
		foodDrinksPage.setDrinkRadioButton();
		foodDrinksPage.setBuyingPriceField("-200");
		foodDrinksPage.setDescriptionField("asdf");
		
		assertTrue(foodDrinksPage.getBuyingPriceFieldNegativeNumberErrorMsg().isDisplayed());
		
		assertFalse(foodDrinksPage.getCreateNewItemButtonDialog().isEnabled());
	}
	
	@Test
	public void createNewItemValidInputs() {
		login();
		
		driver.get(E2EConstants.FOOD_DRINKS_URL);
		
		int numberOfItemsBeforeAdd = foodDrinksPage.getNumberOfItems();
		
		foodDrinksPage.getCreateNewItemButton().click();
		foodDrinksPage.ensureIsDisplayedCreateNewItemForm();
		
		foodDrinksPage.setItemNameField("Sprite");
		foodDrinksPage.setDrinkRadioButton();
		foodDrinksPage.setBuyingPriceField("200");
		foodDrinksPage.setDescriptionField("asdf");
		
		assertTrue(foodDrinksPage.getCreateNewItemButtonDialog().isEnabled());
		foodDrinksPage.getCreateNewItemButtonDialog().click();
		
		foodDrinksPage.ensureIsNotDisplayedCreateNewItemForm();
	
		int numberOfItemsAfterAdd = foodDrinksPage.getNumberOfItems();

		assertEquals(numberOfItemsBeforeAdd + 1, numberOfItemsAfterAdd);
		assertTrue(foodDrinksPage.isItemPresent("Sprite"));
	}
	
	@Test
	public void createNewMenuEmptyInputFields() {
		login();
		
		driver.get(E2EConstants.FOOD_DRINKS_URL);
		
		foodDrinksPage.getCreateNewMenuButton().click();
		foodDrinksPage.ensureIsDisplayedCreateNewMenuForm();
		
		foodDrinksPage.getMenuNameField().click();
		foodDrinksPage.getStartDateField().click();
		foodDrinksPage.getExpirationDateField().click();
		
		assertTrue(foodDrinksPage.getMenuNameFieldRequiredErrorMsg().isDisplayed());
		assertTrue(foodDrinksPage.getStartDateFieldRequiredErrorMsg().isDisplayed());
		
		assertFalse(foodDrinksPage.getCreateNewMenuButtonDialog().isEnabled());
	}
	
	@Test
	public void createNewMenuPastStartDate() {
		login();
		
		driver.get(E2EConstants.FOOD_DRINKS_URL);
		
		foodDrinksPage.getCreateNewMenuButton().click();
		foodDrinksPage.ensureIsDisplayedCreateNewMenuForm();
		
		foodDrinksPage.setMenuNameField("TestMenu");
		foodDrinksPage.getStartDateField().sendKeys("25092021");
		foodDrinksPage.getStartDateField().sendKeys(Keys.TAB);
		foodDrinksPage.getStartDateField().sendKeys("0000");
		foodDrinksPage.getExpirationDateField().click();
		
		assertTrue(foodDrinksPage.getStartDateFieldPastDateErrorMsg().isDisplayed());
		
		assertFalse(foodDrinksPage.getCreateNewMenuButtonDialog().isEnabled());
	}
	
	@Test
	public void createNewMenuExpirationDateBeforeStartDate() {
		login();
		
		driver.get(E2EConstants.FOOD_DRINKS_URL);
		
		foodDrinksPage.getCreateNewMenuButton().click();
		foodDrinksPage.ensureIsDisplayedCreateNewMenuForm();
		
		foodDrinksPage.setMenuNameField("TestMenu");
		foodDrinksPage.getStartDateField().sendKeys("25092022");
		foodDrinksPage.getStartDateField().sendKeys(Keys.TAB);
		foodDrinksPage.getStartDateField().sendKeys("0000");
		foodDrinksPage.getExpirationDateField().sendKeys("25092021");
		foodDrinksPage.getExpirationDateField().sendKeys(Keys.TAB);
		foodDrinksPage.getExpirationDateField().sendKeys("0000");
		
		
		assertTrue(foodDrinksPage.getCreateNewMenuButtonDialog().isEnabled());
		foodDrinksPage.getCreateNewMenuButtonDialog().click();
		
		String responseMsg = foodDrinksPage.getSnackbarMessage().getText();
		assertEquals(E2EConstants.getDateNotValidOrder("2022-09-25", "2021-09-25"), responseMsg);
	}
	
	@Test
	public void createNewMenuValidInputs() {
		login();
		
		driver.get(E2EConstants.FOOD_DRINKS_URL);
		
		int numberOfMenusBeforeAdd = foodDrinksPage.getNumberOfMenus();

		foodDrinksPage.getCreateNewMenuButton().click();
		foodDrinksPage.ensureIsDisplayedCreateNewMenuForm();
		
		foodDrinksPage.setMenuNameField("TestMenu");
		foodDrinksPage.getStartDateField().sendKeys("25092022");
		foodDrinksPage.getStartDateField().sendKeys(Keys.TAB);
		foodDrinksPage.getStartDateField().sendKeys("0000");
		foodDrinksPage.getExpirationDateField().sendKeys("25102022");
		foodDrinksPage.getExpirationDateField().sendKeys(Keys.TAB);
		foodDrinksPage.getExpirationDateField().sendKeys("0000");
		
		assertTrue(foodDrinksPage.getCreateNewMenuButtonDialog().isEnabled());
		foodDrinksPage.getCreateNewMenuButtonDialog().click();
		
		foodDrinksPage.ensureIsNotDisplayedCreateNewMenuForm();
		
		int numberOfMenusAfterAdd = foodDrinksPage.getNumberOfMenus();
		
		assertEquals(numberOfMenusBeforeAdd + 1, numberOfMenusAfterAdd);
	}
	
	@Test
	public void deleteSelectedMenu() {
		login();
		
		driver.get(E2EConstants.FOOD_DRINKS_URL);
		
		int numberOfMenusBeforeDelete = foodDrinksPage.getNumberOfMenus();
		
		foodDrinksPage.selectLastMenu();
		foodDrinksPage.getDeleteSelectedMenuButton().click();
		
		int numberOfMenusAfterDelete = foodDrinksPage.getNumberOfMenus();
		
		assertEquals(numberOfMenusBeforeDelete - 1, numberOfMenusAfterDelete);
	}
	
	@Test
	public void addItemInMenuEmptyField() {
		login();
		
		driver.get(E2EConstants.FOOD_DRINKS_URL);
		
		foodDrinksPage.getSecondCarouselItemButton().click();
		foodDrinksPage.ensureIsDisplayedAddItemInMenuForm();
		
		foodDrinksPage.getSellingPriceField().click();
		foodDrinksPage.getSellingPriceField().sendKeys(Keys.TAB);
		
		assertTrue(foodDrinksPage.getSellingPriceFieldRequiredErrorMsg().isDisplayed());
		
		assertFalse(foodDrinksPage.getAddItemInMenuButtonDialog().isEnabled());
	}
	
	@Test
	public void addItemInMenuNegativeSellingPrice() {
		login();
		
		driver.get(E2EConstants.FOOD_DRINKS_URL);
		
		foodDrinksPage.getSecondCarouselItemButton().click();
		foodDrinksPage.ensureIsDisplayedAddItemInMenuForm();
		
		foodDrinksPage.setSellingPriceField("-500");
		foodDrinksPage.getSellingPriceField().sendKeys(Keys.TAB);
		
		assertTrue(foodDrinksPage.getSellingPriceFieldNegativeNumberErrorMsg().isDisplayed());
		
		assertFalse(foodDrinksPage.getAddItemInMenuButtonDialog().isEnabled());
	}
	
	@Test
	public void addItemInMenuItemExistsInMenu() {
		login();
		
		driver.get(E2EConstants.FOOD_DRINKS_URL);
		
		foodDrinksPage.getFirstCarouselItemButton().click();
		foodDrinksPage.ensureIsDisplayedAddItemInMenuForm();
		
		foodDrinksPage.setSellingPriceField("500");
		
		assertTrue(foodDrinksPage.getAddItemInMenuButtonDialog().isEnabled());
		foodDrinksPage.getAddItemInMenuButtonDialog().click();
		
		String responseMsg = foodDrinksPage.getSnackbarMessage().getText();
		assertEquals(E2EConstants.getItemInMenuExistsError("Coca Cola"), responseMsg);
	}
	
	@Test
	public void addItemInMenuValidInputs() {
		login();
		
		driver.get(E2EConstants.FOOD_DRINKS_URL);
		
		int numberOfItemsInMenuBeforeAdd = foodDrinksPage.getNumberOfItemsInMenu();
		
		foodDrinksPage.getSecondCarouselItemButton().click();
		foodDrinksPage.ensureIsDisplayedAddItemInMenuForm();
		
		foodDrinksPage.setSellingPriceField("500");
		
		assertTrue(foodDrinksPage.getAddItemInMenuButtonDialog().isEnabled());
		foodDrinksPage.getAddItemInMenuButtonDialog().click();
		
		foodDrinksPage.ensureIsNotDisplayedAddItemInMenuForm();
		
		int numberOfItemsInMenuAfterAdd = foodDrinksPage.getNumberOfItemsInMenu();
		
		assertEquals(numberOfItemsInMenuBeforeAdd + 1, numberOfItemsInMenuAfterAdd);
		assertTrue(foodDrinksPage.isItemInMenuPresent("Spaghetti"));
	}
	
	@Test
	public void removeItemFromMenu() {
		login();
		
		driver.get(E2EConstants.FOOD_DRINKS_URL);
		
		int numberOfItemsInMenuBeforeRemove = foodDrinksPage.getNumberOfItemsInMenu();
		
		String xpath = "//tr[last()]/td[last()]/button";
		WebElement removeButton = foodDrinksPage.findWebElementByXPath(xpath);
		removeButton.click();
		
		foodDrinksPage.ensureIsNotDisplayedWebElement(removeButton);
		
		int numberOfItemsInMenuAfterRemove = foodDrinksPage.getNumberOfItemsInMenu();
		assertEquals(numberOfItemsInMenuBeforeRemove - 1, numberOfItemsInMenuAfterRemove);
	}
	
	@Test
	public void filterItems() {
		login();
		
		driver.get(E2EConstants.FOOD_DRINKS_URL);
		
		foodDrinksPage.setCategoryFilterSelect("MAIN_COURSE");
		
		int numberOfItems = foodDrinksPage.getNumberOfItems();
		assertEquals(1, numberOfItems);
		
		assertTrue(foodDrinksPage.isItemPresent("Spaghetti"));
		
		foodDrinksPage.setCategoryFilterSelect("ALL");
	}
	
	@Test
	public void searchItems() {
		login();
		
		driver.get(E2EConstants.FOOD_DRINKS_URL);
		
		foodDrinksPage.setSearchInputField("hett");
		foodDrinksPage.getSearchButton().click();
		
		int numberOfItems = foodDrinksPage.getNumberOfItems();
		assertEquals(1, numberOfItems);
		
		assertTrue(foodDrinksPage.isItemPresent("Spaghetti"));
		
		foodDrinksPage.getResetButton().click();
	}
	
	@Test
	public void changeMenu() {
		login();
		
		driver.get(E2EConstants.FOOD_DRINKS_URL);
		
		foodDrinksPage.setMenuSelect("2");
		
		assertEquals("Temp", foodDrinksPage.getCurrentSelectedMenu().getText());
		assertEquals(1, foodDrinksPage.getNumberOfItemsInMenu());
		
		foodDrinksPage.setMenuSelect("1");
	}
}
