package com.kts.sigma.repository;

import java.util.List;

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
	
	@Query("select e from Employee e where e.id = ?1 and e.active = true")
	Employee getActiveEmployeeById(Integer id);
	
	@Query("select e from Employee e where e.active = true")
	List<Employee> findAllActiveEmployees();
	
	@Query("select e from Employee e where e.active = true")
	Page<Employee> findAllActiveEmployeesByCurrentPage(Pageable pageable);
	
	@Query("select count(e) from Employee e where e.active = true")
	Integer getNumberOfActiveEmployeeRecords();
}
