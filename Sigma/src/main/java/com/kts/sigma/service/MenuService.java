package com.kts.sigma.service;

import java.util.Optional;

import com.kts.sigma.model.Menu;

public interface MenuService {
	public Iterable<Menu> getAll();
	
	public Optional<Menu> findById(Integer id);
	
	public Menu save(Menu item);
	
	public void deleteById(Integer id);
}
