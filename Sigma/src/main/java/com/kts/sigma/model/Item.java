package com.kts.sigma.model;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
		name = "ITEM_TYPE",
		discriminatorType = DiscriminatorType.STRING
)
public class Item {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
    private String name;
    
    private String description;
    
    private BigDecimal buyingPrice;
    
    private String image;

	public Item(Integer id, String name, String description, BigDecimal buyingPrice) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.buyingPrice = buyingPrice;
	}
    
	public Item() {
		super();
	}
    
	public Item(Integer id, String name, String description, BigDecimal buyingPrice, String image) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.buyingPrice = buyingPrice;
		this.image = image;
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
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public BigDecimal getBuyingPrice() {
		return buyingPrice;
	}
	
	public void setBuyingPrice(BigDecimal buyingPrice) {
		this.buyingPrice = buyingPrice;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
}