package com.kts.sigma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kts.sigma.dto.ItemDTO;
import com.kts.sigma.model.ItemInMenu;
import com.kts.sigma.service.ItemInMenuService;

@RestController
@RequestMapping("/item-in-menu")
public class ItemInMenuController {
	@Autowired
	private ItemInMenuService itemInMenuService;
	
	@GetMapping(path="")
	public Iterable<ItemDTO> getAll(){
		return itemInMenuService.getAll();
	}
	
	@GetMapping("/{id}")
	ItemDTO getOne(@PathVariable Integer id) {
		return itemInMenuService.findById(id);
	}
	
	@PostMapping("")
	ItemInMenu post(@RequestBody ItemInMenu newEntity) {
	  return itemInMenuService.save(newEntity);
	}
	
	@DeleteMapping("/{id}")
	void delete(@PathVariable Integer id) {
		itemInMenuService.deleteById(id);
	}
}