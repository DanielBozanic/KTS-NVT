package com.kts.sigma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kts.sigma.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer>{

	@Query("select p from Payment p where p.employee.id = ?1")
	Payment findByEmployeeId(Integer id);
}
