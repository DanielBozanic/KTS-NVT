package com.kts.sigma.e2e.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.kts.sigma.constants.E2EConstants;
import com.kts.sigma.e2e.pages.PeoplePage;

public class PeopleTest {

	private WebDriver driver;
	private PeoplePage peoplePage;
	
	@Before
	public void initalize() {
		System.setProperty("webdriver.chrome.driver",
                E2EConstants.CHROME_DRIVER_PATH);
		driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		peoplePage = new PeoplePage(driver);
	}
	
	@After
    public void shutdownBrowser() {
		driver.quit();
    }
	
	@Test
	public void addEmployeeEmptyInputFields() {
		driver.get(E2EConstants.PEOPLE_URL);
		
		peoplePage.ensureIsDisplayedAddButton();
		peoplePage.getAddButton().click();
		peoplePage.ensureIsDisplayedAddEmployeeForm();
		
		peoplePage.getAddEmployeeNameInputField().click();
		peoplePage.getAddEmployeePaymentInputField().click();
		peoplePage.setJobInputField("COOK");
		
		assertTrue(peoplePage.getAddEmployeeNameRequiredErrorMsg().isDisplayed());
		assertTrue(peoplePage.getAddEmployeePaymentRequiredErrorMsg().isDisplayed());
		
		assertFalse(peoplePage.getAddButtonDialog().isEnabled());
	}
	
	@Test
	public void addEmployeeNegativePayment() {
		driver.get(E2EConstants.PEOPLE_URL);
		
		peoplePage.ensureIsDisplayedAddButton();
		peoplePage.getAddButton().click();
		peoplePage.ensureIsDisplayedAddEmployeeForm();
			
		peoplePage.setAddEmployeeNameInputField("Markuza");
		peoplePage.setAddEmployeePaymentInputField("-100");
		peoplePage.setJobInputField("WAITER");
		
		assertTrue(peoplePage.getAddEmployeePaymentNegativeErrorMsg().isDisplayed());
		
		assertFalse(peoplePage.getAddButtonDialog().isEnabled());
	}
	
	@Test
	public void addEmployeeValidFields() {
		driver.get(E2EConstants.PEOPLE_URL);
		
		int numberOfEmployeesBeforeAdd = peoplePage.getNumberOfPeople();
		
		peoplePage.ensureIsDisplayedAddButton();
		peoplePage.getAddButton().click();
		peoplePage.ensureIsDisplayedAddEmployeeForm();
		
		peoplePage.setAddEmployeeNameInputField("Markuza");
		peoplePage.setAddEmployeePaymentInputField("20000");
		peoplePage.setJobInputField("WAITER");
		
		assertTrue(peoplePage.getAddButtonDialog().isEnabled());
		peoplePage.getAddButtonDialog().click();
		
		peoplePage.ensureIsNotDisplayedAddEmployeeForm();
	
		int numberOfEmployeesAfterAdd = peoplePage.getNumberOfPeople();

		assertEquals(numberOfEmployeesBeforeAdd + 1, numberOfEmployeesAfterAdd);
		assertTrue(peoplePage.isEmployeePresent("Markuza"));
	}
	
	@Test
	public void editEmployeeNegativePayment() {
		driver.get(E2EConstants.PEOPLE_URL);
		
		String xpath = "//tr[last()]/td[last() - 1]/button";
		WebElement editButton = peoplePage.findWebElementByXPath(xpath);
		editButton.click();
		
		peoplePage.ensureIsDisplayedEditEmployeeForm();
		
		peoplePage.setEditEmployeePaymentInputField("-100");
		peoplePage.setEditEmployeeNameInputField("Petruza");
		
		assertTrue(peoplePage.getEditEmployeePaymentNegativeErrorMsg().isDisplayed());
		
		assertFalse(peoplePage.getEditButtonDialog().isEnabled());
	}
	
	@Test
	public void editEmployeeValidFields() {
		driver.get(E2EConstants.PEOPLE_URL);
		
		String xpath = "//tr[last()]/td[last() - 1]/button";
		WebElement editButton = peoplePage.findWebElementByXPath(xpath);
		editButton.click();
		
		peoplePage.ensureIsDisplayedEditEmployeeForm();
		
		peoplePage.setEditEmployeePaymentInputField("20000");
		peoplePage.setEditEmployeeNameInputField("Petruza");

		assertTrue(peoplePage.getEditButtonDialog().isEnabled());
		peoplePage.getEditButtonDialog().click();
		
		peoplePage.ensureIsNotDisplayedEditEmployeeForm();
		
		assertTrue(peoplePage.isEmployeePresent("Petruza"));
	}
	
	@Test
	public void deleteEmployee() {
		driver.get(E2EConstants.PEOPLE_URL);
		
		int numberOfPeopleBeforeDelete = peoplePage.getNumberOfPeople();
		
		String xpath = "//tr[last()]/td[last()]/button";
		WebElement deleteButton = peoplePage.findWebElementByXPath(xpath);
		deleteButton.click();
		
		peoplePage.ensureIsNotDisplayedWebElement(deleteButton);
	
		int numberOfPeopleAfterDelete = peoplePage.getNumberOfPeople();
		assertEquals(numberOfPeopleBeforeDelete - 1, numberOfPeopleAfterDelete);
	}
}
