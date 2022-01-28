package com.kts.sigma.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CookPage {
	
	private WebDriver driver;
	
	public CookPage() {
		
	}
	
	public CookPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = "#start-order-btn6")
	private WebElement startOrderButton;
	
	@FindBy(css = "#start-item-btn14")
	private WebElement startItemButton14;
	
	@FindBy(css = "#start-item-btn15")
	private WebElement startItemButton15;
	
	@FindBy(css = "#done-item-btn16")
	private WebElement finishItemButton16;
	
	@FindBy(css = "#done-item-btn17")
	private WebElement finishItemButton17;
	
	@FindBy(css = "#done-item-btn18")
	private WebElement finishItemButton18;
	
	@FindBy(css = "#code-verification-button")
	private WebElement codeVerificationButton;
	
	@FindBy(css = "#code-verification-input")
	private WebElement codeVerificationInput;
	
	
	public WebElement getStartOrderButton6() {
		return startOrderButton;
	}
	
	public WebElement getStartItemButton14() {
		return startItemButton14;
	}
	
	public WebElement getStartItemButton15() {
		return startItemButton15;
	}
	
	public WebElement getFinishItemButton16() {
		return finishItemButton16;
	}
	
	public WebElement getFinishItemButton17() {
		return finishItemButton17;
	}
	
	public WebElement getFinishItemButton18() {
		return finishItemButton18;
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
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("start-order-btn6")));
    }
	
	public void ensureIsDisplayedstartItemButton14() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("start-item-btn14")));
    }
	public void ensureIsDisplayedstartItemButton15() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("start-item-btn15")));
    }
	public void ensureIsDisplayedFinishedItemButton16() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("done-item-btn16")));
    }
	public void ensureIsDisplayedFinishedItemButton17() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("done-item-btn17")));
    }
	public void ensureIsDisplayedFinishedItemButton18() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("done-item-btn18")));
    }
	public void ensureIsRemovedFinishedOrder() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(By.id("orders-in-progress8")));
    }
}
