package com.kts.sigma.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.kts.sigma.Exception.ItemExistsException;
import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.constants.UserContants;
import com.kts.sigma.dto.EmployeeDTO;
import com.kts.sigma.dto.ManagerDTO;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserServiceIntegrationTest {

	@Autowired
	private UserService userService;
	
	@Test
	public void getAllEmployees_ValidState_ReturnsAll() {
		ArrayList<EmployeeDTO> found = userService.getAllEmployees();
		assertEquals(UserContants.DB_TOTAL_ACTIVE_EMPLOYEES.intValue(), found.size());
	}
	
	@Test
	public void getEmployeesByCurrentPage_ValidState_ReturnsAll() {
		List<EmployeeDTO> found = userService.getEmployeesByCurrentPage(UserContants.CURRENT_PAGE, UserContants.PAGE_SIZE);
		assertEquals(UserContants.DB_TOTAL_ACTIVE_EMPLOYEES_CURRENT_PAGE.intValue(), found.size());
	}
	
	@Test(expected = ItemExistsException.class)
	public void addNewManager_UsernameExists_ThrowsException() {
		ManagerDTO managerDto = new ManagerDTO();
		managerDto.setName("Marko");
		managerDto.setUsername(UserContants.DB_MANAGER_USERNAME);
		managerDto.setPassword("12345");
		userService.addNewManager(managerDto);
	}
	
	@Test
	public void addNewManager_ValidUsername_ReturnsCreatedManager() {
		ManagerDTO managerDto = new ManagerDTO();
		managerDto.setName("Petar");
		managerDto.setUsername(UserContants.NEW_MANAGER_USERNAME);
		managerDto.setPassword("12345");
		
		ManagerDTO created = userService.addNewManager(managerDto);
		assertEquals(UserContants.NEW_MANAGER_USERNAME, created.getUsername());
	}
	
	@Test
	public void addNewEmployee_ValidState_ReturnsCreatedEmployee() {
		EmployeeDTO employeeDto = new EmployeeDTO();
		employeeDto.setName("Uros");
		employeeDto.setPaymentBigDecimal(new BigDecimal(29000));
		employeeDto.setType("COOK");
		
		EmployeeDTO created = userService.addNewEmployee(employeeDto);
		assertEquals(UserContants.DB_MAX_CODE + 2, created.getCode().intValue());
		
		userService.deleteEmployee(created.getId());
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void editEmployee_InvalidId_ThrowsException() {
		EmployeeDTO employeeDto = new EmployeeDTO();
		employeeDto.setId(UserContants.INVALID_USER_ID);
		employeeDto.setActive(true);
		
		userService.editEmployee(employeeDto);
	}
	
	@Test
	public void editEmployee_EditNameAndPayment_ReturnsUpdatedEmployee() {
		EmployeeDTO employeeDto = new EmployeeDTO();
		employeeDto.setId(UserContants.DB_EDIT_EMPLOYEE_ID);
		employeeDto.setName(UserContants.EDIT_EMPLOYEE_NAME);
		employeeDto.setPaymentBigDecimal(UserContants.EDIT_EMPLOYEE_PAYMENT);
		employeeDto.setActive(true);
		
		EmployeeDTO updatedEmployee = userService.editEmployee(employeeDto);
		
		assertEquals(UserContants.DB_EDIT_EMPLOYEE_ID, updatedEmployee.getId());
		assertEquals(UserContants.EDIT_EMPLOYEE_PAYMENT, updatedEmployee.getPaymentBigDecimal());
		assertEquals(UserContants.EDIT_EMPLOYEE_NAME, updatedEmployee.getName());
	}
	
	@Test
	public void editEmployee_EditOnlyName_ReturnsUpdatedEmployee() {
		EmployeeDTO employeeDto = new EmployeeDTO();
		employeeDto.setId(UserContants.DB_EDIT_EMPLOYEE_ID);
		employeeDto.setName(UserContants.EDIT_EMPLOYEE_NAME);
		employeeDto.setPaymentBigDecimal(new BigDecimal(30000));
		employeeDto.setActive(true);
		
		EmployeeDTO updatedEmployee = userService.editEmployee(employeeDto);
		
		assertEquals(UserContants.DB_EDIT_EMPLOYEE_ID, updatedEmployee.getId());
		assertEquals(UserContants.EDIT_EMPLOYEE_NAME, updatedEmployee.getName());
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void deleteEmployee_InvalidId_ThrowsException() {
		userService.deleteEmployee(UserContants.INVALID_USER_ID);
	}
	
	@Test
	public void deleteEmployee_ValidId_ReturnsNothing() {
		EmployeeDTO employeeDto = new EmployeeDTO();
		employeeDto.setName("Uros");
		employeeDto.setPaymentBigDecimal(new BigDecimal(29000));
		employeeDto.setType("COOK");
		
		EmployeeDTO created = userService.addNewEmployee(employeeDto);
		
		userService.deleteEmployee(created.getId());
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void findById_InvalidId_ThrowsException() {
		userService.findById(UserContants.INVALID_USER_ID);
	}
	
	@Test
	public void findById_ValidId_ReturnUser() {
		EmployeeDTO found = userService.findById(UserContants.DB_EMPLOYEE_ID_2);
		assertEquals(UserContants.DB_EMPLOYEE_ID_2, found.getId());
	}
}
