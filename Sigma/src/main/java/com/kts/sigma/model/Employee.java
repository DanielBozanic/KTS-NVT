package com.kts.sigma.model;


import java.time.LocalDateTime;
import javax.persistence.Entity;

@Entity
public class Employee extends User {
	
   private Integer code;
   
   private LocalDateTime dateOfEmployment;
   
   private Boolean active;
   
   //public java.util.Collection<Payment> payment;
}