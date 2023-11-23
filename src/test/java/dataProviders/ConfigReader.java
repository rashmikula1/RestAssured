package dataProviders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import utilities.LoggerLoad;


public class ConfigReader {

	private static Properties properties;
	private static final String propertyFilePath = "src/test/resources/configs/Configuration.properties";

	public static void loadProperty() 
	{
		BufferedReader reader;
		try 
		{
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			try 
			{
				properties.load(reader);
				reader.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		}
	}
	
	public static String getProperty( String key) {
		return properties.getProperty(key);
	}
	
	public static void setProperty(String key, String value) throws IOException {
		
		FileOutputStream out;
		try {
			out = new FileOutputStream(propertyFilePath);
			properties.setProperty(key, value);;
			properties.store(out, null);
		} catch (FileNotFoundException e) {
			LoggerLoad.logInfo(e.getMessage());
			e.printStackTrace();
		}	 
	}
	

	
	// login urls
	
	public static String getloginPostUrl()
	{
		String val = properties.getProperty("login.posturl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("login.posturl not specified in the Configuration.properties file.");
	}
	
	public static String getPatientPostUrl()
	{
		String val = properties.getProperty("patient.posturl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("patient.posturl not specified in the Configuration.properties file.");
	}
	
	public static String getPatientPutUrl()
	{
		String val = properties.getProperty("patient.posturl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("patient.puturl not specified in the Configuration.properties file.");
	}
	
	public static String getPatientGetUrl()
	{
		String val = properties.getProperty("patient.posturl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("patient.geturl not specified in the Configuration.properties file.");
	}
	public static String  getMorbidityGetUrl()
	{
		String val = properties.getProperty("morbidity.url");
	if (val != null)
		return val;
	else
		throw new RuntimeException("morbidity.geturl not specified in the Configuration.properties file.");
	}
	
	}
