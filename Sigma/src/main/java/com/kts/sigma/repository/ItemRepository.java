package com.kts.sigma.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kts.sigma.model.Item;

public interface ItemRepository extends JpaRepository<Item, Integer>, PagingAndSortingRepository<Item, Integer> {

	@Query("select i from Item i where i.name like concat('%',?1,'%')")
	ArrayList<Item> search(String searchTerm);
}
