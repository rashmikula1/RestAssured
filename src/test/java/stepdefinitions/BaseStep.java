package stepdefinitions;


import java.util.Map;

import apiEngine.endpoints.GetMorbidityEndpoints;
import apiEngine.endpoints.LoginEndpoints;
import apiEngine.endpoints.PostPatientEndpoints;
import apiEngine.endpoints.PutPatientEndpoints;
import apiEngine.model.request.LoginRequest;

import apiEngine.model.response.LoginResponse;
import dataProviders.ConfigReader;
import dataProviders.ExcelReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import utilities.LoggerLoad;


public class BaseStep {
	
	static final String baseUrl = ConfigReader.getProperty("base.url");
	
	LoginEndpoints loginEndpoints;
	PostPatientEndpoints postPatientEndpoints;
	PutPatientEndpoints putPatientEndpoints;
	GetMorbidityEndpoints getMorbidityEndpoints;
    public BaseStep() 
    {
    	loginEndpoints = new LoginEndpoints(baseUrl);
    	postPatientEndpoints = new PostPatientEndpoints(baseUrl);
         putPatientEndpoints = new PutPatientEndpoints(baseUrl);
         getMorbidityEndpoints = new GetMorbidityEndpoints(baseUrl);
    }
    
   
}
