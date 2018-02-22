package com.smartmarket.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.criterion.Restrictions;

import com.smartmarket.entity.User;
import com.smartmarket.hibernate.GenericHibernateDAO;

public class UserHibernateDAO extends GenericHibernateDAO<User> implements UserDAO {

	@SuppressWarnings("deprecation")
	@Override
	public boolean userExist(String str) {
		User user = (User) getSession().createCriteria(getPersistentClass()).add(Restrictions.eq("email", str)).uniqueResult();
		return user != null;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAllActiveUsers() {
		try {
			
			final String sqlTemplate = "from "+User.class.getSimpleName()+" where active = 1";
			final TypedQuery<User> query = this.getSession().createQuery(sqlTemplate);
			return query.getResultList();
			
		} catch (NoResultException e) {
			return null;
		}
		
		
	}
	
	

}
