package com.kts.sigma.model;

import java.math.BigDecimal;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Order {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
    private OrderState state;
	
    private BigDecimal totalPrice;
   
    public Waiter waiter;
    
    //public Table table;
    
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public OrderState getState() {
		return state;
	}

	public void setState(OrderState state) {
		this.state = state;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public Waiter getWaiter() {
		return waiter;
	}
	
	public void setWaiter(Waiter waiter) {
		this.waiter = waiter;
	}
	
	/*
	 * public Table getTable() { return table; } public void setTable(Table table) {
	 * this.table = table; }
	 */
}