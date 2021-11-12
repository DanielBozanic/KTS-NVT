package com.kts.sigma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.model.Menu;
import com.kts.sigma.service.MenuService;

@RestController
public class MenuController {
	@Autowired
	private MenuService menuService;
	
	@GetMapping(path="/menu")
	public Iterable<Menu> getAll(){
		return menuService.getAll();
	}
	
	@GetMapping("/menu/{id}")
	Menu getOne(@PathVariable Integer id) {
		Menu result = menuService.findById(id).orElse(null);
		if(result == null)
		{
			throw new ItemNotFoundException(id);
		}
	    return result;
	}
	
	@PostMapping("/menu")
	Menu post(@RequestBody Menu newEntity) {
	  return menuService.save(newEntity);
	}
	
	@DeleteMapping("/menu/{id}")
	void delete(@PathVariable Integer id) {
		menuService.deleteById(id);
	}
}