package com.kts.sigma.service;

import java.util.ArrayList;
import java.util.List;

import com.kts.sigma.dto.EmployeeDTO;
import com.kts.sigma.dto.ManagerDTO;

public interface UserService {

	ArrayList<EmployeeDTO> getAllEmployees();
	
	List<EmployeeDTO> getEmployeesByCurrentPage(Integer currentPage, Integer pageSize);
	
	Integer getNumberOfActiveEmployeeRecords();
	
	EmployeeDTO findById(Integer id);
	
	EmployeeDTO addNewEmployee(EmployeeDTO newEmployee);
	
	ManagerDTO addNewManager(ManagerDTO newManager);
	
	EmployeeDTO editEmployee(EmployeeDTO employeeDto);
	
	void deleteEmployee(Integer id);
}
