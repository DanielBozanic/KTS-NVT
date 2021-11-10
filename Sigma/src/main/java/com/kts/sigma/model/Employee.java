package com.kts.sigma.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Employee extends User {

	private Integer code;

	private LocalDateTime dateOfEmployment;

	private Boolean active;

	@JsonIgnore
	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE, CascadeType.DETACH,
			CascadeType.MERGE, CascadeType.REFRESH })
	public Set<Payment> payment;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public LocalDateTime getDateOfEmployment() {
		return dateOfEmployment;
	}

	public void setDateOfEmployment(LocalDateTime dateOfEmployment) {
		this.dateOfEmployment = dateOfEmployment;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Set<Payment> getPayment() {
		return payment;
	}

	public void setPayment(Set<Payment> payment) {
		this.payment = payment;
	}

}