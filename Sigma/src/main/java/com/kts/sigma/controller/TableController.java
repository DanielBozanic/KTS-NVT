package com.kts.sigma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.model.RestaurantTable;
import com.kts.sigma.service.TableService;

@RestController
public class TableController {
	@Autowired
	private TableService tableService;
	
	@GetMapping(path="/tables")
	public Iterable<RestaurantTable> getAll(){
		return tableService.getAll();
	}
	
	@GetMapping("/tables/{id}")
	RestaurantTable getOne(@PathVariable Integer id) {
		RestaurantTable result = tableService.findById(id).orElse(null);
		if(result == null)
		{
			throw new ItemNotFoundException(id);
		}
	    return result;
	}
	
	@PostMapping("/tables")
	RestaurantTable post(@RequestBody RestaurantTable newEntity) {
	  return tableService.save(newEntity);
	}
	
	@DeleteMapping("/tables/{id}")
	void delete(@PathVariable Integer id) {
		tableService.deleteById(id);
	}
}