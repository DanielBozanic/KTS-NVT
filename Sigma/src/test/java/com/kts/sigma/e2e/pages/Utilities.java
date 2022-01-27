package com.kts.sigma.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Utilities {

    public static WebElement visibilityWait(WebDriver driver, WebElement element, int wait) {
        return new WebDriverWait(driver, wait).until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement clickableWait(WebDriver driver, WebElement element, int wait) {
        return new WebDriverWait(driver, wait).until(ExpectedConditions.elementToBeClickable(element));
    }
}
