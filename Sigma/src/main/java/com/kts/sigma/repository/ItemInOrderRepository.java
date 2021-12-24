package com.kts.sigma.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kts.sigma.model.Item;
import com.kts.sigma.model.ItemInOrder;

public interface ItemInOrderRepository extends JpaRepository<ItemInOrder, Integer>{
	//@Query("select i from ItemInOrder i where i.order_id=?1")
	ArrayList<ItemInOrder> getByOrderId(Integer id);
}
