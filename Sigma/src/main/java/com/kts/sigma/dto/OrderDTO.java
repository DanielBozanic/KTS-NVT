package com.kts.sigma.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import com.kts.sigma.model.OrderState;
import com.kts.sigma.model.RestaurantTable;
import com.kts.sigma.model.Waiter;

public class OrderDTO {

	private Integer id;

	private OrderState state;

	private BigDecimal totalPrice;

	public Waiter waiter;

	public RestaurantTable table;
	
	public Set<ItemDTO> items;

	public LocalDateTime orderDateTime;

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

	public RestaurantTable getTable() {
		return table;
	}

	public void setTable(RestaurantTable table) {
		this.table = table;
	}

	public Set<ItemDTO> getItems() {
		return items;
	}

	public void setItems(Set<ItemDTO> items) {
		this.items = items;
	}

	public LocalDateTime getOrderDateTime() {
		return orderDateTime;
	}

	public void setOrderDateTime(LocalDateTime orderDateTime) {
		this.orderDateTime = orderDateTime;
	}
}
