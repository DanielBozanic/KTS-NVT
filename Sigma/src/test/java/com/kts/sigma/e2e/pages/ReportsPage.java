package com.kts.sigma.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ReportsPage {

    private WebDriver driver;

    @FindBy(css = "#mat-date-range-input-0")
    private WebElement startDateInput;

    @FindBy(css = "#end-date-input")
    private WebElement endDateInput;

    @FindBy(css = "#show-report-button")
    private WebElement showReportsButton;

    @FindBy(css = "#enter-dates")
    private WebElement enterDates;

    @FindBy(css = "#chart-div")
    private WebElement chartDivCanvas;

    public ReportsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebElement getStartDateInput() {
        return Utilities.visibilityWait(driver, this.startDateInput, 10);
    }

    public void setStartDateInput(String value) {
        WebElement el = getStartDateInput();
        el.clear();
        el.sendKeys(value);
    }

    public WebElement getEndDateInput() {
        return Utilities.visibilityWait(driver, this.endDateInput, 10);
    }

    public void setEndDateInput(String value) {
        WebElement el = getEndDateInput();
        el.clear();
        el.sendKeys(value);
    }

    public void showReportsButtonClick() {
        Utilities.clickableWait(driver, this.showReportsButton, 10).click();
    }

    public void setEnterDatesClick() {
        Utilities.clickableWait(driver, this.enterDates, 10).click();
    }


    public boolean isReportsChartPresent(){
        try {
            chartDivCanvas.isDisplayed();
            return true;
        }
        catch(Exception ex){
            return false;
        }
    }
}
