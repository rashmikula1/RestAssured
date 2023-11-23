package apiEngine.routes;

import dataProviders.ConfigReader;

public class LoginRoutes {
	
	public static String login()
	{
		return ConfigReader.getloginPostUrl();
	}
	
	

}
