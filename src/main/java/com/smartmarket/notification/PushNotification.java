package com.smartmarket.notification;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

public class PushNotification {
	
	private static String DEVICE_TOKEN = "d2ec7ca6b8b39bcb5245c66f842d31c47c700b5e57e7eca8ff9a6fc9db3f56eb";
    private static String PATH_TO_P12_CERT = "/Users/pedrodurek/Desktop/Projetos/TCC/Mobile/Certificates.p12";
    private static String CERT_PASSWORD = "moonha3014";
	
	public void sendNotification(String title, String message, String deviceToken) {
		
		ApnsService service = APNS.newService().withCert(PATH_TO_P12_CERT, CERT_PASSWORD).withSandboxDestination().build();
        String payload = APNS.newPayload().alertTitle(title).alertBody(message).sound("default").build();

        service.push(DEVICE_TOKEN, payload);
	}
	

}
