package com.kts.sigma.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kts.sigma.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	Employee findByCode(Integer code);
}
