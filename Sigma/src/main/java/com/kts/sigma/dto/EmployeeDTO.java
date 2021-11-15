package com.kts.sigma.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EmployeeDTO {

	private Integer id, code;
	
	private String name, type;
	
	private LocalDateTime dateOfEmployment;
	
	private boolean active;
	
	private BigDecimal payment;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getPayment() {
		return payment;
	}

	public void setPayment(BigDecimal payment) {
		this.payment = payment;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getDateOfEmployment() {
		return dateOfEmployment;
	}

	public void setDateOfEmployment(LocalDateTime dateOfEmployment) {
		this.dateOfEmployment = dateOfEmployment;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}
