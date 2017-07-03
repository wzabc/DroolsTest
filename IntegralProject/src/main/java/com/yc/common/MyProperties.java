package com.yc.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MyProperties extends Properties {
	private static MyProperties myProperties;

	private MyProperties() throws IOException {
		InputStream iss = MyProperties.class.getClassLoader().getResourceAsStream("db.properties");
		this.load(iss);
	}

	public static MyProperties getInstance() throws IOException {
		if (null == myProperties) {
			myProperties = new MyProperties();
		}
		return myProperties;
	}
}