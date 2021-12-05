package com.kts.sigma.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ItemInOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private ItemInOrderState state;

	@ManyToOne
	private RestaurantOrder order;
	
	@ManyToOne
	private ItemInMenu item;
	
	@ManyToOne
	private Employee employee;

	public ItemInOrder() {
		super();
	}
	
	public ItemInOrder(Integer id, ItemInMenu item) {
		super();
		this.id = id;
		this.item = item;
	}

	public ItemInMenu getItem() {
		return item;
	}

	public void setItem(ItemInMenu item) {
		this.item = item;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

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

	public RestaurantOrder getOrder() {
		return order;
	}

	public void setOrder(RestaurantOrder order) {
		this.order = order;
	}

}