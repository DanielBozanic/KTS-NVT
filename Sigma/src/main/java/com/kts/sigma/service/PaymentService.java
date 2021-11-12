package com.kts.sigma.service;

import java.util.Optional;

import com.kts.sigma.model.Payment;

public interface PaymentService {
	public Iterable<Payment> getAll();
	
	public Optional<Payment> findById(Integer id);
	
	public Payment save(Payment item);
	
	public void deleteById(Integer id);
}
