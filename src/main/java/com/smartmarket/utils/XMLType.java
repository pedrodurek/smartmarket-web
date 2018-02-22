package com.smartmarket.utils;

public enum XMLType {
	
	/* Paths */
	MLPythonPredict("paths.mlpython.predict"),
	MLPythonPredictBreak("paths.mlpython.predictbreak"),
	MLPythonScriptTrain("paths.mlpython.scripttrain"),
	MLPhotosPredict("paths.mlphotos.predict"),
	MLPhotosTrain("paths.mlphotos.train");
	
	private String param;
	
	private XMLType(String param) {
		this.setParam(param);
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
	

}
