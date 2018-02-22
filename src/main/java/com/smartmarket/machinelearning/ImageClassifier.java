package com.smartmarket.machinelearning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.smartmarket.endpoint.ProductML;
import com.smartmarket.utils.XMLConfig;
import com.smartmarket.utils.XMLType;

public class ImageClassifier {
	
	private List<String> results;
	
	public void recognizeImage() {
		processImage(XMLConfig.getConfig(XMLType.MLPhotosPredict)+"image.jpg", XMLConfig.getConfig(XMLType.MLPythonPredict));
	}
	
	public void detectStockBreak() {
		processImage(XMLConfig.getConfig(XMLType.MLPhotosPredict)+"image-break.jpg", XMLConfig.getConfig(XMLType.MLPythonPredictBreak));
	}
	
	private void processImage(String imagePath, String codePath) {
		
		results = new ArrayList<String>();
		
		String commands[] = {
			"/Library/Frameworks/Python.framework/Versions/3.6/bin/python3",
			codePath,
			imagePath
		};
		try {

			Scanner scanner = new Scanner(Runtime.getRuntime().exec(commands).getInputStream());
			while (scanner.hasNext()) {
				
				String value = scanner.nextLine();
				System.out.println(value);
				results.add(value);
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void trainImages() {
		
		String commands[] = {
			"/bin/bash",
			"-c",
			XMLConfig.getConfig(XMLType.MLPythonScriptTrain)
		};	
		try {
			
		    ProcessBuilder pb = new ProcessBuilder(commands);
	
	        pb.redirectErrorStream(true);
	        Process proc = pb.start();
	
	        Reader reader = new InputStreamReader(proc.getInputStream());
	        BufferedReader bf = new BufferedReader(reader);
	        String s;
	 
			while ((s = bf.readLine()) != null) {
			    System.out.println(s);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
	
	public ProductML getResult() {
		
		ProductML product = new ProductML();
		for (String result : results) {
			
			double value = filterPorcentage(result);
			if (value > product.getPorcentage()) {
				
				product.setName(filterProductName(result));
				product.setPorcentage(value);
				
			}
		}
		return product;
		
	}
	
	private double filterPorcentage(String str) {
		return Double.parseDouble(str.substring(str.indexOf("(")+1, str.indexOf("%)")));
	}
	
	private String filterProductName(String str) {
		return str.substring(0, str.indexOf("("));
	}

}
