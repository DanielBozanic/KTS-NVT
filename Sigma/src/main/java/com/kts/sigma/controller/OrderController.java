package com.kts.sigma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kts.sigma.dto.OrderDTO;
import com.kts.sigma.model.RestaurantOrder;
import com.kts.sigma.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@GetMapping(path="")
	public Iterable<OrderDTO> getAll(){
		return orderService.getAll();
	}
	
	@GetMapping("/{id}")
	OrderDTO getOne(@PathVariable Integer id) {
		return orderService.findById(id);
	}
	
	@PostMapping("")
	RestaurantOrder post(@RequestBody OrderDTO newEntity) {
	  return orderService.save(newEntity);
	}
	
	@DeleteMapping("/{id}")
	void delete(@PathVariable Integer id) {
		orderService.deleteById(id);
	}
}