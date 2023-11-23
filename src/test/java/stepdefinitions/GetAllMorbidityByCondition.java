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


import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;




import java.io.FileInputStream;
import java.io.IOException;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.http.HttpStatus;
//import io.restassured.path.json.exception.*;
import org.json.simple.JSONObject;


import utilities.LoggerLoad;
import utilities.RestAssuredRequestFilter;
import dataProviders.ConfigReader;


public class GetAllMorbidityByCondition {
	
	



}
