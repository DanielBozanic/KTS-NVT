package com.kts.sigma.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "MANAGER")
public class Manager extends User {
	
    private String username;
    
    private String password;
    
    public Manager() {
    	
    }
    
    public Manager(Integer id, String name, String username, String password) {
    	super(id, name);
    	this.username = username;
    	this.password = password;
    }
    
    public Manager(String name, String username, String password) {
    	super(name);
    	this.username = username;
    	this.password = password;
    }
    
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}