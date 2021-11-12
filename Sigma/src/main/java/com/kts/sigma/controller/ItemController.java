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

import com.kts.sigma.model.Item;
import com.kts.sigma.service.ItemService;
import com.kts.sigma.Exception.ItemNotFoundException;

@RestController
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@GetMapping(path="/items")
	public Iterable<Item> getAll(){
		return itemService.getAll();
	}
	
	@GetMapping("/items/{id}")
	Item getOne(@PathVariable Integer id) {
		Item result = itemService.findById(id).orElse(null);
		if(result == null)
		{
			throw new ItemNotFoundException(id);
		}
	    return result;
	}
	
	@PostMapping("/items")
	Item post(@RequestBody Item newEntity) {
	  return itemService.save(newEntity);
	}
	
	@DeleteMapping("/items/{id}")
	void delete(@PathVariable Integer id) {
		itemService.deleteById(id);
	}
}