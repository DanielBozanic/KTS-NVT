package com.kts.sigma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kts.sigma.dto.EmployeeDTO;
import com.kts.sigma.dto.MenuDTO;
import com.kts.sigma.model.Employee;
import com.kts.sigma.model.Menu;
import com.kts.sigma.model.User;
import com.kts.sigma.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping(path="")
	public Iterable<EmployeeDTO> getAll(){
		return userService.getAll();
	}
	
	@GetMapping("/{id}")
	EmployeeDTO getOne(@PathVariable Integer id) {
		return userService.findById(id);
	}
	
	@PostMapping("")
	User post(@RequestBody Employee newEntity) {
	  return userService.save(newEntity);
	}
	
	@DeleteMapping("/{id}")
	void delete(@PathVariable Integer id) {
		userService.deleteById(id);
	}

}
