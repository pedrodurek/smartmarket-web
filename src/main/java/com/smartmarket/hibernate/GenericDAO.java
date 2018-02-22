package com.smartmarket.hibernate;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO <T extends Serializable> {

	public List<T> findAll();
	public T findById(Long id);
	public T findByName(String field, Object value);
	public void persist(T entity);
	public void update(T entity);
	public void delete(Long id);
	public void delete(T entity);
	public void flush();
	public void clear();
	public void commit();
	public void closeSession();
	
}
