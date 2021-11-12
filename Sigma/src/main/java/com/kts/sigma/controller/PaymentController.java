package com.kts.sigma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.model.Payment;
import com.kts.sigma.service.PaymentService;

@RestController
public class PaymentController {
	@Autowired
	private PaymentService paymentService;
	
	@GetMapping(path="/payments")
	public Iterable<Payment> getAll(){
		return paymentService.getAll();
	}
	
	@GetMapping("/payments/{id}")
	Payment getOne(@PathVariable Integer id) {
		Payment result = paymentService.findById(id).orElse(null);
		if(result == null)
		{
			throw new ItemNotFoundException(id);
		}
	    return result;
	}
	
	@PostMapping("/payments")
	Payment post(@RequestBody Payment newEntity) {
	  return paymentService.save(newEntity);
	}
	
	@DeleteMapping("/payments/{id}")
	void delete(@PathVariable Integer id) {
		paymentService.deleteById(id);
	}
}