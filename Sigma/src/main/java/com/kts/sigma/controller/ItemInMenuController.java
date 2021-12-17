package com.kts.sigma.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.kts.sigma.dto.ItemDTO;
import com.kts.sigma.model.FoodType;
import com.kts.sigma.model.Item;
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
	
	@GetMapping(path="getAll/{menuId}")
	public Iterable<ItemDTO> getAllInMenu(@PathVariable Integer menuId){
		return itemInMenuService.getAllInMenu(menuId);
	}
		
	@GetMapping("/{id}")
	ItemDTO getOne(@PathVariable Integer id) {
		return itemInMenuService.findById(id);
	}
	
	@PostMapping("")
	ItemInMenu post(@RequestBody ItemInMenu newEntity) {
	  return itemInMenuService.save(newEntity);
	}
	
	@GetMapping(value = "/getItemsByCurrentPage", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ItemDTO>> getItemsByCurrentPage(@RequestParam("menuId") Integer menuId, @RequestParam("currentPage") Integer currentPage, 
			@RequestParam("pageSize") Integer pageSize) {
		return new ResponseEntity<>(itemInMenuService.getItemsByCurrentPage(menuId, currentPage, pageSize), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getItemsBySearchTerm/{searchTerm}")
	public ResponseEntity<ArrayList<ItemDTO>> getItemsBySearchTerm(@RequestParam("menuId") Integer menuId, @PathVariable String searchTerm) {
		return new ResponseEntity<>(itemInMenuService.getItemsBySearchTerm(menuId, searchTerm), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getItemsByFoodType/{foodType}")
	public ResponseEntity<ArrayList<ItemDTO>> getItemsByFoodType(@RequestParam("menuId") Integer menuId, @PathVariable FoodType foodType) {
		return new ResponseEntity<>(itemInMenuService.getItemsByFoodType(menuId, foodType), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	void delete(@PathVariable Integer id) {
		itemInMenuService.deleteById(id);
	}
}