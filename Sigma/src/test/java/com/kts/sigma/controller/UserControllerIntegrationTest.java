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
import com.kts.sigma.model.Employee;
import com.kts.sigma.model.Payment;
import com.kts.sigma.repository.EmployeeRepository;
import com.kts.sigma.repository.ManagerRepository;
import com.kts.sigma.repository.PaymentRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class UserControllerIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ManagerRepository managerRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Test
	public void getAllEmployees_ValidState_ReturnsAll() {
		ResponseEntity<EmployeeDTO[]> responseEntity = restTemplate
				.getForEntity("/users/getAllEmployees", EmployeeDTO[].class);

		EmployeeDTO[] employees = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(UserContants.DB_TOTAL_ACTIVE_EMPLOYEES.intValue(), employees.length);
		assertEquals(UserContants.DB_EMPLOYEE_ID_1_CODE.intValue(), employees[0].getCode().intValue());
	}
	
	
	@Test
	public void getEmployeesByCurrentPage_ValidState_ReturnsAll() {
		ResponseEntity<EmployeeDTO[]> responseEntity = restTemplate.getForEntity(
				"/users/getEmployeesByCurrentPage?currentPage=0&pageSize=5", EmployeeDTO[].class);

		EmployeeDTO[] employees = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(UserContants.DB_TOTAL_ACTIVE_EMPLOYEES_CURRENT_PAGE.intValue(), employees.length);
		assertEquals(UserContants.DB_EMPLOYEE_ID_1_CODE.intValue(), employees[0].getCode().intValue());
	}
	
	@Test
	public void getNumberOfActiveEmployeeRecords_ValidState_ReturnsNumberOfActiveEmployeeRecords() {
		ResponseEntity<Integer> responseEntity = restTemplate.getForEntity(
				"/users/getNumberOfActiveEmployeeRecords", Integer.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(3, responseEntity.getBody().intValue());
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
		
		managerRepository.deleteById(manager.getId());
	}
	
	@Test
	public void addNewEmployee_ValidState_ReturnsCreatedEmployee() {
		Integer size = employeeRepository.findAll().size(); 
		
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
		assertEquals(UserContants.DB_MAX_CODE + 1, employee.getCode().intValue());

		List<Employee> employees = employeeRepository.findAll();
		assertEquals(size + 1, employees.size());
		assertEquals(UserContants.DB_MAX_CODE + 1, employees.get(employees.size() - 1).getCode().intValue());

		employeeRepository.deleteById(employee.getId());
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
		Employee oldEmployee = employeeRepository.findById(UserContants.DB_EDIT_EMPLOYEE_ID).get();
		
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
		
		employeeRepository.save(oldEmployee);
	}
	
	@Test
	public void editEmployee_EditOnlyName_ReturnsUpdatedEmployee() {
		Employee oldEmployee = employeeRepository.findById(UserContants.DB_EDIT_EMPLOYEE_ID).get();
		
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
		
		employeeRepository.save(oldEmployee);
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
		Employee employee = new Employee();
		employee.setName("Uros");
		employee.setActive(true);
		employee = employeeRepository.save(employee);
		
		Payment payment = new Payment();
		payment.setDateCreated(LocalDateTime.now());
		payment.setPayment(new BigDecimal(29000));
		payment.setEmployee(employee);
		payment = paymentRepository.save(payment);
		
		Integer size = employeeRepository.getNumberOfActiveEmployeeRecords();

		ResponseEntity<Void> responseEntity = restTemplate.exchange(
				"/users/deleteEmployee/" + employee.getId(), HttpMethod.DELETE, 
				new HttpEntity<Object>(null), Void.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		assertEquals(size - 1, employeeRepository.getNumberOfActiveEmployeeRecords().intValue());
		
		paymentRepository.deleteById(payment.getId());
		employeeRepository.deleteById(employee.getId());
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
