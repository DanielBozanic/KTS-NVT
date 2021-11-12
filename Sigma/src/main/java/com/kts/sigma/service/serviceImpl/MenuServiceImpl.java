package com.kts.sigma.service.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kts.sigma.model.Menu;
import com.kts.sigma.repository.MenuRepository;
import com.kts.sigma.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService{
	@Autowired
	private MenuRepository menuRepository;
	
	@Override
	public Iterable<Menu> getAll() {
		return menuRepository.findAll();
	}
	
	@Override
	public Menu save(Menu item) {
		return menuRepository.save(item);
	}
	
	@Override
	public void deleteById(Integer id) {
		menuRepository.deleteById(id);
	}
	@Override
	public Optional<Menu> findById(Integer id)
	{
		return menuRepository.findById(id);
	}
}
