package com.smartmarket.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.smartmarket.hibernate.EntityGenerator;

@Entity
@Table(name="PRODUCT")
public class Product extends EntityGenerator {

	private static final long serialVersionUID = 1L;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="PRICE")
	private float price;
	
	@Column(name="PROVIDER")
	private String provider;
	
	@Column(name="WEIGHT")
	private String weight;
	
	@Column(name="IMAGE")
	private String image;
	
	@Column(name="FLG_ACTIVE")
	private boolean active;
	
	@Column(name="DT_LAST_UPDATE")
	private Date dateLastUpdate;
	
	public Product() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getDateLastUpdate() {
		return dateLastUpdate;
	}

	public void setDateLastUpdate(Date dateLastUpdate) {
		this.dateLastUpdate = dateLastUpdate;
	}

}
