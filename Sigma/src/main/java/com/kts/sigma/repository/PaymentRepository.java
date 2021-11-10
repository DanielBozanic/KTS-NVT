package com.kts.sigma.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kts.sigma.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer>{

}
