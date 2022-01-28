package com.kts.sigma.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
	
	@FindBy(css = "#edit-employee-btn-dialog")
	private WebElement editButtonDialog;
	
	@FindBy(css = "#edit-employee-name-field")
	private WebElement editEmployeeNameInputField;

	@FindBy(css = "#edit-employee-payment-field")
	private WebElement editEmployeePaymentInputField;
	
	@FindBy(css = "#edit-employee-payment-field-negative-number-error-msg")
	private WebElement editEmployeePaymentInputFieldNegativeNumberErrorMsg;
		
	public PeoplePage() {
		
	}
	
	public PeoplePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void ensureIsDisplayedAddButton() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("add-employee-btn")));
    }
	
	public WebElement getAddButton() {
		return addButton; 
	}
	
	public void ensureIsDisplayedAddEmployeeForm() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("add-employee-btn-dialog")));
    }
	
	public void ensureIsNotDisplayedAddEmployeeForm() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("add-employee-btn-dialog")));
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
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("add-employee-name-field-error-msg")));
		return addEmployeeNameInputFieldErrorMsg;
	}
	
	public WebElement getAddEmployeePaymentRequiredErrorMsg() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("add-employee-payment-field-required-error-msg")));
		return addEmployeePaymentInputFieldRequiredErrorMsg;
	}
	
	public WebElement getAddEmployeePaymentNegativeErrorMsg() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("add-employee-payment-field-negative-number-error-msg")));
		return addEmployeePaymentInputFieldNegativeNumberErrorMsg;
	}
	
	public void ensureIsDisplayedEditEmployeeForm() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-employee-btn-dialog")));
    }
	
	public void ensureIsNotDisplayedEditEmployeeForm() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(By.id("edit-employee-btn-dialog")));
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
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-employee-payment-field-negative-number-error-msg")));
		return editEmployeePaymentInputFieldNegativeNumberErrorMsg;
	}
	
	public int getNumberOfPeople() {
		return new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//table[@id = 'people-table']/tbody/tr"))).size();
	}
	
	public boolean isEmployeePresent(String name) {
		String xpath = String.format("//td[contains(text(),'%s')]", name);
		try {
			(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			return driver.findElement(By.xpath(xpath)).isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public WebElement findWebElementByXPath(String xpath) {
		return driver.findElement(By.xpath(xpath));
	}
	
	public void ensureIsNotDisplayedWebElement(WebElement webElement) {
		new WebDriverWait(driver, 30).until(ExpectedConditions.invisibilityOf(webElement));
	}
	
	public void ensureIsNotDisplayedWebElementById(String id) {
		new WebDriverWait(driver, 30).until(ExpectedConditions.invisibilityOfElementLocated(By.id(id)));
	}
}
