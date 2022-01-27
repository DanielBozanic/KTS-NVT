package com.kts.sigma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kts.sigma.dto.TableDTO;
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
	public TableDTO getOne(@PathVariable Integer id) {
		return tableService.findById(id);
	}
	
	@PostMapping(value = "/addTable", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TableDTO> addTable(@RequestBody TableDTO newEntity) {
		return new ResponseEntity<>(tableService.addTable(newEntity), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}/{state}/{code}")
	public void changeState(@PathVariable Integer id, @PathVariable TableState state, @PathVariable Integer code) {
		tableService.changeState(id, state, code);
	}
	
	@PutMapping(value = "/updateTablePosition", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TableDTO> updateTablePosition(@RequestBody TableDTO tableDto) {
		return new ResponseEntity<>(tableService.updateTablePosition(tableDto), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		tableService.deleteById(id);
	}
}