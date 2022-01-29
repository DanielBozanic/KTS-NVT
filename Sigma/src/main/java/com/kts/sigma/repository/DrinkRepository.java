package com.kts.sigma.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kts.sigma.model.Drink;

public interface DrinkRepository extends JpaRepository<Drink, Integer> {

}
