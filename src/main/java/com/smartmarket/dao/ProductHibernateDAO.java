package com.smartmarket.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import com.smartmarket.dto.ProductIDDTO;
import com.smartmarket.entity.Product;
import com.smartmarket.hibernate.GenericHibernateDAO;

public class ProductHibernateDAO extends GenericHibernateDAO<Product> implements ProductDAO {
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Product> getAllProductsWithoutImage() {
		
		final String sqlTemplate = "from "+Product.class.getName()+" c where c.image is null";
		Query query = getSession().createQuery(sqlTemplate);
		List<Product> products = query.getResultList();
		return products;
		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ProductIDDTO> getAllProductsWithImage() {
		
		List<ProductIDDTO> products = new ArrayList<ProductIDDTO>();
		final String sqlTemplate = "select c.id, c.dateLastUpdate from "+Product.class.getName()+" c where c.image is not null";
		Query query = getSession().createQuery(sqlTemplate);
		List<Object[]> results = query.getResultList();
		for (Object[] row: results) {
			products.add(new ProductIDDTO((Long) row[0], (Date) row[1]));
		}
		return products;
	}	

}
