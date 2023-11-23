package stepdefinitions;

import dataProviders.ConfigReader;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import utilities.RestAssuredRequestFilter;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import dataProviders.ConfigReader;
import dataProviders.ExcelReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import utilities.LoggerLoad;
import utilities.RestAssuredRequestFilter;
import apiEngine.model.request.PostPatientRequest;
import apiEngine.model.response.GetMorbidityResponse;


public class GetAllMorbiditySteps extends BaseStep {
	
	static final String baseUrl = ConfigReader.getProperty("base.url");
	static final String batchUrl = ConfigReader.getProperty("morbidity.url");
	RequestSpecification request;
	Response response;
	Map<String, String> excelDataMap;
	
	@Given("Dietician creates GET Request for all Morbidity API endpoint with fields from {string} with {string}.")
	public void dietician_creates_get_request_for_all_morbidity_api_endpoint_with_fields_from_with(String sheetName,
			String dataKey) {
		try {
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given().filter(new RestAssuredRequestFilter());
		request.header("Content-Type", "application/json");
		excelDataMap = null;
		
			excelDataMap = ExcelReader.getData(dataKey, sheetName);
			if (null != excelDataMap && excelDataMap.size() > 0) {

			LoggerLoad.logInfo("Paitient POST request created");
			if(dataKey.contains("invalid_token")) {
				ConfigReader.setProperty("login.token", "invalidtoken!!!");
			}
			if(dataKey.contains("patient_token")) {
				
			}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	}

	@When("Dietician sends HTTPS Request for all Morbidity API {string}.")
	public void dietician_sends_https_request_for_all_morbidity_api(String dataKey) {
		try {
			response = getMorbidityEndpoints.getAllMorbidity(dataKey);
			LoggerLoad.logInfo("Patient POST request sent");
		} catch (Exception ex) {
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@Then("Dietician receives response for all Morbidity API {string} with {string}.")
	public void dietician_receives_response_for_all_morbidity_api_with(String sheetName,
			String dataKey) {
      try {
		response.then().log().all().extract().response();

		switch (dataKey) {
		case "get_all_morbidity_valid":
			response.then().assertThat()
					// Validate response status
					.statusCode(HttpStatus.SC_OK);
					
					/*// Validate content type
					.contentType(ContentType.JSON)
					// Validate json schema
					.body(JsonSchemaValidator.matchesJsonSchema(
							getClass().getClassLoader().getResourceAsStream("getpatientresponseschema.json")));*/

			

			break;
			
			
		case "get_all_morbidity_invalid_endpoint":
			
			response.then().assertThat()
			// Validate response status
			.statusCode(HttpStatus.SC_NOT_FOUND);
			/*
			// Validate content type
			.contentType(ContentType.JSON)
			// Validate json schema
			.body(JsonSchemaValidator.matchesJsonSchema(
					getClass().getClassLoader().getResourceAsStream("401or403or404or500responseschema.json")));
			*/
			break;
		
		case "get_all_morbidity_invalid_token":	
			
			response.then().assertThat()
			// Validate response status
			.statusCode(HttpStatus.SC_UNAUTHORIZED);
			/*
			// Validate content type
			.contentType(ContentType.JSON)
			// Validate json schema
			.body(JsonSchemaValidator.matchesJsonSchema(
					getClass().getClassLoader().getResourceAsStream("401or403or404or500responseschema.json")));
					*/
			break;
			
		case "get_all_morbidity_patient_token":	
			
			response.then().assertThat()
			// Validate response status
			.statusCode(HttpStatus.SC_FORBIDDEN);
			/*
			// Validate content type
			.contentType(ContentType.JSON)
			// Validate json schema
			.body(JsonSchemaValidator.matchesJsonSchema(
					getClass().getClassLoader().getResourceAsStream("401or403or404or500responseschema.json")));
					*/
			break;
			
		

		default:
			assertTrue(false);
			break;
		}
	} catch (Exception ex) {
		LoggerLoad.logInfo(ex.getMessage());
		ex.printStackTrace();
	}


	   
	}

}
