package stepdefinitions;

import static org.testng.Assert.assertEquals;

import dataProviders.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import utilities.LoggerLoad;

public class LogoutSteps {
	Response response;
	 String baseurl = ConfigReader.getProperty("base.url");
	//String baseurl = "https://dietician-dev-41d9a344a720.herokuapp.com/dietician";
	 String Url;
	

    @Given("User creates GET Request with API endpoint")
	public void user_creates_get_request_for_the_dietician_or_patient_api_endpoint() {
	
	
	
    Url= baseurl+ConfigReader.getProperty("logout.get");
    System.out.println(Url);
    response= RestAssured.given().header("Authorization", "Bearer "+ ConfigReader.getProperty("login.token")).when().get(Url);

    }
    
    @When("User sends HTTPS Request")
	public void user_sends_https_request() {
		
		response= RestAssured.given().header("Authorization", "Bearer "+ ConfigReader.getProperty("login.token")).when().get(Url);

		
      
   	}
    
    @Then("Server should respond back with HTTP Status code {int} with message {string}")
	public void server_should_respond_back_with_http_status_code_with_message(Integer int1, String string) {
	    
		 int statusCode = response.getStatusCode();
	        assertEquals(statusCode, int1, "Expected status code 200, but found " + statusCode);

	        
	        String responseBody = response.getBody().asString();
	        assertEquals(responseBody, string, "Unexpected response message");
			
	
	}
    @When("User sends HTTPS Request with invalid token")
	public void user_sends_https_request_with_invalid_token() {
		
		response= RestAssured.given().header("Authorization", "Bearer "+ ConfigReader.getProperty("invalidtoken")).when().get(Url);
   
		
	}

	@Then("Server should respond back with HTTP Status code {int}")
	public void server_should_respond_back_with_http_status_code(Integer int1) {
	try {	
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
     if(statusCode==int1) {

    	 response.body();
		response.then().body(JsonSchemaValidator.matchesJsonSchema(getClass()
	              .getClassLoader()
	              .getResourceAsStream("postlogin401response.json")));
		
		

	      	LoggerLoad.logInfo("Login POST unsuccessful");
	      	
     }}
     catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
     
       
      //  String responseBody = response.getBody().asString();
        //assertEquals(responseBody, string, "Unexpected response message");
		
	   
	}
	
	@When("User sends HTTPS Request with missing token")
	public void user_sends_https_request_with_missing_token() {
	    
		response= RestAssured.given().when().get(Url);

	}
	
	@When("User sends HTTPS Request missing endpoint")
	public void user_sends_https_request_missing_endpoint() {
	    
		response= RestAssured.given().header("Authorization", "Bearer "+ ConfigReader.getProperty("login.token")).when().get(baseurl);

	}
	
	@When("User sends HTTPS Request invalid endpoint")
	public void user_sends_https_request_invalid_endpoint() {
	    
		 String invalidendpoint= ConfigReader.getProperty("invalidEndPoint");
		
		response= RestAssured.given().header("Authorization", "Bearer "+ ConfigReader.getProperty("login.token")).when().get(invalidendpoint);

	}

    
    

}
