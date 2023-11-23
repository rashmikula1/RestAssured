package apiEngine.routes;

import dataProviders.ConfigReader;
public class GetMorbidityRoutes {

	public static String getMorbidity(String dataKey)
	{
		String endpoint = null;
		if("get_all_morbidity_invalid_endpoint".equals(dataKey))
			endpoint = ConfigReader.getMorbidityGetUrl() + "0000" ;
		else 
			endpoint = ConfigReader.getPatientGetUrl();	
		return endpoint;
	}
}
