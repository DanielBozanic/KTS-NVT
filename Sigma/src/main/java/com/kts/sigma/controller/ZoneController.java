package com.kts.sigma.controller;
import java.util.List;

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

import com.kts.sigma.service.ZoneService;
import com.kts.sigma.dto.TableDTO;
import com.kts.sigma.dto.ZoneDTO;

@RestController
@RequestMapping("/zones")
public class ZoneController {
	
	@Autowired
	private ZoneService zoneService;
	
	@GetMapping(value = "/getAll")
	public List<ZoneDTO> getAll(){
		return zoneService.getAll();
	}
	
	@GetMapping(path="tables/{id}")
	public List<TableDTO> getTables(@PathVariable Integer id){
		return zoneService.getTables(id);
	}

	@GetMapping("/{id}")
	public ZoneDTO getOne(@PathVariable Integer id) {
		return zoneService.findById(id);
	}
	
	@PostMapping(value = "/createNewZone", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ZoneDTO> createNewZone(@RequestBody ZoneDTO newZone) {
		return new ResponseEntity<>(zoneService.createNewZone(newZone), HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/updateNumberChairs")
	public ResponseEntity<TableDTO> updateNumberChairs(@RequestBody TableDTO tableDto) {
		return new ResponseEntity<>(zoneService.updateNumberChairs(tableDto), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/removeTableFromZone")
	public void removeTableFromZone(@RequestBody TableDTO tableDto) {
		zoneService.removeTableFromZone(tableDto);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		zoneService.deleteById(id);
	}
}