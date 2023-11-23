package stepdefinitions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import apiEngine.model.request.LoginRequest;
import dataProviders.ConfigReader;
import dataProviders.ExcelReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import utilities.LoggerLoad;

public class PatientLoginSteps {
	 String baseurl = ConfigReader.getProperty("base.url");
	// String baseurl = "https://dietician-dev-41d9a344a720.herokuapp.com/dietician";
	  RequestSpecification requestSpec;
	//  Response response;
	  Response resp;
	  ValidatableResponse valid_resp;
	  LoginRequest loginrequest;

	@Given("User creates POST Request for the patient API endpoint")
	public void user_creates_post_request_for_the_patient_api_endpoint_using_and_from_excel() {
	   
		 try {
		      
		      Map<String, String> excelDataMap = null;
		      excelDataMap = ExcelReader.getData("login_patient", "login");
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
		            .given().header("Authorization", "Bearer "+ ConfigReader.getProperty("login.token")).log().all()
		            .header("Content-Type", "application/json")
		            .body(loginrequest);
		        
		     /*   Header contentType = new Header("Content-Type","application/json");
		        Header authorization = new Header("Authorization","Bearer "+ ConfigReader.getProperty("login.token"));
		        List<Header> headerList = new ArrayList<Header>();
		        headerList.add(contentType);
		        headerList.add(authorization);
		        Headers header = new Headers(headerList);

		        this.requestSpec =
				          RestAssured.given()
	            .headers(header);*/
		      }
	    	} catch (Exception ex) {
			      LoggerLoad.logInfo(ex.getMessage());
			      ex.printStackTrace();
	    }
	}
	
	@When("User sends HTTPS Request with valid credentials for patient on the request Body.")
	public void user_sends_https_request_with_valid_credentials_for_patient_on_the_request_body() {
	    
		
		 try 
			{
			 
			 
				this.resp = this.requestSpec.when().post(ConfigReader.getProperty("login.post"));
				
			}catch (Exception ex) 
			{
				LoggerLoad.logInfo(ex.getMessage());
				ex.printStackTrace();
			}
	}
	@Then("User receives Status {int} with response body.")
	public void user_receives_status_with_response_body(Integer int1) {
	    
		try
		{this.resp.then().log().all().extract().response();
		  
			System.out.println("The response is........"+resp.statusCode());
		  
					resp.then().log().all().assertThat().statusCode(200)
				        .and().body(JsonSchemaValidator.matchesJsonSchema(getClass()
				              .getClassLoader()
				              .getResourceAsStream("postloginresponseschema.json")));
				      	LoggerLoad.logInfo("Patient Login POST successful");
				      			      	 
				      	System.out.println(resp.getBody());
				      	String responseMsg=resp.toString();
		}
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
				   	  
		    					      	
		  
	}
	

}
