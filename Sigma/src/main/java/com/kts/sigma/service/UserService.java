package com.kts.sigma.service;

import java.util.ArrayList;

import com.kts.sigma.dto.EmployeeDTO;
import com.kts.sigma.dto.ManagerDTO;

public interface UserService {

	ArrayList<EmployeeDTO> getAllEmployees();
	
	EmployeeDTO findById(Integer id);
	
	EmployeeDTO addNewEmployee(EmployeeDTO newEmployee);
	
	ManagerDTO addNewManager(ManagerDTO newManager);
	
	EmployeeDTO editEmployee(EmployeeDTO employeeDto);
	
	void deleteEmployee(Integer id);
}
