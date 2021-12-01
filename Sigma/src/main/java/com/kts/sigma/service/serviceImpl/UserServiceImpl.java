package com.kts.sigma.service.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kts.sigma.Exception.ItemExistsException;
import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.Utility.Mapper;
import com.kts.sigma.dto.EmployeeDTO;
import com.kts.sigma.dto.ManagerDTO;
import com.kts.sigma.model.Bartender;
import com.kts.sigma.model.Cook;
import com.kts.sigma.model.Employee;
import com.kts.sigma.model.Manager;
import com.kts.sigma.model.Payment;
import com.kts.sigma.model.Waiter;
import com.kts.sigma.repository.EmployeeRepository;
import com.kts.sigma.repository.ManagerRepository;
import com.kts.sigma.repository.PaymentRepository;
import com.kts.sigma.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private ManagerRepository managerRepository;
	
	@Override
	public ArrayList<EmployeeDTO> getAllEmployees() {
		List<Employee> users = employeeRepository.findAllActiveEmployees();
		ArrayList<EmployeeDTO> results = new ArrayList<EmployeeDTO>();
		
		for (Employee user : users) {
			EmployeeDTO dto = Mapper.mapper.map(user, EmployeeDTO.class);
			
			if(user instanceof Cook) {
				dto.setType("COOK");
			} else if(user instanceof Bartender) {
				dto.setType("BARTENDER");
			} else {
				dto.setType("WAITER");
			}
			
			Payment employeePayment = paymentRepository.findActivePaymentByEmployeeId(user.getId());
			dto.setPaymentBigDecimal(employeePayment.getPayment());
			
			results.add(dto);
		}
		return results;
	}
	
	@Override
	public List<EmployeeDTO> getEmployeesByCurrentPage(Integer currentPage, Integer pageSize) {
		Pageable page = PageRequest.of(currentPage, pageSize);
		List<Employee> employees = employeeRepository.findAllActiveEmployeesByCurrentPage(page).toList();
		ArrayList<EmployeeDTO> results = new ArrayList<EmployeeDTO>();
		for (Employee e : employees) {
			EmployeeDTO dto = Mapper.mapper.map(e, EmployeeDTO.class);
			
			if(e instanceof Cook) {
				dto.setType("COOK");
			} else if(e instanceof Bartender) {
				dto.setType("BARTENDER");
			} else {
				dto.setType("WAITER");
			}
			
			Payment employeePayment = paymentRepository.findActivePaymentByEmployeeId(e.getId());
			dto.setPaymentBigDecimal(employeePayment.getPayment());
			
			results.add(dto);
		}
		return results;
	}
	
	@Override
	public Integer getNumberOfActiveEmployeeRecords() {
		return employeeRepository.getNumberOfActiveEmployeeRecords();
	}
	
	@Override
	public ManagerDTO addNewManager(ManagerDTO newManager) {
		Manager existingUsername = managerRepository.findByUsername(newManager.getUsername());
		if (existingUsername != null) {
			throw new ItemExistsException("A manager with this username " + 
					"(" + existingUsername.getUsername() + ") " + "already exists!");
		}
		Manager manager = Mapper.mapper.map(newManager, Manager.class);
		manager = managerRepository.save(manager);
		
		return Mapper.mapper.map(manager, ManagerDTO.class);
	}
	
	@Override
	public EmployeeDTO addNewEmployee(EmployeeDTO newEmployee) {
		Integer maxCode = employeeRepository.findMaxCode();
		if (maxCode == null) {
			newEmployee.setCode(1000);
		} else {
			newEmployee.setCode(maxCode + 1);
		}
		
		Employee employee = null;
		if (newEmployee.getType().toUpperCase().equals("WAITER")) {
			employee = Mapper.mapper.map(newEmployee, Waiter.class);
		} else if (newEmployee.getType().toUpperCase().equals("BARTENDER")) {
			employee = Mapper.mapper.map(newEmployee, Bartender.class);
		} else {
			employee = Mapper.mapper.map(newEmployee, Cook.class);
		}
		
		employee.setActive(true);
		employee.setDateOfEmployment(LocalDateTime.now());
		employee = employeeRepository.save(employee);
		
		Payment payment = new Payment();
		payment.setDateCreated(LocalDateTime.now());
		payment.setEmployee(employee);
		payment.setPayment(newEmployee.getPaymentBigDecimal());
		
		paymentRepository.save(payment);
		
		EmployeeDTO dto = Mapper.mapper.map(employee, EmployeeDTO.class);
		if (employee instanceof Cook) {
			dto.setType("COOK");
		} else if(employee instanceof Bartender) {
			dto.setType("BARTENDER");
		} else {
			dto.setType("WAITER");
		}
		dto.setPaymentBigDecimal(newEmployee.getPaymentBigDecimal());
		
		return dto;
	}
	
	@Override
	public EmployeeDTO editEmployee(EmployeeDTO employeeDto) {
		Employee employee = employeeRepository.getActiveEmployeeById(employeeDto.getId());
		if (employee == null) {
			throw new ItemNotFoundException(employeeDto.getId(), "employeee");
		}
		
		employee.setName(employeeDto.getName());
		employee = employeeRepository.save(employee);
		
		EmployeeDTO dto = Mapper.mapper.map(employee, EmployeeDTO.class);
		
		Payment employeePayment = paymentRepository.findActivePaymentByEmployeeId(employeeDto.getId());
		
		if (employeeDto.getPaymentBigDecimal() != employeePayment.getPayment()) {
			employeePayment.setDateEnd(LocalDateTime.now());
			paymentRepository.save(employeePayment);
			
			Payment newPayment = new Payment();
			newPayment.setDateCreated(LocalDateTime.now());
			newPayment.setEmployee(employee);
			newPayment.setPayment(employeeDto.getPaymentBigDecimal());
			
			paymentRepository.save(newPayment);
			
			dto.setPaymentBigDecimal(employeeDto.getPaymentBigDecimal());
		} else {
			dto.setPaymentBigDecimal(employeePayment.getPayment());
		}
		
		if (employee instanceof Cook) {
			dto.setType("COOK");
		} else if(employee instanceof Bartender) {
			dto.setType("BARTENDER");
		} else {
			dto.setType("WAITER");
		}
	
		return dto;
	}
	
	@Override
	public void deleteEmployee(Integer id) {
		Employee employee = employeeRepository.getActiveEmployeeById(id);
		if (employee == null) {
			throw new ItemNotFoundException(id, "employee");
		}
		
		Payment employeePayment = paymentRepository.findActivePaymentByEmployeeId(id);
		employeePayment.setDateEnd(LocalDateTime.now());
		paymentRepository.save(employeePayment);
		
		employee.setActive(false);
		employeeRepository.save(employee);
	}
	
	@Override
	public EmployeeDTO findById(Integer id)
	{
		Employee employee = employeeRepository.getActiveEmployeeById(id);
		if(employee == null) {
			throw new ItemNotFoundException(id, "employee");
		}
		
		EmployeeDTO result = Mapper.mapper.map(employee, EmployeeDTO.class);
		
		if(employee instanceof Cook) {
			result.setType("COOK");
		} else if(employee instanceof Bartender) {
			result.setType("BARTENDER");
		} else {
			result.setType("WAITER");
		}
		
	    return result;
	}
}
