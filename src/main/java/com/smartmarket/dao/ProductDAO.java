package com.smartmarket.dao;

import java.util.List;

import com.smartmarket.dto.ProductIDDTO;
import com.smartmarket.entity.Product;
import com.smartmarket.hibernate.GenericDAO;

public interface ProductDAO extends GenericDAO<Product> {
	
	public List<Product> getAllProductsWithoutImage();
	public List<ProductIDDTO> getAllProductsWithImage();

}
