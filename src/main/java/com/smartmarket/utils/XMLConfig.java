package com.smartmarket.utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public class XMLConfig {
	
	private static XMLConfiguration xmlConfig;
	
	public static int getConfigInt(XMLType type) {
		return Integer.parseInt(getConfig(type));
	}
	
	public static String getConfig(XMLType type) {
		
		if (xmlConfig == null) {
			
			try {
				xmlConfig = new XMLConfiguration("config.xml");
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
			
		}
		
		try {
			return xmlConfig.getString(type.getParam());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	

}
