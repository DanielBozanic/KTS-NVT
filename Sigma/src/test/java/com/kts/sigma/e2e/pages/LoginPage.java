package com.kts.sigma.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private WebDriver driver;

    @FindBy(css = "#username-login")
    private WebElement usernameInput;

    @FindBy(css = "#password-login")
    private WebElement passwordInput;

    @FindBy(css = "#button-login")
    private WebElement loginSubmitButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebElement getUsernameInput() {
        return Utilities.visibilityWait(driver, this.usernameInput, 10);
    }

    public void setUsernameInput(String value) {
        WebElement el = getUsernameInput();
        el.clear();
        el.sendKeys(value);
    }

    public WebElement getPasswordInput() {
        return Utilities.visibilityWait(driver, this.passwordInput, 10);
    }

    public void setPasswordInput(String value) {
        WebElement el = getPasswordInput();
        el.clear();
        el.sendKeys(value);
    }


    public void loginSubmitButtonClick() {
        Utilities.clickableWait(driver, this.loginSubmitButton, 10).click();
    }
}
