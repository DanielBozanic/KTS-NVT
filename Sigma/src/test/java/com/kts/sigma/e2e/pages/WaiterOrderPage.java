package com.kts.sigma.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaiterOrderPage {
	
	private WebDriver driver;
	
	public WaiterOrderPage() {
		
	}
	
	public WaiterOrderPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = "#order-creation-button")
	private WebElement orderCreationButton;
	
	@FindBy(css = "#carousel-item-btn1")
	private WebElement firstCarouselItemButton;
	
	@FindBy(css = "#carousel-item-btn2")
	private WebElement secondCarouselItemButton;
	
	@FindBy(css = "#code-verification-button")
	private WebElement codeVerificationButton;
	
	@FindBy(css = "#code-verification-input")
	private WebElement codeVerificationInput;
	
	public WebElement getOrderCreationButton() {
		return orderCreationButton;
	}
	
	public WebElement getFirstCarouselItemButton() {
		return firstCarouselItemButton;
	}
	
	public WebElement getSecondCarouselItemButton() {
		return secondCarouselItemButton;
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
	
	public void ensureIsDisplayedOrderPage() {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("order-creation-button")));
	}
}
