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
	
	@FindBy(css = "#table-button-3")
	private WebElement tableButton3;
	
	@FindBy(css = "#table-button-4")
	private WebElement tableButton4;
	
	@FindBy(css = "#table-button-5")
	private WebElement tableButton5;
	
	@FindBy(css = "#table-button-6")
	private WebElement tableButton6;
	
	@FindBy(css = "#table-button-7")
	private WebElement tableButton7;
	
	@FindBy(css = "#table-button-8")
	private WebElement tableButton8;
	
	@FindBy(css = "#code-verification-button")
	private WebElement codeVerificationButton;
	
	@FindBy(css = "#reserve-button")
	private WebElement reserveButton;
	
	@FindBy(css = "#order-button")
	private WebElement orderButton;
	
	@FindBy(css = "#code-verification-input")
	private WebElement codeVerificationInput;
	
	@FindBy(css = "#pay-button")
	private WebElement payButton;
	
	@FindBy(css = "#remove-order-button")
	private WebElement removeOrderButton;
	
	@FindBy(css = "#add-items-button")
	private WebElement addItemsButton;
	
	@FindBy(css = "#remove-item-1")
	private WebElement removeItem1;
	
	@FindBy(css = "#deliver-item-6")
	private WebElement deliverItem6;
	
	@FindBy(css = "#remove-item-2")
	private WebElement removeItem2;
	
	@FindBy(css = "#deliver-item-7")
	private WebElement deliverItem7;
	
	public WebElement getZoneSelect() {
		return zoneSelect;
	}
	
	public void setZoneSelect(Integer id) {
		zoneSelect.click();
        driver.findElement(By.id("zone-select-" + id)).click();
	}
	
	public int getNumberOfTables() {
		return new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id='tables']/div/app-waiter-table"))).size();
	}

	public WebElement getTableButton1() {
		return tableButton1;
	}

	public WebElement getTableButton2() {
		return tableButton2;
	}
	
	public WebElement getTableButton3() {
		return tableButton3;
	}
	
	public WebElement getTableButton4() {
		return tableButton4;
	}
	
	public WebElement getTableButton5() {
		return tableButton5;
	}
	
	public WebElement getTableButton6() {
		return tableButton6;
	}
	
	public WebElement getTableButton7() {
		return tableButton7;
	}
	
	public WebElement getTableButton8() {
		return tableButton8;
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
	
	public WebElement getPayButton() {
		return payButton;
	}
	
	public WebElement getRemoveOrderButton() {
		return removeOrderButton;
	}

	public WebElement getAddItemsButton() {
		return addItemsButton;
	}

	public WebElement getRemoveItem1() {
		return removeItem1;
	}

	public WebElement getDeliverItem6() {
		return deliverItem6;
	}
	
	public WebElement getRemoveItem2() {
		return removeItem2;
	}

	public WebElement getDeliverItem7() {
		return deliverItem7;
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
	
	public void ensureIsDisplayedPaymentForm() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("pay-button")));
    }
	
	public void ensureIsDisplayedTables() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("table-button-1")));
    }
	
	public void ensureIsDisplayedOrderForm() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("add-items-button")));
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
