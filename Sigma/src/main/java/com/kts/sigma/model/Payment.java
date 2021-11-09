package com.kts.sigma.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Payment {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
    private BigDecimal payment;
    
    private LocalDateTime dateCreated;
    
    private LocalDateTime dateEnd;
    
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public BigDecimal getPayment() {
		return payment;
	}
	
	public void setPayment(BigDecimal payment) {
		this.payment = payment;
	}
	
	public LocalDateTime getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public LocalDateTime getDateEnd() {
		return dateEnd;
	}
	
	public void setDateEnd(LocalDateTime dateEnd) {
		this.dateEnd = dateEnd;
	}
}