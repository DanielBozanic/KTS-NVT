package com.kts.sigma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kts.sigma.model.RestaurantOrder;

public interface OrderRepository extends JpaRepository<RestaurantOrder, Integer>{

	@Query("select ro from RestaurantOrder ro where ro.table.id = ?1")
	List<RestaurantOrder> findByTableId(Integer tableId);
}
