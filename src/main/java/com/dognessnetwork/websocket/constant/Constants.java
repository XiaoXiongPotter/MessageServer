package com.dognessnetwork.websocket.constant;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Constants {

    public final static Logger logger = LoggerFactory.getLogger(Constants.class);

    public static String SERVER_CONTEXT_DOMAIN = "";
    public static String SERVER_CONTEXT_PATH = "";
	
	static {
		try {
			Properties prop = new Properties();
			InputStream in = Constants.class.getClassLoader().getResourceAsStream("config.properties");
			prop.load(in);
			SERVER_CONTEXT_DOMAIN = prop.getProperty("SERVER_CONTEXT_DOMAIN");
			SERVER_CONTEXT_PATH = prop.getProperty("SERVER_CONTEXT_PATH");
		} catch (IOException e) {
			logger.error("", e);
		}
	}
	
}
