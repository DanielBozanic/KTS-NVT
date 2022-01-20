package com.kts.sigma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
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
import com.kts.sigma.dto.OrderDTO;
import com.kts.sigma.model.ItemInOrder;
import com.kts.sigma.model.OrderState;
import com.kts.sigma.model.RestaurantOrder;
import com.kts.sigma.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@GetMapping(path="")
	public Iterable<OrderDTO> getAll(){
		return orderService.getAll();
	}
	
	@GetMapping(path="allFoodOrders")
	public Iterable<OrderDTO> getAllFoodOrders(){
		return orderService.getAllFoodOrders();
	}
	
	@GetMapping(path="allDrinkOrders")
	public Iterable<OrderDTO> getAllDrinkOrders(){
		return orderService.getAllDrinkOrders();
	}
	
	@GetMapping("/{id}")
	OrderDTO getOne(@PathVariable Integer id) {
		return orderService.findById(id);
	}
	
	@GetMapping("/items/{id}")
	Iterable<ItemInOrderDTO> getAllItems(@PathVariable Integer id) {
		return orderService.getAllItems(id);
	}
	
	@GetMapping("/drinks/{id}")
	Iterable<ItemInOrderDTO> getAllDrinks(@PathVariable Integer id) {
		return orderService.getAllDrinks(id);
	}
	
	@GetMapping("/foods/{id}")
	Iterable<ItemInOrderDTO> getAllFoods(@PathVariable Integer id) {
		return orderService.getAllFoods(id);
	}
	
	@PostMapping("/{code}")
	RestaurantOrder post(@RequestBody OrderDTO newEntity, @PathVariable Integer code) {
	  return orderService.save(newEntity, code);
	}
	
	@MessageMapping({"/order-creation/{code}"})
	@SendTo("/restaurant/order")
	NotificationDTO postNotification(@RequestBody OrderDTO newEntity, @DestinationVariable Integer code) {
	  RestaurantOrder order = orderService.save(newEntity, code);
	  
	  NotificationDTO dto = new NotificationDTO();
	  dto.setCode("200");
	  dto.setId(order.getId());
	  dto.setSuccess(true);
	  dto.setMessage("New order for table" + order.getTable().getId());
	  
	  return dto;
	}
	
	 @MessageExceptionHandler
	 @SendTo("/restaurant/order")
	 NotificationDTO handleException(RuntimeException exception) {
	  NotificationDTO dto = new NotificationDTO();
	  dto.setCode("400");
	  dto.setSuccess(false);
	  dto.setMessage(exception.getLocalizedMessage());
	  return dto;
	}
	
	@PutMapping("/{orderId}/{code}")
	ItemInOrderDTO addItemToOrder(@RequestBody ItemInOrderDTO item, @PathVariable Integer code, @PathVariable Integer orderId) {
	  return orderService.addItemToOrder(item, code, orderId);
	}
	
	@PutMapping("/changeWithoutCode/{orderId}/{state}")
	void changeStateWithoutCode(@PathVariable OrderState state, @PathVariable Integer orderId) {
	  orderService.changeStateWithoutCode(state, orderId);
	}
	
	@PutMapping("/{orderId}/{state}/{code}")
	void changeState(@PathVariable OrderState state, @PathVariable Integer code, @PathVariable Integer orderId) {
	  orderService.changeState(state, code, orderId);
	}
	
	@DeleteMapping("/{orderId}/{itemId}/{code}")
	void removeItemFromOrder(@PathVariable Integer itemId, @PathVariable Integer code, @PathVariable Integer orderId) {
	  orderService.removeItemFromOrder(itemId, code, orderId);
	}
	
	@DeleteMapping("/{id}/{code}")
	void delete(@PathVariable Integer id, @PathVariable Integer code) {
		orderService.deleteById(id, code);
	}
}