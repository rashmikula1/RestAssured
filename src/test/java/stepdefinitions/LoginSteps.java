package stepdefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import apiEngine.model.request.LoginRequest;

import apiEngine.model.response.LoginResponse;
import dataProviders.ExcelReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
//import io.restassured.path.json.exception.*;
import org.json.simple.JSONObject;

import utilities.LoggerLoad;
import dataProviders.ConfigReader;

public class LoginSteps {
	static final String baseUrl = ConfigReader.getProperty("base.url");
  String baseurl = "https://dietician-dev-41d9a344a720.herokuapp.com/dietician";
  RequestSpecification requestSpec;
  Response resp;
  Response response;
  ValidatableResponse valid_resp;
  LoginRequest loginrequest;
  private int statusCode;
   
  @Given("User creates POST Request for the dietician API endpoint using {string} and {string} from excel.")
  public void user_creates_post_request_for_the_dietician_api_endpoint_using_and_from_excel(String dataKey,String sheetName) {
try {
	      
	      Map<String, String> excelDataMap = null;
	      excelDataMap = ExcelReader.getData(dataKey, sheetName);
	      String userId = null, password = null;
	      if (null != excelDataMap && excelDataMap.size() > 0) 
	      {	
	    	  if(!excelDataMap.get("userId").isBlank())
	    		  userId = excelDataMap.get("userId");
	    	  if(!excelDataMap.get("password").isBlank())
	    		  password = excelDataMap.get("password");
	          
	          
	    	  loginrequest = new LoginRequest(userId , password);     
	    	 
	
	        RestAssured.baseURI = baseurl;
	        this.requestSpec =
	          RestAssured
	            .given().log().all()
	            .header("Content-Type", "application/json")
	            .body(loginrequest);
	        
	      }
    	} catch (Exception ex) {
		      LoggerLoad.logInfo(ex.getMessage());
		      ex.printStackTrace();
    }

  }

  @When("User sends HTTPS Request with valid credentials on the request Body.")
  public void user_sends_https_request_with_valid_credentials_on_the_request_body() {
	  try 
		{
			this.resp = this.requestSpec.when().post(ConfigReader.getProperty("login.post"));
			
		}catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
  }

@Then("User receives Status with response body {string} and {string} from excel.")
  public void user_receives_status_with_response_body_and_from_excel( String DataKey, String sheetName) {
	  try 
		{  
		  String jsonString = this.resp.asString();
		  this.resp.then().log().all().extract().response();
		  if(DataKey.contains("login_valid_dietician"))
				
		  {
					resp.then().log().all().assertThat().statusCode(200)
				        .and().body(JsonSchemaValidator.matchesJsonSchema(getClass()
				              .getClassLoader()
				              .getResourceAsStream("postloginresponseschema.json")));
				      	LoggerLoad.logInfo("Login POST successful");
				      			      	 
				      	System.out.println(resp.getBody());
				      	String responseMsg=resp.toString();
				      	
				   	  
		    					      	
		  }
				      
		  else if(DataKey.contains("login_invalid_dietician")||DataKey.contains("login_invalidPass_dietician")||DataKey.contains("login_invalidEmail_dietician")||DataKey.contains("login_missingCred_dietician"))
				      	
				
		  {
					resp.then().log().all().assertThat().statusCode(401)
					.and().body(JsonSchemaValidator.matchesJsonSchema(getClass()
				              .getClassLoader()
				              .getResourceAsStream("postlogin401responseschema.json")));
				      	LoggerLoad.logInfo("Login POST unsuccessful");
		  }	   
		  
		  
		  else {
					resp.then().log().all().assertThat().statusCode(404);
					LoggerLoad.logInfo("Login POST Bad Request");
						
			}
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}

  }

  @Given("User creates POST Request for the login API.")
  public void user_creates_post_request_for_the_login_api() {
  	
  	try {
  	 Map<String, String> excelDataMap = null;
	      excelDataMap = ExcelReader.getData("login_valid_dietician", "login");
	      String userId = null, password = null;
	      if (null != excelDataMap && excelDataMap.size() > 0) 
	      {	
	    	  if(!excelDataMap.get("userId").isBlank())
	    		  userId = excelDataMap.get("userId");
	    	  if(!excelDataMap.get("password").isBlank())
	    		  password = excelDataMap.get("password");
	          
	          
	    	  loginrequest = new LoginRequest(userId , password);     
	    	 
	
	        RestAssured.baseURI = baseUrl;
	        this.requestSpec =
	          RestAssured
	            .given().log().all()
	            .header("Content-Type", "application/json")
	            .body(loginrequest);
	        
	      }
 	} catch (Exception ex) {
		      LoggerLoad.logInfo(ex.getMessage());
		      ex.printStackTrace();
 }

  }
 @When("User sends HTTPS Request with credentials on the request Body.")
  public void user_sends_https_request_valid_credentials_on_the_request_body() {
	  try 
		{
			this.resp = this.requestSpec.when().post(ConfigReader.getProperty("login.post"));
			
		}catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
  }


  @Then("User receives Status with response body containing token information.")
  public void user_receives_status_with_response_body_containing_token_information() {
  	 try 
		{  
		  String jsonString = this.resp.asString();
		  this.resp.then().log().all().extract().response();
		  resp.then().log().all().assertThat().statusCode(200)
				        .and().body(JsonSchemaValidator.matchesJsonSchema(getClass()
				              .getClassLoader()
				              .getResourceAsStream("postloginresponseschema.json")));
				      	LoggerLoad.logInfo("Login POST successful");
				      			      	 
				      	
				      	LoginResponse loginResponse = resp.getBody().as(LoginResponse.class);
				      	ConfigReader.setProperty("login.token", loginResponse.token);
				      	
	    } 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
  }
  
   @Given("User creates POST Request for the dietician API endpoint")
  public void user_creates_post_request_for_the_dietician_api_endpoint() {
	  
	  this.resp = RestAssured.get(ConfigReader.getProperty("invalidEndPoint"));
      
  }
   @When("User sends HTTPS Request with invalid end points")
  public void user_sends_https_request_with_invalid_end_points() {
	  
	  try 
		{
		  
	        statusCode = this.resp.getStatusCode();
	        
	       System.out.println("status "+statusCode);
	     
			
		}catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
    
      
  }

  @Then("User receives Status with response body")
  public void user_receives_status_with_response_body() {
	  
	  System.out.println("status code  "+statusCode);
	   org.testng.Assert.assertEquals(statusCode, 401, "Invalid endpoint. Expected status code 404.");
	  
	  
      
  }
 
 
  }