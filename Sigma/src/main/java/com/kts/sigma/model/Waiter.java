package com.kts.sigma.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "WAITER")
public class Waiter extends Employee {
}