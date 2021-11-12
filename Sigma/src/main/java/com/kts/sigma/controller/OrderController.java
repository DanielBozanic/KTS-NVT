package com.kts.sigma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.model.RestaurantOrder;
import com.kts.sigma.service.OrderService;

@RestController
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@GetMapping(path="/orders")
	public Iterable<RestaurantOrder> getAll(){
		return orderService.getAll();
	}
	
	@GetMapping("/orders/{id}")
	RestaurantOrder getOne(@PathVariable Integer id) {
		RestaurantOrder result = orderService.findById(id).orElse(null);
		if(result == null)
		{
			throw new ItemNotFoundException(id);
		}
	    return result;
	}
	
	@PostMapping("/orders")
	RestaurantOrder post(@RequestBody RestaurantOrder newEntity) {
	  return orderService.save(newEntity);
	}
	
	@DeleteMapping("/orders/{id}")
	void delete(@PathVariable Integer id) {
		orderService.deleteById(id);
	}
}