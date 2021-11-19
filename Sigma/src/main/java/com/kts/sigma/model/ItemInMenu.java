package com.kts.sigma.model;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ItemInMenu {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
    private BigDecimal sellingPrice;
   
    @ManyToOne
    public Item item;
    
    @ManyToOne
    public Menu menu;
    
    private Boolean active;
  
    @JsonIgnore
    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private Set<ItemInOrder> items;
    
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

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Set<ItemInOrder> getItems() {
		return items;
	}

	public void setItems(Set<ItemInOrder> itemInOrder) {
		this.items = itemInOrder;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}