package com.smartmarket.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtils {
	
	public static final String HASH_KEY = "$%74839@#!";
	
	public static String generateHash(String password) {
		
		try {
			
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
			String passWithSalt = password + HASH_KEY;
			 byte[] passBytes = passWithSalt.getBytes();
			 byte[] passHash = sha256.digest(passBytes);
			 StringBuilder sb = new StringBuilder();
			 for (int i = 0; i < passHash.length; i++) {
				 sb.append(Integer.toString((passHash[i] & 0xff) + 0x100, 16).substring(1));         
		     }
			return sb.toString();
			
		} catch (NoSuchAlgorithmException e) { 
			e.printStackTrace(); 
		}
		return null;
		
	}

}
