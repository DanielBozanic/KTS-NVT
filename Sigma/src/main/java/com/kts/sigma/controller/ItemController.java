package com.kts.sigma.controller;
import java.util.ArrayList;
import java.util.List;

import com.kts.sigma.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.kts.sigma.service.ItemService;
import com.kts.sigma.dto.ItemDTO;
import com.kts.sigma.model.FoodType;

@RestController
@RequestMapping("/items")
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@GetMapping(path="")
	public ResponseEntity<Iterable<ItemDTO>> getAll(){
		return new ResponseEntity<>(itemService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ItemDTO> getOne(@PathVariable Integer id) {
	    return new ResponseEntity<>(itemService.findById(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getItemsByCurrentPage", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ItemDTO>> getItemsByCurrentPage(@RequestParam("currentPage") Integer currentPage, 
			@RequestParam("pageSize") Integer pageSize) {
		return new ResponseEntity<>(itemService.getItemsByCurrentPage(currentPage, pageSize), HttpStatus.OK);
	}
	
	@PostMapping(value = "/createNewItem", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Item> createNewItem(@RequestBody ItemDTO itemDto) {
		return new ResponseEntity<>(itemService.createNewItem(itemDto), HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/getItemsBySearchTerm/{searchTerm}")
	public ResponseEntity<ArrayList<ItemDTO>> getItemsBySearchTerm(@PathVariable String searchTerm) {
		return new ResponseEntity<>(itemService.getItemsBySearchTerm(searchTerm), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getItemsByFoodType/{foodType}")
	public ResponseEntity<ArrayList<ItemDTO>> getItemsByFoodType(@PathVariable FoodType foodType) {
		return new ResponseEntity<>(itemService.getItemsByFoodType(foodType), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		itemService.deleteById(id);
	}
}