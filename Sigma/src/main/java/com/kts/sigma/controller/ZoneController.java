package com.kts.sigma.controller;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kts.sigma.service.ZoneService;
import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.dto.TableDTO;
import com.kts.sigma.dto.ZoneDTO;
import com.kts.sigma.model.Zone;

@RestController
@RequestMapping("/zones")
public class ZoneController {
	@Autowired
	private ZoneService zoneService;
	
	@GetMapping(path="")
	public Iterable<ZoneDTO> getAll(){
		return zoneService.getAll();
	}
	
	@GetMapping(path="tables/{id}")
	public Iterable<TableDTO> getTables(@PathVariable Integer id){
		return zoneService.getTables(id);
	}
	
	@GetMapping("/{id}")
	ZoneDTO getOne(@PathVariable Integer id) {
		return zoneService.findById(id);
	}
	
	@PostMapping("")
	Zone post(@RequestBody Zone newEntity) {
	  return zoneService.save(newEntity);
	}
	
	@DeleteMapping("/{id}")
	void delete(@PathVariable Integer id) {
		zoneService.deleteById(id);
	}
	
	
}