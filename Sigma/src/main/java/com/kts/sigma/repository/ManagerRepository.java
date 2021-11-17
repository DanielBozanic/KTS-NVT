package com.kts.sigma.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kts.sigma.model.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {

	Manager findByUsername(String username);
}
