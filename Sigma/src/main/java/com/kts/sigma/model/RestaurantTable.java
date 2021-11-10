package com.kts.sigma.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class RestaurantTable {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
    private Integer numberOfChairs;
    
    private Integer tableNumber;
    
    private TableState state;
   
    @ManyToOne
    public Zone zone;

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
	
	
	 public Zone getZone() { return zone; }
	  
	 public void setZone(Zone zone) { this.zone = zone; }
	 
}