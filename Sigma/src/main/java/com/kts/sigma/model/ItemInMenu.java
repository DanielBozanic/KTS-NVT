package com.kts.sigma.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ItemInMenu {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
    private BigDecimal sellingPrice;
   
    //public Item item;
    
    //public java.util.Collection<Menu> menu;
    
    //public ItemInOrder itemInOrder;
    
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public BigDecimal getSellingPrice() {
		return sellingPrice;
	}
	
	public void setSellingPrice(BigDecimal sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	/*
	 * public Item getItem() { return item; } public void setItem(Item item) {
	 * this.item = item; }
	 */

	/*
	 * public java.util.Collection<Menu> getMenu() { return menu; } public void
	 * setMenu(java.util.Collection<Menu> menu) { this.menu = menu; }
	 */
	/*
	 * public ItemInOrder getItemInOrder() { return itemInOrder; } public void
	 * setItemInOrder(ItemInOrder itemInOrder) { this.itemInOrder = itemInOrder; }
	 */
}