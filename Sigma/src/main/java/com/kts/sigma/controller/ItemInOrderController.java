package com.kts.sigma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.model.ItemInOrder;
import com.kts.sigma.service.ItemInOrderService;

@RestController
public class ItemInOrderController {
	@Autowired
	private ItemInOrderService itemInOrderService;
	
	@GetMapping(path="/item-in-order")
	public Iterable<ItemInOrder> getAll(){
		return itemInOrderService.getAll();
	}
	
	@GetMapping("/item-in-order/{id}")
	ItemInOrder getOne(@PathVariable Integer id) {
		ItemInOrder result = itemInOrderService.findById(id).orElse(null);
		if(result == null)
		{
			throw new ItemNotFoundException(id);
		}
	    return result;
	}
	
	@PostMapping("/item-in-order")
	ItemInOrder post(@RequestBody ItemInOrder newEntity) {
	  return itemInOrderService.save(newEntity);
	}
	
	@DeleteMapping("/item-in-order/{id}")
	void delete(@PathVariable Integer id) {
		itemInOrderService.deleteById(id);
	}
}