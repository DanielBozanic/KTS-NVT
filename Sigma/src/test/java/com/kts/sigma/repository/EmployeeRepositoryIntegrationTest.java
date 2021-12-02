package com.kts.sigma.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.kts.sigma.constants.UserContants;
import com.kts.sigma.model.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class EmployeeRepositoryIntegrationTest {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Test
	public void findMaxCode_EmployeesPresentInDatabase_ReturnMaxCode() {
		Integer maxCode = employeeRepository.findMaxCode();
		assertEquals(UserContants.DB_MAX_CODE, maxCode);
	}
	
	@Test
	public void getActiveEmployeeById_InvalidId_ReturnsNull() {
		Employee employee = employeeRepository.getActiveEmployeeById(UserContants.INVALID_USER_ID);
		assertNull(employee);
	}
	
	@Test
	public void getActiveEmployeeById_InactiveEmployee_ReturnsNull() {
		Employee employee = employeeRepository.getActiveEmployeeById(UserContants.DB_INACTIVE_EMPLOYEE_ID);
		assertNull(employee);
	}
	
	@Test
	public void getActiveEmployeeById_ValidId_ReturnsEmployee() {
		Employee employee = employeeRepository.getActiveEmployeeById(UserContants.DB_EMPLOYEE_ID_1);
		assertEquals(UserContants.DB_EMPLOYEE_ID_1, employee.getId());
	}
	
	@Test
	public void findAllActiveEmployees_ValidState_ReturnsEmployees() {
		List<Employee> employee = employeeRepository.findAllActiveEmployees();
		assertEquals(UserContants.DB_TOTAL_ACTIVE_EMPLOYEES.intValue(), employee.size());
	}
	
	@Test
	public void findAllActiveEmployeesByCurrentPage_ValidState_ReturnEmployees() {
		Pageable page = PageRequest.of(UserContants.CURRENT_PAGE, UserContants.PAGE_SIZE);
		List<Employee> employee = employeeRepository.findAllActiveEmployeesByCurrentPage(page).toList();
		assertEquals(UserContants.DB_TOTAL_ACTIVE_EMPLOYEES_CURRENT_PAGE.intValue(), employee.size());
	}
	
	@Test
	public void getNumberOfActiveEmployeeRecords_ValidState_ReturnsNumberOfActiveEmployees() {
		Integer numberOfActiveEmployees = employeeRepository.getNumberOfActiveEmployeeRecords();
		assertEquals(UserContants.DB_TOTAL_ACTIVE_EMPLOYEES, numberOfActiveEmployees);
	}
}
