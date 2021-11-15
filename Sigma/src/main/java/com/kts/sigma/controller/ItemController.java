package com.kts.sigma.controller;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kts.sigma.model.Food;
import com.kts.sigma.model.Item;
import com.kts.sigma.service.ItemService;
import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.Utility.Mapper;
import com.kts.sigma.dto.ItemDTO;

@RestController
@RequestMapping("/items")
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@GetMapping(path="")
	public Iterable<ItemDTO> getAll(){
		return itemService.getAll();
	}
	
	@GetMapping("/{id}")
	ItemDTO getOne(@PathVariable Integer id) {
		
	    return itemService.findById(id);
	}
	
	@PostMapping("")
	Item post(@RequestBody Item newEntity) {
	  return itemService.save(newEntity);
	}
	
	@DeleteMapping("/{id}")
	void delete(@PathVariable Integer id) {
		itemService.deleteById(id);
	}
}