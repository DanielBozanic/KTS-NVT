package com.kts.sigma.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ZonesPage {

	private WebDriver driver;

	public ZonesPage() {
		
	}
	
	public ZonesPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = "#create-new-zone-btn")
	private WebElement createNewZoneButton;
	
	@FindBy(css = "#add-table-to-selected-zone-btn")
	private WebElement addTableToSelectedZoneButton;
	
	@FindBy(css = "#zone-select")
	private WebElement zoneSelect;
	
	@FindBy(xpath = "//*[@id='tables']/button[@id= '1']")
	private WebElement firstTable;
	
	@FindBy(css = "#create-new-zone-btn-dialog")
	private WebElement createNewZoneButtonDialog;
	
	@FindBy(css = "#name-field")
	private WebElement nameField;
	
	@FindBy(css = "#name-field-required-error-msg")
	private WebElement nameFieldRequiredErrorMsg;
	
	@FindBy(css = "#table-btn-dialog")
	private WebElement tableButtonDialog;
	
	@FindBy(css = "#number-of-chairs")
	private WebElement numberOfChairsField;
	
	@FindBy(css = "#number-of-chairs-field-required-error-msg")
	private WebElement numberOfChairsRequiredErrorMsg;
	
	@FindBy(css = "#number-of-chairs-field-negative-number-error-msg")
	private WebElement numberOfChairsNegativeNumberErrorMsg;
	
	@FindBy(css = "#remove-table-btn")
	private WebElement removeTableButton;
	
	@FindBy(xpath = "//*[@id=\"mat-select-value-1\"]")
	private WebElement currentSelectedZone;
	
	public void ensureIsDisplayedCreateNewZoneForm() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("create-new-zone-btn-dialog")));
    }
	
	public void ensureIsNotDisplayedCreateNewZoneForm() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("create-new-zone-btn-dialog")));
    }
	
	public void ensureIsDisplayedAddTableForm() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("table-btn-dialog")));
    }
	
	public void ensureIsNotDisplayedAddTableForm() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("table-btn-dialog")));
    }
	
	public void ensureIsDisplayedEditTableForm() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("table-btn-dialog")));
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("remove-table-btn")));
    }
	
	public void ensureIsNotDisplayedEditTableForm() {
		new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(By.id("table-btn-dialog")));
		new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(By.id("remove-table-btn")));
    }
	
	public WebElement getCreateNewZoneButton() {
		return createNewZoneButton;
	}

	public void setCreateNewZoneButton(WebElement createNewZoneButton) {
		this.createNewZoneButton = createNewZoneButton;
	}

	public WebElement getAddTableToSelectedZoneButton() {
		return addTableToSelectedZoneButton;
	}

	public void setAddTableToSelectedZoneButton(WebElement addTableToSelectedZoneButton) {
		this.addTableToSelectedZoneButton = addTableToSelectedZoneButton;
	}

	public WebElement getZoneSelect() {
		return zoneSelect;
	}

	public void setZoneSelect(String zoneId) {
		zoneSelect.click();
        driver.findElement(By.id("zone" + zoneId)).click();
	}

	public WebElement getFirstTable() {
		return firstTable;
	}

	public WebElement getCreateNewZoneButtonDialog() {
		return createNewZoneButtonDialog;
	}

	public WebElement getNameField() {
		return nameField;
	}

	public void setNameField(String name) {
		nameField.clear();
		nameField.sendKeys(name);
	}

	public WebElement getNameFieldRequiredErrorMsg() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("name-field-required-error-msg")));
		return nameFieldRequiredErrorMsg;
	}

	public WebElement getTableButtonDialog() {
		return tableButtonDialog;
	}

	public WebElement getNumberOfChairsField() {
		return numberOfChairsField;
	}

	public void setNumberOfChairsField(String numberOfChairs) {
		numberOfChairsField.clear();
		numberOfChairsField.sendKeys(numberOfChairs);
	}

	public WebElement getNumberOfChairsRequiredErrorMsg() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("number-of-chairs-field-required-error-msg")));
		return numberOfChairsRequiredErrorMsg;
	}

	public WebElement getNumberOfChairsNegativeNumberErrorMsg() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("number-of-chairs-field-negative-number-error-msg")));
		return numberOfChairsNegativeNumberErrorMsg;
	}

	public WebElement getRemoveTableButton() {
		return removeTableButton;
	}

	public WebElement getSnackbarMessage() {
        return new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//snack-bar-container/div/div/simple-snack-bar/span")));
	}
	
	public int getNumberOfZones() {
		zoneSelect.click();
		int size = new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id = 'zone-select-panel']/mat-option"))).size();
		driver.findElement(By.id("zone1")).click();
		return size;
	}
	
	public int getNumberOfTables() {
		int size = new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id = 'tables']/button"))).size();
		return size;
	}
	
	public String getNumberOfChairsFirstTable() {
		Actions action = new Actions(driver);
		action.moveToElement(firstTable).perform();
		String tooltip = driver.findElement(By.className("cdk-overlay-container")).getText();
		String[] parts = tooltip.split("\n");
		return parts[1].replaceFirst("^\\s*", "");
	}
	
	public WebElement getLastTableFromZone() {
		return driver.findElement(By.xpath("//*[@id = 'tables']/button[last()]")); 
	}
	
	public WebElement getCurrentSelectedZone() {
		return currentSelectedZone;
	}
}
