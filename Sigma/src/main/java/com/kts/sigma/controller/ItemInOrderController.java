package com.kts.sigma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kts.sigma.dto.ItemInOrderDTO;
import com.kts.sigma.dto.NotificationDTO;
import com.kts.sigma.model.ItemInOrder;
import com.kts.sigma.model.ItemInOrderState;
import com.kts.sigma.service.ItemInOrderService;

@RestController
@RequestMapping("/item-in-order")
public class ItemInOrderController {
	@Autowired
	private ItemInOrderService itemInOrderService;
	
	@GetMapping(path="")
	public Iterable<ItemInOrderDTO> getAll(){
		return itemInOrderService.getAll();
	}
	
	@GetMapping("/{id}")
	ItemInOrderDTO getOne(@PathVariable Integer id) {
		return itemInOrderService.findById(id);
	}
	
	@PostMapping("/{code}")
	ItemInOrder post(@RequestBody ItemInOrderDTO newEntity, @PathVariable Integer code) {
	  return itemInOrderService.save(newEntity, code);
	}
	
	@PutMapping("/{code}")
	public void put(@RequestBody ItemInOrderDTO item, @PathVariable Integer code) {
		itemInOrderService.put(item, code);
	}
	
	@MessageMapping({"/item-creation/{code}"})
	@SendTo("/restaurant/item-creation")
	public NotificationDTO putNotification(@RequestBody ItemInOrderDTO item, @PathVariable Integer code) {
		itemInOrderService.put(item, code);
		
		NotificationDTO dto = new NotificationDTO();
		dto.setCode("200");
		dto.setId(item.getId());
		dto.setSuccess(true);
		dto.setMessage(item.getName() + " has been added to order.");
		
		return dto;
	}
	
	@PutMapping("/{id}/{state}/{code}")
	public ItemInOrderDTO changeState(@PathVariable Integer id, @PathVariable ItemInOrderState state, @PathVariable Integer code) {
		return itemInOrderService.changeState(id, state, code);
	}
	
	@MessageMapping({"/item-change/{id}/{state}/{code}"})
	@SendTo("/restaurant/item-change")
	public NotificationDTO changeStateNotification(@PathVariable Integer id, @PathVariable ItemInOrderState state, @PathVariable Integer code) {
		ItemInOrderDTO item = itemInOrderService.changeState(id, state, code);
		
		NotificationDTO dto = new NotificationDTO();
		dto.setCode("200");
		dto.setId(item.getId());
		dto.setSuccess(true);
		dto.setMessage(item.getName() + " has changed to " + state.toString() + ".");
		
		return dto;
	}
	
	@DeleteMapping("/{id}")
	void delete(@PathVariable Integer id) {
		itemInOrderService.deleteById(id);
	}
}