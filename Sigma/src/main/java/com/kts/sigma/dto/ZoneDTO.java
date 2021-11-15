package com.kts.sigma.dto;

import java.util.Set;

public class ZoneDTO {

	private Integer id;
	
    private String name;
    
    private Set<TableDTO> tables;

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

	public Set<TableDTO> getTables() {
		return tables;
	}

	public void setTables(Set<TableDTO> tables) {
		this.tables = tables;
	}
    
}
