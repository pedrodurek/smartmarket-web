package com.smartmarket.authentication;

import com.smartmarket.dao.UserDAO;
import com.smartmarket.entity.User;
import com.smartmarket.hibernate.FactoryDAO;
import com.smartmarket.utils.SecurityUtils;

public class Auth {
	
	public User validateLogin(String email, String password, String deviceToken) {
		
		// Create DAO to check login
		FactoryDAO factory = new FactoryDAO();
		UserDAO userDAO = factory.getUserDAO();
		User user = userDAO.findByName("email", email);
		System.out.println(deviceToken);
		user = (user.getPassword().equals(SecurityUtils.generateHash(password)))?user:null;
		if (user != null) {
			
			user.setDeviceToken(deviceToken);
			userDAO.update(user);
			userDAO.commit();
			
		}
		userDAO.closeSession();
		return user;
		
	}

}
