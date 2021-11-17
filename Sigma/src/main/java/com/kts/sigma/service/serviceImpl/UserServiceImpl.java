package com.kts.sigma.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.Utility.Mapper;
import com.kts.sigma.dto.EmployeeDTO;
import com.kts.sigma.dto.MenuDTO;
import com.kts.sigma.model.Bartender;
import com.kts.sigma.model.Cook;
import com.kts.sigma.model.Manager;
import com.kts.sigma.model.Menu;
import com.kts.sigma.model.User;
import com.kts.sigma.repository.UserRepository;
import com.kts.sigma.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Iterable<EmployeeDTO> getAll() {
		List<User> users = userRepository.findAll();
		ArrayList<EmployeeDTO> results = new ArrayList<EmployeeDTO>();
		
		for (User user : users) {
			if(!(user instanceof Manager)) {
				EmployeeDTO dto = Mapper.mapper.map(user, EmployeeDTO.class);
				
				if(user instanceof Cook) {
					dto.setType("COOK");
				}else if(user instanceof Bartender) {
					dto.setType("BARTENDER");
				}else {
					dto.setType("WAITER");
				}
				
				results.add(dto);
			}
		}
		
		return results;
	}
	
	@Override
	public User save(User item) {
		return userRepository.save(item);
	}
	
	@Override
	public void deleteById(Integer id) {
		userRepository.deleteById(id);
	}
	@Override
	public EmployeeDTO findById(Integer id)
	{
		User user = userRepository.findById(id).orElse(null);
		if(user == null || user instanceof Manager)
		{
			throw new ItemNotFoundException(id, "employee");
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
