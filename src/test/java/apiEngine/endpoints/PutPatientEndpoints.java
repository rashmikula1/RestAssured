package apiEngine.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import apiEngine.model.request.PostPatientRequest;
import apiEngine.routes.PatientRoutes;
import dataProviders.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.LoggerLoad;

public class PutPatientEndpoints {

String baseUrl;
	
	public PutPatientEndpoints(String baseUrl)
	{
		this.baseUrl = baseUrl;
	}

	public Response putPatient(PostPatientRequest putPatientRequest)
	{
		LoggerLoad.logInfo("baseUrl" + baseUrl );
		String json = null;
		RestAssured.baseURI = baseUrl;
		ObjectMapper mapper = new ObjectMapper();
         try {
             json = mapper.writeValueAsString(putPatientRequest);
             System.out.println("ResultingJSONstring = " + json);
             //System.out.println(json);
         } catch (JsonProcessingException e) {
             e.printStackTrace();
         }
		RequestSpecification request = RestAssured.given().queryParam("patientInfo", json);
		//RequestSpecification request = RestAssured.given().body(postPatientRequest);
		request.header("Content-Type", "application/json");
		String token = "Bearer "+ConfigReader.getProperty("login.token");
		request.header("Authorization", token);
		
		Response response = request.log().all().when().post(PatientRoutes.addPatient());
		
		return response;
	}


	
}
