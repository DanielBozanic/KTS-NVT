package com.kts.sigma.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.kts.sigma.constants.UserContants;
import com.kts.sigma.dto.EmployeeDTO;
import com.kts.sigma.dto.ManagerDTO;
import com.kts.sigma.service.UserService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class UserControllerIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private UserService userService;
	
	@Test
	public void getAllEmployees_ValidState_ReturnsAll() {
		ResponseEntity<EmployeeDTO[]> responseEntity = restTemplate
				.getForEntity("/users/getAllEmployees", EmployeeDTO[].class);

		EmployeeDTO[] employees = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(UserContants.DB_TOTAL_EMPLOYEES.intValue(), employees.length);
		assertEquals(UserContants.DB_EMPLOYEE_ID_1_CODE.intValue(), employees[0].getCode().intValue());
	}
	
	
	@Test
	public void getEmployeesByCurrentPage_ValidState_ReturnsAll() {
		ResponseEntity<EmployeeDTO[]> responseEntity = restTemplate.getForEntity(
				"/users/getEmployeesByCurrentPage?currentPage=0&pageSize=5", EmployeeDTO[].class);

		EmployeeDTO[] employees = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(UserContants.DB_TOTAL_EMPLOYEES.intValue(), employees.length);
		assertEquals(UserContants.DB_EMPLOYEE_ID_1_CODE.intValue(), employees[0].getCode().intValue());
	}
	
	@Test
	public void addNewManager_UsernameExists_ReturnsBadRequest() {
		ManagerDTO managerDto = new ManagerDTO();
		managerDto.setName("Marko");
		managerDto.setUsername(UserContants.DB_MANAGER_USERNAME);
		managerDto.setPassword("12345");
		
		ResponseEntity<String> responseEntity = restTemplate.exchange("/users/addManager", 
				HttpMethod.POST, new HttpEntity<ManagerDTO>(managerDto), String.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
	public void addNewManager_ValidUsername_ReturnsCreatedManager() {
		ManagerDTO managerDto = new ManagerDTO();
		managerDto.setName("Petar");
		managerDto.setUsername(UserContants.NEW_MANAGER_USERNAME);
		managerDto.setPassword("12345");
		
		ResponseEntity<ManagerDTO> responseEntity = restTemplate.postForEntity(
				"/users/addManager", managerDto, ManagerDTO.class);
		
		ManagerDTO manager = responseEntity.getBody();
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(UserContants.NEW_MANAGER_USERNAME, manager.getUsername());
	}
	
	@Test
	public void addNewEmployee_ValidState_ReturnsCreatedEmployee() {
		Integer size = userService.getAllEmployees().size(); 
		
		EmployeeDTO employeeDto = new EmployeeDTO();
		employeeDto.setName("Uros");
		employeeDto.setPaymentBigDecimal(new BigDecimal(29000));
		employeeDto.setType("COOK");
		
		ResponseEntity<EmployeeDTO> responseEntity = restTemplate.postForEntity(
				"/users/addEmployee", employeeDto,
				EmployeeDTO.class);
		
		EmployeeDTO employee = responseEntity.getBody();
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertNotNull(employee);
		assertEquals(UserContants.NEW_EMPLOYEE_ID, employee.getId());

		List<EmployeeDTO> employees = userService.getAllEmployees();
		assertEquals(size + 1, employees.size());
		assertEquals(UserContants.NEW_EMPLOYEE_ID, employees.get(employees.size() - 1).getId());

		userService.deleteEmployee(employee.getId());
	}
	
	@Test
	public void editEmployee_InvalidId_ReturnsNotFound() {
		EmployeeDTO employeeDto = new EmployeeDTO();
		employeeDto.setId(UserContants.INVALID_USER_ID);
		employeeDto.setActive(true);
		
		ResponseEntity<String> responseEntity = restTemplate.exchange("/users/editEmployee", 
				HttpMethod.PUT, new HttpEntity<EmployeeDTO>(employeeDto), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void editEmployee_EditNameAndPayment_ReturnsUpdatedEmployee() {
		EmployeeDTO employeeDto = new EmployeeDTO();
		employeeDto.setId(UserContants.DB_EDIT_EMPLOYEE_ID);
		employeeDto.setName(UserContants.EDIT_EMPLOYEE_NAME);
		employeeDto.setPaymentBigDecimal(UserContants.EDIT_EMPLOYEE_PAYMENT);
		employeeDto.setActive(true);
		
		ResponseEntity<EmployeeDTO> responseEntity = restTemplate.exchange("/users/editEmployee", 
				HttpMethod.PUT, new HttpEntity<EmployeeDTO>(employeeDto), EmployeeDTO.class);
		
		EmployeeDTO employee = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(employee);
		assertEquals(UserContants.DB_EDIT_EMPLOYEE_ID, employee.getId());
		assertEquals(UserContants.EDIT_EMPLOYEE_NAME, employee.getName());
		assertEquals(UserContants.EDIT_EMPLOYEE_PAYMENT, employee.getPaymentBigDecimal());
	}
	
	@Test
	public void editEmployee_EditOnlyName_ReturnsUpdatedEmployee() {
		EmployeeDTO employeeDto = new EmployeeDTO();
		employeeDto.setId(UserContants.DB_EDIT_EMPLOYEE_ID);
		employeeDto.setName(UserContants.EDIT_EMPLOYEE_NAME);
		employeeDto.setPaymentBigDecimal(new BigDecimal(30000));
		employeeDto.setActive(true);
		
		ResponseEntity<EmployeeDTO> responseEntity = restTemplate.exchange("/users/editEmployee", 
				HttpMethod.PUT, new HttpEntity<EmployeeDTO>(employeeDto), EmployeeDTO.class);
		
		EmployeeDTO employee = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(employee);
		assertEquals(UserContants.DB_EDIT_EMPLOYEE_ID, employee.getId());
		assertEquals(UserContants.EDIT_EMPLOYEE_NAME, employee.getName());
		
	}
	
	@Test
	public void deleteEmployee_InvalidId_ReturnsNotFound() {
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"/users/deleteEmployee/" + UserContants.INVALID_USER_ID, HttpMethod.DELETE, 
				new HttpEntity<Object>(null), String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void deleteEmployee_ValidId_ReturnsNothing() {
		EmployeeDTO employeeDto = new EmployeeDTO();
		employeeDto.setName("Uros");
		employeeDto.setPaymentBigDecimal(new BigDecimal(29000));
		employeeDto.setType("COOK");
		
		EmployeeDTO created = userService.addNewEmployee(employeeDto);
		Integer size = userService.getAllEmployees().size();

		ResponseEntity<Void> responseEntity = restTemplate.exchange(
				"/users/deleteEmployee/" + created.getId(), HttpMethod.DELETE, 
				new HttpEntity<Object>(null), Void.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		assertEquals(size - 1, userService.getAllEmployees().size());
	}
	
	@Test
	public void findById_InvalidId_ReturnsNotFound() {
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"/users/" + UserContants.INVALID_USER_ID, HttpMethod.GET, 
				new HttpEntity<Object>(null), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void findById_ValidId_ReturnUser() {
		ResponseEntity<EmployeeDTO> responseEntity = restTemplate.exchange(
				"/users/" + UserContants.DB_EMPLOYEE_ID_2, HttpMethod.GET, 
				new HttpEntity<Object>(null), EmployeeDTO.class);
		
		EmployeeDTO employee = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(employee);
		assertEquals(UserContants.DB_EMPLOYEE_ID_2, employee.getId());
	}
}
