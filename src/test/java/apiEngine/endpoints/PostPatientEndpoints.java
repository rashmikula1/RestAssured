package apiEngine.endpoints;

import java.io.File;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import apiEngine.model.request.LoginRequest;
import apiEngine.model.request.PostPatientRequest;
import apiEngine.routes.LoginRoutes;
import apiEngine.routes.PatientRoutes;
import dataProviders.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.LoggerLoad;

public class PostPatientEndpoints {

String baseUrl;
	
	public PostPatientEndpoints(String baseUrl)
	{
		this.baseUrl = baseUrl;
	}

	public Response postPatient(PostPatientRequest postPatientRequest)
	{
		LoggerLoad.logInfo("baseUrl" + baseUrl );
		String json = null;
		RestAssured.baseURI = baseUrl;
		ObjectMapper mapper = new ObjectMapper();
         try {
             json = mapper.writeValueAsString(postPatientRequest);
             System.out.println("ResultingJSONstring = " + json);
             //System.out.println(json);
         } catch (JsonProcessingException e) {
             e.printStackTrace();
         }
		RequestSpecification request = RestAssured.given().queryParam("patientInfo", json);
		request.header("Content-Type", "application/json");
		String token = "Bearer "+ConfigReader.getProperty("login.token");
		request.header("Authorization", token);
		
		Response response = request.log().all().when()
				.contentType("multipart/form-data")
				.multiPart("file", new File("./src/test/resources/Diabetic.pdf"))
				.post(PatientRoutes.addPatient());
		
		return response;
	}
	
	public Response putPatient(PostPatientRequest postPatientRequest,String dataKey)
	{
		LoggerLoad.logInfo("baseUrl" + baseUrl );
		String json = null;
		RestAssured.baseURI = baseUrl;
		ObjectMapper mapper = new ObjectMapper();
		 try {
             json = mapper.writeValueAsString(postPatientRequest);
             System.out.println("ResultingJSONstring = " + json);
             //System.out.println(json);
         } catch (JsonProcessingException e) {
             e.printStackTrace();
         }
		RequestSpecification request = RestAssured.given().queryParam("patientInfo", json);
		
		request.header("Content-Type", "application/json");
		String token = "Bearer "+ConfigReader.getProperty("login.token");
		request.header("Authorization", token);
		
		Response response = request.log().all().when()
				.contentType("multipart/form-data")
				.multiPart("file", new File("./src/test/resources/Hypo Thyroid-Report.pdf"))
				.put(PatientRoutes.updatePatient(Integer.parseInt(ConfigReader.getProperty("patient.patientId")),dataKey));
		
		return response;
	}
	
	
	public Response getPatient(String dataKey)
	{
		LoggerLoad.logInfo("baseUrl" + baseUrl );
		RestAssured.baseURI = baseUrl;
		String token = "Bearer "+ConfigReader.getProperty("login.token");
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.header("Authorization", token);
		Response response = request.log().all().when().get(PatientRoutes.getPatient(dataKey));
		return response;
		
	}
}
