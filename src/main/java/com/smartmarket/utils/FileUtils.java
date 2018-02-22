package com.smartmarket.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class FileUtils {
	
	public static void createDirectory(String directory) {
		
		File dir = new File(directory);
		if (!dir.exists()) {
			dir.mkdir();
		}
		
	}
	
	public static void writeBytesInFile(String path, String file) {
		writeBytesInFile(path, Base64.getDecoder().decode(file.getBytes()));
	}
	
	public static void writeBytesInFile(String path, byte[] file) {
		
		FileOutputStream stream = null;
		try {
			
			stream = new FileOutputStream(path);
			stream.write(file);
			stream.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void removeFile(String path) {
		new File(path).delete();
	}

}
