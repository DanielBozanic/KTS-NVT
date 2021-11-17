package com.kts.sigma.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kts.sigma.model.RestaurantTable;
import com.kts.sigma.model.TableState;

public interface TableRepository extends JpaRepository<RestaurantTable, Integer>{

	@Query("select rt from RestaurantTable rt where rt.zone.id = ?1")
	ArrayList<RestaurantTable> findByZoneId(Integer zoneId);
	
	@Query("select rt from RestaurantTable rt where rt.id = ?1 and rt.state = ?2")
	RestaurantTable getTableByIdAndState(Integer tableId, TableState state);
}