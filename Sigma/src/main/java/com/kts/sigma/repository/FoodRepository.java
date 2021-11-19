package com.kts.sigma.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kts.sigma.model.Food;
import com.kts.sigma.model.FoodType;

public interface FoodRepository extends JpaRepository<Food, Integer> {

	@Query("select f from Food f where f.type = ?1")
	ArrayList<Food> getItemsByFoodType(FoodType foodType);
}
