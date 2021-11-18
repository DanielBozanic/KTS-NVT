package com.kts.sigma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.dto.MenuDTO;
import com.kts.sigma.model.Menu;
import com.kts.sigma.service.MenuService;

@RestController
@RequestMapping("/menu")
public class MenuController {
	@Autowired
	private MenuService menuService;
	
	@GetMapping(path="")
	public Iterable<MenuDTO> getAll(){
		return menuService.getAll();
	}
	
	@GetMapping("/{id}")
	MenuDTO getOne(@PathVariable Integer id) {
		return menuService.findById(id);
	}
	
	@PostMapping("")
	Menu post(@RequestBody Menu newEntity) {
	  return menuService.save(newEntity);
	}
	
	@DeleteMapping("/{id}")
	void delete(@PathVariable Integer id) {
		menuService.deleteById(id);
	}

	//TODO dodaj search and stuff
}