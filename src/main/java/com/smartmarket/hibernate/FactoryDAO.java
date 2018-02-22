package com.smartmarket.hibernate;

import com.smartmarket.dao.ProductDAO;
import com.smartmarket.dao.ProductHibernateDAO;
import com.smartmarket.dao.UserDAO;
import com.smartmarket.dao.UserHibernateDAO;

public class FactoryDAO {
	
	private GenericHibernateDAO<?> getInstance(Class<?> daoClass) {
		
		try {
			
            GenericHibernateDAO<?> dao = (GenericHibernateDAO<?>) daoClass.newInstance();
            dao.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
            return dao;
            
        } catch (Exception ex) {
            throw new RuntimeException("Can not instantiate DAO: " + daoClass, ex);
        }
		
		
	}
	
	public UserDAO getUserDAO() {
		return (UserDAO) getInstance(UserHibernateDAO.class);
	}
	
	public ProductDAO getProductDAO() {
		return (ProductDAO) getInstance(ProductHibernateDAO.class);
	}

}
