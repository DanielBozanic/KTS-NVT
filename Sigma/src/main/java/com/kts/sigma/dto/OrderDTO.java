package com.kts.sigma.dto;

import java.math.BigDecimal;
import java.util.List;

import com.kts.sigma.model.OrderState;

public class OrderDTO {

	private Integer id;

	private OrderState state;

	private BigDecimal totalPrice;

	public Integer waiterId;

	public Integer tableId;
	
	public List<ItemInOrderDTO> items;

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

	public Integer getWaiterId() {
		return waiterId;
	}

	public void setWaiterId(Integer waiterId) {
		this.waiterId = waiterId;
	}

	public Integer getTableId() {
		return tableId;
	}

	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}

	public List<ItemInOrderDTO> getItems() {
		return items;
	}

	public void setItems(List<ItemInOrderDTO> items) {
		this.items = items;
	}

}
