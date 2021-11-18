package com.kts.sigma.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kts.sigma.model.ItemInOrder;
import com.kts.sigma.model.Manager;

public interface ItemInOrderRepository extends JpaRepository<ItemInOrder, Integer>{
	
}
