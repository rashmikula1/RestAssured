package apiEngine.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import apiEngine.routes.GetMorbidityRoutes;
import apiEngine.routes.PatientRoutes;
import dataProviders.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.LoggerLoad;


public class GetMorbidityEndpoints {
	
	
String baseUrl;
	
	public GetMorbidityEndpoints(String baseUrl)
	{
		this.baseUrl = baseUrl;
	}

	public Response getAllMorbidity(String dataKey)
	{
		LoggerLoad.logInfo("baseUrl" + baseUrl );
		RestAssured.baseURI = baseUrl;
		String token = "Bearer "+ConfigReader.getProperty("login.token");
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.header("Authorization", token);
		Response response = request.log().all().when().get(GetMorbidityRoutes.getMorbidity(dataKey));
		return response;
		
	}

	
	

}
