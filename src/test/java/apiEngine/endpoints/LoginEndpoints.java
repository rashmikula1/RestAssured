package apiEngine.endpoints;

import apiEngine.model.request.LoginRequest;
import apiEngine.routes.LoginRoutes;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.LoggerLoad;

public class LoginEndpoints {
	
	String baseUrl;
	
	public LoginEndpoints(String baseUrl)
	{
		this.baseUrl = baseUrl;
	}

	public Response login(LoginRequest loginReq)
	{
		LoggerLoad.logInfo("baseUrl" + baseUrl );
		RestAssured.baseURI = baseUrl;
		
		RequestSpecification request = RestAssured.given().body(loginReq);
		request.header("Content-Type", "application/json");
		
		Response response = request.when().post(LoginRoutes.login());
		
		return response;
	}
	
	
}
