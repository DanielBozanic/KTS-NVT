package com.kts.sigma.service.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.kts.sigma.model.User;
import com.kts.sigma.model.Waiter;
import com.kts.sigma.repository.EmployeeRepository;
import com.kts.sigma.repository.ManagerRepository;
import com.kts.sigma.repository.PaymentRepository;
import com.kts.sigma.repository.UserRepository;
import com.kts.sigma.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private ManagerRepository managerRepository;
	
	@Override
	public ArrayList<EmployeeDTO> getAllEmployees() {
		List<Employee> users = employeeRepository.findAll();
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
			
			Payment employeePayment = paymentRepository.findByEmployeeId(user.getId());
			dto.setPaymentBigDecimal(employeePayment.getPayment());
			
			results.add(dto);
		}
		return results;
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
		Employee existingCode = employeeRepository.findByCode(newEmployee.getCode());
		if (existingCode != null) {
			throw new ItemExistsException("An employee with this code " + 
					"(" + newEmployee.getCode() + ") " + "already exists!");
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
		Employee employee = employeeRepository.findById(employeeDto.getId()).orElse(null);
		if (employee == null) {
			throw new ItemNotFoundException(employeeDto.getId());
		}
		
		employee.setName(employeeDto.getName());
		employee = employeeRepository.save(employee);
		
		EmployeeDTO dto = Mapper.mapper.map(employee, EmployeeDTO.class);
		
		Payment employeePayment = paymentRepository.findByEmployeeId(employeeDto.getId());
		
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
		Employee employee = employeeRepository.findById(id).orElse(null);
		if (employee == null) {
			throw new ItemNotFoundException(id);
		}
		
		employee.setActive(false);
		employeeRepository.save(employee);
	}
	
	@Override
	public EmployeeDTO findById(Integer id)
	{
		User user = userRepository.findById(id).orElse(null);
		if(user == null || user instanceof Manager)
		{
			throw new ItemNotFoundException(id);
		}
		
		EmployeeDTO result = Mapper.mapper.map(user, EmployeeDTO.class);
		
		if(user instanceof Cook) {
			result.setType("COOK");
		}else if(user instanceof Bartender) {
			result.setType("BARTENDER");
		}else {
			result.setType("WAITER");
		}
		
	    return result;
	}
}
