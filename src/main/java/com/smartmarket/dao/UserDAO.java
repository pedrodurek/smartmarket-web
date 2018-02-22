package com.smartmarket.dao;

import java.util.List;

import com.smartmarket.entity.User;
import com.smartmarket.hibernate.GenericDAO;

public interface UserDAO extends GenericDAO<User> {
	
	public boolean userExist(String str);
	public List<User> findAllActiveUsers();

}
