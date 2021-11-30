package com.kts.sigma.model;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
		name = "USER_TYPE",
		discriminatorType = DiscriminatorType.STRING
)
@Table(name = "Users")
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
    private String name;
    
    public User() {

    }
    
    public User(Integer id, String name) {
    	this.id = id;
    	this.name = name;
    }
    
    public User(String name) {
    	this.name = name;
    }
    
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}