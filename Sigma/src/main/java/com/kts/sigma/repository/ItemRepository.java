package com.kts.sigma.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kts.sigma.model.Item;

public interface ItemRepository extends JpaRepository<Item, Integer>{

}
