package com.kts.sigma.dto;


import com.kts.sigma.model.TableState;
import com.kts.sigma.model.Zone;

public class TableDTO {

	private Integer id;
	
    private Integer numberOfChairs;
    
    private Integer tableNumber;
    
    private Integer zoneId;
    
    private Integer orderId;
    
    private TableState state;
    
    private Integer x;
    
    private Integer y;

	public Integer getZoneId() {
		return zoneId;
	}

	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumberOfChairs() {
		return numberOfChairs;
	}

	public void setNumberOfChairs(Integer numberOfChairs) {
		this.numberOfChairs = numberOfChairs;
	}

	public Integer getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(Integer tableNumber) {
		this.tableNumber = tableNumber;
	}

	public TableState getState() {
		return state;
	}

	public void setState(TableState state) {
		this.state = state;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}
	
}
