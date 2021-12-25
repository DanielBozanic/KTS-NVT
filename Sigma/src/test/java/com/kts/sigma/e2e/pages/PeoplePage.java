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
	
	@FindBy(css = "#job-field")
	private WebElement jobInputField;
	
	@FindBy(css = "#add-employee-payment-field-required-error-msg")
	private WebElement addEmployeePaymentInputFieldRequiredErrorMsg;
	
	@FindBy(css = "#add-employee-payment-field-negative-number-error-msg")
	private WebElement addEmployeePaymentInputFieldNegativeNumberErrorMsg;
	
	@FindBy(css = "#edit-employee-btn2")
	private WebElement editButton;
	
	@FindBy(css = "#edit-employee-btn-dialog")
	private WebElement editButtonDialog;
	
	@FindBy(css = "#edit-employee-name-field")
	private WebElement editEmployeeNameInputField;

	@FindBy(css = "#edit-employee-payment-field")
	private WebElement editEmployeePaymentInputField;
	
	@FindBy(css = "#edit-employee-payment-field-negative-number-error-msg")
	private WebElement editEmployeePaymentInputFieldNegativeNumberErrorMsg;
	
	@FindBy(css = "#delete-employee-btn3")
	private WebElement deleteButton;
	
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
	
	public void ensureIsDisplayedEditButton() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(editButton));
    }
	
	public WebElement getEditButton() {
		return editButton;
	}
	
	public void ensureIsDisplayedEditEmployeeForm() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(editEmployeeNameInputField));
    }
	
	public WebElement getEditButtonDialog() {
		return editButtonDialog;
	}
	
	public WebElement getEditEmployeeNameInputField() {
		return editEmployeeNameInputField;
	}
	
	public void setEditEmployeeNameInputField(String name) {
		editEmployeeNameInputField.clear();
		editEmployeeNameInputField.sendKeys(name);
	}
	
	public WebElement getEditEmployeePaymentInputField() {
		return editEmployeePaymentInputField;
	}
	
	public void setEditEmployeePaymentInputField(String payment) {
		editEmployeePaymentInputField.clear();
		editEmployeePaymentInputField.sendKeys(payment);
	}
	
	public WebElement getEditEmployeePaymentNegativeErrorMsg() {
		return editEmployeePaymentInputFieldNegativeNumberErrorMsg;
	}
	
	public void ensureIsDisplayedDeleteButton() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(deleteButton));
    }
	
	public WebElement getDeleteButton() {
		return deleteButton;
	}
}
