package com.kts.sigma.service.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kts.sigma.model.User;
import com.kts.sigma.repository.UserRepository;
import com.kts.sigma.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Iterable<User> getAll() {
		return userRepository.findAll();
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
	public Optional<User> findById(Integer id)
	{
		return userRepository.findById(id);
	}
}
