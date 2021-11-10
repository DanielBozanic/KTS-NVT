package com.kts.sigma.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kts.sigma.model.RestaurantOrder;

public interface OrderRepository extends JpaRepository<RestaurantOrder, Integer>{

}
