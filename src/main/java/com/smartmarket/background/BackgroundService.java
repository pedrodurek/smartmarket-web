package com.smartmarket.background;

import java.util.List;

import com.smartmarket.dao.UserDAO;
import com.smartmarket.entity.User;
import com.smartmarket.hibernate.FactoryDAO;
import com.smartmarket.machinelearning.ImageClassifier;
import com.smartmarket.notification.PushNotification;


public class BackgroundService implements Runnable {
	
	public static boolean startTraning;
	public static boolean repositorMode;
	
	@Override
	public void run() {
		
		repositorMode = true;
		while (true) {
			
			try {
				Thread.sleep(100); // Avoid bugs
			} catch (InterruptedException e) {}
			
			if (startTraning) {
				
				System.out.println("Teste");
				ImageClassifier imageClassifier = new ImageClassifier();
				imageClassifier.trainImages();
				PushNotification push = new PushNotification();
				FactoryDAO factory = new FactoryDAO();
				
				// Create DAO for getting the active users
				UserDAO userDAO = factory.getUserDAO();
				List<User> users = userDAO.findAllActiveUsers();
				for (User user: users) {
					push.sendNotification("Treinamento", "Treinamento Concluído!", user.getDeviceToken());
				}
				startTraning = false;
				userDAO.closeSession();
				
			}
			
		}
		
	}

}
