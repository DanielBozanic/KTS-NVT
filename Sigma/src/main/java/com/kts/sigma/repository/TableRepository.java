package com.kts.sigma.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kts.sigma.model.RestaurantTable;

public interface TableRepository extends JpaRepository<RestaurantTable, Integer>{

}
