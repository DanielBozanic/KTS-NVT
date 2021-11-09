package com.kts.sigma.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "BARTENDER")
public class Bartender extends Employee {
	
	//public Set<ItemInOrder> itemInOrder;
}