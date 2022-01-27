package com.kts.sigma.e2e.tests;

import com.kts.sigma.constants.E2EConstants;
import com.kts.sigma.e2e.pages.LoginPage;
import com.kts.sigma.e2e.pages.PeoplePage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.*;

public class LoginTest {
    private WebDriver driver;
    private LoginPage loginPage;

    @Before
    public void initalize() {
        System.setProperty("webdriver.chrome.driver",
                OsUtils.isMacOs() ? E2EConstants.CHROME_DRIVER_PATH_MACOS : E2EConstants.CHROME_DRIVER_PATH);
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
    }

    @After
    public void shutdownBrowser() {
        driver.quit();
    }

    @Test
    public void loginTestCorrectCredentials() {
        driver.get(E2EConstants.LOGIN_URL);

        loginPage.setUsernameInput("admin");
        loginPage.setPasswordInput("password");
        loginPage.loginSubmitButtonClick();

        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.urlContains("profile"));
        assertEquals(E2EConstants.ADMIN_PROFILE_URL, driver.getCurrentUrl());
    }

    @Test
    public void loginTestIncorrectCredentials() {
        driver.get(E2EConstants.LOGIN_URL);

        loginPage.setUsernameInput("admin");
        loginPage.setPasswordInput("wrongPass");
        loginPage.loginSubmitButtonClick();

        assertEquals(E2EConstants.LOGIN_URL, driver.getCurrentUrl());
    }




}
