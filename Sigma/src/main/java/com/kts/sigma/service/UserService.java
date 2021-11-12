package com.kts.sigma.service;

import java.util.Optional;

import com.kts.sigma.model.User;

public interface UserService {

	public Iterable<User> getAll();
	
	public Optional<User> findById(Integer id);
	
	public User save(User item);
	
	public void deleteById(Integer id);
}
