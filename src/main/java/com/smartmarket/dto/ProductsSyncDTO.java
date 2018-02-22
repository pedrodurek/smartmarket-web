package com.smartmarket.dto;

import java.util.ArrayList;
import java.util.List;

import com.smartmarket.entity.Product;

public class ProductsSyncDTO {
	
	private List<Product> productsWithoutImage;
	private List<ProductIDDTO> productsWithImage;
	private List<ProductIDDTO> productsToBeRemoved;
	
	public ProductsSyncDTO() {}
	public ProductsSyncDTO(boolean createObjects) {
		
		if (createObjects) {
			
			this.productsWithImage = new ArrayList<ProductIDDTO>();
			this.productsWithoutImage = new ArrayList<Product>();
			this.productsToBeRemoved = new ArrayList<ProductIDDTO>();
			
		}
		
	}

	public List<Product> getProductsWithoutImage() {
		return productsWithoutImage;
	}

	public void setProductsWithoutImage(List<Product> productsWithoutImage) {
		this.productsWithoutImage = productsWithoutImage;
	}
	
	public void addProductsWithoutImage(Product product) {
		this.productsWithoutImage.add(product);
	}

	public List<ProductIDDTO> getProductsWithImage() {
		return productsWithImage;
	}

	public void setProductsWithImage(List<ProductIDDTO> productsWithImage) {
		this.productsWithImage = productsWithImage;
	}
	
	public void addProductsWithImage(ProductIDDTO product) {
		this.productsWithImage.add(product);
	}
	public List<ProductIDDTO> getProductsToBeRemoved() {
		return productsToBeRemoved;
	}
	public void setProductsToBeRemoved(List<ProductIDDTO> productsToBeRemoved) {
		this.productsToBeRemoved = productsToBeRemoved;
	}
	
	public void addProductsToBeRemoved(ProductIDDTO product) {
		this.productsToBeRemoved.add(product);
	}

}
