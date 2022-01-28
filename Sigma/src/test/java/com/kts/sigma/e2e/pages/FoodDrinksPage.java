package com.kts.sigma.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FoodDrinksPage {
	
	private WebDriver driver;

	public FoodDrinksPage() {
		
	}
	
	public FoodDrinksPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = "#category-filter-select")
	private WebElement categoryFilterSelect;
	
	@FindBy(css = "#search-input-field")
	private WebElement searchInputField;
	
	@FindBy(css = "#search-btn")
	private WebElement searchButton;
	
	@FindBy(css = "#reset-btn")
	private WebElement resetButton;
	
	@FindBy(css = "#carousel-item-btn1")
	private WebElement firstCarouselItemButton;
	
	@FindBy(css = "#carousel-item-btn2")
	private WebElement secondCarouselItemButton;
	
	@FindBy(css = "#add-item-in-menu-btn-dialog")
	private WebElement addItemInMenuButtonDialog;
	
	@FindBy(css = "#selling-price-field")
	private WebElement sellingPriceField;
	
	@FindBy(css = "#selling-price-field-required-error-msg")
	private WebElement sellingPriceFieldRequiredErrorMsg;
	
	@FindBy(css = "#selling-price-field-negative-number-error-msg")
	private WebElement sellingPriceFieldNegativeNumberErrorMsg;
	
	@FindBy(css = "#menu-select")
	private WebElement menuSelect;
	
	@FindBy(css = "#create-new-menu-btn")
	private WebElement createNewMenuButton;
	
	@FindBy(css = "#create-new-menu-btn-dialog")
	private WebElement createNewMenuButtonDialog;
	
	@FindBy(css = "#menu-name-field")
	private WebElement menuNameField;
	
	@FindBy(css = "#start-date-field")
	private WebElement startDateField;
	
	@FindBy(css = "#expiration-date-field")
	private WebElement expirationDateField;
	
	@FindBy(css = "#menu-name-field-required-error-msg")
	private WebElement menuNameFieldRequiredErrorMsg;
	
	@FindBy(css = "#start-date-field-required-error-msg")
	private WebElement startDateFieldRequiredErrorMsg;
	
	@FindBy(css = "#start-date-field-past-date-error-msg")
	private WebElement startDateFieldPastDateErrorMsg;
	
	@FindBy(css = "#create-new-item-btn")
	private WebElement createNewItemButton;
	
	@FindBy(css = "#create-new-item-btn-dialog")
	private WebElement createNewItemButtonDialog;
	
	@FindBy(css = "#item-name-field")
	private WebElement itemNameField;
	
	@FindBy(css = "#category-select")
	private WebElement categorySelect;
	
	@FindBy(css = "#buying-price-field")
	private WebElement buyingPriceField;
	
	@FindBy(css = "#description-field")
	private WebElement descriptionField;
	
	@FindBy(css = "#item-name-field-required-error-msg")
	private WebElement itemNameFieldRequiredErrorMsg;
	
	@FindBy(css = "#buying-price-field-required-error-msg")
	private WebElement buyingPriceFieldRequiredErrorMsg;
	
	@FindBy(css = "#buying-price-field-negative-number-error-msg")
	private WebElement buyingPriceFieldNegativeNumberErrorMsg;
	
	@FindBy(css = "#description-field-required-error-msg")
	private WebElement descriptionFieldRequiredErrorMsg;
	
	@FindBy(css = "#delete-selected-menu-btn")
	private WebElement deleteSelectedMenuButton;
	
	@FindBy(xpath = "//*[@id='item-carousel']/div/div/div/button[2]")
	private WebElement rightCarouselNavigationButton;
	
	@FindBy(xpath = "//*[@id=\"mat-select-value-3\"]")
	private WebElement currentSelectedMenu;
	
	public WebElement getCategoryFilterSelect() {
		return categoryFilterSelect;
	}
	
	public void setCategoryFilterSelect(String category) {
		categoryFilterSelect.click();
        driver.findElement(By.id("filter-category-" + category)).click();
	}
	
	public WebElement getSearchInputField() {
		return searchInputField;
	}
	
	public void setSearchInputField(String searchTerm) {
		searchInputField.clear();
		searchInputField.sendKeys(searchTerm);
	}
	
	public WebElement getSearchButton() {
		return searchButton;
	}
	
	public WebElement getResetButton() {
		return resetButton;
	}
	
	public WebElement getFirstCarouselItemButton() {
		return firstCarouselItemButton;
	}
	
	public WebElement getSecondCarouselItemButton() {
		return secondCarouselItemButton;
	}
	
	public WebElement getAddItemInMenuButtonDialog() {
		return addItemInMenuButtonDialog;
	}
	
	public void ensureIsDisplayedAddItemInMenuForm() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("add-item-in-menu-btn-dialog")));
    }
	
	public void ensureIsNotDisplayedAddItemInMenuForm() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("add-item-in-menu-btn-dialog")));
    }
	
	public WebElement getSellingPriceField() {
		return sellingPriceField;
	}
	
	public void setSellingPriceField(String sellingPrice) {
		sellingPriceField.clear();
		sellingPriceField.sendKeys(sellingPrice);
	}
	
	public WebElement getSellingPriceFieldRequiredErrorMsg() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("selling-price-field-required-error-msg")));
		return sellingPriceFieldRequiredErrorMsg;
	}
	
	public WebElement getSellingPriceFieldNegativeNumberErrorMsg() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("selling-price-field-negative-number-error-msg")));
		return sellingPriceFieldNegativeNumberErrorMsg;
	}
	
	public WebElement getMenuSelect() {
		return menuSelect;
	}
	
	public void setMenuSelect(String menuId) {
		menuSelect.click();
        driver.findElement(By.id("menu" + menuId)).click();
	}
	
	public WebElement getCreateNewMenuButton() {
		return createNewMenuButton;
	}
	
	public WebElement getCreateNewMenuButtonDialog() {
		return createNewMenuButtonDialog;
	}
	
	public void ensureIsDisplayedCreateNewMenuForm() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("create-new-menu-btn-dialog")));
    }
	
	public void ensureIsNotDisplayedCreateNewMenuForm() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("create-new-menu-btn-dialog")));
    }
	
	public WebElement getMenuNameField() {
		return menuNameField;
	}
	
	public void setMenuNameField(String menuName) {
		menuNameField.clear();
		menuNameField.sendKeys(menuName);
	}
	
	public WebElement getStartDateField() {
		return startDateField;
	}
	
	public WebElement getExpirationDateField() {
		return expirationDateField;
	}
	
	public WebElement getMenuNameFieldRequiredErrorMsg() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("menu-name-field-required-error-msg")));
		return menuNameFieldRequiredErrorMsg;
	}
	
	public WebElement getStartDateFieldRequiredErrorMsg() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("start-date-field-required-error-msg")));
		return startDateFieldRequiredErrorMsg;
	}
	
	public WebElement getStartDateFieldPastDateErrorMsg() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("start-date-field-past-date-error-msg")));
		return startDateFieldPastDateErrorMsg;
	}
	
	public WebElement getCreateNewItemButton() {
		return createNewItemButton;
	}
	
	public WebElement getCreateNewItemButtonDialog() {
		return createNewItemButtonDialog;
	}
	
	public void ensureIsDisplayedCreateNewItemForm() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("create-new-item-btn-dialog")));
    }
	
	public void ensureIsNotDisplayedCreateNewItemForm() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("create-new-item-btn-dialog")));
    }
	
	public WebElement getItemNameField() {
		return itemNameField;
	}
	
	public void setItemNameField(String itemName) {
		itemNameField.clear();
		itemNameField.sendKeys(itemName);
	}
	
	public void setFoodRadioButton() {
        driver.findElement(By.id("food-radio-btn")).click();
	}
	
	public void setDrinkRadioButton() {
        driver.findElement(By.id("drink-radio-btn")).click();
	}
	
	public WebElement getCategorySelect() {
		return categorySelect;
	}
	
	public void setCategorySelect(String category) {
		categorySelect.click();
        driver.findElement(By.id("category-select-" + category)).click();
	}
	
	public WebElement getBuyingPriceField() {
		return buyingPriceField;
	}
	
	public void setBuyingPriceField(String buyingPrice) {
		buyingPriceField.clear();
		buyingPriceField.sendKeys(buyingPrice);
	}
	
	public WebElement getDescriptionField() {
		return descriptionField;
	}
	
	public void setDescriptionField(String description) {
		descriptionField.clear();
		descriptionField.sendKeys(description);
	}
	
	public WebElement getItemNameFieldRequiredErrorMsg() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("item-name-field-required-error-msg")));
		return itemNameFieldRequiredErrorMsg;
	}
	
	public WebElement getBuyingPriceFieldRequiredErrorMsg() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("buying-price-field-required-error-msg")));
		return buyingPriceFieldRequiredErrorMsg;
	}
	
	public WebElement getBuyingPriceFieldNegativeNumberErrorMsg() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("buying-price-field-negative-number-error-msg")));
		return buyingPriceFieldNegativeNumberErrorMsg;
	}
	
	public WebElement getDescriptionFieldRequiredErrorMsg() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("description-field-required-error-msg")));
		return descriptionFieldRequiredErrorMsg;
	}
	
	public WebElement getDeleteSelectedMenuButton() {
		return deleteSelectedMenuButton;
	}
	
	public WebElement getSnackbarMessage() {
        return new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//snack-bar-container/div/div/simple-snack-bar/span")));
	}
	
	public int getNumberOfItems() {
		return new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id='item-carousel']/div/div/div/div/div/div"))).size();
	}
	
	public int getNumberOfItemsInMenu() {
		return new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//table[@id = 'item-in-menu-table']/tbody/tr"))).size();
	}
	
	public int getNumberOfMenus() {
		menuSelect.click();
		int size = new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id = 'menu-select-panel']/mat-option"))).size();
		driver.findElement(By.id("menu1")).click();
		return size;
	}
	
	public void selectLastMenu() {
		menuSelect.click();
		WebElement lastMenu = driver.findElement(By.xpath("//*[@id = 'menu-select-panel']/mat-option[last()]"));
		lastMenu.click();
	}
	
	public WebElement getCurrentSelectedMenu() {
		return currentSelectedMenu;
	}
	
	public boolean isItemPresent(String name) {
		String xpath = String.format("//*[@id='item-carousel']/div/div/div/div/div/div/mat-card/div/div/h2[contains(text(),'%s')]", name);
		try {
			if (rightCarouselNavigationButton.isEnabled()) {
				rightCarouselNavigationButton.click();
			}
			(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			return driver.findElement(By.xpath(xpath)).isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public boolean isItemInMenuPresent(String name) {
		String xpath = String.format("//td[contains(text(),'%s')]", name);
		try {
			(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			return driver.findElement(By.xpath(xpath)).isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public WebElement findWebElementByXPath(String xpath) {
		return driver.findElement(By.xpath(xpath));
	}
	
	public void ensureIsNotDisplayedWebElement(WebElement webElement) {
		new WebDriverWait(driver, 30).until(ExpectedConditions.invisibilityOf(webElement));
	}
	
	public void ensureIsNotDisplayedWebElementById(String id) {
		new WebDriverWait(driver, 30).until(ExpectedConditions.invisibilityOfElementLocated(By.id(id)));
	}
	
}
