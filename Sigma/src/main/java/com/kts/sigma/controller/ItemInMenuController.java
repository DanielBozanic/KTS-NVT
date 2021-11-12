package com.kts.sigma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.model.ItemInMenu;
import com.kts.sigma.service.ItemInMenuService;

@RestController
public class ItemInMenuController {
	@Autowired
	private ItemInMenuService itemInMenuService;
	
	@GetMapping(path="/item-in-menu")
	public Iterable<ItemInMenu> getAll(){
		return itemInMenuService.getAll();
	}
	
	@GetMapping("/item-in-menu/{id}")
	ItemInMenu getOne(@PathVariable Integer id) {
		ItemInMenu result = itemInMenuService.findById(id).orElse(null);
		if(result == null)
		{
			throw new ItemNotFoundException(id);
		}
	    return result;
	}
	
	@PostMapping("/item-in-menu")
	ItemInMenu post(@RequestBody ItemInMenu newEntity) {
	  return itemInMenuService.save(newEntity);
	}
	
	@DeleteMapping("/item-in-menu/{id}")
	void delete(@PathVariable Integer id) {
		itemInMenuService.deleteById(id);
	}
}