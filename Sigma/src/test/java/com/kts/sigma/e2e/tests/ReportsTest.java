package com.kts.sigma.e2e.tests;

import com.kts.sigma.constants.E2EConstants;
import com.kts.sigma.e2e.pages.LoginPage;
import com.kts.sigma.e2e.pages.ReportsPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.*;

public class ReportsTest {

    private WebDriver driver;
    private ReportsPage reportsPage;
    private LoginPage loginPage;
    private WebDriverWait wait;

    @Before
    public void initalize() {
        System.setProperty("webdriver.chrome.driver",
                OsUtils.isMacOs() ? E2EConstants.CHROME_DRIVER_PATH_MACOS : E2EConstants.CHROME_DRIVER_PATH);
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        reportsPage = new ReportsPage(driver);
        loginPage = new LoginPage(driver);
        wait = new WebDriverWait(driver, 15);
    }

    @After
    public void shutdownBrowser() {
        driver.quit();
    }


    @Test
    public void reportsPageNoInput() {
        driver.get(E2EConstants.LOGIN_URL);
        loginPage.setUsernameInput("admin");
        loginPage.setPasswordInput("password");
        loginPage.loginSubmitButtonClick();

        wait.until(ExpectedConditions.urlContains("profile"));
        assertEquals(E2EConstants.ADMIN_PROFILE_URL, driver.getCurrentUrl());
        driver.get(E2EConstants.REPORTS_URL);
        wait.until(ExpectedConditions.urlContains("reports"));

        reportsPage.showReportsButtonClick();
        assertFalse(reportsPage.isReportsChartPresent());
    }

    @Test
    public void reportsPageWithInput() throws InterruptedException {
        driver.get(E2EConstants.LOGIN_URL);
        loginPage.setUsernameInput("admin");
        loginPage.setPasswordInput("password");
        loginPage.loginSubmitButtonClick();

        wait.until(ExpectedConditions.urlContains("profile"));
        assertEquals(E2EConstants.ADMIN_PROFILE_URL, driver.getCurrentUrl());
        driver.get(E2EConstants.REPORTS_URL);
        wait.until(ExpectedConditions.urlContains("reports"));

        reportsPage.setEnterDatesClick();
        reportsPage.setStartDateInput("1/1/2021");
        reportsPage.setEndDateInput("1/2/2021");
        reportsPage.showReportsButtonClick();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#chart-div")));
        assertTrue(reportsPage.isReportsChartPresent());
    }
}
