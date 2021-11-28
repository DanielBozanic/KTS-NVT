package com.kts.sigma.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kts.sigma.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>, PagingAndSortingRepository<Employee, Integer> {

	Employee findByCode(Integer code);
	
	@Query("select max(e.code) from Employee e")
	Integer findMaxCode();
	
	Page<Employee> findAll(Pageable pageable);
	
	@Query("select count(e) from Employee e where e.active = true")
	Integer getNumberOfActiveEmployeeRecords();
}
