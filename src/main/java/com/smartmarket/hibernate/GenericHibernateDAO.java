package com.smartmarket.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.Session;

@SuppressWarnings("unchecked")
public class GenericHibernateDAO <T extends Serializable> implements GenericDAO<T> {
	
	private Session session;
	private Class<T> persistentClass;
	
	public GenericHibernateDAO() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];	
	}
	
	public void setSession(Session s) {
		
		this.session = s;
		this.session.getTransaction().begin();
		
	}
	
	protected Session getSession() {
		
		if (session == null) {
		    throw new IllegalStateException("Session has not been set on DAO before usage");
		}
		return session;
		
	}
	
	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	@Override
	public List<T> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T findById(Long id) {
		return this.findByName("id", id);
	}
	
	@Override
	public T findByName(String field, Object value) {
		
		try {
			
			final String sqlTemplate = "from "+persistentClass.getSimpleName()+" where "+field+" =:value";
			final TypedQuery<T> query = this.session.createQuery(sqlTemplate);
			query.setParameter("value", value);
			return query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		}
		
		
	}

	@Override
	public void persist(T entity) {
		this.session.persist(entity);
	}

	@Override
	public void update(T entity) {
		this.session.update(entity);	
	}

	@Override
	public void delete(Long id) {
		this.delete(findById(id));
	}

	@Override
	public void delete(T entity) {
		this.session.delete(entity);
		
	}

	@Override
	public void flush() {
		this.session.flush();
	}

	@Override
	public void clear() {
		this.session.clear();
	}

	@Override
	public void commit() {
		
		this.session.getTransaction().commit();
		this.session.close();
		
	}

	@Override
	public void closeSession() {
		this.session.close();
	}

}
