package com.qait.tcautomation.util;

import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * @author amitmalik
 * 
 */
public class PropertyFileReaderUtil {

	private static Properties properties = new Properties();

	
	/**
	 * Method to get the property from login.properties file
	 * 
	 * @param key
	 *            : Key for the property
	 * @return value for the key passed otherwise null
	 */
	public static String getLoginProperty(String key) {
		try {

			properties.load(PropertyFileReaderUtil.class
					.getResourceAsStream("/login.properties"));

		} catch (IOException e) {
			System.out.println(TcAutomationUtil
					.getExceptionDescriptionString(e));
			assertNotNull("Error in reading login.properties", e);
		} catch (Exception e) {
			System.out.println(TcAutomationUtil
					.getExceptionDescriptionString(e));
			assertNotNull("Error in reading login.properties", e);
		}

		return properties.getProperty(key).trim();
	}

	/**
	 * Method to get the property from slo.properties file
	 * 
	 * @param key
	 *            : Key for the property
	 * @return value for the key passed otherwise null
	 */
	public static String getSloProperty(String key) {
		try {

			properties.load(PropertyFileReaderUtil.class
					.getResourceAsStream("/slo.properties"));

		} catch (IOException e) {
			System.out.println(TcAutomationUtil
					.getExceptionDescriptionString(e));
			assertNotNull("Error in reading login.properties", e);
		} catch (Exception e) {
			System.out.println(TcAutomationUtil
					.getExceptionDescriptionString(e));
			assertNotNull("Error in reading login.properties", e);
		}

		return properties.getProperty(key).trim();
	}
}
