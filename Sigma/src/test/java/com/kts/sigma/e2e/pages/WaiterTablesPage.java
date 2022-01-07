package com.kts.sigma.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaiterTablesPage {

	private WebDriver driver;
	
	public WaiterTablesPage() {
		
	}
	
	public WaiterTablesPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = "#zone-select")
	private WebElement zoneSelect;
	
	@FindBy(css = "#table-button-1")
	private WebElement tableButton1;
	
	@FindBy(css = "#table-button-2")
	private WebElement tableButton2;
	
	@FindBy(css = "#code-verification-button")
	private WebElement codeVerificationButton;
	
	@FindBy(css = "#reserve-button")
	private WebElement reserveButton;
	
	@FindBy(css = "#order-button")
	private WebElement orderButton;
	
	@FindBy(css = "#code-verification-input")
	private WebElement codeVerificationInput;
	
	public WebElement getZoneSelect() {
		return zoneSelect;
	}
	
	public void setZoneSelect(Integer id) {
		zoneSelect.click();
        driver.findElement(By.id("zone-select-" + id)).click();
	}
	
	public int getNumberOfTables() {
		return new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id='tables']/div/button"))).size();
	}

	public WebElement getTableButton1() {
		return tableButton1;
	}

	public WebElement getTableButton2() {
		return tableButton2;
	}

	public WebElement getCodeVerificationButton() {
		return codeVerificationButton;
	}

	public WebElement getReserveButton() {
		return reserveButton;
	}

	public WebElement getOrderButton() {
		return orderButton;
	}

	public WebElement getCodeVerificationInput() {
		return codeVerificationInput;
	}
	
	public void setCodeVerificationInput(String code) {
		codeVerificationInput.clear();
		codeVerificationInput.sendKeys(code);
	}
	
	public void ensureIsDisplayedCodeVerificationForm() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("code-verification-button")));
    }
	
	public void ensureIsDisplayedFreeTableForm() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("reserve-button")));
    }
	
	
	
	public WebElement getSnackbarMessage() {
        return new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//snack-bar-container/div/div/simple-snack-bar/span")));
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
