package com.kts.sigma.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.Utility.Mapper;
import com.kts.sigma.dto.MenuDTO;
import com.kts.sigma.model.Menu;
import com.kts.sigma.repository.MenuRepository;
import com.kts.sigma.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService{
	@Autowired
	private MenuRepository menuRepository;
	
	@Override
	public Iterable<MenuDTO> getAll() {
		List<Menu> menus = menuRepository.findAll();
		ArrayList<MenuDTO> results = new ArrayList<MenuDTO>();
		
		for (Menu menu : menus) {
			MenuDTO dto = Mapper.mapper.map(menu, MenuDTO.class);
			results.add(dto);
		}
		
		return results;
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
	public MenuDTO findById(Integer id)
	{
		Menu menu =  menuRepository.findById(id).orElse(null);
		if(menu == null)
		{
			throw new ItemNotFoundException(id);
		}
		
		MenuDTO result = Mapper.mapper.map(menu, MenuDTO.class);
	    return result;
	}
}
