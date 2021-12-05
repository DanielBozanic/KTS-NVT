package com.kts.sigma.dto;

import java.math.BigDecimal;

import com.kts.sigma.model.FoodType;
import com.kts.sigma.model.Item;

public class ItemDTO {

	private Integer id;
	
    private String name;
    
    private String description;
    
    private BigDecimal buyingPrice;
    
    private BigDecimal sellingPrice;
    
    private String image;
    
    private boolean food;
    
    private FoodType type;

	public ItemDTO(Integer id, String name, String description, BigDecimal buyingPrice, BigDecimal sellingPrice, boolean food) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.buyingPrice = buyingPrice;
		this.sellingPrice = sellingPrice;
		this.food = food;
	}

	public ItemDTO(){}

	public BigDecimal getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(BigDecimal sellingPrice) {
		this.sellingPrice = sellingPrice;
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

	public boolean isFood() {
		return food;
	}

	public void setFood(boolean food) {
		this.food = food;
	}

	public FoodType getType() {
		return type;
	}

	public void setType(FoodType type) {
		this.type = type;
	}
    
    
}
