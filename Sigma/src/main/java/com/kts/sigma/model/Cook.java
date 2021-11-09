package com.kts.sigma.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "COOK")
public class Cook extends Employee {
   //public java.util.Collection<ItemInOrder> itemInOrder;
}