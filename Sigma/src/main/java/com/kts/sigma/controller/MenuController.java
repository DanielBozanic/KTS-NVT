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
import com.kts.sigma.dto.MenuDTO;
import com.kts.sigma.service.MenuService;

@RestController
@RequestMapping("/menu")
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	
	@GetMapping(path="")
	public ResponseEntity<List<MenuDTO>> getAll(){
		return new ResponseEntity<>(menuService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getActiveNonExpiredMenus", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MenuDTO>> getActiveNonExpiredMenus(){
		return new ResponseEntity<>(menuService.getActiveNonExpiredMenus(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<MenuDTO> getOne(@PathVariable Integer id) {
		return new ResponseEntity<>(menuService.findById(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/addMenu", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MenuDTO> post(@RequestBody MenuDTO newEntity) {
		return new ResponseEntity<>(menuService.addMenu(newEntity), HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/addItemToMenu")
	public void addItemToMenu(@RequestParam("menuId") Integer menuId, @RequestBody ItemDTO itemDto) {
		menuService.addItemToMenu(itemDto, menuId);
	}
	
	@GetMapping(value = "/getItemsInMenu/{menuId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<ItemDTO>> getItemsInMenu(@PathVariable Integer menuId) {
		return new ResponseEntity<>(menuService.getItemsInMenu(menuId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getItemsInMenuByCurrentPage/{menuId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ItemDTO>> getItemsInMenuByCurrentPage(@PathVariable Integer menuId, @RequestParam("currentPage") Integer currentPage, 
			@RequestParam("pageSize") Integer pageSize) {
		return new ResponseEntity<>(menuService.getItemsInMenuByCurrentPage(menuId, currentPage, pageSize), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getNumberOfActiveItemInMenuRecordsByMenuId/{menuId}")
	public ResponseEntity<Integer> getNumberOfActiveItemInMenuRecordsByMenuId(@PathVariable Integer menuId) {
		return new ResponseEntity<>(menuService.getNumberOfActiveItemInMenuRecordsByMenuId(menuId), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/removeItemFromMenu")
	public void removeItemFromMenu(@RequestParam("itemId") Integer itemId, @RequestParam("menuId") Integer menuId) {
		menuService.removeItemFromMenu(itemId, menuId);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		menuService.deleteMenuById(id);
	}
}