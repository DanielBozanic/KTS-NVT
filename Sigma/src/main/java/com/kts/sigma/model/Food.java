package com.kts.sigma.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "FOOD")
public class Food extends Item {
	
	private FoodType type;

	public FoodType getType() {
		return type;
	}
	
	public void setType(FoodType type) {
		this.type = type;
	}
}