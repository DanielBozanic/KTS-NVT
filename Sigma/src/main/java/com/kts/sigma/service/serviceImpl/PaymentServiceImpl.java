package com.kts.sigma.service.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kts.sigma.model.Payment;
import com.kts.sigma.repository.PaymentRepository;
import com.kts.sigma.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService{
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Override
	public Iterable<Payment> getAll() {
		return paymentRepository.findAll();
	}
	
	@Override
	public Payment save(Payment item) {
		return paymentRepository.save(item);
	}
	
	@Override
	public void deleteById(Integer id) {
		paymentRepository.deleteById(id);
	}
	@Override
	public Optional<Payment> findById(Integer id)
	{
		return paymentRepository.findById(id);
	}
}
