package com.kts.sigma.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ItemInOrder {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
    private ItemInOrderState state;
	
    private Integer quantity;

    //public Order order;

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public ItemInOrderState getState() {
		return state;
	}

	public void setState(ItemInOrderState state) {
		this.state = state;
	}

	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	/*
	 * public Order getOrder() { return order; }
	 * 
	 * public void setOrder(Order order) { this.order = order; }
	 */
}