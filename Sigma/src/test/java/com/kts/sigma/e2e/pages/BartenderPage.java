package com.kts.sigma.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BartenderPage {
	private WebDriver driver;
	
	public BartenderPage() {
		
	}
	
	public BartenderPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = "#start-order-btn9")
	private WebElement startOrderButton9;
	
	@FindBy(css = "#start-item-btn20")
	private WebElement startItemButton20;
	
	@FindBy(css = "#start-item-btn21")
	private WebElement startItemButton21;
	
	@FindBy(css = "#done-item-btn22")
	private WebElement finishItemButton22;
	
	@FindBy(css = "#done-item-btn23")
	private WebElement finishItemButton23;
	
	@FindBy(css = "#done-item-btn24")
	private WebElement finishItemButton24;
	
	@FindBy(css = "#code-verification-button")
	private WebElement codeVerificationButton;
	
	@FindBy(css = "#code-verification-input")
	private WebElement codeVerificationInput;
	
	
	public WebElement getStartOrderButton9() {
		return startOrderButton9;
	}
	
	public WebElement getStartItemButton20() {
		return startItemButton20;
	}
	
	public WebElement getStartItemButton21() {
		return startItemButton21;
	}
	
	public WebElement getFinishItemButton22() {
		return finishItemButton22;
	}
	
	public WebElement getFinishItemButton23() {
		return finishItemButton23;
	}
	
	public WebElement getFinishItemButton24() {
		return finishItemButton24;
	}
	
	public WebElement getCodeVerificationButton() {
		return codeVerificationButton;
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
	
	public WebElement getSnackbarMessage() {
        return new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//snack-bar-container/div/div/simple-snack-bar/span")));
	}
	
	public void ensureIsDisplayedNewStartingOrder() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("start-order-btn9")));
    }
	
	public void ensureIsDisplayedstartItemButton20() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("start-item-btn20")));
    }
	public void ensureIsDisplayedstartItemButton21() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("start-item-btn21")));
    }
	public void ensureIsDisplayedFinishedItemButton22() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("done-item-btn22")));
    }
	public void ensureIsDisplayedFinishedItemButton23() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("done-item-btn23")));
    }
	public void ensureIsDisplayedFinishedItemButton24() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("done-item-btn24")));
    }
	public void ensureIsRemovedFinishedOrder() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(By.id("orders-in-progress11")));
    }
}
