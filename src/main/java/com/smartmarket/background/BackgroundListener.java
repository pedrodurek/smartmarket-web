package com.smartmarket.background;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class BackgroundListener implements ServletContextListener {
	
	private BackgroundService backgroundService;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		backgroundService = new BackgroundService();
		Thread thread = new Thread(backgroundService);
		thread.start();
		
	}
	

}