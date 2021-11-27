package com.kts.sigma.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kts.sigma.dto.EmployeeDTO;
import com.kts.sigma.dto.ManagerDTO;
import com.kts.sigma.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value="/getAllEmployees", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<EmployeeDTO>> getAllEmployees(){
		return new ResponseEntity<>(userService.getAllEmployees(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDTO> getOne(@PathVariable Integer id) {
		return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/addManager", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ManagerDTO> addManager(@RequestBody ManagerDTO managerDto) {
	  return new ResponseEntity<>(userService.addNewManager(managerDto), HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/addEmployee", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody EmployeeDTO newEmployee) {
		return new ResponseEntity<>(userService.addNewEmployee(newEmployee), HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/editEmployee", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeDTO> editEmployee(@RequestBody EmployeeDTO employeeDto) {
		return new ResponseEntity<>(userService.editEmployee(employeeDto), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/deleteEmployee/{employeeId}")
	public void deleteEmployee(@PathVariable Integer employeeId) {
		userService.deleteEmployee(employeeId);
	}

}
