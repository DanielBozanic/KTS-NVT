package com.kts.sigma.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PeoplePage {

	private WebDriver driver;
	
	@FindBy(css = "#add-employee-btn")
	private WebElement addButton;
	
	@FindBy(css = "#add-employee-btn-dialog")
	private WebElement addButtonDialog;
	
	@FindBy(css = "#add-employee-name-field")
	private WebElement addEmployeeNameInputField;
	
	@FindBy(css = "#add-employee-name-field-error-msg")
	private WebElement addEmployeeNameInputFieldErrorMsg;
	
	@FindBy(css = "#add-employee-payment-field")
	private WebElement addEmployeePaymentInputField;
	
	@FindBy(css = "#add-employee-payment-field-required-error-msg")
	private WebElement addEmployeePaymentInputFieldRequiredErrorMsg;
	
	@FindBy(css = "#add-employee-payment-field-negative-number-error-msg")
	private WebElement addEmployeePaymentInputFieldNegativeNumberErrorMsg;
	
	@FindBy(css = "#job-field")
	private WebElement jobInputField;
	
	public PeoplePage() {
		
	}
	
	public PeoplePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void ensureIsDisplayedAddButton() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(addButton));
    }
	
	public WebElement getAddButton() {
		return addButton; 
	}
	
	public void ensureIsDisplayedAddEmployeeForm() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(addEmployeeNameInputField));
    }
	
	public WebElement getAddButtonDialog() {
		return addButtonDialog;
	}
	
	public WebElement getAddEmployeeNameInputField() {
		return addEmployeeNameInputField;
	}
	
	public void setAddEmployeeNameInputField(String name) {
		addEmployeeNameInputField.clear();
		addEmployeeNameInputField.sendKeys(name);
	}
	
	public WebElement getAddEmployeePaymentInputField() {
		return addEmployeePaymentInputField;
	}
	
	public void setAddEmployeePaymentInputField(String payment) {
		addEmployeePaymentInputField.clear();
		addEmployeePaymentInputField.sendKeys(payment);
	}
	
	public WebElement getJobInputField() {
		return jobInputField;
	}
	
	public void setJobInputField(String job) {
		jobInputField.click();
        driver.findElement(By.id(job)).click();
	}
	
	public WebElement getAddEmployeeNameRequiredErrorMsg() {
		return addEmployeeNameInputFieldErrorMsg;
	}
	
	public WebElement getAddEmployeePaymentRequiredErrorMsg() {
		return addEmployeePaymentInputFieldRequiredErrorMsg;
	}
	
	public WebElement getAddEmployeePaymentNegativeErrorMsg() {
		return addEmployeePaymentInputFieldNegativeNumberErrorMsg;
	}
}
