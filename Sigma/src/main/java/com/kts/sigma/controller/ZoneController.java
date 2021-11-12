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
import com.kts.sigma.model.Zone;

@RestController
public class ZoneController {
	@Autowired
	private ZoneService zoneService;
	
	@GetMapping(path="/zones")
	public Iterable<Zone> getAll(){
		return zoneService.getAll();
	}
	
	@GetMapping("/zones/{id}")
	Zone getOne(@PathVariable Integer id) {
		Zone result = zoneService.findById(id).orElse(null);
		if(result == null)
		{
			throw new ItemNotFoundException(id);
		}
	    return result;
	}
	
	@PostMapping("/zones")
	Zone post(@RequestBody Zone newEntity) {
	  return zoneService.save(newEntity);
	}
	
	@DeleteMapping("/zones/{id}")
	void delete(@PathVariable Integer id) {
		zoneService.deleteById(id);
	}
}