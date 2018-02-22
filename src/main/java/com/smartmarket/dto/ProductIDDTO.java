package com.smartmarket.dto;

import java.util.Date;

public class ProductIDDTO {
	
	public ProductIDDTO() {}
	public ProductIDDTO(Long id, Date row) {
		this.id = id;
		this.dateLastUpdate = row;
	}
	
	private Long id;
	private Date dateLastUpdate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateLastUpdate() {
		return dateLastUpdate;
	}

	public void setDateLastUpdate(Date dateLastUpdate) {
		this.dateLastUpdate = dateLastUpdate;
	}

}
