package com.smartmarket.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
	
	public static SessionFactory sessionFactory;
	
	public static SessionFactory getSessionFactory() {
		
		if (sessionFactory == null) {
			
			final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
			try {
				
				sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
				
			} catch (Throwable e) {
				
				StandardServiceRegistryBuilder.destroy(registry);
				throw new ExceptionInInitializerError(e);
				
			}
			return sessionFactory;
			
		} else {
			return sessionFactory;
		}

	}

}
