package com.kts.sigma.service;

import java.util.Optional;

import com.kts.sigma.dto.EmployeeDTO;
import com.kts.sigma.model.User;

public interface UserService {

	public Iterable<EmployeeDTO> getAll();
	
	public EmployeeDTO findById(Integer id);
	
	public User save(User item);
	
	public void deleteById(Integer id);
}
