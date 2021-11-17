package com.kts.sigma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.dto.TableDTO;
import com.kts.sigma.model.RestaurantTable;
import com.kts.sigma.model.TableState;
import com.kts.sigma.service.TableService;

@RestController
@RequestMapping("/tables")
public class TableController {
	@Autowired
	private TableService tableService;
	
	@GetMapping(path="")
	public Iterable<TableDTO> getAll(){
		return tableService.getAll();
	}
	
	@GetMapping("/{id}")
	TableDTO getOne(@PathVariable Integer id) {
		return tableService.findById(id);
	}
	
	@PostMapping("")
	RestaurantTable post(@RequestBody RestaurantTable newEntity) {
	  return tableService.save(newEntity);
	}
	
	@PutMapping("/{id}/{state}")
	public void changeState(@PathVariable Integer id, @PathVariable TableState state) {
		tableService.changeState(id, state);
	}
	
	@DeleteMapping("/{id}")
	void delete(@PathVariable Integer id) {
		tableService.deleteById(id);
	}
}