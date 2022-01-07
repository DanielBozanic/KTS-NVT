package com.kts.sigma.repository;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kts.sigma.model.RestaurantTable;
import com.kts.sigma.model.TableState;

public interface TableRepository extends JpaRepository<RestaurantTable, Integer>, PagingAndSortingRepository<RestaurantTable, Integer>{

	@Query("select rt from RestaurantTable rt where rt.zone.id = ?1")
	ArrayList<RestaurantTable> findByZoneId(Integer zoneId);
	
	@Query("select rt from RestaurantTable rt where rt.zone.id = ?1")
	Page<RestaurantTable> findByZoneIdAndCurrentPage(Integer zoneId, Pageable pageable);
	
	@Query("select rt from RestaurantTable rt where rt.id = ?1 and rt.state = ?2")
	RestaurantTable getTableByIdAndState(Integer tableId, TableState state);
	
	@Query("select rt from RestaurantTable rt where rt.tableNumber = ?1")
	RestaurantTable findByTableNumber(Integer tableNumber);
	
	@Query("select max(rt.tableNumber) from RestaurantTable rt")
	Integer findMaxTableNumber();
	
	@Query("select count(rt) from RestaurantTable rt where rt.zone.id = ?1")
	Integer getNumberOfTablesForZoneRecords(Integer zoneId);
}
