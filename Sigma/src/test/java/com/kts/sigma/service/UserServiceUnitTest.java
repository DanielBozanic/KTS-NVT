package com.kts.sigma.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;

import com.kts.sigma.Exception.ItemExistsException;
import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.constants.UserContants;
import com.kts.sigma.constants.PaymentConstants;
import com.kts.sigma.dto.EmployeeDTO;
import com.kts.sigma.dto.ManagerDTO;
import com.kts.sigma.model.Employee;
import com.kts.sigma.model.Manager;
import com.kts.sigma.model.Payment;
import com.kts.sigma.repository.EmployeeRepository;
import com.kts.sigma.repository.ManagerRepository;
import com.kts.sigma.repository.PaymentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceUnitTest {

	@Autowired
	private UserService userService;
	
	@MockBean
	private EmployeeRepository employeeRepositoryMock;
	
	@MockBean
	private PaymentRepository paymentRepositoryMock;
	
	@MockBean
	private ManagerRepository managerRepositoryMock;
	
	@Test
	public void getAllEmployees_ValidState_ReturnsAll() {
		List<Employee> employees = new ArrayList<>();
		Employee e1 = new Employee(UserContants.DB_EMPLOYEE_ID_1, "Petar Markovic", 
				1000, LocalDateTime.of(2021, 9, 1, 0, 0));
		Employee e2 = new Employee(UserContants.DB_EMPLOYEE_ID_2, "Marko Markovic", 
				1001, LocalDateTime.of(2021, 10, 1, 0, 0));
		Employee e3 = new Employee(UserContants.DB_EMPLOYEE_ID_3, "Sara Markovic", 
				1001, LocalDateTime.of(2021, 11, 1, 0, 0));
		employees.add(e1);
		employees.add(e2);
		employees.add(e3);
		
		Payment payment1 = new Payment(PaymentConstants.DB_PAYMENT_ID_1, new BigDecimal(30000), e1, 
				LocalDateTime.of(2021, 9, 1, 0, 0));
		Payment payment2 = new Payment(PaymentConstants.DB_PAYMENT_ID_2, new BigDecimal(40000), e2, 
				LocalDateTime.of(2021, 10, 1, 0, 0));
		Payment payment3 = new Payment(PaymentConstants.DB_PAYMENT_ID_3, new BigDecimal(40000), e3, 
				LocalDateTime.of(2021, 11, 1, 0, 0));
		
		given(employeeRepositoryMock.findAllActiveEmployees()).willReturn(employees);
		given(paymentRepositoryMock.findActivePaymentByEmployeeId(UserContants.DB_EMPLOYEE_ID_1)).willReturn(payment1);
		given(paymentRepositoryMock.findActivePaymentByEmployeeId(UserContants.DB_EMPLOYEE_ID_2)).willReturn(payment2);
		given(paymentRepositoryMock.findActivePaymentByEmployeeId(UserContants.DB_EMPLOYEE_ID_3)).willReturn(payment3);
		
		ArrayList<EmployeeDTO> found = userService.getAllEmployees();
		
		verify(employeeRepositoryMock, times(1)).findAllActiveEmployees();
		verify(paymentRepositoryMock, times(1)).findActivePaymentByEmployeeId(UserContants.DB_EMPLOYEE_ID_1);
		verify(paymentRepositoryMock, times(1)).findActivePaymentByEmployeeId(UserContants.DB_EMPLOYEE_ID_2);
		verify(paymentRepositoryMock, times(1)).findActivePaymentByEmployeeId(UserContants.DB_EMPLOYEE_ID_3);
		
		assertEquals(UserContants.DB_TOTAL_ACTIVE_EMPLOYEES.intValue(), found.size());
	}
	
	@Test
	public void getEmployeesByCurrentPage_ValidState_ReturnsAll() {
		List<Employee> employees = new ArrayList<>();
		Employee e1 = new Employee(UserContants.DB_EMPLOYEE_ID_1, "Petar Markovic", 
				1000, LocalDateTime.of(2021, 9, 1, 0, 0));
		Employee e2 = new Employee(UserContants.DB_EMPLOYEE_ID_2, "Marko Markovic", 
				1001, LocalDateTime.of(2021, 10, 1, 0, 0));
		Employee e3 = new Employee(UserContants.DB_EMPLOYEE_ID_3, "Sara Markovic", 
				1001, LocalDateTime.of(2021, 11, 1, 0, 0));
		employees.add(e1);
		employees.add(e2);
		employees.add(e3);
		
		Payment payment1 = new Payment(PaymentConstants.DB_PAYMENT_ID_1, new BigDecimal(30000), e1, 
				LocalDateTime.of(2021, 9, 1, 0, 0));
		Payment payment2 = new Payment(PaymentConstants.DB_PAYMENT_ID_2, new BigDecimal(40000), e2, 
				LocalDateTime.of(2021, 10, 1, 0, 0));
		Payment payment3 = new Payment(PaymentConstants.DB_PAYMENT_ID_3, new BigDecimal(40000), e3, 
				LocalDateTime.of(2021, 11, 1, 0, 0));
		
		Pageable pageable = PageRequest.of(UserContants.CURRENT_PAGE, UserContants.PAGE_SIZE);
		Page<Employee> employeePage = new PageImpl<>(employees, pageable, UserContants.TOTAL_ELEMENTS);
		
		given(employeeRepositoryMock.findAllActiveEmployeesByCurrentPage(pageable)).willReturn(employeePage);
		given(paymentRepositoryMock.findActivePaymentByEmployeeId(UserContants.DB_EMPLOYEE_ID_1)).willReturn(payment1);
		given(paymentRepositoryMock.findActivePaymentByEmployeeId(UserContants.DB_EMPLOYEE_ID_2)).willReturn(payment2);
		given(paymentRepositoryMock.findActivePaymentByEmployeeId(UserContants.DB_EMPLOYEE_ID_3)).willReturn(payment3);
		
		List<EmployeeDTO> found = userService.getEmployeesByCurrentPage(UserContants.CURRENT_PAGE, UserContants.PAGE_SIZE);
		
		verify(employeeRepositoryMock, times(1)).findAllActiveEmployeesByCurrentPage(pageable);
		assertEquals(UserContants.DB_TOTAL_ACTIVE_EMPLOYEES_CURRENT_PAGE.intValue(), found.size());
	}
	
	@Test(expected = ItemExistsException.class)
	public void addNewManager_UsernameExists_ThrowsException() {
		Manager manager = new Manager(UserContants.DB_MANAGER_ID, "Marko", 
				UserContants.DB_MANAGER_USERNAME, "12345");
		
		given(managerRepositoryMock.findByUsername(UserContants.DB_MANAGER_USERNAME)).willReturn(manager);
		
		ManagerDTO managerDto = new ManagerDTO();
		managerDto.setName("Marko");
		managerDto.setUsername(UserContants.DB_MANAGER_USERNAME);
		managerDto.setPassword("12345");
		
		userService.addNewManager(managerDto);

		verify(managerRepositoryMock, times(1)).findByUsername(UserContants.DB_MANAGER_USERNAME);
	}
	
	@Test
	public void addNewManager_ValidUsername_ReturnsCreatedManager() {
		Manager savedManager = new Manager(UserContants.NEW_MANAGER_ID, "Petar", 
				UserContants.NEW_MANAGER_USERNAME, "12345");
		
		given(managerRepositoryMock.findByUsername(UserContants.NEW_MANAGER_USERNAME)).willReturn(null);
		given(managerRepositoryMock.save(any(Manager.class))).willReturn(savedManager);
		
		ManagerDTO managerDto = new ManagerDTO();
		managerDto.setName("Petar");
		managerDto.setUsername(UserContants.NEW_MANAGER_USERNAME);
		managerDto.setPassword("12345");
		
		ManagerDTO created = userService.addNewManager(managerDto);

		verify(managerRepositoryMock, times(1)).findByUsername(UserContants.NEW_MANAGER_USERNAME);
		verify(managerRepositoryMock, times(1)).save(any(Manager.class));

		assertEquals(UserContants.NEW_MANAGER_USERNAME, created.getUsername());
		
	}
	
	@Test
	public void addNewEmployee_ValidState_ReturnsCreatedEmployee() {
		Employee savedEmployee = new Employee(UserContants.NEW_EMPLOYEE_ID, "Pera", 
				UserContants.DB_MAX_CODE + 1, LocalDateTime.now());
		
		Payment savedPayment = new Payment(PaymentConstants.NEW_PAYMENT_ID, 
				new BigDecimal(30000), savedEmployee, 
				LocalDateTime.now());
		
		given(employeeRepositoryMock.findMaxCode()).willReturn(UserContants.DB_MAX_CODE);
		given(employeeRepositoryMock.save(any(Employee.class))).willReturn(savedEmployee);
		given(paymentRepositoryMock.save(any(Payment.class))).willReturn(savedPayment);
		
		EmployeeDTO employeeDto = new EmployeeDTO();
		employeeDto.setName("Uros");
		employeeDto.setPaymentBigDecimal(new BigDecimal(29000));
		employeeDto.setType("COOK");
		
		EmployeeDTO created = userService.addNewEmployee(employeeDto);

		verify(employeeRepositoryMock, times(1)).findMaxCode();
		verify(paymentRepositoryMock, times(1)).save(any(Payment.class));
		verify(employeeRepositoryMock, times(1)).save(any(Employee.class));

		assertEquals(UserContants.DB_MAX_CODE + 1, created.getCode().intValue());
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void editEmployee_InvalidId_ThrowsException() {
		given(employeeRepositoryMock.getActiveEmployeeById(UserContants.INVALID_USER_ID)).willReturn(null);
		
		EmployeeDTO employeeDto = new EmployeeDTO();
		employeeDto.setId(UserContants.INVALID_USER_ID);
		employeeDto.setActive(true);
		
		userService.editEmployee(employeeDto);
		
		verify(employeeRepositoryMock, times(1)).getActiveEmployeeById(UserContants.INVALID_USER_ID);
	}
	
	@Test
	public void editEmployee_EditNameAndPayment_ReturnsUpdatedEmployee() {
		Employee savedEmployee = new Employee(UserContants.DB_EDIT_EMPLOYEE_ID, 
				UserContants.EDIT_EMPLOYEE_NAME, 
				2345, LocalDateTime.of(2021, 4, 10, 0, 0));
		
		Employee employee = new Employee(UserContants.DB_EDIT_EMPLOYEE_ID, "Mika Mikic", 2345, 
				LocalDateTime.of(2021, 4, 10, 0, 0));
		employee.setActive(true);
		
		Payment payment = new Payment(PaymentConstants.DB_PAYMENT_ID_2, new BigDecimal(30000), 
				savedEmployee, LocalDateTime.of(2021, 4, 10, 0, 0));
		Payment oldPayment = new Payment(PaymentConstants.DB_PAYMENT_ID_2, new BigDecimal(30000), 
				savedEmployee, LocalDateTime.of(2021, 4, 10, 0, 0));
		oldPayment.setDateEnd(LocalDateTime.now());
		Payment newPayment = new Payment(PaymentConstants.NEW_PAYMENT_ID, UserContants.EDIT_EMPLOYEE_PAYMENT, 
				savedEmployee, LocalDateTime.now());
		
		given(employeeRepositoryMock.getActiveEmployeeById(UserContants.DB_EDIT_EMPLOYEE_ID)).willReturn(employee);
		given(employeeRepositoryMock.save(any(Employee.class))).willReturn(savedEmployee);
		given(paymentRepositoryMock.findActivePaymentByEmployeeId(UserContants.DB_EDIT_EMPLOYEE_ID)).willReturn(payment);
		given(paymentRepositoryMock.save(any(Payment.class))).willReturn(oldPayment);
		given(paymentRepositoryMock.save(any(Payment.class))).willReturn(newPayment);
		
		EmployeeDTO employeeDto = new EmployeeDTO();
		employeeDto.setId(UserContants.DB_EDIT_EMPLOYEE_ID);
		employeeDto.setName(UserContants.EDIT_EMPLOYEE_NAME);
		employeeDto.setPaymentBigDecimal(UserContants.EDIT_EMPLOYEE_PAYMENT);
		employeeDto.setActive(true);
		
		EmployeeDTO updatedEmployee = userService.editEmployee(employeeDto);
		
		verify(employeeRepositoryMock, times(1)).getActiveEmployeeById(UserContants.DB_EDIT_EMPLOYEE_ID);
		verify(employeeRepositoryMock, times(1)).save(any(Employee.class));
		verify(paymentRepositoryMock, times(1)).findActivePaymentByEmployeeId(UserContants.DB_EDIT_EMPLOYEE_ID);
		verify(paymentRepositoryMock, times(2)).save(any(Payment.class));
		
		assertEquals(UserContants.EDIT_EMPLOYEE_PAYMENT, updatedEmployee.getPaymentBigDecimal());
		assertEquals(UserContants.EDIT_EMPLOYEE_NAME, updatedEmployee.getName());
	}
	
	@Test
	public void editEmployee_EditOnlyName_ReturnsUpdatedEmployee() {
		Employee savedEmployee = new Employee(UserContants.DB_EDIT_EMPLOYEE_ID, 
				UserContants.EDIT_EMPLOYEE_NAME, 
				2345, LocalDateTime.of(2021, 4, 10, 0, 0));
		
		Employee employee = new Employee(UserContants.DB_EDIT_EMPLOYEE_ID, "Mika Mikic", 
				2345, LocalDateTime.of(2021, 4, 10, 0, 0));
		employee.setActive(true);
		
		Payment payment = new Payment(PaymentConstants.DB_PAYMENT_ID_2, new BigDecimal(30000), 
				savedEmployee, LocalDateTime.of(2021, 4, 10, 0, 0));

		given(employeeRepositoryMock.getActiveEmployeeById(UserContants.DB_EDIT_EMPLOYEE_ID)).willReturn(employee);
		given(employeeRepositoryMock.save(any(Employee.class))).willReturn(savedEmployee);
		given(paymentRepositoryMock.findActivePaymentByEmployeeId(UserContants.DB_EDIT_EMPLOYEE_ID)).willReturn(payment);
		
		EmployeeDTO employeeDto = new EmployeeDTO();
		employeeDto.setId(UserContants.DB_EDIT_EMPLOYEE_ID);
		employeeDto.setName(UserContants.EDIT_EMPLOYEE_NAME);
		employeeDto.setPaymentBigDecimal(new BigDecimal(30000));
		employeeDto.setActive(true);
		
		EmployeeDTO updatedEmployee = userService.editEmployee(employeeDto);
		
		verify(employeeRepositoryMock, times(1)).getActiveEmployeeById(UserContants.DB_EDIT_EMPLOYEE_ID);
		verify(employeeRepositoryMock, times(1)).save(any(Employee.class));
		verify(paymentRepositoryMock, times(1)).findActivePaymentByEmployeeId(UserContants.DB_EDIT_EMPLOYEE_ID);
		
		assertEquals(UserContants.EDIT_EMPLOYEE_NAME, updatedEmployee.getName());
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void deleteEmployee_InvalidId_ThrowsException() {
		given(employeeRepositoryMock.getActiveEmployeeById(UserContants.INVALID_USER_ID)).willReturn(null);
			
		userService.deleteEmployee(UserContants.INVALID_USER_ID);
		
		verify(employeeRepositoryMock, times(1)).getActiveEmployeeById(UserContants.INVALID_USER_ID);
	}
	
	@Test
	public void deleteEmployee_ValidId_ReturnsNothing() {
		Employee savedEmployee = new Employee(UserContants.DB_DELETE_EMPLOYEE_ID, 
				"Mika Mikic", 2345, LocalDateTime.of(2021, 4, 10, 0, 0));
		savedEmployee.setActive(false);
		Payment payment = new Payment(PaymentConstants.DB_PAYMENT_ID_1, new BigDecimal(30000), 
				savedEmployee, LocalDateTime.of(2021, 4, 10, 0, 0));
		Payment savedPayment = new Payment(PaymentConstants.NEW_PAYMENT_ID, new BigDecimal(30000), 
				savedEmployee, LocalDateTime.of(2021, 4, 10, 0, 0));
		savedPayment.setDateEnd(LocalDateTime.now());
		
		Employee employee = new Employee(UserContants.DB_DELETE_EMPLOYEE_ID, "Mika Mikic", 
				2345, LocalDateTime.of(2021, 4, 10, 0, 0));
		employee.setActive(true);
		
		given(employeeRepositoryMock.getActiveEmployeeById(UserContants.DB_DELETE_EMPLOYEE_ID)).willReturn(employee);
		given(paymentRepositoryMock.findActivePaymentByEmployeeId(UserContants.DB_DELETE_EMPLOYEE_ID)).willReturn(payment);
		given(paymentRepositoryMock.save(any(Payment.class))).willReturn(savedPayment);
		given(employeeRepositoryMock.save(any(Employee.class))).willReturn(savedEmployee);
			
		userService.deleteEmployee(UserContants.DB_DELETE_EMPLOYEE_ID);
		
		verify(employeeRepositoryMock, times(1)).getActiveEmployeeById(UserContants.DB_DELETE_EMPLOYEE_ID);
		verify(paymentRepositoryMock, times(1)).findActivePaymentByEmployeeId(UserContants.DB_DELETE_EMPLOYEE_ID);
		verify(paymentRepositoryMock, times(1)).save(any(Payment.class));
		verify(employeeRepositoryMock, times(1)).save(any(Employee.class));
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void findById_InvalidId_ThrowsException() {
		given(employeeRepositoryMock.getActiveEmployeeById(UserContants.INVALID_USER_ID)).willReturn(null);
		
		userService.findById(UserContants.INVALID_USER_ID);
		
		verify(employeeRepositoryMock, times(1)).getActiveEmployeeById(UserContants.INVALID_USER_ID);
	}
	
	@Test
	public void findById_ValidId_ReturnUser() {	
		Employee employee = new Employee(UserContants.DB_EMPLOYEE_ID_1, "Mika Mikic", 
				2345, LocalDateTime.of(2021, 4, 10, 0, 0));
		employee.setActive(true);
		
		given(employeeRepositoryMock.getActiveEmployeeById(UserContants.DB_EMPLOYEE_ID_1)).willReturn(employee);
		
		EmployeeDTO found = userService.findById(UserContants.DB_EMPLOYEE_ID_1);
		
		verify(employeeRepositoryMock, times(1)).getActiveEmployeeById(UserContants.DB_EMPLOYEE_ID_1);
		
		assertEquals(UserContants.DB_EMPLOYEE_ID_1, found.getId());
	}
	
}
