package com.kts.sigma.controller;

import java.util.ArrayList;

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
import com.kts.sigma.dto.MenuDTO;
import com.kts.sigma.service.MenuService;

@RestController
@RequestMapping("/menu")
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	
	@GetMapping(path="")
	public ResponseEntity<Iterable<MenuDTO>> getAll(){
		return new ResponseEntity<>(menuService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<MenuDTO> getOne(@PathVariable Integer id) {
		return new ResponseEntity<>(menuService.findById(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/addMenu", produces = MediaType.APPLICATION_JSON_VALUE)
	public MenuDTO post(@RequestBody MenuDTO newEntity) {
		return menuService.addMenu(newEntity);
	}
	
	@PostMapping(value = "/addItemToMenu")
	public void addItemToMenu(@RequestParam("menuId") Integer menuId, @RequestBody ItemDTO itemDto) {
		menuService.addItemToMenu(itemDto, menuId);
	}
	
	@GetMapping(value = "/getItemsInMenu/{menuId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<ItemDTO>> getItemsInMenu(@PathVariable Integer menuId) {
		return new ResponseEntity<>(menuService.getItemsInMenu(menuId), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/removeItemFromMenu")
	public void removeItemFromMenu(@RequestParam("itemId") Integer itemId, @RequestParam("menuId") Integer menuId) {
		menuService.removeItemFromMenu(itemId, menuId);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		menuService.deleteById(id);
	}

	//TODO dodaj search and stuff
}