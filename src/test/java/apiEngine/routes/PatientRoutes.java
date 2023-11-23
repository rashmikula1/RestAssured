package apiEngine.routes;

import dataProviders.ConfigReader;

public class PatientRoutes {
	
	
	public static String addPatient()
	{
		return ConfigReader.getPatientPostUrl();
	}
	
	public static String updatePatient(Integer patientId,String dataKey)
	{
		String endpoint = null;
		if("put_patient_invalid_patientid".equals(dataKey))
			endpoint = ConfigReader.getPatientPutUrl() + "/0000" ;
		else 
			endpoint = ConfigReader.getPatientPutUrl() + "/" + patientId;;	
		return endpoint;
		
	}
	
	public static String getPatient(String dataKey)
	{
		String endpoint = null;
		if("get_patient_invalid_endpoint".equals(dataKey))
			endpoint = ConfigReader.getPatientGetUrl() + "0000" ;
		else 
			endpoint = ConfigReader.getPatientGetUrl();	
		return endpoint;
		
	}
	
}
